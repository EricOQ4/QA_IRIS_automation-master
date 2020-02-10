package specs.user.contacts;

import org.junit.*;
import org.openqa.selenium.By;
import pageobjects.user.contactPage.ContactDetailsPage;
import pageobjects.user.contactPage.ContactPage;
import pageobjects.user.contactPage.CorpPartDetailsPage;
import pageobjects.user.contactPage.CreateContactPage;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.loginPage.LoginPage;

import pageobjects.user.securityPage.SecurityOverviewPage;
import specs.AbstractSpec;

/**
 * Created by abbyl on 2019-03-01.
 */

/**
 * Modified by ShardulB on 2019-05-09
 */
/*This script is for testing Create Custom Contacts by Create Contact Modal */

public class CreateContact extends AbstractSpec {
    //for testing only
    private final By contactsMenu = By.xpath("//i[contains(@class,'q4i-contact-2pt')]");
    @Before
    public void setUp() {
        new LoginPage(driver).loginUser();
        new Dashboard(driver).accessFromSideNav(contactsMenu);  //this is for testing only to test quickly

    }


// Test Close/Cancel buttons

    @Test   //Test cancel button at the bottom
    public void testCancelButton() {

        CreateContactPage newContactPage = new CreateContactPage(driver);  //modal
        new ContactPage(driver).openCreateContactModal();                  //open modal from Contact page

        newContactPage.clickCancelButton();                                //click cancel button
        Assert.assertTrue("Create Contact Modal fails to close", newContactPage.closed());

    }
    @Test   //Test close icon at the top
    public void testCloseIcon() {

        CreateContactPage newContactPage = new CreateContactPage(driver);  //modal
        new ContactPage(driver).openCreateContactModal(); //open modal from Contact page

        newContactPage.waitForLoadingScreen();
        newContactPage.clickCloseIcon();
        Assert.assertTrue("Create Contact Modal fails to close", newContactPage.closed());

    }


// Test required fields (first name, last name)

    @Test   //Test first name field
    public void verifyFirstNameField(){
        String firstName = "Jane";
        CreateContactPage newContactPage = new CreateContactPage(driver);  //modal
        new ContactPage(driver).openCreateContactModal(); //open modal from contact page
        newContactPage.typeFirstName(firstName);
        newContactPage.clickSaveButton();
        Assert.assertTrue("FirstName field error message not displayed", newContactPage.getErrorEmptyField());

    }

    @Test   //Test last name field
    public void verifyLastNameField(){

        String lastName = "Doe";
        CreateContactPage newContactPage = new CreateContactPage(driver);  //modal
        new ContactPage(driver).openCreateContactModal(); //open modal from contact page
        newContactPage.typeLastName(lastName);
        newContactPage.clickSaveButton();
        Assert.assertTrue("LastName field error message not displayed", newContactPage.getErrorEmptyField());
    }
    @Test   //Test both name fields (Verify Two error message on both name field)
    public void verifyNamesField(){

        CreateContactPage newContactPage = new CreateContactPage(driver);  //modal
        new ContactPage(driver).openCreateContactModal(); //open modal from contact page

        newContactPage.clickSaveButton();
        //newContactPage.waitForLoadingScreen();
        Assert.assertEquals(2,newContactPage.countErrorEmptyField());
    }

//Test Email Field
    @Test
    public void verifyEmailFieldError(){
        String incorrectEmail = "This is incorrect email";
        CreateContactPage newContactPage = new CreateContactPage(driver);  //modal
        new ContactPage(driver).openCreateContactModal(); //open modal from contact page

        newContactPage.typeEmail(incorrectEmail);
        newContactPage.clickSaveButton();
        //newContactPage.waitForLoadingScreen();

        Assert.assertTrue("Email field error message not displayed", newContactPage.getErrorEmailField());

    }

//Test creating new custom contact and verifying created custom contact from contact list and detail page
    @Test
    public void createNewContact(){
        String firstName = "A";
        String lastName = "Automation Test Shardul";
        String email = "test@q4inc.com";
        String nickName = "TEST";
        String jobTitle = "CEO";
        String primaryPhone = "123-123-1111";
        String secondaryPhone = "123-123-2222";
        String bio = "IR workflow is cyclical based on quarterly earnings.";
        String institution = "The Vanguard Group, Inc.";


        CreateContactPage newContactPage = new CreateContactPage(driver);  //modal
        ContactPage contactLandingPage = new ContactPage(driver).openCreateContactModal(); //open modal from contact page

        newContactPage.createNewContact(firstName, lastName, nickName, jobTitle, primaryPhone, secondaryPhone, email, bio, institution);

        //verify contact from list. Update X path in ContactPage if you change the name!
        Assert.assertEquals(firstName+" "+lastName+"\n"+jobTitle+"\n"+institution, contactLandingPage.getContactNameFromList());

        //verify contact from detail page
        Assert.assertEquals(firstName+" "+lastName, contactLandingPage.getContactFromDetailPage());

    }

