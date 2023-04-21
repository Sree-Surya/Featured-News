package com.cognizant.login;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebElement;

import com.becognizant.base.BaseRequirements;

public class LoginToCognizant extends BaseRequirements {

	/**
	 * This method will login the user into be.cognizant
	 * @throws IOException
	 */
	public void login() throws IOException{
		
		reportInfo("Starting the Log-in Process.");
		
		// Open the website
		openWebsite( config.getProperty("url"));
		
		// Wait till input field is loaded
		waitTillVisibilityOfElement("msLogin_Xpath");
		
		// Locate the email ID input field
		WebElement msLoginInput = getWebElement("msLogin_Xpath");
		
		// Send user email into input field
		sendData( msLoginInput, config.getProperty("email"));
		
		// Mark the action as pass
		reportPass("Email ID entered.");
		
		// Taking screenshot of the page.
		takeScreenshot("LoginPage");
		
		// Press enter 
		pressEnter(msLoginInput);
		
		// Wait till password field is loaded
		waitTillVisibilityOfElement("msPassword_Name");
		
		// Locate the password input field
		WebElement msPasswordInput = getWebElement("msPassword_Name");
		
		// Entering password
		sendData( msPasswordInput, config.getProperty("password"));
		
		// Marking the action as Pass
		reportPass("Password entered.");
		
		// Take screenshot
		takeScreenshot("PasswordPage");
		
		// Press Enter
		pressEnter(msPasswordInput);
		
		// Waiting for 15 Seconds 
		try {
			Thread.sleep(Duration.ofSeconds(15));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Wait till 'Yes' button is visible
		waitTillVisibilityOfElement("msClickYes_Id");
		
		takeScreenshot("Click Yes");
		
		// Click 'Yes'
		clickElement("msClickYes_Id");
		
		// Marking the test case as Pass
		reportPass("Clicked Yes.");
		
		reportPass("Logged into Be.Cognizant successfully");
	}
}
