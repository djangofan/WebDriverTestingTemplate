package qa.webdriver.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import qa.webdriver.util.GoogleSearchPage;
import qa.webdriver.util.GoogleUtilities;

@RunWith(Parameterized.class)
public class GoogleTest extends GoogleUtilities {

	/* GoogleTest class:
	   Testing re-use of browser windows in JUnit with WebDriver
	   Runs parameterized	   
    */
	
	protected static String testName, searchString, ddMatch;
	
	public GoogleTest( String tName, String sString, String dMatch ) {
		testName = tName;
		searchString = sString;
		ddMatch = dMatch;
		staticlogger.info("Running test: " + testName + ", " + searchString + ", " + ddMatch );	
	}
	
	@BeforeClass
	public static void setUpClass() {		

		staticlogger.info("Finished setUpClass");
	}
	
	@Parameters(name = "{0}: {1}: {2}")
	public static Iterable<String[]> loadTestsFromFile() {
		File tFile = loadGradleResource("testdata.csv");
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
		staticlogger.info("{} being run...", testName );
		driver.get("http://www.google.com");
		GoogleSearchPage gs = new GoogleSearchPage();
		gs.setSearchString( "iphone app" );
		selectInGoogleDropdown( "development" );  
		gs.clickSearchButton();
		waitTimer(2, 1000);
		clickByCSSSelector( "div.gbqlca" ); //click Google logo
		staticlogger.info("Page object test '{}' is done.", testName );
	}

	@Test
	public void testFluentPageObject() {    	
		staticlogger.info("{} being run...", testName );
		driver.get("http://www.google.com");
		GoogleSearchPage gsp = new GoogleSearchPage();
		gsp.withFluent().clickSearchField()
		.setSearchString("iphone app").waitForTime(2, 1000)
		.selectItem( "development" ).clickSearchButton()
		.waitForTime(2, 1000).clickLogo(); //click Google logo
		staticlogger.info("Fluent test '{}' is done.", testName );
	}
	
	@After
	public void cleanUp() {
		staticlogger.info("Finished cleanUp");
		// driver.get("about:about");
	}
	
	@AfterClass
	public static void tearDownClass() {
		staticlogger.info("Finished tearDownClass");
	}

}
