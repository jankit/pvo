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

public class MyPropertyService  extends PVOService<JSONArray>{

	@Override
	public JSONArray executeService(String... params) throws Exception{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constant.MyProperty.URL);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			
		//check the web service call from filter 
		if(params.length > 2){
			nameValuePairs.add(new BasicNameValuePair(Constant.FilterList.USER_ID, params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.FilterList.PROPERTY_TYPE, params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.FilterList.PURPOSE, params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.FilterList.LOCATION, params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.FilterList.TXT_KEYWORD, params[4]));
			nameValuePairs.add(new BasicNameValuePair(Constant.FilterList.PAGE, params[5]));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} else {
			nameValuePairs.add(new BasicNameValuePair(Constant.MyProperty.USER_ID, params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.MyProperty.PAGE, params[1]));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		}

		String queryString = Utils.getQueryString(nameValuePairs);
		System.out.println("My Property queryString = "+Constant.MyProperty.URL+"&"+queryString);
		
		HttpResponse response = httpclient.execute(httppost);
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

		return  new JSONArray(responseJsonStr.toString());
	}
}
