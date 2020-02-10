package pageobjects.user.eventsTranscriptsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dannyl on 2017-08-08.
 * Updated by Kelly J in July, 2018
 */
public class EventsTranscriptsPage extends AbstractPageObject {

    //Events and Transcripts header
    private final By watchlistToggle = By.xpath("//div[div/span[text()='Peer List']]//div[contains(@id, 'ext-thumb')]");
    private final By transcriptToggle = By.xpath("//div[div/span[text()='Transcript']]//div[contains(@id, 'ext-thumb')]");
    private final By dateFilter = By.xpath("//div[contains(@class, 'x-size-monitored x-paint-monitored current-date-range x-layout-box-item x-stretched')]");
    private final By todayFilterButton = By.xpath("//span[contains(text(), 'Today')]");
    private final By previousDateRange = By.xpath("//div[contains(@class,'range-navigator') and span[contains(@class,'q4i-arrow-left-2pt')]]");
    private final By nextDateRange = By.xpath("//div[contains(@class,'range-navigator') and span[contains(@class,'q4i-arrow-right-2pt')]]");
    private final By dayFilterButton = By.xpath("//span[contains(text(), 'Day')]");
    private final By weekFilterButton = By.xpath("//span[contains(text(), 'Week')]");
    private final By monthFilterButton = By.xpath("//span[contains(text(), 'Month')]");
    private final By searchInput = By.xpath("(//input[@type='search'])[2]");

    //Date header for which the Events/Transcripts took place
    private final By dateHeaders = By.xpath("//div[contains(@class, 'group-header')]");

    //Events and Transcripts table
    private final By transcriptIcon = By.xpath("//i[contains(@class, 'q4i-transcripts-2pt')]");
    private final By eventTranscriptList = By.xpath("//div[contains(@class, 'x-unsized x-list-item x-list-item-tpl event-list-item x-list-item-relative')]/div[contains(@class, 'x-innerhtml')]");
    private final By eventTitlesList = By.xpath("//div[contains(@class, 'details')]/div[contains(@class, 'title')]");
    private final By eventTypeList = By.xpath("//div[contains(@class, 'x-innerhtml')]/div[contains(@class, 'event-type')]");
    private final By showMoreButton = By.xpath("//span[contains(@class, 'q4i-arrow-down-2pt')]");

    //Add Symbol
    private final By addSymbolInput = By.name("symbol");
    private final By addSymbolResultList = By.xpath("//div[contains(@class,'filter-by-company-results')]");
    private final By addSymbolResultItem = By.xpath("//div[contains(@class,'result-item')]");
    private final By addedSecurity = By.xpath("//div[contains(@class,'saved-company')]");

    //Event types checkboxes
    private By[] eventTypesCheckboxes = new By[9];
    private final By callsEarnings =By.xpath("//div[contains(@class,'checkmark') and contains(@class, 'earningsCall')]");
    private final By callsGuidance =By.xpath("//div[contains(@class,'checkmark') and contains(@class, 'guidanceCall')]");
    private final By callsSalesAndRevenue =By.xpath("//div[contains(@class,'checkmark') and contains(@class, 'salesCall')]");
    private final By releasesEarnings =By.xpath("//div[contains(@class,'checkmark') and contains(@class, 'earningsRelease')]");
    private final By releasesSalesAndRevenue =By.xpath("//div[contains(@class,'checkmark') and contains(@class, 'salesRelease')]");
    private final By miscConferences =By.xpath("//div[contains(@class,'checkmark') and contains(@class, 'conference')]");
    private final By miscPresentations =By.xpath("//div[contains(@class,'checkmark') and contains(@class, 'presentation')]");
    private final By miscMeetings =By.xpath("//div[contains(@class,'checkmark') and contains(@class, 'meeting')]");
    private final By miscOther =By.xpath("//div[contains(@class,'checkmark') and contains(@class, 'other')]");

    //Array that holds the CSS classes for each type of event
    //To check the event type in the landing page list
    private String[] eventTypesClasses = new String[9];

