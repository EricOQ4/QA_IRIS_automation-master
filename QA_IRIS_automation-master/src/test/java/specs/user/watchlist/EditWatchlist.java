package specs.user.watchlist;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.securityPage.SecurityOverviewPage;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.watchlist.WatchlistPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;


/**
 * Created by patrickp on 2016-08-22.
 */
public class EditWatchlist extends AbstractSpec {

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectWatchListFromSideNav();
    }

    @Test
    public void canAddAndRemoveFromWatchlist(){
        String security = "Agilent Technologies Inc";
        WatchlistPage watchlist = new WatchlistPage(driver)
                .addSecurityToWatchlist(security);
        Assert.assertThat("Company was not successfully added to the watchlist", watchlist.getFirstCompanyInList(), containsString(security));

        watchlist.removeSecurityFromWatchlist(security);
        Assert.assertThat("Removed company is still visible in watchlist", watchlist.getWatchlistSecurities(), is(not(security)));
    }

    @Test
    public void canNavigateToCompanyFromWatchlist() {
        // Get first company from list and store as a string
        WatchlistPage watchlistPage = new WatchlistPage(driver).checkForExistingSecurities();
        String companyName = watchlistPage.getFirstCompanyInList();
        System.out.println(companyName);

        SecurityOverviewPage securityOverviewPage = new SecurityOverviewPage(driver).clickOnFirstWatchlistCompany();

        Assert.assertThat("Company name not visible", companyName, containsString(securityOverviewPage.getCompanyName()));
    }

    @Test
    public void canRemoveFromDetailsPage(){
        WatchlistPage watchlist = new WatchlistPage(driver).checkForExistingSecurities();
        String companyName = watchlist.getFirstCompanyName();

        new SecurityOverviewPage(driver).clickOnFirstWatchlistCompany()
                .clickThreePointBtn()
                .clickWatchlistBtn();

        //go back to the page and check if it's there
        watchlist.accessSideNavFromPage()
                 .selectWatchListFromSideNav();

        Assert.assertThat("Removed company is still visible in watchlist", watchlist.getWatchlistSecurities(), is(not(companyName)));
    }

    @Test
    public void canSearchForCompanyOnWatchlist() {
        WatchlistPage watchlist = new WatchlistPage(driver).checkForExistingSecurities();
        String companyName = watchlist.getFirstCompanyName();

        new WatchlistPage(driver).searchForEntity(companyName);

        Assert.assertTrue("Search returned incorrect results", watchlist.getAllCompanyNames().contains(companyName));

    }
}