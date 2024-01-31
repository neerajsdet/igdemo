package org.paytm.insurance.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
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

  /**
   * Method to load properties bases on InputStream object
   * @param inputStreamsObj
   */
  public static void loadProperties(InputStream... inputStreamsObj) {
    if (properties == null)
      synchronized (PropertiesHelper.class) {
        properties = getPropertiesInstance();
      }

    Properties childProperties = new Properties();
    try {
      for (InputStream obj : inputStreamsObj) {
        childProperties.load(obj);
        properties.putAll(childProperties);
      }
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    log.info("Properties loaded successfully.");
  }


  /**
   * Method to load properties bases on properties file path
   * @param filePath
   */
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
      log.error(e.getMessage());
    }
    log.info("Properties loaded successfully.");
  }


  /**
   * Method to get value of passed property
   * @param key
   * @return
   */
  public static String getProperty(String key) {
    return properties.getProperty(key).trim();
  }


}
