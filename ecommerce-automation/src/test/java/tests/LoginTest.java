package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductsPage;
import utils.ConfigReader;

/**
 * LoginTest - Test cases for Login functionality.
 *
 * Test Cases:
 *   TC_LOGIN_001 - Valid login with correct credentials
 *   TC_LOGIN_002 - Invalid login with wrong password
 *   TC_LOGIN_003 - Login with empty username
 *   TC_LOGIN_004 - Login with empty password
 *   TC_LOGIN_005 - Login with locked out user
 *   TC_LOGIN_006 - Successful logout
 */
public class LoginTest extends BaseTest {

    // ──────────────────────────────────────────────────────────────────────────
    // TC_LOGIN_001 - Valid Login
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 1,
          description = "TC_LOGIN_001 - Verify successful login with valid credentials")
    public void testValidLogin() {
        log.info("Running: TC_LOGIN_001 - Valid Login");

        ProductsPage products = loginPage.login(
                ConfigReader.getValidUsername(),
                ConfigReader.getValidPassword()
        );

        Assert.assertTrue(products.isOnProductsPage(),
                "User should be redirected to Products page after login");
        Assert.assertEquals(products.getProductsPageTitle(), "Products",
                "Page title should be 'Products'");

        log.info("TC_LOGIN_001 PASSED");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_LOGIN_002 - Invalid Password
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 2,
          description = "TC_LOGIN_002 - Verify error message on invalid password")
    public void testInvalidPassword() {
        log.info("Running: TC_LOGIN_002 - Invalid Password");

        loginPage.enterUsername(ConfigReader.getValidUsername())
                 .enterPassword("wrongpassword")
                 .clickLoginExpectingError();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for invalid password");
        Assert.assertTrue(loginPage.getErrorMessageText().contains("Username and password do not match"),
                "Error message content mismatch");

        log.info("TC_LOGIN_002 PASSED");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_LOGIN_003 - Empty Username
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 3,
          description = "TC_LOGIN_003 - Verify error message when username is empty")
    public void testEmptyUsername() {
        log.info("Running: TC_LOGIN_003 - Empty Username");

        loginPage.enterPassword(ConfigReader.getValidPassword())
                 .clickLoginExpectingError();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for empty username");
        Assert.assertTrue(loginPage.getErrorMessageText().contains("Username is required"),
                "Error should mention username is required");

        log.info("TC_LOGIN_003 PASSED");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_LOGIN_004 - Empty Password
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 4,
          description = "TC_LOGIN_004 - Verify error message when password is empty")
    public void testEmptyPassword() {
        log.info("Running: TC_LOGIN_004 - Empty Password");

        loginPage.enterUsername(ConfigReader.getValidUsername())
                 .clickLoginExpectingError();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for empty password");
        Assert.assertTrue(loginPage.getErrorMessageText().contains("Password is required"),
                "Error should mention password is required");

        log.info("TC_LOGIN_004 PASSED");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_LOGIN_005 - Locked Out User
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 5,
          description = "TC_LOGIN_005 - Verify error message for locked out user")
    public void testLockedOutUser() {
        log.info("Running: TC_LOGIN_005 - Locked Out User");

        loginPage.enterUsername(ConfigReader.getLockedUsername())
                 .enterPassword(ConfigReader.getLockedPassword())
                 .clickLoginExpectingError();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should appear for locked out user");
        Assert.assertTrue(loginPage.getErrorMessageText().contains("locked out"),
                "Error should mention user is locked out");

        log.info("TC_LOGIN_005 PASSED");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_LOGIN_006 - Logout
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 6,
          description = "TC_LOGIN_006 - Verify user can logout successfully")
    public void testLogout() {
        log.info("Running: TC_LOGIN_006 - Logout");

        ProductsPage products = performLogin();
        Assert.assertTrue(products.isOnProductsPage(), "Should be on Products page");

        products.logout();

        Assert.assertTrue(loginPage.isOnLoginPage(),
                "User should be redirected to Login page after logout");

        log.info("TC_LOGIN_006 PASSED");
    }
}
