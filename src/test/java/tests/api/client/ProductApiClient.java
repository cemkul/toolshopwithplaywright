package tests.api.client;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import tests.api.dto.product.ProductResponse;
import utils.TokenManager;

public class ProductApiClient {

    @Step("Get products response as DTO")
    public ProductResponse getProductsAsDto() {

        return RestAssured
                .given()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductResponse.class);
    }

    @Step("Get product by id: {productId}")
    public ValidatableResponse getProductById(String productId) {
        return RestAssured
                .given()
                .pathParam("productId", productId)
                .when()
                .get("/products/{productId}")
                .then();
    }

    @Step("Search products by keyword: {keyword}")
    public ValidatableResponse searchProducts(String keyword) {
        return RestAssured
                .given()
                .queryParam("by_search", keyword)
                .when()
                .get("/products/search")
                .then();
    }
    @Step("Get products with bearer token")
    public ValidatableResponse getProductsWithAuth() {

        return RestAssured
                .given()
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .when()
                .get("/products")
                .then();
    }
}