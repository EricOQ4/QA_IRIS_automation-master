""" Checking if data on AI Targeting Table matches Fund Profile Page

What the script does:
(style, turnover, eaum, aum, purchasing power on the AI Targeting Page page matches with the fund profile page.

Steps to run:
1. Update config.py
    a. Update Auth token
    b. Update Api url to point to intended environment (stage, dev, ...)
    c. Update Security id to the security you want to target
2. (Optional) update variable "num_pages" to change number of results to check
3. Run script

Output: A CSV with 3 columns, the factset_fund_id, fund_name, Targeting vs. Profile Page.
The last column prints out the value that is different, if any, between the 2 pages.
"""

import json
import ssl
import sys
import pandas as pd
from pandas.io.json import json_normalize
import requests
import numpy
import timeit
from concurrent import futures
import config as cfg

ssl._create_default_https_context = ssl._create_unverified_context


def main(argv):

	api_url = cfg.api_url
	security_id = cfg.security_id
	num_pages = 101
	targeting_params = {
		"_dc": "1570050366302",
		"appver": "3.1.1",
		"location": "",
		"turnover": "",
		"type": "",
		"style": "",
		"owns_security": "",
		"target_type": "fund",
		"investment_style": "all",
		"peer_activity": "all",
		"sector_activity": "all",
		"peers": "false",
		"exclude_activists": "false",
		"ai_only": "false",
		"logged_activity": "all",
		"activity_start": "",
		"activity_end": "",
		"securityId": security_id,
		"page": 1,
		"start": 0,
		"limit": 100,
	}

	fund_columns = ["factset_fund_id", "fund_name", "style", "turnover", "equity_aum", "portfolio_value", "quality_rating", "_purchasing_power.purchasing_power"]
	targeting_df = pd.DataFrame(columns=fund_columns)

	if len(argv) == 0:
		auth_token = cfg.auth_token
		print("If you haven't done so, remember to update the Authorization Token by putting it in quotes as an argument for the script.")
	else:
		auth_token = argv[0]

	# Adjust the number of pages of data to run the script on. The number of results will be display_limit * range(#) in for-loop. You can fiddle around with the 101 number!

	start = timeit.default_timer()

	with futures.ThreadPoolExecutor(max_workers=5) as executor:
		future_targeting = {executor.submit(get_ai_targeting, api_url, targeting_params, auth_token, fund_columns, page): page for page in range(1, num_pages)}
		for future in futures.as_completed(future_targeting):
			try:
				targeting_df = targeting_df.append(future.result(), ignore_index=True)
			except Exception as exc:
				print('generated an exception: %s' % exc)

	result_df = pd.DataFrame(columns=['factset_fund_id', 'fund_name', 'AI_Targeting vs. Profile Page'])

	with futures.ThreadPoolExecutor(max_workers=5) as executor:
		future_comparison = {executor.submit(compare_profile_page, i, auth_token, api_url, security_id): i for i in targeting_df.itertuples()}
		for future in futures.as_completed(future_comparison):
			try:
				result_df = result_df.append(future.result(), ignore_index=True)
			except Exception as exc:
				print('generated an exception: %s' % exc)

	end = timeit.default_timer()
	print(end - start)
	result_df.to_csv("../Excel_SpreadSheets/Output_Data/AI_Targeting/APD_AI-Targeting_surveillance-off_fund_STAGE-TEST.csv",
					 index=False)


