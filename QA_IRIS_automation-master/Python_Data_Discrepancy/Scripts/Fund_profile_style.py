"""
Compares the style of Fund_profile against Postgres and Cyclops database. Follows logic that Desktop follows.

Steps to run:
1. Update config.py
    a. Update Auth token
    b. Update Api url to point to intended environment (stage, dev, ...)
2. Update database-config.py
    a. Update parameters to point to the correct database server (doesn't use mongodb)
3. Run script


Output: Fund_Styles list with 2 new columns, Discrepancy Column and Discrepancy Source

"""
import json
import ssl
import sys

from concurrent import futures
import pandas as pd
import requests
from helper import database_query_helper as db
from itertools import islice
import timeit
import config as cfg

ssl._create_default_https_context = ssl._create_unverified_context

discrepancy_column = []
discrepancy_source = []
api_url = cfg.api_url


def main(argv):
	override_query = "SELECT FundFSLink.FactSetFundId as factset_fund_id, " \
					 "StockFSLink.FSPermSecId as fs_perm_sec_id, " \
					 "FSStandardEntity.EntityProperName as name, " \
					 "ManagerStyleMap.Style as StockStyleOverride, StockFSLink.FSPermSecId as fs_perm_sec_id " \
					 "FROM StockFundOverride LEFT JOIN FundFSLink ON (FundFSLink.FundId = StockFundOverride.FundId) " \
					 "LEFT JOIN FSStandardEntity ON (FSStandardEntity.FactSetEntityId = FundFSLink.FactSetFundId) " \
					 "LEFT JOIN StockFSLink ON (StockFSLink.StockId = StockFundOverride.StockId) " \
					 "LEFT JOIN ManagerStyleMap ON (ManagerStyleMap.StyleId = StockFundOverride.Style) " \
					 "WHERE ManagerStyleMap.Style IS NOT NULL"
	factset_fund_query = "SELECT factset_fund_id, style, turnover_label from own_ent_funds"

	# Cyclops Data Spreadsheet
	# data = pd.read_csv("../Excel_SpreadSheets/Source_Data/Fund_Profile/Fund_Styles.csv")
	data = db.execute_sql_postgres(factset_fund_query)
	# data.columns = ['factset_fund_id', 'style', 'turnover_label']

	# stock_override = pd.read_csv("../Excel_SpreadSheets/Source_Data/Fund_Profile/Fund_Override_list.csv")
	stock_override = db.execute_sql_mssql(override_query)
	stock_override_columns = ['factset_fund_id', 'fs_perm_sec_id', 'name']
	stock_override = stock_override[stock_override_columns]
	stock_override.set_index('factset_fund_id', inplace=True)

	# How many rows of data to run
	start_range = 0
	end_range = len(data)

	# If the user puts in the Authorisation token, replace it with the one below
	if len(argv) == 0:
		auth_token = cfg.auth_token
		print("If you haven't done so, remember to update the Authorization Token by putting it in quotes as an argument for the script.")
	else:
		auth_token = argv[0]

	start_time = timeit.default_timer()
	result_df = pd.DataFrame(columns=['factset_fund_id', 'db_style', 'desktop_style',  'discrepancy'])

	with futures.ThreadPoolExecutor(max_workers=8) as executor:
		future_comparison = {executor.submit(compare_profile, i, auth_token, stock_override): i for i in islice(data.itertuples(), start_range, end_range)}
		for future in futures.as_completed(future_comparison):
			try:
				result_df = result_df.append(future.result(), ignore_index=True)
			except Exception as exc:
				print('generated an exception: %s' % exc)

	end_time = timeit.default_timer()
	print(start_time - end_time)
	result_df.to_csv("../Excel_SpreadSheets/Output_Data/Style/GDDY_Fund_Profile_Style_TRIAL.csv", index=False)


def compare_profile(i, auth_token, stock_override):

	fund_id = str(getattr(i, 'factset_fund_id'))
	factset_style = str(getattr(i, 'style'))
	url = api_url + "api/fund/info/" + fund_id + "?_dc=1563809154129&appver=3.1.1&_dc=1563809154129"
	resp = try_requests(url, auth_token)
	curr_fund_discrepancy = ''

	# check for override value
	try:
		override_style = stock_override.loc[fund_id, 'factset_fund_id']
	except KeyError:
		override_style = ''

	if resp is not None:
		try:
			resp_data = json.loads(resp.text)
		except ValueError:
			curr_fund_discrepancy += "JSON LOAD FAILED"
			return None
	else:
		curr_fund_discrepancy +="API Request FAILED"
		return None
	db_style = ''
	desktop_style = ''
	try:
		desktop_style = str(resp_data['data']['style'])
		print("Desktop Style: " + desktop_style + "--- Factset Data Feed Style: " + factset_style)
		if override_style == '':
			db_style = factset_style
			if factset_style != 'nan':
				# Desktop Fund Style should match FactSet Fund style
				if factset_style != desktop_style:
					# Desktop style does not match FactSet Style
					print("Fund: " + fund_id + " FactSet data does not match Desktop: " + desktop_style)
					curr_fund_discrepancy += "factset style = {0} -- desktop style = {1}".format(factset_style, desktop_style)
			else:
				# FactSet Style is null so Desktop Style should be Unknown
				if desktop_style != '' or desktop_style != 'Unknown' or desktop_style != 'None':
					curr_fund_discrepancy += "factset style = {0} -- desktop style = {1}".format(factset_style, desktop_style)
					print("Fund: " + fund_id + " FactSet (null) data does not match Desktop: " + desktop_style)
		else:
			# Fund is in Override List
			db_style = override_style
			if desktop_style != override_style:
				curr_fund_discrepancy += "factset style = {0} -- desktop style = {1}".format(factset_style, override_style)

	except Exception:
		print("--- ERROR with Fund ID:" + fund_id + " ---")
		curr_fund_discrepancy += "NO DATA"

	df = pd.DataFrame({
		"factset_fund_id": [fund_id], "db_style": [db_style], "desktop_style": [desktop_style],
		"discrepancy": [curr_fund_discrepancy]
	})

	return df


def try_requests(url, authorisation):
	try:
		resp = requests.get(url, headers={"Authorization": authorisation})
		return resp
	except Exception:
		print("--Request Failed--")
		return


if __name__ == '__main__':
	main(sys.argv[1:])
