package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class HomePage extends BasePage {

    private final Locator searchInput;
    private final Locator searchButton;
    private final Locator productNames;
    private final Locator productCards;
    private final Locator sortDropdown;
    private final Locator categoryFilters;
    private final Locator brandFilters;
    private final Locator cartNavigation;

    public HomePage(Page page) {
        super(page);

        this.searchInput = page.locator("[data-test='search-query']");
        this.searchButton = page.locator("[data-test='search-submit']");
        this.productNames = page.locator("[data-test='product-name']");
        this.productCards = page.locator(".card");
        this.sortDropdown = page.locator("[data-test='sort']");
        this.categoryFilters = page.locator("label:has-text('Hammer'), label:has-text('Pliers'), input[type='checkbox']");
        this.brandFilters = page.locator("[data-test^='brand']");
        this.cartNavigation = page.locator("[data-test='nav-cart']");
    }

    @Step("Get home page title")
    public String getPageTitle() {
        return page.title();
    }

    @Step("Verify search input is visible")
    public boolean isSearchVisible() {
        return searchInput.isVisible();
    }

    @Step("Search product: {product}")
    public HomePage searchProduct(String product) {
        type(searchInput, product);
        click(searchButton);
        waitForLoad();
        return this;
    }

    @Step("Get product count")
    public int getProductCount() {
        productCards.first().waitFor();
        return productCards.count();
    }

    @Step("Open first product")
    public ProductPage openFirstProduct() {
        click(productNames.first());
        waitForLoad();
        return new ProductPage(page);
    }

    @Step("Open first available product")
    public ProductPage openFirstAvailableProduct() {

        searchProduct("Hammer");

        Locator product = page.locator("[data-test='product-name']")
                .filter(new Locator.FilterOptions().setHasText("Hammer"))
                .first();

        product.waitFor();
        product.click();

        waitForLoad();

        Locator addToCartButton = page.locator("[data-test='add-to-cart']");
        addToCartButton.waitFor();

        if (!addToCartButton.isVisible() || !addToCartButton.isEnabled()) {
            throw new RuntimeException("Selected product is not available to add to cart");
        }

        return new ProductPage(page);
    }

    @Step("Open product by name: {productName}")
    public ProductPage openProductByName(String productName) {
        Locator product = productNames
                .filter(new Locator.FilterOptions().setHasText(productName))
                .first();

        click(product);
        waitForLoad();

        return new ProductPage(page);
    }

    @Step("Open cart from navigation")
    public CheckoutPage openCartFromNavigation() {
        click(cartNavigation);

        page.locator("[data-test='proceed-2']").waitFor();

        return new CheckoutPage(page);
    }

    @Step("Sort products by: {value}")
    public HomePage sortBy(String value) {
        selectByValue(sortDropdown, value);
        waitForLoad();
        return this;
    }

    @Step("Select first category")
    public HomePage selectFirstCategory() {

        Locator category = page.locator("label:has-text('Hammer')").first();

        if (category.count() == 0) {
            category = page.locator("input[type='checkbox']").first();
        }

        category.waitFor();
        category.click();

        waitForLoad();

        return this;
    }

    @Step("Select first brand")
    public HomePage selectFirstBrand() {
        click(brandFilters.first());
        waitForLoad();
        return this;
    }
}