package pageobjects.admin.releaseNotesPage;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by dannyl on 2017-08-03.
 */
public class EditReleaseNotesPage extends AbstractPageObject {

    private final By versionInput = By.xpath("//input[contains(@id, 'version')]");
    private final By titleInput = By.xpath("//input[contains(@id, 'title')]");
    private final By releaseDateInput = By.xpath("//span[contains(@class, 'ui-calendar')]");
    private final By overviewInput = By.xpath("//div[contains(@class,'ql-editor ql-blank')]");
    private final By overviewInputEdit = By.xpath("//div[contains(@class,'ql-editor')]");
    private final By closeConfirmationMessageButton = By.xpath("//div[contains(@class, 'ui-dialog-titlebar ui-widget-header ui-helper-clearfix ui-corner-top')]/a[contains(@class, 'ui-dialog-titlebar-icon ui-dialog-titlebar-close ui-corner-all')]");
    private final By publishButton = By.xpath("//button[contains(text(), 'Publish')]");
    private final By unpublishButton = By.xpath("//button[contains(text(), 'Un-Publish')]");
    private final By saveButton = By.xpath("//button[contains(text(), 'Save')]");
    private final By deleteButton = By.xpath("//button[contains(@class, 'remove')]");
    private final By modalDeleteButton = By.xpath("//button[contains(text(), 'Confirm')]");
    private final By modalCancelButton = By.xpath("//button[contains(@class, 'button button-no-background')]");
    private final By editReleaseNoteTitle = By.xpath("//div[contains(@class, 'page-title')]");

    public EditReleaseNotesPage(WebDriver driver){super(driver);}

    public EditReleaseNotesPage createReleaseNote (String version, String title, String releaseDay, String overview){
        waitForElementToAppear(versionInput).sendKeys(version);
        waitForElementToAppear(titleInput).sendKeys(title);
        waitForElementToAppear(releaseDateInput).click();
        waitForElementToAppear(By.xpath("//a[contains(@class, 'ui-state-default')][contains(text(), '"+ releaseDay +"')]")).click();
        waitForElementToAppear(overviewInput).sendKeys(overview);

        saveReleaseNote();
        return this;
    }


    public ReleaseNotesPage deleteReleaseNote (String title){
        waitForElementToBeClickable(deleteButton).click();
        waitForElementToBeClickable(modalDeleteButton).click();

        return new ReleaseNotesPage(driver);
    }

    public void saveReleaseNote (){
        waitForElementToBeClickable(saveButton).click();
        try
        {
            waitForElementToAppear(closeConfirmationMessageButton).click();
        }
        catch (TimeoutException e){
            System.out.println("No confirmation modal appeared.");
        }
    }

    public void publishReleaseNote (){
        waitForElementToBeClickable(publishButton).click();
        try
        {
            waitForElementToAppear(closeConfirmationMessageButton).click();
        }
        catch (TimeoutException e){
            System.out.println("No confirmation modal appeared.");
        }
    }

    public void editReleaseNote (String title, String overview, String version, String releaseDay){
        waitForLoadingScreen();

        waitForElementToAppear(titleInput).clear();
        waitForElementToAppear(titleInput).sendKeys(title);

        waitForElementToAppear(overviewInputEdit).clear();
        waitForElementToAppear(overviewInputEdit).sendKeys(overview);

        waitForElementToAppear(versionInput).clear();
        waitForElementToAppear(versionInput).sendKeys(version);

        waitForElementToAppear(releaseDateInput).click();
        waitForElementToAppear(By.xpath("//a[contains(@class, 'ui-state-default')][contains(text(), '"+ releaseDay +"')]")).click();

        saveReleaseNote();
    }

    public ReleaseNotesPage cancelDeleteReleaseNote (){
        waitForElementToBeClickable(deleteButton).click();
        waitForElementToBeClickable(modalCancelButton).click();

        return new ReleaseNotesPage(driver);
    }

    public boolean checkCanSaveReleaseNote (String title){
        new ReleaseNotesPage(driver).navigateToReleaseNotesPage().openReleaseNoteEdit(title);
        try
        {
        waitForElementToAppear(publishButton);
        }
        catch (ElementNotFoundException e){
            return false;
        }
        return true;
    }

    public boolean checkCanPublishReleaseNote (String title){
        new ReleaseNotesPage(driver).navigateToReleaseNotesPage().openReleaseNoteEdit(title);
        try
        {
            waitForElementToAppear(unpublishButton);
        }
        catch (ElementNotFoundException e){
            return false;
        }
        return true;
    }


    public String getTitleOnEditPage (){
        waitForLoadingScreen();
        return waitForElementToAppear(editReleaseNoteTitle).getText();
    }

}
