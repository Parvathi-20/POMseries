package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.frameworkexception.FrameException;

public class ElementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;
	private final int DEFAULT_TIME_OUT = 5;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(this.driver);
	}

	public static void main(String[] args) {

	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public void doClick(By locator, int timeout) {
		checkElementClickable(locator, 5).click();
	}

	public void doSendkeys(By locator, String value) {
		if (value == null) {
			System.out.println("values can not be null..");
			throw new FrameException("VALUECANTBENULL");

		}
		doClear(locator);
		getElement(locator).sendKeys(value);
	}	

	public void doSendkeys(By locator, String value, boolean clearfield) {
		if (clearfield == true) {
			doClear(locator);
		}
		getElement(locator).sendKeys(value);
	}
	

	/*
	 * The below method will wait implicitly for 2S hence discarded public
	 * WebElement getElement(By locator) { WebElement element = null; try { element
	 * = driver.findElement(locator); } catch (NoSuchElementException e) {
	 * System.out.println("Element is not found using this locator .. " + locator);
	 * 
	 * try { Thread.sleep(2000); } catch (InterruptedException ee) {
	 * ee.printStackTrace(); } element = driver.findElement(locator); } return
	 * element; }
	 */

	public WebElement getElement(By locator) {
		WebElement element = null;
		try {
			element = driver.findElement(locator);
		} catch (NoSuchElementException e) {
			System.out.println("Element is not found using this locator .. " + locator);

			try {
				System.out.println("Element is not found using this locator..." + locator);
				element = waitForElementVisible(locator, DEFAULT_TIME_OUT);
			} catch (NoSuchElementException ee) {
				ee.printStackTrace();
			}
			element = driver.findElement(locator);
		}
		
		if(Boolean.parseBoolean(DriverFactory.highlightElement)) {
			jsUtil.flash(element);
		}
		return element;
	}

	public WebElement getElement(By locator, int timeOut) {
		return waitForElementVisible(locator, timeOut);
	}

	public String doGetAttributeValue(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	public void doClear(By locator) {
		getElement(locator).clear();
	}

	public int getElementsCount(By locator) {
		return driver.findElements(locator).size();
	}

	public String doGetElementText(By locator) {
		return getElement(locator).getText();
	}

	public boolean checkElementIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public List<String> getElementAtributeValues(By locator, String attrName) {

		List<WebElement> elelist = getElements(locator);
		List<String> eleAttrList = new ArrayList<String>();

		for (WebElement e : elelist) {
			String attrVal = e.getAttribute(attrName);
			eleAttrList.add(attrVal);
		}
		return eleAttrList;

	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void doSelectDropDownbyIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectDropDownbyValueAttribute(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public void doSelectDropDownbyVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}

	public List<String> getElemetnTextList(By locator) {

		List<WebElement> elementlinks = getElements(locator);
		List<String> linksTextVal = new ArrayList<String>();

		for (WebElement e : elementlinks) {
			String linkstext = e.getText();
			linksTextVal.add(linkstext);
		}
		return linksTextVal;

	}

	// specific section link to click
	public void clickElementFromPageSection(By locator, String expectedtxt) {

		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {
			String text = e.getText();
			if (expectedtxt.equals(text)) {
				e.click();
				break;
			}
		}

	}

	public int getDropDownValueCount(By locator) {
		return getAllDropDownOptions(locator).size();
	}

	public List<String> getAllDropDownOptions(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		List<String> optionsValueList = new ArrayList<String>();

		System.out.println("Total values " + optionsList.size());

		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			optionsValueList.add(text);
		}
		return optionsValueList;
	}

	public boolean doSelectDropDownValue(By locator, String dropdwonvalue) {
		boolean flag = false;
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();

		System.out.println("Total values " + optionsList.size());

		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(dropdwonvalue)) {
				flag = true;
				e.click();
				break;
			}

		}
		if (flag == false) {
			System.out.println(dropdwonvalue + "value is not present in the " + locator);
		}
		return flag;

	}

	public boolean IsElementDisplayed(By locator) {
		List<WebElement> eleList = driver.findElements(locator);
		if (eleList.size() > 0) {
			System.out.println(locator + " element is present on page ");
			return true;
		} else {
			System.out.println(locator + " element not found");
			return false;
		}
	}
	// ****************Actions class Utils************************

	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}

	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
	}

	public void doDragAndDrop(By sourceLocator, By targetLocator) {
		Actions act = new Actions(driver);
		act.dragAndDrop(getElement(sourceLocator), getElement(targetLocator)).build().perform();
	}

	public void doContextClick(By locator) {
		Actions act = new Actions(driver);
		act.contextClick(getElement(locator)).build().perform();
	}

	public void doMoveToElement(By locator) {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(locator)).build().perform();
	}

	public void handleTwoLevelMenu(By parentMenu, By childMenu) throws InterruptedException {
		doMoveToElement(parentMenu);
		Thread.sleep(2000);
		doClick(childMenu);
	}

	public void handleTwoLevelMenu(By parentMenu, String childMenuLinkText) throws InterruptedException {
		doMoveToElement(parentMenu);
		Thread.sleep(2000);
		doClick(By.linkText(childMenuLinkText));
	}

	public void multiLevelMenuChildMenuHandle(By parentMenuLocator, String level2LinkText, String level3LinkText,
			String level4LinkText) throws InterruptedException {

		WebElement level1 = getElement(parentMenuLocator);
		Actions act = new Actions(driver);

		act.moveToElement(level1).build().perform();
		Thread.sleep(1000);

		WebElement level2 = getElement(By.linkText(level2LinkText));
		act.moveToElement(level2).build().perform();
		Thread.sleep(1000);

		WebElement level3 = getElement(By.linkText(level3LinkText));
		act.moveToElement(level3).build().perform();
		Thread.sleep(1000);

		doClick(By.linkText(level4LinkText));
	}

	// *************** waits utils *******

	public Alert waitForAlertJsPopUpWithFluentWait(int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.ignoring(NoAlertPresentException.class).pollingEvery(Duration.ofSeconds(pollingTime))
				.withMessage("------time out is done...Alert is not found.....");

		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public Alert waitForAlertJsPopUp(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String alertJSGetText(int timeOut) {
		return waitForAlertJsPopUp(timeOut).getText();
	}

	public void alertAccpet(int timeOut) {
		waitForAlertJsPopUp(timeOut).accept();
	}

	public void alertDismiss(int timeOut) {
		waitForAlertJsPopUp(timeOut).dismiss();
	}

	public void EnterAlertValue(int timeOut, String value) {
		waitForAlertJsPopUp(timeOut).sendKeys(value);
	}

	public String waitForTitleIsAndCapture(String titleFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
			String title = driver.getTitle();
			System.out.println(" title " + title);

			return title;
		} else {
			System.out.println("title is not present within the given timeout : " + timeOut);
			return null;
		}
	}

	public String waitForFullTitleAndCapture(String titleVal, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.titleIs(titleVal))) {
			String title = driver.getTitle();
			return title;
		} else {
			System.out.println("title is not present within the given timeout : " + timeOut);
			return null;
		}
	}

	public String waitForURLContainsAndCapture(String urlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
			String url = driver.getCurrentUrl();
			return url;
		} else {
			System.out.println("url is not present within the given timeout : " + timeOut);
			return null;
		}
	}

	public String waitForURLAndCapture(String urlValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.urlToBe(urlValue))) {
			String url = driver.getCurrentUrl();
			return url;
		} else {
			System.out.println("url is not present within the given timeout : " + timeOut);
			return null;
		}
	}

	
	
	public boolean waitForTotalWindows(int totalWindowsToBe, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindowsToBe));
	}

	/**
	 * * An expectation for checking that an element is present on the DOM of a
	 * page. This does not necessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}

	/**
	 * * An expectation for checking that an element is present on the DOM of a page
	 * and visible on the page. Visibility means that the element is not only
	 * displayed but also has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}

	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

	}

	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

	}

	public void clickElementWhenReady(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();

	}

	public WebElement checkElementClickable(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));

	}
	
	
	//frames with wait:
	
	
		public void waitForFrameAndSwitchToItWithFluentWait(int timeOut, int pollingTime, String idOrName) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(timeOut))
					.ignoring(NoSuchFrameException.class)
					.pollingEvery(Duration.ofSeconds(pollingTime))
					.withMessage("------time out is done...frame is not found.....");		
			 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
		}
		
		
		public void waitForFrameAndSwitchToItByIDOrName(int timeOut, String idOrName) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
		}

		public void waitForFrameAndSwitchToItByIndex(int timeOut, int frameIndex) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
		}

		public void waitForFrameAndSwitchToItByFrameElement(int timeOut, WebElement frameElement) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
		}

		public void waitForFrameAndSwitchToItByFrameLoctor(int timeOut, By frameLocator) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
		}
		
		public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(timeOut))
					.ignoring(NoSuchElementException.class)
					.ignoring(StaleElementReferenceException.class)
					.ignoring(ElementNotInteractableException.class)
					.pollingEvery(Duration.ofSeconds(pollingTime))
					.withMessage("------time out is done...element is not found.....");
			
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		}
		
		public WebElement waitForElementPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(timeOut))
					.ignoring(NoSuchElementException.class)
					.ignoring(StaleElementReferenceException.class)
					.ignoring(ElementNotInteractableException.class)
					.pollingEvery(Duration.ofSeconds(pollingTime))
					.withMessage("------time out is done...element is not found.....");
			
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

		}
		
		public WebElement retryingElement(By locator, int timeOut) {// 20

			WebElement element = null;
			int attempts = 0;

			while (attempts < timeOut) {
				try {
					element = getElement(locator);
					System.out.println("element is found...." + locator + " in attempt : " + attempts);
					break;
				} catch (NoSuchElementException e) {
					System.out.println("element is not found..." + locator + " in attempt : " + attempts);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				attempts++;
			}

			if (element == null) {
				System.out.println(
						"element is not found...tried for " + timeOut + " secs " + " with the interval of 500 millisecons");
			}
			return element;

		}
		
		public WebElement retryingElement(By locator, int timeOut, int pollingTime) {// 20

			WebElement element = null;
			int attempts = 0;

			while (attempts < timeOut) {
				try {
					element = getElement(locator);
					System.out.println("element is found...." + locator + " in attempt : " + attempts);
					break;
				} catch (NoSuchElementException e) {
					System.out.println("element is not found..." + locator + " in attempt : " + attempts);
					try {
						Thread.sleep(pollingTime);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				attempts++;
			}

			if (element == null) {
				System.out.println(
						"element is not found...tried for " + timeOut + " secs " + " with the interval of 500 millisecons");
			}
			return element;

		}

}
