package webdriver.test;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;

import qa.webdriver.util.MultiWinCacheUtils;
import qa.webdriver.util.SiteServer;

/**
 * @author Jon Thor Austen
 * @date 02/20/2013
 * 
 * To run suite you need to pass the Gradle '-Dtest.single=ParallelSuite' option to the JVM
 * or you can run the included 'runParallelSuite' Gradle task.   This is a 'parallel' or
 * 'multi-threaded' type of test run; an experimental type of JUnit test suite.
 * 
 * There are 4 main static objects for these tests:
 * 1. A single Logger instance for the whole suite
 * 2. A separate WebDriver instance for each test class
 * 3. A single SiteServer instance for the whole suite that serves the HTML pages for the tests
 * 4. A single Selenium RemoteWebDriver JSON server for the whole suite
 * 
 */
public class ParallelSuite extends MultiWinCacheUtils {
	
	// some ideas here
	// https://github.com/krosenvold/configurable-parallel-computer/blob/master/src/test/java/org/jdogma/junit/JUnitCoreConcurrencyTest.java
	
	public ParallelSuite() {
        // do nothing
		// this is a JUnit test
	}

	@Before
	public void setUpParallelSuite() {
		returnLoggerState();
		
		// start a RemoteWebDriver JSON server
		wds = initializeJSONHub( "build/resources/test/WebDriver.json" );
		
		// start a local site server to serve HTML pages
		File httpRoot = new File("build/resources/test");
		logger.info("Server root directory is: " + httpRoot.getAbsolutePath() );
		int httpPort = Integer.parseInt("8080");
		try {
			fs = new SiteServer( httpPort , httpRoot );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.info("Finished setUpParallelSuite");
	}

	@Test  
	public void test() {      
		@SuppressWarnings("rawtypes")
		Class[] cls={TestHandleCacheOne.class,TestHandleCacheTwo.class,TestHandleCacheThree.class };  

		//Parallel among classes  
		JUnitCore.runClasses(ParallelComputer.classes(), cls);  

		//Parallel among methods in a class  
		//JUnitCore.runClasses(ParallelComputer.methods(), cls);  

		//Parallel all methods in all classes  
		//JUnitCore.runClasses(new ParallelComputer(true, true), cls);     
	}
	
	@After
	public void tearDownParallelSuite() {
		 
		logger.info("Finished tearDownParallelSuite");
	}

}
