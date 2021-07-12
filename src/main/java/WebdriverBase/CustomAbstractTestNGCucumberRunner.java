package WebdriverBase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

@CucumberOptions(
		features = "src/main/java/feature", 
		glue = {"stepdefinition"},
		tags = "@regression",
		monochrome = true,
		plugin = {
				"pretty","html:target/site/cucumber-pretty","json:target/cucumber.json" //For TestNG reports
				//"pretty","json:target/report.xml","json:target/cucumber.json","html:target/cucumber", //For Local reports
				//"io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm" - For Allure Reports
		})
public class CustomAbstractTestNGCucumberRunner extends GridDriverManager{



	private TestNGCucumberRunner testNGCucumberRunner;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

	@Test(groups = "smoke", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
	public void scenario(PickleWrapper pickle, FeatureWrapper cucumberFeature) {
		testNGCucumberRunner.runScenario(pickle.getPickle());
	}

	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return testNGCucumberRunner.provideScenarios();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		testNGCucumberRunner.finish();
	}
	

}

