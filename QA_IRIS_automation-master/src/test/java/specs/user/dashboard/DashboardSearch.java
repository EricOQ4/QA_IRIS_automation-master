package specs.user.dashboard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.user.securityPage.SecurityOverviewPage;
import pageobjects.user.contactPage.ContactDetailsPage;
import pageobjects.user.dashboardPage.Dashboard;
import pageobjects.user.fundPage.FundPage;
import pageobjects.user.institutionPage.InstitutionPage;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

public class DashboardSearch extends AbstractSpec{

    @Before
    public void logInUserBeforeEachTest() {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void canSearchForCompany() {
        String companySearchTerm = "Tesla Inc";
        String companyName = "Tesla, Inc";
        SecurityOverviewPage finish = new Dashboard(driver).searchFor(companySearchTerm)
                .selectSecurityFromSearchResults(companyName);

        Assert.assertNotNull("Security does not appear in search results", finish);
        Assert.assertEquals("Did not open expected security page", companySearchTerm, finish.getCompanyName());
    }

    @Test
    public void cantSearchForUnknownCompany() {
        String companyName = "12345qwerty";
        new Dashboard(driver).searchFor(companyName);

        Assert.assertEquals("Search returned results when none were expected", 0, driver.findElements(By.className("company-name")).size());
    }

    @Test
    public void specialCharactersShouldntBreakSearch() {
        String crazyCharacters = "!!!$$$%%%";
        String companySearchTerm = "Tesla Inc";
        String companyName = "Tesla, Inc";
        SecurityOverviewPage finish = new Dashboard(driver).searchFor(crazyCharacters)
                .clearSearchField()
                .searchFor(companySearchTerm)
                .selectSecurityFromSearchResults(companyName);

        Assert.assertNotNull("Security does not appear in search results", finish);
        Assert.assertEquals("Did not open expected security page", companySearchTerm, finish.getCompanyName());
    }

    @Test
    public void canSearchForInstitution() {
        String institutionName = "The Vanguard Group, Inc.";
        InstitutionPage finish = new Dashboard(driver).searchFor(institutionName)
                .selectInstitutionFromSearchResults(institutionName);

        Assert.assertNotNull("Known Issue: DESKTOP-9093 - Institution does not appear in search results", finish);
        Assert.assertThat("Did not open expected institution page", finish.getInstitutionName(), containsString(institutionName));
    }

    @Test
    public void canSearchForContact() {
        String contactSearchTerm = "Christoph Christen";
        String contactName = "Mr. Christoph Christen";
        ContactDetailsPage finish = new Dashboard(driver).searchFor(contactSearchTerm)
                .selectContactFromSearchResults(contactSearchTerm);

        Assert.assertNotNull("Contact does not appear in search results", finish);
        Assert.assertEquals("Did not open expected contact page", contactName, finish.getContactName());
    }

    @Test
    public void canSearchForFund() {
        String fundName = "Fidelity Global Fund";
        String pageTitle = "Fidelity - Fidelity Global Fund\n" +
                "Open-End Fund";
        FundPage finish = new Dashboard(driver).searchFor(fundName)
                .selectFundFromSearchResults(fundName);

        Assert.assertNotNull("Fund does not appear in search results", finish);
        Assert.assertEquals("Did not open expected fund page", pageTitle, finish.getFundName());
    }
}

