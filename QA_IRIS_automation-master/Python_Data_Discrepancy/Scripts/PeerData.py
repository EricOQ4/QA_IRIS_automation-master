"""
Checks peer data in multiple locations to check for consistency.

Steps to run:
Steps to run:
1. Update config.py
    a. Update Auth token
    b. Update Api url to point to intended environment (stage, dev, ...)
    c. Update the security id
2. Update the peers to test on for YOUR Desktop user.
    a. Go to admin --> profile, for the desktop user that you grabbed the auth_token with, change the peers in peerlist tab
3. Run script
"""

import sys
import pandas as pd
from pandas.io.json import json_normalize
import requests
import timeit
import json
import config as cfg


def main(argv):
    start = timeit.default_timer()
    api_url = cfg.api_url
    security_id = cfg.security_id
    peer_params = {
        'limit': '100',
        'security': security_id,
        'select': ['symbol', 'exchange'],
        'service': ['stock', 'average_volume']

    }
    watch_params = {
        '_dc': '1570460926159',
        'appver': '3.1.1',
        'service': 'stock|sentiment|volatility|activism|expected_ranges|returnAnalysis|qualityRating|estimates',
        'page': 1,
        'limit': 100

    }

    if len(argv) == 0:
        auth_token = cfg.auth_token
        print(
            "If you haven't done so, remember to update the Authorization Token by putting it in quotes as an argument for the script.")
    else:
        auth_token = argv[0]
    df = pd.DataFrame(columns=["security id", "relative discrepencies" , "indicator discrepencies", "quality discrepencies"])
    # get_peer_data(api_url,auth_token,peer_params).to_string()
    watchlist = get_watchlist(api_url, auth_token, watch_params)
    for index, peer in watchlist.iterrows():
        df = df.append(compare_watch_list(index, peer, api_url, auth_token))

    df.to_csv( "../Excel_SpreadSheets/Output_Data/AI_Targeting/APD_PeerData_DEV-TEST.csv",
        index=False)
    end = timeit.default_timer()
    print(end - start)


def get_peer_data(api_url, auth_token, peer_params):

    peer_data_url = api_url + "api/peer"
    response = try_requests(peer_data_url, auth_token, params=peer_params)

    if response is not None:
        try:
            ownership_data = json.loads(response.text)
        # print(json.dumps(response.json(), sort_keys=True,indent=4))
        except json.decoder.JSONDecodeError:
            print("JSON DECODER ERROR")
            return
    else:
        return
    volume_df = pd.DataFrame()
    stock_df = pd.DataFrame()
    df = json_normalize(data=response.json(), record_path='data')
    result_df = df[['_security']]
    for i in range(df.shape[0]):
        service_row = (df.iloc[i]['services'])
        volume_df = volume_df.append(json_normalize(service_row[1]))

        stock_df = stock_df.append(json_normalize(service_row[0]))

    result_df2 = pd.concat([volume_df, stock_df], axis=1)
    result_df.reset_index(drop=True, inplace=True)
    result_df2.reset_index(drop=True, inplace=True)
    result_df3 = pd.concat([result_df, result_df2], axis=1)

    print(result_df3.to_string())
    return df


def get_watchlist(api_url, auth_token, params):

    watchlist_url = api_url + "api/watchlist/v2"
    response = try_requests(watchlist_url, auth_token, params=params)

    if response is not None:
        try:
            ownership_data = json.loads(response.text)
        # print(json.dumps(response.json(), sort_keys=True,indent=4))
        except json.decoder.JSONDecodeError:
            print("JSON DECODER ERROR")
            return
    else:
        return
    df = json_normalize(data=response.json())

    return df


def compare_peer_data(i, api_url, auth_token):

    return


def compare_watch_list(index, i, api_url, auth_token):
    relative_discrepencies = ''
    indicator_discrepencies = ''
    quality_discrepencies = ''
    security_id = str(i['_security'])
    print(i['services.activism.oaa'])

    # check relative performance
    relative_perf_url = api_url + "api/oxford/relative-performance/price/{0}".format(security_id)
    relative_perf_response = try_requests(relative_perf_url, auth_token)
    # relative_perf_data = json_normalize(data=relative_perf_response.json(), record_path="data")
    relative_perf_data = pd.DataFrame(relative_perf_response.json())
    relative_perf_data = relative_perf_data['data'].apply(pd.Series)
    relative_perf_cols = ['expectedreturn', 'returnduetomarket', 'returnduetopeers', 'returnduetosector', 'returnondate', 'stockspecificreturn']

    for col in relative_perf_cols:
        list_col = 'services.returnAnalysis.' + col
        if str(float(i[list_col])) != str(float(relative_perf_data[0][col])):
            relative_discrepencies += '| relative performance - watchlist({0}): {1} - profile({2}): {3} |'.format(col, str(i[list_col]), col, str(float(relative_perf_data[0][col])))

    indicators_url = api_url + "api/oxford/indicators/{0}".format(security_id)
    indicators_response = try_requests(indicators_url, auth_token)
    try:
        indicators_data = pd.DataFrame(indicators_response.json())
        indicators_data = indicators_data['data'].apply(pd.Series)
        indicators_cols = {'oaa': 'services.activism.oaa', 'osi': 'services.sentiment.osi', 'ovi': 'services.volatility.ovi'}

        for col in indicators_cols:
            if str(int(i[indicators_cols[col]])) != str(indicators_data[0][col]):
                indicator_discrepencies += '| indicators - watchlist({0}): {1} - profile({2}): {3} |'.format(col, str(int(i[indicators_cols[col]])), col, str(indicators_data[0][col]))
    except Exception:
        print('no indicator data')

    quality_url = api_url + "api/ownership/company/qualityrating/{0}".format(security_id)
    quality_response = try_requests(quality_url, auth_token)
    try:
        quality_data = pd.DataFrame(quality_response.json())
        quality_data = quality_data['data'].apply(pd.Series)
        quality_cols = ['rating']
        quality_peers_cols = ['services.quality_rating.rating']

        for col in quality_cols:
            if str(i[quality_peers_cols[0]]) != str(quality_data[0][col]):
                quality_discrepencies += '|quality - watchlist({0}): {1} - profile({2}): {3} |'.format(col, str(i[quality_peers_cols[0]]), col, str(quality_data[0][col]))
    except Exception:
        print('no quality data')

    return pd.DataFrame({
        "security id" : [security_id],
        "relative discrepencies":  [relative_discrepencies],
        "indicator discrepencies": [indicator_discrepencies],
        "quality discrepencies": [quality_discrepencies]
    })


def try_requests(url, authorization, params=None):

    try:
        resp = requests.get(url, headers={"Authorization": authorization}, params=params)
        return resp

    except Exception:
        print("--Request Failed--")
        return


if __name__ == '__main__':

    main(sys.argv[1:])
