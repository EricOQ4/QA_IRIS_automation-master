package specs.user.securityDetails;

import org.junit.*;
import org.openqa.selenium.By;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.securityPage.SecurityOverviewPage;
import specs.AbstractSpec;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

/**
 * Created by kelvint on 11/2/16.
 */
public class Overview extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectSecurityFromSideNav();
    }

    @Ignore // yahoo stock api doesn't exist anymore, will need to replace with another free service.
    @Test
    public void checkStockPrice() throws IOException {
        SecurityOverviewPage finish = new SecurityOverviewPage(driver);

        Stock stock = YahooFinance.get("SYY");
        float price = stock.getQuote().getPrice().floatValue();
        System.out.println(price);

        Assert.assertEquals("Stock price does not match expected", price, finish.getStockPrice(), 0.5);
    }

    @Test
    /**   Combines Test Cases C490 -> C492    */
    public void companyDataCorrect() {
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);

        String[] companyData = {"Sysco Corp", "SYY", "Food Distributors\nNYSE"};

        Assert.assertEquals(securityOverviewPage.getCompanyName(), companyData[0]);
        Assert.assertEquals(securityOverviewPage.getCompanyTicker(), companyData[1]);
        Assert.assertEquals(securityOverviewPage.getIndustry_Exchange(), companyData[2]);
    }

    @Test
    /**    Test Case C493    */
    public void correctStockFormat() {
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);

        String stockQuote = securityOverviewPage.getStockQuote();

        //To make it into a pure integer. If this fails, a number format exception would appear.
        Double.parseDouble(stockQuote.replace(".", ""));

        //Checking if it is indeed two decimal places
        Assert.assertEquals("Stock Quote does not have a decimal point where it should be (2 decimal places):" , "."
                ,stockQuote.substring((stockQuote.length() - 3), (stockQuote.length() - 2)));

        String stockChange = securityOverviewPage.getStockChange();
        String firstChange = "";
        String secondChange = "";

        //See the chart below the if statements to understand the logic a bit better.
        if (stockChange.length() == 13 && stockChange.substring(0, 1).equals("-")) { //The -> " " one
            firstChange = stockChange.substring(0, 5);
            secondChange = stockChange.substring(6, 14);
        }
        else if (12 <= stockChange.length() && stockChange.length() <= 14) { //The -> "(" ones
            firstChange = stockChange.substring(0, stockChange.length() / 2 - 2);
            secondChange = stockChange.substring(stockChange.length() / 2 - 1, stockChange.length());
        } else if (stockChange.length() < 12 || 14 < stockChange.length()) { //All other invalid cases
            Assert.assertTrue(false);
        }
        /*
                    12 - 14 chars, depending if negative or positive.
                    -0.80 (-1.16%) /2 = 7  -> "("
                    -0.80 (1.16%) /2 = 6  -> " "
                    0.80 (-1.16%) /2 = 6 -> "("
                    0.80 (1.16%)  /2 = 6 -> "("
        */

        Double.parseDouble(firstChange);
        double firstChangeNumber = Double.parseDouble(firstChange.replaceAll("[()%]", ""));
        double secondChangeNumber = Double.parseDouble(secondChange.replaceAll("[()%]", ""));
        //^^replace the chars within [ ]

        //All tests regarding format
        Assert.assertEquals(secondChange.substring(0, 1), "(");
        Assert.assertEquals(secondChange.substring(secondChange.length() - 1, secondChange.length()), ")");
        Assert.assertEquals(secondChange.substring(secondChange.length() - 2, secondChange.length() - 1), "%");

        //Checking change icon color (not arrow)
        if (secondChangeNumber > 0 && firstChangeNumber > 0) {
            Assert.assertEquals(securityOverviewPage.getChangeIconColor(), "462041131"); //These numbers are rgba in a single int val
        } else if (secondChangeNumber < 0 || firstChangeNumber < 0) {
            Assert.assertEquals(securityOverviewPage.getChangeIconColor(), "23176601"); //Formatted in getChangeIconColor method
        }
    }

    @Test

    /**Test Case C494*/
    public void correctVolumeFormat() {
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);

        String volume = securityOverviewPage.getVolume();
        String avgVolume = securityOverviewPage.getAvgVolume();
        int volumeValue = Integer.parseInt(volume.replaceAll(",", ""));
        int avgVolumeValue = Integer.parseInt(avgVolume.replaceAll(",", ""));
        int volumeSize = volume.length();
        int avgVolumeSize = avgVolume.length();

        for (Double x = new Double(0); x < volumeSize; x++) { //Checks if there is a comma for every 3 numbers for Volume
            if (x%4 == 0 && x != 0) { //Checking the double is a whole number
                Assert.assertEquals(volume.substring(volumeSize - x.intValue(), volumeSize - (x.intValue() - 1)), ",");
            }
            else if (volume.substring(volumeSize - (x.intValue() + 1),volumeSize - x.intValue()) == ",") {
                Assert.assertFalse(true);
            }
        }

        for (Double x = new Double(0); x < avgVolumeSize; x++) { //Same as above for avg Volume
            if (x%4 == 0 && x != 0) { //Checking the double is a whole number
                Assert.assertEquals(avgVolume.substring(avgVolumeSize - x.intValue()
                        ,avgVolumeSize - (x.intValue() - 1)), ",");
            }
            else if (avgVolume.substring(avgVolumeSize - (x.intValue() + 1),avgVolumeSize - x.intValue()) == ",") {
               Assert.assertFalse(true);
            }
        }

        if (volumeValue < 0  || avgVolumeValue < 0 ) { //Checking for negatives
            Assert.assertTrue(false);
        }
    }

    @Test
    public void dropdownExists() {
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver).clickViewDropdownMenu();

        Assert.assertTrue("Dropdown menu modal does not exist or open", securityOverviewPage.dropdownMenuExists());

        securityOverviewPage.clickDropdownOverview().clickCoordinate(By.className("company-name") ,537, 145);
        //nothing should happen. 537, 145 -> Clicking outside modal

        Assert.assertFalse("Dropdown menu modal did not close", securityOverviewPage.dropdownMenuExists());
        Assert.assertTrue(securityOverviewPage.overviewPageExists());
    }

    @Test
    public void navigationViaArrows() {
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver).clickDropdownLeftArrowOverview();

        Assert.assertTrue(securityOverviewPage.overviewPageExists());

        securityOverviewPage.clickDropdownRightArrowOverview();

        Assert.assertTrue("Right arrow dropdown click did not take test to Ownership page"
                ,securityOverviewPage.ownershipPageExists());

        securityOverviewPage.clickDropdownLeftArrowOverview();

        Assert.assertTrue("Left arrow dropdown click did not return test to Overview page",
                securityOverviewPage.overviewPageExists());
    }

    @Test
    public void navigationViaDropdown() {
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver).clickViewDropdownMenu();
        securityOverviewPage.clickDropdownOwnership();

        Assert.assertTrue("Ownership dropdown option did not take test to Ownership page"
                ,securityOverviewPage.ownershipPageExists());

        securityOverviewPage.clickViewDropdownMenu().clickDropdownOverview();

        Assert.assertTrue("Overview dropdown option did not return test to Overview page"
                ,securityOverviewPage.overviewPageExists());
    }

    /**     Tests below refer to tests regarding the Header buttons in Security     */

    @Test
    public void compareEstimatesNumToActual() {
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);

        int estimatesButtonNum = Integer.parseInt(securityOverviewPage.getRecentEstimatesButtonNumber());
        securityOverviewPage.clickRecentEstimatesButton();
        int actualEstimatessNum = securityOverviewPage.getNumEstimatesResultsDisplayed();

        Assert.assertEquals("Number shown in recent estimates button does not correspond with actual results"
                ,estimatesButtonNum,actualEstimatessNum);
    }

    @Test
    public void estimatesButtonWorks(){
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);

        if(Integer.parseInt((securityOverviewPage.getRecentEstimatesButtonNumber())) == 0) {
            securityOverviewPage.clickRecentEstimatesButton();
            Assert.assertTrue("Clicking the Recent Estimates Button failed to open the modal."
                    , securityOverviewPage.recentEstimatesModalExists());
            securityOverviewPage.clickCoordinate(By.className("company-name"), 99, 223);
            Assert.assertFalse("Recent Estimates modal failed to close."
                    , securityOverviewPage.recentEstimatesModalExists());
        }
        else {
            securityOverviewPage.clickRecentEstimatesButton();
            Assert.assertTrue("Clicking the Recent Estimates Button failed to open the modal."
                    , securityOverviewPage.recentEstimatesModalExists());
            securityOverviewPage.clickRecentEstimatesResult();
            Assert.assertTrue("Clicking on a Recent Estimates result did not redirect to Estimates page"
            , securityOverviewPage.estimatesPageExists());
            securityOverviewPage.goBackPages(1);
            Assert.assertTrue(securityOverviewPage.overviewPageExists());
        }
    }

    @Test
    public void compareEventsNumToActual() {
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);

        int eventsButtonNum = Integer.parseInt(securityOverviewPage.getRecentEventsButtonNumber());
        securityOverviewPage.clickRecentEventsButton();
        int actualEventssNum = securityOverviewPage.getNumEventsResultsDisplayed();

        Assert.assertEquals("Number shown in recent events button does not correspond with actual results"
                ,eventsButtonNum,actualEventssNum);
    }

    @Ignore
    @Test //Waiting to find one that has events before writing
    public void eventsButtonWorks(){
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);

        if(Integer.parseInt(securityOverviewPage.getRecentEventsButtonNumber()) == 0) {

        }
        else {

        }
    }

    @Test
    public void compareTranscriptsNumToActual() {
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);

        int transcriptsButtonNum = Integer.parseInt(securityOverviewPage.getRecentTranscriptsButtonNumber());
        securityOverviewPage.clickRecentTranscriptsButton();
        int actualTranscriptsNum = securityOverviewPage.getNumTranscriptsResultsDisplayed();

        Assert.assertEquals("Number shown in recent transcripts button does not correspond with actual results"
                ,transcriptsButtonNum,actualTranscriptsNum);
    }

    @Test
    public void transcriptsButtonWorks() {
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);

        if(Integer.parseInt((securityOverviewPage.getRecentTranscriptsButtonNumber())) == 0) {
            securityOverviewPage.clickRecentTranscriptsButton();
            Assert.assertTrue("Clicking the Recent Transcripts Button failed to open the modal."
                    , securityOverviewPage.recentTranscriptsModalExists());
            securityOverviewPage.clickCoordinate(By.className("company-name"), 99, 223);
            Assert.assertFalse("Recent Transcripts modal failed to close."
                    , securityOverviewPage.recentTranscriptsModalExists());
        }
        else {
            securityOverviewPage.clickRecentTranscriptsButton();
            Assert.assertTrue("Clicking the Recent Transcripts Button failed to open the modal."
                    , securityOverviewPage.recentTranscriptsModalExists());
            securityOverviewPage.clickRecentTranscriptsResults();
            Assert.assertTrue("Clicking on a recent transcript fails to open the modal for it."
                    , securityOverviewPage.recentTranscriptsResultsModalExists());
            securityOverviewPage.clickTrancsriptsResultsXBtn();

            Assert.assertTrue("Clicking the 'X' in the result modal did not close it."
                    , securityOverviewPage.recentTranscriptsModalExists());
            securityOverviewPage.clickCoordinate(By.className("company-name"), 99, 223);
            Assert.assertTrue("Recent Transcripts modal failed to close."
                    , !securityOverviewPage.recentTranscriptsModalExists());
        }
    }

    @Ignore
    @Test //To come up with test plan before writing
    public void transcriptsDataOpensNewTab(){
    }

    @Test
    public void compareNewsNumToActual() {
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);

        int newsButtonNum = Integer.parseInt(securityOverviewPage.getRecentNewsButtonNumber());
        securityOverviewPage.clickRecentNewsButton();
        int actualNewsNum = securityOverviewPage.getNumNewsResultsDisplayed();

        Assert.assertEquals("Number shown in recent news button does not correspond with actual results"
                ,newsButtonNum,actualNewsNum);
    }

    @Test
    public void newsButtonWorks(){
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);
        securityOverviewPage.clickRecentNewsButton();
        if(!securityOverviewPage.newsItemsEmpty()) {
            Assert.assertTrue("Clicking the Recent News Button failed to open the modal."
                    , securityOverviewPage.recentNewsModalExists());
            securityOverviewPage.clickRecentNewsResult();

            Assert.assertTrue("Clicking on a Recent News Result fails to open the results' modal", securityOverviewPage
                    .recentNewsResultsModalExists(true));
            securityOverviewPage.clickCoordinate(By.className("company-name"), 99, 223);
            Assert.assertFalse("Recent News Result modal failed to close."
                    , securityOverviewPage.recentNewsResultsModalExists(false));
            securityOverviewPage.clickCoordinate(By.className("company-name"), 99, 223);
            Assert.assertFalse("Recent News modal failed to close."
                    , securityOverviewPage.recentNewsModalExists());
        }

    }

    @Test
    public void compareResearchNumToActual() {
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);

        int researchButtonNum = Integer.parseInt(securityOverviewPage.getRecentResearchButtonNumber());
        securityOverviewPage.clickRecentResearchButton();
        int actualResearchNum = securityOverviewPage.getNumResearchResultsDisplayed();

        Assert.assertEquals("Number shown in recent Research button does not correspond with actual results"
                ,researchButtonNum,actualResearchNum);
    }

    @Test
    public void researchButtonWorks(){
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);

        securityOverviewPage.clickRecentResearchButton();
        Assert.assertTrue("Clicking the Recent Research Button failed to open the modal."
                , securityOverviewPage.recentResearchModalExists());


        if(Integer.parseInt((securityOverviewPage.getRecentResearchButtonNumber())) != 0){
            //Need a way to test whether or not the download started
        }

        securityOverviewPage.clickCoordinate(By.className("company-name"), 99, 223);
        Assert.assertFalse("Recent Research modal failed to close."
                , securityOverviewPage.recentResearchModalExists());
    }

    @Test
    public void canAddIndexToChart(){
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);


        Assert.assertTrue("Unable to add index",securityOverviewPage.addIndexToChart("NAS"));
        Assert.assertTrue("Index not added",securityOverviewPage.isIndexAdded("NASDAQ"));
    }

    @Test
    public void noSpecialCharacters(){
        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver);

        Assert.assertTrue(securityOverviewPage.checkSpeicalCharacter("The Vanguard Group, Inc."));
    }

}
