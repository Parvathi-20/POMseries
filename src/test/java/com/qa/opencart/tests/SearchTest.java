package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class SearchTest extends BaseTest {

	@BeforeClass
	public void searchSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));

	}

	@DataProvider
	public Object[][] getProductsearchKeyData() {
		return new Object[][] {
			{"Macbook"},
			{"samsung"},
			{"imac"}
		};
	}
	
	
	@Test(dataProvider="getProductsearchKeyData")
	public void searchProductResultCountTest(String serachKey) {
		resultsPage = accPage.doSearch(serachKey);
		Assert.assertTrue(resultsPage.getProductResultsCount() > 0);
	}

	@Test(dataProvider="getProductsearchKeyData")
	public void searchPageTitleTest(String serachKey) {
		resultsPage = accPage.doSearch(serachKey);
		String actSearchTitle = resultsPage.getResultsPageTitle(serachKey);
		System.out.println("Search Page Title : " + actSearchTitle);
		Assert.assertEquals(actSearchTitle, "Search - " + serachKey);
	}

	
	@DataProvider
	public Object[][] getProductTestData() {
		return new Object[][] {
			{"Macbook","MacBook Pro"},
			{"samsung","Samsung Galaxy Tab 10.1"},
			{"imac","iMac"}
		};
	}
	
	
	@Test(dataProvider = "getProductTestData")
	public void selectProductTest(String searckey, String productName) {
		resultsPage = accPage.doSearch(searckey);
		productInfoPage = resultsPage.selectProduct(productName);
		String actProductHeaderName = productInfoPage.getProductHeaderName();
		System.out.println("actual product name : " + actProductHeaderName);
		Assert.assertEquals(actProductHeaderName, productName);
	}

	
	@DataProvider
	public Object[][] getProductImagesTestData() {
		return new Object[][] {
			{"Macbook","MacBook Pro",4},
			{"samsung","Samsung Galaxy Tab 10.1",7},
			{"imac","iMac",3}
		};
	}
	
	
	@Test(dataProvider="getProductImagesTestData")
	public void productImagesTest(String searckey, String productName, int expectedimagesCount) {
		resultsPage = accPage.doSearch(searckey);
		productInfoPage = resultsPage.selectProduct(productName);
		int actProductImagesCount = productInfoPage.getProductImagesCount();
		System.out.println("actual product images count : " + actProductImagesCount);
		Assert.assertEquals(actProductImagesCount, expectedimagesCount);

	}
	
	
	

}
