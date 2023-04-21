package com.utils;

import java.util.Date;

public class DateUtils {
	/**
	 * This method will return the time stamp.
	 * @return String
	 */
	public static String getTimeStamp() {
		Date date = new Date();
		
		return date.toString().replaceAll(":", "_").replaceAll(" ","_");
	}
}
