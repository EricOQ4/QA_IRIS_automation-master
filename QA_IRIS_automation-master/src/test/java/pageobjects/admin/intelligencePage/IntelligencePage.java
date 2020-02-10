package pageobjects.admin.intelligencePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import java.util.List;

/**
 * Created by patrickp on 2016-11-09.
 */
public class IntelligencePage extends AbstractPageObject {
    private final By newReportButton = By.cssSelector(".page-header .action-buttons .add");
    private final By intelligencePageButton = By.cssSelector("body > q4-app > div > q4-navbar > nav > div > ul > li:nth-child(3) > a > i");
    private final By companySymbolField = By.cssSelector("body > q4-app > div > div > q4-report > p-dialog > div > div.ui-dialog-content.ui-widget-content > q4-report-create > p-autocomplete > span > input");
    private final By createReportButton = By.cssSelector("button.button.button-yellow");
    private final By newReportInList = By.cssSelector("body > q4-app > div > div > q4-report > p-datatable > div > div > table > tbody > tr:nth-child(1)");
    private final By reportDropdown = By.cssSelector(".modal .ui-dialog .ui-dialog-content .ui-dropdown .ui-inputtext");
    private final By weeklyTradeSummary = By.cssSelector(".ui-dropdown-open > div:nth-child(5) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(2)");
    private final By weeklyOptionsReport = By.cssSelector(".ui-dropdown-open > div:nth-child(5) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(3)");
    private final By salesEquityAndOptions = By.cssSelector(".ui-dropdown-open > div:nth-child(5) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(4)");
    private final By searchResult = By.xpath("//li[contains(@class,'ui-autocomplete-list-item')][.//div[contains(text(), symbol )]]");
    private final By firstReportInList = By.cssSelector("tr.ui-widget-content:nth-child(1)");
    private final By firstReportDateTime = By.cssSelector("tr.ui-widget-content:nth-child(1) > td:nth-child(4)");
    private final By reportList = By.cssSelector(".ui-datatable-data");
    private final By pendingFilter = By.xpath("//button[contains(text(),'Pending')]");
    private final By readyFilter = By.xpath("//button[contains(text(),'Ready')]");
    private final By approvedFilter = By.xpath("//button[contains(text(),'Approved')]");
    private final By readyToPublishFilter = By.xpath("//button[contains(text(),'Published')]");
    private final By reportStatus = By.cssSelector("td:nth-child(4)");
    private final By reportTypeFilterDropdown = By.xpath("//nav[div[contains(text(),'Type')]]//p-dropdown");
    private final By reportTypeFilterOptions = By.className("ui-dropdown-item");
    private final By reportType = By.className("type");
    private final By companyAndReportCulumnHeader = By.cssSelector("body > q4-app > div > div > q4-report > p-datatable > div > div > table > thead > tr > th:nth-child(1)");


    public IntelligencePage(WebDriver driver) {
        super(driver);
    }

    public IntelligencePage createWeeklyTradeSummary(String symbol) {
        waitForLoadingScreen();
        findElement(newReportButton).click();
        findElement(reportDropdown).click();
        findElement(weeklyTradeSummary).click();
        pause(1000L);
        retryClick(companySymbolField);
        findElement(companySymbolField).sendKeys(symbol);
        selectTickerFromDropDown(symbol);
        //findElement(searchResult).click();
        findElement(createReportButton).click();
        waitForLoadingScreen();

        return this;
    }

    public IntelligencePage createWeeklyOptionsAnalytics(String symbol) {
        waitForLoadingScreen();
        findElement(newReportButton).click();
        findElement(reportDropdown).click();
        findElement(weeklyOptionsReport).click();
        pause(500L);
        findElement(companySymbolField).sendKeys(symbol);
        selectTickerFromDropDown(symbol);
        //findElement(searchResult).click();
        findElement(createReportButton).click();

        return this;
    }

    public IntelligencePage createSalesEquitAndOptions(String symbol) {
        waitForLoadingScreen();
        findElement(newReportButton).click();
        findElement(reportDropdown).click();
        findElement(salesEquityAndOptions).click();
        pause(500L);
        findElement(companySymbolField).sendKeys(symbol);
        selectTickerFromDropDown(symbol);
        //findElement(searchResult).click();
        findElement(createReportButton).click();

        return this;
    }

    public String getNewReport() {
        waitForLoadingScreen();
        return findElement(newReportInList).getText();
    }

    public WTSReportDetailsPage selectNewReport() {
        waitForLoadingScreen();
        driver.navigate().refresh();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(firstReportInList,"Ready"));
        findElement(firstReportInList).click();

        return new WTSReportDetailsPage(getDriver());
    }

    public String getNewReportDateTime() {
        waitForLoadingScreen();
        return findElement(firstReportDateTime).getText();
    }

    public String getEntireReportList() {
        waitForLoadingScreen();
        return findElement(reportList).getText();
    }

    public IntelligencePage showPendingReports(){
        waitForLoadingScreen();
        findElement(pendingFilter).click();
        return this;
    }

    public IntelligencePage showReadyReports(){
        waitForLoadingScreen();
        findElement(readyFilter).click();
        return this;
    }

    public IntelligencePage showApprovedReports(){
        waitForLoadingScreen();
        findElement(approvedFilter).click();
        return this;
    }

    public IntelligencePage showReadyToPublishReports(){
        waitForLoadingScreen();
        findElement(readyToPublishFilter).click();
        return this;
    }

    public boolean allReportsHaveStatus(String expected){
        pause(2000);
        List<WebElement> statuses = findElements(reportStatus);
        for (WebElement status : statuses){
            if (!status.getText().equalsIgnoreCase(expected)){
                System.out.println("Found report with status of '"+status.getText()+"' instead of "+expected);
                return false;
            }
        }
        return true;
    }

    public IntelligencePage showReportsOfType(String type){
        waitForLoadingScreen();
        findVisibleElement(reportTypeFilterDropdown).click();
        List <WebElement> options = findVisibleElements(reportTypeFilterOptions);
        for (WebElement option : options){
            if (option.getText().equalsIgnoreCase(type)){
                option.click();
                break;
            }
        }
        return this;
    }

    public boolean allReportsAreOfType(String expected){
        pause(2000);
        List<WebElement> types = findElements(reportType);
        for (WebElement type : types){
            if (!type.getText().equalsIgnoreCase(expected)){
                System.out.println("Found report of type '"+type.getText()+"' instead of "+expected);
                return false;
            }
        }
        return true;
    }
public void selectTickerFromDropDown(String symbol){
   findElement(By.xpath("//li[contains(@class,'ui-autocomplete-list-item')][.//div[contains(text(),'"+symbol+"')]]")).click();
}

    public IntelligencePage sortByCompanyReportType() {
        findElement(companyAndReportCulumnHeader).click();

        return this;
    }
}
