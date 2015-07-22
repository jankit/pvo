package com.pvo.user.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import com.pvo.prototype.PVOService;
import com.pvo.util.Constant;
import com.pvo.util.Utils;

public class AreaListOfCityService  extends PVOService<JSONArray>{
	HttpResponse response;
	@Override
	public JSONArray executeService(String... params) throws Exception{
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constant.Area.URL);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
		
		//First condition call from add new property 
		if(params != null && params.length > 0) {
			System.out.println("===> Call From Add property");
			nameValuePairs.add(new BasicNameValuePair(Constant.Area.CITY_ID,params[0]));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = httpclient.execute(httppost);
			System.out.println("Area (Location) List of city Query String "+Constant.Area.URL+"&"+Utils.getQueryString(nameValuePairs));
	    } else {
	    	response = httpclient.execute(httppost);
	    	System.out.println("All Area(Location) List  Query String "+Constant.Area.URL);
	    }
			
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		String output;
		StringBuilder responseJsonStr = new StringBuilder();
		while ((output = br.readLine()) != null) {
			responseJsonStr.append(output);
		}
		if(!StringUtils.startsWith(responseJsonStr.toString(), "[")) {
			responseJsonStr.insert(0,"[");
			responseJsonStr.append("]");
		}
		
		//String queryString = Utils.getQueryString(nameValuePairs);
		
		//System.out.println("Area List of city response Json String "+responseJsonStr );
		
		return new JSONArray(responseJsonStr.toString());
	}
}
