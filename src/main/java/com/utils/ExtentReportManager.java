package com.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentReportManager {

	public static ExtentReports report;
	
	/**
	 * This method will instantiate ExtentReports object for creating ExtentReport
	 * @return void
	 */
	public static ExtentReports getReportInstance() {
		
		if(report == null) {
			
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"\\reports\\FullReport"+DateUtils.getTimeStamp()+".html");
			
			report = new ExtentReports();
			
			report.attachReporter(htmlReporter);
			
			report.setSystemInfo("OS", "Windows 11");
			
		}
		return report;
	}
}
