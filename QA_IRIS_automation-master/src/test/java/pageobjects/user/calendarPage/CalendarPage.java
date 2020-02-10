package pageobjects.user.calendarPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;
import pageobjects.user.activityPage.LogActivityPage;
import pageobjects.user.briefingBooks.BriefingBookDetailsPage;
import pageobjects.user.noteDetailsPage.NoteDetailsPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kelly J. on 2018-07-16
 * The Calendar page shows the user all their upcoming Activities in a Calendar layout (like Google Calendar)
 * From the Calendar page, the user can create Activities and filter what types of Activities they would like to be displayed.
 */

public class CalendarPage extends AbstractPageObject {

    // Calendar Header
    private final By headerLeftArrow = By.xpath("//button[contains(@class, 'fc-prev-button') and contains(@class, 'fc-corner-right')]");
    private final By headerRightArrow = By.xpath("//button[contains(@class, 'fc-next-button') and contains(@class, 'fc-corner-left')]");
    private final By todayButton = By.xpath("//button[contains(@class, 'fc-today-button')]");
    private final By outlookPluginButton = By.xpath("//button[contains(text(), 'Windows Outlook plugin')]");
    private final By mainMonth = By.xpath("//div[contains(@class, 'fc-center')]/h2");
    private final By activityPlusButton = By.xpath("//button[contains(@class, 'fc-addEvent-button')]");
    private final By monthButton = By.xpath("//button[contains(text(), 'Month')]");
    private final By weekButton = By.xpath("//button[contains(text(), 'Week')]");
    private final By dayButton = By.xpath("//button[contains(text(), 'Day')]");
    private final By scheduleButton = By.xpath("//button[contains(text(), 'Schedule')]");
    private final By iframe = By.xpath("//iframe[contains(@id, 'sencha-viewport')]");

    // Create Activity mini-logger
    private final By miniLoggerMoreOptions = By.xpath("//span[contains(text(), 'More Options')]");
    private final By miniLoggerTitleField = By.xpath("//input[@placeholder='New Activity']");
    private final By miniLoggerStartTimeField = By.xpath("//input[@name='start_time']");
    private final By miniLoggerEndTimeField = By.xpath("//input[@name='end_time']");
    private final By activityTypeDropdown = By.xpath("//div[contains(@class, 'dropdown')]//span[@class='x-button-label']");

    // Activity type dropdown options in the Activity mini-logger
    private By[] activityTypeDropdowns = new By[6];
    private final By activityTypeDropdown_Note = By.xpath("(//div[@class='item'])[2]");
    private final By activityTypeDropdown_Phone = By.xpath("(//div[@class='item'])[3]");
    private final By activityTypeDropdown_Email = By.xpath("(//div[@class='item'])[4]");
    private final By activityTypeDropdown_Meeting = By.xpath("(//div[@class='item'])[5]");
    private final By activityTypeDropdown_Roadshow = By.xpath("(//div[@class='item'])[6]");
    private final By activityTypeDropdown_Conference = By.xpath("(//div[@class='item'])[7]");
    private final By miniLoggerSaveButton = By.xpath("//span[contains(text(), 'Save')]");

    // Calendar cells (ie. cell1 is the top left cell, cell2 is the cell to the right of cell1, etc)
    private By[] calendarCells = new By[6];
    private final By calendarCell1 = By.xpath("(//td[contains(@class, 'fc-sun')])[16]");
    private final By calendarCell2 = By.xpath("(//td[contains(@class, 'fc-mon')])[16]");
    private final By calendarCell3 = By.xpath("(//td[contains(@class, 'fc-tue')])[16]");
    private final By calendarCell4 = By.xpath("(//td[contains(@class, 'fc-wed')])[16]");
    private final By calendarCell5 = By.xpath("(//td[contains(@class, 'fc-thu')])[16]");
    private final By calendarCell6 = By.xpath("(//td[contains(@class, 'fc-fri')])[16]");

