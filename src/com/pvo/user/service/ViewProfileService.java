package com.pvo.user.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import com.pvo.prototype.PVOService;
import com.pvo.util.Constant;

public class ViewProfileService extends PVOService<JSONArray>{

	@Override
	public JSONArray executeService(String... params) throws Exception{
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(Constant.ViewProfile.URL+"&brokerid="+params[0]);
		
		/*HttpPost httppost = new HttpPost(Constant.ViewProfile.URL);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Constant.ViewProfile.BROKER_ID, params[0]));
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));*/
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
		
		//String queryString = Utils.getQueryString(nameValuePairs);
		System.out.println("View profile Query String "+Constant.ViewProfile.URL+"&brokerid="+params[0]);
		System.out.println("View profile response Json String "+responseJsonStr );
		
		
		return new JSONArray(responseJsonStr.toString());
	}
}
