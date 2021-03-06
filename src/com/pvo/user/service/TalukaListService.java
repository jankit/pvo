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

public class TalukaListService  extends PVOService<JSONArray>{
	private HttpResponse response;
	private List<NameValuePair> nameValuePairs;

	@Override
	public JSONArray executeService(String... params) throws Exception{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constant.TalukaList.URL);
		
		//get the list of tpscheme of particular city 
		if(params.length > 0) {
			nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(Constant.TalukaList.DISTRICT_ID,params[0]));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = httpclient.execute(httppost);
			System.out.println("Taluka list District wise Query String "+Utils.getQueryString(nameValuePairs));
		} else {
			response = httpclient.execute(httppost);
		}

		//HttpResponse response = httpclient.execute(httppost);
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
		
		return new JSONArray(responseJsonStr.toString());
			
		
	}
}
