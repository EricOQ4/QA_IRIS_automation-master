package util;

import org.apache.commons.io.FileUtils;
import org.im4java.core.CompareCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.StandardStream;
import org.openqa.selenium.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

/**
 * Created by philipsushkov on 2017-03-08.
 */

public class Functions {

        public static Properties ConnectToPropUI(String sPathSharedUIMap) throws IOException {
            Properties propUI;
            String currentDir;
            propUI = new Properties();
            currentDir = System.getProperty("user.dir") + "/src/test/java/specs/";
            propUI.load(new FileInputStream(currentDir + sPathSharedUIMap));
            return propUI;
        }

    public static boolean compareImages (String exp, String cur, String diff) {
        // This instance wraps the compare command
        CompareCmd compare = new CompareCmd();

        // For metric-output
        compare.setErrorConsumer(StandardStream.STDERR);
        IMOperation cmpOp = new IMOperation();
        // Set the compare metric
        cmpOp.metric("mae");

        // Add the expected image
        cmpOp.addImage(exp);

        // Add the current image
        cmpOp.addImage(cur);

        // This stores the difference
        cmpOp.addImage(diff);

        try {
            // Do the compare
            compare.run(cmpOp);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static String takeScreenshot (WebDriver driver, String sPath, String sShotName, String sPageName, long curThread) {
        String sPathPng = null;

        try {
            // Take screenshot and save it in source object
            File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

            // Define path where Screenshot will be saved
            sPathPng = sPath + sPageName + "/" + sShotName + "_" + curThread + ".png";

            //Copy the source file at the screenshot path
            FileUtils.copyFile(source,  new File(sPathPng));
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot:" + e.getMessage());
        } catch (WebDriverException wde) {
            System.out.println("Failed to capture screenshot:" + wde.getMessage());
        }

        return sPathPng;
    }

    public static void hideElement(WebElement e, WebDriver d) {
        ((JavascriptExecutor)d).executeScript("arguments[0].style.visibility='hidden'", e);
    }

    public static int GetResponseCode(String urlString) throws IOException {
        try {
            URL url = new URL(urlString);
            HttpURLConnection huc = (HttpURLConnection)url.openConnection();
            huc.setRequestMethod("GET");
            //System.out.println(huc.getContentLength());
            //huc.connect();

            //System.out.println(Integer.toString(huc.getContentLength()));
            //System.out.println(Integer.toString(huc.getInputStream().available()));

            int iResponseCode = huc.getResponseCode();
            huc.disconnect();

            return iResponseCode;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 404;
        }
    }

}
