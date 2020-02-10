package pageobjects.user.headerPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.PageObject;
import pageobjects.user.contactPage.ContactDetailsPage;
import pageobjects.user.securityPage.SecurityOverviewPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarahr on 2/1/2017.
 */
/**
 * Updated by abbyl on 3/1/2019.
 */

public interface HeaderPage extends PageObject{

    //selectors

    //Header buttons
    By searchBar = By.xpath("//div[contains(@class,'app-header_search')]//input[contains(@type,'text')]");
    By securityButton = By.xpath("//div[contains(@class,'global-header')]//div[contains(@class,'profile-company')]");
    //    By chatButton = By.xpath("//div[contains(@class,'chat-button')]");    //not exist anymore
    By profileButton = By.xpath("//i[contains(@class,'app-header_icon icon q4i-contact-2pt')]");

    //chat buttons; chat feature is not exist
    /*
    By chatMessageField = By.xpath("//textarea[contains(@name,'message')]");
    By clearChatBtn = By.xpath("//form//div[contains(@class,'clear')]//span[contains(text(),'Clear')]");
    By emptyMessageField = By.xpath("//form[contains(@class,'chat-form')]//div[contains(@class,'x-empty')]");
    */

    //profile menu
    By releaseNotesButton = By.xpath("//li[contains(@role,'menuitem')][1]");
    By leaveFeedbackButton = By.xpath("//li[contains(@role,'menuitem')][3]");
    By changePasswordButton = By.xpath("//li[contains(@role,'menuitem')][5]");
    By logoutButton = By.xpath("//li[contains(@role,'menuitem')][6]");

    //logout buttons ; not exist
    /*
    By logoutOptionsField = By.xpath("//div[contains(@class,'x-msgbox') and contains(@class,'x-floating')]");
    By logoutConfirmation = By.xpath("//div[contains(@class,'x-msgbox')]//span[contains(text(),'Yes')]");
    By logoutRejection = By.xpath("//div[contains(@class,'x-msgbox')]//span[contains(text(),'No')]");
    */

    //change password modal: buttons/fields
    By cancelChangePass = By.xpath("//button[contains(text(),'Cancel')]");
    By postChangePassBtn = By.xpath("//button[contains(text(),'Submit')]");
    By currentPassField = By.xpath("//input[contains(@id,'oldPass')]");
    By newPassField = By.xpath("//input[contains(@id,'newPass')]");
    By confirmNewPassField = By.xpath("//input[contains(@id,'confirmPass')]");
    By changePassField = By.xpath("//div[@class = 'modal_wrap']");
    //  By changePassField = By.xpath("//form[contains(@class,'settings-change-password')]");

    //change password error messages
    By noCurrentPassMsg = By.xpath("//div[contains(@class,'formik-text-input--error')]//input[(@id='oldPass')]");
    By noNewPassMsg = By.xpath("//div[contains(@class,'formik-text-input--error')]//input[(@id='newPass')]");
    By notSecurePassMsg = By.xpath("//div[contains(@class,'tooltip--error--right')]");
    By noConfirmationPassMsg = By.xpath("//div[contains(@class,'formik-text-input--error')]//input[(@id='confirmPass')]");
    By wrongOldPassMsg = By.xpath("//div[contains(text(),'Old password is incorrect')]");
    By samePassAsBeforeMsg = By.xpath("//div[contains(@class,'tooltip--error--right')]");


    //feedback buttons
    By postFeedbackButton = By.xpath("//button[contains(text(),'Submit')]");
    By cancelFeedback = By.xpath("//button[contains(text(),'Cancel')]");
    By emptyFieldMessage = By.xpath("//div[contains(@class,'tooltip--error--right')]");
    //    By msgboxOKBtn = By.xpath("//div[contains(@class,'x-msgbox')]//span[contains(text(),'OK')]");
//    By sucessfulSubmittionField = By.xpath("//div[contains(text(),'Thank you')]");
    By feedbackMsgField = By.xpath("//*[@id=\"message\"]");


