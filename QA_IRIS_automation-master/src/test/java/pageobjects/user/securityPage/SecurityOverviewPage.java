package pageobjects.user.securityPage;

import org.apache.commons.lang.ObjectUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.user.activismPage.ActivismPage;
import pageobjects.user.estimatesPage.SecurityEstimatesPage;
import pageobjects.user.ownershipPage.SecurityOwnershipPage;
import pageobjects.user.relativePerformancePage.RelativePerformacePage;
import pageobjects.user.sentimentPage.SentimentPage;
import pageobjects.user.tradingRangePage.TradingRangePage;
import pageobjects.user.volatilityPage.VolatilityPage;
import pageobjects.user.watchlist.WatchlistPage;

/**
 * Created by patrickp on 2016-08-04.
 */
public class SecurityOverviewPage extends WatchlistPage {

    //Create an interface including these things for overview page
    private final By overviewModal = By.cssSelector(".company-page .company-slide-inner");
    private final By ownershipModal = By.cssSelector(".company-ownership .company-ownership-inner");
    private final By estimatesModal = By.cssSelector(".company-page .company-slide-inner");
    private final By iframe = By.xpath("//iframe[contains(@id, 'sencha-viewport')]");

    /**        HEADER       */

    //data\\


    private final By companyName = By.xpath("//div[contains(@class,'company-name')]");
    private final By companyTicker = By.xpath("//div[contains(@class,'x-innerhtml')]//div[contains(@class,'company-symbol')]");
    private final By industry_Exchange = By.xpath("//div[contains(@class,'exchange')][text()[2]]");
    private final By stockQuote = By.className("stock-price");
    private final By changeIcon = By.className("change-icon");
    private final By stockChange = By.className("change-value");

    private final By volume = By.xpath("//*[@class=\"value\"]");
    private final By avgVolume = By.xpath("(//span[@class='value'])[2]");   // <- Follow this syntax!!!

    //dropdown

    final By dropdownModal = By.cssSelector(".company-service-list .company-service-list-container");

    final By dropdownMenu = By.cssSelector(".company-header .menu-button .x-button-label");
    final By dropdownLeftArrow = By.xpath("//div[contains(@class,'navigate')][span[contains(@class,'q4i-caret-sm-left-4pt')]]");
    final By dropdownRightArrow = By.xpath("//div[contains(@class,'navigate')][span[contains(@class,'q4i-caret-sm-right-4pt')]]");

    final By dropdownOverview = By.xpath("//*[@class=\"company-service-list-item menu-item\"][1]");
    final By dropdownOwnership = By.xpath("//*[@class=\"company-service-list-item menu-item\"][2]");
    final By dropdownTrading = By.xpath("//*[@class=\"company-service-list-item menu-item\"][3]");
    final By dropdownSentiment = By.xpath("//*[@class=\"company-service-list-item menu-item\"][4]");
    final By dropdownVolatility = By.xpath("//*[@class=\"company-service-list-item menu-item\"][5]");
    final By dropdownActivism = By.xpath("//*[@class=\"company-service-list-item menu-item\"][6]");
    final By dropdownPerformance = By.xpath("//*[@class=\"company-service-list-item menu-item\"][7]");
    final By dropdownEstimates = By.xpath("//*[@class=\"company-service-list-item menu-item\"][8]");

    //buttons\\ -> to add stuff for modals that appear when clicking button

    private final By recentEstimatesBtn = By.xpath("//div[contains(@id, 'header-notifications-tab')]//div[contains(@class, 'x-iconalign-center')][span[contains(@class,'q4i-estimates-research-2pt')]]");
    private final By recentEventsBtn = By.xpath("//div[contains(@id, 'header-notifications-tab')]//div[contains(@class, 'x-iconalign-center')][span[contains(@class,'q4i-events-transcripts-2pt')]]");
    private final By recentTranscriptsBtn = By.xpath("//div[contains(@id, 'header-notifications-tab')]//div[contains(@class, 'x-iconalign-center')][span[contains(@class,'q4i-transcripts-2pt')]]");
    private final By recentNewsBtn = By.xpath("//div[contains(@id, 'header-notifications-tab')]//div[contains(@class, 'x-iconalign-center')][span[contains(@class,'q4i-press-releases-2pt')]]");
    private final By recentResearchBtn = By.xpath("//div[contains(@id, 'header-notifications-tab')]//div[contains(@class, 'research-buttonresearch-button')]");

