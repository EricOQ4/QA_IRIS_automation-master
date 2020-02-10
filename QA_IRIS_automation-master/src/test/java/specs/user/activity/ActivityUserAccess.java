package specs.user.activity;

import org.junit.*;
import pageobjects.AbstractPageObject;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

import java.util.List;

/**
 * Created by charleszheng on 10/30/2017.
 */

public class ActivityUserAccess extends AbstractSpec  {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectActivityPageFromSideNav();
    }

    @Test
    public void checkActivityUserAccess(){
        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.createNewNote("UserAccessTest","","");
        activityPage.searchForNote("UserAccessTest");
        List<String> userOne = activityPage.getActivityTitles();
        activityPage.deleteNote("UserAccessTest");
        activityPage.logout();
        new LoginPage(driver).customLoginUser("testy1234@q4inc.com","q4pass1234!")
                .accessSideNav()
                .selectActivityPageFromSideNav();
        activityPage.searchForNote("UserAccessTest");
        List<String> userTwo = activityPage.getActivityTitles();
        Assert.assertTrue("Activities should be different for different users", activityPage
                .showsDifferentActivitiesForDifferentUser(userOne, userTwo));

    }
}
