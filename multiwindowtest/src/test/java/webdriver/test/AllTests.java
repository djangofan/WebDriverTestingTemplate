package webdriver.test;

import qa.webdriver.util.WebDriverUtils;
import qa.webdriver.util.SiteServer;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * To run suite you need to pass the Gradle '-Dtest.single=AllTests' option to the JVM
 * or you can run the included 'runAllTests' Gradle task.   This is a 'non-parallel' or
 * what can be called a 'sequential' type of test run; a typical JUnit test suite.
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ TestHandleCacheOne.class, TestHandleCacheTwo.class })
public class AllTests extends WebDriverUtils {

	@BeforeClass
	public static void setUpSuiteOne() {
		returnLoggerState();
		
		// start http server
		File httpRoot = new File("build/resources/test");
		int httpPort = Integer.parseInt("8080");
		try {
			fs = new SiteServer( httpPort , httpRoot );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.info("Finished setUpSuiteOne");
	}

	@AfterClass
	public static void tearDownSuiteOne() {
		logger.info("Finished tearDownSuiteOne");
		fs.stop();
	}

}
