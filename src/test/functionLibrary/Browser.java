package test.functionLibrary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Reporter;

//Browser class
public class Browser {
	
	private static WebDriver driver;

    public static WebDriver StartBrowser(String browserName, String webDriver, String baseURL)
    {
    	//select and launch given browsename
    	switch (browserName.toLowerCase()){
    	case "firefox":
    		//Launch Firefox browser
    		System.setProperty("webdriver.firefox.marionette", webDriver);
            driver = new FirefoxDriver();
            break;
    	case "chrome":
    		//Launch Chrome browser
    		System.setProperty("webdriver.chrome.driver",webDriver);
            driver = new ChromeDriver();
            break;    
    	case "ie":
    		//Launch InternetExplorer browser
    	    System.setProperty("webdriver.ie.driver",webDriver);
            driver = new InternetExplorerDriver();
            break;
    	}
    	
    	Reporter.log(browserName.toUpperCase() + " browser was launched");

    	//Maximize browser and launch web application
    	driver.manage().window().maximize();
    	driver.get(baseURL);
    	
    	Reporter.log("Web applicaton launched with URL: " + baseURL);
    	
    	return driver;
    }
}