package testClasses;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by josh on 18/01/17.
 */
public class Firefox extends SeleniumTestMethods{

    public Firefox() {
        if (System.getProperties().getProperty("os.name").contains("Windows")) {
            System.setProperty("webdriver.gecko.driver","lib/geckodriver.exe"); //driver for windows systems
        } else {
            System.setProperty("webdriver.gecko.driver","lib/geckodriver"); //driver for linux 64 systems
        }
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
    }



}
