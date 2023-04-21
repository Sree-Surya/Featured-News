package com.becognizant.featuredNews;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.openqa.selenium.WebElement;

import com.becognizant.base.BaseRequirements;

public class ReadNewsData extends BaseRequirements {

	int count = 1, rows = 0, column = 0;
	List<WebElement> news;
	List<String> links;

	CellStyle wrapStyle;
	CellStyle boldText;
	XSSFFont headerFont;
	
	String newsHeadline, writtenBy, writtenOn, article;

	public void readNewsData() throws IOException {
		
		System.out.println("-------------------------FEATURED NEWS ARTICLES------------------------");
		// Create excel sheet
		createExcel("News_Data");
		
		// Set style for workbook
		wrapStyle = workbook.createCellStyle();
		wrapStyle.setWrapText(true); 
		headerFont = workbook.createFont();
		headerFont.setBold(true); // set font style to bold
		headerFont.setFontHeight(17);
		boldText = workbook.createCellStyle();
		boldText.setFont(headerFont);
		
		// Get WebElememt of NEWS
		news = getWebElements("featuredNewsCard_Xpath");
		
		links = new ArrayList<String>();
		
		// Retrieve URL from WebElements
		for( WebElement URL : news) {
			links.add(URL.getAttribute("href"));
		}
		
		takeScreenshot("News");
		
		
		for( String link : links) {
			
			rows = 0;
			// Click right arrow
			if(count > 3) {
				
				waitTillVisibilityOfElement("clickNext_Xpath");
				
				clickElement("clickNext_Xpath");
				
				reportInfo("Moved to next slide.");
			}
			
			if(count == 4) {
				try {
					Thread.sleep(Duration.ofSeconds(5));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				takeScreenshot("Next Slide");
			}
			// Open Featured NEWS
			driver.navigate().to(link);
			
			// Create sheet in excel file
			createSheet("Featured News "+count);
			
			// Get Title of Featured News
			newsHeadline = getTextFromElement("newsHeadline_Xpath");
			
			reportInfo("Reading News: "+ newsHeadline);
			
			takeScreenshot("News "+count);
			
			
			
			System.out.println("Title: "+ newsHeadline);   // title of the news article
			// Add Title into excel
			cell = sheet.createRow(rows++).createCell(column);
			cell.setCellStyle(boldText);
			cell.setCellValue(newsHeadline);
			
			// Get 'Written By' 
			writtenBy = getTextFromElement("wittenBy_Xpath");
			System.out.println("Written "+ writtenBy);
			
			// Add it into sheet
			cell = sheet.createRow(rows++).createCell(column);
			cell.setCellStyle(wrapStyle);
			cell.setCellValue("Written "+ writtenBy);
			
			// Get the time on which the NEWS article was written
			writtenOn = getTextFromElement("writtenOn_Xpath");
			System.out.println("Written On: "+ writtenOn);
			
			// Add the written on data into excel
			cell = sheet.createRow(rows++).createCell(column);
			cell.setCellStyle(wrapStyle);
			cell.setCellValue("Written On: "+ writtenOn);
			
			// Get the article data
			article = getTextFromElement("newsData_Xpath");
			
			System.out.println("Article Info:\n"+ article);
			
			// Adding article data into excel
			cell = sheet.createRow(rows++).createCell(column);
			cell.setCellStyle(wrapStyle);
			cell.setCellValue(article);
			
			
			System.out.println("---------------------------------------------------------------------------------\n");
			
			// Creating more readable excel file
			autoSizeColumn(--rows);
			
			reportPass("News Data of [ "+newsHeadline+" ] read successfully.");
			
			// Navigation back to home page.
			driver.navigate().back();
			
			count++;
		}
		
		// Writing all the data into excel file
		writeExcel();
		
	}
}
