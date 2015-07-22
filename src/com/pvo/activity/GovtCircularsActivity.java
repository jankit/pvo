package com.pvo.activity;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pvo.prototype.PVOActivity;
import com.pvo.util.Constant;

public class GovtCircularsActivity extends PVOActivity{
	private WebView govtCircularWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(RevenueRecordActivity.this));
		setContentView(R.layout.activity_dashboard_govtcircular);
		govtCircularWebView = (WebView) findViewById(R.id.govtCircularWebView);
		govtCircularWebView.getSettings().setJavaScriptEnabled(true);
		govtCircularWebView.setWebViewClient(new WebViewClient() {
			 public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
				 handler.proceed() ;
			 }
		});
		System.out.println("Revenue record URL===> "+Constant.GovtCirculars.URL);
		govtCircularWebView.loadUrl(Constant.GovtCirculars.URL);
		
	}
	@Override
	public void processResponse(Object response) {}
}
