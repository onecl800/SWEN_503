package testClasses;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by josh on 18/01/17.
 */
public class Chrome extends SeleniumTestMethods{

    public Chrome() {
        if (System.getProperties().getProperty("os.name").contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe"); //driver for win32 systems
        } else {
            System.setProperty("webdriver.chrome.driver", "lib/chromedriver"); //driver for linux64 systems
        }
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        js = (JavascriptExecutor) driver;
    }

}
