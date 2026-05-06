package utils;

import io.qameta.allure.Allure;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class ApiLogUtils implements Filter {

    @Override
    public Response filter(
            FilterableRequestSpecification requestSpec,
            FilterableResponseSpecification responseSpec,
            FilterContext ctx
    ) {
        Response response = ctx.next(requestSpec, responseSpec);

        attachRequest(requestSpec);
        attachResponse(response);

        return response;
    }

    private void attachRequest(FilterableRequestSpecification requestSpec) {
        StringBuilder request = new StringBuilder();

        request.append("METHOD: ").append(requestSpec.getMethod()).append("\n");
        request.append("URI: ").append(requestSpec.getURI()).append("\n\n");

        request.append("HEADERS:\n");
        for (Header header : requestSpec.getHeaders()) {
            request.append(header.getName())
                    .append(": ")
                    .append(header.getValue())
                    .append("\n");
        }

        request.append("\nQUERY PARAMS:\n");
        requestSpec.getQueryParams()
                .forEach((key, value) ->
                        request.append(key).append(": ").append(value).append("\n")
                );

        request.append("\nPATH PARAMS:\n");
        requestSpec.getPathParams()
                .forEach((key, value) ->
                        request.append(key).append(": ").append(value).append("\n")
                );

        Object body = requestSpec.getBody();

        if (body != null) {
            request.append("\nBODY:\n");
            request.append(body.toString());
        }

        Allure.addAttachment(
                "API Request",
                "text/plain",
                request.toString()
        );
    }

    private void attachResponse(Response response) {
        Allure.addAttachment(
                "API Response Status",
                "text/plain",
                String.valueOf(response.statusCode())
        );

        Allure.addAttachment(
                "API Response Headers",
                "text/plain",
                response.getHeaders().toString()
        );

        Allure.addAttachment(
                "API Response Body",
                "application/json",
                response.getBody().asPrettyString()
        );
    }
}