"""
Checks Cyclops Data Export against Fund profile data (position, style, turnover, aum, equity_aum, quality rating, AI targeting)

--REQUIRES A FAIR AMOUNT OF PREREQUISITE WORK--

What the script does:
Pulls data from Fund Profile Page and adds new columns to source CSV from Cyclops/Factset Terminal (Workstation) Export. You obtain the funds from the Cyclops export but you need the Factset export
to obtain the factset_fund_id. To match the Factset Terminal Export with the Cyclops Data Feed export, you can use and modify this excel formula: =IF(ISNA(MATCH(D2,H:H,0)),“”,INDEX(H:H,MATCH(D2,H:H,0)))

Output: Adding columns for Desktop value to the source CSV.
"""

import json
import ssl
import sys
import pandas as pd
import requests
import config as cfg

api_url = cfg.api_url


def main(argv):

	data = pd.read_csv("../Excel_SpreadSheets/Source_Data/APD_Cyclops_Fund.csv")
	data.columns = ['fund_entity_id', 'Name', 'Position', 'Style', 'Turnover', 'QualityRating', 'TargetingScore', 'PurchasingPower', 'MaxPurchasingPower']

	position_desktop = []
	style_desktop = []
	turnover_desktop = []
	aum_desktop = []
	equity_aum_desktop = []
	qr_desktop = []
	targeting_score = []

	if len(argv) == 0:
		auth_token = cfg.auth_token
		print("If you haven't done so, remember to update the Authorization Token by putting it in quotes as an argument for the script.")
	else:
		auth_token = argv[0]

	start_range = 0
	end_range = len(data)

	for i in range(0, start_range):
		position_desktop.append("N/A")
		style_desktop.append("N/A")
		turnover_desktop.append("N/A")
		aum_desktop.append("N/A")
		equity_aum_desktop.append("N/A")
		qr_desktop.append("N/A")
		targeting_score.append("N/A")

	for i in range(start_range, end_range):

		fund_entity_id = str(data.loc[i, 'fund_entity_id']).split(' ', 1)[0]

		if fund_entity_id == 'nan':
			style_desktop.append("-")
			turnover_desktop.append("-")
			aum_desktop.append("-")
			equity_aum_desktop.append("-")
			position_desktop.append("-")
			qr_desktop.append("-")
			targeting_score.append("-")
			continue

		# API retrieves style, turnover, equityAUM, AUM
		profile_info_url = api_url + "api/fund/info/" + fund_entity_id + "?_dc=1564512993383&appver=3.1.1&_dc=1564512993383"
		response = try_requests(profile_info_url, auth_token)
		if response is not None:
			profile_info_data = json.loads(response.text)
			print(profile_info_data)
			style_desktop.append(profile_info_data['data']['style'])
			turnover_desktop.append(profile_info_data['data']['turnover'])
			aum_desktop.append(profile_info_data['data']['portfolio_value'])
			equity_aum_desktop.append(profile_info_data['data']['equity_aum'])
		else:
			style_desktop.append("REQUEST FAILED")
			turnover_desktop.append("REQUEST FAILED")
			aum_desktop.append("REQUEST FAILED")
			equity_aum_desktop.append("REQUEST FAILED")

		# API retrieves ai_targeting score for fund
		targeting_score_url = api_url + "api/targeting/ai/score/" + fund_entity_id + "?_dc=1564513046350&appver=3.1.1&_dc=1564513046350&securityId=5d02822d9662c4637c60e68f&type=Fund"
		response = try_requests(targeting_score_url, auth_token)
		if response is not None:
			profile_targeting_data = json.loads(response.text)
			targeting_score.append(profile_targeting_data['data'])
		else:
			targeting_score.append("REQUEST FAILED")

		# API retrieves quality rating for fund
		quality_rating_url = api_url = "api/ownership/fund/qualityrating/" + fund_entity_id + "?_dc=1564513046195&appver=3.1.1&_dc=1564513046195"
		response = try_requests(quality_rating_url, auth_token)
		if response is not None:
			profile_qr_data = json.loads(response.text)
			qr_desktop.append(profile_qr_data['data']['rating'])
		else:
			qr_desktop.append("REQUEST FAILED")

		current_position_url = api_url + "api/ownership/v2/fund/" + fund_entity_id + "/position?appver=3.1.1&_dc=1564513405773&entity_type=fund&securityId=5d02824c9662c4637c619ea0"
		response = try_requests(current_position_url, auth_token)
		if response is not None:
			profile_curr_position_data = json.loads(response.text)
			position_desktop.append(profile_curr_position_data['data']['current'])
		else:
			position_desktop.append("REQUEST FAILED")

	for i in range(end_range, len(data)):
		position_desktop.append("N/A")
		style_desktop.append("N/A")
		turnover_desktop.append("N/A")
		aum_desktop.append("N/A")
		equity_aum_desktop.append("N/A")
		qr_desktop.append("N/A")
		targeting_score.append("N/A")

	data['Style Desktop'] = style_desktop
	data['Turnover Desktop'] = turnover_desktop
	data['AUM Desktop'] = aum_desktop
	data['Equity AUM Desktop'] = equity_aum_desktop
	data['Quality Rating Desktop'] = qr_desktop
	data['Targeting_Score Desktop'] = targeting_score
	data['Position Desktop'] = position_desktop
	data.to_csv("../Excel_SpreadSheets/Output_Data/Profile_Holder_Table/APD_Fund_vs_Cyclops_STAGE.csv", index=False)


def try_requests(url, authorisation):
	try:
		resp = requests.get(url, headers={"Authorization": authorisation})
		return resp

	except Exception:
		print("--Request Failed--")
		return


if __name__ == '__main__':
	main(sys.argv[1:])
