package stepdefinition;

import org.openqa.selenium.WebDriver;
import WebdriverBase.GridDriverManager;
import webpages.BasePage;
import webpages.HomePage;
import webpages.LoginPage;
import webpages.ProductPage;

public class WorkflowManager {
	
	private WebDriver driver;
	private BasePage basePage;
	private LoginPage loginPage;
	private HomePage homePage;
	private ProductPage productPage;
	private GridDriverManager gridDriverManager;
	
	
	public  BasePage getBasePage(WebDriver driver) {
		this.basePage = new BasePage(driver);
		return this.basePage;
	}
	
	public  LoginPage getLoginPage(WebDriver driver) {
		this.loginPage = new LoginPage(driver);
		return this.loginPage;
	}
	
	public  HomePage getHomePage(WebDriver driver) {
		this.homePage = new HomePage(driver);
		return this.homePage;
	}
	
	public  ProductPage getProductPage(WebDriver driver) {
		this.productPage = new ProductPage(driver);
		return this.productPage;
	}
	
	public  GridDriverManager getGridDriverManager(WebDriver driver) {
		this.gridDriverManager = new GridDriverManager();
		return this.gridDriverManager;
	}
	
}
