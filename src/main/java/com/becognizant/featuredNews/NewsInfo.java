package com.becognizant.featuredNews;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.becognizant.base.BaseRequirements;

public class NewsInfo extends BaseRequirements{
	
	public void validateTitle() throws IOException {
		
		// Get the expected title
		String actualTitle = getTextFromElement("featuredNewsTitle_Xpath");
		
		try {
			
			Assert.assertEquals(actualTitle.trim().substring( 0 , 13 ).trim(), config.getProperty("featuredNewsTitle"));
			
		}
		catch(Exception e) {
			
			System.out.println("\n------------------------------------------------------------");
			System.out.println("Title ( FEATURED NEWS ) is Incorrect.");
			System.out.println("------------------------------------------------------------\n");
			
			reportFail("Title is incorrect.");
		}
		
		
		
		// Printing the details into console.
		System.out.println("\n------------------------------------------------------------");
		System.out.println("Title ( FEATURED NEWS ) is correct.");
		System.out.println("------------------------------------------------------------\n");
		
		reportPass("Title ( FEATURED NEWS ) is correct.");
		
		takeScreenshot("Title");
		
	}
	
	/**
	 * This method will print the Number of NEWS articles available in be.cognizant
	 * @throws IOException
	 */
	public void countOfNews() throws IOException {
		
		reportInfo("Getting count of Featured News in Be.Cognizant");
		
		List<WebElement> news = getWebElements("featuredNewsCard_Xpath");
		
		System.out.println("------------------------- NEWS Count -----------------------------");
		System.out.println(news.size());
		System.out.println("------------------------------------------------------------------\n");
		
		reportPass("Number of NEWS articles "+news.size()+" displayed successfully.");
		
		// To take screenshot of next slide
		clickElement("clickNext_Xpath");
		
		try {
			Thread.sleep(Duration.ofSeconds(5));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		takeScreenshot("Slide 2");
		
		clickElement("clickPrevious_Xpath");
	}
}
