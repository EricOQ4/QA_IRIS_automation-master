package pageobjects.user.activityPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.contactPage.CorpPartDetailsPage;
import pageobjects.user.noteDetailsPage.NoteDetailsPage;

/**
 * Created by dannyl on 2017-06-20.
 *
 * Updated by ShardulB on 2019-05-12
 */


public class LogActivityPage extends AbstractPageObject{


    //Buttons
    private final By cancelActivityButton = By.xpath ("//button [contains(@class,'button--shaded')] [text()='Cancel']");
    private final By saveButton = By.xpath ("//button [contains(@class,'button--citrus')] [text()='Save']");
    private final By saveCloseButton = By.xpath ("//button [contains(@class,'button--citrus')] [text()='Save & Close']");
    private final By saveBuildItinerary = By.xpath ("//button [contains (@class,'button--citrus')][text()='Save & Build Itinerary']"); //for parent only
    private final By saveAddAnother = By.xpath ("//button [contains(@class,'button--citrus')][text()='Save & Add Another']");
    private final By cancelDiscard = By.xpath ("/div [@class='message_actions'] /button [text()='Cancel']");
    private final By proceedDiscard = By.xpath ("//div [@class='message_actions'] /button [text()='Proceed']");
    private final By xButton = By.xpath ("//div [@class='modal_exit'] /i [@class='q4i-close-4pt']");

    //Activity and Attendees Type

    private final By institutionIcon = By.xpath ("//button [contains(@class,'range-tab_button')] /div [text()='Institutions']");
    private final By fundIcon = By.xpath ("//button [contains(@class,'range-tab_button')] /div [text()='Funds']");
    private final By contactIcon = By.xpath ("//button [contains(@class,'range-tab_button')] /div [text()='Contacts']");

    private final By noteTab = By.xpath("//label [@for='id--comment']");
    private final By phoneTab = By.xpath ("//label [@for='id--phone']");
    private final By emailTab = By.xpath("//label [@for='id--email']");
    private final By meetingTab = By.xpath("//label [@for='id--meeting']");
    private final By earningsTab = By.xpath("//label [@for='id--earnings']");
    private final By roadshowTab = By.xpath("//label [@for='id--roadshow']");
    private final By conferenceTab = By.xpath("//label [@for='id--conference']");

    //All Fields

    private final By titleField = By.xpath("//input [@placeholder='New Activity']");
    private final By locationField = By.xpath("//input [@placeholder='City, State, Country']");
    private final By venueField = By.xpath("//input [@placeholder='Address']");
    private final By dealField = By.xpath ("//input [@placeholder='Title']");
    private final By typeNoteField = By.xpath ("//div [@class='DraftEditor-editorContainer']/div [@role='textbox']");
    private final By tagField = By.xpath ("//input [contains(@placeholder,'travel')]");
    private final By CpField = By.xpath("//input [@placeholder='Full Name']");
    private final By keywordField = By.xpath("//input[ contains(@placeholder, 'Keyword')]");

    //First Element in List

    private final By firstLocation = By.xpath ("//li [@class='autocomplete_item'] [1] ");
    private final By firstDeal = By.xpath ("//li [contains(@class,'autocomplete_item--selected')] [1]");
    private final By firstTag = By.xpath ("//div [@class='select-list__item'] [1]");
    private final By firstAttendee = By.xpath ("//div [@class='select-list__item'] [1]"); //applies to both CPs and Attendees

    //Removal of First Element

    private final By removeFirstCp = By.xpath ("//div [contains(@class,'participants')]/descendant::div[@class='list-editable']/div[1]/descendant::div[@class='item-header_delete-btn']");
    private final By removeFirstAttendee = By.xpath ("//div [contains(@class,'attendees')]/descendant::div[@class='list-editable']/div[1]/descendant::div[@class='item-header_delete-btn']");
    private final By removeFirstTag = By.xpath ("//div [contains(@class,'tags')]/descendant::div[@class='list-editable']/div[1]/descendant::div[@class='item-header_delete-btn']");
    private final By removeFirstLocation = By.xpath ("//div [contains(@class,'multi-location')]/descendant::div[@class='list-editable']/div[1]/descendant::div[@class='item-header_delete-btn']");
//removeFirstLocation only to be used for Conference/Roadshow

