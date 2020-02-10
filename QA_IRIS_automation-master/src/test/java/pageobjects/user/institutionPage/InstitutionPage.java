package pageobjects.user.institutionPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.Page;

import java.util.List;

/**
 * Created by patrickp on 2016-08-08.
 */
public class InstitutionPage extends Page {

    private final By iframe = By.xpath("//iframe[contains(@id, 'sencha-viewport')]");

    private final By institutionName  = By.xpath("//div[contains(@class,'page-title')]");
    private final By institutionIcon = By.className("institution");
    private final By targetIcon = By.className("q4i-savedtargets-2pt");
    private final By activistIcon = By.className("q4i-activist-2pt");
    private final By QrIcon = By.cssSelector(".rating .val");



    public InstitutionPage(WebDriver driver) {
        super(driver);
    }


    public String getInstitutionName() {
        waitForLoadingScreen();
        switchToiframe();
        waitForAnyElementToAppear(institutionName);
        String name = findVisibleElement(institutionName).getText();
        leaveiframe();
        return name;
    }

    public String getURL(){
        waitForElementToAppear(institutionIcon);
        return driver.getCurrentUrl();
    }

    public boolean isSavedTarget(){
        waitForElement(QrIcon);
        return findVisibleElements(targetIcon).size() > 0;
    }

    public boolean isActivist(){
        waitForElement(activistIcon);
        return findVisibleElements(activistIcon).size() > 0;
    }


    public void switchToiframe(){
        WebElement _iframe = findElement(iframe);
        driver.switchTo().frame(_iframe);
        pause(500L);
    }

    public void leaveiframe(){
        driver.switchTo().defaultContent();
        pause(500L);
    }
}
