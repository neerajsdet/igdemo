package rest.utils;

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
public class RequestData {

  private String baseUrl;
  private String endpoint;
  private String payload;
  private HashMap<String, String> headers;
  private HashMap<String, String> queryParams;
  private HashMap<String, String> pathParams;
  private HashMap<String, String> formParams;
  private RequestData() {
  }





}
