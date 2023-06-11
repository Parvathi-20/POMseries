package com.qa.opencart.pages;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}

	private By productHeader = By.cssSelector("div#content h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetadata = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPricedata = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	private By quantity = By.id("input-quantity");
	private By addtoCartbtn = By.id("button-cart");
	private By addtocartsuccess = By.cssSelector("div.alert-success");

	private Map<String, String> productInfoMap;

	public String getProductHeaderName() {
		return eleUtil.doGetElementText(productHeader);
	}

	public int getProductImagesCount() {
		return eleUtil.waitForElementsVisible(productImages, 10).size();
	}

	public Map<String, String> getProductInfo() {
//		productInfoMap = new HashMap<String, String>(); - Random order
//		productInfoMap = new LinkedHashMap<String, String>(); - with order
		productInfoMap = new TreeMap<String, String>();// sorted order A,a,numerics
		getProductMetaData();
		getProductPriceData();
		productInfoMap.put("productName", getProductHeaderName());
		return productInfoMap;
	}

	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.getElements(productMetadata);
		for (WebElement e : metaList) {
			String text = e.getText();
			String metaInfo[] = text.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
		}

	}

	private void getProductPriceData() {
		List<WebElement> priceList = eleUtil.getElements(productPricedata);
		String price = priceList.get(0).getText();
		String exTaxprice = priceList.get(1).getText();
		String exTaxpricevalue = exTaxprice.split(":")[1].trim();

		productInfoMap.put("productprice", price);
		productInfoMap.put(exTaxprice, exTaxpricevalue);

	}

	public String enterQuantity(String quantityofItems) {
		eleUtil.doSendkeys(quantity, quantityofItems, true);
		String quantityAdded = eleUtil.doGetAttributeValue(quantity, "value");
		return quantityAdded;
	}

	public String clickOnAddtoCart() {
		eleUtil.doClick(addtoCartbtn);
		eleUtil.waitForElementPresence(addtocartsuccess, 3);
		String successMessage = eleUtil.doGetElementText(addtocartsuccess);
		return successMessage;
	}

}