    //Date and Time

    private final By startDate = By.xpath ("//div [contains(@class,'start')] /descendant::input [@class='date-picker_input']");
    private final By startTime = By.xpath ("//div [contains(@class,'start field')] /descendant::input[ @type='text']");
    private final By endDate = By.xpath ("//div [contains(@class,'end field')] /descendant::input [@class='date-picker_input']");
    private final By endTime = By.xpath ("//div [contains(@class,'end field')] /descendant::input [@type='text']");
    private final By nextMonth = By.xpath ("//button [contains(@class,'navigation--next')]");
    private final By previousMonth = By.xpath ("//button [contains(@class,'navigation--previous')]");
    private final By allDay = By.xpath ("//label [contains(@for,'all-day-checkbox')]");
    private final By pickRandomDate = By.xpath("//div[@class='react-datepicker__week'][2]/div[@class='react-datepicker__day react-datepicker__day--thu']"); //Date isn't random, it's the date in the Thursday in the second row.


    //Creating New Cps and Contact

    private final By createCp = By.xpath ("//button [contains(@class,'button--citrus')] [text()='Create Corporate Participant']");
    private final By createContact = By.xpath ("//button [contains(@class,'button--citrus')] [text()='Create Contact']");

    private final By loadingSpinner = By.xpath ("//div [@class='spinner_mask']");


    private  String keyword = "";


    public LogActivityPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Adding/ Entering fields Fields
     */
    public LogActivityPage enterNoteDetails(String title, String note, String tag) {
        //all Child Activities

        //enters a Title, Note and a Tag to an Activity then switches to corresponding radio button

        waitForElementToAppear(titleField);
        findElement(titleField).sendKeys(title);

        findElement(typeNoteField).sendKeys(note);

        findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.ENTER);

