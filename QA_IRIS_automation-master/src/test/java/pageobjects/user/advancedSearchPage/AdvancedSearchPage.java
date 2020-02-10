package pageobjects.user.advancedSearchPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.user.securityPage.SecurityOverviewPage;

/**
 * Created by patrickp on 2016-09-13.
 */
public class AdvancedSearchPage extends AbstractPageObject {
    private final By searchResults = By.cssSelector(".advanced-search-results");
    private final By searchField = By.cssSelector(".advanced-search-header .search-field .x-input-search");
    private final By searchButton = By.xpath("//span[@class='x-button-label'][text()='Search'][1]");
    private final By transcriptsTab = By.xpath("//div[contains(@class,'x-button')][span[contains(@class,'q4i-transcripts-2pt')]]");
    private final By researchTab = By.cssSelector(".advanced-search .search-bar .search-tabs .x-button:last-of-type, .advanced-search .search-bar .search-tabs .x-button:last-child");
    private final By firstSearchResultDetails = By.className("name");
    private final By firstSearchResult = By.cssSelector(".advanced-search-results .advanced-search-results-item");
    private final By securityTab = By.cssSelector(".advanced-search .search-bar .search-tabs .x-button:last-of-type, .advanced-search .search-bar .search-tabs .x-button:nth-child(2)");
    private final By institutionTab = By.cssSelector(".advanced-search .search-bar .search-tabs .x-button:last-of-type, .advanced-search .search-bar .search-tabs .x-button:nth-child(3)");
    private final By fundTab = By.cssSelector(".advanced-search .search-bar .search-tabs .x-button:last-of-type, .advanced-search .search-bar .search-tabs .x-button:nth-child(4)");
    private final By contactTab = By.cssSelector(".advanced-search .search-bar .search-tabs .x-button:last-of-type, .advanced-search .search-bar .search-tabs .x-button:nth-child(5)");
    private final By activityTab = By.cssSelector(".advanced-search .search-bar .search-tabs .x-button:last-of-type, .advanced-search .search-bar .search-tabs .x-button:nth-child(7)");
    private final By contactName = By.className("name");

    public AdvancedSearchPage(WebDriver driver) {
        super(driver);
    }

    public String getAdvancedSearchResults() {
        waitForLoadingScreen();
        waitForElement(searchResults);
        waitForElementToRest(searchResults, 500L);
        return findElement(searchResults).getText();
    }

    public String getContactNameFromSearchResults() {
        waitForLoadingScreen();
        waitForElement(contactName);
        return findElement(contactName).getText();
    }

    public AdvancedSearchPage advancedSearchFor(String entity) {
        waitForLoadingScreen();
        findElement(searchField).click();
        findElement(searchField).sendKeys(entity);
        findElement(searchButton).click();
        waitForLoadingScreen();

        return this;
    }

    public AdvancedSearchPage selectTranscriptsTab() {
        findElement(transcriptsTab).click();

        return this;
    }

    public AdvancedSearchPage selectResearchTab() {
        findElement(researchTab).click();
        waitForLoadingScreen();

        return this;
    }

    public AdvancedSearchPage selectSecurityTab() {
        findElement(securityTab).click();
        waitForLoadingScreen();

        return this;
    }

    public AdvancedSearchPage selectInstitutionTab() {
        findElement(institutionTab).click();
        waitForLoadingScreen();

        return this;
    }

    public AdvancedSearchPage selectFundTab() {
        findElement(fundTab).click();
        waitForLoadingScreen();

        return this;
    }

    public AdvancedSearchPage selectContactTab() {
        findElement(contactTab).click();
        waitForLoadingScreen();

        return this;
    }

    public AdvancedSearchPage selectActivityTab() {
        findElement(activityTab).click();
        waitForLoadingScreen();

        return this;
    }

    public String getFirstSearchResult() {
        return findElement(firstSearchResultDetails).getText();
    }

    public SecurityOverviewPage selectFirstSearchResult() {
        findElement(firstSearchResult).click();

        return new SecurityOverviewPage(getDriver());
    }

    public String getTagSearchResults() {
        return findElement(firstSearchResultDetails).getText();
    }
}
