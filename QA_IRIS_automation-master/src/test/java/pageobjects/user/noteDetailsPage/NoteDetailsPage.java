package pageobjects.user.noteDetailsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.activityPage.FilterType;

/**
 * Created by patrickp on 2016-08-12.
 */
public class NoteDetailsPage extends ActivityPage {

    //Parts of the Page

    private final By detailsHeader = By.xpath ("//header [contains(@class,'banner--default')]");
    private final By detailsBody = By.xpath ("//div [@class='activity-detail_overview_columns']");
    private final By navBar = By.xpath ("//header [@class='section-tab_nav']");
    private final By footer = By.xpath ("//div [@class='footer-inner']");
    private final By noAttendees = By.xpath ("//div [@class='ag-overlay-panel']/div");

    //Header
    private final By activityTitle = By.xpath ("//h1 [@class='banner_title']");
    private final By activityType = By.xpath ("//div [@class='banner_header']/i");
    private final By dateDetails = By.xpath("//div [@class='banner_details'] /span");
    private final By locationDetails = By.xpath ("//div [contains(@class,'overview_location')]");
    private final By venueDetails = By.xpath ("//div [contains(@class,'overview_venue')]");
    private final By cpDetails = By.xpath ("//div [contains(@class,'participant')]/a [contains(@class,'_overview_link')]"); //only works if one CP in list
    private final By dealLink = By.xpath ("//div [contains(@class,'deal')]/a [contains(@class,'overview_link')]");
    private final By commentDetails = By.xpath ("//div [contains(@class,'render-html')]");

    //tag functionality on ActivityPage

    private final By addTagButton = By.xpath ("//button [contains(@class,'tag-input_add-button')]"); //plus button
    private final By addTagField = By.xpath ("//input [@placeholder='Press ENTER or SPACE to add tags']"); //text box where user adds the tag
    private final By addTagUtility = By.xpath ("//div[contains(@class,'tags-item--more')]");

    // Utility, Edit and Export Options

    private final By editButton = By.xpath ("//i [contains(@class,'edit')]/ancestor::button[contains(@class,'square')]");
    private final By exportButton = By.xpath ("//span [text()='Export']/ancestor::button[@class='button']");
    private final By utilityMenu = By.xpath ("//button [contains(@class,'utility')]");
    private final By deleteActivity = By.xpath ("//div [contains(@class,'popover')][text()='Delete']");
    private final By createBriefingBook = By.xpath ("//div [contains(@class,'popover')][text()='Create Briefing Book']");
    private final By printItinerary = By.xpath ("//div [@class='popover_item-detail']/div [text()='Print Itinerary']");

    //Tabs

    private final By attendeesTab = By.xpath ("//span [contains(@class,'tab_label')] [text()='Attendees']");
    private final By itineraryTab = By.xpath ("//span [contains(@class,'section-tab_label')] [text()='Itinerary']");

    //Created/Updated info
    private final By createAuthorDate = By.xpath ("//div [contains(@class,'audit-trail')]/p[1]");
    private final By editAuthorDate = By.xpath ("//div [contains(@class,'audit-trail')]/p[2]");

    // Attendee section

    // First In List (to select a non first entry just change the row index)

    private final By firstAttendeeName = By.xpath ("//div [@row-index='0']/descendant::div [@class='cell_title']");
    private final By firstLocation = By.xpath ("//div [@row-index='0']/div[@col-id='location']");
    private final By detailsTag = By.xpath ("//div[contains(@class,'tags--dark-slate')]/div[1]");
    private final By firstEmailIcon = By.xpath ("//div [@row-index='0']/descendant::div[@class='cell cell--email']");
    private final By firstPhoneIcon = By.xpath ("//div [@row-index='0']/div[@col-id='phone']");

    //Attendee filters
    private final By allTab = By.xpath ("//div [text()='All'] /ancestor::button [contains(@class,'range-tab_button')]");
    private final By institutionTab = By.xpath ("//div [text()='Institution'] / ancestor::button[@class='range-tab_button']");
    private final By fundTab = By.xpath ("//div [text()='Fund'] / ancestor::button[@class='range-tab_button  ']");
    private final By contactTab = By.xpath ("//div [text()='Contact'] / ancestor::button[@class='range-tab_button  ']");

    //Attendee Numbers

    private final By allAmount = By.xpath("//div [contains(@class,'range-tab_label')] [text()='All']/div");
    private final By institutionAmount = By.xpath ("//div [contains(@class,'range-tab_label')] [text()='Institution']/div");
    private final By fundAmount = By.xpath("//div [contains(@class,'range-tab_label')] [text()='Fund']/div");
    private final By contactAmount = By.xpath("//div [contains(@class,'range-tab_label')] [text()='Contact']/div");


    // Itinerary Section

    // (First in List) to select a non first element just change the row-index

    private final By createActivity = By.xpath ("//button [contains(@class,'button--citrus')]");

