package qa.webdriver.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilityClass {

	public static WebDriver driver;
	private static JavascriptExecutor js;
	private static String pageLoadStatus = null;
	public static Logger logger = LoggerFactory.getLogger( "JUnit" );

	public static void clearAndSetValue(WebElement field, String text) { 
		field.clear(); 
		field.sendKeys(Keys.chord(Keys.CONTROL, "a"), text); 
	}

	public static void clearAndType(WebElement field, String text) { 
		field.clear(); 
		field.sendKeys(text); 
	}

	public static void clickElementWithJSEById( String id ) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		WebElement element= driver.findElement( By.id( id ) );
		js.executeScript( "arguments[0].click();", element );
		js = null;
	}
	
	public static void clickByCSSSelector( String sel ) {
		driver.findElement( By.cssSelector( sel ) ).click();
	}

	public static void closeAllBrowserWindows() {
		Set<String> availableWindows = driver.getWindowHandles();
		if ( !availableWindows.isEmpty() ) {
			logger.info("Closing " + availableWindows.size() + " window(s).");
			for ( String windowId : availableWindows ) {
				logger.info("-- Found window named " + windowId );
				driver.switchTo().window( windowId ).close();
			}
		} else {
			logger.info("There were no window handles to close.");
		}
	}

	public static boolean closeWindowUsingTitle( String title ) 
	{
		String currentWindow = driver.getWindowHandle();
		Set<String> availableWindows = driver.getWindowHandles();
		if ( !availableWindows.isEmpty() ) {
			for ( String windowId : availableWindows ) {
				if ( driver.switchTo().window(windowId).getTitle().contains(title) ) {
					driver.close();
					return true;
				} else {
					driver.switchTo().window(currentWindow);
				}
			}
		}
		return false;
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

	public static void initializeBrowser( String type ) {
		if ( type.equalsIgnoreCase( "firefox" ) ) {
			driver = new FirefoxDriver();
		} else if ( type.equalsIgnoreCase( "ie" ) ) {
			driver = new InternetExplorerDriver();
		}
		driver.manage().timeouts().implicitlyWait( 10000, TimeUnit.MILLISECONDS );
		driver.manage().window().setPosition(new Point(200, 10));
		driver.manage().window().setSize(new Dimension(1200, 800));
	}

	public static File loadTestFile( String fileName ) {
		FileReader toReturn;
		File junitFile = new File("build/resources/test/" + fileName );
		try {
			toReturn = new FileReader(junitFile);
			logger.info( "The file '" + junitFile.getAbsolutePath() + "' is loaded." );
		} catch( FileNotFoundException ex ) {
			ex.getLocalizedMessage();
			logger.info( "Problem loading test data input file: " + junitFile.getAbsolutePath() );
		}	
		return junitFile;
	}

	public static ExpectedCondition<WebElement> visibilityOfElementLocated(final By locator) {
		return new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement toReturn = driver.findElement(locator);
				if (toReturn.isDisplayed()) {
					return toReturn;
				}
				return null;
			}
		};
	}

	public static void waitForPageToLoad() {
		logger.info("Waiting for page to load...");
		do {
			js = (JavascriptExecutor) driver;
			pageLoadStatus = (String)js.executeScript("return document.readyState");
			logger.info(".");
		} while ( !pageLoadStatus.equals("complete") );
		    logger.info("Page is loaded.");
	}

	public static void waitTimer( int units, int mills ) {
		//TODO Not sure why this method prints to std-out
		DecimalFormat df = new DecimalFormat("###.##");
		double totalSeconds = ((double)units*mills)/1000;
		logger.info("Explicit pause for " + df.format(totalSeconds) + " seconds divided by " + units + " units of time: ");
		try {
			Thread.currentThread();		
			int x = 0;
			while( x < units ) {
				Thread.sleep( mills );
				logger.info(".");
				x = x + 1;
			}
		} catch ( InterruptedException ex ) {
			ex.printStackTrace();
		}	
	}

	protected UtilityClass() {
		throw new AssertionError(); // to prevent instantiation as an object
	}

}