    private final By recentEstimatesResults = By.cssSelector(".company-header-latest-estimates .latest-estimate-item");
    private final By recentEventsResults = By.xpath("//div[contains(@class,'latest-events-item')]");
    private final By recentTranscriptsResults = By.cssSelector(".company-header-latest-transcripts .latest-transcripts-item");
    private final By recentNewsResults = By.cssSelector(".company-header-latest-news .news-item .news-date"); //n
    private final By recentResearchResults = By.cssSelector(".company-header-latest-research .latest-research-item");
    private final By recentEstimatesModal = By.cssSelector(".company-header-latest-estimates h2");
    private final By recentEventsModal = By.cssSelector(".company-header-latest-events h2");
    private final By recentTranscriptsModal = By.cssSelector(".company-header-latest-transcripts h2");
    private final By recentNewsModal = By.cssSelector(".company-header-latest-news h2");
    private final By recentResearchModal = By.xpath("//div[contains(@class, 'latest-research-item')]");

    private final By eventsResultslModal = By.cssSelector(".event-detail .header");
    //^^^The modal that appears once you click on a result^^^
    private final By transcriptsResultsModal = By.cssSelector(".event-list-item .details .title");
    private final By transciptsResultsExitBtn = By.cssSelector(".x-button .x-button-icon");
    private final By newsResultsModal = By.cssSelector(".modal.company-news .story");

    //three_point_button\\ -> to add stuff for modals that appear when clicking button

    private final By threePointBtn = By.xpath("//span[contains(@class,'q4i-utility-4pt')]");

    private final By watchlistBtn = By.xpath("//span[contains(text(),'Peer List')]");
    private final By logActivityBtn = By.xpath("//*[@class=\"x-unsized x-button x-iconalign-left x-text-align-left\"][1]");
    private final By suggestEditBtn = By.xpath("//*[@class=\"x-unsized x-button x-iconalign-left x-text-align-left\"][2]");

    //tags\\ -> TESTING THIS WILL BE TRICKY. FOR NOW, TEST FOR CREATION OF ONE TAG

    private final By addTagBtn = By.className("x-button x-stretched tag add-button x-iconalign-center");
    private final By createdTagBtn = By.className("tag"); //Can get text from this
    private final By removeTagBtn = By.className("delete");


    /**     CHARTS      */

    //buttons\\

    private final By oneMonthBtn = By.xpath("//*[@class=\"highcharts-button\"][1]");
    private final By threeMonthBtn = By.xpath("//*[@class=\"highcharts-button\"][2]");
    private final By oneYearBtn = By.xpath("//*[@class=\"highcharts-button\"][3]");

    private final By s_P500Btn = By.className("x-button x-button-no-icon legend-sp500 x-layout-box-item x-stretched");
    private final By nasdaqBtn = By.className("x-button x-button-no-icon legend-nasdaq x-layout-box-item x-stretched");
    private final By dowjonesBtn = By.className("x-button x-button-no-icon legend-dowjones x-layout-box-item x-stretched");
    private final By indicatorsBtn = By.className("x-button x-iconalign-left legend-indicators x-layout-box-item x-stretched");

    private final By sentimentBtn = By.xpath("//*[@class=\"company-service-list-item menu-item\"][4]");
    private final By volatilityBtn = By.xpath("//*[@class=\"company-service-list-item menu-item\"][5]");
    private final By activismBtn = By.xpath("//*[@class=\"company-service-list-item menu-item\"][6]");
    private final By performanceBtn = By.className("indicator-item rp");

    private final By toggleBtn = By.cssSelector(".switch-view-button .x-toggle .x-thumb");

