package pageobjects.admin.homePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-09-20.
 */
public class HomePage extends AbstractPageObject {
    private final By pageTitle = By.cssSelector(".main-container");
    private final By errorMessage = By.cssSelector("#a0-lock.a0-theme-default .a0-panel .a0-error");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getAdminPageTitle() {
        wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle));
        return findElement(pageTitle).getText();
    }

    public String getErrorMessage() {
        pause(2000L);
        wait.until(ExpectedConditions.presenceOfElementLocated(errorMessage));
        return findElement(errorMessage).getText();
    }
}
