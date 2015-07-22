package com.pvo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.pvo.util.Constant;

public class AreaUnitCalculatorActivity extends Activity{
	
	private WebView wvLoanCalc;
	private Button btnAraeCalcBackButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard_areaunitcalc);
		wvLoanCalc = (WebView) findViewById(R.id.wvAreaUnitCalc);
		wvLoanCalc.getSettings().setAppCacheEnabled(false);
		wvLoanCalc.getSettings().setJavaScriptEnabled(true);
		wvLoanCalc.getSettings().setBuiltInZoomControls(true);
		wvLoanCalc.setClickable(true);
		wvLoanCalc.setWebViewClient(new WebViewClient());
		wvLoanCalc.loadUrl(Constant.AreaUnitCalculator.URL);
		
		//Back button  
		btnAraeCalcBackButton = (Button) findViewById(R.id.btnAraeCalcBackButton);
		btnAraeCalcBackButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
}
