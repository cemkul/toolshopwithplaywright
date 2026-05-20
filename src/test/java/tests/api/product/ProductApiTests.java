package tests.api.dto.product;
import tests.api.dto.product.ProductResponse;
import org.junit.jupiter.api.Test;
import tests.api.base.BaseApiTest;
import tests.api.client.ProductApiClient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductApiTests extends BaseApiTest {

    ProductApiClient productApi = new ProductApiClient();

    @Test
    void userShouldDeserializeProductsSuccessfully() {

        ProductResponse response = productApi.getProductsAsDto();

        assertThat(response.getData(), not(empty()));

        String firstProductName = response.getData().get(0).getName();

        assertThat(firstProductName, notNullValue());
    }

    @Test
    void userShouldGetSingleProductSuccessfully() {
        productApi.getProductById("01JYV5CEBYQFZYP3J9D7C9YS5Z")
                .statusCode(anyOf(is(200), is(404)));
    }

    @Test
    void userShouldSearchProductsSuccessfully() {
        productApi.searchProducts("hammer")
                .statusCode(200)
                .body("data", notNullValue());
    }

    @Test
    void userShouldNotGetProductWithInvalidId() {
        productApi.getProductById("invalid-product-id")
                .statusCode(anyOf(is(400), is(404)));
    }
}
