package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class CheckoutPage extends BasePage {

    private final Locator countryDropdown;
    private final Locator postalCodeInput;
    private final Locator houseNumberInput;
    private final Locator streetInput;
    private final Locator cityInput;
    private final Locator stateInput;
    private final Locator paymentMethodDropdown;
    private final Locator confirmButton;
    private final Locator successMessage;

    public CheckoutPage(Page page) {
        super(page);

        this.countryDropdown = page.locator("[data-test='country']");
        this.postalCodeInput = page.locator("[data-test='postal_code'], [data-test='postal-code']");
        this.houseNumberInput = page.locator("[data-test='house_number']");
        this.streetInput = page.locator("[data-test='street']");
        this.cityInput = page.locator("[data-test='city']");
        this.stateInput = page.locator("[data-test='state']");
        this.paymentMethodDropdown = page.locator("[data-test='payment-method']");
        this.confirmButton = page.locator("[data-test='finish']");
        this.successMessage = page.locator(".alert-success").or(page.getByText("Payment was successful"));
    }

    @Step("Proceed to billing address")
    public CheckoutPage proceedToAddress() {
        Locator proceedButton = page.locator("[data-test='proceed-2']");
        clickWhenEnabled(proceedButton);
        waitForLoad();
        return this;
    }

    @Step("Fill billing address")
    public CheckoutPage fillBillingAddress(
            String country,
            String postalCode,
            String houseNumber,
            String street,
            String city,
            String state
    ) {

        selectByLabel(countryDropdown, country);
        page.waitForCondition(() -> countryDropdown.inputValue() != null && !countryDropdown.inputValue().isEmpty());

        type(postalCodeInput, postalCode);
        page.waitForCondition(() -> postalCodeInput.inputValue().equals(postalCode));

        type(houseNumberInput, houseNumber);
        type(streetInput, street);
        type(cityInput, city);
        type(stateInput, state);

        return this;
    }

    @Step("Proceed to payment")
    public CheckoutPage proceedToPayment() {
        Locator proceedPaymentButton = page.locator("[data-test='proceed-3']");
        clickWhenEnabled(proceedPaymentButton);
        waitForLoad();
        return this;
    }

    @Step("Select payment method: {paymentMethod}")
    public CheckoutPage selectPaymentMethod(String paymentMethod) {
        selectByValue(paymentMethodDropdown, paymentMethod);
        return this;
    }

    @Step("Confirm order")
    public CheckoutPage confirmOrder() {
        click(confirmButton);
        waitForLoad();
        return this;
    }

    @Step("Verify order success message")
    public boolean isOrderSuccessful() {
        successMessage.first().waitFor();
        return successMessage.first().isVisible();
    }
}