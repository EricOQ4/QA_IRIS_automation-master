package specs.user.WebAnalytics;

import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.webAnalyticsPage.WebAnalyticsPage;
import specs.AbstractSpec;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kelvint on 11/1/16.
 */
public class WebAnalytics extends AbstractSpec {

    @Before
    public void setUp(){
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectWebAnalyticsFromSideNav();
    }

    @Test
    public void checkCustomDateRange() {
        //Sets custom date to today's date for both start and end dates, then compare
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        String date = String.valueOf(dateFormat.format(new Date()));
        String sDate = date + " - " + date;

        WebAnalyticsPage page = new WebAnalyticsPage(driver);

        page.selectCustomBtn();
        page.updateCustomDateRange();

        Assert.assertEquals(page.getDateRange(), sDate, "Date range does not match expected date range");
    }

}
