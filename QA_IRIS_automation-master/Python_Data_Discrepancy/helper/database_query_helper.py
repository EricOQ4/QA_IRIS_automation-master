import pyodbc
import psycopg2
import pandas as pd
import database_config as cfg
import pymongo
from pymongo import MongoClient


def execute_sql_mssql(query, return_as_dataframe=True):
    conn_string = 'DRIVER={0};SERVER={1};DATABASE={2};UID={3};PWD={4};Trusted_Connection=no'.format(
        cfg.myssql['driver'], cfg.myssql['host'], cfg.myssql['db'], cfg.myssql['user'], cfg.myssql['password']
    )
    conn = pyodbc.connect(conn_string)
    if return_as_dataframe:
        return pd.read_sql(query, conn)

    else:
        cursor = conn.cursor()
        cursor.execute(query)
        return cursor


def execute_sql_postgres(query, return_as_dataframe=True):
    conn_string = 'host={0} port=5432 dbname={1} user={2} password={3}'.format(
        cfg.postgres['host'], cfg.postgres['db'], cfg.postgres['user'], cfg.postgres['password']
    )
    conn = psycopg2.connect(conn_string)
    if return_as_dataframe:
        return pd.read_sql(query, conn)

    else:
        cursor = conn.cursor()
        cursor.execute(query)
        return cursor


def execute_mongo_query(collection, query, sort=None, limit=None):
    mongo_uri = 'mongodb+srv://{0}:{1}@{2}/{3}?ssl=true'.format(
        cfg.mongodb['user'], cfg.mongodb['password'], cfg.mongodb['host'],
        cfg.mongodb['auth_database']
    )
    client = MongoClient(mongo_uri)
    db = client[cfg.mongodb['database']]
    db_collection = db[collection]
    if sort is not None and limit is not None:
        doc = db_collection.find(query).limit(4).sort(sort)
    elif sort is not None and limit is None:
        doc = db_collection.find(query).sort(sort)
    elif sort is None and limit is not None:
        doc = db_collection.find(query).limit(limit)
    else:
        doc = db_collection.find(query)
    return doc


if __name__ == '__main__':
    # Print out the name of drivers installed on machine for SQL Server
    print(pyodbc.drivers())
    query = {
        "source": "q4"
    }
    test = execute_mongo_query('contact_strategy', query)
    for contact in test:
        print(contact)