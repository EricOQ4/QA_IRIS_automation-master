package specs.admin.companies;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.admin.companyPage.CompanyDetailsPage;
import pageobjects.admin.companyPage.CompanyList;
import pageobjects.admin.loginPage.AdminLoginPage;
import pageobjects.user.dashboardPage.Dashboard;
import specs.AdminAbstractSpec;
import util.EnvironmentType;

import static org.hamcrest.CoreMatchers.*;

/**
 * Created by patrickp on 2016-09-20.
 */
public class CompanyDetails extends AdminAbstractSpec {

    @Before
    public void setUp() {
        if (hasLoggedIn() && getActiveEnvironment()!= EnvironmentType.LOCALADMIN) {
            new Dashboard(driver).navigateToCompanyPage();
        } else {
            new AdminLoginPage(driver).loginAdmin()
                    .navigateToCompanyPage();
        }
    }


    @Test
    public void canEditCompanyDetails() {
        String newName = "Testing";
        CompanyList companyPage = new CompanyList(driver);
        String companyName = companyPage.getFirstCompanyName();
        companyPage.selectFirstCompanyInList()
                .editCompanyName(newName);

        Assert.assertThat("New company name is not visible on Company details page", companyPage.getCompanyName(), containsString(newName));

        new CompanyDetailsPage(driver).editCompanyName(companyName);
    }

    @Test
    public void canEditPeerNameWhenAdding() {
        String peer = "TSLA";
        String name = "New Test" + RandomStringUtils.randomAlphanumeric(3);
        CompanyList companyPage = new CompanyList(driver).selectFirstCompanyInList()
                .addEditPeer(peer, name);

        Assert.assertThat("New peer name is not shown in peer list", companyPage.getPeerList(), containsString(name));

        companyPage.removePeer();
    }

    @Test
    public void canAddAndRemoveAdditionalTickersToCompany() {
        String companyName = "GOLD";
        String newTicker = "WEED";
        CompanyList companyList = new CompanyList(driver).searchForCompany(companyName)
                .selectFirstCompanyInList()
                .selectTickerTab()
                .addTicker(newTicker);

        driver.navigate().refresh();

        new CompanyDetailsPage(driver).selectTickerTab();

        Assert.assertThat("Added ticker is not visible", companyList.getTickerList(), containsString(newTicker));

        new CompanyDetailsPage(driver).removeTicker();

        driver.navigate().refresh();

        Assert.assertThat("Added ticker is still visible", companyList.getTickerList(), is(not(newTicker)));
    }

    @Test
    public void canDismissAddTickerModal() {
        String companyName = "GOLD";
        new CompanyList(driver).searchForCompany(companyName)
                .selectFirstCompanyInList()
                .selectTickerTab()
                .clickAddTickerButton()
                .dismissAddTickerModal();

        Assert.assertEquals("Forgot password modal was not successfully dismissed", 1, driver.findElements(By.cssSelector(".modal .ui-dialog .ui-dialog-content .ui-autocomplete.auto-complete-search .ui-inputtext")).size());

    }
}
