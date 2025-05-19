package stepDefs;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utilities.ConfigReader;
import utilities.DriverManager;

public class Hooks {

	private static final Logger logger = LogManager.getLogger(Hooks.class);
	public static Map<String, List<String>> tagSummaryMap = new HashMap<>();
	public static String browser;
	public static String platform;
	WebDriver driver = null;
	AppiumDriver mobiledriver = null;

	@Before
	public void setUp(Scenario scenario) {

		browser = System.getProperty("browser");
		platform = System.getProperty("platform");

		if (browser == null) {
			browser = ConfigReader.get("browser").toLowerCase();
		}
		
		if (platform == null) {
			platform = ConfigReader.get("platform").toLowerCase();
		}

		logger.info("Starting tests with browser: {}", browser);

		try {
			switch (browser) {
			case "chrome":
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver.exe");
				driver = new ChromeDriver();
				break;

			case "firefox":
				if (platform.equalsIgnoreCase("linux")) {
					System.setProperty("webdriver.gecko.driver",
							System.getProperty("user.dir") + "/src/test/resources/drivers/linux64/geckodriver");
					FirefoxOptions options = new FirefoxOptions();
					options.addArguments("--headless");
			        driver = new FirefoxDriver(options);
				}else {
					System.setProperty("webdriver.gecko.driver",
							System.getProperty("user.dir") + "/src/test/resources/drivers/geckodriver.exe");
					driver = new FirefoxDriver();
				}
				
				
				break;

			case "edge":
				System.setProperty("webdriver.edge.driver",
						System.getProperty("user.dir") + "/src/test/resources/drivers/msedgedriver.exe");
				driver = new EdgeDriver();
				break;

			case "android":
				try {
					String apkPath = System.getProperty("user.dir") + "/src/test/resources/apks/simple-clinic-demo-app.apk";
					DesiredCapabilities caps = new DesiredCapabilities();
					caps.setCapability("platformName", "Android");
					caps.setCapability("appium:chromedriverExecutable", System.getProperty("user.dir") + "/src/test/resources/drivers/ChromeV83/chromedriver.exe");
					caps.setCapability("appium:deviceName", "emulator-5554"); // or your real device name
					caps.setCapability("appium:automationName", "UiAutomator2");
					caps.setCapability("appium:browserName", "Chrome");
					//caps.setCapability("appium:appActivity", "org.simple.clinic.setup.SetupActivity");
					//caps.setCapability("appium:ensureWebviewsHavePages", true);
					//caps.setCapability("appium:newCommandTimeout", 10);
					//caps.setCapability("appium:nativeWebScreenshot", true);
					//caps.setCapability("appium:app", apkPath);
					//caps.setCapability("appium:chromedriverAutodownload", true);
					//caps.setCapability("appium:chromedriver_autodownload", true);

					//mobiledriver = new AndroidDriver(URI.create("http://127.0.0.1:4723/").toURL(), caps);
					driver = new AndroidDriver(URI.create("http://127.0.0.1:4723/").toURL(), caps);
					driver.get("https://www.screener.in");
					Thread.sleep(2000);
					driver.findElement(By.xpath("//nav/ul/li[5]")).click();
					break;

				} catch (Exception e) {
					e.printStackTrace();
					logger.logMessage(null, null, e.getMessage(), null, null, e);
					throw new RuntimeException("Failed to start Appium session");
				}

			default:
				throw new RuntimeException("Unsupported Platform: " + browser);
			}
			
			DriverManager.setDriver(driver);
			
			/*
			 * if (browser == "android") { MobileDriverManager.setDriver(mobiledriver);
			 * }else { DriverManager.setDriver(driver); }
			 */

			logger.info("WebDriver initialized successfully");

		} catch (Exception e) {
			logger.error("Failed to initialize WebDriver: {}", e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@After
	public void tearDown(Scenario scenario) {
		logger.info("Test execution completed. Closing WebDriver.");
		/*
		 * if (mobiledriver != null) { MobileDriverManager.quitDriver(); }
		 */

		if (driver != null) {
			DriverManager.quitDriver();
		}
		// summaries.add(scenario.getName() + " - " + scenario.getStatus().name());
		String result = scenario.getName() + " - " + scenario.getStatus().name();

		for (String tag : scenario.getSourceTagNames()) {
			tagSummaryMap.computeIfAbsent(tag, k -> new ArrayList<>()).add(result);
		}
	}

	@AfterStep
	public void afterStep(Scenario scenario) {
		if (scenario.isFailed()) {
			File screenshotFile = null;
			logger.error("Step failed: {}", scenario.getName());
			WebDriver driver = DriverManager.getDriver();
			//AppiumDriver mobiledriver = MobileDriverManager.getDriver();
			
			if(driver != null) {
				screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			}else if(mobiledriver != null){
				screenshotFile = ((TakesScreenshot) mobiledriver).getScreenshotAs(OutputType.FILE);
			}
				
				String screenshotName = "screenshot_" + System.currentTimeMillis() + ".png";
				String screenshotPath = "target/extent-reports/screenshots/" + screenshotName;
				new File("target/extent-reports/screenshots/").mkdirs();
				try {
					Files.copy(screenshotFile.toPath(), Paths.get(screenshotPath), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String relPath = "./screenshots/" + screenshotName;
				String html = "<a href='" + relPath + "' target='_blank'>" + "<img src='" + relPath
						+ "' height='200' /></a>";

				scenario.log("Related snapshot link " + html);

		}
	}
}