package com.es.wfx.analytics.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utility {
	
	public static Date getUtcDateTime(String date) throws ParseException{
		return getSimpleDateFormat().parse(date);
	}
	
	public static String getUtcDateTime(Date date) throws ParseException{
		return getSimpleDateFormat().format(date);
	}
	
	private static SimpleDateFormat getSimpleDateFormat(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormat;
	}

}
