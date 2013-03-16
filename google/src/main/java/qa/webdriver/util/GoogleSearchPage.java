package qa.webdriver.util;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.SlowLoadableComponent;
import org.openqa.selenium.support.ui.SystemClock;
import static qa.webdriver.util.WebDriverUtils.*;

public class GoogleSearchPage extends SlowLoadableComponent<GoogleSearchPage> {

	/**
	 *  Inner class
	 *  A fluent API interface that provides methods for calling normal
	 *  page object methods within this class. 
	 */	
	public class GSPFluentInterface {

		public GSPFluentInterface(GoogleSearchPage googleSearchPage) {
			staticlogger.info("Initialized fluent interface.");
		}

		public GSPFluentInterface clickLogo() {
			staticlogger.info("Click Google logo.");
			WebElement logo = null;
			By locator = By.cssSelector( "div.gbqlca" );
			logo = getElementByLocator( locator );
			logo.click();
			return this;
		}

		public GSPFluentInterface clickSearchButton() {
			searchButton.click();
			return this;
		}

		public GSPFluentInterface clickSearchField() {
			searchField.click();
			return this;
		}

		public GSPFluentInterface selectItem( String match ) {
			staticlogger.info("Selecting item in list using fluent API.");
			selectInGoogleDropdown( match );
			return this;
		}

		public GSPFluentInterface setSearchString( String sstr ) {
			By locator = By.id( searchField.getAttribute("id") );
			clearAndType( locator, sstr );
			return this;
		}		

		/**
		 *  Method: waitForTime
		 *  This method shows an example of a fluent method that provides
		 *   access to a method from an outside class.  In this case the
		 *   waitTimer in the utility class.  We cannot call waitTimer in
		 *   the middle of a fluent thread and so we provide a fluent method
		 *   here so that a wait can be defined.   
		 */
		public GSPFluentInterface waitForTime( int units, int ms ) {
			waitTimer( units, ms );
			return this;
		}

	}

	public GSPFluentInterface gspfi;
	public static final String searchFieldId = "gbqfq";
	public static final String searchButtonId = "gbqfb";
	//public final String searchFieldCSSLocator = "div#gs_lc0 input#gbqfq.gbqfif";
	//public final String searchButtonCSSLocator = "div#gbqfw #gbqfb.gbqfb";
	public static final String suggestCSSLocator = "table.gstl_0 tbody tr td.gssb_e table.gssb_m tbody tr td.gssb_a div.gsq_a table tbody tr td span";

	@FindBy(id = searchFieldId ) public WebElement searchField;
	@FindBy(id = searchButtonId ) public WebElement searchButton;

	public GoogleSearchPage() {
		super( new SystemClock(), DEFAULT_EXPLICIT_WAIT);
		this.get(); // SlowLoadableComponent.get()
		staticlogger.info("GoogleSearchPage constructor...");
		gspfi = new GSPFluentInterface( this ); // use this only if you want to
	}

	public void clickSearchButton() {
		if ( searchButton == null ) {
			searchButton = getElementByLocator( By.id( searchButtonId ) );
		} else {
		    try {
				searchButton.click();
			} catch ( ElementNotVisibleException e ) {
				staticlogger.info( "Element not visible exception clicking search button.\n" + e.getMessage() );
				e.printStackTrace();
			} catch ( Exception e ) {
				staticlogger.info( "Exception clicking search button.\n" + e.getMessage() );
				e.printStackTrace();
			}
		}
	}

	public void clickSearchField() {
		if ( searchField == null ) {
			searchField = getElementByLocator( By.id( searchFieldId ) );
		} else {
		    try {
				searchField.click();
			} catch ( Exception e ) {
				staticlogger.info( "Error clicking search field.\n" + e.getMessage() );
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method: isLoaded()
	 * Overidden method from the LoadableComponent class.
	 * This method must contain an Assert on visibility of an element in order
	 *  to trigger another call of load() if element is not found.
	 * @return	void
	 * @throws	null
	 */
	@Override
	protected void isLoaded() throws Error {    	
		staticlogger.info("GoogleSearchPage.isLoaded()...");
		boolean loaded = false;
		if ( !(searchField == null ) ) {
			try {
				if ( searchField.isDisplayed() ) {
					loaded = true;
				}
			} catch ( ElementNotVisibleException e ) {
				staticlogger.info( "Element may not be displayed yet." );
			}
		}
		Assert.assertTrue( "Google search field is not yet displayed.", loaded );
	}

	/**
	 * Method: load
	 * Overidden method from the LoadableComponent class.
	 * @return	void
	 * @throws	null
	 */
	@Override
	protected void load() {
		staticlogger.info("GoogleSearchPage.load()...");
		PageFactory.initElements( driver, this ); // initialize WebElements on page
		waitTimer(3, 1000);
	}

	/**
	 * Because of how the page object is initialized, we are using getAttribute here
	 * @param	sstr
	 * @return	void
	 */
	public void setSearchString( String sstr ) {
		By locator = By.id( searchField.getAttribute("id") );
		clearAndType( locator, sstr );
	}

	/**
	 * Method: withFluent
	 * Entrypoint for an object that can start a fluent action thread.
	 * @return	GSPFluentInterface
	 * @throws	null
	 */
	public GSPFluentInterface withFluent() {
		return gspfi; 
	}	

	/**
	 * Method: selectInGoogleDropdown
	 * Selects element in dropdown using keydowns method (just for fun)
	 * as long as you typed a string first.
	 * @return	void
	 * @throws	null
	 */
	public void selectInGoogleDropdown( String match ) {
		staticlogger.info("Selecting \"" + match + "\" from Google dropdown.");
		List<WebElement> allSuggestions = driver.findElements( By.cssSelector( suggestCSSLocator ) );  
		boolean operate = true;
		int tries = 0;
		while ( operate && tries < 5 ) {
			tries +=1;
			try {
				Thread.sleep(2);
				for ( WebElement suggestion : allSuggestions ) {
					if ( suggestion.getText().contains( match ) ) {
						suggestion.click();
						staticlogger.info("Found item and clicked it.");
						Thread.sleep(2); // just to slow it down so human eyes can see it
					}
				}
				operate = false;
			} catch ( StaleElementReferenceException e ) {
				staticlogger.info("Error while iterating dropdown list:\n" + e.getMessage() );
				//e.printStackTrace();
			} catch ( InterruptedException ie ) {
				// do nothing
			}
		}
		staticlogger.info("Finished select in Google dropdown.");
	}

}
