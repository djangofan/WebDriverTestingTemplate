package qa.webdriver.tests;

import qa.webdriver.util.GoogleUtilities;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * To run suite you need to pass the Gradle '-Dtest.single=SuiteOne' option to the JVM
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ GoogleTest.class })
public class AllTests extends GoogleUtilities {

	public AllTests(String tName, String sString, String dMatch) {
		super(tName, sString, dMatch);
	}

	@BeforeClass
	public static void setUpSuiteOne() {
		wds = initializeJSONHub( "build/resources/test/DefaultNodeWebDriver.json" );
		logger.info("Finished setUpSuiteOne");
	}

	@AfterClass
	public static void tearDownSuiteOne() {
		closeAllBrowserWindows(); 
		try {
			wds.shutDownNodeAndHub();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Finished tearDownSuiteOne");
	}

}
