package pageobjects.user.logActivityModal;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.dashboardPage.Dashboard;

/**
 * Created by patrickp on 2016-08-09.
 */
public class LogActivityModal extends AbstractPageObject {

    private final By commentField = By.name("title");
    private final By typeNoteField = By.name("body");
    private final By tagField = By.name("tag");
    private final By locationField = By.xpath("//input[contains(@placeholder,'Location')]");
    private final By postButton = By.xpath("//span[contains(string(),'POST')]");
    private final By nameField = By.cssSelector(".note-participants-field .participants-field .x-form-field");
    private final By cancelNoteButton = By.xpath("//*[contains(text(), 'Cancel')]");
    private final By linkDropdown = By.cssSelector(".note-link-field .toggle-field .x-toggle.x-toggle-off");
    private final By keywordField = By.cssSelector(".note-link-field .search-field.x-field .x-form-field");
    private final By institutionSearchResult = By.cssSelector(".link-field-result .result-item");
    private final By fundIcon = By.xpath("//div[contains(@class,'new-note-inner')]//div[contains(@class,'x-field-radio') and .//span[contains(text(),'Fund')]]");
    private final By contactIcon = By.xpath("//div[contains(@class,'new-note-inner')]//div[contains(@class,'x-field-radio') and .//span[contains(text(),'Contact')]]");
    private final By phoneTab = By.id("ext-radiofield-2");
    private final By emailTab = By.id("ext-radiofield-3");
    private final By meetingTab = By.id("ext-radiofield-4");
    private final By roadshowTab = By.id("ext-radiofield-5");

    public LogActivityModal(WebDriver driver) {
        super(driver);
    }

    // TODO Remove pauses from these tests and replace with something that won't break when things are running slowly

    public LogActivityModal enterNoteDetails(String comment, String note, String tag) {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(commentField));
        retryClick(findElement(commentField));
        findElement(commentField).sendKeys(comment);
        findElement(typeNoteField).sendKeys(note);
        findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.RETURN);
        wait.until(ExpectedConditions.elementToBeClickable(postButton));
        pause(2000L);

        return this;
    }

    public LogActivityModal enterPhoneNoteDetails(String comment, String name, String note, String tag) {
        retryClick(findElement(commentField));
        findElement(commentField).sendKeys(comment);
        findElement(nameField).click();
        findElement(nameField).sendKeys(name);
        findElement(nameField).sendKeys(Keys.ENTER);
        findElement(typeNoteField).sendKeys(note);
        findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.RETURN);
        pause(1500L);

        return this;
    }

    public LogActivityModal enterEmailNoteDetails(String comment, String note, String tag) {
        retryClick(findElement(commentField));
        findElement(commentField).sendKeys(comment);
        findElement(typeNoteField).sendKeys(note);
        findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.RETURN);
        pause(2000L);

        return this;
    }

    public LogActivityModal enterMeetingDetails(String comment, String name, String note, String tag) {
        retryClick(findElement(commentField));
        findElement(commentField).sendKeys(comment);
        findElement(nameField).sendKeys(name);
        findElement(typeNoteField).sendKeys(note);
        findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.RETURN);
        pause(2000L);

        return this;
    }

    public LogActivityModal enterRoadshowDetails(String title, String location, String tag){
        //The waitForLoadingScreen() makes the test a bit slower than I'd like
        //But at least now it's waiting for something instead of pause()
        retryClick(findElement(commentField));
        findElement(commentField).sendKeys(title);
        findElement(locationField).sendKeys(location);
        waitForLoadingScreen();
        findElement(locationField).sendKeys(Keys.ENTER);
        findElement(tagField).sendKeys(tag);
        waitForLoadingScreen();
        findElement(tagField).sendKeys(Keys.RETURN);

        return this;
    }

    public Dashboard cancelNote() {
        pause(500L);
        waitForElementToAppear(cancelNoteButton);
        findElement(cancelNoteButton).click();
        pause(500L);

        return new Dashboard(getDriver());
    }

    public LogActivityModal linkNoteToInstitution(String institution) {
        findElement(linkDropdown).click();
        findElement(keywordField).sendKeys(institution);
        findElement(institutionSearchResult).click();
        waitForElementToAppear(linkDropdown);
        findElement(linkDropdown).click();

        return this;
    }

    public LogActivityModal linkNoteToFund(String fund) {
        findElement(linkDropdown).click();
        wait.until(ExpectedConditions.elementToBeClickable(fundIcon));
        findElement(fundIcon).click();
        findElement(keywordField).sendKeys(fund);
        findElement(institutionSearchResult).click();
        waitForElementToAppear(linkDropdown);
        findElement(linkDropdown).click();

        return this;
    }

    public LogActivityModal linkNoteToContact(String contact) {
        findElement(linkDropdown).click();
        wait.until(ExpectedConditions.elementToBeClickable(contactIcon));
        findElement(contactIcon).click();
        findElement(keywordField).sendKeys(contact);
        pause(1000L);
        findElement(institutionSearchResult).click();
        waitForElementToAppear(linkDropdown);
        findElement(linkDropdown).click();

        return this;
    }

    public LogActivityModal choosePhoneTab() {
        findElement(phoneTab).click();
        wait.until(ExpectedConditions.elementToBeClickable(postButton));
        findElement(postButton).click();

        return this;
    }

    public LogActivityModal chooseEmailTab() {
        findElement(emailTab).click();
        wait.until(ExpectedConditions.elementToBeClickable(postButton));
        findElement(postButton).click();

        return this;
    }

    public LogActivityModal chooseMeetingTab() {
        findElement(meetingTab).click();
        wait.until(ExpectedConditions.elementToBeClickable(postButton));
        findElement(postButton).click();

        return this;
    }

    public LogActivityModal chooseRoadshowTab() {
        findElement(roadshowTab).click();
        wait.until(ExpectedConditions.elementToBeClickable(postButton));
        findElement(postButton).click();

        return this;
    }

    public LogActivityModal postActivity() {
        waitForElement(postButton);
        scrollToElement(postButton);
        findElement(postButton).click();

        return this;
    }
}
