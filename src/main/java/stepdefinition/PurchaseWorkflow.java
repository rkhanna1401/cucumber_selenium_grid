package stepdefinition;

import org.openqa.selenium.WebDriver;

import Utils.GenericUtils;
import Utils.JsonUtils;
import WebdriverBase.GridDriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import webpages.BasePage;
import webpages.HomePage;
import webpages.ProductPage;

/**
 * @author rishikhanna
 *
 */

public class PurchaseWorkflow {


	private HomePage homePage;
	private ProductPage productPage;
	private WebDriver driver;
	private GridDriverManager gridDriverManager;
	private BaseWorkflow baseWorkflow;
	private BasePage basePage;
	
	//@Before
	public void init() throws Exception {
		gridDriverManager = new GridDriverManager();
		driver = gridDriverManager.getDriver();
		baseWorkflow = new BaseWorkflow(driver);
		basePage = new BasePage(driver);
		homePage = new HomePage(driver);
		productPage = new ProductPage(driver);
	}

	
	@Given("You are on homepage of Amazon")
	public void you_are_on_homepage_of_amazon() throws Exception {		
		basePage.load();
	}

	@When("You search for Macbook Air")
	public void you_search_for_macbook_air() {
		homePage.setSearchText(JsonUtils.getValFromJson(baseWorkflow.getTestData(), "product"));
		homePage.selectProduct();
	}


	@When("Select Buy Now")
	public void select_buy_now() {
		GenericUtils.switchToChildWindow(driver);
		productPage.selectBuyNow();
	}

	@Then("You make a successful purchase")
	public void you_make_a_successful_purchase() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}
	
	//@After
	public void closure() {
		driver.quit();
	}
}
