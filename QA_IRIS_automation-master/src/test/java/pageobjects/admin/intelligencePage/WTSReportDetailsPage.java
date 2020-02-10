package pageobjects.admin.intelligencePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageobjects.AbstractPageObject;
import pageobjects.api.historical.QuandlConnectToApi;
import pageobjects.api.historical.QuandlDataset;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by patrickp on 2016-11-09.
 */
public class WTSReportDetailsPage extends AbstractPageObject {
    private final By reportHeader = By.cssSelector(".page-header .page-title .details");
    private final By closingPrice = By.cssSelector(".crowded > div:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(2)");
    private final By deleteButton = By.cssSelector(".remove");
    private final By approveButton = By.className("button-dark-grey");
    private final By downloadButton = By.className("download");

    // stock trade summary table
    private final By weekClose = By.cssSelector("q4-table-trade-summary td:nth-child(2)");
    private final By weekChange = By.cssSelector("q4-table-trade-summary td:nth-child(3)");
    private final By weekChangeP = By.cssSelector("q4-table-trade-summary td:nth-child(4)");
    private final By weekHigh = By.cssSelector("q4-table-trade-summary td:nth-child(5)");
    private final By weekLow = By.cssSelector("q4-table-trade-summary td:nth-child(6)");
    private final By weekVolume = By.cssSelector("q4-table-trade-summary td:nth-child(7)");
    private final By averageWeekVolume = By.cssSelector("q4-table-trade-summary td:nth-child(8)");
    private final By volumeVariance = By.cssSelector("q4-table-trade-summary td:nth-child(9)");
    private final By qtdChange = By.cssSelector("q4-table-trade-summary td:nth-child(10)");
    private final By ytdChange = By.cssSelector("q4-table-trade-summary td:nth-child(11)");

    // indices summary table
    private final By indicesClose = By.cssSelector("q4-table-indices td:nth-child(2)");
    private final By indicesChange = By.cssSelector("q4-table-indices td:nth-child(3)");
    private final By indicesChangeP = By.cssSelector("q4-table-indices td:nth-child(4)");
    private final By indicesQTDChange = By.cssSelector("q4-table-indices td:nth-child(5)");
    private final By indicesYTDChange = By.cssSelector("q4-table-indices td:nth-child(6)");

    // Q4 projected volatility table

    // daily performance chart
    private final By dailyPerformanceHoverText = By.cssSelector("q4-chart-daily-changes .highcharts-tooltip text");
    private final By dailyPerformanceStockBars = By.cssSelector("q4-chart-daily-changes .highcharts-series-0 rect");
    private final By dailyPerformancePeerBars = By.cssSelector("q4-chart-daily-changes .highcharts-series-1 rect");
    private final By dailyPerformanceSP500Bars = By.cssSelector("q4-chart-daily-changes .highcharts-series-2 rect");

    // YTD change chart
    private final By ytdChangeBars = By.cssSelector("q4-chart-ytd-change .highcharts-point");
    private final By ytdChangeAxisText = By.cssSelector("q4-chart-ytd-change .highcharts-xaxis-labels tspan");
    private final By ytdChangeHoverText = By.cssSelector("q4-chart-ytd-change .highcharts-tooltip text");

    Actions actions = new Actions(driver);

    public WTSReportDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getReportHeader() {
        waitForLoadingScreen();
        return findElement(reportHeader).getText();
    }

    public float getClosePrice() {

        return Float.parseFloat(findElement(closingPrice).getText());
    }

    public float getComparisonClosePrice(String company) throws IOException {
        // Method to get today's date
        Calendar cal = Calendar.getInstance();
        // This ensures that if today is friday but it hasn't passed 5:30pm yet, it still takes last friday's closing price.
        if (((cal.get(Calendar.HOUR) < 17) || (cal.get(Calendar.HOUR) == 17) && (cal.get(Calendar.MINUTE) < 30)) && (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)){
            cal.add(Calendar.DATE, -7);
        }
        else {
            // Go back until the day is friday.
            while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                cal.add(Calendar.DATE, -1);
            }
        }
        Date today = cal.getTime();

        // Format the date so QuandlAPI can read it
        DateFormat todaysDate = new SimpleDateFormat("yyyy-MM-dd");
        String inputDate = todaysDate.format(today);
        System.out.println(inputDate);
        //Creates a QuandlDataset that contains the close price from a specific date
        QuandlDataset stock = QuandlConnectToApi.getDatasetBetweenDates(company, "EOD", inputDate, inputDate);
        String lastClosePrice = stock.getClosingPrices().get(0);

