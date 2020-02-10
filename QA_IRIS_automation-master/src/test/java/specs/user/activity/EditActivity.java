package specs.user.activity;

/**
 * Created by dannyl on 2017-05-04.
 */
import org.apache.commons.lang.RandomStringUtils;
import org.junit.*;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.noteDetailsPage.NoteDetailsPage;
import pageobjects.user.noteDetailsPage.editNoteDetailsPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

public class EditActivity extends AbstractSpec {
    public static final String keyword = "**AUTOMATION** ";

    @Before
    public void setUp(){
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
            activity.waitForLoadingScreen();
        }
        catch(Exception e){
            //I don't want tests to fail because the clean up failed
        }
    }
    // TODO Figure out how to assert suggesting an edit.
    @Ignore
    @Test
    public void suggestAnEdit(){
        String comment =keyword+ "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver).selectFirstNoteInList();
        noteDetailsPage.getSuggestEditTextBox().postASuggestedEdit(comment);

        // Hard to assert suggested edit
        // Assert.assertThat("Note text does not match expected", noteDetailsPage.get);
    }

    @Ignore
    @Test
    public void cancelASuggestedEdit(){
        String comment = keyword+ "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver).selectFirstNoteInList();
        noteDetailsPage.getSuggestEditTextBox().cancelASuggestedEdit(comment);
    }

    @Test
    public void canEditNoteTitle(){
        String comment =keyword+  "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver).yourActivityFilter().selectFirstNoteInList();
        String existingTitle = noteDetailsPage.getActivityTitle();
        String expectedTitle = existingTitle + comment;
        noteDetailsPage.goToEditPage();
        editNoteDetailsPage editPage = new editNoteDetailsPage(driver).editTitle(comment);
        editPage.saveChanges();

        Assert.assertThat("Title text does not match expected", noteDetailsPage.getActivityTitle(), containsString(expectedTitle));
    }

    // TODO Figure out why sendKeys isn't sending the first letter of note
    @Test
    public void canEditActivityNotes(){
        String note =keyword+ "This is a test note" + RandomStringUtils.randomAlphanumeric(6);
        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver).yourActivityFilter().selectFirstNoteInList();
        String existingNote = noteDetailsPage.getActivityDetails();
        String modifiedNote = note.substring(1);
        String expectedNote = modifiedNote + existingNote;
        noteDetailsPage.goToEditPage();
        editNoteDetailsPage editPage = new editNoteDetailsPage(driver).editNoteDetails(note);
        editPage.saveChanges();

        Assert.assertThat("Note text does not match expected", noteDetailsPage.getActivityDetails(), containsString(expectedNote));
    }

    //TODO Write custom assertion that goes back to the notes list page, searches for this activity and asserts the type. Cannot assert from the acvtivity page
    @Ignore
    @Test
    public void canChangeTypeOfActivity(){
        String[] type = {"email", "phone", "note", "roadshow", "meeting"};
        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver).yourActivityFilter().selectFirstNoteInList();

        for (int i = 0; i < 5; i++){

            noteDetailsPage.goToEditPage();
            editNoteDetailsPage editPage = new editNoteDetailsPage(driver).changeTypeOfActivity(type[i]);
            editPage.saveAndClose();


        }
    }

    @Test
    public void changeDate(){
        // The month of the expected date is just 6 months prior to the current month
        String expectedDate = "November 07, 2016";
        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver)
                .openCloseFilterMenu()
                .yourActivityFilter()
                .selectFirstNoteInList();
        noteDetailsPage.goToEditPage();
        editNoteDetailsPage editPage = new editNoteDetailsPage(driver);
        editPage.changeDate();
        editPage.saveChanges();

        Assert.assertThat("Date does not match expected", noteDetailsPage.getDate(), containsString(expectedDate));
    }
    @Test
    public void changeStartTime(){
        String[] time = {"12:00", "12:30", "1:00", "1:30", "2:00", "2:30", "3:00", "3:30", "4:00", "4:30",
                "5:00", "5:30", "6:00", "6:30", "7:00", "7:30", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30",
                "11:00", "11:30"};
        String[] dayOrNight = {"am", "pm"};
        int randomTime = (int) Math.random() * 24;
        int randomDayOrNight = (int) Math.random() * 2;
        String expectedTime = time[randomTime] + dayOrNight[randomDayOrNight];
        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver).yourActivityFilter().selectFirstNoteInList();
        noteDetailsPage.goToEditPage();
        editNoteDetailsPage editPage = new editNoteDetailsPage(driver).changeStartTime(expectedTime);
        editPage.saveChanges();

        Assert.assertThat("Start time does not match expected", noteDetailsPage.getVenueDetails(), containsString(expectedTime));
    }

    @Test
    public void changeEndTime(){
        String[] time = {"12:30", "12:00", "1:00", "1:30", "2:00", "2:30", "3:00", "3:30", "4:00", "4:30",
                "5:00", "5:30", "6:00", "6:30", "7:00", "7:30", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30",
                "11:00", "11:30"};
        String[] dayOrNight = {"am", "pm"};
        double randomTime = Math.random() * 24;
        double randomDayOrNight =  Math.random() * 2;
        String expectedTime = time[(int) randomTime] + dayOrNight[(int) randomDayOrNight];
        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver).yourActivityFilter().selectFirstNoteInList();
        noteDetailsPage.goToEditPage();
        editNoteDetailsPage editPage = new editNoteDetailsPage(driver).changeEndTime(expectedTime);
        editPage.saveChanges();

        Assert.assertThat("End time does not match expected", noteDetailsPage.getVenueDetails(), containsString(expectedTime));
    }


    @Test
    public void editVenue() {
        String someVenue = "Some Venue" + RandomStringUtils.randomAlphanumeric(6);
        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver).yourActivityFilter().selectFirstNoteInList();
        noteDetailsPage.goToEditPage();
        editNoteDetailsPage editPage = new editNoteDetailsPage(driver).addVenue(someVenue);
        editPage.saveChanges();

        Assert.assertThat("Venue does not match expected", noteDetailsPage.getVenueDetails(), containsString(someVenue));
    }

    @Test
    public void editLocation(){
        String someLocation = "Test Location" + RandomStringUtils.randomAlphanumeric(6);
        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver).yourActivityFilter().selectFirstNoteInList();
        noteDetailsPage.goToEditPage();
        editNoteDetailsPage editPage = new editNoteDetailsPage(driver).changeLocation(someLocation);
        editPage.saveChanges();

        Assert.assertThat("Location does not match expected", noteDetailsPage.getLocation(), containsString(someLocation));
    }

    @Test
    public void addParticipants(){
        String[] participants = {"P" + RandomStringUtils.randomAlphabetic(6), "P" + RandomStringUtils.randomAlphabetic(6),
                "P" + RandomStringUtils.randomAlphabetic(6)};
        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver).yourActivityFilter().selectFirstNoteInList();
        for (int i = 0; i < 3; i++){
            noteDetailsPage.goToEditPage();
            editNoteDetailsPage editPage = new editNoteDetailsPage(driver).createParticipants(participants[i]);
            editPage.saveChanges();

            Assert.assertThat("Participants don't match", noteDetailsPage.getParticipant(1), containsString(participants[i]));
        }
    }

    @Test
    public void editProfile()
    {
        String name = "Vanguard Ventures";
        String type = "Institution";
        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver).yourActivityFilter().selectFirstNoteInList();
        noteDetailsPage.goToEditPage();

        editNoteDetailsPage editPage = new editNoteDetailsPage(driver).editAttendees(name, type);
        editPage.saveChanges();

        Assert.assertThat("Name does not match expected", noteDetailsPage.getAttendees(), containsString(name));

    }

    @Test
    public void addItinerary(){
        String title = "Testing Title" + RandomStringUtils.randomAlphanumeric(6);
        String venue = "Some Venue" + RandomStringUtils.randomAlphanumeric(6);
        String participant = "P" + RandomStringUtils.randomAlphabetic(6);
        String name = "Vanguard Ventures";
        String type = "Institution";
        String startTime = "12:00pm";
        String endTime = "3:00pm";
        String expectedTime = startTime + " to " + endTime;
        String expectedDate = "November 07, 2016";

        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver).yourActivityFilter().selectFirstNoteInList();
        noteDetailsPage.goToEditPage();
        editNoteDetailsPage editPage = new editNoteDetailsPage(driver).showItinerary();

        // Editting every element of the itinerary
        editPage.editItineraryTitle(title);
        editPage.editItineraryVenue(venue);
        editPage.editItineraryParticipant(participant);
        editPage.editProfiles(name, type);
        editPage.changeItineraryStartTime(startTime);
        editPage.changeItineraryEndTime(endTime);
        editPage.changeIntineraryDate();
        editPage.addItinerary();
        editPage.saveChanges();

        Assert.assertThat("Title does not match expected", noteDetailsPage.getItineraryTitle(), containsString(title));
        Assert.assertThat("Venue does not match expected", noteDetailsPage.getItineraryVenue(), containsString(venue));
        Assert.assertThat("Time does not match expected", noteDetailsPage.getItineraryTime(), containsString(expectedTime));
        Assert.assertThat("Date does not match expected", noteDetailsPage.getItineraryDate(), containsString(expectedDate));
        Assert.assertThat("Participants does not match expected", noteDetailsPage.getItineraryParticipants(), containsString(participant));
        Assert.assertThat("Attendees does not match expected", noteDetailsPage.getItineraryAttendees(), containsString(name));


    }
}
