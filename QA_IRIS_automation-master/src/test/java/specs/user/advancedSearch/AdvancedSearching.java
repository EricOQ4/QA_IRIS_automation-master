package specs.user.advancedSearch;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.advancedSearchPage.AdvancedSearchPage;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2017-03-14.
 */
public class AdvancedSearching extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectAdvancedSearchFromSideNav();
    }

    @Test
    public void canSearchForSecurity() {
        String security = "easyJet";
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(driver).advancedSearchFor(security);

        Assert.assertThat("Could not find security in search results", advancedSearchPage.getAdvancedSearchResults(), containsString((security)));
    }

    @Test
    public void canSearchForInstitution() {
        String institution = "The Vanguard Group, Inc.";
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(driver).advancedSearchFor(institution);

        Assert.assertThat("Could not find institution in search results", advancedSearchPage.getAdvancedSearchResults(), containsString((institution)));
    }

    @Test
    public void canSearchForFund() {
        String fund = "Vanguard Explorer Fund";
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(driver).advancedSearchFor(fund);

        Assert.assertThat("Could not find fund in search results", advancedSearchPage.getAdvancedSearchResults(), containsString((fund)));
    }

    @Test
    public void canSearchForContact() {
        String contact = "Christoph Hilfiker";
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(driver).advancedSearchFor(contact);

        Assert.assertThat("Could not find contact in search results", advancedSearchPage.getAdvancedSearchResults(), containsString((contact)));
    }

    @Test
    public void canSearchForTranscripts() {
        String transcriptKeyword = "share";
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(driver).advancedSearchFor(transcriptKeyword)
                .selectTranscriptsTab();

        Assert.assertThat("Could not find transcript in search results", advancedSearchPage.getAdvancedSearchResults(), containsString((transcriptKeyword)));
    }

    @Test
    public void canSearchForActivityWithKeyword() {
        String activityKeyword = "Sysco";
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(driver).advancedSearchFor(activityKeyword);

        Assert.assertThat("Could not find activity in search results", advancedSearchPage.getAdvancedSearchResults(), containsString((activityKeyword)));
    }

    @Test
    public void canSearchForActivityByTag() {
        String tagName = "#sysco";
        String comment = "Sysco";
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(driver).advancedSearchFor(tagName);

        Assert.assertThat("Could not find activity in search results", advancedSearchPage.getTagSearchResults(), containsString(comment));
    }

    @Test
    public void canSearchForResearch() {
        String researchKeyword = "DXC";
        AdvancedSearchPage advancedSearchPage = new AdvancedSearchPage(driver).advancedSearchFor(researchKeyword)
                .selectResearchTab();

        Assert.assertThat("Could not find research in search results", advancedSearchPage.getAdvancedSearchResults(), containsString((researchKeyword)));
    }
}
