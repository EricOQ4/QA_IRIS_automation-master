package pageobjects.user.watchlist;

import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.securityPage.SecurityOverviewPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrickp on 2016-08-05.
 */
public class WatchlistPage extends AbstractPageObject{

    private final By editWatchlistButton = By.cssSelector(".toolbar-panel .toolbar-button");
    private final By securitySearchField = By.xpath("//input[contains(@placeholder,'Add a security')]");
    private final By searchResult = By.cssSelector(".watchlist-search-results .x-list-item .icon.q4i-add-4pt");
    private final By watchlist = By.xpath("//div[contains(@class,'watchlist-list')]");
    private final By watchlistDeleteButton = By.xpath("//span[contains(@class,'q4i-trashbin-4pt')]");
    private final By firstCompanyInList = By.cssSelector(".watchlist-list .watchlist-row");
    private final By firstCompanyNameInList = By.cssSelector(".watchlist-list .watchlist-row h5");
    private final By addSecurityButton = By.cssSelector(".watchlist-new-item .header-button [class*=\" q4i-\"]");
    private final By confirmDelete = By.xpath("//div[contains(@class,'x-msgbox')]//div[span[contains(text(),'Yes')]]");
    private final By cancelDelete =By.xpath("//div[contains(@class,'x-msgbox')]//div[span[contains(text(),'No')]]");
    private final By watchlistSearchField = By.cssSelector(".toolbar-panel .search .x-field-input .x-input-el");
    private final By selectAll = By.xpath("//div[contains(@class,'bulk-checkbox')]//div[contains(@class,'field-mask')]");


    public WatchlistPage(WebDriver driver) {
        super(driver);
    }

    public WatchlistPage addSecurityToWatchlist(String security) {
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(addSecurityButton));
        findElement(addSecurityButton).click();
        pause(1000L);
        wait.until(ExpectedConditions.elementToBeClickable(securitySearchField));
        findElement(securitySearchField).sendKeys(security);
        wait.until(ExpectedConditions.elementToBeClickable(searchResult));
        retryClick(searchResult);
        pause(1000L);
        findElement(addSecurityButton).click();
        pause(1000L);
        return this;
    }

    public WatchlistPage addListOfSecuritiesToWatchlist(String[] securities) {
        for (int i = 0; i < securities.length; i++) {
           addSecurityToWatchlist(securities[i]);
        }
        return this;
    }


    public String getWatchlistSecurities() {
        waitForLoadingScreen();
        waitForElement(watchlist);
        return findElement(watchlist).getText().replaceAll("\\p{P}", "");
    }

    // Checks to see if the watchlist is empty
    public boolean watchlistHadSecurities() {
        waitForLoadingScreen();
        if (findElements(firstCompanyInList).size() != 0) {
            return true;
        }
        return false;
    }

    // Checks to see if th watchlist has enough securities to run sorting test on
    public boolean watchlistHadEnoughSecurities() {
        waitForLoadingScreen();
        if (findElements(firstCompanyInList).size() > 3) {
            return true;
        }
        return false;
    }

    public WatchlistPage removeSecurityFromWatchlist(String security) {

         ArrayList<WebElement> securitiesList = new ArrayList<WebElement>(findElements(By.xpath("//div[contains(@class,'x-dataview-item')]")));

        for (WebElement row :securitiesList){
            if(row.getText().replaceAll("\\p{P}", "").contains(security)){
                row.findElement(By.cssSelector(".column.bulk")).click();
            }
        }
            findElement(watchlistDeleteButton).click();
            pause(500L);
            wait.until(ExpectedConditions.elementToBeClickable(confirmDelete));
            pause(500L);
            findElement(confirmDelete).click();
        return this;
    }

    public String getFirstCompanyInList() {
        waitForLoadingScreen();
        waitForElementToAppear(firstCompanyInList);
        return findElement(firstCompanyInList).getText().replaceAll("\\p{P}", "");
    }

    public String getFirstCompanyName() {
        int index = 0;
        WebElement baseTable = driver.findElement(By.id("ext-watchlist-1"));
        List<WebElement> tableRows = baseTable.findElements(By.cssSelector(".watchlist-list .watchlist-row h5"));
        return tableRows.get(index).getText();

//        waitForLoadingScreen();
//        waitForElementToAppear(firstCompanyNameInList);
//        return findElement(firstCompanyNameInList).getText().replaceAll("\\p{P}", "");
    }

    public SecurityOverviewPage clickOnFirstWatchlistCompany() {
        findElement(firstCompanyInList).click();
        waitForLoadingScreen();
        pause(1000L);
        return new SecurityOverviewPage(getDriver());
    }

    // If the watchlist is empty, this adds a company so it can later be removed
    public WatchlistPage checkForExistingSecurities() {
        if (watchlistHadSecurities()) {
            wait.until(ExpectedConditions.elementToBeClickable(firstCompanyInList));
        }
        else
        {
            addSecurityToWatchlist("VRX");
        }
        return this;
    }

    // Checks to see if a specific security exists in the watchlist. If it doesn't exist, it adds it.
    public WatchlistPage securityIsVisible(String security) {
        waitForLoadingScreen();
        String actual = getFirstCompanyName();

        if (actual.equals(security)) {
            removeSecurityFromWatchlist(actual);
            addSecurityToWatchlist(security);
        } else {
            addSecurityToWatchlist(security);
        }
        return this;
    }

    // If the watchlist is empty, this adds a company so it can later be removed
    public WatchlistPage addCompanyIfWatchlistEmpty(String ticker) {
        if (watchlistHadSecurities()) {
            wait.until(ExpectedConditions.elementToBeClickable(firstCompanyInList));
        }
        else
        {
            addSecurityToWatchlist(ticker);
        }
        return this;
    }

    public WatchlistPage searchForEntity(String companyName) {
        findElement(watchlistSearchField).sendKeys(companyName);
        return this;
    }

    public List<String> getAllCompanyNames() {
        List<WebElement> companyList = findElements(By.xpath("//div[contains(@class,'dataview-container')]/div//h5"));
        List<String> companyNameList = new ArrayList <String>();
        for(WebElement e:companyList)
        {
            companyNameList.add(e.getText());
        }
        return companyNameList;
    }

    public boolean isAlphabeticallySorted(List<String> names){
        driver.navigate().refresh();
        waitForLoadingScreen();
        return Ordering.from(String.CASE_INSENSITIVE_ORDER).isOrdered(names);
    }
}