    //Search results
    //These xpaths are large because the selectors completely change if a search query returns only one result.
    /*
    By securityResults = By.xpath("//div[preceding-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]//div[contains(text(),'security')] and following-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]]");
    By institutionResults = By.xpath("//div[preceding-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]//div[contains(text(),'institution')] and following-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]]");
   By contactResults = By.xpath("//div[preceding-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]//div[contains(text(),'contact')] and following-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]] | //div[preceding-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][contains(@class, 'x-list-item-first')][not(contains(@class, 'x-hidden-display'))]//div[contains(text(),'contact')]]");
    By fundResults = By.xpath("//div[preceding-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]//div[contains(text(),'fund')]]");
  */
    By genericSearchResults = By.xpath("//div[contains(@class,'app-header_results')]//ul[contains(@role,'listbox')]");
    By releaseNotesPageHeader = By.xpath("//h1[contains(text(),'Release Notes')]");


    //Quick Search
    By quickSearchBar = By.xpath("//div[contains(@class,'quickSearch-input')]//input[contains(@type,'text')]");
    By quickGenericSearchResults = By.xpath("//div[contains(@class,'quickSearch-results')]//ul[contains(@role,'listbox')]//span[contains(@class,'link-name')]");



    default HeaderPage searchResults(String searchTerm, String name){
        waitForElementToBeClickable(searchBar).click();
        findElement(searchBar).clear();
        waitForElementToAppear(searchBar).sendKeys(searchTerm);
        pause(2000L);
        //waitForAnyElementToAppear(genericSearchResults);
        //pause(2000L);

        List<WebElement> results = findElements(genericSearchResults);
        for (WebElement result : results) {
            if (result.getText().contains(name)) {
                result.click();
                return this;
            }
        }

        // If not found, click first element
        System.out.println("Failed to find " + name + " in search results, choosing first from list");
        findElement(genericSearchResults).click();
        return this;
    }

    default HeaderPage quickSearchResults(String searchTerm, String name){
        waitForElementToBeClickable(quickSearchBar).click();
        findElement(quickSearchBar).clear();
        waitForElementToAppear(quickSearchBar).sendKeys(searchTerm);
        pause(2000L);

            List<WebElement> results = findElements(quickGenericSearchResults);
            if (results.size() > 0) {
                for (WebElement result : results) {

                    if (result.getText().contains(searchTerm)) {
                        result.click();
                        return this;
                    }
                }
                System.out.println("Failed to find " + name + " in search results, choosing first from list");
                System.out.println("Pick first one from the list");
                results.get(0).click();
            } else {
                System.out.println("No result data list");
            }

        return this;
    }

    default HeaderPage quickSearchDetailResults(String searchTerm, String name){
        waitForElementToBeClickable(quickSearchBar).click();
        findElement(quickSearchBar).clear();
        waitForElementToAppear(quickSearchBar).sendKeys(searchTerm);
        pause(2000L);

        List<WebElement> results = findElements(quickGenericSearchResults);
        if (results.size() > 0) {
            for (WebElement result : results) {

                if (result.getText().contains(name)) {
                    result.click();
                    return this;
                }
            }
            System.out.println("Failed to find " + name + " in search results, choosing first from list");
            System.out.println("Pick first one from the list");
            results.get(0).click();
        } else {
            System.out.println("No result data list");
        }

        return this;
    }

/*
    default HeaderPage securitySearch(String searchTerm, String title){
        waitForElementToBeClickable(searchBar).click();
        findElement(searchBar).clear();
        findElement(searchBar).sendKeys(searchTerm);

        waitForAnyElementToAppear(securityResults);

        List<WebElement> contacts = findElements(securityResults);
        for (WebElement contact : contacts) {
            if (contact.getText().contains(title)) {
                contact.click();
                return this;
            }
        }

        // If not found, click first element
        System.out.println("Failed to find " + title + " in search results, choosing first from list");
        findElement(securityResults).click();

        return this;
    }

    default HeaderPage institutionSearch(String searchTerm, String title){
        waitForElementToBeClickable(searchBar).click();
        findElement(searchBar).clear();
        findElement(searchBar).sendKeys(searchTerm);

        waitForAnyElementToAppear(institutionResults);

        List<WebElement> contacts = findElements(institutionResults);
        for (WebElement contact : contacts) {
            if (contact.getText().contains(title)) {
                contact.click();
                return this;
            }
        }

        // If not found, click first element
        System.out.println("Failed to find " + title + " in search results, choosing first from list");
        findElement(institutionResults).click();

        return this;
    }

    default HeaderPage fundSearch(String searchTerm, String title){
        findElement(searchBar).click();
        findElement(searchBar).clear();
        findElement(searchBar).sendKeys(searchTerm);

        waitForAnyElementToAppear(fundResults);

        List<WebElement> contacts = findElements(fundResults);
        for (WebElement contact : contacts) {
            if (contact.getText().contains(title)) {
                contact.click();
                return this;
            }
        }

        // If not found, click first element
        System.out.println("Failed to find " + title + " in search results, choosing first from list");
        findElement(fundResults).click();

        return this;
    }
*/
    /////
    /////


