package specs.user.login;

import org.junit.Assert;
import org.junit.Test;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

/*
 * Created by patrickp on 2016-08-08.
 */
/*
 * Updated by abbyl on 2019-03-04.
 */
public class UserLogin extends AbstractSpec {

    @Test
    public void incorrectUserName() {
        String errorMessage = "Username and password don't match!";
        LoginPage start = new LoginPage(driver);
        start.customLoginUser("imnotauser@mail.com", "notapassword");

        Assert.assertEquals("Login error message not displayed", errorMessage, start.getErrorMessage());
    }

    @Test
    public void incorrectEmailFormat() {
        String errorMessage = "Invalid email address format!";
        LoginPage start = new LoginPage(driver);
        start.customLoginUser("email", "password");

        Assert.assertEquals("Invalid email address format!", errorMessage, start.getErrorTooltip());
    }

    @Test
    public void incorrectPassword() {
        String errorMessage = "Username and password don't match!";
        LoginPage start = new LoginPage(driver);
        start.customLoginUser("patrickp@q4inc.com", "nottherightpassword");

        Assert.assertEquals("Login error message not displayed", errorMessage, start.getErrorMessage());
    }

    @Test
    public void emptyFields() {
        String errorMessage = "Please enter username and password\n" +
                "OK";
        LoginPage start = new LoginPage(driver);
        start.customLoginUser("","");

        Assert.assertTrue("Login error message not displayed", start.getErrorEmptyField());
    }

    @Test
    public void incorrectLogin() {
        String errorMessage = "Username and password don't match!";
        LoginPage start = new LoginPage(driver);
        start.customLoginUser("1234@qwer.com", "1235abc");

        Assert.assertEquals("Login error message not displayed", errorMessage, start.getErrorMessage());

    }
    @Test
    public void successfulLogin(){
        String searchFieldText = "Search";

        LoginPage start = new LoginPage(driver);
        Dashboard finish = new Dashboard(driver);
        start.customLoginUser("test@q4inc.com", "q4pass1234!");

        Assert.assertEquals("User was not logged in successfully", searchFieldText, finish.getSearchFieldText());

    }
}
