package specs.user.relativePerformance;

import org.junit.Before;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by kelvint on 11/2/16.
 */
public class RelativePerformance extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectSecurityFromSideNav();
    }

}
