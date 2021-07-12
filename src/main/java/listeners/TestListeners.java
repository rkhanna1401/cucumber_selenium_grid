package listeners;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListeners implements ITestListener, ISuiteListener{

	private static Logger logger;

	@Override
	public void onTestStart(ITestResult iTestResult) {
		logger.log(Level.INFO, iTestResult.getMethod().getMethodName() + "test has started");
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		logger.log(Level.INFO, iTestResult.getMethod().getMethodName() + "test is passed");

	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		
	}



	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		logger.log(Level.INFO, iTestResult.getMethod().getMethodName() + "test is skipped execution");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
	}

	@Override
	public void onStart(ITestContext context) {

	}

	@Override
	public void onFinish(ITestContext context) {

	}

	@Override
	public void onStart(ISuite suite) {
		logger = Logger.getLogger(TestListeners.class.getName());
	}

	@Override
	public void onFinish(ISuite suite) {
		logger.log(Level.INFO, "Suite execution completed. Please check the reports");
	}

}
