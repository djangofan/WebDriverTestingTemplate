package webdriver.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import qa.webdriver.util.WebDriverUtils;

/**
 *  This class tests a multi-window handling method that I call "handle cache"
 *  
 * @author Jon Thor Austen
 *
 */
public class TestHandleCacheOne extends WebDriverUtils {
	
	/**
	 *  Setup web server before loading class
	 */
	@BeforeClass
	public static void setUpTestHandleCacheOneClass() {
		LOGGER.info("Finished TestHandleCacheOneClass");
	}
	
	/**
	 *  Load main window handle before tests
	 */
	public TestHandleCacheOne() {
		classlogger.info("Constructed TestHandleCacheOne");
	}
	
	/**
	 *  Start main window handle for tests
	 */
	@Before
	public void setUpTestHandleCacheOne() {
		testXOffset = 0;
		initializeRemoteBrowser( "firefox", System.getProperty("hubUrl") , Integer.parseInt( System.getProperty("hubPort") ) );
		System.out.println("HandleCacheOne thread id = " + Thread.currentThread().getId());
		classlogger.info("Finished setUpTestHandleCacheOne");
	}
	
	/**
	 *  Tests opening a few windows and then closing them
	 */
	@Test
	public void testHandleCacheOne() {
		classlogger.info("Starting test testHandleCacheOne" );
		classlogger.info("Loading Window1 contents");
		driver.get( System.getProperty("testProtocol") + "://" + System.getProperty("testDomain") + ":" +
		        System.getProperty("testPort") + System.getProperty("testUri") );
		waitTimer(2, 500);

		// Open Window2 via Window1
		classlogger.info("Opening Window2");
		driver.findElement( By.id("btnNewNamelessWindow") ).click();        
		String h2 = handleNewWindow();
		waitTimer(2, 500);

		// Open Window3 and Window4 via Window2
		//driver.findElement( By.cssSelector("html body a:first-child") ).click();
		classlogger.info("Opening Window3");
		driver.findElement(By.id("w3link")).click();
		String h3 = handleNewWindow();
		waitTimer(2, 500);
		driver.switchTo().window( h2 );
		//driver.findElement( By.cssSelector("html body a:last-child") ).click();
		classlogger.info("Opening Window4");
		driver.findElement(By.id("w4link")).click();
		waitTimer(2, 500);
		String h4 = handleNewWindow();
		waitTimer(2, 500);

		// close Window4
		closeWindowByHandle( h4 );
		updateHandleCache();
		waitTimer(6, 500);

		// close Window3
		closeWindowByHandle( h3 );
		updateHandleCache();
		waitTimer(2, 500);

		// close Window2
		closeWindowByHandle( h2 );
		updateHandleCache();        
		waitTimer(2, 500);         

		classlogger.info( "Finished testHandleCacheOne" );
	}	
  
	/**
	 *  Close main window handle after tests finish
	 */
	@After
	public void tearDownTestHandleCacheOne() {
		// close main window handle
		driver.switchTo().window( mainHandle );
		driver.get("about:about");
		updateHandleCache();  
		waitTimer(6, 500);
    closeAllBrowserWindows(); 
		classlogger.info("Finished tearDownTestHandleCacheOne");
	}

	/**
	 *  Cleanup any remaining objects after unloading class
	 */
	@AfterClass
	public static void tearDownTestHandleCacheOneClass() {
		LOGGER.info("Finished tearDownTestHandleCacheOneClass");
	}

}
