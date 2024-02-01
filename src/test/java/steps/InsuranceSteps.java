package steps;

import api.ReqResUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.paytm.insurance.utils.CommonUtils;



@Slf4j
public class InsuranceSteps {

  ReqResUtil reqResUtil = new ReqResUtil();


  @Given("set the request base url {string} and endpoint {string}")
  public void setBaseUrlAndEndpoint(String baseUrl, String endpoint) {
    reqResUtil.setBaseUrlAndEndpoint(baseUrl, endpoint);
  }


  @Given("set the request headers, query params and payload with json file name {string}")
  public void updatePayloadJson(String jsonFileName, DataTable dataTable) {
    HashMap<String, HashMap<String, String>> map = CommonUtils.getDataParamsMap(dataTable);
    reqResUtil.setHeadersData(map.get("headers"));
    reqResUtil.setQueryParamsData(map.get("queryParams"));
    reqResUtil.updateJsonAndSetPayload(jsonFileName, map.get("fileParams"));
  }


  @Given("set the request headers and query params")
  public void updateParams(DataTable dataTable) {
    HashMap<String, HashMap<String, String>> map = CommonUtils.getDataParamsMap(dataTable);
    reqResUtil.setHeadersData(map.get("headers"));
    reqResUtil.setQueryParamsData(map.get("queryParams"));
  }


  @When("perform the {string} api call, verify response code {int} and below response data")
  public void performApiCallAndValidateResponseData(String httpMethod, int expectedResponseCode, DataTable dataTable) {
    reqResUtil.processRequestAndVerifyResponse(httpMethod,expectedResponseCode,dataTable);
  }


  @When("perform the {string} api call and validate response code {int}")
  public void performApiCallAndValidateResponseCode(String httpMethod, int expectedResponseCode) {
    reqResUtil.processRequestAndVerifyResponseCode(httpMethod, expectedResponseCode);
  }


  @When("perform the {string} api call")
  public void performApiCall(String httpMethod) {
    reqResUtil.processRequest(httpMethod);
  }



}
