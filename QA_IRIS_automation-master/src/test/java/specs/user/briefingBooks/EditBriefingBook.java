package specs.user.briefingBooks;

import org.junit.*;
import pageobjects.user.briefingBooks.BriefingBookDetailsPage;
import pageobjects.user.briefingBooks.BriefingBookList;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by noelc on 2016-11-23.
 */
public class EditBriefingBook extends AbstractSpec {
    private final static String keyword = "**AUTOMATION**";
    private  static final String nightlyBook = keyword+" nightlyTestingBriefingBook";
    private final String fund = "Canada Pension Plan";
    private final String contact = "Samuel Stursberg";
    private final String institution = "Fidelity Capital Investors, Inc.";
    private static boolean setupIsDone =false;


    @Before
    public void setupTest(){

        String inputInstitution="Fidelity Capital Investors"; //input must not be full name otherwise search result will not pop up

        if(setupIsDone) {
            new LoginPage(driver).loginUser()
                    .accessSideNav()
                    .selectBriefingBookFromSideNav();
        }
        else{
            new LoginPage(driver).loginUser()
                    .accessSideNav()
                    .selectBriefingBookFromSideNav();
            BriefingBookList briefingBookList = new BriefingBookList(driver)
                    .switchToiframe()
                    .addNewBriefingBook()
                    .enterTitle(nightlyBook)
                    .enterInstitution(inputInstitution) //Attendee is mandatory
                    .saveBriefingBook(nightlyBook);
            setupIsDone = true;
        }
    }

    @After
    public void cleanUp(){
        try {
            BriefingBookList briefingBookList = new BriefingBookDetailsPage(driver).accessSideNavFromPage().selectBriefingBookFromSideNav();
            briefingBookList.switchToiframe();
            briefingBookList.searchFor(keyword);
            briefingBookList.deleteAllBriefingBooks();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void canDeleteFundInBriefingBook(){
        BriefingBookList briefingBookList = new BriefingBookList(driver);
        BriefingBookDetailsPage briefingBookDetailsPage = briefingBookList
                .viewNewBriefingBook()
                .addFund(fund)
                .deleteEntity(fund);

        Assert.assertFalse(briefingBookDetailsPage.doesEntityExist(fund));
        setupIsDone=false; //so subsequent tests can receive the same setup
    }

    @Test  //Test Scenario starts with Fidelity Capital Investors Inc. already added as Attendees

    public void canBulkDeleteAttendees() { //Adds and Deletes Institutions, Funds and Contacts

        BriefingBookList briefingBookList = new BriefingBookList(driver);

        String [] attendees = {institution, "Folketrygdfondet", "Parus Fund Plc", "Geir Kirkeby", "Norges Bank Investment Management", "Boncura"};
        //the type array indicates the corresponding attendeeType of each attendees ex. attendees[0] is an Institution

        //some Institutions names have been shortened since for some reason AutoComplete does not seem to work on Automation when entering full name

        String [] type = {"Institution", "Institution", "Fund", "Contact", "Institution", "Fund" };

        BriefingBookDetailsPage briefingBookDetailsPage = briefingBookList
                .viewNewBriefingBook()
                .bulkAddAttendees(attendees,type);
        attendees[3]+=" - Portfolio Manager"+"\n"+"Folketrygdfondet"; //Job Title and Institution name must be added in order for the checkbox for the contact to be selected
        briefingBookDetailsPage.bulkDeleteAttendees(attendees , attendees.length); //deletes all Attendees

        Assert.assertEquals("There are still Attendees that were not deleted" ,false, briefingBookDetailsPage.doesAnyEntityExist(attendees));



    }

    @Test
    @Ignore

    public void canBulkAddAttendees() {


    }




}
