package specs.admin.implementation;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.admin.implementationPage.ImplementationPage;
import pageobjects.admin.loginPage.AdminLoginPage;
import specs.AdminAbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by patrickp on 2016-10-12.
 */
public class AddEditImplementations extends AdminAbstractSpec {

    @Before
    public void setUp() {
        new AdminLoginPage(driver).loginAdmin()
                .navigateToImplementationPage();
    }

    @Test
    public void canAddImplementation() {
        String alias = "Test Alias" + RandomStringUtils.randomAlphanumeric(3);
        String sourceUrl = "www." + RandomStringUtils.randomAlphanumeric(5) + ".com";

        ImplementationPage implementationPage = new ImplementationPage(driver).addNewImplementation(alias, sourceUrl);

        Assert.assertThat("Known issue - ADMIN-418 - New implementation not shown in list", implementationPage.getImplementationList(), containsString(alias));
    }

    @Test
    public void canEditImplementation() {
        String sourceUrl = "www." + RandomStringUtils.randomAlphanumeric(5) + ".com";

        ImplementationPage implementationPage = new ImplementationPage(driver).selectFirstImplementation()
                .editDetails(sourceUrl);

        Assert.assertThat("Implementation changes are not reflected after saving", implementationPage.getImplementationList(), containsString(sourceUrl));
    }

    @Test
    public void cannotAddImplementationWithoutSourceUrl() {
        String alias = "Alias" + RandomStringUtils.randomAlphanumeric(5);
        String emptyUrl = "";
        ImplementationPage implementationPage = new ImplementationPage(driver).addNewImplementation(alias, emptyUrl);

        Assert.assertThat("Implementation error message not shown", implementationPage.getError(), containsString("ClientProxy validation failed"));
    }

    @Test
    public void cannotAddImplementationWithoutAlias() {
        String emptyAlias = "";
        String url = "www.test.com";
        ImplementationPage implementationPage = new ImplementationPage(driver).addNewImplementation(emptyAlias, url);

        Assert.assertThat("Implementation error message not shown", implementationPage.getError(), containsString("ClientProxy validation failed"));
    }

    @Test
    public void canCancelImplementationCreation() {
        new ImplementationPage(driver).openNewImplementationModal()
                .cancelImplementationCreation();

        // Assert that modal has been dismissed.
        Assert.assertEquals("Implementation modal not successfully dismissed", 1, driver.findElements(By.className("ui-dialog-title")).size());
    }
}
