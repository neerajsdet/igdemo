import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.paytm.insurance.reports.ReportUtil;
import org.paytm.insurance.utils.PropertiesHelper;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


@CucumberOptions(
    plugin = {"pretty","html:target/report/cucumber.html","json:target/report/cucumber.json"},
    features = {"src/test/resources/features/insurance"},
//    tags = "@motor",
//    tags = "@shop-insurance",
    tags = "@shop-insurance or @motor",
    glue = {"steps"}
)
public class Runner  extends AbstractTestNGCucumberTests {



  @BeforeSuite
  public void beforeSuite(){
    String config = "src/test/resources/config/staging/config.properties";
    String endpoints = "src/test/resources/endpoints/endpoint.properties";
    PropertiesHelper.loadProperties(config,endpoints);
  }


  @AfterSuite
  public void generateReport(){
    ReportUtil.generateWebReport();
    ReportUtil.generateEmailAbleReport();
  }







}
