"""
Checks the quick search with different contacts to see if they would show up in the search results with short names

Steps to run:
1. Update config.py
    a. Update Auth token
    b. Update Api url to point to intended environment (stage, dev, ...)

2. Update the input and expected output of a search query for contact names located at:
    Excel_SpreadSheets/Output_Data/Contact_ShortName_Search/short_name_search_result.csv
3. Run Script

Output: A CSV file that returns true or false if they show up as a top 5 and/or top 10 of the results.
"""

import sys
import pandas as pd
import requests
import config as cfg
import timeit
from concurrent import futures

API_URL = cfg.api_url
AUTH_TOKEN = cfg.auth_token


def main(argv):
    start = timeit.default_timer()
    csv_path = '../Excel_SpreadSheets/Source_Data/Contact_ShortName_Search/short_name_search.csv'
    final_df = pd.DataFrame(columns=['Query', 'Expected', 'Was_Found_Top_5', 'Was_Found_Top_10'])

    contact_search_dict = create_dict(csv_path)

    with futures.ThreadPoolExecutor(max_workers=5) as executor:
        future_targeting = {
            executor.submit(compare_result, contact_search_dict, key): key for key
            in contact_search_dict}
        for future in futures.as_completed(future_targeting):
            try:
                final_df = final_df.append(future.result(), ignore_index=True)
            except Exception as exc:
                print('generated an exception: %s' % exc)
    final_df.to_csv('../Excel_SpreadSheets/Output_Data/Contact_ShortName_Search/short_name_search_result.csv')

    end = timeit.default_timer()
    print(str(end - start) + ' seconds')


def create_dict(csv_path):
    df = pd.read_csv(csv_path)
    search_dict = {}
    for i in range(len(df)):
        if df.at[i, 'Input']:
            search_dict[df.at[i, 'Input']] = df.at[i, 'Output']
    return search_dict


def compare_result(contact_search_dict, key):

    counter = 0
    num_results = 10
    query = key
    expected = contact_search_dict.get(key)

    is_found_5 = False
    is_found_10 = False
    results = do_search(query, num_results)
    if results['success']:
        for result in results['data']:
            if result['type'] == 'contact':
                if result['name'] == expected:
                    if counter < 5:
                        is_found_5 = True
                        is_found_10 = True
                    else:
                        is_found_10 = True
        counter += 1
    else:
        print("Error getting request")

    print("Query: {0} || Expected: {1}  ||  Was Found in top 5: {2} ||  Was found in top 10: {3}".format(
        query, expected, is_found_5, is_found_10))
    df = pd.DataFrame({
        'Query': [query],
        'Expected': [expected],
        'Was_Found_Top_5': [is_found_5],
        'Was_Found_Top_10': [is_found_10]
    })

    return df


def do_search(query, num_results):

    page_size = num_results
    quick_search_url = API_URL + 'api/search/v2/quick?pageSize={0}&query={1}'.format(page_size, query)
    response = try_requests(quick_search_url, AUTH_TOKEN)

    return response.json()


def try_requests(url, authorisation, params=None):
    try:
        resp = requests.get(url, headers={"Authorization": authorisation}, params=params)
        return resp
    except Exception:
        print("--Request Failed--")
        return


if __name__ == '__main__':
    main(sys.argv[1:])
