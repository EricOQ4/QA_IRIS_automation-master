"""
Checks _position_ and _marketValue_ in the holdings table against Factset Terminal Export. Talk to Taylor Johnson about obtaining Factset Terminal Export.

--REQUIRES A FAIR AMOUNT OF PREREQUISITE WORK--
What the script does:
Pulls data from Fund Profile Page (Position and Market_Value) on Holders Table and adds 2 new columns to source CSV from Factset Terminal (Workstation) Export.
The Factset Terminal (Workstation) Export needs to include the fsym_id of each security so that we can check against Desktop.
To match the Factset Terminal Export with the Data Feed export, you can use and modify this excel formula: =IF(ISNA(MATCH(D2,H:H,0)),“”,INDEX(H:H,MATCH(D2,H:H,0)))

Output: Adding two new columns, Market_Value Desktop and Position Desktop to the original CSV.
"""

import json
import ssl
import sys
import pandas as pd
import requests
import config as cfg

ssl._create_default_https_context = ssl._create_unverified_context


def main(argv):

	api_url = cfg.api_url
	data = pd.read_csv("../Excel_SpreadSheets/Source_Data/Profile_Holder_Table/GovtFundV2.csv")
	data.columns = ['factset_fund_id', 'fs_perm_sec_id', 'fsym_id_1', 'security_name', 'fsym_id', 'FS_Mkt_Val', 'FS_Position', 'Upper_Case_Name', 'NAME', 'Mkt_Val', 'Pos']

	all_ownership_data = []

	market_value_desktop = []
	position_desktop = []

	if len(argv) == 0:
		auth_token = cfg.auth_token
		print("If you haven't done so, remember to update the Authorization Token by putting it in quotes as an argument for the script.")
	else:
		auth_token = argv[0]

	ownership_url = api_url + "api/ownership/v2/fund/04CC29-E/holdings?appver=3.1.0&_dc=1563214531855&securityId=57b92885be1c33ae235f5895&page=1&start=0&limit=100&sort=%5B%7B%22property%22%3A%22current%22%2C%22direction%22%3A%22DESC%22%7D%5D"

	response = requests.get(ownership_url, headers={"Authorization": auth_token})
	ownership_data = json.loads(response.text)
	print(ownership_data)

	for page in range(1, (ownership_data["total"]//100) + 2):
		ownership_url = api_url + "api/ownership/v2/fund/04CC29-E/holdings?appver=3.1.0&_dc=1563214531855&securityId=57b92885be1c33ae235f5895&page=" + str(page) + "&start=0&limit=100&sort=%5B%7B%22property%22%3A%22current%22%2C%22direction%22%3A%22DESC%22%7D%5D"
		response = requests.get(ownership_url, headers={"Authorization": auth_token})
		ownership_data = json.loads(response.text)

		for i in ownership_data['data']:
			institution_dict = {'company_name': str(i['company_name']), 'fsym_id': str(i['fsym_id']), 'market_value': str(i['market_value']), 'current': str(i['current'])}
			all_ownership_data.append(institution_dict)
			print(institution_dict)

	for index in range(len(data)):
		# Go through all the ownership data to look for current index security
		curr_security = 'None'
		for security in all_ownership_data:
			# Find the security in the Excel spreadsheet by searching by fsym_id
			if data.loc[index, 'fsym_id'] == security['fsym_id']:
				curr_security = security

		if curr_security != 'None':

			market_value_desktop.append(str(curr_security['market_value']))
			position_desktop.append(str(curr_security['current']))
			print("Market Value for: " + str(curr_security['company_name']) + ": " + str(curr_security['market_value']))

		else:
			# curr_security is None, i.e. that the security wasn't filled in in the excel spreadsheet
			market_value_desktop.append("-")
			position_desktop.append("-")

	data['Market Value Desktop'] = market_value_desktop
	data['Position Desktop'] = position_desktop
	data.to_csv("../Excel_SpreadSheets/Output_Data/Profile_Holder_Table/SYY_GovtPensionFund_holdings_v2.csv", index=False)


if __name__ == '__main__':
	main(sys.argv[1:])
