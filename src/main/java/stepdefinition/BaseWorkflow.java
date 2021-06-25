package stepdefinition;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.google.gson.JsonObject;

import Utils.JsonUtils;
import WebdriverBase.GridDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import webpages.BasePage;
import webpages.HomePage;
import webpages.LoginPage;

public class BaseWorkflow {
	
	private JsonObject testData;
	private WebDriver driver;
	public BasePage basePage;
	public LoginPage loginPage;
	public HomePage homePage;
	public GridDriverManager gridDriverManager;
	
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
	

	public void closure() {
		driver.quit();
	}
}
