package pageobjects.user.aiTargetingPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by yevam on 2018-07-25
 */

/**
 * Updated by _____ on 2020-02-10
 */

public class SavedTargetsPage extends AbstractPageObject {


    //Profile filter tab
   // private final By savedTargetsAllButton = By.xpath("//div[contains(@class, 'q4-range-tabs-inner')]//span[contains(text(), 'All')]");

    private final By savedTargetsAllButton = By.xpath("//button[contains(@class, 'range-tab_button')]//div[contains(text(), 'All')]");

    //private final By savedTargetsInstitutionsButton = By.xpath("//div[contains(@class, 'q4-range-tabs-inner')]//span[contains(text(), 'Institutions')]");
    private final By savedTargetsInstitutionsButton = By.xpath("//button[contains(@class, 'range-tab_button')]//div[contains(text(), 'Institutions')]");

    //private final By savedTargetsFundsButton = By.xpath("//div[contains(@class, 'q4-range-tabs-inner')]//span[contains(text(), 'Funds')]");
    private final By savedTargetsFundsButton = By.xpath("//button[contains(@class, 'range-tab_button')]//div[contains(text(), 'Funds')]");
   // private final By savedTargetsContactsButton = By.xpath("//div[contains(@class, 'q4-range-tabs-inner')]//span[contains(text(), 'Contacts')]");

   private final By savedTargetsContactsButton = By.xpath("//button[contains(@class, 'range-tab_button')]//div[contains(text(), 'Contacts')]");


    //Export button
    private final By savedTargetsExportButton = By.xpath("//div[contains(@class, 'section-tab_view-item section-tab_view-item--active')]//button[contains(@class, 'button--steel')]");
    //Search
    //private final By savedTargetsSearchField = By.xpath("(//div[contains(@class,'field-search')]//input[contains(@class,'x-input-search')])[2]"); //here
    private final By savedTargetsSearchField = By.xpath("//input[contains(@class, 'section-search_input')]");

    //Table header
    /*
    private final By savedTargetsTableHeaderName = By.xpath("//span[contains(text(), 'Name')]");
    private final By savedTargetsTableHeaderLocation = By.xpath("//span[contains(text(), 'Location')]");
    private final By savedTargetBulkCheckbox = By.xpath("(//div[contains(@class,'x-field-input')] //div[contains(@class,'x-field-mask')]) [3]");*/
    //Table
    /*
    private final By savedTargetCheckbox = By.xpath("(//div[contains(@class,'column column--bulk checkbox')]) [1]");
    private final By savedTargetTrushbinButton = By.xpath("//span[contains(@class,'q4i-trashbin-4pt')]");*/


    //private final By savedTargetsTableLocation = By.xpath("(//div[contains(@class,'column column_location')]) [1]");
    private final By savedTargetsTableLocation = By.xpath("(//div[contains(@role, 'row')]//div[contains(@col-id, 'entity_name')])[1]");

    //private final By savedTargetsTableRows = By.xpath("//div[contains(@class, 'x-list-item')]");
    private final By savedTargetsTableRows = By.xpath("//div[contains(@role, 'row')]/div[contains(@col-id, 'entity_name')]");

    //private final By savedTargetsTableProfileNames = By.xpath("//div[contains(@class, 'target-page_grid_detail--name')]");
    private final By savedTargetsTableProfileNames = By.xpath("//div[contains(@role, 'row')]/div[contains(@col-id, 'entity_name')]//div[contains(@class, 'cell_title--name')]");

  // private final By savedTargetsInstitutionIcon = By.xpath("(//div[contains(@class, 'column_title')]//i[contains(@class, 'q4i-institution-2pt')])[1]");
   // private final By savedTargetsFundIcon = By.xpath("(//div[contains(@class, 'column_title')]//i[contains(@class, 'q4i-fund-2pt')])[1]");
    // private final By savedTargetsContactIcon = By.xpath("(//div[contains(@class, 'column_title')]//i[contains(@class, 'q4i-contact-2pt')])[1]");

    private final By savedTargetsInstitutionIcon = By.xpath("(//div[contains(@class, 'cell')]//i[contains(@class, 'q4i-institution-2pt')])[1]");
    private final By savedTargetsFundIcon = By.xpath("(//div[contains(@class, 'cell')]//i[contains(@class, 'q4i-fund-2pt')])[1]");
    private final By savedTargetsContactIcon = By.xpath("(//div[contains(@class, 'cell')]//i[contains(@class, 'q4i-contact-2pt')])[1]");

    private final By savedTargetsTableNameColumn = By.xpath("//div[@col-id='entity_name']//div[@class='cell_title--name']");
    private final By savedTargetsTableLocationColumn = By.xpath("//div[@col-id='locations']//div[@class='expandable-cell_label']");

    private final By PaginationPageLink = By.xpath("//div[contains(@class, 'section-tab_view-item section-tab_view-item--active')]//ul[@class='pagination_actions pagination_actions--pages']//a");
    private final By PaginationPageRight = By.xpath("//div[contains(@class, 'section-tab_view-item section-tab_view-item--active')]//ul[@class='pagination_actions pagination_actions--pages']//a//i[@class='q4i-arrow-sm-right-4pt']");

    //private - access modifier; By - object type, [] - means array; filterTabs - name of the array; new - need to declare an array;
    // By[4] - need to specify the size of the array before storing objects in the array
    private By[] filterTabs = new By[4];
    private By[] profileTableIcons = new By[3];




    public SavedTargetsPage(WebDriver driver) {
        super(driver);
    }


