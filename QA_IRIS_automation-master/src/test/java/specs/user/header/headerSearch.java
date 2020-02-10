package specs.user.header;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Pause;
import pageobjects.user.contactPage.ContactDetailsPage;
import pageobjects.user.fundPage.FundPage;
import pageobjects.user.institutionPage.InstitutionPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.securityPage.SecurityOverviewPage;
import specs.AbstractSpec;
import java.util.regex.Pattern;

/**
 * Created by sarahr on 2/9/2017.
 */
/**
 * Updated by abbyl on 3/1/2019.
 */

public class headerSearch extends AbstractSpec{

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser();

    }

    @Test
    public void canSearchForSecurityFromHeader() {
        String securitySearchTerm = "Facebook";
        String expectedSecurity = "Facebook, Inc.";

        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.searchResults(securitySearchTerm, expectedSecurity)
                .waitForLoadingScreen();

        Assert.assertEquals("Did not open correct security page", securityOverviewPage.getCompanyName(), "Facebook Inc");
    }

    @Test
    public void canSearchForInstitutionFromHeader() {
        String institutionSearchTerm = "The Vanguard Group, Inc";
        String expectedInstitution = "The Vanguard Group, Inc";

        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.searchResults(institutionSearchTerm, expectedInstitution)
                .waitForLoadingScreen();

        InstitutionPage institutionPage = new InstitutionPage(driver);

        Assert.assertTrue("Did not open correct institution page", institutionPage.getInstitutionName().contains(expectedInstitution));
    }

    @Test
    public void canSearchForContactFromHeader() {
        String contactSearchTerm = "andrew mccormick";
        String expectedContact = "Andrew C. McCormick";

        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.searchResults(contactSearchTerm, expectedContact)
                .waitForLoadingScreen();

        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);

        Assert.assertEquals("Did not open correct contact page", contactDetailsPage.getContactName(), "Mr. Andrew C. McCormick");
    }

    @Test
    public void canSearchForFundFromHeader() {
        String fundSearchTerm = "capitalcredit";
        String expectedFund = "Capitalcredit";

        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.searchResults(fundSearchTerm, expectedFund)
                .waitForLoadingScreen();

        FundPage fundPage = new FundPage(driver);

        Assert.assertTrue("Did not open correct fund page", fundPage.getFundName().contains(expectedFund));
    }

}
