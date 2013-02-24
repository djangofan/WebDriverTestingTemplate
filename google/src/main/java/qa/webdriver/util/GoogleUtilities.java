package qa.webdriver.util;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public abstract class GoogleUtilities extends WebDriverUtils {
	
	public GoogleUtilities( String tName, String sString, String dMatch ) {
		super();
		testName = tName;
		searchString = sString;
		ddMatch = dMatch;
		logger.info("Running test: " + testName + ", " + searchString + ", " + ddMatch );
	}

    /**
     * Method: selectInGoogleDropdown
     * Selects element in dropdown using keydowns (just for fun).
     * @return	void
     * @throws	null
     */
    public static void selectInGoogleDropdown( String match ) {
        WebElement dd = driver.findElement( By.xpath( "//input[@id='gbqfq']" ) );
        waitTimer(4, 500);
        long end = System.currentTimeMillis() + 5000;
        while ( System.currentTimeMillis() < end ) {
            WebElement resultsLi = driver.findElement( By.xpath( "//div[@class='gsq_a']" ) );
            if ( resultsLi.isDisplayed() ) break;
        }
        int matchedPosition = 0;
        int optpos = 0;
        List<WebElement> allSuggestions = driver.findElements( By.xpath( "//div[@class='gsq_a']/table/tbody/tr/td/span" ) );        
        for ( WebElement suggestion : allSuggestions ) {
            if ( suggestion.getText().contains( match ) ) {
                matchedPosition = optpos;
            } else {
                optpos++;
            }
        }
        for ( int i= 0; i < matchedPosition ; i++ ) {
            dd.sendKeys( Keys.ARROW_DOWN );
            logger.info("...key down");
            waitTimer(1, 500); // to slow down the arrow key so you can see it
        }
        WebElement targetItem = allSuggestions.get( matchedPosition );
        targetItem.click();
        waitTimer(1, 500);
    }

}