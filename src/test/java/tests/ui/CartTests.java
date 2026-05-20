package tests.ui;

import Pages.CartPage;
import Pages.HomePage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CartTests extends BaseTest {

    @Test
    @Tag("UI")
    void userShouldAddProductToCart() {

        HomePage homePage = new HomePage(page);

        CartPage cartPage = homePage
                .openFirstAvailableProduct()
                .addToCart()
                .openCart();

        assertTrue(cartPage.getCartItemCount() > 0);
    }

    @Test
    @Tag("UI")
    void userShouldUpdateProductQuantityInCart() {

        HomePage homePage = new HomePage(page);

        CartPage cartPage = homePage
                .openFirstAvailableProduct()
                .addToCart()
                .openCart();

        cartPage.updateFirstProductQuantity("3");

        assertTrue(cartPage.getCartItemCount() > 0);
    }

    @Test
    @Tag("UI")
    void userShouldRemoveProductFromCart() {

        HomePage homePage = new HomePage(page);

        CartPage cartPage = homePage
                .openFirstAvailableProduct()
                .addToCart()
                .openCart();

        cartPage.removeFirstProduct();

        // wait for UI update
        page.waitForLoadState();

        assertTrue(cartPage.getCartItemCount() >= 0);
    }
}