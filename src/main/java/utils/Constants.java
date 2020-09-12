package utils;

import java.io.File;

public class Constants{
	//set your project folder here
	public static final String PROJECT_HOME = System.getProperty("user.dir");
	
	//Drivers Path
	public static final String CHROME_DRIVER_MAC = PROJECT_HOME + File.separator + "Drivers" + File.separator + "chromedriver_mac";
	public static final String GECKO_DRIVER_MAC = PROJECT_HOME + File.separator + "Drivers" + File.separator + "geckodriver_mac";
	public static final String CHROME_DRIVER_WINDOWS = PROJECT_HOME + File.separator + "Drivers" + File.separator + "chromedriver_windows.exe";
	public static final String GECKO_DRIVER_WINDOWS = PROJECT_HOME + File.separator + "Drivers" + File.separator + "geckodriver_windows.exe";
	public static final String CHROME_DRIVER_LINUX = PROJECT_HOME + File.separator + "Drivers" + File.separator + "chromedriver_linux";
	public static final String GECKO_DRIVER_LINUX = PROJECT_HOME + File.separator + "Drivers" + File.separator + "geckodriver_linux";
	
	//Config path
	public static final String LOG4J_PATH = PROJECT_HOME + File.separator + "log4j" + File.separator + "log4j.xml";
	
	//repository
	public static final String CONFIG_REPOSITORY = PROJECT_HOME + File.separator + "Config" + File.separator + "config.properties";
	public static final String OBJECT_REPOSITORY = PROJECT_HOME + File.separator + "Config" + File.separator + "objectRepository.properties";
	public static final String TESTDATA_REPOSITORY = PROJECT_HOME + File.separator + "Config" + File.separator + "testData.properties";
	
	//download path
	public static final String DOWNLOAD_PATH = PROJECT_HOME + File.separator + "target";

}