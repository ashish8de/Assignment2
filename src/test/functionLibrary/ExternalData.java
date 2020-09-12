package test.functionLibrary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.By;

//ExternalData Class
public class ExternalData {
	
	private Properties properties;
	
	//Initialize class variables in constructor
	public ExternalData(String filePath) {
		
		//Open text files
		try {
			FileInputStream inputstream = new FileInputStream(filePath);
			
			//Initialize properties
			properties = new Properties();
			properties.load(inputstream);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Get property value
	public String getValue(String propertyName) {
		String propertyValue = null;
		
		try {
			propertyValue = properties.getProperty(propertyName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return propertyValue.trim();
	}
	
	//Get locator from ObjectRepository file
	//! TO BE USED ONLY FOR ObjectRepository
	public By getLocator(String ElementName) {
		
		By byLocator = null;
		
		try {		
			//Get locator name & value from ObjectRepository
			String[] objectRepository = properties.getProperty(ElementName).split("[|]");
			String locatorName = objectRepository[0].trim();
			String locatorValue = objectRepository[1].trim();
			
			//Select locator based on locator name
			switch (locatorName.toLowerCase()) {
			case "id": 
				//Locator By id
				byLocator = By.id(locatorValue);
				break;
			case "name":
				//Locator By name
				byLocator = By.name(locatorValue);
				break;
			case "tagname":
				//Locator By tagname
				byLocator = By.tagName(locatorValue);
				break;
			case "classname":
				//Locator By classname
				byLocator = By.className(locatorValue);
				break;
			case "cssselector":
			case "css":
				//Locator By cssselector
				byLocator = By.cssSelector(locatorValue);
				break;
			case "xpath":
				//Locator By xpath
				byLocator = By.xpath(locatorValue);
				break;
			case "linktext":
				//Locator By linktext
				byLocator = By.linkText(locatorValue);
				break;
			case "patiallinktext":
				//Locator By partiallinktext
				byLocator = By.partialLinkText(locatorValue);
				break;
			} 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return byLocator;
	}		
}