package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.google.gson.JsonObject;

import Utils.JsonUtils;

public class BasePage {

	private WebDriver driver;
	private String url;
	private JsonObject jsonObjectUrl;


	public BasePage(WebDriver driver) {
		this.driver = driver;
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 30);
		PageFactory.initElements(factory, this);
	}

	public void load() {
		setUrl();
		driver.get(url);
	}

	protected WebDriver getDriver() {
		return this.driver;
	}

	public String getTitle(){
		return driver.getTitle();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl() {
		if(jsonObjectUrl==null) {
			try {
				jsonObjectUrl = JsonUtils.SetupJsonConfig(JsonUtils.getConfigsJsonFilePath("url"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			url = JsonUtils.getValFromJson(jsonObjectUrl, "baseurl","");
		}
	}

}
