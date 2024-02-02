package steps;

import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.paytm.insurance.reports.ScenarioUtils;
import org.paytm.insurance.utils.PropertiesHelper;


public class Hooks {


  @BeforeAll
  public static void runBeforeAllScenarios() {
    String config = "src/test/resources/config/staging/config.properties";
    String endpoints = "src/test/resources/endpoints/endpoint.properties";
    PropertiesHelper.loadProperties(config, endpoints);
  }


  @Before(order = 1)
  public void runBeforeEachScenario(final Scenario scenario) {
    ScenarioUtils.putScenario(scenario);
  }


}
