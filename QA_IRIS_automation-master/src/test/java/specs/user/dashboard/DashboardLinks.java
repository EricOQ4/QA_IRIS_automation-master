package specs.user.dashboard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.securityPage.SecurityOverviewPage;
import specs.AbstractSpec;


/**
 * Created by patrickp on 2016-08-08.
 */
public class DashboardLinks extends AbstractSpec {

    @Before
    public void logInUser() {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void canNavigateToMyCompanyBigSharePrice() {
        String companyName = "Sysco Corp";
        SecurityOverviewPage finish = new SecurityOverviewPage(driver);
        new Dashboard(driver).clickBigSharePrice();

        Assert.assertEquals("Company name is not visible, or doesn't match expected", companyName, finish.getCompanyName());
    }

    // TODO this has been removed. Not sure if we plan on bringing this functionality back
    @Ignore
    @Test
    public void canNavigateToMyCompanySmallSharePrice() {
        String companyName = "Sysco Corp";
        Dashboard finish = new Dashboard(driver).clickSmallSharePrice();

      Assert.assertTrue("Company name is not visible, or doesn't match expected", finish.checkAlternateSymbols());
    }
}
