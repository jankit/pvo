package com.pvo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pvo.util.Constant;

public class LoanCalculatorActivity extends Activity {
	
	private WebView wvLoanCalc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard_loancalc);
		wvLoanCalc = (WebView) findViewById(R.id.wvLoanCalc);
		wvLoanCalc.getSettings().setAppCacheEnabled(false);
		wvLoanCalc.getSettings().setJavaScriptEnabled(true);
		wvLoanCalc.getSettings().setBuiltInZoomControls(true);
		wvLoanCalc.setClickable(true);
		wvLoanCalc.setWebViewClient(new WebViewClient());
		wvLoanCalc.loadUrl(Constant.LoanCalculator.URL);
	}
}
