package com.pvo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class DashboardProjectActivity extends FragmentActivity{
	
	private Intent intent;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_dashboard_project);
		intent = getIntent();
		if(intent.getStringExtra("Project").equals("Project")) {
			Bundle adsLisingBundle = new Bundle();
			adsLisingBundle.putString("Type", "Project");
			adsLisingBundle.putString("DashboardProjectDetail", "DashboardProjectDetail");
			AdsListingActivity adsListingActivity = new AdsListingActivity();
			adsListingActivity.setArguments(adsLisingBundle);
			getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, adsListingActivity).commit();
		}
	}
}
