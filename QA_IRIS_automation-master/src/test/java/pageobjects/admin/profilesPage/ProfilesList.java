//package pageobjects.admin.profilesPage;
//
//import org.apache.commons.lang.RandomStringUtils;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import pageobjects.AbstractPageObject;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//
///**
// * Created by patrickp on 2016-10-11.
// */
//public class ProfilesList extends AbstractPageObject {
//    private final By profileSearchField = By.cssSelector(".search.dark .search-input");
//    private final By profileList = By.cssSelector(".ui-datatable table");
//
//    private final By addProfileButton = By.cssSelector(".page-header .action-buttons .add");
//
//    //Add profile modal
//    private final By emailField = By.name("user");
//    private final By passwordField = By.name("password");
//    private final By passwordConfirmField = By.name("confirmPassword");
//    private final By organizationField = By.cssSelector("body > q4-app > div > div > q4-profile > p-dialog > div > div.ui-dialog-content.ui-widget-content > q4-profile-create > form > p-autocomplete > span > input");
//    private final By firstNameField = By.name("firstName");
//    private final By lastNameField = By.name("lastName");
//    private final By titleField = By.name("title");
//    private final By phoneField = By.name("phone");
//    private final By saveButton = By.cssSelector("body > q4-app > div > div > q4-profile > p-dialog > div > div.ui-dialog-content.ui-widget-content > q4-profile-create > form > div > button.button.button-yellow");
//    private final By firstSearchResult = By.cssSelector("body > q4-app > div > div > q4-profile > p-dialog > div > div.ui-dialog-content.ui-widget-content > q4-profile-create > form > p-autocomplete > span > div > ul > li");
//
//    // Profile List
//    private final By firstProfileInList = By.xpath("//tr[1]/td[1]");
//    public ProfilesList(WebDriver driver) {
//        super(driver);
//    }
//
//    public ProfilesList searchForProfile(String username) {
//        findElement(profileSearchField).sendKeys(username);
//        waitForLoadingScreen();
//
//        return this;
//    }
//
//    public String getProfileList() {
//        waitForLoadingScreen();
//        return findElement(profileList).getText();
//    }
//
//    public ProfilesList addNewProfile(String email, String password, String organization, String firstName, String lastName, String title, String phone) {
//        waitForLoadingScreen();
//        findElement(addProfileButton).click();
//        findElement(emailField).sendKeys(email);
//        findElement(passwordField).sendKeys(password);
//        findElement(passwordConfirmField).sendKeys(password);
//        pause(500L);
//        findElement(organizationField).click();
//        findElement(organizationField).sendKeys(organization);
//        findElement(firstSearchResult).click();
//        findElement(firstNameField).sendKeys(firstName);
//        findElement(lastNameField).sendKeys(lastName);
//        findElement(titleField).sendKeys(title);
//        findElement(phoneField).sendKeys(phone);
//        findElement(saveButton).click();
//
//        return this;
//    }
//
//    public ProfileDetails selectFirstProfileInList() {
//        waitForLoadingScreen();
//        waitForElementToAppear(firstProfileInList);
//        findElement(firstProfileInList).click();
//
//        return new ProfileDetails(getDriver());
//    }
//
//    public boolean bulkAddUser (Boolean researchToggle, Boolean estimatesToggle, int profilesPerCompany, String filename) {
//        int counter = 0;
//        try {
//            File file = new File(System.getProperty("user.dir") + "/src/test/java/pageobjects/admin/profilesPage/" + filename);
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String ticker;
//            while((ticker = bufferedReader.readLine()) != null) {
//                for(int ii=0; ii<profilesPerCompany; ii++) {
//                    String email = "*" + counter++ + ticker + "-" + ii + "-" + RandomStringUtils.randomAlphanumeric(5) + "@q4inc.com";
//                    String password = "testing123!";
//                    String organization = ticker;
//                    String firstName = "*" + ticker;
//                    String lastName = "Profile" + ii + "-" + RandomStringUtils.randomAlphanumeric(5);
//                    String title = "Tester";
//                    String phone = "123-456-7890";
//                    //addNewProfile(email, password, organization, firstName, lastName, title, phone);
//                    System.out.println(email + " " + password + " " + organization + " " + firstName + " " + lastName + " " + title + " " + phone);
//                }
//            }
//            fileReader.close();
//            System.out.println("Profiles added!");
//            return true;
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}


package pageobjects.admin.profilesPage;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.admin.loginPage.AdminLoginPage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by patrickp on 2016-10-11.
 */
