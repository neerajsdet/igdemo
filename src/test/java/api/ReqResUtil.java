package api;


import io.restassured.response.Response;
import java.util.HashMap;
import org.paytm.insurance.utils.JsonUtil;
import org.paytm.insurance.utils.PropertiesHelper;
import org.testng.Assert;

public class ReqResUtil {

  ApiData apiData = ApiData.builder().build();
  RestProcessor restProcessor = new RestProcessor();


  public void setBaseUrlAndEndpoint(String baseUrlKey, String endpointKey) {
    apiData.setBaseUrl(PropertiesHelper.getProperty(baseUrlKey));
    apiData.setEndpoint(PropertiesHelper.getProperty(endpointKey));
  }


  public void setHeadersData(HashMap<String, String> headersMap) {
    apiData.setHeaders(headersMap);
  }


  public void setQueryParamsData(HashMap<String, String> queryParamsMap) {
    apiData.setQueryParams(queryParamsMap);

  }



  public void updateJsonAndSetPayload(String jsonFileName, HashMap<String, String> fileParamMap) {
    String payload = JsonUtil.updateJsonFile(jsonFileName,fileParamMap);
    apiData.setPayload(payload);
  }


  public void processRequestAndSetResponse(String httpMethod, int expectedResponseCode) {
    Response response = restProcessor.processApiRequest(httpMethod,apiData);
    apiData.setResponse(response);
    Assert.assertEquals(apiData.getResponse().getStatusCode(), expectedResponseCode);
  }



  public void processRequest(String httpMethod) {
    Response response = restProcessor.processApiRequest(httpMethod,apiData);
    apiData.setResponse(response);
  }




  public Object getValueFromResponse(String jsonPath){
    return apiData.getResponse().jsonPath().get(jsonPath);
  }






}