    // Activity preview modal (Type icons)
    private By[] activityTypeIcons = new By[6];
    private final By noteIcon = By.xpath("//i[contains(@class, 'q4i-note-2pt')]");
    private final By phoneIcon = By.xpath("//i[contains(@class, 'q4i-phone-2pt')]");
    private final By emailIcon = By.xpath("//i[contains(@class, 'q4i-mail-2pt')]");
    private final By meetingIcon = By.xpath("//i[contains(@class, 'q4i-meeting-2pt')]");
    private final By roadshowIcon = By.xpath("//i[contains(@class, 'q4i-roadshow-2pt')]");
    private final By conferenceIcon = By.xpath("//i[contains(@class, 'q4i-conference-2pt')]");

    // Activity preview modal
    private final By activityPreviewTitle = By.xpath("//h1[@class='title']");
    private final By activityPreviewDateAndTime = By.xpath("//div[@class='section']");
    private final By activityPreviewTime = By.xpath("//div[@class='section']//div[@class='time']");
    private final By activityPreviewUtilityMenu = By.xpath("//span[contains(@class, 'q4i-utility-4pt')]");
    private final By utilityMenuCreateBriefingBook = By.xpath("//span[contains(text(), 'Create Briefing Book')]");
    private final By activityPreviewViewDetailsButton = By.xpath("//span[contains(text(), 'View Details')]");
    private final By closeButton = By.xpath("//span[contains(@class, 'q4i-close-4pt')]");
    private final By utilityMenuDeleteActivity = By.xpath("//span[contains(text(), 'Delete Activity')]");
    private final By deleteActivityYesButton = By.xpath("//span[contains(text(), 'Yes')]");

    // Activity types checkboxes (left panel)
    private By[] activityTypeCheckboxes = new By[6];
    private final By noteCheckbox = By.xpath("//div[contains(@class,'checkmark') and contains(@class, 'citrus')]");
    private final By phoneCheckbox = By.xpath("//div[contains(@class,'checkmark') and contains(@class, 'sky')]");
    private final By emailCheckbox = By.xpath("//div[contains(@class,'checkmark') and contains(@class, 'tangerine')]");
    private final By meetingCheckbox = By.xpath("//div[contains(@class,'checkmark') and contains(@class, 'teal')]");
    private final By roadshowCheckbox = By.xpath("//div[contains(@class,'checkmark') and contains(@class, 'spice')]");
    private final By conferenceCheckbox = By.xpath("//div[contains(@class,'checkmark') and contains(@class, 'eggplant')]");

    //Activity selector buttons in Calendar cells 1-6
    private By[] activityCells = new By[6];
    private final By activityCellList = By.xpath("//a[contains(@class,'fc-day-grid-event')]");
    private final By activityCell1 = By.xpath("(//a[contains(@class,'fc-day-grid-event')])[1]");
    private final By activityCell2 = By.xpath("(//a[contains(@class,'fc-day-grid-event')])[2]");
    private final By activityCell3 = By.xpath("(//a[contains(@class,'fc-day-grid-event')])[3]");
    private final By activityCell4 = By.xpath("(//a[contains(@class,'fc-day-grid-event')])[4]");
    private final By activityCell5 = By.xpath("(//a[contains(@class,'fc-day-grid-event')])[5]");
    private final By activityCell6 = By.xpath("(//a[contains(@class,'fc-day-grid-event')])[6]");

    public CalendarPage(WebDriver driver) {super(driver);}

    //Set up the xpath arrays so you don't need to create methods for each type of Activity
    //You just need to pass in an index into the method to specify what Activity type the test is for
    public void setCalendarCells() {
        calendarCells[0] = calendarCell1;
        calendarCells[1] = calendarCell2;
        calendarCells[2] = calendarCell3;
        calendarCells[3] = calendarCell4;
        calendarCells[4] = calendarCell5;
        calendarCells[5] = calendarCell6;
    }

