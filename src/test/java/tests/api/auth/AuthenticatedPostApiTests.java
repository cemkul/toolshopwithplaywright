package tests.api.auth;

import org.junit.jupiter.api.Test;
import tests.api.base.BaseApiTest;
import tests.api.client.AuthApiClient;
import tests.api.client.MessageApiClient;
import tests.api.dto.auth.LoginRequest;
import tests.api.dto.auth.LoginResponse;
import tests.api.dto.message.ContactMessageRequest;
import tests.api.dto.message.ContactMessageResponse;
import utils.TokenManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthenticatedPostApiTests extends BaseApiTest {

    AuthApiClient authApi = new AuthApiClient();
    MessageApiClient messageApi = new MessageApiClient();

    @Test
    void userShouldSendAuthenticatedMessageSuccessfully() {

        LoginRequest loginRequest = new LoginRequest(
                "customer@practicesoftwaretesting.com",
                "welcome01"
        );

        LoginResponse loginResponse = authApi.login(loginRequest);

        TokenManager.setToken(loginResponse.getAccess_token());

        ContactMessageRequest request = new ContactMessageRequest(
                "Can",
                "Kullu",
                "can@test.com",
                "Support",
                "Automation API message"
        );

        ContactMessageResponse response = messageApi.sendMessage(request);

        assertNotNull(response);
        assertNotNull(response.getMessage());
    }
}