    //data\\ -> Found once toggled

    private final By prevCloseValue = By.xpath("//*[@class=\"value\"][3]");
    private final By openValue = By.xpath("//*[@class=\"value\"][7]");
    private final By intradayLowValue1 = By.xpath("//*[@class=\"value\"][4]");
    private final By intradayLowValue2 = By.xpath("//*[@id=\"day-bg-group\"]/text[2]"); //Found on 1d chart, above "LOW"
    private final By intradayHighValue1 = By.xpath("//*[@class=\"value\"][8]");
    private final By intradayHighValue2 = By.xpath("//*[@id=\"day-bg-group\"]/text[3]"); //Found on 1d chart, above "HIGH"
    private final By yearLowValue1 = By.xpath("//*[@class=\"value\"][5]"); //year Low = 52wk Low
    private final By yearLowValue2 = By.xpath("//*[@id=\"week-bg-group\"]/text[2]"); //Found on 52wk chart, above "LOW"
    private final By yearHighValue1 = By.xpath("//*[@class=\"value\"][9]");
    private final By yearHighValue2 = By.xpath("//*[@id=\"week-bg-group\"]/text[3]"); //Found on 52wk chart, above "HIGH"
    private final By dailyVolumeValue = By.xpath("//*[@class=\"value\"][6]");
    private final By currencyType = By.xpath("//*[@class=\"value\"][10]");
    private final By searchResult = By.xpath("//div[contains(@class,'company-item')]");
    private final By profileModal = By.xpath("//div[contains(@class,'profile-modal')]");

    /**     INPUT       */

    private final By indexInput = By.xpath("//input[contains(@placeholder,'Add index')]");


    /**     QUALITY RATING       */


    private final By qualityRatingValue = By.cssSelector(".overview-quality-rating .qr-value .x-innerhtml:not(:empty)");

    //investor_stlye_data\\

    private final By growthValue = By.xpath("//*[@class=\"val\"][22]");
    private final By indexValue = By.xpath("//*[@class=\"val\"][23]");
    private final By yieldValue = By.xpath("//*[@class=\"val\"][24]");
    private final By investorStyleOtherValue = By.xpath("//*[@class=\"val\"][25]");

    //turnover_data\\

    private final By veryLowValue = By.xpath("//*[@class=\"val\"][26]");
    private final By lowValue = By.xpath("//*[@class=\"val\"][27]");
    private final By mediumValue = By.xpath("//*[@class=\"val\"][28]");
    private final By turnoverOtherValue = By.xpath("//*[@class=\"val\"][29]");

    //buttons\\

    private final By ownershipBtn = By.cssSelector(".overview-quality-rating");
    private final By profile = By.xpath("//span[contains(@class,'button-label') and contains(text(),'Profile')]");
    //^^This is the Ownership Quality Rating modal, excluding EPS/SALES

    //private final By epsBtn = //EPS value to be found here
    //private final By salesBtn = //The Sales value to be found here.


    /**     MINI CHARTS      */


    //expected_trading_range\\

    private final By hiValue = By.xpath("//*[@class=\"val\"][18]");
    private final By loValue = By.xpath("//*[@class=\"val\"][19]"); //click on this for redirection

    //relative_performance\\

    private final By actualValue = By.xpath("//*[@class=\"val\"][20]");
    private final By stockValue = By.xpath("//*[@class=\"val\"][21]"); //click on this for redirection

    //sentiment\\

    private final By sentimentValue = By.xpath("//*[contains(@class, 'peer-piechart')]");
    //^^^Click on value for redirection

    //volatility\\

    private final By volatilityValue = By.xpath("//*[contains(@class, 'volatility-chart')]");
    //^^^Click on value for redirection

    //activism\\

    private final By activismValue = By.xpath("//*[contains(@class,'activism-chart')]");
    //^^^Click on value for redirection


    /**       EVENTS & TRANSCRIPTS       */ //NO TEST CASES YET!!!


