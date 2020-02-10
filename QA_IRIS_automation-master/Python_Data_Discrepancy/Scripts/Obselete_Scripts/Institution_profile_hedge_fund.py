"""
Checking if the all the institutions in the Hedge Fund list match as Hedge Fund
"""
import pandas as pd
import requests
import ssl
import json
import sys
import pymongo
from bson.objectid import ObjectId

ssl._create_default_https_context = ssl._create_unverified_context


def main(argv):
	data = pd.read_csv("../Excel_SpreadSheets/Source_Data/Institution_Style/Institution_Hedge_Fund_Data.csv")
	data.columns = ['FactSetEntityId', 'CurrentReportDate', 'Style', 'ManagerStyle', 'TurnoverLabel', 'TotalAUM', 'FDS13FFlag']

	start_range = 0
	end_range = 20

	hedge_fund_counter = 0
	error_counter = 0
	discrepancy_column = []
	# If the user puts in an Authorisation token, replace it with the one below
	if len(argv) == 0:
		auth_token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfaWQiOiI1Y2QwNmFiZDI5YTgyZTAwMDQ1NGM3MDMiLCJfb3JnYW5pemF0aW9uIjp7Il9pZCI6IjVhZjQ5ZDdiMzYxODUzMDAwNDk0ZTViMyJ9LCJ1c2VyIjoic2hhcmR1bGJAcTRpbmMuY29tIiwiaWF0IjoxNTYyMzM0MjE4LCJleHAiOjE1NjI0MjA2MTh9.k9TVCIJH3pITVfo1a-uOMUpsagGfGYAmkS1ZMUvR-W8"
		print("If you haven't done so, remember to update the Authorization Token")
	else:
		auth_token = argv[0]

	for i in range(0, start_range):
		discrepancy_column.append("N/A")

	for i in range(start_range, end_range):
		institution_url = data.loc[i, 'FactSetEntityId']

		url = "https://api-dev.q4desktop.com/api/institution/info/" + institution_url + "?_dc=1559741223800&appver=3.0.9&_dc=1559741223800"
		try:
			# Might have to update the Bearer token
			resp = requests.get(url, headers={
				"Authorization": auth_token})
			resp_data = json.loads(resp.text)
			desktop_style = str(resp_data['data']['manager_style'])

			if desktop_style != 'Hedge Fund':
				discrepancy_column.append("Incorrect, " + desktop_style)
				error_counter += 1
			else:
				discrepancy_column.append('')

		except Exception:
			print("---ERROR with: " + str(institution_url) + "---")
			error_counter += 1

	print("Error Counter: " + str(error_counter) + " --- Hedge fund Error Counter: " + str(hedge_fund_counter))

	for i in range(end_range, len(data)):
		discrepancy_column.append("N/A")

	data['Discrepancy Column'] = discrepancy_column
	data.to_csv("../Excel_SpreadSheets/Output_Data/Style/Institution_Profile_Hedge_fund_style.csv")


if __name__ == '__main__':
	main(sys.argv[1:])
