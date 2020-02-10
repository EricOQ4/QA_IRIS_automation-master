package pageobjects.api.historical;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static specs.ApiAbstractSpec.propAPI;

/**
 * Created by easong on 3/23/17.
 */
public class HistoricalStockQuote {

    private int failurecount;
    private int compareDataCounter = 0;
    private String earliestDate = "";
    private String q4DatabaseRequestDate;
    private double QuandlClosePrice;
    private double Q4Price = 0;
    private ArrayList<String> Q4prices = new ArrayList<String>();
    private ArrayList<String> Q4dates = new ArrayList<String>();
    private int numberOfDates = 290;
    private String ticker;
    private String QuandlTicker;
    private String exchange;
    private String security_name;
    private String securityId;
    private boolean individualstockresult = true;
    private boolean requestSuccess;
    private int securityCounter;
    private String Q4Currency;
    private String Q4Date;
    private String QuandlDate;

    private String host;
    private String app_ver;
    private String access_token;
    private String connection;
    private String user_agent;
    private boolean result = false;
    private boolean dataexists = true;
    private HttpClient client;
    private final String DEVELOP_ENV = "Preprod_Env";
    private final String SECURITIES = "Securities";
    private final String PROTOCOL = "https://";
    private final String HISTORICAL = "historical";
    private org.json.JSONArray securityArray = new org.json.JSONArray();
    ArrayList<String> accurateCompanies = new ArrayList<String>();

    private QuandlDataset stockInformation;
    private HttpResponse response;
    private DateFormat quandlFormat;
    private JSONObject individualdata;

    private List<String> zeroDataList = new ArrayList<>();
    private List<String> inaccurateDataList = new ArrayList<>();
    private List<String> missingDataList = new ArrayList<>();
    private List<String> miscellaneousErrorList = new ArrayList<>();

    // Purpose: Reads the globalindividualdata JSON file and runs a test for each Stock in the globalindividualdata file in the given environment.
    // Environment can be one of PREPROD_ENV, STAGING_ENV, or DEVELOP_ENV
    public HistoricalStockQuote(JSONObject globalindividualdata, String environment) throws IOException {

        individualdata = new JSONObject(globalindividualdata);

        // setup all environment variables. JSON file locations, Q4 API Permissions

        // creating paths to the JSON files
        String sPathToFileAuth, sDataFileAuthJson, sPathToFileHist, sDataFileHistJson;

        JSONParser parser = new JSONParser();
        JSONObject jsonEnvData;
        JSONObject jsonEnv = new JSONObject();
        org.json.simple.JSONArray jsonHistData;

        //To hide warnings logs from execution console.
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);

        sPathToFileAuth = System.getProperty("user.dir") + propAPI.getProperty("dataPath_Auth");
        sDataFileAuthJson = propAPI.getProperty("jsonData_Auth");
        sPathToFileHist = System.getProperty("user.dir") + propAPI.getProperty("dataPath_Hist");
        sDataFileHistJson = propAPI.getProperty("jsonData_Hist");
        client = HttpClientBuilder.create().build();

        try {
            // reading in environment variables
            FileReader readAuthFile = new FileReader(sPathToFileAuth + sDataFileAuthJson);
            jsonEnvData = (org.json.simple.JSONObject) parser.parse(readAuthFile);
            jsonEnv = (JSONObject) jsonEnvData.get(environment);
            // reading in stock data
            FileReader readHistFile = new FileReader(sPathToFileHist + sDataFileHistJson);
            // creating an JSONArray of all stocks
            jsonHistData = (org.json.simple.JSONArray) parser.parse(readHistFile);
            securityArray = new JSONArray(jsonHistData.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        host = jsonEnv.get("host").toString();
        app_ver = jsonEnv.get("app_ver").toString();
        access_token = jsonEnv.get("access_token").toString();
        user_agent = jsonEnv.get("user_agent").toString();
        connection = jsonEnv.get("connection").toString();
    }

    // Purpose: Ensures that Q4 data exists before continuing, and returns an error message in the console if it doesn't.
    public void dataValidation() {

        try {
            // this boolean keep track of the data health for individual stocks
            individualstockresult = true;
            requestSuccess = true;

            security_name = individualdata.get("company_name").toString();
            ticker = individualdata.get("symbol").toString();
            exchange = individualdata.get("exchange").toString();
            securityId = individualdata.get("_id").toString();
            QuandlTicker = ticker;

            // Q4 retains 300 days of stock data from the current date
            // To get all 300 days of data from Quandl to compare against Q4DB, we need the earliest date value in our DB to create a "from" parameter for a Quandl Request
            sendStockQuoteRequestToQ4DB();

            // Obtaining earliest date in Q4 DB
            // ensuring request was successful - if code == 200 it means success
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();

                String responseBody = EntityUtils.toString(entity);
                org.json.JSONObject jsonResponse = new org.json.JSONObject(responseBody);
                // Creating array of stock data where each day is the next index
                org.json.JSONArray historicalArray = jsonResponse.getJSONArray(HISTORICAL);
                // Checking if data exists
                if (jsonResponse.getJSONArray("historical").length() != 0) {
                    // read through Historical Stock Data for final entry
                    if (numberOfDates > historicalArray.length()){
                        numberOfDates = historicalArray.length();
                    }
                    for (int j = 0; j < numberOfDates; j++) {
                        org.json.JSONObject jsonHistItem = historicalArray.getJSONObject(j);
                        // recording the date property, data is stored so the first element of the array stores the stock data for today, so earliestDate will eventually get the most past day
                        earliestDate = jsonHistItem.get("Date").toString();
                    }
                   // System.out.println(earliestDate);
                    // Now we can compare the two close prices
                    compareQ4AndQuandlClosePrices();

                } else {
                    // no data was found in the request, storing error in list
                    miscellaneousErrorList.add("Q4 Returned no historical stock data for " + ticker + " : " + exchange + "     security ID: " + securityId);
                    individualstockresult = false;
                }

            } else {
                // initial request to database was not successful, storing error in list
                miscellaneousErrorList.add("Response code from Q4 Database was not 200, it was " + response.getStatusLine().getStatusCode());
                individualstockresult = false;
            }

        } catch (IOException e) {
        }
    }
    // Purpose: Makes a call to the Q4 api to get all the possible historical data. This lets us know the maximum number of dates we are able to call from the Quandl Api.
    public void sendStockQuoteRequestToQ4DB() {

        try {


            // API Request format: {{url}}/api/stock/historical?appver={{appver}}&securityID={{securityId}}
            String urlHistQuery = PROTOCOL + host + "/api/stock/historical?appver=" + app_ver + "&securityId=" + securityId;
            // System.out.println("Q4 query = " + urlHistQuery);

            HttpGet get = new HttpGet(urlHistQuery);
            // Setting up authentication headers
            get.setHeader("User-Agent", user_agent);
            get.setHeader("Connection", connection);
            get.setHeader("Authorization", "Bearer " + access_token);

            response = client.execute(get);
        } catch (IOException e) {
        }

    }



