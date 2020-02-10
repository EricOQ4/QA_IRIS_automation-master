package pageobjects.user.briefingBooks;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.dashboardPage.Dashboard;

/**
 * Created by patrickp on 2016-08-10.
 */
public class CreateBriefingBookModal extends AbstractPageObject {

    private final By cancelBriefingButton = By.xpath("//div[contains(@class,'x-unsized x-button form-button no-background x-button-no-icon')]");
    private final By closeBriefingBookIcon = By.xpath ("//span [@class='x-button-icon x-shown q4i-close-4pt']");
    private final By nameField = By.name("title");
    private final By attendeesField = By.xpath("//input [contains(@class,'x-input-el x-form-field x-input-text')] [contains(@placeholder,'Keyword')]");
    private final By institutionsTab = By.xpath("//span[text()='Institutions']");
    private final By fundsTab = By.xpath("//span[text()='Funds']");
    private final By contactsTab = By.xpath("//span [text()='Contacts']");
    private final By saveButton = By.xpath("//span[@class='x-button-label'][text()='Save']");

    public CreateBriefingBookModal(WebDriver driver) {
        super(driver);
    }

    public Dashboard cancelCreateBriefingBookModal() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelBriefingButton));
        findElement(cancelBriefingButton).click();

        return new Dashboard(getDriver());
    }

    public Dashboard dismissCreateBriefingBookModal() {
        wait.until(ExpectedConditions.elementToBeClickable(closeBriefingBookIcon));
        findElement(closeBriefingBookIcon).click();

        return new Dashboard(getDriver());
    }

    public CreateBriefingBookModal enterTitle(String title) { //adds Title in the Create Briefing Book Modal

        waitForElementToAppear(nameField);

        findElement(nameField).sendKeys(title);



        return new CreateBriefingBookModal(getDriver());

    }

    public BriefingBookList saveBriefingBook(String briefingBookName) { //only saves the Briefing Book
        waitForElementToAppear(nameField);
        findElement(nameField).sendKeys();
        findElement(saveButton).click();
        return new BriefingBookList(getDriver());
    }


    public CreateBriefingBookModal enterInstitution(String institution) {
        waitForElementToAppear(institutionsTab);
        findElement(institutionsTab).click(); //by default this does nothing but if future test scenarios include switching from another type Attendee to Institutions this will be useful

        selectFirstDropdown(institution);

        return this;

    }

    public CreateBriefingBookModal enterFund(String fund) { //On Create Briefing Book modal, switches to Fund Tab and adds a Fund
        waitForElementToAppear(fundsTab);
        findElement(fundsTab).click();

        selectFirstDropdown(fund); //basically selects the first Dropdown Suggestion and selects it to add as attendee

        return this;
    }

    public CreateBriefingBookModal enterContact (String contact) { //On Create Briefing Book modal, switches to Contacts Tab and adds a Contact
        waitForElementToAppear(contactsTab);
        findElement(contactsTab).click();

        selectFirstDropdown(contact); //basically selects the first Dropdown Suggestion and selects it to add as attendee
        return this;
    }
    //TODO Figure out how to get it to wait before clicking the Down Arrow Key and Enter without using pause
    // PreCondition : The Driver should be switched to the correct Attendee Tab before calling this function
    private CreateBriefingBookModal selectFirstDropdown(String attendee) {

        waitForElementToAppear(attendeesField);
        findElement(attendeesField).sendKeys(attendee);
        pause(3000);
        findElement(attendeesField).sendKeys(Keys.ARROW_DOWN);
        pause(1000);
        findElement(attendeesField).sendKeys(Keys.ENTER);


        return this;
    }




}
