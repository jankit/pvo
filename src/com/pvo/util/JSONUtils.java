package com.pvo.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JSONUtils {
	/**
	 * Get list of value for given key from given JSONArray
	 * 
	 * @param arraySrc
	 * @param key
	 * @return
	 * @throws JSONException
	 */
	public static List<String> getList(JSONArray arraySrc, String key) throws JSONException {
		List<String> resultList = new ArrayList<String>();
		
		for(int i=0 ; i < arraySrc.length(); i++) {
		    resultList.add(((JSONObject) arraySrc.get(i)).getString(key));
		}
		return resultList;
	}
	
	/**
	 * Remove element from given json array at given position.
	 * 
	 * @param jsonArray
	 * @param position
	 * @return
	 * @throws JSONException 
	 */
	public static JSONArray removeFromJSONArray(JSONArray jsonArray,int position) throws JSONException {
		
		JSONArray resultJsonArray = new JSONArray();
		
		for(int i=0; i < jsonArray.length(); i++) {
			if(position != i)
				resultJsonArray.put(jsonArray.get(i));
		}
		
		return resultJsonArray;
	}
	
	/**
	 * Get list of value for given key from given JSONArray
	 * 
	 * @param arraySrc
	 * @param key
	 * @return
	 * @throws JSONException
	 */
	public static List<String> getListFromFirstPos(JSONArray arraySrc, String key) throws JSONException {
		List<String> resultList = new ArrayList<String>();
		
		for(int i=1 ; i < arraySrc.length(); i++) {
		    resultList.add(((JSONObject) arraySrc.get(i)).getString(key));
		}
		return resultList;
	}
}
