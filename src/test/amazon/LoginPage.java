package test.amazon;

import java.util.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import test.functionLibrary.ExternalData;

//LoginPage Class
public class LoginPage {
	
	private WebDriver driver;
	
	//Page Locators variables
	private By emailTextbox;
	private By passwordTextbox;
	private By signInButton;
	private By continueButton;
	
	//Initiate class variables in constructor
	public LoginPage(WebDriver driver, ExternalData or) {
		
		//Get class full name
		String className = this.getClass().getName();
		
		//Assign value to class variables
		this.driver = driver;
		this.emailTextbox=or.getLocator(className + ".emailTextbox");
		this.passwordTextbox=or.getLocator(className + ".passwordTextbox");
		this.signInButton=or.getLocator(className + ".signInButton");
		this.continueButton=or.getLocator(className + ".continueButton");
		
		//Initiate class variables
		PageFactory.initElements(driver, this);
	}

	//Login to application
	public void login(ExternalData credentials) {
		
		String emailAddress = credentials.getValue("Username");
		String encyptedPassword = credentials.getValue("EncyptedPassword");
		
		//Enter email id and click on continue button
		driver.findElement(emailTextbox).sendKeys(emailAddress);
		driver.findElement(continueButton).click();
		
		//Wait for password field to enable
		WebElement ele = driver.findElement(passwordTextbox);
		new WebDriverWait(driver, 10)
			.until(ExpectedConditions.visibilityOfAllElements(ele));
		
		//Enter password and click on signin button
		ele.sendKeys(new String(Base64.getDecoder().decode(encyptedPassword.getBytes())));
		driver.findElement(signInButton).click();
		
		Reporter.log("User signin request was sent.");
	}
	
	//Verify if page is loaded
	public boolean isPageDisplayed() {
		boolean flag = false;
		
		//Wait for Page elements to load
		WebElement ele = driver.findElement(emailTextbox);
		new WebDriverWait(driver, 10)
			.until(ExpectedConditions.visibilityOfAllElements(ele));
		
		//Check if element in page is loaded and is displayed
		flag = ele.isDisplayed();
		
		//Report Result
		if(flag) {
			Reporter.log("Login Page was loaded successfully.");
		} else {
			Reporter.log("Login Page was not loaded successfully.");
		}
		
		return flag;
	}
}
