package WebdriverBase;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
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
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.google.gson.JsonObject;

import Utils.JsonUtils;
import Utils.PropertyManager;
import enums.PlatformList;

public class GridDriverManager {

	public static ThreadLocal<RemoteWebDriver> threadLocalDriver = new ThreadLocal<RemoteWebDriver>();
	private static String hubIpAddress;
	private JsonObject jsonObject;
	static ChromeOptions chromeOptions;
	SafariOptions safariOptions;
	FirefoxOptions firefoxOptions;
	static DesiredCapabilities capabilities;
	public static WebDriver driver;
	private	static ArrayList<WebDriver> webDriverList = new ArrayList<WebDriver>();
	private	static ArrayList<RemoteWebDriver> remoteWebDriverList = new ArrayList<RemoteWebDriver>();
	private static String platform;
	private static String browser;

	@BeforeSuite
	public void initServer() {
		HubNodeConfiguration.configureServer();
		if(jsonObject==null) {
			try {
				jsonObject = JsonUtils.SetupJsonConfig(JsonUtils.getConfigsJsonFilePath("url"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		hubIpAddress = JsonUtils.getValFromJson(jsonObject, "huburl","");
		platform = PropertyManager.getPropertyHelper("configuration").get("platform").toString();
		browser = PropertyManager.getPropertyHelper("configuration").get("browser").toString();
		setPlatform(platform);
		setDriverLocation(browser);
	}



	public WebDriver getLocalDriver(String browserType,String platformType)  {

		DesiredCapabilities capabilities = new DesiredCapabilities();
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
		driver.manage().window().maximize();
		return driver;
	}

	public RemoteWebDriver getDriver() {
	
		return setDriver(browser,platform);
	}

	/**
	 * 
	 * @param browserType
	 * @param platformType
	 * @return Remote Driver Instance if running on Grid mode or upcasted webdriver instance if running locally
	 */
	public RemoteWebDriver setDriver(String browserType,String platformType)  {
		synchronized (browserType) {
			DesiredCapabilities caps = null;
			if(PropertyManager.getPropertyHelper("configuration").get("grid_mode").equals("ON"))
			{
				try {
					Thread.sleep(1000);
					if(browserType.equalsIgnoreCase("chrome")) {
						setDriverLocation(browserType);
						 caps = DesiredCapabilities.chrome();
						
					}
					else if(browserType.equalsIgnoreCase("safari")) {
					caps = DesiredCapabilities.safari();
						threadLocalDriver.set(new RemoteWebDriver(new URL(hubIpAddress),caps));
					}
					else if(browserType.equalsIgnoreCase("firefox")) {
						 caps = DesiredCapabilities.firefox();
						threadLocalDriver.set(new RemoteWebDriver(new URL(hubIpAddress),caps));
					}
					driver = new RemoteWebDriver(new URL(hubIpAddress), caps);
					driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
					driver.manage().window().maximize();
				} catch (Exception e) {
					e.printStackTrace();
				}
				remoteWebDriverList.add((RemoteWebDriver) driver);
				return (RemoteWebDriver) driver;
			}
			else {
				getLocalDriver(browserType, platformType);
				driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
				driver.manage().window().maximize();
				webDriverList.add(driver);
				return (RemoteWebDriver) driver;
			}
		}
	}

	/**
	 * Set Drivers Platform
	 */
	private static void setPlatform(String platform) {
		switch (platform) {
		case "windows":
			capabilities.setPlatform(Platform.WINDOWS);
			break;
		case "mac":
			capabilities.setPlatform(Platform.MAC);
			break;
		default:
			//  LOGGER.info("Failed to set the Platform as: " + platform);
			break;
		}
		// LOGGER.info("Successfully set the Platform as: " + platform);
	}

	/**
	 * Set Drivers Location System Properties
	 */
	private static void setDriverLocation(String browserName) {
		String driverLocation = "Driver";
		File file = new File(driverLocation);
		String driverLocationPath = file.getAbsolutePath();
		switch (browserName) {
		case "chrome":
			if (platform.equals("windows"))
				System.setProperty("webdriver.chrome.driver", driverLocationPath + "/chromedriver.exe");
			else if (platform.equalsIgnoreCase("mac"))
				System.setProperty("webdriver.chrome.driver", driverLocationPath + "/chromedriver");
			break;
		case "firefox":
			if (platform.equals("windows"))
				System.setProperty("webdriver.gecko.driver", driverLocationPath + "/geckodriver.exe");
			else if (platform.equals("mac"))
				System.setProperty("webdriver.gecko.driver", driverLocationPath + "/geckodriver");
			break;

		default:
			//  LOGGER.info("Failed to  Set the driver locations");
			System.exit(1);
			break;
		}
		// LOGGER.info("Successfully Set the driver locations");
	}


	public String getValFromJson(JsonObject json, String dataKey) {
		return json.get(dataKey).getAsString();
	}

	@AfterSuite
	public void tearDown() {
		if(!remoteWebDriverList.isEmpty()) {
			for(RemoteWebDriver driver : remoteWebDriverList)
				driver.quit();
		}
		if(!webDriverList.isEmpty()) {
			for(WebDriver driver : webDriverList)
				driver.quit();
		}
	}
}
