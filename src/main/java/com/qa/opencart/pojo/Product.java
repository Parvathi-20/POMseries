package com.qa.opencart.pojo;

public class Product {

	private String serachKey;
	private String productName;
	private int productImage;

	public Product(String serachKey, String productName, int productImage) {
		this.serachKey = serachKey;
		this.productImage = productImage;
		this.productName = productName;
	}
	
	public String getSerachKey() {
		return serachKey;
	}

	public void setSerachKey(String serachKey) {
		this.serachKey = serachKey;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductImage() {
		return productImage;
	}

	public void setProductImage(int productImage) {
		this.productImage = productImage;
	}



	public String toString() {
		return "Product [serachKey=" + serachKey + ",productName=" + productName + ", productImages= " + productImage
				+ "]";
	}

}
