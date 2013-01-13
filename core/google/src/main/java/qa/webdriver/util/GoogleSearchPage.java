package qa.webdriver.util;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.SlowLoadableComponent;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static qa.webdriver.util.GoogleUtilities.*;

public class GoogleSearchPage extends SlowLoadableComponent<GoogleSearchPage> {
	
    /**
     *  Inner class
     *  A fluent API interface that provides methods for calling normal
     *  page object methods within this class. 
     */	
    public class GSPFluentInterface {
        
        private GoogleSearchPage gsp;
		
        public GSPFluentInterface(GoogleSearchPage googleSearchPage) {
            gsp = googleSearchPage;
        }

        public GSPFluentInterface clickLogo( String id ) {
            System.out.println("Click Google logo using fluent API.");
            clickElementWithJSE( id );
            return this;
        }
		
        public GSPFluentInterface clickSearchButton() {
            gsp.searchButton.click();
            return this;
        }
		
        public GSPFluentInterface clickSearchField() {
            gsp.searchField.click();
            return this;
        }
		
        public GSPFluentInterface selectItem( String ele ) {
            System.out.println("Selecting item in list using fluent API.");
            selectInGoogleDropdown( ele );
            return this;
        }
		
        public GSPFluentInterface setSearchString( String sstr ) {
            clearAndType( gsp.searchField, sstr );
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
    
    private static int timeOutInSeconds = 15; // seconds that get() will try to load the page
    private GSPFluentInterface gspfi;
    private Boolean isSFieldPresent = false;
    @FindBy(id = "gbqfq") private WebElement searchField;
    @FindBy(id = "gbqfb") private WebElement searchButton;
	
    public GoogleSearchPage() {
        super( new SystemClock(), timeOutInSeconds);
        System.out.println("GoogleSearchPage constructor...");
        gspfi = new GSPFluentInterface( this );
        this.get(); // if load() fails, calls isLoaded() until page is finished loading
        PageFactory.initElements(driver, this); // initialize WebElements on page 
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
    	System.out.println("GoogleSearchPage.isLoaded()...");
        Assert.assertTrue("Google search page is not yet loaded.", isSearchFieldVisible() );
    }
	
    /**
     * Method: isSearchFieldVisible
     * Method that checks to see if the page contains one and only one
     *  Google search field WebElement.
     * @return	boolean
     * @throws	none since findElements throws no exception when 0 are found
     */
    public boolean isSearchFieldVisible() {
    	isSFieldPresent = driver.findElements( By.id("gbqfq") ).size() == 1;					
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
        System.out.println("GoogleSearchPage.load()...");
        if ( isSFieldPresent ) {
            Wait<WebDriver> wait = new WebDriverWait( driver, 3 );        
            wait.until( visibilityOfElementLocated( By.id("gbqfq") ) ).click();
        }
    }
	
    /**
     * Method: selectInGoogleDropdown
     * Selects element in dropdown using keydowns (just for fun).
     * @return	void
     * @throws	null
     */
    public void selectInGoogleDropdown( String match ) {
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
            System.out.println("...key down");
            waitTimer(1, 500); // to slow down the arrow key so you can see it
        }
        WebElement targetItem = allSuggestions.get( matchedPosition );
        targetItem.click();
        waitTimer(1, 500);
    }
	
    public void setSearchString( String sstr ) {
        clearAndType( searchField, sstr );
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
    
}
