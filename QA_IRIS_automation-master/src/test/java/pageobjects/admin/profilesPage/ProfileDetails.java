package pageobjects.admin.profilesPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-10-11.
 */
public class ProfileDetails extends AbstractPageObject {
    private final By editProfileButton = By.cssSelector(".page-header .action-buttons .edit");
    private final By firstNameField = By.name("firstName");
    private final By saveButton = By.xpath("//button[contains(text(), 'Save')]");
    private final By loadingSpinner = By.className("outer-spinner-container");
    private final By researchToggle = By.xpath("//tr[contains(@class,'ui-widget-content') and .//span[contains(text(),'Research')]]//p-inputswitch[contains(@styleclass,'q4-toggle-button')]");
    private final By estimatesToggle = By.xpath("//tr[contains(@class,'ui-widget-content') and .//span[contains(text(),'Estimates')]]//p-inputswitch[contains(@styleclass,'q4-toggle-button')]");
    private final By expectedRangesToggle = By.xpath("//tr[contains(@class,'ui-widget-content') and .//span[contains(text(),'Expected Ranges')]]//p-inputswitch[contains(@styleclass,'q4-toggle-button')]");
    private final By sentimentToggle = By.xpath("//tr[contains(@class,'ui-widget-content') and .//span[contains(text(),'Sentiment')]]//p-inputswitch[contains(@styleclass,'q4-toggle-button')]");
    private final By volatilityToggle = By.xpath("//tr[contains(@class,'ui-widget-content') and .//span[contains(text(),'Volatility')]]//p-inputswitch[contains(@styleclass,'q4-toggle-button')]");
    private final By activismToggle = By.xpath("//tr[contains(@class,'ui-widget-content') and .//span[contains(text(),'Activism')]]//p-inputswitch[contains(@styleclass,'q4-toggle-button')]");
    private final By eventsToggle = By.xpath("//tr[contains(@class,'ui-widget-content') and .//span[contains(text(),'Events')]]//p-inputswitch[contains(@styleclass,'q4-toggle-button')]");
    private final By webcastToggle = By.xpath("//tr[contains(@class,'ui-widget-content') and .//span[contains(text(),'Webcast')]]//p-inputswitch[contains(@styleclass,'q4-toggle-button')]");
    private final By websiteToggle = By.xpath("//tr[contains(@class,'ui-widget-content') and .//span[contains(text(),'Website')]]//p-inputswitch[contains(@styleclass,'q4-toggle-button')]");
    private final By tradingAnalyticsToggle = By.xpath("//tr[contains(@class,'ui-widget-content') and .//span[contains(text(),'Trading Analytics')]]//p-inputswitch[contains(@styleclass,'q4-toggle-button')]");
    private final By relativePerformaceToggle = By.xpath("//tr[contains(@class,'ui-widget-content') and .//span[contains(text(),'Relative Performance')]]//p-inputswitch[contains(@styleclass,'q4-toggle-button')]");
    private final By surveillanceToggle = By.xpath("//tr[contains(@class,'ui-widget-content') and .//span[contains(text(),'Surveillance')]]//p-inputswitch[contains(@styleclass,'q4-toggle-button')]");


    public ProfileDetails(WebDriver driver) {
        super(driver);
    }

    public ProfileDetails editProfileFirstName(String firstName) {
        waitForLoadingScreen();
        findElement(editProfileButton).click();
        findElement(firstNameField).clear();
        findElement(firstNameField).sendKeys(firstName);
        waitForElementToBeClickable(saveButton).click();
        waitForLoadingScreen();

        return this;
    }

    public ProfileDetails disableEstimates() {
        toggleOff(estimatesToggle);

        return this;
    }

    public ProfileDetails enableResearch(){
        toggleOn(researchToggle);
        return this;
    }

    public ProfileDetails enabledEstimates(){
        toggleOn(estimatesToggle);
        return this;
    }

    public ProfileDetails enabledExpectedRanges() {
        toggleOn(expectedRangesToggle);

        return this;
    }

    public ProfileDetails enableSentiment() {
        toggleOn(sentimentToggle);

        return this;
    }

    public ProfileDetails enabledVolatility() {
        toggleOn(volatilityToggle);

        return this;
    }

    public ProfileDetails enabledActivism() {
        toggleOn(activismToggle);

        return this;
    }

    public ProfileDetails enabledEvents() {
        toggleOn(eventsToggle);

        return this;
    }

    public ProfileDetails enableWebcast() {
        toggleOn(webcastToggle);

        return this;
    }

    public ProfileDetails enabledWebsite() {
        toggleOn(websiteToggle);

        return this;
    }

    public ProfileDetails enabledTradingAnalytics() {
        toggleOn(tradingAnalyticsToggle);

        return this;
    }

    public ProfileDetails enableRelativePerformance() {
        toggleOn(relativePerformaceToggle);

        return this;
    }

    public ProfileDetails enableSurveillance() {
        toggleOn(surveillanceToggle);
        return this;
    }
}
