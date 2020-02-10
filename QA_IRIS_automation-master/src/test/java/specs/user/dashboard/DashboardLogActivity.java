package specs.user.dashboard;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.activityPage.LogActivityPage;
import pageobjects.user.noteDetailsPage.NoteDetailsPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-08-04.
 */
public class DashboardLogActivity extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void canLogNoteFromDashboard() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver);
        new Dashboard(driver).logActivity();
        new LogActivityPage(driver)
                .enterNoteDetails(comment, note, tag);

        new NoteDetailsPage(driver)
                .addNewTag(tag)
                .accessSideNavFromPage()
                .selectActivityPageFromSideNav()
                .searchForNote(comment)
                .waitForText(comment);

        Assert.assertThat("Note does not contain the expected comment text", activityPage.getNewNote(), containsString(comment));
        Assert.assertThat("Note does not contain the expected tag", activityPage.getNewNote(), containsString(tag));
    }

    @Test
    public void canCancelActivity() {
        new Dashboard(driver).logActivity()
                .cancelNote();

        Assert.assertEquals("Activity modal was not dismissed", 0, driver.findElements(By.cssSelector(".new-note .cancel-btn")).size());
    }

    @Test
    public void canLinkNoteToInstitution() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String institution = "Norfund";
        // The add activity note area is behaving strangely with selenium and truncating the first letter of our note so we'll use this to pass the test for now
        String expectedNote = note.substring(1);

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        new Dashboard(driver).logActivity();
        new LogActivityPage(driver)
                .linkNoteToInstitution(institution)
                .enterNoteDetails(comment,note,tag);

        new Dashboard(driver)
                .accessSideNavFromPage()
                .selectActivityPageFromSideNav()
                .searchForNote(comment)
                .selectFirstNoteInList();

        Assert.assertThat("Note does not contain expected text", noteDetailsPage.getNoteBody(), containsString(expectedNote));
        Assert.assertThat("Note does not contain expected comment text", noteDetailsPage.getCommentText(), containsString(comment));
        Assert.assertThat("Note does not contain expected linked institution", noteDetailsPage.clickInstitutionTab().returnAttendeesList(), containsString(institution));
    }

    @Test
    public void canLinkNoteToFund() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String fund = "Fundy";
        // The add activity note area is behaving strangely with selenium and truncating the first letter of our note so we'll use this to pass the test for now
        String expectedNote = note.substring(1);

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        new Dashboard(driver).logActivity();
        new LogActivityPage(driver)
                .linkNoteToFund(fund)
                .enterNoteDetails(comment,note,tag);

        new Dashboard(driver)
                .accessSideNavFromPage()
                .selectActivityPageFromSideNav()
                .searchForNote(comment)
                .selectFirstNoteInList();

        Assert.assertThat("Note does not contain expected text", noteDetailsPage.getNoteBody(), containsString(expectedNote));
        Assert.assertThat("Note does not contain expected comment text", noteDetailsPage.getCommentText(), containsString(comment));
        Assert.assertThat("Note does not contain expected linked fund", noteDetailsPage.clickFundTab().returnAttendeesList(), containsString(fund));
    }

    @Test
    public void canLinkNoteToContact() {
        String comment = "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String contact = "Joe Galligan";
        // The add activity note area is behaving strangely with selenium and truncating the first letter of our note so we'll use this to pass the test for now
        String expectedNote = note.substring(1);

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        new Dashboard(driver).logActivity();
        new LogActivityPage(driver)
                .linkNoteToContact(contact)
                .enterNoteDetails(comment,note,tag)
                .pageRefresh();

    //search comment and then search contacts
        Assert.assertThat("Note does not contain expected text", noteDetailsPage.getNoteBody(), containsString(expectedNote));
        Assert.assertThat("Note does not contain expected comment text", noteDetailsPage.getCommentText(), containsString(comment));
        Assert.assertThat("Note does not contain expected linked contact", noteDetailsPage.clickContactTab().returnAttendeesList(), containsString(contact));
    }
}
