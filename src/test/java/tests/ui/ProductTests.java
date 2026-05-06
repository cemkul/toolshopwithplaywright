package tests.ui;


import Pages.HomePage;
import Pages.ProductPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTests extends BaseTest {

    @Test
    void userShouldOpenProductDetailsPage() {

        HomePage homePage = new HomePage(page);

        ProductPage productPage = homePage.openFirstProduct();

        assertFalse(productPage.getProductName().isEmpty());
    }

    @Test
    void userShouldFilterByCategory() {

        HomePage homePage = new HomePage(page);

        homePage.selectFirstCategory();

        assertTrue(homePage.getProductCount() > 0);
    }

    @Test
    void userShouldFilterByBrand() {

        HomePage homePage = new HomePage(page);

        homePage.selectFirstBrand();

        assertTrue(homePage.getProductCount() > 0);
    }

    @Test
    void userShouldSortProducts() {

        HomePage homePage = new HomePage(page);

        homePage.sortBy("price,desc");

        assertTrue(homePage.getProductCount() > 0);
    }
}