    public void setActivityTypeDropdowns() {
        activityTypeDropdowns[0] = activityTypeDropdown_Note;
        activityTypeDropdowns[1] = activityTypeDropdown_Phone;
        activityTypeDropdowns[2] = activityTypeDropdown_Email;
        activityTypeDropdowns[3] = activityTypeDropdown_Meeting;
        activityTypeDropdowns[4] = activityTypeDropdown_Roadshow;
        activityTypeDropdowns[5] = activityTypeDropdown_Conference;
    }

    public void setActivityTypeIcons() {
        activityTypeIcons[0] = noteIcon;
        activityTypeIcons[1] = phoneIcon;
        activityTypeIcons[2] = emailIcon;
        activityTypeIcons[3] = meetingIcon;
        activityTypeIcons[4] = roadshowIcon;
        activityTypeIcons[5] = conferenceIcon;
    }

    public void setActivityTypeCheckboxes() {
        activityTypeCheckboxes[0] = noteCheckbox;
        activityTypeCheckboxes[1] = phoneCheckbox;
        activityTypeCheckboxes[2] = emailCheckbox;
        activityTypeCheckboxes[3] = meetingCheckbox;
        activityTypeCheckboxes[4] = roadshowCheckbox;
        activityTypeCheckboxes[5] = conferenceCheckbox;
    }

    public void setActivityCells() {
        activityCells[0] = activityCell1;
        activityCells[1] = activityCell2;
        activityCells[2] = activityCell3;
        activityCells[3] = activityCell4;
        activityCells[4] = activityCell5;
        activityCells[5] = activityCell6;
    }

    //Determines whether a 'Create New Activity' page was opened (checks modal's title)
    public boolean wasCreateActivityPageOpened(AbstractPageObject page) {
        return (page.getModalPageTitle()).equals("Create New Activity");
    }

    //Clicks the + button
    //The test calls wasCreateActivityPageOpened to determine whether the page opened was a 'Create New Activity' page
    public AbstractPageObject createActivityFromPlusButton() {
        waitForLoadingScreen();
        waitForElementToBeClickable(activityPlusButton).click();
        return new AbstractPageObject(getDriver());
    }

    //Clicks a calendar cell and then the 'More Options' button
    //The test calls wasCreateActivityPageOpened to determine whether the page opened was a 'Create New Activity' page
    public AbstractPageObject createActivityFromMoreOptions() {
        waitForLoadingScreen();
        waitForElementToBeClickable(calendarCell1).click();
        waitForLoadingScreen();

        waitForElementToBeClickable(miniLoggerMoreOptions).click();
        return new AbstractPageObject(getDriver());
    }

    /*public boolean downloadOutlookPlugin() {

    }*/

    //Creates Activity in the Calendar cell with the type corresponding to 'index'
    //   eg. index of 0 = Note activity in Calendar Cell 1
    //       index of 1 = Phone activity in Calendar Cell 2
    //Sets the start and end time
    public void createActivityFromCalendarCell(int index, String title, String startTime, String endTime) {
        waitForLoadingScreen();
        waitForElementToBeClickable(calendarCells[index]).click();
        waitForLoadingScreen();

        waitForElementToBeClickable(miniLoggerTitleField).click();
        findElement(miniLoggerTitleField).sendKeys(title);

        waitForElementToBeClickable(miniLoggerStartTimeField).click();
        findElement(miniLoggerStartTimeField).sendKeys(startTime);

        waitForElementToBeClickable(miniLoggerEndTimeField).click();
        findElement(miniLoggerEndTimeField).sendKeys(endTime);

        waitForElementToBeClickable(activityTypeDropdown).click();
        waitForElementToBeClickable(activityTypeDropdowns[index]).click();

        waitForElementToBeClickable(miniLoggerSaveButton).click();
        waitForLoadingScreen();
    }

