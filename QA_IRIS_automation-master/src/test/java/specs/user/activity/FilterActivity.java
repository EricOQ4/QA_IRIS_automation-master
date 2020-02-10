package specs.user.activity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.Calendar;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.activityPage.ColumnType;
import pageobjects.user.activityPage.FilterType;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;
import static org.hamcrest.CoreMatchers.containsString;
import static specs.user.activity.LogActivity.keyword;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by noelc on 2016-11-18.

 * Updated by danielh on 2019-04-29

 * Updated by shardulb on 2019-05-09
 */

public class FilterActivity extends AbstractSpec {
    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectActivityPageFromSideNav();
    }

    //Filter by Activity Type will only pass if an Activity type has 100 or less Activities (test doesn't take into account pagination)
    //Assertion One : Checks to make sure that all Activities listed are of the corresponding type
    //Assertion Two : Checks to make sure that the number of Checkboxes next to each Activity is
    //equal to the number of Activities the Filter Menu says it displays
    @Test
    public void filterByYourActivities() {

        ActivityPage activityPage = new ActivityPage(driver)
                .logNote()
                .enterNoteDetails("**AUTOMATION** Note", "This is an automation note", "getmerky")
                .saveActivity()
                .openCloseFilterMenu()
                .searchForNote("**Automation** Note")
                .changeToHundred()
                .yourActivityFilter();
        //Check that at least one of the displayed activities was created by automation script:
        Assert.assertEquals("Your Activity filter did not filter correctly", activityPage.getActivityTitle(0).substring(0, 14), keyword);
    }

    @Test
    public void filterActivitiesByEmail(){ //Est Duration : 1min

        FilterType email = FilterType.EMAIL;
        ActivityPage activityPage = new ActivityPage(driver)
                .openCloseFilterMenu()
                .changeToHundred()
                .clickFilterCheckbox(email);

        Assert.assertTrue("Filter not applied correctly for emails", activityPage.isFilteredCorrectly(email));
        Assert.assertEquals("Email count listed and displayed do not match", activityPage.getNumberOfDisplayedActivities(), activityPage.getCountFilter(email));

    }

    @Test
    public void filterActivitiesByNote(){ //see Above for info on Test Case
        FilterType note = FilterType.NOTE;

        ActivityPage activityPage = new ActivityPage(driver)
                .openCloseFilterMenu()
                .clickFilterCheckbox(note);

        Assert.assertTrue("Filter not applied correctly for notes", activityPage.isFilteredCorrectly(note));
        Assert.assertEquals("Note count listed and displayed do not match", activityPage.getNumberOfDisplayedActivities(), activityPage.getCountFilter(note));

    }

    @Test
    public void filterActivitiesByMeeting(){ //see Above for info on Test Scenario
        FilterType meeting = FilterType.MEETING;
        ActivityPage activityPage = new ActivityPage(driver)
                .openCloseFilterMenu()
                .clickFilterCheckbox(meeting);

        Assert.assertTrue("Filter not applied correctly for meetings",activityPage.isFilteredCorrectly(meeting));
        Assert.assertEquals("Meeting count listed and displayed do not match", activityPage.getNumberOfDisplayedActivities(), activityPage.getCountFilter(meeting));

    }

    @Test
    public void filterActivitiesByRoadshow(){ //see Above for info on Test Scenario
        FilterType roadshow = FilterType.ROADHSHOW;
        ActivityPage activityPage = new ActivityPage(driver)
                .openCloseFilterMenu()
                .clickFilterCheckbox(roadshow);

        Assert.assertTrue("Filter not applied correctly for meetings",activityPage.isFilteredCorrectly(roadshow));
        Assert.assertEquals("Roadshow count listed and displayed do not match", activityPage.getNumberOfDisplayedActivities(), activityPage.getCountFilter(roadshow));

    }

    @Test
    public void filterActivitiesByPhone(){ //see Above for info on Test Scenario
        FilterType phone = FilterType.PHONE;
        ActivityPage activityPage = new ActivityPage(driver)
                .openCloseFilterMenu()
                .clickFilterCheckbox(phone);

        Assert.assertTrue("Filter not applied correctly for phone",activityPage.isFilteredCorrectly(phone));
        Assert.assertEquals("Phone count listed and displayed do not match", activityPage.getNumberOfDisplayedActivities(), activityPage.getCountFilter(phone));
    }

    @Test
    public void filterActivitiesByConference() { //see Above for info on Test Scenario
        FilterType conference = FilterType.CONFERENCE;
        ActivityPage activityPage = new ActivityPage(driver)
                .openCloseFilterMenu()
                .clickFilterCheckbox(conference);

        Assert.assertTrue( "Filter not applied correctly for conferences",activityPage.isFilteredCorrectly(conference));
        Assert.assertEquals("Conference count listed and displayed do not match", activityPage.getNumberOfDisplayedActivities(), activityPage.getCountFilter(conference));

    }

    @Test
    public void filterActivitiesByEarnings() { //see Above for info on Test Scenario
        FilterType earnings = FilterType.EARNINGS;
        ActivityPage activityPage = new ActivityPage(driver)
                .openCloseFilterMenu()
                .clickFilterCheckbox(earnings);

        Assert.assertTrue ("Filter not applied correctly for earnings", activityPage.isFilteredCorrectly(earnings));
        Assert.assertEquals("Earnings count listed and displayed do not match", activityPage.getNumberOfDisplayedActivities(), activityPage.getCountFilter(earnings));
    }

