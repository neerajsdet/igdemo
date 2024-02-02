package rest.utils;


import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.paytm.insurance.utils.PropertiesHelper;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import steps.Base;

public class ReqResUtils {
  RequestData requestData = RequestData.builder().build();
  RestProcessor restProcessor = new RestProcessor();


  public void setBaseUrlAndEndpoint(String baseUrlKey, String endpointKey) {
    requestData.setBaseUrl(PropertiesHelper.getProperty(baseUrlKey));
    requestData.setEndpoint(PropertiesHelper.getProperty(endpointKey));
  }


  public void setHeadersData(HashMap<String, String> headersMap) {
    requestData.setHeaders(headersMap);
  }


  public void setQueryParamsData(HashMap<String, String> queryParamsMap) {
    requestData.setQueryParams(queryParamsMap);
  }


  public void updateJsonAndSetPayload(String jsonFileName, HashMap<String, String> fileParamMap) {
    String payload = JsonUtils.updateJsonFile(jsonFileName, fileParamMap);
    requestData.setPayload(payload);
  }


  public void processRequestAndVerifyResponseCode(String httpMethod, int expectedResponseCode) {
    Response response = restProcessor.processApiRequest(httpMethod, requestData);
    Assert.assertEquals(response.getStatusCode(), expectedResponseCode);
  }


  public void processRequestAndVerifyResponse(String httpMethod, int expectedResponseCode,
      DataTable dataTable) {
    Response response = restProcessor.processApiRequest(httpMethod, requestData);
    Assert.assertEquals(response.getStatusCode(), expectedResponseCode);

    SoftAssert softAssert = new SoftAssert();
    List<Map<String, String>> listMap = dataTable.asMaps();
    for (Map<String, String> hashMap : listMap) {
      hashMap.forEach((k, v) -> {
        if (v == null) {
          v = "";
        } else if (Base.globalDataMap.get(Thread.currentThread().getId()).containsKey(v)) {
          v = Base.globalDataMap.get(Thread.currentThread().getId()).get(v);
        }
        Object jsonResponseValue = this.getValueFromResponse(response, k);
        String actualValue = jsonResponseValue == null ? "null" : jsonResponseValue.toString();
        softAssert.assertEquals(actualValue, v, "Actual response is not as expected");
      });
    }
    softAssert.assertAll();
  }


  public void processRequest(String httpMethod) {
    Response response = restProcessor.processApiRequest(httpMethod, requestData);

  }


  public Object getValueFromResponse(Response response, String jsonPath) {
    return response.jsonPath().get(jsonPath);
  }


}
