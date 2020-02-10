package specs.user.activity;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.*;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.activityPage.FilterType;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.noteDetailsPage.NoteDetailsPage;
import specs.AbstractSpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by sarahr on 3/27/2017.
 */

/**
 * Updated by danielh on 4/29/2019.
 */

public class ActivityDetails extends AbstractSpec {

    private String keyword = "AUTOMATION";
    private String title = keyword+" Activity Details Test " + RandomStringUtils.randomAlphanumeric(6);
    private String location = "New York";
    private String tag = "automation" + RandomStringUtils.randomAlphabetic(6);
    private String newTag = "newTag" + RandomStringUtils.randomAlphabetic(6);


    @Before
    public void setup() {
        //Logging an activity
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectActivityPageFromSideNav();

        ActivityPage activityPage = new ActivityPage(driver)
                .logNote()
                .enterParentDetails(title, location, tag, FilterType.ROADHSHOW)
                .saveActivity();
        //.accessSideNavFromPage()
        //.selectActivityPageFromSideNav();
    }

    @After
    public void cleanUp(){

        try {
            NoteDetailsPage note = new NoteDetailsPage(driver);
            ActivityPage activity = note.accessSideNavFromPage().selectActivityPageFromSideNav();
            activity.waitForLoadingScreen();
            note.deleteAllNotes(title);
        }
        catch(Exception e){
            //I don't want tests to fail because the clean up failed

        }
    }

    /**
     * Basically these tests create an Activity and veriy a specific field
     */

    @Test
    public void detailsPageAppears(){ //Run Time : 2min 5 sec
        //checking to see if the word Details appears in the correct section
        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver)
                .searchForNote(title)
                .clickActivity(title); //or selectFirstNoteInList()
        Assert.assertTrue(noteDetailsPage.detailsPageExists());
    }

    @Test
    public void titleIsCorrect(){
        //checking to see if the title on the activity page is the same on the details page
        NoteDetailsPage note = new NoteDetailsPage(driver)
                .searchForNote(title)
                .clickActivity(title);
        String actualTitle = note.getActivityTitle();
        Assert.assertEquals("Titles do not match", actualTitle, title);
    }

    @Test
    public void locationIsCorrect(){
        //Checks if location in details page is the same as one displayed on activity page
        NoteDetailsPage note = new NoteDetailsPage(driver);

        note.searchForNote(title);
        String activityLocation = note.getActivityPageLocation();
        note.selectFirstNoteInList();

        String detailsLocation = note.getDetailsLocation();

        Assert.assertEquals("Locations do not match", detailsLocation, activityLocation);

    }

    @Test
    public void tagIsCorrect(){ //Run Time : 1min 55 secs
        //Checking to see if the tag on the details page is the same as tag generated above
        NoteDetailsPage note = new NoteDetailsPage(driver)
                .searchForNote(title)
                .selectFirstNoteInList()
                .addNewTag(tag);
        String actualTag = note.getDetailsTag();
        //Add '#' because the actual tag contains '#' in the beginning
        Assert.assertEquals("Tags do not match", actualTag, "#"+tag);

    }

    @Test
    public void dateIsCorrect() throws ParseException { //Not sure how to fix this test

        //Checking to see if date in details page is the same as one displayed on activity page
        NoteDetailsPage note = new NoteDetailsPage(driver);

        note.searchForNote(title);
        String activityDate = new SimpleDateFormat("EEEE, MMMM d, yyyy")
                .format(new SimpleDateFormat("MMM dd, yyyy")
                        .parse(new ActivityPage(driver).getDate()));
        note.selectFirstNoteInList();

        String detailsDate = note.getDetailsDate();
        // AssertTrue used because it works
        Assert.assertTrue("Date does not match", detailsDate.contains(activityDate));
    }

    @Test
    public void canEditActivity(){

        //Creates an Activity with Title, Note, Tag and Type
        //Changes Title and Type
        //Asserts that Title and Type correspond to the new Title

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);

        String note = "Automation Test for Notes " +RandomStringUtils.randomAlphanumeric(6);

        String newTitle = "Automation Edited " +RandomStringUtils.randomAlphanumeric(4);

        ActivityPage activityPage = new ActivityPage(driver)
                .logIcon()
                .enterNoteDetails(title,note,tag)
                .postActivity(FilterType.PHONE)
                .saveActivity();

        activityPage
                .changeToHundred()  //so that the Activity is on the first page (will fail if more than 100 Activities before it)
                .clickActivity(title)
                .goToEditPage()
                .editTitle(newTitle)
                .changeTypeOfActivity("Earnings")
                .saveAndClose()
                .waitForLoadingScreen();

        Assert.assertThat("Title has not been changed", noteDetailsPage.getActivityTitle(), containsString(newTitle));
        Assert.assertTrue("Activity type has not been changed", noteDetailsPage.isCorrectActivityType(FilterType.EARNINGS));

    }

    @Test
    public void canAddTag(){ //Run Time
        //Two parts; 1st part: Check if tag can be added from details page
        NoteDetailsPage note = new NoteDetailsPage(driver)
                .searchForNote(title)
                .selectFirstNoteInList()
                .addNewTag(newTag)
                .removeTag(2);

        note.pageRefresh();
        String actualNewTag = note.getDetailsTag();

        Assert.assertEquals("New tag does not match", actualNewTag, "#"+newTag);

        //2nd part: Check from the activity page if new tag is there
        note.goBackPages(1);
        note.searchForNote(title);

        String actualNewTagOnActivityPage = note.getActivityPageTag();

        Assert.assertEquals("New tag on activity page does not match", actualNewTagOnActivityPage, "#"+newTag);

    }
//TODO Add this test scenario
    @Test
    public void dealIsCorrect() {


    }

}

