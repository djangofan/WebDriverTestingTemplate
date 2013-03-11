package qa.webdriver.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
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

	protected static RemoteWebDriver driver;
	public static int DEFAULT_IMPLICIT_WAIT = 30;
	protected static String mainHandle = "";
	protected static String mainWindowTitle = "";
	protected static Set<String> handleCache = new HashSet<String>();
	public static int testXOffset = 100;
	protected static File reportFile;
	protected static long startTime;

	public static void clearAndSetValue(WebElement field, String text) { 
		field.clear(); 
		field.sendKeys(Keys.chord(Keys.CONTROL, "a"), text); 
	}

	public static void clearAndType(WebElement field, String text) { 
		field.clear(); 
		field.sendKeys(text); 
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
		driver.manage().timeouts().implicitlyWait( DEFAULT_IMPLICIT_WAIT, TimeUnit.MILLISECONDS );
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

	public static WebElement getElementByLocator( By locator ) {		
		int weWait = DEFAULT_IMPLICIT_WAIT;
		int cycle = 1;
		WebElement we = null;
		List<WebElement> weList = driver.findElements( locator );
		while ( weList.size() == 0 && cycle <= 3 ) { // 3 cycles equals 180 seconds of wait		
			staticlogger.info("DOM not ready.  Trying again for " + weWait + " more seconds...");
			driver.manage().timeouts().implicitlyWait( weWait, TimeUnit.SECONDS );
			weList = driver.findElements( locator ); 
			weWait +=30;
			cycle +=1;
		}
        // if list not 0 then try getting the element directly
		try {
			we = driver.findElement( locator );
		} catch ( StaleElementReferenceException sere ) {
			staticlogger.info( "Element not ready.\n" + sere.getMessage() );
		}
		// as a safety measure, if the element is still null then try getting it from the previous element list
        if ( we == null ) {
		try {
			we = weList.get(0);
		} catch ( Exception e ) {
			staticlogger.info( e.getMessage() );
		}
        }
		return we;
	}

}
