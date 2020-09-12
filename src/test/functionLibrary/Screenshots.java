package test.functionLibrary;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

//Screenshots Class
public class Screenshots {
	
	//Capture screenshot
	public static void captureScreenshot(WebDriver driver, String screenshotDir, String screenshotName) {

		File directory = new File(screenshotDir);
		
		//Create screenshot directory if not exists
		if (!directory.exists()) directory.mkdir();
		screenshotName = screenshotDir + "\\" + screenshotName + 
				new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()) + ".png";
		try {
			//Capture screenshot and save as file
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(screenshotName));
			
		} catch (Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
}

