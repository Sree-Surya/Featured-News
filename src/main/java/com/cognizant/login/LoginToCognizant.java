package com.cognizant.login;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebElement;

import com.becognizant.base.BaseRequirements;

public class LoginToCognizant extends BaseRequirements {

	
	public void login() throws IOException{

		openWebsite( config.getProperty("url"));
		
		waitTillVisibilityOfElement("msLogin_Xpath");
		
		WebElement msLoginInput = getWebElement("msLogin_Xpath");
		
		
		sendData( msLoginInput, config.getProperty("email"));
		
		reportPass("Email ID entered.");
		
		takeScreenshot("LoginPage");
		
		pressEnter(msLoginInput);
		
		waitTillVisibilityOfElement("msPassword_Name");
		
		WebElement msPasswordInput = getWebElement("msPassword_Name");
		
		sendData( msPasswordInput, config.getProperty("password"));
		
		reportPass("Password entered.");
		
		takeScreenshot("PasswordPage");
		
		pressEnter(msPasswordInput);
		
		try {
			Thread.sleep(Duration.ofSeconds(15));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		waitTillVisibilityOfElement("msClickYes_Id");
		
		takeScreenshot("Click Yes");
		
		clickElement("msClickYes_Id");
		
		
		reportPass("Clicked Yes.");
	}
}
