package pageobjects.user.ownershipPage;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.institutionPage.InstitutionPage;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SecurityOwnershipPage extends AbstractPageObject implements DateDropDownConstants {
    private final By tabTitle = By.cssSelector(".company-header .menu-button .x-button-label");
    private final By dateDropDown = By.xpath("//div[contains(@class,'buys-sells-header')]//div[contains(@class,'dropdown')]");
    private final By dateDropDownItems = By.xpath("//div[contains(@class,'dropdown-list')]//div[contains(@class,'x-dataview-item')]");
    private final By asOfDate = By.cssSelector(".company-ownership-inner .disclaimer span");
    private final By topBuyersNumbers = By.xpath("//div[contains(@class,'buyers-sellers-list')]//div[contains(@class,'value up')]");
    private final By topSellersNumbers = By.xpath("//div[contains(@class,'buyers-sellers-list')]//div[contains(@class,'value down')]");
    private final By topBuyersAndSellers = By.xpath("//div[contains(@class,'buyers-sellers-list')]//div[contains(@class,'name')]");
    private final By lastTopSeller = By.cssSelector(".ownership-buyers-sellers .buyers-sellers-list .header .name, .ownership-buyers-sellers .buyers-sellers-list .row .name:last-child");

    // holders table
    //private final By activistFilter = By.cssSelector(".ownership-report-holders .toggle-button");
    private final By activistFilter = By.xpath("(//div[contains(@class, 'x-inner x-toggle-inner x-size-monitored x-paint-monitored')])[10]");
    //private final By activistFilterOn = By.cssSelector(".ownership-report-holders .toggle-button .x-toggle-on");
    private final By activistFilterOn = By.xpath("//div[contains(@id, 'ext-surveillance-holders-toolbar-1')]//div[contains(@class, 'x-toggle-on')]//div[contains(@class, 'x-inner x-toggle-inner x-size-monitored x-paint-monitored')]");
    //private final By activistFilterOff = By.cssSelector(".ownership-report-holders .toggle-button .x-toggle-off");
    private final By activistFilterOff = By.xpath("//div[contains(@id, 'ext-surveillance-holders-toolbar-1')]//div[contains(@class, 'x-toggle-off')]//div[contains(@class, 'x-inner x-toggle-inner x-size-monitored x-paint-monitored')]");
    private final By allTypesFilter = By.xpath("//span[contains(text(),'All')]");
    private final By institutionsFilter = By.cssSelector("span.q4i-institution-2pt");
    private final By insidersFilter = By.cssSelector("span.q4i-insider-2pt");
    private final By fundsFilter = By.cssSelector("span.q4i-fund-2pt");
    private final By allBuyersSellersFilter = By.cssSelector(".ownership-report-main-content .range-tabs .x-button:first-child");
    private final By buyersFilter = By.cssSelector(".ownership-report-main-content .range-tabs .x-button:nth-child(2)");
    private final By sellersFilter = By.cssSelector(".ownership-report-main-content .range-tabs .x-button:nth-child(3)");
    //private final By holderTableHeaderName = By.cssSelector(".x-grid-column:first-child");
    private final By holderTableHeaderName = By.xpath("(//div[contains(@class, 'x-inner x-grid-header-container-inner x-align-stretch x-horizontal x-pack-start x-layout-box')]//div[contains(text(), 'Name')])[3]");
    //private final By holderTableHeaderPOS = By.cssSelector(".x-grid-column:nth-child(2)");
    private final By holderTableHeaderPOS = By.xpath("(//div[contains(@class, 'x-inner x-grid-header-container-inner x-align-stretch x-horizontal x-pack-start x-layout-box')]//div[contains(text(), 'POS')])[2]");
    //private final By holderTableHeader1QChg = By.xpath("//*[@id=\"ext-column-87\"]");
    private final By holderTableHeader1QChg = By.xpath("(//div[contains(@class, 'x-inner x-grid-header-container-inner x-align-stretch x-horizontal x-pack-start x-layout-box')]//div[contains(text(), 'POS')])[2]");
    //private final By holderTableHeaderMktVal = By.cssSelector(".x-grid-column:nth-child(4)");
    private final By holderTableHeaderMktVal = By.xpath("(//div[contains(@class, 'x-inner x-grid-header-container-inner x-align-stretch x-horizontal x-pack-start x-layout-box')]//div[contains(text(), 'Mkt Val*')])[2]");;
    //private final By holderTableHeaderMktValChg = By.cssSelector(".x-grid-column:nth-child(5)");
    private final By holderTableHeaderMktValChg = By.xpath("(//div[contains(@class, 'x-inner x-grid-header-container-inner x-align-stretch x-horizontal x-pack-start x-layout-box')]//div[contains(text(), 'CHG*')])[2]");
    //private final By holderTableHeaderPercOS = By.cssSelector(".x-grid-column:nth-child(6)");
    private final By holderTableHeaderPercOS = By.xpath("(//div[contains(@class, 'x-inner x-grid-header-container-inner x-align-stretch x-horizontal x-pack-start x-layout-box')]//div[contains(text(), '%OS')])[2]");
    //private final By holderTableHeaderPercPort = By.cssSelector(".x-grid-column:nth-child(7)");
    private final By holderTableHeaderPercPort = By.xpath("(//div[contains(@class, 'x-inner x-grid-header-container-inner x-align-stretch x-horizontal x-pack-start x-layout-box')]//div[contains(text(), '%Port')])[2]");
    //private final By holderTableHeaderStyle = By.cssSelector(".x-grid-column:nth-child(8)");
    private final By holderTableHeaderStyle = By.xpath("(//div[contains(@class, 'x-inner x-grid-header-container-inner x-align-stretch x-horizontal x-pack-start x-layout-box')]//div[contains(text(), 'Style')])[2]");
    private final By holderTableHeaderTurnover = By.cssSelector(".x-grid-column:nth-child(9)");
    private final By holderTableHeaderAUM = By.cssSelector(".x-grid-column:nth-child(10)");
    private final By holderTableHeaderAsOf = By.cssSelector(".x-grid-column:nth-child(11)");
    //private final By holderTableHeaderQR = By.cssSelector(".x-grid-column:nth-child(12)");
    private final By holderTableHeaderQR = By.xpath("(//div[contains(@class, 'x-inner x-grid-header-container-inner x-align-stretch x-horizontal x-pack-start x-layout-box')]//div[contains(text(), 'QR')])[2]");
    /*
    private final By holderTableHeaderName = By.cssSelector(".x-grid-column:nth-child(2)");
    private final By holderTableHeaderPOS = By.cssSelector(".x-grid-column:nth-child(3)");
    private final By holderTableHeader1QChg = By.cssSelector(".x-grid-column:nth-child(4)");
    private final By holderTableHeaderMktVal = By.cssSelector(".x-grid-column:nth-child(5)");
    private final By holderTableHeaderMktValChg = By.cssSelector(".x-grid-column:nth-child(6)");
    private final By holderTableHeaderPercOS = By.cssSelector(".x-grid-column:nth-child(7)");
    private final By holderTableHeaderPercPort = By.cssSelector(".x-grid-column:nth-child(8)");
    private final By holderTableHeaderStyle = By.cssSelector(".x-grid-column:nth-child(9)");
    private final By holderTableHeaderTurnover = By.cssSelector(".x-grid-column:nth-child(10)");
    private final By holderTableHeaderAUM = By.cssSelector(".x-grid-column:nth-child(11)");
    private final By holderTableHeaderAsOf = By.cssSelector(".x-grid-column:nth-child(12)");
    private final By holderTableHeaderQR = By.cssSelector(".x-grid-column:nth-child(13)");
    */
  
    private final By holderTableRow = By.cssSelector(".x-grid-row:not([style*='-10000px'])");
    private final By insiderTableRow = By.xpath("//div[div[contains(@class,'holder-info')]]/i[@class='icon-type q4i-insider-2pt']");
    private final By alternateHolderTableRow = By.cssSelector(".top-holders-list-institutions .x-dataview-item"); // exists instead of above when using Buyers or Sellers filter
    //private final By holderTableName = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:first-child");
    private final By holderTableName = By.xpath("//div[contains(@class,'grid-cell-align-left')]");
    //private final By holderTablePOS = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(2)");
    private final By holderTablePOS = By.xpath("//div[contains(@class,'grid-cell-align-right')][1]");
    private final By holderTable1QChg = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(3) span");
    private final By holderTableMktVal = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(3)");
    private final By holderTableMktValChg = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(4) span");
    private final By holderTablePercOS = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(5)");
    private final By holderTablePercPort = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(6)");
    private final By holderTableStyle = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(7)");
    private final By holderTableTurnover = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(8)");
    private final By holderTableAUM = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(9)");
    private final By holderTableAsOf = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(10)");
    private final By holderTableQR = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(11)");
  
    /*
    private final By holderTableName = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(2)");
    private final By holderTablePOS = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(3)");
    private final By holderTable1QChg = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(4) span");
    private final By holderTableMktVal = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(5)");
    private final By holderTableMktValChg = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(6) span");
    private final By holderTablePercOS = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(7)");
    private final By holderTablePercPort = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(8)");
    private final By holderTableStyle = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(9)");
    private final By holderTableTurnover = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(10)");
    private final By holderTableAUM = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(11)");
    private final By holderTableAsOf = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(12)");
    private final By holderTableQR = By.cssSelector(".x-grid-row:not([style*='-10000px']) .x-grid-cell:nth-child(13)");
    */
  
    private final By holderTable1W = By.cssSelector(".x-dataview-item .view-list-item:nth-child(4)"); // only exists when using Buyers or Sellers filter
    private final By showMoreButton = By.className("q4i-arrow-down-2pt");
    private final By activistIcon = By.cssSelector(".icon.activists");
    private final By institutionIcon = By.cssSelector("i.q4i-institution-2pt");

    // institutional ownership section
    private final By styleBarsBlue = By.cssSelector(".style + .ownership-report-institutional-charts rect[fill='#297ac5']");
    private final By styleBarsRed = By.cssSelector(".style + .ownership-report-institutional-charts rect[fill='#ec6a4c']");
    private final By styleBarsOrange = By.cssSelector(".style + .ownership-report-institutional-charts rect[fill='#fc7e26']");
    private final By styleBarsYellow = By.cssSelector(".style + .ownership-report-institutional-charts rect[fill='#f1af0f']");
    private final By turnoverBarsBlue = By.cssSelector(".turnover + .ownership-report-institutional-charts rect[fill='#297ac5']");
    private final By turnoverBarsRed = By.cssSelector(".turnover + .ownership-report-institutional-charts rect[fill='#ec6a4c']");
    private final By turnoverBarsOrange = By.cssSelector(".turnover + .ownership-report-institutional-charts rect[fill='#fc7e26']");
    private final By turnoverBarsYellow = By.cssSelector(".turnover + .ownership-report-institutional-charts rect[fill='#f1af0f']");
    private final By institutionalPercentages = By.cssSelector(".highcharts-data-labels g");

    // trend analysis section
    private final By trendAnalysisPage = By.xpath("//div[contains(@class,'company-ownership-content')]//div[contains(@class,'anchor-toolbar')]//div[contains(@class,'x-button') and .//span [contains(text(),'Trend Analysis')]]");

    private final By trendAnalysisChartBody = By.cssSelector(".area-chart .highcharts-series path:first-child");
    private final By trendAnalysisHoverText = By.cssSelector(".ownership-report-trend-analysis-content .highcharts-tooltip");

    // institutional holder analysis section
    private final By holderBreakdownValues = By.cssSelector(".analysis-breakdown-list .value");
    private final By holderTypeValues = By.cssSelector(".analysis-investor-type-list .value");
    private final By holderTypeOther = By.cssSelector(".analysis-investor-type-list .other");
    private final By holderStyleValues = By.cssSelector(".analysis-pie-bundles .q4-pie-bundle:nth-child(1) .q4-pie-bundle-item .chart text");
    private final By holderStyleOther = By.cssSelector(".analysis-pie-bundles .q4-pie-bundle:nth-child(1) .other");
    private final By holderTurnoverValues = By.cssSelector(".analysis-pie-bundles .q4-pie-bundle:nth-child(2) .q4-pie-bundle-item .chart text");
    private final By holderTurnoverOther = By.cssSelector(".analysis-pie-bundles .q4-pie-bundle:nth-child(2) .other");
    private final By holderOtherDropdown = By.className("q4-list-modal-inner");
    private final By holderOtherValues = By.cssSelector(".q4-list-modal-inner .value"); //values contained within whatever other dropdown is open (returns no elements when closed)

    Actions actions = new Actions(driver);
    private final DateTimeFormatter shortDate = DateTimeFormatter.ofPattern("MM/dd/yy");
    private final DateTimeFormatter longDate = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
    private final LocalDate today = LocalDate.now();
    private final By historicalTab = By.cssSelector("#ext-tab-2");
    private final By currentTab = By.cssSelector("#ext-tab-1");
    //private final By holdingsSearchField = By.cssSelector(".ownership-top-holders.q4-tab .search-field .x-field-input .x-input-el");
    private final By holdingsSearchField = By.xpath("//div[contains(@class,'q4-tab x-tabpanel')]//input[contains(@class,'x-input-search')]");

    //different types of Holder Searches
    private final By historicalInstitutionsHolderSearchResult = By.cssSelector("#ext-top-holders-historical-institutions-1");
    //private final By currentInsidersHolderSearchResult = By.cssSelector("#ext-ownership-top-holders-current-1");
    private final By currentInsidersHolderSearchResult = By.xpath("(//div[contains(@class,'q4-grid locked')]//div[contains(@class,'locked-item-first')])[4]//div[contains (@class, 'name')]");
    //private final By historicalFundsHolderSearchResults = By.cssSelector("#ext-top-holders-historical-fund-1");
    private final By historicalFundsHolderSearchResults = By.xpath("(//div[contains(@id,'ownership-historical-fund')]//div[contains(@class,'q4-grid locked')]//div[contains(@class,'locked-item-first')])[2]//div[contains (@class, 'name')]");
    private final By holderSearchResult = By.cssSelector("#ext-top-holders-historical-institutions-1");
    private final By holderSearchResulttwo = By.cssSelector(".top-holders-list.fund .details .holder-info");
    private final By FundsETFsTab = By.cssSelector("#ext-tab-8");
    //private final By InstitutionTab = By.cssSelector("#ext-tab-2");
    private final By InstitutionTab = By.xpath("//div[contains(@class, 'x-container x-unsized ownership-holders current x-layout-fit-item')]//div[contains(@class, 'x-container x-unsized toolbar-panel q4-blue')]//span[contains(@class, 'q4i-institution-2pt')]");

    /*
    private final By historicalTab = By.xpath("//div[contains(@class,'ownership-top-holders')]//div[span[contains(text(),'Historical')]] ");
    private final By currentTab = By.xpath("//div[contains(@class,'ownership-top-holders')]//div[span[contains(text(),'Current')]] ");
    private final By holdingsSearchField = By.cssSelector(".ownership-top-holders.q4-tab .search-field .x-field-input .x-input-el");

    //different types of Holder Searches
    private final By historicalInstitutionsHolderSearchResult = By.cssSelector("#ext-top-holders-historical-institutions-1");
    private final By currentInsidersHolderSearchResult = By.cssSelector("#ext-ownership-top-holders-current-1");
    private final By historicalFundsHolderSearchResults = By.xpath("//*[@id='ext-ownership-historical-fund-1']//div[contains(@class,'x-list-item')]");
    private final By holderSearchResult = By.xpath("//div[@id='ext-ownership-historical-institution-1']//div[contains(@class,'holder-info')]");
    private final By holderSearchResulttwo = By.cssSelector(".top-holders-list.fund .details .holder-info");
    private final By FundsETFsTab = By.xpath("//div[span[contains(text(),'Funds')]]");
    private final By InstitutionTab = By.xpath("//div[span[contains(text(),'Institutions')]]");
    */

    private final By InstitutionSearchResult = By.cssSelector(".top-holders-list.institution .details .holder-info .name");
    private final By InsidersTab = By.xpath("//div[span[contains(text(),'Insiders')]]");
    private final By InsiderSearchResult = By.cssSelector(".top-holders-list.insider .details .holder-info .name");
    private final By DefaultInsiderResult = By.cssSelector("#ext-element-1965");
    private final By DefaultHolderResult = By.cssSelector("#ext-element-5048");
    private final By thirteenFButton = By.xpath("//span[contains(text(),'13F')]");
    private final By nextPageButton = By.xpath("//div[contains(@class, 'x-unsized x-button nav-button next-page x-iconalign-center')]");
    private final By previousPageButton = By.xpath("//div[contains(@class, 'x-unsized x-button nav-button prev-page x-iconalign-center')]");

    //peer analysis section
    private final By peerAnalysisTab = By.xpath("//span[text()='Peer Analysis']");
    private final By institutionFilter = By.xpath("//div[contains(@class,'tab-icon')]//span[text()='Institutions']");
    private final By peerAnalysisCompany = By.xpath("//div[contains(@class,'ownership-peer-analysis-list')]//div[contains(@class,'x-domscroller')]//div[contains(@class,'citrus')]");

    //buyers&sellers filter section
    private final By weekData = By.xpath("//div[contains(@class,'dataview-item')]/div[contains(@class,'view-list-item')][4]");
    private final By pieLabels = By.xpath("//*[name()='svg']//*[contains(@class,'highcharts-title')]");
    private final By barLabels = By.xpath("//div[contains(@class,'trend-by-qr')]//*[name()='svg']//*[name()='text']");

    //map section
    private final By countryList = By.xpath("(//div[contains(@class,'truncated-list-inner')])[2]//div[contains(@class,'label')]");
    private final By countryValues = By.xpath("(//div[contains(@class,'truncated-list-inner')])[2]//div[contains(@class,'value')]");
    private final By other = By.xpath("//div[contains(@class,'list-modal-item')]/span[contains(@class,'label')]");

    public SecurityOwnershipPage(WebDriver driver) {
        super(driver);
    }

    public String getOwnershipTabTitle() {
        waitForLoadingScreen();
        return findElement(tabTitle).getText();
    }

    //// UNLESS OTHERWISE INDICATED, ALL METHODS ON THIS PAGE ARE DESIGNED TO OCCUR WHEN THE SURVEILLANCE TAB IS SELECTED. \\\\
    //      (They may or may not work while one of the date options is selected.)

    // index goes from 0 to 4, with 0 being end of last quarter
    public void clickLocation(By selector){
        Actions builder =  new Actions(driver);
        builder.moveToElement(findVisibleElement(selector)).click().build().perform();
    }

    public SecurityOwnershipPage selectDateRange(int index){
        wait.until(ExpectedConditions.elementToBeClickable(dateDropDown));
        findElement(dateDropDown).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dateDropDownItems));
        findVisibleElements(dateDropDownItems).get(index).click();
        pause(500);
        wait.until(ExpectedConditions.elementToBeClickable(lastTopSeller));
        return this;
    }

    // checks that the date tabs indicate the end dates of the last four quarters
    public boolean dateOptionsAreValid(){
        wait.until(ExpectedConditions.elementToBeClickable(dateDropDown));
        findElement(dateDropDown).click();
        LocalDate endOfLastQuarter;
        if (today.getMonthValue() > 3){
            endOfLastQuarter = today.withMonth((today.getMonthValue()-1)/3*3).with(TemporalAdjusters.lastDayOfMonth());
        }
        else {
            endOfLastQuarter = today.minusYears(1).with(TemporalAdjusters.lastDayOfYear());;
        }

        if (!LocalDate.parse(findElements(dateDropDownItems).get(0).getText(), shortDate).equals(endOfLastQuarter)){
            System.out.println("First date option is incorrect.\n\tExpected: "+endOfLastQuarter.format(shortDate)
                    +"\n\tDisplayed: "+findElements(dateDropDownItems).get(0).getText());
            return false;
        }
        if (!LocalDate.parse(findElements(dateDropDownItems).get(1).getText(), shortDate).equals(endOfLastQuarter.minusMonths(3).with(TemporalAdjusters.lastDayOfMonth()))){
            System.out.println("Second date option is incorrect.\n\tExpected: "+endOfLastQuarter.minusMonths(3).with(TemporalAdjusters.lastDayOfMonth()).format(shortDate)
                    +"\n\tDisplayed: "+findElements(dateDropDownItems).get(1).getText());
            return false;
        }
        if (!LocalDate.parse(findElements(dateDropDownItems).get(2).getText(), shortDate).equals(endOfLastQuarter.minusMonths(6).with(TemporalAdjusters.lastDayOfMonth()))){
            System.out.println("Third date option is incorrect.\n\tExpected: "+endOfLastQuarter.minusMonths(6).with(TemporalAdjusters.lastDayOfMonth()).format(shortDate)
                    +"\n\tDisplayed: "+findElements(dateDropDownItems).get(2).getText());
            return false;
        }
        if (!LocalDate.parse(findElements(dateDropDown).get(3).getText(), shortDate).equals(endOfLastQuarter.minusMonths(9).with(TemporalAdjusters.lastDayOfMonth()))){
            System.out.println("Fourth date option is incorrect.\n\tExpected: "+endOfLastQuarter.minusMonths(9).with(TemporalAdjusters.lastDayOfMonth()).format(shortDate)
                    +"\n\tDisplayed: "+findElements(dateDropDownItems).get(3).getText());
            return false;
        }

        return true;
    }


    // checks that the "As of" text is correct when each date option is selected

    public boolean asOfDateIsValid(){
        waitForElement(asOfDate);
        LocalDate lastFriday = today.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        LocalDate endOfQuarter;
        if (today.getMonthValue() > 3){
            endOfQuarter = today.withMonth((today.getMonthValue()-1)/3*3).with(TemporalAdjusters.lastDayOfMonth());
        }
        else {
            endOfQuarter = today.minusYears(1).with(TemporalAdjusters.lastDayOfYear());
        }
        LocalDate startOfQuarter = endOfQuarter.minusMonths(2).with(TemporalAdjusters.firstDayOfMonth());

        // with Surveillance tab selected, as of date should be last friday
        if (!LocalDate.parse(findElement(asOfDate).getText(), longDate).equals(lastFriday)){
            System.out.println("As of date while in surveillance mode is incorrect.\n\tExpected: "+lastFriday.format(longDate)
                    +"\n\tDisplayed: "+findElement(asOfDate).getText());
            return false;
        }

        // select first date tab and check that as of date is start - end of last quarter

        // select second date tab and check that as of date is start - end of quarter before that
        selectDateRange(ONE_MONTH);
        pause(500);
        endOfQuarter = endOfQuarter.minusMonths(3).with(TemporalAdjusters.lastDayOfMonth());
        startOfQuarter = startOfQuarter.minusMonths(3);
        if (!findElement(asOfDate).getText().equalsIgnoreCase(startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate))){
            System.out.println("As of date range after selecting second date option is incorrect.\n\tExpected: "+startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate)
                    +"\n\tDisplayed: "+findElement(asOfDate).getText());
            return false;
        }

        // select third date tab and check that as of date is start - end of quarter before that
        selectDateRange(THREE_MONTHS);
        pause(500);
        endOfQuarter = endOfQuarter.minusMonths(3).with(TemporalAdjusters.lastDayOfMonth());
        startOfQuarter = startOfQuarter.minusMonths(3);
        if (!findElement(asOfDate).getText().equalsIgnoreCase(startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate))){
            System.out.println("As of date range after selecting third date option is incorrect.\n\tExpected: "+startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate)
                    +"\n\tDisplayed: "+findElement(asOfDate).getText());
            return false;
        }

        // select fourth date tab and check that as of date is start - end of quarter before that
        selectDateRange(SIX_MONTHS);
        pause(500);
        endOfQuarter = endOfQuarter.minusMonths(3).with(TemporalAdjusters.lastDayOfMonth());
        startOfQuarter = startOfQuarter.minusMonths(3);
        if (!findElement(asOfDate).getText().equalsIgnoreCase(startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate))){
            System.out.println("As of date range after selecting third date option is incorrect.\n\tExpected: "+startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate)
                    +"\n\tDisplayed: "+findElement(asOfDate).getText());
            return false;
        }
        selectDateRange(ONE_YEAR);
        pause(500);
        endOfQuarter = endOfQuarter.minusMonths(3).with(TemporalAdjusters.lastDayOfMonth());
        startOfQuarter = startOfQuarter.minusMonths(3);
        if (!findElement(asOfDate).getText().equalsIgnoreCase(startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate))){
            System.out.println("As of date range after selecting third date option is incorrect.\n\tExpected: "+startOfQuarter.format(longDate)+" - "+endOfQuarter.format(longDate)
                    +"\n\tDisplayed: "+findElement(asOfDate).getText());
            return false;
        }

        return true;
    }

    // checks that entries on top buyers list have positive change numbers
    public boolean topBuyersListIsPositive(){
        waitForLoadingScreen();
        waitForElement(topBuyersNumbers);
        return elementsAreAllPositive(findVisibleElements(topBuyersNumbers));
    }

    // checks that entries on top sellers list have negative change numbers
    public boolean topSellersListIsNegative(){
        waitForLoadingScreen();
        waitForElement(topSellersNumbers);
        return elementsAreAllNegative(findVisibleElements(topSellersNumbers));
    }

    // checks that entries on top buyers list have change numbers in descending order
    public boolean topBuyersListIsDescending(){
        waitForLoadingScreen();
        waitForElement(topBuyersNumbers);
        return elementsAreNumDownSorted(findVisibleElements(topBuyersNumbers));
    }

    // checks that entries on top sellers list have change numbers in ascending order
    public boolean topSellersListIsAscending(){
        waitForLoadingScreen();
        waitForElement(topSellersNumbers);
        return elementsAreNumUpSorted(findVisibleElements(topSellersNumbers));
    }

    // checks that no institution appears in both the top buyers and the top sellers list (or appears twice in either list)
    public boolean topBuyersAndSellersAreUnique(){
        waitForLoadingScreen();
        waitForElementToAppear(topBuyersAndSellers);
        return elementsDoNotContainDuplicates(findVisibleElements(topBuyersAndSellers));
    }

    //// HOLDERS TABLE METHODS \\\\

    // this method does not work when Buyers or Sellers filter is selected
    public int getNumOfHoldersDisplayed(){
        waitForElement(holderTableName);
        return findVisibleElements(holderTableName).size();
    }

    // use this method when Buyers or Sellers filter is selected
    public int getNumOfHoldersDisplayedAlternate(){
        try {
            waitForElement(alternateHolderTableRow);
        }catch (TimeoutException e){ //will timeout if no entries are displayed or if buyers/sellers filter is not actually selected
            if (doesElementExist(holderTableName)){ //in case these entries become formatted like how the holders table is otherwise
                return findVisibleElements(holderTableName).size();
            }
            return 0;
        }
        return findVisibleElements(alternateHolderTableRow).size();
    }

    public SecurityOwnershipPage showMoreHolders(){
        waitForLoadingScreen();
        waitForElement(showMoreButton);
        int numHolders = getNumOfHoldersDisplayed();
        findVisibleElement(showMoreButton).click();
        for (int i=0; i<100; i++){
            if (getNumOfHoldersDisplayed() > numHolders){
                return this;
            }
            pause(100);
        }
        return this;
    }

    public boolean checkShowMoreHolders(){
        waitForLoadingScreen();
        scrollToElement(nextPageButton);
        try {
            findVisibleElement(nextPageButton).click();
            waitForLoadingScreen();
            findVisibleElement(previousPageButton).click();
        }
        catch (WebDriverException e)
        {
            return false;
        }
        return true;
    }

    public boolean canSortByName(){
        waitForLoadingScreen();
        boolean isSorted = true;
        long length = 1000L;
        // sorting by name ascending
        findVisibleElement(holderTableHeaderName).click();
        waitForLoadingScreen();
        if (!elementsAreAlphaUpSortedIgnoreCase(findVisibleElements(holderTableName))){
            System.out.println("Holders are not sorted by name ascending.");
            isSorted = false;
        }

        // sorting by name descending
        findVisibleElement(holderTableHeaderName).click();
        waitForLoadingScreen();
        if (!elementsAreAlphaDownSorted(findVisibleElements(holderTableName))){
            System.out.println("Holders are not sorted by name descending.");
            isSorted = false;
        }
        return isSorted;
    }



    public boolean canSortByPOS(){
        waitForLoadingScreen();
        boolean isSorted = true;
        clickLocation(holderTableHeaderPOS);
        //findVisibleElement(holderTableHeaderPOS).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findVisibleElements(holderTablePOS))){
            System.out.println("Holders are not sorted by POS ascending.");
            isSorted = false;
        }

        // sorting by POS descending
        findVisibleElement(holderTableHeaderPOS).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findVisibleElements(holderTablePOS))){
            System.out.println("Holders are not sorted by POS descending.");
            isSorted = false;
        }

        return isSorted;
    }

    public boolean canSortBy1Q(){
        waitForLoadingScreen();
        boolean isSorted =true;
        clickLocation(holderTableHeader1QChg);
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTable1QChg))){
            System.out.println("Holders are not sorted by 1Q change ascending.");
            isSorted = false;
        }

        // sorting by 1Q change descending
        clickLocation(holderTableHeader1QChg);
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTable1QChg))){
            System.out.println("Holders are not sorted by 1Q change descending.");
            isSorted = false;
        }
        return isSorted;
    }

    public boolean canSortByMarketValue(){
        waitForLoadingScreen();
        boolean isSorted =true;
        findVisibleElement(holderTableHeaderMktVal).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTableMktVal))){
            System.out.println("Holders are not sorted by market value ascending.");
            isSorted = false;
        }

        // sorting by market value descending
        findVisibleElement(holderTableHeaderMktVal).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTableMktVal))){
            System.out.println("Holders are not sorted by market value descending.");
            isSorted = false;
        }
        return isSorted;
    }


    public boolean canSortByMarketValueChange(){
        waitForLoadingScreen();
        boolean isSorted= true;

        findVisibleElement(holderTableHeaderMktValChg).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTableMktValChg))){
            System.out.println("Holders are not sorted by market value change ascending.");
            isSorted = false;
        }

        // sorting by market value change descending
        findVisibleElement(holderTableHeaderMktValChg).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTableMktValChg))){
            System.out.println("Holders are not sorted by market value change descending.");
            isSorted = false;
        }
        return isSorted;
    }

    public boolean canSortByOS(){
        waitForLoadingScreen();
        boolean isSorted = true;
        findVisibleElement(holderTableHeaderPercOS).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTablePercOS))){
            System.out.println("Holders are not sorted by %OS ascending.");
            isSorted = false;
        }

        // sorting by %OS descending
        findVisibleElement(holderTableHeaderPercOS).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTablePercOS))){
            System.out.println("Holders are not sorted by %OS descending.");
            isSorted = false;
        }

        return isSorted;

    }

    public boolean canSortByPort(){
        waitForLoadingScreen();
        boolean isSorted=true;
        clickLocation(holderTableHeaderPercPort);
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTablePercPort))){
            System.out.println("Holders are not sorted by %Port ascending.");
            isSorted = false;
        }

        // sorting by %Port descending
        clickLocation(holderTableHeaderPercPort);
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTablePercPort))){
            System.out.println("Holders are not sorted by %Port descending.");
            isSorted = false;
        }
        return isSorted;
    }

    public boolean canSortByStyle(){
        waitForLoadingScreen();
        boolean isSorted =true;

        findVisibleElement(holderTableHeaderStyle).click();
        waitForLoadingScreen();
        if (!elementsAreAlphaUpSorted(findElements(holderTableStyle))){
            System.out.println("Holders are not sorted by style ascending.");
            isSorted = false;
        }

        // sorting by style descending
        // Scrolling to the Activists toggle button so that the style header is back in the frame
        scrollToElement(activistFilter);
        findVisibleElement(holderTableHeaderStyle).click();

        //waitForElementToBeClickable(holderTableHeaderStyle).click();
        waitForLoadingScreen();
        if (!elementsAreAlphaDownSorted(findElements(holderTableStyle))){
            System.out.println("Holders are not sorted by style descending.");
            isSorted = false;
        }
        return isSorted;
    }

    public boolean canSoryByDate(){
        waitForLoadingScreen();
        boolean isSorted = true;
        findVisibleElement(holderTableHeaderAsOf).click();
        waitForLoadingScreen();
        if (!elementsAreDateUpSorted(findElements(holderTableAsOf))){
            System.out.println("Holders are not sorted by as of date ascending.");
            isSorted = false;
        }

        // sorting by as of date descending
        findVisibleElement(holderTableHeaderAsOf).click();
        waitForLoadingScreen();
        if (!elementsAreDateDownSorted(findElements(holderTableAsOf))){
            System.out.println("Holders are not sorted by as of date descending.");
            isSorted = false;
        }
        return isSorted;
    }

    public boolean canSortByQR() {
        boolean isSorted = true;// sorting by QR ascending
        waitForLoadingScreen();
        findElement(holderTableHeaderQR).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTableQR))) {
            System.out.println("Holders are not sorted by QR ascending.");
            isSorted = false;
        }

        // sorting by QR descending
        findVisibleElement(holderTableHeaderQR).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTableQR))) {
            System.out.println("Holders are not sorted by QR descending.");
            isSorted = false;
        }
        return isSorted;
    }

    public boolean canSortHoldersTable(){
        boolean isSorted = true;
        long length = 1000L;
        // sorting by name ascending
        findVisibleElement(holderTableHeaderName).click();
        waitForLoadingScreen();
        if (!elementsAreAlphaUpSortedIgnoreCase(findElements(holderTableName))){
            System.out.println("Holders are not sorted by name ascending.");
            isSorted = false;
        }

        // sorting by name descending
        findVisibleElement(holderTableHeaderName).click();
        waitForLoadingScreen();
        if (!elementsAreAlphaDownSorted(findElements(holderTableName))){
            System.out.println("Holders are not sorted by name descending.");
            isSorted = false;
        }

        // sorting by POS ascending
        findVisibleElement(holderTableHeaderPOS).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTablePOS))){
            System.out.println("Holders are not sorted by POS ascending.");
            isSorted = false;
        }

        // sorting by POS descending
        findVisibleElement(holderTableHeaderPOS).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTablePOS))){
            System.out.println("Holders are not sorted by POS descending.");
            isSorted = false;
        }

        // sorting by 1Q change ascending
        findVisibleElement(holderTableHeader1QChg).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTable1QChg))){
            System.out.println("Holders are not sorted by 1Q change ascending.");
            isSorted = false;
        }

        // sorting by 1Q change descending
        findVisibleElement(holderTableHeader1QChg).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTable1QChg))){
            System.out.println("Holders are not sorted by 1Q change descending.");
            isSorted = false;
        }

        // sorting by market value ascending
        findVisibleElement(holderTableHeaderMktVal).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTableMktVal))){
            System.out.println("Holders are not sorted by market value ascending.");
            isSorted = false;
        }

        // sorting by market value descending
        findVisibleElement(holderTableHeaderMktVal).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTableMktVal))){
            System.out.println("Holders are not sorted by market value descending.");
            isSorted = false;
        }

        // sorting by market value change ascending
        findVisibleElement(holderTableHeaderMktValChg).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTableMktValChg))){
            System.out.println("Holders are not sorted by market value change ascending.");
            isSorted = false;
        }

        // sorting by market value change descending
        findVisibleElement(holderTableHeaderMktValChg).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTableMktValChg))){
            System.out.println("Holders are not sorted by market value change descending.");
            isSorted = false;
        }

        // sorting by %OS ascending
        findVisibleElement(holderTableHeaderPercOS).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTablePercOS))){
            System.out.println("Holders are not sorted by %OS ascending.");
            isSorted = false;
        }

        // sorting by %OS descending
        findVisibleElement(holderTableHeaderPercOS).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTablePercOS))){
            System.out.println("Holders are not sorted by %OS descending.");
            isSorted = false;
        }

        // sorting by %Port ascending
        findVisibleElement(holderTableHeaderPercPort).click();
        pause(length);
        if (!elementsAreNumUpSorted(findElements(holderTablePercPort))){
            System.out.println("Holders are not sorted by %Port ascending.");
            isSorted = false;
        }

        // sorting by %Port descending
        findVisibleElement(holderTableHeaderPercPort).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTablePercPort))){
            System.out.println("Holders are not sorted by %Port descending.");
            isSorted = false;
        }

        // sorting by style ascending
        findVisibleElement(holderTableHeaderStyle).click();
        waitForLoadingScreen();;
        if (!elementsAreAlphaUpSorted(findElements(holderTableStyle))){
            System.out.println("Holders are not sorted by style ascending.");
            isSorted = false;
        }

        // sorting by style descending
        findVisibleElement(holderTableHeaderStyle).click();
        waitForLoadingScreen();
        if (!elementsAreAlphaDownSorted(findElements(holderTableStyle))){
            System.out.println("Holders are not sorted by style descending.");
            isSorted = false;
        }