    public boolean checkrequestfailure(int failcount, String ticker, String exchange, String securityId) {

        // loop will fail after 10 runs
        if (failcount == 10) {
            // Storing error
            miscellaneousErrorList.add("Quandl encountered an error while requesting for " + ticker + " : " + exchange + "     securtiyID = " + securityId);
            // stock check failed
            individualstockresult = false;
            return false;
        } else {
            // request will keep going
            return true;
        }
    }

    // Purpose: Format the dates so that requests to the Quandl Api can be made and then format the dates again so a request to the Q4 api can be made and then the data is compared.
    void compareQ4AndQuandlClosePrices(){
        // Changing the format of the date so it works in the Quandl api
        if (ticker.contains("."))
        {
            QuandlTicker = ticker.replace(".", "_");
        }
        quandlFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date earliestDateInQuandl = quandlFormat.parse(earliestDate);
            SimpleDateFormat realQuandlFormat = new SimpleDateFormat("yyyy-MM-dd");
          String earliestDateInQuandlFormat = realQuandlFormat.format(earliestDateInQuandl);

            // sending first request to Quandl to record number of days of data Quandl has from the earliestDate in the Q4 database
            // In case a request to Quandl fails, this loop sends the same request back 10 times before giving up and allowing the error to end the test for the current ticker
            for (failurecount = 0; requestSuccess; failurecount++) {
                try {
                    // EOD exchange stands for END OF DAY, so it will give you all the end of day info for that stock
                    stockInformation = QuandlConnectToApi.getDatasetFromDate(QuandlTicker, "EOD", earliestDateInQuandlFormat);
                    break;
                } catch (Exception e) {
                    requestSuccess = checkrequestfailure(failurecount, ticker, exchange, securityId);
                }
            }
        } catch (java.text.ParseException e) {
            System.out.println ("Q4 date couldn't be parsed");
        }
        if (stockInformation.getClosingPriceDates().size() != 0) {

            // Gets the most recent date that has stock data from the QuandlDataset StockInformation
            String quandlDate = stockInformation.getClosingPriceDates().get(0);
            // Change date so we can retrieve the matching data from the q4 database for comparison
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date quandlDateObject = formatter.parse(quandlDate);
                SimpleDateFormat formatQuandlToQ4 = new SimpleDateFormat("MM/dd/yyyy");
                q4DatabaseRequestDate = formatQuandlToQ4.format(quandlDateObject);
                //System.out.println(q4DatabaseRequestDate);
            } catch (java.text.ParseException e) {
                System.out.println("Could not parse Quandl Date");
            }
            collectQ4Data();

            //break if previous request failed
            if (!requestSuccess) {
                return;
            }

            // This if statement ensures that if either Q4 or Quandl has more dates than the other, the smallest amount of dates is used as the number of dates to avoid going out of bounds of either array.
            if (stockInformation.getClosingPriceDates().size() >= Q4dates.size()){
                numberOfDates = Q4dates.size();
            }
            else
            {
                numberOfDates = stockInformation.getClosingPriceDates().size();
            }

