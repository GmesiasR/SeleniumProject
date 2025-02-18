package resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import testcomponents.BaseTest;

public class ExtentReporterNG extends BaseTest {
    static String reporterPath = reportsPath+ "//index.html";


    public static ExtentReports getReportObject() {
        ExtentSparkReporter reporter = new ExtentSparkReporter(reporterPath);
        reporter.config().setReportName("WebAutomationResults");
        reporter.config().setDocumentTitle("Test Results");

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Gonzalo Mesias");
        return extent;
    }
}
