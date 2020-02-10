package pageobjects.admin.usersPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import java.util.ArrayList;

/**
 * Created by noelc on 2016-11-22.
 */
public class UsersPage extends AbstractPageObject {

    public UsersPage(WebDriver driver) {
        super(driver);
    }

    private final By userRoleFilter = By.xpath("//nav[contains(@class,'dropdown-menu') and .//div[contains(text(),'Roles')]]//label[contains(@class,'ui-dropdown-label')]");
    private final By userRoleFilterList=By.xpath("//nav[contains(@class,'dropdown-menu') and .//div[contains(text(),'Roles')]]//div[contains(@class,'ui-dropdown-panel')]");
    private final By tableData=By.xpath("//tbody/tr/td[not(contains(@class,'dropdown-overflow'))]");

    private ArrayList<WebElement> getUsers (){
        waitForLoadingScreen();
        ArrayList<WebElement> userRoles = new ArrayList<>(findElements(tableData));
        return userRoles;
    }

    public UsersPage clickUserRoleFilter(){
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(userRoleFilter));
        findElement(userRoleFilter).click();
        return this;
    }

    public UsersPage clickRoleInFilter (UserRole role){
        wait.until(ExpectedConditions.visibilityOfElementLocated(userRoleFilterList));
        WebElement roleList=findElement(userRoleFilterList);
        roleList.findElement(By.xpath(".//li[span[contains(text(),'"+role.getRole()+"')]]")).click();
        waitForLoadingScreen();
        return this;
    }

    public boolean areUsersFilteredByRole(UserRole role){

        ArrayList<WebElement> filteredUsers = getUsers();
        boolean hasRole = false;
        for (WebElement currentuser : filteredUsers){
            ArrayList<WebElement> usersroles = new ArrayList<>(currentuser.findElements(By.tagName("button")));
            for( WebElement userrole : usersroles){
                if(userrole.getText().equals(role.getRole())){
                    hasRole=true;
                }
            }
            if(!hasRole){
                return false;
            }
            else{
                hasRole=false;
            }
        }
        return true;
    }

    public UserRoleDetails clickFirstUserFromResults(){
        findElement(By.xpath("//tbody/tr[1]")).click();
        waitForLoadingScreen();
        return new UserRoleDetails(driver);
    }

    public UserRoleDetails selectUser(String userEmail){
        ArrayList<WebElement> users = getUsers();
        for(int i=0;i<users.size();i++){
            if(users.get(i).getText().contains(userEmail)){
                users.get(i).click();
                waitForLoadingScreen();
                return new UserRoleDetails(driver);
            }
        }
        return null;
    }


}
