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


public class RegisterService extends PVOService<JSONObject> {

	/**
	 * To register user and return response of webservice.
	 * 
	 * @param mobileNo
	 * @param verificationCode
	 * @return
	 */
	@Override
	public JSONObject executeService(String... params) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constant.Registration.URL);
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Constant.Registration.MOBILE_NO, params[0]));
		nameValuePairs.add(new BasicNameValuePair(Constant.Registration.AGENT_CODE, params[1]));
		nameValuePairs.add(new BasicNameValuePair(Constant.Registration.GSM_ID, params[2]));
		nameValuePairs.add(new BasicNameValuePair(Constant.Registration.FIRST_NAME, params[3]));
		nameValuePairs.add(new BasicNameValuePair(Constant.Registration.COUNTRY_ID, params[4]));
		nameValuePairs.add(new BasicNameValuePair(Constant.Registration.STATE_ID, params[5]));
		nameValuePairs.add(new BasicNameValuePair(Constant.Registration.CITY_ID, params[6]));
		nameValuePairs.add(new BasicNameValuePair(Constant.Registration.DISTRICT_ID, params[7]));
		
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	
		HttpResponse response = httpclient.execute(httppost);
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		String output;
		StringBuilder responseJsonStr = new StringBuilder();
		
		while ((output = br.readLine()) != null) {
			responseJsonStr.append(output);
		}
		
		String queryString = Utils.getQueryString(nameValuePairs);
		System.out.println("Register Query String "+Constant.Registration.URL +"&"+queryString);
		System.out.println("Register Response "+responseJsonStr.toString());
		
		return new JSONObject(responseJsonStr.toString());
	}
}


