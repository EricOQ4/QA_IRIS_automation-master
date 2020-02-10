"""
Checks contact coverage/strategy of factset contacts in postgres and compares it to the contact page

Steps to run:
1. Update config.py
    a. Update Auth token
    b. Update Api url to point to intended environment (stage, dev, ...)
    c. Update the Security id
2. Update database-config.py
    a. Update parameters to point to the correct database server (Only uses postgres database)
3. (If necessary) update the contact strategy mapping csv files. This is used to map the strategy and region codes to their name
    Located in Excel_SpreadSheets/Source_Data/Contact_Strategy_Mapping folder
    a. Update ppl_specialization_code.csv
    b. Update region_code.csv
4. Run Script

Output: A CSV file of the contact_id, name and any discrepancies

"""


import json
import ssl
import sys
import pandas as pd
from pandas.io.json import json_normalize
import requests
import config as cfg
from concurrent import futures
from helper import database_query_helper
import timeit

ssl._create_default_https_context = ssl._create_unverified_context

API_URL = cfg.api_url
AUTH_TOKEN = cfg.auth_token


def main(argv):
    start = timeit.default_timer()

    result_df = pd.DataFrame(columns=['contact_id', 'factset_person_id', 'factset_entity_id', 'discrepancy'])
    security_id = cfg.security_id
    contact_lookup_params = {
        '_dc': 1572270709826,
        'appver': '3.1.3',
        'securityId': security_id,
        'notes': 'true',
        'entity_id': '',
        'page': 1,
        'start': 0,
        'limit': 50
    }
    institution_params = {
        '_dc': 1572270709826,
        'appver': '3.1.3',
        'holder_type': 'institution',
        'page': 1,
        'start': 0,
        'limit': 50,
    }
    pages = 10
    specialization_code_mapping = pd.read_csv('../Excel_SpreadSheets/Source_Data/Contact_Strategy_Mapping/ppl_specialization_code.csv')
    specialization_code_mapping = specialization_code_mapping.set_index('ppl_specialization_code')
    region_code_mapping = pd.read_csv('../Excel_SpreadSheets/Source_Data/Contact_Strategy_Mapping/region_code.csv')
    region_code_mapping = region_code_mapping.set_index('ppl_region_code')

    contact_strategy_sql = "SELECT own_con_people.factset_person_id, own_con_jobs.factset_entity_id, STRING_AGG(DISTINCT ppl_asset_type_coverage.ppl_specialization_code::varchar(255), ',') as ppl_specialization_code, STRING_AGG(DISTINCT ppl_asset_type_coverage.ppl_region_code::varchar(255), ',') as ppl_region_code, STRING_AGG(DISTINCT own_con_job_functions.job_function_code::varchar(255), ',') as job_function_code, STRING_AGG(DISTINCT ppl_bus_region_coverage.ppl_bus_code::varchar(255), ',') as ppl_bus_code, STRING_AGG(DISTINCT ppl_bus_map.ppl_bus_desc::varchar(255), ',') as ppl_bus_desc FROM own_con_people LEFT JOIN own_con_jobs ON (own_con_jobs.factset_person_id = own_con_people.factset_person_id AND own_con_jobs.active = 1) LEFT JOIN ppl_asset_type_coverage ON ppl_asset_type_coverage.job_id = own_con_jobs.job_id LEFT JOIN own_con_job_functions ON (own_con_job_functions.job_id = own_con_jobs.job_id AND own_con_job_functions.active = 1) LEFT JOIN ppl_bus_region_coverage ON (ppl_bus_region_coverage.job_id = own_con_jobs.job_id AND ppl_bus_region_coverage.active = 1) LEFT JOIN ppl_bus_map ON ppl_bus_map.ppl_bus_code = ppl_bus_region_coverage.ppl_bus_code GROUP BY own_con_people.factset_person_id, own_con_jobs.factset_entity_id"
    postgres_contacts_df = database_query_helper.execute_sql_postgres(contact_strategy_sql)

    contact_list = pd.DataFrame(columns=['_id', 'factset_person_id', 'factset_entity_id'])
    for page in range(pages):
        institution_params['page'] = page
        contact_list = contact_list.append(get_contact_list(contact_lookup_params, institution_params), ignore_index=True)

    with futures.ThreadPoolExecutor(max_workers=10) as executor:
        future_strategy = {
            executor.submit(execute_comparison, i, postgres_contacts_df, specialization_code_mapping, region_code_mapping): i for i in contact_list.itertuples()
        }
        for future in futures.as_completed(future_strategy):
            try:
                result_df = result_df.append(future.result(), ignore_index=True)
            except Exception as exc:
                print('generated an exception: %s' % exc)

    result_df.to_csv(
        "../Excel_SpreadSheets/Output_Data/Contact/Contact_Strategy.csv",
        index=False)
    end = timeit.default_timer()
    duration = end - start
    print("Duration: " + str(duration) + " seconds")


