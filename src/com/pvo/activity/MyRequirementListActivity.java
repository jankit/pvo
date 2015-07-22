package com.pvo.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import z.com.pvo.util.ProjectUtility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;
import com.pvo.custom.adapter.MyRequirementArrayAdaptor;
import com.pvo.custom.adapter.MyRequirementItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.MyRequirementService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;


//This is Used For display the My Requirement in this pass the Broker Id into the Web Service
public class MyRequirementListActivity extends PVOFragment {
	
	private Boolean isPrint = true;
	private String TAG = "MyRequirementListActivity";
	
	
    private LinearLayout progresLayout;
    private PullToRefreshListView myReqlistView;
    private TextView addMyRequirementButton;
    private LinearLayout myReqRefineSearchLayout;
    private Bundle filterMyReqBundel;
    private MyRequirementService myRequirementService;
    private UserSessionManager userSessionManager;
    private boolean flag_loading;
    private int pageCount = 1;
    private List<MyRequirementItem> requirementItems = new ArrayList<MyRequirementItem>();
    private MyRequirementArrayAdaptor myRequirementArrayAdaptor;
    private TextView tvMyReqTotalRecord;
    private Boolean refresh = false;
	
	//Set the layout content view
	public MyRequirementListActivity() {
		setContentView(R.layout.activity_myrequirement_list);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		filterMyReqBundel 			= getArguments();
		myRequirementService 		= new MyRequirementService();
		userSessionManager 			= new UserSessionManager(getActivity().getApplicationContext());
		myReqlistView 				= (PullToRefreshListView) findViewById(R.id.myReqListView);
		addMyRequirementButton 		= (TextView)findViewById(R.id.myReqAddTextView);
		progresLayout 				= (LinearLayout) findViewById(R.id.progresLayout);
		tvMyReqTotalRecord			= (TextView) findViewById(R.id.tvMyReqTotalRecord);
		
		//Open Add new requirement Screen
		addMyRequirementButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyRequirementAddActivity addMyRequirementActivity = new MyRequirementAddActivity();
				((MainFragmentActivity)getActivity()).redirectScreen(addMyRequirementActivity);
			}
		});
		
		//open filter Popup window
		myReqRefineSearchLayout = (LinearLayout) findViewById(R.id.myReqRefineSearchLayout);
		myReqRefineSearchLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FilterPropertyActivity filterPropertyActivity = new FilterPropertyActivity();
				((MainFragmentActivity)getActivity()).redirectScreen(filterPropertyActivity);
			}
		});
	
		//Call getMyRequirement method to get list of requirement list
		getMyRequirementRecords(pageCount);
		
		//on Listview scroll increase the page counter
		myReqlistView.setOnScrollListener(new OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if(firstVisibleItem != 0) {
					if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0) {
						if(flag_loading == false) {
							flag_loading = true;
		                    getMyRequirementRecords(++pageCount);
		                }
		            }
				}
			}
		});
		
		myReqlistView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            	pageCount = 1;
            	refresh = true;
            	getMyRequirementRecords(pageCount);
            }
        });
	}
	
	
	//Call web service and get records for given page number
	private void getMyRequirementRecords(int pageNumber) {
		//call when used return form refine search /filter 
		if(filterMyReqBundel.getString("Type").equals("Filter") && filterMyReqBundel.getString("Type") != null){
			new WebserviceClient(MyRequirementListActivity.this, myRequirementService,progresLayout).execute(
					userSessionManager.getSessionValue(Constant.Login.USER_ID),
					filterMyReqBundel.getString(Constant.FilterList.PROPERTY_TYPE),
					filterMyReqBundel.getString(Constant.FilterList.PURPOSE),
					filterMyReqBundel.getString(Constant.FilterList.LOCATION),
					filterMyReqBundel.getString(Constant.FilterList.TXT_KEYWORD),
					String.valueOf(pageNumber));
		}//call when user tap on my requirement from menu 
		else if(filterMyReqBundel.getString("Type").equals("Search") && filterMyReqBundel.getString("Type") != null){
			
			new WebserviceClient(MyRequirementListActivity.this, myRequirementService,progresLayout).execute(userSessionManager.getSessionValue(Constant.Login.USER_ID),String.valueOf(pageNumber));
			
		} else if(filterMyReqBundel.getString("Type").equals("Requirement")) {//Call from agent detail page 
			
			new WebserviceClient(MyRequirementListActivity.this, myRequirementService,progresLayout).execute(filterMyReqBundel.getString(Constant.FindAgent.BROKER_ID), String.valueOf(pageNumber));
			
		} 
		
		else { //call when add new requirement button click
			new WebserviceClient(MyRequirementListActivity.this, myRequirementService,progresLayout).execute(userSessionManager.getSessionValue(Constant.Login.USER_ID), String.valueOf(pageNumber));
		}
	}

	@Override
	public void processResponse(Object res) {
		JSONArray Jarray = (JSONArray) res;
		try {
				if (Jarray != null && !((JSONObject)Jarray.get(0)).has(Constant.MyRequirement.API_STATUS)){
					tvMyReqTotalRecord.setText("Total Record: "+Jarray.getJSONObject(0).getString("no_record"));
					if(refresh && pageCount == 1) {
						refresh = false;
						requirementItems.clear();
					}
					
					for (int i = 1; i < Jarray.length(); i++) {
						JSONObject jsonObj = Jarray.getJSONObject(i);
						
						ProjectUtility.sys(isPrint, TAG, "Requirement Id--> "+jsonObj.getString(Constant.MyRequirement.REQUIREMENT_ID));
						
						MyRequirementItem reqItem = new MyRequirementItem();
						
						reqItem.setRequirementid(jsonObj.getString(Constant.MyRequirement.REQUIREMENT_ID));
						reqItem.setPropertytype(jsonObj.getString(Constant.MyRequirement.PROPERTY_TYPE));
						reqItem.setPropertysubtype(jsonObj.getString(Constant.MyRequirement.PROPERTY_SUB_TYPE));
						reqItem.setPurpose(jsonObj.getString(Constant.MyRequirement.PURPOSE));
						reqItem.setMinfloor(jsonObj.getString(Constant.MyRequirement.MIN_FLOOR));
						reqItem.setMaxfloor(jsonObj.getString(Constant.MyRequirement.MAX_FLOOR));
						reqItem.setMinsqfoot(jsonObj.getString(Constant.MyRequirement.MIN_SQFOOT));
						reqItem.setMaxsqfoot(jsonObj.getString(Constant.MyRequirement.MAX_SQFOOT));
						reqItem.setRise(jsonObj.getString(Constant.MyRequirement.RISE));
						reqItem.setMinplotarea(jsonObj.getString(Constant.MyRequirement.MIN_PLOT_AREA));
						reqItem.setMaxplotarea(jsonObj.getString(Constant.MyRequirement.MAX_PLOT_AREA));
						reqItem.setMaxconstructionarea(jsonObj.getString(Constant.MyRequirement.MAX_CONSTRUCTION_AREA));
						reqItem.setMinconstructionarea(jsonObj.getString(Constant.MyRequirement.MIN_CONSTRUCTION_AREA));
						reqItem.setStpurpose(jsonObj.getString(Constant.MyRequirement.ST_PURPOSE));
						reqItem.setLocation1(jsonObj.getString(Constant.MyRequirement.LOCATION1));
						reqItem.setLocation2(jsonObj.getString(Constant.MyRequirement.LOCATION2));
						reqItem.setStstatus(jsonObj.getString(Constant.MyRequirement.ST_STATUS));
						reqItem.setMinprice(jsonObj.getString(Constant.MyRequirement.MIN_PRICE));
						reqItem.setMaxprice(jsonObj.getString(Constant.MyRequirement.MAX_PRICE));
						reqItem.setMinrent(jsonObj.getString(Constant.MyRequirement.MIN_RENT));
						reqItem.setMaxrent(jsonObj.getString(Constant.MyRequirement.MAX_RENT));
						reqItem.setLocation1name(jsonObj.getString(Constant.MyRequirement.LOCATION1_NAME));
						reqItem.setLocation2name(jsonObj.getString(Constant.MyRequirement.LOCATION2_NAME));
						reqItem.setLocation3name(jsonObj.getString(Constant.MyRequirement.LOCATION3_NAME));
						reqItem.setDtadded(jsonObj.getString(Constant.MyRequirement.DT_ADDED));
						reqItem.setDtupdated(jsonObj.getString(Constant.MyRequirement.DT_UPDATE));
						reqItem.setMinbed(jsonObj.getString(Constant.MyRequirement.MIN_BED));
						reqItem.setMaxbed(jsonObj.getString(Constant.MyRequirement.MAX_BED));
						reqItem.setLogoencoded(jsonObj.getString(Constant.MyRequirement.LOGO_ENCODED));
						reqItem.setPhotoencoded(jsonObj.getString(Constant.MyRequirement.PHOTO_ENCODED));
						reqItem.setStateid(jsonObj.getString(Constant.MyRequirement.STATE_ID));
						reqItem.setCityid(jsonObj.getString(Constant.MyRequirement.CITY_ID));
						reqItem.setDistrictid(jsonObj.getString(Constant.MyRequirement.DISTRICT_ID));
						reqItem.setTp(jsonObj.getString(Constant.MyRequirement.TP));
						reqItem.setHint(jsonObj.getString(Constant.MyRequirement.HINT));
						reqItem.setKeyword(jsonObj.getString(Constant.MyRequirement.KEYWORD));
						reqItem.setZone(jsonObj.getString(Constant.MyRequirement.ZONE));
						reqItem.setAlllocationsname(jsonObj.getString(Constant.MyRequirement.ALL_LOCATION_NAME));
						reqItem.setInquirycount(jsonObj.getString(Constant.MyRequirement.INQUIRY_COUNT));
						requirementItems.add(reqItem);
					}
					
					if(myRequirementArrayAdaptor == null) {
						myRequirementArrayAdaptor = new MyRequirementArrayAdaptor(getActivity(), R.id.myReqListView, requirementItems);
						myReqlistView.setAdapter(myRequirementArrayAdaptor);
					} else {
						myRequirementArrayAdaptor.notifyDataSetChanged();
						flag_loading = false;
					}
			} else {
				Toast.makeText(getActivity(),((JSONObject)Jarray.get(0)).getString(Constant.MyRequirement.API_MESSAGE),Toast.LENGTH_LONG).show();
			}
			myReqlistView.onRefreshComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    if(filterMyReqBundel.getString("Type").equals("Requirement"))
			   this.getActivity().getActionBar().setTitle("Requirements of "+filterMyReqBundel.getString(Constant.PreferredBrokers.FIRST_NAME));
		   else
			   this.getActivity().getActionBar().setTitle("My Requirements");
	}
}
