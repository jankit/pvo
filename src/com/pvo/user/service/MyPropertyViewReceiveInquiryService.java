package com.pvo.user.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import com.pvo.prototype.PVOService;
import com.pvo.util.Constant;
import com.pvo.util.Utils;

public class MyPropertyViewReceiveInquiryService  extends PVOService<JSONArray>{

	@Override
	public JSONArray executeService(String... params) throws Exception{
		HttpClient httpclient = new DefaultHttpClient();
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Constant.MyPropertyViewReceiveInquiry.PROPERTY_ID, params[0]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyPropertyViewReceiveInquiry.PAGE, params[1]));

		String parameters = URLEncodedUtils.format(nameValuePairs, "utf-8");
		HttpGet httpget = new HttpGet(Constant.MyPropertyViewReceiveInquiry.URL+"&"+parameters);
		
		HttpResponse response = httpclient.execute(httpget);
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
		String queryString = Utils.getQueryString(nameValuePairs);
		System.out.println("Inquery MyProperty Query String "+Constant.MyPropertyViewReceiveInquiry.URL +"&"+queryString);
		System.out.println("Inquery MyProperty response Json String "+responseJsonStr );
		
		return new JSONArray(responseJsonStr.toString());
	}
	
}
