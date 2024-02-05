package steps;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;
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
        Base.reqResUtilMap.get(threadId).processRequestAndVerifyResponse(httpMethod, expectedResponseCode, dataTable);
    }


    @When("perform the {string} api call and validate response code {int}")
    public void performApiCallAndValidateResponseCode(String httpMethod, int expectedResponseCode) {
        Base.reqResUtilMap.get(threadId).processRequestAndVerifyResponseCode(httpMethod, expectedResponseCode);
    }


    @When("perform the {string} api call")
    public void performApiCall(String httpMethod) {
        Base.reqResUtilMap.get(threadId).processRequest(httpMethod);
    }


    @Given("generate the sso token with mobile {string}, password {string} and save in global data with key {string}")
    public void generateSsoToken(String mobile, String password, String key) {
        Map<String, String> headers = Map.of(
                "Content-Type", "application/x-www-form-urlencoded; charset=UTF-8",
                "Authorization", "Basic cGF5dG0tcGctY2xpZW50LXN0YWdpbmc6YTc0MjZiZTAtYTJkZC00N2NmLWExODEtYjM3YzgwMWYzNGM2"
        );
        Map<String, String> formParams = Map.of(
                "response_type", "code",
                "client_id", "paytm-pg-client-staging",
                "do_not_redirect", "true",
                "scope", "paytm",
                "username", mobile,
                "password", password
        );
        Base.reqResUtilMap.get(threadId).setBaseUrlAndEndpoint("account_url", "account_authorize_api_endpoint");
        Base.reqResUtilMap.get(threadId).setHeadersData(new HashMap<>(headers));
        Base.reqResUtilMap.get(threadId).setFormsData(new HashMap<>(formParams));

        Response response = Base.reqResUtilMap.get(threadId).processRequestAndVerifyResponseCode("POST", 200);
        log.info(response.asString());

        Base.reqResUtilMap.get(threadId).setFormsData(new HashMap<>());

    }


}
