package test.amazon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import test.functionLibrary.ExternalData;

//DepartmentPage Class
public class DepartmentPage {
	
	private WebDriver driver;
	
	//Page Locators variables
	private By categoryOption;
	private By categoryBanner;
	private By sortingDropdown;
	private By departmentList;
	private By sortedItemsList;
	private By priceElements;
	private By productPrice;
	private By productTitle;
	private By productPageLink;
	
	//Initiate class variables in constructor
	public DepartmentPage(WebDriver driver, ExternalData or) {
		
		//Get class full name
		String className = this.getClass().getName();
		
		//Assign values to class variables
		this.driver = driver;
		this.categoryOption = or.getLocator(className + ".categoryOption");
		this.categoryBanner = or.getLocator(className + ".categoryBanner");
		this.sortingDropdown = or.getLocator(className + ".sortingDropdown");
		this.departmentList = or.getLocator(className + ".departmentList");
		this.sortedItemsList = or.getLocator(className + ".sortedItemsList");
		this.priceElements = or.getLocator(className + ".categoryOption");
		this.productPrice = or.getLocator(className + ".productPrice");
		this.productTitle = or.getLocator(className + ".productTitle");
		this.productPageLink = or.getLocator(className + ".productPageLink");
		
		//Initiate class variables
		PageFactory.initElements(driver, this);
	}
	
	//Sort product list in given order using visible text
	public void sortItems(String sortingText) {
		new Select(driver.findElement(sortingDropdown)).selectByVisibleText(sortingText);
		Reporter.log("Product list was sorted in order- " + sortingText);
	}
	
	//Select category from department list
	public void selectCategory(String categoryName) {
		
		//Get list of all categories under department list
		 List<WebElement> li = driver.findElement(departmentList).findElements(categoryOption);
		 
		 //Select given category from department list
		 for(WebElement ele:li) {
			 if (ele.getText().equalsIgnoreCase(categoryName)) {
				 ele.click();
				 break;
			 }
		 }
		 Reporter.log("Category selected from departmentlist: " + categoryName);
	}
	
	//Select given products from selected category
	public HashMap<String,String> selectProducts(HashMap<String, String> products, int productNumber) {
		
		int productIndex = 0;
		
		for (WebElement ele:driver.findElements(sortedItemsList)) {
			
			//Check if product is available to buy
			if (ele.findElements(productPrice).size() > 0) {
				productIndex++;
				
				//Add selected product details in Map
				products.put(ele.findElements(productPrice).get(0)
						.getAttribute("innerText"), ele.findElement(productTitle).getText());
				if (productIndex == productNumber) {
					
					//Select given product
					ele.findElement(productPageLink).click();
					break;
				}
			}
		}
		Reporter.log("Product index selected from category: " + productNumber);
		return products;
	}

	//Verify if page is loaded
	public boolean isPageDisplayed() {
		boolean flag = false;
		
		//Wait for Page elements to be loaded
		WebElement ele = driver.findElement(departmentList);
		new WebDriverWait(driver, 10)
			.until(ExpectedConditions.visibilityOfAllElements(ele));
		
		//Check if element in page is loaded and is displayed
		flag = ele.isDisplayed();
		
		//Report Result
		if(flag) {
			Reporter.log("Department Page was loaded successfully.");
		} else {
			Reporter.log("Department Page was not loaded successfully.");
		}
		
		return flag;
	}

	//Verify if sorting is applied correctly
	public boolean isProductSortingCorrect(String sortingOrder) {
		
		boolean flag = false;
		
		List<String> unSortedpriceList = new ArrayList<String>();
		List<String> sortedpriceList = new ArrayList<String>();
		
		//fetch price for each product
		for(WebElement ele:driver.findElements(priceElements)) {
			
			//store price in 2 lists
			unSortedpriceList.add(ele.getAttribute("innertext"));
			sortedpriceList.add(ele.getAttribute("innertext"));
		}
		
		//sort 2nd list in given order
		try {
			if(sortingOrder == "Price: High to Low") {
				Collections.sort(sortedpriceList, Comparator.nullsLast(Comparator.reverseOrder()));
			} 
			else if (sortingOrder == "Price: Low to High") {
				Collections.sort(sortedpriceList, Comparator.nullsLast(Comparator.naturalOrder()));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
			
		flag = unSortedpriceList.equals(sortedpriceList);
		
		//Report Result
		if(flag) {
			Reporter.log("Products were correctly sorted in given order.");
		} else {
			Reporter.log("Products were not correctly sorted in given order.");
		}
		
		return flag;
	}
	
	//Verify if category is loaded
	public boolean isCategoryLoaded(String categoryName) {
		boolean flag = false;
		
		//Wait for Page elements to be loaded
		WebElement ele = driver.findElement(categoryBanner);
		new WebDriverWait(driver, 10)
			.until(ExpectedConditions.visibilityOfAllElements(ele));
		
		//Check if element in page is loaded and is displayed
		flag = ele.isDisplayed() && ele.getText().equals(categoryName);
		
		//Report Result
		if(flag) {
			Reporter.log("Selected category was loaded successfully.");
		} else {
			Reporter.log("Selected category was not loaded successfully.");
		}
		
		return flag;
	}

}