        // loop to compare the data
        for (int i = 0; i < numberOfDates; i++) {
            // Store the closing price of a specific date for comparison
            Q4Price = Double.parseDouble(Q4prices.get(i));
            Q4Date = Q4dates.get(i);
            QuandlDate = stockInformation.getClosingPriceDates().get(i);
            QuandlClosePrice = Double.parseDouble(stockInformation.getClosingPrices().get(i));

            try {
                Date currentDateInQuandl = quandlFormat.parse(Q4Date);
                SimpleDateFormat realQuandlFormat = new SimpleDateFormat("yyyy-MM-dd");
                Q4Date = realQuandlFormat.format(currentDateInQuandl);
            }
            catch (java.text.ParseException e){
                System.out.println("Could not parse Quandl Date before comparing");
            }

            if (Q4Date.equalsIgnoreCase(QuandlDate)){
                compareData();
                compareDataCounter++;
            }
            else {
                System.out.println("Dates didn't match. Q4 date was " + Q4Date + ". While Quandl date was " + QuandlDate + ".");
            }

            }
        }
        else
        {
            System.out.println("No Quandl Data for "+ ticker);
            miscellaneousErrorList.add("Quandl Data not found for stock");
        }
    }

    // Purpose: Compares the Q4 stock price with the Quandl stock price
    private boolean compareData() {
        // margin of error between the 2 stock prices
        result = (Math.abs(QuandlClosePrice - Q4Price) < 0.01);

        // accounting for case of 0 days
        if (Q4Price == 0.0) {
            zeroDataList.add("0 value on " + Q4Date + " while Quandl's price is " + QuandlClosePrice);
        }

        // accounting for generally inaccurate days
        if (Q4Price != 0.0) {
            inaccurateDataList.add("Inaccurate: on " + Q4Date + " Q4 price is: " + Q4Price + " while Quandl's price is " + QuandlClosePrice);
        }

        if (!result) {
            // this stock was found to have at least one error
            individualstockresult = false;
            System.out.println(ticker + ": " + "Quandl price: " + QuandlClosePrice + " Q4 Price: " + Q4Price);
            System.out.println("Date:" + QuandlDate);

            // divides each failure
            System.out.println("------");
        }
        /*else
        {
            System.out.println("Success");
        }*/

        return result;

    }
    // Purpose: Makes a call to the Q4api and stores the closing price in an ArrayList.
    private void collectQ4Data() {

        try {
            // Collecting Q4Data for that date
            String q4DataBaseIndividualRequest = PROTOCOL + host + "/api/stock/historical?appver=" + app_ver + "&securityId=" + securityId + "&startDate=" + earliestDate + "&endDate=" + q4DatabaseRequestDate;
            // Setting up new requests
            HttpGet getIndividual = new HttpGet((q4DataBaseIndividualRequest));
            // Setting up authentication headers for individual get query
            getIndividual.setHeader("User-Agent", user_agent);
            getIndividual.setHeader("Connection", connection);
            getIndividual.setHeader("Authorization", "Bearer " + access_token);
            HttpResponse individualResponse = client.execute(getIndividual);

            // if statuscode == 200 that basically means it's a success and we create an HttpEntity
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity individualEntity = individualResponse.getEntity();
                String individualResponseBody = EntityUtils.toString(individualEntity);
                // Get a JSONObject from the Q4 database
                org.json.JSONObject individualJSONResponse = new org.json.JSONObject(individualResponseBody);
                // Get a JSONArray from the JSONObject above
                org.json.JSONArray individualJSONArray = individualJSONResponse.getJSONArray(HISTORICAL);

                if (individualJSONResponse.getJSONArray("historical").length() != 0) {
                    dataexists = true;
                    for (int z = 0; z < individualJSONArray.length(); z++) {
                        org.json.JSONObject jsonHistItem = individualJSONArray.getJSONObject(z);
                        // print statement to see all Q4 prices
                        // System.out.println("Q4 Desktop Price : " + jsonHistItem.get("Last").toString() + " on " + jsonHistItem.get("Date").toString()  + " on thread " + Thread.currentThread().getId());
                        Q4prices.add(jsonHistItem.get("Last").toString());
                        Q4dates.add(jsonHistItem.get("Date").toString());
                        //Q4Currency = jsonHistItem.get("Currency").toString();
                    }
                }
                else
                {
                    System.out.println("No data");
                }
            }
        } catch (IOException e) {
        }
    }

    public boolean stockDataIsPerfect() {

        return individualstockresult;
    }

    public boolean stockDataReturnZeros() {

        if (zeroDataList.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<String> getZeroDataList() {

        return zeroDataList;
    }

    public boolean stockDataIsInaccurate() {
        if (inaccurateDataList.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<String> getInaccurateStockDataList() {

        return inaccurateDataList;
    }

    public boolean stockDataIsMissing() {
        if (missingDataList.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<String> getMissingDataList() {

        return missingDataList;
    }

    public boolean miscellaneousErrorsExist() {
        if (miscellaneousErrorList.size() != 0) {
            return true;
        } else {
            return false;
        }
    }


    public List<String> getMiscellaneousErrorList() {

        return miscellaneousErrorList;
    }
}
