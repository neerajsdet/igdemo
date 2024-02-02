package rest.utils;

import static io.restassured.RestAssured.config;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.filter.Filter;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.paytm.insurance.reports.ScenarioUtil;


@Slf4j
public class RestProcessor {

  private static Filter logFilter = new CustomLogFilter();


  public Response processApiRequest(String httpMethod, RequestData requestData) {
    Response response = null;
    switch (httpMethod) {
      case "GET":
        response = RestAssured.given()
            .baseUri(requestData.getBaseUrl())
            .headers(requestData.getHeaders())
            .queryParams(requestData.getQueryParams())
            .config(
                config().sslConfig(new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()))
            .filter(logFilter)
            .request(httpMethod, requestData.getEndpoint())
            .thenReturn();
        break;
      case "POST":
        response = RestAssured.given()
            .baseUri(requestData.getBaseUrl())
            .headers(requestData.getHeaders())
            .queryParams(requestData.getQueryParams())
            .body(requestData.getPayload())
            .config(
                config().sslConfig(new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()))
            .filter(logFilter)
            .request(httpMethod, requestData.getEndpoint())
            .thenReturn();
        break;
      default:
        System.out.println("Invalid http method");
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
