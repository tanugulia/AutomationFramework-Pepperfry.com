package utils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class DriverInitialization implements ITest {
	public WebDriver driver;
	public Logger log;
	private ThreadLocal<String> testName = new ThreadLocal<>();
	private ChromeOptions chromeOptions;
	private FirefoxOptions firefoxOptions;
	String os;

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		try {
			DOMConfigurator.configure(Constants.LOG4J_PATH);
			log = Logger.getLogger(this.getClass().getSimpleName());
			Config.loadPropertiesFile();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@BeforeClass(alwaysRun = true)
	@Parameters({ "browser" })
	public void beforeTest(String browser) {
		try {
			try {
				String browserNew = System.getProperty("browser");
				if (browserNew != null) {
					browser = browserNew;
				}
			} catch (Exception e) {
			}
			log = Logger.getLogger(this.getClass().getSimpleName());
			os = System.getProperty("os.name").toLowerCase();
			switch (browser.toLowerCase()) {
			case "chrome":
				if (os.contains("mac")) {
					// Setting new download directory path
					HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
					chromePrefs.put("download.default_directory", Constants.DOWNLOAD_PATH);
					System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_MAC);
					// ChromeOptions options = new ChromeOptions();
					chromeOptions = new ChromeOptions();
					chromeOptions.setExperimentalOption("excludeSwitches",
						    Arrays.asList("--disable-popup-blocking"));
					chromeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
					chromeOptions.setExperimentalOption("prefs", chromePrefs);
					chromeOptions.addArguments("--disable-infobars");
					chromeOptions.addArguments("--disable-popup-blocking");
					// To open the browser in incognito mode
					chromeOptions.addArguments("--incognito");
					// To open the browser in maximized mode
					// chromeOptions.addArguments("--start-fullscreen");
					chromeOptions.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
					driver = new ChromeDriver(chromeOptions);
				} else if (os.contains("win")) {
					// Setting new download directory path
					HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
					chromePrefs.put("download.default_directory", Constants.DOWNLOAD_PATH);
					System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_WINDOWS);
					chromeOptions = new ChromeOptions();
					chromeOptions.setExperimentalOption("prefs", chromePrefs);
					chromeOptions.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
					chromeOptions.addArguments("--disable-infobars");
					// To open the browser in incognito mode
					chromeOptions.addArguments("--incognito");
					// To open the browser in maximized mode
					chromeOptions.addArguments("--start-fullscreen");
					chromeOptions.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
					driver = new ChromeDriver(chromeOptions);
				} else {
					// Setting new download directory path
					HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
					chromePrefs.put("download.default_directory", Constants.DOWNLOAD_PATH);
					System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_LINUX);
					chromeOptions = new ChromeOptions();
					chromeOptions.setExperimentalOption("prefs", chromePrefs);
					chromeOptions.addArguments("--headless");
					chromeOptions.addArguments("--disable-extensions");
					chromeOptions.addArguments("--no-sandbox");
					chromeOptions.addArguments("--disable-infobars");
					chromeOptions.addArguments("--disable-web-security");
					chromeOptions.addArguments("--disable-dev-shm-usage");
					chromeOptions.addArguments("--whitelisted-ips");
					chromeOptions.addArguments("--verbose");
					chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
					driver = new ChromeDriver(chromeOptions);
				}
				break;
			case "firefox":
				if (os.contains("mac")) {
					System.setProperty("webdriver.gecko.driver", Constants.GECKO_DRIVER_MAC);
					firefoxOptions = new FirefoxOptions();
					firefoxOptions.addPreference("browser.download.folderList", 2);
					firefoxOptions.addPreference("browser.helperApps.alwaysAsk.force", false);
					firefoxOptions.addPreference("browser.download.dir", Constants.DOWNLOAD_PATH);
					firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk",
							"application/octet-stream,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
					driver = new FirefoxDriver(firefoxOptions);
				} else if (os.contains("win")) {
					System.setProperty("webdriver.gecko.driver", Constants.GECKO_DRIVER_WINDOWS);
					firefoxOptions = new FirefoxOptions();
					firefoxOptions.addPreference("browser.download.folderList", 2);
					firefoxOptions.addPreference("browser.helperApps.alwaysAsk.force", false);
					firefoxOptions.addPreference("browser.download.dir", Constants.DOWNLOAD_PATH);
					firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk",
							"application/octet-stream,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
					driver = new FirefoxDriver(firefoxOptions);
				} else {
					System.setProperty("webdriver.gecko.driver", Constants.GECKO_DRIVER_LINUX);
					firefoxOptions = new FirefoxOptions();
					firefoxOptions.addArguments("--headless");
					firefoxOptions.addArguments("--disable-extensions");
					firefoxOptions.addArguments("--no-sandbox");
					firefoxOptions.addArguments("--disable-infobars");
					firefoxOptions.addArguments("--disable-web-security");
					firefoxOptions.addArguments("--disable-dev-shm-usage");
					firefoxOptions.addArguments("--whitelisted-ips");
					firefoxOptions.addArguments("--verbose");
					firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
					firefoxOptions.addPreference("browser.download.folderList", 2);
					firefoxOptions.addPreference("browser.helperApps.alwaysAsk.force", false);
					firefoxOptions.addPreference("browser.download.dir", Constants.DOWNLOAD_PATH);
					firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk",
							"application/csv,text/csv,application/excel, application/vnd.ms-excel, application/x-excel, application/x-msexcel");
					driver = new FirefoxDriver(firefoxOptions);
				}
				break;
			case "safari":
				driver = new SafariDriver();
				break;
			case "ie":
				driver = new InternetExplorerDriver();
				break;
			default:
				log.info("invalid browser name");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@AfterClass(alwaysRun = true)
	public void closeBrowser() {
		System.gc();
		try {
			driver.close();
		} catch (Exception e) {
		}
	}

	@Override
	public String getTestName() {
		return testName.get();
	}

	@BeforeMethod
	public void BeforeMethod(Method method, Object[] testData, ITestContext ctx) {
		if (testData.length > 0) {
			testName.set(method.getName() + "_" + testData[0]);
			ctx.setAttribute("testName", testName.get());
		} else
			testName.set(method.getName());
		ctx.setAttribute("testName", testName.get());
	}
}