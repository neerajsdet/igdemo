package org.paytm.insurance.reports;

import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.json.support.Status;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ReportUtils {

  public static void generateWebReport(){
    File reportOutputDirectory = new File("target/report");
    List<String> jsonFiles = new ArrayList<>();
    jsonFiles.add("target/report/cucumber.json");

    String buildNumber = "1";
    String projectName = "Insurance Test";

    Configuration configuration = new Configuration(reportOutputDirectory, projectName);
    configuration.setNotFailingStatuses(Collections.singleton(Status.SKIPPED));
    configuration.setBuildNumber(buildNumber);
    configuration.addClassifications("Platform", "iOS");
    configuration.addClassifications("Branch", "master");

    ReportBuilder reportBuilder = new ReportBuilder(jsonFiles,configuration);
    reportBuilder.generateReports();
    log.info("Report generated!");
  }

  @SneakyThrows
  public static void generateEmailAbleReport() {
    CucumberResultsOverview results = new CucumberResultsOverview();
    results.setOutputDirectory("target/report/emailable-report");
    results.setOutputName("emailablereport");
    results.setSourceFile("./target/report/cucumber.json");
    results.execute();
    log.info("Emailable report generated!");
  }





}
