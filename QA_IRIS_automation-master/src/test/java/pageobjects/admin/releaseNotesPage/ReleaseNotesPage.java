package pageobjects.admin.releaseNotesPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.activityPage.ColumnType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dannyl on 2017-08-03.
 */

// This pageobject includes the details page of release notes
public class ReleaseNotesPage extends AbstractPageObject {

    private final By addButton = By.xpath("//button[contains(@class, 'square-button add')]");
    private final By editButton = By.xpath("//button[contains(text(), 'Edit')]");
    private final By firstReleaseNote = By.xpath("//tr[contains(@class, 'ui-widget-content ui-datatable-even')][1]");
    private final By searchField = By.xpath("//input[contains(@type, 'search')]");
    private final By dateHeader = By.xpath("//span[contains(text(), 'Date')]/parent::th");
    private final By versionHeader = By.xpath("//span[contains(text(), 'Release')]/parent::th");
    private final By statusHeader = By.xpath("//span[contains(text(), 'Status')]/parent::th");
    
    public ReleaseNotesPage (WebDriver driver) {super(driver);}

    private List<WebElement> returnTableRows (){
        List<WebElement> rowList = findVisibleElements(By.xpath("//tbody[contains(@class,'ui-datatable-data')]//div[contains(@class,'row')]//div[contains(@class, 'x-unsized x-list-item x-list-item-tpl event-list-item x-list-item-relative')]"));
        ArrayList<WebElement> tableRowsList = new ArrayList<>(rowList);
        return tableRowsList;

    }

    public EditReleaseNotesPage goToCreateReleaseNotePage(){

        waitForElementToBeClickable(addButton).click();
        return new EditReleaseNotesPage(driver);
    }

    public EditReleaseNotesPage openReleaseNoteEdit (String title){

        searchForNote(title);
        waitForLoadingScreen();
        waitForElementToBeClickable(editButton).click();
        return new EditReleaseNotesPage(driver);
    }

    public ReleaseNotesPage searchForNote (String title){
        waitForElementToBeClickable(searchField).sendKeys(title);
        waitForLoadingScreen();
        waitForElementToBeClickable(firstReleaseNote).click();

        return this;
    }
    public String checkCanDeleteReleaseNote (String title){

        try {
            searchForNote(title);
        }
        catch (Exception e)
        {
            return null;
        }
        EditReleaseNotesPage editReleaseNotesPage = new EditReleaseNotesPage(driver);
        return editReleaseNotesPage.getTitleOnEditPage();
    }

    private By getColumnSelector(ColumnType column){
        By selector =null;

        switch(column){
            case DATE:
                selector = dateHeader;
                break;
            case VERSION:
                selector = versionHeader;
                break;
            case STATUS:
                selector = statusHeader;
                break;
        }
        return selector;
    }

    public ReleaseNotesPage clickColumnHeader(ColumnType column){
        By selector = getColumnSelector(column);
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(selector));
        findElement(selector).click();
        return this;
    }


    boolean isColumnAscending(ColumnType column){
        By selector = getColumnSelector(column);
        return findElement(selector).getAttribute("class").contains("asc");

    }

    private boolean isDateSorted(List<WebElement> rows){
        ArrayList<WebElement> dates = new ArrayList<>();
        for(WebElement i : rows){
            dates.add(i.findElement(By.className("date")));
        }
        if(isColumnAscending(ColumnType.DATE)){
            return elementsAreDateUpSorted(dates);
        }else{
            return elementsAreDateDownSorted(dates);
        }
    }

    private boolean isVersionSorted(List<WebElement> rows){
        ArrayList<WebElement> versions = new ArrayList<>();
        for(WebElement i : rows){
            versions.add(i.findElement(By.className("version")));
        }
        if(isColumnAscending(ColumnType.VERSION)){
            return elementsAreAlphaUpSortedIgnoreCase(versions);
        }else{
            return elementsAreAlphaDownSortedIgnoreCase(versions);
        }
    }

    private boolean isStatusSorted(List<WebElement> rows){
        ArrayList<WebElement> statuses = new ArrayList<>();
        for(WebElement i : rows){
            statuses.add(i.findElement(By.className("statuses")));
        }
        if(isColumnAscending(ColumnType.VERSION)){
            return elementsAreAlphaUpSortedIgnoreCase(statuses);
        }else{
            return elementsAreAlphaDownSortedIgnoreCase(statuses);
        }
    }

    public boolean isColumnSorted(ColumnType column){
        waitForLoadingScreen();
        switch(column){
            case DATE:
                return isDateSorted(returnTableRows());
            case VERSION:
                return isVersionSorted(returnTableRows());
            case STATUS:
                return isStatusSorted(returnTableRows());
        }
        return false;
    }
}
