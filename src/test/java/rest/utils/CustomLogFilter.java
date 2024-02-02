package rest.utils;


import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import java.util.Optional;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
public class CustomLogFilter implements Filter {

  private StringBuffer requestBuilderLogs;
  private StringBuffer curlBuilderLogs;
  private StringBuffer responseBuilderLogs;


  @SneakyThrows
  @Override
  public Response filter(FilterableRequestSpecification requestSpec,
      FilterableResponseSpecification responseSpec, FilterContext ctx) {
    Response response = ctx.next(requestSpec, responseSpec);

    requestBuilderLogs = new StringBuffer();
    curlBuilderLogs = new StringBuffer();
    responseBuilderLogs = new StringBuffer();

    requestBuilderLogs.append(
        "\n Request method: " + Optional.ofNullable(requestSpec.getMethod()).orElse("Null"));
    requestBuilderLogs.append(
        "\n Request URI: " + Optional.ofNullable(requestSpec.getURI()).orElse("Null"));
    requestBuilderLogs.append(
        "\n Form Params: " + Optional.ofNullable(requestSpec.getFormParams()).orElse(null));
    requestBuilderLogs.append(
        "\n Path Param: " + Optional.ofNullable(requestSpec.getRequestParams()).orElse(null));
    requestBuilderLogs.append(
        "\n Request Query Param: " + Optional.ofNullable(requestSpec.getQueryParams())
            .orElse(null));
    requestBuilderLogs.append(
        "\n Headers: " + Optional.ofNullable(requestSpec.getHeaders()).orElse(null));
    requestBuilderLogs.append(
        "\n Cookies: " + Optional.ofNullable(requestSpec.getCookies()).orElse(null));
    requestBuilderLogs.append(
        "\n Body: " + Optional.ofNullable(requestSpec.getBody()).orElse("Null"));
    requestBuilderLogs.append(
        "\n------------------------------------------------------------------");
//    log.info(requestBuilderLogs.toString());

    curlBuilderLogs.append(
        "\n curl --location --request " + Optional.ofNullable(requestSpec.getMethod())
            .orElse("Null"));
    curlBuilderLogs.append(" '" + Optional.ofNullable(requestSpec.getURI()).orElse("Null") + "' ");
    for (Header eachHeader : requestSpec.getHeaders()) {
      curlBuilderLogs.append("\n --header '" + eachHeader.toString().replace("=", ":") + "'");
    }
    curlBuilderLogs.append(
        "\n --data-raw '" + Optional.ofNullable(requestSpec.getBody()).orElse("Null") + "'\n");
    curlBuilderLogs.append("------------------------------------------------------------------");
//    log.info(curlBuilderLogs.toString());

    responseBuilderLogs.append(
        "\n Status Code: " + Optional.ofNullable(response.getStatusCode()).orElse(null));
    responseBuilderLogs.append(
        "\n Response Content Type: " + Optional.ofNullable(response.contentType())
            .orElse("Null"));
    responseBuilderLogs.append(
        "\n Response Body: " + Optional.ofNullable(response.asString()).orElse(null));
//    log.info(responseBuilderLogs.toString());

    return response;
  }


}
