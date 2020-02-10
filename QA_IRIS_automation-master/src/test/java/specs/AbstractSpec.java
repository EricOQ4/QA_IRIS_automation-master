package specs;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeTest;
import util.BrowserStackCapability;
import util.BrowserType;
import util.EnvironmentType;


import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public abstract class AbstractSpec {

// IMPORTANT:
// Determines which environment the test suite will run on but can be overridden by command line
//------------------------------------------------------------------------------
    //private  final EnvironmentType DEFAULT_ENVIRONMENT = EnvironmentType.LOCAL;
    private  final EnvironmentType DEFAULT_ENVIRONMENT = EnvironmentType.STAGING;
//------------------------------------------------------------------------------

    private final EnvironmentType activeEnvironment = setupEnvironment();

    private final String BROWSER_STACK_URL = "http://jencampbell2:6jEURzbszfaWhLJc7XWx@hub.browserstack.com/wd/hub";
    private final String BUILD_ID = RandomStringUtils.randomAlphanumeric(6);
    public final long DEFAULT_TIMEOUT = 120L;
    private  URL desktopUrl;
    private  BrowserStackCapability browser;
    protected  WebDriver driver;
    private boolean setupIsDone = false;
    private final Logger LOG = Logger.getLogger(AbstractSpec.class.getName());

    @Rule
    public TestName testName = new TestName();

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
            case LOCAL:
            case LOCALADMIN:
                //setupLocalDriver();
                break;
            case DEVELOP:
            case STAGING:
                setupLocalDriver();
                break;
            case BETA:
            case PRODUCTION:
                //setupWebDriver();
                //setupLocalDriver();
                break;
        }
    }

    private void setupLocalDriver() {
//        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//        ChromeOptions options = new ChromeOptions();
//        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        //driver.manage().window().fullscreen();
        //driver.manage().window().setSize(new Dimension(1400, 1400));
        driver.manage().window().maximize();
        driver.get(desktopUrl.toString());
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
        //driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        //driver.manage().window().setSize(new Dimension(1400, 1400));

        driver.get(desktopUrl.toString());
    }

    @After
    public void teardownWebDriver() throws Exception {
        String testMethodName = testName.getMethodName();

        if (getActiveEnvironment() != EnvironmentType.LOCAL) {
             driver.quit();
            System.out.println(testMethodName + " - " + "complete");
        }
    }

    public EnvironmentType getActiveEnvironment() {
        return activeEnvironment;
    }

    private EnvironmentType setupEnvironment () {
        String overrideEnvironment = System.getProperty("q4inc.specs");
        if (overrideEnvironment != null) {
            return EnvironmentType.valueOf(overrideEnvironment);
        } else {
            return DEFAULT_ENVIRONMENT;
        }
    }
}
