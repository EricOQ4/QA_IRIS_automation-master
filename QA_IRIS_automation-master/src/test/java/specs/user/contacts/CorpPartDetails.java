package specs.user.contacts;

import org.junit.*;
import pageobjects.user.contactPage.ContactPage;
import pageobjects.user.contactPage.CorpPartDetailsPage;
import specs.AbstractSpec;

import org.apache.commons.lang.RandomStringUtils;
import pageobjects.user.loginPage.LoginPage;

/**
 * Created by ShardulB on 05-22-2019
 */

public class CorpPartDetails extends AbstractSpec {

    //NOTE: IF YOU CHANGE THE NAME OF THE CP, YOU MUST UPDATE IT IN CONTACT PAGE!
    private static String corpPartFirstName = "Automated_" + RandomStringUtils.randomAlphanumeric(6);
    private static String corpPartLastName = "Corporate P.";
    private static String corpPartNickName = "Auto";
    private static String corpPartJobTitle = "Manager";
    private static String corpPartPhoneNumber = "416-412-1234";
    private static String corpPartEmail = "corp@auto.com";

    //Create a new Corporate Participant and then travel to their Detail Page
    @Before
    public void setUp() {
        new LoginPage(driver)
                .loginUser()
                .accessSideNav()
                .selectContactsFromSideNav();

        new ContactPage(driver)
                .openCreateContactModal()
                .createNewCorpPart(
                corpPartFirstName,
                corpPartLastName,
                corpPartNickName,
                corpPartJobTitle,
                corpPartPhoneNumber,
                corpPartPhoneNumber,
                corpPartEmail)
                .searchForContact(corpPartFirstName + " " + corpPartLastName)
                .clickFirstCorpPart();
    }

    @After
    public void cleanUp() {
        deleteCP();
    }

    @Test
    public void logActivityWithCP() {

        String noteTitle = "Automated Note with Corporate Participant";
        String noteDescription = "This is an automated note. What's up?";
        String noteTag = "getmerky";
        String location = "Yeerongpilly, QL, Australia";
        String contact = "Aaron Staines";

        new CorpPartDetailsPage(driver)
                .menuLogActivity()
                .enterNoteDetails(noteTitle, noteDescription, noteTag)
                .enterLocation(location)
                .linkNoteToContact(contact)
                .saveCorpPartActivity();
        CorpPartDetailsPage corpPart = new CorpPartDetailsPage(driver);

        Assert.assertEquals("Activity Titles do not match", corpPart.searchForActivity(noteTitle).getActivityTitleByRow(0), noteTitle);
        Assert.assertEquals("Activity Contact does not match", corpPart.searchForActivity(noteTitle).getActivityContactByRow(0), contact);
        Assert.assertEquals("Activity Tag does not match", corpPart.searchForActivity(noteTitle).getActivityTagByRow(0), "#" + noteTag);
        Assert.assertEquals("Activity Type does not match", corpPart.searchForActivity(noteTitle).getActivityTypeByRow(0), "q4i-note-4pt");
        Assert.assertEquals("Activity Type does not match", corpPart.searchForActivity(noteTitle).getActivityLocationByRow(0), location);

    }

    //Edit the CP using the pencil icon on the Corporate Participant Page
    @Test
    public void editCP() {

        String firstName= "Modified";
        String lastName = "Automated";
        String nickName = "nick";
        String jobTitle = "Manager";
        String phone = "123-145-4214";
        String email = "new@corpPartEmail.com";

        CorpPartDetailsPage corpPart = new CorpPartDetailsPage(driver)
                .editCorpPart()
                .fillInAllCPFields(firstName, lastName, nickName, jobTitle, phone, phone, email)
                .saveContact();

        Assert.assertEquals("Corporate Participant name wasn't modified correctly!",
                corpPart.getName(), firstName + " " + lastName);
        Assert.assertEquals("Corporate Participant corpPartNickName wasn't modified correctly!",
                corpPart.getNickName(), "Nickname - " + nickName);
        Assert.assertEquals("Corporate Participant job title wasn't modified correctly!",
                corpPart.getJobTitle(), jobTitle);
        Assert.assertEquals("Corporate Participant primary phone number wasn't modified correctly!",
                corpPart.getPrimaryPhone(), phone);
        Assert.assertEquals("Corporate Participant secondary phone number wasn't modified correctly!",
                corpPart.getSecondaryPhone(), phone);
        Assert.assertEquals("Corporate Participant corpPartEmail wasn't modified correctly!",
                corpPart.getEmail(), email);


    }

    @Test
    public void editCPWithoutFields() {

        String firstName= "Modified";
        String lastName = "Automated";
        String nickName = "";
        String jobTitle = "";
        String phone = "";
        String email = "new@corpPartEmail.com";

        CorpPartDetailsPage corpPart = new CorpPartDetailsPage(driver)
                .editCorpPart()
                .fillInAllCPFields(firstName, lastName, nickName, jobTitle, phone, phone, email)
                .saveContact();

        Assert.assertEquals("Corporate Participant name wasn't modified correctly!", firstName + " " + lastName, corpPart.getName());
        Assert.assertEquals("Corporate Participant corpPartNickName wasn't modified correctly!", "Nickname - " + nickName, corpPart.getNickName());
        Assert.assertEquals("Corporate Participant job title wasn't modified correctly!", jobTitle, corpPart.getJobTitle());
        Assert.assertEquals("Corporate Participant primary phone number wasn't modified correctly!", phone,  corpPart.getPrimaryPhone());
        Assert.assertEquals("Corporate Participant secondary phone number wasn't modified correctly!", phone, corpPart.getSecondaryPhone());
        Assert.assertEquals("Corporate Participant corpPartEmail wasn't modified correctly!", email, corpPart.getEmail());
    }

    @Test
    public void editCPThenCancel() {

        String firstName= "Modified";
        String lastName = "Automated";
        String nickName = "";
        String jobTitle = "";
        String phone = "";
        String email = "new@corpPartEmail.com";

        CorpPartDetailsPage corpPart = new CorpPartDetailsPage(driver)
                .editCorpPart()
                .fillInAllCPFields(firstName, lastName, nickName, jobTitle, phone, phone, email)
                .cancelSaveContact();

        Assert.assertEquals("Corporate Participant name wasn't modified correctly!",
                corpPartFirstName + " " + corpPartLastName, corpPart.getName());
        Assert.assertEquals("Corporate Participant corpPartNickName wasn't modified correctly!",
                "Nickname - " + corpPartNickName, corpPart.getNickName());
        Assert.assertEquals("Corporate Participant job title wasn't modified correctly!",
                corpPartJobTitle, corpPart.getJobTitle());
        Assert.assertEquals("Corporate Participant primary phone number wasn't modified correctly!",
                corpPartPhoneNumber, corpPart.getPrimaryPhone());
        Assert.assertEquals("Corporate Participant secondary phone number wasn't modified correctly!",
                corpPartPhoneNumber, corpPart.getSecondaryPhone());
        Assert.assertEquals("Corporate Participant corpPartEmail wasn't modified correctly!",
               corpPartEmail,  corpPart.getEmail());
    }

    @Test
    public void deleteCP() {

        CorpPartDetailsPage deadCorpPart = new CorpPartDetailsPage(driver);
        deadCorpPart.deleteCorpPart();

    }
}
