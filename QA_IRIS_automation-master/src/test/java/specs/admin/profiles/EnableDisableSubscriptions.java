package specs.admin.profiles;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pageobjects.admin.loginPage.AdminLoginPage;
import pageobjects.admin.profilesPage.ProfilesList;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.estimatesPage.SecurityEstimatesPage;
import pageobjects.user.loginPage.LoginPage;
import specs.AdminAbstractSpec;
import util.EnvironmentType;

/**
 * Created by patrickp on 2016-10-13.
 */
public class EnableDisableSubscriptions extends AdminAbstractSpec {

    @Before
    public void setUp() {
        if (hasLoggedIn() && getActiveEnvironment()!= EnvironmentType.LOCALADMIN) {
            new Dashboard(driver).navigateToProfilesPage();
        }
        else {
            new AdminLoginPage(driver).loginAdmin()
                    .navigateToProfilesPage();
        }
    }

    //This still needs an assert
    // TODO this is breaking shit. Ignore for now. We should add a tear down that enables estimates..
    @Ignore
    @Test
    public void canDisableEstimates() {
        new ProfilesList(driver).searchForProfile("patrickp@q4inc.com")
                .selectFirstProfileInList()
                .disableEstimates()

                .enableResearch()
                .enabledExpectedRanges()
                .enableSentiment()
                .enabledVolatility()
                .enabledActivism()
                .enabledEvents()
                .enableWebcast()
                .enabledWebsite()
                .enabledTradingAnalytics()
                .enableRelativePerformance()
                .enableSurveillance();

        driver.navigate().to("https://develop.q4desktop.com");

        SecurityEstimatesPage loginPage = new LoginPage(driver).customLoginUser("patrickp@q4inc.com", "patrick!")
                .accessSideNav()
                .selectEstimatesFromSideNav();
        Assert.assertTrue("No option to subscribe is available", loginPage.userIsNotSubscribed());
    }
}
