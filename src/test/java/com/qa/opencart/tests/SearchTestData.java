package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.dataprovides.ProductDataprovider;
import com.qa.opencart.pojo.Product;

public class SearchTestData extends BaseTest {

	@BeforeClass
	public void searchSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));

	}
//** passing data in single data provider within same class
//	@DataProvider(name = "productData")
//	public Object[][] getProductTestData() {
//		return new Object[][] { 
//		{new Product ( "Macbook", "MacBook Pro", 4)}, 
//		{new Product ( "samsung", "Samsung Galaxy Tab 10.1", 7 )},
//		{new Product ( "imac", "iMac", 3 )} 
//			
//		};
//	}

	
	//** passing data in single data provider from different pojo class
	@Test(dataProvider = "productData", dataProviderClass = ProductDataprovider.class)
	public void searchProductResultCountTest(Product Product) {
		resultsPage = accPage.doSearch(Product.getSerachKey());
		Assert.assertTrue(resultsPage.getProductResultsCount() > 0);
	}

	@Test(dataProvider = "productData")
	public void searchPageTitleTest(Product Product) {
		resultsPage = accPage.doSearch(Product.getSerachKey());
		String actSearchTitle = resultsPage.getResultsPageTitle(Product.getSerachKey());
		System.out.println("Search Page Title : " + actSearchTitle);
		Assert.assertEquals(actSearchTitle, "Search - " + Product.getSerachKey());
	}

	@Test(dataProvider = "productData")
	public void selectProductTest(Product Product) {
		resultsPage = accPage.doSearch(Product.getSerachKey());
		productInfoPage = resultsPage.selectProduct(Product.getProductName());
		String actProductHeaderName = productInfoPage.getProductHeaderName();
		System.out.println("actual product name : " + actProductHeaderName);
		Assert.assertEquals(actProductHeaderName, Product.getProductName());
	}

	@Test(dataProvider = "productData")
	public void productImagesTest(Product Product) {
		resultsPage = accPage.doSearch(Product.getSerachKey());
		productInfoPage = resultsPage.selectProduct(Product.getProductName());
		int actProductImagesCount = productInfoPage.getProductImagesCount();
		System.out.println("actual product images count : " + actProductImagesCount);
		Assert.assertEquals(actProductImagesCount, Product.getProductImage());

	}

}
