package pageobjects.user.contactPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by abbyl on 2019-04-17.
 */

public class CreateContactPage extends ContactPage {


    private final By contactModalTitle = By.xpath("//h2[contains(text(),'Create Contact')]");

    //buttons
    private final By cancelButton = By.xpath("//button[contains(text(),'Cancel')]");
    private final By saveButton = By.xpath("//button[contains(text(),'Save')]");
    private final By closeIcon = By.xpath("//i[contains(@class,'q4i-close-4pt')]");

    //Contact Type
    private final By selectContact = By.xpath("//label [@for='contactTypeCustom']");
    private final By selectCorpPart = By.xpath("//label [@for='contactTypeParticipant']");

    //fields
    private final By firstNameField = By.xpath("//input[@id='firstName']");
    private final By lastNameField = By.xpath("//input[@id='lastName']");
    private final By emailField = By.xpath("//input[@id='email']");
    private final By nickNameField = By.xpath("//input[@id='nickName']");
    private final By jobTitleField = By.xpath("//input[@id='jobTitle']");
    private final By primaryPhoneField = By.xpath("//input[@id='phone']");
    private final By secondaryPhoneField = By.xpath("//input[@id='mobile']");
    private final By biographyField = By.xpath("//textarea[@id='bio']");
    private final By institutionNameField = By.xpath("//input[@placeholder='Keyword Search']");

    //error message
    private final By emptyFieldError = By.xpath("//div[contains(@class,'text-input--error')]"); //error icon
    private final By emailFieldError = By.xpath("//div[contains(@class,'tooltip tooltip--error tooltip--error--right')]"); //email field error tooltip


    //dropdown menu
    private final By institutionDropdown =By.xpath("//div[@class='select-list__item'][1]");

    public CreateContactPage(WebDriver driver) {
        super(driver);
    }


    public String getTitle(){
        waitForLoadingScreen();
        waitForAnyElementToAppear(contactModalTitle);
        return findElement(contactModalTitle).getText();
    }

    public boolean closed(){
        waitForLoadingScreen();
        if(findElements(contactModalTitle).size() ==0){
            return true;
        }
        return false;
    }


    public void clickCancelButton(){
        waitForElementToBeClickable(cancelButton).click();
    }

    public void clickCloseIcon(){
        waitForElementToBeClickable(closeIcon).click();
    }

    public void clickSaveButton(){
        // waitForLoadingScreen();
        waitForElementToBeClickable(saveButton).click();
    }
    public void typeFirstName(String comment){
        waitForElementToBeClickable(firstNameField).click();
        findElement(firstNameField).sendKeys(comment);
    }
    public void typeLastName(String comment){
        waitForElementToBeClickable(lastNameField).click();
        findElement(lastNameField).sendKeys(comment);
    }

    public void typeEmail(String comment){
        waitForElementToBeClickable(emailField).click();
        findElement(emailField).sendKeys(comment);
    }


    //    public CreateContactPage typeAllField(String fName, String lName, String nickName, String jobTitle, String phone, String mobile, String email, String bio, String institution){
//
//        typeIntoField(firstNameField, fName);
//        typeIntoField(lastNameField, lName);
//        typeIntoField(nickNameField,nickName);
//        typeIntoField(jobTitleField, jobTitle);
//        typeIntoField(primaryPhoneField, phone);
//        typeIntoField(secondaryPhoneField,mobile);
//        typeIntoField(emailField, email);
//        typeIntoField(biographyField, bio);
//        typeIntoDropdownField(institutionNameField, institution);
//
//        return this;
//
//    }
    public void typeIntoField (By field, String comment){
        waitForElementToBeClickable(field).click();
        findElement(field).sendKeys(comment);
    }
    public void typeIntoDropdownField (By field, String comment){
        waitForElementToBeClickable(field).click();
        findElements(field).clear();
        findElement(field).sendKeys(comment);
        waitForElementToBeClickable(institutionDropdown).click();

    }

    public boolean getErrorEmailField() {
        waitForElementToAppear(emailFieldError);
        if (getDriver().findElement(emailFieldError).isDisplayed()) {

            return true;
        }

        return false;
    }

    public boolean getErrorEmptyField() {
        waitForElementToAppear(emptyFieldError);
        if (getDriver().findElement(emptyFieldError).isDisplayed()) {

            return true;
        }

        return false;
    }
    public int countErrorEmptyField() {
        return findElements(emptyFieldError).size();
    }

    public ContactPage createNewContact(String fName, String lName, String nickName, String jobTitle, String phone, String mobile, String email, String bio, String institution){

        typeIntoField(firstNameField, fName);
        typeIntoField(lastNameField, lName);
        typeIntoField(nickNameField,nickName);
        typeIntoField(jobTitleField, jobTitle);
        typeIntoField(primaryPhoneField, phone);
        typeIntoField(secondaryPhoneField,mobile);
        typeIntoField(emailField, email);
        typeIntoField(biographyField, bio);
        typeIntoDropdownField(institutionNameField, institution);


        waitForElementToBeClickable(saveButton).click();
        waitForLoadingScreen();  //wait for Contact landing page
        return new ContactPage(getDriver());

    }

    //Create new Corporate Participant
    public ContactPage createNewCorpPart(String fName, String lName, String nickName, String jobTitle, String phone, String mobile, String email){

        waitForElementToBeClickable(selectCorpPart).click();

        typeIntoField(firstNameField, fName);
        typeIntoField(lastNameField, lName);
        typeIntoField(nickNameField,nickName);
        typeIntoField(jobTitleField, jobTitle);
        typeIntoField(primaryPhoneField, phone);
        typeIntoField(secondaryPhoneField,mobile);
        typeIntoField(emailField, email);

        waitForElementToBeClickable(saveButton).click();
        waitForLoadingScreen();  //wait for Contact landing page
        return new ContactPage(getDriver());

    }

}
