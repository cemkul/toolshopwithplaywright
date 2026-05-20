package tests.api.base;
import utils.ApiLogUtils;
import config.ConfigReader;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseApiTest {

    @BeforeAll
    static void setupApi() {

        // Set base URL
        RestAssured.baseURI = ConfigReader.get("api.base.url");

        // Add Allure logging filter
        RestAssured.filters(new ApiLogUtils());
    }
}