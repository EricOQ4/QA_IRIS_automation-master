package specs.admin.releaseNotes;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.admin.loginPage.AdminLoginPage;
import pageobjects.admin.releaseNotesPage.EditReleaseNotesPage;
import pageobjects.admin.releaseNotesPage.ReleaseNoteDetails;
import pageobjects.admin.releaseNotesPage.ReleaseNotesPage;
import pageobjects.user.activityPage.ColumnType;
import pageobjects.user.dashboardPage.Dashboard;
import specs.AdminAbstractSpec;
import util.EnvironmentType;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by dannyl on 2017-08-03.
 */
public class ReleaseNotes extends AdminAbstractSpec {

    @Before
    public void setUp(){
        if (hasLoggedIn() && getActiveEnvironment()!= EnvironmentType.LOCALADMIN) {
            new Dashboard(driver).navigateToReleaseNotesPage();
        }
        else {
            new AdminLoginPage(driver).loginAdmin()
                    .navigateToReleaseNotesPage();
        }
    }

    @After
    public void cleanUp(){
        try {
            new EditReleaseNotesPage(driver)
                    .deleteReleaseNote("Big Baller");
        }
        catch(Exception e){
        }
    }

    @Test
    public void canSaveReleaseNote(){
        String version = "0.0.5"+RandomStringUtils.randomAlphanumeric(6);
        String title = "Test Release Note " + RandomStringUtils.randomAlphanumeric(6);
        String releaseDay = "15";
        String overview = "Testing Comment " + RandomStringUtils.randomAlphanumeric(6);

        EditReleaseNotesPage EditReleaseNotesPage = new EditReleaseNotesPage(driver);
        new ReleaseNotesPage(driver)
                .goToCreateReleaseNotePage()
                .createReleaseNote(version, title, releaseDay, overview);

        Assert.assertTrue("Publish Button doesn't exist on this page, thus saving release note failed", EditReleaseNotesPage.checkCanSaveReleaseNote(title));

    }

    @Test
    public void canEditReleaseNote(){
        String version = "0.0.6"+RandomStringUtils.randomAlphanumeric(6);
        String title = "Test Release Note " + RandomStringUtils.randomAlphanumeric(6);
        String releaseDay = "15";
        String overview = "Testing Comment " + RandomStringUtils.randomAlphanumeric(6);

        String edittedTitle = "Big Baller Test Release Note " + RandomStringUtils.randomAlphanumeric(6);
        String edittedOverview = "Testing Comment " + RandomStringUtils.randomAlphanumeric(6);
        String edittedVersion = "0.0.6";
        String edittedReleaseDay = "18";

        ReleaseNoteDetails releaseNoteDetails = new ReleaseNoteDetails(driver);
        new ReleaseNotesPage(driver)
                .goToCreateReleaseNotePage()
                .createReleaseNote(version, title, releaseDay, overview)
                .navigateToReleaseNotesPage()
                .openReleaseNoteEdit(title)
                .editReleaseNote(edittedTitle, edittedOverview, edittedVersion, edittedReleaseDay);

        new ReleaseNotesPage(driver)
                .navigateToReleaseNotesPage()
                .searchForNote(edittedTitle);

        Assert.assertThat("Title does not match.", releaseNoteDetails.getTitle(), containsString(edittedTitle));
        Assert.assertThat("Overview does not match.", releaseNoteDetails.getOverview(), containsString(edittedOverview));
        Assert.assertThat("Version does not match.", releaseNoteDetails.getVersion(), containsString(edittedVersion));
        Assert.assertThat("Release Date does not match.", releaseNoteDetails.getReleaseDate(), containsString(edittedReleaseDay));

        new ReleaseNotesPage(driver)
                .navigateToReleaseNotesPage()
                .openReleaseNoteEdit(edittedTitle);

    }

