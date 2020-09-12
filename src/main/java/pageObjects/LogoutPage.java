package pageObjects;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utils.Config;
import utils.SeleniumUtils;

public class LogoutPage extends SeleniumUtils {
	private Logger log;

	public LogoutPage(WebDriver driver) {
		super(driver);
		log = LogManager.getLogger(this.getClass().getSimpleName());
	}

	public boolean logout() {
		boolean flag = false;
		try {
			// mouse hover over username
			mouseHover(Config.getLocator("homeLoggedInUserName"));
			// click on logout link
			click(Config.getLocator("logoutLink"));
			waitFor(2);
			// logout confirmation
			if (getElementCount(Config.getLocator("homeLoggedInUserName")) == 0) {
				log.info("Logged out successfully");
				flag = true;
				;
			} else {
				log.error("User has not logged nout");
			}
		} catch (Exception e) {
			log.error("Failed to logout due to " + e.getMessage());
			Assert.fail("Failed to logout due to " + e.getMessage());
		}
		return flag;
	}
}
