package stepDefs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import io.appium.java_client.AppiumDriver;
import utilities.DriverManager;

public class AndroidTestMethods {
	
	private static final Logger logger = LogManager.getLogger(AndroidTestMethods.class);
	private WebDriver driver;
	private AppiumDriver mobiledriver;

	public AndroidTestMethods() {
		this.driver = DriverManager.getDriver();

	}

}
