package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {

	public ProductPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[@class='a-button-stack']//span[contains(text(),'Buy Now')]")
	private WebElement buyNow;

	public void selectBuyNow() {
		buyNow.click();
	}
}
