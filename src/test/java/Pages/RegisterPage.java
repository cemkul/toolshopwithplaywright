package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class RegisterPage extends BasePage {

    private final Locator firstName;
    private final Locator lastName;
    private final Locator dateOfBirth;
    private final Locator street;
    private final Locator postalCode;
    private final Locator city;
    private final Locator state;
    private final Locator country;
    private final Locator phone;
    private final Locator email;
    private final Locator password;
    private final Locator registerButton;
    private final Locator successMessage;

    public RegisterPage(Page page) {
        super(page);

        this.firstName = page.locator("[data-test='first-name']");
        this.lastName = page.locator("[data-test='last-name']");
        this.dateOfBirth = page.locator("[data-test='dob']");
        this.street = page.locator("[data-test='street']");
        this.postalCode = page.locator("[data-test='postal_code']");
        this.city = page.locator("[data-test='city']");
        this.state = page.locator("[data-test='state']");
        this.country = page.locator("[data-test='country']");
        this.phone = page.locator("[data-test='phone']");
        this.email = page.locator("[data-test='email']");
        this.password = page.locator("[data-test='password']");
        this.registerButton = page.locator("[data-test='register-submit']");
        this.successMessage = page.locator(".alert-success").or(page.getByText("successfully registered"));
    }

    @Step("Register user with email: {emailValue}")
    public LoginPage registerUser(String emailValue, String passwordValue) {
        type(firstName, "Test");
        type(lastName, "User");
        type(dateOfBirth, "1995-01-01");
        type(street, "Automation Street 1");
        type(postalCode, "34000");
        type(city, "Istanbul");
        type(state, "Istanbul");
        selectByLabel(country, "Turkey");
        type(phone, "5555555555");
        type(email, emailValue);
        type(password, passwordValue);

        click(registerButton);
        waitForLoad();

        return new LoginPage(page);
    }

    @Step("Verify registration success")
    public boolean isRegistrationSuccessful() {
        successMessage.first().waitFor();
        return successMessage.first().isVisible();
    }

    public String getCurrentUrl() {
        return page.url();
    }
}