    //private final By allBtn
    //private final By transcriptsBtn
    //private final By webcastsBtn
    //private final By slidesBtn
    //private final By releasesBtn
    //private final By secBtn

    //private final By events_TranscriptsBtn
    //private final By newsBtn


    /**       NEWS      */ //NO TEST CASES YET!!!


    /**      WATCHLIST BAR       */ //WILL DO LATER


    //private final By readMore

    //Regarding the dropdown menus to check if a certain part of a site exists, just use the URL? Will figure this out
    //at a later date. This applies to other applications as well.
    //Include whole modal elements for the tests that check if a modal closed successfully



    public SecurityOverviewPage(WebDriver driver) {
        super(driver);
    }


    /**         EXISTS         */ //Create abstract class that contains all these.


    public  boolean dropdownMenuExists() {
        pause(500L);
        return doesElementExist(dropdownModal);
    }

    public boolean overviewPageExists() {
        pause(500L);
        return doesElementExist(overviewModal);

    }

    public boolean ownershipPageExists() {
        pause(500L);
        return doesElementExist(ownershipModal);
    }

    public boolean estimatesPageExists() {
        pause(500L);
        return doesElementExist(estimatesModal);
    }

    public boolean recentEstimatesModalExists() {
        pause(500L);
        return doesElementExist(recentEstimatesModal);
    }

    public boolean recentTranscriptsModalExists() {
        pause(500L);
        return doesElementExist(recentTranscriptsModal);
    }

    public boolean recentTranscriptsResultsModalExists() {
        pause(500L);
        return findVisibleElement(transcriptsResultsModal).isDisplayed();
    }

    public boolean recentNewsModalExists() {
        pause(500L);
        return doesElementExist(recentNewsModal);
    }

    public boolean recentNewsResultsModalExists(boolean active) {
        if (active) {
            waitForAnyElementToAppear(newsResultsModal);
            return findVisibleElement(newsResultsModal).isDisplayed();
        }
        waitForElement(newsResultsModal);
        return !findVisibleElement(recentNewsResults).isDisplayed();
    }

    public boolean recentEventsModalExists() {
        pause(500L);
        return doesElementExist(recentEventsModal);
    }

    public boolean recentEventsResultsModalExists() {
        pause(500L);
        return doesElementExist(eventsResultslModal);
    }

    public boolean recentResearchModalExists(){
        pause(500L);
        return doesElementExist(recentResearchModal);
    }

    //Research doesn't need a results modal because it starts a download, not a new page




    /**          GETTERS:         */


    //HEADER\\

    public String getCompanyName() {
        waitForLoadingScreen();
        switchToiframe();
        waitForElementToAppear(companyName);
        String returnName = findElement(companyName).getText().replaceAll("\\p{P}", "");
        leaveiframe();
        return returnName;
    }

    public String getCompanyTicker() {
        waitForElementToAppear(companyTicker);
        return findElement(companyTicker).getAttribute("innerHTML").replaceAll("\\p{P}", "");
    }

    public String getIndustry_Exchange() {
        waitForElementToAppear(industry_Exchange);
        return findElement(industry_Exchange).getText().replaceAll("\\p{P}", "");
    }

    public String getStockQuote() {
        waitForElementToAppear(stockQuote);
        return findElement(stockQuote).getText().replaceAll("[^0-9.]+", "");
    }

    public String getChangeIconColor() {
        waitForElementToAppear(changeIcon);
        String color = findElement(changeIcon).getCssValue("background-color");
        return color.replaceAll("[ rgba(),]", "");
    }

    public String getStockChange() {
        waitForElementToAppear(stockChange);
        return findElement(stockChange).getText();
    }

    public String getVolume() {
        waitForElementToAppear(volume);
        return findElement(volume).getText();
    }

    public String getAvgVolume() {
        waitForElementToAppear(avgVolume);
        return findElement(avgVolume).getText();
    }

    public String getRecentEstimatesButtonNumber() {
        waitForLoadingScreen();
        waitForElement(recentEstimatesBtn);
        waitForTextToChange(recentEstimatesBtn, "-");
        return findElement(recentEstimatesBtn).getText();
    }