    default SecurityOverviewPage smallstockQuote(){
        findElement(securityButton).click();

        return new SecurityOverviewPage(getDriver());
    }

    //this opens the chat, but where should all the chat info go? ; chat feature is not exist
    /*
    default void openChat(){
        waitForElementToBeClickable(chatButton).click();
    }

    default boolean clearChat(String message){

        waitForElementToBeClickable(chatMessageField).sendKeys(message);
        waitForElementToBeClickable(clearChatBtn).click();

        try{
            waitForElement(emptyMessageField);
            if(findElement(emptyMessageField).isDisplayed()){
                return true;
            }
            else{
                return false;
            }

        }
        catch(Exception e){
            return false;
        }

    }
    */

    default void openProfile(){
        waitForElementToBeClickable(profileButton).click();
    }


    //in profile
    default void clickReleaseNotes(){
        waitForElementToBeClickable(releaseNotesButton).click();
        try{
            waitForElement(releaseNotesPageHeader);
        }
        catch(Exception e){
        }
    }

    default boolean leaveBlankFeedback(){
        waitForElementToBeClickable(leaveFeedbackButton).click();
        pause(1000L);
        waitForElementToBeClickable(postFeedbackButton).click();

        waitForElementToAppear(emptyFieldMessage);


        if(getDriver().findElement(emptyFieldMessage).isDisplayed()){

            return true;
        }

        return false;
    }

    default boolean leaveFeedback(String feedbackMsg){
        waitForElementToBeClickable(leaveFeedbackButton).click();
        waitForElementToBeClickable(feedbackMsgField).sendKeys(feedbackMsg);

        waitForElementToBeClickable(cancelFeedback).click();

        try {
            if (getDriver().findElement(feedbackMsgField).isDisplayed()) {
                return false;
            }
            else{
                return true;
            }
        }

        catch(Exception e){
            return true;

        }
    }


    default boolean cancelChangePassword(){
        waitForElementToBeClickable(changePasswordButton).click();
        pause(2000L);
        waitForElementToBeClickable(cancelChangePass).click();

        try{

            if(getDriver().findElement(changePassField).isDisplayed()){
                return false;
            }
            else{
                return true;
            }
        }

        catch(Exception e){
            return true;
        }


    }

    default boolean isClosed(){
        waitForLoadingScreen();
        if(findElements(changePassField).size() ==0){
            return true;
        }
        return false;
    }

    default boolean noCurrentPassword(String newPass){
        waitForElementToBeClickable(changePasswordButton).click();
        waitForElementToBeClickable(newPassField).sendKeys(newPass);
        waitForElementToBeClickable(confirmNewPassField).sendKeys(newPass);

        waitForElementToBeClickable(postChangePassBtn).click();


        try{
            waitForElementToAppear(noCurrentPassMsg);
            if(getDriver().findElement(noCurrentPassMsg).isDisplayed()){
                return true;
            }
            else{
                return false;
            }
        }

        catch(Exception e){
            return false;
        }
    }

