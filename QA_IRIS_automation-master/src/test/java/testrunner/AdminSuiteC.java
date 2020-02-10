package testrunner;

/**
 * Created by noelc on 2016-12-07.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.admin.intelligence.WTSReportDetails;
import specs.admin.morningCoffee.morningCoffeeReport;
import specs.admin.profiles.EnableDisableSubscriptions;
import specs.admin.users.UserRoles;
import specs.user.activity.ActivityDetails;
import specs.user.briefingBooks.CreateBriefingBook;
import specs.user.contacts.ContactDetails;
import specs.user.contacts.ContactList;
import specs.user.dashboard.DashboardLogActivity;
//import specs.user.targeting.TargetingList;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        WTSReportDetails.class,
        UserRoles.class,
        EnableDisableSubscriptions.class,
        morningCoffeeReport.class,

})
public class AdminSuiteC {
}
