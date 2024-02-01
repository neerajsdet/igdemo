package steps;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.paytm.insurance.reports.ScenarioUtil;

public class Hooks {

  @Before(order = 1)
  public void beforeScenario(final Scenario scenario){
    ScenarioUtil.putScenario(scenario);
  }


}
