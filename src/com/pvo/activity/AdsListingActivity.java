package com.pvo.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.pvo.components.SpinnerItem;
import com.pvo.custom.adapter.AdsListingArrayAdaptor;
import com.pvo.custom.adapter.AdsListingItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.AdsListingService;
import com.pvo.util.Constant;
import com.pvo.util.JSONUtils;


//This is used to display the list of Public property
public class AdsListingActivity extends PVOFragment{
	
	private ListView adsListView;
	private AdsListingService adsListingService;
	private List<AdsListingItem> adsListingItems = new ArrayList<AdsListingItem>();
	private AdsListingArrayAdaptor adsListingArrayAdaptor;
	private LinearLayout adsRefineLayout;
	public static List<String> builderIdList = new ArrayList<String>();
	private Bundle filterProjectBundel;
	
	//Set the layout content View 
	public AdsListingActivity() {
		setContentView(R.layout.activity_ads_list);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		adsListingService 	= new AdsListingService();
		filterProjectBundel = getArguments();
		adsListView 		= (ListView) findViewById(R.id.adsListView);
		
		//Redirect to search project from listing
		adsRefineLayout = (LinearLayout) findViewById(R.id.adsAaaNewPropLayout);
		if(filterProjectBundel.getString("Type").equals("Project"))
			adsRefineLayout.setVisibility(View.GONE);
		
		adsRefineLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FilterProjectActivity filterProjectActivity = new FilterProjectActivity();
				((MainFragmentActivity)getActivity()).redirectScreen(filterProjectActivity);
			}
		});
		
		if (filterProjectBundel.getString("Type").equals("Filter")) {
			new WebserviceClient(AdsListingActivity.this, adsListingService).execute(
					filterProjectBundel.getString(Constant.AdsListing.BUILDER_ID),//BUILDER_ID
					filterProjectBundel.getString(Constant.AdsListing.BUILDER_NAME),//BUILDER_NAME
					filterProjectBundel.getString(Constant.AdsListing.AREA_NAME),//CMB_AREA1
					filterProjectBundel.getString("KEYWORD"),//TXT_KEYWORD
					String.valueOf(SpinnerItem.getProjectBudgetList().get(filterProjectBundel.getString("PRICERANGE")))//PRICE_RANGE
					);
		} else if(filterProjectBundel.getString("Type").equals("Search") || filterProjectBundel.getString("Type").equals("Project")) {
			new WebserviceClient(AdsListingActivity.this, adsListingService).execute();
		}
	 }

	//MyProperty List web service Response
	@Override
	public void processResponse(Object response) {
		JSONArray jsonArry = (JSONArray)response;
		try {
			if (response != null && !((JSONObject)jsonArry.get(0)).has(Constant.MyProperty.API_STATUS)) {
				for (int i = 0; i < jsonArry.length(); i++) {
					JSONObject jsonObj = jsonArry.getJSONObject(i);
					AdsListingItem adsListingItem = new AdsListingItem();
					adsListingItem.setPropertylogo(jsonObj.getString(Constant.AdsListing.PROPERTY_LOGO));
					adsListingItem.setProjectid(jsonObj.getString(Constant.AdsListing.PROJECT_ID));
					adsListingItem.setTitle(jsonObj.getString(Constant.AdsListing.TITLE));
					adsListingItem.setStartprice(jsonObj.getString(Constant.AdsListing.START_PRICE));
					adsListingItem.setEndprice(jsonObj.getString(Constant.AdsListing.END_PRICE));
					adsListingItem.setAddress(jsonObj.getString(Constant.AdsListing.ADDRESS));
					adsListingItem.setPropertytype(jsonObj.getString(Constant.AdsListing.PROPERTY_TYPE));
					adsListingItem.setBuildername(jsonObj.getString(Constant.AdsListing.BUILDER_NAME));
					adsListingItem.setContactperson(jsonObj.getString(Constant.AdsListing.CONTACT_PERSON));
					adsListingItem.setContactnumber(jsonObj.getString(Constant.AdsListing.CONTACT_NUMBER));
					adsListingItem.setDetail(jsonObj.getString(Constant.AdsListing.DETAIL));
					adsListingItem.setEmail(jsonObj.getString(Constant.AdsListing.EMAIL));
					adsListingItem.setPropertyheaderimage(jsonObj.getString(Constant.AdsListing.PROPERTY_HEADER_IMAGE));
										
					//set price pdf
					if(jsonObj.getString(Constant.AdsListing.PRICE_PDF).length() > 0 && !jsonObj.getString(Constant.AdsListing.PRICE_PDF).equals("")) 
						adsListingItem.setPricepdf(jsonObj.getString(Constant.AdsListing.PRICE_PDF));
					
					//set floor plan pdf
					if(jsonObj.getString(Constant.AdsListing.FLOOR_PLAN).length() > 0 && !jsonObj.getString(Constant.AdsListing.FLOOR_PLAN).equals("")) 
						adsListingItem.setFloorplan(jsonObj.getString(Constant.AdsListing.FLOOR_PLAN));
					
					//get the builder id and add into builder id list
					if(!filterProjectBundel.getString("Type").equals("Filter")) 
						builderIdList = JSONUtils.getList(jsonArry, Constant.AdsListing.BUILDER_ID);
					
					adsListingItems.add(adsListingItem);
				}
				if(adsListingArrayAdaptor == null) {
					adsListingArrayAdaptor = new AdsListingArrayAdaptor(getActivity(), R.id.adsListView, adsListingItems,filterProjectBundel.getString("DashboardProjectDetail"));
					adsListView.setAdapter(adsListingArrayAdaptor);
				} else {
					adsListingArrayAdaptor.notifyDataSetChanged();
				}
				
			} else{
				Toast.makeText(getActivity(),((JSONObject)jsonArry.get(0)).getString(Constant.AddProperty.API_MESSAGE), Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Set the title of the current fragment
	@Override
	public void onResume() {
		super.onResume();
		if(!filterProjectBundel.getString("Type").equals("Project"))
			getActivity().getActionBar().setTitle("Project");
	}
}
