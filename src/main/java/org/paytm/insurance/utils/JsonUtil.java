package org.paytm.insurance.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.FileReader;
import java.util.HashMap;
import lombok.SneakyThrows;

public class JsonUtil {


  @SneakyThrows
  public static JsonObject getJsonObjectOfJsonFile(String fileName) {
    String filePath = "src/test/resources/testdata/json/" + fileName;
    try (FileReader reader = new FileReader(filePath)) {
      Gson gson = new Gson();
      return gson.fromJson(reader, JsonObject.class);
    }
  }


  @SneakyThrows
  public static String readJsonFile(String fileName) {
    JsonObject jsonObject;
    String filePath = "src/test/resources/testdata/json/" + fileName;
    try (FileReader reader = new FileReader(filePath)) {
      Gson gson = new Gson();
      jsonObject = gson.fromJson(reader, JsonObject.class);
    }
    return jsonObject.toString();
  }


  public static String updateJsonFile(String jsonFileName, HashMap<String, String> fileParamsMap) {
    JsonObject jsonObject = getJsonObjectOfJsonFile(jsonFileName);
    if (!(fileParamsMap == null)) {
      fileParamsMap.forEach(
          (key, value) -> {
            if (value == null) {
              jsonObject.add(key, null);
            } else if (value.trim().isEmpty()) {
              jsonObject.addProperty(key, "");
            } else if (CommonUtils.globalMap.containsKey(value)) {
              value = CommonUtils.globalMap.get(value);
              jsonObject.addProperty(key, value);
            } else {
              jsonObject.addProperty(key, value);
            }
          }
      );
    }
    return jsonObject.toString();
  }


}
