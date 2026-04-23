package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * WaitUtils - Provides explicit wait methods to avoid flaky tests.
 */
public class WaitUtils {

    private static final Logger log = LogManager.getLogger(WaitUtils.class);
    private static final int TIMEOUT = ConfigReader.getExplicitWait();

    private WaitUtils() {}

    private static WebDriverWait getWait() {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT));
    }

    public static WebElement waitForVisible(WebElement element) {
        log.debug("Waiting for element to be visible: {}", element);
        return getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForClickable(WebElement element) {
        log.debug("Waiting for element to be clickable: {}", element);
        return getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForVisible(By locator) {
        log.debug("Waiting for locator to be visible: {}", locator);
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static boolean waitForUrl(String urlFragment) {
        log.debug("Waiting for URL to contain: {}", urlFragment);
        return getWait().until(ExpectedConditions.urlContains(urlFragment));
    }

    public static boolean waitForTextPresent(WebElement element, String text) {
        log.debug("Waiting for text '{}' in element", text);
        return getWait().until(ExpectedConditions.textToBePresentInElement(element, text));
    }
}
