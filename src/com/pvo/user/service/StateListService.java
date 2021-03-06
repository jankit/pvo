package com.pvo.user.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import com.pvo.prototype.PVOService;
import com.pvo.util.Constant;

public class StateListService  extends PVOService<JSONArray>{

	@Override
	public JSONArray executeService(String... params) throws Exception{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constant.State.URL);
			
			HttpResponse response = httpclient.execute(httppost);
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String output;
			StringBuilder responseJsonStr = new StringBuilder();
			
			while ((output = br.readLine()) != null) {
				responseJsonStr.append(output);
			}
			
	        System.out.println("State list Query String "+Constant.City.URL);
	        System.out.println("state List response Json String "+responseJsonStr);
			
			return new JSONArray(responseJsonStr.toString());
	}
}
