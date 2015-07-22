package com.pvo.activity;

import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pvo.prototype.PVOActivity;
import com.pvo.util.Constant;

public class RevenueRecordActivity extends PVOActivity{
	private WebView revenueRecWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(RevenueRecordActivity.this));
		setContentView(R.layout.activity_dashboard_revenuerecord);
		revenueRecWebView = (WebView) findViewById(R.id.revenueRecWebView);
		revenueRecWebView.getSettings().setJavaScriptEnabled(true);
		revenueRecWebView.setWebViewClient(new WebViewClient() {
			 public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
				 handler.proceed() ;
			 }
		});
		System.out.println("Revenue record URL===> "+Constant.RevenueRecord.URL);
		revenueRecWebView.loadUrl(Constant.RevenueRecord.URL);
		
	}
	@Override
	public void processResponse(Object response) {}
}
