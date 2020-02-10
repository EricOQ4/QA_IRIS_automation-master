package specs.user.research;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.researchPage.ResearchPage;
import specs.AbstractSpec;

/**
 * Created by charlesz on 10/27/2017.
 */

public class UnsubscribedUserResearch extends AbstractSpec{
    @Before
    public void setUp() {
        new LoginPage(driver).loginUnsubscribedUser()
                .accessSideNav()
                .selectResearchFromSideNav();
    }

    @Test
    public void canNotifyUnsubscribedUser(){
        ResearchPage researchPage = new ResearchPage(driver);
        Assert.assertTrue("Should receive notification for unsubsribed user.", researchPage.isUnsubscribed());
    }

    @After
    public void tearDown(){
        driver.quit();
    }
}
