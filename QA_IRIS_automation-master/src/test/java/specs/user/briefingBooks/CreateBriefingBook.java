package specs.user.briefingBooks;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.briefingBooks.BriefingBookColumnType;
import pageobjects.user.briefingBooks.BriefingBookDetailsPage;
import pageobjects.user.briefingBooks.BriefingBookList;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by patrickp on 2016-09-14.
 */
public class CreateBriefingBook extends AbstractSpec {
    private final static String keyword = "**AUTOMATION**";
    private final static String briefingBookTitle =keyword+"New Briefing Book";

    @Before
    public void setUp() {
        new LoginPage(driver).loginUser()
                .accessSideNav()
                .selectBriefingBookFromSideNav();
    }

    @After
    public void cleanUp(){
        try {

            BriefingBookList briefingBookList = new BriefingBookDetailsPage(driver)
                    .leaveiframe()
                    .accessSideNavFromPage()
                    .selectBriefingBookFromSideNav();

            briefingBookList.switchToiframe();
            briefingBookList.searchFor(keyword);

            briefingBookList.deleteAllBriefingBooks();
        }
        catch(Exception e) {

            e.printStackTrace();

        }
    }

    @Test
    public void canCreateNewBriefingBook() {
        String briefingBookName = briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        String institution = "Fidelity"; //not entering in full Institution since Autocomplete does not open
        BriefingBookList briefingBookList = new BriefingBookList(driver).switchToiframe()
                .addNewBriefingBook()
                .enterTitle(briefingBookName)
                .enterInstitution(institution)
                .saveBriefingBook(briefingBookName)
                .waitForListToUpdate();

        Assert.assertThat("New briefing book was not created", briefingBookList.getBriefingBookList(), containsString(briefingBookName));

    }

    @Test
    public void canDeleteBriefingBookFromDetailsPage() {
        String briefingBookName =briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        String institution = "Vanguard"; //not entering in full Institution since Autocomplete does not open
        BriefingBookList briefingBookList = new BriefingBookList(driver).switchToiframe()
                .addNewBriefingBook()
                .enterTitle(briefingBookName)
                .enterInstitution(institution)
                .saveBriefingBook(briefingBookName)
                .waitForListToUpdate();
        Assert.assertThat("New briefing book was not created", briefingBookList.getBriefingBookList(), containsString(briefingBookName));
        briefingBookList.viewNewBriefingBook()
                .deleteBriefingBookFromDetailsPage();

        Assert.assertThat("Briefing book still appears in the list", briefingBookList.getBriefingBookList(), not(containsString(briefingBookName)));
    }

    @Test
    public void canDeleteBriefingBookFromMainPage(){
        String briefingBookName = briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        String institution = "Vanguard"; //Entering in the full name will make the The Vanguard Group, Inc. disappear for some reason
        BriefingBookList briefingBookList = new BriefingBookList(driver).switchToiframe()
                .addNewBriefingBook()
                .enterTitle(briefingBookName)
                .enterInstitution(institution)
                .saveBriefingBook(briefingBookName)
                .waitForListToUpdate();
        Assert.assertThat("New briefing book was not created", briefingBookList.getBriefingBookList(), containsString(briefingBookName));
        briefingBookList.deleteNewBriefingBook();

        Assert.assertThat("Briefing book still appears in the list", briefingBookList.getBriefingBookList(), not(containsString(briefingBookName)));
    }

    @Test
    public void canAddInstitutionToBriefingBook(){ //This Function adds one Institution on the Create Briefing Book Modal
        String briefingBookName = briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        String institutionAdded = "Vanguard";
        String institutionDefault= "Fidelity Management"; //default is needed because at least one Attendee is required to Create Briefing Book
        //incomplete names since result does not appear when full name is entered in

        BriefingBookDetailsPage briefingBookDetailsPage = new BriefingBookList(driver)
                .switchToiframe()
                .addNewBriefingBook()
                .enterTitle(briefingBookName)
                .enterInstitution(institutionDefault) // Adds Institution on Create Briefing Book Modal r
                .saveBriefingBook(briefingBookName)
                .viewNewBriefingBook()
                .addInstitution(institutionAdded);//Adds Institution on Add to Briefing Book Modal

        institutionAdded="The Vanguard Group, Inc."; //changes name so that the exact String can be compared

        Assert.assertTrue("Institution was not successfully added to the Briefing Book!", briefingBookDetailsPage.doesEntityExist(institutionAdded));
    }

    @Test
    public void canAddFundToBriefingBook(){
        String briefingBookName = briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        String fundDefault = "Treasury Master Fund";
        String fundAdded = "Canada Pension Plan";
        BriefingBookDetailsPage briefingBookDetailsPage = new BriefingBookList(driver)
                .switchToiframe()
                .addNewBriefingBook()
                .enterTitle(briefingBookName)
                .enterFund(fundDefault)
                .saveBriefingBook(briefingBookName)
                .viewNewBriefingBook()
                .addFund(fundAdded);

        Assert.assertTrue("New Fund is not listed in the briefing book", briefingBookDetailsPage.doesEntityExist(fundAdded));
    }

