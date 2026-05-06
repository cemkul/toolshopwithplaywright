package tests.api.client;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import tests.api.dto.message.ContactMessageRequest;
import tests.api.dto.message.ContactMessageResponse;
import utils.TokenManager;

public class MessageApiClient {

    @Step("Send authenticated contact message")
    public ContactMessageResponse sendMessage(
            ContactMessageRequest request
    ) {

        return RestAssured
                .given()
                .contentType("application/json")
                .header(
                        "Authorization",
                        "Bearer " + TokenManager.getToken()
                )
                .body(request)
                .when()
                .post("/messages")
                .then()
                .statusCode(200)
                .extract()
                .as(ContactMessageResponse.class);
    }
}