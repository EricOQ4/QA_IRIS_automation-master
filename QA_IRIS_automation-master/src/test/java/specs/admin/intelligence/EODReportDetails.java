package specs.admin.intelligence;

import org.junit.Assert;
import org.junit.Test;
import pageobjects.admin.homePage.HomePage;
import pageobjects.admin.intelligencePage.EODReportDetailsPage;
import pageobjects.admin.loginPage.AdminLoginPage;
import specs.AdminAbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

public class EODReportDetails extends AdminAbstractSpec {
    private static String company = "SYY";
    double delta = 0.1;

    /*@BeforeClass
    public static void setup() {
        String pageTitle = "Home";
        HomePage homePage = new HomePage(driver);
        new AdminLoginPage(driver).customLoginAdmin("test@q4websystems.com", "testing!");
        Assert.assertThat("Dashboard not visible after successful login attempt", homePage.getAdminPageTitle(), containsString(pageTitle));
        new EODReportDetailsPage(driver).openEODPage();
        new EODReportDetailsPage(driver).addEODReport(company);
    }

    @Before
    public void openEOD() {
        String pageTitle = "Home";
        HomePage homePage = new HomePage(driver);
        new AdminLoginPage(driver).customLoginAdmin("test@q4websystems.com", "testing!");
        Assert.assertThat("Dashboard not visible after successful login attempt", homePage.getAdminPageTitle(), containsString(pageTitle));
        new EODReportDetailsPage(driver).openEODPage();
        new EODReportDetailsPage(driver).viewEODReport();
    }

    @Test
    public void priceChangeCheck() {
        Assert.assertTrue(new EODReportDetailsPage(driver).CHGCheck("priceCHG", delta));
    }

    @Test
    public void percentChangeCheck() {
        Assert.assertTrue(new EODReportDetailsPage(driver).CHGCheck("percentCHG", delta));
    }

    @Test
    public void percentQTDChangeCheck() {
        Assert.assertTrue(new EODReportDetailsPage(driver).CHGCheck("percentQTDCHG", delta));
    }

    @Test
    public void percentYTDChangeCheck() {
        Assert.assertTrue(new EODReportDetailsPage(driver).CHGCheck("percentYTDCHG", delta));
    }

    @AfterClass
    public static void cleanup() {
        String pageTitle = "Home";
        HomePage homePage = new HomePage(driver);
        new AdminLoginPage(driver).customLoginAdmin("test@q4websystems.com", "testing!");
        Assert.assertThat("Dashboard not visible after successful login attempt", homePage.getAdminPageTitle(), containsString(pageTitle));
        new EODReportDetailsPage(driver).openEODPage();
        new EODReportDetailsPage(driver).viewEODReport();
        new EODReportDetailsPage(driver).teardown();
    }*/



    @Test
    public void checkPeerAverages() {
        String pageTitle = "Home";
        HomePage homePage = new HomePage(driver);
        new AdminLoginPage(driver).customLoginAdmin("test@q4websystems.com", "testing!");
        Assert.assertThat("Dashboard not visible after successful login attempt", homePage.getAdminPageTitle(), containsString(pageTitle));
        new EODReportDetailsPage(driver).openEODPage();
        new EODReportDetailsPage(driver).addEODReport(company);
        new EODReportDetailsPage(driver).viewEODReport();
        Assert.assertTrue(new EODReportDetailsPage(driver).CHGCheck("priceCHG", delta));
        Assert.assertTrue(new EODReportDetailsPage(driver).CHGCheck("percentCHG", delta));
        Assert.assertTrue(new EODReportDetailsPage(driver).CHGCheck("percentQTDCHG", delta));
        Assert.assertTrue(new EODReportDetailsPage(driver).CHGCheck("percentYTDCHG", delta));
        //new EODReportDetailsPage(driver).teardown();
    }
}
