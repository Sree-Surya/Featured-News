package com.becognizant.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.utils.DateUtils;
import com.utils.ExtentReportManager;

public class BaseRequirements {
	protected static WebDriver driver;
	protected static Properties browser, config;
	WebDriverWait wait;
	public static ExtentReports report = ExtentReportManager.getReportInstance();;
	protected static ExtentTest logger;
	protected static int imageNumber=0;
	protected static XSSFWorkbook workbook;
	protected static XSSFSheet sheet;
	protected static XSSFRow row;
	protected static XSSFCell cell;
	protected static FileOutputStream excel;
	
	
	/**
	 * ---------------------------------- Driver  Utilities ---------------------------------------
	 */
	/**
	 * This method will take the user input to select the required browser and instantiate the WebDriver object.
	 * @throws IOException
	 */
	public void selectBrowser() throws IOException {

		String option;

		browser = new Properties();
		OutputStream selection = new FileOutputStream(
				System.getProperty("user.dir") + "\\src\\resources\\browser.properties");

		Scanner getOption = new Scanner(System.in);

		System.out.println("Choose your Browser: ");
		System.out.println("1. Google Chrome");
		System.out.println("2. Microsoft Edge");
		System.out.println("Enter 1 or 2: ");
		
		
		// Getting User Input
		option = getOption.next();

		// Write the Output into browser.properties.
		browser.setProperty("browser", option);
		browser.store(selection, null);

		driver = getWebDriver();
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		getOption.close();

	}
	
	/**
	 * This method will return the driver required
	 * @return WebDriver
	 * @throws IOException
	 */
	public WebDriver getWebDriver() throws IOException {
		// Read the browser.properties file and return the driver as per user input
		try {
			if (browser.getProperty("browser").equalsIgnoreCase("1")) {
				driver = new ChromeDriver();
			} else {
				driver = new EdgeDriver();
			}
		}catch(Exception e) {
			reportFail(e.getMessage());
		}
		
		return driver;
	}


	/**
	 * Close the WebDriver object
	 */
	public void closeDriver() {
		driver.close();
	}
	
	
	/**
	 * ------------------------------------ Data  Utility ---------------------------------------
	 */
	
	/**
	 * This method will load all the required properties files.
	 * @throws IOException
	 */
	public void loadData() throws IOException {
		imageNumber = 1;
		InputStream browserInput, configInput;
		
		// Loading the browser.properties file
		browserInput = new FileInputStream(System.getProperty("user.dir") + "\\src\\resources\\browser.properties");
		browser = new Properties();
		browser.load(browserInput);

		// Loading the config.properties file
		configInput = new FileInputStream(System.getProperty("user.dir") + "\\src\\resources\\config.properties");
		config = new Properties();
		config.load(configInput);
		

	}
	
	/**
	 * ---------------------------------- Screenshot  Utility ---------------------------------------
	 */

	/**
	 * This method takes String as input and creates a screenshot with the same.
	 * It will append the screenshot to Extent Report.
	 * @param imageName
	 * @throws IOException
	 */
	public void takeScreenshot(String imageName) throws IOException {

		TakesScreenshot screenshot = ((TakesScreenshot) driver);

		File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
		
		File destinationFile = new File( System.getProperty("user.dir") + "\\screenshot\\"+(imageNumber)+" "+ imageName + ".png");
		
		FileUtils.copyFile(screenshotFile, destinationFile);
		
		logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "\\screenshot\\"+(imageNumber)+" "+ imageName + ".png");
		
