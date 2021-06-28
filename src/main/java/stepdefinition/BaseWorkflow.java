package stepdefinition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.google.gson.JsonObject;

import Utils.JsonUtils;

public class BaseWorkflow {
	
	private JsonObject testData;
	private WebDriver driver;
	
	public BaseWorkflow(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		if(testData==null) {
			try {
				testData = JsonUtils.SetupJsonConfig(JsonUtils.getConfigsJsonFilePath("testdata"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public JsonObject getTestData() {
		return testData;
	}

}
