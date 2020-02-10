package specs.user.research;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.Calendar;
import pageobjects.user.contactPage.ContactPage;
import pageobjects.user.institutionPage.InstitutionPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.researchPage.ResearchPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-11-09.
 */
public class ResearchList extends AbstractSpec {

    private String documentName = "Solid Finish to a Strong Year";

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectResearchFromSideNav();
    }

    @Test
    public void canSearchForResearch() {
        ResearchPage researchPage = new ResearchPage(driver).searchForDocument(documentName);

        Assert.assertThat("Searching did not return the correct result", researchPage.getResearchHeadline(), containsString(documentName));
        // Make sure number of returned documents is reflected in interface (should be 1)
        Assert.assertEquals("Number of documents shown is greater than one", 2, researchPage.getNumberOfDocuments());
    }

    @Test
    public void canAccessFundDetailsPageFromList() {
        InstitutionPage institutionPage = new InstitutionPage(driver);
        ResearchPage researchPage = new ResearchPage(driver).searchForDocument(documentName);

        String firmName = researchPage.getFirmNameFromList();

        researchPage.selectFirmFromResearchList();

        Assert.assertThat("Institution name was not visible", institutionPage.getInstitutionName(), containsString(firmName));
    }

    @Test
    public void canAccessAnalystPageFromList() {
        ContactPage contactPage = new ContactPage(driver);
        ResearchPage researchPage = new ResearchPage(driver).searchForDocument(documentName)
                .viewMultipleAnaysts();

        String analystName = researchPage.getAnalystNameFromList();

        researchPage.selectAnalystFromResearchList();

        Assert.assertThat("Analysts name was not visible", contactPage.getPageTitle(), containsString(analystName));
    }

    @Test
    public void canSortResearchByHeadline() {
        Assert.assertTrue("Research Reports cannot be sorted by headline.", new ResearchPage(driver).sortByHeadline());
    }

    @Test
    public void canSortResearchByDate() {
        // Checking all research items fall within a designated time frame selected through "start" and "end" times
        ResearchPage researchPage = new ResearchPage(driver);
        Calendar calendar = new Calendar(driver);
        researchPage.filterDate(calendar);

        Assert.assertTrue("Research Reports cannot be sorted by date.", new ResearchPage(driver).sortByDateRange(calendar));
    }

    @Test
    public void canSortResearchByFirm() {
        Assert.assertTrue("Research Reports cannot be sorted by firm.", new ResearchPage(driver).sortByFirm());
    }
}