package pageobjects.user.activityPage;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.Calendar;
import pageobjects.user.contactPage.ContactDetailsPage;
import pageobjects.user.contactPage.ContactPage;
import pageobjects.user.contactPage.CorpPartDetailsPage;
import pageobjects.user.noteDetailsPage.NoteDetailsPage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by philipsushkov on 2016-08-07.
 *
 * Modified by shardulb on 2019-05-09
 */
public class ActivityPage extends AbstractPageObject {


    //Properties of First Item in List (to apply it to non-first just change row-index)
    private final By firstCheckbox = By.xpath ("//div [@row-index='0']/div [@col-id='ag-Grid-AutoColumn']/span/span");
    private final By firstIcon = By.xpath ("//div [@row-index='0']/descendant::div[@class='cell-category']");
    private final By firstActivityTitle = By.xpath("//div [@row-index='0'] /descendant::div [@class='cell-title']");
    private final By firstNoteInListContact = By.xpath ("//div [@row-index='0'] /descendant::div [@col-id='contact']");
    private final By firstNoteInListInstitution = By.xpath ("//div [@row-index='0']/descendant::div[@col-id='institution']");
    private final By firstNoteInListDate = By.xpath("//div [@row-index='0']/descendant::div[@col-id='start']");
    private final By firstNoteInListLocation = By.xpath("//div [@row-index='0']/descendant::div[@col-id='address']");
    private final By firstNoteInListNewTag = By.xpath("//div [@row-index='0']/descendant::div[@class='tags']/div[1]");
    private final By firstCorpPartInDropdown = By.xpath ("//div [@class='select-list_scrollbar']/ div/ div[1]");

    //Buttons
    private final By deleteIcon = By.xpath ("//li [@class='bulk-action-bar_action-item']");
    private final By cancelDelete = By.xpath ("//button [contains(@class,'button--shaded')] [text()='cancel']");
    private final By confirmDelete = By.xpath ("//button [contains(@class,'button--spice')][text()='confirm']");

    private final By logActivityIcon = By.xpath ("//button [contains(@class,'app-header_button--activity')]/i"); //from top menu
    private final By newActivityIcon = By.xpath("//button [contains(@class,'button--square')]");
    private final By exportButton = By.xpath ("//button [contains(@class,'button--steel')] /i [contains(@class,'q4i-download-4pt')]");

    private final By resetButton = By.xpath ("//button [contains(@class,'button--ghost')]");

    //Checkboxes

    private final By rowCheckBox = By.xpath ("//div [@class='ag-pinned-left-cols-container']/descendant::span [@class='ag-selection-checkbox']");
    private final By bulkCheckBox = By.xpath ("//span[@class='ag-header-select-all']/span [contains(@class,'ag-checkbox')][2]");//selects all checkboxes
    private final By activityTitles = By.xpath ("//div [@class='cell-title']");
    ////span[@class="ag-icon ag-icon-checkbox-unchecked"]

    //Click Stealers (element is not clickable at (x,y) other element would get the click error

    private final By fieldLabel = By.xpath("[@class='field_label']"); //steals click from newActivityIcon (sometimes)
    private final By spinner = By.xpath ("//div [@class='spinner_mask']"); //Loading Screen
    private final By filterPopup = By.xpath ("//button [text()='GOT IT']"); //not really needed anymore unless cache is cleared basically a Got it Notification
    private final By toaster = By.xpath ("//div [@class='toast']"); //interferes with LogActivityIcon


    //Filter options

    private final By filterMenu = By.xpath ("//i [contains(@class,'funnel-filter-4pt')]/ancestor::button[contains(@class,'button--steel')]");
    private final By activitySearchField = By.xpath ("//input [contains(@class,'search_input')]");
    private final By clearActivitySearchField = By.xpath ("//div [@class='section-search_inner']/i");
    private final By CpDropdown = By.xpath ("//div [contains(@class,'toggle_value-label')] [text()='Corporate Participants']");
    private final By CorpPartDropdown = By.xpath("//div [@class='field field--full']/descendant::div [@class='dropdown-toggle_toggler']");
    private final By CorpPartDropDownMask = By.xpath("//div [@class='dropdown-select_mask']");
    private final By CorpPartiframe = By.xpath("//iframe[contains(@id, 'xh-bar')]");

