package tests.ui;

import Pages.HomePage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HomeTests extends BaseTest {

    @Test
    @Tag("UI")
    void homePageShouldLoadSuccessfully() {

        HomePage homePage = new HomePage(page);

        assertTrue(homePage.getPageTitle().contains("Practice Software Testing"));
        assertTrue(homePage.isSearchVisible());
    }

    @Test
    @Tag("UI")
    void userShouldSearchProductSuccessfully() {

        HomePage homePage = new HomePage(page);

        homePage.searchProduct("hammer");

        assertTrue(homePage.getProductCount() > 0);
    }
}