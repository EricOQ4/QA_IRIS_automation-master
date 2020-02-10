import pandas as pd
import numpy
import timeit
from fuzzywuzzy import fuzz
from fuzzywuzzy import process
import re
import multiprocessing as mp
import unicodedata


def main():
    file_names = ["Microsoft_Holders", "Alibaba_Holders"]
    for file_name in file_names:
        file_path = "../Excel_SpreadSheets/Fuzzy_Data_Comparison/Input_Data/{0}.csv".format(file_name)
        print(file_path)
        df = pd.read_csv(file_path)
        rows = df['MI Holder'].count()
        # create 4 seperate lists for 4 processes to handle concurrently
        array_range = int(rows/4)
        array_ranges = [(df, 0, array_range), (df, array_range, array_range * 2), (df, array_range * 2, array_range * 3), (df, array_range * 3, rows)]
        # create 4 processes
        pool = mp.Pool(processes=4)
        # start processes
        df_final = pd.concat(pool.starmap(fuzzy_matching, array_ranges))
        pool.close()
        pool.join()
        output_file_path = "../Excel_SpreadSheets/Fuzzy_Data_Comparison/Output_Data/{0}.csv".format(file_name)
        df_final.to_csv(output_file_path)


def fuzzy_matching(df, starting_index, ending_index):
    is_institutions = True
    # Get number of columns and column indices that we want to use from original Dataframe
    mi_name_col = 0
    mi_pos_col = 1
    mi_market_val_col = 2
    dt_name_col = 3
    dt_pos_col = 4
    dt_market_val_col = 5

    # Column index numbers for new Dataframe
    mi_name_col_2 = 0
    mi_pos_col_2 = 1
    mi_market_val_col_2 = 2
    matched_holder_col = 3
    matched_pos_col = 4
    matched_market_val_col = 5
    pos_diff_col = 6
    market_val_diff_col = 7
    mi_not_matched_col = 8
    dt_not_matched_col = 9

    # Counter to keep track of not_matched rows
    not_matched_counter = 0

    # Array to keep track of DT names that were matched
    dt_matched = [0] * df['DT Holder'].count()
    df['DT Is Matched'] = dt_matched
    df2 = pd.DataFrame(columns=['MI name', 'MI Pos', 'MI Market Val',  'DT Name', 'DT Pos', 'DT Market Val',
                                'Pos Diff (Percentage)', 'Market Val Diff (Percent)', 'MI Not Matched',
                                'DT Not Matched'], index=range(starting_index, ending_index + 1))

    # Normalize the strings in the list of companies in Q4 Desktop
    dt_name_num_col = df['DT Holder'].count()
    df_normalized_names = df['DT Holder'].copy()
    for i in range(dt_name_num_col):
        df_normalized_names.loc[i] = normalize_string(df.iloc[i, dt_name_col], is_institutions=is_institutions)

    # Start fuzzy matching
    start = timeit.default_timer()
    counter = 0
    for i in range(starting_index, ending_index):
        # Create a new row

        # Normalize string we are searching
        str1 = normalize_string(df.iloc[i, mi_name_col], is_institutions=is_institutions)
        str_options = df_normalized_names
        # Get the highest match
        match, score, index = process.extractOne(str1, str_options, scorer= fuzz.WRatio)#fuzz.token_set_ratio)
        # Consider the result a match if above threshold
        # Make changes to the appropriate columns
        if score > 87:
            df2.iat[counter, mi_name_col_2] = df.iat[i, mi_name_col]
            df2.iat[counter, mi_pos_col_2] = df.iat[i, mi_pos_col]
            df2.iat[counter, mi_market_val_col_2] = df.iat[i, mi_market_val_col]
            df2.iat[counter, matched_holder_col] = df.iat[index, dt_name_col]
            df2.iat[counter, matched_pos_col] = df.iat[index, dt_pos_col]
            df2.iat[counter, matched_market_val_col] = df.iat[index, dt_market_val_col]
            df2.iat[counter, pos_diff_col] = calc_percent_diff(df.iat[i, mi_pos_col], df.iat[index, dt_pos_col])
            df2.iat[counter, market_val_diff_col] = calc_percent_diff(df.iat[i, mi_market_val_col] * 1000000,
                                                                      df.iat[index, dt_market_val_col])
            df.iloc[index, dt_market_val_col + 1] = 1
            counter += 1
        # if not above threshold then add it to the list of companies that could not be found in Desktop
        else:
            print(df.iat[i, 0] + " |" + df.iat[index, dt_name_col] + "| ratio " + str(score) + " index: " + str(index))
            df2.iat[not_matched_counter, mi_not_matched_col] = df.iat[i, 0]
            not_matched_counter += 1

        # counter and timer to keep track of progress
        if i % 10 == 0:
            end = timeit.default_timer()
            elapsed_time = end - start
            print("Processed " + str(i) + "/" + str(ending_index) + "  Time elapsed " + str(elapsed_time) + " seconds")

    try:
        dt_counter = 0
        for i in range(len(dt_matched) - 1):
            if df.iloc[i, dt_market_val_col + 1] == 1:
                df2.iat[dt_counter, dt_not_matched_col] = df.iat[i, dt_name_col]
    except:
        print("Exception with dt not matched")

    print(df2)
    return df2


def calc_percent_diff(num1, num2):

    if num1 == num2:
        return 0
    else:
        # Get the percentage difference between two numbers. Get The absolute number
        percent = round((num1 - num2)/((num1 + num2)/2) * 100, 2)
        return percent


# Tries to normalize certain values. For example, change Corp. to Corporation, LTD. to Limited
# Removes periods since some company names have periods, others don't between the two datasources.
# e.g Telepizza Group S.A and Telepizza Group SA
def normalize_string(str1, is_institutions=False):
    str1 = str1.replace('.', '')
    str1 = str1.replace(',', '')

    if is_institutions:
        str1 = normalize_string_institutions(str1)

    # remove accents from strings
    str1 = unicodedata.normalize(u'NFKD', str1).encode('ascii', 'ignore').decode('utf8')
    str1 = re.sub(r"\bcorp\b", "Corporation", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\bltd\b", "Limited", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\bco\b", "Company", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\bcos\b", "Companies", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\bAktiengesellschaft\b", "AG", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\bKapitalanlagegesellschaft\b", "KAG", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\(publ\)", "Class B", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\(investment management\)", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\(invt mgmt\)", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\(investment portfolio\)", "", str1, flags=re.IGNORECASE)

    return str1


def normalize_string_institutions(str1):
    str1 = re.sub(r"\binvestment\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\bmanagement\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\basset\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\bof\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\bthe\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\bcapital\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\binc\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\bco\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\band\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\b&\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\binvestments\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\bwealth\b", "", str1, flags=re.IGNORECASE)
    # str1 = re.sub(r"\basset\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\b\(\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\b\)\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\bltd\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\bLLC\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\beg\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\bsa\b", "", str1, flags=re.IGNORECASE)
    str1 = re.sub(r"\bgroup\b", "", str1, flags=re.IGNORECASE)
    return str1


if __name__ == '__main__':
    main()
