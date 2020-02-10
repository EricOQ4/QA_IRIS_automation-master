package pageobjects.user.briefingBooks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.AbstractPageObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrickp on 2016-08-10.
 */

public class BriefingBookList extends AbstractPageObject {

    //private final By reportList = By.xpath("//*[contains(@class,'briefing-book-list')]//div[contains(@class,'x-dataview-container')]");
    private final By iframe = By.xpath("//iframe[contains(@id,'sencha-viewport')]");
    private final By reportList = By.xpath("//div [contains(@class,'x-inner x-align-stretch x-horizontal x-pack-start x-layout-box')]"); //old one doesn't work this new one seems to work
    private final By emptyList =By.xpath("//div[contains(@class,'no-data_content')]");
    private final By createBookButton = By.xpath("//span[contains(@class,'x-button-icon x-shown q4i-add-4pt')]");
    private final By newBriefingBook = By.xpath("//div[contains(@class,'x-grid-cell x-grid-cell-align-left')] [2]");

    //Checkboxes
    private final By checkbox = By.xpath("//div [contains(@class,'checkbox checkmark')]");
    private final By bulkCheckbox = By.xpath(("//div[contains(@class,'bulk-checkbox')]"));

    //Deleting
    private final By deleteButton = By.xpath("//div[contains(@class,'bulk-button')]");
    private final By confirmDeleteButton = By.cssSelector(".q4-message-modal .x-button.primary");

    //Searching
    private final By searchBox = By.xpath("//input[contains(@type,'search')]");


    private final By briefingBookTitle = By.cssSelector(".briefing-book-item .row div:nth-child(2)");
    private final By generalBriefingBookItem = By.xpath("//div[contains(@class,'briefing-book-item')]");

    //Filter
    private final By yourBooks = By.xpath("//div[contains(@class,'x-unsized x-thumb x-size-monitored x-paint-monitored x-draggable')]");
    //Headers
    private final By titleHeader = By.xpath("//div[@class='x-innerhtml'] [text()='Title']");
    private final By authorHeader = By.xpath("//div[@class='x-innerhtml'] [text()='Author']");
    private final By createdHeader = By.xpath("//div[@class='x-innerhtml'] [text()='Created']");
    private final By updatedHeader = By.xpath("//div[@class='x-innerhtml'] [text()='Updated']");

    //Loading screen i think
    private final By clickStealer = By.xpath ("//div [@class='x-mask x-sized x-floating']");

    public BriefingBookList(WebDriver driver) {
        super(driver);
    }

    private ArrayList<WebElement> getBriefingBookListItems() {
        waitForLoadingScreen();
        ArrayList<WebElement> briefingBooks = new ArrayList<>(findElements(generalBriefingBookItem));
        return briefingBooks;
    }

    private boolean isSortedByAlpha(BriefingBookColumnType type){
        ArrayList<WebElement> items = getBriefingBookListItems();
        ArrayList<WebElement> strings = new ArrayList<>();
        String searchString;

        if(type==BriefingBookColumnType.AUTHOR){
            searchString=".//div[contains(@class,'medium') and contains(@class, 'column')]";
        }
        else{
            searchString=".//div[contains(@class,'flex') and contains(@class, 'column')]";
        }

        for(WebElement item : items){
            strings.add(item.findElement(By.xpath(searchString)));
            System.out.print(item.findElement(By.xpath(searchString)).getText()+"\n");
        }
        return elementsAreAlphaUpSortedIgnoreCase(strings);
    }

    private boolean isSortedByDate(BriefingBookColumnType type){
        ArrayList<WebElement> items = getBriefingBookListItems();
        ArrayList<WebElement> strings = new ArrayList<>();
        String searchString;

        if(type==BriefingBookColumnType.CREATED){
            searchString=".//div[contains(@class,'small') and contains(@class, 'column')][position()=1]";
        }
        else{
            searchString=".//div[contains(@class,'small') and contains(@class, 'column')][last()]";
        }
        for(WebElement item : items){
            strings.add(item.findElement(By.xpath(searchString)));
            System.out.print(item.findElement(By.xpath(searchString)).getText()+"\n");
        }
        return elementsAreDateUpSorted(strings);
    }

