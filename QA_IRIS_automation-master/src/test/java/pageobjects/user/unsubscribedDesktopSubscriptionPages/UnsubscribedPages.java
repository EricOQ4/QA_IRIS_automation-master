package pageobjects.user.unsubscribedDesktopSubscriptionPages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import pageobjects.AbstractPageObject;

/**
 * Created by dannyl on 2017-08-11.
 */
public class UnsubscribedPages extends AbstractPageObject {

    private final By unsubscribedMessage = By.xpath("//h1[contains(text(), 'Looks like you have')]");

    public UnsubscribedPages(WebDriver driver){super(driver);}

    public boolean checkForUnsubscribedMessage() {
        waitForLoadingScreen();
        try {
            findVisibleElement(unsubscribedMessage);
        } catch (ElementNotVisibleException e) {
            return false;
        }
        catch (WebDriverException e){
            return false;
        }
        return true;
    }


}
