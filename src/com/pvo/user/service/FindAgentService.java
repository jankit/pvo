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


public class FindAgentService extends PVOService<JSONArray> {

	/**
	 *  return response of webservice.
	 * 
	 * @param 
	 * @param 
	 * @return
	 */
	
	@Override
	public JSONArray executeService(String... params) throws Exception {
		
		HttpResponse response;
		HttpClient httpclient = new DefaultHttpClient();
		//HttpPost httppost = new HttpPost(Constant.FindAgent.URL);
		HttpGet httpGet;	
		List<NameValuePair> nameValuePair = getNameValuePair(params);
		String queryString = Utils.getQueryString(nameValuePair);
		System.out.println("Find agent queryString = "+Constant.FindAgent.URL+"&"+queryString);//URLEncoder.encode(queryString,"utf-8"));
		httpGet = new HttpGet(Constant.FindAgent.URL+"&"+queryString.replaceAll(" ", "%20"));
		response = httpclient.execute(httpGet);
			
			/*//Add agent to prefere broker list
			if(params[0].equals("Prefere")) {
				System.out.println("Agent From Prefere broker");
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.AGENT_ID, params[1]));
				nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.ACTION, "add"));
				nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.PREF_BROKER_ID, params[2]));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				response = httpclient.execute(httppost);
			} 
			//get the list of broker for particular area from add new property 
			else if(params[0].equals("AddNewProperty")) {
				System.out.println("get the broker when add new property");
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.SEARCH_STRING,params[1]));
				nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.CMBFIELDS,"area"));
				nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.AGENT_ID,params[2]));//login user id
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				response = httpclient.execute(httppost);
			} else {
				System.out.println("Find agent[Service]");
				System.out.println("Page numbe ==> "+params[3]);
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.SEARCH_STRING, params[0]));
				nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.CMBFIELDS,params[1]));
				nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.AGENT_ID,params[2]));
				nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.PAGE,params[3]));
				System.out.println("Search string ---> "+nameValuePairs.get(0));
				System.out.println("CMB field ---> "+nameValuePairs.get(1));
				System.out.println("Agent id ---> "+nameValuePairs.get(2));
				System.out.println("Page ---> "+nameValuePairs.get(3));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				response = httpclient.execute(httppost);
				 
				System.out.println("Find Agent Query String ==> "+Constant.FindAgent.URL+"&"+Utils.getQueryString(nameValuePairs));
			}*/
			
			
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
			
			/*String queryString = Utils.getQueryString(nameValuePairs);
			System.out.println("Find Agent Query String ==> "+Constant.FindAgent.URL+"&"+queryString);*/
			//System.out.println("Response===> "+responseJsonStr);
			return new JSONArray(responseJsonStr.toString());
	}
	
	private List<NameValuePair> getNameValuePair(String[] params){
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		//Flat For Rent Request Parameter 
		
		//Add agent to prefere broker list
		if(params[0].equals("Prefere")) {
			System.out.println("Agent From Prefere broker");
			//List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.AGENT_ID, params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.ACTION, "add"));
			nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.PREF_BROKER_ID, params[2]));
			//httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			//response = httpclient.execute(httppost);
		} 
		//get the list of broker for particular area from add new property 
		else if(params[0].equals("AddNewProperty")) {
			System.out.println("get the broker when add new property");
			//List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.SEARCH_STRING,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.CMBFIELDS,"area"));
			nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.AGENT_ID,params[2]));//login user id
			//httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			//response = httpclient.execute(httppost);
		} else {
			System.out.println("Find agent[Service]");
			System.out.println("Page numbe ==> "+params[3]);
			//List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.SEARCH_STRING, params[0]));
			nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.CMBFIELDS,params[1]));
			nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.AGENT_ID,params[2]));
			nameValuePairs.add(new BasicNameValuePair(Constant.FindAgent.PAGE,params[3]));
			System.out.println("Search string ---> "+nameValuePairs.get(0));
			System.out.println("CMB field ---> "+nameValuePairs.get(1));
			System.out.println("Agent id ---> "+nameValuePairs.get(2));
			System.out.println("Page ---> "+nameValuePairs.get(3));
			//httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			//response = httpclient.execute(httppost);
			//System.out.println("Find Agent Query String ==> "+Constant.FindAgent.URL+"&"+Utils.getQueryString(nameValuePairs));
		}
		
		return nameValuePairs;
		
	}
}


