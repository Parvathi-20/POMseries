package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.frameworkexception.FrameException;

public class DriverFactory {

	WebDriver driver;
	OptionsManager optionsmanager;
	public static String highlightElement;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser").trim();

		System.out.println("browser name is : " + browserName);
		highlightElement = prop.getProperty("highlight");
		optionsmanager = new OptionsManager(prop);

		switch (browserName.toLowerCase()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(optionsmanager.getChromeOptions()));
//			driver = new ChromeDriver(optionsmanager.getChromeOptions());
			break;
		case "edge":
			tlDriver.set(new EdgeDriver(optionsmanager.getEdgeOptions()));
//			driver = new EdgeDriver(optionsmanager.getEdgeOptions());
			break;
		case "safari":
			driver = new SafariDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver(optionsmanager.getFirefoxOptions());
			break;

		default:
			System.out.println("plz pass the right browser ...." + browserName);
//			throw new FrameException("NOBROWSERFOUNDEXCEPTION");
			break;
		}
//
//		driver.manage().deleteAllCookies();
//		driver.manage().window().maximize();
//		driver.get(prop.getProperty("url"));

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

//		return driver;
		return getDriver();

	}

	public synchronized static WebDriver getDriver() {
		return tlDriver.get();
	}

	public Properties initProp() {
		Properties prop = new Properties();
		FileInputStream ip = null;
		// mvn clean install -Denv="qa"
		// mvn clean install

		String envname = System.getProperty("env");

		try {
			if (envname == null) {
				System.out.println("no env selecting, hence running on qa..");
				ip = new FileInputStream("./src/main/resources/config/qa.config.properties");
			} else {
				switch (envname.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/main/resources/config/qa.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/main/resources/config/dev.config.properties");
					break;

				case "stage":
					ip = new FileInputStream("./src/main/resources/config/stage.config.properties");
					break;

				case "uat":
					ip = new FileInputStream("./src/main/resources/config/uat.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/main/resources/config/config.properties");
					break;
				default:
					System.out.println("pls pass the right env...");
					throw new FrameException("NOVALIDENVGIVEN");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;

	}
	
	
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		
		File destination = new File(path);
		
		try {
			FileUtils.copyFile(srcFile, destination);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return path;
	}

}
