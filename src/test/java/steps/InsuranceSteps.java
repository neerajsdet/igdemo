package steps;

import api.ReqResUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.paytm.insurance.utils.CommonUtils;
import org.testng.asserts.SoftAssert;


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


  @When("perform the {string} api call and validate response code {int}")
  public void performApiCallAndValidateResponseCode(String httpMethod, int expectedResponseCode) {
    reqResUtil.processRequestAndSetResponse(httpMethod, expectedResponseCode);
  }


  @When("perform the {string} api call")
  public void performApiCallAndValidateResponseCode(String httpMethod) {
    reqResUtil.processRequest(httpMethod);
  }


  @Given("generate random alphanumeric string with length {int} and save as key {string}")
  public void generateRandomAlphanumericString(int length, String key) {
    String randomString = CommonUtils.randomAlphanumericStringGenerator(length);
    CommonUtils.globalMap.put(key, randomString);
  }


  @Then("validate the api response for below key")
  public void validateApiResponse(DataTable dataTable) {

    SoftAssert softAssert = new SoftAssert();
    List<Map<String, String>> listMap = dataTable.asMaps();

    for (Map<String, String> hashMap : listMap) {
      hashMap.forEach((k, v) -> {
        if (v == null) {
          v = "";
        } else if (CommonUtils.globalMap.containsKey(v)) {
          v = CommonUtils.globalMap.get(v);
        }
        Object jsonResponseValue = reqResUtil.getValueFromResponse(k);
        String actualValue = jsonResponseValue == null ? "null" : jsonResponseValue.toString();
        softAssert.assertEquals(actualValue, v, "Actual response is not as expected");
      });
    }

    softAssert.assertAll();
  }


}