    public int getNumEstimatesResultsDisplayed() {
        waitForElementToAppear(recentEstimatesModal);
        return findElements(recentEstimatesResults).size();
    }

    public String getRecentNewsButtonNumber() {
        waitForLoadingScreen();
        waitForElementToAppear(recentNewsBtn);
        waitForTextToChange(recentNewsBtn, "-");
        return findElement(recentNewsBtn).getText();
    }

    public int getNumNewsResultsDisplayed() { //Issue spans here. Get news text and do a regex, checking it has the text "hour" within it
        waitForElementToAppear(recentNewsModal);
        return findElements(recentNewsResults).size();
    }

    public String getRecentEventsButtonNumber() {
        waitForLoadingScreen();
        waitForElementToAppear(recentEventsBtn);
        waitForTextToChange(recentEventsBtn, "-");
        return findElement(recentEventsBtn).getText();
    }

    public int getNumEventsResultsDisplayed() {
        waitForElement(recentEventsModal);
        return findElements(recentEventsResults).size();
    }

    public String getRecentTranscriptsButtonNumber() {
        waitForLoadingScreen();
        waitForElementToAppear(recentTranscriptsBtn);
        waitForTextToChange(recentTranscriptsBtn, "-");
        return findElement(recentTranscriptsBtn).getText();
    }

    public int getNumTranscriptsResultsDisplayed() {
        waitForElementToAppear(recentTranscriptsModal);
        return findElements(recentTranscriptsResults).size();
    }

    public String getRecentResearchButtonNumber(){
        waitForLoadingScreen();
        waitForElement(recentResearchBtn);
        waitForTextToChange(recentResearchBtn, "-");
        return findElement(recentResearchBtn).getText();
    }

    public int getNumResearchResultsDisplayed(){
        waitForElementToAppear(recentResearchModal);
        return findElements(recentResearchResults).size();
    }

    //CHARTS\\

    public String getPrevCloseValue() {
        return findElement(prevCloseValue).getText().replaceAll("\\p{P}", "");
    }

    public String getOpenValue() {
        return findElement(openValue).getText().replaceAll("\\p{P}", "");
    }

    public String getIntradayLowValue1() //Not found in chart
    {
        return findElement(intradayLowValue1).getText().replaceAll("\\p{P}", "");
    }

    public String getIntradayLowValue2() {
        return findElement(intradayLowValue2).getText().replaceAll("\\p{P}", "");
    }

    public String getIntradayHighValue1() {
        return findElement(intradayHighValue1).getText().replaceAll("\\p{P}", "");
    }

    public String getIntradayHighValue2() {
        return findElement(intradayHighValue2).getText().replaceAll("\\p{P}", "");
    }

    public String getYearLowValue1() {
        return findElement(yearLowValue1).getText().replaceAll("\\p{P}", "");
    }

    public String getYearLowValue2() {
        return findElement(yearLowValue2).getText().replaceAll("\\p{P}", "");
    }

    public String getYearHighValue1() {
        return findElement(yearHighValue1).getText().replaceAll("\\p{P}", "");
    }

    public String getYearHighValue2() {
        return findElement(yearHighValue2).getText().replaceAll("\\p{P}", "");
    }

    public String getDailyVolumeValue() {
        return findElement(dailyVolumeValue).getText().replaceAll("\\p{P}", "");
    }

    public String getCurrencyType() {
        return findElement(currencyType).getText().replaceAll("\\p{P}", "");
    }

    //QUALITY RATING\\

    public String getGrowthValue() {
        return findElement(growthValue).getText().replaceAll("\\p{P}", "");
    }

    public String getIndexValue() {
        return findElement(indexValue).getText().replaceAll("\\p{P}", "");
    }

    public String getYieldValue() {
        return findElement(yieldValue).getText().replaceAll("\\p{P}", "");
    }

    public String getInvestorStyleOtherValue() {
        return findElement(investorStyleOtherValue).getText().replaceAll("\\p{P}", "");
    }


