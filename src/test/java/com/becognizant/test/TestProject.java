package com.becognizant.test;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.becognizant.base.BaseRequirements;
import com.becognizant.featuredNews.NewsInfo;
import com.becognizant.featuredNews.ReadNewsData;
import com.becognizant.profile.ProfileInfo;
import com.cognizant.login.LoginToCognizant;

public class TestProject extends BaseRequirements{
	
	LoginToCognizant loginObj = new LoginToCognizant();
	ProfileInfo info = new ProfileInfo();
	NewsInfo news = new NewsInfo();
	ReadNewsData data = new ReadNewsData();

	@BeforeSuite
	public void selectDriver() throws IOException {
		
		logger = report.createTest("Select Driver");
		
		reportInfo("Initializing the Browser");
		selectBrowser();
		reportInfo("Loading the required Files");
		loadData();
		reportPass("Select Driver passed..");
	}
	
	@Test
	public void loginMicrosoft() throws IOException{
		
		createTest("Login to Be.Cognizant");
		
		reportInfo("Starting the Log-in Process.");
		
		loginObj.login();
		
		reportPass("Logged into Be.Cognizant successfully");
		
	}
	
	@Test( dependsOnMethods = {"loginMicrosoft"} )
	public void getProfileInfo() throws IOException {
		
		createTest("Get Profile Information");
		
		reportInfo("Retreiving Profile Info");
		
		info.getProfileInfo();
		
		reportPass("Retrieved Profile data successfully");
	}
	
	@Test( dependsOnMethods = {"getProfileInfo"} )
	public void newsInfo() throws IOException {
		
		createTest("Information regarding News");
		
		news.validateTitle();
		
		reportInfo("Getting count of Featured News in Be.Cognizant");
		
		news.countOfNews();
		
		reportPass("News count displayed successfully.");
		
		
	}
	
	@Test( dependsOnMethods = {"newsInfo"} )
	public void readNewsData() throws IOException {
		createTest("Read News Data");
		
		data.readNewsData();
		
		
	}
	
	
	@AfterTest
	public void closeBrowser() {
		closeDriver();
		report.flush();
	}
}
