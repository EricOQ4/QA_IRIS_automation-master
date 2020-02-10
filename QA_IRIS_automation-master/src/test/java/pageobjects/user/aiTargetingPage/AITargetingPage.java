package pageobjects.user.aiTargetingPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by yevam on 2018-07-25
 */

/**
 * Updated by _____ on 2020-02-05
 */

public class AITargetingPage extends AbstractPageObject {

//Search Form

    private final By filtersButton = By.xpath("//div[contains(@class, 'q4-range-tabs')]//span[contains(text(), 'Filters')]");
    private final By savedButton = By.xpath("//div[contains(@class, 'q4-range-tabs')]//span[contains(text(), 'Saved')]");
    private final By clearFiltersButton = By.xpath("//div[contains(@class, 'targeting-page_filter-action-button--no-background')]//span[contains(text(), 'Clear Filters')]");

    private final By metroAreaInput = By.xpath("//input[@placeholder=\"Metro Area\"]");
    private final By metroAreaFilterAdded = By.xpath("//div[@class=\"location\"]");
    private final By metroAreaFilterCloseButton = By.xpath("//i[contains(@class, 'q4i-close-4pt')]");


    private final By institutionToggle = By.xpath("//div[div/span[text()='Institution']]");
    private final By fundToggle = By.xpath("//div[div/span[text()='Fund']]");
    private final By qrMinInput = By.xpath("//input[@placeholder=\"0 (min)\"]");
    private final By qrMaxInput = By.xpath("//input[@placeholder=\"100 (max)\"]");
    private final By aumMinInput = By.xpath("//input[@placeholder=\"0\"]");
    private final By aumMaxInput = By.xpath("//input[@placeholder=\"00,000,000\"]");

    private final By turnoverSelector = By.xpath("//span[contains(text(), 'Turnover')]");
    private final By turnoverSelector_veryLow = By.xpath("(//div[contains(text(), 'Very Low')]) [1]");
    private final By typeSelector = By.xpath("//span[contains(text(), 'Type')]");
    private final By typeSelector_bankPortfolio = By.xpath("(//div[contains(text(), 'Bank Portfolio')]) [1]");
    private final By styleSelector = By.xpath("//span[contains(text(), 'Style')]");
    private final By styleSelector_aggressiveGrowth = By.xpath("(//div[contains(text(), 'Aggressive Growth')]) [1]");
    private final By ownershipSelector = By.xpath("//span[contains(text(), 'Ownership')]");
    private final By ownershipSelector_POSinMyCompany = By.xpath("(//div[contains(text(), 'Holds position in SYY:XNYS')]) [1]");

    private final By sectorActivity_netBuyerToggle = By.xpath("(//div[div/span[text()='Net Buyer']]) [1]");
    private final By sectorActivity_sellerToggle = By.xpath("(//div[div/span[text()='Net Seller']]) [1]");
    private final By sectorActivity_allToggle = By.xpath("(//div[div/span[text()='All']]) [1]");
    private final By peerActivity_netBuyerToggle = By.xpath("(//div[div/span[text()='Net Buyer']]) [2]");
    private final By peerActivity_sellerToggle = By.xpath("(//div[div/span[text()='Net Seller']]) [2]");
    private final By pperActivity_allToggle = By.xpath("(//div[div/span[text()='All']]) [2]");

    private final By excludeActivistsCheckbox = By.xpath("//div[div/span[text()='Exclude Activists']]");
    private final By loggedActivityCheckbox = By.xpath("//div[div/span[text()='Logged Activity']]");
    private final By aiTargetsOnlyCheckbox = By.xpath("//div[div/span[text()='AI Targets Only']]");

    private final By saveFilterButtonSave = By.xpath("//div[contains(@class, 'targeting-page_filter-action-button')]//span[contains(text(), 'Save')]");
    private final By saveFilterButtonSearch = By.xpath("//span[contains(text(), 'Search')]");

//Results table

    private final By targettingExportButton = By.xpath("//span[contains(text(), 'Export')]");
    private final By targettingFundIcon = By.xpath("(//div[contains(@class, 'targeting-page_grid-details')]//i[contains(@class, 'fund')])[1]");
    private final By targettingInstitutionIcon = By.xpath("(//div[contains(@class, 'targeting-page_grid-details')]//i[contains(@class, 'institution')])[1]");
    private final By targetingTableRow_nameColumn = By.xpath("(//div[contains(@class, 'targeting-page_grid-details')])[1]");
    private final By targetingTableRow_locationColumn = By.xpath("(//div[@id='ext-gridcell-1']");
    private final By targetingTableRow_styleColumn = By.xpath("(//div[@id='ext-gridcell-2']");
    private final By targetingTableRow_turnoverColumn = By.xpath("(//div[@id='ext-gridcell-3']");
    private final By targetingTableRow_aumColumn = By.xpath("(//div[@id='ext-gridcell-4']");
    private final By targetingTableRow_aiScoreColumn = By.xpath("(//div[@id='ext-gridcell-5']");
    private final By targetingTableRow_qrColumn = By.xpath("(//div[@id='ext-gridcell-6']");
    private final By targetingTableRow_utilityMenu = By.xpath("(//div[contains(@class, 'targeting-page_grid-details')]//i[contains(@class, 'utility')])[1]");

    //private final By savedTargetsButton = By.xpath("//div[contains(@class, 'button-no-icon')]//span[contains(text(), 'Saved Targets')]");
    private final By savedTargetsButton = By.xpath("//div[contains(@class, 'section-tab_nav-item')]//span[contains(text(), 'Saved Targets')]");

    public AITargetingPage(WebDriver driver) {
        super(driver);
    }


    public SavedTargetsPage goToSavedTargetsPage() {

        waitForLoadingScreen();
        waitForElementToBeClickable(savedTargetsButton).click();

        return new SavedTargetsPage(getDriver());
    }
}