    @Test
    public void canPublishReleaseNote(){
        String version = "0.0.7"+RandomStringUtils.randomAlphanumeric(6);;
        String title = "Test Release Note " + RandomStringUtils.randomAlphanumeric(6);
        String releaseDay = "15";
        String overview = "Testing Comment " + RandomStringUtils.randomAlphanumeric(6);

        EditReleaseNotesPage EditReleaseNotesPage = new EditReleaseNotesPage(driver);
        new ReleaseNotesPage(driver)
                .goToCreateReleaseNotePage()
                .createReleaseNote(version, title, releaseDay, overview)
                .publishReleaseNote();

        Assert.assertTrue("Release note not published properly", EditReleaseNotesPage.checkCanPublishReleaseNote(title));

    }


    @Test
    public void canDeleteReleaseNote(){
        String version = "0.0.8";
        String title = "Test Release Note " + RandomStringUtils.randomAlphanumeric(6);
        String releaseDay = "15";
        String overview = "Testing Comment " + RandomStringUtils.randomAlphanumeric(6);

       ReleaseNotesPage releaseNotesPage = new ReleaseNotesPage(driver)
                .goToCreateReleaseNotePage()
                .createReleaseNote(version, title, releaseDay, overview)
                .deleteReleaseNote(title);

        Assert.assertFalse("Release Note was not deleted properly", releaseNotesPage.checkCanDeleteReleaseNote(title).contains(title));

    }

    @Test
    public void canCancelDeleteReleaseNote(){
        String version = "0.0.9"+RandomStringUtils.randomAlphanumeric(6);
        String title = "Test Release Note " + RandomStringUtils.randomAlphanumeric(6);
        String releaseDay = "15";
        String overview = "Testing Comment " + RandomStringUtils.randomAlphanumeric(6);
        EditReleaseNotesPage editReleaseNotesPage = new EditReleaseNotesPage(driver);
        new ReleaseNotesPage(driver)
                .goToCreateReleaseNotePage()
                .createReleaseNote(version, title, releaseDay, overview)
                .cancelDeleteReleaseNote();

        Assert.assertThat("Cancelling deletion failed", editReleaseNotesPage.getTitleOnEditPage(), containsString("Edit Release Note"));
    }

    @Test
    public void canSearchForReleaseNote(){
        String version = "0.0.3"+RandomStringUtils.randomAlphanumeric(6);
        String title = "Test Release Note " + RandomStringUtils.randomAlphanumeric(6);
        String releaseDay = "15";
        String overview = "Testing Comment " + RandomStringUtils.randomAlphanumeric(6);

        ReleaseNoteDetails releaseNoteDetails = new ReleaseNoteDetails(driver);
        new ReleaseNotesPage(driver)
                .goToCreateReleaseNotePage()
                .createReleaseNote(version, title, releaseDay, overview)
                .navigateToReleaseNotesPage()
                .searchForNote(title);

        Assert.assertThat("Can't find press release", releaseNoteDetails.getTitle(), containsString(title));

        new ReleaseNotesPage(driver)
                .navigateToReleaseNotesPage()
                .openReleaseNoteEdit(title);
    }

    @Test
    public void sortReleaseNotesByDate(){
        ColumnType date = ColumnType.DATE;
        ReleaseNotesPage releaseNotesPage = new ReleaseNotesPage(driver).clickColumnHeader(date);
        Assert.assertTrue("Dates are not sorted correctly", releaseNotesPage.isColumnSorted(date));

    }

    @Test
    public void sortReleaseNotesByVersion() {
        ColumnType version = ColumnType.VERSION;
        ReleaseNotesPage releaseNotesPage = new ReleaseNotesPage(driver).clickColumnHeader(version);
        Assert.assertTrue("Versions are not sorted correctly", releaseNotesPage.isColumnSorted(version));
    }

    @Test
    public void sortReleaseNotesByStatus() {
        ColumnType status = ColumnType.STATUS;
        ReleaseNotesPage releaseNotesPage = new ReleaseNotesPage(driver).clickColumnHeader(status);
        Assert.assertTrue("Statuses are not sorted correctly", releaseNotesPage.isColumnSorted(status));
    }
}
