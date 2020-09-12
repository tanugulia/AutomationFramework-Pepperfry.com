package listeners;
import java.io.File;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ScreenshotListener implements ITestListener{
	private Logger log=Logger.getLogger(this.getClass().getSimpleName());
	static ExtentReports extent= new ExtentReports(System.getProperty("user.dir")+File.separator+"target"+File.separator+"ExtentReport.html");
	public static ExtentTest logger;
	private String suiteName;
	public String fileName;

	@Override
	public void onStart(ITestContext context) {
		log.info("****************************************************************************************");
		log.info("                                " + context.getName() + "       ");
		log.info("----------------------------------------------------------------------------------------");
		extent.loadConfig(new File(System.getProperty("user.dir")+File.separator+"ExternalJARS"+File.separator+"extent-config.xml"));
		suiteName=context.getName();
	}

	@Override
	public void onTestStart(ITestResult result) {
		log.info("\n\n" + "<< --- TestCase START --->> " + result.getName() + "\n");
		logger = extent.startTest(suiteName + " # " + result.getName().toString());
		logger.log(LogStatus.INFO,
				"<b><i>Description of the test :: </b></i> \"" + result.getMethod().getDescription() + "\"");
		fileName = result.getName();
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		log.info("\n\n TestCase: " + result.getName() + ": --->>> PASS \n");
		logger.log(LogStatus.PASS, result.getName().toString() + " test has passed");
		extent.endTest(logger);		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		logger = extent.startTest(suiteName + " # " + result.getName().toString());
		logger.log(LogStatus.INFO,
				"<b><i>Description of the test :: </b></i>\"" + result.getMethod().getDescription() + "\"");
		logger.log(LogStatus.SKIP, result.getName().toString() + " test has Skipped");
		extent.endTest(logger);		
		log.info("\n\n TestCase: " + result.getName() + ": --->>> SKIPPED");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		log.info("***** Error " + result.getName() + " test has failed *****");
		logger.log(LogStatus.FAIL, result.getName().toString() + " test has failed");
		extent.endTest(logger);
	}	

	@Override
	public void onFinish(ITestContext context) {
		log.info("----------------------------------------------------------------------------------------\n");
		log.info("****************************************************************************************\n");
		extent.flush();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		log.info("\n\n TestCase: " + result.getName() + ": --->>> FAILED With percentage");
	}
}