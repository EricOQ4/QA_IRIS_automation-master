# README: Python Data Discrepancy Scripts
##### Author: Shardul Bansal

Python Scripts to run on CSVs, or to gather data from data tables in Desktop and push into CSVs. The excel list is gathered from SQLPro Studio. To access further queries, you can check the [TestRail test cases](https://q4web.testrail.com/index.php?/runs/view/7777&group_by=cases:section_id&group_order=asc) (provided courtesy of May Lam)

Some setup to do:
Change config.py to change the environment to target and the bearer token.
Additionally, copy the database_config_example.py to make a new file database_config.py and enter in the credentials to connect to the database.
MSSQL will require a driver to be installed on your device. https://docs.microsoft.com/en-us/sql/connect/odbc/download-odbc-driver-for-sql-server?view=sql-server-ver15. Run the database_helper.py script to figure out what driver is installed


### What the scripts do:
There's 2 types of scripts. One which, given a CSV (Factset/Cyclops Data export), call the Desktop API and create a *new* CSV with the original data collated with data from Desktop. The other kind of script compares data on different pages of Desktop, like the profile page of a fund/institution, ownership page and AI Targeting page, by calling APIs in different parts of Desktop. It then produces a CSV of all the data it checked and points out any inconsistencies. If you're running any scripts with Cyclops as the source data, remember to turn on surveillance.

**AI_Targeting_Fund.py**: Checking if data (style, turnover, eaum, aum) on AI Targeting Table matches Fund Profile Page.

**AI_Targeting_Institution.py**:  Checking if data (style, turnover, eaum, aum) on AI Targeting Table matches Institution Profile Page

**Fund_POS_MarketValue.py**: Checks _position_ and _mkValue_ in the fund's holdings table against Factset Terminal Export.

**Fund_profile_eaum_aum.py**: Compares the AUM and EquityAUM of Fund Profile page against Factset Data Feed Export.

**Fund_profile_style.py**: Compares the style of Fund_profile against Factset Data Feed export. Follows Desktop logic.

**Fund_vs_Cyclops.py**: Checks Cyclops Data Export against Fund profile data (position, style, turnover, aum, equity_aum, quality rating, AI targeting). Make sure surveillance is turned on in Q4-admin. 

**Institution_POS_MarketValue.py**: Checks _position_ and _mktValue_ in the institution's holdings table against Factset Terminal Export.

**Institution_Position_Security_Ownership.py**: Checks if position on Ownership page matches Factset Data Feed export.

**Institution_profile_aum_e-aum.py**: Compares the AUM and EquityAUM of Institution Profile page against Factset Data Feed Export.

**Institution_profile_style**: Checks if Institution Profile Style matches Data Feeds.

**Institution_vs_Cyclops.py**: Checks Cyclops Data Export against Institution profile data (position, style, turnover, aum, equity_aum, quality rating, AI targeting)

**Security_Ownership_Fund.py**: Checks if data (style, aum, equity_aum, turnover) on Security Ownership Page matches the Fund Profile Page

**Security_Ownership_Funds_calculate-EAUM.py**: Calculate the Equity AUM for Funds in the Ownership Table.

**Security_Ownership_Inst_calculate-EAUM.py**: Calculate the Equity AUM for Institutions in the Ownership Table.

**Security_Ownership_Institution.py**: Checks if data (style, aum, equity_aum, turnover) on Ownership Table matches Institution Profile Page

**Subfiler_Check.py**: Check if the institutions in the Factset Data Export are subfilers


### Motivation for the Scripts:
The Python scripts came about when the new Data Discrepancy Squad was created. The QA Team felt it was necessary to check large amounts of data at once and manual testing was not an option at the scale the changes were being made by the developers. So these scripts were created in an effort to test the major parts of the product that display data.

### How to run the scripts:
Before doing anything, you need Python installed to run the scripts. You can do that here: https://www.python.org/downloads/

1. I use the PyCharm IDE to run the scripts but you can run the python script straight from terminal. If operating on a PC, I highly recommend the PyCharm IDE (which belongs to JetBrains). You can download that here: https://www.jetbrains.com/pycharm/.
	
	a. If running straight from terminal, type in `python3` in your terminal. A prompt should pop up. Type in: 
	`import pandas as pd` . Another prompt should pop up. If it doesn't and instead an error comes up, type in `exit()` and then in the terminal window, type `pip3 install pandas`. It's also safe to assume that the requests library haven't been downloaded so after that has run, type in `pip3 install requests`. Now, after typing in python3 again in your terminal, in the prompt if you type in `import pandas as pd`, nothing should happen. The same should be true for `import requests`. 
		
	b. If using the PyCharm IDE, check out:https://www.jetbrains.com/help/pycharm/configuring-python-interpreter.html
After configuring PyCharm to use Python 3.7 (or above) as your interpreter, go to PyCharm > Preferences > Project Settings, Project Interpreter > "+" > Search for pandas > Install Package. Do the same for the requests library. You can run the scripts straight from the built-in interpreter in PyCharm.

2. If you're not using PyCharm, run the scripts by opening a new window in Terminal. Navigate to Python_Data_Discrepancy/Scripts and input: `python3 <script name> <Authorization Token>`. You can include the Authorization Token as an argument if you wish to, or you can update the script by pasting in the new Authorisation Token.  NOTE: You must be logged into Q4 Desktop develop/staging to run the script: http://develop.q4desktop.com/. Here's a screenshot of where to obtain the Authorisation Token: ![Authorisation Token Photo](Authorisation_Token.jpg?raw=true "Authorisation Token")
	For example: 
	
	> python3 DiscrepancyTest.py
	
	Or: 
	
	> python3 DiscrepancyTest.py "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfaWQiOiI1Y2QwNmFiZDI5YTgyZTAwMDQ1NGM3MDMiLCJfb3JnYW5pemF0aW9uIjp7Il9pZCI6IjVhZjQ5ZDdiMzYxODUzMDAwNDk0ZTViMyJ9LCJ1c2VyIjoic2hhcmR1bGJAcTRpbmMuY29tIiwiaWF0IjoxNTYwNDQ4Nzc5LCJleHAiOjE1NjA1MzUxNzl9.BVK7aqPxxPoCYe3hwtaHwGTnUCy2GW76XFIELGCZS-Q"
	
	Also, if running scripts that deal with data on the Ownership Page or the AI Targeting Page, update the API calls. You can update them by looking at the network calls in the specific page and pasting it in the script. Remember to place the variables like `str(page)` and `str(display_limit)` where they were orginally placed in the API.
	

3. Expected Output: After running the files, the Script should output a CSV. To find out specifically which CSV was created, follow the path specified in the python scripts.

### Things to Note before running a Script:
1. Make sure the Bearer Authorisation Token is updated. The authorisation token expires around once a day, so don't expect a script that was running yesterday to run today.

2. Update the API calls for the specific fund/institution/security. If you are running one of the scripts comparing Factset Data of the Holdings of an institution to the Institution profile page on Desktop, make sure you are calling the right API call. Some scripts might require you to update the Authorisation Token in more than one place!

3. Update the CSV file to read from. If you downloaded a new CSV and that's the file you want to read the data from, then make sure you update the code to match the path of the new CSV. I recommend not updating the path, but rather renaming and moving the file to the destination specified in the scripts.

4. Make sure the columns are laid out the same way the scripts specify it. You can also update the column names of the CSV in the code:

> data.columns = ['factset_entity_id', 'total_aum', 'equity_aum']

5. It is best to run the scripts after the data reconciliation process is complete. This normally takes place early in the morning but confirm and check with one of the developers.


### Troubleshooting

- If you are getting `--- ERROR with Institution ID: xxxxx---`, then you need to change the Authorization Token on the script. This can be done by going to an institution profile page on Desktop, filtering for 'info' on Network in the DevTools page and clicking on an API call. In the Headers section, scroll down to the 'Request Headers' sub-section and copy the Authorization token into the script right after the colon in the line '"Authorization":'. You can also pass in the token as an argument, check step 2.


- Sometimes you can get an error tokenizing data, or a `C error`. If it looks like it's related to pandas, then it means that pandas library cannot parse the data from the CSV. Open the CSV that you're trying to read from, the path of which can be determined from the line of code in the script that looks like this:

 > data = pd.read_csv("../Excel_SpreadSheets/Source_Data/xxx.csv")

Make sure the format of the CSV is exactly how the script specifies it. Often times, an error won't be raised but the data produced will be garbage if the CSV isn't laid out as specified in the script. When you're saving the script, make sure to save it as a .csv and NOT as an excel file.

- If you're receiving a JSON encoding error, chances are that when you copied the API links into the script, you didn't remove the limit that was already there, and so you'll have:
> ownership_url = "https://api-stage.q4desktop.com/api/oxford/holdings?_dc=1564518439885&appver=3.1.1&_dc=1564518439885&position=&style=&type=&page=" + str(page) + "&start=0&limit=100" + str(display_limit)

whereas what you want is:

> ownership_url = "https://api-stage.q4desktop.com/api/oxford/holdings?_dc=1564518439885&appver=3.1.1&_dc=1564518439885&position=&style=&type=&page=" + str(page) + "&start=0&limit=" + str(display_limit)

So the API call instead perceives there's a limit of 100100, returning a JSON encoding erorr because the GET request can't parse that much data in one call.


### Improvements
A few thoughts on improving the current python script: 
1. We can implement multi-threading in python to reduce the run time of the script (calling APIs in parallel) 
2. To make the python script more accessible to the QA team, we can implement a flask server to create a simple endpoint that returns the result in json format instead of another csv file. In this case, we don’t have to worry  about hardcoding the csv file name 
3. Reformat the code and have a utility file so that in the future, it will be easy to make updates.

### Script Extensions

As mentioned above, there's two kinds of scripts. One which tackles discrepancies from the source and the other which checks consistency in data in different areas in Desktop. What Q4 needs is an end-to-end data checking solution. This would involve creating a master script to gather data from the source, perform the correct logic and then check the consistency of that value throughout Desktop. 

The end-to-end solution that's been suggested by Tanim Wahid, Taylor Johnson and several others is one which implements the queries within the Python Scripts so formatting of the CSVs isn't required to run these scripts. This would reduce the pre-requisite work tremendously and can significantly improve the data checking framework that's put in place. 

- If you are running into errors early on in the script, and you run `print(request.text)` and see a message about the `jwt expired` then you need to update the Authorisation Token. The JWT is the JSON Web Token and needs to be updated about once a day

