package test.functionLibrary;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import test.amazon.DepartmentPage;
import test.amazon.ProductPage;

//CommonFunctions Class
public class CommonFunctions {

	  public static ProductPage fn_selecting_products_and_opening_productpage(
			  WebDriver driver, ExternalData or, HashMap<String, String> products, int productNumber) {
		  
		  //Select product from category page by position and add product details in map
		  DepartmentPage department = new DepartmentPage(driver, or);
		  products = department.selectProducts(products, productNumber);
		  
		  return new ProductPage(driver, or);
	  }
	  
	  public static ProductPage fn_adding_products_to_cart(
			  WebDriver driver, ExternalData or, HashMap<String, String> products, 
			  	int productsCount, int productNumber) {
		  
		  //Add product to cart
		  ProductPage product = new ProductPage(driver, or);
		  product.addToCart();
		  
		  //Add products to cart if given number of products are not added to cart
		  while (productsCount >= productNumber) {
			  //Navigate back to department page
			  product.returnToDepartmentPage();
			  //Select product by its position and navigate to product page
			  fn_selecting_products_and_opening_productpage(driver, or, products, productNumber);
			  productNumber++;
			  //Add product(s) to cart and select & add additional product 
			  fn_adding_products_to_cart(driver, or, products, productsCount, productNumber);
		  }
		  
		  return new ProductPage(driver, or);
	  }

}
