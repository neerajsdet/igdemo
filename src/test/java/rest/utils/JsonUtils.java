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


//  public static String updateJsonFile(String jsonFileName, HashMap<String, String> fileParamsMap) {
//    JsonObject jsonObject = getJsonObjectOfJsonFile(jsonFileName);
//    if (!(fileParamsMap == null)) {
//      fileParamsMap.forEach(
//          (key, value) -> {
//            if (value == null) {
//              if (key.contains("int:")) {
//                key = key.replace("int:", "");
//              }
//              jsonObject.add(key, null);
//            } else if (value.trim().isEmpty()) {
//              if (key.contains("int:")) {
//                key = key.replace("int:", "");
//              }
//              jsonObject.addProperty(key, "");
//            } else if (Base.globalDataMap.get(Thread.currentThread().getId())
//                .containsKey(value)) {
//              value = Base.globalDataMap.get(Thread.currentThread().getId()).get(value);
//              if (key.contains("int:")) {
//                key = key.replace("int:", "");
//                jsonObject.addProperty(key, Integer.parseInt(value));
//              } else {
//                jsonObject.addProperty(key, value);
//              }
//            } else {
//              if (key.contains("int:")) {
//                key = key.replace("int:", "");
//                jsonObject.addProperty(key, Integer.valueOf(value));
//              } else {
//                jsonObject.addProperty(key, value);
//              }
//            }
//          }
//      );
//    }
//    return jsonObject.toString();
//  }


  public static String updateJsonFile(String jsonFileName, HashMap<String, String> fileParamsMap) {
    JsonObject[] jsonObject = {getJsonObjectOfJsonFile(jsonFileName)};
    if (!(fileParamsMap == null)) {
      fileParamsMap.forEach(
          (key, value) -> {
            if (value == null) {
              if (key.contains("int:")) {
                key = key.replace("int:", "");
              }
              jsonObject[0].add(key, null);
            } else if (value.trim().isEmpty()) {
              if (key.contains("int:")) {
                key = key.replace("int:", "");
              }
              jsonObject[0].addProperty(key, "");
            } else if (Base.globalDataMap.get(Thread.currentThread().getId())
                .containsKey(value)) {
              value = Base.globalDataMap.get(Thread.currentThread().getId()).get(value);
              if (key.contains("int:")) {
                key = key.replace("int:", "");
                jsonObject[0].addProperty(key, Integer.parseInt(value));
              } else {
//                jsonObject.addProperty(key, value);
                jsonObject[0] = updateJsonPathValue(jsonObject[0], key, value);
              }
            } else {
              if (key.contains("int:")) {
                key = key.replace("int:", "");
                jsonObject[0].addProperty(key, Integer.valueOf(value));
              } else {
//                jsonObject.addProperty(key, value);
                updateJsonPathValue(jsonObject[0], key, value);
              }
            }
          }
      );
    }
    return jsonObject[0].toString();
  }



  public static JsonObject updateJsonPathValue(JsonObject jsonObject, String jsonPathKey, Object value) {
    DocumentContext documentContext = JsonPath.parse(jsonObject.toString());
    documentContext.set(jsonPathKey, value);
    return new Gson().fromJson(documentContext.jsonString(), JsonObject.class);
  }

}
