package pageobjects.user;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import pageobjects.AbstractPageObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Created by easong on 3/6/17. */

public class Calendar extends AbstractPageObject{

    private String startMonth;
    private String startDay;
    private String formattedStartDate;
    private String formattedEndDate;
    private String endMonth;
    private String endDay;

    public Calendar(WebDriver driver) {
        super(driver);
    }

    // these parameters change according to where the calendar tool appears on the web page
    public Calendar selectStartDate(By startTimeSelector, By previousMonthButton, By selectedMonth, By selectedDay) {
        waitForLoadingScreen();
        waitForElementToBeClickable(startTimeSelector);
        scrollToElement(startTimeSelector);
        findElement(startTimeSelector).click();
            // start time will always go back 5 months from the current month. This will adapt over time
            for (int i = 0; i < 6; i++)
            {
                scrollToElement(previousMonthButton);
                waitForElementToBeClickable(previousMonthButton).click();
            }
        // collects a string in format "SEPTEMBER 2016"
        scrollToElement(selectedMonth);
        startMonth = waitForElementToAppear(selectedMonth).getText();
        // collects a string in format "20"
        scrollToElement(selectedDay);
        startDay = waitForElementToAppear(selectedDay).getText();
        waitForElementToBeClickable(selectedDay).click();
        // calls method to convert 2 strings into format "MM/DD/YY"
        formattedStartDate = dateFormatConversion(startMonth, startDay);
        return this;

    }
    // these parameters change according to where the calendar tool appears on the web page
    public Calendar selectEndDate(By endTimeSelector, By nextMonthButton, By selectedMonth, By selectedDay) {

        scrollToElement(endTimeSelector);
        waitForElementToBeClickable(endTimeSelector).click();
        // end time will always go ahead 2 months from the current month
            for (int i = 0; i < 3; i++)
            {
                scrollToElement(nextMonthButton);
                findElement(nextMonthButton).click();
            }
        // collects a string in format "NOVEMBER 2016"
        scrollToElement(selectedMonth);
        endMonth = findElement(selectedMonth).getText();
        // collects a string in format "20"
        scrollToElement(selectedDay);
        endDay = findElement(selectedDay).getText();
        findElement(selectedDay).click();
        // calls method to convert 2 strings into format "MM/DD/YY"
        formattedEndDate = dateFormatConversion(endMonth, endDay);
        return this;
    }

    public void filter(By dateFilterButton) {
        // clicks the "go" button to initiate filtering
        findElement(dateFilterButton).click();
    }
    public void print() {
        // to keep track of which days were selected during nightly runs
        System.out.println("StartDate is " + startMonth + " " + startDay);
        System.out.println("EndDate is " + endMonth + " " + endDay);
    }

    public String dateFormatConversion(String monthYear, String day) {
        // converting StartMonth and endDay into format dd/mm//yy. This is needed for comparisons against the date in the chart
        String delims = "[ ]+";
        // separating startMonth string by spaces
        String[] tokens = monthYear.split(delims);
        // switch statement to convert Months from letters to numbers
        String month = tokens[0];
        switch(month) {
            case "JANUARY":
                month = "01";
                break;
            case "FEBRUARY":
                month = "02";
                break;
            case "MARCH":
                month = "03";
                break;
            case "APRIL":
                month = "04";
                break;
            case "MAY":
                month = "05";
                break;
            case "JUNE":
                month = "06";
                break;
            case "JULY":
                month = "07";
                break;
            case "AUGUST":
                month = "08";
                break;
            case "SEPTEMBER":
                month = "09";
                break;
            case "OCTOBER":
                month = "10";
                break;
            case "NOVEMBER":
                month = "11";
                break;
            case "DECEMBER":
                month = "12";
                break;
        }

        String year;
        // this collects the last 2 digits of the year string. "2017" becomes "17"
        year = tokens[1].substring(tokens[1].length() - 2);
        String formattedDate = month + "/" + day + "/" + year;

        return formattedDate;
    }

    public boolean latestDateWithinRange(String topItemDate) {
        // this method converts dates in the "MM/DD/YY" format into Epoch for easy comparison
        Date actualSelectedDate = null;
        Date actualCollectedDate = null;
        SimpleDateFormat partialYearFormat = new SimpleDateFormat("MM/dd/yy");

        try {
            actualSelectedDate = partialYearFormat.parse(formattedEndDate);
        }
        catch (ParseException pe) {
            System.out.println(pe.toString());
        }

        try {
            actualCollectedDate = partialYearFormat.parse(topItemDate);
        }
        catch (ParseException pe) {
            System.out.println(pe.toString());
        }

        //convert date to seconds since 1970
        long epochSelected = actualSelectedDate.getTime();
        long epochCollected = actualCollectedDate.getTime();

        return (epochCollected <= epochSelected);

    }

    public boolean EarliestDateWithinRange(String topItemDate) {
        // this method converts dates in the "MM/DD/YY" format into Epoch for easy comparison
        Date actualSelectedDate = null;
        Date actualCollectedDate = null;
        SimpleDateFormat partialYearFormat = new SimpleDateFormat("MM/dd/yy");

        try {
            actualSelectedDate = partialYearFormat.parse(formattedStartDate);
        }
        catch (ParseException pe) {
            System.out.println(pe.toString());
        }

        try {
            actualCollectedDate = partialYearFormat.parse(topItemDate);
        }
        catch (ParseException pe) {
            System.out.println(pe.toString());
        }

        //convert date to seconds since 1970
        long epochSelected = actualSelectedDate.getTime();
        long epochCollected = actualCollectedDate.getTime();

        return (epochCollected >= epochSelected);

    }

}
