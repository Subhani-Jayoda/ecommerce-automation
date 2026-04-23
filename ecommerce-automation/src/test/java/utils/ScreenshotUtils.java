package utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ScreenshotUtils - Captures and saves screenshots.
 * Returns the file path for use in Extent Reports.
 */
public class ScreenshotUtils {

    private static final Logger log = LogManager.getLogger(ScreenshotUtils.class);

    private ScreenshotUtils() {}

    /**
     * Takes a screenshot and saves it with a timestamped filename.
     *
     * @param testName Name of the test (used in filename)
     * @return Absolute path of the saved screenshot
     */
    public static String captureScreenshot(String testName) {
        WebDriver driver = DriverManager.getDriver();
        String timestamp   = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotDir = ConfigReader.getScreenshotDir();
        String fileName    = screenshotDir + "/" + testName + "_" + timestamp + ".png";

        try {
            File src  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(fileName);
            FileUtils.copyFile(src, dest);
            log.info("Screenshot saved: {}", fileName);
            return dest.getAbsolutePath();
        } catch (IOException e) {
            log.error("Failed to capture screenshot for test '{}': {}", testName, e.getMessage());
            return null;
        }
    }

    /**
     * Ensures the screenshots directory exists.
     */
    public static void createScreenshotDir() {
        File dir = new File(ConfigReader.getScreenshotDir());
        if (!dir.exists()) {
            dir.mkdirs();
            log.info("Screenshots directory created: {}", dir.getAbsolutePath());
        }
    }
}