/* No Longer sorting these because of performance issues
        // sorting by turnover ascending
        findVisibleElement(holderTableHeaderTurnover).click();
        pause(length);
        if (!elementsAreTurnoverUpSorted(findElements(holderTableTurnover))){
            System.out.println("Holders are not sorted by turnover ascending.");
            isSorted = false;
        }

        // sorting by turnover descending
        findVisibleElement(holderTableHeaderTurnover).click();
        pause(length);
        if (!elementsAreTurnoverDownSorted(findElements(holderTableTurnover))){
            System.out.println("Holders are not sorted by turnover descending.");
            isSorted = false;
        }

        // sorting by AUM ascending
        findVisibleElement(holderTableHeaderAUM).click();
        pause(length);
        if (!elementsAreNumUpSorted(findElements(holderTableAUM))){
            System.out.println("Holders are not sorted by AUM ascending.");
            isSorted = false;
        }

        // sorting by AUM descending
        findVisibleElement(holderTableHeaderAUM).click();
        pause(length);
        if (!elementsAreNumDownSorted(findElements(holderTableAUM))){
            System.out.println("Holders are not sorted by AUM descending.");
            isSorted = false;
        }
        */


        // sorting by as of date ascending
        findVisibleElement(holderTableHeaderAsOf).click();
        waitForLoadingScreen();
        if (!elementsAreDateUpSorted(findElements(holderTableAsOf))){
            System.out.println("Holders are not sorted by as of date ascending.");
            isSorted = false;
        }

        // sorting by as of date descending
        findVisibleElement(holderTableHeaderAsOf).click();
        waitForLoadingScreen();
        if (!elementsAreDateDownSorted(findElements(holderTableAsOf))){
            System.out.println("Holders are not sorted by as of date descending.");
            isSorted = false;
        }

        // sorting by QR ascending
        findVisibleElement(holderTableHeaderQR).click();
        waitForLoadingScreen();
        if (!elementsAreNumUpSorted(findElements(holderTableQR))){
            System.out.println("Holders are not sorted by QR ascending.");
            isSorted = false;
        }

        // sorting by QR descending
        findVisibleElement(holderTableHeaderQR).click();
        waitForLoadingScreen();
        if (!elementsAreNumDownSorted(findElements(holderTableQR))){
            System.out.println("Holders are not sorted by QR descending.");
            isSorted = false;
        }

        return isSorted;
    }

    // Checks that all values in the 1Q CHG and MKT VAL CHG columns are green if positive, red if negative, or dark grey if zero
    public boolean holderTableChangeValueColouringIsCorrect(){
        boolean correct = true;
        List<WebElement> values = findVisibleElements(holderTable1QChg);
        values.addAll(findVisibleElements(holderTableMktValChg));

        for (WebElement value : values){
            if (Double.parseDouble(value.getText().replace(",", "")) > 0){
                if (!value.getCssValue("color").equals("rgba(26, 188, 156, 1)")){
                    System.out.println("Positive change value of "+value.getText()+" is not green.\n\tExpected: rgba(26, 188, 156, 1)\n\tActual: "+value.getCssValue("color"));
                    correct = false;
                }
            }
            else if (Double.parseDouble(value.getText().replace(",", "")) < 0){
                if (!value.getCssValue("color").equals("rgba(236, 106, 76, 1)")){
                    System.out.println("Negative change value of "+value.getText()+" is not red.\n\tExpected: rgba(236, 106, 76, 1)\n\tActual: "+value.getCssValue("color"));
                    correct = false;
                }
            }
            else {
                if (!value.getCssValue("color").equals("rgba(84, 91, 98, 1)")){
                    System.out.println("Zero change value of "+value.getText()+" is not dark grey.\n\tExpected: rgba(84, 91, 98, 1)\n\tActual: "+value.getCssValue("color"));
                    correct = false;
                }
            }
        }

        return correct;
    }

    public String[] getHolderNames(){
        waitForElementToAppear(holderTableName);
        List<WebElement> elements = findVisibleElements(holderTableName);
        String[] names = new String[elements.size()];
        for (int i=0; i<elements.size(); i++){
            names[i] = elements.get(i).getText();
        }
        return names;
    }

    public InstitutionPage goToInstitutionalHolder(int index){
        waitForElement(holderTableName);
        findVisibleElements(holderTableName).get(index).click();
        return new InstitutionPage(driver);
    }

    public SecurityOwnershipPage showOnlyActivists(){
        waitForElement(activistFilter);
        if (doesElementExist(activistFilterOff)){ //does nothing if filter is already selected
            int numHolders = getNumOfHoldersDisplayed();
            findElement(activistFilter).click();
            for (int i=0; i<50; i++){ //waits up to 5 seconds for the number of holders displayed to change
                if (getNumOfHoldersDisplayed() != numHolders){
                    return this;
                }
                pause(100);
            }
        }
        //waitForLoadingScreen();
        return this;
    }

    public SecurityOwnershipPage doNotShowOnlyActivists(){
        waitForElement(activistFilter);
        if (doesElementExist(activistFilterOn)){ //does nothing if filter is not selected
            int numHolders = getNumOfHoldersDisplayed();
            findElement(activistFilter).click();
            for (int i=0; i<50; i++){ //waits up to 5 seconds for the number of holders displayed to change
                if (getNumOfHoldersDisplayed() != numHolders){
                    return this;
                }
                pause(100);
            }
        }
        return this;
    }

    public int getNumOfActivistsDisplayed(){
        waitForElement(activistIcon);
        List<WebElement> icons = findVisibleElements(activistIcon);
        int numIcons = 0;
        for (WebElement icon : icons){ //only counts icons if they aren't obstructed from view
            try {
                wait.until(ExpectedConditions.elementToBeClickable(icon));
                numIcons++;
            }catch (TimeoutException e){ //will go here if not clickable (because there are fully obstructed)
                System.out.println("Activist icon is not clickable.");
            }
        }
        return numIcons;
    }

    // this method is to be used while one of the date tabs is selected
    public SecurityOwnershipPage showAllTypes(){
        waitForElement(allTypesFilter);
        findVisibleElement(allTypesFilter).click();
        waitForLoadingScreen();
        return this;
    }

    // this method is to be used while one of the date tabs is selected
    public SecurityOwnershipPage showOnlyInstitutions(){
        waitForElement(institutionsFilter);
        findVisibleElement(institutionsFilter).click();
        waitForLoadingScreen();
        return this;
    }

    // this method is to be used while one of the date tabs is selected
    public SecurityOwnershipPage showOnlyInsiders(){
        waitForLoadingScreen();
        waitForElement(insidersFilter);
        findVisibleElement(insidersFilter).click();
        waitForLoadingScreen();
        return this;
    }

    // this method is to be used while one of the date tabs is selected
    public SecurityOwnershipPage showOnlyFunds(){
        waitForElement(fundsFilter);
        findVisibleElement(fundsFilter).click();
        waitForLoadingScreen();
        return this;
    }

    // this method is to be used while one of the date tabs is selected
    // institutions have a red institution icon
    public int getNumOfInstitutionsDisplayed(){
        waitForElement(institutionIcon);
        return findVisibleElements(institutionIcon).size();
    }

    // this method is to be used while one of the date tabs is selected
    public int getNumOfInsidersDisplayed(){
        int numInsiders = 0;
        waitForLoadingScreen();
        waitForElement(holderTableRow);
        List<WebElement> rows = findVisibleElements(holderTableRow);
        for (WebElement row : rows){
                if (doesElementExist(By.xpath("//div[@id='"+row.getAttribute("id")+"']//i[contains(@class,'q4i-insider-2pt')]"))){ //checks whether there's no institution icon within that row
                    numInsiders++;
                }
        }
        return numInsiders;
    }

    // this method is to be used while one of the date tabs is selected
    // funds have QR scores but no institution icons
    public int getNumOfFundsDisplayed(){
        int numFunds = 0;
        waitForElement(holderTableRow);
        List<WebElement> rows = findVisibleElements(holderTableRow);

        for (WebElement row : rows){
            if (doesElementExist(By.xpath("//div[@id='"+row.getAttribute("id")+"']//i[contains(@class,'q4i-fund-2pt')]"))){ //checks whether there's no institution icon within that row
                numFunds++;
            }

        }
        return numFunds;
    }

    public SecurityOwnershipPage showOnlyBuyers(){
        waitForElement(buyersFilter);
        findVisibleElement(buyersFilter).click();
        waitForLoadingScreen();
        return this;
    }

    public SecurityOwnershipPage showOnlySellers(){
        waitForElement(sellersFilter);
        findVisibleElement(sellersFilter).click();
        waitForLoadingScreen();
        return this;
    }

    public SecurityOwnershipPage showBuyersAndSellers(){
        waitForElement(allBuyersSellersFilter);
        findVisibleElement(allBuyersSellersFilter).click();
        waitForLoadingScreen();
        return this;
    }

    public int getNumofBuyersDisplayed(){
        int numBuyers = 0;
        waitForElement(holderTable1W);
        List<WebElement> values = findVisibleElements(holderTable1W);
        for (WebElement value : values){
            if (Integer.parseInt(value.getText().replace(",","")) > 0){
                numBuyers++;
            }
        }
        return numBuyers;
    }

    public int getNumofSellersDisplayed(){
        int numSellers = 0;
        waitForElement(holderTable1W);
        List<WebElement> values = findVisibleElements(holderTable1W);
        for (WebElement value : values){
            if (Integer.parseInt(value.getText().replace(",","")) < 0){
                numSellers++;
            }
        }
        return numSellers;
    }

    //// INSTITUTIONAL OWNERSHIP METHODS \\\\
    
    // checks that the correct number of style bars of each colour are present
    public boolean styleBarsNumsAreCorrect(){
        boolean correct = true;
        int numStyles = 7; //number of styles (including "Other") that should be displayed
        waitForElement(styleBarsYellow);

        if (findVisibleElements(styleBarsBlue).size() != numStyles){
            System.out.println("Incorrect number of blue style bars displayed.\n\tExpected: "+numStyles+"\n\tActual: "+findVisibleElements(styleBarsBlue).size());
            correct = false;
        }
        if (findVisibleElements(styleBarsRed).size() != numStyles){
            System.out.println("Incorrect number of red style bars displayed.\n\tExpected: "+numStyles+"\n\tActual: "+findVisibleElements(styleBarsRed).size());
            correct = false;
        }
        if (findVisibleElements(styleBarsOrange).size() != numStyles){
            System.out.println("Incorrect number of orange style bars displayed.\n\tExpected: "+numStyles+"\n\tActual: "+findVisibleElements(styleBarsOrange).size());
            correct = false;
        }
        if (findVisibleElements(styleBarsYellow).size() != numStyles){
            System.out.println("Incorrect number of yellow style bars displayed.\n\tExpected: "+numStyles+"\n\tActual: "+findVisibleElements(styleBarsBlue).size());
            correct = false;
        }

        return correct;
    }

    // checks that the correct number of turnover bars of each colour are present
    public boolean turnoverBarsNumsAreCorrect(){
        boolean correct = true;
        int numTurnover = 5; //number of turnover types that should be displayed
        waitForElement(turnoverBarsYellow);

        if (findVisibleElements(turnoverBarsBlue).size() != numTurnover){
            System.out.println("Incorrect number of blue turnover bars displayed.\n\tExpected: "+numTurnover+"\n\tActual: "+findVisibleElements(turnoverBarsBlue).size());
            correct = false;
        }
        if (findVisibleElements(turnoverBarsRed).size() != numTurnover){
            System.out.println("Incorrect number of red turnover bars displayed.\n\tExpected: "+numTurnover+"\n\tActual: "+findVisibleElements(turnoverBarsRed).size());
            correct = false;
        }
        if (findVisibleElements(turnoverBarsOrange).size() != numTurnover){
            System.out.println("Incorrect number of orange turnover bars displayed.\n\tExpected: "+numTurnover+"\n\tActual: "+findVisibleElements(turnoverBarsOrange).size());
            correct = false;
        }
        if (findVisibleElements(turnoverBarsYellow).size() != numTurnover){
            System.out.println("Incorrect number of yellow turnover bars displayed.\n\tExpected: "+numTurnover+"\n\tActual: "+findVisibleElements(turnoverBarsBlue).size());
            correct = false;
        }

        return correct;
    }

    // checks that all bars have values between 0 and 100 displayed above them
    public boolean institutionalBarsHaveValidNumbers(){
        waitForElement(institutionalPercentages);
        return elementsAreAllPercentages(findVisibleElements(institutionalPercentages));
    }

    //// TREND ANALYSIS METHODS \\\\

    // Checks whether hovering over the charts causes hovertext to appear
    public boolean canHoverOverTrendAnalysisCharts(){
        boolean canHover = true;
        waitForLoadingScreen();
        waitForAnyElementToAppear(trendAnalysisChartBody);
        List<WebElement> charts = findVisibleElements(trendAnalysisChartBody);


        for(int i=0; i<charts.size(); i++){
            //actions.clickAndHold(charts.get(i)).perform(); //clickAndHold needed so that cursor is still there when getAttribute is run
            //actions.moveToElement(charts.get(i)).click().perform();
            actions.moveToElement(charts.get(i)).perform();
            actions.click(charts.get(i)).perform();
            pause(2000);
            waitForAnyElementToAppear(trendAnalysisHoverText);
            List<WebElement> hovertexts = findVisibleElements(trendAnalysisHoverText);

            if(hovertexts.size()==0){
                canHover=false;
            }
            if(hovertexts.size()>2){
                System.out.print("You were right\n "+i);
            }
            for(int j=0; j<hovertexts.size(); j++) {
                if (!hovertexts.get(j).getAttribute("opacity").equals("1")) { // when hovertext is not visible, opacity attribute is either zero or non-existent
                    System.out.println("Trend Analysis chart (index " + i + ") does have visible hovertext when hovering.");
                    canHover = false;
                }
            }
        }

        return canHover;
    }

    //// INSTITUTIONAL HOLDER ANALYSIS METHODS \\\\
    // these methods are to be used while one of the date tabs is selected

    public int getNumOfHolderBreakdownValues(){
        waitForLoadingScreen();
        waitForElement(holderBreakdownValues);
        return findVisibleElements(holderBreakdownValues).size();
    }

    // checks that breakdown values are between 0 and 100 (inclusive)
    public boolean holderBreakdownValuesAreValid(){
        waitForLoadingScreen();
        waitForElement(holderBreakdownValues);
        return elementsAreAllPercentages(findVisibleElements(holderBreakdownValues));
    }

    public int getNumofHolderTypeValues(){
        waitForLoadingScreen();
        waitForElement(holderTypeValues);
        return findVisibleElements(holderTypeValues).size();
    }

    // checks that all type values are between 0 and 100 (inclusive)
    public boolean holderTypeValuesAreValid(){
        waitForLoadingScreen();
        waitForElement(holderTypeValues);
        return elementsAreAllPercentages(findVisibleElements(holderTypeValues));
    }

    // clicks on the "Other" option under "Investor Type", causing the dropdown to open
    public SecurityOwnershipPage openOtherHolderTypes(){
        waitForLoadingScreen();
        waitForElement(holderTypeOther);
        findVisibleElement(holderTypeOther).click();
        waitForElement(holderOtherDropdown);
        return this;
    }

    public int getNumofHolderStyleValues(){
        waitForLoadingScreen();
        waitForElement(holderStyleValues);
        return findVisibleElements(holderStyleValues).size();
    }

    // checks that all style values are between 0 and 100 (inclusive)
    public boolean holderStyleValuesAreValid(){
        waitForLoadingScreen();
        waitForElement(holderStyleValues);
        return elementsAreAllPercentages(findVisibleElements(holderStyleValues));
    }

    // clicks on the "Other" option under "Holding Style", causing the dropdown to open
    public SecurityOwnershipPage openOtherHolderStyles(){
        waitForElement(holderStyleOther);
        findVisibleElement(holderStyleOther).click();
        waitForElement(holderOtherDropdown);
        return this;
    }

    public int getNumofHolderTurnoverValues(){
        waitForLoadingScreen();
        waitForElement(holderTurnoverValues);
        return findVisibleElements(holderTurnoverValues).size();
    }

    // checks that all turnover values are between 0 and 100 (inclusive)
    public boolean holderTurnoverValuesAreValid(){
        waitForLoadingScreen();
        waitForElement(holderTurnoverValues);
        return elementsAreAllPercentages(findVisibleElements(holderTurnoverValues));
    }

    // clicks on the "Other" option under "Turnover", causing the dropdown to open
    public SecurityOwnershipPage openOtherHolderTurnovers(){
        waitForElement(holderTurnoverOther);
        findVisibleElement(holderTurnoverOther).click();
        waitForElement(holderOtherDropdown);
        return this;
    }

    // checks that there are at least two values in the other dropdown
    public boolean otherHolderValuesArePresent(){
        waitForElement(holderOtherValues);
        return findVisibleElements(holderOtherValues).size() > 1;
    }

    // checks that all of the values in the other dropdown are between 0 and 100 (inclusive)
    public boolean otherHolderValuesAreValid(){
        waitForElement(holderOtherValues);
        return elementsAreAllPercentages(findVisibleElements(holderOtherValues));

    }

    // closes the other dropdown by clicking outside it
    public SecurityOwnershipPage closeOtherHolderDropdown(){
        waitForElement(holderOtherDropdown);
        actions.moveToElement(findElement(holderOtherDropdown), -10, -10).click().perform();
        pause(3000);
        return this;
    }

    // checks that the other dropdown has been closed
    public boolean otherHolderDropdownIsClosed(){
        return !doesElementExist(holderOtherDropdown);
    }

    public SecurityOwnershipPage viewHistoricalHolders() {
        waitForLoadingScreen();
        findElement(thirteenFButton).click();
        findElement(historicalTab).click();
        waitForLoadingScreen();

        return this;
    }

    public SecurityOwnershipPage viewCurrentHolders(){
        waitForLoadingScreen();
        findElement(thirteenFButton).click();
        findElement(currentTab).click();

        return this;
    }

    public SecurityOwnershipPage searchForHoldings(String searchTerm) {
        waitForElementToAppear(holdingsSearchField).sendKeys(searchTerm);
        // Added pause to account for the situation where the test will take the original top value of the table and won't wait for the results to be updated by the search.
        pause(3000);
        return this;
    }

    //different types of Holder Searches
    public String getHistoricalInstitutionsHolderSearchResults() {
        waitForElementToRest(historicalInstitutionsHolderSearchResult, 700L);
        return waitForElementToAppear(historicalInstitutionsHolderSearchResult).getText();
    }

    public String getCurrentInsidersHolderSearchResults() {
        waitForElementToRest(currentInsidersHolderSearchResult, 700L);
        return waitForElementToAppear(currentInsidersHolderSearchResult).getText();
    }

    public String getHistoricalFundsHolderSearchResults(){
        waitForElementToRest(historicalFundsHolderSearchResults, 700L);
        return waitForElementToAppear(historicalFundsHolderSearchResults).getText();
    }

    public SecurityOwnershipPage searchForFundsETFs(String searchTerm) {
        waitForElementToAppear(holdingsSearchField).sendKeys(searchTerm);
        // Added pause to account for the situation where the test will take the original top value of the table and won't wait for the results to be updated by the search.
        pause(3000);
        return this;
    }


    public String getInstitutionSearchResults() {
        waitForElementToRest(InstitutionSearchResult, 700L);
        return waitForElementToAppear(InstitutionSearchResult).getText();
    }

    public void selectInsiderstab() {
        waitForLoadingScreen();
        waitForElementToBeClickable(thirteenFButton).click();
        findVisibleElement(InsidersTab).click();

    }

    public String getInsiderSearchResults() {
        waitForElementToRest(InsiderSearchResult, 700L);
        return waitForElementToAppear(InsiderSearchResult).getText();
    }

    public String getHolderSearchResults() {
        waitForElementToRest(holderSearchResult, 700L);
        return findVisibleElement(holderSearchResult).getText();
    }

    public SecurityOwnershipPage selectFundsETFstab() {
        waitForLoadingScreen();
        findVisibleElement(FundsETFsTab).click();
        return this;
    }

    public String getHolderSearchResultstwo() {
        waitForElement(holderSearchResulttwo);
        scrollToElement(holderSearchResulttwo);
        waitForElementToAppear(holderSearchResulttwo);
        return findElement(holderSearchResulttwo).getText();
    }

    public SecurityOwnershipPage viewInstitutiontab() {
        waitForLoadingScreen();
        findElement(thirteenFButton).click();
        findVisibleElement(InstitutionTab).click();

        return this;
    }

    public SecurityOwnershipPage selectThirteenF() {
        waitForLoadingScreen();
        waitForElement(thirteenFButton).click();
        waitForLoadingScreen();
        return this;
    }

    public SecurityOwnershipPage selectTrendAnalysis(){
        waitForLoadingScreen();
        findElement(trendAnalysisPage).click();
        return this;
    }

     public boolean checkPeerAnalysis(String companyName){
        waitForLoadingScreen();
        findElement(thirteenFButton).click();
        waitForLoadingScreen();
        findElement(peerAnalysisTab).click();
        waitForLoadingScreen();
        if (!getPeerAnalysisName().contains(companyName))return false; //check company title for institutions
        List<String> firstTenPeerDataIns = getFirstTenPeerData(); //check data is in correct form
        for (String s: firstTenPeerDataIns){
            if (s == "-" || s == null || s == "0"){
                return false;
            }
        }
         waitForLoadingScreen();
         findVisibleElement(fundsFilter).click();
         waitForLoadingScreen();
         if (!getPeerAnalysisName().contains(companyName))return false; //check company title for funds
         List<String> firstTenPeerDataFund = getFirstTenPeerData(); //check data is correct form
         for (String s: firstTenPeerDataFund){
             if (s == "-" || s == null || s == "0"){
                 return false;
             }
         }
        return true;
    }

    public String getPeerAnalysisName(){
        return findVisibleElement(peerAnalysisCompany).getText();
    }

    public List<String> getFirstTenPeerData(){
        List<WebElement> peerData = new ArrayList<WebElement>();
        try {
            peerData = findVisibleElements(By.xpath("//div[div[contains(@class,'x-grid-cell') and div[contains(@class,'details')]]]//following-sibling::div[contains(@class,'x-grid-cell')]"));
        }catch(NullPointerException e)
        {
            e.printStackTrace();
        }
        List<String> list = new ArrayList<String>();
        for (WebElement e : peerData){
            list.add(e.getText());
        }
        return list;
    }

    public boolean checkBuyerSellerFilter(){
        waitForLoadingScreen();
        findElement(buyersFilter).click();
        waitForLoadingScreen();
        List <String> buyers = getWeekData();
        findElement(sellersFilter).click();
        waitForLoadingScreen();
        List <String> sellers = getWeekData();
        for (String e : buyers)
        {
            if (e.contains("-")){
                return false;
            }
        }
        for (String e : sellers)
        {
            if (!e.contains("-")){
                return false;
            }
        }
        return true;
    }

    public List<String> getWeekData(){
        List <WebElement> dataElements = findVisibleElements(weekData);
        List <String> dataStrings = new ArrayList<>();
        for (WebElement e : dataElements){
            dataStrings.add(e.getText());
        }
        return dataStrings;
    }

    public boolean checkPies(){
        waitForLoadingScreen();
        findElement(buyersFilter).click();
        waitForLoadingScreen();
        List<WebElement> pieTitle = findElements(pieLabels);
        if(!(pieTitle.get(0).getText().trim().contentEquals(pieTitle.get(4).getText().trim())
        && getLastFriday().contains(pieTitle.get(4).getText().trim()))){
            System.out.println(getLastFriday());
            System.out.println(pieTitle.get(4).getText().trim());
            return false;
        }
        else if(!(pieTitle.get(2).getText().trim().contentEquals(pieTitle.get(6).getText().trim())
                && getEndOfLastQuarter().contains(pieTitle.get(6).getText().trim()))){
            System.out.println(getEndOfLastQuarter());
            System.out.println(pieTitle.get(6).getText().trim());
            return false;
        }
        else if(!(pieTitle.get(3).getText().trim().contentEquals(pieTitle.get(7).getText().trim())
                && getEndOfQuarterBeforeLast().contains(pieTitle.get(7).getText().trim()))){
            System.out.println(getEndOfQuarterBeforeLast());
            System.out.println(pieTitle.get(7).getText().trim());
            return false;
        }
        else if(!(pieTitle.get(1).getText().trim().contentEquals(pieTitle.get(5).getText().trim())
                && pieTitle.get(5).getText().trim().contains("6WK"))){
            System.out.println(pieTitle.get(5).getText().trim());
            return false;
        }
        return true;
    }

    public Boolean checkBars(){
        waitForLoadingScreen();
        findElement(buyersFilter).click();
        waitForLoadingScreen();
        List<WebElement> barTitle = findElements(barLabels);
        for(int i = 0; i < 4; i++){
        if(Integer.parseInt(barTitle.get(i).getText().trim()) < 0
                || Integer.parseInt(barTitle.get(i).getText().trim()) > 100){
            return false;
        }
        }
        if(!(getLastFriday().contains(barTitle.get(4).getText().trim()))){
            return false;
        }
        else if(!(barTitle.get(5).getText().trim().contains("6WK"))){
            return false;
        }
        else if(!(getEndOfLastQuarter().contains(barTitle.get(6).getText().trim()))){
            return false;
        }
        else if(!(getEndOfQuarterBeforeLast().contains(barTitle.get(7).getText().trim()))){
            return false;
        }
        
        return true;
    }

    private String getLastFriday() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        cal.set(Calendar.DAY_OF_WEEK, 6);
        return dateFormat.format(cal.getTime());
    }

    private String getEndOfLastQuarter(){
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        switch (cal.get (Calendar.MONTH) / 3){
            case 3 :
                return "9/30/2017";
            case 2 :
                return "6/30/2017";
            case 1 :
                return "3/31/2017";
            case 0 : default :
                return "12/31/2017";
        }
    }

    private String getEndOfQuarterBeforeLast(){
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        switch (cal.get (Calendar.MONTH) / 3){
            case 3 :
                return "6/30/2017";
            case 2 :
                return "3/31/2017";
            case 1 :
                return "12/31/2017";
            case 0 : default :
                return "9/30/2017";
        }
    }

    private String hexToRgb(String hex){
        return "color: rgb(" + Color.decode(hex).getRed() + ", " + Color.decode(hex).getGreen()
                + ", " + Color.decode(hex).getBlue() + ");";
    }

    public boolean checkInstitutionalHolderAnalysisMap(){
        waitForLoadingScreen();
        findElement(thirteenFButton).click();
        waitForLoadingScreen();
        List<WebElement> countries = findElements(countryList);
        List<WebElement> values = findElements(countryValues);
        List<String> colours = new ArrayList<>();
        //check all numbers under "countrty by" are between 0 and 100
        //and records colour in hex form
        for (WebElement e : values){
            colours.add(e.getAttribute("style"));
            if (Double.parseDouble(e.getText()) > 100
                    || Double.parseDouble(e.getText()) < 0){
                return false;
            }
        }
        countries.get(3).click();
        waitForLoadingScreen();
        List<WebElement> others = findElements(other);
        List<String> countryNames = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            countryNames.add(countries.get(i).getText().toLowerCase().replace(" ","-").split(",")[0]);
        }
        for (WebElement e : others){
            countryNames.add(e.getText().toLowerCase().replace(" ", "-").split(",")[0]);
        }
        //compare the colours
        for (int j = 0; j < countryNames.size(); j++){
            if (j >= 0 && j <= 2){
                    if (!(hexToRgb(findVisibleElement(By.xpath("//*[name() = 'path'][contains(@class,'highcharts-name-" + countryNames.get(j) +  "')]"))
                            .getAttribute("fill")).contentEquals(colours.get(j)))){
                        System.out.println(hexToRgb(findVisibleElement(By.xpath("//*[name() = 'path'][contains(@class,'highcharts-name-" + countryNames.get(j) +  "')]"))
                                .getAttribute("fill")));
                        System.out.println(colours.get(j));
                        return false;
                    }
            }
            else {
                if (doesElementExist(By.xpath("//*[name() = 'path'][contains(@class,'highcharts-name-" + countryNames.get(j) + "')]"))) {
                    if (!(hexToRgb(findVisibleElement(By.xpath("//*[name() = 'path'][contains(@class,'highcharts-name-" + countryNames.get(j) +  "')]"))
                            .getAttribute("fill")).contentEquals(colours.get(3)))){
                        System.out.println(hexToRgb(findVisibleElement(By.xpath("//*[name() = 'path'][contains(@class,'highcharts-name-" + countryNames.get(j) +  "')]"))
                                .getAttribute("fill")));
                        System.out.println(colours.get(3));
                        return false;
                    }
                }
                else{
                    System.out.println(countryNames.get(j)); //shows countries that are not in the map
                    //note: korea is inspected as south-korea and bahamas as the-bahamas in map hence can't be found
                }
            }
        }

        return true;
    }


}
