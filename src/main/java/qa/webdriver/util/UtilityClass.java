package qa.webdriver.util;

import java.io.File;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

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

public class UtilityClass {

	public static WebDriver driver;
	private static JavascriptExecutor js;
	private static String pageLoadStatus = null;
	
	public static void clearAndSetValue(WebElement field, String text) { 
		field.clear(); 
		field.sendKeys(Keys.chord(Keys.CONTROL, "a"), text); 
	}
	
	public static void clearAndType(WebElement field, String text) { 
		field.clear(); 
		field.sendKeys(text); 
	}

	public static void clickElementWithJSE( String id ) {
		//Create the object of JavaScript Executor 
		//click command through Javascript
		JavascriptExecutor js = (JavascriptExecutor)driver;
		WebElement element= driver.findElement( By.id( id ) );
		//Use any locator type using to identify the element
		js.executeScript( "arguments[0].click();", element );
		js = null;
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
		File gradleFile = new File( fileName );
		File junitFile = new File("src/main/resources/" + fileName );
		if ( gradleFile.exists() ) {
			System.out.println("Loaded '" + gradleFile.getAbsolutePath() + "' from Gradle runtime.");
			return gradleFile;
		} else if ( junitFile.exists() ) {
			System.out.println("Loaded '" + junitFile.getAbsolutePath() + "' from JUnit runtime.");
			return junitFile;
		}
		System.out.println("Problem loading test data input file.  Trying to run from Gradle bin directory...");
		return new File("bin/" + fileName);
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
		System.out.println("Waiting for page to load...");
		do {
			js = (JavascriptExecutor) driver;
			pageLoadStatus = (String)js.executeScript("return document.readyState");
			System.out.print(".");
		} while ( !pageLoadStatus.equals("complete") );
		System.out.println();
	}

	public static void waitTimer( int units, int mills ) {
		DecimalFormat df = new DecimalFormat("###.##");
		double totalSeconds = ((double)units*mills)/1000;
		System.out.print("Explicit pause for " + df.format(totalSeconds) + " seconds divided by " + units + " units of time: ");
		try {
			Thread.currentThread();		
			int x = 0;
			while( x < units ) {
				Thread.sleep( mills );
				System.out.print("." );
				x = x + 1;
			}
			System.out.print('\n');
		} catch ( InterruptedException ex ) {
			ex.printStackTrace();
		}	
	}

	protected UtilityClass() {
		throw new AssertionError(); // to prevent instantiation as an object
	}

}
