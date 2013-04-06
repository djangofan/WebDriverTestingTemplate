package webdriver.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import qa.webdriver.util.WebDriverUtils;

/**
 *  This class tests a multi-window handling method that I call "handle cache"
 *  
 * @author Jon Thor Austen
 *
 */
public class TestFrames extends WebDriverUtils {
	
	/**
	 *  Load main window handle before tests
	 */
	public TestFrames() {
		classlogger.info("Called constructor for TestFrames...");
	}
	
	/**
	 *  Setup web server before loading class
	 */
	@BeforeClass
	public static void setUpTestFramesClass() {
		LOGGER.info("Finished TestFramesClass");
	}
	
	/**
	 *  Start main window handle for tests
	 */
	@Before
	public void setUpTestFrames() {
		testXOffset = 0;
		initializeRemoteBrowser( System.getProperty("browserType"), System.getProperty("hubUrl") ,
                Integer.parseInt( System.getProperty("hubPort") ) );
		System.out.println("TestFrames thread id = " + Thread.currentThread().getId());
		classlogger.info("Finished setUpTestFrames");
	}
	
	@Test
	public void testIFrame1() {
		classlogger.info("Starting test testIFrame1" );
		driver.get( System.getProperty("testProtocol") + "://" + System.getProperty("testDomain") + ":" +
		        System.getProperty("testPort") + System.getProperty("testUri") + "/w1.html#iFrame1" );
		//MainPage mp1 = new MainPage( driver ).get();		
		IFrame1 if1 = new IFrame1( driver ).get();
		waitTimer( 2, 1000);
		if1.setInputField("Iframe1 test." );
		if1.clickCopyButton();
		if1.exitFrame();
		//mp1.setControlInputField( "Test input" );
		//mp1.clickControlButton();
		//mp1.clickControlButton();
		waitTimer( 2, 1000);	
		classlogger.info( "Finished testIFrame1" );
	}	
	
	@Test
	public void testIFrame2() {
		classlogger.info("Starting test testIFrame2" );
		driver.get( System.getProperty("testProtocol") + "://" + System.getProperty("testDomain") + ":" +
		        System.getProperty("testPort") + System.getProperty("testUri") + "/w1.html#iFrame2" );
		//MainPage mp2 = new MainPage( driver ).get();		
		IFrame2 if2 = new IFrame2( driver ).get();
		waitTimer( 2, 1000);
		if2.setInputField("Iframe2 test." );
		if2.clickCopyButton();
		if2.exitFrame();
		//mp2.setControlInputField( "Test input" );
		//mp2.clickControlButton();
		//mp2.clickControlButton();
		waitTimer( 2, 1000);		
		classlogger.info( "Finished testIFrame2" );
	}	

	/**
	 *  Close main window handle after tests finish
	 */
	@After
	public void cleanUpTestFrames() {
		driver.switchTo().window( mainHandle );
		showMessageInBrowser("Test finished.");
		updateHandleCache();  
		waitTimer(6, 500);
        closeAllBrowserWindows(); 
		classlogger.info("Finished cleanUpTestFrames");
	}

	/**
	 *  Cleanup any remaining objects after unloading class
	 */
	@AfterClass
	public static void tearDownTestFramesClass() {
		LOGGER.info("Finished tearDownTestFramesClass");
	}

}
