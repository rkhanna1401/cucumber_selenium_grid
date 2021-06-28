package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryListener implements IRetryAnalyzer{

	int counter =0;
	int retry = 1;
	
	@Override
	public boolean retry(ITestResult result) {
		if(counter < retry) {
			counter ++;
			return true;
		}
		else
		return false;
	}

}
