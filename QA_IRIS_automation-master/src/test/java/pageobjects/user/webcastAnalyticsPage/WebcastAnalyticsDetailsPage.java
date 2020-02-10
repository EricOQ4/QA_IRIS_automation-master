package pageobjects.user.webcastAnalyticsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by andyp on 2017-05-10.
 */
public class WebcastAnalyticsDetailsPage extends WebcastAnalyticsPage{

    private final By webcastTitle = By.cssSelector("div.webcast-info > div.title");
    private final By registeredAttendees = By.cssSelector("div.value.registered");
    private final By totalAttendees = By.cssSelector("div.value.attendance");
    private final By onDemandAttendees = By.cssSelector("div.counter");

    public WebcastAnalyticsDetailsPage(WebDriver driver){
        super(driver);
    }

    public String getDetailsWebcastTitle() {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.visibilityOfElementLocated(webcastTitle));
        return findElement(webcastTitle).getText();
    }

    public String getDetailsRegisteredAttendees() {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.visibilityOfElementLocated(registeredAttendees));
        return findElement(registeredAttendees).getText();
    }

    public String getDetailsTotalAttendees() {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalAttendees));
        return findElement(totalAttendees).getText();
    }

    public String getDetailsOnDemandAttendees() {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.visibilityOfElementLocated(onDemandAttendees));
        return findElement(onDemandAttendees).getText();
    }
}
