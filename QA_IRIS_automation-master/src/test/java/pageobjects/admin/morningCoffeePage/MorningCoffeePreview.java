package pageobjects.admin.morningCoffeePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import java.util.ArrayList;

/**
 * Created by noelc on 2017-01-18.
 */
public class MorningCoffeePreview extends AbstractPageObject {

   private final By mailIcon = By.className("mail");
   private final By deleteIcon = By.className("remove");
   private final By saveIcon =  By.className("button-dark-grey");
   private final By detailsSetion = By.xpath("//button[@contains(text(),'save')]");
   private final By saveConfirmation = By.xpath("//h2[contains(text(),'Success!')]");
   private final By cancelDelete = By.xpath("//q4-delete-confirm//button[contains(text(),'Cancel')]");
   private final By confirmDelete = By.xpath("//q4-delete-confirm//button[contains(text(),'Confirm')]");
   private final By marketCommentaryIframe = By.xpath("//div[contains(@class,'col-1') and .//span[contains(text(),'Market Commentary')]]//mce-editor//iframe");
    private final By sectorCommentaryIframe = By.xpath("//div[contains(@class,'col-1') and .//span[contains(text(),'Sector Commentary')]]//mce-editor//iframe");
    private final By companyCommentaryIframe = By.xpath("//div[contains(@class,'col-1') and .//span[contains(text(),'Company Commentary')]]//mce-editor//iframe");

    private final By textAreaOfCommentaryIframe =  By.xpath("//body");

    private final By morningCoffeeTitle = By.xpath("//q4-morning-coffee-details//div[contains(text(),'Morning Coffee Report')]");


    //*[@id="tinymce"]/p
    //Ugly selectors below. No real unique styling for this group of elements.
    //Stock summary selectors
    private final By stockSummary = By.xpath("//h3[contains(text(),'Stock Summary')]");
    private final By stockSummaryStockTitle = By.xpath("//q4-table-stock-summary/p-datatable//span[contains(text(),'Stock ($)')]");
    private final By stockSummaryChangeTitle= By.xpath("//q4-table-stock-summary/p-datatable//span[contains(text(),'Change ($)')]");
    private final By stockSummaryVolumeTitle = By.xpath("//q4-table-stock-summary/p-datatable//span[contains(text(),'volume')]");
    private final By stockSummaryStockAmount = By.xpath("//q4-table-stock-summary/p-datatable//table/tbody/tr/td[1]");
    private final By stockSummaryChangeAmount =By.xpath("//q4-table-stock-summary/p-datatable//table/tbody/tr/td[2]");
    private final By stockSummaryVolumeAmount =By.xpath("//q4-table-stock-summary/p-datatable//table/tbody/tr/td[3]");

    //MailingList selectors

    private final By mailingListNames = By.xpath("//morning-coffee-mail//li");
    private final By deleteNameWithX = By.xpath(".//button[contains(@class,'remove-button')]");


    public MorningCoffeePreview(WebDriver driver){
        super(driver);
    }

    public MorningCoffeePreview clickMailIcon(){
        findElement(mailIcon).click();
        return this;
    }


    public MorningCoffeePreview clickDeleteIcon(){
        findElement(deleteIcon).click();
        waitForLoadingScreen();
        return this;
    }

    public MorningCoffeePreview clickSaveIcon(){
        scrollToTopOfPage();
        pause(5000);
        clickElementLocation(saveIcon);
        return this;
    }



    public boolean canSaveMorningReport(){
        clickSaveIcon();
        waitForLoadingScreen();
        try{
        findElement(saveConfirmation);
        return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean previewPageLoaded(){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(morningCoffeeTitle));
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean canCancelDelete(){
        clickDeleteIcon();
        waitForLoadingScreen();
        wait.until(ExpectedConditions.visibilityOfElementLocated(cancelDelete));
        findElement(cancelDelete).click();
        try{
            findElement(deleteIcon);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public String returnMarketCommentary(){
        String commentary;
        driver.switchTo().frame(findElement(marketCommentaryIframe));
        commentary =findElement(textAreaOfCommentaryIframe).getText();
        driver.switchTo().defaultContent();
        return commentary;

    }
    public String returnSectorCommentary(){
        String commentary;
        driver.switchTo().frame(findElement(sectorCommentaryIframe));
        commentary =findElement(textAreaOfCommentaryIframe).getText();
        driver.switchTo().defaultContent();
        return commentary;

    }
    public String returnCompanyCommentary(){
        String commentary;
        driver.switchTo().frame(findElement(companyCommentaryIframe));
        commentary =findElement(textAreaOfCommentaryIframe).getText();
        driver.switchTo().defaultContent();
        return commentary;
    }

    public MorningCoffeePreview typeInMarketCommentary(String text){
        driver.switchTo().frame(findElement(marketCommentaryIframe));
       findElement(textAreaOfCommentaryIframe).sendKeys(text);
        pause(1000);
        driver.switchTo().defaultContent();
        return this;
    }

    public MorningCoffeePreview typeInSectorCommentary(String text){
 driver.switchTo().frame(findElement(sectorCommentaryIframe));
       findElement(textAreaOfCommentaryIframe).sendKeys(text);
        pause(1000);
        driver.switchTo().defaultContent();
        return this;
    }

    public MorningCoffeePreview typeCompanyCommentary(String text){
    driver.switchTo().frame(findElement(companyCommentaryIframe));
       findElement(textAreaOfCommentaryIframe).sendKeys(text);
        pause(1000);
        driver.switchTo().defaultContent();
        return this;
    }
    public MorningCoffeePage confirmDelete(){
        clickDeleteIcon();
        waitForLoadingScreen();
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmDelete));
        findVisibleElement(confirmDelete).click();
        waitForLoadingScreen();
        return new MorningCoffeePage(driver);
    }

    public boolean stockSummaryPresent() {
        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(stockSummary)));
            wait.until(ExpectedConditions.visibilityOf(findElement(stockSummaryStockTitle)));
            wait.until(ExpectedConditions.visibilityOf(findElement(stockSummaryChangeTitle)));
            wait.until(ExpectedConditions.visibilityOf(findElement(stockSummaryVolumeTitle)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public MorningCoffeePreview clickMailingList(){
        findElement(mailIcon).click();
        waitForLoadingScreen();
        return this;
    }

    public boolean nameIsPresentInMailingList(String name){
       ArrayList<WebElement> mailingList =new ArrayList<>(findElements(mailingListNames));
       for(WebElement nameInList : mailingList){

           if(nameInList.getText().contains(name)){
               return true;
           }
       }
       return false;
    }

    public MorningCoffeePreview removeNameFromMailingList(String name){
        ArrayList<WebElement> mailingList =new ArrayList<>(findElements(mailingListNames));
        for(WebElement nameInList : mailingList){

            if(nameInList.getText().contains(name)){
                nameInList.findElement(deleteNameWithX).click();
            }
        }
        return this;
    }


}
