package com.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentReportManager {
//	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports report;
	
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
