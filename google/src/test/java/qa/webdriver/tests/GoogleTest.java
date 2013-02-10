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
	
	public GoogleTest( String tName, String sString, String dMatch ) {
		super( tName, sString, dMatch );	
	}
	
	@BeforeClass
	public static void setUpClass() {		
		initializeRemoteBrowser( "firefox", "localhost", 4444 );
		logger.info("Finished setUpClass");
	}
	
	@Parameters(name = "{0}: {1}: {2}")
	public static Iterable<String[]> loadTestsFromFile() {
		File tFile = loadTestFile("testdata.csv");
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
		logger.info("{} being run...", testName );
		driver.get("http://www.google.com");
		GoogleSearchPage gs = new GoogleSearchPage();
		gs.setSearchString( "iphone app" );
		selectInGoogleDropdown( "development" );  
		gs.clickSearchButton();
		waitTimer(2, 1000);
		clickByCSSSelector( "div.gbqlca" ); //click Google logo
		logger.info("Page object test '{}' is done.", testName );
	}

	@Test
	public void testFluentPageObject() {    	
		logger.info("{} being run...", testName );
		driver.get("http://www.google.com");
		GoogleSearchPage gsp = new GoogleSearchPage();
		gsp.withFluent().clickSearchField()
		.setSearchString("iphone app").waitForTime(2, 1000)
		.selectItem( "development" ).clickSearchButton()
		.waitForTime(2, 1000).clickLogo(); //click Google logo
		logger.info("Fluent test '{}' is done.", testName );
	}
	
	@After
	public void cleanUp() {
		logger.info("Finished cleanUp");
		// driver.get("about:about");
	}
	
	@AfterClass
	public static void tearDownClass() {
		logger.info("Finished tearDownClass");
	}

}
