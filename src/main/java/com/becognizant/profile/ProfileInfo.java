package com.becognizant.profile;

import java.io.IOException;

import com.becognizant.base.BaseRequirements;

public class ProfileInfo extends BaseRequirements{
	
	public void getProfileInfo() throws IOException {
		
		int index = 0;
		String name, jobTitle;
		
		name = getTextFromElement("profile_Xpath");
		
		// S, Sreesurya ( Contractor )
		
		index = name.indexOf("(");
		name = name.substring(0, index);
		
		jobTitle = getTextFromElement("designation_Xpath");
		
		takeScreenshot("Profile_Info");
		
		System.out.println("------------------- Profile Information -------------------");
		System.out.println("Name: "+name);
		System.out.println("Job Title: "+jobTitle);
		System.out.println("-----------------------------------------------------------");
		
		
	}
}
