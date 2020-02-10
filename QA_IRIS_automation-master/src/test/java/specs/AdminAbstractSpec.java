package specs;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.annotations.AfterSuite;
import util.BrowserStackCapability;
import util.BrowserType;
import util.EnvironmentType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by patrickp on 2016-09-21.
 */
public class AdminAbstractSpec {
    // IMPORTANT:
// Determines which environment the test suite will run on but can be overridden by command line
//------------------------------------------------------------------------------
    private static final EnvironmentType DEFAULT_ENVIRONMENT = EnvironmentType.LOCALADMIN;
//------------------------------------------------------------------------------

    private static final EnvironmentType activeEnvironment = setupEnvironment();

    private static final String BROWSER_STACK_URL = "http://jencampbell2:6jEURzbszfaWhLJc7XWx@hub.browserstack.com/wd/hub";
    private static final String BUILD_ID = RandomStringUtils.randomAlphanumeric(6);
    public static final long DEFAULT_TIMEOUT = 1L;

    private static URL desktopUrl;
    private static BrowserStackCapability browser;
    protected static WebDriver driver;
    private static boolean setupIsDone = false;
    private static final Logger LOG = Logger.getLogger(AbstractSpec.class.getName());
    private SessionId sessionId;
    private static File file;

    @Rule
    public TestName testName = new TestName();

    @AfterSuite
    public void cleanUp(){
        try {
            file = new File("session_file.txt");
            file.delete();
            file = new File("session_profile.txt");
            file.delete();
        }
        catch(Exception e){

        }
    }

    @Before
    public void init() throws Exception {
        if (!setupIsDone) {
            setupEnvironment();

            desktopUrl = new URL(activeEnvironment.getProtocol() + activeEnvironment.getHost());

            LOG.info("ENV URL: " + desktopUrl);

            browser = new BrowserStackCapability(Platform.WIN8, BrowserType.CHROME, null);
            //browser = new BrowserStackCapability(Platform.WIN8, BrowserType.OPERA, null);
            //browser = new BrowserStackCapability(Platform.WIN8, BrowserType.IE, null);
            //browser = new BrowserStackCapability(Platform.WIN8, BrowserType.FIREFOX, null);

            setupIsDone = true;
        }

        switch (getActiveEnvironment()) {
            case LOCALADMIN:
                setupLocalDriver();
                break;
            case DEVELOPADMIN:
            case STAGINGADMIN:
                setupWebDriver();
                break;
        }
        file = new File("session_file.txt");
        file.createNewFile();
        file = new File("session_profile.txt");
        file.createNewFile();
    }
private void setTokenId(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("session_file.txt"));
            String line;
            String session_id = "";
            FileReader fileReader = new FileReader(file);
            while ((line = br.readLine()) != null) {
                session_id += line;
            }
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String test = ((String) js.executeScript(String.format("return window.localStorage.setItem('%s','%s');", "id_token", session_id)));
        }
        catch(Exception e){

        }
}
private void setProfile(){
    try {
        BufferedReader br = new BufferedReader(new FileReader("session_profile.txt"));
        String line;
        String session_profile = "";
        while ((line = br.readLine()) != null) {
            session_profile += line;
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String test = ((String) js.executeScript(String.format("return window.localStorage.setItem('%s','%s');", "profile", session_profile)));
    }
    catch(Exception e){

    }

}
    public void setSessionVariables(){
    setProfile();
    setTokenId();

    }

    private void setupLocalDriver() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(1400, 1400));

        driver.get(desktopUrl.toString());
        setProfile();
        setTokenId();
        driver.navigate().refresh();


    }

    private void setupWebDriver() throws Exception {
        String testMethodName = testName.getMethodName();

        DesiredCapabilities capability = browser.toDesiredCapability();
        capability.setCapability("project", getActiveEnvironment().name());
        capability.setCapability("build", getActiveEnvironment().name() + " - " + browser.getPlatformType().name() + " " + browser.getBrowserType().getName() + " " + browser.getBrowserType().getLatestVersion()+ " - " + BUILD_ID);
        capability.setCapability("name", testMethodName);
        capability.setCapability("resolution","1920x1200");
        capability.setCapability("acceptSslCerts", "true");
        capability.setCapability("browserstack.video","false");
        capability.setCapability("browserstack.debug", "false");

        driver = new RemoteWebDriver(new URL(BROWSER_STACK_URL), capability);
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(1400, 1400));

        driver.get(desktopUrl.toString());

        setProfile();
        setTokenId();
        driver.navigate().refresh();

    }


    @After
    public void teardownWebDriver() {
        String testMethodName = testName.getMethodName();

        if (getActiveEnvironment() != EnvironmentType.LOCALADMIN) {
            driver.quit();
            System.out.println(testMethodName + " - " + "complete");
       }
    }

    public static EnvironmentType getActiveEnvironment() {
        return activeEnvironment;
    }

    public boolean hasLoggedIn(){
        File file = new File("session_file.txt");
        if (file.exists()) {
            if(file.length()>0){
                setSessionVariables();
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    private static EnvironmentType setupEnvironment () {
        String overrideEnvironment = System.getProperty("q4inc.specs");
        if (overrideEnvironment != null) {
            return EnvironmentType.valueOf(overrideEnvironment);
        } else {
            return DEFAULT_ENVIRONMENT;
        }
    }


}
