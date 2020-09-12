package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.google.common.io.Files;
import com.relevantcodes.extentreports.LogStatus;

import listeners.ScreenshotListener;

public class SeleniumUtils {
	private final String failedScreenShotsFolderPath = System.getProperty("user.dir") + File.separator + "target"
			+ File.separator + "FailedTestsScreenshots/";
	protected WebDriver driver;
	private Logger log;
	private final int counter = 60;

	public SeleniumUtils(WebDriver driver) {
		this.driver = driver;
		log = LogManager.getLogger(this.getClass().getSimpleName());
	}

	final public void get(String url) {
		try {
			driver.get(url);
		} catch (Exception e) {
			log.error("Unable to hit the URL " + url + "due to " + e.getMessage());
			fail("Unable to hit the URL " + url + "due to " + e.getMessage());
		}
	}

	public String getCurrentURL() {
		String currentURL = null;
		try {
			currentURL = driver.getCurrentUrl();
		} catch (Exception e) {
			log.error("Unable to get current url due to " + e.getMessage());
			fail("Unable to get current url due to " + e.getMessage());
		}
		return currentURL;
	}

	public String getTitle() {
		String title = null;
		try {
			title = driver.getTitle();
		} catch (Exception e) {
			log.error("Unable to get current url due to " + e.getMessage());
			fail("Unable to get current url due to " + e.getMessage());
		}
		return title;
	}

	final public void click(By element) {
		try {
			scroll(element);
			waitUntilElementIsClickable(element);
			driver.findElement(element).click();
			log.info("successfully clicked on element " + element);
		} catch (Exception e) {
			log.error(element + " cannot be clicked due to " + e.getMessage());
			fail(element + " cannot be clicked due to " + e.getMessage());
		}
	}

	final public void click(WebElement element) {
		try {
			scroll(element);
			waitUntilWebElementIsVisible(element);
			element.click();
			log.info("successfully clicked on element " + element);
		} catch (TimeoutException e) {
		} catch (Exception e) {
			log.error(element + " cannot be clicked due to " + e.getMessage());
			fail(element + " cannot be clicked due to " + e.getMessage());
		}
	}

