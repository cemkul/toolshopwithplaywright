package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class ProductPage extends BasePage {

    private final Locator productName;
    private final Locator productPrice;
    private final Locator quantityInput;
    private final Locator increaseQuantityButton;
    private final Locator decreaseQuantityButton;
    private final Locator addToCartButton;
    private final Locator cartButton;

    public ProductPage(Page page) {
        super(page);

        this.productName = page.locator("h1[data-test='product-name']");
        this.productPrice = page.locator("[data-test='unit-price']");
        this.quantityInput = page.locator("[data-test='quantity']");
        this.increaseQuantityButton = page.locator("[data-test='increase-quantity']");
        this.decreaseQuantityButton = page.locator("[data-test='decrease-quantity']");
        this.addToCartButton = page.locator("[data-test='add-to-cart']");
        this.cartButton = page.locator("[data-test='nav-cart']");
    }

    @Step("Get product name")
    public String getProductName() {
        return getText(productName);
    }

    @Step("Get product price")
    public String getProductPrice() {
        return getText(productPrice);
    }

    @Step("Set product quantity to: {targetQuantity}")
    public ProductPage setQuantity(int targetQuantity) {
        quantityInput.waitFor();

        int currentQuantity = Integer.parseInt(quantityInput.inputValue());

        while (currentQuantity < targetQuantity) {
            click(increaseQuantityButton);
            currentQuantity++;
        }

        while (currentQuantity > targetQuantity) {
            click(decreaseQuantityButton);
            currentQuantity--;
        }

        return this;
    }

    @Step("Add product to cart")
    public ProductPage addToCart() {
        click(addToCartButton);
        return this;
    }

    @Step("Verify add to cart button is visible")
    public boolean isAddToCartButtonVisible() {
        return addToCartButton.isVisible();
    }

    @Step("Open cart from product page")
    public CartPage openCart() {
        click(cartButton);

        page.locator("[data-test='proceed-1']").waitFor();

        return new CartPage(page);
    }
}