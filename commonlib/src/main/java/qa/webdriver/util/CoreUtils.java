package qa.webdriver.util;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CoreUtils {

	protected static RemoteWebDriver driver;
	public static SiteServer fs;
	protected static JavascriptExecutor js;
	protected static String pageLoadStatus = null;
	public static Logger staticlogger = LoggerFactory.getLogger( "StaticLogger" );
	protected Logger classlogger = LoggerFactory.getLogger( getClass() );
	protected static String testName, searchString, ddMatch;

	public static void clearAndSetValue(WebElement field, String text) { 
		field.clear(); 
		field.sendKeys(Keys.chord(Keys.CONTROL, "a"), text); 
	}

	public static void clearAndType(WebElement field, String text) { 
		field.clear(); 
		field.sendKeys(text); 
	}

	public static void clickByCSSSelector( String sel ) {
		driver.findElement( By.cssSelector( sel ) ).click();
	}
	
	public static void clickByIdWithJavascript( String id ) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		WebElement element= driver.findElement( By.id( id ) );
		js.executeScript( "arguments[0].click();", element );
		js = null;
	}

	/**
	 * This can load a gradle resource, such as a .properties file.
	 * 
	 * @param fileName
	 * @return
	 */
	public static File loadGradleResource( String fileName ) {
		File junitFile = new File("build/resources/test/" + fileName );
		if ( junitFile.exists() ) {
			staticlogger.info( "The file '" + junitFile.getAbsolutePath() + "' exists." );
		} else {
			staticlogger.info( "Problem loading Gradle resource: " + junitFile.getAbsolutePath() );
		}	
		return junitFile;
	}
	
	public static void mouseClickByCSSLocator( String cssLocator ) {
	     String locator = cssLocator;
	     WebElement el = driver.findElement( By.cssSelector( locator ) );
	     Actions builder = new Actions(driver);
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
		staticlogger.info("Waiting for page to load...");
		do {
			js = (JavascriptExecutor) driver;
			pageLoadStatus = (String)js.executeScript("return document.readyState");
			staticlogger.info(".");
		} while ( !pageLoadStatus.equals("complete") );
		    staticlogger.info("Page is loaded.");
	}
	
	public static void waitTimer( int units, int mills ) {
    	DecimalFormat df = new DecimalFormat("###.##");
		double totalSeconds = ((double)units*mills)/1000;
		staticlogger.info("Explicit pause for " + df.format(totalSeconds) + " seconds divided by " + units + " units of time: ");
		try {
			Thread.currentThread();		
			int x = 0;
			while( x < units ) {
				Thread.sleep( mills );
				staticlogger.info(".");
				x = x + 1;
			}
		} catch ( InterruptedException ex ) {
			ex.printStackTrace();
		}	
	}

	protected CoreUtils() {
        // do nothing
	}

}
