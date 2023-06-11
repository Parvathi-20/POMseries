package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.navigateToregisterLink();

	}

	public String getRandomEmailId() {
		String emailID = "digital" + System.currentTimeMillis() + "@rediff.com";
//		String emailID = "digital" + UUID.randomUUID()+"@rediff.com";
		System.out.println(emailID);
		return emailID;
	}

	@DataProvider(name = "regExcelData")
	public Object[][] getRegExcelTestData() {
		Object regdata[][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regdata;
	}

	@Test(dataProvider = "regExcelData")
	public void userRegisterTest(String fname, String lname, String telephone, String password, String subscribe) {
		String actRegSuccMessg = registerPage.registerUser("fname", "lname", getRandomEmailId(), "9090909090",
				"success", "yes");
		Assert.assertEquals(actRegSuccMessg, AppConstants.USER_RESG_SUCCESS_MESSG);
	}

}
