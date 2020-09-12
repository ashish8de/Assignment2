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

//CartPage Class
public class CartPage {
	
	private WebDriver driver;
	
	//Page Locators variables
	private By productsList;
	private By subTotalLabel;
	private By productTitle;
	private By productPrice;
	
	//Initiate class variables in constructor
	public CartPage(WebDriver driver, ExternalData or) {
		
		//Get class full name
		String className = this.getClass().getName();
		
		//Assign values to class variables
		this.driver = driver;
		this.productsList = or.getLocator(className + ".productsList");
		this.subTotalLabel = or.getLocator(className + ".subTotalLabel");
		this.productTitle = or.getLocator(className + ".productTitle");
		this.productPrice = or.getLocator(className + ".productPrice");
		
		//Initiate class variables
		PageFactory.initElements(driver, this);
	}
	
	//Verify if page is loaded
	public boolean isPageDisplayed() {
		
		boolean flag = false;
		
		//Wait for Page elements to load
		WebElement ele = driver.findElement(subTotalLabel);
		new WebDriverWait(driver, 10)
			.until(ExpectedConditions.visibilityOfAllElements(ele));
		
		//Check if element in page is loaded and is displayed
		flag = ele.isDisplayed();
		
		//Report Result
		if(flag) {
			Reporter.log("Cart Page was loaded successfully.");
		} else {
			Reporter.log("Cart Page was not loaded successfully.");
		}
		
		return flag;
	}
	
	//Verify if Subtotal field has correct sum
	public boolean isSubTotalCorrect() {
		
		boolean flag = false;
		double SubTotal = 0;
		
		//Sum prices for all product in cart
		for (WebElement ele:driver.findElements(productsList)) {
			SubTotal = SubTotal + Double.parseDouble
					(ele.findElement(productPrice).getText().replaceAll("[^.a-zA-Z0-9]", ""));
		}

		flag = (SubTotal == Double.parseDouble
				(driver.findElement(subTotalLabel).getText().replaceAll("[^.a-zA-Z0-9]","")));
		
		//Report Result
		if(flag) {
			Reporter.log("Subtotal in cart was correct.");
		} else {
			Reporter.log("Subtotal in cart was not correct.");
		}
		
		return flag;
	}

	//Verify if correct number of products are listed in cart
	public boolean isCorrectProductsNumListed(int productCount) {
		
		boolean flag = false;
		
		//Check if correct number of products are added in cart
		if (productCount==driver.findElements(productsList).size()) {
			flag = true;
		} else {
			flag = false;
		}
		
		//Report Result
		if(flag) {
			Reporter.log("Correct number of products were listed in cart.");
		} else {
			Reporter.log("Correct number of products were not listed in cart.");
		}
		
		return flag;
	}
	
	//Verify if selected products are listed in cart
	public boolean isCorrectProductsListed(HashMap<String, String> products) {
		
		boolean flag = false;
		String price;
		
		for (WebElement ele:driver.findElements(productsList)) {
			price = ele.findElement(productPrice).getText();
			
			//Check if selected products are added in cart
			if (products.containsKey(price)){
				if (products.get(price).equals(ele.findElement(productTitle).getText())) {
					flag = true;
				} else {
					flag = false;
				}
			} else {
				flag = false;
			}
		}

		//Report Result
		if(flag) {
			Reporter.log("Selected products were displayed in cart.");
		} else {
			Reporter.log("Selected products were not displayed in cart.");
		}
		
		return flag;
	}
	
}
