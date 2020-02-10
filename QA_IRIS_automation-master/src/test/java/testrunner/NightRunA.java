package testrunner;

/**
 * Created by noelc on 2016-12-07.
 *
 */
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.activity.*;
import specs.user.contacts.ContactDetails;
import specs.user.briefingBooks.CreateBriefingBook;
import specs.user.briefingBooks.EditBriefingBook;
import specs.user.contacts.ContactList;
import specs.user.dashboard.DashboardLinks;
import specs.user.dashboard.DashboardLogActivity;
import specs.user.dashboard.DashboardSearch;
import specs.user.desktopSubscriptions.DesktopSubscriptions;
import specs.user.estimates.Estimates;
import specs.user.research.ResearchList;
import specs.user.ownership.Ownership;
import specs.user.sideNavBar.TabNavigationExpanded;
//import specs.user.targeting.TargetingList;
import specs.user.team.Q4TeamPage;
import specs.user.watchlist.EditWatchlist;

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
        Estimates.class

})


public class NightRunA {


}
