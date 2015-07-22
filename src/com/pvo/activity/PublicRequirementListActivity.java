package com.pvo.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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
import com.pvo.custom.adapter.PublicRequirementArrayAdaptor;
import com.pvo.custom.adapter.PublicRequirementItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.PublicRequirementService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;


/**
 * This is used to display the List of the Public requirement 
 * */
public class PublicRequirementListActivity extends PVOFragment {
	
	private TextView tvPublicReqTotalRecord;
    private LinearLayout publicRequirementProgresLayout;
    private PullToRefreshListView publicReqlistView;
    private PublicRequirementService publicRequirementService;
    private LinearLayout publicReqRefineSearch;
    private Bundle filterPublicReqBundel;
	private boolean flag_loading;
	private int pageCount = 1;
	private List<PublicRequirementItem> publicRequirementItems = new ArrayList<PublicRequirementItem>();
	private PublicRequirementArrayAdaptor publicRequirementArrayAdaptor;
	private UserSessionManager userSessionManager;
	private Boolean refresh = false;

	/** set the layout content View **/
	public PublicRequirementListActivity() {
		setContentView(R.layout.activity_publicrequirement_list);
	}
	/** END **/

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		filterPublicReqBundel = getArguments();
		publicRequirementService = new PublicRequirementService();
		userSessionManager = new UserSessionManager(getActivity());
		publicReqlistView = (PullToRefreshListView) findViewById(R.id.publicReqListView);
		publicRequirementProgresLayout = (LinearLayout) findViewById(R.id.publicRequirementProgresLayout);
		tvPublicReqTotalRecord = (TextView) findViewById(R.id.tvPublicReqTotalRecord);
		
