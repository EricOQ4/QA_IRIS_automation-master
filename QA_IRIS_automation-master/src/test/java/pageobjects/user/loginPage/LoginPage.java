package pageobjects.user.loginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.Page;

/*
 * Created by patrickp on 2016-08-03.
 */
/*
 * Updated by abbyl on 2019-03-01.
 */
public class LoginPage extends Page {


    // Switch to iframe
    private final By iframe = By.xpath("//iframe[contains(@id, 'sencha-viewport')]");

    // Log in form
    private final By emailField = By.id("email");
    private final By passwordField = By.id("password");
    private final By loginButton = By.cssSelector("#root > div > div.app-body > div > div.login-page_content > form > button");

    // Error modal
    private final By incorrectUsernamePasswordErrorModal = By.xpath("//div[contains(@class,'login-error')]");
  //  private final By okButtonInErrorModal = By.cssSelector(".x-button-no-icon > .x-button-label");

    //error tooltip
    private final By incorrectEmailErrorTooltip = By.xpath("//div[contains(@class,'tooltip--error--right')]");

    //empty field
    private final By emptyFieldError = By.xpath("//div[contains(@class,'formik-text-input formik-text-input--error formik-text-input--condensed')]");

    // Forgot password modal
    private final By forgotPasswordLink = By.xpath("//button[contains(text(),'Forgot Password?')]");
    private final By forgotPasswordModalTitle = By. xpath("//h2[@class='modal_title']");
    private final By cancelForgotPassword = By.xpath("//button[contains(text(),'Cancel')]");
    private final By forgotPasswordEmailField = By.xpath("//div[contains(@class,'modal_wrap')]//input[contains(@class,'formik-text-input_field')]");
    //private final By forgotPasswordEmailField = By.xpath("//input[contains(@class,'x-input-email')]");
    private final By submitForgottenEmail = By.xpath("//button[contains(text(),'Submit')]");

    // Invalid email modal (triggered by entering invalid email on password reset)
    private final By invalidEmailErrorModal= By.id("ext-messagebox-1");

    // Valid email confirmation modal
    private final By confirmationModal = By.xpath("//div[contains(@class,'modal_content')]//div[contains(@class,'message')]");
    private final By confirmationOkButton = By.xpath("//button[contains(text(),'OK')]");

    //Dashboard Welcome Note
    //private final By GetStartedBTN = By.xpath ("//span[@class='x-button-label'][text()='Get Started']");
    private final By GetStartedBTN = By.xpath("//button[contains(text(), 'Get Started')]");

    // Longer WebDriverWait for initial loading screen
    WebDriverWait longWait = new WebDriverWait(driver, 20);

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public Dashboard loginUser() {
        longWait.until(ExpectedConditions.elementToBeClickable(emailField)).clear();
        findElement(passwordField).clear();
        findElement(emailField).sendKeys("test@q4inc.com");
        findElement(passwordField).sendKeys("q4pass1234!");
        waitForElementToBeClickable(loginButton).click();
       // waitForDashboardToLoad();

        pause(1000L);

//        waitForElementToBeClickable(GetStartedBTN).click();
        waitForLoadingScreen();

        return new Dashboard(getDriver());
    }

    public Dashboard loginUserCloseWelcomeNote() {
        longWait.until(ExpectedConditions.elementToBeClickable(emailField)).clear();
        findElement(passwordField).clear();
        findElement(emailField).sendKeys("test@q4inc.com");
        findElement(passwordField).sendKeys("q4pass1234!");
        waitForElementToBeClickable(loginButton).click();

        pause(1000L);

        waitForElementToBeClickable(GetStartedBTN).click();
        waitForLoadingScreen();
        return new Dashboard(getDriver());
    }

    public Dashboard loginUnsubscribedUser() {
        longWait.until(ExpectedConditions.elementToBeClickable(emailField)).clear();
        findElement(passwordField).clear();
        findElement(emailField).sendKeys("test@q4websystems.com");
        findElement(passwordField).sendKeys("q4pass1234!");
        waitForElementToBeClickable(loginButton).click();
        // waitForDashboardToLoad();
        pause(1000L);

        return new Dashboard(getDriver());
    }

    public Dashboard customLoginUser(String email, String password) {
        longWait.until(ExpectedConditions.elementToBeClickable(emailField)).clear();
        findElement(passwordField).clear();
        findElement(emailField).sendKeys(email);
        findElement(passwordField).sendKeys(password);
        waitForElementToBeClickable(loginButton).click();
        return new Dashboard(getDriver());
    }

    public String getErrorMessage() {
        waitForElementToAppear(incorrectUsernamePasswordErrorModal);
        return findElement(incorrectUsernamePasswordErrorModal).getText();
    }

    public String getErrorTooltip() {
        waitForElementToAppear(incorrectEmailErrorTooltip);
        return findElement(incorrectEmailErrorTooltip).getText();
    }

    public boolean getErrorEmptyField(){
        waitForElementToAppear(emptyFieldError);
        if(getDriver().findElement(emptyFieldError).isDisplayed()){

            return true;
        }

        return false;
    }

//    public LoginPage dismissErrorModal() {
//        waitForElementToAppear(incorrectUsernamePasswordErrorModal);
//        findElement(okButtonInErrorModal).click();
//
//        return this;
//    }

    public LoginPage forgotPassword() {
        waitForLoadingScreen();
        waitForElementToBeClickable(forgotPasswordLink).click();

        return this;
    }

    public String getForgotPasswordModalTitle() {

        waitForElementToAppear(forgotPasswordModalTitle);
        return findElement(forgotPasswordModalTitle).getText();
    }

    public LoginPage dismissForgotPasswordModal() {

        waitForElementToAppear(cancelForgotPassword);
        findElement(cancelForgotPassword).click();

        return this;
    }

    public LoginPage enterForgotPasswordEmail(String email) {
        //waitForLoadingScreen();
        waitForElementToAppear(forgotPasswordEmailField);
        findVisibleElement(forgotPasswordEmailField).click();

        findVisibleElement(forgotPasswordEmailField).sendKeys(email);
        findVisibleElement(submitForgottenEmail).click();

        return  this;
    }

    public boolean getInvalidEmailError(){
        try {
            if (getDriver().findElement(feedbackMsgField).isDisplayed()) {
                return false;
            }
            else{
                return true;
            }
        }

        catch(Exception e){
            return true;

        }
    }
//    public String getInvalidEmailError() {
//        waitForElementToAppear(invalidEmailErrorModal);
//        return findElement(invalidEmailErrorModal).getText();
//    }

    public String getConfirmationText() {
        waitForElementToAppear(confirmationModal);
        return findElement(confirmationModal).getText();
    }

    public LoginPage dismissConfirmationModal() {
        waitForElementToAppear(confirmationOkButton);
        findElement(confirmationOkButton).click();

        return this;
    }

    public void switchToiframe(){
        WebElement _iframe = findElement(iframe);
        driver.switchTo().frame(_iframe);
        pause(500L);
    }

    public void leaveiframe(){
        driver.switchTo().defaultContent();
        pause(500L);
    }

}
