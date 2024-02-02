package rest.utils;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.SSLConfig;
import io.restassured.filter.Filter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.paytm.insurance.reports.ScenarioUtil;


@Slf4j
public class RestProcessor {

  private static Filter logFilter = new CustomLogFilter();


  public Response processApiRequest(String httpMethod, RequestData requestData) {
    Response response = null;
    RequestSpecification requestSpecification = new RequestSpecBuilder()
        .setBaseUri(requestData.getBaseUrl())
        .addHeaders(requestData.getHeaders())
        .addQueryParams(requestData.getQueryParams())
        .setConfig(config().sslConfig(new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()))
        .addFilter(logFilter)
        .build();

    switch (httpMethod) {
      case "GET":
        response = given().spec(requestSpecification)
            .request(httpMethod, requestData.getEndpoint())
            .thenReturn();
        break;
      case "POST":
      case "PUT":
        response = given().spec(requestSpecification)
            .body(requestData.getPayload())
            .request(httpMethod, requestData.getEndpoint())
            .thenReturn();
        break;
      default:
        log.error("Invalid http method");
    }

    log.info("Received response for url: {}, request payload: {}, status code: {}, response: {}",
        requestData.getBaseUrl(),
        requestData.getPayload(),
        response.statusCode(),
        response.body().asString());

    if (logFilter instanceof CustomLogFilter) {
      CustomLogFilter customLogFilter = (CustomLogFilter) logFilter;
      ScenarioUtil.getScenario().attach(
          "\nAPI Request: " + customLogFilter.getRequestBuilderLogs() + "\nCurl Request: "
              + customLogFilter.getCurlBuilderLogs() + "\nAPI Response: "
              + customLogFilter.getResponseBuilderLogs(), "text/plain", "API Request/Response");
    }
    return response;
  }


}
