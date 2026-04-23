package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * LoginPage - Page Object for SauceDemo login page.
 * URL: https://www.saucedemo.com
 */
public class LoginPage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    // ─── Constructor ──────────────────────────────────────────────────────────

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ─── Actions ──────────────────────────────────────────────────────────────

    /**
     * Navigates to the login page.
     */
    public LoginPage navigateTo(String url) {
        driver.get(url);
        log.info("Navigated to: {}", url);
        return this;
    }

    /**
     * Enters username into the username field.
     */
    public LoginPage enterUsername(String username) {
        type(usernameField, username);
        log.info("Entered username: {}", username);
        return this;
    }

    /**
     * Enters password into the password field.
     */
    public LoginPage enterPassword(String password) {
        type(passwordField, password);
        log.info("Entered password: ****");
        return this;
    }

    /**
     * Clicks the Login button.
     */
    public ProductsPage clickLogin() {
        click(loginButton);
        log.info("Clicked Login button");
        return new ProductsPage(driver);
    }

    /**
     * Performs a full login flow.
     */
    public ProductsPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return clickLogin();
    }

    /**
     * Clicks login and stays on page (for negative tests).
     */
    public LoginPage clickLoginExpectingError() {
        click(loginButton);
        return this;
    }

    // ─── Verifications ────────────────────────────────────────────────────────

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessageText() {
        return getText(errorMessage);
    }

    public boolean isOnLoginPage() {
        return getCurrentUrl().equals("https://www.saucedemo.com/");
    }
}
