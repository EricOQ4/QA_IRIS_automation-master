"""
Checks the contact coverage/strategy in MongoDB matches the contact profile page for Q4 Contacts

How to run:
1. Update config.py
    a. Update Auth token
    b. Update Api url to point to intended environment (stage, dev, ...)
    c. Update Security id to the security you want to target
2. Update the database-config.py file
    a. Change the database params for the database server to use. (Tihs script just uses mongodb)

3. Run script.

"""

import json
import pandas as pd
import requests
import config as cfg
from helper import database_query_helper

API_URL = cfg.api_url
AUTH_TOKEN = cfg.auth_token


def main():

    result_df = pd.DataFrame(columns=['contact_id', 'factset_entity_id', 'discrepancy'])
    q4_contacts = get_q4_contacts()
    for contact in q4_contacts:
        try:
            result_df = result_df.append(compare_contact_strategy(contact))
            result_df.to_csv(
                "../Excel_SpreadSheets/Output_Data/Contact/Q4_Contact_Strategy.csv",
                index=False)
        except Exception as exc:
            print(exc)


def compare_contact_strategy(contact):
    discrepancy = ''
    contact_strategies = get_contact_strategy(contact)
    contact_strategy = None
    for i in contact_strategies:
        if i['factset_entity_id'] == contact['factset_entity_id']:
            contact_strategy = i

    values_list = [
        'security_type', 'market_cap', 'country',
        'custom_region', 'strategy', 'style', 'quality', 'summary'
    ]

    if contact_strategy is not None:
        for v in values_list:
            profile_value = contact_strategy[v]
            mongo_value = contact[v]
            if profile_value != mongo_value:
                discrepancy += '--- {0} : Profile = {1} : Mongo = {2}'.format(v, profile_value, mongo_value)

        return pd.DataFrame({
            'contact_id': {contact_strategy['_contact']},
            'factset_entity_id': {contact_strategy['factset_entity_id']},
            'discrepancy': {discrepancy}
        })
    else:
        return None


def get_contact_strategy(mongo_contact):
    contact_id = mongo_contact['_contact']
    strategy_endpoint = API_URL + 'api/contact/{0}/strategy'.format(contact_id)
    response = try_requests(strategy_endpoint, AUTH_TOKEN)
    if response is not None:
        try:
            json.loads(response.text)
        except json.decoder.JSONDecodeError:
            print("JSON DECODER ERROR")
            return
    else:
        return

    return response.json()['data']


def get_q4_contacts():
    query = {
        "source": "q4"
    }

    return database_query_helper.execute_mongo_query('contact_strategy', query)


def try_requests(url, authorization, params=None):

    try:
        resp = requests.get(url, headers={"Authorization": authorization}, params=params)
        print(resp.text)
        return resp

    except Exception:
        print("--Request Failed--")
        return


if __name__ == '__main__':
    main()