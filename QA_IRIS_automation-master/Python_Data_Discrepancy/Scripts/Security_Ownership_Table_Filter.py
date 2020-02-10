"""
Work in progress. Logic doesn't work properly to compare with filters


Checks api response of Ownership Table filters.
Filters: Institutions: Type and Style

How to run:
1. Update config.py
    a. Update Auth token
    b. Update Api url to point to intended environment (stage, dev, ...)
    c. Update Security id to the security you want to target
2. Hit Run

"""
import sys
import pandas as pd
from pandas.io.json import json_normalize
import requests
import timeit
from concurrent import futures
import config as cfg
import math

API_URL = cfg.api_url
SECURITY_ID = cfg.security_id
AUTH_TOKEN = cfg.auth_token


def main(argv):
    start = timeit.default_timer()
    position_filters = [
        'new', 'sold', 'increased', 'decreased', 'maintained'
    ]
    style_filters = [
        'Aggressive Growth', 'Yield', 'Value', 'Specialty', 'Quant', 'Private Equity', 'Index', 'Income', 'Hedge Fund',
        'Growth', 'Generalist', 'GARP', 'Deep Value', 'Broker/Dealer', 'Alternative'
    ]
    type_filter = [
        "Arbitrage",
        "Bank Investment Division",
        "Broker",
        "Corporate",
        "Custodial",
        "Family Office",
        "Foundation/Endowment Manager",
        "Fund Distributor",
        "Fund of Funds Manager",
        "Fund of Hedge Funds Manager",
        "Fund",
        "Govt (Fed/Local/Agency)",
        "Hedge Fund Manager",
        "Insurance Company",
        "Investment Adviser",
        "Investment Banking",
        "Investment Company",
        "Market Maker",
        "Master Ltd Part",
        "Mutual Fund Manager",
        "Pension Fund Manager",
        "Private Banking/Wealth Mgmt",
        "Real Estate Manager",
        "Research Firm",
        "Sovereign Wealth Manager",
        "Stock Borrowing/Lending",
        "Subsidiary Branch",
        "Venture Capital/Pvt Equity",
    ]
    activity_filter = [
        True, False, ''
    ]

    params = {
        'appver': '3.1.5',
        '_dc': '1576786186843',
        'securityId': SECURITY_ID,
        'holder_type': 'institution',
        'position': '',
        'style': '',
        'logged_activity': '',
        'type': '',
        'activity_start': '',
        'activity_end': '',
        'page': 1,
        'start': 0,
        'limit': 100
    }
    df_all = pd.DataFrame()
    num_pages = get_num_pages(params)
    if num_pages != 0:
        with futures.ThreadPoolExecutor(max_workers=5) as executor:
            future_ownership = {
                executor.submit(get_ownership_table, params, page): page for page in range(1, num_pages+1)}
            for future in futures.as_completed(future_ownership):
                try:
                    df_all = df_all.append(future.result(), ignore_index=True)
                except Exception as exc:
                    print('generated an exception: %s' % exc)
    print(df_all.to_string())

    compare_filter(style_filters, params, df_all.copy(), 'style')
    compare_filter(type_filter, params, df_all.copy(), 'type')

    end = timeit.default_timer()
    print(str(end - start) + ' seconds')


def compare_filter(filters, params, df_all, filter_type):

    for filter_name in filters:
        type_params = params.copy()
        type_params[filter_type] = filter_name
        filter_df = pd.DataFrame()
        num_pages = get_num_pages(type_params)
        with futures.ThreadPoolExecutor(max_workers=5) as executor:
            future_ownership = {
                executor.submit(get_ownership_table, type_params, page): page for page in range(1, num_pages+1)}
            for future in futures.as_completed(future_ownership):
                try:
                    filter_df = filter_df.append(future.result(), ignore_index=True)
                except Exception as exc:
                    print('generated an exception: %s' % exc)
        df_all_subset = df_all[df_all[filter_type] == filter_name]
        print('{0} Filter: {1}'.format(filter_type, filter_name))

        print('expected size: {0} | fitered: {1}'.format(str(df_all_subset.shape[0]), str(filter_df.shape[0])))
        if df_all_subset.shape[0] != 0 and filter_df.shape[0] != 0:
            df_all_subset = df_all_subset[['_id', 'factset_entity_id', 'holder_name', filter_type]]
            filter_df = filter_df[['_id', 'factset_entity_id', 'holder_name', filter_type]]
            combined = pd.concat([df_all_subset, filter_df]).drop_duplicates(keep=False)
            print(combined.to_string())
        else:
            print(df_all_subset.to_string())
            print(filter_df.to_string())


def get_ownership_table(params, page):
    print('page: ' + str(page))
    ownership_params = params.copy()
    ownership_params['page'] = page
    ownership_url = API_URL + 'api/ownership/v2/security/{0}/current'.format(SECURITY_ID)
    response = try_requests(ownership_url, AUTH_TOKEN, params=ownership_params)

    return json_normalize(data=response.json(), record_path='data')


def get_num_pages(params):
    ownership_url = API_URL + 'api/ownership/v2/security/{0}/current'.format(SECURITY_ID)
    resp = try_requests(ownership_url, AUTH_TOKEN, params=params).json()
    if resp['success']:

        return math.ceil(resp['total']/100)
    else:
        return 0


def try_requests(url, authorisation, params=None):

    try:
        resp = requests.get(url, headers={"Authorization": authorisation}, params=params)
        return resp

    except Exception:
        print("--Request Failed--")
        return


if __name__ == '__main__':
    main(sys.argv[1:])
