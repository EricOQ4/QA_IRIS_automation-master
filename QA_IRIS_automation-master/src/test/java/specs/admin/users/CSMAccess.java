package specs.admin.users;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.admin.companyPage.CompanyDetailsPage;
import pageobjects.admin.companyPage.CompanyList;
import pageobjects.admin.loginPage.AdminLoginPage;
import pageobjects.admin.usersPage.UserRole;
import pageobjects.admin.usersPage.UserRoleDetails;
import specs.AdminAbstractSpec;

import java.util.Date;

/**
 * Created by noelc on 2017-07-18.
 */
public class CSMAccess extends AdminAbstractSpec {

    private static String roleEmail = "roleaccess@q4websystems.com";
    private static String rolePassword = "roleaccess!";
    @Before
    public void setUp() {
        UserRoleDetails userDetailsPage = new AdminLoginPage(driver).loginAdmin().navigateToUsersPage().selectUser(roleEmail).disableAllRoles().enableRole(UserRole.CUSTOMER_SUCCESS_MANAGER);
        userDetailsPage.logout();
        AdminLoginPage adminLogin = new AdminLoginPage(driver).customLoginAdmin(roleEmail,rolePassword);

    }

    @Test
    public void canEditCompanyProfile(){
        String editedCompanyName = "Harmonic, Inc. ";
        Date today = new Date();
        CompanyList companyList = new CompanyList(driver).navigateToCompanyPage();
        CompanyDetailsPage companyDetails = companyList.searchForCompany(editedCompanyName).selectFirstCompanyInList();
        companyDetails.editCompanyName(editedCompanyName+ today);
       Assert.assertTrue("Company name not edited",companyDetails.getCompanyName().contains(editedCompanyName+today));
    }

    @Test
    public void canViewPeerToACompany(){
        String editedCompanyName = "Harmonic, Inc. ";
        Date today = new Date();
        CompanyList companyList = new CompanyList(driver).navigateToCompanyPage();
        CompanyDetailsPage companyDetails = companyList.searchForCompany(editedCompanyName).selectFirstCompanyInList();
        Assert.assertTrue("CSM cannot accesss peer table for viewing",companyDetails.clickPeerTab().onPeerTab());
    }

    @Test
    public void canViewMailingList(){
        String editedCompanyName = "Harmonic, Inc. ";
        Date today = new Date();
        CompanyList companyList = new CompanyList(driver).navigateToCompanyPage();
        CompanyDetailsPage companyDetails = companyList.searchForCompany(editedCompanyName).selectFirstCompanyInList();
        Assert.assertTrue("CSM cannot access mailing list viewing",companyDetails.clickMailingTab().onMailTab());
    }

    @Test
    public void canEditTickers(){
        String editedCompanyName = "Harmonic, Inc. ";
        String ticker = "SYY";
        Date today = new Date();
        CompanyList companyList = new CompanyList(driver).navigateToCompanyPage();
        CompanyDetailsPage companyDetails = companyList.searchForCompany(editedCompanyName).selectFirstCompanyInList();
        companyDetails.selectTickerTab().addTicker(ticker);
        Assert.assertTrue("Ticker is not present after being added",companyDetails.isTickerPresent(ticker));
        companyDetails.removeTicker();
    }

    @Test
    public void canEditQ4Team(){
        String editedCompanyName = "Harmonic, Inc. ";
        String ticker = "SYY";
        String name = "QA Test";
        Date today = new Date();
        CompanyList companyList = new CompanyList(driver).navigateToCompanyPage();
        CompanyDetailsPage companyDetails = companyList.searchForCompany(editedCompanyName).selectFirstCompanyInList();
        companyDetails.clickQ4TeamTab().clickAddButton().addTeamMember(name);
        Assert.assertTrue("Team member not added", companyDetails.isTeamMemberPresent(name));
        companyDetails.removeTeamMember(name);
    }

}
