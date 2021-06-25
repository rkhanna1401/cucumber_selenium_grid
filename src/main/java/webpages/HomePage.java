package webpages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{


	public HomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='nav-search-submit-button']/../../../..//input[@id='twotabsearchtextbox']")
	private WebElement searchText;

	@FindBy(xpath = "(//a[@class='a-link-normal a-text-normal'])[1]")
	private WebElement product;


	public void setSearchText(String searchText) {
		this.searchText.sendKeys(searchText);
		this.searchText.sendKeys(Keys.ENTER);
	}

	public void selectProduct() {
		product.click();
	}
}
