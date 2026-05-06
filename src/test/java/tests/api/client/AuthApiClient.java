package tests.api.client;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import tests.api.dto.auth.LoginRequest;
import tests.api.dto.auth.LoginResponse;

public class AuthApiClient {

    @Step("Login API request")
    public LoginResponse login(LoginRequest request) {

        return RestAssured
                .given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/users/login")
                .then()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);
    }
}