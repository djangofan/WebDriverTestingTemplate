package webdriver.test;

import static org.junit.Assert.*;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import static qa.webdriver.util.CoreUtils.LOGGER;
import static qa.webdriver.util.WebDriverUtils.*;

public class IFrame1 extends LoadableComponent<IFrame1> {

	private RemoteWebDriver driver;

	@FindBy(id = "iFrame1TextFieldTestInputControlID" ) public WebElement iFrame1TextFieldInput;
	@FindBy(id = "iFrame1TextFieldTestProcessButtonID" ) public WebElement copyButton;

	public IFrame1( RemoteWebDriver drv ) {
		super();
		this.driver = drv;
		this.driver.switchTo().frame("BodyFrame1");
		LOGGER.info("IFrame1 constructor...");
	}

	public void setInputField( String text ) {
		clearAndType( iFrame1TextFieldInput, text );
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
	protected void isLoaded() throws Error {    	
		LOGGER.info("IFrame1.isLoaded()...");
		PageFactory.initElements( driver, this );
		try {
			assertTrue( "Title is not yet available.", 
					driver.findElementByCssSelector("body form#webDriverUnitiFrame1TestFormID h1")
					.getText().equals("iFrame1 Test") );
		} catch ( NoSuchElementException e) {
			LOGGER.info("No such element." );
		}
	}

	/**
	 * Method: load
	 * Overidden method from the LoadableComponent class.
	 * @return	void
	 * @throws	null
	 */
	@Override
	protected void load() {
		LOGGER.info("IFrame1.load()...");
		waitTimer(1, 1000);
	}

	/**
	 * Because of how the page object is initialized, we are using getAttribute here
	 * @param	sstr
	 * @return	void
	 */
	public void clickCopyButton() {
		copyButton.click();
	}
	
	public void exitFrame() {
		this.driver.switchTo().defaultContent();
	}

}
