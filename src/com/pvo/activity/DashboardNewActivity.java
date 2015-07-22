package com.pvo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

public class DashboardNewActivity extends Activity{
	
	private LinearLayout llProject;
	private LinearLayout llRealestetNews;
	private LinearLayout llTpDpVillage;
	private LinearLayout llJantri;
	private LinearLayout llRevenueRec;
	private LinearLayout llLoanCalc;
	private LinearLayout llUnitCalc;
	private LinearLayout llCircularGov;
	private LinearLayout llLogin;
	private LinearLayout llHome;
	private UserSessionManager userSessionManager; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard_new);
		userSessionManager = new UserSessionManager(getApplicationContext());
		
		//Redirect to project screen
		llProject = (LinearLayout) findViewById(R.id.llProject);
		llProject.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),DashboardProjectActivity.class);
				intent.putExtra("Project", "Project");
				startActivity(intent);
			}
		});
		
		llRealestetNews = (LinearLayout) findViewById(R.id.llRealestetNews);
		
		//open Jantri
		llJantri = (LinearLayout) findViewById(R.id.llJantri);
		llJantri.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getApplicationContext(), JantriActivity.class));
			}
		});
		
		//Revenue record
		llRevenueRec = (LinearLayout) findViewById(R.id.llRevenueRec);
		llRevenueRec.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getApplicationContext(), RevenueRecordActivity.class));
			}
		});
		
		//Redirect to loan calulator web view
		llLoanCalc = (LinearLayout) findViewById(R.id.llLoanCalc);
		llLoanCalc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getApplicationContext(), LoanCalculatorActivity.class));
			}
		});
		
		//REdirect to area unit calculator
		llUnitCalc = (LinearLayout) findViewById(R.id.llUnitCalc);
		llUnitCalc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), AreaUnitCalculatorActivity.class));
			}
		});
		
		//Redirect to govt. circular
		llCircularGov = (LinearLayout) findViewById(R.id.llCircularGov);
		llCircularGov.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), GovtCircularsActivity.class));
			}
		});
		
		//Open login screen
		llLogin = (LinearLayout) findViewById(R.id.llLogin);
		llLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), LoginActivity.class));
			}
		});
		
		//Redirect to main home(Notification)
		llHome = (LinearLayout) findViewById(R.id.llHome);
		if(userSessionManager.isUserLoggedIn()) {
			llLogin.setVisibility(View.GONE);
			llHome.setVisibility(View.VISIBLE);
		} else {
			llHome.setVisibility(View.GONE);
		}	
		llHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mainFrgmentIntent = new Intent(getApplicationContext(), MainFragmentActivity.class);
				mainFrgmentIntent.putExtra(Constant.Login.LOGIN_FIREST,"false");
				startActivity(mainFrgmentIntent);
			}
		});
		
		//open TP screen
		llTpDpVillage = (LinearLayout) findViewById(R.id.llTpDpVillage);
		llTpDpVillage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), TpDpVillageMapActivity.class));
			}
		});
	}

	//on back press close the application
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
	    intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		System.exit(0);
	}
}
