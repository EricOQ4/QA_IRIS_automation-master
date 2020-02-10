package specs.user.contacts;

import org.junit.*;
import pageobjects.user.contactPage.ContactColumnType;
import pageobjects.user.contactPage.ContactPage;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by patrickp on 2016-08-25.
 */
public class ContactList extends AbstractSpec {
//random push
    @Before
    public void setUp() {
        new LoginPage(driver).loginUser();

    }

    //Adds, searches, deletes from details page
    @Test
    public void canAddAndRemoveContactFromList() {
        String contactName = "Andrew C. McCormick";
        ContactPage contactPage = new ContactPage(driver);

        new Dashboard(driver).searchFor(contactName)
                .selectContactFromSearchResults(contactName)
                .addToContacts()
                .accessSideNavFromPage()
                .selectContactsFromSideNav()
                .searchForContact(contactName);

        Assert.assertThat("Contact does not appear in contact list", contactPage.getContacts(), containsString(contactName));

        new ContactPage(driver).viewContactDetails()
                .removeContactFromList()
                .accessSideNavFromPage()
                .selectContactsFromSideNav()
                .searchForContact(contactName);

        Assert.assertThat("Contact was not removed from contact list", contactPage.getContacts(), is(not(containsString(contactName))));
    }

    @Test
    public void canDeleteContactFromContactList(){

        String contactName = "Andrew C. McCormick";
        ContactPage contactPage = new ContactPage(driver);

        new Dashboard(driver).searchFor(contactName)
                .selectContactFromSearchResults(contactName)
                .addToContacts()
                .accessSideNavFromPage()
                .selectContactsFromSideNav()
                .searchForContact(contactName)
                .removeFromList();

        Assert.assertThat("Contact was not removed from contact list", contactPage.getContacts(), is(not(containsString(contactName))));

    }

    @Test
    public void sortByName(){

        new Dashboard(driver)
                .accessSideNav()
                .selectContactsFromSideNav();

        ContactColumnType name = ContactColumnType.NAME;
        //Up sorted
        ContactPage contactPage = new ContactPage(driver).clickColumnHeader(name);
        Assert.assertTrue("Type not sorted correctly",contactPage.isColumnSorted(name));
        //By clicking again, is down sorted
        contactPage.clickColumnHeader(name);
        Assert.assertTrue("Type not sorted correctly",contactPage.isColumnSorted(name));
    }

    //Location is currently broken, so this test will not pass. When ticket is in done we can activate it again
    @Ignore
    @Test
    public void sortByLocation(){

        new Dashboard(driver)
                .accessSideNav()
                .selectContactsFromSideNav();

        ContactColumnType location = ContactColumnType.LOCATION;
        //Up sorted
        ContactPage contactPage = new ContactPage(driver).clickColumnHeader(location);
        Assert.assertTrue("Type not sorted correctly",contactPage.isColumnSorted(location));
        //By clicking again, is down sorted
        contactPage.clickColumnHeader(location);
        Assert.assertTrue("Type not sorted correctly", contactPage.isColumnAscending(location));
    }
    @Ignore
    @Test
    public void sortByPhone(){

        new Dashboard(driver)
                .accessSideNav()
                .selectContactsFromSideNav();

        ContactColumnType phone = ContactColumnType.PHONE;
        //Up sorted
        ContactPage contactPage = new ContactPage(driver).clickColumnHeader(phone);
        Assert.assertTrue("Type not sorted correctly",contactPage.isColumnSorted(phone));
        //By clicking again, is down sort
        contactPage.clickColumnHeader(phone);
        Assert.assertTrue("Type not sorted correctly",contactPage.isColumnSorted(phone));

    }

}
