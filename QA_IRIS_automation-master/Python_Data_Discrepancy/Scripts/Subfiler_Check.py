"""Check if the institutions in the Factset Data Export are subfilers

What the script does:
Checks if these factset_entity_ids have institution profile pages.

Output: Modifies the source CSV with data about Institution existence on Desktop.
"""

import json
import os
import sys

import pandas as pd
import requests
import config as cfg

api_url = cfg.api_url


def main(argv):

	sub_filer_list = pd.read_csv("../Excel_SpreadSheets/Source_Data/Institution_subfilers_list.csv")
	sub_filer_list.columns = ['factset_entity_id']

	discrepancy = []

	if len(argv) == 0:
		auth_token = cfg.auth_token
		print("If you haven't done so, remember to update the Authorization Token by putting it in quotes as an argument for the script.")
	else:
		auth_token = argv[0]
	try:
		for i in range(0, len(sub_filer_list)):

			institution_url = sub_filer_list.loc[i, 'factset_entity_id']

			# API call that gathers data from Desktop. Can change api-stage to api-dev
			url = api_url + "api/institution/info/" + institution_url + "?_dc=1562094863180&appver=3.1.0&_dc=1562094863180"
			resp = requests.get(url, headers={
				"Authorization": auth_token})
			resp_data = json.loads(resp.text)

			if str(resp_data['data']) != 'None':
				discrepancy.append("Institution EXISTS")
			else:
				print("Data Exists: " + institution_url)
				discrepancy.append("")
	except KeyError:
		print("ERROR: Update Authorization Header (Bearer Token)")

	try:
		sub_filer_list['Discrepancy Column'] = discrepancy
		sub_filer_list.to_csv("../Excel_SpreadSheets/Script Output Data/Institution_subfiler_results.csv", index=False)
	except ValueError:
		print("The length of the new column does not match the length of the index.")


if __name__ == '__main__':
	main(sys.argv[1:])
