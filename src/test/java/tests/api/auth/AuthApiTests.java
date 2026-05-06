package tests.api.auth;

import org.junit.jupiter.api.Test;
import tests.api.base.BaseApiTest;
import tests.api.client.AuthApiClient;
import tests.api.dto.auth.LoginRequest;
import tests.api.dto.auth.LoginResponse;
import utils.TokenManager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthApiTests extends BaseApiTest {

    AuthApiClient authApi = new AuthApiClient();

    @Test
    void userShouldLoginSuccessfully() {

        LoginRequest request = new LoginRequest(
                "customer@practicesoftwaretesting.com",
                "welcome01"
        );

        LoginResponse response = authApi.login(request);

        assertNotNull(response);
        assertNotNull(response.getAccess_token());
        assertFalse(response.getAccess_token().isEmpty());

        TokenManager.setToken(response.getAccess_token());
    }
}