package specs.admin.companies;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.admin.companyPage.CompanyList;
import pageobjects.admin.loginPage.AdminLoginPage;
import pageobjects.user.dashboardPage.Dashboard;
import specs.AdminAbstractSpec;
import util.EnvironmentType;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-09-20.
 */
public class CompaniesList extends AdminAbstractSpec {

    @Before
    public void setUp() {
        if (hasLoggedIn() && getActiveEnvironment()!= EnvironmentType.LOCALADMIN) {
            new Dashboard(driver).navigateToCompanyPage();
        } else {
            new AdminLoginPage(driver).loginAdmin().navigateToCompanyPage();
        }
    }

    @Ignore
    @Test
    public void canAddCompanyToList() {
        String companyName = "Canopy Growth Corp.";
        CompanyList companyPage = new CompanyList(driver).addNewCompany(companyName);
    }

    @Ignore
    @Test
    public void canEditCompanyNameWhenAdding() {

    }

    @Test
    public void canViewCompanyDetails() {
        String companyName = new CompanyList(driver).getFirstCompanyName();
        CompanyList companyPage = new CompanyList(driver).selectFirstCompanyInList();

        Assert.assertThat("Company name does not match expected", companyPage.getAdminPageTitle(), containsString(companyName));
    }

    @Test
    public void cannotAddInvalidCompany(){
        String companyName = "Can't add this";
        CompanyList companyList= new CompanyList(driver).searchForNewCompany(companyName);
        Assert.assertFalse("Incorrectly able to add a company not in the system",companyList.dismissTickerDropdown().isSaveButtonEnabled());


    }

    @Test
    public void canSearchForCompany() {
        String companyName = "Yum";
        CompanyList companyPage = new CompanyList(driver).searchForCompany(companyName);

        Assert.assertThat("Search results did not return expected results", companyPage.getCompanyList(), containsString(companyName));
    }

    @Test
    public void unknownSearchReturnsNoResults() {
        String fakeName = "1234";
        CompanyList companyPage = new CompanyList(driver).searchForCompany(fakeName);

        Assert.assertThat("Search returned results when none were expected", companyPage.getCompanyList(), containsString("No records found"));
    }

    @Test
    public void canCancelAddModal() {
        new CompanyList(driver).triggerAddCompanyModal()
                .cancelAddCompany();

        Assert.assertEquals("New company modal was not dismissed", 1, driver.findElements(By.className("ui-dialog-title")).size());
    }

    @Test
    public void canDismissAddModal() {
        new CompanyList(driver).triggerAddCompanyModal()
                .exitAddCompanyModal();

        Assert.assertEquals("New company modal was not dismissed", 1, driver.findElements(By.className("ui-dialog-title")).size());
    }
}