        return this;
    }

    public LogActivityPage enterParentDetails(String title, String location, String tag, FilterType parent){

        //only Parent Activities, enters a Title, Location and Tag then switches to corresponding radio button

        waitForElementToDissapear(loadingSpinner);

        waitForElementToAppear(titleField).sendKeys(title);
        findElement(locationField).sendKeys(location);
        findElement(tagField).sendKeys(tag);
        findElement(tagField).sendKeys(Keys.ENTER);

        postActivity(parent);

        return this;
    }
    public LogActivityPage enterLocation (String location) {

        waitForElement(locationField);
        findVisibleElement(locationField).sendKeys(location);

        return this;
    }

    public LogActivityPage enterVenue (String venue) {

        waitForElement(locationField);
        findVisibleElement(venueField).sendKeys(venue);

        return this;
    }

    public LogActivityPage enterDeal (String dealName) {

        findVisibleElement(dealField).sendKeys(dealName);
        waitForElementToBeClickable(firstDeal).click();

        return this;

    }

    public LogActivityPage enterStartDate(int future_month) {

        //ToDO: Have to update Automation Test with new Calendar on Activity Page
        
        waitForElementToBeClickable(startDate).click();

        //Have to select next month several times
        for(int i = 0; i < future_month; i++) {
            selectNextMonth();
        }
        waitForElementToBeClickable(pickRandomDate).click(); //Date isn't random, it's the date in the Thursday in the second row.

        return this;
    }

    /**
     * Adding Attendees
     */

    //helper function to enter and select the first Dropdown result for Attendees (the right attendee type must already be selected)
    private LogActivityPage enterSelectFirstAttendee(String attendee) {

        keyword=attendee;
        findElement(keywordField).sendKeys(keyword);

        waitForElementToBeClickable(firstAttendee).click();


        return this;

    }
    //helper function to enter and select the first Dropdown result for CP
    public LogActivityPage enterSelectFirstParticipant(String corporate) {
        keyword=corporate;
        findElement(CpField).sendKeys(corporate);

        waitForElementToBeClickable(firstAttendee).click();

        return this;

    }
    //Untested method
    //code is a bit inefficient feel free to simplify
    //two arrays of same size each Attendee name has a corresponding type
    //
    public LogActivityPage bulkAddAttendees(String [] name, String [] type) {

        int x=0;
        for (String element : name) { //walks through names of Attendees and switches it to appropriate type and adds the Attendees

            switch (type[x]) {
                case "Institution" : linkNoteToInstitution(element);
                    break;
                case "Fund" : linkNoteToFund(element);
                    break;
                case "Contact" : linkNoteToContact(element);
                    break;
            }
            x++;

        }
        return this;
    }
    //TODO : Merge linkNote Functions into One using a switch case for attendeeType (if needed)

    //linkNote functions switch to appropriate attendeeType then enter and select the appropriate attendee Name
    public LogActivityPage linkNoteToInstitution(String institution) {

        waitForElementToBeClickable(institutionIcon).click();
        enterSelectFirstAttendee(institution);
        return this;
    }

    public LogActivityPage linkNoteToFund(String fund) {

        waitForElementToBeClickable(fundIcon).click();
        enterSelectFirstAttendee(fund);

        return this;
    }

    public LogActivityPage linkNoteToContact(String contact) {

        waitForElementToBeClickable(contactIcon).click();
        enterSelectFirstAttendee(contact);

        return this;
    }


    //switch case to select the Activity Type radio button based off the Activity
    public LogActivityPage postActivity(FilterType activity) {

        switch (activity) {
            case NOTE : findVisibleElement (noteTab).click();
                break;
            case PHONE : findVisibleElement (phoneTab).click();
                break;
            case EMAIL : findVisibleElement (emailTab).click();
                break;
            case MEETING : findVisibleElement (meetingTab).click();
                break;
            case EARNINGS : findVisibleElement (earningsTab).click();
                break;
            case ROADHSHOW : findVisibleElement (roadshowTab).click();
                break;
            case CONFERENCE : findVisibleElement (conferenceTab).click();

        }

        return this;

    }

    /**
     * Removing Fields
     */

    public LogActivityPage clearNoteDetails() {

        try {
            waitForElementToDissapear(loadingSpinner);
            waitForElementToAppear(removeFirstTag).click();
            findElement(typeNoteField).clear();
            findElement(locationField).clear();
        }

        catch (Exception e) { //so test won't fail if one of the elements aren't found

            }

        return this;
    }
    public LogActivityPage removeAttendee(String attendee) { //clicks the minus icon for an Attendee listed in Attendee section

        findElement(By.xpath("//div [text()="+attendee+"]/ancestor::div [@class='item-header']/descendant::div[@class='item-header_delete-icon']"));

        return this;
    }


    /**
     * Actions performed on Activity
     *
     */
    //Cancel button then confirms
    public ActivityPage cancelNote() {


        waitForElementToBeClickable(cancelActivityButton).click();
        waitForElementToBeClickable(proceedDiscard).click();

        return new ActivityPage(getDriver());
    }

    //Save & Close
    public ActivityPage saveActivity() {

        waitForElementToBeClickable(saveCloseButton).click();
        waitForElementToDissapear(loadingSpinner);
        return new ActivityPage(getDriver());
    }

    //Logging Activity from Corporate Participants Page
    public CorpPartDetailsPage saveCorpPartActivity() {

        waitForElementToBeClickable(saveCloseButton).click();
        waitForElementToDissapear(loadingSpinner);
        return new CorpPartDetailsPage(getDriver());
}

    //Save
    public LogActivityPage saveNotClose () {

        waitForElementToBeClickable(saveButton).click();
        return this;

    }

    public LogActivityPage selectAllDay() {

        waitForElementToBeClickable(allDay).click();
        return this;

    }

    /**
     * Calendar actions
     *
     */
    //TODO Add more Calendar actions such as setting a Start date, Start Time, End Date, End Time

    public LogActivityPage selectNextMonth() {

        waitForElementToBeClickable(nextMonth).click();
        return this;

    }

    public LogActivityPage selectPreviousMonth() {

        waitForElementToBeClickable(previousMonth).click();
        return this;

    }

    /**
     * Itinerary actions
     *
     */
    public LogActivityPage buildItinerary () {

        waitForElementToBeClickable(saveBuildItinerary).click();

        return this;
    }

    public LogActivityPage addAnotherItinerary() {

        waitForElementToBeClickable(saveAddAnother).click();

        return this;

    }







}
