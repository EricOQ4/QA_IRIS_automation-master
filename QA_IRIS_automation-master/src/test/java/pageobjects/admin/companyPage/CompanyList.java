package pageobjects.admin.companyPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-09-16.
 */
public class CompanyList extends AbstractPageObject {

    private final By addCompanyButton = By.cssSelector(".page-header .action-buttons .add");
    private final By companyField = By.cssSelector(".modal .ui-dialog .ui-dialog-content .ui-autocomplete.auto-complete-search .ui-inputtext");
    private final By saveButton = By.xpath("//button[contains(text(),'Save')]");
    private final By cancelButton = By.cssSelector(".button-no-background");
    private final By firstCompany = By.cssSelector(".q4-list .ui-datatable tr.ui-widget-content td");
    private final By companyName = By.cssSelector("body > q4-app > div > div > q4-organization > p-datatable > div > div > table > tbody > tr:nth-child(1) > td:nth-child(2)");
    private final By searchField = By.cssSelector(".search.dark .search-input");
    private final By companyList = By.cssSelector(".ui-datatable-data");
    private final By companyHeaderName = By.cssSelector(".page-header .page-title .details h2");
    private final By peerList = By.cssSelector(".ui-datatable table");
    private final By deletePeerButton = By.cssSelector("body > q4-app > div > div > q4-organization-details > q4-organization-peers > p-datatable > div > div > table > tbody > tr.ui-widget-content.ui-datatable-even > td.action-buttons > span > button.square-button.button-no-background.remove");
    private final By firstSearchResult = By.cssSelector("body > q4-app > div > div > q4-organization > p-dialog > div > div.ui-dialog-content.ui-widget-content > q4-organization-create > p-autocomplete > span > div > ul > li:nth-child(1)");
    private final By modalX = By.xpath("/html/body/q4-app/div/div/q4-organization/p-dialog/div/div[1]/a");
    private final By tickerList = By.cssSelector(".ui-datatable tbody");
    private final By confirmDeleteButton = By.xpath("/html/body/q4-app/div/div/q4-organization-details/q4-organization-peers/p-datatable/div/div/table/tbody/tr/td[4]/span/ng-component/p-dialog/div/div[2]/div/button[2]");

    public CompanyList(WebDriver driver) {
        super(driver);
    }

    public CompanyList addNewCompany(String companyName) {
        searchForNewCompany(companyName);
        findElement(firstSearchResult).click();
        findElement(saveButton).click();

        return this;
    }

    public CompanyList searchForNewCompany(String companyName){
        wait.until(ExpectedConditions.elementToBeClickable(addCompanyButton));
        findElement(addCompanyButton).click();
        findElement(companyField).sendKeys(companyName);
        pause(500L);
        return this;
    }

    public CompanyList dismissTickerDropdown(){
        WebElement searchField = findElement(companyField);
        Actions action = new Actions(driver);
        action.doubleClick(searchField).perform();
        return this;

    }

    public boolean isSaveButtonEnabled(){
        return findElement(saveButton).isEnabled();
    }

    public CompanyList triggerAddCompanyModal() {
        pause(500L);
        findElement(addCompanyButton).click();

        return this;
    }

    public CompanyList cancelAddCompany() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        findElement(cancelButton).click();
        pause(5000L);

        return this;
    }

    public CompanyDetailsPage selectFirstCompanyInList() {
        pause(1000L);
        findElement(firstCompany).click();

        return new CompanyDetailsPage(getDriver());
    }

    public String getFirstCompanyName() {
        return findElement(companyName).getText();
    }

    public CompanyList searchForCompany(String companyName) {
        findElement(searchField).sendKeys(companyName);
        waitForLoadingScreen();

        return this;
    }

    public String getCompanyList() {
        waitForLoadingScreen();
        return findElement(companyList).getText();
    }

    public String getCompanyName() {
        waitForLoadingScreen();
        return findElement(companyHeaderName).getText();
    }

    public String getPeerList() {
        waitForLoadingScreen();
        return findElement(peerList).getText();
    }

    public CompanyList removePeer() {
        findElement(deletePeerButton).click();
        waitForElementToAppear(confirmDeleteButton);
        findElement(confirmDeleteButton).click();

        return this;
    }

    public CompanyList exitAddCompanyModal() {
        waitForLoadingScreen();
        findElement(modalX).click();

        return this;
    }

    public String getTickerList() {
        return findElement(tickerList).getText();
    }
}