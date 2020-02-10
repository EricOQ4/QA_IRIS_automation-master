package pageobjects;

import org.apache.commons.collections4.Predicate;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.admin.companyPage.CompanyList;
import pageobjects.admin.implementationPage.ImplementationPage;
import pageobjects.admin.intelligencePage.IntelligencePage;
import pageobjects.admin.morningCoffeePage.MorningCoffeePage;
import pageobjects.admin.profilesPage.ProfilesList;
import pageobjects.admin.releaseNotesPage.ReleaseNotesPage;
import pageobjects.admin.usersPage.UsersPage;
import pageobjects.user.headerPage.HeaderPage;
import pageobjects.user.logActivityModal.LogActivityModal;
import pageobjects.user.loginPage.LoginPage;
import pageobjects.user.sideNavBar.SideNavBar;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Updated by abbyl on 2019-04-17.

 * Updated by Shardul B. on 19-06-2019
 */

public class AbstractPageObject implements HeaderPage{

    public final WebDriver driver;

    public final WebDriverWait wait;

    private final Predicate<WebElement> displayedElementPredicate = new Predicate<WebElement>() {
        @Override
        public boolean evaluate(WebElement t) {
            return t.isDisplayed();
        }
    };

    private final By notSubscribed = By.xpath("//h1[contains(text(),'Interested?')]");

    // Dashboard
    private final By dashboardLoaded = By.xpath("//div[@class='dashboard_item q4-fade-in']");

    // Side hamburger menu icon
    private final By sideNavIcon = By.cssSelector(".page-home .menu-btn"); //use for dashboard
    //private final By hamburgerIcon = By.cssSelector(".navigation-toggler .x-button-icon"); //use for other pages
    //private final By hamburgerIcon = By.cssSelector(".q4i-hamburger-desktop-2pt"); //use for other pages
    private final By hamburgerIcon = By.xpath("//i[@class='app-nav_menu-header_toggle q4i-hamburger-desktop-2pt']"); //use for other pages

    private final By pageTitle = By.cssSelector(".q4-hero-banner .page-title");
    private final By modalPageTitle = By.xpath("//h1[@class='title']");
    private final By otherPageTitle = By.cssSelector(".q4-hero-banner .page-title h1");
    private final By watchListPageTitle = By.cssSelector(".watchlist-manager-page .page-title");

    // Admin page elements
    private final By adminPageTitle = By.cssSelector(".page-header .page-title .details h2");
    private final By companyPage = By.cssSelector("body > q4-app > div > q4-navbar > nav > div > ul > li:nth-child(2) > a > i");
    private final By profilesPage = By.cssSelector("body > q4-app > div > q4-navbar > nav > div > ul > li:nth-child(3) > a > i");
    private final By intelligencePage = By.cssSelector("body > q4-app > div > q4-navbar > nav > div > ul > li:nth-child(4) > a > i");
    private final By implementationPage = By.cssSelector("body > q4-app > div > q4-navbar > nav > div > ul > li:nth-child(3) > a > i");
    private final By morningCoffeePage = By.partialLinkText("Morning Coffee");
    private final By reportHeader = By.cssSelector(".page-header .page-title .details");
    private final By usersPage = By.cssSelector("body > q4-app > div > q4-navbar > nav > div > ul > li:nth-child(6) > a > i");
    private final By releaseNotesPage = By.xpath("//a[contains(@title, 'Release Notes')]");
    private final By profileIcon = By.xpath("//div[contains(@class,'profile x-dock-item')]");
    private final By feedback = By.xpath("//div[@class='profile-menu-item']/span[contains(text(),'Leave Feedback')]");
    private final By password = By.xpath("//div[@class='profile-menu-item']/span[contains(text(),'Change Password')]");
    private final By logout = By.xpath("//span[contains(text(),'Logout')]");
    private final By confirmLogout = By.xpath("//div[contains(@class,'x-button-action') and ./span[contains(text(),'Yes')]]");
    private final By productDropDown = By.xpath("//p-dropdown");
    private final By desktopSelect = By.xpath("//p-dropdown//span[contains(text(),'Desktop')]");
    private final By webSelect = By.xpath("//p-dropdown//span[contains(text(),'Web')]");
    private final By surveillanceSelect = By.xpath("//p-dropdown//span[contains(text(),'Surveillance')]");
    private final String DESKTOP = "Desktop";
    private final String WEB ="Web";
    private final String SURVEILLANCE = "Surveillance";

