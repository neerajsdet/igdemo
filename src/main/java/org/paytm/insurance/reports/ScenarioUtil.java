package org.paytm.insurance.reports;

import io.cucumber.java.Scenario;
import java.util.HashMap;

public class ScenarioUtil {
  private static final HashMap<Thread, Scenario> scenarioMap = new HashMap<>();


  public static void putScenario(final Scenario scenario) {
    scenarioMap.put(Thread.currentThread(), scenario);
  }

  public static Scenario getScenario() {
    return scenarioMap.get(Thread.currentThread());
  }


}
