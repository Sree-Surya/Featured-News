package com.becognizant.featuredNews;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.becognizant.base.BaseRequirements;

public class NewsInfo extends BaseRequirements{
	
	public void validateTitle() throws IOException {
		
		
		String expectedTitle = getTextFromElement("featuredNewsTitle_Xpath");
		
		try {

			Assert.assertEquals(config.getProperty("featuredNewsTitle"), expectedTitle.trim().substring( 0 , 13 ).trim());
			
		}
		catch(Exception e) {
			
			System.out.println("\n------------------------------------------------------------");
			System.out.println("Title ( FEATURED NEWS ) is Incorrect.");
			System.out.println("------------------------------------------------------------\n");
			
			reportFail("Title is incorrect.");
		}
		
		reportPass("Title ( FEATURED NEWS ) is correct.");
		
		System.out.println("\n------------------------------------------------------------");
		System.out.println("Title ( FEATURED NEWS ) is correct.");
		System.out.println("------------------------------------------------------------\n");
		
		takeScreenshot("Title");
		
	}
	
	public void countOfNews() throws IOException {
		
		List<WebElement> news = getWebElements("featuredNewsCard_Xpath");
		
		System.out.println("------------------- NEWS Count -------------------");
		System.out.println(news.size());
		System.out.println("--------------------------------------------------");
		
		
	}
}
