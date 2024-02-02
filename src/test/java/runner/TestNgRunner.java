package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.paytm.insurance.reports.ReportUtil;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;


@CucumberOptions(
    plugin = {"pretty", "html:target/report/cucumber.html", "json:target/report/cucumber.json"},
    features = {"src/test/resources/features/insurance"},
//    tags = "@motor",
//    tags = "@shop-insurance",
    tags = "@shop-insurance or @motor",
    glue = {"steps"}
)
public class TestNgRunner extends AbstractTestNGCucumberTests {


  @Override
  @DataProvider(parallel = false)
  public Object[][] scenarios() {
    return super.scenarios();
  }


  @AfterSuite
  public static void tearDown() {
    ReportUtil.generateWebReport();
    ReportUtil.generateEmailAbleReport();
  }


}
