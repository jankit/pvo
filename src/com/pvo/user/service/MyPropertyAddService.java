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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import z.com.pvo.util.ProjectUtility;

import com.pvo.prototype.PVOService;
import com.pvo.util.Constant;
import com.pvo.util.Utils;

public class MyPropertyAddService extends PVOService<JSONObject>{
	
	private boolean isPrint = true;
	private String TAG = "MyPropertyAddService";

	@Override
	public JSONObject executeService(String... params) throws Exception {
		
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used. 
		int timeoutConnection = 300000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 500000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
				
		
	   HttpClient httpclient = new DefaultHttpClient(httpParameters);
	   HttpPost httppost = new HttpPost(Constant.AddProperty.URL);
	   MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	   
	   File image1 = new File(params[68]);
	   ContentBody cbImage1 = new FileBody(image1, "jpeg");
	   File image2 = new File(params[69]);
	   ContentBody cbImage2 = new FileBody(image2, "jpeg");
	   File image3 = new File(params[70]);
	   ContentBody cbImage3 = new FileBody(image3, "jpeg");
	   File image4 = new File(params[71]);
	   ContentBody cbImage4 = new FileBody(image4, "jpeg");
	   File image5 = new File(params[72]);
	   ContentBody cbImage5 = new FileBody(image5, "jpeg");
	   
	    mpEntity.addPart(Constant.AddProperty.USER_ID, new StringBody(params[0], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.PROPERTY_TYPE, new StringBody(params[1], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.ADDRESS,new StringBody(params[2], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.POST_CODE, new StringBody(params[3], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.CMB_AREA1, new StringBody(params[4], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.COUNTRY_ID, new StringBody(params[5], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.STATE_ID,new StringBody(params[6], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.CITY_ID,new StringBody(params[7], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.DISTRICT_ID,new StringBody(params[8], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.STR_OPTIONS,new StringBody(params[9], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.LANDMARK1,new StringBody(params[10], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.LANDMARK2,new StringBody(params[11], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.LANDMARK1_OTHER,new StringBody(params[12], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.LANDMARK2_OTHER,new StringBody(params[13], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.LONGITUDE,new StringBody(params[14], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.LATITUDE,new StringBody(params[15], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.OCCUPACY,new StringBody(params[16], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.OCCUPANCY_NAME,new StringBody(params[17], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.OCCUPANCY_DETAIL,new StringBody(params[18], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.OCCUPANCY_DATE,new StringBody(params[19], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.PRICE,new StringBody(params[20], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.TOTAL_PRICE,new StringBody(params[21], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.RENT,new StringBody(params[22], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.DASTAWAGE,new StringBody(params[23], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.RENT_DEPOSIT,new StringBody(params[24], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.AREA_UNIT,new StringBody(params[25], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.CMB_PURPOSE,new StringBody(params[26], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.MIN_AREA,new StringBody(params[27], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.MAX_AREA,new StringBody(params[28], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.YEAR_BUILD_UP,new StringBody(params[29], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.COMMENTS,new StringBody(params[30], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.HINT,new StringBody(params[31], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.BED,new StringBody(params[32], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.FURNISH_STATUS,new StringBody(params[33], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.FURNISH_COMMENT,new StringBody(params[34], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.FLOOR,new StringBody(params[35], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.RISE,new StringBody(params[36], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.WHOM_TO_LET,new StringBody(params[37], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.WHOM_TO_LET_OTHER,new StringBody(params[38], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.PARKING,new StringBody(params[39], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.FRONT_HEIGHT,new StringBody(params[40], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.ATTACH_COMMON,new StringBody(params[41], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.CONSTRUCTION_AREA,new StringBody(params[42], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.BUNGLOW_TYPE,new StringBody(params[43], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.MIN_PLOT_AREA,new StringBody(params[44], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.PLOT_AREA,new StringBody(params[45], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.PLOT_AREA_UNIT,new StringBody(params[46], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.PLOT_TYPE,new StringBody(params[47], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.NA_STATUS,new StringBody(params[48], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.KHETI,new StringBody(params[49], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.NAVISHARAT,new StringBody(params[50], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.JUNISHARAT,new StringBody(params[51], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.PRASSAP,new StringBody(params[52], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.DIS_PUTE,new StringBody(params[53], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.TITLE_CLEAR,new StringBody(params[54], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.SHREE_SARKAR,new StringBody(params[55], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.ON_ROAD,new StringBody(params[56], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.PREF_OPT,new StringBody(params[57], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.MAINTENANCE,new StringBody(params[58], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.TRANSFER_FEES,new StringBody(params[59], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.AEC_AUDA,new StringBody(params[60], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.CMBTP_SCHEME,new StringBody(params[61], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.CHK_ZONE,new StringBody(params[62], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.CHK_MESSAGE,new StringBody(params[63], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.CHK_MAIL,new StringBody(params[64], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.CHK,new StringBody(params[65], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.CHK_FACILITY,new StringBody(params[66], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.CHK_BROKER,new StringBody(params[67], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.IMAGE_1,cbImage1);
		mpEntity.addPart(Constant.AddProperty.IMAGE_2,cbImage2);
		mpEntity.addPart(Constant.AddProperty.IMAGE_3,cbImage3);
		mpEntity.addPart(Constant.AddProperty.IMAGE_4,cbImage4);
		mpEntity.addPart(Constant.AddProperty.IMAGE_5,cbImage5);
		mpEntity.addPart(Constant.AddProperty.CHK_GROUP,new StringBody(params[73], Charset.forName("UTF-8")));
		mpEntity.addPart(Constant.AddProperty.CHK_AREA,new StringBody(params[74], Charset.forName("UTF-8")));
		httppost.setEntity(mpEntity);
		
		
		ProjectUtility.sys(isPrint, TAG, "Zone--> "+params[62]);
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.USER_ID, params[0]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.PROPERTY_TYPE, params[1]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.ADDRESS,params[2] ));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.POST_CODE, params[3]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.CMB_AREA1, params[4]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.COUNTRY_ID, params[5]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.STATE_ID,params[6]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.CITY_ID,params[7]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.DISTRICT_ID,params[8]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.STR_OPTIONS,params[9]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.LANDMARK1,params[10]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.LANDMARK2,params[11]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.LANDMARK1_OTHER,params[12]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.LANDMARK2_OTHER,params[13]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.LONGITUDE,params[14]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.LATITUDE,params[15]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.OCCUPACY,params[16]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.OCCUPANCY_NAME,params[17]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.OCCUPANCY_DETAIL,params[18]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.OCCUPANCY_DATE,params[19]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.PRICE,params[20]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.TOTAL_PRICE,params[21]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.RENT,params[22]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.DASTAWAGE,params[23]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.RENT_DEPOSIT,params[24]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.AREA_UNIT,params[25]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.CMB_PURPOSE,params[26]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.MIN_AREA,params[27]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.MAX_AREA,params[28]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.YEAR_BUILD_UP,params[29]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.COMMENTS,params[30]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.HINT,params[31]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.BED,params[32]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.FURNISH_STATUS,params[33]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.FURNISH_COMMENT,params[34]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.FLOOR,params[35]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.RISE,params[36]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.WHOM_TO_LET,params[37]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.WHOM_TO_LET_OTHER,params[38]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.PARKING,params[39]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.FRONT_HEIGHT,params[40]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.ATTACH_COMMON,params[41]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.CONSTRUCTION_AREA,params[42]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.BUNGLOW_TYPE,params[43]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.MIN_PLOT_AREA,params[44]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.PLOT_AREA,params[45]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.PLOT_AREA_UNIT,params[46]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.PLOT_TYPE,params[47]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.NA_STATUS,params[48]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.KHETI,params[49]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.NAVISHARAT,params[50]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.JUNISHARAT,params[51]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.PRASSAP,params[52]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.DIS_PUTE,params[53]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.TITLE_CLEAR,params[54]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.SHREE_SARKAR,params[55]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.ON_ROAD,params[56]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.PREF_OPT,params[57]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.MAINTENANCE,params[58]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.TRANSFER_FEES,params[59]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.AEC_AUDA,params[60]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.CMBTP_SCHEME,params[61]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.CHK_ZONE,params[62]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.CHK_MESSAGE,params[63]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.CHK_MAIL,params[64]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.CHK,params[65]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.CHK_FACILITY,params[66]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.CHK_BROKER,params[67]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.IMAGE_1,params[68]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.IMAGE_2,params[69]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.IMAGE_3,params[70]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.IMAGE_4,params[71]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.IMAGE_5,params[72]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.CHK_GROUP,params[73]));
		nameValuePairs.add(new BasicNameValuePair(Constant.AddProperty.CHK_AREA,params[74]));
		
		/*httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));*/
		
		HttpResponse response = httpclient.execute(httppost);
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		String output;
		StringBuilder responseJsonStr = new StringBuilder();
	
		while ((output = br.readLine()) != null) {
			responseJsonStr.append(output);
		}
		
		System.out.println("Add MyProperty Query String "+Constant.AddProperty.URL +"&"+Utils.getQueryString(nameValuePairs));
		System.out.println("Add MyProperty response Json String "+responseJsonStr );
		
		return new JSONObject(responseJsonStr.toString());
	}
}