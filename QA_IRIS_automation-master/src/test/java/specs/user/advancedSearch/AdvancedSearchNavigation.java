package specs.user.advancedSearch;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.advancedSearchPage.AdvancedSearchPage;
import pageobjects.user.contactPage.ContactDetailsPage;
import pageobjects.user.fundPage.FundPage;
import pageobjects.user.institutionPage.InstitutionPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.noteDetailsPage.NoteDetailsPage;
import pageobjects.user.securityPage.SecurityOverviewPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2017-03-17.
 */
public class AdvancedSearchNavigation extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectAdvancedSearchFromSideNav();
    }

    @Test
    public void canNavigateToEntityFromAllTab() {
        String security = "easyJet";
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(driver).advancedSearchFor(security);

        String entityDetails = advancedSearchPage.getFirstSearchResult();

        advancedSearchPage.selectFirstSearchResult();

        Assert.assertThat("Security overview page does not match search result", securityOverviewPage.getCompanyName(), containsString(entityDetails));
    }

    @Test
    public void canNavigateToEntityFromSecurityTab() {
        String security = "easyJet";
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(driver).advancedSearchFor(security)
                .selectSecurityTab();

        String entityDetails = advancedSearchPage.getFirstSearchResult();

        advancedSearchPage.selectFirstSearchResult();

        Assert.assertThat("Security overview page does not match search result", securityOverviewPage.getCompanyName(), containsString(entityDetails));
    }

    @Test
    public void canNavigateToEntityFromInstitutionTab() {
        String institution = "Vanguard Ventures";
        InstitutionPage institutionPage = new InstitutionPage(driver);
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(driver).advancedSearchFor(institution)
                .selectInstitutionTab();

        String entityDetails = advancedSearchPage.getFirstSearchResult();

        advancedSearchPage.selectFirstSearchResult();

        Assert.assertThat("Institution overview page does not match search result", institutionPage.getInstitutionName(), containsString(entityDetails));
    }

    @Test
    public void canNavigateToEntityFromFundTab() {
        String fund = "Vanguard Explorer Fund";
        FundPage fundPage = new FundPage(driver);
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(driver).advancedSearchFor(fund)
                .selectFundTab();

        String entityDetails = advancedSearchPage.getFirstSearchResult();

        advancedSearchPage.selectFirstSearchResult();

        Assert.assertThat("Fund overview page does not match search result", fundPage.getFundName(), containsString(entityDetails));
    }

    @Test
    public void canNavigateToEntityFromContactTab() {
        String contact = "Christoph Hilfiker";
        ContactDetailsPage contactDetailsPage = new ContactDetailsPage(driver);
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(driver).advancedSearchFor(contact)
                .selectContactTab();

        String entityDetails = advancedSearchPage.getFirstSearchResult();

        advancedSearchPage.selectFirstSearchResult();

        Assert.assertThat("Contact overview page does not match search result", contactDetailsPage.getContactName(), containsString(entityDetails));
    }

    @Test
    public void canNavigateToEntityFromActivityTab() {
        String activityKeyword = "This is a test commentSc9MrN";
        NoteDetailsPage noteDetailsPage = new NoteDetailsPage(driver);
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(driver).advancedSearchFor(activityKeyword)
                .selectActivityTab();

        String entityDetails = advancedSearchPage.getFirstSearchResult();

        advancedSearchPage.selectFirstSearchResult();

        Assert.assertThat("Activity overview page does not match search result", noteDetailsPage.getCommentText(), containsString(entityDetails));
    }
}
