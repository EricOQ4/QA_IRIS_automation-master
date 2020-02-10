""" Checking if position on Ownership page matches Factset Values provided in CSV:
"../Excel_SpreadSheets/Source_Data/Position/APD_Inst_POS_NonSurveillance.csv"

What the script does:
The script goes to the Ownership Table of the security (must update the ownership_url variable) and pulls the position of the institution in the security, adding it to the source CSV

Output: A CSV with an extra column showing Desktop Position
"""

import json
import ssl
import sys
import pandas as pd
import requests
import config as cfg

api_url = cfg.api_url
ssl._create_default_https_context = ssl._create_unverified_context
security_id = cfg.security_id


def main(argv):

	desktop_column = []
	all_ownership_data = []

	data = pd.read_csv("../Excel_SpreadSheets/Source_Data/Position/APD_Inst_POS_NonSurveillance.csv")
	data.columns = ['fsym-id', 'security_name', 'factset_entity_id', 'report_date', 'adj_holding']

	if len(argv) == 0:
		auth_token = cfg.auth_token
		print("If you haven't done so, remember to update the Authorization Token by putting it in quotes as an argument for the script.")
	else:
		auth_token = argv[0]

	display_limit = 100

	ownership_url = api_url + "api/ownership/v2/security/{0}/current?appver=3.1.1&_dc=1563988685020&securityId={1}&holder_type=all&page=1&start=0&limit=".format(security_id, security_id) + str(display_limit)

	response = requests.get(ownership_url, headers={"Authorization": auth_token})
	ownership_data = json.loads(response.text)

	for page in range(1, (ownership_data["total"] // display_limit) + 2):
		ownership_url = api_url + "api/ownership/v2/security/{0}/current?appver=3.1.1&_dc=1563988685020&securityId={1}&holder_type=all&page=".format(security_id, security_id) + str(page) + "&start=0&limit=" + str(display_limit)
		response = requests.get(ownership_url, headers={"Authorization": auth_token})
		ownership_data = json.loads(response.text)

		for i in ownership_data['data']:
			institution_dict = {'factset_entity_id': str(i['factset_entity_id']), 'holder_name': str(i['holder_name']), 'current': str(i['current'])}
			print(institution_dict)
			all_ownership_data.append(institution_dict)

	# How many rows of data to run
	start_range = 0
	end_range = len(data)

	# If START RANGE doesn't start from 0
	for i in range(0, start_range):
		desktop_column.append("N/A")

	try:
		for i in range(start_range, end_range):

			curr_factset_id = data.loc[i, 'factset_entity_id']
			curr_ownership_inst = ''

			# For all the items in the ownership table, search for the data with the current factset_entity_id
			for item in all_ownership_data:
				if str(item['factset_entity_id']) == curr_factset_id:
					print(curr_factset_id + " " + str(item['factset_entity_id']))
					curr_ownership_inst = item

			# Found the data with the factset entity id in the ownership table. Append to desktop list
			if curr_ownership_inst == '':
				desktop_column.append("NOT IN OWNERSHIP TABLE")
			else:
				desktop_column.append(curr_ownership_inst['current'])

	finally:

		# If END RANGE doesn't finish at the end of the document
		for j in range(end_range, len(data)):
			desktop_column.append("N/A")

		print("Desktop Column: ", len(desktop_column), "Length of Data: ", len(data))
		data['Desktop Position'] = desktop_column
		data.to_csv("../Excel_SpreadSheets/Output_Data/Security_Ownership_Profile/APD_position_surveillance-off.csv", index=False)


if __name__ == '__main__':
	main(sys.argv[1:])
