package pageobjects.user.noteDetailsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.user.Calendar;
import pageobjects.user.activityPage.ActivityPage;
/**
 * Created by dannyl on 2017-05-05.
 */

public class editNoteDetailsPage extends ActivityPage {


    //Buttons
    private final By saveButton = By.xpath("//button [contains(@class,'button--citrus')] [text()='Save']");
    private final By saveCloseButton = By.xpath ("//button [contains(@class,'button--citrus')] [text()='Save & Close']");
    private final By saveBuildItinerary = By.xpath ("//button [text()='Save & Build Itinerary']");
    private final By cancelButton = By.xpath ("//footer [@class='modal_footer'] /descendant::button [text()='Cancel']");
    private final By cancelDiscard = By.xpath ("//footer [@class='message_footer']/descendant::button [text()='Cancel'] ");
    private final By proceedDiscard = By.xpath ("//footer [@class='message_footer']/descendant::button [text()='Proceed']");
    private final By xButton = By.xpath ("//div [@class='modal_exit'] /i [@class='q4i-close-4pt']");
    private final By deleteActivity = By.xpath ("//div [contains(@class,'modal_actions--left')]/button [contains(@class,'button--square')]");
    private final By confirmDelete = By.xpath ("//div [@class='message_actions']/button[text()='Yes']");
    private final By cancelDelete = By.xpath("//div [@class='message_actions']//button [text()='Cancel']");

    // Type Menu Buttons (waitForElementToBeClickable does not work on these elements)
    private final By changeTypeToNoteButton = By.xpath ("//label [@for='id--comment']");
    private final By changeTypeToPhoneButton = By.xpath ("//label [@for='id--phone']");
    private final By changeTypeToEmailButton = By.xpath ("//label [@for='id--email']");
    private final By changeTypeToMeetingButton = By.xpath ("//label [@for='id--meeting']");
    private final By changeTypeToEarningsButton = By.xpath ("//label [@for='id--earnings']");
    private final By changeTypeToRoadshowButton = By.xpath ("//label [@for='id--roadshow']");
    private final By changeTypeToConferenceButton = By.xpath ("//label [@for='id--conference']");

    // Text Field Option Buttons
    private final By toggleBoldTextButton = By.cssSelector("#mceu_14 > button");
    private final By toggleItalicsButton = By.cssSelector("#mceu_15 > button");
    private final By toggleUnderlineButton = By.cssSelector("#mceu_16 > button");
    private final By toggleLeftIndentButton = By.cssSelector("#mceu_17 > button");
    private final By toggleCenterTextButton = By.cssSelector("#mceu_18 > button");
    private final By toggleRightIndentButton = By.cssSelector("#mceu_19 > button");

    // Font Sizes
    private final By fontDropdownMenu = By.cssSelector("#mceu_13-open");
    private final By fontSize11 = By.cssSelector("#mceu_28-text");
    private final By fontSize13 = By.cssSelector("#mceu_29");
    private final By fontSize15 = By.cssSelector("#mceu_30");
    private final By fontSize18 = By.cssSelector("#mceu_31");
    private final By fontSize24 = By.cssSelector("#mceu_32");

    // Menus and Fields

    private final By titleField = By.xpath("//input [@class='text-input_field']");
    private final By startDateField = By.xpath("//label [text()='Start']/parent::div/descendant::input [@class='date-picker_input']");
    private final By endDateField = By.xpath ("//label [text()='End']/parent::div/descendant::input [@class='date-picker_input']");
    private final By startTimeMenu = By.xpath ("//label [text()='Start']/parent::div/descendant::input [@type='text']");
    private final By endTimeMenu = By.xpath("//label [text()='End']/parent::div/descendant::input [@type='text']");
    private final By locationMenu = By.xpath("//input [@placeholder='City, State, Country']");
    private final By venueMenu = By.xpath("//input [@placeholder='Address']");
    private final By dealMenu = By.xpath ("//input [@placeholder='Title']");
    private final By noteField = By.xpath ("//div [@class='DraftEditor-editorContainer']/div [@role='textbox']");
    private final By tagMenu = By.xpath ("//input [contains(@placeholder,'travel')]");
    private final By CpField = By.xpath("//input [@placeholder='Full Name']");
    private final By profileSearchField = By.xpath("//input [@placeholder='Keyword']");
    private final By corporateTextField = By.xpath("//input [@placeholder='Full Name']");

