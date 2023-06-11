package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class LoginPageNegativeTest extends BaseTest {

	@DataProvider
	public Object[][] incorrectLoginTestData() {
		return new Object[][] { { "cust", "cust" }, { "auto123@gmaill.com", "123456" },
				{ "test@@gmaill.com", "123456" }, { "auto", "test" }, { "#@$@#$@#$@", "@#!@#!@#!" } };

	}

	@DataProvider(name = "negativeLoginData")
	public Object[][] getnegativeLoginData() {
		Object dataNegative[][] = ExcelUtil.getTestData(AppConstants.LOGIN_SHEET_NAME);
		return dataNegative;
	}

//	@Test(dataProvider = "incorrectLoginTestData")
	@Test(dataProvider = "negativeLoginData")
	public void loginWithWrongCredentialsTest(String userName, String password) {
		Assert.assertTrue(loginPage.doLoginWithWrongCredentials(userName, password));
	}

}
