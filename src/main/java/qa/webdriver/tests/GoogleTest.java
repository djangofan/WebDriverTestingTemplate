package qa.webdriver.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import qa.webdriver.util.GoogleSearchPage;
import static qa.webdriver.util.ExtendedUtilities.*;

@RunWith(Parameterized.class)
public class GoogleTest {

	/* GoogleTest class:
	   Testing re-use of browser windows in JUnit with WebDriver
	   Runs parameterized	   
    */
	
	private String testName, searchString, ddMatch;

	public GoogleTest( String tName, String sString, String dMatch ) {
		String current = null;
		try {
			current = new File( "." ).getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("Current dir: " + current);
        String currentDir = System.getProperty("user.dir");
        System.out.println("System dir:  " +currentDir);
		this.testName = tName;
		this.searchString = sString;
		this.ddMatch = dMatch;
		System.out.println("Running test: " + testName + ", " + searchString + ", " + ddMatch );
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
		driver.get("http://www.google.com");
		GoogleSearchPage gs = new GoogleSearchPage();
		gs.setSearchString( searchString );
		selectInGoogleDropdown( ddMatch );  
		gs.clickSearchButton();
		waitTimer(3, 1000);
		clickElementWithJSE( "gbql" ); //click Google logo
		System.out.println("Done with test.");
	}
	
	@AfterClass
	public static void tearDown() {
		waitTimer(6, 1000);
		driver.close();
		System.out.println("Finished tearDown.");
	}

}
