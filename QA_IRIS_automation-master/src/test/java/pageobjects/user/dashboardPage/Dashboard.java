package pageobjects.user.dashboardPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.briefingBooks.CreateBriefingBookModal;
import pageobjects.user.contactPage.ContactDetailsPage;
import pageobjects.user.fundPage.FundPage;
import pageobjects.user.institutionPage.InstitutionPage;
import pageobjects.user.logActivityModal.LogActivityModal;
import pageobjects.user.securityPage.SecurityOverviewPage;

import java.util.ArrayList;

public class Dashboard extends AbstractPageObject {

    // Search field on dashboardPage
    private final By searchField = By.xpath("//div[contains(@class,'app-header_search')]//input[contains(@type,'text')]");
    private final By firstCompanyInList = By.cssSelector("span:nth-child(1)");
    private final By clearSearchButton = By.cssSelector(".home-search .x-field-input .x-clear-icon");
    // Search results selectors are defined in HeaderPage.java

    // Dashboard background (to get out of focused search)
    private final By dashboardGeneral = By.className("x-dock-body");

    // Big and small share price shown on dashboardPage
    private final By bigSharePrice = By.id("ext-home-stock-1");
    private final By smallSharePrice = By.cssSelector(".company-symbol");
    private final By alternateTickers = By.className("company-menu-item");

    // Build report icon

    private final By buildReportDashboardButton = By.xpath("//div[contains(@class, 'x-button x-iconalign-center x-layout-box-item x-stretched')]/span[contains(@class, 'x-button-icon x-shown q4i-reports-2pt')]");

    // Log Note icon (unique selectors for dashboard)
    private final By logNoteIcon = By.id("ext-button-10");
    private final By logPhoneIcon = By.id("ext-button-11");
    private final By logEmailIcon = By.id("ext-button-12");
    private final By logMeetingIcon = By.id("ext-button-13");
    private final By logRoadshowIcon = By.id("ext-button-14");

    // Log activity button
    private final By logActivityButton = By.xpath("//button[@class='app-header_button app-header_button--activity']");

    public Dashboard(WebDriver driver) {
        super(driver);
    }

    public Dashboard searchFor(String companyName) {
        waitForElementToAppear(searchField);
        findElement(searchField).click();
        findElement(searchField).clear();
        findElement(searchField).sendKeys(companyName);

        return this;
    }

    public SecurityOverviewPage selectCompanyFromSearch() {
        waitForElementToAppear(firstCompanyInList);
        retryClick(firstCompanyInList);

        return new SecurityOverviewPage(getDriver());
    }

    public Dashboard clearSearchField() {
        waitForElementToAppear(dashboardGeneral);
        findElement(dashboardGeneral).click();
        pause(2000L);
        retryClick(clearSearchButton);

        return this;
    }

    public String getSearchFieldText() {
        waitForElementToAppear(searchField);
        return findElement(searchField).getAttribute("placeholder");
    }

    public SecurityOverviewPage clickBigSharePrice() {
        wait.until(ExpectedConditions.elementToBeClickable(bigSharePrice));
        findElement(bigSharePrice).click();

        return new SecurityOverviewPage(getDriver());
    }

    public Dashboard clickSmallSharePrice() {
        wait.until(ExpectedConditions.elementToBeClickable(smallSharePrice));
        findElement(smallSharePrice).click();

        return this;
    }

    private String getSymbol(){
        String[] completeText;
        WebElement element = findElement(smallSharePrice);
        completeText=element.getText().split("\\|");

        return completeText[0];
    }

    public boolean checkAlternateSymbols(){
        String mainSymbol = getSymbol();
        ArrayList<WebElement> alternateTickerElements =new ArrayList<>(findElements(alternateTickers));

        for(WebElement element : alternateTickerElements){
            if(!element.getText().contains(mainSymbol)){
                return false;
            }
        }

        return true;
    }

    public SecurityOverviewPage selectSecurityFromSearchResults(String security) {
        if (selectItemFromSearchResults(security, genericSearchResults)) {
            return new SecurityOverviewPage(driver);
        } else return null;
    }

    public InstitutionPage selectInstitutionFromSearchResults(String institution) {
        if (selectItemFromSearchResults(institution, genericSearchResults)) {
            return new InstitutionPage(driver);
        } else return null;
    }

    public ContactDetailsPage selectContactFromSearchResults(String contact) {
        if (selectItemFromSearchResults(contact, genericSearchResults)) {
            return new ContactDetailsPage(driver);
        } else return null;
    }

    public FundPage selectFundFromSearchResults(String fund) {
        if (selectItemFromSearchResults(fund, genericSearchResults)) {
            return new FundPage(driver);
        } else return null;
    }

    private Boolean selectItemFromSearchResults(String item, By selector) {
        waitForElementToAppear(selector);
        ArrayList<WebElement> searchResults = new ArrayList<>(findElements(selector));

        for (WebElement row: searchResults) {
            if (row.getText().contains(item)) {
                row.click();
                return true;
            }
        }

        return false;
    }

    public CreateBriefingBookModal selectCreateBriefingBook() {
        waitForElementToBeClickable(buildReportDashboardButton).click();

        return new CreateBriefingBookModal(getDriver());
    }
    /*
    New Dashboard does not have icons for these buttons, but a single 'Log Activity' button

    public LogActivityModal logNote() {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(logNoteIcon));
        findElement(logNoteIcon).click();

        return new LogActivityModal(getDriver());
    }

    public LogActivityModal logPhoneNote() {
        wait.until(ExpectedConditions.elementToBeClickable(logPhoneIcon));
        findElement(logPhoneIcon).click();

        return new LogActivityModal(getDriver());
    }

    public LogActivityModal logEmailNote() {
        wait.until(ExpectedConditions.elementToBeClickable(logEmailIcon));
        findElement(logEmailIcon).click();

        return new LogActivityModal(getDriver());
    }

    public LogActivityModal logMeetingNote() {
        wait.until(ExpectedConditions.elementToBeClickable(logMeetingIcon));
        findElement(logMeetingIcon).click();

        return new LogActivityModal(getDriver());
    }

    public LogActivityModal logRoadshowNote() {
        wait.until(ExpectedConditions.elementToBeClickable(logRoadshowIcon));
        findElement(logRoadshowIcon).click();

        return new LogActivityModal(getDriver());
    }
    */
    public LogActivityModal logActivity(){
        waitForDashboardToLoad();
        waitForElementToBeClickable(logActivityButton).click();

        return new LogActivityModal(getDriver());
    }
}
