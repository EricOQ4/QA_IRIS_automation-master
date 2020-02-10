"""
Script is used to help with exporting MongoDB Queries as csv files

Example: Search by ObjectId:
query = {
    '_id': ObjectId('57d38c5891aea376a7b6d4df')
}

Example: Search by a string:
query = {
    'factset_person_id': '002FR3-E'
}

sorting example:
1 = ascending
-1 = descending
sort = [(factset_person_id, -1), (create_date, 1)]

Limit example: an integer
limit = 20

Change Collection
collection = 'contacts'
collection = 'securities' etc....

"""

import pandas as pd
from helper import database_query_helper as db
from pandas.io.json import json_normalize
from bson import ObjectId


def main():
    df = pd.DataFrame()

    query = {
    }
    limit = None
    sort = None

    collection = 'contacts'
    results = db.execute_mongo_query(collection, query, limit=limit, sort=sort)
    for result in results:
        df = df.append(json_normalize(data=result), ignore_index=True)

    print(df.to_string())
    df.to_csv('../Excel_SpreadSheets/Output_Data/MongoDB_CSV_Export/MongoDB_Export.csv')


if __name__ == '__main__':
    main()