package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import org.paytm.insurance.reports.ReportUtils;
import org.paytm.insurance.utils.Retry;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;


@CucumberOptions(
    plugin = {"pretty", "html:target/report/cucumber.html", "json:target/report/cucumber.json"},
    features = {"src/test/resources/features/insurance"},
    tags = "@1sb-test",
    glue = {"steps"}
)
public class TestNgRunner extends AbstractTestNGCucumberTests {


  @Override
  @DataProvider(parallel = false)
  public Object[][] scenarios() {
    return super.scenarios();
  }


  @Test(
      groups = "cucumber",
      description = "Runs Cucumber Scenarios",
      dataProvider = "scenarios",
      retryAnalyzer = Retry.class
  )
  public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
    super.runScenario(pickleWrapper, featureWrapper);
  }


  @AfterSuite
  public static void tearDown() {
    ReportUtils.generateWebReport();
    ReportUtils.generateEmailAbleReport();
  }


}
