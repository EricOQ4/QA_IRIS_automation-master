""" Checking if data on Ownership Table matches Institution Profile Page (For surveillance data)
What the script does:
Looks at the Ownership Table of current security (need to update ownership_url) and compares the values in the ownership table against the Institution Profile page

Output: A CSV with 3 columns, the factset_entity_id, institution_name, Ownership vs. Profile Page.
The last column prints out the value that is different, if any, between the 2 pages.
"""
import json
import ssl
import sys
import pandas as pd
import requests

ssl._create_default_https_context = ssl._create_unverified_context


def main(argv):

	factset_entity_id = []
	institution_name = []
	discrepancy_column = []
	all_ownership_data = []

	if len(argv) == 0:
		auth_token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfaWQiOiI1Y2QwNmFiZDI5YTgyZTAwMDQ1NGM3MDMiLCJfb3JnYW5pemF0aW9uIjp7Il9pZCI6IjVhZjQ5ZDdiMzYxODUzMDAwNDk0ZTViMyJ9LCJ1c2VyIjoic2hhcmR1bGJAcTRpbmMuY29tIiwiaWF0IjoxNTY1Mjc4ODc4LCJleHAiOjE1NjUzNjUyNzh9.sDclAYcHPrQpIulUxnd12Atq9JoBUilnoXTEZ-AgXX0"
		print("If you haven't done so, remember to update the Authorization Token by putting it in quotes as an argument for the script.")
	else:
		auth_token = argv[0]

	display_limit = 100

	ownership_url = "https://api-dev.q4desktop.com/api/oxford/holdings?_dc=1565290798369&appver=3.1.1&_dc=1565290798369&position=&style=&type=&page=1&start=0&limit=" + str(display_limit)

	response = requests.get(ownership_url, headers={"Authorization": auth_token})
	ownership_data = json.loads(response.text)

	for page in range(1, (ownership_data["total"]//display_limit) + 2):
		ownership_url = "https://api-dev.q4desktop.com/api/oxford/holdings?_dc=1565290798369&appver=3.1.1&_dc=1565290798369&position=&style=&type=&page=" + str(page) + "&start=0&limit=" + str(display_limit)
		response = requests.get(ownership_url, headers={"Authorization": auth_token})
		ownership_data = json.loads(response.text)

		for i in ownership_data['data']:
			institution_dict = {'factset_entity_id': str(i['factset_entity_id']), 'institution_name': str(i['institution_name']), 'style': str(i['style']), 'turnover': str(i['turnover']), 'equity_aum': str(i['equity_aum']), 'total_aum': str(i['total_aum']), 'market_value': str(i['market_value']), 'current': str(i['current']), 'quality_rating': str(i['quality_rating'])}
			print(institution_dict)
			all_ownership_data.append(institution_dict)

	try:
		for item in all_ownership_data:
			print(item)

			ownership_style = str(item['style'])
			ownership_equity_aum = str(item['equity_aum'])
			ownership_aum = str(item['total_aum'])
			ownership_turnover = str(item['turnover'])
			ownership_position = str(item['current'])

			institution_id = item['factset_entity_id']
			factset_entity_id.append(institution_id)

			institution_url = "https://api-dev.q4desktop.com/api/institution/info/" + institution_id + "?_dc=1564519563317&appver=3.1.1&_dc=1564519563317"
			profile_resp = requests.get(institution_url, headers={"Authorization": auth_token})
			profile_data = json.loads(profile_resp.text)

			institution_position_url = "https://api-dev.q4desktop.com/api/ownership/v2/institution/" + institution_id + "/position?appver=3.1.1&_dc=1565291896276&entity_type=institution&securityId=57b92894be1c33ae235fd931"
			profile_position_request = requests.get(institution_position_url, headers={"Authorization": auth_token})
			profile_position_response = json.loads(profile_position_request.text)
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

			profile_position = str(profile_position_response['data']['current'])

			curr_institution_discrepancy = ""

			if (ownership_style != profile_style) or (ownership_aum != profile_aum) or (ownership_equity_aum != profile_equity_aum) or (ownership_turnover != profile_turnover):
				curr_institution_discrepancy += "Incorrect: "

			if ownership_style != profile_style:
				curr_institution_discrepancy += "style-(Ownership:" + ownership_style + " Profile: " + profile_style + ") "

			if ownership_aum != profile_aum:
				curr_institution_discrepancy += "aum-(Ownership:" + ownership_aum + " Profile: " + profile_aum + ") "

			if ownership_equity_aum != profile_equity_aum:
				curr_institution_discrepancy += "eaum-(Ownership:" + ownership_equity_aum + " Profile: " + profile_equity_aum + ") "

			if ownership_turnover != profile_turnover:
				curr_institution_discrepancy += "turnover-(Ownership:" + ownership_turnover + " Profile: " + profile_turnover + ") "

			if profile_position != ownership_position:
				curr_institution_discrepancy += "position-(Ownership:" + ownership_position + "Profile: " + profile_position + ") "

			if (profile_style == 'None') and (profile_turnover == 'None') and (profile_aum == 'null') and (profile_aum == 'null'):
				curr_institution_discrepancy = "NO DATA"

			discrepancy_column.append(curr_institution_discrepancy)
			institution_name.append(str(item['institution_name']))
	finally:

		dictionary = {'FactSet Entity ID': factset_entity_id, 'Institution_name': institution_name, 'Discrepancy_Column': discrepancy_column}
		data_frame = pd.DataFrame(data=dictionary)
		data_frame.to_csv("../../Excel_SpreadSheets/Output_Data/Security_Ownership_Profile/GDDY_surveillance-on_inst.csv", index=False)


if __name__ == '__main__':
	main(sys.argv[1:])
