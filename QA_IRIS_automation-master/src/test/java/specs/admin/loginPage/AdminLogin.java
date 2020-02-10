package specs.admin.loginPage;

import org.junit.Assert;
import org.junit.Test;
import pageobjects.admin.homePage.HomePage;
import pageobjects.admin.loginPage.AdminLoginPage;
import specs.AdminAbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-09-16.
 */
public class AdminLogin extends AdminAbstractSpec {
    //
    @Test
    public void adminCanLogin() {
        String pageTitle = "Home";
        HomePage homePage = new HomePage(driver);
        new AdminLoginPage(driver).customLoginAdmin("test@q4websystems.com", "testing!");

        Assert.assertThat("Dashboard not visible after successful login attempt", homePage.getAdminPageTitle(), containsString(pageTitle));
    }

    @Test
    public void unknownUserCannotLogin() {
        String error = "Access denied.";
        AdminLoginPage adminPage = new AdminLoginPage(driver).customLoginAdmin("qfourtester@gmail.com", "testing!");

        Assert.assertTrue("Incorrectly able to login", adminPage.onLoginPage());
    }
}

