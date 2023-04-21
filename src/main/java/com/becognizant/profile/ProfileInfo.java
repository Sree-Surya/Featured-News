package com.becognizant.profile;

import java.io.IOException;

import com.becognizant.base.BaseRequirements;

public class ProfileInfo extends BaseRequirements{
	
	int index = 0;
	String nameText, jobTitle, name;
	
	/**
	 * This method will Read the profile information and prints them.
	 * @throws IOException
	 */
	public void getProfileInfo() throws IOException {
		
		reportInfo("Retreiving Profile Info");
		
		// Get Profile Name including designation
		nameText = getTextFromElement("profile_Xpath");
		
		index = nameText.indexOf("(");
		
		// Extract only name from nameText which also includes designation
		name = nameText.substring(0, index);
		
		// Get job title
		jobTitle = getTextFromElement("designation_Xpath");
		
		takeScreenshot("Profile_Info");
		
		// Printing the details into console
		
		System.out.println("------------------- Profile Information -------------------");
		System.out.println("Name: "+name);
		System.out.println("Job Title: "+jobTitle);
		System.out.println("-----------------------------------------------------------");
		
		reportPass("Retrieved Profile data successfully");
		
		
	}
}
