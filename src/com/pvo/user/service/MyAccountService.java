
package com.pvo.user.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.pvo.prototype.PVOService;
import com.pvo.util.Constant;
import com.pvo.util.Utils;

public class MyAccountService extends PVOService<JSONObject> {

	@Override
	public JSONObject executeService(String... params) throws Exception {
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constant.MyAccount.URL);
		
		MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	   
	    File image1 = new File(params[9]);
	    ContentBody cbImage1 = new FileBody(image1, "jpeg");
	    System.out.println("cbImage1===> "+cbImage1);
	    File image2 = new File(params[10]);
	    ContentBody cbImage2 = new FileBody(image2, "jpeg");
   	    
   	    
   	    mpEntity.addPart(Constant.MyAccount.USERID, new StringBody( params[0], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.FIRSTNAME, new StringBody( params[1], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.LASTNAME, new StringBody( params[2], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.COMPANYNAME, new StringBody( params[3], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.WEBSITE, new StringBody( params[4], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.ADDRESS, new StringBody( params[5], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.PHONEOFFICE, new StringBody( params[6], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.PHONEMOBILE1, new StringBody( params[7], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.EMAIL, new StringBody( params[8], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.PHOTO, cbImage1);
		mpEntity.addPart(Constant.MyAccount.LOGO, cbImage2);
		mpEntity.addPart(Constant.MyAccount.STATE, new StringBody( params[11], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.DISTINCT, new StringBody( params[12], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.CITY, new StringBody( params[13], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.AREA, new StringBody( params[14], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.PROPERTYDEALS, new StringBody( params[15], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.POSTCODE, new StringBody( params[16], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.FACEBOOKLINK, new StringBody( params[17], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.TWITTERLINK, new StringBody( params[18], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.LINKEDINLINK, new StringBody( params[19], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.BUSINESSPAGELINK, new StringBody( params[20], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.AFFIATEDWITH, new StringBody( params[21], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.BUSINESSSCIENCE, new StringBody( params[22], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.LANGUAGEKNOW, new StringBody( params[23], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.LETTITUDE, new StringBody( params[24], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.MyAccount.LONGITUDE, new StringBody( params[25], Charset.forName("UTF-8")));
		httppost.setEntity(mpEntity);
   	    
   	    HttpResponse response = httpclient.execute(httppost);
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		String output;
		StringBuilder responseJsonStr = new StringBuilder();
		
		while ((output = br.readLine()) != null) {
			responseJsonStr.append(output);
		}
		
		//This code is for printing on query string
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.USERID, params[0]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.FIRSTNAME, params[1]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.LASTNAME, params[2]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.COMPANYNAME, params[3]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.WEBSITE, params[4]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.ADDRESS, params[5]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.PHONEOFFICE, params[6]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.PHONEMOBILE1, params[7]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.EMAIL, params[8]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.PHOTO, params[9]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.LOGO, params[10]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.STATE, params[11]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.DISTINCT, params[12]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.CITY, params[13]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.AREA, params[14]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.PROPERTYDEALS, params[15]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.POSTCODE, params[16]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.FACEBOOKLINK, params[17]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.TWITTERLINK, params[18]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.LINKEDINLINK, params[19]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.BUSINESSPAGELINK, params[20]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.AFFIATEDWITH, params[21]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.BUSINESSSCIENCE, params[22]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.LANGUAGEKNOW, params[23]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.LETTITUDE, params[24]));
		nameValuePairs.add(new BasicNameValuePair(Constant.MyAccount.LONGITUDE, params[25]));
		String queryString = Utils.getQueryString(nameValuePairs);
		System.out.println("My Account Query String "+Constant.MyAccount.URL +"&"+queryString);
		System.out.println("My acccount response===> "+responseJsonStr.toString());
		
		return new JSONObject(responseJsonStr.toString());
		
	}
}