	final public void waitUntilElementIsClickable(By element) {
		try {
			boolean flag = false;
			String message = null;
			waitUntilElementIsVisible(element);
			for (int i = 0; i < counter; i++) {
				try {
					implicitlyWait(0);
					if (driver.findElement(element).isEnabled()) {
						flag = true;
						break;
					} else {
						log.info("waiting for element to be clickable " + i + "seconds");
						waitFor(1);
					}
				} catch (Exception e) {
					log.info("Waiting for element to be clickable " + i + " seconds");
					waitFor(1);
					message = e.getMessage();
				}
			}
			if (!flag) {
				log.error(element + " is not clickable till " + counter + " seconds due to " + message);
				fail(element + " is not clickable till " + counter + " seconds due to " + message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			implicitlyWait(60);
		}
	}

	final public void waitUntilElementIsVisible(By element) {
		try {
			boolean flag = false;
			String message = null;
			for (int i = 0; i < counter; i++) {
				try {
					implicitlyWait(0);
					if (driver.findElement(element).isDisplayed()) {
						flag = true;
						break;
					} else {
						log.info("waiting for element to be visible!!! " + i + "seconds");
						waitFor(1);
					}
				} catch (NoSuchElementException | StaleElementReferenceException e) {
					log.info("Waiting for element to be visible !!! " + i + " seconds :: " + element);
					waitFor(1);
				} catch (Exception e) {
					log.info("Waiting for element to be visible!!! " + i + " seconds");
					waitFor(1);
					message = e.getMessage();
				}
			}
			if (!flag) {
				log.error(element + " is not visible till " + counter + " seconds due to " + message);
				fail(element + " is not visible till " + counter + " seconds due to " + message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			implicitlyWait(60);
		}
	}

	final public void waitUntilWebElementIsVisible(WebElement element) {
		try {
			int counter = 0;
			while (!element.isDisplayed()) {
				if (counter > 60) {
					log.error("Failed to Display Element  " + element + " in " + counter + " secs");
					break;
				} else {
					counter++;
					waitFor(1);
					log.info("Loading...!!!" + counter + " secs over element :: " + element);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	final public void waitUntilElementIsInVisible(By element) {
		try {
			int i;
			for (i = 0; i < counter; i++) {
				try {
					implicitlyWait(0);
					boolean flag = driver.findElement(element).isDisplayed();
					if (!flag) {
						break;
					} else {
						log.info("Waiting for element to be Invisible !!! " + i + " seconds " + element);
						waitFor(1);
					}
				} catch (StaleElementReferenceException r) {
					log.info("Waiting for element to be Invisible !!! " + i + " seconds " + element);
					waitFor(1);
				} catch (NoSuchElementException e) {
					break;
				} catch (Exception e) {
					log.error("Cannot Verify Whether element is displayed or not due to " + e.getMessage());
					fail("Cannot Verify Whether element is displayed or not due to " + e.getMessage());
				}
			}
			if (i >= counter) {
				log.error(element + " Element is  displayed after " + i + " seconds");
				fail(element + " Element is  displayed after " + i + " seconds");
			}
		} catch (Exception e) {
		} finally {
			implicitlyWait(60);
		}

	}

	final public void clear(WebElement element) {
		try {
			click(element);
			element.clear();
		} catch (Exception e) {
			log.error("unable to clear data present in input box for element " + element + " due to " + e.getMessage());
			fail("unable to clear data present in input box for element " + element + " due to " + e.getMessage());
		}
	}

	final public void clear(By element) {
		try {
			click(element);
			findElement(element).clear();
		} catch (Exception e) {
			log.error("unable to clear data present in input box for element " + element + " due to " + e.getMessage());
			fail("unable to clear data present in input box for element " + element + " due to " + e.getMessage());
		}
	}

	final public void sendKeys(By element, String keys) {
		try {
			clear(element);
			findElement(element).sendKeys("");
			findElement(element).sendKeys(keys.trim());
			log.info("succesfully sent the keys:: \"" + keys + "\" to the input box of element:: " + element);
		} catch (Exception e) {
			log.error("unable to send \"" + keys + "\" keys to input box " + element + " due to " + e.getMessage());
			fail("unable to send \"" + keys + "\" keys to input box " + element + " due to " + e.getMessage());
		}
	}

	final public void sendKeys(WebElement element, String keys) {
		try {
			clear(element);
			element.sendKeys("");
			element.sendKeys(keys);
			log.info("succesfully sent the keys:: \"" + keys + "\" to the input box of element:: " + element);
		} catch (Exception e) {
			log.error("unable to send \"" + keys + "\" keys to input box " + element + " due to " + e.getMessage());
			fail("unable to send \"" + keys + "\" keys to input box " + element + " due to " + e.getMessage());
		}
	}

	final public void actionSendShortcutKeys(Keys keys) {
		Actions act = new Actions(driver);
		try {
			act.sendKeys(keys).build().perform();
			waitFor(0.5f);
			act.release();
			log.info("succesfully sent shortcut keys:: " + keys);
		} catch (Exception e) {

			log.error("unable to send \"" + keys + "\" keys due to " + e.getMessage());
			fail("unable to send \"" + keys + "\" keys  due to " + e.getMessage());
		}
	}

	final public WebElement findElement(By element) {
		WebElement webElement = null;
		try {
			waitUntilElementIsPresent(element);
			webElement = driver.findElement(element);
		} catch (Exception e) {
			log.error("unable to find the element " + element + " due to " + e.getMessage());
			fail("unable to find the element " + element + " due to " + e.getMessage());
		}
		return webElement;
	}

	final public List<WebElement> findElements(By elements) {
		List<WebElement> element = null;
		try {
			// WaitUntilElementIsPresent(elements);
			element = driver.findElements(elements);
		} catch (Exception e) {
			log.error("unable to find elements due to " + e.getMessage());
			fail("unable to find elements due to " + e.getMessage());
		}
		return element;
	}

	final public void waitUntilElementIsPresent(By element) {
		try {
			boolean flag = false;
			for (int i = 0; i < counter; i++) {
				try {
					implicitlyWait(0);
					driver.findElement(element);
					flag = true;
					break;
				} catch (NoSuchElementException | StaleElementReferenceException e) {
					log.info("Waiting for element to be present in DOM " + i + " seconds element:: " + element);
					waitFor(1);
				} catch (Exception e) {
					e.getStackTrace();
					break;
				}
			}
			if (flag) {
			} else {
				log.error("Element is not present in DOM element:: " + element);
				fail("Element is not present in DOM element:: " + element);
			}
		} catch (Exception e) {
		} finally {
			implicitlyWait(60);
		}
	}

	final public void scroll(By element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			waitUntilElementIsPresent(element);
			js.executeScript("arguments[0].scrollIntoView(false);", driver.findElement(element));
		} catch (Exception e) {
			log.error("Failed to scroll to " + element + " due to " + e.getMessage());
			fail("Failed to scroll to " + element + " due to " + e.getMessage());
		} finally {
			js = null;
			Runtime.getRuntime().gc();
		}
	}

	final public void scroll(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			js.executeScript("arguments[0].scrollIntoView(false)", element);
		} catch (Exception e) {
			log.error("Failed to scroll to " + element + " due to " + e.getMessage());
			fail("Failed to scroll to " + element + " due to " + e.getMessage());
		} finally {
			js = null;
			Runtime.getRuntime().gc();
		}
	}

	final public void scrollIntoView(By element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			waitUntilElementIsPresent(element);
			js.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(element));
		} catch (Exception e) {
			log.error("Failed to scroll to " + element + " due to " + e.getMessage());
			fail("Failed to scroll to " + element + " due to " + e.getMessage());
		} finally {
			js = null;
		}
	}

	final public void scrollIntoView(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			js.executeScript("arguments[0].scrollIntoView(true)", element);
		} catch (Exception e) {
			log.error("Failed to scroll to " + element + " due to " + e.getMessage());
			fail("Failed to scroll to " + element + " due to " + e.getMessage());
		} finally {
			js = null;
		}
	}

	final public String getText(By element) {
		String value = null;
		try {
			scroll(element);
			waitUntilElementIsVisible(element);
			value = findElement(element).getText();
			log.info(" Text of the element :: " + element + " is ::" + value);
		} catch (Exception e) {
			log.error("Cannot get the text of element " + element + " due to " + e.getMessage());
			fail("Cannot get the text of element " + element + " due to " + e.getMessage());
		}
		return value.trim();
	}

	final public String getText(WebElement element) {
		String value = null;
		try {
			scroll(element);
			value = element.getText();
		} catch (Exception e) {
			log.error("Cannot get the text of element " + element + " due to " + e.getMessage());
			fail("Cannot get the text of element " + element + " due to " + e.getMessage());
		}
		return value.trim();
	}

	final public String getAttribute(By element, String attribute) {
		String value = null;
		try {
			value = findElement(element).getAttribute(attribute);
		} catch (Exception e) {
			log.error("Cannot get the Attribute :: " + attribute + " value of element " + element + " due to "
					+ e.getMessage());
			fail("Cannot get the Attribute :: " + attribute + " value of element " + element + " due to "
					+ e.getMessage());
		}
		return value;
	}

	final public String getAttribute(WebElement element, String attribute) {
		String value = null;
		try {
			value = element.getAttribute(attribute);
		} catch (Exception e) {
			log.error("Cannot get the Attribute :: " + attribute + " value of element " + element + " due to "
					+ e.getMessage());
			fail("Cannot get the Attribute :: " + attribute + " value of element " + element + " due to "
					+ e.getMessage());
		}
		return value;
	}

	final public void waitFor(float f) {
		try {
			Thread.sleep((long) f * 1000);
		} catch (InterruptedException e) {
		}
	}

	final public void implicitlyWait(int time) {
		try {
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	final public void close() {
		driver.close();
	}

	final public boolean isDisplayed(By element) {
		boolean flag = false;
		try {
			for (int i = 0; i < counter; i++) {
				try {
					implicitlyWait(0);
					flag = driver.findElement(element).isDisplayed();
					if (flag) {
						flag = true;
						break;
					} else {
						waitFor(1);
					}
				} catch (Exception e) {
					waitFor(1);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			implicitlyWait(60);
		}
		return flag;
	}

	final public boolean isDisplayed(WebElement element) {
		boolean flag = false;
		try {
			for (int i = 0; i < counter; i++) {
				try {
					flag = element.isDisplayed();
					if (flag) {
						flag = true;
						break;
					} else {
						waitFor(1);
					}
				} catch (NoSuchElementException e) {
					waitFor(1);
				}
			}
		} catch (Exception e) {

			log.error(e.getMessage());
		}
		return flag;
	}

	final public void fail(String failureMessage) {
		try {
			String methodName = getPresentRunningTestName();
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			new File(failedScreenShotsFolderPath).mkdirs(); // Ensure directory
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String destination = System.getProperty("user.dir") + "/target/FailedTestsScreenshots/" + methodName
					+ timeStamp + ".png";
			File finalDestination = new File(destination);
			Files.copy(source, finalDestination);
			ScreenshotListener.logger.log(LogStatus.INFO, "Failure message is :: " + failureMessage);
			ScreenshotListener.logger.log(LogStatus.FAIL, ScreenshotListener.logger.addScreenCapture(destination));
		} catch (Exception e) {
		} finally {
			log.error(failureMessage);
			get(Config.getConfigData("login_url"));
			waitFor(2);
			// wait until small loading symbol is invisible
			waitFor(2);
			waitUntilElementIsInVisible(Config.getLocator("genericLoadingSymbol"));
			org.testng.Assert.fail(failureMessage);
		}
	}

	final public void fail() {
		String methodName = null;
		try {
			methodName = getPresentRunningTestName();
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			new File(failedScreenShotsFolderPath).mkdirs(); // Ensure directory
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String destination = System.getProperty("user.dir") + "/target/FailedTestsScreenshots/" + methodName
					+ timeStamp + ".png";
			File finalDestination = new File(destination);
			Files.copy(source, finalDestination);
			ScreenshotListener.logger.log(LogStatus.FAIL, ScreenshotListener.logger.addScreenCapture(destination));
		} catch (Exception e) {
		} finally {
			log.error(methodName + " has failed due to " + null);
			get(Config.getConfigData("login_url"));
			waitFor(2);
			// wait until small loading symbol is invisible
			waitFor(2);
			waitUntilElementIsInVisible(Config.getLocator("genericLoadingSymbol"));
			org.testng.Assert.fail(methodName + " has failed due to " + null);
		}
	}

	final private String getPresentRunningTestName() {
		String testName = null;
		StackTraceElement[] stack = new Exception().getStackTrace();
		for (int i = 0; i < stack.length; i++) {
			if (stack[i].getMethodName().contains("invoke0")) {
				testName = stack[i - 1].getMethodName();
				break;
			}
		}
		return testName;
	}

	final public void mouseHover(WebElement element) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(element).perform();
		} catch (Exception e) {
			log.error("Unable to mouse hover to element: " + element + " due to " + e.getMessage());
		}
	}

	final public void mouseHover(By element) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(findElement(element)).perform();
		} catch (Exception e) {
			log.error("Unable to mouse hover to element: " + element + " due to " + e.getMessage());
		}
	}

	final public int getElementCount(By element) {
		int count = 0;
		try {
			implicitlyWait(0);
			count = driver.findElements(element).size();
		} catch (Exception e) {
		}
		implicitlyWait(60);
		return count;
	}

	final public void executeScript(String Script) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			js.executeScript(Script);
		} catch (Exception e) {

			log.error("Cannot execute javascript :: " + Script + " due to " + e.getMessage());
			fail("Cannot execute javascript :: " + Script + " due to " + e.getMessage());
		} finally {
			js = null;
		}
	}

}