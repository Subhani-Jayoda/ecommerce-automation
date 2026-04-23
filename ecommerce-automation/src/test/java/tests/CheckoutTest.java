package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.ProductsPage;
import utils.ConfigReader;

/**
 * CheckoutTest - Test cases for the full Checkout flow.
 *
 * Test Cases:
 *   TC_CHK_001 - Full end-to-end successful checkout
 *   TC_CHK_002 - Checkout with missing first name
 *   TC_CHK_003 - Checkout with missing last name
 *   TC_CHK_004 - Checkout with missing postal code
 *   TC_CHK_005 - Verify order summary on Step 2
 *   TC_CHK_006 - Cancel checkout returns to cart
 */
public class CheckoutTest extends BaseTest {

    // ──────────────────────────────────────────────────────────────────────────
    // TC_CHK_001 - Full E2E Checkout
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 1,
          description = "TC_CHK_001 - Verify full end-to-end checkout flow completes successfully")
    public void testFullCheckoutFlow() {
        log.info("Running: TC_CHK_001 - Full E2E Checkout Flow");

        // Step 1: Login
        ProductsPage products = performLogin();
        Assert.assertTrue(products.isOnProductsPage(), "Should be on Products page after login");

        // Step 2: Add product to cart
        products.addProductToCart(ConfigReader.getProductName());
        Assert.assertEquals(products.getCartItemCount(), 1, "Cart should show 1 item");

        // Step 3: Go to Cart
        CartPage cart = products.goToCart();
        Assert.assertTrue(cart.isItemInCart(ConfigReader.getProductName()),
                "Product should be in cart");

        // Step 4: Proceed to Checkout
        CheckoutPage checkout = cart.proceedToCheckout();
        Assert.assertTrue(checkout.isOnCheckoutStep1(),
                "Should be on Checkout Step 1");

        // Step 5: Fill shipping info
        checkout.fillShippingInfo(
                ConfigReader.getFirstName(),
                ConfigReader.getLastName(),
                ConfigReader.getZipCode()
        ).clickContinue();

        Assert.assertTrue(checkout.isOnCheckoutStep2(),
                "Should advance to Checkout Step 2 (Overview)");

        // Step 6: Verify order summary is shown
        Assert.assertFalse(checkout.getSubtotal().isEmpty(), "Subtotal should be displayed");
        Assert.assertFalse(checkout.getTotal().isEmpty(),    "Total should be displayed");

        // Step 7: Finish
        checkout.clickFinish();
        Assert.assertTrue(checkout.isOrderConfirmed(),
                "Should land on order confirmation page");

        // Step 8: Verify confirmation message
        Assert.assertEquals(checkout.getConfirmationHeader(), "Thank you for your order!",
                "Confirmation header mismatch");

        log.info("TC_CHK_001 PASSED | Order placed successfully!");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_CHK_002 - Missing First Name
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 2,
          description = "TC_CHK_002 - Verify error when first name is missing at checkout")
    public void testCheckoutMissingFirstName() {
        log.info("Running: TC_CHK_002 - Missing First Name");

        ProductsPage products = performLogin();
        products.addProductToCart(ConfigReader.getProductName());

        CheckoutPage checkout = products.goToCart().proceedToCheckout();

        checkout.enterLastName(ConfigReader.getLastName())
                .enterPostalCode(ConfigReader.getZipCode())
                .clickContinue();

        Assert.assertTrue(checkout.isErrorMessageDisplayed(),
                "Error should appear when first name is missing");
        Assert.assertTrue(checkout.getErrorMessageText().contains("First Name"),
                "Error should mention First Name");

        log.info("TC_CHK_002 PASSED");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_CHK_003 - Missing Last Name
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 3,
          description = "TC_CHK_003 - Verify error when last name is missing at checkout")
    public void testCheckoutMissingLastName() {
        log.info("Running: TC_CHK_003 - Missing Last Name");

        ProductsPage products = performLogin();
        products.addProductToCart(ConfigReader.getProductName());

        CheckoutPage checkout = products.goToCart().proceedToCheckout();

        checkout.enterFirstName(ConfigReader.getFirstName())
                .enterPostalCode(ConfigReader.getZipCode())
                .clickContinue();

        Assert.assertTrue(checkout.isErrorMessageDisplayed(),
                "Error should appear when last name is missing");
        Assert.assertTrue(checkout.getErrorMessageText().contains("Last Name"),
                "Error should mention Last Name");

        log.info("TC_CHK_003 PASSED");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_CHK_004 - Missing Postal Code
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 4,
          description = "TC_CHK_004 - Verify error when postal code is missing at checkout")
    public void testCheckoutMissingPostalCode() {
        log.info("Running: TC_CHK_004 - Missing Postal Code");

        ProductsPage products = performLogin();
        products.addProductToCart(ConfigReader.getProductName());

        CheckoutPage checkout = products.goToCart().proceedToCheckout();

        checkout.enterFirstName(ConfigReader.getFirstName())
                .enterLastName(ConfigReader.getLastName())
                .clickContinue();

        Assert.assertTrue(checkout.isErrorMessageDisplayed(),
                "Error should appear when postal code is missing");
        Assert.assertTrue(checkout.getErrorMessageText().contains("Postal Code"),
                "Error should mention Postal Code");

        log.info("TC_CHK_004 PASSED");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_CHK_005 - Order Summary on Step 2
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 5,
          description = "TC_CHK_005 - Verify order summary details are shown on Step 2")
    public void testOrderSummaryOnStep2() {
        log.info("Running: TC_CHK_005 - Order Summary Step 2");

        ProductsPage products = performLogin();
        products.addProductToCart(ConfigReader.getProductName());

        CheckoutPage checkout = products.goToCart().proceedToCheckout();

        checkout.fillShippingInfo(
                ConfigReader.getFirstName(),
                ConfigReader.getLastName(),
                ConfigReader.getZipCode()
        ).clickContinue();

        Assert.assertTrue(checkout.isOnCheckoutStep2(), "Should be on Step 2");
        Assert.assertTrue(checkout.getSubtotal().contains("$"),
                "Subtotal should contain '$'");
        Assert.assertTrue(checkout.getTax().contains("$"),
                "Tax should contain '$'");
        Assert.assertTrue(checkout.getTotal().contains("$"),
                "Total should contain '$'");

        log.info("TC_CHK_005 PASSED | Total: {}", checkout.getTotal());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_CHK_006 - Cancel Checkout
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 6,
          description = "TC_CHK_006 - Verify canceling checkout returns to cart")
    public void testCancelCheckout() {
        log.info("Running: TC_CHK_006 - Cancel Checkout");

        ProductsPage products = performLogin();
        products.addProductToCart(ConfigReader.getProductName());

        CheckoutPage checkout = products.goToCart().proceedToCheckout();
        Assert.assertTrue(checkout.isOnCheckoutStep1(), "Should be on Step 1");

        CartPage cart = checkout.clickCancel();
        Assert.assertTrue(cart.isOnCartPage(),
                "Canceling checkout should return to Cart page");

        log.info("TC_CHK_006 PASSED");
    }
}
