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

public class FilterFindAgentService extends PVOService<JSONArray>{

	@Override
	public JSONArray executeService(String... params) throws Exception{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constant.FilterList.URL_FIND_AGENT);
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(Constant.FilterList.PROPERTY_TYPE, params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.FilterList.PURPOSE, params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.FilterList.LOCATION, params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.FilterList.TXT_KEYWORD, params[3]));
			//nameValuePairs.add(new BasicNameValuePair(Constant.FilterList.PAGE, params[4]));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
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
			
			return new JSONArray(responseJsonStr.toString());

	}	
}
