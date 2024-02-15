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


    public void setFormsData(HashMap<String, String> formParamsMap) {
        requestData.setFormParams(formParamsMap);
    }

    public void updateJsonAndSetPayload(String jsonFileName, HashMap<String, String> fileParamMap) {
        String payload = JsonUtils.updateJsonFile(jsonFileName, fileParamMap);
        requestData.setPayload(payload);
    }


    public Response processRequestAndVerifyResponseCode(String httpMethod, int expectedResponseCode) {
        Response response = restProcessor.processApiRequest(httpMethod, requestData);
        Assert.assertEquals(response.getStatusCode(), expectedResponseCode);
        return response;
    }

    public void processRequestAndVerifyResponseCodeAndGetValues(String httpMethod, int expectedResponseCode, String jsonPathKeys, String globalKeys) {
        Response response = restProcessor.processApiRequest(httpMethod, requestData);
        Assert.assertEquals(response.getStatusCode(), expectedResponseCode);
        String[] jsonKeys = jsonPathKeys.split(",");
        String[] globalDataKeys = globalKeys.split(",");
        Assert.assertEquals(globalDataKeys.length, jsonKeys.length,
            "Json keys and global data keys are not equal");
        for (int i = 0; i < jsonKeys.length; i++) {
            Base.globalDataMap.get(Thread.currentThread().getId()).put(globalDataKeys[i], response.jsonPath().get(jsonKeys[i]).toString());
        }
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

    public void resetRequestData() {
        requestData.setBaseUrl(null);
        requestData.setEndpoint(null);
        requestData.setHeaders(new HashMap<>());
        requestData.setQueryParams(new HashMap<>());
        requestData.setPathParams(new HashMap<>());
        requestData.setFormParams(new HashMap<>());
        requestData.setPayload(null);
    }


    public HashMap<String, HashMap<String, String>> getDataParamsMap(
            final DataTable dataTable) {
        HashMap<String, HashMap<String, String>> resultMap = new HashMap<>();
        HashMap<String, String> headerMap = new HashMap<>();
        HashMap<String, String> queryParamMap = new HashMap<>();
        HashMap<String, String> fileParamMap = new HashMap<>();

        for (Map<String, String> hm : dataTable.asMaps()) {
            hm.forEach((k, v) -> {
                if (k.contains("h:")) {
                    if (v.equalsIgnoreCase("null")) {
                        headerMap.put(k.substring(2), null);
                    } else
                        headerMap.put(k.substring(2),
                                Base.globalDataMap.get(Thread.currentThread().getId()).getOrDefault(v, v));
                } else if (k.contains("qp:")) {
                    if (v.equalsIgnoreCase("null")) {
                        queryParamMap.put(k.substring(3), null);
                    } else {
                        queryParamMap.put(k.substring(3),
                                Base.globalDataMap.get(Thread.currentThread().getId()).getOrDefault(v, v));
                    }
                } else {
                    if (v == null) {
                        fileParamMap.put(k, "");
                    } else if (v.equalsIgnoreCase("null")) {
                        fileParamMap.put(k, null);
                    } else {
                        fileParamMap.put(k, v);
                    }
                }
            });
        }

        resultMap.put("headers", headerMap);
        resultMap.put("queryParams", queryParamMap);
        resultMap.put("fileParams", fileParamMap);
        return resultMap;
    }


}
