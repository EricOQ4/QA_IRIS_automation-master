package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.WebAnalytics.WebAnalytics;
//import specs.user.activism.Activism;
import specs.user.activity.*;
import specs.user.advancedSearch.AdvancedSearchNavigation;
import specs.user.advancedSearch.AdvancedSearching;
import specs.user.briefingBooks.CreateBriefingBook;
import specs.user.briefingBooks.EditBriefingBook;
import specs.user.briefingBooks.GenerateBriefingBook;
import specs.user.contacts.ContactDetails;
import specs.user.contacts.ContactList;
import specs.user.dashboard.DashboardLinks;
import specs.user.dashboard.DashboardLogActivity;
import specs.user.dashboard.DashboardSearch;
import specs.user.desktopSubscriptions.DesktopSubscriptions;
import specs.user.estimates.Estimates;
import specs.user.eventsTranscripts.EventsTranscripts;
import specs.user.eventsTranscripts.EventsTranscriptsList;
import specs.user.header.header;
import specs.user.login.ForgotPassword;
import specs.user.login.UserLogin;
import specs.user.ownership.Ownership;
import specs.user.research.ResearchList;
import specs.user.securityDetails.Overview;
import specs.user.sentiment.Sentiment;
import specs.user.sideNavBar.TabNavigationExpanded;
//import specs.user.targeting.TargetingList;
import specs.user.team.Q4TeamPage;
import specs.user.volatility.Volatility;
import specs.user.watchlist.EditWatchlist;
import specs.user.webcastAnalytics.WebcastAnalytics;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LogActivity.class,
        FilterActivity.class,
        ActivityDetails.class,
        BulkActivityActions.class,
        ContactList.class,
        ContactDetails.class,
        DashboardLinks.class,
        DashboardLogActivity.class,
        DashboardSearch.class,
        DesktopSubscriptions.class,
        Estimates.class,

        EventsTranscriptsList.class,
        EventsTranscripts.class,
        ForgotPassword.class,
        UserLogin.class,
        Overview.class,
        TabNavigationExpanded.class,
        EditWatchlist.class,
        Q4TeamPage.class,
        SearchForActivity.class,
//        TargetingList.class,
        ResearchList.class,
        Ownership.class,

        CreateBriefingBook.class,
        EditBriefingBook.class,
        GenerateBriefingBook.class,
        header.class,
//        Activism.class,
        Sentiment.class,
        Volatility.class,
        AdvancedSearching.class,
        AdvancedSearchNavigation.class,
        WebcastAnalytics.class,
        WebAnalytics.class
})

public class NightRun {
}
