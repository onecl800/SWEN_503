package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by jinxiao3 on 23/01/2017.
 */

// specify a runner class: Suite.class
@RunWith(Suite.class)

// specify an array of test classes
@Suite.SuiteClasses({
        BrowserIntegrationTest.class,
        FunctionalityTest.class}
)

public class FullTestSuite {
}
