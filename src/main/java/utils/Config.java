package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.testng.Assert;

public class Config {
	static Properties configProp, objectRepoProp, testDataProp;

	public static void loadPropertiesFile() {
		try {
			configProp = new Properties();
			objectRepoProp = new Properties();
			testDataProp = new Properties();
			FileInputStream confRepo = new FileInputStream(Constants.CONFIG_REPOSITORY);
			FileInputStream objectRepo = new FileInputStream(Constants.OBJECT_REPOSITORY);
			FileInputStream testDataRepo = new FileInputStream(Constants.TESTDATA_REPOSITORY);
			configProp.load(confRepo);
			objectRepoProp.load(objectRepo);
			testDataProp.load(testDataRepo);
			confRepo.close();
			objectRepo.close();
			testDataRepo.close();
		} catch (IOException e) {
			Assert.fail("Failed to load properties file" + e.getMessage());
		}
	}

	public static String getConfigData(String key) {
		String propertyValue = null;
		try {
			propertyValue = configProp.getProperty(key);
			if (propertyValue == null) {
				System.out.println("Unable to find the key value for the given key \"" + key + "\"");
				Assert.fail("Unable to find the key value for the given key \"" + key + "\"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return propertyValue.trim();
	}

	public static By getLocator(String strElement) {

		// retrieve the specified object from the object list
		String locator = objectRepoProp.getProperty(strElement);
		if (locator == null) {
			System.out.println("No value is present for the key \"" + strElement + "\" in the object repository");
			Assert.fail("No value is present for the key \"" + strElement + "\" in the object repository");
		}

		// extract the locator type and value from the object
		String[] locatorSplit = locator.split(":", 2);
		String locatorType = locatorSplit[0];
		String locatorValue = locatorSplit[1];

		// return an instance of the By class based on the type of the locator
		// this By can be used by the browser object in the actual test
		if (locatorType.toLowerCase().equals("id"))
			return By.id(locatorValue);
		else if (locatorType.toLowerCase().equals("name"))
			return By.name(locatorValue);
		else if ((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
			return By.className(locatorValue);
		else if ((locatorType.toLowerCase().equals("tagname")) || (locatorType.toLowerCase().equals("tag")))
			return By.tagName(locatorValue);
		else if ((locatorType.toLowerCase().equals("linktext")) || (locatorType.toLowerCase().equals("link")))
			return By.linkText(locatorValue);
		else if (locatorType.toLowerCase().equals("partiallinktext"))
			return By.partialLinkText(locatorValue);
		else if ((locatorType.toLowerCase().equals("cssselector")) || (locatorType.toLowerCase().equals("css")))
			return By.cssSelector(locatorValue);
		else if (locatorType.toLowerCase().equals("xpath"))
			return By.xpath(locatorValue);
		else {
			Assert.fail("Unknown locator type '" + locatorType + "'");
			return null;
		}
	}

	public static String getTestData(String key) {
		String propertValue = null;
		try {
			propertValue = testDataProp.getProperty(key);
			if (propertValue == null) {
				System.out.println("Unable to find the key value for the given key \"" + key + "\"");
				Assert.fail("Unable to find the key value for the given key \"" + key + "\"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return propertValue.trim();
	}
}