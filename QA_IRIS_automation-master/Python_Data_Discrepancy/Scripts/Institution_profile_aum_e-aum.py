""" Compares the AUM and EquityAUM of institution Profile page against Factset Data Feed Export.

Steps to run:
1. Update config.py
    a. Update Auth token
    b. Update Api url to point to intended environment (stage, dev, ...)
2. Update database-config.py
    a. Update parameters to point to the correct database server (Only uses postgres database)
3. Run script

Output: Adding two new columns, Desktop EquityAUM and Desktop AUM to the original CSV export.
"""
import json
import sys

import numpy
import requests
import config as cfg
from helper import database_query_helper as db
import timeit

desktop_aum = []
desktop_equity_aum = []
desktop_discrepency = []


def main(argv):
	api_url = cfg.api_url
	# Factset Data Spreadsheet
	query = \
		'select q4_institution_values.factset_entity_id, ' \
		'own_ent_institutions.total_aum as aum, q4_institution_values.equity_aum ' \
		'from q4_institution_values inner join own_ent_institutions ' \
		'on q4_institution_values.factset_entity_id = own_ent_institutions.factset_entity_id'
	data = db.execute_sql_postgres(query)
	print(data.to_string())

	# How many rows of data to run
	start_range = 10000
	end_range = len(data)

	# If the user puts in the Authorisation token, replace it with the one below
	if len(argv) == 0:
		auth_token = cfg.auth_token
		print("If you haven't done so, remember to update the Authorization Token by putting it in quotes as an argument for the script.")
	else:
		auth_token = argv[0]

	# If START RANGE doesn't start from 0
	for i in range(0, start_range):
		add_to_columns("N/A", "N/A", "")

	try:
		for i in range(start_range, end_range):
			discrepancy = ''

			institution_url = str(data.loc[i, 'factset_entity_id']).replace(" ", "")
			if not numpy.isnan(data.loc[i, 'aum']):
				institution_aum = int(data.loc[i, 'aum'])
			else:
				institution_aum = None
			if not numpy.isnan(data.loc[i, 'equity_aum']):
				institution_eaum = int(data.loc[i, 'equity_aum'])
			else:
				institution_eaum = None

			# API call that gathers data from Desktop. Can change environments, dev, stage and production
			url = api_url + "api/institution/info/" + institution_url + "?_dc=1562860982524&appver=3.1.4&_dc=1562860982524"
			profile_resp = retry_requests(url, auth_token)
			if profile_resp is not None:
				resp_data = json.loads(profile_resp.text)
			else:
				add_to_columns("Request ISSUE", "Request ISSUE", "")
				continue

			# If you're have a LOT of "---ERROR xxx", try updating the Bearer Token. Check out README.md
			try:
				institution_desktop_aum = resp_data['data']['total_aum']
				institution_desktop_eaum = resp_data['data']['equity_aum']
				if institution_eaum != institution_desktop_eaum:
					discrepancy += "Desktop EAUM: " + str(institution_desktop_eaum) + "--- Factset Data Feed EAUM: " + str(institution_eaum)
				if institution_aum != institution_desktop_aum:
					discrepancy += "Desktop AUM: " + str(institution_desktop_aum) + "--- Factset Data Feed AUM: " + str(institution_aum)
				print(institution_url + " " + discrepancy)
				add_to_columns(str(institution_desktop_aum), str(institution_desktop_eaum), discrepancy)

			except TypeError:
				print("--- ERROR with institution ID:" + institution_url + " ---")
				add_to_columns("NO DATA", "", "")

	finally:
		# If END RANGE doesn't finish at the end of the document
		for j in range(len(desktop_aum), len(data)):
			add_to_columns("N/A", "N/A", "")
		data['Desktop AUM'] = desktop_aum
		data['Desktop Equity AUM'] = desktop_equity_aum
		data['Discrepancy'] = desktop_discrepency
		data.to_csv("../Excel_SpreadSheets/Output_Data/AUM_Equity-AUM/GDDY_Fund_Profile_aum_eaum_surveillance-off_1.csv", index=False)


def add_to_columns(aum, e_aum, discrepancy) -> None:
	desktop_aum.append(aum)
	desktop_equity_aum.append(e_aum)
	desktop_discrepency.append(discrepancy)


def retry_requests(url, authorisation):
	try:
		resp = requests.get(url, headers={"Authorization": authorisation})
		return resp

	except Exception:
		print("--Request Failed--")
		return


if __name__ == '__main__':
	start = timeit.default_timer()
	main(sys.argv[1:])
	end = timeit.default_timer()
	print(end - start)
