package tests.ui;

import Pages.LoginPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthTests extends BaseTest {
    @Tag("UI")
    @Test
    void userShouldNotLoginWithInvalidCredentials() {

        LoginPage loginPage = new LoginPage(page);

        loginPage
                .open()
                .login("wrong@email.com", "wrongPassword123");

        assertTrue(loginPage.isErrorVisible());
    }
    @Tag("UI")
    @Test
    void userShouldNavigateToRegisterPage() {

        LoginPage loginPage = new LoginPage(page);

        String currentUrl = loginPage
                .open()
                .openRegisterPage()
                .getCurrentUrl();

        assertTrue(currentUrl.contains("register"));
    }
}