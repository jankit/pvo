package com.pvo.json.cache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class JsonParser {

	static InputStream is = null;
	static String json = "";

	// constructor
	public JsonParser() {}

	public static String getJSONFromUrl(String url) {
		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpPost = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			StatusLine statusLine = httpResponse.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
			    HttpEntity httpEntity = httpResponse.getEntity();
			    is = httpEntity.getContent();
			} else {
			    Log.e("Notice", "Failed to download file");
		        return "";
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			if(!StringUtils.startsWith(sb.toString(), "[")) {
				sb.insert(0,"[");
				sb.append("]");
			}
			is.close();
			json = sb.toString();
			
			
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
			return "";
		}
		return json;
	}
}