    private final By typeField = By.xpath ("//div [@class='field field--full field--grouped']//div [@class='dropdown-toggle_value-label']"); //used to get the amount of Activities
    private final By typeFilterDropdown = By.xpath ("//div [@class='field field--full field--grouped']/descendant::div [@class='dropdown-toggle_toggler']");
    private final By allFilterOption = By.xpath ("//div [@class='select-list__item'] [text()='All']");
    private final By noteFilterOption = By.xpath ("//div [@class='select-list__item'] [text()='Note']");
    private final By phoneFilterOption = By.xpath ("//div [@class='select-list__item'] [text()='Phone']");
    private final By emailFilterOption = By.xpath ("//div [@class='select-list__item'] [text()='Email']");
    private final By meetingFilterOption = By.xpath ("//div [@class='select-list__item'] [text()='Meeting']");
    private final By earningsFilterOption = By.xpath ("//div [@class='select-list__item'] [text()='Earnings']");
    private final By roadshowFilterOption = By.xpath ("//div [@class='select-list__item'] [text()='Roadshow']");
    private final By conferenceFilterOption = By.xpath ("//div [@class='select-list__item'] [text()='Conference']");

    private final By yourActivityToggle = By.xpath ("//label [@class='checkbox_label checkbox_label--right']");
    //for Calendar filters (not used yet)
    private final By dateRangeField = By.xpath ("//input [@placeholder='Select date range']");
    private final By openCalendarFilter = By.xpath ("//i[@class='anticon anticon-calendar ant-calendar-picker-icon']");
    private final By cancelFilter = By.xpath ("//button [contains(@class,'e-control e-keyboard')] [text()='Cancel']");
    private final By applyFilter = By.xpath ("//button [contains(@class,'e-control e-keyboard')] [text()='Apply']");

    //Outdated
    private final By startTimeSelector = By.xpath("//label[contains(@class,'field_label--white')] [text()='Start']");
    private final By endTimeSelector = By.xpath("//label[contains(@class,'field_label--white')] [text()='End']");
    private final By previousMonthButton = By.xpath("//button [contains(@class,'datepicker__navigation--previous')]");
    private final By nextMonthButton = By.xpath("//button [contains(@class,'datepicker__navigation--next')]");
    private final By selectedMonth = By.xpath("//div[contains(@class,'current-month')]");
    private final By selectedDay = By.xpath("//div [contains(@class,'keyboard-selected')]");

    //Pagination options
    private final By getListOfPages = By.xpath("//a [@class='pagination_link']");
    private final By tenListed = By.xpath("//a [@class='pagination_link'] [text()='10']");
    private final By twentyFiveListed = By.xpath("//a [@class='pagination_link'] [text()='25']");
    private final By fiftyListed = By.xpath("//a [@class='pagination_link'] [text()='50']");
    private final By hundredListed = By.xpath("//span [@class='pagination_link'] [text()='100']");
    private final By nextPage = By.xpath ("//li [contains(@class, 'pagination_action--next')]");
    private final By previousPage = By.xpath ("//li [contains(@class, 'pagination_action--prev')]");
    private final By currentPage = By.xpath ("//ul [contains(@class,'pagination_actions--pages')] /li [contains(@class,'pagination_action--active')]");

    //Column headers (can be used for sorting test scenarios)

    private final By typeHeader = By.xpath("//span [@ref='eText'] [text()='Type']");
    private final By titleHeader = By.xpath("//span [@ref='eText'] [text()='Title']");
    private final By contactHeader = By.xpath("//span [@ref='eText'] [text()='Contact']");
    private final By institutionHeader = By.xpath("//span [@ref='eText'] [text()='Institution']");
    private final By locationHeader = By.xpath("//span [@ref='eText'] [text()='Location']");
    private final By dateHeader = By.xpath("//span [@ref='eText'] [text()='Start Date']");
    private final By tagsHeader = By.xpath("//span [@ref='eText'][text()='Tags']");
    private final By emptyResults = By.xpath ("//div [contains(@class,'ag-layout-auto-height')] [text()='No data available']");

    //Not sure what to do with these
    private final By notesSection = By.xpath("//div[contains(@class,'note-manager-list')]//div[contains(@class,'x-dataview-item')]");

    //Activity Details Page
    private final By corpPartInActivityDetails = By.xpath("//a[@class='activity-detail_overview_link']");



    public ActivityPage(WebDriver driver) {
        super(driver);
    }


    private List<WebElement> returnTableRows (){

        List <WebElement> rowList = findElements(By.xpath("//div[@class='ag-pinned-left-cols-container']/div[@row-index]"));
        ArrayList<WebElement> tableRowsList = new ArrayList<>(rowList);

        return tableRowsList;

    }

    public String getNewNote() {
        // Waits for the load more button to appear at the bottom of the page.
        waitForLoadingScreen();
        waitForAnyElementToAppear(notesSection);
        return findVisibleElement(notesSection).getText();
    }

    //selecting first note
    public NoteDetailsPage selectFirstNoteInList() {

        List<WebElement> elements = findVisibleElements(firstActivityTitle);
        for(WebElement i : elements){
            try {
                i.click();
            }
            catch (WebDriverException e)
            {}
        }

        return new NoteDetailsPage(getDriver());
    }
    //opens Create Activity modal
    public LogActivityPage logNote() {

        waitForElementToDissapear(fieldLabel);
        waitForElementToBeClickable(newActivityIcon).click();

        return new LogActivityPage(getDriver());
    }

