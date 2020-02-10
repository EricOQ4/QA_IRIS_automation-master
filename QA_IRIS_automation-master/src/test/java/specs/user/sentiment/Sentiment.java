package specs.user.sentiment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.sentimentPage.SentimentPage;
import specs.AbstractSpec;

/**
 * Created by kelvint on 11/2/16.
 */
public class Sentiment extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectSecurityFromSideNav()
                .navigateToSentimentPage();
    }

    @Test
    public void checkForSentimentNLG() {
        SentimentPage sentimentPage = new SentimentPage(driver);

        Assert.assertTrue("NLG is not present on page", sentimentPage.verifyTextIsPresent());
    }
}
