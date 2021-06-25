package WebdriverBase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;


@CucumberOptions(plugin = {"pretty","json:target/report.xml","json:target/cucumber.json","html:target/cucumber"})
public class CustomAbstractTestNGCucumberRunner<CucumberFeatureWrapper> {
	
	private TestNGCucumberRunner testNGCucumberRunner;

	 @BeforeClass(alwaysRun = true )
	public void setUpClass() throws Exception {
        this.testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }
	
	@Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void scenario(PickleWrapper pickleEvent, CucumberFeatureWrapper cucumberFeature) throws Throwable {
        testNGCucumberRunner.runScenario(pickleEvent.getPickle());
    }

    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }
	
    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        this.testNGCucumberRunner.finish();
    }
}
