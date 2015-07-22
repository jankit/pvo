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

public class MyRequirementAddService extends PVOService<JSONObject>{

	@Override
	public JSONObject executeService(String... params) throws Exception {
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constant.AddMyRequirement.URL);

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.USER_ID, params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.ID,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.CMB_PROP_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.PROPERTY_SUB_TYPE, params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.COUNTRY_ID, params[4]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.STATE_ID,params[5]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.CITY_ID,params[6]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.DISTRICT_ID,params[7]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.LOCATION,params[8]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.HINT,params[9]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.KEYWORd,params[10]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.STR_OPTION,params[11]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.TXT_MIN_PRICE,params[12]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.TXT_MAX_PRICE,params[13]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.TXT_MIN_RENT,params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.TXT_MAX_RENT,params[15]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.MIN_BED,params[16]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.MAX_BED,params[17]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.MIN_FLOOR,params[18]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.MAX_FLOOR,params[19]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.FURNISH_STATUS,params[20]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.RISE,params[21]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.MIN_SQ_FOOT,params[22]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.MAX_SQ_FOOT,params[23]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.MIN_PLOT_AREA,params[24]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.MAX_PLOT_AREA,params[25]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.MIN_CONSTRUCTION_AREA,params[26]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.MAX_CONSTRUCTION_AREA,params[27]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.CMB_TP_SCHEME,params[28]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.ST_PURPOSE,params[29]));
			nameValuePairs.add(new BasicNameValuePair(Constant.AddMyRequirement.CHK_ZONE,params[30]));
			
			
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String output;
			StringBuilder responseJsonStr = new StringBuilder();
			
		
			while ((output = br.readLine()) != null) {
				responseJsonStr.append(output);
			}
			String queryString = Utils.getQueryString(nameValuePairs);
			System.out.println("Add My Requirement Query String "+Constant.AddMyRequirement.URL +"&"+queryString);
			System.out.println("Add Requirement response Json String "+responseJsonStr );
			
			return new JSONObject(responseJsonStr.toString());
			
	}
}