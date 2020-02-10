package pageobjects.user.webAnalyticsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-08-16.
 */

public class WebAnalyticsPage extends AbstractPageObject{


    /**     HEADER       */

    //buttons

        private final By dayBtn = By.xpath("//div[@class='x-button x-button-no-icon x-layout-box-item x-stretched']/span[@class='x-button-label'][text()='Day']");
        private final By monthBtn = By.xpath("//div[@class='x-button x-button-no-icon x-layout-box-item x-stretched']/span[@class='x-button-label'][text()='Month']");
        private final By yearBtn = By.xpath("//div[@class='x-button x-button-no-icon x-layout-box-item x-stretched']/span[@class='x-button-label'][text()='Year']");
        private final By customBtn = By.xpath("//div[@class='x-button x-button-no-icon x-layout-box-item x-stretched']/span[@class='x-button-label'][text()='Custom']");

    //icons

        private final By sessionsIcon_= By.xpath("//span[@class='icon q4i-session-1pt']");
        private final By newSessionsIcon = By.xpath("//span[@class='icon q4i-new-session-1pt']");
        private final By usersIcon = By.xpath("icon q4i-contact-1pt");
        private final By newUsersIcon = By.xpath("//span[@class='icon q4i-new-user-1pt']");
        private final By pages_SessionsIcon = By.xpath("//span[@class='icon q4i-pages-session-1pt']");
        private final By bounceRateIcon = By.xpath("//span[@class='icon q4i-rate-1pt']");

    //data

        private final By sessionsData_ = By.xpath("//div[@class='item color-good']//div[@class='value'][../div[@class='label'][text()='Sessions']]");
        private final By newSessionsData  = By.xpath("//div[@class='item color-good']//div[@class='value'][../div[@class='label'][text()='New Sessions']]");
        private final By usersData  = By.xpath("//div[@class='item color-good']//div[@class='value'][../div[@class='label'][text()='Users']]");
        private final By newUsersData  = By.xpath("//div[@class='item color-good']//div[@class='value'][../div[@class='label'][text()='New Users']]");
        private final By pages_SessionsData  = By.xpath("//div[@class='item color-good']//div[@class='value'][../div[@class='label'][text()='Pages / Session']]");
        private final By bounceRateData  = By.xpath("//div[@class='item color-good']//div[@class='value'][../div[@class='label'][text()='Bounce Rate']]");
        private final By dateRange  = By.xpath("//div[@class='time-period']");

    /**     CUSTOM_DATE_RANGE   */
        private final By customDateRangeTitle = By.xpath("//div[@class='x-innerhtml'][text()='Custom Date Range']");
        private final By customStartDate = By.xpath("//input[@name='startDate']");
        private final By customEndDate = By.xpath("//input[@name='endDate']");
        private final By cancelBtn = By.xpath("//span[@class='x-button-label'][text()='Cancel']");
        private final By updateBtn = By.xpath(("//span[@class='x-button-label'][text()='UPDATE']"));

    /**     TRAFFIC_IMPACT     */

    //buttons

        //private final By sessionsBtn
        //private final By news_EventsBtn

    //data

    /**     DEVICE_TYPES     */

    //icons

        private final By desktopIcon = By.xpath("//i[@class='q4i-desktop-1pt']");
        private final By mobileIcon = By.xpath("//i[@class='q4i-mobile-1pt']");
        private final By tabletIcon = By.xpath("//i[@class='q4i-tablet-1pt']");

    //data

        private final By desktopData = By.xpath("//div[@class='devices']//div[@class='value'][../i[@class='q4i-desktop-1pt']]");
        private final By mobileData = By.xpath("//div[@class='devices']//div[@class='value'][../i[@class='q4i-mobile-1pt']]");
        private final By tabletData = By.xpath("//div[@class='devices']//div[@class='value'][../i[@class='q4i-tablet-1pt']]");

    /**     BREAKDOWN_SECTION     */
        private final By breakdownToggleBtn = By.xpath("//div[@id='ext-slider-3']");

        //Check for existance to see whether or not page is set to chart or table
        private final By setToCharts = By.xpath("//div[@class='x-container x-unsized x-toggle x-size-monitored x-paint-monitored x-toggle-off' and @id='ext-slider-3']");
        private final By setToTable = By.xpath("//div[@class='x-container x-unsized x-toggle x-size-monitored x-paint-monitored x-toggle-on' and @id='ext-slider-3']");

    /**     BREAKDOWN_CHARTS     */


    /**     BREAKDOWN_TABLES     */


    /**     LOCATIONS        */

        private final By usBtn = By.xpath("//span[@class='x-button-label'][text()='US']");
        private final By worldBtn = By.xpath("//span[@class='x-button-label'][text()='World']");



    public WebAnalyticsPage(WebDriver driver) {
        super(driver);
    }

    public void selectDayBtn(){
        waitForLoadingScreen();
        waitForElement(dayBtn);
        findElement(dayBtn).click();
    }

    public void selectMonthBtn(){
        waitForLoadingScreen();
        waitForElement(monthBtn);
        findElement(monthBtn).click();
    }

    public void selectYearBtn(){
        waitForLoadingScreen();
        waitForElement(yearBtn);
        findElement(yearBtn).click();
    }
    
    public void selectCustomBtn(){
        waitForLoadingScreen();
        waitForElement(customBtn);
        findElement(customBtn).click();
    }

    public void updateCustomDateRange(){
        waitForElement(customStartDate);

        findElement(customStartDate).click();
        findElement(customStartDate).sendKeys(Keys.ENTER);

        findElement(customEndDate).click();
        findElement(customEndDate).sendKeys(Keys.ENTER);

        findElement(updateBtn).click();
    }

    public String getDateRange(){
        waitForLoadingScreen();
        waitForElement(dateRange);

        return findElement(dateRange).getAttribute("innerText");
    }
}