    private final By itineraryDate = By.xpath("//div [@row-index='0'] /div [@col-id='start_date']");
    private final By itineraryTime = By.xpath ("//div [@row-index='0'] /div [@col-id='time']");
    private final By itineraryTitle = By.xpath("//div [@row-index='0'] /div [@col-id='title']");
    private final By itineraryContact = By.xpath ("//div [@row-index='0'] /div [@col-id='contact']");
    private final By itineraryInstitution = By.xpath ("//div [@row-index='0'] /div [@col-id='institution']");
    private final By itineraryParticipants = By.xpath("//div [@row-index='0'] /div [@col-id='participants']");
    private final By itineraryVenue = By.xpath("//div [@row-index='0'] /div [@col-id='location']");

    //Activity Dropdown on Itinerary Grid for Activity types

    private final By typeDropdown = By.xpath ("//div [contains (@class,'toggle_value-label')]");
    private final By allType = By.xpath ("//div [contains(@class,'select-list__item')] [1]");
    private final By noteType = By.xpath("//div [contains(@class,'select-list__item')] [2]");
    private final By phoneType = By.xpath ("//div [contains(@class,'select-list__item')] [3]");
    private final By emailType = By.xpath ("//div [contains(@class,'select-list__item')] [4]");
    private final By meetingType = By.xpath ("//div [contains(@class,'select-list__item')] [5]");
    private final By earningsType = By.xpath ("//div [contains(@class,'select-list__item')] [6]");

    //Pagination for Attendees grid

    private final By tenPage = By.xpath ("//li [contains(@class,'pagination_item')]/a [text()='10']");
    private final By twentyfivePage = By.xpath ("//li [contains(@class,'pagination_item')]/a [text()='25']");
    private final By fiftyPage = By.xpath ("//li [contains(@class,'pagination_item')]/a [text()='50']");
    private final By hundredPage = By.xpath ("//li [contains(@class,'pagination_item')]/a [text()='100']");
    private final By nextPage = By.xpath ("//li [contains(@class,'pagination_action--next')]/a[@class='pagination_link']");
    private final By previousPage = By.xpath ("//li [contains(@class,'pagination_action--prev')]/a[@class='pagination_link']");

    //Not sure what to do with these (Outdated elements)

    private final By itineraryAttendees = By.xpath("//div[contains(@class,'itinerary-list-item')]/div[contains(@class,'links')]");
    private final By linkedToDetails = By.xpath("//div[contains(@class, 'x-container x-unsized activity-attendees-list x-dataview x-paint-monitored')]//div[contains(@class, 'column flex name')]");
    private final By activityDetails = By.cssSelector(".preview-note-view p");
    private final By attendeeDetails = By.xpath("//div[contains (@class, 'x-unsized x-dataview-container')]/div[contains(@class, 'x-dataview-item')]/div[contains(@class, 'details')]");
    private final By noteDetails = By.xpath("//div[contains(@class, 'x-container x-unsized x-size-monitored x-paint-monitored notes')]/div[contains(@class, 'x-inner')]/div[contains(@class, 'x-innerhtml')]/div[contains(@class, 'q4-fade-in')]");

    private final By suggestEditMenuDropdownButton = By.cssSelector("#ext-element-661");
    private final By suggestEditMenuButton = By.cssSelector(".utility-menu-items > .x-inner");
    private final By suggestEditTextBox = By.name("message");
    private final By suggestPostEditButton = By.cssSelector(".q4-form .form-button.yellow");
    private final By suggestCancelEditButton = By.cssSelector(".q4-form .form-button.no-background");



    public NoteDetailsPage(WebDriver driver) {
        super(driver);
    }

    public boolean detailsPageExists(){

        try{
            waitForElement(detailsHeader);
            findElement(detailsHeader);
            findElement(detailsBody);
            findElement(navBar);
            return true;
        }
        catch(Exception e){
            return false;
        }

    }

    public boolean isCorrectActivityType(FilterType type) {

        WebElement activity = findElement(activityType);

        if (activity.getAttribute("class").contains(type.iconClass())) {
            return true;
        }
        return false;

    }

    public editNoteDetailsPage goToEditPage(){

        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        findElement(editButton).click();
        return new editNoteDetailsPage(getDriver());
    }

    public String returnAttendeesList(){ //outdated

        waitForElement(linkedToDetails);

        return findVisibleElement(linkedToDetails).getText();

    }

    private String getAttendeeTitle(int index) {

        return findVisibleElement (By.xpath("//div [@row-index='"+index+"']/descendant::div [@class='cell_title']")).getText();

    }

    public String [] getAttendeeTitleList(int size) {

        String [] list = new String [size];

        for (int x=0;x<size;x++) {

            list[x] = getAttendeeTitle(x);

        }

        return list;
    }

    public NoteDetailsPage clickInstitutionTab(){

        waitForElementToBeClickable(institutionTab).click();

        waitForLoadingScreen();
        return this;
    }
    public NoteDetailsPage clickFundTab(){

        waitForElementToBeClickable(fundTab).click();

        waitForLoadingScreen();
        return this;
    }
    public NoteDetailsPage clickContactTab(){

        waitForElementToBeClickable(contactTab).click();

        waitForLoadingScreen();
        return this;
    }


    // public String getActivityHeader() {
    //      return findElement(activityHeader).getText();
    // }

