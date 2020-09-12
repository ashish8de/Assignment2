package test.amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import test.functionLibrary.ExternalData;

//HomePage Class
public class HomePage {
	
	private WebDriver driver;
	
	//Page Locators variables
	private By signInButton;
	private By signOutButton;
	private By categoryLink;	
	
	//Initiate class variables in constructor
	public HomePage(WebDriver driver, ExternalData or) {
		
		//Get class full name
		String className = this.getClass().getName();
		
		//Assign value to class variables
		this.driver = driver;
		this.signInButton = or.getLocator(className + ".signInButton");
		this.signOutButton = or.getLocator(className + ".signOutButton");
		this.categoryLink = or.getLocator(className + ".categoryLink");
		
		//Initiate class variables
		PageFactory.initElements(driver, this);
	}
	
	//Launch signin prompt
	public void signIn(){
		driver.findElement(signInButton).click();
		Reporter.log("Signin button was clicked.");
	}
	
	//Verify if user is logged in
	public boolean isUserLoggedIn() {
		
		boolean flag = driver.findElements(signOutButton).size() > 0;
		//Report Result
		if(flag) {
			Reporter.log("user was logged in.");
		} else {
			Reporter.log("user was not logged in.");
		}
		return flag;
	}
	
	//Navigate to department page
	public void navigateToDepartmentPage() {
		driver.findElement(categoryLink).click();
		Reporter.log("Shop by Category link was clicked.");
	}
	
	//Verify if page is loaded
	public boolean isPageDisplayed() {
		
		boolean flag = false;
		
		//Wait for Page elements to load
		WebElement ele = driver.findElement(signInButton);
		new WebDriverWait(driver, 10)
			.until(ExpectedConditions.visibilityOfAllElements(ele));
		
		//Check if element in page is loaded and is displayed
		flag = ele.isDisplayed();
		
		//Report Result
		if(flag) {
			Reporter.log("Home Page was loaded successfully.");
		} else {
			Reporter.log("Home Page was not loaded successfully.");
		}
		
		return flag;
	}
}
