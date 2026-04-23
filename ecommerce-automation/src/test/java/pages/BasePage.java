package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtils;

/**
 * BasePage - Parent class for all Page Object classes.
 * Initializes PageFactory and provides common helper methods.
 */
public class BasePage {

    protected WebDriver driver;
    protected final Logger log = LogManager.getLogger(getClass());

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Clicks an element after waiting for it to be clickable.
     */
    protected void click(WebElement element) {
        WaitUtils.waitForClickable(element).click();
        log.debug("Clicked element: {}", element);
    }

    /**
     * Types text into a field after clearing it.
     */
    protected void type(WebElement element, String text) {
        WaitUtils.waitForVisible(element).clear();
        element.sendKeys(text);
        log.debug("Typed '{}' into element", text);
    }

    /**
     * Returns trimmed text of a visible element.
     */
    protected String getText(WebElement element) {
        return WaitUtils.waitForVisible(element).getText().trim();
    }

    /**
     * Checks whether an element is displayed.
     */
    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns the current page title.
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Returns the current URL.
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
