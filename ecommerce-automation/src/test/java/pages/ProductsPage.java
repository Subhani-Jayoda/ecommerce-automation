package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * ProductsPage - Page Object for the SauceDemo products inventory page.
 * URL: https://www.saucedemo.com/inventory.html
 */
public class ProductsPage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(css = ".inventory_item")
    private List<WebElement> productItems;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    // ─── Constructor ──────────────────────────────────────────────────────────

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    // ─── Actions ──────────────────────────────────────────────────────────────

    /**
     * Sorts products using the dropdown.
     * @param sortOption "az" | "za" | "lohi" | "hilo"
     */
    public ProductsPage sortProductsBy(String sortOption) {
        Select select = new Select(sortDropdown);
        select.selectByValue(sortOption);
        log.info("Sorted products by: {}", sortOption);
        return this;
    }

    /**
     * Adds a product to cart by its exact name.
     */
    public ProductsPage addProductToCart(String productName) {
        for (WebElement item : productItems) {
            String name = item.findElement(By.className("inventory_item_name")).getText();
            if (name.equalsIgnoreCase(productName)) {
                WebElement addBtn = item.findElement(By.cssSelector("button[data-test^='add-to-cart']"));
                click(addBtn);
                log.info("Added product to cart: {}", productName);
                return this;
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    /**
     * Removes a product from cart by its exact name.
     */
    public ProductsPage removeProductFromCart(String productName) {
        for (WebElement item : productItems) {
            String name = item.findElement(By.className("inventory_item_name")).getText();
            if (name.equalsIgnoreCase(productName)) {
                WebElement removeBtn = item.findElement(By.cssSelector("button[data-test^='remove']"));
                click(removeBtn);
                log.info("Removed product from cart: {}", productName);
                return this;
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    /**
     * Opens a product detail page by clicking its name.
     */
    public ProductDetailPage openProduct(String productName) {
        for (WebElement item : productItems) {
            WebElement nameEl = item.findElement(By.className("inventory_item_name"));
            if (nameEl.getText().equalsIgnoreCase(productName)) {
                click(nameEl);
                log.info("Opened product detail page: {}", productName);
                return new ProductDetailPage(driver);
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    /**
     * Navigates to the shopping cart.
     */
    public CartPage goToCart() {
        click(cartIcon);
        log.info("Navigated to Cart page");
        return new CartPage(driver);
    }

    /**
     * Logs out from the application.
     */
    public LoginPage logout() {
        click(menuButton);
        click(logoutLink);
        log.info("Logged out successfully");
        return new LoginPage(driver);
    }

    // ─── Verifications ────────────────────────────────────────────────────────

    public boolean isOnProductsPage() {
        return getCurrentUrl().contains("inventory");
    }

    public String getProductsPageTitle() {
        return getText(pageTitle);
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(getText(cartBadge));
        } catch (Exception e) {
            return 0;
        }
    }

    public int getTotalProductsDisplayed() {
        return productItems.size();
    }
}