public class ProfilesList extends AbstractPageObject {
    private final By profileSearchField = By.cssSelector(".search.dark .search-input");
    private final By profileList = By.cssSelector(".ui-datatable table");
    private final By addProfileButton = By.cssSelector(".page-header .action-buttons .add");
    //Add profile modal
    private final By emailField = By.name("user");
    private final By passwordField = By.name("password");
    private final By passwordConfirmField = By.name("confirmPassword");
    private final By organizationField = By.xpath("//input[@placeholder='Company Symbol']");
    private final By firstNameField = By.name("firstName");
    private final By lastNameField = By.name("lastName");
    private final By titleField = By.name("title");
    private final By phoneField = By.name("phone");
    private final By saveButton = By.xpath("//button[text()='Save']");
    private final By firstSearchResult = By.xpath("(//li[contains(@class,'list-item')])[1]");
    private final By accountDetailsField = By.xpath("//label[contains(@class,'dropdown-label')][text()='Desktop Account Type']");
    private final By successMessage = By.xpath("//p[contains(@class, 'status-message-body')][contains(text(),'Profile was created successfully!')]");
    private final By okayButton = By.xpath("//button[text()='Okay']");
    private final By alertsTab = By.xpath("//span[text()='Alerts']");
    private final By subscriptionsTab = By.xpath("//span[text()='Subscriptions']");
    private final By q4DesktopToggle = By.xpath("//span[text()='Q4 Desktop']/parent::td/following-sibling::td//div");
    private final By q4TouchToggle = By.xpath("//span[text()='Q4 Touch']/parent::td/following-sibling::td//div");
    private final By estimatesToggle = By.xpath("//span[text()='Estimates']/ancestor::td/following-sibling::td//div");
    private final By researchToggle = By.xpath("//span[text()='Research']/ancestor::td/following-sibling::td//div");
    private final By confirmButton = By.xpath("//button[text()='Confirm']");
    // Profile List
    private final By firstProfileInList = By.xpath("//tr[1]/td[1]");
    public ProfilesList(WebDriver driver) {
        super(driver);
    }
    public ProfilesList searchForProfile(String username) {
        findElement(profileSearchField).sendKeys(username);
        waitForLoadingScreen();
        return this;
    }
    public String getProfileList() {
        waitForLoadingScreen();
        return findElement(profileList).getText();
    }
    public ProfilesList addNewProfile(String email, String password, String organization, String firstName, String lastName, String title, String phone, String accountType) {
        waitForLoadingScreen();
        findElement(addProfileButton).click();
        findElement(emailField).sendKeys(email);
        findElement(passwordField).sendKeys(password);
        findElement(passwordConfirmField).sendKeys(password);
        findElement(accountDetailsField).click();
        findElement(By.xpath("//span[text()='" + accountType + "']")).click();
        pause(500L);
        findElement(organizationField).click();
        findElement(organizationField).sendKeys(organization);
        findElement(firstSearchResult).click();
        findElement(firstNameField).sendKeys(firstName);
        findElement(lastNameField).sendKeys(lastName);
        findElement(titleField).sendKeys(title);
        findElement(phoneField).sendKeys(phone);
        findElement(saveButton).click();
        waitForElement(successMessage);
        findElement(okayButton).click();
        return this;
    }
    public ProfileDetails selectFirstProfileInList() {
        waitForLoadingScreen();
        waitForElementToAppear(firstProfileInList);
        findElement(firstProfileInList).click();
        return new ProfileDetails(getDriver());
    }
    public void subscribeAlerts(){
        //At user detail page, subscribe to Research/Estimates Alert
        waitForElementToBeClickable(q4DesktopToggle);
        findVisibleElement(q4DesktopToggle).click();
        waitForElementToBeClickable(confirmButton);
        findVisibleElement(confirmButton).click();
        findVisibleElement(q4TouchToggle).click();
        waitForElementToBeClickable(confirmButton);
        findVisibleElement(confirmButton).click();
        findElement(subscriptionsTab).click();
        findVisibleElement(estimatesToggle).click();
        waitForElementToBeClickable(confirmButton);
        findVisibleElement(confirmButton).click();
        findVisibleElement(researchToggle).click();
        waitForElementToBeClickable(confirmButton);
        findVisibleElement(confirmButton).click();

        findElement(By.xpath("//span[text()='External Subscriptions']")).click();
        findVisibleElement(By.xpath("//button[contains(@class,'add')]")).click();
        findVisibleElement(By.xpath("//input[contains(@id,'factset-id')]")).sendKeys("704928");
        findVisibleElement(By.xpath("//input[contains(@id,'username')]")).sendKeys("CORP3_MASTER");
        findVisibleElement(By.xpath("//button[text()='Add']")).click();

        waitForLoadingScreen();
        findElement(alertsTab).click();
        findVisibleElement(estimatesToggle).click();
        waitForElementToBeClickable(confirmButton);
        findVisibleElement(confirmButton).click();
        findVisibleElement(researchToggle).click();
        waitForElementToBeClickable(confirmButton);
        findVisibleElement(confirmButton).click();
    }
    public boolean bulkAddUser (Boolean alertsToggle, int profilesPerCompany, String filename) {
        int counter = 196;
        try {
            File file = new File(System.getProperty("user.dir") + "/src/test/java/pageobjects/admin/profilesPage/" + filename);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String ticker;
            while((ticker = bufferedReader.readLine()) != null) {
                for(int ii=0; ii<profilesPerCompany; ii++) {
                    String random = RandomStringUtils.randomAlphabetic(5);
                    String email = counter++ + ticker + "-" + ii + "-" + random + "@q4inc.com";
                    //String email = counter++ + ticker + ii + random + "@q4inc.com";
                    String password = "testing123!";
                    String organization = ticker;
                    //String firstName = "*" + ticker;
                    String firstName = ticker;
                    String lastName = "Profile" + "-" + random;
                    //String lastName = "Profile";
                    String title = "Tester";
                    String phone = "1234567890";
                    String accountType = "Q4 Employee";
                    System.out.println("Adding: " + email + " " + password + " " + organization + " " + firstName + " " + lastName + " " + title + " " + phone);
                    addNewProfile(email, password, organization, firstName, lastName, title, phone, accountType);
                    if (alertsToggle) {
                        subscribeAlerts();
                    }
                    new AdminLoginPage(driver).navigateToProfilesPageFlash();
                }
            }
            fileReader.close();
            System.out.println("Profiles added!");
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}