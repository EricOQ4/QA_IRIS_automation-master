package pageobjects.admin.releaseNotesPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by dannyl on 2017-08-04.
 */
public class ReleaseNoteDetails extends AbstractPageObject {

    private final By releaseNoteTitle = By.xpath("//div[contains(@class, 'detail-column')]/span");
    private final By overviewDetails = By.xpath("//div[contains(@class, 'html')]");
    private final By versionDetails = By.xpath("//div[contains(@class, 'details')]/h2");
    private final By releaseDetails = By.xpath("//div[contains(@class, 'subtitle')]");

    public ReleaseNoteDetails (WebDriver driver) {super(driver);}

    public String getTitle(){
        waitForLoadingScreen();
        return waitForElementToBeClickable(releaseNoteTitle).getText();
    }

    public String getOverview(){
        waitForLoadingScreen();
        return waitForElementToBeClickable(overviewDetails).getText();
    }

    public String getVersion(){
        waitForLoadingScreen();
        return waitForElementToBeClickable(versionDetails).getText();
    }

    public String getReleaseDate(){
        waitForLoadingScreen();
        return waitForElementToBeClickable(releaseDetails).getText();
    }

}
