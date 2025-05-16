package stepDefs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utilities.DriverManager;
import utilities.MobileDriverManager;

public class CucumberMethods {

	private static final Logger logger = LogManager.getLogger(CucumberMethods.class);
	private WebDriver driver;
	private AppiumDriver mobiledriver;
	private LoginPage loginPage;

	public CucumberMethods() {
		this.driver = DriverManager.getDriver();

	}

	@Given("User is on the Main Landing screen")
	public void user_is_on_the_main_landing_screen() {
		logger.info("Navigating to the login page.");
		driver.get("https://www.screener.in");
		loginPage = new LoginPage(driver);
		loginPage.isLoginButtonDisplayed();
		if (loginPage.isLoginButtonDisplayed()) {
			logger.info("Login page is loaded.");
		}
	}

	@When("User enters valid credentials {string} {string}")
	public void user_enters_valid_credentials(String username, String password) {
		loginPage.loginAs(username, password);
	}

	@Then("User should login successfully {string}")
	public void user_should_login_successfully(String string) {
		Assert.assertEquals(loginPage.getProfileButtonText(), string, "Text validation failed!");
	}
}