def execute_comparison(contact, postgres_contacts_df, specialization_code_mapping, region_code_mapping):
    if getattr(contact, 'factset_person_id') != 'NaN':
        db_strategy = postgres_contacts_df.loc[
            (postgres_contacts_df['factset_person_id'] == getattr(contact, 'factset_person_id'))]
        db_strategy = db_strategy.loc[(db_strategy['factset_entity_id'] == getattr(contact, 'factset_entity_id'))]
        profile_strategy = get_contact_strategy(contact)
        return compare_strategy(db_strategy, profile_strategy, specialization_code_mapping, region_code_mapping)
    else:
        return


def get_contact_strategy(i):
    contact_id = getattr(i, '_1')
    strategy_endpoint = API_URL + 'api/contact/{0}/strategy'.format(contact_id)
    response = try_requests(strategy_endpoint, AUTH_TOKEN)
    if response is not None:
        try:
            ownership_data = json.loads(response.text)
        # print(json.dumps(response.json(), sort_keys=True,indent=4))
        except json.decoder.JSONDecodeError:
            print("JSON DECODER ERROR")
            return
    else:
        return
    df = json_normalize(data=response.json(), record_path='data')

    return df


def compare_strategy(db_strategy, profile_strategy, specialization_code_mapping, region_code_mapping):
    contact_id = ''
    person_id = ''
    entity_id = ''
    discrepancy = ''

    ppl_specialization_code = []
    ppl_region_code = []
    ppl_bus_desc = []

    if not db_strategy.empty:
        if db_strategy.iloc[0]['ppl_specialization_code'] is not None:
            ppl_specialization_code = db_strategy.iloc[0]['ppl_specialization_code'].split(',')
        if db_strategy.iloc[0]['ppl_region_code'] is not None:
            ppl_region_code = db_strategy.iloc[0]['ppl_region_code'].split(',')
        if db_strategy.iloc[0]['ppl_bus_desc'] is not None:
            ppl_bus_desc = db_strategy.iloc[0]['ppl_bus_desc'].split(',')
        person_id = db_strategy.iloc[0]['factset_person_id']
        entity_id = db_strategy.iloc[0]['factset_entity_id']

    if profile_strategy.empty:
        if not db_strategy.empty:
            if ppl_specialization_code or ppl_region_code or ppl_bus_desc:
                discrepancy += ' Profile is empty: DB is '
                discrepancy += db_strategy.iloc[0]['ppl_specialization_code'] if db_strategy.iloc[0]['ppl_specialization_code'] is not None else ''
                discrepancy += db_strategy.iloc[0]['ppl_region_code'] if db_strategy.iloc[0]['ppl_region_code'] is not None else ''
                discrepancy += db_strategy.iloc[0]['ppl_bus_desc'] if db_strategy.iloc[0]['ppl_bus_desc'] is not None else ''
    else:
        profile_strategy = profile_strategy.loc[(profile_strategy['factset_entity_id'] == entity_id)]

    if not profile_strategy.empty:
        # Check Region
        contact_id = profile_strategy.iloc[0]['_contact']
        profile_region = profile_strategy.iloc[0]['country'] + profile_strategy.iloc[0]['region_group'] + profile_strategy.iloc[0]['custom_region']
        for region_code in ppl_region_code:
            region_value = region_code_mapping.at[region_code, 'region_value'].split(',')
            for value in region_value:
                if value not in profile_region:
                    discrepancy += ' --- DB Region Code {0} '.format( value)

        # Check Sector
        for sector in ppl_bus_desc:
            if sector not in profile_strategy.iloc[0]['sectors']:
                discrepancy += ' --- Profile Sector: {0} | DB Sector: {1} '.format(profile_strategy.iloc[0]['sectors'], sector)

        # Check Specialization
        for specialization in ppl_specialization_code:
            security_value = []
            market_value = []
            country_value = []
            custom_region_value = []
            strategy_value = []
            style_value = []
            quality_value = []
            # Check each type
            if str(specialization_code_mapping.at[specialization, 'Security_Type_Value']) != 'nan':
                security_value = specialization_code_mapping.at[specialization, 'Security_Type_Value'].split(',')
            if str(specialization_code_mapping.at[specialization, 'Market_Cap_Value']) != 'nan':
                market_value = specialization_code_mapping.at[specialization, 'Market_Cap_Value'].split(',')
            if str(specialization_code_mapping.at[specialization, 'Country_Value']) != 'nan':
                country_value = specialization_code_mapping.at[specialization, 'Country_Value'].split(',')
            if str(specialization_code_mapping.at[specialization, 'Custom_Region_Value']) != 'nan':
                custom_region_value = specialization_code_mapping.at[specialization, 'Custom_Region_Value'].split(',')
            if str(specialization_code_mapping.at[specialization, 'Strategy_Value']) != 'nan':
                strategy_value = specialization_code_mapping.at[specialization, 'Strategy_Value'].split(',')
            if str(specialization_code_mapping.at[specialization, 'Style_Value']) != 'nan':
                style_value = specialization_code_mapping.at[specialization, 'Style_Value'].split(',')
            if str(specialization_code_mapping.at[specialization, 'Quality']) != 'nan':
                quality_value = specialization_code_mapping.at[specialization, 'Quality'].split(',')

            value_dict = {
                'security_type': security_value, 'market_cap': market_value, 'country': country_value,
                'custom_region': custom_region_value, 'strategy': strategy_value, 'style': style_value, 'quality': quality_value
            }
            for k in value_dict:
                for v in value_dict[k]:
                    if v.strip() not in profile_strategy.iloc[0][k]:
                        discrepancy += ' --- {0} : {1} value '.format(v, k)

    result_df = pd.DataFrame({
        'contact_id': [contact_id],
        'factset_person_id': [person_id],
        'factset_entity_id': [entity_id],
        'discrepancy': [discrepancy]

    })
    print(discrepancy)

    return result_df


