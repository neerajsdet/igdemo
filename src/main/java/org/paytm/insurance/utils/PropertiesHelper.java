package org.paytm.insurance.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertiesHelper {
  public static Properties properties = null;

  private PropertiesHelper() {
  }

  private static Properties getPropertiesInstance() {
    properties = new Properties();
    synchronized (PropertiesHelper.class) {
      properties = new Properties();
    }
    return properties;
  }


  @SneakyThrows
  public static void loadProperties(InputStream... inputStreamsObj) {
    if (properties == null)
      synchronized (PropertiesHelper.class) {
        properties = getPropertiesInstance();
      }

    Properties childProperties = new Properties();
      for (InputStream obj : inputStreamsObj) {
        childProperties.load(obj);
        properties.putAll(childProperties);
      }
  }



  public static void loadProperties(String... filePath) {
    if (properties == null)
      synchronized (PropertiesHelper.class) {
        properties = getPropertiesInstance();
      }

    Properties childProperties = new Properties();
    try {
      for (String file : filePath) {
        try(FileInputStream fis = new FileInputStream(file)){
          childProperties.load(fis);
        }
        properties.putAll(childProperties);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    System.out.println("Properties loaded successfully.");
  }



  public static String getProperty(String key) {
    return properties.getProperty(key).trim();
  }


}
