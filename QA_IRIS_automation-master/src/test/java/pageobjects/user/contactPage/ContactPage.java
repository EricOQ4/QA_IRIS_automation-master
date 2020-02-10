package pageobjects.user.contactPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import specs.user.contacts.CorpPartDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrickp on 2016-08-25.
 */
/**
 * Updated by abbyl on 2019-04-17.
 */

/**
 * Updated by ShardulB on 2019-05-12
 */
public class ContactPage extends AbstractPageObject {

    public String CONTACT_NAME = "A Automation Test Shardul";
    public String CP_NAME = "Corporate P.";
    private final By iframe = By.xpath("//iframe[contains(@id, 'sencha-viewport')]");

    private final By contactList = By.cssSelector(".contact-favorite-list");
    private final By firstContactInList = By.cssSelector(".contact-favorite-list-container .contact-favorite-list .x-dataview-item .column:nth-child(2)");
    private final By searchField = By.name("contactsFilterSearch");
    private final By showMoreLink = By.cssSelector(".load-more .x-button");

    private final By nameSort = By.xpath("//div[contains(@class,'x-button') and contains(@class,'name')]");
    private final By locationSort = By.xpath("//div[contains(@class,'x-button') and contains(@class,'location')]");
    private final By phoneSort = By.xpath("//div[contains(@class,'x-button') and contains(@class,'phone')]");

    private final By contactDelete = By.xpath("//div[contains(@class,'checkbox-mask')]");
    private final By deleteButton = By.cssSelector(".x-button-icon.q4i-trashbin-4pt");
    private final By confirmDeletion = By.cssSelector(".q4-message-modal .x-button.primary");
    private final By secondConfirmDelete = By.xpath("//div[contains(@class,'primary') and span[contains(text(),'Ok')]]");
    private final By refusedDeletion = By.xpath("//span[contains(text(),'No')]");

    private final By plusButton= By.xpath("//span[@class='x-button-icon x-shown q4i-add-4pt']");
    private final By createContactButton = By.xpath("//span[contains(text(),'Create Contact')]");

    //Contact Lists on the left
    private final By corpPartList = By.xpath("//div[@class='contact-list-list-name' and contains(text(), 'Corporate Participants')]");

    //search result
    private final By searchResults = By.xpath("//div[contains (@class,'x-dataview-item')]");
    private final By contactJopTitle = By.xpath("//div[contains(@class,'job-title')]");
    private final By contactInstitution = By.xpath("//div[contains(@class,'institution-name')]");
    private final By contactLocation = By.xpath("//div[contains(@class,'column location')]");

    //custom contact detail page
    private final By utilityMenu = By.xpath("//div[@class='x-inner contact-page-inner']//span[contains(@class,'x-button-icon x-shown q4i-utility-4pt')]");
    private final By buttonDeleteCustomContact = By.xpath("//span[contains(text(),'Delete Custom Contact')]");
    private final By buttonYes = By.xpath("//span[contains(text(),'Yes')]");
    private By firstContact = By.xpath("//div[contains(text(), '" + CONTACT_NAME +"')][1]"); //!Update CONTACTNAME if you are checking new contact!
    private final By contactName = By.xpath("//div[@class='contact-header']//div[@class='contact-name']");

    //Corporate Participant detail page
    private final By CorpPartUtility = By.xpath("//button[@class='button button--square button--utility']");
    private final By buttonDeleteCorpPart = By.xpath("//div[contains(text(), 'Delete Participant')]");
    private final By buttonDelete = By.xpath("//button[contains(text(), 'delete')]");

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    private List<WebElement> returnTableRows (){
        List<WebElement> rowList = findElements(By.xpath("//div[contains(@class,'x-dataview-container')]//div[contains(@class,'row')]"));
        ArrayList<WebElement> tableRowsList = new ArrayList<>(rowList);
        return tableRowsList;

    }

    public CreateContactPage openCreateContactModal() {
        //waitForLoadingScreen(); //duplicate
        switchToiframe();
        waitForElementToBeClickable(plusButton).click();
        waitForElementToBeClickable(createContactButton).click();
        leaveiframe();
        CreateContactPage modal = new CreateContactPage(getDriver());
        modal.waitForLoadingScreen();
        return modal;
    }



    public String getContacts() {
        waitForLoadingScreen();
        loadAllContacts();
        return findElement(contactList).getText();
    }

    public void loadAllContacts(){
        try {
            while (findElement(showMoreLink).isDisplayed()) {
                findElement(showMoreLink).click();
                waitForLoadingScreen();
            }
        }
        catch(Exception e){
            System.out.println("All contacts could not be loaded.");
        }
    }

    public ContactDetailsPage viewContactDetails() {
        waitForElementToBeClickable(firstContactInList);
        retryClick(firstContactInList);

        return new ContactDetailsPage(getDriver());
    }

    public CorpPartDetailsPage clickFirstCorpPart() {
        //Click on the first corporate participant
        switchToiframe();
            waitForElementToBeClickable(By.xpath("//div[contains(text(), '"+CP_NAME+"')]"));
            retryClick(By.xpath("//div[contains(text(), '"+CP_NAME+"')]"));
        leaveiframe();
        return new CorpPartDetailsPage(getDriver());
    }

