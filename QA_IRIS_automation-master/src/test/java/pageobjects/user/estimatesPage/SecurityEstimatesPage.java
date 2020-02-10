package pageobjects.user.estimatesPage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;
import pageobjects.user.Calendar;
import pageobjects.user.contactPage.ContactDetailsPage;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by patrickp on 2016-08-24.
 */
public class SecurityEstimatesPage extends AbstractPageObject{

    private final By tabTitle = By.cssSelector(".company-header .menu-button .x-button-label");

    //anchor links
    private final By researchAnchorLink = By.xpath("//div[contains(@class,'anchor-toolbar')]//span[contains(string(),'Research')]");

    // for consensus
    private final By earningsPerShareTab = By.xpath("//div[contains(@class, 'estimates-consensus')]//span[@class='x-button-label'][text()='Earnings Per Share']");
    private final By incomeStatementTab = By.xpath("//div[contains(@class, 'estimates-consensus')]//span[@class='x-button-label'][text()='Income Statement']");
    private final By balanceSheetTab = By.xpath("//div[contains(@class, 'estimates-consensus')]//span[@class='x-button-label'][text()='Balance Sheet']");
    private final By cashFlowTab = By.xpath("//div[contains(@class, 'estimates-consensus')]//span[@class='x-button-label'][text()='Cash Flow']");
    private final By perShareItemsTab = By.xpath("//div[contains(@class, 'estimates-consensus')]//span[@class='x-button-label'][text()='Per Share Items']");

    private final By earningsPerShareTable = By.xpath("//div[contains(@id, 'ext-consensus-earnings-per-share')]//div[contains(@class, 'list-item')]");
    private final By incomeStatementTable = By.xpath("//div[contains(@id, 'ext-consensus-income-statement')]//div[contains(@class, 'list-item')]");
    private final By balanceSheetTable = By.xpath("//div[contains(@id, 'ext-consensus-balance-sheet')]//div[contains(@class, 'list-item')]");
    private final By cashflowTable = By.xpath("//div[contains(@id, 'ext-consensus-cash-flow')]//div[contains(@class, 'list-item')]");
    private final By perShareItemsTable = By.xpath("//div[contains(@id, 'ext-consensus-per-share-items')]//div[contains(@class, 'list-item')]");

    private final By earningsPerShareValues = By.xpath("//div[contains(@id, 'ext-consensus-earnings-per-share')]//div[contains(@class, 'x-grid-cell-align-right')][div[text()]]");
    private final By incomeStatementValues = By.xpath("//div[contains(@id, 'ext-consensus-income-statement')]//div[contains(@class, 'x-grid-cell-align-right')][div[text()]]");
    private final By balanceSheetValues = By.xpath("//div[contains(@id, 'ext-consensus-balance-sheet')]//div[contains(@class, 'x-grid-cell-align-right')][div[text()]]");
    private final By cashFlowValues = By.xpath("//div[contains(@id, 'ext-consensus-cash-flow')]//div[contains(@class, 'x-grid-cell-align-right')][div[text()]]");
    private final By perShareItemsValues = By.xpath("//div[contains(@id, 'ext-consensus-per-share-items')]//div[contains(@class, 'x-grid-cell-align-right')][div[text()]]");
    private final By headerEPSAnalystNum = By.xpath("//div[contains(@class,'eps')]//div[contains(@class,'row')][1]//div[contains(@class,'details')][div[contains(text(),'Analyst')]]");
    private final By headerSalesAnalystNum =By.xpath("//div[span[contains(text(),'Sales')]]//div[contains(@class,'row')][1]//div[contains(@class,'details')][div[contains(text(),'Analyst')]]");
    // for broker detail
    private final By brokerEarningsPerShareTab = By.xpath("//div[contains(@id, 'ext-estimates-broker-detail')]//span[@class='x-button-label'][text()='Earnings Per Share']");
    private final By brokerSalesTab= By.xpath("//div[contains(@id, 'ext-estimates-broker-detail')]//span[@class='x-button-label'][text()='Sales']");
    private final By salesTab = By.xpath("//div[contains(@id, 'ext-estimates-broker-detail')]//span[@class='x-button-label'][text()='Sales']");
    private final By brokerDetailsList= By.xpath("//div[contains(@class,'broker-detail-list')]//div[contains(@class,'row')]");

    private final By earningsPerShareBarChart = By.xpath("//div[contains(@id, 'broker-detail-earnings-per-share')]//div[contains(@class, 'bar-chart')]");
    private final By salesBarChart = By.xpath("//div[contains(@id, 'broker-detail-sales')]//div[contains(@class, 'bar-chart')]");
    private final By brokerDetailsSalesMeanNum = By.xpath("//div[@id='ext-broker-detail-sales-1']//div[span[contains(text(),'Mean')]]");

