package specs.user.desktopSubscriptions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.securityPage.SecurityOverviewPage;
import pageobjects.user.sideNavBar.SideNavBar;
import pageobjects.user.unsubscribedDesktopSubscriptionPages.UnsubscribedPages;
import specs.AbstractSpec;

/**
 * Created by dannyl on 2017-08-11.
 */
public class DesktopSubscriptions extends AbstractSpec {

    @Before
    public void setUp(){
        new LoginPage(driver).loginUnsubscribedUser()
                .accessSideNav()
                .selectOwnershipFromSideNav();
    }

    /*Unsubscribed pages being tested are
        - Surveillance
        - Relative Performance
        - Expected Ranges
        - Sentiment
        - Volatility
        - Activism
        - Events Page
        - Webcast Analytics
        - Web Analytics
     */
    @Test
    public void checkAllUnsubcribedPages (){
        SideNavBar sideNavBar = new SideNavBar(driver);
        UnsubscribedPages unsubscribedPages = new UnsubscribedPages(driver);
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);

        // For Web Analytics
        sideNavBar
                .accessSideNavFromPage()
                .selectWebAnalyticsFromSideNav();
        Assert.assertTrue("Web Analytics did not display correct unsubcription message", unsubscribedPages.checkForUnsubscribedMessage());

        // For Webcast Analytics
        sideNavBar
                .accessSideNavFromPage()
                .selectWebcastAnalyticsFromSideNav();
        Assert.assertTrue("Webcast Analytics did not display correct unsubcription message", unsubscribedPages.checkForUnsubscribedMessage());

        // For Research
        sideNavBar
                .accessSideNavFromPage()
                .selectResearchFromSideNav();
        Assert.assertTrue("Research did not display correct unsubcription message", unsubscribedPages.checkForUnsubscribedMessage());

        // For Estimates
        sideNavBar
                .accessSideNavFromPage()
                .selectEstimatesFromSideNav();
        Assert.assertTrue("Estimates did not display correct unsubcription message", unsubscribedPages.checkForUnsubscribedMessage());

        // Navigate to security page
        sideNavBar
                .accessSideNavFromPage()
                .selectSecurityFromSideNav();

        // For Expected Trading
        securityOverviewPage
                .clickViewDropdownMenu()
                .clickDropdownTrading();
        Assert.assertTrue("Expected Trading Range did not display correct unsubcription message", unsubscribedPages.checkForUnsubscribedMessage());

        // For sentiment
        securityOverviewPage
                .clickViewDropdownMenu()
                .clickDropdownSentiment();
        Assert.assertTrue("Sentiment did not display correct unsubcription message", unsubscribedPages.checkForUnsubscribedMessage());

        // For Volatility
        securityOverviewPage
                .clickViewDropdownMenu()
                .clickDropdownVolatility();
        Assert.assertTrue("Volatility did not display correct unsubcription message", unsubscribedPages.checkForUnsubscribedMessage());

        // For Activism
        securityOverviewPage
                .clickViewDropdownMenu()
                .clickDropdownActivism();
        Assert.assertTrue("Activism did not display correct unsubcription message", unsubscribedPages.checkForUnsubscribedMessage());

        // For Performance
        securityOverviewPage
                .clickViewDropdownMenu()
                .clickDropdownPerformance();
        Assert.assertTrue("Performance did not display correct unsubcription message", unsubscribedPages.checkForUnsubscribedMessage());


    }
}