def compare_profile_page(i, auth_token, api_url, security_id):
	if numpy.isnan(getattr(i, '_8')):
		targeting_pp = "None"
	else:
		targeting_pp = str(int(getattr(i, '_8')))
	targeting_style = str(getattr(i, 'style'))
	if numpy.isnan(getattr(i, 'equity_aum')):
		targeting_equity_aum = "None"
	else:
		targeting_equity_aum = str(int(getattr(i, 'equity_aum')))
	if numpy.isnan(getattr(i, 'portfolio_value')):
		targeting_aum = 'None'
	else:
		targeting_aum = str(int(getattr(i, 'portfolio_value')))
	targeting_turnover = str(getattr(i, 'turnover'))

	fund_id = str(getattr(i, 'factset_fund_id'))

	curr_institution_discrepancy = ""
	fund_url = api_url + "api/fund/info/" + fund_id + "?_dc=1570108937383&appver=3.1.1&_dc=1570108937383"
	purchasing_power_url = api_url + "api/purchasingpower/fund/" + fund_id + "?_dc=1570128471108&appver=3.1.1&_dc=1570128471108&securityId=" + security_id
	profile_resp = try_requests(fund_url, auth_token)
	purchasing_power_resp = try_requests(purchasing_power_url, auth_token)
	if profile_resp is not None:
		profile_data = json.loads(profile_resp.text)
	else:
		curr_institution_discrepancy += 'API REQUEST FAILED'
		return curr_institution_discrepancy

	try:
		profile_style = str(profile_data['data']['style'])
	except TypeError:
		profile_style = 'None'
	try:
		profile_turnover = str(profile_data['data']['turnover'])
	except TypeError:
		profile_turnover = 'None'
	try:
		profile_aum = str(profile_data['data']['portfolio_value'])
	except TypeError:
		profile_aum = 'null'
	try:
		profile_equity_aum = str(profile_data['data']['equity_aum'])
	except TypeError:
		profile_equity_aum = 'null'

	if purchasing_power_resp is not None:
		purchasing_power_data = purchasing_power_resp.json()
		profile_purchasing_power = str(purchasing_power_data['data']['purchasing_power'])
		if profile_purchasing_power != targeting_pp:
			curr_institution_discrepancy += "purchasing_p-(Targeting:" + targeting_pp + " Profile: " + profile_purchasing_power + ") "

	if (targeting_style != profile_style) or (targeting_aum != profile_aum) or (
			targeting_equity_aum != profile_equity_aum) or (targeting_turnover != profile_turnover):
		curr_institution_discrepancy += "Discrepancy: "

	if targeting_style != profile_style:
		curr_institution_discrepancy += "style-(Targeting:" + targeting_style + " Profile: " + profile_style + ") "

	if targeting_aum != profile_aum:
		curr_institution_discrepancy += "aum-(Targeting:" + targeting_aum + " Profile: " + profile_aum + ") "

	if targeting_equity_aum != profile_equity_aum:
		curr_institution_discrepancy += "eaum-(Targeting:" + targeting_equity_aum + " Profile: " + profile_equity_aum + ") "

	if targeting_turnover != profile_turnover:
		curr_institution_discrepancy += "turnover-(Targeting:" + targeting_turnover + " Profile: " + profile_turnover + ") "

	if (profile_style == 'None') and (profile_turnover == 'None') and (profile_aum == 'null') and (
			profile_aum == 'null'):
		curr_institution_discrepancy = "NO DATA"

	print(
		"AI Targeting Page: " + targeting_equity_aum + " -- Profile Page E AUM: " + profile_equity_aum + " ---- AI Targeting Page AUM: " + targeting_aum + " -- Profile Page AUM: " + profile_aum + " ---- AI Targeting Page PP: " + targeting_pp + " -- Profile Page PP: " + profile_purchasing_power)
	result_df = pd.DataFrame({	"factset_fund_id": [fund_id],
								"fund_name": [str(getattr(i, 'fund_name'))],
								"AI_Targeting vs. Profile Page": [curr_institution_discrepancy]})
	return result_df


def get_ai_targeting(api_url, targeting_params, auth_token, fund_columns, page):
	print("AI Page: {0} ".format(page))
	targeting_params['page'] = page
	targeting_url = api_url + "api/targeting/search"
	response = try_requests(targeting_url, auth_token, params=targeting_params)
	# print(response.status_code)
	if response is not None:
		try:
			ownership_data = json.loads(response.text)
			# print(json.dumps(response.json(), sort_keys=True,indent=4))
		except json.decoder.JSONDecodeError:
			print("JSON DECODER ERROR")
			return
	else:
		return
	df = json_normalize(data=response.json(), record_path='data')
	re_data = df.to_json(orient='records')
	df_new = json_normalize(data=json.loads(re_data))
	df = df_new[fund_columns]

	return df


def try_requests(url, authorization, params=None):

	try:
		resp = requests.get(url, headers={"Authorization": authorization}, params=params)
		print(resp.text)
		return resp

	except Exception:
		print("--Request Failed--")
		return


if __name__ == '__main__':
	main(sys.argv[1:])