    @Test //Create a new Corporate Participant
    public void createNewCorporateParticipant() {
        String firstName = "A Automation";
        String lastName = "Test Shardul";
        String email = "test@q4inc.com";
        String nickName = "TEST";
        String jobTitle = "CEO";
        String primaryPhone = "123-123-1111";
        String secondaryPhone = "123-123-2222";

        CreateContactPage newCorpPart = new CreateContactPage(driver); //modal
        ContactPage contactLandingPage = new ContactPage(driver).openCreateContactModal();

        newCorpPart.createNewCorpPart(firstName, lastName, nickName, jobTitle, primaryPhone, secondaryPhone, email);
        contactLandingPage.searchForContact(firstName + " " + lastName);

        //MIGHT HAVE TO CHANGE THE CONTACT NAME!
        Assert.assertEquals(firstName+" "+lastName + "\n" + jobTitle + '\n' + '-', contactLandingPage.getContactNameFromList());

        // ToDO b) Corporate Participants list on Contacts landing page. List and count are updated
        // ToDO c) Other Calendars on Calendar page
        // ToDO d) Corporate Participant filter list on Activity landing page
        // ToDO e) Auto complete in Create & Edit Activity modals
        // ToDO f) Auto complete in Create & Edit Itinerary modals
        // ToDO g) Exported All Contacts csv


    }

//Test removing custom contact from contact list
    @Test
    public void removeContactFromList(){
        String name = "AAAAA Automation";
        String expectedContactName = "AAAAA Automation";
        ContactPage newContactPage = new ContactPage(driver);
        if(newContactPage.searchForContact(name).getContactFromList()) {
            newContactPage.removeFromList();    //remove contact from contact list

            //search custom contact from header
            new SecurityOverviewPage(driver).searchResults(name, expectedContactName)
                    .waitForLoadingScreen();
            //delete contact from detail page
            ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);
            Assert.assertEquals("Did not open correct contact page", contactDetailsPage.getContactName(), expectedContactName);

            contactDetailsPage.deleteContact();
        }


    }


    //Test delete custom contact from contact detail page
    @Test
    public void deleteCustomContact(){

        String name = "A AAAA Automation";
        ContactPage newContactPage = new ContactPage(driver);
        if(newContactPage.searchForContact(name).getContactFromList()){
            newContactPage.deleteContactFromDetailPage();
        }

    }

    @Test
    public void deleteCorpPart() {

        String name = "A Automation Test";
        ContactPage newContactPage = new ContactPage(driver);
        if(newContactPage.searchForContact(name).getContactFromList()) {
            newContactPage.deleteCorpPartFromDetailPage();
        }

//        ContactPage contactPage = new ContactPage(driver);
//        Assert.assertEquals("Corporate Participant not deleted", "", contactPage.searchForContact(name).getContactNameFromList());

    }
}
