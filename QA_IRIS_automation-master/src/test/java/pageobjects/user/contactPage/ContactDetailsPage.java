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
import pageobjects.user.logActivityModal.LogActivityModal;

import java.util.List;

/**
 * Created by patrickp on 2016-08-08.
 */
/**
 * Updated by abbyl on 2019-04-17.
 */

public class ContactDetailsPage extends Page {

    private final By iframe = By.xpath("//iframe[contains(@id, 'sencha-viewport')]");

    private final By contactName = By.xpath("//div[@class='contact-name']");
    private final By contactIcon = By.cssSelector(".contact-header .icon.q4i-contact-2pt");

    private final By contactDropDown = By.cssSelector(".contact-page .utility-menu .more-button");
    private final By contactDropdownPostAdd = By.cssSelector(".contact-page .utility-menu .more-button");
    private final By addOption = By.xpath("//*[contains(text(), 'Add to Contacts')]");
    private final By favIcon = By.cssSelector(".contact-page .contact-header .contact-favorite");
    private final By removeFromContacts = By.xpath("//*[contains(text(), 'Remove from Contacts')]");
    private final By addTag = By.cssSelector(".tags-view .tag.add-button");
    private final By tagInputField = By.cssSelector(".tags-modal-list .tag-field input");
    private final By contactTags = By.cssSelector(".contact-hero-banner .tags-view");
    private final By institutionName = By.cssSelector(".contact-background .informational-cards .card-header h1");
    private final By managedFunds = By.id("ext-tab-2");
    private final By fundTableNames = By.cssSelector(".q4-list .x-dataview-container");
    private final By tagIcon = By.cssSelector(".x-dataview-inlineblock .x-dataview-item, .x-dataview-inlineblock .x-data-item");
    private final By logActivityOption = By.xpath("//*[contains(text(), 'Log an Activity')]");
    private final By noteDetails = By.xpath("//div[contains(@class, 'x-unsized x-dataview-container')]/div[contains(@class, 'x-dataview-item entity-note')]/div[contains(@class, 'details')]");
    private final By createTearSheet = By.xpath("//*[contains(text(), 'Create Tear Sheet')]");
    private final By reportTitle = By.cssSelector(".report-create .x-input-el");
    private final By createButton = By.cssSelector(".report-create .submit-button");
    private final By cancelButton = By.cssSelector(".report-create .cancel-button");
    private final By targetIcon = By.className("q4i-savedtargets-2pt");
    private final By markTarget = By.xpath("//*[contains(text(), 'Mark as Target')]");
    private final By removeTarget = By.xpath("//*[contains(text(), 'Remove from Targets')]");
    private final By postedDate = By.cssSelector(".entity-note-list .details .create-date");
    private final By generateButton = By.cssSelector(".q4-modal.briefing-book-generate.q4-form .action-buttons .form-button.yellow");
    private final By activityChartTitle = By.xpath("//div[contains(@class,'activity-chart-container')]//h3[contains(text(),'Ownership Activity')]");
    private final By activityChart = By.xpath("//div[contains(@class,'activity-chart-container')]//div[contains(@class,'activity-chart')]");
    // Add contact modal and confirmation
    private final By contactListSave = By.cssSelector(".add-contact-to-list .modal-btns-panel .form-button");
    private final By okayConfirmationButton = By.cssSelector(".q4-message-modal .x-button.primary");

    //delete contact manually
    private final By searchField = By.xpath("//input[contains(@placeholder,'Search by person')]");
    private final By utilityMenu = By.xpath("//div[@class='x-inner contact-page-inner']//span[contains(@class,'x-button-icon x-shown q4i-utility-4pt')]");
    private final By buttonDeleteCustomContact = By.xpath("//span[contains(text(),'Delete Custom Contact')]");
    private final By buttonYes = By.xpath("//span[contains(text(),'Yes')]");


    public ContactDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getActivityDate() {
        return waitForElementToAppear(postedDate).getText();
    }

    public String getContactName() {
        switchToiframe();
        waitForAnyElementToAppear(contactName);
        String text = findVisibleElement(contactName).getText();
        leaveiframe();
        return text;
    }

    public ContactDetailsPage addToContacts() {

        waitForElementToBeClickable(contactDropDown).click();

        try {
            waitForElementToBeClickable(addOption).click();
            waitForElementToBeClickable(contactListSave).click();
            waitForElementToBeClickable(okayConfirmationButton).click();
        }
        catch (Exception e){
            waitForElementToBeClickable(removeFromContacts).click();
            waitForLoadingScreen();
            waitForElementToBeClickable(okayConfirmationButton).click();
            pause(300L);
            waitForElementToBeClickable(okayConfirmationButton).click();
            pause(300L);
            waitForElementToBeClickable(contactDropDown).click();
            waitForElementToBeClickable(addOption).click();
            waitForElementToBeClickable(contactListSave).click();
            waitForElementToBeClickable(okayConfirmationButton).click();

        }

        return this;
    }

    public boolean activityChartIsDisplayed(){

        try {
            waitForElementToAppear(activityChartTitle);
            waitForElementToAppear(activityChart);
            return true;
        }catch(Exception e){
            return false;
        }


    }

