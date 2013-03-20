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
		staticlogger.info("Finished TestHandleCacheOneClass");
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
	 *  Tests opening one window and clicking a button in an iframe
	 */
	@Test
	public void testIFrame() {
		classlogger.info("Starting test testMultiWindows" );
		classlogger.info("Loading Window1 contents");
		getDriver().get( System.getProperty("testDomainURL") + System.getProperty("testURI") );
		waitTimer(5, 1000);
		
		// test iFrame here
		getDriver().switchTo().defaultContent();		
		WebElement iframe = getDriver().findElement( By.name("BodyFrame") );		
		staticlogger.info( "iFrame Location: " + iframe.getLocation() );		
		getDriver().switchTo().frame( iframe );		
		WebElement we = getDriver().findElement( By.id( "buttonId" ) );
		try {
			//getDriver().manage().timeouts().implicitlyWait( DEFAULT_IMPLICIT_WAIT, TimeUnit.SECONDS );
			we.click();
		} catch ( WebDriverException wde ) {
			staticlogger.info("ERROR: WebDriverException " + wde.getLocalizedMessage() + "\n\n" + wde.getCause() + "\n\n" );
		}	
		getDriver().switchTo().defaultContent();
		waitTimer( 10, 1000);
		
		classlogger.info( "Finished testMultiWindows" );
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
		staticlogger.info("Finished tearDownTestHandleCacheOneClass");
	}

}
