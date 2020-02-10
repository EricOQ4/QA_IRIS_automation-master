""" Checking if Report Builder's Institution export data matches the data in the Report Builder API.

What the script does:
Creates a new csv file with a new position column comparing values to the exported position column

Output: A CSV with 3 columns, the factset_fund_id, fund_name, Targeting vs. Profile Page.
The last column prints out the value that is different, if any, between the 2 pages.

Created by Noah Lattai for #grustle
"""

import sys
import pandas as pd
import requests
import json
import config as cfg

api_url = cfg.api_url


def main(argv):
    if len(argv) == 0:
        auth_token = cfg.auth_token
        print(
            "If you haven't done so, remember to update the Authorization Token by putting it in quotes as an argument for the script.")
    else:
        auth_token = argv[0]

    headers = {
        'Pragma': 'no-cache',
        'Expiries': '-1',
        'Authorization': auth_token,
        'Sec-Fetch-Mode': 'cors',
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Cache-control': 'no-cache, no-store, must-revalidate, max-age=-1, private',
        'Referer': 'https://develop.q4desktop.com/report/5d3867b7f379690004c9a4c8',
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36',
    }

    params = (
        ('securityId', '57b92871be1c33ae235ec804'),
    )

    data = json.dumps({"_entityType": ["5bae6434924eb2f6cb5db6e2"],
                       "fields": ["5bae6e6e74b3f9c5570a6724", "5bae6e6e74b3f9c5570a6704", "5bdb1716c595c97e0ee92ecc"],
                       "filters": [{"_field": "5bdb1716c595c97e0ee92ecc", "operator": "gte",
                                    "value": "2019-06-30T00:00:00.000Z"}], "sort": [], "peers": [], "limit": [
            {"_id": "5d3867b7f379690004c9a4ca", "_entityType": "5bae6434924eb2f6cb5db6e2", "value": 250}],
                       "isPivot": True})
    response = requests.post(api_url + 'api/report/dataItem/snapshot', headers=headers,
                             params=params, data=data)

    rbdata = json.loads(response.text)
    check_discrepancy(rbdata)

    print(response.text)


def check_discrepancy(rpdata):
    data = pd.read_csv("../Excel_SpreadSheets/Source_Data/Report_Builder/data.csv", skiprows=11)
    data.columns = ['Institution Name', 'POS']
    desktop_position = []
    factset_entity_id = []
    discrepancy = []

    print(rpdata)
    for i in rpdata['data']:
        factset_entity_id.append(i['factset_entity_id'])
        print(i['holdings'][0]['current'])
        desktop_position.append(i['holdings'][0]['current'])

    print(len(desktop_position))
    print(len(factset_entity_id))
    print(len(data['Institution Name']))
    data['Desktop Position'] = desktop_position
    data['Factset Entity ID'] = factset_entity_id

    data.to_csv("../Excel_SpreadSheets/Output_Data/data_updated.csv", index=False)

    print('chillin')


if __name__ == '__main__':
    main(sys.argv[1:])

