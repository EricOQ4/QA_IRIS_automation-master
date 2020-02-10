package specs.user.activity;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.activityPage.FilterType;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.contactPage.ContactPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.noteDetailsPage.NoteDetailsPage;
import pageobjects.user.activityPage.LogActivityPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-08-22.

 * Updated by danielh on 2019-04-29.

 * Updated by ShardulB on 2019-05-15
 */

public class LogActivity extends AbstractSpec {
    public final static String keyword = "**AUTOMATION**";
    public static String initTitle = keyword+" Original save" + RandomStringUtils.randomAlphanumeric(6);

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectActivityPageFromSideNav();
    }

    //Cleanup deletes all newly created activities
    @After
    public void cleanUp(){
        try {
            ActivityPage activity = new ActivityPage(driver).accessSideNav().selectActivityPageFromSideNav();
//            Uncomment lines below to delete specific notes
//            activity.deleteAllNotes(keyword);
            activity.deleteAllYourActivities();
//        activity.deleteActivityWithCP();
        }
        catch (Exception e) {
            System.out.println("CLEAN UP FAILED.");
        }

    }

    /**These test scenarios log an Activity with Title, Note, Tag and Type
     *Then verify that those fields appear properly on the Activity Landing Page
     */

    @Test
    public void createNoteWith$Symbol(){ //

        String title = keyword+"This is a test comment with $" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note with $" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String location = "Automation" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver)
                .logNote()
                .enterNoteDetails(title, note, tag)
                .postActivity(FilterType.NOTE)
                .enterLocation(location)
                .saveActivity()
                .searchForNote(title);

        // Make sure the new comment appears on page
        Assert.assertThat ("Title does not match expected", activityPage.getActivityTitle(0), containsString(title));
        Assert.assertThat ("Tag does not match expected", activityPage.getActivityTag(0,1), containsString(tag));
        Assert.assertThat ("Type does not match expected", activityPage.getActivityType(title),containsString("q4i-note-4pt"));
        Assert.assertThat ("Location does not match expected", activityPage.getLocation(0), containsString(location));

    }

    @Test
    public void canLogNoteFromActivityPage() {

        String title = keyword + "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String location = "Automation" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver)
                .logNote()
                .enterNoteDetails(title, note, tag)
                .postActivity(FilterType.NOTE)
                .enterLocation(location)
                .saveActivity()
                .searchForNote(title);

        // Make sure the new comment appears on page
        Assert.assertThat ("Title does not match expected", activityPage.getActivityTitle(0), containsString(title));
        Assert.assertThat ("Tag does not match expected", activityPage.getActivityTag(0,1), containsString(tag));
        Assert.assertThat ("Type does not match expected", activityPage.getActivityType(title),containsString("q4i-note-4pt"));
        Assert.assertThat ("Location does not match expected", activityPage.getLocation(0), containsString(location));

    }

    @Test
    public void canLogEarningsFromActivityPage() {

        String title = keyword + "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String location = "Automation" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver)
                .logNote()
                .postActivity(FilterType.EARNINGS)
                .enterNoteDetails(title, note, tag)
                .enterLocation(location)
                .saveActivity()
                .searchForNote(title);

        // Make sure the new comment appears on page
        Assert.assertThat ("Title does not match expected", activityPage.getActivityTitle(0), containsString(title));
        Assert.assertThat ("Tag does not match expected", activityPage.getActivityTag(0,1), containsString(tag));
        Assert.assertThat ("Type does not match expected", activityPage.getActivityType(title),containsString("q4i-earnings-4pt"));
        Assert.assertThat ("Location does not match expected", activityPage.getLocation(0), containsString(location));

    }

    @Test
    public void canLogCallFromActivityPage() {

        String title = keyword + "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test Call" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String location = "Automation" + RandomStringUtils.randomAlphanumeric(6);
        String institution = "Magellan Asset Management Ltd.";


        ActivityPage activityPage = new ActivityPage(driver).logNote()
                .enterNoteDetails(title, note, tag)
                .postActivity(FilterType.PHONE)
                .enterLocation(location)
                .linkNoteToInstitution(institution)
                .saveActivity()
                .searchForNote(title);

        // Make sure the new comment appears on page
        Assert.assertThat ("Title does not match expected", activityPage.getActivityTitle(0), containsString(title));
        Assert.assertThat ("Tag does not match expected", activityPage.getActivityTag(0,1), containsString(tag));
        Assert.assertThat ("Type does not match expected", activityPage.getActivityType(title),containsString("q4i-phone-4pt"));
        Assert.assertThat ("Location does not match expected", activityPage.getLocation(0), containsString(location));
        Assert.assertThat ("Institution is not correct", activityPage.getInstitution(0), containsString(institution));

    }


    @Test
    public void canLogEmailFromActivityPage() {

        String title =keyword+"This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String location = "Automation" + RandomStringUtils.randomAlphanumeric(6);


        ActivityPage activityPage = new ActivityPage(driver).logNote()
                .enterNoteDetails(title, note, tag)
                .postActivity(FilterType.EMAIL)
                .enterLocation(location)
                .saveActivity()
                .searchForNote(title.substring(6));

        Assert.assertThat ("Title does not match expected", activityPage.getActivityTitle(0), containsString(title));
        Assert.assertThat ("Tag does not match expected", activityPage.getActivityTag(0,1), containsString(tag));
        Assert.assertThat ("Type does not match expected", activityPage.getActivityType(title),containsString("q4i-mail-4pt"));
        Assert.assertThat ("Location does not match expected", activityPage.getLocation(0), containsString(location));
    }

    @Test
    public void canLogMeetingFromActivityPage() {

        String title =keyword+"This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String location = "Automation" + RandomStringUtils.randomAlphanumeric(6);

//        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        ActivityPage activityPage = new ActivityPage(driver).logNote()
                .enterNoteDetails(title, note, tag)
                .postActivity(FilterType.MEETING)
                .enterLocation(location)
                .saveActivity()
                .searchForNote(title);


        // Checking Title, Tag, Type and Location fields on Landing page
        Assert.assertThat ("Title does not match expected", activityPage.getActivityTitle(0), containsString(title));
        Assert.assertThat ("Tag does not match expected", activityPage.getActivityTag(0,1), containsString(tag));
        Assert.assertThat ("Type does not match expected", activityPage.getActivityType(title),containsString("q4i-meeting-4pt"));
        Assert.assertThat ("Location does not match expected", activityPage.getLocation(0), containsString(location));
    }

    @Test
    public void canLogRoadShowFromActivityPage() {

        String title =keyword+"This is a test title" + RandomStringUtils.randomAlphanumeric(6);
        String location = "This is a test location" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver)
                .logNote()
                .enterParentDetails(title, location, tag, FilterType.ROADHSHOW)
                .saveActivity()
                .searchForNote(title);

        // Make sure the new comment appears on page
        Assert.assertThat ("Title does not match expected", activityPage.getActivityTitle(0), containsString(title));
        Assert.assertThat ("Tag does not match expected", activityPage.getActivityTag(0,1), containsString(tag));
        Assert.assertThat ("Type does not match expected", activityPage.getActivityType(title),containsString("q4i-roadshow-4pt"));
        Assert.assertThat ("Location does not match expected", activityPage.getLocation(0), containsString(location));
    }

    @Test
    public void canLogConferenceFromActivityPage() {
        String title = keyword+"Testing Conference Activities" + RandomStringUtils.randomAlphanumeric(6);
        String location = "Testing, Java" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

        ActivityPage activityPage = new ActivityPage(driver)
                .logNote()
                .enterParentDetails(title, location, tag, FilterType.CONFERENCE)
                .saveActivity()
                .searchForNote(title);

        //Make sure the new comment appears on page
        Assert.assertThat ("Title does not match expected", activityPage.getActivityTitle(0), containsString(title));
        Assert.assertThat ("Tag does not match expected", activityPage.getActivityTag(0,1), containsString(tag));
        Assert.assertThat ("Type does not match expected", activityPage.getActivityType(title),containsString("q4i-conference-4pt"));
        Assert.assertThat ("Location does not match expected", activityPage.getLocation(0), containsString(location));

    }
    //Creating Note with Corporate Participant, also choosing Date
    @Test
    public void createActivityWithCorpPart() {

        String initNote = keyword+"This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String initTag = "TestTag" + RandomStringUtils.randomAlphanumeric(4);
        String initCP = "Shardul Automated";

        ActivityPage activityPage = new ActivityPage(driver);

        new ActivityPage(driver).logNote()
                .enterNoteDetails(initTitle,initNote,initTag)
                .enterSelectFirstParticipant(initCP)
                .postActivity(FilterType.EARNINGS)
                .enterStartDate(5) //5 months in the future
                .saveActivity() //clicks Save and Close button
                .searchForNote(initTitle);

        Assert.assertThat ("Title does not match expected", activityPage.getActivityTitle(0), containsString(initTitle));
        Assert.assertThat ("Tag does not match expected", activityPage.getActivityTag(0,1), containsString(initTag));
        Assert.assertThat ("Type does not match expected", activityPage.getActivityType(initTitle),containsString("q4i-earnings-4pt"));

        //Check if Activity you created exists on the Corporate Participant Detail Page
        String activityName = activityPage
                .selectFirstNoteInList()
                .clickOnCorpPart()
                .searchForActivity(initTitle.substring(8))
                .getActivityTitleByRow(0);

        Assert.assertEquals("The Activity names do not match", activityName, initTitle);

    }

    /**
     *  This test basically creates an Activity, adds some Details, saves (not Save & Close) then Changes them and clicks Cancel
     *     Checks Activity Detail and Landing pages to make sure the version that existed when Save was clicked appears on Details Page
     */

    @Test
    public void createNoteActivityWithContact() {
        String title = keyword + "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        String note = "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);
        String location = "Automation" + RandomStringUtils.randomAlphanumeric(6);
        String contact = "Automation";

        ActivityPage activityPage = new ActivityPage(driver)
                .logNote()
                .enterNoteDetails(title, note, tag)
                .postActivity(FilterType.NOTE)
                .enterLocation(location)
                .linkNoteToContact(contact)
                .saveActivity()
                .searchForNote(title.substring(7));

        // Make sure the new comment appears on page
        Assert.assertThat ("Title does not match expected", activityPage.getActivityTitle(0), containsString(title));
        Assert.assertThat ("Tag does not match expected", activityPage.getActivityTag(0,1), containsString(tag));
        Assert.assertThat ("Type does not match expected", activityPage.getActivityType(title),containsString("q4i-note-4pt"));
        Assert.assertThat ("Location does not match expected", activityPage.getLocation(0), containsString(location));

    }

    @Test
    public void canSaveActivityThenCancel() {

        String initTitle = keyword+"Original save" + RandomStringUtils.randomAlphanumeric(6);
        String initNote = keyword+"This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        String initTag = "TestTag" + RandomStringUtils.randomAlphanumeric(4);
        String initCP = "Shardul Automated";

        String newTitle = "add"+RandomStringUtils.randomAlphanumeric(3);
        String newNote = keyword+"Changed Note"+ RandomStringUtils.randomAlphanumeric(6);
        String newTag = "Edited";
        String newLocation = "Timbuktwo"+RandomStringUtils.randomAlphanumeric(3);

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        ActivityPage activityPage = new ActivityPage(driver);

        new ActivityPage(driver).logNote()
                .enterNoteDetails(initTitle,initNote,initTag)
                .enterSelectFirstParticipant(initCP)
                .postActivity(FilterType.EARNINGS)
                .saveNotClose(); //clicks Save button

        new LogActivityPage(driver)
                .clearNoteDetails()
                .enterNoteDetails(newTitle, newNote, newTag)
                .enterLocation(newLocation)
                .postActivity(FilterType.EMAIL)
                .cancelNote() //cancel updated changes
                .searchForNote(keyword); //this tests to make sure Activity title is not changed

        //Assertion On Landing

        Assert.assertThat ("Title does not match expected", activityPage.getActivityTitle(0), containsString(initTitle));
        Assert.assertThat ("Tag does not match expected", activityPage.getActivityTag(0,1), containsString(initTag));
        Assert.assertThat ("Type does not match expected", activityPage.getActivityType(initTitle),containsString("q4i-earnings-4pt"));

        activityPage.clickActivity(initTitle);

        //Assertion on Details page

        Assert.assertThat("Note is incorrect", noteDetailsPage.getCommentText() , containsString(initNote));
        Assert.assertThat("Tag is incorrect", noteDetailsPage.getDetailsTag(), containsString(initTag) );
        Assert.assertThat("CP is incorrect", noteDetailsPage.getParticipant(1), containsString(initCP));
        Assert.assertThat("Location is incorrect", noteDetailsPage.getLocation(), containsString("...")); //make sure location is blank
        Assert.assertTrue("Type is incorrect", noteDetailsPage.isCorrectActivityType(FilterType.EARNINGS));


    }

}