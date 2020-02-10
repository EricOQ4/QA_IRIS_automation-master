"""
Checks _position_ and _marketValue_ in the holdings table against Factset Terminal Export. Talk to Taylor Johnson about obtaining Factset Terminal Export.

--REQUIRES A FAIR AMOUNT OF PREREQUISITE WORK--
What the script does:
Pulls data from Institution Profile Page (Position and Market_Value) on Holders Table and adds 2 new columns to source CSV from Factset Terminal (Workstation) Export.
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
api_url = cfg.api_url


def main(argv):

	data = pd.read_csv("../Excel_SpreadSheets/Source_Data/Profile_Holder_Table/TheVanguardGroup_fsym-id.csv")
	data.columns = ['fsym_id', 'Security', 'Ticker', 'Position', 'MV', 'factset_entity_id', 'fs_perm_sec_id', 'fsym_id_1', 'Security Name']

	all_ownership_data = []

	market_value_desktop = []
	position_desktop = []

	if len(argv) == 0:
		auth_token = cfg.auth_token
		print("If you haven't done so, remember to update the Authorization Token by putting it in quotes as an argument for the script.")
	else:
		auth_token = argv[0]

	ownership_url = api_url + "api/ownership/v2/institution/002FYS-E/current?appver=3.1.1&_dc=1563458247711&securityId=57b92894be1c33ae235fd931&holding_type=security&page=1&start=0&limit=100&sort=%5B%7B%22property%22%3A%22current%22%2C%22direction%22%3A%22DESC%22%7D%5D"
	response = requests.get(ownership_url, headers={"Authorization": auth_token})
	ownership_data = json.loads(response.text)

	try:
		for page in range(1, (ownership_data["total"]//100) + 2):
			ownership_url = api_url + "api/ownership/v2/institution/002FYS-E/current?appver=3.1.1&_dc=1563458247711&securityId=57b92894be1c33ae235fd931&holding_type=security&page=" + str(page) + "&start=0&limit=100&sort=%5B%7B%22property%22%3A%22current%22%2C%22direction%22%3A%22DESC%22%7D%5D"
			response = requests.get(ownership_url, headers={"Authorization": auth_token})
			ownership_data = json.loads(response.text)

			for i in ownership_data['data']:
				institution_dict = {'factset_company_id': str(i['factset_company_id']), 'company_name': str(i['company_name']), 'fsym_id': str(i['fsym_id']), 'market_value': str(i['market_value']), 'current': str(i['current'])}
				print(institution_dict)
				all_ownership_data.append(institution_dict)
	except KeyError:
		print("ERROR: Update the Authorisation Token")

	for index in range(len(data)):
		# Go through all the ownership data to look for current index security
		curr_security = 'None'
		for security in all_ownership_data:
			# Find the security in the Excel spreadsheet by searching for fsym_id
			if data.loc[index, 'fsym_id'] == security['fsym_id']:
				curr_security = security

		if curr_security != 'None':

			market_value_desktop.append(str(curr_security['market_value']))
			print("FSYM-id: " + str(curr_security['fsym_id']) + "---Position - Desktop: " + str(curr_security['current']) + " Factset: " + str(data.loc[index, 'Position']))
			position_desktop.append(str(curr_security['current']))

		else:
			# curr_security is None, i.e. that the security wasn't filled in in the excel spreadsheet
			market_value_desktop.append("-")
			position_desktop.append("-")

	data['Mkt Value Desktop'] = market_value_desktop
	data['Position Desktop'] = position_desktop
	data.to_csv("../Excel_SpreadSheets/Output_Data/Profile_Holder_Table/GDDY_Vanguard_holdings.csv", index=False)


def retry_requests(url, authorisation):
	try:
		resp = requests.get(url, headers={"Authorization": authorisation})
		return resp

	except Exception:
		print("--Request Failed--")
		return


if __name__ == '__main__':
	main(sys.argv[1:])