    //remove contact from List
    public void removeFromList(){
        //waitForLoadingScreen();
        switchToiframe();
        waitForElementToBeClickable(contactDelete).click();
        waitForElementToBeClickable(deleteButton).click();
        waitForLoadingScreen();
        waitForElementToBeClickable(confirmDeletion).click();
        waitForElementToBeClickable(secondConfirmDelete).click();
        waitForElement(contactList);
        waitForElementToRest(contactList, 200L);
        leaveiframe();
    }

    //delete custom contact
    public void deleteContactFromDetailPage(){
        switchToiframe();
        findElement(firstContact).click();
        waitForElementToBeClickable(utilityMenu).click();
        waitForElementToAppear(buttonDeleteCustomContact).click();
        waitForElementToAppear(buttonYes).click();
        leaveiframe();
    }


    public ContactPage searchForContact(String name) {
        waitForLoadingScreen();
        switchToiframe();
        waitForElementToBeClickable(searchField).click();
        findElement(searchField).sendKeys(name);
        findElement(searchField).sendKeys(Keys.ENTER);
        pause(2000L);
        leaveiframe();
        return this;
    }

    public boolean isColumnAscending(ContactColumnType column){
        By selector = getColumnSelector(column);
        return findElement(selector).getAttribute("class").contains("asc");

    }

    public ContactPage clickColumnHeader(ContactColumnType column){
        By selector = getColumnSelector(column);
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(selector));
        findElement(selector).click();
        return this;
    }

    public ContactPage clickOnCorpPart() {
        waitForLoadingScreen();
        switchToiframe();
        waitForElementToBeClickable(corpPartList).click();
        leaveiframe();

        return this;
    }

    //Checking if the columns are sorted

    public boolean isNameSorted(List<WebElement> rows){
        ArrayList<WebElement> names = new ArrayList<>();
        for(WebElement i : rows){
            names.add(i.findElement(By.className("name")));
        }
        if(isColumnAscending(ContactColumnType.NAME)){
            return elementsAreAlphaUpSorted(names);
        }
        else
            return elementsAreAlphaDownSorted(names);
    }


    public boolean isLocationSorted(List<WebElement> rows){
        ArrayList<WebElement> locations = new ArrayList<>();
        for(WebElement i : rows){
            locations.add(i.findElement(By.className("location")));
        }
        if(isColumnAscending(ContactColumnType.LOCATION)){
            return elementsAreAlphaUpSorted(locations);
        }
        else
            return elementsAreAlphaDownSorted(locations);
    }


    public boolean isPhoneSorted(List<WebElement> rows){
        ArrayList<WebElement> phoneNums = new ArrayList<>();
        for(WebElement i : rows){
            phoneNums.add(i.findElement(By.className("phone")));
        }
        if(isColumnAscending(ContactColumnType.PHONE)){
            return elementsAreNumUpSorted(phoneNums);
        }
        else
            return elementsAreNumDownSorted(phoneNums);
    }


    //Redirecting methods to the right column

    public boolean isColumnSorted(ContactColumnType column){
        waitForLoadingScreen();
        switch(column){
            case NAME:
                return isNameSorted(returnTableRows());
            case LOCATION:
                return isLocationSorted(returnTableRows());
            case PHONE:
                return isPhoneSorted(returnTableRows());
            case EMAIL:
                break;
        }
        return false;
    }

    private By getColumnSelector(ContactColumnType column){
        By selector =null;

        switch(column){
            case NAME:
                selector = nameSort;
                break;
            case LOCATION:
                selector = locationSort;
                break;
            case PHONE:
                selector = phoneSort;
                break;
            case EMAIL:
                break;
        }
        return selector;
    }

    public String getContactNameFromList() {
        switchToiframe();
        waitForElementToAppear(firstContact);
        String text= findElement(firstContact).getText();
        leaveiframe();
        return text;
    }

    public CorpPartDetailsPage clickOnCorpPartFromList() {
        switchToiframe();
        waitForElementToAppear(firstContact);
        findElement(firstContact).click();
        leaveiframe();

        return new CorpPartDetailsPage(getDriver());
    }

    public boolean getContactFromList() {
        switchToiframe();
        boolean result = false;

        waitForLoadingScreen();
        if(findElements(searchResults).size()>0){
            result = true;
        }
        else{
            result = false;
        }

        leaveiframe();
        return result;

    }

    //Get Contact Name
    public String getContactName(int rowIndex) {
        waitForElementToAppear(By.xpath("//div [@row-index='"+rowIndex+"']/descendant::div[@col-id='start']")).getText();

        return findElement(By.xpath("//div [@row-index='"+rowIndex+"']/descendant::div[@col-id='start']")).getText();
    }

    public void deleteCorpPartFromDetailPage() {
        switchToiframe();
        findElement(firstContact).click();
        leaveiframe();
        waitForElementToBeClickable(CorpPartUtility).click();
        waitForElementToAppear(buttonDeleteCorpPart).click();
        waitForSiteToLoad();
        waitForElementToBeClickable(buttonDelete).click();
    }

    public String getContactFromDetailPage(){
        switchToiframe();
        findElement(firstContact).click();
        waitForLoadingScreen();
        String text = waitForElementToAppear(contactName).getText();
        leaveiframe();
        return text;
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
