package specs.user.activity;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.*;
import pageobjects.user.activityPage.ActivityPage;
import pageobjects.user.activityPage.FilterType;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.sideNavBar.SideNavBar;
import specs.AbstractSpec;

/**
 * Created by zacharyk on 2017-07-17.
 */

/**
 * Updated by danielh on 2019-04-29.
 */
public class BulkActivityActions extends AbstractSpec {
    public final String keyword = "**BULK DELETE**";

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectActivityPageFromSideNav();
    }

    /**
     * Both tests, verify bulk deletion and canceling of bulk Deletion
     */
    //PreCondition: No pre-existing titles of **BULK DELETE** in order to pass
    //Creates 5 activities then bulk deletes them
    @Test
    public void createAndBulkDeleteNotes() {
        String comment, note, tag;

        ActivityPage activityPage = new ActivityPage(driver);


        for (int i=0; i<5; i++) {
            comment = keyword + "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
            note = "Test note" + RandomStringUtils.randomAlphanumeric(6);
            tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

            activityPage.logNote()
                    .enterNoteDetails(comment, note, tag)
                    .postActivity(FilterType.NOTE)
                    .saveActivity();

            activityPage.accessSideNavFromPage().selectActivityPageFromSideNav();
        }

        activityPage.searchForNote(keyword);

        for (int i=0; i<3; i++) {
            activityPage.clickNthActivityCheckBox(i);
        }


        activityPage.clickDeleteButton();
        Assert.assertEquals(2, activityPage.getNumberOfDisplayedActivities());
        activityPage.deleteAllNotes(keyword);
        Assert.assertTrue("Activities were not deleted successfully", activityPage.noResults());

    }


    //cancels bulk deletions, all 5 activities created should remain
    //then actually bulk deletes the activities and ensures that 0 activities remain
    @Test
    public void cancelBulkDelete() {

        String comment, note, tag;

        ActivityPage activityPage = new ActivityPage (driver);


        for (int x=0; x<5;x++) {

            comment = keyword + "This is a test comment" + RandomStringUtils.randomAlphanumeric(6);
            note = "Test note" + RandomStringUtils.randomAlphanumeric(6);
            tag = "TestTag" + RandomStringUtils.randomAlphanumeric(6);

            activityPage.logNote()
                    .enterNoteDetails(comment, note, tag)
                    .postActivity(FilterType.NOTE)
                    .saveActivity();

            activityPage.accessSideNavFromPage().selectActivityPageFromSideNav();

        }
        activityPage.cancelBulkDelete(keyword+"This is a test comment");

        Assert.assertEquals("Activities were deleted when Cancel action was used", 5, activityPage.getNumberOfDisplayedActivities());

        activityPage.clickDeleteButton(); //this part basically acts as a form of clean up, will actually delete all the Automation activities created

        Assert.assertTrue("Activities were not deleted successfully", activityPage.noResults());


    }
}
