package specs.admin.profiles;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pageobjects.admin.loginPage.AdminLoginPage;
import pageobjects.admin.profilesPage.ProfileDetails;
import pageobjects.admin.profilesPage.ProfilesList;
import pageobjects.user.dashboardPage.Dashboard;
import specs.AdminAbstractSpec;
import util.EnvironmentType;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-10-11.
 */
public class EditProfilesList extends AdminAbstractSpec {

    @Before
    public void setUp() {
        if (hasLoggedIn() && getActiveEnvironment()!= EnvironmentType.LOCALADMIN) {
            new Dashboard(driver).navigateToProfilesPage();
        }
        else {
            new AdminLoginPage(driver).loginAdmin()
                    .navigateToProfilesPage();
        }
    }

    @Test
    public void canSearchForProfile() {
        String userName = "Patrick";
        ProfilesList profilesList = new ProfilesList(driver).searchForProfile(userName);

        Assert.assertThat("Search results did not contain expected username", profilesList.getProfileList(), containsString(userName));
    }

    // TODO ignoring until we have a way to delete profiles
    @Ignore
    @Test
    public void canAddProfile() {
        String email = "Test@" + RandomStringUtils.randomAlphanumeric(5) + ".com";
        String password = "testing123!";
        String organization = "WIX";
        String firstName = "Test";
        String lastName = "Testerson";
        String title = "Tester";
        String phone = "123-456-7890";
        String accountType = "Q4 Employee";

        ProfilesList profilesList = new ProfilesList(driver).addNewProfile(email, password, organization, firstName, lastName, title, phone, accountType);
    }

    @Test
    public void addMultipleUsers() {
        ProfilesList profilesList = new ProfilesList(driver);
        Assert.assertTrue(profilesList.bulkAddUser(true,4, "tickerList.txt"));
    }

    @Test
    public void canEditExistingProfile() {
        String firstName = "Test" + RandomStringUtils.randomAlphanumeric(3);
        String userName = "Testerson";

        ProfileDetails profilesList = new ProfilesList(driver).searchForProfile(userName)
                .selectFirstProfileInList()
                .editProfileFirstName(firstName);

        Assert.assertThat("Changes to profile not saved correctly", profilesList.getAdminPageTitle(), containsString(firstName));
    }
}
