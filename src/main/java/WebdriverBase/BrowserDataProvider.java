package WebdriverBase;

import org.testng.annotations.DataProvider;

import enums.BrowserList;
import enums.PlatformList;

public class BrowserDataProvider {

	@DataProvider(name="browser",parallel = true)
	public static Object[][] browser(){
		return new Object[][]{

			{BrowserList.CHROME,PlatformList.WINDOWS},
			{BrowserList.FIREFOX,PlatformList.WINDOWS},
			{BrowserList.SAFARI,PlatformList.MAC}

		};

	}
}