    //Name of the 1st event in the landing page list
    private final By firstEvent = By.xpath("(//div[contains(@class, 'x-unsized x-list-item x-list-item-tpl event-list-item x-list-item-relative')]/div[contains(@class, 'x-innerhtml')])[1]");

    //Company of the 1st event in the landing page list
    private final By firstEventCompany = By.xpath("(//div[contains(@class, 'event-company')]//span[contains(@class, 'name')])[1]");

    //Event Container (click an event in the landing page list)
    private final By companyInEventContainer = By.xpath("//div[contains(@class, 'company-details')]//div[contains(@class, 'name')]");
    private final By closeButtonEventContainer = By.xpath("//span[contains(@class, 'q4i-close-4pt')]");


    public EventsTranscriptsPage(WebDriver driver) {super(driver);}

    //Returns list of all the visible date headers
    private ArrayList<String> returnDateHeaders () {
        int attempts = 0;

        List<WebElement> headerList = null;

        //To avoid crashing from StaleElementReferenceException
        //Solution from http://darrellgrainger.blogspot.com/2012/06/staleelementexception.html
        while (attempts < 2) {
            try {
                headerList = findVisibleElements(dateHeaders);
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }

        attempts = 0;
        ArrayList<String> dates = new ArrayList<>();
        for (WebElement i : headerList) {
            while (attempts < 2) {
                try {
                    dates.add(i.getText());
                    break;
                } catch (StaleElementReferenceException e) {
                }
                attempts++;
            }
        }
        return dates;
    }

    //Returns list of the table rows in the Events & Transcripts table
    private List<WebElement> returnEventsList (){
        List<WebElement> rowList = findVisibleElements(eventTranscriptList);
        ArrayList<WebElement> tableRowsList = new ArrayList<>(rowList);
        return tableRowsList;
    }

    //Returns list of all the Events & Transcripts titles in the table
    private List<WebElement> returnEventsTitlesList (){
        List<WebElement> rowList = findVisibleElements(eventTitlesList);
        ArrayList<WebElement> tableRowsList = new ArrayList<>(rowList);
        return tableRowsList;

    }

    //Returns list of all the Events & Transcripts titles in the table
    private List<WebElement> returnEventsTypesList (){
        List<WebElement> rowList = findVisibleElements(eventTypeList);
        ArrayList<WebElement> tableRowsList = new ArrayList<>(rowList);
        return tableRowsList;

    }

    //Clicks Peer List toggle and checks the toggle state (through html class)
    public boolean clickPeerListToggle() {
        WebElement peerlistToggle = driver.findElement(By.xpath("//div[div/span[text()='Peer List']]//div[contains(@id, 'ext-slider')]"));
        waitForElementToBeClickable(watchlistToggle).click();
        waitForLoadingScreen();
        if (peerlistToggle.getAttribute("class").contains("x-toggle-on")) {
            waitForElementToBeClickable(watchlistToggle).click();
            waitForLoadingScreen();
            return peerlistToggle.getAttribute("class").contains("x-toggle-off");
        } else {
            return false;
        }
    }

    //Clicks the 'Day' filter button
    //Checks that the date filter matches the date on the Date Header
    public Boolean filterByDay(){
        waitForLoadingScreen();
        waitForElementToBeClickable(dayFilterButton).click();
        waitForLoadingScreen();

        String date = waitForElementToAppear(dateFilter).getText();
        int index = date.indexOf(",");
        if (index != -1) {
            date = date.substring(index + 2); // To get rid of the day and space
        }

        return dateMatchesDateHeader(date);
    }

    //Clicks the 'Today' button then the 'Month' button
    //Checks that the Date Header contains today's month
    public Boolean filterByMonth(){
        waitForLoadingScreen();
        waitForElementToBeClickable(todayFilterButton).click();
        waitForElementToBeClickable(monthFilterButton).click();
        waitForLoadingScreen();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMM");
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        String date = dateFormat.format(today);

        return dateMatchesDateHeader(date);
    }

    //Clicks the 'Today' button then the 'Week' button
    //Checks that the 2nd date in the date range matches the date in the Date header
    //ex. If the date range is 'August 26, 2018 - September 1, 2018', the first date in the Date Header should be
    //    September 1, 2018
    public Boolean filterByWeek(){
        waitForElementToBeClickable(todayFilterButton).click();
        waitForElementToBeClickable(weekFilterButton).click();
       // waitForElementToBeClickable(watchlistToggle).click();
        waitForLoadingScreen();

        String date = waitForElementToAppear(dateFilter).getText();
        int index = date.indexOf("- ");
        if (index != -1) {
        date = date.substring(index + 2); // +2 because we want to get rid of the dash and space
        }

        return dateMatchesDateHeader(date);
    }

    //Clicks the 'Today' button
    //Checks that the Date Header contains today's date
    public Boolean filterByToday(){
        waitForElementToBeClickable(todayFilterButton).click();
        waitForLoadingScreen();
        waitForElementToRest(eventTranscriptList, 500L);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMM dd, yyyy");
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        String date = dateFormat.format(today);
        System.out.println(date);

        return dateMatchesDateHeader(date);
    }

    //Checks that each row of the table includes the Transcripts icon
    private boolean checkFilterForTranscript(){
        List<WebElement> transcripts = returnEventsList();
        for (WebElement i : transcripts){
            try {
                i.findElement(transcriptIcon);
            }
            catch (ElementNotFoundException e) {
                return false;
            }
        }
        return true;
    }

    //CLicks the Transcript toggle
    //Checks that each row of the table includes the Transcripts icon
    //Checks that the status of the toggle is updated in the HTML
    public Boolean filterByTranscript(){
        WebElement transcriptText = driver.findElement(By.xpath("//div[div/span[text()='Transcript']]//div[contains(@id, 'ext-slider')]"));
        waitForElementToBeClickable(transcriptToggle).click();
        waitForLoadingScreen();
        pause(2000L);
        if (transcriptText.getAttribute("class").contains("x-toggle-on")) {
            if (checkFilterForTranscript()) {
                waitForElementToBeClickable(transcriptToggle).click();
                waitForLoadingScreen();
                return transcriptText.getAttribute("class").contains("x-toggle-off");
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //Clicks the Today button (Day filter is automatically clicked too)
    //Clicks the left arrow if direction = "previous" and right arrow if direction = "next"
    //Clicks the respective arrow twice and checks the Date Header updates correctly
    public boolean filterByDateRange_Day(String direction) {
        int numDays = 1;

        if (direction.equals("previous")) {
            numDays = -1;
        }

        waitForElementToBeClickable(todayFilterButton).click();
        waitForLoadingScreen();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMM dd, yyyy");

        Calendar cal = Calendar.getInstance();

        if (direction.equals("previous")) {
            waitForElementToBeClickable(previousDateRange).click();
        } else if (direction.equals("next")) {
            waitForElementToBeClickable(nextDateRange).click();
        }

        waitForLoadingScreen();
        cal.add(Calendar.DATE, numDays);

        Date newDay = cal.getTime();

        String newDate = dateFormat.format(newDay);

        if (dateMatchesDateHeader(newDate)) {
            if (direction.equals("previous")) {
                waitForElementToBeClickable(previousDateRange).click();
            } else if (direction.equals("next")) {
                waitForElementToBeClickable(nextDateRange).click();
            }

            waitForLoadingScreen();
            cal.add(Calendar.DATE, numDays);
            newDay = cal.getTime();
            newDate = dateFormat.format(newDay);
            return dateMatchesDateHeader(newDate);

        } else {
            return false;
        }
    }

    //Clicks the Week button
    //Clicks the left arrow if direction = "previous" and right arrow if direction = "next"
    //Clicks the respective arrow twice and checks the Date Header updates correctly
    public boolean filterByDateRange_Week(String direction) {
        int numWeeks = 1;

        if (direction.equals("previous")) {
            numWeeks = -1;
        }

        waitForElementToBeClickable(weekFilterButton).click();
        waitForLoadingScreen();

        String date = waitForElementToAppear(dateFilter).getText();
        int index = date.indexOf("- ");
        if (index != -1) {
            date = date.substring(index + 2); // +2 because we want to get rid of the dash and space
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMM dd, yyyy");
        Date dateOnFilter = null;

        try {
            dateOnFilter = dateFormat.parse(date);
        } catch(ParseException e) {
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateOnFilter);

        if (direction.equals("previous")) {
            waitForElementToBeClickable(previousDateRange).click();
        } else if (direction.equals("next")) {
            waitForElementToBeClickable(nextDateRange).click();
        }

        waitForLoadingScreen();
        cal.add(Calendar.WEEK_OF_MONTH, numWeeks);
        Date newWeek = cal.getTime();
        String newDate = dateFormat.format(newWeek);


        if (dateMatchesDateHeader(newDate)) {
            if (direction.equals("previous")) {
                waitForElementToBeClickable(previousDateRange).click();
            } else if (direction.equals("next")) {
                waitForElementToBeClickable(nextDateRange).click();
            }

            waitForLoadingScreen();
            cal.add(Calendar.WEEK_OF_MONTH, numWeeks);
            newWeek = cal.getTime();
            newDate = dateFormat.format(newWeek);

            return dateMatchesDateHeader(newDate);

        } else {
            return false;
        }
    }

    //Clicks the Month button
    //Clicks the left arrow if direction = "previous" and right arrow if direction = "next"
    //Clicks the respective arrow twice and checks the Date Header updates correctly
    public boolean filterByDateRange_Month(String direction) {
        int numMonths = 1;

        if (direction.equals("previous")) {
            numMonths = -1;
        }

        waitForElementToBeClickable(todayFilterButton).click();
        waitForLoadingScreen();
        waitForElementToBeClickable(monthFilterButton).click();
        waitForLoadingScreen();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMM");
        Calendar cal = Calendar.getInstance();

        if (direction.equals("previous")) {
            waitForElementToBeClickable(previousDateRange).click();
        } else if (direction.equals("next")) {
            waitForElementToBeClickable(nextDateRange).click();
        }

        waitForLoadingScreen();
        cal.add(Calendar.MONTH, numMonths);
        Date newMonth = cal.getTime();
        String newDate = dateFormat.format(newMonth);


        if (dateMatchesDateHeader(newDate)) {
            if (direction.equals("previous")) {
                waitForElementToBeClickable(previousDateRange).click();
            } else if (direction.equals("next")) {
                waitForElementToBeClickable(nextDateRange).click();
            }

            waitForLoadingScreen();
            cal.add(Calendar.MONTH, numMonths);
            newMonth = cal.getTime();
            newDate = dateFormat.format(newMonth);
            return dateMatchesDateHeader(newDate);

        } else {
            return false;
        }
    }

    //Checks whether the passed in "date" variable matches the date in the Date Header of the table
    private boolean dateMatchesDateHeader(String date){
        waitForLoadingScreen();
        ArrayList<String> expectedDates = returnDateHeaders();
        for (String i : expectedDates){
            if (i.toLowerCase().equals(date.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    //Adds symbol with the passed in ticker and exchange
    public EventsTranscriptsPage addSymbol(String ticker, String exchange){
        List<WebElement> results;
        waitForElementToBeClickable(addSymbolInput).sendKeys(ticker);
        waitForElementToAppear(addSymbolResultList);
        waitForElementToBeClickable(addSymbolResultItem);
        results = new ArrayList<>(findVisibleElements(addSymbolResultItem));
        for(WebElement result : results){
            if(result.findElement(By.xpath(".//span[contains(@class,'symbol')]")).getText().contains(ticker) && result.findElement(By.xpath(".//span[contains(@class,'exchange')]")).getText().contains(exchange)){
                result.click();
                waitForLoadingScreen();
                return this;
            }
        }
        return null;
    }

    //Checks whether the passed in "ticker" variable is in the Security panel on the left
    public boolean wasSecurityAdded(String ticker){
        try {
            waitForAnyElementToAppear(addedSecurity);
            List<WebElement> securities = findVisibleElements(addedSecurity);
            for (WebElement security : securities) {
                if (security.getText().contains(ticker)) {
                    return true;
                }
            }
            return false;
        }
        catch(Exception e){
            return false;
        }

    }

    //Deletes the added symbol
    public EventsTranscriptsPage deleteSymbol() {
        waitForElementToBeClickable(addedSecurity).click();
        waitForLoadingScreen();
        return this;
    }

    //During these tests, only 1 security will be added
    //After deleting the security, checks that the security list is of size 0 (aka empty)
    public Boolean wasSecurityDeleted() {
        List<WebElement> securities = findVisibleElements(addedSecurity);
        return securities.size() == 0;
    }


    //Searches for the 2nd event in the table, and verifies that the event becomes first in the table
    //(aka checks that the search worked correctly)
    public Boolean searchEvent() {
        waitForLoadingScreen();
        List<WebElement> eventTitles = returnEventsTitlesList();
        String title = eventTitles.get(1).getText();

        findVisibleElement(searchInput).click();
        findVisibleElement(searchInput).clear();
        findVisibleElement(searchInput).sendKeys(title);
        pause(1000L);
        waitForLoadingScreen();

        String firstEventTitle = returnEventsTitlesList().get(0).getText();

        return firstEventTitle.equals(title);
    }

    //Sets up the eventTypesCheckboxes array
    public void setEventTypeArray() {
        eventTypesCheckboxes[0] = callsEarnings;
        eventTypesCheckboxes[1] = callsGuidance;
        eventTypesCheckboxes[2] = callsSalesAndRevenue;
        eventTypesCheckboxes[3] = releasesEarnings;
        eventTypesCheckboxes[4] = releasesSalesAndRevenue;
        eventTypesCheckboxes[5] = miscConferences;
        eventTypesCheckboxes[6] = miscPresentations;
        eventTypesCheckboxes[7] = miscMeetings;
        eventTypesCheckboxes[8] = miscOther;
    }

    //Sets up the eventTypesClasses array
    public void setEventTypeClassesArray() {
        eventTypesClasses[0] = "earningsCall";
        eventTypesClasses[1] = "guidanceCall";
        eventTypesClasses[2] = "salesCall";
        eventTypesClasses[3] = "earningsRelease";
        eventTypesClasses[4] = "salesRelease";
        eventTypesClasses[5] = "conference";
        eventTypesClasses[6] = "presentation";
        eventTypesClasses[7] = "meeting";
        eventTypesClasses[8] = "other";
    }

    //Checks the event type checkbox corresponding to index (ex. index of 0 = Calls - Earnings checkbox)
    //Clicks the show more button to show more events
    //Checks that only the corresponding event type shows up in the table (checks CSS of the event type icon)
    public Boolean checkEventTypeCheckbox(int index) {
        setEventTypeClassesArray();

        waitForLoadingScreen();
        waitForElementToBeClickable(eventTypesCheckboxes[index]).click();

        boolean showMoreDisplayed = driver.findElement(showMoreButton).isDisplayed();

        if (showMoreDisplayed) {
            waitForElementToBeClickable(showMoreButton).click();
            waitForLoadingScreen();
        }
        waitForElementToRest(showMoreButton, 700L);

        List<WebElement> eventTypes = returnEventsTypesList();
        for (WebElement title : eventTypes) {
            if(!(title.getAttribute("class").contains(eventTypesClasses[index]))) {
                return false;
            }
        }

        return true;
    }

    //Clicks on the first event and checks that the company matches in the table row and in the Event Details container
    public Boolean checkEventDetailContainer() {
        waitForLoadingScreen();
        String firstEventCompanyText = waitForElementToAppear(firstEventCompany).getText();

        waitForElementToBeClickable(firstEvent).click();
        waitForLoadingScreen();
        String companyInContainer = waitForAnyElementToAppear(companyInEventContainer).getText();

        waitForElementToBeClickable(closeButtonEventContainer).click();

        return firstEventCompanyText.equals(companyInContainer);
    }

    //Unchecks all the event types checkboxes
    public void uncheckAllEventTypes() {
        for(int i=0; i < 9; i++) {
            waitForElementToBeClickable(eventTypesCheckboxes[i]).click();
        }
    }





}
