package listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentReportManager;
import utils.ScreenshotUtils;

/**
 * TestListener - TestNG ITestListener implementation.
 * Hooks into test lifecycle to:
 *  - Create Extent Report test entries
 *  - Log PASS/FAIL/SKIP with details
 *  - Attach screenshots on failure
 *  - Flush reports after each suite
 */
public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        log.info("========================================");
        log.info("Test Suite Started: {}", context.getName());
        log.info("========================================");
        ExtentReportManager.initReports();
        ScreenshotUtils.createScreenshotDir();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        log.info("▶ Test Started: {}", testName);
        ExtentReportManager.createTest(testName, description);
        ExtentReportManager.getTest().log(Status.INFO, "Test started: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.info("✅ Test PASSED: {}", testName);
        ExtentReportManager.getTest().log(Status.PASS, "Test PASSED: " + testName);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.error("❌ Test FAILED: {} | Reason: {}", testName, result.getThrowable().getMessage());

        ExtentReportManager.getTest().log(Status.FAIL,
                "Test FAILED: " + testName + " | " + result.getThrowable().getMessage());

        // Capture screenshot and attach to report
        String screenshotPath = ScreenshotUtils.captureScreenshot(testName);
        if (screenshotPath != null) {
            try {
                ExtentReportManager.getTest().fail("Screenshot on failure:",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                log.info("Screenshot attached to report: {}", screenshotPath);
            } catch (Exception e) {
                log.warn("Could not attach screenshot to report: {}", e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.warn("⚠️ Test SKIPPED: {}", testName);
        ExtentReportManager.getTest().log(Status.SKIP, "Test SKIPPED: " + testName);
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("========================================");
        log.info("Test Suite Finished: {}", context.getName());
        log.info("Passed:  {}", context.getPassedTests().size());
        log.info("Failed:  {}", context.getFailedTests().size());
        log.info("Skipped: {}", context.getSkippedTests().size());
        log.info("========================================");
        ExtentReportManager.flushReports();
    }
}
