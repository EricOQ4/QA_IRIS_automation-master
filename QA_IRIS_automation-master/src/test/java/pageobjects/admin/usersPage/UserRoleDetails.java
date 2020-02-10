package pageobjects.admin.usersPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by noelc on 2017-02-03.
 */
public class UserRoleDetails extends AbstractPageObject {

    private final By toggleSelector = By.xpath(".//td[contains(@class,'action-switch')]//p-inputswitch");

    public UserRoleDetails(WebDriver driver){
        super(driver);
    }

    public UserRoleDetails enableRole(UserRole role){
        if(!isRoleEnabled(role)){
            findElement(By.xpath(role.returnSelector())).findElement(toggleSelector).click();
        }
        return this;
    }

    public UserRoleDetails disableRole(UserRole role){
        if(isRoleEnabled(role)){
            findElement(By.xpath(role.returnSelector())).findElement(toggleSelector).click();
        }
        return this;
    }

    public UserRoleDetails disableAllRoles(){
        for(UserRole role : UserRole.values()){
            disableRole(role);
        }
        return this;
    }


    public boolean isRoleEnabled(UserRole role){

        if(findElement(By.xpath(role.returnSelector())).findElement(toggleSelector).getAttribute("class").contains("off")){
            return false;
        }
        else{
            return true;
        }
    }

}
