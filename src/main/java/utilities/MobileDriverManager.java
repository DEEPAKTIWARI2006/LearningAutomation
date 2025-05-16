package utilities;

import io.appium.java_client.AppiumDriver;

public class MobileDriverManager {

    private static ThreadLocal<AppiumDriver> mobiledriver = new ThreadLocal<>();
    

    public static AppiumDriver getDriver() {
        return mobiledriver.get();
    }

    public static void setDriver(AppiumDriver driverInstance) {
    	mobiledriver.set(driverInstance);
    }

    public static void quitDriver() {
        if (mobiledriver.get() != null) {
        	mobiledriver.get().quit();
        	mobiledriver.remove();
        }
    }
}