    public ActivityPage searchForNote(String note) {

        waitForElement(activitySearchField);
        waitForElementToDissapear(spinner);

        removeSearchContents();
        findVisibleElement(activitySearchField).sendKeys(note);
        waitForLoadingScreen();
//sometimes fails waitForElementToRest
        if (doesElementExist(firstActivityTitle)) {
            waitForElementToRest(firstActivityTitle, 2000L);
        }

        return this;
    }

    public CorpPartDetailsPage clickOnCorpPart() {

        waitForElementToBeClickable(corpPartInActivityDetails).click();

        return new CorpPartDetailsPage(getDriver());

    }

    public ActivityPage removeSearchContents() { //clears Activity Search Field

        try {
            waitForElementToAppear(clearActivitySearchField);
            findVisibleElement(clearActivitySearchField).click();
        }

        catch (Exception e) { //basically if nothing in field already do nothing

        }

        return this;
    }

    /**
     * FILTERS
     */

    public ActivityPage openCloseFilterMenu() {

        waitForElementToBeClickable(filterMenu).click();

        return this;
    }

    private By getColumnSelector(ColumnType column){
        By selector =null;

        switch(column){
            case TYPE:
                selector = typeHeader;
                break;
            case TITLE:
                selector = titleHeader;
                break;
            case CONTACT:
                selector = contactHeader;
                break;
            case INSTITUTION:
                selector = institutionHeader;
                break;
            case LOCATION:
                selector = locationHeader;
                break;
            case DATE:
                selector = dateHeader;
                break;
            case TAGS:
                selector = tagsHeader;
                break;
        }
        return selector;
    }

    private By getFilterSelector(FilterType filter){
        By selector=null;
        switch(filter){
            case EMAIL:
                selector = emailFilterOption;
                break;
            case PHONE:
                selector = phoneFilterOption;
                break;
            case NOTE:
                selector = noteFilterOption;
                break;
            case MEETING:
                selector = meetingFilterOption;
                break;
            case EARNINGS:
                selector = earningsFilterOption;
                break;
            case ROADHSHOW:
                selector = roadshowFilterOption;
                break;
            case CONFERENCE:
                selector = conferenceFilterOption;
                break;
        }
        return selector;
    }

    public ActivityPage clickColumnHeader(ColumnType column){
        By selector = getColumnSelector(column);
        waitForLoadingScreen();
        waitForElementToBeClickable(selector);
        findElement(selector).click();
        waitForLoadingScreen();
        return this;
    }


    private boolean isColumnAscending(ColumnType column){
        By selector = getColumnSelector(column);
        return findElement(selector).getAttribute("class").contains("asc");
    }

    public Calendar filterDate(Calendar calendar) {
        calendar.selectStartDate(startTimeSelector, previousMonthButton, selectedMonth, selectedDay);
        calendar.selectEndDate(endTimeSelector, nextMonthButton, selectedMonth, selectedDay);
        pause(500L);
        // helps keep track of which days were selected
        calendar.print();
        return calendar;
    }

    public ActivityPage updateDateRangeFilter() {

        java.util.Calendar cal = java.util.Calendar.getInstance();
        String curr_month = new SimpleDateFormat ("MMMM").format(cal.getTime());
        String curr_year = new SimpleDateFormat("YYYY").format(cal.getTime());

        waitForElementToBeClickable(openCalendarFilter).click();
        waitForElementToBeClickable(By.xpath("//td[@title='"+ curr_month +" 2, "+ curr_year +"']")).click();

        cal.add(java.util.Calendar.MONTH, 1);
        String next_month = new SimpleDateFormat("MMMM").format(cal.getTime());
        String next_year = new SimpleDateFormat("YYYY").format(cal.getTime());

        findVisibleElement(By.xpath("//td[@title='"+ next_month +" 28, "+ next_year +"']")).click();

        waitForLoadingScreen();
        return this;
    }


    public ActivityPage clickFilterCheckbox(FilterType filter){

        By selector = getFilterSelector(filter);
        wait.until(ExpectedConditions.elementToBeClickable(typeFilterDropdown)).click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(selector));
        waitForElementToBeClickable(selector).click();
        waitForLoadingScreen();


        return this;

    }

