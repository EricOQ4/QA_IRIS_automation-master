package pageobjects.user.briefingBooks;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
/**
 * Created by patrickp on 2016-09-13.
 */

/**
 * Updated by danielh on 2019-04-29
 */

public class BriefingBookDetailsPage extends AbstractPageObject {

    //Iframe
    private final By iframe = By.xpath("//iframe[contains(@id,'sencha-viewport')]");

    private final By deleteButton = By.xpath("//span[@class='x-button-label'] [text()='Delete']");

    private final By addButton = By.xpath("//div [contains(@class,'x-unsized x-button x-iconalign-center')]/descendant::span[@class='x-button-icon x-shown q4i-add-4pt']");

    private final By trashCan = By.xpath ("//div[@class='x-container x-unsized x-size-monitored x-paint-monitored bulk-action-toolbar'] //span [@class='x-button-icon x-shown q4i-trashbin-4pt']");
    private final By utilityMenu = By.xpath("//span [@class='x-button-icon x-shown q4i-utility-4pt']");
    private final By deleteConfirmation = By.xpath("//div [contains (@class,'x-button-no-icon primary x-layout-box-item x-stretched')]");
    private final By saveButton = By.xpath("//div[contains(@class,'x-button-no-icon') and ./span[contains(text(),'Save')]]");//save button for title
    private final By saveEntityButton = By.xpath("//span [contains(@class,'x-button-label')] [text()='Save']"); //save

    //Attendee type options
    private final By institutionOption = By.xpath ("//div [contains(@class,'entity-search_radio-field')]/descendant::span[text()='Institutions']");
    private final By fundOption = By.xpath ("//div [contains(@class,'entity-search_radio-field')]/descendant::span[text()='Funds']");
    private final By contactOption = By.xpath ("//div [contains(@class,'entity-search_radio-field')]/descendant::span[text()='Contacts']");


    private final By entitySearchBox = By.xpath("//input[@class='x-input-el x-form-field x-input-text']");
    private final By entityResults = By.xpath("result-it");
    private final By firstEntityResult = By.xpath("//div[contains(@class,'result-item')][1]");

    private final By entityList = By.className("briefing-book-detail-list");
    private final By editButton = By.xpath("//div[contains(@class,'x-button-no-icon') and ./span[contains(text(),'Edit')]]");
    private final By cancelButton = By.xpath("//div[contains(@class,'x-button-no-icon') and ./span[contains(text(),'Cancel')]]");
    private final By generalEntity = By.xpath("//div[contains(@class,'x-list-item')]");
    private final By deleteConfirmationPopUp = By.className("x-floating");
    private final By entityDragHandle = By.className("x-list-sortablehandle");
    private final By topOfEntityList = By.className("bulk-action-toolbar");
    //private final By entityName = By.cssSelector(".x-list-item .name");
    private final By entityName = By.xpath ("//div[@class='column flex book-detail']/descendant::div[@class='text-ellipsis']");

    //ExportCSV options
    private final By exportButton = By.xpath("//span[contains(@class,'x-button-label')] [text()='Export']");

    private final By pdfOption = By.xpath ("//div [contains(@class,'pdf checked')]"); //On Export Briefing Book Modal when choosing between PDF and CSV
    private final By csvOption = By.xpath ("//div [contains(@class,'csv')]");

    private final By okDownloadExport = By.xpath ("//div [contains(@class,'x-button x-button-no-icon primary x-layout-box-item x-stretched')]");
    private final By downloadPDF = By.xpath ("//div[@class='label'] [text()='Download PDF']");
    private final By downloadCSV = By.xpath ("//div [@class='label'] [text()='Download CSV']");

    //features to include in PDF
    private final By includeActivityBox = By.xpath("//input[@name='activity']/following-sibling::div[1]");
    private final By includeCoverPageBox = By.xpath("//input[@name='cover']/following-sibling::div[1]");
    private final By coverPageTitle = By.xpath("//input[@name='title']");
    private final By coverPageNotes = By.xpath("//textarea[@name='note']");
    private final By generateButtonModal = By.xpath("//div [contains(@class,'x-button x-button-no-icon form-button citrus')]");

    //Briefing Book popup
    private final By savePopup = By.xpath ("//div [@class='iziToast-texts'] [text()='Briefing Book has been successfully saved']");
    private final By closePopup = By.xpath ("//button [@class='iziToast-close']");


    Actions actions = new Actions(driver);

    public BriefingBookDetailsPage(WebDriver driver) {
        super(driver);
    }

