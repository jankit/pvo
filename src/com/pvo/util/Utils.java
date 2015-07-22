package com.pvo.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;

public class Utils {

	public static String getQueryString(List<NameValuePair> parameters)
	{
	    List<String> items = new ArrayList<String>();
	 
	    for(NameValuePair name : parameters)
	        items.add(name.getName()+ "=" + name.getValue());
	    
	    return StringUtils.join(items.iterator(), "&");
	}
	
}
