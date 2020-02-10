package specs.user.aiTargeting;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pageobjects.user.aiTargetingPage.SavedTargetsPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.aiTargetingPage.AITargetingPage;
import specs.AbstractSpec;
import specs.downloadVerification;

import java.io.File;
import java.util.Vector;

/**
 * Created by yevam on 2018-08-02
 */

/**
 * updated by _____ on 2020-02-10
 */

public class SavedTargets extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectaiTargetingFromSideNav()
                .goToSavedTargetsPage();

    }

    @Test
    public void clickInstitutionTab() {
        SavedTargetsPage page = new SavedTargetsPage(driver);
        page.setFilterTabs();
        page.setProfileTableIcons();
        Assert.assertTrue("Incorrect profile type", page.checkFilterTabs(1));

    }

    @Test
    public void clickFundsTab() {
        SavedTargetsPage savedTargetsPage = new SavedTargetsPage(driver);
        savedTargetsPage.setFilterTabs();
        savedTargetsPage.setProfileTableIcons();
        Assert.assertTrue("Incorrect profile type", savedTargetsPage.checkFilterTabs(2));

    }

    @Test
    public void clickContactsTab() {
        SavedTargetsPage savedTargetsPage = new SavedTargetsPage(driver);
        savedTargetsPage.setFilterTabs();
        savedTargetsPage.setProfileTableIcons();
        Assert.assertTrue("Incorrect profile type", savedTargetsPage.checkFilterTabs(3));

    }

    @Test
    public void clickAllTab() {
        SavedTargetsPage savedTargetsPage = new SavedTargetsPage(driver);
        savedTargetsPage.setFilterTabs();
        savedTargetsPage.setProfileTableIcons();
        Assert.assertTrue("Could not find profile list", savedTargetsPage.clickAllTab());

    }

    @Test
    public void searchSavedTarget() {

        SavedTargetsPage savedTargetsPage = new SavedTargetsPage(driver);
        Assert.assertTrue("Search is broken", savedTargetsPage.searchForTarget("Harvey Hamilton Hardee"));

    }


    // This test first clicks the export button, then creates a verifyLatestDownload object which lets you assert that the export button is
    //      indeed working properly
    @Test
    public void verifyExportButton(){
        SavedTargetsPage savedTargetsPage = new SavedTargetsPage(driver);
        savedTargetsPage.clickExportButton();
        downloadVerification verifyLatestDownload = new downloadVerification();

        if(verifyLatestDownload.wasSuccessfulDownload()){

            Vector<Vector<String>> grid = savedTargetsPage.parseGrid();

            Assert.assertTrue(verifyLatestDownload.compareDataToCSV(grid));

        }else{
            Assert.assertTrue(verifyLatestDownload.wasSuccessfulDownload());
        }
    }

}


