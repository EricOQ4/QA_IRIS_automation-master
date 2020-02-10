package pageobjects.user.fundPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.Page;

/**
 * Created by patrickp on 2016-08-08.
 *//**
 * Updated by abbyl on 2019-04-18.
 */

public class FundPage extends Page {

    private final By iframe = By.xpath("//iframe[contains(@id, 'sencha-viewport')]");
    private final By fundName = By.className("page-title");

    public FundPage(WebDriver driver) {
        super(driver);
    }

    public String getFundName() {
        waitForLoadingScreen();
        switchToiframe();
        waitForAnyElementToAppear(fundName);
        String name = findVisibleElement(fundName).getText();
        leaveiframe();
        return name;
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