    //First in Dropdown List

    //applies to CP and Attendees
    private final By firstAttendee = By.xpath ("//div [@class='select-list_scrollbar']/div/div[1]");
    //applies to Location and Deal
    private final By firstLocationOrDeal = By.xpath ("//div [contains(@class,'react-scrollbar')]/div/ul/li[1]");



    private final By nextMonthButton = By.xpath("//button [contains(@class,'navigation--next')]");
    private final By previousMonthButton = By.xpath ("//button [contains(@class,'navigation--previous')]");
    private final By profileInstitutionButton = By.xpath("//div [contains(@class,'range-tab_label')] [text()='Institutions']");
    private final By profileFundButton = By.xpath("//div [contains(@class,'range-tab_label')] [text()='Funds']");
    private final By profileContactButton = By.xpath("//div [contains(@class,'range-tab_label')] [text()='Contacts']");

    //Add Contacts and Cps
    private final By corporateAddButton = By.xpath("//button [contains(@class,'button--citrus')] [text()='Create Corporate Participant']");
    private final By contactAddButton = By.xpath ("//button [contains (@class,'button--citrus')] [text()='Create Contact']");

    //Remove First Element

    private final By removeFirstLocation = By.xpath ("//div [contains(@class,'multi-location')] /descendant::div [@class='list-editable']/div[1]/descendant::div [@class='item-header_delete-icon']");
    private final By removeFirstCP = By.xpath ("//div [contains(@class,'activity-participants')] /descendant::div [@class='list-editable']/div[1]/descendant::div [@class='item-header_delete-icon']");
    private final By removeFirstTag = By.xpath ("//div [contains(@class,'activity-tags')] /descendant::div [@class='list-editable']/div[1]/descendant::div [@class='item-header_delete-icon']");
    private final By removeFirstAttendee = By.xpath ("//div [contains(@class,'activity-attendees')] /descendant::div [@class='list-editable']/div[1]/descendant::div [@class='item-header_delete-icon']");


    // Itinerary Menu
    private final By itineraryDate = By.cssSelector("#ext-element-563");
    private final By itineraryDatePrev = By.cssSelector(".pickmeup .pmu-instance nav .pmu-prev");
    private final By itineraryDateNext = By.cssSelector("");
    private final By itineraryCurrentMonth = By.cssSelector(".pickmeup .pmu-instance:first-child:last-child .pmu-month");
    private final By itineraryCurrentDay = By.xpath("//div[contains(@class, 'pickmeup light-theme pmu-view-days')]/div[contains(@class, 'pmu-instance')]" +
            "/div[contains(@class, 'pmu-days')]/div[contains (@class, 'pmu-selected pmu-today pmu-button')])");
    private final By chosenDay = By.xpath("//div[contains(@class, 'pickmeup light-theme pmu-view-days')]/div[contains(@class, 'pmu-instance')]/div[contains(@class, 'pmu-days')]/*[contains (text(), '7')]");
    private final By itineraryShowButton = By.cssSelector(".note-itinerary .show-itinerary-button");
    private final By itineraryStartTimeMenu = By.cssSelector("#ext-element-569");
    private final By itineraryEndTimeMenu = By.cssSelector("#ext-element-574");
    private final By itineraryTitleField = By.cssSelector("#ext-element-579");
    private final By itinerarySelectProfileMenu = By.cssSelector("#ext-togglefield-5");
    private final By itineraryProfileTextField = By.cssSelector("#ext-element-620");
    private final By itineraryProfileFundButton = By.cssSelector("#ext-radiofield-10");
    private final By itineraryProfileInstitutionButton = By.cssSelector("#ext-radiofield-9");
    private final By itineraryProfileContactButton = By.cssSelector("#ext-radiofield-11");
    private final By itineraryCorporateParticipantField = By.cssSelector("#ext-element-629");
    private final By itineraryAddCorporateParticipant = By.cssSelector(".note-itinerary .itinerary-form .note-participants-field .add-participant");
    private final By itineraryVenueField = By.cssSelector("#ext-element-635");
    private final By itineraryAdd = By.cssSelector(".note-itinerary .add-itinerary-button");

