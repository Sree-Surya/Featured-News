package com.becognizant.featuredNews;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.openqa.selenium.WebElement;

import com.becognizant.base.BaseRequirements;

public class ReadNewsData extends BaseRequirements{
	int count = 1, rows = 0, column = 0;
	List<WebElement> news;
	List<String> links;
	CellStyle wrapStyle;
	
	public void readNewsData() throws IOException {
		
		createExcel("News_Data");
		wrapStyle = workbook.createCellStyle();
		wrapStyle.setWrapText(true); 

		
		news = getWebElements("featuredNewsCard_Xpath");
		links = new ArrayList<String>();
		
		for( WebElement url: news) {
			links.add(url.getAttribute("href"));
		}
		
		takeScreenshot("News");
		
		for( String link : links) {
			
			rows = 0;
			
			if(count > 3) {
				
				waitTillVisibilityOfElement("clickNext_Xpath");
				
				clickElement("clickNext_Xpath");
				
				reportInfo("Moved to next slide.");
			}
			
			if(count == 4) {
				takeScreenshot("Next Slide");
			}
			
			driver.navigate().to(link);
			
			createSheet("Featured News "+count);
			
			reportInfo("Reading News: "+ getTextFromElement("newsHeadline_Xpath"));
			
			takeScreenshot("News "+count);
			
			System.out.println("Title: "+ getTextFromElement("newsHeadline_Xpath"));   // title of the news article
			
			cell = sheet.createRow(rows++).createCell(column);
			cell.setCellStyle(wrapStyle);
			cell.setCellValue("Title: "+ getTextFromElement("newsHeadline_Xpath"));
			
			System.out.println("Written "+ getTextFromElement("wittenBy_Xpath"));
			
			cell = sheet.createRow(rows++).createCell(column);
			cell.setCellStyle(wrapStyle);
			cell.setCellValue("Written "+ getTextFromElement("wittenBy_Xpath"));
			
			System.out.println("Article Info:\n"+ getTextFromElement("newsData_Xpath"));
			
			cell = sheet.createRow(rows++).createCell(column);
			cell.setCellStyle(wrapStyle);
			cell.setCellValue(getTextFromElement("newsData_Xpath"));
			
			
			System.out.println("-------------------------------------------------------------------\n");
			
			
			autoSizeColumn(--rows);
			
			reportPass("News Data of [ "+getTextFromElement("newsHeadline_Xpath")+" ] read successfully.");
			
			driver.navigate().back();
			
			count++;
		}
		
		writeExcel();
		
	}
}
