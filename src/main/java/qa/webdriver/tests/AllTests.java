package qa.webdriver.tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

//@RunWith(Suite.class)
//@SuiteClasses({ GoogleTest.class })
public class AllTests {

	public static void main(String[] args) throws Exception {                    
	       JUnitCore.main("qa.webdriver.tests.GoogleTest");            
	}
	
}
