package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * CartPage - Page Object for the SauceDemo shopping cart page.
 * URL: https://www.saucedemo.com/cart.html
 */
public class CartPage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    // ─── Constructor ──────────────────────────────────────────────────────────

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // ─── Actions ──────────────────────────────────────────────────────────────

    /**
     * Removes a specific item from the cart by its name.
     */
    public CartPage removeItem(String productName) {
        for (WebElement item : cartItems) {
            String name = item.findElement(By.className("inventory_item_name")).getText();
            if (name.equalsIgnoreCase(productName)) {
                WebElement removeBtn = item.findElement(By.cssSelector("button[data-test^='remove']"));
                click(removeBtn);
                log.info("Removed item from cart: {}", productName);
                return this;
            }
        }
        throw new RuntimeException("Item not found in cart: " + productName);
    }

    /**
     * Proceeds to checkout.
     */
    public CheckoutPage proceedToCheckout() {
        click(checkoutButton);
        log.info("Clicked Checkout button");
        return new CheckoutPage(driver);
    }

    /**
     * Continues shopping (back to products).
     */
    public ProductsPage continueShopping() {
        click(continueShoppingButton);
        log.info("Clicked Continue Shopping button");
        return new ProductsPage(driver);
    }

    // ─── Verifications ────────────────────────────────────────────────────────

    public boolean isOnCartPage() {
        return getCurrentUrl().contains("cart");
    }

    public String getCartPageTitle() {
        return getText(pageTitle);
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public boolean isItemInCart(String productName) {
        return cartItems.stream().anyMatch(item -> {
            String name = item.findElement(By.className("inventory_item_name")).getText();
            return name.equalsIgnoreCase(productName);
        });
    }

    public String getItemPrice(String productName) {
        for (WebElement item : cartItems) {
            String name = item.findElement(By.className("inventory_item_name")).getText();
            if (name.equalsIgnoreCase(productName)) {
                return item.findElement(By.className("inventory_item_price")).getText();
            }
        }
        throw new RuntimeException("Item not found in cart: " + productName);
    }
}