    public editNoteDetailsPage(WebDriver driver) {
        super(driver);
    }

    // Change the type of activity.
    // Requires: activity must be one of "Note", "Phone", "Email", "Meeting", "RoadShow", "Earnings" or "Conference"
    public editNoteDetailsPage changeTypeOfActivity(String activity) {

        waitForElementToAppear(changeTypeToNoteButton);

        if (activity.equalsIgnoreCase("note")) {
            findElement(changeTypeToNoteButton).click();
        } else if (activity.equalsIgnoreCase("phone")) {
            findElement(changeTypeToPhoneButton).click();
        } else if (activity.equalsIgnoreCase("email")) {
            findElement(changeTypeToEmailButton).click();
        } else if (activity.equalsIgnoreCase("meeting")) {
            findElement(changeTypeToMeetingButton).click();
        }
        else if (activity.equalsIgnoreCase("earnings")) {
            findElement(changeTypeToEarningsButton).click();
        }
        else if (activity.equalsIgnoreCase("conference")) {
            findElement(changeTypeToConferenceButton).click();
        }
        else  {
            findElement(changeTypeToRoadshowButton).click();
        }
        return this;
    }

    // Input newTitle into the title field
    public editNoteDetailsPage editTitle(String newTitle) {

        waitForElementToAppear(titleField);
        findElement(titleField).clear();
        findElement(titleField).sendKeys(newTitle);
        return this;
    }


    //  Input comment into large text box
    public editNoteDetailsPage editNoteDetails(String comment) {

        waitForElementToAppear(noteField);
        findVisibleElement(noteField).sendKeys(comment);

        return this;
    }

    // Change how the text looks depending on style
    // Requires: style must be one of "Bold", "Italic", "Underline", "Left", "Right", "Center"
    public editNoteDetailsPage changeLookOfText(String style) {
        switch (style.toLowerCase()) {
            case "bold":
                findElement(toggleBoldTextButton).click();
                break;
            case "italic":
                findElement(toggleItalicsButton).click();
                break;
            case "underline":
                findElement(toggleUnderlineButton).click();
                break;
            case "left":
                findElement(toggleLeftIndentButton).click();
                break;
            case "right":
                findElement(toggleRightIndentButton).click();
                break;
            case "center":
                findElement(toggleCenterTextButton).click();
                break;
            default:
                break;
        }
        return this;
    }

    // Change the font size based on given size
    // Requires: size must equal either: 11, 13, 15, 18, 24
    public editNoteDetailsPage changeFontSize(int size) {
        findElement(fontDropdownMenu).click();
        waitForElementToAppear(fontSize11);
        switch (size) {
            case 11:
                findElement(fontSize11).click();
                break;
            case 13:
                findElement(fontSize13).click();
                break;
            case 15:
                findElement(fontSize15).click();
                break;
            case 18:
                findElement(fontSize18).click();
                break;
            case 24:
                findElement(fontSize24).click();
                break;
            default:
                break;
        }
        return this;
    }

    // Add the venue location venueLocation into the venue text box
    public editNoteDetailsPage addVenue(String venueLocation) {
        waitForElementToAppear(venueMenu);
        findElement(venueMenu).clear();
        findElement(venueMenu).sendKeys(venueLocation);
        return this;
    }

    // Edit the location
    public editNoteDetailsPage changeLocation(String someLocation) {

        waitForElementToAppear(locationMenu);
        findElement(locationMenu).clear();
        findElement(locationMenu).sendKeys(someLocation);
        return this;
    }
    //removes the initial Deal (since only one deal can exist at a time), then adds the new Deal, selecting the first Deal on the list
    public editNoteDetailsPage  replaceDeal(String dealName) {

        enterSelectFirstFromDropdown("deal",dealName);



        return this;
    }
    //Creating new Corporate Participants (Unable to use this currently as no Page Object for Create CP Modal has been created as of Late April 2019)
    //TODO Add A Create Corporate Participant Modal For this Function to be usable
    public editNoteDetailsPage createParticipants(String participant) {

        waitForElementToAppear(corporateTextField);
        findElement(corporateTextField).sendKeys(participant);
        findElement(corporateAddButton).click();

        return this;
    }
    //Adding already existing Corporate Participants as Attendees
    //Guess this could be deleted and instead call enterSelectFirstFromDropdown("participant", participant) directly from Test Class
    public editNoteDetailsPage addParticipants(String participantName) {

        enterSelectFirstFromDropdown("participant",participantName);
        return this;

    }

