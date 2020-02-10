package pageobjects.user.contactPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.HashMap;
/**
 * Created by ShardulB on 30-05-2019
 */
public class EditContact extends ContactPage{

    //Text fields on the Edit Contact Page (common between Corporate Participants and Custom Contacts)
    private final By firstName = By.xpath("//input[@id='firstName']");
    private final By lastName = By.xpath("//input[@id='lastName']");
    private final By nickName = By.xpath("//input[@id='nickName']");
    private final By jobTitle = By.xpath("//input[@id='jobTitle']");
    private final By primaryPhone = By.xpath("//input[@id='phone']");
    private final By secondaryPhone = By.xpath("//input[@id='mobile']");
    private final By email = By.xpath("//input[@id='email']");

    //Text fields on the Edit Custom Contact Page (Elements that are not on Corporate Participants Page)
    private final By bio = By.xpath("//textarea[@id='bio']");
    private final By institutionName = By.xpath("//input[@id='institutionName']");
    private final By firstInstitutionInList = By.xpath("//div[@class='select-list__item'][1]");
    private final By customInstbox = By.xpath("//label[@for='customInstitution']");
    private final By addressStreet = By.xpath("//input[@id='street']");
    private final By addressCity = By.xpath("//input[@id='city']");
    private final By addressState = By.xpath("//input[@id='state']");
    private final By addressZip = By.xpath("//input[@id='zip']");
    private final By adressCountry = By.xpath("//input[@id='countryName']");
    private final By corporatePhone = By.xpath("//input[@id='corporatePhone']");

    //Buttons on the Edit Contact Page
    private final By cancel = By.xpath("//button[contains(text(), 'Cancel')]");
    private final By save = By.xpath("//button[contains(text(), 'Save')]");
    private final By delete = By.xpath("//button[contains(text(), 'Delete')]"); //Only for Editing Corporate Participant

    public EditContact(WebDriver driver) {
        super(driver);
    }

    //Fill in all Fields for Corporate Participant Edit Page
    public EditContact fillInAllCPFields(
            String fName, String lName, String nName, String job, String pPhone, String sPhone, String mail) {

        typeIntoField(firstName, fName);
        typeIntoField(lastName, lName);
        typeIntoField(nickName, nName);
        typeIntoField(jobTitle, job);
        typeIntoField(primaryPhone, pPhone);
        typeIntoField(secondaryPhone, sPhone);
        typeIntoField(email, mail);

        return this;

    }

    public CorpPartDetailsPage saveContact() {
        waitForLoadingScreen();
        waitForElementToBeClickable(save).click();

        return new CorpPartDetailsPage(getDriver());
    }

    public CorpPartDetailsPage cancelSaveContact() {
        waitForLoadingScreen();
        waitForElementToBeClickable(cancel).click();

        return new CorpPartDetailsPage(getDriver());
    }

    //Fill in all the Fields for customContacts
    //Hashmaps are like dictionaries in python, i.e. instead of indexing with integers, we index with pre-defined strings
    public ContactDetailsPage fillInAllFields(HashMap <String, String> allFields) {

        typeIntoField(firstName, allFields.get("firstName"));
        typeIntoField(lastName, allFields.get("lastName"));
        typeIntoField(nickName, allFields.get("nickName"));
        typeIntoField(jobTitle, allFields.get("job"));
        typeIntoField(primaryPhone, allFields.get("primaryPhone"));
        typeIntoField(secondaryPhone, allFields.get("secondaryPhone"));
        typeIntoField(email, allFields.get("email"));
        typeIntoField(bio, allFields.get("bio"));
        typeIntoField(institutionName, allFields.get("institutionName"));

        selectFirstInstitution("BlackRock Financial Management, Inc.");

        waitForElementToBeClickable(save).click();

        return new ContactDetailsPage(getDriver());
    }

    public EditContact selectFirstInstitution(String iName) {

        typeIntoField(institutionName, iName);
        waitForElementToBeClickable(firstInstitutionInList).click();

        return this;
    }

    public void typeIntoField (By field, String comment){
        waitForElementToBeClickable(field).click();
        findElement(field).clear();
        findElement(field).sendKeys(comment);
    }

}