    default boolean noNewPassword(String currentPass){
        waitForElementToBeClickable(changePasswordButton).click();
        waitForElementToBeClickable(currentPassField).sendKeys(currentPass);
        pause(1000L);
        waitForElementToBeClickable(postChangePassBtn).click();

        try{
            waitForElementToAppear(noNewPassMsg);
            if(getDriver().findElement(noNewPassMsg).isDisplayed()){
                return true;
            }
            else{
                return false;
            }
        }

        catch(Exception e){
            return false;
        }

    }

    default boolean notSecurePass(String currentPass, String newPass){
        waitForElementToBeClickable(changePasswordButton).click();
        waitForElementToBeClickable(currentPassField).sendKeys(currentPass);
        waitForElementToBeClickable(newPassField).sendKeys(newPass);

        waitForElementToBeClickable(postChangePassBtn).click();

        try{
            waitForElementToAppear(notSecurePassMsg);
            if(getDriver().findElement(notSecurePassMsg).isDisplayed()){
                return true;
            }
            else{
                return false;
            }
        }

        catch(Exception e){
            return false;
        }

    }

    default boolean noConfirmationPass(String currentPass, String newPass){
        waitForElementToBeClickable(changePasswordButton).click();
        waitForElementToBeClickable(currentPassField).sendKeys(currentPass);
        waitForElementToBeClickable(newPassField).sendKeys(newPass);

        waitForElementToBeClickable(postChangePassBtn).click();

        try{
            waitForElementToAppear(noConfirmationPassMsg);
            if(getDriver().findElement(noConfirmationPassMsg).isDisplayed()){
                return true;
            }
            else{
                return false;
            }
        }

        catch(Exception e){
            return false;
        }

    }

    default boolean wrongOldPass(String currentPass, String newPass){
        waitForElementToBeClickable(changePasswordButton).click();
        waitForElementToBeClickable(currentPassField).sendKeys(currentPass);
        pause(1000L);
        waitForElementToBeClickable(newPassField).sendKeys(newPass);
        waitForElementToBeClickable(confirmNewPassField).sendKeys(newPass);

        waitForElementToBeClickable(postChangePassBtn).click();

        try{
            waitForElementToAppear(wrongOldPassMsg);
            if(getDriver().findElement(wrongOldPassMsg).isDisplayed()){
                return true;
            }
            else{
                return false;
            }
        }

        catch(Exception e){
            return false;
        }

    }

    default boolean samePassAsBefore(String password){
        waitForElementToBeClickable(changePasswordButton).click();
        waitForElementToBeClickable(currentPassField).sendKeys(password);
        pause(1000L);
        waitForElementToBeClickable(newPassField).sendKeys(password);
        waitForElementToBeClickable(confirmNewPassField).sendKeys(password);

        waitForElementToBeClickable(postChangePassBtn).click();

        try{
            waitForElementToAppear(samePassAsBeforeMsg);
            if(getDriver().findElement(samePassAsBeforeMsg).isDisplayed()){
                return true;
            }
            else{
                return false;
            }
        }

        catch(Exception e){
            return false;
        }

    }

    default void logoutFromPage(){
        waitForElement(logoutButton);
        WebElement staleElement = findElement(logoutButton);
        findElement(logoutButton).click();
        getWait().until(ExpectedConditions.stalenessOf(staleElement));
    }

    default By getSearchResultsBetween(String first, String last) {
        String path;
        if (last != null) {
            path = "//div[preceding-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]//div[text()='"
                    + first + "'] and following-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]//div[@class='type'][text()='"
                    + last + "']]";
        } else {
            path = "//div[preceding-sibling::div[contains(@class, 'x-list-item')][contains(@class, 'x-size-monitored')][not(contains(@class, 'x-hidden-display'))]//div[text()='"
                    + first + "']]";
        }
        return By.xpath(path);
    }
}
