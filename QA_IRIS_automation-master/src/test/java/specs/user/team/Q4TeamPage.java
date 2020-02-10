package specs.user.team;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.myQ4TeamPage.MyQ4TeamPage;
import specs.AbstractSpec;

/**
 * Created by patrickp on 2016-10-11.
 */
public class Q4TeamPage extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav();
    }

    @Ignore
    @Test
    public void canViewTeamBio() {
        MyQ4TeamPage teamPage = new MyQ4TeamPage(driver).viewTeamBio();
    }
}
