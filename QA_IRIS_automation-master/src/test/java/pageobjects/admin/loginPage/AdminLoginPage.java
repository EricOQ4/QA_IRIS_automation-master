package pageobjects.admin.loginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-09-16.
 */
public class AdminLoginPage extends AbstractPageObject {
    private final By loginButton = By.cssSelector("#login-page > div > button.login-button");
    private final By googleLoginButton = By.className("auth0-lock-social-button-text");
    private final By enterEmail = By.id("identifierId");
    private final By enterPassword = By.name("password");
    private final By submit = By.cssSelector("#passwordNext ");
    private final By nextButton = By.cssSelector("#identifierNext");
    private final By useAnotherAccount = By.id("identifierLink");

    public AdminLoginPage(WebDriver driver) {
        super(driver);
    }

    public AdminLoginPage customLoginAdmin(String email, String password) {
        pause(5000L);
        // Store the current window handle
        String winHandleBefore = driver.getWindowHandle();

        findElement(loginButton).click();
        findElement(googleLoginButton).click();

        // Perform the click operation that opens new window

        // Switch to new window opened
        windowDidLoad("Sign in - Google Accounts");
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        // Perform the actions on new window

      try{
            findElement(enterEmail);
      }
      catch(Exception e){
            findElement(useAnotherAccount).click();
      }
        findElement(enterEmail).sendKeys(email);
        findElement(nextButton).click();
        waitForElementToAppear(enterPassword);
        findElement(enterPassword).sendKeys(password);
        waitForElementToAppear(submit);
        findElement(submit).click();
        pause(2000L);
        //String mainWindowTitle = "";
        // Switch back to original browser (first window)
        //windowDidLoad(mainWindowTitle);
        driver.switchTo().window(winHandleBefore);
        getSessionVariables();

        return this;
    }

    public AdminLoginPage loginAdmin() {
        // Store the current window handle
        String winHandleBefore = driver.getWindowHandle();

        // Perform the click operation that opens new window
        findElement(loginButton).click();
        waitForElementToAppear(googleLoginButton);
        findElement(googleLoginButton).click();

        // Switch to new window opened
        windowDidLoad("Sign in - Google Accounts");
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        // Perform the actions on new window
        findElement(enterEmail).sendKeys("test@q4inc.com");
        findElement(nextButton).click();
        waitForElementToAppear(enterPassword);
        findElement(enterPassword).sendKeys("testing!");
        waitForElementToAppear(submit);
        findElement(submit).click();
        pause(2000L);
        // Switch back to original browser (first window)
        driver.switchTo().window(winHandleBefore);
        getSessionVariables();
        return this;
    }

    public boolean onLoginPage(){
        waitForLoadingScreen();
        WebDriverWait longWait = new WebDriverWait(driver,15);
        try{
            longWait.until(ExpectedConditions.elementToBeClickable(loginButton));
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    private void getSessionVariables(){
        pause(5000L);
        getProfile();
        getTokenId();
    }

}