    public void setFilterTabs() {
        filterTabs[0] = savedTargetsAllButton;
        filterTabs[1] = savedTargetsInstitutionsButton;
        filterTabs[2] = savedTargetsFundsButton;
        filterTabs[3] = savedTargetsContactsButton;

    }

    public void setProfileTableIcons() {
        profileTableIcons[0] = savedTargetsInstitutionIcon;
        profileTableIcons[1] = savedTargetsFundIcon;
        profileTableIcons[2] = savedTargetsContactIcon;

    }


    // Click profile filter tabs
    //checkFilterTabs(int index) - "int index" to set the params, need when declare the method
    public boolean checkFilterTabs(int index) {
        waitForLoadingScreen();
        waitForElementToBeClickable(filterTabs[index]).click();
        waitForLoadingScreen();

        return checkProfileIcons(index - 1);
    }



    public boolean checkProfileIcons(int index) {
        List<WebElement> tableRows = returnRows();
        for (WebElement i : tableRows) {
            try {
                i.findElement(profileTableIcons[index]);
            } catch (ElementNotFoundException e) {
                return false;
            }
        }
        return true;
    }

    //Click All profile tab
    public boolean clickAllTab(){

        waitForLoadingScreen();
        waitForElementToBeClickable(filterTabs[1]).click();
        waitForLoadingScreen();
        waitForElementToBeClickable(filterTabs[0]).click();
        waitForLoadingScreen();

        return findElements(savedTargetsTableLocation).size() != 0;
    }


    private List<WebElement> returnRows() {
        List<WebElement> rowList = findVisibleElements(savedTargetsTableRows);
        return new ArrayList<>(rowList);

    }


    //Search Target
    public boolean searchForTarget(String targetName) {

        waitForLoadingScreen();

        waitForElement(savedTargetsSearchField);
        findVisibleElement(savedTargetsSearchField).click();
        findVisibleElement(savedTargetsSearchField).sendKeys(targetName);
        waitForLoadingScreen();

        // I commented this out since the waitForElementToRest() method seems to be broken with the new react grids, so we just rely on the waitForLoadingScreen() method
        //      to do the wait for us. It seems to work fine currently, but I'll leave this here anyways.

        /*if (doesElementExist(savedTargetsTableRows)) {

           waitForElementToRest(savedTargetsTableRows, 2000L);
        }*/

        waitForLoadingScreen();

        List<WebElement> tableRows = returnProfileNames();
        String savedTargetSearchResult = tableRows.get(0).getText();

        return savedTargetSearchResult.equals(targetName);

    }


    private List<WebElement> returnProfileNames() {
        List<WebElement> rowList = findVisibleElements(savedTargetsTableProfileNames);
        return new ArrayList<>(rowList);

    }

    public void clickExportButton() {
        waitForLoadingScreen();
        waitForElementToAppear(savedTargetsExportButton);
        waitForElementToBeClickable(savedTargetsExportButton);
        findElement(savedTargetsExportButton).click();

        pause(100L); // This pause is just to give time for the download to start, once it starts the downloadVerification will properly wait for it to finish
    }

    public Vector<Vector<String>> parseGrid(){


        Vector<Vector<String>> parsedGrid = new Vector<Vector<String>>();
        Vector<String> entities = new Vector<String>();
        Vector<String> names = new Vector<String>();
        Vector<String> locations = new Vector<String>();

        entities.add("Entity");
        names.add("Name");
        locations.add("Location");

        waitForLoadingScreen();
        waitForElementToAppear(savedTargetsTableNameColumn);
        waitForElementToAppear(savedTargetsTableLocationColumn);
        waitForElementToAppear(PaginationPageLink);

        List<WebElement> PaginationElements = findVisibleElements(PaginationPageLink);

        int pageNum = Integer.parseInt(PaginationElements.get(PaginationElements.size() - 2).getText());

        for(int j = 0; j < pageNum; j++) {

            List<WebElement> nameElements = findVisibleElements(savedTargetsTableNameColumn);
            List<WebElement> locationElements = findVisibleElements(savedTargetsTableLocationColumn);

            int nameSize = nameElements.size();
            int locationSize = locationElements.size();

            for (int i = 0; i < nameSize; i++) {

                if(findVisibleElement(By.xpath("//div[@row-index='" + i + "']//div[@col-id='entity_name']//i")).getAttribute("class").equals("cell_icon cell_icon--contact q4i-contact-2pt")){
                    entities.add("Contact");
                }else if(findVisibleElement(By.xpath("//div[@row-index='" + i + "']//div[@col-id='entity_name']//i")).getAttribute("class").equals("cell_icon cell_icon--institution q4i-institution-2pt")){
                    entities.add("Institution");
                }else if(findVisibleElement(By.xpath("//div[@row-index='" + i + "']//div[@col-id='entity_name']//i")).getAttribute("class").equals( "cell_icon cell_icon--fund q4i-fund-2pt")){
                    entities.add("Fund");
                }else{
                    entities.add("");
                }

                names.add(nameElements.get(i).getText());

                if(i >= locationSize){
                    locations.add("");
                }else{
                    locations.add(locationElements.get(i).getText());
                }
            }

            if(j + 1 != pageNum){
                findElement(PaginationPageRight).click();
            }

            waitForLoadingScreen();
            waitForElementToAppear(savedTargetsTableNameColumn);
            waitForElementToAppear(savedTargetsTableLocationColumn);
        }

        parsedGrid.add(entities);
        parsedGrid.add(names);
        parsedGrid.add(locations);

        return parsedGrid;
    }


}