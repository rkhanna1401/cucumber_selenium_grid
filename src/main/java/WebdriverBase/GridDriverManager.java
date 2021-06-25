package WebdriverBase;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import com.google.gson.JsonObject;

import Utils.JsonUtils;
import Utils.PropertyManager;

public class GridDriverManager {

	public static ThreadLocal<RemoteWebDriver> threadLocalDriver = new ThreadLocal<RemoteWebDriver>();
	private String hubIpAddress;
	private JsonObject jsonObject;
	static ChromeOptions chromeOptions;
	SafariOptions safariOptions;
	FirefoxOptions firefoxOptions;
	static DesiredCapabilities capabilities;

	public GridDriverManager() throws Exception {
		if(jsonObject==null) {
			jsonObject = JsonUtils.SetupJsonConfig(JsonUtils.getConfigsJsonFilePath("url"));
		}
		hubIpAddress = JsonUtils.getValFromJson(jsonObject, "huburl","");
	}

	public WebDriver getDriver(String browserType,String platformType) throws IOException {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		WebDriver driver = null;
		// Browsers

		if (browserType.equalsIgnoreCase("Firefox")) {
			firefoxOptions = new FirefoxOptions();
			firefoxOptions.merge(capabilities);

		}
		if (browserType.equalsIgnoreCase("Chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-infobars");
			options.addArguments("start-maximized");
			options.setAcceptInsecureCerts(true);
			options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
			options.merge(capabilities);
			driver = new ChromeDriver(options);
		}
		if (browserType.equalsIgnoreCase("Safari")) {
			SafariOptions options = new SafariOptions();
			options.merge(capabilities);
		}

		// Platforms
		if (platformType.equalsIgnoreCase("WINDOWS")) {
			capabilities.setPlatform(Platform.WINDOWS);
		}
		if (platformType.equalsIgnoreCase("MAC")) {
			capabilities.setPlatform(Platform.MAC);
		}
		driver.manage().window().maximize();
		//capabilities.setVersion(getValFromJson(jsonObject,"version"));

		return driver;
	}

	@SuppressWarnings("deprecation")
	public DesiredCapabilities getCapabilities(String browserType,String platformType) throws IOException {

		 capabilities = new DesiredCapabilities();
		// Browsers

		if (browserType.equalsIgnoreCase("Firefox")) {
			FirefoxProfile firefoxProfile = new FirefoxProfile();
            firefoxProfile.setPreference("enableNativeEvents", true);
            firefoxProfile.setAssumeUntrustedCertificateIssuer(true);
            capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
		}
		if (browserType.equalsIgnoreCase("Chrome")) {
			try {
				System.setProperty("webdriver.chrome.driver","/Users/rishikhanna/Documents/cucumber/shopping/Driver/chromedriver");
			} catch (Exception e) {
				e.printStackTrace();
			}
		//	chromeOptions = new ChromeOptions();
		//	capabilities.setCapability("chrome.switches", Arrays.asList("--proxy \"http=http://proxyserver:port/;https=http://proxyserver:port/\""));

//			chromeOptions.setAcceptInsecureCerts(true);
//			chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
//			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			//chromeOptions.merge(capabilities);
//			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
//            chromePrefs.put("profile.default_content_settings.popups", 0);
//            ChromeOptions options = new ChromeOptions();
//            options.setExperimentalOption("prefs", chromePrefs);
            capabilities.setBrowserName("chrome");
            capabilities.setPlatform(Platform.MOJAVE);
            capabilities.setVersion("91.0.4472.114");
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
          //  capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		}
		if (browserType.equalsIgnoreCase("Safari")) {
			safariOptions = new SafariOptions();
			capabilities.setJavascriptEnabled(true);
			capabilities.setCapability("unexpectedAlertBehaviour", "accept");
			capabilities.setCapability(SafariOptions.CAPABILITY, safariOptions);

		}

		// Platforms
		if (platformType.equalsIgnoreCase("WINDOWS")) {
			capabilities.setPlatform(Platform.WINDOWS);
		}
		if (platformType.equalsIgnoreCase("MAC")) {
			capabilities.setPlatform(Platform.MOJAVE);
		}

		//capabilities.setVersion(getValFromJson(jsonObject,"version"));
		return capabilities;
	}

	public String getValFromJson(JsonObject json, String dataKey) {
		return json.get(dataKey).getAsString();
	}

	public RemoteWebDriver getDriver() {
		try {
			getCapabilities("chrome", "MAC");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDriver("chrome", "WINDOWS");
		return threadLocalDriver.get();
	}

	public void setDriver(String browserType,String platformType)  {

		if(PropertyManager.getPropertyHelper("configuration").get("GRID_MODE").equals("ON"))
		{
			try {
				HubNodeConfiguration.configureServer();
				System.setProperty("webdriver.chrome.driver","/Users/rishikhanna/Documents/cucumber/shopping/Driver/chromedriver");
				threadLocalDriver.set(new RemoteWebDriver(new URL("http://localhost:9090/wd/hub"),capabilities));
				threadLocalDriver.get().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		
		else {
			if(browserType.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver","/Users/rishikhanna/Documents/cucumber/shopping/Driver/chromedriver");
				threadLocalDriver.set(new ChromeDriver(chromeOptions));
			}
			else if(browserType.equalsIgnoreCase("safari")) {
				threadLocalDriver.set(new SafariDriver(safariOptions));
			}
			else if (browserType.equalsIgnoreCase("firefox")) {
				threadLocalDriver.set(new FirefoxDriver(firefoxOptions));
			}
			threadLocalDriver.get().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			threadLocalDriver.get().manage().window().maximize();
		}
	}


}
