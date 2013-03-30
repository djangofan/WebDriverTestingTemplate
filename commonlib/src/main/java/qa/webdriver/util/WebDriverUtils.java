package qa.webdriver.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import qa.webdriver.util.CoreUtils;

/**
 * @author Jon Austen
 *
 */
public abstract class WebDriverUtils extends CoreUtils {

	protected WebDriverUtils() {
		// do nothing
	}

	protected static RemoteWebDriver driver;
	public static int DEFAULT_IMPLICIT_WAIT = 30;
	public static int DEFAULT_EXPLICIT_WAIT = 10; // seconds that LoadableComponent get() will try to load
	protected static String mainHandle = "";
	protected static String mainWindowTitle = "";
	protected static Set<String> handleCache = new HashSet<String>();
	public static int testXOffset = 0; //default value

	public static void clearAndSetValue( By locator, String text ) { 
		WebElement field = driver.findElement( locator );
		field.clear(); 
		field.sendKeys( Keys.chord(Keys.CONTROL, "a" ), text ); 
	}

	public static void clearAndType( By locator, String text ) {
		WebElement field = driver.findElement( locator );
		field.clear(); 
		field.sendKeys( text ); 
	}

	public static void mouseClickByLocator( By locator ) {
		WebElement el = driver.findElement( locator );
		Actions builder = new Actions( driver );
		builder.moveToElement( el ).click( el );
		builder.perform();
	}

	public static void switchToWindowByName( String name ) {
		Set<String> windowSet = driver.getWindowHandles();
		for( String handle: windowSet ) { 
			driver.switchTo().window( handle );
			if ( driver.getTitle().equals( name ) ) {
				break;
			}
		}
	}