        return Float.parseFloat(lastClosePrice);
    }

    public IntelligencePage deleteReport() {
        waitForLoadingScreen();
        findElement(deleteButton).click();

        return new IntelligencePage(getDriver());
    }

    public WTSReportDetailsPage approveReport(){
        waitForLoadingScreen();
        findElement(approveButton).click();
        return this;
    }

    public boolean reportIsApproved(){
        waitForLoadingScreen();
        return downloadButtonIsPresent();
    }

    public boolean downloadButtonIsPresent(){
        driver.navigate().refresh();
        return doesElementExist(downloadButton) && findElement(downloadButton).isDisplayed();
    }


    /** reportDataIsValidUsingQuandl validates Q4's trade summary report for a list of symbols by comparing the data with data taken from Quandl API's
     *  returns true if all of the data is valid and false otherwise
     *  Requires: symbols must be a valid list of symbols. If testing a different number of symbols, ensure that this code is correctly modified to support that number.
     *            Some symbols require their own specific API request hence why changing the number of symbols requires large modifications.
     */
    public boolean reportDataIsValidUsingQuandl(String[] symbols) {
        boolean allValid = true;
        DateFormat quandlFormat = new SimpleDateFormat("yyyy-MM-dd");

        ZonedDateTime lastFriday = ZonedDateTime.now().with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)); //most-recent date that report should be based off of (may be incorrect if last friday is not a trading day)
        String lastFridayString = quandlFormat.format(Date.from(lastFriday.toInstant()));

        ZonedDateTime thirteenWeeksEarlier = lastFriday.minusDays(95);
        String thirteenWeeksEarlierString = quandlFormat.format(Date.from(thirteenWeeksEarlier.toInstant()));

        ZonedDateTime endOfLastQuarter;
        ZonedDateTime endOfLastYear = lastFriday.minusYears(1).with(TemporalAdjusters.lastDayOfYear());
        String endOfLastYearString = quandlFormat.format(Date.from(endOfLastYear.toInstant()));

        if (lastFriday.getMonthValue() > 3) {
            endOfLastQuarter = lastFriday.withMonth((lastFriday.getMonthValue() - 1) / 3 * 3).with(TemporalAdjusters.lastDayOfMonth());
        } else {
            endOfLastQuarter = endOfLastYear;
        }

        ZonedDateTime endOfLastQuarterLastWeek = endOfLastQuarter.minusDays(7);
        String endOfLastQuarterLastWeekString = quandlFormat.format(Date.from(endOfLastQuarterLastWeek.toInstant()));

        Date.from(endOfLastQuarter.toInstant());
        String endOfLastQuarterString = quandlFormat.format(Date.from(endOfLastQuarter.toInstant()));

        ZonedDateTime endOfLastYearLastWeek = endOfLastYear.minusDays(7);
        String endOfLastYearStringLastWeek = quandlFormat.format(Date.from(endOfLastYearLastWeek.toInstant()));

        ZonedDateTime lastFridayLastWeek = lastFriday.minusDays(10/*7*/);
        String lastFridayLastString = quandlFormat.format(Date.from(lastFridayLastWeek.toInstant()));

        // determining and calculating "Stock Trade Summary" values (using data from Quandl)
        ArrayList<QuandlDataset> companyStocksLast13Weeks = new ArrayList<>();
        ArrayList<QuandlDataset> companyStocksEndOfLastQuarter = new ArrayList<>();
        ArrayList<QuandlDataset> companyStocksEndOfLastYear = new ArrayList<>();
        double[] expectedClose = new double[symbols.length];
        double[] previousClose = new double[symbols.length];
        double[] expectedWeekChange = new double[symbols.length];
        double[] expectedWeekChangeP = new double[symbols.length];
        double expectedWeekChangePAverage = 0;
        double[] expectedHigh = new double[symbols.length];
        double[] expectedLow = new double[symbols.length];
        long[] expectedVolume = new long[symbols.length];
        long[] expectedVolumeWeekAverage = new long[symbols.length];
        double[] expectedVolumeVariance = new double[symbols.length];
        double[] lastQuarterClose = new double[symbols.length];
        double[] lastYearClose = new double[symbols.length];
        double[] expectedQuarterToDateChange = new double[symbols.length];
        double expectedQuarterToDateChangeAverage = 0;
        double[] expectedYearToDateChange = new double[symbols.length];
        double expectedYearToDateChangeAverage = 0;

        for (int stock = 0; stock < symbols.length; stock++) {
            expectedHigh[stock] = 0;
            expectedLow[stock] = Double.POSITIVE_INFINITY;
            expectedVolume[stock] = 0;
            expectedVolumeWeekAverage[stock] = 0;

        }
        // fetching stock data from Quandl


        for (int stock = 0; stock < symbols.length; stock++) {
            companyStocksLast13Weeks.add(QuandlConnectToApi.getDatasetBetweenDates(symbols[stock], "EOD", thirteenWeeksEarlierString, lastFridayString));
            companyStocksEndOfLastQuarter.add(QuandlConnectToApi.getDatasetBetweenDates(symbols[stock], "EOD", endOfLastQuarterLastWeekString, endOfLastQuarterString));
            companyStocksEndOfLastYear.add(QuandlConnectToApi.getDatasetBetweenDates(symbols[stock], "EOD", endOfLastYearStringLastWeek, endOfLastYearString));
        }

        // calculating values for each stock
        for (int stock = 0; stock < symbols.length; stock++) {
            for (int day = 0; day < 5; day++) {
                if (Double.parseDouble(companyStocksLast13Weeks.get(stock).getHighPrices().get(day)) > expectedHigh[stock]) {
                    expectedHigh[stock] = Double.parseDouble(companyStocksLast13Weeks.get(stock).getHighPrices().get(day));
                }
                if (Double.parseDouble(companyStocksLast13Weeks.get(stock).getLowPrices().get(day)) < expectedLow[stock]) {
                    expectedLow[stock] = Double.parseDouble(companyStocksLast13Weeks.get(stock).getLowPrices().get(day));
                }
                expectedVolume[stock] += Double.parseDouble(companyStocksLast13Weeks.get(stock).getVolume().get(day));

                expectedClose[stock] = Double.parseDouble(companyStocksLast13Weeks.get(stock).getClosingPrices().get(0)); //last friday's closing price
                previousClose[stock] = Double.parseDouble(companyStocksLast13Weeks.get(stock).getClosingPrices().get(5)); //friday before's closing price
                expectedWeekChange[stock] = expectedClose[stock] - previousClose[stock];
                expectedWeekChangeP[stock] = 100 * expectedWeekChange[stock] / previousClose[stock];
            }
            // calculating average weekly volume over the last 13 weeks


            for (int day = 5; day < companyStocksLast13Weeks.get(stock).getSize(); day++) {
                expectedVolumeWeekAverage[stock] += Double.parseDouble(companyStocksLast13Weeks.get(stock).getVolume().get(day));
            }
            expectedVolumeWeekAverage[stock] /= 13;
            expectedVolumeVariance[stock] = 100 * (expectedVolume[stock] - expectedVolumeWeekAverage[stock]) / expectedVolumeWeekAverage[stock];
            // determining end-of-quarter and end-of-year prices
            lastQuarterClose[stock] = Double.parseDouble(companyStocksEndOfLastQuarter.get(stock).getClosingPrices().get(0));
            lastYearClose[stock] = Double.parseDouble(companyStocksEndOfLastYear.get(stock).getClosingPrices().get(0));
            expectedQuarterToDateChange[stock] = 100 * (expectedClose[stock] - lastQuarterClose[stock]) / lastQuarterClose[stock];
            expectedYearToDateChange[stock] = 100 * (expectedClose[stock] - lastYearClose[stock]) / lastYearClose[stock];

        }
        // calculating peer average values
        for (int stock = 1; stock < symbols.length; stock++) {
            expectedWeekChangePAverage += expectedWeekChangeP[stock];
            expectedQuarterToDateChangeAverage += expectedQuarterToDateChange[stock];
            expectedYearToDateChangeAverage += expectedYearToDateChange[stock];
        }
        expectedWeekChangePAverage /= symbols.length - 1;
        expectedQuarterToDateChangeAverage /= symbols.length - 1;
        expectedYearToDateChangeAverage /= symbols.length - 1;

        // determining and calculating "Indices" values (using data from Yahoo)
        String[] indices = {"COMP", "COMP", "CME_SP1", "USA10Y", "GOLD", "ICE_T1"};//could not find symbols in Yahoo for gold and oil that could provide historical data
        ArrayList<QuandlDataset> indicesLastWeek = new ArrayList<>();//first index is for each index symbol, second index is for each day of data gathered (starting from most recent)
        ArrayList<QuandlDataset> indicesEndOfLastQuarter = new ArrayList<>();
        ArrayList<QuandlDataset> indicesEndOfLastYear = new ArrayList<>();
        double[] indicesExpectedClose = new double[indices.length];
        double[] indicesPreviousClose = new double[indices.length];
        double[] indicesExpectedWeekChange = new double[indices.length];
        double[] indicesExpectedWeekChangeP = new double[indices.length];
        double[] indicesLastQuarterClose = new double[indices.length];
        double[] indicesLastYearClose = new double[indices.length];
        double[] indicesExpectedQuarterToDateChange = new double[indices.length];
        double[] indicesExpectedYearToDateChange = new double[indices.length];

        //TODO Find api for Dow Jones and S&P 500
        // For Dow Jones Indsutrial Average
        indicesLastWeek.add(QuandlConnectToApi.getDatasetBetweenDates(indices[0], "NASDAQOMX", lastFridayLastString, lastFridayString));
        indicesEndOfLastQuarter.add(QuandlConnectToApi.getDatasetBetweenDates(indices[0], "NASDAQOMX", endOfLastQuarterLastWeekString, endOfLastQuarterString));
        indicesEndOfLastYear.add(QuandlConnectToApi.getDatasetBetweenDates(indices[0], "NASDAQOMX", endOfLastYearStringLastWeek, endOfLastYearString));

        // For NASDAQ Composite
        indicesLastWeek.add(QuandlConnectToApi.getDatasetBetweenDates(indices[1], "NASDAQOMX", lastFridayLastString, lastFridayString));
        indicesEndOfLastQuarter.add(QuandlConnectToApi.getDatasetBetweenDates(indices[1], "NASDAQOMX", endOfLastQuarterLastWeekString, endOfLastQuarterString));
        indicesEndOfLastYear.add(QuandlConnectToApi.getDatasetBetweenDates(indices[1], "NASDAQOMX", endOfLastYearStringLastWeek, endOfLastYearString));

        // For S&P 500
        indicesLastWeek.add(QuandlConnectToApi.getDatasetBetweenDates(indices[2], "CHRIS", lastFridayLastString, lastFridayString));
        indicesEndOfLastQuarter.add(QuandlConnectToApi.getDatasetBetweenDates(indices[2], "CHRIS", endOfLastQuarterLastWeekString, endOfLastQuarterString));
        indicesEndOfLastYear.add(QuandlConnectToApi.getDatasetBetweenDates(indices[2], "CHRIS", endOfLastYearStringLastWeek, endOfLastYearString));

        // For 10 Year Bond
        indicesLastWeek.add(QuandlConnectToApi.getDatasetBetweenDates(indices[3], "YC", lastFridayLastString, lastFridayString));
        indicesEndOfLastQuarter.add(QuandlConnectToApi.getDatasetBetweenDates(indices[3], "YC", endOfLastQuarterLastWeekString, endOfLastQuarterString));
        indicesEndOfLastYear.add(QuandlConnectToApi.getDatasetBetweenDates(indices[3], "YC", endOfLastYearStringLastWeek, endOfLastYearString));

        // For Gold
        indicesLastWeek.add(QuandlConnectToApi.getDatasetBetweenDates(indices[4], "LBMA", lastFridayLastString, lastFridayString));
        indicesEndOfLastQuarter.add(QuandlConnectToApi.getDatasetBetweenDates(indices[4], "LBMA", endOfLastQuarterLastWeekString, endOfLastQuarterString));
        indicesEndOfLastYear.add(QuandlConnectToApi.getDatasetBetweenDates(indices[4], "LBMA", endOfLastYearStringLastWeek, endOfLastYearString));

        // For Crude Oil
        indicesLastWeek.add(QuandlConnectToApi.getDatasetBetweenDates(indices[5], "CHRIS", lastFridayLastString, lastFridayString));
        indicesEndOfLastQuarter.add(QuandlConnectToApi.getDatasetBetweenDates(indices[5], "CHRIS", endOfLastQuarterLastWeekString, endOfLastQuarterString));
        indicesEndOfLastYear.add(QuandlConnectToApi.getDatasetBetweenDates(indices[5], "CHRIS", endOfLastYearStringLastWeek, endOfLastYearString));


        // For Dow Jones Industrial Average
        indicesExpectedClose[0] = Double.parseDouble(indicesLastWeek.get(0).getClosingPrices().get(0));//last friday's closing price
        indicesPreviousClose[0] = Double.parseDouble(indicesLastWeek.get(0).getClosingPrices().get(5));//friday before's closing price
        indicesExpectedWeekChange[0] = indicesExpectedClose[0] - indicesPreviousClose[0];
        indicesExpectedWeekChangeP[0] = 100 * indicesExpectedWeekChange[0] / indicesPreviousClose[0];
        indicesLastQuarterClose[0] = Double.parseDouble(indicesEndOfLastYear.get(0).getClosingPrices().get(0));
        indicesExpectedQuarterToDateChange[0] = 100 * (indicesExpectedClose[0] - indicesLastQuarterClose[0]) / indicesLastQuarterClose[0];
        indicesExpectedYearToDateChange[0] = 100 * (indicesExpectedClose[0] - indicesLastYearClose[0]) / indicesLastYearClose[0];

        // For NASDAQ Composite
        //Keep in mind that quandl stores their index data differently so refer to this documentation for clarity.
        // getOpen returns Closing Prices for  NASDAQ Composite
        // getClosingPrices returns Total Market Value for  NASDAQ Composite
        // getVolume returns Dividend Market Value for NASDAQ Composite
        indicesExpectedClose[1] = Double.parseDouble(indicesLastWeek.get(1).getOpenPrices().get(0));//last friday's closing price
        indicesPreviousClose[1] = Double.parseDouble(indicesLastWeek.get(1).getOpenPrices().get(5));//friday before's closing price
        indicesExpectedWeekChange[1] = indicesExpectedClose[1] - indicesPreviousClose[1];
        indicesExpectedWeekChangeP[1] = 100 * indicesExpectedWeekChange[1] / indicesPreviousClose[1];
        indicesLastQuarterClose[1] = Double.parseDouble(indicesEndOfLastQuarter.get(1).getOpenPrices().get(0));
        indicesExpectedQuarterToDateChange[1] = 100 * (indicesExpectedClose[1] - indicesLastQuarterClose[1]) / indicesLastQuarterClose[1];
        indicesExpectedYearToDateChange[1] = 100 * (indicesExpectedClose[1] - indicesLastYearClose[1]) / indicesLastYearClose[1];

        // For S&P 500
        indicesExpectedClose[2] = Double.parseDouble(indicesLastWeek.get(2).getClosingPrices().get(0));//last friday's closing price
        indicesPreviousClose[2] = Double.parseDouble(indicesLastWeek.get(2).getClosingPrices().get(5));//friday before's closing price
        indicesExpectedWeekChange[2] = indicesExpectedClose[2] - indicesPreviousClose[2];
        indicesExpectedWeekChangeP[2] = 100 * indicesExpectedWeekChange[2] / indicesPreviousClose[2];
        indicesLastQuarterClose[2] = Double.parseDouble(indicesEndOfLastQuarter.get(2).getClosingPrices().get(0));
        indicesExpectedQuarterToDateChange[2] = 100 * (indicesExpectedClose[2] - indicesLastQuarterClose[2]) / indicesLastQuarterClose[2];
        indicesExpectedYearToDateChange[2] = 100 * (indicesExpectedClose[2] - indicesLastYearClose[2]) / indicesLastYearClose[2];

        // For the 10-Year Bond
        //Keep in mind that quandl stores their index data differently so refer to this documentation for clarity.
        // Closing price is stored in openPrices
        indicesExpectedClose[3] = Double.parseDouble(indicesLastWeek.get(3).getOpenPrices().get(0));//last friday's closing price
        indicesPreviousClose[3] = Double.parseDouble(indicesLastWeek.get(3).getOpenPrices().get(5));//friday before's closing price
        indicesExpectedWeekChange[3] = indicesExpectedClose[3] - indicesPreviousClose[3];
        indicesExpectedWeekChangeP[3] = 100 * indicesExpectedWeekChange[3] / indicesPreviousClose[3];
        indicesLastQuarterClose[3] = Double.parseDouble(indicesEndOfLastQuarter.get(3).getOpenPrices().get(0));
        indicesExpectedQuarterToDateChange[3] = 100 * (indicesExpectedClose[3] - indicesLastQuarterClose[3]) / indicesLastQuarterClose[3];
        indicesExpectedYearToDateChange[3] = 100 * (indicesExpectedClose[3] - indicesLastYearClose[3]) / indicesLastYearClose[3];

        // For Gold
        indicesExpectedClose[4] = Double.parseDouble(indicesLastWeek.get(4).getHighPrices().get(0));//last friday's closing price
        indicesPreviousClose[4] = Double.parseDouble(indicesLastWeek.get(4).getHighPrices().get(5));//friday before's closing price
        indicesExpectedWeekChange[4] = indicesExpectedClose[4] - indicesPreviousClose[4];
        indicesExpectedWeekChangeP[4] = 100 * indicesExpectedWeekChange[4] / indicesPreviousClose[4];
        indicesLastQuarterClose[4] = Double.parseDouble(indicesEndOfLastQuarter.get(4).getHighPrices().get(0));
        indicesExpectedQuarterToDateChange[4] = 100 * (indicesExpectedClose[4] - indicesLastQuarterClose[4]) / indicesLastQuarterClose[4];
        indicesExpectedYearToDateChange[4] = 100 * (indicesExpectedClose[4] - indicesLastYearClose[4]) / indicesLastYearClose[4];

        // For Crude Oil
        indicesExpectedClose[5] = Double.parseDouble(indicesLastWeek.get(5).getClosingPrices().get(0));//last friday's closing price
        indicesPreviousClose[5] = Double.parseDouble(indicesLastWeek.get(5).getClosingPrices().get(5));//friday before's closing price
        indicesExpectedWeekChange[5] = indicesExpectedClose[5] - indicesPreviousClose[5];
        indicesExpectedWeekChangeP[5] = 100 * indicesExpectedWeekChange[5] / indicesPreviousClose[5];
        indicesLastQuarterClose[5] = Double.parseDouble(indicesEndOfLastQuarter.get(5).getClosingPrices().get(0));
        indicesExpectedQuarterToDateChange[5] = 100 * (indicesExpectedClose[5] - indicesLastQuarterClose[5]) / indicesLastQuarterClose[5];
        indicesExpectedYearToDateChange[5] = 100 * (indicesExpectedClose[5] - indicesLastYearClose[5]) / indicesLastYearClose[5];

