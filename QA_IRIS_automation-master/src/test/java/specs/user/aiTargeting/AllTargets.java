package specs.user.aiTargeting;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.aiTargetingPage.SavedTargetsPage;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;
import specs.downloadVerification;

import java.util.Vector;

/**
 * Created by yevam on 2018-08-02
 */

/**
 * updated by _____ on 2020-02-10
 */

public class AllTargets extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectaiTargetingFromSideNav();

    }

    @Test
    public void verifyNavigation(){

        Assert.assertTrue(true);
    }


}


