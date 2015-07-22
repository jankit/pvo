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


// This Web Service is For Search Requirement 
public class SearchRequirementService extends PVOService<JSONArray>{
	
	@Override
	public JSONArray executeService(String... params) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet;
			
		List<NameValuePair> nameValuePair = getNameValuePair(params);
		String queryString = Utils.getQueryString(nameValuePair);
		System.out.println("Search Requirement queryString = "+Constant.SearchRequirement.URL+"&"+queryString);//URLEncoder.encode(queryString,"utf-8"));
		
		httpGet = new HttpGet(Constant.SearchRequirement.URL+"&"+queryString.replaceAll(" ", "%20"));
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
		//System.out.println("Response String "+responseJsonStr);
		return new JSONArray(responseJsonStr.toString());
	}	
	
	//This method is used for return the name value pair for Flat,Shop,Bunglow,Plot for Sale and Rent
	private List<NameValuePair> getNameValuePair(String[] params){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		//Flat For Rent Request Parameter 
		System.out.println("proeprty type---> "+params[0]);
		if(params[0].equals("Flat") && params[1].equals("Rent")){
			System.out.println("****** Flat Rent *****");
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.BED,params[4]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.FURNISH_STATUS,params[5]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.FLOOR,params[6]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.RISE,params[7]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.ST_PURPOSE,params[8]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.AREA_SQFOOT,params[9]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.TXT_MIN_RENT,params[10]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.TXT_MAX_RENT,params[11]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.KEYWORD,params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PAGE,params[26]));
		}//Falt For Sale Request Parameter
		else if(params[0].equals("Flat") && params[1].equals("Sale")){
			System.out.println("****** Flat Sale *****");
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.BED,params[4]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.FURNISH_STATUS,params[5]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.FLOOR,params[6]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.RISE,params[7]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.ST_PURPOSE,params[8]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.AREA_SQFOOT,params[9]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.TXT_MIN_PRICE,params[12]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.TXT_MAX_PRICE,params[13]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.KEYWORD,params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PAGE,params[26]));
		}//Bunglow for Sale Request Parameter all bunglow
		else if((params[0].equals("Bunglow") || params[0].equals("all bunglow"))  && params[1].equals("Sale") && !params[2].equals("Farm House(Bunglow)")){
			System.out.println("****** Bunglow Sale Not Farm house *****");
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_SUBTYPE,params[25]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.BED,params[4]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.FURNISH_STATUS,params[5]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.ST_PURPOSE,params[8]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PLOT_AREA,params[15]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.AREA_UNIT,params[18]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CONSTR_AREA,params[17]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.TXT_MIN_PRICE,params[12]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.TXT_MAX_PRICE,params[13]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.KEYWORD,params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PAGE,params[26]));
		}//Bunglow for Rent Request Parameter 
		else if((params[0].equals("Bunglow") || params[0].equals("all bunglow")) && params[1].equals("Rent") && !params[2].equals("Farm House(Bunglow)")){
			System.out.println("****** Bunglow Rent Not Farm house *****");
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_SUBTYPE,params[25]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.BED,params[4]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.FURNISH_STATUS,params[5]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.ST_PURPOSE,params[8]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PLOT_AREA,params[15]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.AREA_UNIT,params[18]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CONSTR_AREA,params[17]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.TXT_MIN_RENT,params[10]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.TXT_MAX_RENT,params[11]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.KEYWORD,params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PAGE,params[26]));
		}// Shop For Sale Request Parameter all shop
		else if((params[0].equals("Shop") || params[0].equals("all shop")) && params[1].equals("Sale")){
			System.out.println("****** Shop Sale *****");
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_SUBTYPE,params[25]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.FLOOR,params[6]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.FURNISH_STATUS,params[5]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.AREA_SQFOOT,params[9]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.AREA_UNIT,params[18]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PLOT_AREA,params[15]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PLOT_AREA_UNIT,params[22]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CONSTR_AREA,params[17]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CONSTR_AREA_UNIT,params[23]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.RISE,params[7]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.TXT_MIN_PRICE,params[12]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.TXT_MAX_PRICE,params[13]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.KEYWORD,params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PAGE,params[26]));
		}//Shop For Rent Request Parameter 
		else if((params[0].equals("Shop") || params[0].equals("all shop")) && params[1].equals("Rent")){
			System.out.println("****** Shop Rent *****");	
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_SUBTYPE,params[25]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.FLOOR,params[6]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.FURNISH_STATUS,params[5]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.AREA_SQFOOT,params[9]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.AREA_UNIT,params[18]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PLOT_AREA,params[15]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PLOT_AREA_UNIT,params[22]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CONSTR_AREA,params[17]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CONSTR_AREA_UNIT,params[23]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.RISE,params[7]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.TXT_MIN_RENT,params[10]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.TXT_MAX_RENT,params[11]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.KEYWORD,params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PAGE,params[26]));
		}//Property Plot Sale Request Parameter all plots
		else if((params[0].equals("Plot") || params[0].equals("all plots")) && params[1].equals("Sale")){ // && !params[2].equals("Farm House(Plot)")
			System.out.println("****** Plot sale *****");
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_SUBTYPE,params[25]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME,params[19]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME2,params[20]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME3,params[21]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CHK_ZONE,params[24]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.ST_PURPOSE,params[8]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PLOT_AREA,params[15]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.AREA_UNIT,params[18]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.TXT_MIN_PRICE,params[12]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.TXT_MAX_PRICE,params[13]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.KEYWORD,params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PAGE,params[26]));
		}//Property Plot Rent Request Parameter
		else if((params[0].equals("Plot") || params[0].equals("all plots")) && params[1].equals("Rent") ){//&& !params[2].equals("Farm House(Plot)")
			System.out.println("****** Plot Rent *****");
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_TYPE,params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_OPTION,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_PROPERTY_TYPE,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_AREA1,params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_SUBTYPE,params[25]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME,params[19]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME2,params[20]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME3,params[21]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CHK_ZONE,params[24]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.ST_PURPOSE,params[8]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PLOT_AREA,params[15]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.AREA_UNIT,params[18]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.TXT_MIN_RENT,params[10]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.TXT_MAX_RENT,params[11]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.KEYWORD,params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PAGE,params[26]));
		}//Farm House(Bunglow) Rent
		else if(params[2].equals("Farm House(Bunglow)") && params[1].equals("Rent")){
			System.out.println("****** Farm house bunglow Rent *****");
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_TYPE, params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_OPTION, params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_PROPERTY_TYPE, params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_AREA1, params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_SUBTYPE, params[25]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME, params[19]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME2, params[20]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME3, params[21]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CHK_ZONE, params[24]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.ST_PURPOSE, params[8]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.MIN_PLOT_AREA, params[15]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PLOT_AREA_UNIT, params[22]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CONSTR_AREA, params[17]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.MIN_RENT, params[10]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.MAX_RENT, params[11]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.KEYWORD, params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PAGE, params[26]));
		}//Farm house(bunglow) for Sale
		else if(params[2].equals("Farm House(Bunglow)") && params[1].equals("Sale")){
			System.out.println("****** Farm house bunglow sale *****");
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_TYPE, params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_OPTION, params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_PROPERTY_TYPE, params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_AREA1, params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_SUBTYPE, params[4]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME, params[19]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME2, params[20]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME3, params[21]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CHK_ZONE, params[24]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.ST_PURPOSE, params[8]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.MIN_PLOT_AREA, params[15]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PLOT_AREA_UNIT, params[22]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CONSTR_AREA, params[17]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.MIN_PRICE, params[12]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.MAX_PRICE, params[13]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.KEYWORD, params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PAGE, params[26]));
		}//Farm house(bunglow) for Rent
		/*else if(params[2].equals("Farm House(Bunglow)") && params[1].equals("Rent")){
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_TYPE, params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_OPTION, params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_PROPERTY_TYPE, params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_AREA1, params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_SUBTYPE, params[4]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME, params[19]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME2, params[20]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME3, params[21]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CHK_ZONE, params[24]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.ST_PURPOSE, params[8]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.MIN_PLOT_AREA, params[15]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PLOT_AREA_UNIT, params[22]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CONSTR_AREA, params[17]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.MIN_RENT, params[10]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.MAX_RENT, params[11]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.KEYWORD, params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PAGE, params[26]));
		}//Farm House(Plot) for Sale
		else if(params[2].equals("Farm House(Plot)") && params[1].equals("Sale")){
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_TYPE, params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_OPTION, params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_PROPERTY_TYPE, params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_AREA1, params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_SUBTYPE, params[4]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME, params[19]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME2, params[20]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME3, params[21]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CHK_ZONE, params[24]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.ST_PURPOSE, params[8]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.MIN_PLOT_AREA, params[15]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PLOT_AREA_UNIT, params[22]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CONSTR_AREA, params[17]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.MIN_PRICE, params[12]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.MAX_PRICE, params[13]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.KEYWORD, params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PAGE, params[26]));
		}//Farm House(Plot) for Rent
		else {
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_TYPE, params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_OPTION, params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_PROPERTY_TYPE, params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_AREA1, params[3]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PROPERTY_SUBTYPE, params[4]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME, params[19]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME2, params[20]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CMB_TP_SCHEME3, params[21]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CHK_ZONE, params[24]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.ST_PURPOSE, params[8]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.MIN_PLOT_AREA, params[15]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PLOT_AREA_UNIT, params[22]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.CONSTR_AREA, params[17]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.MIN_RENT, params[10]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.MAX_RENT, params[11]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.KEYWORD, params[14]));
			nameValuePairs.add(new BasicNameValuePair(Constant.SearchRequirement.PAGE, params[26]));
		}*/
		return nameValuePairs;
	}
}


