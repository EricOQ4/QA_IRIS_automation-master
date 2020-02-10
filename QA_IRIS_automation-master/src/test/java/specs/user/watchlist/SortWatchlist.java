package specs.user.watchlist;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.watchlist.WatchlistPage;
import specs.AbstractSpec;

import java.util.List;


/**
 * Created by charlesz on 2017-10-25.
 */

public class SortWatchlist extends AbstractSpec{

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectWatchListFromSideNav();
    }

    @Test
    public void canSortAlphabetically() {
        WatchlistPage watchlist = new WatchlistPage(driver);
        //if the watchlist is empty, add a list of peers for testing
        if (!watchlist.watchlistHadEnoughSecurities()){
        String securityList[] = new String[] {"Agilent Technologies Inc",
       "Zillow Group Inc","Dominion Energy Inc", "Barnes Group Inc",
                "TELUS Corp"};
                watchlist.addListOfSecuritiesToWatchlist(securityList);}
        //get all company names in the page
        List<String> companyNameList = watchlist.getAllCompanyNames();
        //assert the names to be in alphabetical order
        Assert.assertTrue("The list should be in alphabetically order",
                watchlist.isAlphabeticallySorted(companyNameList));
    }

    @After
    public void tearDown(){
        driver.quit();
    }
}
