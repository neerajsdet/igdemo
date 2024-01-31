package api;

import io.restassured.response.Response;
import java.util.HashMap;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiData {

  private String baseUrl;
  private String endpoint;
  private String payload;
  private HashMap<String, String> headers;
  private HashMap<String, String> queryParams;
  private HashMap<String, String> pathParams;
  private Response response;

  private ApiData() {
  }

}
