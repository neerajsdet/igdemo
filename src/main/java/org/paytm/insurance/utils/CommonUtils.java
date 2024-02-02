package org.paytm.insurance.utils;

import io.cucumber.datatable.DataTable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;

public class CommonUtils {


  public static HashMap<String, HashMap<String, String>> getDataParamsMap(
      final DataTable dataTable) {
    HashMap<String, HashMap<String, String>> resultMap = new HashMap<>();
    HashMap<String, String> headerMap = new HashMap<>();
    HashMap<String, String> queryParamMap = new HashMap<>();
    HashMap<String, String> fileParamMap = new HashMap<>();

    for (Map<String, String> hm : dataTable.asMaps()) {
      hm.forEach((k, v) -> {
        if (k.contains("h:")) {
          if (v.equalsIgnoreCase("null")) {
            headerMap.put(k.substring(2, k.length()), null);
          } else {
            headerMap.put(k.substring(2, k.length()), v);
          }
        } else if (k.contains("qp:")) {
          if (v.equalsIgnoreCase("null")) {
            queryParamMap.put(k.substring(3, k.length()), null);
          } else {
            queryParamMap.put(k.substring(3, k.length()), v);
          }
        } else {
          if (v == null) {
            fileParamMap.put(k.substring(0, k.length()), "");
          } else if (v.equalsIgnoreCase("null")) {
            fileParamMap.put(k.substring(0, k.length()), null);
          } else {
            fileParamMap.put(k.substring(0, k.length()), v);
          }
        }
      });
    }

    resultMap.put("headers", headerMap);
    resultMap.put("queryParams", queryParamMap);
    resultMap.put("fileParams", fileParamMap);
    return resultMap;
  }


  public static String randomAlphanumericStringGenerator(int length) {
    return RandomStringUtils.randomAlphanumeric(length);
  }


}
