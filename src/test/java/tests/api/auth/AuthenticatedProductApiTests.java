package tests.api.auth;

import org.junit.jupiter.api.Test;
import tests.api.base.BaseApiTest;
import tests.api.client.AuthApiClient;
import tests.api.client.ProductApiClient;
import tests.api.dto.auth.LoginRequest;
import tests.api.dto.auth.LoginResponse;
import utils.TokenManager;

import static org.hamcrest.Matchers.notNullValue;

public class AuthenticatedProductApiTests extends BaseApiTest {

    AuthApiClient authApi = new AuthApiClient();
    ProductApiClient productApi = new ProductApiClient();

    @Test
    void userShouldReuseTokenSuccessfully() {

        LoginRequest request = new LoginRequest(
                "customer@practicesoftwaretesting.com",
                "welcome01"
        );

        LoginResponse response = authApi.login(request);

        TokenManager.setToken(response.getAccess_token());

        productApi.getProductsWithAuth()
                .statusCode(200)
                .body("data", notNullValue());
    }
}