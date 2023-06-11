package com.qa.opencart.dataprovides;

import org.testng.annotations.DataProvider;

import com.qa.opencart.pojo.Product;

public class ProductDataprovider {

	@DataProvider(name = "productData")
	public Object[][] getProductTestData() {
		return new Object[][] { 
			{ new Product("Macbook", "MacBook Pro", 4) },
			{ new Product("samsung", "Samsung Galaxy Tab 10.1", 7) },
			{ new Product("imac", "iMac", 3) }

		};
	}

}
