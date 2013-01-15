package qa.webdriver.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import qa.webdriver.util.BingSearchPage;
import static qa.webdriver.util.BingUtilities.*;

@RunWith(Parameterized.class)
public class BingTest {

	/* BingTest class:
	   Testing re-use of browser windows in JUnit with WebDriver
	   Runs parameterized	   
    */
	
	private String testName, searchString, ddMatch;

	public BingTest( String tName, String sString, String dMatch ) {
		
		// start: figure out why currentdir is not correct when running with Gradle
		String current = null;
		try {
			current = new File( "." ).getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
        logger.info("Current dir: " + current);
        String currentDir = System.getProperty("user.dir");
        logger.info("System dir:  " +currentDir);
        // end
        
		this.testName = tName;
		this.searchString = sString;
		this.ddMatch = dMatch;
		logger.info("Running test: " + testName + ", " + searchString + ", " + ddMatch );
	}
	
	@BeforeClass
	public static void prepareBrowser() {
		initializeBrowser( "firefox" );  // either firefox or ie
	}
	
	/* @Parameters(name = "{index}: {0}={1}")
       public static Iterable<Object[]> data() {
           return Arrays.asList(new Object[][] { { 0, 0 }, { 1, 1 }, { 2, 1 },
                { 3, 2 }, { 4, 3 }, { 5, 5 }, { 6, 8 } });
       }
    */
	
	@Parameters(name = "{0}: {1}: {2}")
	public static Iterable<String[]> loadTestsFromFile() {
		File tFile = loadTestFile("testdata.csv"); // to handle Gradle vs. JUnit runtime
		FileReader fileReader = null;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			String line = null;
			fileReader = new FileReader( tFile );
			BufferedReader bufferedReader = new BufferedReader( fileReader );
			while ( ( line = bufferedReader.readLine() ) != null ) { // make sure line is not empty
				if ( line.contains(",") ) { // make sure line contains commas
				    lines.add(line); 
				}
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<String[]> data = new ArrayList<String[]>();
		for ( String lin : lines ) {
				String[] stritems = lin.split( "\\," ); // split on commas rather than Tokenizer 
				data.add( stritems );
		}
		return data;
	}  

	@Test
	public void testWithPageObject() {    	
		driver.get("http://bing.com");
		BingSearchPage bsp = new BingSearchPage();
		bsp.setSearchString( searchString );
		selectInBingDropdown( ddMatch );  
		bsp.clickSearchButton();
		waitTimer(3, 1000);
		//bsp.clickLogo(); //click Bing logo
		//driver.get("http://bing.com");
		logger.info("Done with test.");
	}
	
	@Test
	public void testFluentPageObject() {    	
		logger.info("{} being run...", testName );
		driver.get("http://bing.com");
		BingSearchPage bsp = new BingSearchPage();
		bsp.withFluent()
		.clickSearchField()
		.setSearchString("iphone app").waitForTime(2, 1000)
		.selectItem( "development" ).clickSearchButton()
		.waitForTime(2, 1000)
		.clickLogo();
		logger.info("Fluent test '{}' is done.", testName );
	}
	
	@After
	public void cleanUp() {
		driver.get("about:about");
	}
	
	@AfterClass
	public static void tearDown() {
        closeAllBrowserWindows();
		logger.info("Finished tearDown.");
	}

}