		imageNumber++;
	}
	
	/**
	 * ---------------------------------- Selenium  Utilities ---------------------------------------
	 */
	
	/**
	 * Accepts String URL and opens the URL specified.
	 * @param URL
	 */
	public void openWebsite(String URL) {
		driver.get(URL);
	}
	
	
	/**
	 * This method will return WebElement using Locator specified.
	 * @param locator
	 * @return WebElement
	 * @throws IOException
	 */
	public WebElement getWebElement(String locator) throws IOException {
		WebElement element = null;
		try {
			if (locator.endsWith("_Id")) {
				element = driver.findElement(By.id(config.getProperty(locator)));
			} else if (locator.endsWith("_Xpath")) {
				element = driver.findElement(By.xpath(config.getProperty(locator)));
			} else if (locator.endsWith("_Name")) {
				element = driver.findElement(By.name(config.getProperty(locator)));
			} else if (locator.endsWith("_TagName")) {
				element = driver.findElement(By.tagName(config.getProperty(locator)));
			} else if (locator.endsWith("_CSS")) {
				element = driver.findElement(By.cssSelector(config.getProperty(locator)));
			} else if (locator.endsWith("_PartialLinkText")) {
				element = driver.findElement(By.partialLinkText(config.getProperty(locator)));
			} else if(locator.endsWith("_LinkText")){
				element = driver.findElement(By.linkText(config.getProperty(locator)));
			}
			else {
				Assert.fail("Failing Test Case due to invalid locator: "+locator);
				reportFail("Failing Test Case due to invalid locator: "+locator);
			}
			
		} catch (Exception e) {

			reportFail(e.getMessage());
			e.printStackTrace();

			Assert.fail("The test case failed : " + e.getMessage());
		}
		return element;
	}

	/**
	 * This method will return List of Elements using Locator specified.
	 * @param locator
	 * @return List
	 * @throws IOException
	 */
	public List<WebElement> getWebElements(String locator) throws IOException{
		List<WebElement> elements = null;
		try {
			if (locator.endsWith("_Id")) {
				elements = driver.findElements(By.id(config.getProperty(locator)));
			} else if (locator.endsWith("_Xpath")) {
				elements = driver.findElements(By.xpath(config.getProperty(locator)));
			} else if (locator.endsWith("_Name")) {
				elements = driver.findElements(By.name(config.getProperty(locator)));
			} else if (locator.endsWith("_TagName")) {
				elements = driver.findElements(By.tagName(config.getProperty(locator)));
			} else if (locator.endsWith("_CSS")) {
				elements = driver.findElements(By.cssSelector(config.getProperty(locator)));
			} else if (locator.endsWith("_PartialLinkText")) {
				elements = driver.findElements(By.partialLinkText(config.getProperty(locator)));
			} else if(locator.endsWith("_LinkText")){
				elements = driver.findElements(By.linkText(config.getProperty(locator)));
			}
			else {
				Assert.fail("Failing test case due to invalid locator: "+locator);
				reportFail("Failing Test Case due to invalid locator: "+locator);
			}
		}
		catch(Exception e) {
			
			reportFail(e.getMessage());
			e.printStackTrace();
			Assert.fail("The test case failed: "+e.getMessage());
		}
		
		return elements;
	}
	
	/**
	 * This method fills the String data into the WebElement
	 * @param WebElement, String
	 * @param data
	 */
	public void sendData(WebElement element, String data) {
		element.sendKeys(data);
	}
	
	/**
	 * This methods takes locator as input and returns the Text inside the WebElement
	 * @param String
	 * @return String
	 * @throws IOException
	 */
	public String getTextFromElement(String locator) throws IOException {
		WebElement element = getWebElement(locator);
		return element.getText();
	}
	
	/**
	 * Takes WebElement as input and returns text inside it.
	 * @param WebElement
	 * @return
	 */
	public String getTextFromElement(WebElement element) {
		return element.getText();
	}
	
	/**
	 * This method locates 
	 * @param String
	 * @throws IOException
	 */
	public void clickElement(String locator) throws IOException {
		WebElement element = getWebElement(locator);
		element.click();
	}

	public void pressEnter(String locator) throws IOException {
		getWebElement(locator).sendKeys(Keys.ENTER);
	}

	public void pressEnter(WebElement element) {
		element.sendKeys(Keys.ENTER);
	}
	
	public void waitTillVisibilityOfElement(String locator) throws IOException {
		
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			if (locator.endsWith("_Id")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(config.getProperty(locator))));
			} else if (locator.endsWith("_Xpath")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(config.getProperty(locator))));
			} else if (locator.endsWith("_Name")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(config.getProperty(locator))));
			} else if (locator.endsWith("_TagName")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(config.getProperty(locator))));
			} else if (locator.endsWith("_CSS")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(config.getProperty(locator))));
			} else if (locator.endsWith("_PartialLinkText")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(config.getProperty(locator))));
			} else if(locator.endsWith("_LinkText")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(config.getProperty(locator))));
			}
			else {
				Assert.fail("Element not visibile: "+locator);
			}
		}catch(Exception e) {
			reportFail(e.getMessage());
			e.printStackTrace();
			Assert.fail("The test case failed: "+e.getMessage());
		}
		

	}
	/**
	 * ---------------------------------- Reporting  Utilities ---------------------------------------
	 */
	public void reportFail(String message) throws IOException {
		logger.log(Status.FAIL, message);
		takeScreenshot("Failed case: "+DateUtils.getTimeStamp());
	}

	public void reportPass(String message) {
		logger.log(Status.PASS, message);
	}
	
	public void reportInfo(String message) {
		logger.log(Status.INFO, message);
	}
	
	public void createTest(String testName) {
		logger = report.createTest(testName);

	}
	
	/**
	 * ---------------------------------- Excel  Utilities ---------------------------------------
	 */
	
	public void createExcel(String fileName) throws IOException {
		excel = new FileOutputStream( System.getProperty("user.dir") +"\\output\\"+fileName+".xlsx");
		workbook = new XSSFWorkbook();
	}
	
	public void createSheet(String sheetName) {
		sheet = workbook.createSheet(sheetName);
	}
	
	
	public void writeExcel() throws IOException {
		workbook.write(excel);
	}
	
	public void autoSizeColumn(int number) {
		row = sheet.getRow(number);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			sheet.autoSizeColumn(i);
		}
	}

}
