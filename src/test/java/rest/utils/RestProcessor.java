package rest.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.SSLConfig;
import io.restassured.filter.Filter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.paytm.insurance.reports.ScenarioUtils;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;


@Slf4j
public class RestProcessor {

    private static Filter logFilter = new CustomLogFilter();


    public Response processApiRequest(String httpMethod, RequestData requestData) {
        Response response;
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(requestData.getBaseUrl())
                .setConfig(config().sslConfig(new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()))
                .addFilter(logFilter)
                .build();
        if (requestData.getHeaders()!=null && !requestData.getHeaders().isEmpty())
            requestSpecification.headers(requestData.getHeaders());

        if (requestData.getQueryParams()!=null && !requestData.getQueryParams().isEmpty())
            requestSpecification.queryParams(requestData.getQueryParams());

        if (requestData.getFormParams()!=null && !requestData.getFormParams().isEmpty()) {
            requestSpecification.formParams(requestData.getFormParams());
        } else if (requestData.getPayload() != null) {
            requestSpecification.body(requestData.getPayload());
        }

        response = given().spec(requestSpecification)
                .request(httpMethod, requestData.getEndpoint())
                .thenReturn();

        log.info("Received response for url: {}, request payload: {}, status code: {}, response: {}",
                requestData.getBaseUrl() + requestData.getEndpoint(),
                requestData.getPayload(),
                response.statusCode(),
                response.body().asString());

        if (logFilter instanceof CustomLogFilter) {
            CustomLogFilter customLogFilter = (CustomLogFilter) logFilter;
            ScenarioUtils.getScenario().attach(
                    "\nAPI Request: " + customLogFilter.getRequestBuilderLogs() + "\nCurl Request: "
                            + customLogFilter.getCurlBuilderLogs() + "\nAPI Response: "
                            + customLogFilter.getResponseBuilderLogs(), "text/plain", "API Request/Response");
        }
        return response;
    }


}
