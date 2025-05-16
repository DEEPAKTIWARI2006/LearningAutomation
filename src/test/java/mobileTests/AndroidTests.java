package mobileTests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;
import utilities.MobileDriverManager;

public class AndroidTests {
	
	private static final Logger logger = LogManager.getLogger(AndroidTests.class);
	private AppiumDriver mobiledriver;


	public AndroidTests() {
		this.mobiledriver = MobileDriverManager.getDriver();
	}
	
	public void mobileTest1() {
		
		mobiledriver.findElement(By.partialLinkText(null)).click();
	}

}
