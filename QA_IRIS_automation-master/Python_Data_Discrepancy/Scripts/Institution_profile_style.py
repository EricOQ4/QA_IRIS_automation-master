"""
Checks if Institution Profile Style matches Postgres and Cyclops database

Steps to run:
1. Update config.py
    a. Update Auth token
    b. Update Api url to point to intended environment (stage, dev, ...)
2. Update database-config.py
    a. Update parameters to point to the correct database server (doesn't use mongodb)
3. Run script

Output: Institution_Styles list with 2 new columns, Discrepancy Column and Discrepancy Source
"""
import json
import ssl
import sys

import pandas as pd
import requests
import config as cfg
from helper import database_query_helper as db

api_url = cfg.api_url
ssl._create_default_https_context = ssl._create_unverified_context
discrepancy_column = []
discrepancy_source = []


def main(argv):

	# Cyclops Data Spreadsheet
	cyclops_data_query = \
		'SELECT TOP 10000 FSInstitution.FactSetEntityId as factset_entity_id, ' \
		'COALESCE(Manager.Name, FSStandardEntity.EntityProperName) as name, ' \
		'ManagerStyleMap.Style as ManagerStyle, FSInstitutionFixed.Style as InstitutionFixedStyle ' \
		'FROM FSInstitution LEFT JOIN ManagerFSLink ' \
		'ON (ManagerFSLink.FactSetEntityId = FSInstitution.FactSetEntityId) ' \
		'LEFT JOIN Manager ON (Manager.Id = ManagerFSLink.ManagerId) ' \
		'LEFT JOIN ManagerStyleMap ON (ManagerStyleMap.StyleId = Manager.Style ' \
		'AND ManagerStyleMap.StyleId != 0) ' \
		'LEFT JOIN FSStandardEntity ON (FSStandardEntity.FactSetEntityId = FSInstitution.FactSetEntityId) ' \
		'LEFT JOIN FSInstitutionFixed ON (FSInstitutionFixed.FactSetEntityId = FSInstitution.FactSetEntityId) ' \

	data = db.execute_sql_mssql(cyclops_data_query)
	print(data.to_string())
	# data.columns = ['factset_entity_id', 'name', 'ManagerStyle', 'InstitutionFixedStyle']

	hedge_fund_query = 'SELECT FactSetEntityId as \'FactSet ID\', EntitySubType from FSInstType;'
	hedge_fund_data = db.execute_sql_mssql(hedge_fund_query)
	# hedge_fund_data.columns = ['FactSet ID', 'EntitySubType']
	hedge_fund_data.set_index('FactSet ID', inplace=True)

	# FactSet Data Spreadsheet
	factset_query = \
		'select factset_entity_id as "Factset Entity Id", style as "Style", turnover_label as TurnoverLabel, ' \
		'total_aum as TotalAUM from own_ent_institutions'
	factset_data = db.execute_sql_postgres(factset_query)
	# factset_data.columns = ['Factset Entity Id', 'Style', 'TurnoverLabel', 'TotalAUM']
	factset_data.set_index('Factset Entity Id', inplace=True)

	# How many rows of data to run
	start_range = 0
	end_range = len(data)

	# If the user puts in the Authorisation token, replace it with the one below
	if len(argv) == 0:
		auth_token = cfg.auth_token
		print("If you haven't done so, remember to update the Authorization Token by putting it in quotes as an argument for the script.")
	else:
		auth_token = argv[0]

	# If START RANGE doesn't start from 0
	for i in range(0, start_range):
		add_to_columns("N/A", "N/A")

	try:
		for i in range(start_range, end_range):

			institution_url = data.loc[i, 'factset_entity_id']
			manager_style = data.loc[i, 'ManagerStyle']
			fixed_style = data.loc[i, 'InstitutionFixedStyle']

			try:
				factset_style = factset_data.loc[institution_url, 'Style']
			except KeyError:
				factset_style = ''

			try:
				is_hedge_fund = hedge_fund_data.loc[institution_url, 'EntitySubType']
			except KeyError:
				is_hedge_fund = ''

			# API call that gathers data from Desktop. Can change api-stage to api-dev
			url = api_url + "api/institution/info/" + institution_url + "?_dc=1562094863180&appver=3.1.0&_dc=1562094863180"
			resp = try_requests(url, auth_token)

			if resp is not None:
				try:
					resp_data = json.loads(resp.text)
				except ValueError:
					add_to_columns("JSON LOAD FAILED", "JSON LOAD FAILED")
					continue
			else:
				add_to_columns("API Request FAILED", "API Request FAILED")
				continue

			print(institution_url)
			# If you're have a LOT of "---ERROR xxx", try updating the Bearer Token. Check out README.md
			try:
				desktop_style = str(resp_data['data']['style'])

				if str(manager_style) != 'None':
					if desktop_style != manager_style:
						print("Manager Style does not match of Institution ID:" + institution_url + "..." + str(resp_data['data']))
						add_to_columns(desktop_style, "MANAGER STYLE (Cyclops)")
					else:
						add_to_columns("", "")

				elif str(is_hedge_fund) == 'HF':
					# Manager Style is None but the institution is a Hedge Fund
					if desktop_style == 'Hedge Fund':
						add_to_columns("", "")
					else:
						print("Desktop Style should be Hedge Fund for Institution: " + institution_url)
						add_to_columns("Hedge Fund", "Hedge Fund List")

				elif str(fixed_style) != '':
					# We know that manager_style is NULL, Institution is not in Hedge Fund list and Institution Fixed Style is not NULL

					if desktop_style != fixed_style:
						print("InstitutionFixedStyle does not match Desktop ---" + str(resp_data['data']) + fixed_style)
						add_to_columns(desktop_style, "INSTITUTION FIXED STYLE (Cyclops)")

					else:
						add_to_columns("", "")

				else:
					# Manager Style and InstitutionFixedStyle are both NULL, then Desktop style should match Factset Data Feed
					if desktop_style == 'None' or desktop_style == "" or desktop_style == 'Unknown':
						add_to_columns("", "")

					else:
						# Desktop Style is not Unknown even though Manager Style and Institution Fixed Style are unknown. Check Factset Data Feed:
						if str(factset_style) == str(desktop_style):
							# Desktop Style matches FactSet Data Feed
							add_to_columns("", "")
						else:
							print("FACTSET STYLE: " + factset_style + " Desktop Style: " + desktop_style)
							print("Desktop Value does not match. Institution ID: " + institution_url + " --- " + desktop_style)
							add_to_columns(desktop_style, "FACTSET")

			except Exception:
				print("--- ERROR with Institution ID:" + institution_url + " ---")
				add_to_columns("NO DATA", "")

	finally:
		# If END RANGE doesn't finish at the end of the document
		for i in range(end_range, len(data)):
			add_to_columns("N/A", "N/A")

		data['Discrepancy Column'] = discrepancy_column
		data['Discrepancy Source'] = discrepancy_source
		data.to_csv("../Excel_SpreadSheets/Output_Data/Style/EIX_Institution_Profile_style_surveillance-off.csv", index=False)


def add_to_columns(discrepancy, source) -> None:
	discrepancy_column.append(discrepancy)
	discrepancy_source.append(source)


def try_requests(url, authorisation):
	try:
		resp = requests.get(url, headers={"Authorization": authorisation})
		return resp
	except Exception:
		print("--Request Failed--")
		return


if __name__ == '__main__':
	main(sys.argv[1:])
