package pageobjects.user.webcastAnalyticsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrickp on 2016-08-16.
 */
public class WebcastAnalyticsPage extends AbstractPageObject {

    private final By webcastAnalyticsHeader = By.xpath("//h1[contains(string(),'Webcast Analytics')]");

    private final By firstWebcastOnTheList = By.cssSelector(".row");
    private final By firstWebcastOnTheListTitle = By.cssSelector("div > div.column.flex > div > div.title");
    private final By firstWebcastOnTheListRegistrants = By.cssSelector("div > div:nth-child(2) > span.counter");
    private final By firstWebcastOnTheListLiveAttendees = By.cssSelector("div > div:nth-child(3) > span.counter");
    private final By firstWebcastOnTheListOnDemandAttendees = By.cssSelector("div > div:nth-child(4) > span.counter");
    private final By firstWebcastOnTheListTotalAttendees = By.cssSelector("div > div:nth-child(5) > span.counter");
    private final By detailsHeader = By.xpath("//div[contains(@class, 'x-unsized x-button x-button-no-icon column flex x-text-align-left')]");
    private final By registrantsHeader = By.xpath("//div[contains(@class, 'x-unsized x-button x-button-no-icon column medium centered')][1]");
    private final By liveAttendeesHeader = By.xpath("//div[contains(@class, 'x-unsized x-button x-button-no-icon column medium centered')][2]");
    private final By onDemandAttendeesHeader = By.xpath("//div[contains(@class, 'x-unsized x-button x-button-no-icon column medium centered')][3]");
    private final By totalAttendeesHeader = By.xpath("//div[contains(@class, 'x-unsized x-button x-button-no-icon column medium centered')][4]");

    public WebcastAnalyticsPage(WebDriver driver) {
        super(driver);
    }

    private List<WebElement> returnTableRows (){
        List<WebElement> rowList = findVisibleElements(By.xpath("//div[contains(@class, 'x-dataview-item webcast-item')]"));
        ArrayList<WebElement> tableRowsList = new ArrayList<>(rowList);
        return tableRowsList;

    }

    public WebcastAnalyticsDetailsPage selectFirstWebcast(){
        waitForLoadingScreen();
        findElement(firstWebcastOnTheList).click();

        return new WebcastAnalyticsDetailsPage(getDriver());
    }

    public String getWebcastTitle(){
        waitForLoadingScreen();
        return findElement(firstWebcastOnTheListTitle).getText();
    }

    public String getWebcastRegistrants(){
        waitForLoadingScreen();
        return findElement(firstWebcastOnTheListRegistrants).getText();
    }

    public String getWebcastLiveAttendees(){
        waitForLoadingScreen();
        return findElement(firstWebcastOnTheListLiveAttendees).getText();
    }

    public String getWebcastOnDemandAttendees(){
        waitForLoadingScreen();
        return findElement(firstWebcastOnTheListOnDemandAttendees).getText();
    }

    public String getWebcastTotalAttendees(){
        waitForLoadingScreen();
        return findElement(firstWebcastOnTheListTotalAttendees).getText();
    }

    public boolean isColumnAscending(By column){
        return findElement(column).getAttribute("class").contains("asc");

    }

    public boolean canSortByDetails(){
        waitForElementToBeClickable(detailsHeader).click();
        ArrayList<WebElement> titles = new ArrayList<>();
        List<WebElement> rows = returnTableRows();
        for (WebElement i : rows){
            titles.add(i.findElement(By.className("title")));
        }
        if (isColumnAscending(detailsHeader)){
            return elementsAreAlphaUpSortedIgnoreCase(titles);
        }
        else
        {
            return elementsAreAlphaDownSortedIgnoreCase(titles);
        }
    }

    public boolean canSortByRegistrants(){
        waitForElementToBeClickable(registrantsHeader).click();
        ArrayList<WebElement> registrants = new ArrayList<>();
        List<WebElement> rows = returnTableRows();
        for (WebElement i : rows){
            registrants.add(i.findElement(By.xpath("//div[contains(@class, 'column medium centered')][1]/span[contains(@class, 'counter')]")));
        }
        if (isColumnAscending(registrantsHeader)){
            return elementsAreNumUpSorted(registrants);
        }
        else
        {
            return elementsAreAlphaDownSortedIgnoreCase(registrants);
        }
    }

    public boolean canSortByLiveAttendees(){
        waitForElementToBeClickable(liveAttendeesHeader).click();
        ArrayList<WebElement> liveAttendees = new ArrayList<>();
        List<WebElement> rows = returnTableRows();
        for (WebElement i : rows){
            liveAttendees.add(i.findElement(By.xpath("//div[contains(@class, 'column medium centered')][2]/span[contains(@class, 'counter')]")));
        }
        if (isColumnAscending(liveAttendeesHeader)){
            return elementsAreNumUpSorted(liveAttendees);
        }
        else
        {
            return elementsAreNumUpSorted(liveAttendees);
        }
    }

    public boolean canSortByOnDemandAttendees(){
        waitForElementToBeClickable(onDemandAttendeesHeader).click();
        ArrayList<WebElement> onDemandAttendees = new ArrayList<>();
        List<WebElement> rows = returnTableRows();
        for (WebElement i : rows){
            onDemandAttendees.add(i.findElement(By.xpath("//div[contains(@class, 'column medium centered')][3]/span[contains(@class, 'counter')]")));
        }
        if (isColumnAscending(onDemandAttendeesHeader)){
            return elementsAreNumUpSorted(onDemandAttendees);
        }
        else
        {
            return elementsAreNumUpSorted(onDemandAttendees);
        }
    }

    public boolean canSortByTotalAttendees(){
        waitForElementToBeClickable(totalAttendeesHeader).click();
        ArrayList<WebElement> totalAttendees = new ArrayList<>();
        List<WebElement> rows = returnTableRows();
        for (WebElement i : rows){
            totalAttendees.add(i.findElement(By.xpath("//div[contains(@class, 'column medium centered')][4]/span[contains(@class, 'counter')]")));
        }
        if (isColumnAscending(totalAttendeesHeader)){
            return elementsAreNumUpSorted(totalAttendees);
        }
        else
        {
            return elementsAreNumUpSorted(totalAttendees);
        }
    }


}
