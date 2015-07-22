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
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import z.com.pvo.util.ProjectUtility;
import android.util.Log;

import com.pvo.prototype.PVOService;
import com.pvo.util.Constant;
import com.pvo.util.Utils;

public class CityListService  extends PVOService<JSONArray>{
	private HttpResponse response;
	private List<NameValuePair> nameValuePairs;
	
	@Override
	public JSONArray executeService(String... params) throws Exception{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constant.City.URL);

		nameValuePairs = new ArrayList<NameValuePair>();
		//First condition call from add my property
		if(params.length == 1) {
			//nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(Constant.City.STATE_ID,params[0]));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = httpclient.execute(httppost);
			System.out.println("City List of state Query String "+Constant.City.URL+"&"+Utils.getQueryString(nameValuePairs));
		} else if(params.length == 2){
			//nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(Constant.City.DISTRICT_ID,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.City.STATE_ID,params[1]));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = httpclient.execute(httppost);
			System.out.println("City List Query String by district "+Constant.City.URL+"&"+Utils.getQueryString(nameValuePairs));
		} else {
			response = httpclient.execute(httppost);
			System.out.println("City List Query String "+Constant.City.URL);
		}
		
		//InputStreamReader _response = EntityUtils.toString(response.getEntity().getContent()); // content will be consume only once
		
		//System.out.println("_response --> "+_response );
		/*final JSONObject jObject=new JSONObject(_response);
		Log.e("XXX",_response);*/
		
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
		//System.out.println("City List response Json String "+responseJsonStr );
		return new JSONArray(responseJsonStr.toString());
	}
}