    @Test
    public void canAddContactToBriefingBook(){
        String briefingBookName = briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        String contactDefault = "Samuel Stursberg";
        String contactAdded = "Eugene Wu";
        BriefingBookDetailsPage briefingBookDetailsPage = new BriefingBookList(driver)
                .switchToiframe()
                .addNewBriefingBook()
                .enterTitle(briefingBookName)
                .enterContact(contactDefault)
                .saveBriefingBook(briefingBookName)
                .viewNewBriefingBook()
                .addContact(contactAdded);
        contactAdded+=" - Managing Partner";
        Assert.assertTrue("Contact is not listed in the briefing book", briefingBookDetailsPage.doesEntityExist(contactAdded));
    }

    @Test
    // This test relies on the existence of a briefing book called "Search Test - DO NOT DELETE" in order to pass
    public void canSearchForBriefingBook(){
        String briefingBookName = "Search Test - DO NOT DELETE"; // should be one exact match
        // performing search for which there should be one result and checking that result contains search term
        BriefingBookList briefingBookList = new BriefingBookList(driver)
                .switchToiframe()
                .searchFor(briefingBookName);
        Assert.assertThat("Search for known briefing book fails", briefingBookList.getBriefingBookList(), containsString(briefingBookName));
    }

    @Test
    public void searchingForUnknownKeywordReturnsNoResults(){
        String randomSearchTerm = "asdsfgdhfasdfb"; // should be no matches

        // performing search for which there should be no results
        BriefingBookList briefingBookList = new BriefingBookList(driver)
                .switchToiframe()
                .searchFor(randomSearchTerm);

        Assert.assertFalse("Results appear for invalid search.", briefingBookList.briefingBooksAreDisplayed());
    }

    @Test
    public void canReorderTearSheets(){

        String briefingBookName = briefingBookTitle + RandomStringUtils.randomAlphanumeric(6);
        //Autofill only works in Automation when the full Institution isn't listed, removed some text from each Institution
        String[] institutions = {"Vanguard Group", "Fidelity Capital Investors, Inc", "Dodge & Co", "Suez Venture", "HSBC Guyerzeller Bank"};
        // creating and opening blank briefing book
        BriefingBookDetailsPage briefingBookDetailsPage = new BriefingBookList(driver).switchToiframe()
                .addNewBriefingBook()
                .enterTitle(briefingBookName)
                .enterInstitution (institutions[0])  //Since mandatory to have at least one Attendee present when creating a Briefing Book
                .saveBriefingBook(briefingBookName)
                .viewNewBriefingBook();
        //adding institutions to briefing book
        for (int x=1;x<5;x++){ //the first element is skipped since it was added by default

            briefingBookDetailsPage.addInstitution(institutions[x]);
        }

        // clicking edit, dragging last item to top of list, clicking save, refreshing page, and checking that it is now the first item
        String lastEntity = briefingBookDetailsPage.getEntity(3);
        briefingBookDetailsPage.reorderEntityToBeginning(3);
        Assert.assertEquals("Reordered entity is not first", lastEntity, briefingBookDetailsPage.getEntity(0));

    }

    @Test
    public void briefingBooksSortedByTitle(){ //Sorts in Alphabetic Order
        BriefingBookList list = new BriefingBookList(driver)
                .switchToiframe();

        list.clickHeader(BriefingBookColumnType.TITLE);

        Assert.assertTrue("Titles are not sorted alphabetically",list.isSortedBy(BriefingBookColumnType.TITLE));

    }

    @Test
    public void briefingBooksSortedByAuthor(){ //Sorts in Alphabetic Order
        BriefingBookList list = new BriefingBookList(driver).switchToiframe();
        list.clickHeader(BriefingBookColumnType.AUTHOR);
        Assert.assertTrue("Authors are not sorted alphabetically",list.isSortedBy(BriefingBookColumnType.AUTHOR));

    }

    @Test
    public void briefingBooksSortedByCreated(){ //Sorts in Chronological Order
        BriefingBookList list = new BriefingBookList(driver).switchToiframe();
        list.clickHeader(BriefingBookColumnType.CREATED);
        Assert.assertTrue("Created dates not sorted correctly",list.isSortedBy(BriefingBookColumnType.CREATED));


    }

    @Test
    public void briefingBooksSortedByLastUpdated(){ //Sorts in Chronological Order
        BriefingBookList list = new BriefingBookList(driver).switchToiframe();
        list.clickHeader(BriefingBookColumnType.LAST_UPDATED);
        Assert.assertTrue("Last Updated not sorted correctly",list.isSortedBy(BriefingBookColumnType.LAST_UPDATED));

    }
}
