package specs.user.ownership;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pageobjects.user.institutionPage.InstitutionPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.ownershipPage.SecurityOwnershipPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

public class Ownership extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectOwnershipFromSideNav();
    }

    @Ignore
    @Test
    public void dateOptionsWorkCorrectly(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        // checking that the date tabs indicate the end dates of the last four quarters
        Assert.assertTrue("The displayed date options are incorrect.", securityOwnershipPage.dateOptionsAreValid());
        // checking that the "As of" text is correct when each date option is selected
        Assert.assertTrue("As of statement is incorrect when switching through date options.", securityOwnershipPage.asOfDateIsValid());
    }

    @Test
    public void topBuyersAndSellersListIsValid(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        // checking top buyers and sellers list while Surveillance tab is selected
        Assert.assertTrue("Top buyers list contains negative numbers.", securityOwnershipPage.topBuyersListIsPositive());
        Assert.assertTrue("Top sellers list contains positive numbers.", securityOwnershipPage.topSellersListIsNegative());
        Assert.assertTrue("Top buyers list is not in descending order.", securityOwnershipPage.topBuyersListIsDescending());
        Assert.assertTrue("Top sellers list is not in ascending order.", securityOwnershipPage.topSellersListIsAscending());
        Assert.assertTrue("Top buyers and sellers list contains duplicates.", securityOwnershipPage.topBuyersAndSellersAreUnique());
        // checking top buyers and sellers list while each date option is selected
        for (int i=1; i<4; i++){//this should ignore 1 month data which tends to be empty for most securities.
                securityOwnershipPage.selectDateRange(i);
                Assert.assertTrue("Top buyers list contains negative numbers while in date option" + (i + 1), securityOwnershipPage.topBuyersListIsPositive());
                Assert.assertTrue("Top sellers list contains positive numbers while in date option" + (i + 1), securityOwnershipPage.topSellersListIsNegative());
                Assert.assertTrue("Top buyers list is not in descending order while in date option" + (i + 1), securityOwnershipPage.topBuyersListIsDescending());
                Assert.assertTrue("Top sellers list is not in ascending order while in date option" + (i + 1), securityOwnershipPage.topSellersListIsAscending());
                Assert.assertTrue("Top buyers and sellers list contains duplicates while in date option" + (i + 1), securityOwnershipPage.topBuyersAndSellersAreUnique());
        }
    }

    @Ignore
    //Needs to be fixed (pagination was added)
    @Test
    public void canShowMoreHolders(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        // checking that 10 holders are initially shown and that the table can be sorted
        Assert.assertEquals("Initial number of holders displayed is incorrect", 10, securityOwnershipPage.getNumOfHoldersDisplayed());// clicking show more and checking that 20 holders are now shown and that the table can still be sorted
        Assert.assertTrue("Can't show more holders", securityOwnershipPage.checkShowMoreHolders());
    }

    @Test
    public void canSortHoldersByName(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        Assert.assertTrue("Holders incorrectly sorted by Name",securityOwnershipPage.canSortByName());
    }

    @Test
    public void canSortHoldersByPOS(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        Assert.assertTrue("Holders incorrectly sorted by POS",securityOwnershipPage.canSortByPOS());
    }

    @Test
    public void canSortHoldersBy1QCHG(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        Assert.assertTrue("Holders incorrectly sorted by 1Q Change",securityOwnershipPage.canSortBy1Q());
    }

    @Test
    public void canSortHoldersByMarketValue(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        Assert.assertTrue("Holders incorrectly sorted by Market Value",securityOwnershipPage.canSortByMarketValue());
    }

    @Test
    public void canSortHoldersByMarketValueChange(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        Assert.assertTrue("Holders incorrectly sorted by Change in Market Value",securityOwnershipPage.canSortByMarketValueChange());
    }

    @Test
    public void canSortHoldersByOS(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        Assert.assertTrue("Holders incorrectly sorted by %OS",securityOwnershipPage.canSortByOS());
    }

    @Test
    public void canSortHoldersByPort(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        Assert.assertTrue("Holders incorrectly sorted by Port",securityOwnershipPage.canSortByPort());
    }


    @Test
    public void canSortHoldersByStyle(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        Assert.assertTrue("Holders incorrectly sorted by Style",securityOwnershipPage.canSortByStyle());
    }

    @Ignore
    //Needs to be fixed for side scroll
    @Test
    public void canSortHoldersByQR(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        Assert.assertTrue("Holders incorrectly sorted by Quality Rating",securityOwnershipPage.canSortByQR());
    }

    @Test
    public void holderTableColouringIsCorrect(){
        // Checks that all values in the 1Q CHG and MKT VAL CHG columns are green if positive, red if negative, or dark grey if zero
        Assert.assertTrue("One or more change values in the Holders table is not coloured correctly",
                new SecurityOwnershipPage(driver).holderTableChangeValueColouringIsCorrect());
    }



    @Test
    public void canSeeActivistHolders(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        //store current holders list
        String[] holders = securityOwnershipPage.getHolderNames();
        //select activist filter and check that all have red flag
        securityOwnershipPage.showOnlyActivists();
        Assert.assertEquals("Known issue - DESKTOP-7285 - Not all filtered entries have activist icon", securityOwnershipPage.getNumOfHoldersDisplayed(), securityOwnershipPage.getNumOfActivistsDisplayed());
        //unselect activist filter
        securityOwnershipPage.doNotShowOnlyActivists();
        //check that original list is displayed
        Assert.assertArrayEquals("Known issue - DESKTOP-8902", holders, securityOwnershipPage.getHolderNames());
    }

    @Ignore
    //Does not currently work, requires investigation
    @Test
    public void allFilteredActivistsAreActivists(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        // store list of filtered holders
        String[] institutions = securityOwnershipPage.showOnlyActivists().getHolderNames();
        // for each holder, go to institution page and check that activist icon appears
        for (int i=0; i<institutions.length; i++){
            InstitutionPage institutionPage = securityOwnershipPage.goToInstitutionalHolder(i);
            Assert.assertThat("ERROR: incorrect institution page opened", institutionPage.getInstitutionName(), containsString(institutions[i]));
            Assert.assertTrue("Known issue - DESKTOP-7281 - Institution "+institutions[i]+" is not an activist.", institutionPage.isActivist());
             driver.navigate().back();
            securityOwnershipPage= new SecurityOwnershipPage(driver);
             securityOwnershipPage.showOnlyActivists();
        }
    }

    @Test
    public void canFilterHoldersByType(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        // selecting first date tab
        securityOwnershipPage.selectThirteenF();
        // store original holders list
        String[] holders = securityOwnershipPage.getHolderNames();
        // select institution filter and check that all entries shown are institutions
        securityOwnershipPage.showOnlyInstitutions();
        Assert.assertEquals("Not all filtered entries are institutions", securityOwnershipPage.getNumOfHoldersDisplayed(), securityOwnershipPage.getNumOfInstitutionsDisplayed());
        // select insiders filter and check that all entries shown are insiders
        securityOwnershipPage.showOnlyInsiders();
        Assert.assertEquals("Not all filtered entries are insiders", securityOwnershipPage.getNumOfHoldersDisplayed(), securityOwnershipPage.getNumOfInsidersDisplayed());
        // select funds filter and check that all entries shown are funds
        securityOwnershipPage.showOnlyFunds();
        Assert.assertEquals("Not all filtered entries are funds", securityOwnershipPage.getNumOfHoldersDisplayed(), securityOwnershipPage.getNumOfFundsDisplayed());
        // select all option and check that original list is displayed
        securityOwnershipPage.showAllTypes();
        Assert.assertArrayEquals("Original list is not displayed after selecting All types filter", holders, securityOwnershipPage.getHolderNames());
    }

    @Test
    public void canSeeFilterHoldersByBuyersAndSellers(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        // storing current holders list
        String[] holders = securityOwnershipPage.getHolderNames();
        // selecting buyers filter and checking at least one holder is displayed and that all entries have positive 1W value
        securityOwnershipPage.showOnlyBuyers();
        Assert.assertNotEquals("Known issue - DESKTOP-7309 - No holders displayed after using Buyers filter", 0, securityOwnershipPage.getNumOfHoldersDisplayedAlternate());
        Assert.assertEquals("Not all filtered entries are buyers", securityOwnershipPage.getNumOfHoldersDisplayedAlternate(), securityOwnershipPage.getNumofBuyersDisplayed());
        // selecting sellers filter and checking checking at least one holder is displayed and that all entries have negative 1W value
        securityOwnershipPage.showOnlySellers();
        Assert.assertNotEquals("Known issue - DESKTOP-7309 - No holders displayed after using Sellers filter", 0, securityOwnershipPage.getNumOfHoldersDisplayedAlternate());
        Assert.assertEquals("Not all filtered entries are sellers", securityOwnershipPage.getNumOfHoldersDisplayedAlternate(), securityOwnershipPage.getNumofSellersDisplayed());
        // selecting "All" option and checking that original list is displayed
        securityOwnershipPage.showBuyersAndSellers();
        Assert.assertArrayEquals("Original list is not displayed after selecting All filter", holders, securityOwnershipPage.getHolderNames());
    }

    @Test
    public void checkInstitutionalOwnershipCharts(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        // checking that each color has 7 style bars and 5 turnover bars
        Assert.assertTrue("Incorrect number of style bars in one or more colours are displayed.", securityOwnershipPage.styleBarsNumsAreCorrect());
        Assert.assertTrue("Incorrect number of turnover bars in one or more colours are displayed.", securityOwnershipPage.turnoverBarsNumsAreCorrect());
        // checking that all bars have values between 0 and 100
        Assert.assertTrue("One or more bars does not have a value between 0 and 100.", securityOwnershipPage.institutionalBarsHaveValidNumbers());
    }

    @Test
    public void trendAnalysisChartsWork(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        securityOwnershipPage.selectTrendAnalysis();
        // checking that hovertext appear when hovering cursor over each of the 4 charts
        Assert.assertTrue("Hovering does not work on one or more of the Trend Analysis Charts.", securityOwnershipPage.canHoverOverTrendAnalysisCharts());
    }


    @Ignore // this doesn't seem to be valid anymore
    @Test
    public void checkHolderBreakdownPie(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        // selecting first date tab
        securityOwnershipPage.selectThirteenF();
        // checking that there are the correct number of breakdown values
        Assert.assertEquals("The number of Holder Analysis Breakdown values is incorrect", 4, securityOwnershipPage.getNumOfHolderBreakdownValues());
        // checking that all breakdown values are between 0 and 100
        Assert.assertTrue("One or more Holder Analysis Breakdown values are not between 0 and 100.", securityOwnershipPage.holderBreakdownValuesAreValid());
    }

    @Test
    public void checkHolderTypePie(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        // selecting first date tab
        securityOwnershipPage.selectThirteenF();
        // checking that there are the correct number of type values
        Assert.assertEquals("The number of Holder Analysis Type values (including the 'Other' value) is incorrect", 4, securityOwnershipPage.getNumofHolderTypeValues());
        // checking that all type values are between 0 and 100
        Assert.assertTrue("One or more Holder Analysis Type values are not between 0 and 100.", securityOwnershipPage.holderTypeValuesAreValid());
        // opening the type "Other" dropdown and checking that at least two values are present
        securityOwnershipPage.openOtherHolderTypes();
        Assert.assertTrue("Less than two values are displayed in the other Holder Analysis Type dropdown.", securityOwnershipPage.otherHolderValuesArePresent());
        // checking that the other type values are all between 0 and 100
        Assert.assertTrue("One or more Holder Analysis other type values are not between 0 and 100.", securityOwnershipPage.otherHolderValuesAreValid());
        // closing the other dropdown and checking that it is closed
        securityOwnershipPage.closeOtherHolderDropdown();
        Assert.assertTrue("Other Holder Analysis Type dropdown has not been closed.", securityOwnershipPage.otherHolderDropdownIsClosed());
    }

    @Test
    public void checkHolderStylePies(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        // selecting first date tab
        securityOwnershipPage.selectThirteenF();
        // checking that there are the correct number of style values
        Assert.assertEquals("The number of Holder Analysis Style values (including the 'Other' value) is incorrect", 4, securityOwnershipPage.getNumofHolderStyleValues());
        // checking that all style values are between 0 and 100
        Assert.assertTrue("One or more Holder Analysis Style values are not between 0 and 100.", securityOwnershipPage.holderStyleValuesAreValid());
        // opening the style "Other" dropdown and checking that at least two values are present
        securityOwnershipPage.openOtherHolderStyles();
        Assert.assertTrue("Less than two values are displayed in the other Holder Analysis Style dropdown.", securityOwnershipPage.otherHolderValuesArePresent());
        // checking that the other style values are all between 0 and 100
        Assert.assertTrue("One or more Holder Analysis other style values are not between 0 and 100.", securityOwnershipPage.otherHolderValuesAreValid());
        // closing the other dropdown and checking that it is closed
        securityOwnershipPage.closeOtherHolderDropdown();
        Assert.assertTrue("Other Holder Analysis Style dropdown has not been closed.", securityOwnershipPage.otherHolderDropdownIsClosed());
    }

    @Test
    public void checkHolderTurnoverPies(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        // selecting first date tab
        securityOwnershipPage.selectThirteenF();
        // checking that there are the correct number of turnover values
        Assert.assertEquals("The number of Holder Analysis Turnover values (including the 'Other' value) is incorrect", 4, securityOwnershipPage.getNumofHolderTurnoverValues());
        // checking that all turnover values are between 0 and 100
        Assert.assertTrue("One or more Holder Analysis Turnover values are not between 0 and 100.", securityOwnershipPage.holderTurnoverValuesAreValid());
        // opening the turnover "Other" dropdown and checking that at least two values are present
        securityOwnershipPage.openOtherHolderTurnovers();
        Assert.assertTrue("Less than two values are displayed in the other Holder Analysis Turnover dropdown.", securityOwnershipPage.otherHolderValuesArePresent());
        // checking that the other turnover values are all between 0 and 100
        Assert.assertTrue("One or more Holder Analysis other turnover values are not between 0 and 100.", securityOwnershipPage.otherHolderValuesAreValid());
        // closing the other dropdown and checking that it is closed
        securityOwnershipPage.closeOtherHolderDropdown();
        Assert.assertTrue("Other Holder Analysis Style dropdown has not been closed.", securityOwnershipPage.otherHolderDropdownIsClosed());
    }

    //Historical Holder Filters

    @Ignore
    //Does not currently work, requires investigation
    @Test
    public void canSearchForHistoricalInstitutions() {
        // Search for specific historical owners on the Institutions tab of the Historical table
        String holder = "Chevy Chase";
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver).selectThirteenF()
                .viewHistoricalHolders()
                .searchForHoldings(holder);
      
        Assert.assertThat("Institution not found", securityOwnershipPage.getHolderSearchResults(), containsString(holder));
    }

    @Ignore
    //Does not currently work, requires investigation
    @Test
    public void canSearchForHistoricalInsiders() {
        // Search for specific insiders on the insiders tab of the historical section of the Holder's table
        String holder = "William J. DeLaney";
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver).selectThirteenF();
        securityOwnershipPage.viewHistoricalHolders();
        securityOwnershipPage.selectInsiderstab();
        securityOwnershipPage.searchForHoldings(holder);

        Assert.assertThat("Known Issue: DESKTOP-8692", securityOwnershipPage.getInsiderSearchResults(), containsString(holder));
    }

    @Test
    public void canSearchForHistoricalFunds(){
        //Search for specific historical owners on the Funds and ETF's tab of the Historical table
        String holder = "Pacific Select Fund";
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver).selectThirteenF()
                .viewHistoricalHolders()
                .showOnlyFunds()
                .searchForHoldings(holder);
        Assert.assertThat(securityOwnershipPage.getHistoricalFundsHolderSearchResults(), containsString(holder));
    }

    @Ignore
    //Same as canSearchForHistoricalFunds
    @Test
    public void canSearchForHistoricalFundsETFs() {
        // Search for funds and ETFs under the Funds and ETFs tab of the Historical table
        String holder = "Vanguard 500";
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver).selectThirteenF()
                .viewHistoricalHolders()
                .selectFundsETFstab()
                .searchForFundsETFs(holder);

        Assert.assertThat(securityOwnershipPage.getHolderSearchResultstwo(), containsString(holder));
    }

    //Current Holder filters

    @Test
    public void canSearchForCurrentInstitutions() {
        // Search for specific current owners on the Institutions tab of the current table
        String holder = "Parnassus Investments";
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver).selectThirteenF()
                .viewInstitutiontab()
                .searchForHoldings(holder);

        Assert.assertThat(securityOwnershipPage.getInstitutionSearchResults(), containsString(holder));
    }

    @Test
    public void canSearchForCurrentInsiders(){
        //Search for specific current insiders on the Insiders tab of the Current Holders table
        String holder = "Jonathan Golden";
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver).selectThirteenF()
                .viewCurrentHolders()
                .showOnlyInsiders()
                .searchForHoldings(holder);

        Assert.assertThat(securityOwnershipPage.getCurrentInsidersHolderSearchResults(), containsString(holder));

        Assert.assertThat(securityOwnershipPage.getInsiderSearchResults(), containsString(holder));
    }

    @Test
    public void canGetPeerAnalysisData(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        Assert.assertTrue("Failed checking peer analysis", securityOwnershipPage.checkPeerAnalysis("SYY NYSE"));
    }

    @Test
    public void canUseBuyerSellerFilter(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        Assert.assertTrue("Failed checking Buyer&Seller filter", securityOwnershipPage.checkBuyerSellerFilter());
    }

    @Test
    public void canShowCorrectChartLabels(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        Assert.assertTrue("Failed checking Pies", securityOwnershipPage.checkPies());
        Assert.assertTrue("Failed checking Bars", securityOwnershipPage.checkBars());
    }

    @Test
    public void canShowCorrectMap(){
        SecurityOwnershipPage securityOwnershipPage = new SecurityOwnershipPage(driver);
        Assert.assertTrue("Failed checking institutional holder analysis map",
                securityOwnershipPage.checkInstitutionalHolderAnalysisMap());
    }
}
