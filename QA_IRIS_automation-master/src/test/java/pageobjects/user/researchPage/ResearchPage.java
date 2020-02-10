package pageobjects.user.researchPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.user.Calendar;
import pageobjects.user.contactPage.ContactPage;
import pageobjects.user.institutionPage.InstitutionPage;

/**
 * Created by patrickp on 2016-11-09.
 */
public class ResearchPage extends AbstractPageObject {
    private final By researchSearchField = By.name("contactsFilterSearch");
    private final By researchSearchResults = By.cssSelector(".active");
    private final By numberOfDocuments = By.cssSelector(".count");
    private final By firmName = By.cssSelector(".institution");
    private final By analystName = By.cssSelector("div.contact:nth-child(1) > a:nth-child(1) > span:nth-child(2)");
    private final By multipleAnalystDropdown = By.xpath("//div[contains(@class,'multiple') and contains(@class,'contacts')]");
    private final By researchHeadlineColumnHeader = By.cssSelector(".research-list .list-header .x-button.headline, .research-list .list-item .column.headline");
    private final By researchHeadline = By.cssSelector(".list-item .column.headline");
    private final By researchDateColumnHeader = By.cssSelector(".research-list .list-header .x-button:first-child, .research-list .list-item .column:first-child");
    private final By researchDate = By.cssSelector(".research-list .list-item .column:first-child");
    private final By researchFirmColumnHeader = By.cssSelector(".research-list .list-header .x-button.wide");
    private final By researchFirm = By.xpath("//div[contains(@class, 'institution q4i-institution-2pt')]");


    //for calendars
    private final By startTimeSelector = By.id("ext-input-5");
    private final By endTimeSelector = By.id("ext-input-6");
    private final By previousMonthButton = By.xpath("//div[@class='pmu-prev pmu-button']");
    private final By selectedMonth = By.xpath("//div[@class='pmu-month pmu-button']");
    private final By selectedDay = By.xpath("//div[@class='pmu-days']/div[@class='pmu-button'][11]");
    private final By dateFilterButton = By.cssSelector(".toolbar-panel .date-range .search-range");

    public ResearchPage(WebDriver driver) {
        super(driver);
    }

    public ResearchPage searchForDocument(String documentName) {
        waitForLoadingScreen();
        waitForElementToBeClickable(researchSearchField).click();
        findElement(researchSearchField).sendKeys(documentName);
        findElement(researchSearchField).sendKeys(Keys.ENTER);
        waitForLoadingScreen();

        return this;
    }

    public String getResearchHeadline() {
        return waitForElementToAppear(researchSearchResults).getText();
    }

    public int getNumberOfDocuments() {
        return Integer.parseInt(waitForElementToAppear(numberOfDocuments).getText().replaceAll("[()]", ""));
    }

    public String getFirmNameFromList() {
        return waitForElementToAppear(firmName).getText();
    }

    public InstitutionPage selectFirmFromResearchList() {
        waitForLoadingScreen();
        waitForElementToBeClickable(firmName).click();

        return new InstitutionPage(getDriver());
    }

    public String getAnalystNameFromList() {
        waitForAnyElementToAppear(analystName);
        return findVisibleElement(analystName).getText();
    }

    public ResearchPage viewMultipleAnaysts() {
        waitForElementToBeClickable(multipleAnalystDropdown).click();
        return this;
    }

    public ContactPage selectAnalystFromResearchList() {
        waitForElementToBeClickable(analystName).click();
        pause(1000L);

        return new ContactPage(getDriver());
    }

    public boolean sortByHeadline() {
        waitForLoadingScreen();
        // sorting by headline ascending
        findVisibleElement(researchHeadlineColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaUpSorted(findVisibleElements(researchHeadline))) {
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }
        // sorting by headline ascending
        findVisibleElement(researchHeadlineColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaDownSorted(findVisibleElements(researchHeadline))) {
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }
        return true;
    }

    public boolean sortByDate() {
        waitForLoadingScreen();
        // sorting by headline ascending
        findVisibleElement(researchDateColumnHeader).click();
        pause(300);
        if (!elementsAreDateUpSorted(findVisibleElements(researchDate))) {
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }
        // sorting by headline ascending
        findVisibleElement(researchDateColumnHeader).click();
        pause(300);
        if (!elementsAreDateDownSorted(findVisibleElements(researchDate))) {
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }
        return true;
    }

    public boolean sortByFirm() {
        waitForLoadingScreen();
        // sorting by headline ascending
        findElement(researchFirmColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaUpSorted(findVisibleElements(researchFirm))) {
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }
        // sorting by headline ascending
        findVisibleElement(researchFirmColumnHeader).click();
        pause(300);
        if (!elementsAreAlphaDownSorted(findVisibleElements(researchFirm))) {
            System.out.println("SORT ERROR: Names are not in ascending order.");
            return false;
        }
        return true;
    }

    public Calendar filterDate(Calendar calendar) {
        calendar.selectStartDate(startTimeSelector, previousMonthButton, selectedMonth, selectedDay);
        calendar.selectEndDate(endTimeSelector, previousMonthButton, selectedMonth, selectedDay);
        calendar.filter(dateFilterButton);
        pause(500L);
        // helps keep track of which days were selected
        calendar.print();
        return calendar;
    }

    public boolean sortByDateRange(Calendar calendar) {
        waitForLoadingScreen();
        // sorting the date by earliest to latest
        findVisibleElement(researchDateColumnHeader).click();
        pause(200L);
        boolean Sorted = true;
        if (!calendar.EarliestDateWithinRange(findVisibleElement(researchDate).getText())) {
            System.out.println("Earliest date in the table is earlier than the selected end time");
            Sorted = false;
        }

        // sorting the date by latest to earliest
        findVisibleElement(researchDateColumnHeader).click();
        pause(200L);
        if (!calendar.latestDateWithinRange(findVisibleElement(researchDate).getText())) {
            System.out.println("Latest date in the table is later than the selected end time");
            Sorted = false;
        }
        return Sorted;
    }

}