// determining and calculating "Daily Performance" values (using data from Quandl)
        double[] stockDailyPerformance = new double[5];
        double[] peerDailyPerformance = new double[5];
        double[] sp500DailyPerformance = new double[5];
        QuandlDataset sp500LastWeek;
        //fetching S&P 500 data from Quandl
        sp500LastWeek = QuandlConnectToApi.getDatasetBetweenDates(indices[2], "CHRIS", lastFridayLastString, lastFridayString);
        // calculating daily performance for each day
        for (int day = 0, chartDay = 4; day < 5; day++, chartDay--) { //stock data starts from most recent day, chart goes from Monday to Friday
            double stockClose = Double.parseDouble(companyStocksLast13Weeks.get(0).getClosingPrices().get(day));
            double previousStockClose = Double.parseDouble(companyStocksLast13Weeks.get(0).getClosingPrices().get(day + 1));//closing price of day before
            stockDailyPerformance[chartDay] = 100 * (stockClose - previousStockClose) / previousStockClose;
            //calculating peer average
            peerDailyPerformance[chartDay] = 0;
            for (int stock = 1; stock < symbols.length; stock++) {
                double peerClose = Double.parseDouble(companyStocksLast13Weeks.get(stock).getClosingPrices().get(day));
                double previousPeerClose = Double.parseDouble(companyStocksLast13Weeks.get(stock).getClosingPrices().get(day + 1));
                peerDailyPerformance[chartDay] += 100 * (peerClose - previousPeerClose) / previousPeerClose;
            }
            peerDailyPerformance[chartDay] /= symbols.length - 1;
            double sp500Close = Double.parseDouble(sp500LastWeek.getClosingPrices().get(day));
            double previousSP500Close = Double.parseDouble(sp500LastWeek.getClosingPrices().get(day + 1));
            sp500DailyPerformance[chartDay] = 100 * (sp500Close - previousSP500Close) / previousSP500Close;
        }


        // verifying that "Stock Trade Summary" values are correct
        for (int stock = 0; stock < symbols.length; stock++) {
            if (Math.abs(Double.parseDouble(findElements(weekClose).get(stock).getText()) - expectedClose[stock]) > 0.02) { // checks if calculated value is within 0.02 of displayed
                System.out.println(symbols[stock] + "'s week close is inaccurate.\n\tExpected: " + expectedClose[stock] + "\n\tDisplayed: " + findElements(weekClose).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(weekChange).get(stock).getText()) - expectedWeekChange[stock]) > 0.02) {
                System.out.println(symbols[stock] + "'s week change is inaccurate.\n\tExpected: " + expectedWeekChange[stock] + "\n\tDisplayed: " + findElements(weekChange).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(weekChangeP).get(stock).getText()) - expectedWeekChangeP[stock]) > 0.02) {
                System.out.println(symbols[stock] + "'s week change % is inaccurate.\n\tExpected: " + expectedWeekChangeP[stock] + "\n\tDisplayed: " + findElements(weekChangeP).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(weekHigh).get(stock).getText()) - expectedHigh[stock]) > 0.02) {
                System.out.println(symbols[stock] + "'s week high price is inaccurate.\n\tExpected: " + expectedHigh[stock] + "\n\tDisplayed: " + findElements(weekHigh).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(weekLow).get(stock).getText()) - expectedLow[stock]) > 0.02) {
                System.out.println(symbols[stock] + "'s week low price is inaccurate.\n\tExpected: " + expectedLow[stock] + "\n\tDisplayed: " + findElements(weekLow).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(weekVolume).get(stock).getText()) * 10E5 - expectedVolume[stock]) > 20000) {
                System.out.println("Known issue - ADMIN-399 - " + symbols[stock] + "'s week volume is inaccurate.\n\tExpected: " + expectedVolume[stock] + "\n\tDisplayed: " + findElements(weekVolume).get(stock).getText() + " million");
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(averageWeekVolume).get(stock).getText()) * 10E5 - expectedVolumeWeekAverage[stock]) > 20000) {
                System.out.println(symbols[stock] + "'s average weekly volume is inaccurate.\n\tExpected: " + expectedVolumeWeekAverage[stock] + "\n\tDisplayed: " + findElements(averageWeekVolume).get(stock).getText() + " million");
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(volumeVariance).get(stock).getText()) - expectedVolumeVariance[stock]) > 0.02) {
                // when displayed value is wrong, determine if calculation using displayed values gives same result (meaning that only those other displayed values are wrong)
                if ((100 * (Double.parseDouble(findElements(weekVolume).get(stock).getText()) - Double.parseDouble(findElements(averageWeekVolume).get(stock).getText()))
                        / Double.parseDouble(findElements(averageWeekVolume).get(stock).getText())) - Double.parseDouble(findElements(volumeVariance).get(stock).getText()) < 0.02) {
                    System.out.println(symbols[stock] + "'s volume variance is inaccurate due to carryover from inaccurate week volume or/and average weekly volume.");
                    allValid = false;
                } else {
                    System.out.println(symbols[stock] + "'s volume variance is inaccurate.\n\tExpected: " + expectedVolumeVariance[stock] + "\n\tDisplayed: " + findElements(volumeVariance).get(stock).getText());
                    allValid = false;
                }
           }
            if (Math.abs(Double.parseDouble(findElements(qtdChange).get(stock).getText()) - expectedQuarterToDateChange[stock]) > 0.02) {
                System.out.println(symbols[stock] + "'s QTD change is inaccurate.\n\tExpected: " + expectedQuarterToDateChange[stock] + "\n\tDisplayed: " + findElements(qtdChange).get(stock).getText());
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(findElements(ytdChange).get(stock).getText()) - expectedYearToDateChange[stock]) > 0.02) {
                System.out.println(symbols[stock] + "'s YTD change is inaccurate.\n\tExpected: " + expectedYearToDateChange[stock] + "\n\tDisplayed: " + findElements(ytdChange).get(stock).getText());
                allValid = false;
            }
        }
        if (Math.abs(Double.parseDouble(findElements(weekChangeP).get(symbols.length).getText()) - expectedWeekChangePAverage) > 0.02) {
            System.out.println("Peer average week change % is inaccurate.\n\tExpected: " + expectedWeekChangePAverage + "\n\tDisplayed: " + findElements(weekChangeP).get(symbols.length).getText());
            allValid = false;
        }
        if (Math.abs(Double.parseDouble(findElements(qtdChange).get(symbols.length).getText()) - expectedQuarterToDateChangeAverage) > 0.02) {
            System.out.println("Peer average QTD change is inaccurate.\n\tExpected: " + expectedQuarterToDateChangeAverage + "\n\tDisplayed: " + findElements(qtdChange).get(symbols.length).getText());
            allValid = false;
        }
        if (Math.abs(Double.parseDouble(findElements(ytdChange).get(symbols.length).getText()) - expectedYearToDateChangeAverage) > 0.02) {
            System.out.println("Peer average YTD change is inaccurate.\n\tExpected: " + expectedYearToDateChangeAverage + "\n\tDisplayed: " + findElements(ytdChange).get(symbols.length).getText());
            allValid = false;
        }

        // We don't have an api for Dow Jones and S&P 500 historical data so the for loop starts at 1 instead of 0 so we ignore Dow Jones
        // verifying that "Indices" values are correct
        for (int index = 1; index < 3; index++) {
            if (index == 2) {
                continue;
            }
            if (getNumFromText(findElements(indicesClose).get(index).getText()) - indicesExpectedClose[index] > 5) {
                System.out.println("Index " + indices[index] + "'s week close is inaccurate.\n\tExpected: " + indicesExpectedClose[index] + "\n\tDisplayed: " + findElements(indicesClose).get(index).getText());
                allValid = false;
            }
            if (getNumFromText(findElements(indicesChange).get(index).getText()) - indicesExpectedWeekChange[index] > 5) {
                System.out.println("Index " + indices[index] + "'s week change is inaccurate.\n\tExpected: " + indicesExpectedWeekChange[index] + "\n\tDisplayed: " + findElements(indicesChange).get(index).getText());
                allValid = false;
            }
            if (getNumFromText(findElements(indicesChangeP).get(index).getText()) - indicesExpectedWeekChangeP[index] > 0.25) {
                System.out.println("Index " + indices[index] + "'s week change % is inaccurate.\n\tExpected: " + indicesExpectedWeekChangeP[index] + "\n\tDisplayed: " + findElements(indicesChangeP).get(index).getText());
                allValid = false;
            }
            if (getNumFromText(findElements(indicesQTDChange).get(index).getText()) - indicesExpectedQuarterToDateChange[index] > 0.25) {
                System.out.println("Index " + indices[index] + "'s QTD change is inaccurate.\n\tExpected: " + indicesExpectedQuarterToDateChange[index] + "\n\tDisplayed: " + findElements(indicesQTDChange).get(index).getText());
                allValid = false;
            }
            if (getNumFromText(findElements(indicesYTDChange).get(index).getText()) - indicesExpectedYearToDateChange[index] > 0.25) {
                System.out.println("Index " + indices[index] + "'s YTD change is inaccurate.\n\tExpected: " + indicesExpectedYearToDateChange[index] + "\n\tDisplayed: " + findElements(indicesYTDChange).get(index).getText());
                allValid = false;
            }
        }

        // Gold and Oil prices have a greater discrepancy, hence larger room for error
        for (int index = 3; index < indices.length; index++) {
            if (getNumFromText(findElements(indicesClose).get(index).getText()) - indicesExpectedClose[index] > 15) {
                System.out.println("Index " + indices[index] + "'s week close is inaccurate.\n\tExpected: " + indicesExpectedClose[index] + "\n\tDisplayed: " + findElements(indicesClose).get(index).getText());
                allValid = false;
            }
            if (getNumFromText(findElements(indicesChange).get(index).getText()) - indicesExpectedWeekChange[index] > 5) {
                System.out.println("Index " + indices[index] + "'s week change is inaccurate.\n\tExpected: " + indicesExpectedWeekChange[index] + "\n\tDisplayed: " + findElements(indicesChange).get(index).getText());
                allValid = false;
            }
            if (getNumFromText(findElements(indicesChangeP).get(index).getText()) - indicesExpectedWeekChangeP[index] > 0.5) {
                System.out.println("Index " + indices[index] + "'s week change % is inaccurate.\n\tExpected: " + indicesExpectedWeekChangeP[index] + "\n\tDisplayed: " + findElements(indicesChangeP).get(index).getText());
                allValid = false;
            }
            if (getNumFromText(findElements(indicesQTDChange).get(index).getText()) - indicesExpectedQuarterToDateChange[index] > 0.6) {
                System.out.println("Index " + indices[index] + "'s QTD change is inaccurate.\n\tExpected: " + indicesExpectedQuarterToDateChange[index] + "\n\tDisplayed: " + findElements(indicesQTDChange).get(index).getText());
                allValid = false;
            }
            if (getNumFromText(findElements(indicesYTDChange).get(index).getText()) - indicesExpectedYearToDateChange[index] > 0.25) {
                System.out.println("Index " + indices[index] + "'s YTD change is inaccurate.\n\tExpected: " + indicesExpectedYearToDateChange[index] + "\n\tDisplayed: " + findElements(indicesYTDChange).get(index).getText());
                allValid = false;
            }
        }



        // verifying that "Daily Performance" values are correct (by hovering over each of the bars)
        int numDays = findVisibleElements(dailyPerformanceStockBars).size();
        for (int chartDay = 0; chartDay < numDays; chartDay++) {
            // checking for stock
            actions.clickAndHold(findVisibleElements(dailyPerformanceStockBars).get(chartDay)).perform();// clickAndHold needed to keep cursor there while getText() runs
            String hoverText = findElement(dailyPerformanceHoverText).getText().trim();
            if (!hoverText.startsWith(symbols[0])) {
                System.out.println("Daily Performance: Hover text for blue bar on day " + (chartDay + 1) + " does not start with desired symbol " + symbols[0] + "\n\tDisplayed hover text: " + hoverText);
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(hoverText.substring(hoverText.indexOf(':') + 2, hoverText.indexOf('%'))) - stockDailyPerformance[chartDay]) > 0.002) {
                System.out.println("Stock's daily performance for day " + (chartDay + 1) + " is inaccurate.\n\tExpected: " + stockDailyPerformance[chartDay] + "%\n\tDisplayed hover text: " + hoverText);
                allValid = false;
            }
            // checking for peer average
            actions.clickAndHold(findVisibleElements(dailyPerformancePeerBars).get(chartDay)).perform();
            hoverText = findElement(dailyPerformanceHoverText).getText().trim();
            if (!hoverText.startsWith("Peer Avg:")) {
                System.out.println("Daily Performance: Hover text for yellow bar on day " + (chartDay + 1) + " does not start with 'Peer Avg:'\n\tDisplayed hover text: " + hoverText);
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(hoverText.substring(hoverText.indexOf(':') + 2, hoverText.indexOf('%'))) - peerDailyPerformance[chartDay]) > 0.002) {
                System.out.println("Peer average daily performance for day " + (chartDay + 1) + " is inaccurate.\n\tExpected: " + peerDailyPerformance[chartDay] + "%\n\tDisplayed hover text: " + hoverText);
                allValid = false;
            }
            // checking for S&P 500
            actions.clickAndHold(findVisibleElements(dailyPerformanceSP500Bars).get(chartDay)).perform();
            hoverText = findElement(dailyPerformanceHoverText).getText().trim();
            if (!hoverText.startsWith("S&P 500:")) {
                System.out.println("Daily Performance: Hover text for red bar on day " + (chartDay + 1) + " does not start with 'S&P 500:'\n\tDisplayed hover text: " + hoverText);
                allValid = false;
            }
            if (Math.abs(Double.parseDouble(hoverText.substring(hoverText.indexOf(':') + 2, hoverText.indexOf('%'))) - sp500DailyPerformance[chartDay]) > 0.002) {
                // Need to find api for S&P 500 data
                System.out.println("Known Issue: No S&P data from Quandl Api");
                continue;
                //System.out.println("S&P 500 daily performance for day "+(chartDay+1)+" is inaccurate.\n\tExpected: "+sp500DailyPerformance[chartDay]+"%\n\tDisplayed hover text: "+hoverText);
                //allValid = false;
            }
        }

        ArrayList<WebElement> testArray = new ArrayList<>(findVisibleElements(ytdChangeBars));
        // verifying that "YTD Change" values are correct
        for (int bar = 0; bar < symbols.length + 1; bar++) {

            actions.clickAndHold(findElements(ytdChangeBars).get(bar)).perform();// clickAndHold needed to keep cursor there while getText() runs
            String hoverText = findElement(ytdChangeHoverText).getText().trim();
            // checking the peer average bar
            if (findElement(ytdChangeAxisText).getText().contains("Peer")) {
                if (Math.abs(Double.parseDouble(hoverText.substring(hoverText.indexOf(':') + 2, hoverText.indexOf('%'))) - expectedYearToDateChangeAverage) > 0.2) {
                    allValid = false;
                    // when displayed value is wrong, determine if value is different from that displayed on Stock Trade Summary table (meaning that the graph is wrong)
                    if (Math.abs(Double.parseDouble(hoverText.substring(hoverText.indexOf(':') + 2, hoverText.indexOf('%')))
                            - Double.parseDouble(findElements(ytdChange).get(symbols.length).getText())) > 0.2) {
                        System.out.println("YTD Change graph: Peer average bar is inaccurate.\n\tExpected: " + expectedYearToDateChangeAverage
                                + "%\n\tValue on STS table: " + findElements(ytdChange).get(symbols.length).getText() + "\n\tDisplayed hover text: " + hoverText);
                    } else {
                        System.out.println("YTD Change graph: Peer average bar is inaccurate due to carryover from inaccurate value (see Stock Trade Summary table).");
                    }
                }
            }
            // checking the other bars
            else {
                for (int stock = 0; stock < symbols.length; stock++) {
                    if (findElements(ytdChangeAxisText).get(bar).getText().contains(symbols[stock])) { //matches each bar with its stock
                        if (Math.abs(Double.parseDouble(hoverText.substring(hoverText.indexOf(':') + 2, hoverText.indexOf('%'))) - expectedYearToDateChange[stock]) > 0.2) {
                            allValid = false;
                            // when displayed value is wrong, determine if value is different from that displayed on Stock Trade Summary table (meaning that the graph is wrong)
                            if (Math.abs(Double.parseDouble(hoverText.substring(hoverText.indexOf(':') + 2, hoverText.indexOf('%')))
                                    - Double.parseDouble(findElements(ytdChange).get(stock).getText())) > 0.2) {
                                System.out.println("YTD Change graph: " + symbols[stock] + " bar is inaccurate.\n\tExpected: " + expectedYearToDateChange[stock]
                                        + "%\n\tValue on STS table: " + findElements(ytdChange).get(stock).getText() + "\n\tDisplayed hover text: " + hoverText);
                            } else {
                                System.out.println("YTD Change graph: " + symbols[stock] + " bar is inaccurate due to carryover from inaccurate value (see Stock Trade Summary table).");
                            }
                        }
                        break;
                    }
                }
            }
        }

        return allValid; // will be false if there were any inaccurate values (this way a full list of errors can be generated instead of stopping after one)
    }
}
