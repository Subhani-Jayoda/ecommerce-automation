package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReportManager - Manages the Extent Reports instance.
 * Uses ThreadLocal for ExtentTest to support parallel execution.
 */
public class ExtentReportManager {

    private static final Logger log = LogManager.getLogger(ExtentReportManager.class);
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    private ExtentReportManager() {}

    /**
     * Initializes ExtentReports with Spark reporter.
     */
    public static void initReports() {
        String reportDir  = ConfigReader.getReportDir();
        String timestamp  = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = reportDir + "/TestReport_" + timestamp + ".html";

        // Ensure reports dir exists
        new File(reportDir).mkdirs();

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("E-Commerce Automation Report");
        spark.config().setReportName("Selenium + TestNG + POM Test Results");
        spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Framework",    "Selenium + TestNG + POM");
        extent.setSystemInfo("Browser",      ConfigReader.getBrowser());
        extent.setSystemInfo("Base URL",     ConfigReader.getBaseUrl());
        extent.setSystemInfo("OS",           System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Author",       "QA Automation Engineer");

        log.info("Extent Reports initialized: {}", reportPath);
    }

    /**
     * Creates a new test entry in the report.
     */
    public static void createTest(String testName, String description) {
        ExtentTest test = extent.createTest(testName, description);
        extentTest.set(test);
    }

    /**
     * Returns the ExtentTest for the current thread.
     */
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    /**
     * Flushes (writes) the report to disk.
     */
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
            log.info("Extent Reports flushed successfully.");
        }
    }
}
