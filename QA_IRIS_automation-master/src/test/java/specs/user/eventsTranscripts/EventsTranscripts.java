package specs.user.eventsTranscripts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.eventsTranscriptsPage.EventsTranscriptsPage;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by dannyl on 2017-08-08.
 */
public class EventsTranscripts extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUserCloseWelcomeNote()
                .accessSideNav()
                .selectEventsAndTranscriptsFromSideNav();
    }

    @Test
    public void canclickPeerListToggle() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("Did not click Peer List toggle properly", eventsTranscriptsPage.clickPeerListToggle());
    }

    @Test
    public void canFilterByDay() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

       // eventsTranscriptsPage.noEventsResults();
        Assert.assertTrue("Did not filter by day properly", eventsTranscriptsPage.filterByDay());
    }

    @Test
    public void canFilterByMonth() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("Did not filter by month properly", eventsTranscriptsPage.filterByMonth());
    }

    @Test
    public void canFilterByWeek() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("Did not filter by week properly", eventsTranscriptsPage.filterByWeek());
    }

    @Test
    public void canFilterByToday() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("Did not filter by today properly", eventsTranscriptsPage.filterByToday());
    }

    @Test
    public void canFilterByTranscript() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("Did not filter by transcript properly", eventsTranscriptsPage.filterByTranscript());
    }

    @Test
    public void canFilterByPreviousDateRange_Day() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("Did not filter by previous date range for Day properly",
                eventsTranscriptsPage.filterByDateRange_Day("previous"));
    }

    @Test
    public void canFilterByNextDateRange_Day() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("Did not filter by next date range for Day properly",
                eventsTranscriptsPage.filterByDateRange_Day("next"));
    }

    @Test
    public void canFilterByPreviousDateRange_Week() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("Did not filter by previous date range for Week properly",
                eventsTranscriptsPage.filterByDateRange_Week("previous"));
    }

    @Test
    public void canFilterByNextDateRange_Week() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("Did not filter by next date range for Week properly",
                eventsTranscriptsPage.filterByDateRange_Week("next"));
    }

    @Test
    public void canFilterByPreviousDateRange_Month() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("Did not filter by previous date range for Month properly",
                eventsTranscriptsPage.filterByDateRange_Month("previous"));
    }

    @Test
    public void canFilterByNextDateRange_Month() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("Did not filter by next date range for Month properly",
                eventsTranscriptsPage.filterByDateRange_Month("next"));
    }

    @Test
    public void canAddandDeleteSymbol() {
        final String ticker = "YUM";
        final String exchange = "NYSE";
        EventsTranscriptsPage eventAndTranscriptPage = new EventsTranscriptsPage(driver);
        eventAndTranscriptPage.addSymbol(ticker, exchange);
        Assert.assertTrue("Security " + ticker + " was not added", eventAndTranscriptPage.wasSecurityAdded(ticker));

        eventAndTranscriptPage.deleteSymbol();
        Assert.assertTrue("Security " + ticker + " was not deleted", eventAndTranscriptPage.wasSecurityDeleted());
    }

    @Test
    public void canSearchForEvent() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("Did not search for event properly", eventsTranscriptsPage.searchEvent());
    }

    @Test
    public void checkEventType_CallsEarnings() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        eventsTranscriptsPage.setEventTypeArray();
        eventsTranscriptsPage.uncheckAllEventTypes();

        Assert.assertTrue("Checking Calls - Earnings didn't filter properly", eventsTranscriptsPage.checkEventTypeCheckbox(0));
    }

    @Test
    public void checkEventType_CallsGuidance() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        eventsTranscriptsPage.setEventTypeArray();
        eventsTranscriptsPage.uncheckAllEventTypes();

        Assert.assertTrue("Checking Calls - Guidance didn't filter properly", eventsTranscriptsPage.checkEventTypeCheckbox(1));
    }

    @Test
    public void checkEventType_CallsSalesAndRevenue() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        eventsTranscriptsPage.setEventTypeArray();
        eventsTranscriptsPage.uncheckAllEventTypes();

        Assert.assertTrue("Checking Calls - Sales and Revenue didn't filter properly", eventsTranscriptsPage.checkEventTypeCheckbox(2));
    }

    @Test
    public void checkEventType_ReleasesEarnings() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        eventsTranscriptsPage.setEventTypeArray();
        eventsTranscriptsPage.uncheckAllEventTypes();

        Assert.assertTrue("Checking Releases - Earnings didn't filter properly", eventsTranscriptsPage.checkEventTypeCheckbox(3));
    }

    @Test
    public void checkEventType_ReleasesSalesAndRevenue() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        eventsTranscriptsPage.setEventTypeArray();
        eventsTranscriptsPage.uncheckAllEventTypes();

        Assert.assertTrue("Checking Releases - Sales and Revenue didn't filter properly", eventsTranscriptsPage.checkEventTypeCheckbox(4));
    }

    @Test
    public void checkEventType_MiscConferences() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        eventsTranscriptsPage.setEventTypeArray();
        eventsTranscriptsPage.uncheckAllEventTypes();

        Assert.assertTrue("Checking Misc - Conferences didn't filter properly", eventsTranscriptsPage.checkEventTypeCheckbox(5));
    }

    @Test
    public void checkEventType_MiscPresentations() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        eventsTranscriptsPage.setEventTypeArray();
        eventsTranscriptsPage.uncheckAllEventTypes();

        Assert.assertTrue("Checking Misc - Presentations didn't filter properly", eventsTranscriptsPage.checkEventTypeCheckbox(6));
    }

    @Test
    public void checkEventType_MiscMeetings() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        eventsTranscriptsPage.setEventTypeArray();
        eventsTranscriptsPage.uncheckAllEventTypes();

        Assert.assertTrue("Checking Misc - Meetings didn't filter properly", eventsTranscriptsPage.checkEventTypeCheckbox(7));
    }

    @Test
    public void checkEventType_MiscOther() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        eventsTranscriptsPage.setEventTypeArray();
        eventsTranscriptsPage.uncheckAllEventTypes();

        Assert.assertTrue("Checking Misc - Other didn't filter properly", eventsTranscriptsPage.checkEventTypeCheckbox(8));
    }

    @Test
    public void checkEventDetailsContainer() {
        EventsTranscriptsPage eventsTranscriptsPage = new EventsTranscriptsPage(driver);

        Assert.assertTrue("The Event details container didn't open correctly.", eventsTranscriptsPage.checkEventDetailContainer());
    }
}

