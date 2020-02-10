package specs.api.historical;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.api.historical.Historical;
import pageobjects.api.historical.HistoricalStockQuote;
import pageobjects.api.login.Auth;
import specs.ApiAbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-03-08.
 */

public class CheckHistorical extends ApiAbstractSpec {

    private static Historical historical;
    private static Auth auth;
    private static String sPathToFileHist, sDataFileHistJson;
    private static JSONParser parser;
    private final String STOCKDATA = "getData";
    private static final String DEVELOP_ENV = "Develop_Env";
    private static final String PREPROD_ENV = "Preprod_Env";
    private static int totalNumberOfTests;
    private static int testIteration = 0;

    ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    public void setUp() throws IOException {
        auth = new Auth();

        // gets access token for the desired environment
        Assert.assertTrue(new Auth().getAccessToken(PREPROD_ENV), "Access Token didn't receive");

        sPathToFileHist = System.getProperty("user.dir") + propAPI.getProperty("dataPath_Hist");
        sDataFileHistJson = propAPI.getProperty("jsonData_Hist");
        parser = new JSONParser();

        extent = ExtentManager.GetExtent();
        System.setProperty("Q4 web driver", "Not quite sure what this is");

    }


    @Test(dataProvider = STOCKDATA, threadPoolSize = 20)
    public void CheckQ4DesktopAuth(JSONObject data) throws IOException {

        ExtentTest test = extent.createTest("symbol: " + data.get("symbol").toString() + " exchange: " + data.get("exchange").toString());

        JSONObject individualdata = new JSONObject(data);

        // 2nd parameter specifies which environment to run in
        HistoricalStockQuote historicalStockQuote = new HistoricalStockQuote(individualdata, PREPROD_ENV );

        // validating data
        historicalStockQuote.dataValidation();

        // checking data with the ExtentReport framework

        // checking if stock data is overall accurate
        if (historicalStockQuote.stockDataIsPerfect() ) {
            test.log(Status.PASS, "Stock data is accurate");
        } else {

            // checking if stock data has zero values
            if (historicalStockQuote.stockDataReturnZeros()) {

                for (String errorinstance : historicalStockQuote.getZeroDataList()) {
                    test.log(Status.FAIL, errorinstance);
                }
            }

            // checking if stock data has inaccurate data
            if (historicalStockQuote.stockDataIsInaccurate()) {

                for (String errorinstance : historicalStockQuote.getInaccurateStockDataList()) {
                    test.log(Status.FAIL, errorinstance);
                }
            }

            // checking if stock data is missing
            if (historicalStockQuote.stockDataIsMissing()) {

                for (String errorinstance : historicalStockQuote.getMissingDataList()) {
                    test.log(Status.FAIL, errorinstance);
                }
            }

            // checking for other related request errors
            if (historicalStockQuote.miscellaneousErrorsExist()) {

                for (String errorinstance : historicalStockQuote.getMiscellaneousErrorList()) {
                    test.log(Status.FAIL, errorinstance);
                }
            }
        }
    }

    @DataProvider (parallel = true)
    public Object[][] getData() {

        try {
            JSONArray stockDataArray = (JSONArray) parser.parse(new FileReader(sPathToFileHist + sDataFileHistJson));

            totalNumberOfTests = stockDataArray.size();

            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < stockDataArray.size(); i++) {
                    zoom.add(stockDataArray.get(i));
            }

            Object[][] stockData = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                stockData[i][0] = zoom.get(i);
            }
            return stockData;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @AfterClass
    public void teardown()
    {
        extent.flush();
    }
}
