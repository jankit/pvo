package com.pvo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class ConvertDateFormat {

	static SimpleDateFormat originalDate = new SimpleDateFormat("yyyy-MM-dd");
	@SuppressLint("SimpleDateFormat")
	static SimpleDateFormat convertedDate = new SimpleDateFormat("MMM dd, yyyy");
	
	public static String convertDateFormat(String date) throws ParseException {
		if(date.length() > 0) {
			Date stringToDate= originalDate.parse(date);
			String cdateToString = convertedDate.format(stringToDate);
			return cdateToString;
		}
		return null;
	}
}

