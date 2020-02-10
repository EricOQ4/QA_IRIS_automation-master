package pageobjects.user.sentimentPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by kelvint on 11/1/16.
 */
public class SentimentPage extends AbstractPageObject {

    private final By nlg = By.cssSelector(".x-landscape .company-page .company-slide .natural-lang");

    public SentimentPage(WebDriver driver) {
        super(driver);
    }


    public String getNlgText() {
        waitForLoadingScreen();
        return findVisibleElement(nlg).getText();
    }

    public boolean verifyTextIsPresent() {
        waitForLoadingScreen();
        String nglText = getNlgText();
        if (nglText.isEmpty()) {
            {
                return false;
            }
        } else {
            return true;
        }
    }
}