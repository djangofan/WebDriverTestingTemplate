package webdriver.test;

import static org.junit.Assert.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import static qa.webdriver.util.WebDriverUtils.*;

public class MainPage extends LoadableComponent<MainPage> {

	private RemoteWebDriver driver;

	@FindBy(id = "textFieldTestInputControlID" )
	@CacheLookup
	public WebElement inputControlTextField;
	
	@FindBy(id = "textFieldTestProcessButtonID" )
	@CacheLookup
	public WebElement controlButton;

	public MainPage( RemoteWebDriver drv ) {
		super();
		this.driver = drv;
		driver.switchTo().defaultContent();
		LOGGER.info("MainPage constructor...");
	}

	public void setControlInputField( String text ) {
		clearAndType( inputControlTextField, text );
	}
	
	public void clearAndSetValue( WebElement field, String text ) { 
		field.clear(); 
		field.sendKeys( Keys.chord(Keys.CONTROL, "a" ), text ); 
	}

	public void clearAndType( WebElement field, String text ) {
		field.clear(); 
		field.sendKeys( text ); 
	}

	@Override
	protected void isLoaded() {    	
		LOGGER.info("MainPage.isLoaded()...");
		PageFactory.initElements( driver, this );
		assertTrue( "Title is not yet available.", driver.getTitle().equals("w1.html") );
	}

	/**
	 * Method: load
	 * Overidden method from the LoadableComponent class.
	 * @return	void
	 * @throws	null
	 */
	@Override
	protected void load() {
		LOGGER.info("MainPage.load()...");
		waitTimer(1, 1000);
	}

	/**
	 * Because of how the page object is initialized, we are using getAttribute here
	 * @param	sstr
	 * @return	void
	 */
	public void clickControlButton() {
		controlButton.click();
	}


}
