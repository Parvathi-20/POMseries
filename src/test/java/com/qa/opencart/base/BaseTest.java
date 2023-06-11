package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.ResultsPage;
import com.qa.opencart.utils.ElementUtil;

public class BaseTest {
	
	
	WebDriver driver;
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected ResultsPage resultsPage;
	protected ProductInfoPage productInfoPage;
	protected ElementUtil eleUtil;
	protected DriverFactory df;
	protected Properties prop;
	protected SoftAssert softAssert;
	protected RegisterPage  registerPage;
	
	@Parameters({"browser"})
	@BeforeTest
	public void setup(String browserName) {
		loginPage = new LoginPage(driver);
		df = new DriverFactory();
		prop = df.initProp();
		
		if(browserName != null) {
			prop.setProperty("browser", browserName);
		}
		
		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
		softAssert = new SoftAssert();
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
//	<argLine>-Xmx1024m -XX:MaxPermSize=256m</argLine>


}
