package test;

import org.junit.Test;

import testClasses.Chrome;
import testClasses.Firefox;

/**
 * Created by josh on 20/01/17.
 */
public class RunExample {
    @Test
    public void runExample() {
        Chrome driver = new Chrome();
    	//Firefox driver = new Firefox();
        String url = "http://localhost/home/index.php";
        //String url = "http://regent.ecs.vuw.ac.nz:10241/index.php";
        String testId = "001";

        driver.runExample(url, testId);
        driver.clickButtonById("finish_button_top_corner");
    }
}
