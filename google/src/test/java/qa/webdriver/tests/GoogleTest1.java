package qa.webdriver.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;

import qa.webdriver.util.GoogleSearchPage;
import qa.webdriver.util.GoogleUtilities;

@RunWith(Parameterized.class)
public class GoogleTest1 extends GoogleUtilities {

	protected static String testName, searchString, ddMatch;

	public GoogleTest1( String tName, String sString, String dMatch ) {
		testName = tName;
		searchString = sString;
		ddMatch = dMatch;
		testXOffset = 0;
	}

	@Before
	public void setUp() {	
		if ( driver == null) initializeRemoteBrowser( "firefox", "localhost", 4444 );
		staticlogger.info("Finished setUpGoogleTest1");
	}

	@Parameters(name = "{0}: {1}: {2}")
	public static Iterable<String[]> loadTestsFromFile1() {
		File tFile = loadGradleResource( System.getProperty("user.dir") + separator +  "build" +
		        separator + "resources" + separator +  "test" + separator + "testdata1.csv" );
		FileReader fileReader = null;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			String line = null;
			fileReader = new FileReader( tFile );
			BufferedReader bufferedReader = new BufferedReader( fileReader );
			while ( !( line = bufferedReader.readLine() ).isEmpty() ) { // make sure line is not empty
				if ( !line.startsWith("#") ) { // skip lines commented out with a # char
					if ( line.contains(",") ) { // make sure line contains commas
						lines.add(line); 
					} else {
						staticlogger.info("Test input contained invalid entry.");
					}
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
		staticlogger.info("{} being run...", testName );
		driver.get("http://www.google.com");
		GoogleSearchPage gs = new GoogleSearchPage();
		gs.setSearchString( searchString );
		selectInGoogleDropdown( ddMatch );  
		gs.clickSearchButton();
		waitTimer(2, 1000);
		getElementByLocator( By.cssSelector( "div.gbqlca" ) ).click(); // click Google logo
		staticlogger.info("Page object test '{}' is done.", testName );
	}

	@Test
	public void testFluentPageObject() {    	
		staticlogger.info("{} being run...", testName );
		driver.get("https://www.google.com/webhp?hl=en&tab=ww");
		GoogleSearchPage gsp = new GoogleSearchPage();
		gsp.withFluent().clickSearchField()
		.setSearchString( searchString ).waitForTime(2, 1000)
		.selectItem( ddMatch ).clickSearchButton()
		.waitForTime(2, 1000).clickLogo(); //click Google logo
		staticlogger.info("Fluent test '{}' is done.", testName );
	}

	@After
	public void cleanUp() {
		staticlogger.info("Finished cleanUpGoogleTest1");
		driver.get("about:about");
		// remaining open browser window will garbage collect within 30 seconds
	}

}
