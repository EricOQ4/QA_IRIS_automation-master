package specs.user.header;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.contactPage.ContactDetailsPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.securityPage.SecurityOverviewPage;
import specs.AbstractSpecDevTest;

/**
 * Created by abbyl 2019-04-29
 */

//-------------------------------------------------------------------------------------
//specify link for testing development level
//also it can be run on staging when change AbstractSpecDevTest -> AbstractSpec
//and edit setUp() properly
//   1. change AbstractSpecDevTest -> AbstractSpec
//   2. delete url "https://develop.q4desktop.com/dashboard/?newquick"
//   3. delete "driver.navigate().to(url)"
//-------------------------------------------------------------------------------------
public class NewHeaderQuickSearch extends AbstractSpecDevTest {

    @Before
    public void setUp() {
        String url = "https://develop.q4desktop.com/dashboard/?newquick";
        new LoginPage(driver).loginUser();
        driver.navigate().to(url); //redirect to testing url

    }

    //can find contact by first name
    @Test
    public void canSearchContactByFirstNameFromHeader() {
        String contactSearchTerm = "TestContactFirstName";
        String expectedResult = "Mr. TestContactFirstName TestContactLastName";


        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.quickSearchResults(contactSearchTerm, expectedResult)
                .waitForLoadingScreen();

        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);
        Assert.assertEquals("Did not open correct security page", contactDetailsPage.getContactName(), expectedResult);
    }

    //can find contact by last name
    @Test
    public void canSearchContactByLastNameFromHeader() {
        String contactSearchTerm = "TestContactLastName";
        String expectedResult = "Mr. TestContactFirstName TestContactLastName";


        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.quickSearchResults(contactSearchTerm, expectedResult)
                .waitForLoadingScreen();

        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);
        Assert.assertEquals("Did not open correct security page", contactDetailsPage.getContactName(), expectedResult);
    }

    //contact full name 
    @Test
    public void canSearchContactByNameFromHeader() {
        String contactSearchTerm = "TestContactLastName";
        String expectedResult = "Mr. TestContactFirstName TestContactLastName";


        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.quickSearchResults(contactSearchTerm, expectedResult)
            .waitForLoadingScreen();

        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);
        Assert.assertEquals("Did not open correct security page", contactDetailsPage.getContactName(), expectedResult);
    }


//Test scenario 3:

//email address
    @Test
    public void canSearchContactByEmailFromHeader() {
        String contactSearchTerm = "test@q4inc.com";
        String expectedResult = "TestContactFirstName TestContactLastName";


        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.quickSearchDetailResults(contactSearchTerm, expectedResult)
            .waitForLoadingScreen();

        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);


        Assert.assertEquals("Did not open correct security page", contactDetailsPage.getContactName(), "Mr. TestContactFirstName TestContactLastName");
    }
//can search contact by phone number
    @Test
    public void canSearchContactByPhoneNumFromHeader(){
        String contactSearchTerm = "415.670.2000";
        String expectedResult = "Mr. TestContactFirstName TestContactLastName";


        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.quickSearchResults(contactSearchTerm, expectedResult)
                .waitForLoadingScreen();

        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);

        Assert.assertEquals("Did not open correct security page", contactDetailsPage.getContactName(), expectedResult);



    }
//can search contact by nickname
    @Test
    public void canSearchContactByNicknameFromHeader(){
        String contactSearchTerm = "TestContactNickname";
        String expectedResult = "Mr. TestContactFirstName TestContactLastName";


        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.quickSearchResults(contactSearchTerm, expectedResult)
                .waitForLoadingScreen();

        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);
        Assert.assertEquals("Did not open correct security page", contactDetailsPage.getContactName(), expectedResult);

    }


//can search contact by address
    @Test
    public void canSearchContactByAddressFromHeader(){
        String contactSearchTerm = "400 Howard Street";
        String expectedResult = "Mr. TestContactFirstName TestContactLastName";


        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.quickSearchResults(contactSearchTerm, expectedResult)
                .waitForLoadingScreen();

        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);
        Assert.assertEquals("Did not open correct security page", contactDetailsPage.getContactName(), expectedResult);
    }

}
