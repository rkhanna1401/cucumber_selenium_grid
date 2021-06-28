package Utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import Exceptions.UtilException;

public class GenericUtils {

	private static String parent;

	
	public static boolean isDisplayed(WebElement element) {
		boolean displayed = false;
		try {
			displayed = element.isDisplayed();
		}
		catch(Exception e) {

		}
		return displayed;
	}

	public static void switchToChildWindow(WebDriver driver) {
		parent = driver.getWindowHandle();
		Set<String> windowHandles =	driver.getWindowHandles();
		Iterator<String> it = windowHandles.iterator();
		String child;
		while(it.hasNext()) {
			child = it.next();
			if(!parent.equalsIgnoreCase(child)) {
				driver.switchTo().window(child);
			}
		}
	}

	//To be use in combination of switchToChildWindow(WebDriver driver)
	public static void switchToParentWindow(WebDriver driver) {
		driver.switchTo().window(parent);
	}

	public static String getChromeDriverPath() throws Exception {
		GenericUtils utils = new GenericUtils();
		ClassLoader cl = utils.getClass().getClassLoader();
		URL resource;
		resource = cl.getResource("bin/chromedriver");

		if (resource == null) {
			throw new UtilException("Could not access the path bin/chromedriver");
		}

		File f = new File("Driver");
		if (!f.exists()) {
			if (!f.mkdir()) {
				throw new UtilException("Could not create Driver folder");
			}
		}

		File chromeDriver = new File("Driver" + File.separator + "chromedriver");
		if (!chromeDriver.exists()) {
			try {
				if (chromeDriver.createNewFile()) {
					FileUtils.copyURLToFile(resource, chromeDriver);
				} else {
					throw new UtilException("Could not create chromedriver file");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!chromeDriver.setExecutable(true)) {
			throw new UtilException("Could not set chromeDriver Executable");
		}

		return chromeDriver.getAbsolutePath();
	}

	public static String createOutputFolderPath() {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String formattedDate  = simpleDateFormat.format(date);
		String path = "output/screenshots/" + formattedDate+"/";
		File targetFolder = new File(path);
		if(!targetFolder.exists()) {
			targetFolder.mkdir();
		}
	
		
		return path;
	}
}

