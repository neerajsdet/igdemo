package steps;

import io.cucumber.java.en.Given;
import org.paytm.insurance.utils.CommonUtils;


public class GenericSteps extends Base {

  @Given("generate random alphanumeric string with length {int} and save as key {string}")
  public void generateRandomAlphanumericString(int length, String key) {
    String randomString = CommonUtils.randomAlphanumericStringGenerator(length);
    Base.globalDataMap.get(threadId).put(key, randomString);
  }


}
