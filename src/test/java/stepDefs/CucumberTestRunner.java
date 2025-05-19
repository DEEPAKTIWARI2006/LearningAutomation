package stepDefs;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import pages.LoginPage;
import utilities.CombinedTagPdfReport;
import utilities.ConvertExtentToPDF;

@CucumberOptions(features = { "src/test/java/featurefiles" },
		// dryRun = true,
		monochrome = true, 
		//tags = "@smoke or @regression or @androidChrome or @androidapp or @androidChrome",
		tags = "@smoke or @regression",
		glue = "stepDefs", plugin = { "pretty",
				"html:target/cucumber/cucumber.html",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" })

public class CucumberTestRunner extends AbstractTestNGCucumberTests {

	private static final Logger logger = LogManager.getLogger(LoginPage.class);

	@BeforeClass
	public void beforeRunningTests() {
		logger.info("Before Running Tests");
	}

	@AfterClass
	public void afterRunningTests() throws IOException, InterruptedException {
		
	}
	
	@AfterSuite
    public void generatePdfFromHtmlReport() {
        
        try {
        	ConvertExtentToPDF.ConvertExtentToPDF();
        	CombinedTagPdfReport.generate(Hooks.tagSummaryMap, "target/TestSummaryTabular.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
