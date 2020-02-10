package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.admin.intelligence.IntelligenceList;
import specs.admin.profiles.EditProfilesList;
import specs.admin.releaseNotes.ReleaseNotes;
import specs.user.activity.SearchForActivity;
import specs.user.sideNavBar.TabNavigationExpanded;
import specs.user.team.Q4TeamPage;
import specs.user.watchlist.EditWatchlist;

/**
 * Created by noelc on 2016-12-07.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        EditProfilesList.class,
        // AddEditImplementations.class,
        IntelligenceList.class,
        ReleaseNotes.class,
})
public class AdminSuiteB {
}
