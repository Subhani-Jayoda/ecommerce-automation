package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.*;
import utils.ConfigReader;
import utils.DriverManager;

/**
 * BaseTest - Parent class for all test classes.
 * Handles browser setup and teardown.
 * All tests extend this class.
 */
public class BaseTest {

    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    protected WebDriver driver;

    // Page Objects - initialized in @BeforeMethod
    protected LoginPage      loginPage;
    protected ProductsPage   productsPage;
    protected CartPage       cartPage;
    protected CheckoutPage   checkoutPage;

    @BeforeMethod
    public void setUp() {
        DriverManager.initDriver();
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.getBaseUrl());
        log.info("Browser launched. Navigated to: {}", ConfigReader.getBaseUrl());

        // Initialize all page objects
        loginPage    = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage     = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
        log.info("Browser closed.");
    }

    // ─── Helper: Perform login (reusable across tests) ──────────────────────

    protected ProductsPage performLogin() {
        return loginPage.login(
                ConfigReader.getValidUsername(),
                ConfigReader.getValidPassword()
        );
    }
}
