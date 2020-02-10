package specs.admin.morningCoffee;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.admin.loginPage.AdminLoginPage;
import pageobjects.admin.morningCoffeePage.Market;
import pageobjects.admin.morningCoffeePage.MorningCoffeePage;
import pageobjects.admin.morningCoffeePage.MorningCoffeePreview;
import pageobjects.admin.morningCoffeePage.Sector;
import pageobjects.user.dashboardPage.Dashboard;
import specs.AdminAbstractSpec;
import util.EnvironmentType;

import java.util.Date;

/**
 * Created by noelc on 2017-01-16.
 */
public class morningCoffeeReport extends AdminAbstractSpec {


    @Before
    public void setUp() {
            if (hasLoggedIn() && getActiveEnvironment()!= EnvironmentType.LOCALADMIN) {
                    new Dashboard(driver).navigateToMorningCoffeePage();
                }
                else {
                    new AdminLoginPage(driver).loginAdmin().navigateToMorningCoffeePage();
                }
    }



    @Test
    public void canCreateReport(){
        String symbol ="AAPL";
        Date currentDate = new Date();
       MorningCoffeePage morningCoffeePage =  new MorningCoffeePage(driver);
       morningCoffeePage.clickAddReport().inputCompanySymbol(symbol).clickCreateReport();
        Assert.assertTrue("Record not found",morningCoffeePage.recentReportExists(symbol,currentDate)); //current Date is used to check that is the report from today, no other way to identify a unique report.

    }

    @Test
    public void canCancelAReportGeneration(){
        String symbol ="NFLX";
        Date currentDate = new Date();
        MorningCoffeePage morningCoffeePage =  new MorningCoffeePage(driver);
        morningCoffeePage.clickAddReport().inputCompanySymbol(symbol).clickCancelReport();
        Assert.assertFalse("Record was found",morningCoffeePage.recentReportExists(symbol,currentDate));
    }

    @Test
    public void canPreviewNewlyCreatedReport(){
        String symbol ="YUM";
        Date currentDate = new Date();
        MorningCoffeePage morningCoffeePage =  new MorningCoffeePage(driver);
        Assert.assertTrue("Preview of report didn't load",
                morningCoffeePage
                        .clickAddReport()
                        .inputCompanySymbol(symbol)
                        .clickCreateReport()
                        .clickRecentReport(symbol, currentDate)
                        .previewPageLoaded());
    }

    @Test
    public void canRemoveEmailFromMailingList(){
        String name ="Patrick Priestley";
        String symbol ="SYY";
        Date currentDate = new Date();
        MorningCoffeePage morningCoffeePage =  new MorningCoffeePage(driver);
        MorningCoffeePreview morningCoffeePreview= morningCoffeePage.clickAddReport().inputCompanySymbol(symbol).clickCreateReport().clickRecentReport(symbol, currentDate).clickMailIcon();
        Assert.assertTrue("Required email not present for test",morningCoffeePreview.nameIsPresentInMailingList(name));
        morningCoffeePreview.removeNameFromMailingList(name);
        Assert.assertFalse("Name not successfuly removed",morningCoffeePreview.nameIsPresentInMailingList(name));
    }
    @Test
    public void stockSummaryPresentInReportPreview(){
        String symbol ="SYY";
        Date currentDate = new Date();
        MorningCoffeePage morningCoffeePage =  new MorningCoffeePage(driver);
       Assert.assertTrue("Stock summary not present",morningCoffeePage.clickAddReport().inputCompanySymbol(symbol).clickCreateReport().clickRecentReport(symbol, currentDate).stockSummaryPresent());
    }

    @Test
    public void ownerCanBeSortedAscending(){
        MorningCoffeePage morningCoffeePage = new MorningCoffeePage(driver);
       Assert.assertTrue("Not sorted", morningCoffeePage.clickOwnerHeader().isOwnerSortedAscending());
    }

    @Test
    public void addMarketCommentary(){
        String commentary = "Canada commentary added via automation!";
        MorningCoffeePage morningCoffeePage = new MorningCoffeePage(driver);
        morningCoffeePage.clickCommentaryTab().clickMarketSegment().createCommentary(Market.CANADA,commentary);
        Assert.assertTrue("Commentary not added",morningCoffeePage.returnMarketCommentary(Market.CANADA).equals(commentary));


    }

    @Test
    public void addSectorCommentary(){
        String commentary ="Energy commentary added via automation";
        MorningCoffeePage morningCoffeePage = new MorningCoffeePage(driver);
        morningCoffeePage.clickCommentaryTab().clickSectorSegment().createCommentary(Sector.ENERGY,commentary);
       Assert.assertTrue("Commentary not added",morningCoffeePage.returnSectorCommentary(Sector.ENERGY).equals(commentary));

    }

