package pages;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
	private static final Logger logger = LogManager.getLogger(LoginPage.class);
    private WebDriver driver;

    // Locators
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton = By.xpath("//button[@type='submit']");
    private final By launchLoginBtn = By.xpath("//a[contains(@href,'login') and @class='button account']");
    private final By profileBtn = By.xpath("//div[@id='account-dropdown']/button[@type='button']");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Page Actions
    public void enterUsername(String username) {
    	logger.info("Entering User Name - " + username);
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
    	logger.info("Entering password - " + password);
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
    
    public void launchLoginPage() {
    	driver.findElement(launchLoginBtn).click();
    }

    public void loginAs(String username, String password) {
    	launchLoginPage();
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // Validation or getter method
    public boolean isLoginButtonDisplayed() {
    	// Wait for the login page to load
    			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    			wait.until(ExpectedConditions
    					.visibilityOfElementLocated(launchLoginBtn));
        return driver.findElement(launchLoginBtn).isDisplayed();
    }
    
    public String getProfileButtonText() {
    	return  driver.findElement(profileBtn).getText();
    }
}
