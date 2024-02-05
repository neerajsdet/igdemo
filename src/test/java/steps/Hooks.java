package steps;

import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.paytm.insurance.reports.ScenarioUtils;
import org.paytm.insurance.utils.PropertiesHelper;

@Slf4j
public class Hooks {


    @BeforeAll
    public static void runBeforeAllScenarios() {
        String env = System.getProperty("env") != null ? System.getProperty("env") : "staging";
        log.info("Running on environment: {}", env);
        String config = "src/test/resources/profiles/" + env + "/config.properties";
        String endpoints = "src/test/resources/endpoints/endpoint.properties";
        PropertiesHelper.loadProperties(config, endpoints);
    }


    @Before(order = 1)
    public void runBeforeEachScenario(final Scenario scenario) {
        ScenarioUtils.putScenario(scenario);
    }


}
