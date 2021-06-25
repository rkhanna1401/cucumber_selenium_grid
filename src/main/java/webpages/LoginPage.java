package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
	
	}

	@FindBy(xpath = "//input[@type='email']")
	private WebElement email;

	@FindBy(xpath = "//input[@type='password']")
	private WebElement password;

	@FindBy(xpath = "//input[@id='continue']")
	private WebElement continueBtn;

	@FindBy(xpath = "//input[@id='signInSubmit']")
	private WebElement signInBtn;

	@FindBy(xpath = "//a[contains(text(),'Forgot Password')]]")
	private WebElement forgotPassword;

	@FindBy(xpath = "//span[contains(text(),'Your password is incorrect')]")
	private WebElement errorMessageLbl;

	@FindBy(xpath = "//a[contains(text(),'Sign in')]")
	private WebElement signIn;

	public void clickSignIn() {
		signIn.click();
	}

	public WebElement getErrorMessageLbl() {
		return errorMessageLbl;
	}

	public void setPassword(String password) {
		this.password.sendKeys(password);
	}

	public void clicktSignInBtn() {
		signInBtn.click();
	}

	public void clicktContinueBtn() {
		continueBtn.click();
	}

	public void clickForgotPassword() {
		forgotPassword.click();
	}

	public void setEmail(String email) {
		this.email.sendKeys(email);
	}


}