    public ContactDetailsPage removeContactFromList() {
        pageRefresh();
        waitForElementToBeClickable(contactDropdownPostAdd);
        retryClick(contactDropdownPostAdd);
        wait.until(ExpectedConditions.elementToBeClickable(removeFromContacts));
        waitForElementToBeClickable(removeFromContacts).click();
        waitForElementToBeClickable(okayConfirmationButton).click();
        pause(500L);
        waitForElementToBeClickable(okayConfirmationButton).click();

        return this;
    }

    public ContactDetailsPage addTagToContact(String tag) {
        pause(500L);
        wait.until(ExpectedConditions.elementToBeClickable(addTag));
        findElement(addTag).click();
        wait.until(ExpectedConditions.elementToBeClickable(tagInputField));
        findElement(tagInputField).sendKeys(tag);
        findElement(tagInputField).sendKeys(Keys.RETURN);
        pause(500L);
        pageRefresh();

        return this;
    }

    public String getContactTags() {
        waitForLoadingScreen();
        return waitForElementToAppear(contactTags).getText();
    }

    public String getInstitutionName() {
        waitForElementToBeClickable(institutionName);
        return findElement(institutionName).getText();
    }

    public InstitutionPage navigateToInstitution() {
        waitForLoadingScreen();
        waitForElementToBeClickable(institutionName).click();
        pageRefresh();
        return new InstitutionPage(getDriver());
    }

    public ContactDetailsPage switchToManagedFundsTab() {
        waitForElementToAppear(managedFunds);
        scrollToElement(managedFunds);
        findElement(managedFunds).click();

        return this;
    }

    public String getManagedFunds() {
        return findElement(fundTableNames).getText();
    }

    public AdvancedSearchPage viewTagResults() {
        waitForElementToBeClickable(tagIcon).click();

        return new AdvancedSearchPage(getDriver());
    }

    public ContactDetailsPage accessContactDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(contactDropDown));
        findElement(contactDropDown).click();

        return this;
    }

    public LogActivityPage logActivity() {
        waitForElementToBeClickable(logActivityOption).click();

        return new LogActivityPage(driver);
    }

    public String getNoteDetails() {
        waitForLoadingScreen();
        waitForElement(noteDetails);
        scrollToElement(noteDetails);
        return findElement(noteDetails).getText();
    }

    public ContactDetailsPage createTearSheet(String report) {
        wait.until(ExpectedConditions.elementToBeClickable(createTearSheet));
        findElement(createTearSheet).click();
        findElement(reportTitle).sendKeys(report);
        findElement(createButton).click();

        return this;
    }

    public ContactDetailsPage selectCreateTearSheet() {
        wait.until(ExpectedConditions.elementToBeClickable(createTearSheet));
        findElement(createTearSheet).click();

        return this;
    }

    public ContactDetailsPage cancelTearSheetCreation() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        findElement(cancelButton).click();

        return this;
    }

    public String getURL(){
        waitForElementToAppear(contactIcon);
        return driver.getCurrentUrl();
    }

    public boolean isSavedTarget(){
        waitForLoadingScreen();
        List<WebElement> targetIcons = findElements(targetIcon);
        for (int i=0; i<targetIcons.size(); i++){
            if (targetIcons.get(i).isDisplayed()){
                return true;
            }
        }
        return false;
    }

    public void markAsTarget(){
        if (isSavedTarget()) {
            waitForLoadingScreen();
            waitForElementToBeClickable(contactDropDown).click();
            waitForElementToBeClickable(removeTarget).click();
            waitForElementToRest(contactDropDown, 1000L);
            findElement(contactDropDown).click();
            waitForElementToAppear(markTarget);
            findElement(markTarget).click();
            waitForElementToDissapear(markTarget);
        }
        else
        {
            waitForElementToBeClickable(contactDropDown).click();
            waitForElementToBeClickable(markTarget).click();
            waitForElementToDissapear(markTarget);
        }
        waitForElementToAppear(targetIcon);
    }

    public void removeFromTargets(){
        waitForElementToAppear(contactDropDown);
        findElement(contactDropDown).click();
        waitForElementToAppear(removeTarget);
        findElement(removeTarget).click();
        waitForElementToDissapear(removeTarget);
        waitForElementToDissapear(targetIcon);
    }

    public ContactDetailsPage generateTearSheet(String title) {
        waitForLoadingScreen();
        findElement(createTearSheet).click();
        pause(1000L);
        findElement(generateButton).click();

        return this;
    }

    public boolean modalIsDismissed() {
        waitForLoadingScreen();
        List<WebElement> generate = findElements(generateButton);
        for (int i=0; i<generate.size(); i++){
            if (generate.get(i).isDisplayed()){
                return true;
            }
        }
        return false;
    }


    //delete the custom contact manually from custom contact detail page
    public ContactDetailsPage deleteContact(){
        // waitForLoadingScreen();
        switchToiframe();
        waitForElementToAppear(utilityMenu).click();
        waitForElementToAppear(buttonDeleteCustomContact).click();
        waitForElementToAppear(buttonYes).click();
        leaveiframe();
        return this;
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