    //Applies to fields that provide AutoComplete options
    //Based on the fieldName, will search and select the first AutoSuggest option that appears
    //Warning : Location field can be entered and saved without using a Dropdown
    //Warning : For Attendees, the corresponding Attendee type should be selected prior to calling this function

    private editNoteDetailsPage enterSelectFirstFromDropdown(String fieldName, String text) {

        switch(fieldName.toLowerCase()) {

            case "location" :
                waitForElement(locationMenu).sendKeys(text);
                waitForElementToBeClickable(firstLocationOrDeal).click();
                break;

            case "deal" :
                waitForElement(dealMenu).clear(); //removes original Deal (if exists) then replaces with new Deal
                waitForElement(dealMenu).sendKeys(text);
                waitForElementToBeClickable(firstLocationOrDeal).click();
                break;

            case "participant" : waitForElement(CpField).sendKeys(text);
                waitForElementToBeClickable(CpField).click();
                break;

            case "attendees" : waitForElement(profileSearchField).sendKeys(text);
                waitForElementToBeClickable(firstAttendee).click();
                break;

        }
        return this;


    }

    // editAttendees selects a type from the profile menu and adds the keyword to the attendees list
    // Requires: type must be one of: "Institution", "Fund", "Contact"
    //           keyword must exist in the database
    public editNoteDetailsPage editAttendees(String keyword, String type) {

        //waitForElementToAppear(profileMenu);
        //findElement(profileMenu).click();
        waitForElementToAppear(profileSearchField);
        switch (type.toLowerCase()) {
            case "institution":

                findElement(profileInstitutionButton).click();
                break;

            case "fund":

                findElement(profileFundButton).click();
                break;

            case "contact":

                findElement(profileContactButton).click();

                break;

            default:
                break;
        }
        enterSelectFirstFromDropdown("attendees",keyword);
        return this;
    }

    // Changes the start time to the given time
    public editNoteDetailsPage selectStartTime(int time) {

        return this;
    }
    // Saves Changes and closes Modal to return to Activity Page (Save & Close button)
    public ActivityPage saveAndClose() {

        waitForElementToAppear(saveCloseButton);
        findElement(saveCloseButton).click();

        return this;
    }
    // Saves the edits made (Save button)
    public editNoteDetailsPage saveChanges() {

        waitForElementToAppear(saveButton);
        findElement(saveButton).click();

        return this;
    }
    // Cancels changes applied and returns user to Note Details page
    public NoteDetailsPage cancelChanges() {

        waitForElementToAppear(cancelButton);
        findElement(cancelButton).click();
        waitForElement(proceedDiscard).click();

        return new NoteDetailsPage(getDriver());
    }

    public editNoteDetailsPage changeDate(){

        waitForLoadingScreen();
        scrollToElement(itineraryShowButton);
        Calendar someCalendar = new Calendar(driver).selectStartDate(startDateField, itineraryDatePrev,itineraryCurrentMonth,chosenDay);

        return this;
    }

    public editNoteDetailsPage changeStartTime(String time) {
        waitForElementToAppear(startTimeMenu);
        findElement(startTimeMenu).click();
        waitForElementToAppear(By.xpath("//div [@class='select-list__item'][contains(text(), '" + time + "')]"));
        findElement(By.xpath("//div [@class='select-list__item'][contains(text(), '" + time + "')]")).click();

        return this;
    }

    public editNoteDetailsPage changeEndTime(String time) {
        waitForElementToAppear(endTimeMenu);
        findElement(endTimeMenu).click();
        waitForElementToAppear(By.xpath("//div [@class='select-list__item'][contains(text(), '" + time + "')]"));
        findElement(By.xpath("//div [@class='select-list__item'][contains(text(), '" + time + "')]")).click();

        return this;
    }

