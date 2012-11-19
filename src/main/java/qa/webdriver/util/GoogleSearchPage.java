package qa.webdriver.util;

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

import static qa.webdriver.util.ExtendedUtilities.*;

public class GoogleSearchPage extends SlowLoadableComponent<GoogleSearchPage> {
	
	private static int timeOutInSeconds = 3;

    public GoogleSearchPage() {
        super( new SystemClock(), timeOutInSeconds);
		System.out.println("Loaded Google Search Page");
		this.get(); //calls load and isLoaded
		PageFactory.initElements(driver, this); 
    }

	@Override
	protected void load() {
		System.out.println("Calling GoogleSearchPage.load...");
		Wait<WebDriver> wait = new WebDriverWait(driver, 30); 
		if ( driver.findElement( By.id("//div[@id='navBar']//div[1]")).getAttribute("onclick") == null )
		{			
			WebElement defnav = wait.until( visibilityOfElementLocated( By.id("gbqfq") ) );
			defnav.click();
			WebElement adnav = wait.until( visibilityOfElementLocated( By.id("gbqfq") ) );
			adnav.click();			
		} else {
			WebElement adnav = wait.until( visibilityOfElementLocated( By.id("gbqfq") ) );
			adnav.click();
		}
	}

	@Override
	protected void isLoaded() throws Error {
		System.out.println("Calling GoogleSearchPage.isLoaded...");
		final WebElement coreField = (new WebDriverWait(driver, 10))
				.until(new ExpectedCondition<WebElement>(){
					public WebElement apply(WebDriver d) {
						return d.findElement( By.id("gbqfq") );
					}});
		if ( coreField.isDisplayed() )
		{
			System.out.println("Google search page is loaded.\nWill initialize page object.");
		} else {
			System.out.println("Google search page is not yet loaded." );
		} 
	}
	
	@FindBy(id = "gbqfq") private WebElement searchField;
	public void setSearchString( String sstr ) {
		clearAndType( searchField, sstr );
	}
	
	@FindBy(id = "gbqfb") private WebElement searchButton;
	public void clickSearchButton() {
		searchButton.click();
	}
	
    
}
