""" Calculate the Equity AUM for Funds
What the script does:
The script goes to every fund in the Ownership Table (13F data) of the security (must update the ownership_url variable)
and sums up the market_value of each holding. The sum of the market values is the Equity AUM.
Remember to change the Ownership URLs with the Ownership of the security you wish to test.

How to run:
1. Update config.py
    a. Update Auth token
    b. Update Api url to point to intended environment (stage, dev, ...)
    c. Update Security id to the security you want to target
2. Hit Run

Output: A CSV with 4 columns, the factset_fund_id, fund_name, profile page equity AUM, calculated equity AUM
"""

import json
import sys
import pandas as pd
import requests
import config as cfg

API_URL = cfg.api_url
SECURITY_ID = cfg.security_id


def main(argv):
	factset_fund_id = []
	all_ownership_data = []
	equity_aum_column = []
	fund_name = []
	market_value_column = []

	if len(argv) == 0:
		auth_token = cfg.auth_token
		print("If you haven't done so, remember to update the Authorization Token by putting it in quotes as an argument for the script.")
	else:
		auth_token = argv[0]

	display_limit = 10
	ownership_url = API_URL + "api/ownership/v2/security/{0}/current?appver=3.1.1&_dc=1564599542638&securityId={1}&holder_type=fund&type=&page=1&start=0&limit=".format(SECURITY_ID, SECURITY_ID) + str(display_limit)
	response = requests.get(ownership_url, headers={"Authorization": auth_token})
	ownership_data = json.loads(response.text)

	# for page in range(1, (ownership_data["total"] // 100) + 2):
	for page in range(1):

		ownership_url = API_URL + "api/ownership/v2/security/{0}/current?appver=3.1.1&_dc=1564599542638&securityId={1}&holder_type=fund&type=&page=".format(SECURITY_ID, SECURITY_ID) + str(page) + "&start=0&limit=" + str(display_limit)
		response = try_requests(ownership_url, auth_token)
		ownership_data = json.loads(response.text)

		for i in ownership_data['data']:
			institution_dict = {'factset_fund_id': str(i['factset_fund_id']), 'fund_name': str(i['fund_name']), 'market_value': str(i['market_value']), 'current': str(i['current'])}
			all_ownership_data.append(institution_dict)
			print(institution_dict)

	# For each fund in the ownership table, sum up all of the fund's holdings' market values. This should be the equity AUM.
	for fund in all_ownership_data:
		fund_id = str(fund['factset_fund_id'])
		market_value_sum = 0

		url = API_URL + "api/fund/info/" + fund_id + "?_dc=1563912805155&appver=3.1.1&_dc=1563912805155"
		resp = try_requests(url, auth_token)

		if resp is not None:
			try:
				resp_data = json.loads(resp.text)
			except ValueError:
				continue
		else:
			continue
		print(resp_data)
		fund_equity_aum = resp_data['data']['equity_aum']

		holdings_api = API_URL + "api/ownership/v2/fund/" + fund_id + "/holdings?appver=3.1.1&_dc=1564599656981&securityId={0}&page=1&start=0&limit=100&sort=%5B%7B%22property%22%3A%22current%22%2C%22direction%22%3A%22DESC%22%7D%5D".format(SECURITY_ID)
		fund_response = try_requests(holdings_api, auth_token)

		if fund_response is not None:
			holdings_data = json.loads(fund_response.text)
		else:
			continue

		# Gather all the holdings of the fund by iterating over all the pages of the fund
		for current_page in range(1, (holdings_data["total"] // 100) + 2):
			holdings_api = API_URL + "api/ownership/v2/fund/" + fund_id + "/holdings?appver=3.1.1&_dc=1564599656981&securityId={0}&page=".format(SECURITY_ID) + str(current_page) + "&start=0&limit=100&sort=%5B%7B%22property%22%3A%22current%22%2C%22direction%22%3A%22DESC%22%7D%5D"
			curr_response = try_requests(holdings_api, auth_token)

			# if curr_response is not None:
			# 	holdings_data = json.loads(curr_response.text)
			try:
				holdings_data = json.loads(curr_response.text)

				for i in holdings_data['data']:
					print("FactSet_fund_id:" + str(i['factset_fund_id']) + " " + str(i['company_name']) + " market_value: " + str(i['market_value']))
					market_value_sum += i['market_value']
			except Exception:
				# I want to break because if I can't sum the market_value for one page of a particular fund, the calculation of the Equity AUM is useless. Move to the next fund.
				break

		factset_fund_id.append(fund_id)
		equity_aum_column.append(fund_equity_aum)
		fund_name.append(fund['fund_name'])
		market_value_column.append(market_value_sum)

		print("FUND: " + fund['fund_name'] + "---" + str(market_value_sum))

		dictionary = {'Fund_Entity_ID': factset_fund_id, 'Fund Name': fund_name, 'Profile Equity AUM': equity_aum_column, 'Calculated Equity AUM': market_value_column}
		data_frame = pd.DataFrame(data=dictionary)
		data_frame.to_csv("../Excel_SpreadSheets/Output_Data/AUM_Equity-AUM/FDX-Ownership_calculate_eAUM.csv", index=False)


def try_requests(url, authorisation):
	try:
		return requests.get(url, headers={"Authorization": authorisation})

	except Exception:
		print("--Request Failed--")
		return


if __name__ == '__main__':
	main(sys.argv[1:])