    public editNoteDetailsPage showItinerary() {
        waitForElementToAppear(itineraryShowButton);
        findElement(itineraryShowButton).click();

        return this;
    }

    public editNoteDetailsPage editItineraryTitle(String title) {
        waitForElementToAppear(itineraryTitleField);
        findElement(itineraryTitleField).sendKeys(title);

        return this;
    }

    public editNoteDetailsPage editItineraryVenue(String venue) {
        waitForElementToAppear(itineraryVenueField);
        findElement(itineraryVenueField).sendKeys(venue);

        return this;
    }

    public editNoteDetailsPage editItineraryParticipant(String participant) {
        waitForElementToAppear(itineraryCorporateParticipantField);
        findElement(itineraryCorporateParticipantField).sendKeys(participant);
        findElement(itineraryAddCorporateParticipant).click();

        return this;
    }

    public editNoteDetailsPage changeItineraryStartTime(String time) {
        waitForElementToAppear(itineraryStartTimeMenu);
        findElement(itineraryStartTimeMenu).click();
        waitForElementToAppear(By.xpath("//div[contains(@class, 'x-unsized x-dataview-container')]/div[contains(@class, 'x-dataview-item')]/div[contains(@class, 'item')][contains(text(), '" + time + "')]"));
        findElement(By.xpath("//div[contains(@class, 'x-unsized x-dataview-container')]/div[contains(@class, 'x-dataview-item')]/div[contains(@class, 'item')][contains(text(), '" + time + "')]")).click();

        return this;
    }

    public editNoteDetailsPage changeItineraryEndTime(String time){
        waitForElementToAppear(itineraryEndTimeMenu);
        findElement(itineraryEndTimeMenu).click();
        waitForElementToAppear(By.xpath("//div[contains(@class, 'x-unsized x-dataview-container')]/div[contains(@class, 'x-dataview-item')]/div[contains(@class, 'item')][contains(text(), '" + time + "')]"));
        findElement(By.xpath("//div[contains(@class, 'x-unsized x-dataview-container')]/div[contains(@class, 'x-dataview-item')]/div[contains(@class, 'item')][contains(text(), '" + time + "')]")).click();

        return this;
    }

    public editNoteDetailsPage editProfiles (String keyword, String type){

        waitForElementToAppear(itinerarySelectProfileMenu);
        findElement(itinerarySelectProfileMenu).click();
        switch (type.toLowerCase()) {
            case "institution":
                findElement(itineraryProfileInstitutionButton).click();
                findElement(itineraryProfileTextField).sendKeys(keyword);
                waitForElementToAppear(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]"));
                findElement(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]")).click();
                break;
            case "fund":
                findElement(itineraryProfileFundButton).click();
                findElement(itineraryProfileTextField).sendKeys(keyword);
                waitForElementToAppear(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]"));
                findElement(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]")).click();
                break;
            case "contact":
                findElement(itineraryProfileContactButton).click();
                findElement(itineraryProfileTextField).sendKeys(keyword);
                waitForElementToAppear(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]"));
                findElement(By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(text(), '" + keyword + "')]")).click();
                break;
            default:
                break;
        }
        return this;
    }
    public editNoteDetailsPage changeIntineraryDate(){
        Calendar someCalendar = new Calendar(driver).selectStartDate(itineraryDate, itineraryDatePrev, itineraryCurrentMonth, chosenDay);
        return this;
    }

    public editNoteDetailsPage addItinerary(){
        waitForElementToAppear(itineraryAdd);
        findElement(itineraryAdd).click();
        return this;
    }
    //clears all text fields (Title, Location, Venue, Deal, Notes)

    public editNoteDetailsPage clearAllContents() {

        waitForElement(titleField);
        findVisibleElement(titleField).clear();
        waitForElement(locationMenu).clear();
        waitForElement(venueMenu).clear();
        waitForElement(dealMenu).clear();
        waitForElement(noteField).clear();

        return this;
    }

    public ActivityPage deleteActivity() {


        waitForElementToBeClickable(deleteActivity).click();
        waitForElementToBeClickable(confirmDelete).click();

        return new ActivityPage(getDriver());

    }
}






