"""
Checks the quick search with different GDPR contacts to see if they would show up in the search results (Which they should not)

Steps to run:
1. Update config.py
    a. Update Auth token
    b. Update Api url to point to intended environment (stage, dev, ...)

2. Update the input and expected output of a search query for contact names located at:
    Excel_SpreadSheets/Output_Data/Contact_GDPR_Search/GDPR_Contacts.csv
3. Run Script

Output: A CSV file that returns true or false if they show up as a top 5 and/or top 10 of the results.
location: /Excel_SpreadSheets/Output_Data/Contact/gdpr_contact_search_result.csv
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
    csv_path = '../Excel_SpreadSheets/Source_Data/Contact_GDPR_Search/GDPR_Contacts.csv'
    final_df = pd.DataFrame(columns=['Query', 'Status', 'Was_Found_In_Quick_Search', 'Was_Found_In_Activity_Search'])

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
    final_df.to_csv('../Excel_SpreadSheets/Output_Data/Contact/gdpr_contact_search_result.csv')

    end = timeit.default_timer()
    print(str(end - start) + ' seconds')


def create_dict(csv_path):

    df = pd.read_csv(csv_path)
    search_dict = {}
    for i in range(len(df)):
        if df.at[i, 'Name']:
            search_dict[df.at[i, 'Name']] = df.at[i, 'Status']
    return search_dict


def compare_result(contact_search_dict, key):

    counter = 0
    num_results = 10
    query = key
    status = contact_search_dict.get(key)

    is_found_quick = False
    is_found_activity = False
    quick_results = do_quick_search(query, num_results)
    if quick_results['success']:
        for result in quick_results['data']:
            if result['type'] == 'contact':
                if result['name'] == query:
                    is_found_quick = True
        counter += 1
    else:
        print("Error getting request")

    activity_results = do_activity_search(query, num_results)
    if activity_results['success']:
        for result in activity_results['data']:
            if result['_type'] == 'contact':
                if result['_source'].get('full_name') == query:
                    is_found_activity = True
        counter += 1
    else:
        print("Error getting request")

    print("Query: {0} || Status: {1}  ||  Was Found in Quick: {2} || "
          " Was found in Activity: {3}".format(query, status, is_found_quick, is_found_activity))
    df = pd.DataFrame({
        'Query': [query],
        'Status': [status],
        'Was_Found_In_Quick_Search': [is_found_quick],
        'Was_Found_In_Activity_Search': [is_found_activity]
    })

    return df


def do_quick_search(query, num_results):

    page_size = num_results
    quick_search_url = API_URL + 'api/search/v2/quick?pageSize={0}&query={1}'.format(page_size, query)
    response = try_requests(quick_search_url, AUTH_TOKEN)

    return response.json()


def do_activity_search(query, num_results):
    page_size = num_results
    'https://api-dev.q4desktop.com/api/search/v2/entity?'
    activity_search_url = API_URL + 'api/search/v2/entity?limit={0}&pageSize=1&query={1}&type=contact'.format(page_size, query)
    response = try_requests(activity_search_url, AUTH_TOKEN)

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
