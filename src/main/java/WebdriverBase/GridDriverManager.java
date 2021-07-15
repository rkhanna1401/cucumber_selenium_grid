package WebdriverBase;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.google.gson.JsonObject;
import Utils.GenericUtils;
import Utils.JsonUtils;
import Utils.PropertyManager;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

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
	private static Logger logger;
	private static GridDriverManager gridDriverManager;
	private static 	String path;
	private static File srcFile;
	private static String Base64StringOfScreenshot="";

	public static GridDriverManager getInstance()
	{
		if (gridDriverManager==null)
			gridDriverManager = new GridDriverManager();
		return gridDriverManager;
	}

	@BeforeSuite
	public void initServer() {
		if(PropertyManager.getPropertyHelper("configuration").get("grid_mode").equals("ON") && 
				PropertyManager.getPropertyHelper("configuration").get("docker").equals("false")) {
			HubNodeConfiguration.configureServer();
		}
		logger = Logger.getLogger(GridDriverManager.class.getName());
		if(jsonObject==null) {
			try {
				jsonObject = JsonUtils.SetupJsonConfig(JsonUtils.getConfigsJsonFilePath("url"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		capabilities = new DesiredCapabilities();
		hubIpAddress = JsonUtils.getValFromJson(jsonObject, "huburl","");
		platform = PropertyManager.getPropertyHelper("configuration").get("platform").toString();
		browser = PropertyManager.getPropertyHelper("configuration").get("browser").toString();
		//setPlatform(platform);
		setDriverLocation(browser);
		path =  GenericUtils.createOutputFolderPath();
	}



	public WebDriver getLocalDriver(String browserType,String platformType)  {

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
			//driver = new ChromeDriver(options);
			threadLocalDriver.set(new ChromeDriver(options));
		}
		if (browserType.equalsIgnoreCase("Safari")) {
			SafariOptions options = new SafariOptions();
			options.merge(capabilities);
		}
		return driver;
	}

	public RemoteWebDriver getDriver() {
		if(threadLocalDriver.get() == null) {
			setDriver(browser, platform);
		}
		return threadLocalDriver.get();
	}

	/**
	 * 
	 * @param browserType
	 * @param platformType
	 * @return Remote Driver Instance if running on Grid mode or up-casted webdriver instance if running locally
	 */
	public void setDriver(String browserType,String platformType)  {
		synchronized (browserType) {
			if(PropertyManager.getPropertyHelper("configuration").get("grid_mode").equals("ON"))
			{
				try {
					Thread.sleep(1000);
					if(browserType.equalsIgnoreCase("chrome")) {
						ChromeOptions options = new ChromeOptions();
						options.setExperimentalOption("excludeSwitches", Arrays.asList("test-type"));
						capabilities.merge(options);
						//capabilities = DesiredCapabilities.chrome();
						threadLocalDriver.set(new RemoteWebDriver(new URL(hubIpAddress),options));
					}
					else if(browserType.equalsIgnoreCase("safari")) {
						capabilities = DesiredCapabilities.safari();
					}
					else if(browserType.equalsIgnoreCase("firefox")) {
						firefoxOptions = new FirefoxOptions();
						firefoxOptions.merge(capabilities);
						//capabilities = DesiredCapabilities.firefox();
					}
					//threadLocalDriver.set(new RemoteWebDriver(new URL(hubIpAddress),capabilities));
					threadLocalDriver.get().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
					threadLocalDriver.get().manage().window().maximize();
					//driver = new RemoteWebDriver(new URL(hubIpAddress), capabilities);
				} catch (Exception e) {
					e.printStackTrace();
				}
				remoteWebDriverList.add(threadLocalDriver.get());
			}
			else {
				getLocalDriver(browserType, platformType);
				threadLocalDriver.get().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
				threadLocalDriver.get().manage().window().maximize();
				webDriverList.add(threadLocalDriver.get());
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
			logger.info("Failed to set the Platform as: " + platform);
			break;
		}
		logger.info("Successfully set the Platform as: " + platform);
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
			logger.info("Failed to  Set the driver locations");
			System.exit(1);
			break;
		}
		logger.info("Successfully Set the driver locations");
	}


	public String getValFromJson(JsonObject json, String dataKey) {
		return json.get(dataKey).getAsString();
	}


	@AfterMethod
	public void captureScreenshot(ITestResult iTestResult) throws IOException {
		String currentTestName = iTestResult.getMethod().getMethodName();
		if(iTestResult.isSuccess()) {}
		else {
			logger.log(Level.INFO,currentTestName + "test is failed");
			TakesScreenshot scrShot =((TakesScreenshot)threadLocalDriver.get());
			srcFile=scrShot.getScreenshotAs(OutputType.FILE);
			File destFile=new File(path+currentTestName+".png");
			try {
				FileUtils.copyFile(srcFile, destFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			byte[] fileContent = FileUtils.readFileToByteArray(srcFile);
			Base64StringOfScreenshot = "data:image/png;base64," + Base64.getEncoder().encodeToString(fileContent);
			Allure.addAttachment(currentTestName, new ByteArrayInputStream(((TakesScreenshot)threadLocalDriver.get()).getScreenshotAs(OutputType.BYTES)));

		}
	}

	@AfterSuite
	public void tearDown() {
		if(!remoteWebDriverList.isEmpty()) {
			for(RemoteWebDriver driver : remoteWebDriverList)
				driver.quit();
			if(PropertyManager.getPropertyHelper("configuration").get("grid_mode").equals("ON") && 
					PropertyManager.getPropertyHelper("configuration").get("docker").equals("false")) {
				HubNodeConfiguration.tearDownHub();
			}
			logger.info("Hub and Node has been shut down");
		}
		if(!webDriverList.isEmpty()) {
			for(WebDriver driver : webDriverList)
				driver.quit();
		}
		logger.info("All browser instances has been shut down");

	}

	@Attachment(type = "image/png")
	@After
	public void af0(Scenario scenario) throws InterruptedException, IOException, IllegalMonitorStateException
	{	
		//ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(Base64StringOfScreenshot);
		if(scenario.isFailed()) {
			Allure.addAttachment(scenario.getName(), new ByteArrayInputStream(((TakesScreenshot)threadLocalDriver.get()).getScreenshotAs(OutputType.BYTES)));	}
	}
}