    //Opens activity in the Calendar cell corresponding to 'index' and verifies the details in the preview modal:
    //   Title
    //   Start and End Time
    //   Correct Activity type icon is in the header
    public boolean verifyActivityDetails(int index, String correctTitle, String correctStartTime, String correctEndTime) {
        waitForLoadingScreen();
        pause(1000L);
        waitForElementToBeClickable(activityCellList).click();
        waitForLoadingScreen();
        String title = waitForElementToAppear(activityPreviewTitle).getText();
        int numIcons = 0;

        //Extract the start and end times from the Activity details string
        String details = waitForElementToAppear(activityPreviewTime).getText();
        String times = details.substring(0, details.length() - 4);
        String startTime = times.substring(0, times.indexOf(" ")) +
                (times.substring(times.indexOf(" ") + 1, times.indexOf(" ") + 3)).toLowerCase();
        times = times.substring(times.indexOf("- ") + 2);
        String endTime = times.substring(0, times.indexOf(" ")) +
                (times.substring(times.indexOf(" ") + 1, times.indexOf(" ") + 3)).toLowerCase();

        //Checks that 2 of the appropriate icon shows up (2 because there's 1 in the preview modal and 1 in the checkbox list)
        numIcons = driver.findElements(activityTypeIcons[index]).size();

        waitForElementToBeClickable(closeButton).click();
        waitForLoadingScreen();

        return title.equals(correctTitle) && startTime.equals(correctStartTime) && endTime.equals(correctEndTime) && numIcons == 2;
    }

    //Returns a List of all the activity titles that are displayed in the Calendar
    private List<WebElement> returnActivitiesInCalendar() {
        List<WebElement> rowList = findVisibleElements(activityCellList);
        ArrayList<WebElement> tableRowsList = new ArrayList<>(rowList);
        return tableRowsList;

    }

    //Deletes activity in the Calendar cell corresponding to 'index'
    //Checks the activity is deleted by obtaining a List of all the activities in the Calendar and checking that the
    //   activity is not in the List
    public boolean deleteActivity(int index, String activityTitle) {
        waitForLoadingScreen();
        waitForElementToBeClickable(activityCellList).click();
        waitForLoadingScreen();
        waitForElementToBeClickable(activityPreviewUtilityMenu).click();
        waitForLoadingScreen();
        waitForElementToBeClickable(utilityMenuDeleteActivity).click();
        waitForLoadingScreen();
        waitForElementToBeClickable(deleteActivityYesButton).click();
        waitForLoadingScreen();
        pause(2000L);

        List<WebElement> activities = returnActivitiesInCalendar();

        for(WebElement activity : activities){
            if(activity.getText().contains(activityTitle)){
                return false;
            }
        }

        return true;
    }

    // Checks that clicking 'View Details' in the Activity preview modal takes you to an Activity details page
    // Verifies that the Activity title on the details page is correct
    public String viewActivityDetails(int index, String title, String startTime, String endTime) {
        createActivityFromCalendarCell(index, title, startTime, endTime);
        waitForLoadingScreen();
        pause(500L);
        waitForElementToBeClickable(activityCellList).click();
        waitForLoadingScreen();
        waitForElementToBeClickable(activityPreviewViewDetailsButton).click();
        waitForLoadingScreen();
        NoteDetailsPage detailsPage = new NoteDetailsPage(getDriver());
        String activityTitle = detailsPage.getActivityTitle();

        waitForLoadingScreen();
        waitForElementToBeClickable(activityPreviewUtilityMenu).click();
        waitForLoadingScreen();
        waitForElementToBeClickable(utilityMenuDeleteActivity).click();
        waitForLoadingScreen();
        waitForElementToBeClickable(deleteActivityYesButton).click();
        waitForLoadingScreen();

       // if (deleteActivity(index, title)) {
            return activityTitle;
      //  } else {
       //     return "";
       // }
    }

    //Click the Activity type checkbox corresponding to 'index'
    //ex. index 0 = note
    public void clickActivityTypeCheckbox(int index) {
        pause(2000L);
        waitForElementToBeClickable(activityTypeCheckboxes[index]).click();
        pause(2000L);
    }


