"""
--- DEPRECATED SCRIPT ---

Check if the institution profile page matching AUM"""

import json
import sys

import pandas as pd
import requests


def main(argv):

	# Read the CSV and store the data in a dataframe called 'data'.
	data = pd.read_csv("../Excel_SpreadSheets/Source_Data/Institution_Profile/FS_Inst_Position.csv")
	# Specify column names to access the data in each column
	data.columns = ['factset_entity_id', 'total_aum']

	# If the user doesn't pass any arguments when running the script from terminal (len(argv) == 0), then take the Authorisation header specified below. Otherwise, use the user specified argument.
	if len(argv) == 0:
		auth_token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfaWQiOiI1Y2VlOTI3NmUwOWYzMzAwMDQ5MzI1NjQiLCJfb3JnYW5pemF0aW9uIjp7Il9pZCI6IjU4YzJkYzY5MWIzYTg5MDAwNGFkMDVlNiJ9LCJ1c2VyIjoic2hhcmR1bGJAcTR3ZWJzeXN0ZW1zLmNvbSIsImlhdCI6MTU2NDQ5NDYyNCwiZXhwIjoxNTY1MDk5NDI0fQ.JSWlnKzgzzc_Ll6e0IlqojLxrkqi0DA3IYUqtVb6nZU"
		print("If you haven't done so, remember to update the Authorization Token by putting it in quotes as an argument for the script.")
	else:
		auth_token = argv[0]

	# In the excel file, specify the row where to start and end the script.
	start_range = 0
	end_range = len(data)

	desktop_position = []

	# If START RANGE doesn't start from 0, then the first *start_range* rows will be filled with 'N/A'
	for i in range(0, start_range):
		desktop_position.append("N/A")

	try:
		for i in range(start_range, end_range):
			# Specify the factset_entity_id of the institution that we wish to gather the data from. Sometimes, the factset_entity_id is specified "04DF37-E ". Notice the space at the end of the id.
			# To get rid of the space, you can split the string by the space and take the first element.
			institution_url = str(data.loc[i, 'factset_entity_id']).split(' ', 1)[0]

			# API call that gathers data from Desktop. Can change between Staging and Develop
			try:

				url = "https://api-stage.q4desktop.com/api/ownership/v2/institution/" + institution_url + "/position?appver=3.1.1&_dc=1564508526745&entity_type=institution&securityId=5d02822d9662c4637c60e68f"
				profile_resp = try_requests(url, auth_token)
				if profile_resp is not None:
					try:
						resp_data = json.loads(profile_resp.text)
					except json.decoder.JSONDecodeError:
						desktop_position.append("JSON DECODER Error")
						print('Json ERROR')
						continue
				else:
					desktop_position.append("API Request FAILED")
					continue

				print(resp_data)
				# Add the AUM and Equity AUM to the list. If If there is no data for the API, then add "NO DATA" to each column.
				if str(resp_data['data']) != 'None':
					desktop_position.append(resp_data['data']['current'])
				else:
					desktop_position.append("NO DATA")
			except OSError:
				pass
	finally:
		# If the script is not running down to the length of the excel, then this for loop will fill the lists so that there isn't a ValueError when pushing out to CSV.
		for j in range(len(desktop_position), len(data)):
			desktop_position.append("N/A")

		# Creating two new columns and filling them with the API data
		data['Desktop Institution Position'] = desktop_position
		# Pushing the modified data frame to a CSV
		data.to_csv("../Excel_SpreadSheets/Output_Data/Position/FDX_inst_Position_surveillance-off-STAGE.csv", index=False)


def try_requests(url, authorisation):
	try:
		resp = requests.get(url, headers={"Authorization": authorisation})
		return resp
	except Exception:
		print("--Request Failed--")
		return


if __name__ == '__main__':
	main(sys.argv[1:])
