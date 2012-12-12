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

import static qa.webdriver.util.BingUtilities.*;

public class BingSearchPage extends SlowLoadableComponent<BingSearchPage> {
	
	private static int timeOutInSeconds = 3;

    @FindBy(id = "sb_form_q") private WebElement searchField;

	@FindBy(id = "sb_form_go") private WebElement searchButton;

	public BingSearchPage() {
        super( new SystemClock(), timeOutInSeconds);
		System.out.println("Loaded Bing Search Page");
		this.get(); //calls load and isLoaded
		PageFactory.initElements(driver, this); 
    }
	
	public void clickSearchButton() {
		searchButton.click();
	}
	
	@Override
	protected void isLoaded() throws Error {
		System.out.println("Calling BingSearchPage.isLoaded...");
		final WebElement coreField = (new WebDriverWait(driver, 10))
				.until(new ExpectedCondition<WebElement>(){
					public WebElement apply(WebDriver d) {
						return d.findElement( By.id("sb_form_q") );
					}});
		if ( coreField.isDisplayed() )
		{
			System.out.println("Bing search page is loaded.\nWill initialize page object.");
		} else {
			System.out.println("Bing search page is not yet loaded." );
		} 
	}
	
	@Override
	protected void load() {
		System.out.println("Calling BingSearchPage.load...");
		Wait<WebDriver> wait = new WebDriverWait(driver, 30); 
		driver.get("http://bing.com");  // just a hack
	}
	
	public void setSearchString( String sstr ) {
		clearAndType( searchField, sstr );
	}	
    
}
