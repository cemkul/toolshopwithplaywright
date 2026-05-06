package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {

    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator registerLink;
    private final Locator errorMessage;

    public LoginPage(Page page) {
        super(page);

        this.emailInput = page.locator("[data-test='email']");
        this.passwordInput = page.locator("[data-test='password']");
        this.loginButton = page.locator("[data-test='login-submit']");
        this.registerLink = page.locator("[data-test='register-link']");
        this.errorMessage = page.locator(".alert-danger, .alert, [role='alert']");
    }

    @Step("Open login page")
    public LoginPage open() {
        click(page.locator("[data-test='nav-sign-in']"));
        waitForLoad();
        return this;
    }

    @Step("Login with email: {email} and password: ******")
    public LoginPage login(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        click(loginButton);
        waitForLoad();
        return this;
    }

    @Step("Open register page")
    public RegisterPage openRegisterPage() {
        click(registerLink);
        waitForLoad();
        return new RegisterPage(page);
    }

    @Step("Verify login error is visible")
    public boolean isErrorVisible() {
        errorMessage.first().waitFor();
        return errorMessage.first().isVisible();
    }
}