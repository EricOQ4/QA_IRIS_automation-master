package pageobjects.admin.implementationPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-10-12.
 */
public class ImplementationPage extends AbstractPageObject {

    private final By addButton = By.cssSelector("body > q4-app > div > div > q4-implementation > header > div > div.action-buttons > button");
    private final By aliasField = By.name("alias");
    private final By sourceField = By.name("sourceURL");
    private final By saveButton = By.cssSelector("body > q4-app > div > div > q4-implementation > div.modal > p-dialog > div > div.ui-dialog-content.ui-widget-content > q4-implementation-create > div > button.button.button-yellow");
    private final By implementationList = By.cssSelector(".ui-datatable table");
    private final By firstImplementationEditButton = By.cssSelector(".q4-list .action-buttons .edit");
    private final By errorMessage = By.cssSelector("body > q4-app > div > div > q4-implementation > div.modal > p-dialog > div > div.ui-dialog-content.ui-widget-content > q4-implementation-create > div.error");
    private final By cancelButton = By.cssSelector("body > q4-app > div > div > q4-implementation > div.modal > p-dialog > div > div.ui-dialog-content.ui-widget-content > q4-implementation-create > div > button.button.button-no-background");

    public ImplementationPage(WebDriver driver) {
        super(driver);
    }

    public ImplementationPage addNewImplementation(String alias, String sourceUrl) {
        waitForLoadingScreen();
        findElement(addButton).click();
        findElement(aliasField).sendKeys(alias);
        findElement(sourceField).sendKeys(sourceUrl);
        findElement(saveButton).click();

        return this;
    }

    public String getImplementationList() {
        waitForLoadingScreen();
        return findElement(implementationList).getText();
    }

    public ImplementationPage selectFirstImplementation() {
        waitForLoadingScreen();
        findElement(firstImplementationEditButton).click();

        return this;
    }

    public ImplementationPage editDetails(String sourceUrl) {
        waitForLoadingScreen();
        findElement(sourceField).clear();
        findElement(sourceField).sendKeys(sourceUrl);
        findElement(saveButton).click();

        return this;
    }

    public String getError() {
        return findElement(errorMessage).getText();
    }

    public ImplementationPage openNewImplementationModal() {
        waitForLoadingScreen();
        findElement(addButton).click();

        return this;
    }


    public ImplementationPage cancelImplementationCreation() {
        waitForLoadingScreen();
        findElement(cancelButton).click();

        return this;
    }
}
