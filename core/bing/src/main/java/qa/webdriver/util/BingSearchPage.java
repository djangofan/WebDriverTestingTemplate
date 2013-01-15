package qa.webdriver.util;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.SlowLoadableComponent;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static qa.webdriver.util.BingUtilities.*;

public class BingSearchPage extends SlowLoadableComponent<BingSearchPage> {

	/**
	 *  Inner class
	 *  A fluent API interface that provides methods for calling normal
	 *  page object methods within this class. 
	 */	
	public class BSPFluentInterface {

		private BingSearchPage bsp;

		public BSPFluentInterface(BingSearchPage bingSearchPage) {
			bsp = bingSearchPage;
		}

		public BSPFluentInterface clickElementByCSS( String clazz ) {
			System.out.println("Click element by CSS selector using Javascript method.");
			clickElementWithJSEByCSS( clazz );
			return this;
		}

		public BSPFluentInterface clickSearchButton() {
			bsp.searchButton.click();
			return this;
		}

		public BSPFluentInterface clickSearchField() {
			bsp.searchField.click();
			return this;
		}

		public BSPFluentInterface selectItem( String ele ) {
			System.out.println("Selecting item in list using fluent API.");
			selectInBingDropdown( ele );
			return this;
		}

		public BSPFluentInterface setSearchString( String sstr ) {
			clearAndType( bsp.searchField, sstr );
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
		public BSPFluentInterface waitForTime( int units, int ms ) {
			waitTimer( units, ms );
			return this;
		}

	}

	private static int timeOutInSeconds = 15; // seconds that get() will try to load the page
	private BSPFluentInterface bspfi;
	private Boolean isSFieldPresent = false;
	@FindBy(id = "sb_form_q") private WebElement searchField;
	@FindBy(id = "sb_form_go") private WebElement searchButton;

	public BingSearchPage() {
		super( new SystemClock(), timeOutInSeconds);
		System.out.println("Loaded Bing Search Page");
		bspfi = new BSPFluentInterface( this );
		this.get();
		PageFactory.initElements( driver, this ); // initialize page elements
	}

	public void clickSearchButton() {
		searchButton.click();
	}

	public void clickSearchField() {
		searchField.click();
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
		System.out.println("BingSearchPage.isLoaded()...");
		Assert.assertTrue("Bing search page is not yet loaded.", isSearchFieldVisible() );
	}

	/**
	 * Method: isSearchFieldVisible
	 * Method that checks to see if the page contains one and only one
	 *  Bing search field WebElement.
	 * @return	boolean
	 * @throws	none since findElements throws no exception when 0 are found
	 */
	public boolean isSearchFieldVisible() {
		isSFieldPresent = driver.findElements( By.id("sb_form_q") ).size() == 1;					
		return isSFieldPresent;
	}

	/**
	 * Method: load
	 * Overidden method from the LoadableComponent class.
	 * @return	void
	 * @throws	null
	 */
	@Override
	protected void load() {
		System.out.println("BingSearchPage.load()...");
		if ( isSFieldPresent ) {
			Wait<WebDriver> wait = new WebDriverWait( driver, 3 );        
			wait.until( visibilityOfElementLocated( By.id("sb_form_q") ) ).click();
		}
	}

	public void setSearchString( String sstr ) {
		clearAndType( searchField, sstr );
	}

	/**
	 * Method: withFluent
	 * Entrypoint for an object that can start a fluent action thread.
	 * @return	BSPFluentInterface
	 * @throws	null
	 */
	public BSPFluentInterface withFluent() {
		return bspfi; 
	}	

}
