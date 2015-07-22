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

public class AdsListingService  extends PVOService<JSONArray>{
	
	private HttpResponse response;
	@Override
	public JSONArray executeService(String... params) throws Exception{
		
		HttpClient httpclient = new DefaultHttpClient();
		
		HttpPost httppost = new HttpPost(Constant.AdsListing.URL);
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
		
		if(params != null && params.length > 0) {
			nameValuePairs.add(new BasicNameValuePair(Constant.FilterAdsListing.BUILDER_ID, params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.FilterAdsListing.BUILDER_NAME, params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.FilterAdsListing.CMB_AREA1,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.FilterAdsListing.TXT_KEYWORD,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.FilterAdsListing.PRICE_RANGE,params[4]));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = httpclient.execute(httppost);
			
	    } else {
	    	response = httpclient.execute(httppost);
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
			
			System.out.println("AdsListing Query String "+Constant.AdsListing.URL);
			System.out.println("AdsListing response Json String "+responseJsonStr );
			
			return new JSONArray(responseJsonStr.toString());
	}
}
