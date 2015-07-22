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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import com.pvo.prototype.PVOService;
import com.pvo.util.Constant;
import com.pvo.util.Utils;

public class PublicBrokersService  extends PVOService<JSONArray> {

	@Override
	public JSONArray executeService(String... params) throws Exception{
		
		HttpClient httpclient = new DefaultHttpClient();
			
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Constant.PublicBrokers.AGENT_ID,params[0]));
		nameValuePairs.add(new BasicNameValuePair(Constant.PublicBrokers.PAGE,params[1]));
		String queryString = Utils.getQueryString(nameValuePairs);
		
		HttpGet httpGet = new HttpGet(Constant.PublicBrokers.URL+"&"+queryString);
		HttpResponse response = httpclient.execute(httpGet);
		
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
		//System.out.println("Public Broker Query String "+Constant.PublicBrokers.URL+"&"+queryString);
		
		return new JSONArray(responseJsonStr.toString());
			
		
	}
}
