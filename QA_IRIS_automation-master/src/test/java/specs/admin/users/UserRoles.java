package specs.admin.users;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.admin.loginPage.AdminLoginPage;
import pageobjects.admin.usersPage.UserRole;
import pageobjects.admin.usersPage.UserRoleDetails;
import pageobjects.admin.usersPage.UsersPage;
import pageobjects.user.dashboardPage.Dashboard;
import specs.AdminAbstractSpec;

/**
 * Created by noelc on 2016-11-22.
 */
public class UserRoles extends AdminAbstractSpec {
    @Before
    public void setUp() {
        if (hasLoggedIn()) {
            new Dashboard(driver).navigateToUsersPage();
        }
        else {
            new AdminLoginPage(driver).loginAdmin().navigateToUsersPage();
        }

    }




    @Test
    public void filterUsersByAdminRole(){
        UsersPage userPage = new UsersPage(driver);
        UserRoleDetails userDetails= userPage.clickUserRoleFilter().clickRoleInFilter(UserRole.ADMIN).clickFirstUserFromResults();
        Assert.assertTrue("Users were not filtered by the Admin role",userDetails.isRoleEnabled(UserRole.ADMIN));
    }


    @Test
    public void filterUserByDeveloperRole(){
        UsersPage userPage = new UsersPage(driver);
        UserRoleDetails userDetails= userPage.clickUserRoleFilter().clickRoleInFilter(UserRole.DEVOPS).clickFirstUserFromResults();
        Assert.assertTrue("Users were not filtered by the Admin role",userDetails.isRoleEnabled(UserRole.DEVOPS));
    }

    @Test
    public void filterUserByIntelligenceRole(){
        UsersPage userPage = new UsersPage(driver);
        UserRoleDetails userDetails= userPage.clickUserRoleFilter().clickRoleInFilter(UserRole.INTELLIGENCE_ANALYST).clickFirstUserFromResults();
        Assert.assertTrue("Users were not filtered by the Admin role",userDetails.isRoleEnabled(UserRole.INTELLIGENCE_ANALYST));
    }

    @Test
    public void filterUserByImplementationRole(){
        UsersPage userPage = new UsersPage(driver);
        UserRoleDetails userDetails= userPage.clickUserRoleFilter().clickRoleInFilter(UserRole.IMPLEMENTATION_MANAGER).clickFirstUserFromResults();
        Assert.assertTrue("Users were not filtered by the Admin role",userDetails.isRoleEnabled(UserRole.IMPLEMENTATION_MANAGER));
    }

    @Test
    public void filterUserByCustomerSuccessRole(){
        UsersPage userPage = new UsersPage(driver);
        UserRoleDetails userDetails= userPage.clickUserRoleFilter().clickRoleInFilter(UserRole.CUSTOMER_SUCCESS_MANAGER).clickFirstUserFromResults();
        Assert.assertTrue("Users were not filtered by the Admin role",userDetails.isRoleEnabled(UserRole.CUSTOMER_SUCCESS_MANAGER));
    }

    @Test
    public void filterUserBySalesManagerRole(){
        UsersPage userPage = new UsersPage(driver);
        UserRoleDetails userDetails= userPage.clickUserRoleFilter().clickRoleInFilter(UserRole.SALES_MANAGER).clickFirstUserFromResults();
        Assert.assertTrue("Users were not filtered by the Admin role",userDetails.isRoleEnabled(UserRole.SALES_MANAGER));
    }
}
