package qa.webdriver.util;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import qa.webdriver.core.UtilityClass;

public class BingUtilities extends UtilityClass {

	public BingUtilities() {
		super(); // to prevent instantiation as an object
	}

	public static void selectInBingDropdown( String match )	{
		//TODO there may be a problem with this method
		WebElement dd = driver.findElement( By.xpath( "//input[@id='sb_form_q']" ) );
		waitTimer(4, 500);
		long end = System.currentTimeMillis() + 5000;
		while ( System.currentTimeMillis() < end ) {
			WebElement resultsLi = driver.findElement( By.xpath( "//ul[@class='sa_drw']" ) );
			if ( resultsLi.isDisplayed() ) break;
		}
		int matchedPosition = 0;
		int optpos = 0;
		List<WebElement> allSuggestions = driver.findElements( By.xpath( "//ul[@class='sa_drw']/li/div" ) );        
		for ( WebElement suggestion : allSuggestions ) {
            if ( suggestion.getText().contains( match ) ) {
                matchedPosition = optpos;
            } else {
                optpos++;
            }
		}
		for ( int i= 0; i < matchedPosition ; i++ ) {
			dd.sendKeys( Keys.ARROW_DOWN );
			System.out.println("...key down");
			waitTimer(1, 500); // to slow down the arrow key so you can see it
		}
		WebElement targetItem = allSuggestions.get( matchedPosition );
		targetItem.click();
	}

}