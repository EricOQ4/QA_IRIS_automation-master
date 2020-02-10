package pageobjects.admin.intelligencePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;


public class EODReportDetailsPage extends AbstractPageObject {

    public static By productDropdown = By.cssSelector("body > q4-app > div > q4-header > header > div.product-select > nav > p-dropdown > div > div.ui-dropdown-trigger.ui-state-default.ui-corner-right > span");
    public static By productDropdownSurveillance = By.xpath("/html/body/q4-app/div/q4-header/header/div[1]/nav/p-dropdown/div/div[4]/div/ul/li[3]/span");
    public static By endOfDayButton = By.cssSelector("body > q4-app > div > q4-navbar > nav > div > ul > li:nth-child(6) > a > i");
    public static By addEODReportButton = By.cssSelector("body > q4-app > div > div > q4-end-of-day > div.sub-toolbar > div.components.right > div.action-buttons > button");
    public static By tickerField = By.xpath("/html/body/q4-app/div/div/q4-end-of-day/p-dialog/div/div[2]/q4-end-of-day-create/p-autocomplete/span/input");
    public static By tickerListDropDown = By.xpath("/html/body/q4-app/div/div/q4-end-of-day/p-dialog/div/div[2]/q4-end-of-day-create/p-autocomplete/span/div/ul/li[1]");
    public static By createEODReportButton = By.xpath("/html/body/q4-app/div/div/q4-end-of-day/p-dialog/div/div[2]/q4-end-of-day-create/div/button[2]");
    public static By viewEODReport = By.cssSelector("q4-end-of-day > p-datatable tr:nth-child(1) > td:nth-child(1)");
    public static By deleteButton = By.cssSelector("body > q4-app > div > div > q4-end-of-day-report-details > header > div > div.action-buttons > button.square-button.remove");
    public static By confirmDeleteButton = By.cssSelector("body > q4-app > div > div > q4-end-of-day-report-details > header > div > div.action-buttons > ng-component > p-dialog > div > div.ui-dialog-content.ui-widget-content > div > button.button.button-red");

    public EODReportDetailsPage(WebDriver driver) {
        super(driver);
    }
    public boolean openEODPage() {
        timeDelay(1000);
        driver.findElement(productDropdown).click();
        timeDelay(1000);
        driver.findElement(productDropdownSurveillance).click();
        timeDelay(1000);
        driver.findElement(endOfDayButton).click();
        return true;
    }
    public boolean addEODReport(String ticker) {
        timeDelay(1000);
        driver.findElement(addEODReportButton).click();
        timeDelay(1000);
        driver.findElement(tickerField).click();
        timeDelay(1000);
        driver.findElement(tickerField).sendKeys(ticker);
        timeDelay(1000);
        driver.findElement(tickerListDropDown).click();
        timeDelay(1000);
        driver.findElement(createEODReportButton).click();
        timeDelay(10000);
        driver.navigate().refresh();
        return true;
    }
    public boolean viewEODReport() {
        timeDelay(1000);
        driver.findElement(viewEODReport).click();
        return true;
    }
    public int countCompanies() {
        int length = 0;
        do {
            length++;
        }
        while (!driver.findElement(By.cssSelector("q4-eod-stock-summary-table > p-datatable > div > div > table > tbody > tr:nth-child(" + (length+1) + ") > td:nth-child(1)")).getText().equals("Peer Average"));
        return length;
    }
    public String[][] tableArray(int length) {
        String table[][] = new String[length][7];
        for(int ii=0; ii < length; ii++){
            for(int jj=0; jj < 7; jj++) {
                table[ii][jj] = driver.findElement(By.cssSelector("q4-eod-stock-summary-table > p-datatable > div > div > table > tbody > tr:nth-child(" + (ii+1) + ") > td:nth-child(" + (jj+1) + ")")).getText();
                table[ii][jj]=table[ii][jj].replace(",","");
                if (table[ii][jj].equals("-")) {
                    table[ii][jj] = "0";
                }
            }
        }
        return table;
    }
    public boolean CHGCheck(String type, double delta) {
        //System.out.println(countCompanies());
        if(countCompanies() < 2)
            return false;
        int xx = countCompanies()+1,
            colIndex = 0;
        float CHG = 0,
              CHGAVG = 0;
        String table[][] = new String[xx][7];
        table=tableArray(xx);
        switch (type) {
            case "priceCHG":
                colIndex = 2;
                break;
            case "percentCHG":
                colIndex = 3;
                break;
            case "percentQTDCHG":
                colIndex = 5;
                break;
            case "percentYTDCHG":
                colIndex = 6;
                break;
            default:
                System.out.println("ERROR! Unrecognised Type");
                return false;
        }
        for(int ii = 0; ii < xx; ii++) {
            for(int jj=0; jj < 7; jj++) {
                /*System.out.print(table[ii][jj]);
                if(jj==0)
                    System.out.print("\n");
                else
                    System.out.print("\t");*/
                if(ii != 0 && ii != xx-1 && jj == colIndex) {
                    CHG += (Float.valueOf(table[ii][jj]));
                }

            }
            //System.out.print("CHG: " + CHG + "\t");
            //System.out.print("\n");
        }
        CHGAVG = CHG/(xx-2);
        double lowerBound = Float.valueOf(table[xx-1][colIndex])*(1-delta);
        double upperBound = Float.valueOf(table[xx-1][colIndex])*(1+delta);
        System.out.println("Checking " + type);
        System.out.println("Lower Bound: " + lowerBound);
        System.out.println("Upper Bound: " + upperBound);
        if(CHGAVG == 0)
        {
            System.out.println("Calculated average: " + CHGAVG + "\tTable average: " + table[xx-1][colIndex] + "\tWithin Delta -> PASS\n");
            return true;
        }
        else if(CHGAVG >= 0 && CHGAVG > lowerBound && CHGAVG < upperBound) {
            System.out.println("Calculated average: " + CHGAVG + "\tTable average: " + table[xx-1][colIndex] + "\tWithin Delta -> PASS\n");
            return true;
        }
        else if(CHGAVG < 0 && CHGAVG < lowerBound && CHGAVG > upperBound) {
            System.out.println("Calculated average: " + CHGAVG + "\tTable average: " + table[xx-1][colIndex] + "\tWithin Delta -> PASS\n");
            return true;
        }
        else {
            System.out.println("Calculated average: " + CHGAVG + "\tTable average: " + table[xx-1][colIndex] + "\tOutside Delta -> FAIL\n");
            return false;
        }
    }
    public boolean teardown() {
        timeDelay(1000);
        driver.findElement(deleteButton).click();
        timeDelay(1000);
        driver.findElement(confirmDeleteButton).click();
        return true;
    }
    public void timeDelay(int time){
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}