//  Test the filtering of Corporate Participants Dropdown in Activities Section
    @Test
    public void filterActivityByCorpPart() {

        String corpPart = "A Automation Test";

        ActivityPage activityPage = new ActivityPage(driver)
                .openCloseFilterMenu()
                .changeToHundred()
                .dropDownCorpPartFilter()
                .clickSpecificCorpPartFilter(corpPart);

        try {
            Assert.assertThat("Title does not match expected", activityPage.getActivityTitle(0), containsString(keyword + "Original save"));
        }
        catch (Exception e) {
            System.out.println("Corporate Participant '" + corpPart + "' doesn't have any activities");
        }
    }

    @Test
    public void filterActivityByDate() {

        ActivityPage activityPage = new ActivityPage(driver)
                .openCloseFilterMenu()
                .changeToHundred()
                .updateDateRangeFilter();

        java.util.Calendar cal = java.util.Calendar.getInstance();
        String startDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());
        cal.add(java.util.Calendar.MONTH, 1);
        String endDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());

        Assert.assertTrue("The date is not in between start and end date of activity",
                helperDateFilter(startDate, activityPage.getActivityStartDate(0), endDate));
    }

    private boolean helperDateFilter(String sDate, String date, String eDate) {

        try {
            Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse(sDate);
            Date compareDate = new SimpleDateFormat("MM/dd/yy").parse(date);
            Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse(eDate);

            return ((startDate.compareTo(compareDate) <= 0) && (endDate.compareTo(compareDate) >= 0));

        } catch (Exception e) {
            System.out.println("Dates could not be parsed");
            return false; //Dates could not be parsed
        }
    }

//ToDO Fix this Calendar filter Test with new Implementation of Calendar filter
    public void filterActivitiesByDate(){

        ActivityPage activityPage = new ActivityPage(driver);
        Calendar calendar = new Calendar(driver);
        activityPage.filterDate(calendar);

        Assert.assertTrue("Activities cannot be filtered by date", activityPage.verifyDateFilter(calendar));
    }

    @Test
    public void sortActivitiesByType(){

        ColumnType type = ColumnType.TYPE;
        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.clickColumnHeader(type);

        Assert.assertTrue("Type not sorted correctly",activityPage.isColumnSorted(type));
    }

    @Test
    public void sortActivitiesByTitle(){
        ColumnType title = ColumnType.TITLE;
        ActivityPage activityPage = new ActivityPage(driver).clickColumnHeader(title);
        activityPage.clickColumnHeader(title);

        Assert.assertTrue("Title not sorted correctly (check DESKTOP-8696)",activityPage.isColumnSorted(title));
    }

    @Test
    public void sortActivitiesByDate(){

        ColumnType date = ColumnType.DATE;
        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.clickColumnHeader(date);

        Assert.assertTrue("Date not sorted correctly", activityPage.isColumnSorted(date));
    }

    @Test  //Tests will sometimes fail since when comparing the strings the +<number> appears in the string
    public void sortActivitiesByContact(){

        ColumnType contact = ColumnType.CONTACT;
        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.clickColumnHeader(contact);

        Assert.assertTrue("Contacts are not sorted correctly", activityPage.isColumnSorted(contact));
    }

    @Test  //Tests will sometimes fail since when comparing the strings the +<number>, appears in the string
    public void sortActivitiesByInstitution(){

        ColumnType institution = ColumnType.INSTITUTION;
        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.clickColumnHeader(institution);

        Assert.assertTrue("Institutions are not sorted correctly", activityPage.isColumnSorted(institution));

    }

    @Test
    public void multiSort(){
        //This checks that the sorting works even when it's been clicked before

        ColumnType type = ColumnType.TYPE;
        ColumnType title = ColumnType.TITLE;
        ColumnType date = ColumnType.DATE;


        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.clickColumnHeader(date);
        Assert.assertTrue("Date not sorted by ascending order", activityPage.isColumnSorted(date));

        activityPage.clickColumnHeader(type);
        Assert.assertTrue("Type not sorted by ascending order", activityPage.isColumnSorted(type));

        activityPage.clickColumnHeader(title);
        Assert.assertTrue("Known Issue: DESKTOP-8696", activityPage.isColumnSorted(title));

        //now going back, and clicking those filters again (to see if clicking them a second time has any effect
        activityPage.clickColumnHeader(date);
        Assert.assertTrue("Date not sorted by descending order", activityPage.isColumnSorted(date));

        activityPage.clickColumnHeader(type);
        Assert.assertTrue("Type not sorted by descending order", activityPage.isColumnSorted(type));

        activityPage.clickColumnHeader(title);
        Assert.assertTrue("Title not sorted by descending order", activityPage.isColumnSorted(title));
    }

}
