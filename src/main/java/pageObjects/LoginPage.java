package pageObjects;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utils.Config;
import utils.SeleniumUtils;

public class LoginPage extends SeleniumUtils {
	private Logger log;

	public LoginPage(WebDriver driver) {
		super(driver);
		log = LogManager.getLogger(this.getClass().getSimpleName());
	}

	public void loginToTheInstance(String username, String password) {
		try {
			// hit the instance url
			get(Config.getConfigData("login_url"));
			//click on Login Link
			click(Config.getLocator("loginOrRegisterLink"));
			//wait for the register popup
			waitUntilElementIsVisible(Config.getLocator("loginOrRegisterPopUp"));
			//click on Existing User Button
			click(Config.getLocator("loginExistingUser"));
			//wait for the login popup
			waitUntilElementIsVisible(Config.getLocator("loginPopUp"));
			// enter the username
			sendKeys(Config.getLocator("loginUserNameInputBox"), username);
			// enter the password
			sendKeys(Config.getLocator("loginPasswordInputBox"), password);
			//click on Log In
			click(Config.getLocator("loginButton"));
			//wait for the login popup
			waitUntilElementIsInVisible(Config.getLocator("loginPopUp"));
			if(isDisplayed(Config.getLocator("homeLoggedInUserName"))) {
				log.info("Logged in successfully");
			} else {
				log.error("Login failed");
				Assert.fail("Login failed");						
			}
		} catch (Exception e) {
			log.error("Failed to Log In due to " + e.getMessage());
			Assert.fail("Failed to Log In due to " + e.getMessage());
		}
	}
	
}