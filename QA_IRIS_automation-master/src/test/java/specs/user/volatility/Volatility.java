package specs.user.volatility;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.volatilityPage.VolatilityPage;
import specs.AbstractSpec;

/**
 * Created by kelvint on 11/2/16.
 */
public class Volatility extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectSecurityFromSideNav()
                .navigateToVolatilityPage();
    }

    @Test
    public void checkForVolatilityNLG() {
        VolatilityPage volatilityPage = new VolatilityPage(driver);

        Assert.assertTrue("NLG is not present on page", volatilityPage.verifyTextIsPresent());
    }
}
