package tests.ui;

import Pages.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutTests extends BaseTest {

    @Test
    @Tag("UI")
    void userShouldCompleteCheckoutSuccessfully() {

        HomePage homePage = new HomePage(page);
        CartPage cartPage = homePage
                .openFirstAvailableProduct()
                .addToCart()
                .openCart();
        cartPage.proceedToCheckout(); // proceed-1

        LoginPage loginPage = new LoginPage(page);
        loginPage.login("customer@practicesoftwaretesting.com", "welcome01");

        CheckoutPage checkoutPage = new CheckoutPage(page);
        checkoutPage.proceedToAddress(); // proceed-2
        checkoutPage.fillBillingAddress(
                "Turkey",
                "34000",
                "10",
                "Automation Street",
                "Istanbul",
                "Istanbul"
        );
        checkoutPage.proceedToPayment(); // proceed-3
        checkoutPage.selectPaymentMethod("cash-on-delivery");
        checkoutPage.confirmOrder();
        assertTrue(checkoutPage.isOrderSuccessful());

    }
}