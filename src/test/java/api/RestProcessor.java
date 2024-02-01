package api;

import static io.restassured.RestAssured.config;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;



@Slf4j
public class RestProcessor {


  public Response processApiRequest(String httpMethod, RequestData requestData) {
    Response response = null;
    switch (httpMethod) {
      case "GET":
        response = RestAssured.given()
            .baseUri(requestData.getBaseUrl())
            .headers(requestData.getHeaders())
            .queryParams(requestData.getQueryParams())
            .config(config().sslConfig(new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()))
            .log().all()
            .request(httpMethod, requestData.getEndpoint())
            .then()
            .extract()
            .response();
        break;
      case "POST":
        response = RestAssured.given()
            .baseUri(requestData.getBaseUrl())
            .headers(requestData.getHeaders())
            .queryParams(requestData.getQueryParams())
            .body(requestData.getPayload())
            .config(config().sslConfig(new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()))
            .log().all()
            .request(httpMethod, requestData.getEndpoint())
            .then()
            .extract()
            .response();
        break;
      default:
        System.out.println("Invalid http method");
    }
    System.out.println("Response: " + response.asString());
    return response;
  }


}