# Have to get list of contacts by choosing a security, get list of institututions/funds and get contact id from those
# entities
def get_contact_list(contact_params, institution_params):

    contact_list_df = pd.DataFrame(columns=['_id', 'factset_person_id', 'factset_entity_id'])
    # Get list of institutions
    current_holdings_url = API_URL + 'api/ownership/v2/security/{0}/current'.format(contact_params['securityId'])
    response = try_requests(current_holdings_url, AUTH_TOKEN, params=institution_params)
    if response is not None:
        try:
            ownership_data = json.loads(response.text)
        except json.decoder.JSONDecodeError:
            print("JSON DECODER ERROR")
            return
    else:
        return

    df = json_normalize(data=response.json(), record_path='data')
    df = df[['_id', 'factset_entity_id']]
    contacts_url = API_URL + 'api/institution/contact'
    for i in df.itertuples():
        entity_id = getattr(i, 'factset_entity_id')
        contact_params['entity_id'] = entity_id
        response = try_requests(contacts_url, AUTH_TOKEN, params=contact_params)
        contact_df = json_normalize(data=response.json(), record_path='data')
        contact_df['factset_entity_id'] = entity_id
        try:
            contact_list_df = contact_list_df.append(contact_df[['_id', 'factset_person_id', 'factset_entity_id']])
        except:
            print("Error retrieving contacts for institution: " + entity_id)
    return contact_list_df


def try_requests(url, authorization, params=None):

    try:
        resp = requests.get(url, headers={"Authorization": authorization}, params=params)
        print(resp.text)
        return resp

    except Exception:
        print("--Request Failed--")
        return


if __name__ == '__main__':
    main(sys.argv[1:])