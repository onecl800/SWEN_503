package testClasses;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.safari.SafariDriver;

/**
 * Created by josh on 1/22/17.
 */
public class Safari extends SeleniumTestMethods{

    public Safari() {
        System.setProperty("webdriver.safari.driver", "lib/SafariDriver.safariextz"); //driver for OSX systems
        System.setProperty("webdriver.safari.noinstall", "true"); //To stop uninstall each time. Not sure if this line is necessary, will need testing. Source: http://stackoverflow.com/questions/26598239/how-to-launch-safari-with-selenium-webdriver-using-java
        driver = new SafariDriver();
        driver.manage().window().maximize();
        js = (JavascriptExecutor) driver;
    }

}
