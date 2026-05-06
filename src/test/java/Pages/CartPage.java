package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class CartPage extends BasePage {

    private final Locator cartItems;
    private final Locator quantityInputs;
    private final Locator deleteButtons;
    private final Locator cartTotal;
    private final Locator checkoutButton;
    private final Locator emptyCartMessage;

    public CartPage(Page page) {
        super(page);

        this.cartItems = page.locator("tr, .cart-item");
        this.quantityInputs = page.locator("[data-test='product-quantity']");
        this.deleteButtons = page.locator("button:has-text('Delete'), .btn-danger");
        this.cartTotal = page.locator("[data-test='cart-total']");
        this.checkoutButton = page.locator("[data-test='proceed-1']");
        this.emptyCartMessage = page.locator("text=cart is empty");
    }

    @Step("Get cart item count")
    public int getCartItemCount() {
        cartItems.first().waitFor();
        return cartItems.count();
    }

    @Step("Update first product quantity to: {quantity}")
    public CartPage updateFirstProductQuantity(String quantity) {
        type(quantityInputs.first(), quantity);
        quantityInputs.first().press("Enter");
        waitForLoad();
        return this;
    }

    @Step("Remove first product from cart")
    public CartPage removeFirstProduct() {
        click(deleteButtons.first());
        waitForLoad();
        return this;
    }

    @Step("Get cart total")
    public String getCartTotal() {
        return getText(cartTotal);
    }

    @Step("Verify cart is empty")
    public boolean isCartEmpty() {
        return emptyCartMessage.first().isVisible();
    }

    @Step("Proceed to checkout from cart")
    public CheckoutPage proceedToCheckout() {
        click(checkoutButton);
        waitForLoad();
        return new CheckoutPage(page);
    }
}