    public BriefingBookList deleteBriefingBookFromDetailsPage() {

        new WebDriverWait (driver,5).until(ExpectedConditions.invisibilityOfElementLocated(savePopup));
        waitForElementToBeClickable(utilityMenu).click();

        waitForLoadingScreen();
        waitForElementToAppear(deleteButton);
        waitForElementToBeClickable(deleteButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteConfirmation));
        waitForElementToBeClickable(deleteConfirmation).click();

        return new BriefingBookList(getDriver());
    }

    public BriefingBookDetailsPage addInstitution(String name) {


        addAttendees(); //opens Add to Briefing Book Modal
        waitForElementToBeClickable(institutionOption).click();
        selectFirstDropdownOption(name);

        return this;
    }

    public BriefingBookDetailsPage addFund(String name) {

        addAttendees(); //Opens Add to Briefing Book Modal
        waitForElementToBeClickable(fundOption).click();
        selectFirstDropdownOption(name);

        return this;
    }

    public BriefingBookDetailsPage addContact(String name) {

        addAttendees();
        waitForElementToBeClickable(contactOption).click();
        selectFirstDropdownOption(name);

        return this;
    }
    //TODO Eliminate Pause Command - Find the xpath  of the Dropdown Options to select an Attendee
//Pre-Requisite : Must be on the proper Attendees Tab
    private BriefingBookDetailsPage selectFirstDropdownOption(String attendee) { //selects the First Autocomplete Suggestion based off of provided String

        findElement(entitySearchBox).sendKeys(attendee);
        pause (3000);
        findElement(entitySearchBox).sendKeys(Keys.ARROW_DOWN);
        pause (1000);
        findElement (entitySearchBox).sendKeys(Keys.ENTER);
        waitForElementToRest(saveEntityButton, 750); //was getting StaleElementReferenceException
        waitForElementToBeClickable(saveEntityButton).click();


        return this;
    }

    private BriefingBookDetailsPage addAttendees() {


        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(closePopup));
        waitForLoadingScreen();
        findVisibleElement(addButton).click();

        return this;

    }

    public BriefingBookDetailsPage clickEdit() {

        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        findElement(editButton);

        return this;
    }

    public BriefingBookDetailsPage clickCancel() {

        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        findElement(editButton);
        return this;
    }

    public BriefingBookDetailsPage clickSave() {

        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        findElement(editButton);

        return this;
    }


    public BriefingBookDetailsPage deleteEntity(String name){ //deletes Attendees

        selectAttendee(name);
        selectTrashCan();
        confirmDelete();

        return this;
    }


    private WebElement returnEntity(String name){

        ArrayList<WebElement> entities = new ArrayList<>();
        try {
            entities = returnTableEntities();

        }
        catch (Exception e) {
            System.out.print("Entity not present");
        }

        for (WebElement entity : entities) {

            if(entity.isDisplayed()) {

                if (entity.findElement(By.cssSelector("div.column.flex.book-detail")).getText().equals(name)) {

                    return entity;
                }
            }
        }

        return null;
    }

    public boolean doesEntityExist(String name){

        if(returnEntity(name)==null){
            return false;
        }
        else{
            return true;
        }
    }
    //Goes through a List of Attendees and checks to see if any of the original attendees are still present
    public boolean doesAnyEntityExist(String attendees[]) {

        for (String element : attendees) {

            if (doesEntityExist(element)) {

                return true;
            }

        }

        return false;
    }

    //Selects one Attendee
    private BriefingBookDetailsPage selectAttendee(String name){


        WebElement entityToDelete = null;

        entityToDelete=returnEntity(name);


        if(entityToDelete!=null){

            entityToDelete.findElement(By.xpath(".//div[contains(@class,'checkmark') and contains(@class,'checkbox')]")).click();


        }

        return this;
    }

    private void selectTrashCan() {
        waitForElementToBeClickable(trashCan).click();

    }

    private BriefingBookDetailsPage cancelDelete(){

        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteConfirmationPopUp));
        findElement(deleteConfirmationPopUp).findElement(By.xpath("//div[contains(@class,'x-button-no-icon') and ./span [contains(text(),'No')]]")).click();
        waitForLoadingScreen();

        return this;
    }

    private BriefingBookDetailsPage confirmDelete(){

        wait.until(ExpectedConditions.visibilityOfElementLocated(deleteConfirmationPopUp));
        findElement(deleteConfirmationPopUp).findElement(By.xpath("//div[contains(@class,'x-button-no-icon') and ./span [contains(text(),'Yes')]]")).click();
        waitForLoadingScreen();

        return this;
    }

    private ArrayList<WebElement> returnTableEntities() {

        waitForLoadingScreen();
        ArrayList<WebElement> entities = new ArrayList<>(findElements(generalEntity));

        return entities;
    }

    public String getEntityList(){

        waitForLoadingScreen();

        return findElement(entityList).getText();
    }

    public String getEntity(int index){

        waitForLoadingScreen();
        waitForElement(entityName);

        return findElements(entityName).get(index).getText();
    }

    public void reorderEntityToBeginning(int originIndex){

        waitForLoadingScreen();
        actions.dragAndDrop(findElements(entityDragHandle).get(originIndex), findElement(topOfEntityList)).perform();
        waitForElementToRest(topOfEntityList, 1000L);
        pageRefresh();
    }

    public BriefingBookDetailsPage generateBriefingBook(Boolean generatePDF) { //true to generate PDF false to generate CSV

        waitForElementToBeClickable(exportButton).click();

        if (!generatePDF) { //if CSV is wanted
            waitForElementToBeClickable(csvOption);

        }
        waitForElementToBeClickable(generateButtonModal).click();
        waitForLoadingScreen();

        return this;
    }

    public BriefingBookDetailsPage openDownloadedFile(boolean filePDF) { //true to open a PDF file false to open a CSV file

        waitForElementToBeClickable(okDownloadExport).click();
        waitForLoadingScreen();

        if (filePDF) {

            waitForElementToBeClickable(downloadPDF).click();
        }

        else {

            waitForElementToBeClickable(downloadCSV).click();

        }

        pause(3000);
        return this;
    }

    public BriefingBookDetailsPage generateBriefingBookWithCoverPage(String title, String notes, Boolean generatePDF) {

        waitForElementToBeClickable(exportButton).click();
//        if (!includeActivity) {
//            waitForElementToBeClickable(includeActivityBox).click();
//       }
        waitForElementToBeClickable(includeCoverPageBox).click();
        waitForElementToAppear(coverPageTitle).sendKeys(title);
        findElement(coverPageNotes).sendKeys(notes);

        if (!generatePDF) {
            waitForElementToBeClickable(csvOption);
        }
        findElement(generateButtonModal).click();
        waitForLoadingScreen();

        pause(3000);

        return this;
    }

    public String getBriefingBookPdfContent(String title) {

        try {

            URL briefingBookUrl = getPdfUrl(title);
            BufferedInputStream briefingBookFile = new BufferedInputStream(briefingBookUrl.openStream());
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

    public int getBriefingBookPdfNumOfPages(String title) {

        try {
            URL briefingBookUrl = getPdfUrl(title);
            BufferedInputStream briefingBookFile = new BufferedInputStream(briefingBookUrl.openStream());
            PDDocument document = PDDocument.load(briefingBookFile);
            int pages = document.getNumberOfPages();

            document.close();
            briefingBookFile.close();

            return pages;

        } catch (IOException e) {
            e.printStackTrace();

            return 0;
        }
    }
    private URL getPdfUrl(String title) {
        try {
            return new URL("file://" + System.getProperty("user.home") + "/Downloads/"
                    + title.replace(" ", "_")
                    + ".pdf");
            //.replace(" " , "%20")
            //.replace ('*'. '_')
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }


    //Precondition: First element in array has been already added
    //Two Arrays, first one represents all the Attendees name , second one the corresponding type (Institution/Fund/Contact) of each Attendee
    public BriefingBookDetailsPage bulkAddAttendees (String [] attendees, String [] attendeeType) {

        //loop walks through the elements and
        for (int x=1;x<attendees.length;x++) { //skips first element
            switch (attendeeType[x]) {
                case "Institution" :
                    addInstitution(attendees[x]);
                    break;
                case "Fund" :
                    addFund(attendees[x]);
                    break;
                case "Contact" :
                    addContact(attendees[x]);
                    break;
                default : System.err.println ("Invalid Type for "+ attendees[x]);
            }
        }

        return this;
    }

    //Selects Multiple Attendees
    private BriefingBookDetailsPage bulkSelectAttendees (String [] attendees, int amountSelect) {

        for (int x=0;x<amountSelect;x++) {

            selectAttendee(attendees[x]);

        }
        return this;
    }
    //Precondition : Have Attendees listed
    //Deletes an amount (sizeToDelete) of Attendees starting from the top of the list
    public BriefingBookDetailsPage bulkDeleteAttendees (String [] attendees, int sizeToDelete) {

        bulkSelectAttendees (attendees,sizeToDelete); //selects Attendees
        selectTrashCan(); //selects Trash Can
        confirmDelete(); //confirms removal


        return this;
    }



    public BriefingBookDetailsPage switchToiframe(){
        WebElement _iframe = findElement(iframe);
        driver.switchTo().frame(_iframe);
        pause(500L);

        return this;
    }

    public BriefingBookDetailsPage leaveiframe(){
        driver.switchTo().defaultContent();
        pause(500L);

        return this;
    }



}
