package tests.ui;

import Pages.HomePage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HomeTests extends BaseTest {

    @Test
    void homePageShouldLoadSuccessfully() {

        HomePage homePage = new HomePage(page);

        assertTrue(homePage.getPageTitle().contains("Practice Software Testing"));
        assertTrue(homePage.isSearchVisible());
    }

    @Test
    void userShouldSearchProductSuccessfully() {

        HomePage homePage = new HomePage(page);

        homePage.searchProduct("hammer");

        assertTrue(homePage.getProductCount() > 0);
    }
}