    private final By brokerEarningsPerShareTable = By.xpath("//div[contains(@id, 'ext-earnings-per-share-list-1')]//div[contains(@class, 'list-item')]");
    private final By salesTable = By.xpath("//div[contains(@id, 'ext-sales-list')]//div[contains(@class, 'list-item')]");

    private final By brokerEarningsPerShareNumValues = By.xpath("//div[contains(@id, 'ext-earnings-per-share-list')]//div[contains(@class, 'list-item')]//div[@class='column']");
    private final By salesNumValues = By.xpath("//div[contains(@id, 'ext-sales-list')]//div[contains(@class, 'list-item')]//div[@class='column']");

    private final By brokerDetailDateSelector = By.className("broker-details-date-selector");
    private final By brokerDetailDateOptions = By.xpath("//div[contains(@class, 'estimates-date-selector-dropdown')]//div[contains(@id, 'ext-simplelistitem')]");
    private final By brokerDetailEPSMeanNum =By.xpath("//div[@id='ext-broker-detail-earnings-per-share-1']//div[span[contains(text(),'Mean')]]");

    // for historical
    private final By historicalTable = By.xpath("//div[contains(@class, 'estimates-historical')]//div[contains(@class, 'x-dataview')]");
    private final By historicalTableValues = By.xpath("//div[contains(@class, 'estimates-historical')]//div[contains(@class, 'x-dataview')]/*[@class='view-list-item'][preceding-sibling::div]");

    //for research column headers
    private final By researchDateColumnHeader = By.xpath("//div[contains(@class,'estimates-research-list')]//span[contains(string(),'Date')]");
    private final By researchHeadlineColumnHeader = By.xpath("//div[contains(@class, 'estimates-research-list')]//span[text()='Headline']");
    private final By researchContributorColumnHeader = By.xpath("//div[contains(@class, 'estimates-research-list')]//span[text()='Contributor Firm']");
    private final By researchAnalystColumnHeader = By.xpath("//div[contains(@class, 'estimates-research-list')]//span[text()='Analyst']");
    private final By firstResearchDate = By.xpath("//div[contains(@class,'estimates-research-list')]//div[1][contains(@class,'x-dataview-item')]//div[contains(@class,'column date')]");

    // research table data
    private final By researchSearchBar = By.xpath("//div[contains(@id, 'ext-estimates-research-1')]//input[@name='searchQuery']");
    private final By researchHeadlines = By.xpath("//div[contains(@class, 'estimates-research-list')]//div[contains(@class, 'column headline')]");
    private final By researchContributors = By.xpath("//div[contains(@class, 'estimates-research-list')]//div[contains(@class, 'column contributor')]");
    private final By researchAnalysts = By.xpath("//div[contains(@class, 'estimates-research-list')]//div[contains(@class, 'column analyst')]");

    private final By researchShowMore = By.xpath("//span[contains(@class, 'q4i-arrow-down-2pt')]");

    //for calendars
    private final By startTimeSelector = By.xpath("//input[contains(@name,'startDate')]");
    private final By endTimeSelector = By.xpath("//input[contains(@name,'endDate')]");
    private final By previousMonthButton = By.xpath("//div[@class='pmu-prev pmu-button']");
    private final By selectedMonth = By.xpath("//div[@class='pmu-month pmu-button']");
    private final By selectedDay = By.xpath("//div[@class='pmu-days']/div[@class='pmu-button'][11]");
    private final By dateFilterButton = By.xpath("//span[contains(string(),'GO')]");

    //for unsubscribers
    private final By unsubscribeMessage = By.xpath("//h1[text()=\"Looks like you haven't subscribed to this feature. Interested?\"]");

    public SecurityEstimatesPage(WebDriver driver) {
        super(driver);
    }

    public String getEstimatesTabTitle() {

        waitForLoadingScreen();
        return waitForElementToAppear(tabTitle).getText();
    }

    private SecurityEstimatesPage selectTab(By tab) {
        waitForLoadingScreen();
        waitForElementToBeClickable(tab).click();
        waitForLoadingScreen();
        return this;
    }