		/** REdirect to the filter public requirement **/
		publicReqRefineSearch = (LinearLayout) findViewById(R.id.publicReqRefineSearch);
		publicReqRefineSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FilterPropertyActivity filterPropertyActivity = new FilterPropertyActivity();
				((MainFragmentActivity)getActivity()).redirectScreen(filterPropertyActivity);
			}
		});
		/** END **/
		
		/** get the list of public Requirement  **/
		getPublicRequirementRecords(pageCount);
		/** END **/
		
		/** on list view scroll increase the page number  and pass into the web service **/
		publicReqlistView.setOnScrollListener(new OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if(firstVisibleItem != 0) {	
					if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0) {
		                if(flag_loading == false) {
		                    flag_loading = true;
		                    getPublicRequirementRecords(++pageCount);
		                }
		            }
				}
			}
		});
		/** END **/
		
		publicReqlistView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            	pageCount = 1;
            	refresh = true;
            	getPublicRequirementRecords(pageCount);
            }
        });
	}
	
	//Call web service and get records for given page number
	private void getPublicRequirementRecords(int pageNumber) {
		if(filterPublicReqBundel.getString("Type").equals("Filter")){
			new WebserviceClient(PublicRequirementListActivity.this, publicRequirementService, publicRequirementProgresLayout).execute(
			        userSessionManager.getSessionValue(Constant.Login.USER_ID),
			        filterPublicReqBundel.getString(Constant.FilterList.PROPERTY_TYPE),
					filterPublicReqBundel.getString(Constant.FilterList.PURPOSE),
					filterPublicReqBundel.getString(Constant.FilterList.LOCATION),
					filterPublicReqBundel.getString(Constant.FilterList.TXT_KEYWORD),
					String.valueOf(pageNumber));
		} else if(filterPublicReqBundel.getString("Type").equals("Search")) {
			new WebserviceClient(PublicRequirementListActivity.this, publicRequirementService, publicRequirementProgresLayout).execute(String.valueOf(pageNumber));
		}
	}
	
	@Override
	public void processResponse(Object response) {
		JSONArray jsonArry = (JSONArray) response;
		try {
			if (jsonArry != null && ! ((JSONObject)jsonArry.get(0)).has(Constant.PublicRequirement.API_STATUS)) {
				tvPublicReqTotalRecord.setText("Total Record:- "+jsonArry.getJSONObject(0).getString("no_record"));
				if(refresh && pageCount == 1) {
					refresh = false;
					publicRequirementItems.clear();
				}
				for (int i = 1; i < jsonArry.length(); i++) {
					JSONObject jsonObj = jsonArry.getJSONObject(i);
					PublicRequirementItem publicRequirementItem = new PublicRequirementItem();
					publicRequirementItem.setRequirementid(jsonObj.getString(Constant.PublicRequirement.REQUIREMENT_ID));
					publicRequirementItem.setPropertytype(jsonObj.getString(Constant.PublicRequirement.PROPERTY_TYPE));
					publicRequirementItem.setPropertysubtype(jsonObj.getString(Constant.PublicRequirement.PROPERTY_SUB_TYPE));
					publicRequirementItem.setLocation1(jsonObj.getString(Constant.PublicRequirement.LOCATION1));
					publicRequirementItem.setLocation2(jsonObj.getString(Constant.PublicRequirement.LOCATION2));
					publicRequirementItem.setLocation3(jsonObj.getString(Constant.PublicRequirement.LOCATION3));
					publicRequirementItem.setPurpose(jsonObj.getString(Constant.PublicRequirement.PURPOSE));
					publicRequirementItem.setMinrent(jsonObj.getString(Constant.PublicRequirement.MIN_RENT));
					publicRequirementItem.setMaxrent(jsonObj.getString(Constant.PublicRequirement.MAX_RENT));
					publicRequirementItem.setMinprice(jsonObj.getString(Constant.PublicRequirement.MIN_PRICE));
					publicRequirementItem.setMaxprice(jsonObj.getString(Constant.PublicRequirement.MAX_PRICE));
					publicRequirementItem.setMinbed(jsonObj.getString(Constant.PublicRequirement.MIN_BED));
					publicRequirementItem.setMaxbed(jsonObj.getString(Constant.PublicRequirement.MAX_BED));
					publicRequirementItem.setMinfloor(jsonObj.getString(Constant.PublicRequirement.MIN_FLOOR));
					publicRequirementItem.setMaxfloor(jsonObj.getString(Constant.PublicRequirement.MAX_FLOOR));
					publicRequirementItem.setFurnish(jsonObj.getInt(Constant.PublicRequirement.FURNISH));
					publicRequirementItem.setRise(jsonObj.getString(Constant.PublicRequirement.RISE));
					publicRequirementItem.setMinsqfoot(jsonObj.getString(Constant.PublicRequirement.MIN_SQFOOT));
					publicRequirementItem.setMaxsqfoot(jsonObj.getString(Constant.PublicRequirement.MAX_SQFOOT));
					publicRequirementItem.setDtadded(jsonObj.getString(Constant.PublicRequirement.DT_ADDED));
					publicRequirementItem.setDtupdated(jsonObj.getString(Constant.PublicRequirement.DT_UPDATE));
					publicRequirementItem.setStstatus(jsonObj.getString(Constant.PublicRequirement.ST_STATUS));
					publicRequirementItem.setStpurpose(jsonObj.getString(Constant.PublicRequirement.ST_PURPOSE));
					publicRequirementItem.setLocation1name(jsonObj.getString(Constant.PublicRequirement.LOCATION1_NAME));
					publicRequirementItem.setLocation2name(jsonObj.getString(Constant.PublicRequirement.LOCATION2_NAME));
					publicRequirementItem.setLocation3name(jsonObj.getString(Constant.PublicRequirement.LOCATION3_NAME));
					publicRequirementItem.setLogoencoded(jsonObj.getString(Constant.PublicRequirement.LOGO_ENCODED));
					publicRequirementItem.setPhotoencoded(jsonObj.getString(Constant.PublicRequirement.PHOTO_ENCODED));
					publicRequirementItem.setFirstname(jsonObj.getString(Constant.PublicRequirement.FIRST_NAEE));
					publicRequirementItem.setLastname(jsonObj.getString(Constant.PublicRequirement.LAST_NAME));
					publicRequirementItem.setPhonem(jsonObj.getString(Constant.PublicRequirement.PHONE_M));
					publicRequirementItem.setMinplotarea(jsonObj.getString(Constant.PublicRequirement.MIN_PLOT_AREA));
					publicRequirementItem.setMaxplotarea(jsonObj.getString(Constant.PublicRequirement.MAX_PLOT_AREA));
					publicRequirementItem.setMaxconstrarea(jsonObj.getString(Constant.PublicRequirement.MAX_CONSTRUCTION_AREA));
					publicRequirementItem.setMinconstrarea(jsonObj.getString(Constant.PublicRequirement.MIN_CONSTRUCTION_AREA));
					publicRequirementItem.setAlllocationsname(jsonObj.getString(Constant.PublicRequirement.ALL_LOCATION_NAME));
					publicRequirementItem.setPage(String.valueOf(pageCount));
					publicRequirementItems.add(publicRequirementItem);
				}
				if(publicRequirementArrayAdaptor == null) {
					publicRequirementArrayAdaptor = new PublicRequirementArrayAdaptor(getActivity(), R.id.publicReqListView, publicRequirementItems);
					publicReqlistView.setAdapter(publicRequirementArrayAdaptor);
				} else {
					publicRequirementArrayAdaptor.notifyDataSetChanged();
					flag_loading = false;
				}
			} else {
				Toast.makeText(getActivity(), ((JSONObject)jsonArry.get(0)).getString(Constant.PublicRequirement.API_MESSAGE), Toast.LENGTH_LONG).show();
			}
			publicReqlistView.onRefreshComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Set the title according to fragment **/
	@Override
	public void onResume() {
		super.onResume();
		this.getActivity().getActionBar().setTitle("Public Requirement");
	}
	/** END **/
}