    public boolean isSortedBy(BriefingBookColumnType type){
        switch(type){
            case TITLE : case AUTHOR:
                return isSortedByAlpha(type);
            case LAST_UPDATED: case CREATED:
                return isSortedByDate(type);
        }
        return false;
    }

    public String getBriefingBookList(){
        waitForLoadingScreen();
        return findElement(reportList).getText();

    }

    public CreateBriefingBookModal addNewBriefingBook() {

        waitForLoadingScreen();
        waitForElementToBeClickable(createBookButton).click();

        return new CreateBriefingBookModal(getDriver());
    }

    public BriefingBookDetailsPage viewNewBriefingBook() {

        waitForLoadingScreen();
        waitForElementToDissapear(clickStealer);
        waitForElementToBeClickable(newBriefingBook);
        waitForElementToRest(newBriefingBook, 1000L).click();

        return new BriefingBookDetailsPage(getDriver());
    }

    public BriefingBookList deleteNewBriefingBook(){
        findElement(checkbox).click();
        findElement(deleteButton).click();
        waitForElementToAppear(confirmDeleteButton);
        findElement(confirmDeleteButton).click();
        waitForElementToRest(reportList, 1000L);
        return this;
    }

    public BriefingBookList deleteAllBriefingBooks(){
        waitForLoadingScreen();
        if(!waitForAnyElementToAppear(bulkCheckbox).getAttribute("class").contains("x-item-disabled")){
            findVisibleElement(bulkCheckbox).click();
            findVisibleElement(deleteButton).click();
            waitForElementToAppear(confirmDeleteButton);
            findVisibleElement(confirmDeleteButton).click();
        }
        waitForLoadingScreen();
        return this;
    }

    public BriefingBookList searchFor(String searchTerm){
        waitForLoadingScreen();
        // findVisibleElement(searchBox).sendKeys(" ");
        //findVisibleElement(searchBox).clear();
        findVisibleElement(searchBox).sendKeys(searchTerm);
        waitForElementToRest(reportList, 1000L);
        return this;
    }

    public boolean briefingBooksAreDisplayed(){
        waitForLoadingScreen();
        return !findElement(emptyList).getText().contains("No data available.");
    }

    public boolean allTitlesContain(String term){
        waitForLoadingScreen();
        List<WebElement> titles = findElements(briefingBookTitle);
        if (titles.size()==0){
            System.out.println("Results are not displayed.");
            return false;
        }
        for (WebElement title : titles){
            if (!title.getText().toLowerCase().contains(term.toLowerCase())){
                System.out.println("Briefing book title: "+title.getText()+"\n\tdoes not contain term: "+term);
                return false;
            }
        }
        return true;
    }

    public void clickHeader(BriefingBookColumnType column){
        waitForLoadingScreen();
        switch(column){
            case TITLE:
                findElement(titleHeader).click();
                break;
            case AUTHOR:
                findElement(authorHeader).click();
                break;
            case CREATED:
                findElement(createdHeader).click();
                break;
            case LAST_UPDATED:
                findElement(updatedHeader).click();
                break;
        }
        if (doesElementExist(generalBriefingBookItem)) {
            waitForElementToRest(generalBriefingBookItem, 1000L);
        }
    }

    public BriefingBookList waitForListToUpdate() {
        waitForElement(reportList);
        waitForElementToRest(reportList, 1000L);
        return this;
    }
    public BriefingBookList switchToiframe(){
        WebElement _iframe = findElement(iframe);
        driver.switchTo().frame(_iframe);
        pause(500L);
        return this;
    }
    public  BriefingBookList leaveiframe(){
        driver.switchTo().defaultContent();
        pause(500L);
        return this;
    }




}

