package testrunner;

/**
 * Created by andyp on 2017-07-07.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.admin.companies.CompaniesList;
import specs.admin.companies.CompanyDetails;
import specs.admin.loginPage.AdminLogin;
import specs.user.activity.ActivityDetails;
import specs.user.activity.SearchForActivity;
import specs.user.sideNavBar.TabNavigationExpanded;
import specs.user.team.Q4TeamPage;
import specs.user.watchlist.EditWatchlist;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AdminLogin.class,
        CompaniesList.class,
        CompanyDetails.class,

})
public class AdminSuiteA {
}
