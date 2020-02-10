""" Checking if data (style, aum, equity_aum, turnover) on Ownership Table matches Institution Profile Page
What the script does:
Looks at the Ownership Table of current security (need to update ownership_url) and compares the values in the ownership table against the Institution Profile page

How to run:
1. Update config.py
    a. Update Auth token
    b. Update Api url to point to intended environment (stage, dev, ...)
    c. Update Security id to the security you want to target
2. Hit Run

Output: A CSV with 3 columns, the factset_entity_id, institution_name, Discrepancy_Column.
The last column prints out the value that is different, if any, between the 2 pages.
"""

import json
import ssl
import sys
import pandas as pd
from pandas.io.json import json_normalize
import requests
import numpy
from concurrent import futures
import config as cfg

ssl._create_default_https_context = ssl._create_unverified_context


def main(argv):

	api_url = cfg.api_url
	ownership_columns = [
		'factset_entity_id', 'holder_name', 'style', 'turnover', 'equity_aum',
		'total_aum', 'market_value', 'current', 'quality_rating',
	]
	ownership_df = pd.DataFrame(columns=ownership_columns)
	security_id = cfg.security_id
	ownership_table_params = {
		'appver':'3.1.1',
		'_dc': '1569949324440',
		'securityid': security_id,
		'holder_type': 'all',
		'search': '',
		'page': '1',
		'start': '0',
		'limit': '100'
	}
	ownership_url = api_url + "api/ownership/v2/security/{0}/current".format(security_id)

	if len(argv) == 0:
		auth_token = cfg.auth_token
		print("If you haven't done so, remember to update the Authorization Token by putting it in quotes as an argument for the script.")
	else:
		auth_token = argv[0]

	display_limit = 100

	ownership_data = try_requests(ownership_url, auth_token, params=ownership_table_params).json()
	num_pages = (ownership_data["total"]//display_limit) + 2

	with futures.ThreadPoolExecutor(max_workers=5) as executor:
		future_ownership_data = {executor.submit(get_ownership_data, ownership_table_params, ownership_url, auth_token, ownership_columns, i): i for i in range(1, num_pages)}
		for future in futures.as_completed(future_ownership_data):
			try:
				ownership_df = ownership_df.append(future.result(), ignore_index=True)
			except Exception as exc:
				print('generated an exception: %s' % exc)

	result_df = pd.DataFrame(columns=['factset_entity_id','institution_name', 'Discrepency'])

	with futures.ThreadPoolExecutor(max_workers=5) as executor:
		future_comparison = {executor.submit(ownership_comparison, item, auth_token, api_url): item for item in ownership_df.itertuples()}
		for future in futures.as_completed(future_comparison):
			try:
				result_df = result_df.append(future.result(), ignore_index=True)
			except Exception as exc:
				print('generated an exception: %s' % exc)

	result_df.to_csv("../Excel_SpreadSheets/Output_Data/Security_Ownership_Profile/APD_surveillance-off_institution_TEST.csv", index=False)


def ownership_comparison(item, auth_token, api_url):
	ownership_style = str(getattr(item, 'style'))
	if numpy.isnan(getattr(item , 'equity_aum')):
		ownership_equity_aum = "None"
	else:
		ownership_equity_aum = str(int(getattr(item , 'equity_aum')))
	if numpy.isnan(getattr(item , 'total_aum')):
		ownership_aum = "None"
	else:
		ownership_aum = str(int(getattr(item, 'total_aum')))
	ownership_turnover = str(getattr(item, 'turnover'))

	print(item)
	institution_id = getattr(item, 'factset_entity_id')

	institution_url = api_url + "api/institution/info/" + institution_id + "?_dc=1563905284318&appver=3.1.1&_dc=1563905284318"
	profile_resp = requests.get(institution_url, headers={"Authorization": auth_token})
	profile_data = json.loads(profile_resp.text)

	try:
		profile_style = str(profile_data['data']['style'])
	except TypeError:
		profile_style = 'None'
	try:
		profile_turnover = str(profile_data['data']['turnover'])
	except TypeError:
		profile_turnover = 'None'
	try:
		profile_aum = str(profile_data['data']['total_aum'])
	except TypeError:
		profile_aum = 'null'
	try:
		profile_equity_aum = str(profile_data['data']['equity_aum'])
	except TypeError:
		profile_equity_aum = 'null'

	curr_institution_discrepancy = ""

	if (ownership_style != profile_style) or (ownership_aum != profile_aum) or (
			ownership_equity_aum != profile_equity_aum) or (ownership_turnover != profile_turnover):
		curr_institution_discrepancy += "Incorrect: "

	if ownership_style != profile_style:
		curr_institution_discrepancy += "style-(Ownership:" + ownership_style + " Profile: " + profile_style + ") "

	if ownership_aum != profile_aum:
		curr_institution_discrepancy += "aum-(Ownership:" + ownership_aum + " Profile: " + profile_aum + ") "

	if ownership_equity_aum != profile_equity_aum:
		curr_institution_discrepancy += "eaum-(Ownership:" + ownership_equity_aum + " Profile: " + profile_equity_aum + ") "

	if ownership_turnover != profile_turnover:
		curr_institution_discrepancy += "turnover-(Ownership:" + ownership_turnover + " Profile: " + profile_turnover + ") "

	if (profile_style == 'None') and (profile_turnover == 'None') and (profile_aum == 'null') and (
			profile_aum == 'null'):
		curr_institution_discrepancy = "NO DATA"

	result_df = pd.DataFrame({
		"factset_entity_id": [institution_id],
		"institution_name": [str(getattr(item, 'holder_name'))],
		"Discrepency": [curr_institution_discrepancy]}, index=None)
	return result_df


def get_ownership_data(request_params, ownership_url, auth_token, ownership_columns, page):
	request_params['page'] = page
	response = try_requests(ownership_url, auth_token, params=request_params)

	df = json_normalize(response.json(), 'data')
	df = df[ownership_columns]

	return df


def try_requests(url, auth_token, params=None):
	try:
		resp = requests.get(url, headers={"Authorization": auth_token}, params=params)
		return resp

	except Exception:
		print("--Request Failed--")
		return


if __name__ == '__main__':
	main(sys.argv[1:])
