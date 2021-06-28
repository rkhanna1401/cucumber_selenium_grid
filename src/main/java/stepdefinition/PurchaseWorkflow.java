package stepdefinition;

import org.openqa.selenium.WebDriver;

import Utils.GenericUtils;
import Utils.JsonUtils;
import WebdriverBase.GridDriverManager;
import io.cucumber.java.Before;
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


	private WebDriver driver;
	private GridDriverManager gridDriverManager;
	private BaseWorkflow baseWorkflow;
	private WorkflowManager workflowManager;

	@Before
	public void init() throws Exception {
		gridDriverManager = GridDriverManager.getInstance();
		driver = gridDriverManager.getDriver();
		baseWorkflow = new BaseWorkflow(driver);
		workflowManager = new WorkflowManager();
	}



	@Given("You are on homepage of Amazon")
	public void you_are_on_homepage_of_amazon() throws Exception {		
		workflowManager.getBasePage(driver).load();
	}

	@When("You search for Macbook Air")
	public void you_search_for_macbook_air() {
		workflowManager.getHomePage(driver).setSearchText(JsonUtils.getValFromJson(baseWorkflow.getTestData(), "product"));
		workflowManager.getHomePage(driver).selectProduct();
	}


	@When("Select Buy Now")
	public void select_buy_now() {
		GenericUtils.switchToChildWindow(driver);
		workflowManager.getProductPage(driver).selectBuyNow();
	}

	@Then("You make a successful purchase")
	public void you_make_a_successful_purchase() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

}
