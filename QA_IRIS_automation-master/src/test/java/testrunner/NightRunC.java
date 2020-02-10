package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.user.advancedSearch.AdvancedSearchNavigation;
import specs.user.advancedSearch.AdvancedSearching;
import specs.user.briefingBooks.CreateBriefingBook;
import specs.user.briefingBooks.EditBriefingBook;
import specs.user.briefingBooks.GenerateBriefingBook;
import specs.user.dashboard.DashboardSearch;
import specs.user.header.header;
import specs.user.login.ForgotPassword;
import specs.user.login.UserLogin;
import specs.user.ownership.Ownership;
import specs.user.research.ResearchList;
import specs.user.securityDetails.Overview;
import specs.user.sentiment.Sentiment;
//import specs.user.targeting.TargetingList;
import specs.user.volatility.Volatility;
import specs.user.webcastAnalytics.WebcastAnalytics;

/**
 * Created by noelc on 2016-12-07.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
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
        ResearchList.class,
        Ownership.class
})
public class NightRunC {
}
