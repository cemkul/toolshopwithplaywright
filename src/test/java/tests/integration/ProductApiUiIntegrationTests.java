package tests.integration;

import Pages.HomePage;
import config.ConfigReader;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tests.ui.BaseTest;
import tests.api.client.ProductApiClient;
import utils.ApiLogUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductApiUiIntegrationTests extends BaseTest {

    ProductApiClient productApi = new ProductApiClient();

    @BeforeAll
    static void setupApi() {
        RestAssured.baseURI = ConfigReader.get("api.base.url");
        RestAssured.filters(new ApiLogUtils());
    }

    @Test
    void productReturnedByApiShouldBeSearchableInUi() {

        String productName = getProductNameFromApi("hammer");

        HomePage homePage = new HomePage(page);

        homePage.searchProduct(productName);

        assertTrue(
                homePage.getProductCount() > 0,
                "Product from API should be visible in UI search results"
        );
    }

    @Step("Get product name from API using keyword: {keyword}")
    private String getProductNameFromApi(String keyword) {

        String productName = productApi.searchProducts(keyword)
                .statusCode(200)
                .extract()
                .path("data[0].name");

        if (productName == null || productName.isBlank()) {

            productName = productApi.getProductsAsDto()
                    .getData()
                    .get(0)
                    .getName();
        }

        if (productName == null || productName.isBlank()) {
            throw new RuntimeException("No product name returned from API");
        }

        return productName;
    }
}