    public String getVeryLowValue() {
        return findElement(veryLowValue).getText().replaceAll("\\p{P}", "");
    }

    public String getLowValue() {
        return findElement(lowValue).getText().replaceAll("\\p{P}", "");
    }

    public String getMediumValue() {
        return findElement(mediumValue).getText().replaceAll("\\p{P}", "");
    }

    public String getTurnoverOtherValue() {
        return findElement(turnoverOtherValue).getText().replaceAll("\\p{P}", "");
    }

    //MINI CHARTS\\

    public String getHiValue() {
        return findElement(hiValue).getText().replaceAll("\\p{P}", "");
    }

    public String getLoValue() {
        return findElement(loValue).getText().replaceAll("\\p{P}", "");
    }

    public String getActualValue() {
        return findElement(actualValue).getText().replaceAll("\\p{P}", "");
    }

    public String getStockValue() {
        return findElement(stockValue).getText().replaceAll("\\p{P}", "");
    }

    public String getSentimentValue() {
        return findElement(sentimentValue).getText().replaceAll("\\p{P}", "");
    }

    public String getVolatilityValue() {
        return findElement(volatilityValue).getText().replaceAll("\\p{P}", "");
    }

    public String getActivismValue() {
        return findElement(activismValue).getText().replaceAll("\\p{P}", "");
    }

    // Gets the stock price from the security details page
    public float getStockPrice() {
        waitForElement(stockQuote);
        return Float.parseFloat(findElement(stockQuote).getText().replaceAll("[^0-9.]+", ""));
    }


    /**            CLICKERS            */


    //RECALL, ALL TESTS ARE BASED ON OVERVIEW PAGE, SO JUST CHECKS IF A BUTTON TAKES YOU TO RIGHT PAGE BUT NO MORE.
    public SecurityOverviewPage clickDropdownLeftArrowOverview() {
        waitForElementToBeClickable(dropdownLeftArrow).click();
        return this;
    }

    public SecurityOwnershipPage clickDropdownRightArrowOverview() { //Other versions of this on other pages
        waitForElementToBeClickable(dropdownRightArrow).click();
        return new SecurityOwnershipPage(getDriver());
    }

    public SecurityOverviewPage clickViewDropdownMenu() {
        waitForElementToBeClickable(dropdownMenu).click();
        return this;
    }

    public SecurityOverviewPage clickDropdownOverview()//Perhaps put these methods into an interface for security?
    {
        waitForElementToBeClickable(dropdownOverview).click();
        return new SecurityOverviewPage(getDriver());
    }

    public SecurityOwnershipPage clickDropdownOwnership()
    {
        waitForElementToBeClickable(dropdownOwnership).click();
        return new SecurityOwnershipPage(getDriver());
    }

    public TradingRangePage clickDropdownTrading() {

        waitForElementToBeClickable(dropdownTrading).click();
        return new TradingRangePage(getDriver());
    }

    public SentimentPage clickDropdownSentiment() {

        waitForElementToBeClickable(dropdownSentiment).click();
        return new SentimentPage(getDriver());
    }

    public VolatilityPage clickDropdownVolatility() {

        waitForElementToBeClickable(dropdownVolatility).click();
        return new VolatilityPage(getDriver());
    }

    public ActivismPage clickDropdownActivism() {

        waitForElementToBeClickable(dropdownActivism).click();
        return new ActivismPage(getDriver());
    }

    public RelativePerformacePage clickDropdownPerformance() {

        waitForElementToBeClickable(dropdownPerformance).click();
        return new RelativePerformacePage(getDriver());
    }

    public SecurityEstimatesPage clickDropdownEstimates() {

        waitForElementToBeClickable(dropdownEstimates).click();
        return new SecurityEstimatesPage(getDriver());
    }

    public void clickRecentEstimatesButton(){
        waitForElementToBeClickable(recentEstimatesBtn).click();
    }

