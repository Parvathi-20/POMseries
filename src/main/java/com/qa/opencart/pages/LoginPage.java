package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

//	1. constructor of the class
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 2. private By locators
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdlink = By.linkText("Forgotten Password");
	private By footerLinks = By.xpath("//footer//a");
	private By errorMessage = By.cssSelector("div.alert.alert-danger.alert-dismissible");
	private By registerLink = By.linkText("Register");

	// 3. public page actions/methods:
	@Step("getting login page title")
	public String getLoginPageTitle() {
		return eleUtil.waitForTitleIsAndCapture("Account Login", 5);
	}

	@Step("getting login page url")
	public String getLoginPageURL() {
		return eleUtil.waitForURLContainsAndCapture("route=account/login", 5);
	}

	@Step("checking forgot pwd link exist on the login page")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.checkElementIsDisplayed(forgotPwdlink);
	}
	
	@Step("getting footer links")
	public List<String> getFooterLinksList() {
		List<WebElement> footerLinksList = eleUtil.waitForElementsVisible(footerLinks, 10);
		List<String> footerTextList = new ArrayList<String>();
		for (WebElement e : footerLinksList) {
			String text = e.getText();
			footerTextList.add(text);
		}
		return footerTextList;
	}
	
	@Step("login with username {0} and password {1} ")
	public AccountsPage doLogin(String userName, String pwd) {
		eleUtil.waitForElementVisible(emailId, 10).sendKeys(userName);
		eleUtil.doSendkeys(password, pwd, true);
		eleUtil.doClick(loginBtn);
		// return the next landing page -- AccountsPage -- page chaining model
		return new AccountsPage(driver);
	}

	@Step("login with wrong username {0} and password {1} ")
	public boolean doLoginWithWrongCredentials(String userName, String pwd) {
		eleUtil.waitForElementVisible(emailId, 10).sendKeys(userName);
		eleUtil.doSendkeys(password, pwd);
		eleUtil.doClick(loginBtn);
		eleUtil.waitForElementPresence(errorMessage, 5);
		String errorMsg = eleUtil.doGetElementText(errorMessage);
		if (errorMsg.contains(AppConstants.LOGIN_ERROR_MESSAGE)) {
			eleUtil.doClear(emailId);
			eleUtil.doClear(password);
			return true;
		} else {
			eleUtil.doClear(emailId);
			eleUtil.doClear(password);
			return false;

		}

	}

	public RegisterPage navigateToregisterLink() {
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}

}
