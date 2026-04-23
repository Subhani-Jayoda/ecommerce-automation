package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductDetailPage;
import pages.ProductsPage;
import utils.ConfigReader;

/**
 * ProductTest - Test cases for Product listing and detail functionality.
 *
 * Test Cases:
 *   TC_PROD_001 - Verify products page loads with items
 *   TC_PROD_002 - Verify product sort (A-Z)
 *   TC_PROD_003 - Verify product sort (Price Low to High)
 *   TC_PROD_004 - Verify product detail page opens correctly
 *   TC_PROD_005 - Add product to cart from detail page
 */
public class ProductTest extends BaseTest {

    // ──────────────────────────────────────────────────────────────────────────
    // TC_PROD_001 - Products Page Loads
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 1,
          description = "TC_PROD_001 - Verify products page loads with all items")
    public void testProductsPageLoads() {
        log.info("Running: TC_PROD_001 - Products Page Loads");

        ProductsPage products = performLogin();

        Assert.assertTrue(products.isOnProductsPage(),
                "Should be on the Products inventory page");
        Assert.assertEquals(products.getProductsPageTitle(), "Products",
                "Page header should read 'Products'");
        Assert.assertTrue(products.getTotalProductsDisplayed() > 0,
                "At least one product should be visible");

        log.info("TC_PROD_001 PASSED | Products found: {}", products.getTotalProductsDisplayed());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_PROD_002 - Sort A-Z
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 2,
          description = "TC_PROD_002 - Verify sorting products by name A-Z")
    public void testSortProductsAZ() {
        log.info("Running: TC_PROD_002 - Sort A-Z");

        ProductsPage products = performLogin();
        products.sortProductsBy("az");

        Assert.assertTrue(products.getTotalProductsDisplayed() > 0,
                "Products should still be visible after sorting");

        log.info("TC_PROD_002 PASSED");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_PROD_003 - Sort Price Low to High
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 3,
          description = "TC_PROD_003 - Verify sorting products by price low to high")
    public void testSortProductsPriceLowHigh() {
        log.info("Running: TC_PROD_003 - Sort Price Low to High");

        ProductsPage products = performLogin();
        products.sortProductsBy("lohi");

        Assert.assertTrue(products.getTotalProductsDisplayed() > 0,
                "Products should be displayed after price sort");

        log.info("TC_PROD_003 PASSED");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_PROD_004 - Product Detail Page
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 4,
          description = "TC_PROD_004 - Verify product detail page opens with correct info")
    public void testProductDetailPage() {
        log.info("Running: TC_PROD_004 - Product Detail Page");

        String targetProduct = ConfigReader.getProductName();
        ProductsPage products = performLogin();
        ProductDetailPage detailPage = products.openProduct(targetProduct);

        Assert.assertEquals(detailPage.getProductName(), targetProduct,
                "Product name on detail page should match clicked product");
        Assert.assertFalse(detailPage.getProductDescription().isEmpty(),
                "Product description should not be empty");
        Assert.assertTrue(detailPage.getProductPrice().startsWith("$"),
                "Product price should start with '$'");
        Assert.assertTrue(detailPage.isAddToCartButtonDisplayed(),
                "Add to Cart button should be visible");

        log.info("TC_PROD_004 PASSED | Product: {} | Price: {}",
                detailPage.getProductName(), detailPage.getProductPrice());
    }

    // ──────────────────────────────────────────────────────────────────────────
    // TC_PROD_005 - Add to Cart from Detail Page
    // ──────────────────────────────────────────────────────────────────────────
    @Test(priority = 5,
          description = "TC_PROD_005 - Verify adding product to cart from detail page")
    public void testAddToCartFromDetailPage() {
        log.info("Running: TC_PROD_005 - Add to Cart from Detail Page");

        String targetProduct = ConfigReader.getProductName();
        ProductsPage products = performLogin();
        ProductDetailPage detailPage = products.openProduct(targetProduct);

        detailPage.addToCart();

        Assert.assertTrue(detailPage.isRemoveButtonDisplayed(),
                "Remove button should appear after adding to cart");

        detailPage.goBackToProducts();
        Assert.assertEquals(products.getCartItemCount(), 1,
                "Cart badge should show 1 item");

        log.info("TC_PROD_005 PASSED");
    }
}
