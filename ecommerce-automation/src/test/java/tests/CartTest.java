package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductsPage;
import utils.ConfigReader;

/**
 * CartTest - Test cases for Shopping Cart functionality.
 *
 * Test Cases:
 *   TC_CART_001 - Add single product to cart
 *   TC_CART_002 - Add multiple products to cart
 *   TC_CART_003 - Remove product from cart
 *   TC_CART_004 - Verify cart badge count updates
 *   TC_CART_005 - Continue shopping from cart
 */
public class CartTest extends BaseTest {

    // ──────────────────────────────────────────────────────────────────────────
    // TC_CART_001 - Add Single Product
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 1,
          description = "TC_CART_001 - Verify single product can be added to cart")
    public void testAddSingleProductToCart() {
        log.info("Running: TC_CART_001 - Add Single Product to Cart");

        String product = ConfigReader.getProductName();
        ProductsPage products = performLogin();
        products.addProductToCart(product);

        CartPage cart = products.goToCart();

        Assert.assertTrue(cart.isOnCartPage(),
                "Should navigate to cart page");
        Assert.assertEquals(cart.getCartItemCount(), 1,
                "Cart should contain exactly 1 item");
        Assert.assertTrue(cart.isItemInCart(product),
                "The added product should be in the cart");

        log.info("TC_CART_001 PASSED | Product in cart: {}", product);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_CART_002 - Add Multiple Products
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 2,
          description = "TC_CART_002 - Verify multiple products can be added to cart")
    public void testAddMultipleProductsToCart() {
        log.info("Running: TC_CART_002 - Add Multiple Products to Cart");

        ProductsPage products = performLogin();
        products.addProductToCart("Sauce Labs Backpack");
        products.addProductToCart("Sauce Labs Bike Light");

        Assert.assertEquals(products.getCartItemCount(), 2,
                "Cart badge should reflect 2 items");

        CartPage cart = products.goToCart();
        Assert.assertEquals(cart.getCartItemCount(), 2,
                "Cart page should show 2 items");

        log.info("TC_CART_002 PASSED");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_CART_003 - Remove Product from Cart
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 3,
          description = "TC_CART_003 - Verify product can be removed from cart")
    public void testRemoveProductFromCart() {
        log.info("Running: TC_CART_003 - Remove Product from Cart");

        String product = ConfigReader.getProductName();
        ProductsPage products = performLogin();
        products.addProductToCart(product);

        CartPage cart = products.goToCart();
        Assert.assertEquals(cart.getCartItemCount(), 1, "Should have 1 item before removal");

        cart.removeItem(product);

        Assert.assertEquals(cart.getCartItemCount(), 0,
                "Cart should be empty after removal");
        Assert.assertFalse(cart.isItemInCart(product),
                "Removed product should no longer be in cart");

        log.info("TC_CART_003 PASSED");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_CART_004 - Cart Badge Count
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 4,
          description = "TC_CART_004 - Verify cart badge count updates correctly")
    public void testCartBadgeCount() {
        log.info("Running: TC_CART_004 - Cart Badge Count");

        ProductsPage products = performLogin();

        Assert.assertEquals(products.getCartItemCount(), 0,
                "Cart should be empty initially");

        products.addProductToCart("Sauce Labs Backpack");
        Assert.assertEquals(products.getCartItemCount(), 1,
                "Badge should show 1 after first addition");

        products.addProductToCart("Sauce Labs Bike Light");
        Assert.assertEquals(products.getCartItemCount(), 2,
                "Badge should show 2 after second addition");

        log.info("TC_CART_004 PASSED");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_CART_005 - Continue Shopping from Cart
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 5,
          description = "TC_CART_005 - Verify 'Continue Shopping' navigates back to products")
    public void testContinueShopping() {
        log.info("Running: TC_CART_005 - Continue Shopping from Cart");

        ProductsPage products = performLogin();
        products.addProductToCart(ConfigReader.getProductName());

        CartPage cart = products.goToCart();
        Assert.assertTrue(cart.isOnCartPage(), "Should be on cart page");

        ProductsPage backToProducts = cart.continueShopping();
        Assert.assertTrue(backToProducts.isOnProductsPage(),
                "Should return to Products page after clicking Continue Shopping");

        log.info("TC_CART_005 PASSED");
    }
}
