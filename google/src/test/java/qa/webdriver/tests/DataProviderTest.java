package qa.webdriver.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import au.com.bytecode.opencsv.CSVReader;

import qa.webdriver.util.WebDriverUtils;

@RunWith(Parameterized.class)
public class DataProviderTest extends WebDriverUtils {

	private static String testName, searchString, ddMatch;

	public DataProviderTest( String tName, String sString, String dMatch ) {
		testName = tName;
		searchString = sString;
		ddMatch = dMatch;
		testXOffset = 700;
	}

	@Before
	public void setUp() {	
		LOGGER.info("setUp");
	}

	@Parameters(name = "{0}: {1}: {2}")
	public static List<String[]> loadParams() {
		File tFile = loadGradleResource( System.getProperty("user.dir") + separator +  "build" +
				separator + "resources" + separator +  "test" + separator + "testdata2.csv" );
		List<String[]> rows = null;
		if ( tFile.exists() ) {
			CSVReader reader = null;
			try {
				reader = new CSVReader( new FileReader( tFile ), ',' );
				rows = reader.readAll();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		//String[][] csvMatrix = rows.toArray(new String[rows.size()][]);
		LOGGER.info("Finished loadParams()");
		return rows;
	}  

	@Test
	public void testParams() {
		LOGGER.info("Param '{}' being run...", testName );
		LOGGER.info("Search string: " + searchString );
		LOGGER.info("ddMatch: " + ddMatch );
		LOGGER.info("Test '{}' is done.", testName );
	}

	@After
	public void cleanUp() {
		LOGGER.info("Finished cleanUp");
	}


}
