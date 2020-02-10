package specs.user.estimates;

import org.junit.*;
import pageobjects.user.estimatesPage.SecurityEstimatesPage;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by charlesz on 10/27/2017.
 */

public class UnsubscribedUserEstimates extends AbstractSpec{

    @Before
    public void setUp() {
        new LoginPage(driver).loginUnsubscribedUser()
                .accessSideNav()
                .selectEstimatesFromSideNav();
    }

    @Test
    public void canNotifyUnsubscribedUser(){
        SecurityEstimatesPage estimatesPage = new SecurityEstimatesPage(driver);
        Assert.assertTrue("Should receive notification for unsubsribed user.", estimatesPage.isUnsubscribed());
    }

    @After
    public void tearDown(){
        driver.quit();
    }

}
