package test.testcases;

import org.testng.annotations.Test;
import test.amazon.CartPage;
import test.amazon.DepartmentPage;
import test.amazon.HomePage;
import test.amazon.LoginPage;
import test.amazon.ProductPage;
import test.functionLibrary.Browser;
import test.functionLibrary.CommonFunctions;
import test.functionLibrary.ExternalData;
import test.functionLibrary.Screenshots;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterTest;

//Test Class: ExpensiveItemInCart
public class ExpensiveItemInCart {
	
	private static WebDriver driver;
	private static int productNumber = 1;
	private static HashMap<String, String> products;
	private static ExternalData or;
	private static ExternalData config;
	private static ExternalData data;
	private String startTimeStamp;
	
  @Test(priority=0)
  public void launch_homepage() {
	  //Verify if home page is loaded
	  HomePage home = new HomePage(driver, or);
	  Assert.assertTrue(home.isPageDisplayed(), "Home Page had not loaded correctly");
  }
  
  @Test(priority = 1, enabled = false) //Test disabled as Amazon security OTP is required
  public void login_to_application() {
	  HomePage home = new HomePage(driver, or);
	  
	  //Login to application if not already logged in
	  if (!home.isUserLoggedIn()) {
		  //Navigate to login page
		  home.signIn();
		  
		  ExternalData credentials = new ExternalData(userCredentials);
		  
		  LoginPage login = new LoginPage(driver, or);
		  //Verify if login page is loaded
		  Assert.assertTrue(login.isPageDisplayed(), "Login Page had not loaded correctly");
		  //Login to application
		  login.login(credentials);
	  }
	  //Verify if user login is successful
	  Assert.assertTrue(home.isUserLoggedIn(), "User Login was not successful");
  }

  @Test(priority = 2)
  public void navigate_to_departmentpage() {
	  //Navigate to department page
	  HomePage home = new HomePage(driver, or);
	  home.navigateToDepartmentPage();
	  
	  //Verify if department page is loaded
	  DepartmentPage department = new DepartmentPage(driver, or);
	  Assert.assertTrue(department.isPageDisplayed(), "Navigation to Department Page had failed");
  }

  @Test(priority = 3)
  public void select_category_and_sort_items() {
	  String categoryName = data.getValue("CategoryName");
	  String sortingOerder = data.getValue("SortingOrder");
	  //Select category and sort products list
	  DepartmentPage department = new DepartmentPage(driver, or);
	  //Select given category
	  department.selectCategory(categoryName);
	  //Verify if category is loaded
	  department.isCategoryLoaded(categoryName);
	  //Sort products in given order
	  department.sortItems(sortingOerder);
	  //Verify is products are sorted in given order
	  Assert.assertTrue(department.isProductSortingCorrect(sortingOerder), "Sorting Order was incorrect");
  }
  
  @Test(priority = 4)
  public void select_product_and_navigate_to_productpage() {
	  //Select product by its position and navigate to product page
	  ProductPage product =  CommonFunctions.fn_selecting_products_and_opening_productpage(
			  driver, or, products, productNumber);
	  productNumber++;
	  //Verify if product page is loaded
	  Assert.assertTrue(product.isPageDisplayed(),"Navigation to Product Page had failed");
	  //Verify if correct product page is loaded
	  Assert.assertTrue(product.isCorrectProductsPage(products),"Correct Product was not displayed");
  }
  
  @Test(priority = 5)
  public void add_products_to_cart() {
	  int productsCount = Integer.parseInt(data.getValue("ProductsCount"));
	  //Add product(s) to cart and select & add additional product 
	  ProductPage product = CommonFunctions.fn_adding_products_to_cart(
			  driver, or, products, productsCount, productNumber);
	  //Navigate to cart page
	  product.navigateToCart();
	  //Verify if Cart page is loaded
	  CartPage cart = new CartPage(driver, or);
	  Assert.assertTrue(cart.isPageDisplayed(), "Navigation to Cart Page had failed");
	  
  }
	
  @Test(priority = 6)
  public void verify_products_in_cart() {
	  //Verify correct product is added and subtotal of cart items
	  CartPage cart = new CartPage(driver, or);	
	  Assert.assertTrue(cart.isCorrectProductsListed(products), "Correct Product are not added in cart");
	  Assert.assertTrue(cart.isSubTotalCorrect(), "Subtotal of products price is not correct");
  }

  @AfterMethod
  public void captureScreenshot(ITestResult result) throws Exception {
	  String screenshotDir = config.getValue("ScreenshotDirectory") + "_" + startTimeStamp;
	  String screenshotName = result.getName() + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
	  
	  //Capture screenshot after each method
	  Screenshots.captureScreenshot(driver, screenshotDir, screenshotName);
  }

  @BeforeTest
  @Parameters("configFile")
  public void launch_browser(String configFile) {
	  startTimeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
	  
	  //Load Test Config from testng parameter
	  config = new ExternalData(configFile);
	  //Load ObjectRepository
	  or = new ExternalData(config.getValue("ObjectRepository"));
	  //Load Testdate file
	  data = new ExternalData(config.getValue("TestdataFile"));
	  
	  products = new HashMap<String, String>();
	  
	  //Launch browser and launch web application
	  driver = Browser.StartBrowser(config.getValue("BrowserName"), 
			  config.getValue("BrowserDriverPath"), config.getValue("BaseURL"));
	  driver.manage().timeouts().implicitlyWait
	  	(Long.parseLong(config.getValue("ImplicitWaitTimeout")), TimeUnit.SECONDS);
  }

  @AfterTest
  public void close_browser() {
	  //Close browser
	  driver.close();
  }

}
