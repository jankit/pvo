package com.pvo.user.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.pvo.prototype.PVOService;
import com.pvo.util.Constant;
import com.pvo.util.Utils;

public class MyPropertyInActiveService extends PVOService<JSONObject>{

	@Override
	public JSONObject executeService(String... params) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constant.InActiveMyProperty.URL);
			
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Constant.InActiveMyProperty.USER_ID, params[0]));
		nameValuePairs.add(new BasicNameValuePair(Constant.InActiveMyProperty.INT_ID,params[1] ));
		
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse response = httpclient.execute(httppost);

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())));
		String output;
		StringBuilder responseJsonStr = new StringBuilder();
	
		while ((output = br.readLine()) != null) {
			responseJsonStr.append(output);
		}
		String queryString = Utils.getQueryString(nameValuePairs);
		System.out.println("InActive MyProperty Query String "+Constant.InActiveMyProperty.URL +"&"+queryString);
		System.out.println("InActive MyProperty response Json String "+responseJsonStr );
		return new JSONObject(responseJsonStr.toString());
	}	
}


