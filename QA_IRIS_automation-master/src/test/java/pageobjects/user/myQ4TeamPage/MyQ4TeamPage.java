package pageobjects.user.myQ4TeamPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by patrickp on 2016-08-16.
 */
public class MyQ4TeamPage extends AbstractPageObject{
    private final By bioButton = By.cssSelector(".team-list .team-member .contact-information .bio-button");

    public MyQ4TeamPage(WebDriver driver) {
        super(driver);
    }

    public MyQ4TeamPage viewTeamBio() {
        findElement(bioButton).click();

        return this;
    }
}
