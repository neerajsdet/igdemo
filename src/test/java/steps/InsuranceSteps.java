package steps;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.paytm.insurance.utils.CommonUtils;



@Slf4j
public class InsuranceSteps extends Base {
  

  @Given("set the request base url {string} and endpoint {string}")
  public void setBaseUrlAndEndpoint(String baseUrl, String endpoint) {
    Base.reqResUtilMap.get(threadId).setBaseUrlAndEndpoint(baseUrl, endpoint);
  }


  @Given("set the request headers, query params and payload with json file name {string}")
  public void updatePayloadJson(String jsonFileName, DataTable dataTable) {
    HashMap<String, HashMap<String, String>> map = CommonUtils.getDataParamsMap(dataTable);
    Base.reqResUtilMap.get(threadId).setHeadersData(map.get("headers"));
    Base.reqResUtilMap.get(threadId).setQueryParamsData(map.get("queryParams"));
    Base.reqResUtilMap.get(threadId).updateJsonAndSetPayload(jsonFileName, map.get("fileParams"));
  }


  @Given("set the request headers and query params")
  public void updateParams(DataTable dataTable) {
    HashMap<String, HashMap<String, String>> map = CommonUtils.getDataParamsMap(dataTable);
    Base.reqResUtilMap.get(threadId).setHeadersData(map.get("headers"));
    Base.reqResUtilMap.get(threadId).setQueryParamsData(map.get("queryParams"));
  }


  @When("perform the {string} api call, verify response code {int} and below response data")
  public void performApiCallAndValidateResponseData(String httpMethod, int expectedResponseCode, DataTable dataTable) {
    Base.reqResUtilMap.get(threadId).processRequestAndVerifyResponse(httpMethod,expectedResponseCode,dataTable);
  }


  @When("perform the {string} api call and validate response code {int}")
  public void performApiCallAndValidateResponseCode(String httpMethod, int expectedResponseCode) {
    Base.reqResUtilMap.get(threadId).processRequestAndVerifyResponseCode(httpMethod, expectedResponseCode);
  }


  @When("perform the {string} api call")
  public void performApiCall(String httpMethod) {
    Base.reqResUtilMap.get(threadId).processRequest(httpMethod);
  }



}
