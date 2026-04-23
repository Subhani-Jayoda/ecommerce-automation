package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * ProductDetailPage - Page Object for individual product detail pages.
 */
public class ProductDetailPage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(className = "inventory_details_name")
    private WebElement productName;

    @FindBy(className = "inventory_details_desc")
    private WebElement productDescription;

    @FindBy(className = "inventory_details_price")
    private WebElement productPrice;

    @FindBy(css = "button[data-test^='add-to-cart']")
    private WebElement addToCartButton;

    @FindBy(css = "button[data-test^='remove']")
    private WebElement removeButton;

    @FindBy(id = "back-to-products")
    private WebElement backButton;

    // ─── Constructor ──────────────────────────────────────────────────────────

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    // ─── Actions ──────────────────────────────────────────────────────────────

    public ProductDetailPage addToCart() {
        click(addToCartButton);
        log.info("Added product to cart from detail page");
        return this;
    }

    public ProductDetailPage removeFromCart() {
        click(removeButton);
        log.info("Removed product from cart on detail page");
        return this;
    }

    public ProductsPage goBackToProducts() {
        click(backButton);
        log.info("Navigated back to products page");
        return new ProductsPage(driver);
    }

    // ─── Verifications ────────────────────────────────────────────────────────

    public String getProductName()        { return getText(productName); }
    public String getProductDescription() { return getText(productDescription); }
    public String getProductPrice()       { return getText(productPrice); }

    public boolean isAddToCartButtonDisplayed() { return isDisplayed(addToCartButton); }
    public boolean isRemoveButtonDisplayed()    { return isDisplayed(removeButton); }
}