    //Check unsubscribers
    private final By unsubscribeMessage = By.xpath("//h1[text()=\"Looks like you haven't subscribed to this feature. Interested?\"]");


    public AbstractPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15L, 500L);
    }

    @Override
    public WebDriver getDriver() {
        return this.driver;
    }

    @Override
    public Predicate<WebElement> getDisplayedElementPredicate() {
        return this.displayedElementPredicate;
    }

    @Override
    public WebDriverWait getWait() {
        return this.wait;
    }

    // Check if subscription toggle is unchecked. If its unchecked, check it
    public void toggleOn(By selector) {
        String value = findElement(selector).getAttribute("class");
        if (value.equals("off ng-untouched ng-pristine ng-valid")) {
            findElement(selector).click();
        }
    }

    // Check if subscription toggle is check. If its checked, un-check it
    public void toggleOff(By selector) {
        String value = findElement(selector).getAttribute("class");
        System.out.println(value);
        if (value.equals("on ng-untouched ng-pristine ng-valid")) {
            findElement(selector).click();
        }
    }

    //use from dashboard
    public SideNavBar accessSideNav() {

        //waitForLoadingScreen();

        waitForElementToBeClickable(hamburgerIcon).click();
        return new SideNavBar(getDriver());
    }

    //for testing contact
    //no hamburger menu, click menu directly
    public SideNavBar accessFromSideNav(By navMenu) {
        waitForElementToBeClickable(navMenu).click();
        return new SideNavBar(getDriver());
    }

    //use from other pages
    public SideNavBar accessSideNavFromPage() {
        waitForElementToBeClickable(hamburgerIcon).click();
        return new SideNavBar(getDriver());
    }

    public String getPageTitle() {
        waitForLoadingScreen();
        return findVisibleElement(pageTitle).getText();
    }

    //For fullscreen modals (ex. Create New Activity modal)
    public String getModalPageTitle() {
        waitForLoadingScreen();
        return findVisibleElement(modalPageTitle).getText();
    }

    // Some pages have a different header
    public String getOtherPageTitle() {
        waitForLoadingScreen();
        return findVisibleElement(otherPageTitle).getText();
    }

    // Watchlist page header is also different :|
    public String getWatchListPageTitle() {
        waitForLoadingScreen();
        return findVisibleElement(otherPageTitle).getText();

    }

    public LogActivityModal pageRefresh() {
        pause(2000L);

        driver.navigate().refresh();
        waitForSiteToLoad();
        waitForLoadingScreen();

        return new LogActivityModal(getDriver());
    }

    public void goBackPages(int numPages) //Added this to allow functionality to go to a prev. page
    {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.history.go(-" + numPages + ")");
        pause(2000L);
    }

    //Allow the clicking of a specific coordinate. HEAVILY dependent on window size (the x & y values change due to it)
    //Any element on the page can be used, the method adjusts for each.
    public void clickCoordinate(By anyElement,int coordX,int coordY)
    {
        /*
        Moves the mouse to an offset from the top-left corner of the companyName element, then click it.
        Get chrome extension Mouse XY to find coordinates of a web-page
        The companyName element has coordinates of about (80, 100) when in 100% zoom
        The coordinates are heavily dependent on the size of the browser screen(window)/resolution.
        This function allows you to pick a coordinate, and it clicks on said chosen coordinate
        */
        Actions execute = new Actions(driver);
        WebElement offsetElement = findVisibleElement(anyElement);
        Point coordinate = offsetElement.getLocation();
        coordX -= coordinate.getX();
        coordY -= coordinate.getY();
        execute.moveToElement(offsetElement, coordX, coordY).click().perform();
    }

    public void clickElementLocation(By anyElement)
    {
        /*
        Moves the mouse to an offset from the top-left corner of the companyName element, then click it.
        Get chrome extension Mouse XY to find coordinates of a web-page
        The companyName element has coordinates of about (80, 100) when in 100% zoom
        The coordinates are heavily dependent on the size of the browser screen(window)/resolution.
        This function allows you to pick a coordinate, and it clicks on said chosen coordinate
        */

        Actions execute = new Actions(driver);
        execute.moveToElement(findElement(anyElement)).click().perform();
    }

    public void waitForDashboardToLoad() {
        waitForElementToAppear(dashboardLoaded);
    }

    //Can't leave this for JUST contacts
    public boolean elementsAreAlphaUpSorted(List<WebElement> elements){
        //adding so to ignore the Multiple
        //Must account for - contacts too
        By footer = By.xpath("//div[@id='app-footer']");
        By multipleFirstResult = By.xpath("//div[contains(@class, 'modal')]//div[@class='contact'][1]//*[text()]");

        waitForElement(footer);
        scrollToElement(footer);

        String previous = null;
        for (WebElement element : elements) {
            String current = element.getText();

            if (current.contains("Multiple")) {
                element.click();
                current = waitForElementToAppear(multipleFirstResult).getText();
                clickCoordinate(searchBar,10,10);
            }

            if (previous != null) {
                if (current.compareTo(previous) < 0) {
                    System.out.println("MIS-SORT: Ascending: '"+current+"' should not be after '"+previous+"'");
                    return false;
                }
            }

            previous = current;
        }

        return true;
    }

    public boolean elementsAreAlphaUpSortedMorningCoffee(List<WebElement> elements){
        //adding so to ignore the Multiple
        //Must account for - contacts too
        By multipleFirstResult = By.xpath("//div//h2//..//div[1]");
        By test = By.xpath("//div[contains(@class,'footer-content')]");


        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){

            String frontElement = elements.get(i+1).getText();
            String backElement = elements.get(i).getText();

            if(frontElement.contains("Multiple")){
                findElement(test);
                (elements.get(i+1)).click();
                waitForElementToAppear(multipleFirstResult);
                frontElement = findElement(multipleFirstResult).getText();

                // Sometimes multipleFirstResult returns nothing so we run a loop to try again 10 times until text is returned.
                for (int k = 0; k < 9; k++)
                {
                    if (frontElement.equalsIgnoreCase("")){
                        (elements.get(i+1)).click();
                        waitForElementToAppear(multipleFirstResult);
                        frontElement = findElement(multipleFirstResult).getText();
                    }
                    else
                    {
                        break;
                    }
                }
                clickCoordinate(searchBar,10,10);
                pause(500);
            }

            if(backElement.contains("Multiple")){
                findElement(test);
                (elements.get(i)).click();
                waitForElementToAppear(multipleFirstResult);
                backElement = findElement(multipleFirstResult).getText();

                // Sometimes multipleFirstResult returns nothing so we run a loop to try again 10 times until text is returned.
                for (int k = 0; k < 9; k++)
                {
                    if (backElement.equalsIgnoreCase("")){
                        (elements.get(i)).click();
                        waitForElementToAppear(multipleFirstResult);
                        backElement = findElement(multipleFirstResult).getText();
                    }
                    else
                    {
                        break;
                    }
                }

                clickCoordinate(searchBar,10,10);
                pause(500);
            }

            if (frontElement.compareTo(backElement) < 0){
                System.out.println("MIS-SORT: Ascending: '"+frontElement+"' should not be after '"+backElement+"'");
                sortedWell = false;
            }
        }
        return sortedWell;
    }

    public boolean elementsAreAlphaUpSortedIgnoreCase(List<WebElement> elements){
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            if (elements.get(i+1).getText().compareToIgnoreCase(elements.get(i).getText()) < 0){
                System.out.println("MIS-SORT: Ascending: '"+elements.get(i+1).getText()+"' should not be after '"+elements.get(i).getText()+"'");
                sortedWell = false;
            }
        }
        return sortedWell;
    }
    public boolean elementsAreAlphaDownSortedIgnoreCase(List<WebElement> elements){
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            if (elements.get(i+1).getText().compareToIgnoreCase(elements.get(i).getText()) > 0){
                System.out.println("MIS-SORT: Descending: '"+elements.get(i+1).getText()+"' should not be after '"+elements.get(i).getText()+"'");
                sortedWell = false;
            }
        }
        return sortedWell;
    }

    public boolean elementsAreAlphaDownSorted(List<WebElement> elements){
        By footer = By.xpath("//div[@id='app-footer']");
        By multipleFirstResult = By.xpath("//div[contains(@class, 'modal')]//div[@class='contact'][1]//*[text()]");

        waitForElement(footer);
        scrollToElement(footer);

        String previous = null;
        for (WebElement element : elements) {
            String current = element.getText();

            if (current.contains("Multiple")) {
                element.click();
                current = waitForElementToAppear(multipleFirstResult).getText();
                clickCoordinate(searchBar,10,10);
            }

            if (previous != null) {
                if (current.compareTo(previous) > 0) {
                    System.out.println("MIS-SORT: Descending: '"+current+"' should not be after '"+previous+"'");
                    return false;
                }
            }

            previous = current;
        }

        return true;
    }

    /** Used for numerical values displayed on page. Treats '-' as having value of zero. */
    public double getNumFromText(String text){
        if (text.equals("-")){
            return 0;
        }
        else if (text.contains(",")){
            return Double.parseDouble(text.replace(",",""));
        }
        else {
            return Double.parseDouble(text);
        }
    }

    public boolean elementsAreNumUpSorted(List<WebElement> elements){
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            if (getNumFromText(elements.get(i+1).getText()) < getNumFromText(elements.get(i).getText())){
                System.out.println("MIS-SORT: Ascending: '"+elements.get(i+1).getText()+"' should not be after '"+elements.get(i).getText()+"'");
                sortedWell = false;
            }
        }
        return sortedWell;
    }

    public boolean elementsAreNumDownSorted(List<WebElement> elements){
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            if (getNumFromText(elements.get(i+1).getText()) > getNumFromText(elements.get(i).getText())){
                System.out.println("MIS-SORT: Descending: '"+elements.get(i+1).getText()+"' should not be after '"+elements.get(i).getText()+"'");
                sortedWell = false;
            }
        }
        return sortedWell;
    }

    private enum Turnover {
        None, Very_Low, Low, Medium, High, Very_High
    }

    private int turnoverTextToValue(String text){
        if (text.equals("-")){
            return Turnover.None.ordinal();
        }
        else {
            return Turnover.valueOf(text.replace(' ','_')).ordinal();
        }
    }

    public boolean elementsAreTurnoverUpSorted(List<WebElement> elements){
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            if (turnoverTextToValue(elements.get(i+1).getText()) < turnoverTextToValue(elements.get(i).getText())){
                System.out.println("MIS-SORT: Ascending: '"+elements.get(i+1).getText()+"' should not be after '"+elements.get(i).getText()+"'");
                sortedWell = false;
            }
        }
        return sortedWell;
    }

    public boolean elementsAreTurnoverDownSorted(List<WebElement> elements){
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            if (turnoverTextToValue(elements.get(i+1).getText()) > turnoverTextToValue(elements.get(i).getText())){
                System.out.println("MIS-SORT: Descending: '"+elements.get(i+1).getText()+"' should not be after '"+elements.get(i).getText()+"'");
                sortedWell = false;
            }
        }
        return sortedWell;
    }

    public boolean elementsAreDateUpSorted(List<WebElement> elements){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            System.out.print(elements.get(i).getText());
            if(!(elements.get(i+1).getText().contains("-") || elements.get(i).getText().contains("START DATE"))) {
                try {
                    if (dateFormat.parse(elements.get(i + 1).getText()).before(dateFormat.parse(elements.get(i).getText()))) {
                        System.out.println("MIS-SORT: Ascending: Date " + elements.get(i + 1).getText() + " should not be after " + elements.get(i).getText());
                        sortedWell = false;
                    }
                } catch (ParseException e) {
                    System.out.println("Error parsing date: " + elements.get(i + 1).getText());
                    return false;
                }
            }
        }
        return sortedWell;
    }

    public boolean elementsAreDateDownSorted(List<WebElement> elements){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            if(!(elements.get(i+1).getText().contains("-") || elements.get(i).getText().contains("START DATE"))) {
                try {
                    if (dateFormat.parse(elements.get(i + 1).getText()).after(dateFormat.parse(elements.get(i).getText()))) {
                        System.out.println("MIS-SORT: Descending: Date " + elements.get(i + 1).getText() + " should not be after " + elements.get(i).getText());
                        sortedWell = false;
                    }
                } catch (ParseException e) {
                    System.out.println("Error parsing date: " + elements.get(i + 1).getText());
                    return false;
                }
            }
        }
        return sortedWell;
    }

    public boolean elementsAreDateUpSorted(List<WebElement> elements, SimpleDateFormat dateFormat){
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            try {
                if(dateFormat.parse(elements.get(i+1).getText()).before(dateFormat.parse(elements.get(i).getText()))){
                    System.out.println("MIS-SORT: Ascending: Date "+elements.get(i+1).getText()+" should not be after "+elements.get(i).getText());
                    sortedWell = false;
                }
            }catch (ParseException e){
                System.out.println("Error parsing date: "+elements.get(i+1).getText());
                return false;
            }
        }
        return sortedWell;
    }

    public boolean elementsAreDateDownSorted(List<WebElement> elements,SimpleDateFormat dateFormat){
        boolean sortedWell = true;
        for (int i=0; i<elements.size()-1; i++){
            try {
                if(dateFormat.parse(elements.get(i+1).getText()).after(dateFormat.parse(elements.get(i).getText()))){
                    System.out.println("MIS-SORT: Descending: Date "+elements.get(i+1).getText()+" should not be after "+elements.get(i).getText());
                    sortedWell = false;
                }
            }catch (ParseException e){
                System.out.println("Error parsing date: "+elements.get(i+1).getText());
                return false;
            }
        }
        return sortedWell;
    }

    public boolean elementsAreAllNegative(List<WebElement> elements){
        boolean allNegative = true;
        for (WebElement element : elements){
            try {
                if (NumberFormat.getNumberInstance(Locale.US).parse(element.getText()).intValue() >= 0){
                    System.out.println("Non-negative number: "+element.getText());
                    allNegative = false;
                }
            }catch (ParseException e){
                System.out.println("Error parsing number: "+element.getText());
                allNegative = false;
            }
        }
        return allNegative;
    }

    public boolean elementsAreAllPositive(List<WebElement> elements){
        boolean allPositive = true;
        for (WebElement element : elements){
            try {
                if (NumberFormat.getNumberInstance(Locale.US).parse(element.getText()).intValue() <= 0){
                    System.out.println("Non-positive number: "+element.getText());
                    allPositive = false;
                }
            }catch (ParseException e){
                System.out.println("Error parsing number: "+element.getText());
                allPositive = false;
            }
        }
        return allPositive;
    }

    // checks whether the elements are a number (which may include a % sign) between 0 and 100 (inclusive)
    public boolean elementsAreAllPercentages(List<WebElement> elements){
        boolean allPercent = true;
        for (WebElement element : elements){
            try {
                double value = Double.parseDouble(element.getText().replace("%",""));
                if (value < 0 || value > 100){
                    System.out.println("Number not between 0 and 100: "+element.getText());
                    allPercent = false;
                }
            }catch (NumberFormatException e){
                System.out.println("Error parsing number: "+element.getText());
                allPercent = false;
            }
        }
        return allPercent;
    }

    public boolean elementsDoNotContainDuplicates(List<WebElement> elements){
        List<String> names = new ArrayList<>();
        names.add(elements.get(0).getText());
        for (int i=1; i<elements.size(); i++){
            String newName = elements.get(i).getText();
            for (String name : names){
                if (name.equalsIgnoreCase(newName)){
                    System.out.println("DUPLICATE ELEMENT: "+name);
                    return false;
                }
            }
            names.add(newName);
        }
        return true;
    }

    // ADMIN METHODS \\

    public void switchToNewTab() {
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
    }

    public CompanyList navigateToCompanyPage() {
        waitForLoadingScreen();
        selectProduct(DESKTOP);
        findElement(companyPage).click();
        return new CompanyList(getDriver());
    }

    public String getAdminPageTitle() {
        return findElement(adminPageTitle).getText();
    }

    public ProfilesList navigateToProfilesPage() {
        waitForLoadingScreen();
        selectProduct(DESKTOP);
        findElement(profilesPage).click();
        waitForLoadingScreen();

        return new ProfilesList(driver);
    }

    public ProfilesList navigateToProfilesPageFlash() {
        selectProduct(DESKTOP);
        findElement(profilesPage).click();
        return new ProfilesList(driver);
    }

    public ImplementationPage navigateToImplementationPage() {
        waitForLoadingScreen();
        selectProduct(WEB);
        findElement(implementationPage).click();

        return new ImplementationPage(getDriver());
    }

    public MorningCoffeePage navigateToMorningCoffeePage(){
        waitForLoadingScreen();
        selectProduct(SURVEILLANCE);
        findElement(morningCoffeePage).click();
        waitForLoadingScreen();
        return new MorningCoffeePage(getDriver());
    }

    public IntelligencePage navigateToIntelligencePage() {
        waitForLoadingScreen();
        selectProduct(SURVEILLANCE);
        findElement(intelligencePage).click();

        return new IntelligencePage(getDriver());
    }

    public UsersPage navigateToUsersPage(){
        waitForLoadingScreen();
        selectProduct(DESKTOP);
        findElement(usersPage).click();

        return new UsersPage(getDriver());
    }

    public ReleaseNotesPage navigateToReleaseNotesPage(){
        waitForLoadingScreen();
        selectProduct(DESKTOP);
        findElement(releaseNotesPage).click();

        return new ReleaseNotesPage(getDriver());
    }

    public String getReportHeader() {
        waitForLoadingScreen();
        return findElement(reportHeader).getText();
    }

    public LoginPage logout (){
        waitForElement(profileIcon);
        findVisibleElement(profileIcon).click();
        waitForElement(logout);
        findElement(logout).click();
        waitForLoadingScreen();
        return new LoginPage(getDriver());
    }

    public boolean windowDidLoad (String title) {
        for (int i = 0; i < 10; i++) {
            for (String winHandle : driver.getWindowHandles()) {

                if (driver.switchTo().window(winHandle).getTitle().equals(title)) {
                    return true;
                }
            }
            pause(1000L);
        }
        return false;
    }


    public void selectProduct(String product){
        waitForElementToAppear(productDropDown);
        findElement(productDropDown).click();
        if(product.equals(DESKTOP)){
            waitForElementToAppear(desktopSelect);
            findElement(desktopSelect).click();
        }
        else if (product.equals(WEB)){
            waitForElement(webSelect);
            findElement(webSelect).click();
        }
        else {
            waitForElement(surveillanceSelect);
            findElement(surveillanceSelect).click();
        }
    }

    public boolean userIsNotSubscribed(){

        try{
            if(getDriver().findElement(notSubscribed).isDisplayed()){
                return true;
            }
        }

        catch(Exception e){
            return false;
        }


        return false;
    }

    public void scrollToElement(By locator){
        WebElement element = driver.findElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    public void scrollToTopOfPage(){
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollTo(0,0)");
    }

    public void getTokenId(){
        try{
            JavascriptExecutor js  = (JavascriptExecutor) driver;
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("session_file.txt"),"utf-8"));
            String test = ((String)js.executeScript(String.format("return window.localStorage.getItem('%s');", "id_token")));
            writer.write(test);
            writer.close();
        }catch(Exception e){

        }
    }

    public void getProfile(){
        try{
            JavascriptExecutor js  = (JavascriptExecutor) driver;
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("session_profile.txt"),"utf-8"));
            String test = ((String)js.executeScript(String.format("return window.localStorage.getItem('%s');", "profile")));
            writer.write(test);
            writer.close();
        }catch(Exception e){

        }
    }


    public Boolean isUnsubscribed(){
        waitForLoadingScreen();
        return doesElementExist(unsubscribeMessage);
    }

}