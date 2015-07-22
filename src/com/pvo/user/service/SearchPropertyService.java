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

public class SearchPropertyService extends PVOService<JSONArray>{
	
	@Override
	public JSONArray executeService(String... params) throws Exception {
		
		HttpClient httpclient = new DefaultHttpClient();
		List<NameValuePair> nameValuePair = getNameValuePair(params);
		String queryString = Utils.getQueryString(nameValuePair);
		System.out.println("SearchProperty queryString = "+Constant.SearchProperty.URL+"&"+queryString);	
		HttpGet httpGet = new HttpGet(Constant.SearchProperty.URL+"&"+queryString.replaceAll(" ", "%20"));//queryString.replaceAll(" ", "%20"));
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
		System.out.println("Response "+responseJsonStr);
		return new JSONArray(responseJsonStr.toString());
	}	
	
	private List<NameValuePair> getNameValuePair(String[] params){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if(params[0].equals("Flat") && params[1].equals("Rent")){//Flat For Rent Request Parameter
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PROPERTY_SUB_TYPE, params[4]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_LANDMARK1,""));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_LANDMARK2,""));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.KEYWORD,params[5]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.MIN_AREA,params[6]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.MAX_AREA,params[7]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CHK_RENT,"1"));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_RENT,params[12]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_RENT,params[13]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_MIN_BED,params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_MAX_BED,params[15]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_MIN_FLOOR,params[16]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_MAX_FLOOR,params[17]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PURPOSE,params[18]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.RD_RISE,params[19]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.FURNISH_STATUS,params[28]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PAGE,params[38]));
		
		} else if(params[0].equals("Flat") && params[1].equals("Sale")){//Falt For Sale Request Parameter
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PROPERTY_SUB_TYPE, params[4]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_LANDMARK1,""));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_LANDMARK2,""));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.KEYWORD,params[5]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.MIN_AREA,params[6]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.MAX_AREA,params[7]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CHK_SELL,"1"));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_PRICE,params[10]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_PRICE,params[11]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_MIN_BED,params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_MAX_BED,params[15]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_MIN_FLOOR,params[16]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_MAX_FLOOR,params[17]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.RD_RISE,params[19]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PURPOSE,params[18]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.FURNISH_STATUS,params[28]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PAGE,params[38]));
		
		} //Bunglow/all bunglow for Sale Request Parameter
		else if((params[0].equals("Bunglow") || params[0].equals("all bunglow")) && params[1].equals("Sale") && !params[2].equals("Farm House(Bunglow)")) {
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PROPERTY_SUB_TYPE, params[4]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_LANDMARK1,""));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_LANDMARK2,""));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.KEYWORD,params[5]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.MIN_PLOT_AREA,params[20]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.MAX_PLOT_AREA,params[21]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.MIN_CONSTRUCTION_AREA,params[22]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.MAX_CONSTRUCTION_AREA,params[23]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CHK_SELL,"1"));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_PRICE,params[10]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_PRICE,params[11]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_MIN_BED,params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_MAX_BED,params[15]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PURPOSE,params[18]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.FURNISH_STATUS,params[28]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PAGE,params[38]));
		}//Bunglow/all bunglow for Rent Request Parameter 
		else if((params[0].equals("Bunglow") || params[0].equals("all bunglow")) && params[1].equals("Rent") && !params[2].equals("Farm House(Bunglow)")){
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PROPERTY_SUB_TYPE, params[4]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_LANDMARK1,""));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_LANDMARK2,""));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.KEYWORD,params[5]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.MIN_PLOT_AREA,params[20]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.MAX_PLOT_AREA,params[21]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.MIN_CONSTRUCTION_AREA,params[22]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.MAX_CONSTRUCTION_AREA,params[23]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CHK_RENT,"1"));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_RENT,params[12]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_RENT,params[13]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_MIN_BED,params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_MAX_BED,params[15]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PURPOSE,params[18]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.FURNISH_STATUS,params[28]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PAGE,params[38]));
		} //Shop/all shop For Sale Request Parameter
		else if((params[0].equals("Shop") || params[0].equals("all shop")) && params[1].equals("Sale")){
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PROPERTY_SUB_TYPE, params[4]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_LANDMARK1,""));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_LANDMARK2,""));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.KEYWORD,params[5]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_AREA,params[8]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_AREA,params[9]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CHK_SELL,"1"));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_PRICE,params[10]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_PRICE,params[11]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_MIN_FLOOR,params[16]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_MAX_FLOOR,params[17]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.RD_RISE,params[19]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.FURNISH_STATUS,params[28]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PAGE,params[38]));
		}//Shop/all shop For Rent Request Parameter 
		else if((params[0].equals("Shop") || params[0].equals("all shop")) && params[1].equals("Rent")) {
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PROPERTY_SUB_TYPE, params[4]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_LANDMARK1,""));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_LANDMARK2,""));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.KEYWORD,params[5]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_PLOT_AREA,params[24]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_PLOT_AREA,params[25]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_CONSTRUCTION_AREA,params[26]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_CONSTRUCTION_AREA,params[27]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CHK_RENT,"1"));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_RENT,params[12]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_RENT,params[13]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_MIN_FLOOR,params[16]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_MAX_FLOOR,params[17]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.RD_RISE,params[19]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.FURNISH_STATUS,params[28]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PAGE,params[38]));
		} //Plot/all plot For Sale Web Service
		else if((params[0].equals("Plot") || params[0].equals("all plots")) && params[1].equals("Sale")) {
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_TP_SCHEME,params[30]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.KEYWORD,params[5]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CHK_ZONE,params[29]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.NA_STATUS,params[37]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.NAVI_SHARAT,params[31]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.JUNI_SHARAT,params[32]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.KHETI,params[33]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PRASSAP,params[34]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.DISPUTE,params[35]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.SHREE_SARKAR,params[36]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_PLOT_AREA,params[24]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_PLOT_AREA,params[25]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_CONSTRUCTION_AREA,params[26]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_CONSTRUCTION_AREA,params[27]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CHK_SELL,"1"));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_PRICE,params[10]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_PRICE,params[11]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PURPOSE,params[18]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PAGE,params[38]));
		} //Plot/all plot For Rent Web Service 
		else if((params[0].equals("Plot") || params[0].equals("all plots")) && params[1].equals("Rent")){
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_TP_SCHEME,params[30]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.KEYWORD,params[5]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CHK_ZONE,params[29]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.NA_STATUS,params[37]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.NAVI_SHARAT,params[31]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.JUNI_SHARAT,params[32]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.KHETI,params[33]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PRASSAP,params[34]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.DISPUTE,params[35]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.SHREE_SARKAR,params[36]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_PLOT_AREA,params[24]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_PLOT_AREA,params[25]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_CONSTRUCTION_AREA,params[26]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_CONSTRUCTION_AREA,params[27]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CHK_RENT,"1"));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_RENT,params[12]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_RENT,params[13]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PURPOSE,params[18]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PAGE,params[38]));
		} //Farm House(Bunglow) for Sale
		else if(params[1].equals("Sale") && params[2].equals("Farm House(Bunglow)") ){
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_TP_SCHEME,params[30]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.KEYWORD,params[5]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CHK_ZONE,params[29]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.NA_STATUS,params[37]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.NAVI_SHARAT,params[31]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.JUNI_SHARAT,params[32]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.KHETI,params[33]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PRASSAP,params[34]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.DISPUTE,params[35]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.SHREE_SARKAR,params[36]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_PLOT_AREA,params[24]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_PLOT_AREA,params[25]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_CONSTRUCTION_AREA,params[26]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_CONSTRUCTION_AREA,params[27]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CHK_SELL,"1"));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_PRICE,params[10]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_PRICE,params[11]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PURPOSE,params[18]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PAGE,params[38]));
		} //Farm House(Bunglow) for Rent
		else{
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_TP_SCHEME,params[30]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.KEYWORD,params[5]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CHK_ZONE,params[29]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.NA_STATUS,params[37]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.NAVI_SHARAT,params[31]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.JUNI_SHARAT,params[32]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.KHETI,params[33]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PRASSAP,params[34]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.DISPUTE,params[35]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.SHREE_SARKAR,params[36]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.MIN_PLOT_AREA,params[20]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.MAX_PLOT_AREA,params[21]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.MIN_CONSTRUCTION_AREA,params[22]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.MAX_CONSTRUCTION_AREA,params[23]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CHK_RENT,"1"));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MIN_RENT,params[12]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.TXT_MAX_RENT,params[13]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.CMB_PURPOSE,params[18]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchProperty.PAGE,params[38]));
		}
		return nameValuePairs;
	}
}


