package specs.user.eventsTranscripts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.user.eventsTranscriptsPage.EventsTranscriptsPage;
import pageobjects.user.loginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by patrickp on 2016-09-15.
 */
public class EventsTranscriptsList extends AbstractSpec {


    @Before
    public void setup() {
        new LoginPage(driver)
                .loginUser()
                .accessSideNav()
                .selectEventsAndTranscriptsFromSideNav();
    }

    @Test
    public void canAddSymbol(){
         final String ticker = "YUM";
         final String exchange = "NYSE";
        EventsTranscriptsPage eventAndTranscriptPage = new EventsTranscriptsPage(driver);
        eventAndTranscriptPage.addSymbol(ticker,exchange);
        Assert.assertTrue("Security "+ticker +" was not added",eventAndTranscriptPage.wasSecurityAdded(ticker));
    }
}