    //Unchecks the Activity type checkbox corresponding to 'index'
    //Checks the activity of the corresponding type is not displayed anymore by obtaining a list of all the activities
    //    in the Calendar and checking that the activity is not in the list
    public boolean checkActivityTypeCheckbox(int index, String activityTitle) {
        waitForLoadingScreen();
        clickActivityTypeCheckbox(index);

        List<WebElement> activities = returnActivitiesInCalendar();

        for(WebElement activity : activities) {
            if(activity.getText().contains(activityTitle)){
                return false;
            }
        }

        return true;
    }

    //Creates a Note in the 1st Cell, opens the Create New Activity modal and adds Vanguard as an attendee, and saves
    //Returns back to the Calendar Page
    public void createActivityWithAttendees(String title) {
        waitForLoadingScreen();
        waitForElementToBeClickable(calendarCell1).click();
        waitForLoadingScreen();

        waitForElementToBeClickable(miniLoggerMoreOptions).click();
        waitForLoadingScreen();
        new LogActivityPage(getDriver())
                //.enterNoteDetails(title, "note", "tag")
                .linkNoteToInstitution("The Vanguard Group")
                .saveActivity();

        /*new ActivityPage(getDriver())
                .accessSideNavFromPage()
                .selectCalendarFromSideNav();*/

    }

    //Opens the Preview modal, and from the Utility menu, creates a Briefing Book
    //Verifies the Briefing Book name is the activity title
    public boolean createBriefingBook(String title) {
        waitForLoadingScreen();
        waitForElementToBeClickable(activityCellList).click();
        waitForLoadingScreen();
        waitForElementToBeClickable(activityPreviewUtilityMenu).click();
        waitForLoadingScreen();
        waitForElementToBeClickable(utilityMenuCreateBriefingBook).click();
        waitForLoadingScreen();
        pause(2000L);
        BriefingBookDetailsPage bbDetailsPage = new BriefingBookDetailsPage(getDriver());

        String bbTitle = bbDetailsPage.getPageTitle();
        System.out.println(bbTitle);

        leaveiframe();

        bbDetailsPage.accessSideNavFromPage()
                .selectCalendarFromSideNav();

        return bbTitle.contains(title);
    }

    //Clicks Month button in Calendar Header
    //Checks that the button is active
    public boolean clickMonthButton() {
        waitForLoadingScreen();
        waitForElementToBeClickable(weekButton).click();
        waitForLoadingScreen();
        pause(500L);
        waitForElementToBeClickable(monthButton).click();
        waitForLoadingScreen();
        return findElement(monthButton).getAttribute("class").contains("fc-state-active");
    }

    //Clicks Week button in Calendar Header
    //Checks that the button is active
    public boolean clickWeekButton() {
        waitForLoadingScreen();
        pause(500L);
        waitForElementToBeClickable(weekButton).click();
        waitForLoadingScreen();
        return findElement(weekButton).getAttribute("class").contains("fc-state-active");
    }

    //Clicks Day button in Calendar Header
    //Checks that the button is active
    public boolean clickDayButton() {
        waitForLoadingScreen();
        pause(500L);
        waitForElementToBeClickable(dayButton).click();
        waitForLoadingScreen();
        return findElement(dayButton).getAttribute("class").contains("fc-state-active");
    }

    //Clicks Schedule button in Calendar Header
    //Checks that the button is active
    public boolean clickScheduleButton() {
        waitForLoadingScreen();
        pause(500L);
        waitForElementToBeClickable(scheduleButton).click();
        waitForLoadingScreen();
        return findElement(scheduleButton).getAttribute("class").contains("fc-state-active");
    }

    //Clicks the header left arrow twice
    //Checks that the month/year updates accordingly and matches the mini calendar

    public void switchToiframe(){
        WebElement _iframe = findElement(iframe);
        driver.switchTo().frame(_iframe);
        pause(500L);
    }

    public void leaveiframe(){
        driver.switchTo().defaultContent();
        pause(500L);
    }
}
