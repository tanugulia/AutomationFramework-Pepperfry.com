package pageObjects;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import utils.Config;
import utils.SeleniumUtils;

public class GenericPage extends SeleniumUtils {
	private Logger log;

	public GenericPage(WebDriver driver) {
		super(driver);
		log = LogManager.getLogger(this.getClass().getSimpleName());
	}

	public void checkLoadingSymbol() {
		try {
			for (int i = 0; i < 60; i++) {
				String visibility = getAttribute(Config.getLocator("genericLoadingSymbol"), "style");
				if (visibility.equalsIgnoreCase(Config.getTestData("genericLoadingSymbolVisibile"))) {
					log.info("Loading symbol is displayed. Waiting for " + i + " sec");
					waitFor(1);
				} else {
					break;
				}
			}
		} catch (Exception e) {
		}
	}

	public void closeIFrameIfExists() {
		try {
			executeScript(Config.getTestData("genericClosePopUp"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

}
