package testrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import specs.admin.companies.CompaniesList;
import specs.admin.companies.CompanyDetails;
import specs.admin.intelligence.IntelligenceList;
import specs.admin.intelligence.WTSReportDetails;
import specs.admin.loginPage.AdminLogin;
import specs.admin.morningCoffee.morningCoffeeReport;
import specs.admin.profiles.EditProfilesList;
import specs.admin.profiles.EnableDisableSubscriptions;
import specs.admin.releaseNotes.ReleaseNotes;
import specs.admin.users.UserRoles;

/**
 * Created by patrickp on 2016-09-20.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AdminLogin.class,
        CompaniesList.class,
        CompanyDetails.class,
        EditProfilesList.class,
        // AddEditImplementations.class,
        IntelligenceList.class,
        ReleaseNotes.class,
        WTSReportDetails.class,
        UserRoles.class,
        EnableDisableSubscriptions.class,
        morningCoffeeReport.class,

})

public class AdminSuite {
}