    /**
     * Getters basically returns a specific field's contents
     */
    public String getNoteBody() {
        waitForLoadingScreen();
        return waitForElementToAppear(noteDetails).getText();
    }

    public String getActivityTitle(){

        waitForElementToAppear(activityTitle);
        return findElement(activityTitle).getText();
    }


    public String getDate(){
        waitForLoadingScreen();
        waitForElement(dateDetails);
        return findElement(dateDetails).getText();
    }

    public String getVenueDetails(){
        waitForLoadingScreen();
        waitForElement(venueDetails);
        return findElement(venueDetails).getText();
    }

    public String getLocation(){
        waitForLoadingScreen();
        waitForElement(locationDetails);
        // waitForTextToChange(locationDetails, "...");
        return findElement(locationDetails).getText();
    }

    public String getActivityType() {

        waitForElement(activityType);
        return findElement(activityType).getText();

    }

    public String getDealName() {

        waitForElement(dealLink);
        return findElement(dealLink).getText();
    }


    public String getCommentText() {

        waitForElement(commentDetails);
        return findElement(commentDetails).getText();
    }

    // index starts from 1 and starts from the top so the 3rd Cp on the list would have an Index of 3
    public String getParticipant(int index){

        waitForElement(By.xpath("//h2 [text()='Corporate Participant(s)']/parent::div/div["+index+"]/a"));
        return findElement(By.xpath("//h2 [text()='Corporate Participant(s)']/parent::div/div["+index+"]/a")).getText();
    }

    public String getAttendees(){

        waitForElement(attendeeDetails);
        return findElement(attendeeDetails).getText();
    }
    //These functions only work for first in a list

    public String getItineraryTitle(){

        waitForElement(itineraryTitle);
        return findElement(itineraryTitle).getText();
    }
    public String getItineraryDate(){

        waitForElement(itineraryDate);
        return findElement(itineraryDate).getText();
    }
    public String getItineraryTime(){

        waitForElement(itineraryTime);
        return findElement(itineraryTime).getText();
    }

    public String getItineraryVenue(){

        waitForElement(itineraryVenue);
        return findElement(itineraryVenue).getText();
    }

    public String getItineraryParticipants(){

        waitForElement(itineraryParticipants);
        return findElement(itineraryParticipants).getText();
    }

    public String getItineraryAttendees(){

        waitForElement(itineraryAttendees);
        return findElement(itineraryAttendees).getText();
    }

    public String getDetailsDate(){

        waitForElementToAppear(dateDetails);
        return findElement(dateDetails).getText();
    }

    public String getDetailsTag(){
        //Get tag from the details page

        waitForElement(detailsTag);
        return findElement(detailsTag).getText();
    }
    public String getDetailsLocation(){
        //TODO Update this Function
        //Gets the location from details page - detailsLocation will return all content inside details page, substringBetween selects the location
        waitForLoadingScreen();
        waitForElement(locationDetails);

        return findElement(locationDetails).getText();
    }

    //Tag actions

    public NoteDetailsPage addNewTag(String newTag){
        //Add new tag from details page
        waitForElementToBeClickable(addTagButton).click();
        findElement(addTagField).sendKeys(newTag);
        findElement(addTagField).sendKeys(Keys.ENTER);
        findElement(detailsHeader).click(); //random click to close add tags modal
        waitForLoadingScreen();
        //findElement(addTagField).sendKeys(Keys.ESCAPE);

        return this;
    }

    public NoteDetailsPage removeTag(int tagNumber) { //tagNumber numbers starts at 1 from left to right


        waitForElementToBeClickable(By.xpath("//div [contains (@class,'tags--dark-slate')]/div[" + tagNumber + "]/i")).click();

        return this;
    }

    /**
     * Suggesting an edit (Outdated maybe?)
     * Not really sure how this should work
     */
    public NoteDetailsPage getSuggestEditTextBox(){

        wait.until(ExpectedConditions.elementToBeClickable(suggestEditMenuDropdownButton));
        findElement(suggestEditMenuDropdownButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(suggestEditMenuButton));
        findElement(suggestEditMenuButton).click();

        return this;
    }

    public NoteDetailsPage postASuggestedEdit (String comment){

        wait.until(ExpectedConditions.elementToBeClickable(suggestEditTextBox));
        findElement(suggestEditTextBox).sendKeys(comment);
        wait.until(ExpectedConditions.elementToBeClickable(suggestPostEditButton));
        findElement(suggestPostEditButton).click();

        return this;
    }

    public NoteDetailsPage cancelASuggestedEdit (String comment){

        wait.until(ExpectedConditions.elementToBeClickable(suggestEditTextBox));
        findElement(suggestEditTextBox).clear();
        findElement(suggestEditTextBox).sendKeys(comment);
        wait.until(ExpectedConditions.elementToBeClickable(suggestCancelEditButton));
        findElement(suggestCancelEditButton).click();

        return this;
    }

    public String getActivityDetails(){
        try {
            waitForLoadingScreen();
            return findElement(activityDetails).getText();
        }
        catch(ElementNotFoundException e)
        {
            return "";
        }
    }


}
