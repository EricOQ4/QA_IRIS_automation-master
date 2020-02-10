package pageobjects.user.contactPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.Page;
import pageobjects.user.activityPage.LogActivityPage;
import pageobjects.user.advancedSearchPage.AdvancedSearchPage;
import pageobjects.user.institutionPage.InstitutionPage;

/**
 * Created by ShardulB on 05-22-2019
 */

public class CorpPartDetailsPage extends Page {

    private final By iframe = By.xpath("//iframe[contains(@id, 'sencha-viewport')]");

    private final By corpPartSearchField = By.xpath("//input[@class='section-search_input']");

    private final By editCorpPart = By.xpath("//button[@class='button button--square']");
    //Menu button Options
    private final By menuButton = By.xpath("//button[@class='button button--square button--utility']");
    private final By logCPActivity = By.xpath("//div/div[contains(text(), 'Log Activity')]");
    private final By deleteParticipant = By.xpath("//div[contains(text(), 'Delete Participant')]");
    private final By confirmDelete = By.xpath("//button[contains(text(), 'delete')]");

    private final By activityCategoryDropDown = By.xpath("//div[@class='dropdown-toggle_value-label']");


    public CorpPartDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getName() {
        waitForLoadingScreen();
        return waitForAnyElementToAppear(By.xpath("//h1[@class='banner_title']")).getText();
    }

    public String getJobTitle() {
        waitForLoadingScreen();
        return waitForElementToAppear(By.xpath("//div[@class='banner_details']/span[1]")).getText();
    }

    public String getNickName() {
        waitForLoadingScreen();
        return waitForElementToAppear(By.xpath("//div[@class='banner_details']/span[2]")).getText();
    }

    public String getPrimaryPhone() {
        waitForLoadingScreen();
        return waitForElementToAppear(By.xpath("//div[1]/div[@class='corporate-participant-overview_detail--value']")).getText();
    }

    public String getSecondaryPhone() {
        waitForLoadingScreen();
        return waitForElementToAppear(By.xpath("//div[2]/div[@class='corporate-participant-overview_detail--value']")).getText();
    }

    public String getEmail() {
        waitForLoadingScreen();
        return waitForElementToAppear(By.xpath("//div[3]/div[@class='corporate-participant-overview_detail--value']")).getText();
    }


    //Index starts from 1
    public String getActivityTitleByRow(int rowIndex) {
        waitForElementToAppear(By.xpath("//tr["+rowIndex + 1 +"]/td[@class='data-table_cell data-table_cell--body data-table_cell--title data-table_cell--auto data-table_cell--left']")).getText();
        waitForLoadingScreen();
        return findElement(By.xpath("//tr["+rowIndex + 1 +"]/td[@class='data-table_cell data-table_cell--body data-table_cell--title data-table_cell--auto data-table_cell--left']")).getText();
    }
    //Index starts from 1
    public String getActivityContactByRow(int rowIndex) {
        waitForElementToAppear(By.xpath("//tr["+ rowIndex + 1 +"]/td[@class='data-table_cell data-table_cell--body data-table_cell--contact data-table_cell--wide data-table_cell--left']")).getText();
        return findElement(By.xpath("//tr["+ rowIndex + 1 +"]/td[@class='data-table_cell data-table_cell--body data-table_cell--contact data-table_cell--wide data-table_cell--left']")).getText();
    }
    //Index starts from 1
    public String getActivityLocationByRow(int rowIndex) {
        waitForElementToAppear(By.xpath("//tr["+ rowIndex + 1 +"]/td/div[@class='data-table_value data-table_value--truncate']")).getText();
        return findElement(By.xpath("//tr["+ rowIndex + 1 +"]/td/div[@class='data-table_value data-table_value--truncate']")).getText();
    }
    //Index starts from 1
    public String getActivityTagByRow(int rowIndex) {
        waitForElementToAppear(By.xpath("//tr["+ rowIndex + 1 +"]/td/div[@class='tags']")).getText();
        return findElement(By.xpath("//tr["+ rowIndex + 1 +"]/td/div[@class='tags']")).getText();
    }
    //Index starts from 1
    public String getActivityTypeByRow(int rowIndex) {
        return findElement(By.xpath("//tr["+ rowIndex + 1 +"]/td/i")).getAttribute("class");
    }


    public EditContact editCorpPart() {

        waitForLoadingScreen();
        waitForElementToAppear(editCorpPart).click();

        return new EditContact(getDriver());
    }

    public LogActivityPage menuLogActivity() {
        waitForLoadingScreen();
        waitForElementToBeClickable(menuButton).click();

        waitForElementToAppear(logCPActivity);
        waitForElementToBeClickable(logCPActivity).click();

        return new LogActivityPage(getDriver());
    }

    public CorpPartDetailsPage clickActivityCategoryDropdown() {

        waitForElementToBeClickable(activityCategoryDropDown).click();

        return this;
    }

    //Search for an activity on the Corporate Participant Details Page
    public CorpPartDetailsPage searchForActivity(String note) {

        waitForElement(corpPartSearchField);

        findVisibleElement(corpPartSearchField).sendKeys(note);
        waitForLoadingScreen();

        return this;
    }

    public ContactPage deleteCorpPart() {

        waitForElementToBeClickable(menuButton).click();
        waitForElementToBeClickable(deleteParticipant).click();

        waitForLoadingScreen();
        waitForElementToBeClickable(confirmDelete).click();
        return new ContactPage(getDriver());
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