	public static void clickByIdWithJavascript( String id ) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		WebElement element= driver.findElement( By.id( id ) );
		js.executeScript( "arguments[0].click();", element );
		js = null;
	}

	public static void closeAllBrowserWindows() {
		Set<String> handles = driver.getWindowHandles();
		if ( !handles.isEmpty() ) {
			LOGGER.info("Closing " + handles.size() + " window(s).");
			for ( String windowId : handles ) {
				LOGGER.info("-- Closing window handle: " + windowId );
				driver.switchTo().window( windowId ).close();
			}
		} else {
			LOGGER.info("There were no window handles to close.");
		}
		driver.quit();  // this quit is critical, otherwise window will hang open
	}

	public static void closeWindowByHandle( String windowHandle ) {  
		driver.switchTo().window( windowHandle );
		LOGGER.info("Closing window with title \"" + driver.getTitle() + "\"." );
		driver.close();
	}
	/**
	 * Print internal Logger status
	 */
	public static void returnLoggerState() {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		StatusPrinter.print(lc);
	}

	/**
	 * Loops to determine if WebDriver.getWindowHandles() returns any
	 *  additional windows that the allHandles cache does not currently
	 *  contain. If new windows are found, switch to latest window and
	 *  update allHandles cache.
	 */
	public static String handleNewWindow() {
		String newHandle = "";
		printHandles();
		Set<String> updatedHandles = driver.getWindowHandles();
		if ( updatedHandles.size() <= handleCache.size() ) {
			mainHandle = "";
			throw new IllegalStateException("This method handleNewWindow is not appropriate\n" +
					"in this case.  You are probably looking for the\n"+
					"use of the updateHandleCache method.");
		} else {
			if ( !updatedHandles.isEmpty() ) {
				for ( String windowId : updatedHandles ) {
					if ( !windowId.equals( mainHandle ) ) { // for all windows except main window
						if ( !handleCache.contains( windowId) ) { // for child windows not in allHandles cache
							newHandle = windowId; // set value of newly found window handle						
							LOGGER.info("-- Open window handle: " + newHandle + " (new window)" );
						}
					}
				}
				if ( !newHandle.equals("") ) { // outside loop so it catches latest window handle if there are multiple
					LOGGER.info("Switch to new window.");
					driver.switchTo().window( newHandle ); // switch to new window handle
				}
			} else {
				mainHandle = "";
				throw new IllegalStateException("No browser window handles are open.");
			}
		}
		handleCache = updatedHandles; // updates remembered set of open windows
		return newHandle;
	}

	public static void updateHandleCache() {
		printHandles();
		Set<String> updatedHandles = driver.getWindowHandles();
		if ( !updatedHandles.isEmpty() ) {
			if ( updatedHandles.size() > handleCache.size() ) {
				LOGGER.info( "Window handle number increased to: " + updatedHandles.size() );
			} else if ( updatedHandles.size() == handleCache.size() ) {
				LOGGER.info( "Window handle number is unchanged from: " + updatedHandles.size() );
			} else {
				LOGGER.info( "Window handle number decreased to: " + updatedHandles.size() );
			}			
		} else {
			mainHandle = null;
			throw new IllegalStateException("No browser window handles are open.");
		}
		handleCache = updatedHandles; // updates remembered set of open windows
	}

	public static void printHandles() {
		LOGGER.info( "Open windows:" );
		for ( String windowId : handleCache ) {
			LOGGER.info( "-- Open window handle: " + windowId );
			if ( windowId.equals( mainHandle ) ) {
				LOGGER.info(" (main handle)");
			}
		}
	}	

	public static void initializeLocalBrowser( String type ) {  	
		if ( type.equalsIgnoreCase( "firefox" ) ) {
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("network.proxy.http", "localhost");
			profile.setPreference("network.proxy.http_port", "3128");
			driver = new FirefoxDriver( profile );
		} else if ( type.equalsIgnoreCase( "internetExplorer" ) ) {
			driver = new InternetExplorerDriver();
		}
		else if ( type.equalsIgnoreCase( "chrome" ) ) {
			driver = new ChromeDriver();
		} else {
			LOGGER.info( "Invalid browser type. Cannot initialize." );
		}
		driver.manage().timeouts().implicitlyWait( DEFAULT_IMPLICIT_WAIT, TimeUnit.MILLISECONDS );
		positionMainHandle();		
	}

	public static void initializeRemoteBrowser( String type, String host, int port ) {
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability( "takesScreenshot", false );
		dc.setCapability( "webdriver.remote.quietExceptions", false );
		try {
			if ( type.equalsIgnoreCase( "firefox" ) ) {
				dc.setBrowserName("firefox");
				driver = new RemoteWebDriver( new URL("http://" + host + ":" + port + "/wd/hub"), dc );

			} else if ( type.equalsIgnoreCase( "ie" ) ) {
				dc.setBrowserName("internetExplorer");
				driver = new RemoteWebDriver( new URL("http://" + host + ":" + port + "/wd/hub"), dc );
			}
			else if ( type.equalsIgnoreCase( "chrome" ) ) {
				dc.setBrowserName("chrome");
				driver = new RemoteWebDriver( new URL("http://" + host + ":" + port + "/wd/hub"), dc );
			} else {
				LOGGER.info( "Invalid browser type. Cannot initialize." );
			}
		} catch ( MalformedURLException e ) {
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait( DEFAULT_IMPLICIT_WAIT, TimeUnit.MILLISECONDS );
		positionMainHandle();
	}

	private static void positionMainHandle() {
		handleCache = driver.getWindowHandles();
		if ( handleCache.size() == 0 ) {
			mainHandle = "";
			throw new IllegalStateException("No browser window handles are open.\n" +
					"Browser is uninitialized.");
		} else if ( handleCache.size() > 1 ) {
			mainHandle = "";
			throw new IllegalStateException("More than one browser window handle is open.\n" +
					"Please close all browsers and restart test.");
		} else {
			mainHandle = driver.switchTo().defaultContent().getWindowHandle();
			mainWindowTitle = driver.switchTo().defaultContent().getTitle();
			int fromLeft = Integer.parseInt( System.getProperty("windowXPosition") );
			int fromTop = Integer.parseInt( System.getProperty("windowYPosition") );
			int width = Integer.parseInt( System.getProperty("windowWidth") );
			int height = Integer.parseInt( System.getProperty("windowHeight") );
			setWindowPosition( mainHandle, width, height, fromLeft + testXOffset, fromTop );
		}
	}

	public static void setWindowPosition(String handle, int width, int height, int fleft, int ftop) {
		driver.switchTo().window( handle ).manage().window().setPosition( new Point(fleft, ftop) );
		driver.switchTo().window( handle ).manage().window().setSize( new Dimension( width, height) );
		//TODO add a javascript executor to get window focus
	}

	public static WebElement getElementByLocator( final By locator ) {
		LOGGER.info( "Get element by locator: " + locator.toString() );		
		final long startTime = System.currentTimeMillis();
		Wait<WebDriver> wait = new FluentWait<WebDriver>( driver )
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring( StaleElementReferenceException.class ) ;
		int tries = 0;
		boolean found = false;
		WebElement we = null;
		while ( (System.currentTimeMillis() - startTime) < 91000 ) {
		    LOGGER.info( "Searching for element. Try number " + (tries++) );  
			try {
				we = wait.until( ExpectedConditions.visibilityOfElementLocated( locator ) );
				found = true;
			} catch ( StaleElementReferenceException e ) {						
				LOGGER.info( "Stale element: \n" + e.getMessage() + "\n");
			}
		}
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		if ( found ) {
			LOGGER.info("Found element after waiting for " + totalTime + " milliseconds." );
		} else {
			LOGGER.info( "Failed to find element after " + totalTime + " milliseconds." );
		}
		return we;
	}


}