//    Create a FilterCheckbox for Corporate Participant Dropdown Menu
    public ActivityPage dropDownCorpPartFilter(){

        try {
            findElement(CpDropdown).click();
            waitForLoadingScreen();

            return this;
        } catch (Exception e) {
            return this;
        }

    }

    // Close the Corporate Participant Dropdown
    public ActivityPage closeCorpPartDropDown() {
        wait.until(ExpectedConditions.elementToBeClickable(CorpPartDropDownMask)).click();
        waitForLoadingScreen();
        return this;
    }

    //Click a specific Corporate Participant, can edit by checking out specFilterCorpPart
    public ActivityPage clickSpecificCorpPartFilter(String corporatePart) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div [@class='select-list__item-multi_label'] [text()='"+corporatePart+"']"))).click();
        waitForLoadingScreen();
        return this;

    }


    public ActivityPage checkCorpPartBox() {
        wait.until(ExpectedConditions.elementToBeClickable(firstCorpPartInDropdown)).click();
        waitForLoadingScreen();
        return this;
    }


    public boolean isFilteredCorrectly(FilterType filter){

        List<WebElement> rows = returnTableRows();

        for (WebElement i : rows){

            if(!(i.findElement(By.xpath(".//i")).getAttribute("class").equals("cell-category_icon " + filter.iconClass()))){

                return false;
            }

        }
        return true;
    }

    public ActivityPage yourActivityFilter(){

        findElement(yourActivityToggle).click();
        waitForLoadingScreen();

        return this;
    }

    private boolean isTitleSorted (List<WebElement> rows){

        ArrayList<WebElement> titles = new ArrayList<>();
        for(WebElement i : rows){
            titles.add(i.findElement(By.xpath("//div[@col-id='title']")));
        }
        if(isColumnAscending(ColumnType.TITLE)){
            return elementsAreAlphaUpSortedIgnoreCase(titles);
        }
        else
            return elementsAreAlphaDownSortedIgnoreCase(titles);
    }

    private boolean isContactSorted (List<WebElement> rows){

        ArrayList<WebElement> contacts = new ArrayList<>();
        for(WebElement i : rows){
            contacts.add(i.findElement(By.xpath("//div[@col-id='contact']")));
        }
        if(isColumnAscending(ColumnType.CONTACT)){
            return elementsAreAlphaUpSorted(contacts);
        }
        else
            return elementsAreAlphaDownSorted(contacts);
    }
    private boolean isInstitutionSorted(List<WebElement> rows){
        ArrayList<WebElement> institutions = new ArrayList<>();
        for(WebElement i : rows){

//            institutions.add(i.findElement(By.className("institution")));
            waitForElementToAppear(By.xpath("//div[@col-id='institution']"));
            institutions.add(i.findElement(By.xpath("//div[@col-id='institution']")));
        }

        if(isColumnAscending(ColumnType.INSTITUTION)){
            return elementsAreAlphaUpSorted(institutions);
        }
        else
            return elementsAreAlphaDownSorted(institutions);
    }


    private boolean isTypeSorted(List<WebElement> rows){
        FilterType email = FilterType.EMAIL;
        FilterType note = FilterType.NOTE;
        FilterType meeting = FilterType.MEETING;
        FilterType phone = FilterType.PHONE;
        FilterType earnings = FilterType.EARNINGS;
        FilterType roadshow = FilterType.ROADHSHOW;
        FilterType conference = FilterType.CONFERENCE;
        FilterType none = FilterType.NONE;
        FilterType savedIcon = null;
        FilterType currentIcon= FilterType.PHONE;

        for (WebElement i : rows){
            if(savedIcon!=null){
                currentIcon = currentIcon.returnType(i.findElement(By.xpath("//div[@class='cell-category']/i")).getAttribute("class"));
                if (currentIcon!=savedIcon) {
                    switch (currentIcon) {
                        case EMAIL:
                            if (email.isChecked()) {
                                return false;
                            } else {
                                switch(savedIcon){
                                    case EMAIL:
                                        email.setChecked(true);
                                        break;
                                    case NOTE:
                                        note.setChecked(true);
                                        break;
                                    case MEETING:
                                        meeting.setChecked(true);
                                        break;
                                    case PHONE:
                                        phone.setChecked(true);
                                        break;
                                    case EARNINGS:
                                        earnings.setChecked(true);
                                        break;
                                    case ROADHSHOW:
                                        roadshow.setChecked(true);
                                        break;
                                    case CONFERENCE:
                                        conference.setChecked(true);
                                    case NONE:
                                        none.setChecked(true);
                                        break;
                                }
                                savedIcon = email;
                            }
                            break;
                        case NOTE:
                            if (note.isChecked()) {

                                return false;

                            } else {
                                switch(savedIcon){
                                    case EMAIL:
                                        email.setChecked(true);
                                        break;
                                    case NOTE:
                                        note.setChecked(true);
                                        break;
                                    case MEETING:
                                        meeting.setChecked(true);
                                        break;
                                    case PHONE:
                                        phone.setChecked(true);
                                        break;
                                    case EARNINGS:
                                        earnings.setChecked(true);
                                    case ROADHSHOW:
                                        roadshow.setChecked(true);
                                        break;
                                    case CONFERENCE:
                                        conference.setChecked(true);
                                    case NONE:
                                        none.setChecked(true);
                                        break;
                                }
                                savedIcon = note;
                            }
                            break;
                        case MEETING:
                            if (meeting.isChecked()) {
                                return false;
                            } else {
                                switch(savedIcon){
                                    case EMAIL:
                                        email.setChecked(true);
                                        break;
                                    case NOTE:
                                        note.setChecked(true);
                                        break;
                                    case MEETING:
                                        meeting.setChecked(true);
                                        break;
                                    case PHONE:
                                        phone.setChecked(true);
                                        break;
                                    case EARNINGS:
                                        earnings.setChecked(true);
                                        break;
                                    case ROADHSHOW:
                                        roadshow.setChecked(true);
                                        break;
                                    case CONFERENCE:
                                        conference.setChecked(true);
                                        break;
                                    case NONE:
                                        none.setChecked(true);
                                        break;
                                }
                                savedIcon = meeting;
                            }
                            break;
                        case PHONE:
                            if (phone.isChecked()) {
                                return false;
                            } else {
                                switch(savedIcon){
                                    case EMAIL:
                                        email.setChecked(true);
                                        break;
                                    case NOTE:
                                        note.setChecked(true);
                                        break;
                                    case MEETING:
                                        meeting.setChecked(true);
                                        break;
                                    case PHONE:
                                        phone.setChecked(true);
                                        break;
                                    case EARNINGS:
                                        earnings.setChecked(true);
                                    case ROADHSHOW:
                                        roadshow.setChecked(true);
                                        break;
                                    case CONFERENCE:
                                        conference.setChecked(true);
                                    case NONE:
                                        none.setChecked(true);
                                        break;
                                }
                                savedIcon = phone;
                            }
                        case EARNINGS:
                            if (earnings.isChecked()) {

                                return false;

                            } else {
                                switch(savedIcon) {
                                    case EMAIL:
                                        email.setChecked(true);
                                        break;
                                    case NOTE:
                                        note.setChecked(true);
                                        break;
                                    case MEETING:
                                        meeting.setChecked(true);
                                        break;
                                    case PHONE:
                                        phone.setChecked(true);
                                        break;
                                    case EARNINGS:
                                        earnings.setChecked(true);
                                    case ROADHSHOW:
                                        roadshow.setChecked(true);
                                        break;
                                    case CONFERENCE:
                                        conference.setChecked(true);
                                        break;
                                    case NONE:
                                        none.setChecked(true);
                                        break;
                                }
                                savedIcon = roadshow;
                            }

                        case ROADHSHOW:
                            if (roadshow.isChecked()) {
                                return false;
                            } else {
                                switch(savedIcon){
                                    case EMAIL:
                                        email.setChecked(true);
                                        break;
                                    case NOTE:
                                        note.setChecked(true);
                                        break;
                                    case MEETING:
                                        meeting.setChecked(true);
                                        break;
                                    case PHONE:
                                        phone.setChecked(true);
                                        break;
                                    case EARNINGS:
                                        earnings.setChecked(true);
                                    case ROADHSHOW:
                                        roadshow.setChecked(true);
                                        break;
                                    case CONFERENCE:
                                        conference.setChecked(true);
                                        break;
                                    case NONE:
                                        none.setChecked(true);
                                        break;
                                }
                                savedIcon = roadshow;
                            }
                        case CONFERENCE:
                            if (roadshow.isChecked()) {
                                return false;
                            } else {
                                switch (savedIcon) {
                                    case EMAIL:
                                        email.setChecked(true);
                                        break;
                                    case NOTE:
                                        note.setChecked(true);
                                        break;
                                    case MEETING:
                                        meeting.setChecked(true);
                                        break;
                                    case PHONE:
                                        phone.setChecked(true);
                                        break;
                                    case EARNINGS:
                                        earnings.setChecked(true);
                                        break;
                                    case ROADHSHOW:
                                        roadshow.setChecked(true);
                                        break;
                                    case CONFERENCE:
                                        conference.setChecked(true);
                                        break;
                                    case NONE:
                                        none.setChecked(true);
                                        break;
                                }
                                savedIcon = conference;
                            }
                        case NONE:
                            if (none.isChecked()) {
                                return false;
                            } else {
                                switch(savedIcon){
                                    case EMAIL:
                                        email.setChecked(true);
                                        break;
                                    case NOTE:
                                        note.setChecked(true);
                                        break;
                                    case MEETING:
                                        meeting.setChecked(true);
                                        break;
                                    case PHONE:
                                        phone.setChecked(true);
                                        break;
                                    case EARNINGS:
                                        earnings.setChecked(true);
                                    case ROADHSHOW:
                                        roadshow.setChecked(true);
                                        break;
                                    case CONFERENCE:
                                        conference.setChecked(true);
                                        break;
                                    case NONE:
                                        none.setChecked(true);
                                        break;
                                }
                                savedIcon = none;
                            }
                    }
                }

            }
            else {
                if (i.findElement(By.xpath(".//i")).getAttribute("class").equals(email.iconClass())) {
                    savedIcon = FilterType.EMAIL;
                } else if (i.findElement(By.xpath(".//i")).getAttribute("class").equals(note.iconClass())) {
                    savedIcon = FilterType.NOTE;
                } else if (i.findElement(By.xpath(".//i")).getAttribute("class").equals(meeting.iconClass())){
                    savedIcon = FilterType.MEETING;
                } else if(i.findElement(By.xpath(".//i")).getAttribute("class").equals(roadshow.iconClass())) {
                    savedIcon = FilterType.ROADHSHOW;
                }
                else if(i.findElement(By.xpath(".//i")).getAttribute("class").equals(conference.iconClass())) {
                    savedIcon = FilterType.CONFERENCE;
                }
                else if(i.findElement(By.xpath(".//i")).getAttribute("class").equals(earnings.iconClass())) {
                    savedIcon = FilterType.EARNINGS;
                }
                else {
                    savedIcon = FilterType.PHONE;
                }
            }
        }
        return true;
    }


    private boolean isDateSorted(List<WebElement> rows){
        ArrayList<WebElement> dates = new ArrayList<>();
        for(WebElement i : rows){
            dates.add(i.findElement(By.xpath("//div[@col-id='start']")));
        }
        if(isColumnAscending(ColumnType.DATE)){
            return elementsAreDateUpSorted(dates);
        } else{
            return elementsAreDateDownSorted(dates);
        }
    }



    public boolean isColumnSorted(ColumnType column){

        //waitForLoadingScreen();

        switch(column){
            case TYPE:
                return isTypeSorted(returnTableRows());
            case TITLE:
                return isTitleSorted(returnTableRows());
            case CONTACT:
                return isContactSorted(returnTableRows());
            case INSTITUTION:
                return isInstitutionSorted(returnTableRows());
            case DATE:
                return isDateSorted(returnTableRows());
            case LOCATION:
            case TAGS:
                break;
        }
        return false;
    }

    public boolean isEmptyResults() {

        try {
            waitForElement(emptyResults);
            findElement(emptyResults);
            return true;
        }
        catch(Exception e) {

            return false;
        }
    }

    /**
     *Getters : They basically return the contents of a specific field
     *
     */
    public String getDate() throws ParseException {
        //Gets date from first note on the activity page and format it
        DateFormat activityFormat = new SimpleDateFormat("MM/dd/yy");
        DateFormat detailsFormat = new SimpleDateFormat("MMMM d, yyyy");
        Date strToDate = activityFormat.parse(findVisibleElement(firstNoteInListDate).getText());
        String dateToString = detailsFormat.format(strToDate);
        return dateToString;
    }

    public String getActivityPageTag(){

        return findElement(firstNoteInListNewTag).getText();
    }

    public String getActivityPageLocation(){return findVisibleElement(firstNoteInListLocation).getText(); }

    public String getNoNote() {

        waitForElement(emptyResults);
        return findElement(emptyResults).getText();
    }


    public String getSearchResults() { return findElement(notesSection).getText(); }

    //text from Elements comes in the form Note (4) removing all the string except the number, parses the String into an int
    public int getCountFilter(FilterType filter) {

        String type="";
        type+=findElement(typeField).getText();
        switch(filter) {
            case NOTE:

                type=type.replace("Note (", "");

                break;

            case PHONE:

                type=type.replace("Phone (", "");

                break;

            case EMAIL:

                type=type.replace("Email (", "");

                break;

            case MEETING:

                type=type.replace ("Meeting (", "");

                break;

            case EARNINGS:

                type=type.replace ("Earnings (", "");

                break;

            case ROADHSHOW:

                type=type.replace ("Roadshow (", "");

                break;

            case CONFERENCE:

                type=type.replace ("Conference (", "");

                break;

            default : //if FilterType is null will consider type= All (<number>)
                type=type.replace("All (", "");

        }
        type=type.replace(")","");
        return Integer.parseInt(type);

    }

    public boolean verifyDateFilter(Calendar calendar) {
        waitForLoadingScreen();
        // sorting the date by earliest to latest
        findVisibleElement(dateHeader).click();
        pause(2000L);
        boolean Sorted = true;
        if (!calendar.EarliestDateWithinRange(findVisibleElement(firstNoteInListDate).getText())) {
            System.out.println("Earliest date in the table is earlier than the selected end time");
            Sorted = false;
        }
        waitForLoadingScreen();
        // sorting the date by latest to earliest
        findVisibleElement(dateHeader).click();
        pause(2000L);
        if (!calendar.latestDateWithinRange(findVisibleElement(firstNoteInListDate).getText())) {
            System.out.println("Latest date in the table is later than the selected end time");
            Sorted = false;
        }
        return Sorted;
    }

    public ActivityPage clickNthActivityCheckBox(int row) {

        try {
            waitForElementToBeClickable(By.xpath("//div [@row-index='"+row+"']/descendant::span [@class='ag-icon ag-icon-checkbox-unchecked']")).click();
        } catch (StaleElementReferenceException e) {
            waitForElementToBeClickable(By.xpath("//div [@row-index='"+row+"']/descendant::span [@class='ag-icon ag-icon-checkbox-unchecked']")).click();
        }

        return this;
    }
    //index starts from 0
    public String  getActivityTitle(int rowIndex) { //returns Title of the Activity based off of String

        waitForElementToAppear(By.xpath ("//div [@row-index='"+rowIndex+"']/descendant::div[@col-id='title']")).getText();

        return findElement(By.xpath ("//div [@row-index='"+rowIndex+"']/descendant::div[@col-id='title']")).getText();

    }
    public String getActivityStartDate(int rowIndex) { //returns the Start Date of the Activity based off of row index

        waitForElementToAppear(By.xpath("//div [@row-index='"+rowIndex+"']/descendant::div[@col-id='start']")).getText();

        return findElement(By.xpath("//div [@row-index='"+rowIndex+"']/descendant::div[@col-id='start']")).getText();
    }

    public String getActivityType(String title) {

        return findElement(By.xpath("//div[text()='"+title+"']/ancestor::div[@role='row']/descendant::div[@class='cell-category']/i")).getAttribute("class");

    }
    //TODO make getInstitution and getContact apply to more than one Institution/Contact

    public String getInstitution (int rowIndex) {

        return findElement(By.xpath ("//div [@row-index='"+rowIndex+"']/descendant::div[@col-id='institution']")).getText();

    }
    public String [] getInstitutions (int rowIndex, int quantity) { //if more than one Institution hasn't been tested yet

        String [] elements = new String[quantity];
        waitForElement(By.xpath("//div [@row-index='"+rowIndex+"']/descendant::div[@col-id='institution']/div/div")).click(); //clicks the more icon

        for (int x=0;x<quantity;x++) {
            elements[x]= findElement(By.xpath ("//div [contains(@class,'react-scrollbar')]/div/div["+(x+1)+"] [@class='popover_item popover_item--thin']")).getText(); //gathers the contents of each element on popup
        }
        return elements;


    }
    public String getContact (int rowIndex) {

        return findElement(By.xpath ("//div [@row-index='"+rowIndex+"']/descendant::div[@col-id='contact']")).getText();
    }

    public String getLocation (int rowIndex) {

        return findElement(By.xpath ("//div [@row-index='"+rowIndex+"']/descendant::div[@col-id='address']")).getText();
    }

    public String getActivityTag(int row, int tagNumber) {
        //returns tag on Activity Landing page without using Utility Menu so only works for the first 2/3 tags on an Activity

        //row starts from 0 and tagNumber starts from 1


        return findElement(By.xpath ("//div [@row-index='"+row+"']/descendant::div[@class='tags']/div ["+tagNumber+"]")).getText();


    }

    public NoteDetailsPage clickActivity (String title) { //clicks on an Activity to travel to its Details page
        //uses Title to select an element
        waitForElementToDissapear(spinner);

        waitForElementToBeClickable(By.xpath("//div [@class='cell-title_text'] [text()=\""+title+"\"]")).click();



        return new NoteDetailsPage (getDriver());
    }

    /**
     *Delete Operations
     *
     */
    public ActivityPage clickDeleteButton() {

        findVisibleElement(deleteIcon).click();
        waitForElementToAppear(confirmDelete);
        findVisibleElement(confirmDelete).click();
        waitForElementToDissapear(spinner);
        waitForLoadingScreen();

        return this;
    }

    public ActivityPage deleteActivityWithCP() {

        removeSearchContents();
        openCloseFilterMenu();
        dropDownCorpPartFilter();
        clickSpecificCorpPartFilter("A Automation Test");
        closeCorpPartDropDown();

        selectBulkCheckbox();
        clickDeleteButton();

        try {
            waitForElement(emptyResults);
        }
        catch (Exception e) { //so test won't fail if there are still results left over (ex. 11 results 10 get deleted empty Results doesn't appear)

        }

        return this;
    }

    public ActivityPage deleteAllYourActivities() {

        openCloseFilterMenu();
        yourActivityFilter();

        selectBulkCheckbox();
        clickDeleteButton();

        try {
            waitForElement(emptyResults);
        }
        catch (Exception e) { //so test won't fail if there are still results left over (ex. 11 results 10 get deleted empty Results doesn't appear)

        }

        return this;
    }

    //ToDo Add a Conditional Statement to check if Checkbox is selected, skipping if the Checkbox is already selected
    public ActivityPage deleteAllNotes(String title){ //assumes bulkCheckbox is not selected

        //List<WebElement> searchResults;

        searchForNote(title);
        selectBulkCheckbox();
        clickDeleteButton(); //clicks delete Icon and confirms delete

        try {
           waitForElement(emptyResults);
        }
        catch (Exception e) { //so test won't fail if there are still results left over (ex. 11 results 10 get deleted empty Results doesn't appear)

        }

        return this;
    }
    //same as deleteAllNotes except it cancels the act of deleting
    public ActivityPage cancelBulkDelete(String title) {

        //List<WebElement> searchResults;

        searchForNote(title);
        selectBulkCheckbox();
        findElement(deleteIcon).click();
        waitForElementToAppear(cancelDelete);
        findVisibleElement(cancelDelete).click();
        waitForElementToDissapear(spinner);
        waitForLoadingScreen();

        return this;
    }
    public ActivityPage deleteNote(String title) {

        waitForLoadingScreen();
        searchForNote(title);
        selectCheckbox(title);
        clickDeleteButton();

        return this;
    }

    //PreCondition : 100 or less Activities displayed (100 per page setting)
    //Child Activities being expanded can distort the count to be greater than what is displayed
    //Works for Checkboxes both checked and unchecked



    public List<String> getActivityTitles(){

        waitForLoadingScreen();
        List<WebElement> titlesElement = findElements(activityTitles);
        List<String> titlesString = new ArrayList<>();
        for (WebElement e : titlesElement)
        {
            titlesString.add(e.getText());
        }

        return titlesString;
    }

    /**
     * Number of Activities counting
     *
     */

    public int getNumberOfDisplayedActivities(){

        //Always displaying 10 elements per page, this is default pagination
        //changeToTen();

        //Traverse to last page on pagination
        List<WebElement> listOfPages = findElements(getListOfPages);
        int getLastPage = Integer.parseInt(listOfPages.get(listOfPages.size()-2).getText());

        if(getLastPage != 1) {
            findElement(By.xpath("//a [@aria-label='Page " + getLastPage + "']")).click();
        }
        //Multiplying by 10 because of pagination, showing 10 elements per page
        try {

            return findVisibleElements(rowCheckBox).size() + ((getLastPage - 1) * 10);

        } catch (StaleElementReferenceException e) {

            return findVisibleElements(rowCheckBox).size() + ((getLastPage - 1) * 10);
        }
    }

    public Boolean showsDifferentActivitiesForDifferentUser(List<String> userOne,List<String> userTwo){

        if (userOne.size() == userTwo.size()){
            if (userOne.get(0).contentEquals(userTwo.get(0))){
                return false;
            }
            return true;
        }
        return true;
    }

    /**
     * Creating Activities
     *
     */

    public void createNewNote(String title, String note, String tag) {

        waitForLoadingScreen();
        LogActivityPage logActivityPage = logNote();
        waitForLoadingScreen();
        logActivityPage.enterNoteDetails(title,note,tag)
                .postActivity(FilterType.EARNINGS);
        waitForLoadingScreen();
        accessSideNavFromPage().selectActivityPageFromSideNav();

    }
    public LogActivityPage logIcon() {

        waitForElementToDissapear(toaster);
        waitForElementToAppear(logActivityIcon);
        findElement(logActivityIcon).click();

        return new LogActivityPage(getDriver());
    }

    /**
     * Selecting Checkboxes
     *
     */

    private ActivityPage selectCheckbox(String title) { //selects an Activity based off its title

        waitForLoadingScreen();
        waitForElement (By.xpath ("//div [text()='"+title+"']/ancestor::div [@role='row']/descendant::span [@class='ag-selection-checkbox']"));
        findElement (By.xpath ("//div [text()='"+title+"']/ancestor::div [@role='row']/descendant::span [@class='ag-selection-checkbox']")).click();

        return this;
    }
    private ActivityPage selectBulkCheckbox() { //bulk selects Activities and clicks the Delete icon (opens Confirmation Delete modal)

        waitForElement(bulkCheckBox);
        findVisibleElement(bulkCheckBox).click();

        return this;
    }

    /**
     * Pagination
     *
     */

    public ActivityPage changeToTen() {

        waitForElement(tenListed);
        findVisibleElement(tenListed).click();

        return this;
    }
    public ActivityPage changeToTwentyFive() {

        waitForElement(twentyFiveListed);
        findVisibleElement(twentyFiveListed).click();

        return this;
    }
    public ActivityPage changeToFifty() {

        waitForElement(fiftyListed);
        findVisibleElement(fiftyListed).click();

        return this;
    }

    public ActivityPage changeToHundred() { //sometimes the pagination switch is not clickable not sure how to resolve


        waitForElement(hundredListed);
        findVisibleElement(hundredListed).click();

        return this;
    }

    //Testing to see if any results are returned true means no results false means results exist
    public boolean noResults() {

        try {
            findElement(emptyResults);

            return true;
        }
        catch (Exception e) {

            return false;

        }
    }




}

