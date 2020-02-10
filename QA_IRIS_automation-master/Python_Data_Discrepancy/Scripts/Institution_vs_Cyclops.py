"""
Checks Cyclops Data Export against Institution profile data (position, style, turnover, aum, equity_aum, quality rating, AI targeting)

--REQUIRES PREREQUISITE WORK--
What the script does:
Pulls data from Institution Profile Page and adds new columns to source CSV from Cyclops/Factset Terminal (Workstation) Export.
To match the your export with the Data Feed export (provided in ../Excel_SpreadSheets/Source_Data/), you can use and modify this excel formula: =IF(ISNA(MATCH(D2,H:H,0)),“”,INDEX(H:H,MATCH(D2,H:H,0)))

Output: Adding columns for Desktop value to the source CSV
"""

import json
import sys
import pandas as pd
import requests
import config as cfg

api_url = cfg.api_url
security_id = cfg.security_id


def main(argv):

	data = pd.read_csv("../Excel_SpreadSheets/Source_Data/APD_Cyclops_Institution.csv")
	data.columns = ['factset_entity_id', 'Name', 'Position', 'Style', 'Turnover', 'EquityAssets', 'QualityRating']

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
		position_desktop.append("N/A ")
		style_desktop.append("N/A ")
		turnover_desktop.append("N/A ")
		aum_desktop.append("N/A ")
		equity_aum_desktop.append("N/A ")
		qr_desktop.append("N/A ")
		targeting_score.append("N/A ")

	for i in range(start_range, end_range):

		factset_entity_id = str(data.loc[i, 'factset_entity_id']).split(' ', 1)[0]
		print(factset_entity_id)

		# API retrieves style, turnover, equityAUM, AUM
		profile_info_url = api_url + "api/institution/info/" + factset_entity_id + "?_dc=1564164994497&appver=3.1.1&_dc=1564164994497"
		response = try_requests(profile_info_url, auth_token)
		if response is not None:
			profile_info_data = json.loads(response.text)
			style_desktop.append(profile_info_data['data']['style'])
			turnover_desktop.append(profile_info_data['data']['turnover'])
			aum_desktop.append(profile_info_data['data']['total_aum'])
			equity_aum_desktop.append(profile_info_data['data']['equity_aum'])
		else:
			style_desktop.append("REQUEST FAILED")
			turnover_desktop.append("REQUEST FAILED")
			aum_desktop.append("REQUEST FAILED")
			equity_aum_desktop.append("REQUEST FAILED")

		# API retrieves ai_targeting score for institution
		targeting_score_url = api_url + "api/targeting/ai/score/" + factset_entity_id + "?_dc=1564164994906&appver=3.1.1&_dc=1564164994906&securityId={0}&type=Institution".format(security_id)
		response = try_requests(targeting_score_url, auth_token)
		if response is not None:
			profile_targeting_data = json.loads(response.text)
			targeting_score.append(profile_targeting_data['data'])
		else:
			targeting_score.append("REQUEST FAILED")

		# API retrieves quality rating for institution
		quality_rating_url = api_url + "api/ownership/institution/qualityrating/" + factset_entity_id + "?_dc=1564164994429&appver=3.1.1&_dc=1564164994429"
		response = try_requests(quality_rating_url, auth_token)
		if response is not None:
			profile_qr_data = json.loads(response.text)
			qr_desktop.append(profile_qr_data['data']['rating'])
		else:
			qr_desktop.append("REQUEST FAILED")

		current_position_url = api_url + "api/ownership/v2/institution/" + factset_entity_id + "/position?appver=3.1.1&_dc=1564164995313&entity_type=institution&securityId={0}".format(security_id)
		response = try_requests(current_position_url, auth_token)
		if response is not None:
			profile_curr_position_data = json.loads(response.text)
			position_desktop.append(profile_curr_position_data['data']['current'])
		else:
			position_desktop.append("REQUEST FAILED")

	for i in range(end_range, len(data)):
		position_desktop.append("N/A ")
		style_desktop.append("N/A ")
		turnover_desktop.append("N/A ")
		aum_desktop.append("N/A ")
		equity_aum_desktop.append("N/A ")
		qr_desktop.append("N/A ")
		targeting_score.append("N/A ")

	data['Style Desktop'] = style_desktop
	data['Turnover Desktop'] = turnover_desktop
	data['AUM Desktop'] = aum_desktop
	data['Equity AUM Desktop'] = equity_aum_desktop
	data['Quality Rating Desktop'] = qr_desktop
	data['Targeting_Score Desktop'] = targeting_score
	data['Position Desktop'] = position_desktop
	data.to_csv("../Excel_SpreadSheets/Output_Data/Profile_Holder_Table/APD_Institution_vs_Cyclops_STAGE.csv", index=False)


def try_requests(url, authorisation):
	try:
		resp = requests.get(url, headers={"Authorization": authorisation})
		return resp

	except Exception:
		print("--Request Failed--")
		return


if __name__ == '__main__':
	main(sys.argv[1:])