    public boolean newsItemsEmpty(){
        try{
            if(findVisibleElement(By.xpath("//div[contains(@class,'no-data')]")).getText().contains("No news available"))
                return true;
            else
                return false;
        }
        catch (Exception e){
            return false;

        }
    }
    public void clickRecentNewsButton(){
        waitForLoadingScreen();
        waitForElementToBeClickable(recentNewsBtn).click();
    }

    public void clickRecentTranscriptsButton(){
        waitForLoadingScreen();
        waitForElementToBeClickable(recentTranscriptsBtn).click();
    }

    public void clickRecentEventsButton(){
        waitForLoadingScreen();
        waitForElementToBeClickable(recentEventsBtn).click();
    }

    public void clickRecentResearchButton(){
        waitForLoadingScreen();
        waitForElementToBeClickable(recentResearchBtn).click();
    }


    //
    //Results - I.e. the pop up
    //


    public void clickRecentEstimatesResult(){
        waitForElementToBeClickable(recentEstimatesResults).click();
    }

    public void clickRecentNewsResult() {
        waitForElementToBeClickable(recentNewsResults).click();
    }

    public void clickRecentEventsResult()
    {
        findElement(recentEventsResults).click();
    }

    public void clickRecentTranscriptsResults(){
        findElement(recentTranscriptsResults).click();
    }

    public void clickTrancsriptsResultsXBtn(){
        findElement(transciptsResultsExitBtn).click();
    }

    public SecurityOverviewPage clickThreePointBtn(){
        waitForElementToBeClickable(threePointBtn).click();
        return new SecurityOverviewPage(getDriver());
    }
    public SecurityOverviewPage clickWatchlistBtn(){
        waitForElementToBeClickable(watchlistBtn).click();
        return new SecurityOverviewPage(getDriver());
    }

    public SentimentPage navigateToSentimentPage() {
        waitForElementToBeClickable(dropdownMenu).click();
        waitForElementToBeClickable(sentimentBtn).click();
        waitForLoadingScreen();

        return new SentimentPage(driver);
    }

    public VolatilityPage navigateToVolatilityPage() {
        waitForElementToBeClickable(dropdownMenu).click();
        waitForElementToBeClickable(volatilityBtn).click();
        waitForLoadingScreen();

        return new VolatilityPage(driver);
    }

    public ActivismPage navigateToActivismPage() {
        waitForElementToBeClickable(dropdownMenu).click();
        waitForElementToBeClickable(activismBtn).click();
        waitForLoadingScreen();

        return new ActivismPage(driver);
    }

    public Boolean addIndexToChart(String index) {
        try{ waitForLoadingScreen();
            waitForElementToAppear(indexInput);
            findElement(indexInput).sendKeys(index);
            waitForLoadingScreen();
            findElement(searchResult).click();
            return true;
        }
        catch(NoSuchElementException e)
        { e.printStackTrace();}
        catch(NullPointerException e)
        { e.printStackTrace();}
        return false;
    }

    public Boolean isIndexAdded(String index) {
        waitForLoadingScreen();
        //As I am not able to get the path for the added index in the chart
        //this is the best test method I can think of for now :
        //checks if the INDEX tag is added below the chart
        if (findElement(By.xpath("//div[contains(@class,'index')]")).getText().contains(index))
            return true;
        else
            return false;
    }

    public Boolean checkSpeicalCharacter(String companyName) {
        waitForLoadingScreen();
        findElement(searchBar).sendKeys(companyName);
        waitForElement(By.xpath("//span[contains(text(),'" + companyName + "')][contains(@class,'name')]"));
        findElement(By.xpath("//span[contains(text(),'" + companyName + "')][contains(@class,'name')]")).click();
        waitForLoadingScreen();
        findVisibleElement(profile).click();
        waitForLoadingScreen();
        if(findElement(profileModal).getText().contains("<?>")){
            return false;
        }
        return true;
    }
    public void switchToiframe(){
        WebElement _iframe = findElement(iframe);
        driver.switchTo().frame(_iframe);
        pause(500L);
    }

    public void leaveiframe(){
        driver.switchTo().defaultContent();
        pause(500L);
    }
}
