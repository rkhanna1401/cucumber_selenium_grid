package stepdefinition;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import Utils.JsonUtils;
import WebdriverBase.GridDriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * @author rishikhanna
 *
 */

public class LoginWorkflow {

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



	@Given("Navigate To Loginpage")
	public void navigate_to_loginpage() {
		workflowManager.getBasePage(driver).load();
		workflowManager.getLoginPage(driver).clickSignIn();
	}

	@When("User enter correct username {string}")
	public void user_enter_correct_username(String string) {
		//loginPage.setEmail(JsonUtils.getValFromJson(baseWorkflow.getTestData(), "email"));
		workflowManager.getLoginPage(driver).setEmail(string);
		workflowManager.getLoginPage(driver).clicktContinueBtn();
	}


	@When("User enter incorrect password")
	public void user_enter_incorrect_password() {
		workflowManager.getLoginPage(driver).setPassword(JsonUtils.getValFromJson(baseWorkflow.getTestData(), "password"));
		workflowManager.getLoginPage(driver).clicktSignInBtn();
	}

	@Then("Message displayed Wrong UserName & Password")
	public void message_displayed_wrong_user_name_password() {
		//Assert.assertTrue(GenericUtils.isDisplayed(loginPage.getErrorMessageLbl()));
	}

	@Then("User is not logged-in.")
	public void user_is_not_logged_in() {

	}

	@Given("Navigate to password page")
	public void navigate_to_password_page() {
		navigate_to_loginpage();
		workflowManager.getLoginPage(driver).setEmail(JsonUtils.getValFromJson(baseWorkflow.getTestData(), "email"));
	}

	@When("User selects Forgot password")
	public void user_selects_forgot_password() {
		workflowManager.getLoginPage(driver).clickForgotPassword();
	}

	@When("User navigated to password reset page")
	public void user_navigated_to_password_reset_page() {
		Assert.fail();
	}

	@Then("User succesfully resets the password")
	public void user_succesfully_resets_the_password() {

	}
}
