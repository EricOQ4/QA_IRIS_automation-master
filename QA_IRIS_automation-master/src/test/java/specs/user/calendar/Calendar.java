package specs.user.calendar;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.AbstractPageObject;
import pageobjects.user.calendarPage.CalendarPage;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by Kelly J. on 2018-07-16
 */

public class Calendar extends AbstractSpec {

    String activityTitle;
    String startTime = "10:15am";
    String endTime = "11:11pm";

    @Before
    public void setUp() {
        new LoginPage(driver)
                .loginUserCloseWelcomeNote()
                .accessSideNav()
                .selectCalendarFromSideNav();
        activityTitle = "AUTOMATION Calendar Activity Test" + RandomStringUtils.randomAlphanumeric(6);
    }


    //Clicks + button and confirms a Create New Activity modal is opened
    @Test
    public void canCreateActivityFromPlusButton() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.switchToiframe();
        AbstractPageObject page = calendarPage.createActivityFromPlusButton();

        Assert.assertTrue("Plus button didn't open Create new Activity Modal",calendarPage.wasCreateActivityPageOpened(page));
    }

    //Clicks a Calendar cell, "More Options" button, and confirms a Create New Activity modal is opened
    @Test
    public void canCreateActivityFromMoreOptions() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.switchToiframe();
        AbstractPageObject page = calendarPage.createActivityFromMoreOptions();

        Assert.assertTrue("'More Options' button didn't open Create new Activity Modal",calendarPage.wasCreateActivityPageOpened(page));
    }

    //Creates a Note activity, verifies the details in the Preview modal, and then deletes activity
    @Test
    public void createAndDeleteNoteActivity() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.switchToiframe();
        calendarPage.setCalendarCells();
        calendarPage.setActivityTypeDropdowns();
        calendarPage.setActivityTypeIcons();
        calendarPage.setActivityCells();

        calendarPage.createActivityFromCalendarCell(0, activityTitle, startTime, endTime);
        Assert.assertTrue("Note activity details are not correct", calendarPage.verifyActivityDetails(0, activityTitle, startTime, endTime));

        calendarPage.pause(2000L);
        Assert.assertTrue("Note activity not deleted properly", calendarPage.deleteActivity(0, activityTitle));
    }

    //Creates a Phone activity, verifies the details in the Preview modal, and then deletes activity
    @Test
    public void createAndDeletePhoneActivity() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.switchToiframe();
        calendarPage.setCalendarCells();
        calendarPage.setActivityTypeDropdowns();
        calendarPage.setActivityTypeIcons();
        calendarPage.setActivityCells();

        calendarPage.createActivityFromCalendarCell(1, activityTitle, startTime, endTime);
        Assert.assertTrue("Phone activity details are not correct", calendarPage.verifyActivityDetails(1, activityTitle, startTime, endTime));

        calendarPage.pause(2000L);
        Assert.assertTrue("Phone activity not deleted properly", calendarPage.deleteActivity(1, activityTitle));
    }

    //Creates an Email activity, verifies the details in the Preview modal, and then deletes activity
    @Test
    public void createAndDeleteEmailActivity() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.switchToiframe();
        calendarPage.setCalendarCells();
        calendarPage.setActivityTypeDropdowns();
        calendarPage.setActivityTypeIcons();
        calendarPage.setActivityCells();

        calendarPage.createActivityFromCalendarCell(2, activityTitle, startTime, endTime);
        Assert.assertTrue("Email activity details are not correct", calendarPage.verifyActivityDetails(2, activityTitle, startTime, endTime));

        calendarPage.pause(2000L);
        Assert.assertTrue("Email activity not deleted properly", calendarPage.deleteActivity(2, activityTitle));
    }

    //Creates a Meeting activity, verifies the details in the Preview modal, and then deletes activity
    @Test
    public void createAndDeleteMeetingActivity() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.switchToiframe();
        calendarPage.setCalendarCells();
        calendarPage.setActivityTypeDropdowns();
        calendarPage.setActivityTypeIcons();
        calendarPage.setActivityCells();

        calendarPage.createActivityFromCalendarCell(3, activityTitle, startTime, endTime);
        Assert.assertTrue("Meeting activity details are not correct", calendarPage.verifyActivityDetails(3, activityTitle, startTime, endTime));

        calendarPage.pause(2000L);
        Assert.assertTrue("Meeting activity not deleted properly", calendarPage.deleteActivity(3, activityTitle));
    }

    //Creates a Roadshow activity, verifies the details in the Preview modal, and then deletes activity
    @Test
    public void createAndDeleteRoadshowActivity() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.switchToiframe();
        calendarPage.setCalendarCells();
        calendarPage.setActivityTypeDropdowns();
        calendarPage.setActivityTypeIcons();
        calendarPage.setActivityCells();

        calendarPage.createActivityFromCalendarCell(4, activityTitle, startTime, endTime);
        Assert.assertTrue("Roadshow activity details are not correct", calendarPage.verifyActivityDetails(4, activityTitle, startTime, endTime));

        calendarPage.pause(2000L);
        Assert.assertTrue("Roadshow activity not deleted properly", calendarPage.deleteActivity(4, activityTitle));
    }

    //Creates a Conference activity, verifies the details in the Preview modal, and then deletes activity
    @Test
    public void createAndDeleteConferenceActivity() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.switchToiframe();
        calendarPage.setCalendarCells();
        calendarPage.setActivityTypeDropdowns();
        calendarPage.setActivityTypeIcons();
        calendarPage.setActivityCells();

        calendarPage.createActivityFromCalendarCell(5, activityTitle, startTime, endTime);
        Assert.assertTrue("Conference activity details are not correct", calendarPage.verifyActivityDetails(5, activityTitle, startTime, endTime));

        calendarPage.pause(2000L);
        Assert.assertTrue("Conference activity not deleted properly", calendarPage.deleteActivity(5, activityTitle));
    }

    //Creates activity, opens Preview modal, clicks 'View Details' button to go to Activity details page, and verifies
    //the title on the details page is correct
    @Test
    public void canViewActivityDetails() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.switchToiframe();
        calendarPage.setCalendarCells();
        calendarPage.setActivityTypeDropdowns();
        calendarPage.setActivityCells();

        Assert.assertEquals("Activity title is incorrect in the details page from 'View Details'", activityTitle,
                calendarPage.viewActivityDetails(0, activityTitle, startTime, endTime));
    }

    //Creates a Note activity, clicks the Note checkbox, checks that the Note is gone from the calendar, and deletes
    //Repeat for all the other Activity types
    @Test
    public void verifyActivityTypeCheckboxes() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.switchToiframe();
        calendarPage.setCalendarCells();
        calendarPage.setActivityTypeDropdowns();
        calendarPage.setActivityCells();
        calendarPage.setActivityTypeCheckboxes();

        String noteTitle = "AUTOMATION Calendar Test Note";
        String phoneTitle = "AUTOMATION Calendar Test Phone";
        String emailTitle = "AUTOMATION Calendar Test Email";
        String meetingTitle = "AUTOMATION Calendar Test Meeting";
        String roadshowTitle = "AUTOMATION Calendar Test Roadshow";
        String conferenceTitle = "AUTOMATION Calendar Test Conference";

        calendarPage.createActivityFromCalendarCell(0, noteTitle, startTime, endTime);
        Assert.assertTrue("Note checkbox not working correctly", calendarPage.checkActivityTypeCheckbox(0, noteTitle));
        calendarPage.clickActivityTypeCheckbox(0);
        calendarPage.deleteActivity(0, noteTitle);

        calendarPage.createActivityFromCalendarCell(1, phoneTitle, startTime, endTime);
        Assert.assertTrue("Phone checkbox not working correctly", calendarPage.checkActivityTypeCheckbox(1, phoneTitle));
        calendarPage.clickActivityTypeCheckbox(1);
        calendarPage.deleteActivity(1, phoneTitle);

        calendarPage.createActivityFromCalendarCell(2, emailTitle, startTime, endTime);
        Assert.assertTrue("Email checkbox not working correctly", calendarPage.checkActivityTypeCheckbox(2, emailTitle));
        calendarPage.clickActivityTypeCheckbox(2);
        calendarPage.deleteActivity(2, emailTitle);

        calendarPage.createActivityFromCalendarCell(3, meetingTitle, startTime, endTime);
        Assert.assertTrue("Meeting checkbox not working correctly", calendarPage.checkActivityTypeCheckbox(3, meetingTitle));
        calendarPage.clickActivityTypeCheckbox(3);
        calendarPage.deleteActivity(3, meetingTitle);

        calendarPage.createActivityFromCalendarCell(4, roadshowTitle, startTime, endTime);
        Assert.assertTrue("Roadshow checkbox not working correctly", calendarPage.checkActivityTypeCheckbox(4, roadshowTitle));
        calendarPage.clickActivityTypeCheckbox(4);
        calendarPage.deleteActivity(4, roadshowTitle);

        calendarPage.createActivityFromCalendarCell(5, conferenceTitle, startTime, endTime);
        Assert.assertTrue("Conference checkbox not working correctly", calendarPage.checkActivityTypeCheckbox(5, conferenceTitle));
        calendarPage.clickActivityTypeCheckbox(5);
        calendarPage.deleteActivity(5, conferenceTitle);
    }

    //Creates a Note activity with an attendee, creates a BB, and verifies it takes you to a BB details page with
    //the correct title
    @Test
    public void canCreateBriefingBook() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.switchToiframe();
        calendarPage.setCalendarCells();
        String title = "AUTOMATION attendees";

        calendarPage.createActivityWithAttendees(title);
        Assert.assertTrue("Create Briefing Book option not working", calendarPage.createBriefingBook("New Note"));
        calendarPage.switchToiframe();
        calendarPage.deleteActivity(0, title);
    }

    //Clicks Month/Week/Day/Schedule buttons in Calendar Header
    //Checks that the buttons become active when clicked
    @Test
    public void canClickDateFilterButtons() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.switchToiframe();
        Assert.assertTrue("Month button not working", calendarPage.clickMonthButton());
        Assert.assertTrue("Week button not working", calendarPage.clickWeekButton());
        Assert.assertTrue("Day button not working", calendarPage.clickDayButton());
        Assert.assertTrue("Schedule button not working", calendarPage.clickScheduleButton());
    }

    /*
    //To test access to all pages in the app
    //Commented out after test since it is not related to Calendar page
    @Test
    public void accessAllPages() {
        CalendarPage calendarPage = new CalendarPage(driver);
        calendarPage.accessSideNav().selectContactsFromSideNav();
        calendarPage.accessSideNav().selectDashboardFromSideNav();
        calendarPage.accessSideNav().selectActivityPageFromSideNav();
        calendarPage.accessSideNav().selectEventsAndTranscriptsFromSideNav();
        calendarPage.accessSideNav().selectBriefingBookFromSideNav();
        calendarPage.accessSideNav().selectReportsFromSideNav();
        calendarPage.accessSideNav().selectOwnershipFromSideNav();
        calendarPage.accessSideNav().selectEstimatesFromSideNav();
        calendarPage.accessSideNav().selectWebAnalyticsFromSideNav();
        calendarPage.accessSideNav().selectWebcastAnalyticsFromSideNav();
        calendarPage.accessSideNav().selectSecurityFromSideNav();
        calendarPage.accessSideNav().selectaiTargetingFromSideNav();
        calendarPage.accessSideNav().selectAdvancedSearchFromSideNav();
        calendarPage.accessSideNav().selectResearchFromSideNav();
    }
    */
}


