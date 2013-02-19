package testing.qa;

import static testing.qa.AllTests.logger;
import static testing.qa.AllTests.pool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ConcurrentTests {

	@Parameters(name = "{0}: {1}: {2}")
	public static Iterable<String[]> loadTestsFromFile() {
		File tFile = loadTestFile("searches.csv");
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
	public void testHtmlUnitSet() {

		String inputValue = null;

		final HtmlUnitTask task = new HtmlUnitTask(inputValue);
		logger.info("invoke HtmlUnitTask(" + inputValue + ")  <- " + Thread.currentThread().getName());

		final String result = pool.invoke(task);
		logger.info("result = " + result + "\n");

		Assert.assertTrue(true); // no verification of any kind yet
	}

	public static File loadTestFile( String fileName ) {
		File junitFile = new File("build/resources/test/" + fileName );
		if ( junitFile.exists() ) {
			logger.info( "The file '" + junitFile.getAbsolutePath() + "' is loaded." );
		} else {
			logger.info( "Problem loading test data input file: " + junitFile.getAbsolutePath() );
		}	
		return junitFile;
	}

}

