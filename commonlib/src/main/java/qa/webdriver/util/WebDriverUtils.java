package qa.webdriver.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
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

	protected static String mainHandle = "";
	protected static String mainWindowTitle = "";
	protected static Set<String> handleCache = new HashSet<String>();
	protected static int xOffSet;

	public static void clearAndSetValue(WebElement field, String text) { 
		field.clear(); 
		field.sendKeys(Keys.chord(Keys.CONTROL, "a"), text); 
	}

	public static void closeAllBrowserWindows() {
		Set<String> handles = driver.getWindowHandles();
		if ( !handles.isEmpty() ) {
			staticlogger.info("Closing " + handles.size() + " window(s).");
			for ( String windowId : handles ) {
				staticlogger.info("-- Closing window handle: " + windowId );
				driver.switchTo().window( windowId ).close();
			}
		} else {
			staticlogger.info("There were no window handles to close.");
		}
		driver.quit();  // this quit is critical, otherwise window will hang open
	}
	
	public static void closeWindowByHandle( String windowHandle ) {  
		driver.switchTo().window( windowHandle );
		staticlogger.info("Closing window with title \"" + driver.getTitle() + "\"." );
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
							staticlogger.info("-- Open window handle: " + newHandle + " (new window)" );
						}
					}
				}
				if ( !newHandle.equals("") ) { // outside loop so it catches latest window handle if there are multiple
					staticlogger.info("Switch to new window.");
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
				staticlogger.info( "Window handle number increased to: " + updatedHandles.size() );
			} else if ( updatedHandles.size() == handleCache.size() ) {
				staticlogger.info( "Window handle number is unchanged from: " + updatedHandles.size() );
			} else {
				staticlogger.info( "Window handle number decreased to: " + updatedHandles.size() );
			}			
		} else {
			mainHandle = null;
			throw new IllegalStateException("No browser window handles are open.");
		}
		handleCache = updatedHandles; // updates remembered set of open windows
	}

	public static void printHandles() {
		staticlogger.info( "Open windows:" );
		for ( String windowId : handleCache ) {
			staticlogger.info( "-- Open window handle: " + windowId );
			if ( windowId.equals( mainHandle ) ) {
				staticlogger.info(" (main handle)");
			}
		}
	}	
	
	public static void initializeRemoteBrowser( String type, String host, int port ) {
		if ( type.equalsIgnoreCase( "firefox" ) ) {
			try {
				driver = new RemoteWebDriver( new URL("http://" + host + ":" + port + "/wd/hub"), DesiredCapabilities.firefox() );
			} catch ( MalformedURLException e ) {
				e.printStackTrace();
			}
		} else if ( type.equalsIgnoreCase( "ie" ) ) {
			driver = new InternetExplorerDriver();
		}
		driver.manage().timeouts().implicitlyWait( 10000, TimeUnit.MILLISECONDS );
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
			setWindowPosition( mainHandle, 600, 800, 300 + xOffSet, 40 );
		}
	}

	public static void initializeStandaloneBrowser( String type ) {
		if ( type.equalsIgnoreCase( "firefox" ) ) {
			driver = new FirefoxDriver();
		} else if ( type.equalsIgnoreCase( "ie" ) ) {
			// ie driver server .exe needs to be in your system path
			driver = new InternetExplorerDriver();
		}
		driver.manage().timeouts().implicitlyWait( 30, TimeUnit.SECONDS );
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
			setWindowPosition( mainHandle, 600, 600, 100 + xOffSet, 40 );
		}
	}

	public static void setWindowPosition(String handle, int width, int height, int fleft, int ftop) {
		driver.switchTo().window( handle ).manage().window().setPosition( new Point(fleft, ftop) );
		driver.switchTo().window( handle ).manage().window().setSize( new Dimension( width, height) );
		//TODO add a javascript executor to get window focus
	}
	
	public static boolean waitForElementBy( By cssLocator ) {
		long t= System.currentTimeMillis();
		long end = t + 30000; // 30 seconds long-wait
		int num = 0;
		int theSize;
		boolean visible = false; // default to false
		while( System.currentTimeMillis() < end ) {
			theSize = driver.findElements( cssLocator ).size();
			if ( theSize > 1 ) staticlogger.info("WARNING: More than one element found for: " + cssLocator.toString() );
			while ( theSize == 0 ) {				
				num +=1;
				staticlogger.info( "WARNING: No element found yet for: " + cssLocator.toString() );
				staticlogger.info( "Attempt number: " + num );
				waitTimer(2, 1000); // slow down loop
			}
			if ( theSize == 1 ) staticlogger.info("Found at least one element after waiting: " + cssLocator.toString() );
			try {
				driver.findElement( cssLocator );
				visible = true;
			} catch ( NoSuchElementException nse ) {
				staticlogger.info( nse.toString() );
			}
			return visible;
		}
		staticlogger.info("Failed to find element.  Timeout was reached.");
		return visible; // default return value
	}

}
