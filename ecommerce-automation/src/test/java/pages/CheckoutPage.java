package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * CheckoutPage - Page Object for the SauceDemo checkout flow.
 * Covers Step 1 (info), Step 2 (overview), and confirmation.
 * URLs:
 *   Step 1:   /checkout-step-one.html
 *   Step 2:   /checkout-step-two.html
 *   Complete: /checkout-complete.html
 */
public class CheckoutPage extends BasePage {

    // ─── Step 1 – Your Information ────────────────────────────────────────────

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    // ─── Step 2 – Overview ────────────────────────────────────────────────────

    @FindBy(className = "summary_subtotal_label")
    private WebElement subtotalLabel;

    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    @FindBy(id = "finish")
    private WebElement finishButton;

    // ─── Confirmation ─────────────────────────────────────────────────────────

    @FindBy(className = "complete-header")
    private WebElement confirmationHeader;

    @FindBy(className = "complete-text")
    private WebElement confirmationText;

    @FindBy(id = "back-to-products")
    private WebElement backHomeButton;

    // ─── Constructor ──────────────────────────────────────────────────────────

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // ─── Step 1 Actions ───────────────────────────────────────────────────────

    public CheckoutPage enterFirstName(String firstName) {
        type(firstNameField, firstName);
        log.info("Entered first name: {}", firstName);
        return this;
    }

    public CheckoutPage enterLastName(String lastName) {
        type(lastNameField, lastName);
        log.info("Entered last name: {}", lastName);
        return this;
    }

    public CheckoutPage enterPostalCode(String postalCode) {
        type(postalCodeField, postalCode);
        log.info("Entered postal code: {}", postalCode);
        return this;
    }

    public CheckoutPage fillShippingInfo(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
        return this;
    }

    public CheckoutPage clickContinue() {
        click(continueButton);
        log.info("Clicked Continue button");
        return this;
    }

    public CartPage clickCancel() {
        click(cancelButton);
        log.info("Clicked Cancel — returning to cart");
        return new CartPage(driver);
    }

    // ─── Step 2 Actions ───────────────────────────────────────────────────────

    public CheckoutPage clickFinish() {
        click(finishButton);
        log.info("Clicked Finish button — order placed");
        return this;
    }

    // ─── Confirmation Actions ─────────────────────────────────────────────────

    public ProductsPage backToHome() {
        click(backHomeButton);
        log.info("Navigated back to products from confirmation");
        return new ProductsPage(driver);
    }

    // ─── Verifications ────────────────────────────────────────────────────────

    public boolean isOnCheckoutStep1() {
        return getCurrentUrl().contains("checkout-step-one");
    }

    public boolean isOnCheckoutStep2() {
        return getCurrentUrl().contains("checkout-step-two");
    }

    public boolean isOrderConfirmed() {
        return getCurrentUrl().contains("checkout-complete");
    }

    public String getConfirmationHeader() {
        return getText(confirmationHeader);
    }

    public String getConfirmationText() {
        return getText(confirmationText);
    }

    public String getSubtotal() {
        return getText(subtotalLabel);
    }

    public String getTax() {
        return getText(taxLabel);
    }

    public String getTotal() {
        return getText(totalLabel);
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessageText() {
        return getText(errorMessage);
    }
}
