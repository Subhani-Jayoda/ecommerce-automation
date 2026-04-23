package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.ProductsPage;
import utils.ConfigReader;

/**
 * E2ETest - End-to-End smoke test covering the complete user journey.
 *
 * Flow:
 *   Login → Browse Products → Add to Cart → Checkout → Confirm Order → Logout
 */
public class E2ETest extends BaseTest {

    @Test(priority = 1,
          description = "E2E_001 - Full user journey: Login → Add to Cart → Checkout → Confirm → Logout")
    public void testCompleteUserJourney() {
        log.info("===========================================");
        log.info("Running: E2E_001 - Full User Journey");
        log.info("===========================================");

        // ── 1. Login ──────────────────────────────────────────────────────────
        log.info("Step 1: Login");
        ProductsPage products = performLogin();
        Assert.assertTrue(products.isOnProductsPage(), "[FAIL] Login did not redirect to Products page");
        log.info("✅ Step 1 PASSED - Logged in successfully");

        // ── 2. Browse and Add Product ─────────────────────────────────────────
        log.info("Step 2: Add product to cart");
        String productName = ConfigReader.getProductName();
        products.addProductToCart(productName);
        Assert.assertEquals(products.getCartItemCount(), 1,
                "[FAIL] Cart badge should show 1");
        log.info("✅ Step 2 PASSED - Product added: {}", productName);

        // ── 3. View Cart ──────────────────────────────────────────────────────
        log.info("Step 3: View cart");
        CartPage cart = products.goToCart();
        Assert.assertTrue(cart.isOnCartPage(), "[FAIL] Not on cart page");
        Assert.assertTrue(cart.isItemInCart(productName),
                "[FAIL] Product not in cart: " + productName);
        log.info("✅ Step 3 PASSED - Product verified in cart");

        // ── 4. Checkout – Step 1 (Shipping Info) ─────────────────────────────
        log.info("Step 4: Checkout - Shipping info");
        CheckoutPage checkout = cart.proceedToCheckout();
        Assert.assertTrue(checkout.isOnCheckoutStep1(),
                "[FAIL] Not on Checkout Step 1");

        checkout.fillShippingInfo(
                ConfigReader.getFirstName(),
                ConfigReader.getLastName(),
                ConfigReader.getZipCode()
        ).clickContinue();

        Assert.assertTrue(checkout.isOnCheckoutStep2(),
                "[FAIL] Did not advance to Checkout Step 2");
        log.info("✅ Step 4 PASSED - Shipping info submitted");

        // ── 5. Checkout – Step 2 (Review & Finish) ───────────────────────────
        log.info("Step 5: Order review and finish");
        String total = checkout.getTotal();
        Assert.assertFalse(total.isEmpty(), "[FAIL] Total amount should not be empty");
        log.info("   Order total: {}", total);

        checkout.clickFinish();
        Assert.assertTrue(checkout.isOrderConfirmed(),
                "[FAIL] Order was not confirmed");
        Assert.assertEquals(checkout.getConfirmationHeader(), "Thank you for your order!",
                "[FAIL] Confirmation header does not match");
        log.info("✅ Step 5 PASSED - Order confirmed!");

        // ── 6. Back to Home and Logout ────────────────────────────────────────
        log.info("Step 6: Back to products and logout");
        checkout.backToHome();
        products.logout();
        Assert.assertTrue(loginPage.isOnLoginPage(),
                "[FAIL] Should be on login page after logout");
        log.info("✅ Step 6 PASSED - Logged out");

        log.info("===========================================");
        log.info("🎉 E2E_001 PASSED - Full User Journey Complete!");
        log.info("===========================================");
    }
}
