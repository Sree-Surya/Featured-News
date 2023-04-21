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
	
	LoginToCognizant loginObj;
	ProfileInfo info;
	NewsInfo news;
	ReadNewsData data;
	
	@BeforeSuite
	public void selectDriver() throws IOException {
		
		logger = report.createTest("Select Driver");
		
		selectBrowser();
		
		loadData();
		
		reportPass("Select Driver passed..");
	}
	
	@Test
	public void loginMicrosoft() throws IOException{
		
		loginObj = new LoginToCognizant();
		createTest("Login to Be.Cognizant");
		
		loginObj.login();
	}
	
	@Test( dependsOnMethods = {"loginMicrosoft"} )
	public void getProfileInfo() throws IOException {
		
		info = new ProfileInfo();
		createTest("Get Profile Information");
		
		info.getProfileInfo();
		
	}
	
	@Test( dependsOnMethods = {"getProfileInfo"} )
	public void newsInfo() throws IOException {
		
		news = new NewsInfo();
		
		createTest("Information regarding News");
		
		news.validateTitle();
		
		news.countOfNews();
		
		
		
	}
	
	@Test( dependsOnMethods = {"newsInfo"} )
	public void readNewsData() throws IOException {
		
		data = new ReadNewsData();
		
		createTest("Read News Data");
		
		data.readNewsData();
		
		
	}
	
	
	@AfterTest
	public void closeBrowser() {
		
		createTest("After Test");
		
		closeDriver();
		
		flushReport();
	}
}
