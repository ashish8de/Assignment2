package test.amazon;

import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import test.functionLibrary.ExternalData;

//ProductPage Class
public class ProductPage {
	
	private WebDriver driver;
	
	//Page Locators variables
	private By addToCartButton;
	private By productPrice;
	private By productTitle;
	private By cartButton;
	private By newcartButton;
	
	//Initiate class variables in constructor
	public ProductPage(WebDriver driver, ExternalData or) {
		
		//Get class full name
		String className = this.getClass().getName();
		
		//Assign value to class variables
		this.driver = driver;
		this.addToCartButton = or.getLocator(className + ".addToCartButton");
		this.productPrice = or.getLocator(className + ".productPrice");
		this.productTitle = or.getLocator(className + ".productTitle");
		this.cartButton = or.getLocator(className + ".cartButton");
		this.newcartButton = or.getLocator(className + ".newcartButton");
		
		//Initiate class variables
		PageFactory.initElements(driver, this);
	}
	
	//Add product to cart
	public void addToCart() {
		driver.findElement(addToCartButton).click();
		Reporter.log("Add to Cart button was clicked.");
	}
	
	//Navigate to cart page
	public void navigateToCart() {
		
		//Check which cart button is displayed and click on cart button
		if(driver.findElements(cartButton).size()> 0) {
			driver.findElement(cartButton).click();
		} else if (driver.findElements(newcartButton).size()> 0) {
			driver.findElement(newcartButton).click();
		}
		Reporter.log("Cart button was clicked.");
	}

	//Navigate back to department page
	public void returnToDepartmentPage() {
		driver.navigate().back();
		driver.navigate().back();
		Reporter.log("Navigated back to Department Page.");
	}
	
	//Verify if correct product page is loaded
	public boolean isPageDisplayed() {
		
		boolean flag = false;
		
		//Wait for Page elements to load
		WebElement ele = driver.findElement(addToCartButton);
		new WebDriverWait(driver, 10)
			.until(ExpectedConditions.visibilityOfAllElements(ele));
		
		//Check if element in page is loaded and is displayed
		flag = ele.isDisplayed();
		
		//Report Result
		if(flag) {
			Reporter.log("Product Page was loaded successfully.");
		} else {
			Reporter.log("Product Page was not loaded successfully.");
		}
		
		return flag;
	}
	
	//Verify if correct product page is loaded
	public boolean isCorrectProductsPage(HashMap<String, String> products) {
		
		boolean flag = false;
		String price = driver.findElement(productPrice).getText();
		
			//Check if product details matches which category page
			if (products.containsKey(price)){
				if (products.get(price).equals(driver.findElement(productTitle).getText())) {
					flag = true;
				}
				else {
					flag = false;
				}
			}
		//Report Result
		if(flag) {
			Reporter.log("Correct product page was opened.");
		} else {
			Reporter.log("Correct product page was not opened.");
		}
		
		return flag;
	}
}
