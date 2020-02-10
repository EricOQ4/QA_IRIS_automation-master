package specs.user.activity;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.activityPage.FilterType;
import pageobjects.user.activityPage.LogActivityPage;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.noteDetailsPage.NoteDetailsPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-08-22.
 */

/**
 * Updated by danielh on 2019-04-29.
 */
public class SearchForActivity extends AbstractSpec {

    public final static String keyword = "**AUTOMATION**";
    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectActivityPageFromSideNav();

    }
    @After
    public void cleanUp(){
        try {
            NoteDetailsPage note = new NoteDetailsPage(driver);
            ActivityPage activity = note.accessSideNavFromPage().selectActivityPageFromSideNav();
            activity.deleteAllNotes(keyword);

        }
        catch(Exception e){
            //I don't want tests to fail because the clean up failed
        }
    }

    /**
     * These tests try searching for newly created activities based off of Title,
     * Institution, Contacts and Tags
     */

    //TODO

    @Test
    public void canSearchForActivityTitleTags() { //Est Duration : Just over 3mins

        //Creates Three Activities with Title, Type, Note, Tag then : performs a search by an incomplete title which returns Activity One and Two
        //performs a second search by a tag which should return only Activity Three

        //Note : This test will fail if lingering "**AUTOMATION* This is a test" Activities are already existing



        String comment1 = "**AUTOMATION** This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note1 = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag1 = "TestTag" + RandomStringUtils.randomAlphanumeric(6);


        String comment2 = "**AUTOMATION** This is a test also" + RandomStringUtils.randomAlphanumeric(6);
        String note2 = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag2 = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        String comment3 = "**AUTOMATION** Changed" + RandomStringUtils.randomAlphanumeric(6);
        String note3 = "Testing Automation notes" + RandomStringUtils.randomAlphanumeric(6);
        String tag3 = "Unique" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver);

        activityPage
                .logIcon()
                .enterNoteDetails(comment1, note1, tag1)
                .postActivity(FilterType.NOTE)
                .saveActivity()
                .accessSideNavFromPage()
                .selectActivityPageFromSideNav();


        activityPage
                .logIcon()
                .enterNoteDetails(comment2, note2, tag2)
                .postActivity(FilterType.EMAIL)
                .saveActivity()
                .accessSideNavFromPage()
                .selectActivityPageFromSideNav();

        activityPage
                .logIcon()
                .enterNoteDetails(comment3, note3, tag3)
                .postActivity(FilterType.EARNINGS)
                .saveActivity()
                .accessSideNavFromPage()
                .selectDashboardFromSideNav();

        //Performs incomplete search of TItle
        activityPage
                .accessSideNav()
                .selectActivityPageFromSideNav()
                .changeToHundred()
                .searchForNote("**AUTOMATION** This is a test");

        //Assertion One : Activity One and Activity Two is returned with this incomplete title search

        Assert.assertThat("First Activity was not included", activityPage.getActivityTitle(1), containsString(comment1));
        Assert.assertThat("Second Activity was not included", activityPage.getActivityTitle(0), containsString(comment2));
        Assert.assertTrue("Searching did not return the same number of results as expected", activityPage.getNumberOfDisplayedActivities()==2);


        activityPage
                .removeSearchContents()
                .searchForNote(tag3);

        Assert.assertThat ("Searching for Tags does not work", activityPage.getActivityTag(0, 1) , containsString(tag3));
        Assert.assertTrue ("Searching for Tags did not return the expected amount",  activityPage.getNumberOfDisplayedActivities()==1);





    }

    @Test
    public void canSearchForInstitutionContact() {

        //Creates Three Activities with  All  Activities being created with Title, Note, Tag, Institution and Contact
        //Performs a Search on one Institution and one Contact and checks to see if the right Activities are returned

        //Note : If there exists Activities with any of the Institutions or Contacts mentioned here, this test WILL FAIL

        String comment1 = "**AUTOMATION** This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note1 = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag1 = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String institution1 = "Caledonia Investments Plc (13F)";
        String contact1 = "Wayne J. Lin";

        String comment2 = "**AUTOMATION** This is a test also" + RandomStringUtils.randomAlphanumeric(6);
        String note2 = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag2 = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String contact2 = "Wayne J. Lin";

        String comment3 = "**AUTOMATION** Changed" + RandomStringUtils.randomAlphanumeric(6);
        String note3 = "Testing Automation notes" + RandomStringUtils.randomAlphanumeric(6);
        String tag3 = "Unique" + RandomStringUtils.randomAlphanumeric(6);
        String contact3 = "Xavier Mentree";
        String institution3 = "Caledonia Investments Plc (13F)";

        ActivityPage activityPage = new ActivityPage(driver);

        activityPage
                .logIcon()
                .enterNoteDetails(comment1, note1, tag1)
                .linkNoteToInstitution(institution1)
                .linkNoteToContact(contact1)
                .postActivity(FilterType.NOTE)
                .saveActivity()
                .accessSideNavFromPage()
                .selectActivityPageFromSideNav();
        //Activity Two
        activityPage
                .logIcon()
                .enterNoteDetails(comment2, note2, tag2)
                .linkNoteToContact(contact2)
                .postActivity(FilterType.EARNINGS)
                .saveActivity()
                .accessSideNavFromPage()
                .selectActivityPageFromSideNav();

        //Activity Three
        activityPage
                .logIcon()
                .enterNoteDetails(comment3, note3, tag3)
                .linkNoteToInstitution(institution3)
                .linkNoteToContact(contact3)
                .postActivity(FilterType.MEETING)
                .saveActivity()
                .accessSideNavFromPage()
                .selectDashboardFromSideNav();


        activityPage
                .accessSideNavFromPage()
                .selectActivityPageFromSideNav()
                .searchForNote(institution1);

        //Assertion Part One : Checks to make sure that only Activity One and Two are returned, and that they both have SPF Beheer BV listed as Institution

        Assert.assertTrue("Two Activities should be shown",activityPage.getNumberOfDisplayedActivities() == 2); //Activity One and Two should only be shown
        Assert.assertThat("Activity One isn't shown", activityPage.getActivityTitle(1), containsString(comment1));
        Assert.assertThat("Activity Three isn't shown", activityPage.getActivityTitle(0), containsString(comment3));
        Assert.assertThat("Institution name is incorrect", activityPage.getInstitution(0),containsString(institution1));

        //Search for Wayne J. Lin
        activityPage
                .removeSearchContents()
                .searchForNote(contact1);

        //Assertion Part Two : Checks to make sure that only Activity One and Three are returned, and have Wayne J. Lin listed as Contact

        Assert.assertTrue("Two Activities should be shown",activityPage.getNumberOfDisplayedActivities() == 2);
        Assert.assertThat("Activity One isn't shown", activityPage.getActivityTitle(1), containsString(comment1));
        Assert.assertThat("Activity Two isn't shown", activityPage.getActivityTitle(0), containsString(comment2));
        Assert.assertThat("Contact name is incorrect", activityPage.getContact(0), containsString(contact1));






    }

    @Test
    public void searchForActivityThatDoesNotExist() {

        String noResults = "No data available";
        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.searchForNote("123456789");

        Assert.assertThat("Searching did not return the expected results", activityPage.getNoNote(), containsString(noResults));
    }
}
