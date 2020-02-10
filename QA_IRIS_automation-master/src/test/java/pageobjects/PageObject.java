package pageobjects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface PageObject {

    final class ElementNotFoundException extends RuntimeException {
        private ElementNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    WebDriver getDriver();

    Predicate<WebElement> getDisplayedElementPredicate();

    WebDriverWait getWait();

    default void typeInSelect2Input(String keys) {
        findElement(By.cssSelector("input.select2-input.select2-focused")).sendKeys(keys);
    }

    default boolean retryTypeInSelect2Input(String keys) {
        boolean result = false;

        for (int i = 0; i < 3; i++) {
            try {
                findElement(By.cssSelector("input.select2-input.select2-focused")).clear();
                findElement(By.cssSelector("input.select2-input.select2-focused")).sendKeys(keys);
                result = true;
                break;
            } catch (StaleElementReferenceException e) {
                // Retry if Stale Element Reference Exception occurs
            }
        }
        return result;
    }

    default void chooseSelect2Match() {
        findElement(By.className("select2-match")).click();
    }

    default void chooseFirstSelect2Result() {
        retryClick(By.className("select2-result"));
    }

    default WebElement findElement(By selector) {
        try {
            return getDriver().findElement(selector);
        } catch (NoSuchElementException e) {
            throw new ElementNotFoundException("Could not find element " + selector, e);
        }
    }

    default boolean doesElementExist(By selector) {
        try {
            getDriver().findElement(selector);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    default List<WebElement> findElements(By selector) {
        return getDriver().findElements(selector);
    }

    default WebElement findChildOfVisibleParent(By parentSelector, By childSelector) {
        return findVisibleElement(parentSelector).findElement(childSelector);
    }

    default WebElement findVisibleElement(By selector) {
        List<WebElement> elements = getDriver().findElements(selector);

        return CollectionUtils.find(elements, getDisplayedElementPredicate());
    }

    default List<WebElement> findVisibleElements(By selector) {
        List<WebElement> elements = getDriver().findElements(selector);
        List<WebElement> visible = new ArrayList<WebElement>();
        for (WebElement element : elements){
            if (element.isDisplayed()){
                visible.add(element);
            }
        }
        return visible;
    }

    default WebElement findParentOf(By selector) {
        return findElement(selector).findElement(By.xpath("parent::*"));
    }

    default void waitForLoadingScreen() {

        // We decided to comment this out and replace it with a manual pause. We know this is not a good solution to this problem, but as spinner id's and
        //      seemingly the funcionality for what was here before has changed, and we don't know how to fix it, this is a bandaid solution to allow the program to run.
        //      Before this change, this method stalled the automation for around 5 minutes per call, making programs take impossible amounts of time to run. If you know
        //      how to fix this then please do.
        pause(3000L);

        //Waits 2 sec for spinners to appear, then 8 sec for spinners to disappear
        /*
        WebDriverWait spinnerWait = new WebDriverWait(getDriver(), 8);

        try {

            spinnerWait.until(ExpectedConditions.presenceOfElementLocated(By.className("x-loading-spinner")));

            getWait().until(ExpectedConditions.invisibilityOfAllElements(findElements(By.className("x-loading-spinner"))));

            } catch (Exception e) {
                // No loading spinners; do nothing
         }*/

    }

    default WebElement waitForElement(By selector) {
        return getWait().until(ExpectedConditions.presenceOfElementLocated(selector));
    }

    default void waitForElementToDissapear(By selector) {
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(selector));
    }

    default WebElement waitForElementToAppear(By selector) {
         return getWait().until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    default WebElement waitForAnyElementToAppear(By selector) {

        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        long loopStartTime = startTime;
        long loopTime = 0;

        while (elapsedTime < 10000) {
            if (loopTime > 500) {
                List<WebElement> elements = findElements(selector);
                for (WebElement element : elements) {
                    if (element.isDisplayed()) return element;
                }
                loopStartTime = System.currentTimeMillis();
                loopTime = 0;
            } else {
                loopTime = System.currentTimeMillis()-loopStartTime;
            }
            elapsedTime = System.currentTimeMillis()-startTime;
        }
        throw new TimeoutException("Timed out after 10 seconds waiting for element to appear: " + selector);
    }

    default WebElement waitForElementToBeClickable(By selector) {
        return getWait().until(ExpectedConditions.elementToBeClickable(selector));
    }//div[@row-index='0']//div[@col-id='entity_name']//i

    default void waitForText(String text) {
        waitForElement(By.xpath("//*[contains(text(), '"+ text +"')]"));
    }

    default void waitForTextToChange(By selector) {
        String currentText = findElement(selector).getText();
        getWait().until(ExpectedConditions.not(ExpectedConditions.textToBe(selector, currentText)));
    }

    default void waitForTextToChange(By selector, String from) {
        getWait().until(ExpectedConditions.not(ExpectedConditions.textToBe(selector, from)));
    }

    default WebElement waitForElementToRest(By selector, long restTime) {
        // Waits for element to remain unchanged for the given time (in millis), or for 10s

        WebElement element = findElement(selector);
        long startTime = System.currentTimeMillis();
        long sinceChanged = startTime;
        long elapsedTime = 0;
        while (elapsedTime < restTime) {
            if (!element.equals(findElement(selector))) {
                sinceChanged = System.currentTimeMillis();
            }
            elapsedTime = System.currentTimeMillis()-sinceChanged;

            if (System.currentTimeMillis()-startTime > 10000) {
                throw new TimeoutException("Timed out after 10 seconds waiting for element to rest: " + selector);
            }
        }
        return element;
    }

    default void waitForSiteToLoad() {
        // Waits for blue site loading screen to disappear (use after refresh)
        try {
            new WebDriverWait(getDriver(), 20).until(ExpectedConditions.presenceOfElementLocated(By.className("loading")));
            new WebDriverWait(getDriver(), 20).until(ExpectedConditions.invisibilityOfElementLocated(By.className("loading")));
        }
        catch(Exception e) {
        }

    }

    default void disableAnimations() {

        // See https://stackoverflow.com/questions/14791094/how-to-set-the-universal-css-selector-with-javascript

        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript(";(function(exports) {var style = document.querySelector(\"head\").appendChild(document.createElement(\"style\"));\n" +
                "var styleSheet = document.styleSheets[document.styleSheets.length - 1];\n" +
                "styleSheet.insertRule(\"* {}\", 0);\n" +
                "exports.universal = styleSheet.cssRules[0];}(window));");
        executor.executeScript("window.universal.style.cssText += (\"; animation-duration: 0s !important; transition-duration: 0s !important\")");
    }

    default <T extends PageObject> T pause(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return (T) this;
    }

    default void searchSelect2For(By selector, String thingToSelect) {
        findParentOf(selector).findElement(By.cssSelector(".select2-container > a")).click();
        typeInSelect2Input(thingToSelect);
        chooseSelect2Match();
    }

    // Attempts to click an element 3 times to prevent stale element reference.
    //   This is caused when the driver tries to click an element but the element
    //   reloads for some reason
    default boolean retryClick(By by) {
        boolean result = false;

        for (int i = 0; i < 3; i++) {
            try {
                findElement(by).click();
                result = true;
                break;
            } catch (Exception e) {
                // Retry if exception occurs
            }
        }
        return result;
    }

    default boolean retryClick(WebElement element) {
        boolean result = false;

        for (int i = 0; i < 3; i++) {
            try {
                element.click();
                result = true;
                break;
            } catch (Exception e) {
                // Retry if exception occurs
            }
        }
        return result;
    }

    default String retryGetText(By by) {
        String result = null;
        for (int i = 0; i < 3; i++) {
            try {
                result = findElement(by).getText();
                break;
            } catch (StaleElementReferenceException e) {
                // Retry if Stale Element Reference Exception occurs
            }
        }
        return result;
    }
}