    @Test
    public void editSectorCommentarty(){
        Date currentDate = new Date();
        String commentary = "Consumer staples sector was edited via automation "+currentDate.toString();
        MorningCoffeePage morningCoffeePage = new MorningCoffeePage(driver);
        morningCoffeePage.clickCommentaryTab().clickSectorSegment().editSectorCommentary(Sector.CONSUMER_STAPLES,commentary).saveEditedCommentary();
        Assert.assertTrue("Commentary not added - Known issue ADMIN-817 ",morningCoffeePage.returnSectorCommentary(Sector.CONSUMER_STAPLES).equals(commentary));

    }

    @Test
    public void editMarketCommentarty(){
        Date currentDate = new Date();
        String commentary = "United Kingdom Market Commentary was edited via automation "+currentDate.toString();
        MorningCoffeePage morningCoffeePage = new MorningCoffeePage(driver);
        morningCoffeePage.clickCommentaryTab()
                .clickMarketSegment()
                .editMarketCommentary(Market.CANADA,commentary)
                .saveEditedCommentary();
        Assert.assertTrue("Commentary not added - Known issue ADMIN-817 ",morningCoffeePage.returnMarketCommentary(Market.CANADA).equals(commentary));

    }

    @Test
    public void canFilterOnlyMarkets(){
        MorningCoffeePage morningCoffeePage = new MorningCoffeePage(driver);
      Assert.assertTrue("Not filtering table to be Market commentaries only",morningCoffeePage.clickCommentaryTab().clickMarketSegment().onMarketTab());

    }

    @Test
    public void canFilterOnlySectors(){
        MorningCoffeePage morningCoffeePage = new MorningCoffeePage(driver);
        Assert.assertFalse("Market commentaries present on sector commentary page",morningCoffeePage.clickCommentaryTab().clickSectorSegment().marketTypesPresent());


    }

    @Test
    public void canDeleteMorningCoffeeReport(){
        String symbol ="CSCO";
        Date currentDate = new Date();
        MorningCoffeePage morningCoffeePage =  new MorningCoffeePage(driver);
        morningCoffeePage.clickAddReport().inputCompanySymbol(symbol).clickCreateReport();
        Date dateOfLatestReport =morningCoffeePage.getRecentReportDate();
        MorningCoffeePreview morningCoffeePreview = morningCoffeePage.clickRecentReport(symbol,currentDate);
        morningCoffeePage=morningCoffeePreview.confirmDelete();
        Assert.assertTrue("Report not deleted",morningCoffeePage.confirmReportDelete("SYY",dateOfLatestReport));
    }

    @Test
    public void canEditMorningCoffeeReportMailing(){
        String symbol ="FB";
        Date currentDate = new Date();
        MorningCoffeePage morningCoffeePage =  new MorningCoffeePage(driver);
        morningCoffeePage.clickAddReport().inputCompanySymbol(symbol).clickCreateReport();
        Date dateOfLatestReport =morningCoffeePage.getRecentReportDate();
        MorningCoffeePreview morningCoffeePreview = morningCoffeePage.clickRecentReport(symbol,currentDate).clickMailIcon();
    }

    @Test
    public void canSearchForCommentary(){
        String search="Canada commentary added via automation!";
        MorningCoffeePage morningCoffeePage = new MorningCoffeePage(driver);
        Assert.assertTrue("Search query not found",morningCoffeePage.clickCommentaryTab().inputSearch(search).findSearchQuery(search));
    }

    @Test
    public void stillFilteringBySectorAfterCreation(){
        String commentary ="Financial commentary added via automation";
        MorningCoffeePage morningCoffeePage = new MorningCoffeePage(driver);
        morningCoffeePage.clickCommentaryTab().clickSectorSegment().createCommentary(Sector.FINANCIALS,commentary);
        Assert.assertFalse("Market commentaries present on sector commentary page after adding a sector",morningCoffeePage.marketTypesPresent());
    }

    @Test
    public void userCanEditCommentaryForSpecificReport(){
        String symbol ="GE";
        Date currentDate = new Date();
        String editText = " EDITED "+currentDate.toString()+" ";
        MorningCoffeePage morningCoffeePage =  new MorningCoffeePage(driver);
        MorningCoffeePreview morningCoffeePreview= morningCoffeePage.clickAddReport().inputCompanySymbol(symbol).clickCreateReport().clickRecentReport(symbol, currentDate).typeInMarketCommentary(editText).clickSaveIcon();
        driver.navigate().back();
        driver.navigate().refresh();
        morningCoffeePage = new MorningCoffeePage(driver);
        morningCoffeePreview = morningCoffeePage.clickRecentReport(symbol, currentDate);
        Assert.assertTrue("Commentary not saved",morningCoffeePreview.returnMarketCommentary().contains(editText));
    }

}
