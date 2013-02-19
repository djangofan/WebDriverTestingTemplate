package testing.qa;

import java.util.concurrent.ForkJoinPool;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * To run suite you need to pass the Gradle '-Dtest.single=SuiteOne' option to the JVM
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ ConcurrentTests.class })
public class AllTests {

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
	public static String inputDir;	
	public static Logger logger = LoggerFactory.getLogger( HtmlUnitTask.class );
    public static ForkJoinPool pool = new ForkJoinPool(AVAILABLE_PROCESSORS);    

	public AllTests( String dirName ) {
		inputDir = dirName;
	}

	@BeforeClass
	public static void setUpAllTests() {
		
		logger.info("Finished setUpAllTests");
	}

	@AfterClass
	public static void tearDownAllTests() {
		
		logger.info("Finished tearDownAllTests");
	}

}
