package stepdefinition;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import Utils.GenericUtils;
import Utils.JsonUtils;
import WebdriverBase.GridDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import webpages.BasePage;
import webpages.HomePage;
import webpages.LoginPage;

/**
 * @author rishikhanna
 *
 */

public class LoginWorkflow {


	private WebDriver driver;
	private BasePage basePage;
	private LoginPage loginPage;
	private HomePage homePage;
	private GridDriverManager gridDriverManager;
	private BaseWorkflow baseWorkflow;

	@Before
	public void init() throws Exception {
		gridDriverManager = new GridDriverManager();
		driver = gridDriverManager.getDriver();
		baseWorkflow = new BaseWorkflow(driver);
		basePage = new BasePage(driver);
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
	}



	@Given("Navigate To Loginpage")
	public void navigate_to_loginpage() {
		basePage.load();
		loginPage.clickSignIn();
	}

	@When("User enter correct username {string}")
	public void user_enter_correct_username(String string) {
		//loginPage.setEmail(JsonUtils.getValFromJson(baseWorkflow.getTestData(), "email"));
		loginPage.setEmail(string);
		loginPage.clicktContinueBtn();
	}


	@When("User enter incorrect password")
	public void user_enter_incorrect_password() {
		loginPage.setPassword(JsonUtils.getValFromJson(baseWorkflow.getTestData(), "password"));
		loginPage.clicktSignInBtn();
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
		loginPage.setEmail(JsonUtils.getValFromJson(baseWorkflow.getTestData(), "email"));
	}

	@When("User selects Forgot password")
	public void user_selects_forgot_password() {
		loginPage.clickForgotPassword();
	}

	@When("User navigated to password reset page")
	public void user_navigated_to_password_reset_page() {

	}

	@Then("User succesfully resets the password")
	public void user_succesfully_resets_the_password() {

	}

	@After
	public void closure() {
		driver.quit();
	}
}
