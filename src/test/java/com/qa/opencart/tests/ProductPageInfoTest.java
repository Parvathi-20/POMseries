package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qa.opencart.base.BaseTest;

public class ProductPageInfoTest extends BaseTest {

	@BeforeClass
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));

	}

	@Test
	public void productInfoTest() {
		resultsPage = accPage.doSearch("samsung");
		productInfoPage = resultsPage.selectProduct("Samsung Galaxy Tab 10.1");
		Map<String, String> productInfoMap = productInfoPage.getProductInfo();
		System.out.println(productInfoMap);
//		{Availability=In Stock, Brand=Apple, Ex Tax: $2,000.00=$2,000.00, Product Code=Product 18, 
//		Reward Points=800, productName=MacBook Pro, productprice=$2,000.00}
//		Assert.assertEquals(productInfoMap.get("Brand"), "Apple");
//		Assert.assertEquals(productInfoMap.get("Availability"), "In Stock");
//		Assert.assertEquals(productInfoMap.get("productName"), "MacBook Pro");
//		Assert.assertEquals(productInfoMap.get("productprice"), "$2,000.00");
//		Assert.assertEquals(productInfoMap.get("Product Code"), "Product 18");

//		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
//		softAssert.assertEquals(productInfoMap.get("Availability"), "In Stock");
//		softAssert.assertEquals(productInfoMap.get("productName"), "MacBook Pro");
//		softAssert.assertEquals(productInfoMap.get("productprice"), "$2,000.00");
//		softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
//		softAssert.assertAll();

		softAssert.assertEquals(productInfoPage.enterQuantity("3"), "3");
		String successMsg = productInfoPage.clickOnAddtoCart();
		String expmsg = "Success: You have added " + productInfoMap.get("productName") + " to your shopping cart!×";
		softAssert.assertEquals(successMsg, expmsg);
		softAssert.assertAll();
	}

}
