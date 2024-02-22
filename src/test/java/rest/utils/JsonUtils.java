package rest.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import java.io.FileReader;
import java.util.HashMap;
import lombok.SneakyThrows;
import steps.Base;

public class JsonUtils {


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
    final JsonObject[] jsonObject = {getJsonObjectOfJsonFile(jsonFileName)};
    if (fileParamsMap != null) {
      fileParamsMap.forEach((key, value) -> {
        String type = "";
        if (key.contains("int:")) {
          key = key.replace("int:", "");
          type = "int";
        } else if (key.contains("bool:")) {
          key = key.replace("bool:", "");
          type = "bool";
        }

        if (Base.globalDataMap.get(Thread.currentThread().getId()).containsKey(value)) {
          value = Base.globalDataMap.get(Thread.currentThread().getId()).get(value);
        }

        jsonObject[0] = updateJsonPathValueWithType(jsonObject[0], key, value, type);
      });
    }
    return jsonObject[0].toString();
  }


  private static JsonObject updateJsonPathValueWithType(JsonObject jsonObject, String jsonPathKey, String value, String type) {
    Object finalValue;
    if (value == null) {
      finalValue = null;
    } else if (value.trim().isEmpty()) {
      finalValue = "";
    } else {
      switch (type) {
        case "int":
          finalValue = Integer.valueOf(value);
          break;
        case "bool":
          finalValue = Boolean.valueOf(value);
          break;
        default:
          finalValue = value;
      }
    }
    return updateJsonPathValue(jsonObject, jsonPathKey, finalValue);
  }


  public static JsonObject updateJsonPathValue(JsonObject jsonObject, String jsonPathKey, Object value) {
    DocumentContext documentContext = JsonPath.parse(jsonObject.toString());
    documentContext.set(jsonPathKey, value);
    return new Gson().fromJson(documentContext.jsonString(), JsonObject.class);
  }



}