    public Boolean checkConsensusTabs() {
        int numOfTabs = 5;
        By[] tabs = {incomeStatementTab, balanceSheetTab, cashFlowTab, perShareItemsTab, earningsPerShareTab};
        By[] tables = {incomeStatementTable, balanceSheetTable, cashflowTable, perShareItemsTable, earningsPerShareTable};
        By[] values = {incomeStatementValues, balanceSheetValues, cashFlowValues, perShareItemsValues, earningsPerShareValues};

        waitForLoadingScreen();

        for (int i=0; i<numOfTabs; i++) {
            selectTab(tabs[i]);
            waitForLoadingScreen();
            if (!waitForElement(tables[i]).isDisplayed()) {
                return false;
            }
            for (WebElement value : findElements(values[i])) {
                if (!valueIsAValidNumber(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Boolean checkBrokerDetailTabs() {
        int numOfTabs = 2;
        By[] tabs = {salesTab, brokerEarningsPerShareTab};
        By[] barCharts = {salesBarChart, earningsPerShareBarChart};
        By[] tables = {salesTable, brokerEarningsPerShareTable};
        By[] numValues = {salesNumValues, brokerEarningsPerShareNumValues};

        waitForLoadingScreen();

        for (int i=0; i<numOfTabs; i++) {
            selectTab(tabs[i]);
            waitForLoadingScreen();
            if (!waitForElement(barCharts[i]).isDisplayed()) {
                System.out.println("Did not display correct bar chart");
                return false;
            }
            if (!waitForElement(tables[i]).isDisplayed()) {
                System.out.println("Did not display correct table");
                return false;
            }
            for (WebElement value : findElements(numValues[i])) {
                if (!valueIsAValidNumber(value)) {
                    return false;
                }
            }
        }
        return true;
    }




    public SecurityEstimatesPage changeBrokerDetailsDate(int i) {
        waitForLoadingScreen();
        waitForElementToBeClickable(brokerDetailDateSelector).click();
        waitForAnyElementToAppear(brokerDetailDateOptions);
        findVisibleElements(brokerDetailDateOptions).get(i).click();
        waitForLoadingScreen();
        return this;
    }

    public String getNthRowText(int n) {
        waitForLoadingScreen();
        waitForAnyElementToAppear(brokerEarningsPerShareTable);
        return findVisibleElements(brokerEarningsPerShareTable).get(n).getText();
    }

    public String getNthBroker(int n) {
        waitForLoadingScreen();
        waitForAnyElementToAppear(brokerEarningsPerShareTable);
        return findVisibleElements(brokerEarningsPerShareTable).get(n).findElement(By.xpath(".//div[@class='column auto name']")).getText();
    }

    public String getNthAnalyst(int n) {
        waitForLoadingScreen();
        waitForAnyElementToAppear(brokerEarningsPerShareTable);
        return findVisibleElements(brokerEarningsPerShareTable).get(n).findElement(By.xpath(".//div[@class='column analyst']")).getText();
    }

    public ContactDetailsPage selectNthAnalyst(int n) {
        waitForLoadingScreen();
        waitForAnyElementToAppear(brokerEarningsPerShareTable);
        findVisibleElements(brokerEarningsPerShareTable).get(n).click();
        return new ContactDetailsPage(driver);
    }

    public Boolean checkHistoricalTable() {
        waitForLoadingScreen();
        waitForElement(historicalTable);
        return findVisibleElements(historicalTable).size() > 0;
    }

    public Boolean checkHistoricalTableValues() {
        waitForLoadingScreen();

        waitForElementToAppear(historicalTableValues);

        for (WebElement value : findVisibleElements(historicalTableValues)) {
            if (!valueIsAValidNumber(value)) {
                return false;
            }
        }

        return true;
    }

    public SecurityEstimatesPage searchForResearch(String query) {
        waitForLoadingScreen();
        waitForElementToAppear(researchSearchBar).sendKeys(query);
        findElement(researchSearchBar).sendKeys(Keys.ENTER);
        waitForElementToRest(researchHeadlines, 1000L);
        return this;
    }

    public int getNumberOfResearch() {
        waitForLoadingScreen();
        waitForElement(researchHeadlines);
        waitForElementToRest(researchHeadlines, 200L);
        return findVisibleElements(researchHeadlines).size();
    }

    public WebElement getNthResearchHeadline(int n) {
        waitForLoadingScreen();
        waitForElement(researchHeadlines);
        return findVisibleElements(researchHeadlines).get(n);
    }

    public WebElement getLastResearchHeadline() {
        waitForLoadingScreen();
        waitForElement(researchHeadlines);
        List<WebElement> elements = findVisibleElements(researchHeadlines);
        return elements.get(elements.size()-1);
    }


    public Calendar filterDate(Calendar calendar) {
        waitForLoadingScreen();
        calendar.selectStartDate(startTimeSelector, previousMonthButton, selectedMonth, selectedDay);
        calendar.selectEndDate(endTimeSelector, previousMonthButton, selectedMonth, selectedDay);
        calendar.filter(dateFilterButton);
        pause(500L);
        // helps keep track of which days were selected
        calendar.print();
        return calendar;
    }

    public boolean sortByDateRange(Calendar calendar) {
        waitForLoadingScreen();
        // sorting the date by earliest to latest
        findVisibleElement(researchDateColumnHeader).click();
        pause(200L);
        boolean Sorted = true;
        if (!calendar.EarliestDateWithinRange(findVisibleElement(firstResearchDate).getText())) {
            System.out.println("Earliest date in the table is earlier than the selected end time");
            Sorted = false;
        }

        // sorting the date by latest to earliest
        findVisibleElement(researchDateColumnHeader).click();
        pause(200L);
        if (!calendar.latestDateWithinRange(findVisibleElement(firstResearchDate).getText())) {
            System.out.println("Latest date in the table is later than the selected end time");
            Sorted = false;
        }
        return Sorted;
    }

    public boolean sortByHeadline() {
        return sortBy(researchHeadlineColumnHeader, researchHeadlines);
    }

    public boolean sortByContributor() {
        return sortBy(researchContributorColumnHeader, researchContributors);
    }

    public boolean sortByAnalyst() {
        return sortBy(researchAnalystColumnHeader, researchAnalysts);
    }

    private boolean sortBy(By columnHeader, By column) {
        waitForLoadingScreen();
        //sort ascending
        waitForElementToBeClickable(columnHeader).click();
        /*pause(200L);
        if (!elementsAreAlphaUpSorted(findVisibleElements(column))) {
            return false;
        }*/

        //sort descending
        waitForElementToBeClickable(columnHeader).click();
        pause(200L);
        if (!elementsAreAlphaDownSorted(findVisibleElements(column))) {
            return false;
        }

        return true;
    }

    public SecurityEstimatesPage showMoreResearch() {
        waitForLoadingScreen();
        waitForElement(researchShowMore);
        if (!findVisibleElements(researchShowMore).isEmpty()) {
            findVisibleElement(researchShowMore).click();
        } else {
            System.out.println("No show more button");
        }
        waitForElementToRest(researchShowMore, 500L);
        waitForLoadingScreen();
        return this;
    }

    public String getReportPdfContent(String title) {
        try {
            title = title.replaceAll(">", "-"); // Some characters are replaced in the download title
            title = title.replaceAll("/", "-"); // Some characters are replaced in the download title
            //title = title.replaceAll("\\|", "-"); // Some characters are replaced in the download title


            URL reportUrl;
            reportUrl = getPdfUrl(title);
            BufferedInputStream briefingBookFile;
            try {
               briefingBookFile = new BufferedInputStream(reportUrl.openStream());
            }
            catch(FileNotFoundException e){
                reportUrl = getNonPdfUrl(title);
                briefingBookFile = new BufferedInputStream(reportUrl.openStream());
            }
            PDDocument document = PDDocument.load(briefingBookFile);
                String contents = new PDFTextStripper().getText(document);
            document.close();
            briefingBookFile.close();
            return contents;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private URL getPdfUrl(String title) {
        try {
            return new URL("file://" + System.getProperty("user.home") + "/Downloads/" + title + ".pdf");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

    }
    private URL getNonPdfUrl(String title) {
        try {
            return new URL("file://" + System.getProperty("user.home") + "/Downloads/" + title.replace(" ", "%20"));
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private Boolean valueIsAValidNumber(WebElement element) {
        String value = element.getText();
        if (value.equals("-")) {
            return true;
        } else {
            try {
                Double.valueOf(value.replaceAll("[MB]", ""));
                return true;
            } catch (NumberFormatException e) {
                System.out.println("Value is not a valid number: " + value);
                return false;
            }
        }
    }

    public int returnHeaderEpsAnalystNumber(){

        return Integer.parseInt(waitForElementToAppear(headerEPSAnalystNum).getText().replaceAll("[^\\d]", ""));
    }
    public int returnHeaderSalesAnalystNumber(){
        return Integer.parseInt(waitForElementToAppear(headerSalesAnalystNum).getText().replaceAll("[^\\d]", ""));
    }

    public int returnBrokerDetailsEpsAnalystNumber(){
        selectTab(brokerEarningsPerShareTab);
        waitForLoadingScreen();
        return Integer.parseInt(waitForElementToAppear(brokerDetailEPSMeanNum).getText().replaceAll("[^\\d]", ""));
    }
    public int returnBrokerDetailsSalesAnalystNumber(){
        selectTab(brokerSalesTab);
        waitForLoadingScreen();
        return Integer.parseInt(waitForElementToAppear(brokerDetailsSalesMeanNum).getText().replaceAll("[^\\d]", ""));
    }

    public int returnNumItemsInEPSBrokerList(){
        selectTab(brokerEarningsPerShareTab);
        waitForLoadingScreen();
        return findVisibleElements(brokerDetailsList).size();
    }

    public int returnNumItemsInSalesBrokerList(){
        selectTab(brokerSalesTab);
        waitForLoadingScreen();
        return findVisibleElements(brokerDetailsList).size();

    }

    public Boolean isUnsubscribed(){
        waitForLoadingScreen();
        return doesElementExist(unsubscribeMessage);
    }
}
