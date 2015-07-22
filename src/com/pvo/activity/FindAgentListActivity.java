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
import com.pvo.custom.adapter.FindAgentArrayAdaptor;
import com.pvo.custom.adapter.FindAgentItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.FindAgentService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;


//Display the List Of Agent Which is match the search criteriain this pass the search string, criteria and BrokerId and area deals 

public class FindAgentListActivity extends PVOFragment {

	private PullToRefreshListView findAgentlistView;
    private LinearLayout findAgentRefineSearchLayout;
    private LinearLayout findAgentAddNewPropLayout;
    private FindAgentService findAgentService;
    private UserSessionManager userSessionManager;
    private Bundle intent;
    private List<FindAgentItem> findAgentItems = new ArrayList<FindAgentItem>();
    private FindAgentArrayAdaptor findAgentArrayAdaptor;
    private TextView tvAgentTotalRecord;
    
    private boolean flag_loading;
    private int pageCount = 1;
    private boolean refresh = false;
    private int scrollState = 0;
	
	//set Layout Content View
	public FindAgentListActivity() {
		setContentView(R.layout.activity_findagent_list);
	}
	
	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		intent=getArguments();
		findAgentService=new FindAgentService();
		userSessionManager = new UserSessionManager(getActivity().getApplicationContext());
		findAgentlistView = (PullToRefreshListView) findViewById(R.id.findAgentListListView);
		tvAgentTotalRecord = (TextView) findViewById(R.id.tvAgentTotalRecord);
		
		//Change the criteria of search
		findAgentRefineSearchLayout = (LinearLayout) findViewById(R.id.findAgentRefineSearchLayout);
		findAgentRefineSearchLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});

		//redirect to add new property screen
		findAgentAddNewPropLayout = (LinearLayout) findViewById(R.id.findAgentAddNewPropLayout);
		findAgentAddNewPropLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyPropertyAddActivity addMyPropertyActivity = new MyPropertyAddActivity();
				((MainFragmentActivity)getActivity()).redirectScreen(addMyPropertyActivity);
			}
		});
		getAgentRecord(pageCount);
		//on List view scroll change increase the page counter
		findAgentlistView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int aScrollState) {
				scrollState = aScrollState;
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if(firstVisibleItem != 0) {
					if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0) {
							if(flag_loading == false) {
				                flag_loading = true;
				                getAgentRecord(++pageCount);
				            }
			        }
				}
			}
		});
		
		findAgentlistView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            	pageCount = 1;
            	refresh = true;
            	 getAgentRecord(pageCount);
            }
        });
		
	}
    
	@Override
	public void processResponse(Object response) {
		JSONArray jsonArry = (JSONArray) response;
		System.out.println("Json response ==>"+jsonArry);
		try {
			if (jsonArry != null && !((JSONObject) jsonArry.get(0)).has(Constant.FindAgent.API_STATUS) ) {
				tvAgentTotalRecord.setText("Total Record:-"+jsonArry.getJSONObject(0).getString("no_record"));
				//System.out.println("==> Process response "+jsonArry);
				if(refresh && pageCount == 1) {
					refresh = true;
					findAgentItems.clear();
				}
				for (int i = 1; i < jsonArry.length(); i++) {
					JSONObject jsonObj = jsonArry.getJSONObject(i);
					FindAgentItem findAgentItem  = new FindAgentItem();
					findAgentItem.setBrokerid((jsonObj.getString(Constant.FindAgent.BROKER_ID)));
					findAgentItem.setFirstname((jsonObj.getString(Constant.FindAgent.FIRST_NAME)));
					findAgentItem.setLastname((jsonObj.getString(Constant.FindAgent.LAST_NAME)));
					findAgentItem.setCompanyname((jsonObj.getString(Constant.FindAgent.COMPANY_NAME)));
					findAgentItem.setPropertytypedealin((jsonObj.getString(Constant.FindAgent.PROPERTY_DEALS_IN)));
					findAgentItem.setAddress((jsonObj.getString(Constant.FindAgent.ADDRESS)));
					findAgentItem.setPostcode((jsonObj.getString(Constant.FindAgent.POST_CODE)));
					findAgentItem.setPhonem((jsonObj.getString(Constant.FindAgent.PHONEM)));
					findAgentItem.setPhoneo((jsonObj.getString(Constant.FindAgent.PHONEO)));
					findAgentItem.setDtjoin((jsonObj.getString(Constant.FindAgent.DT_JOIN)));
					findAgentItem.setWebsite((jsonObj.getString(Constant.FindAgent.WEB_SITE)));
					findAgentItem.setEmail((jsonObj.getString(Constant.FindAgent.EMAIL)));
					findAgentItem.setStstatus((jsonObj.getString(Constant.FindAgent.ST_STATUS)));
					findAgentItem.setLogo((jsonObj.getString(Constant.FindAgent.LOGO)));
					findAgentItem.setPhoto((jsonObj.getString(Constant.FindAgent.PHOTO)));
					findAgentItem.setFacebook((jsonObj.getString(Constant.FindAgent.FACEBOOK)));
					findAgentItem.setTwitter((jsonObj.getString(Constant.FindAgent.TWITTER)));
					findAgentItem.setLinkedin((jsonObj.getString(Constant.FindAgent.LINKEDIN)));
					findAgentItem.setAffiliatedWith((jsonObj.getString(Constant.FindAgent.AFFILIATED_WITH)));
					findAgentItem.setBusinessPage((jsonObj.getString(Constant.FindAgent.BUSINESS_PAGE)));
					findAgentItem.setBusinessScince((jsonObj.getString(Constant.FindAgent.BUSINNESS_SCINCE)));
					findAgentItem.setLanguageKnown((jsonObj.getString(Constant.FindAgent.LANGUAGE_KNOWN)));
					findAgentItem.setMaxproperty((jsonObj.getString(Constant.FindAgent.MAX_PROPERTY)));
					findAgentItem.setAreadealsin((jsonObj.getString(Constant.FindAgent.AREA_DEALS_IN)));
					findAgentItem.setPhotolink((jsonObj.getString(Constant.FindAgent.PHOTO_LINK)));
					findAgentItem.setLogolink((jsonObj.getString(Constant.FindAgent.LOGO_LINK)));
					findAgentItem.setTotalproperty(jsonObj.getString(Constant.FindAgent.TOTAL_PROPERTY));
					findAgentItem.setTotalrequirement(jsonObj.getString(Constant.FindAgent.TOTAL_REQUIREMENT));
					findAgentItems.add(findAgentItem);
				}
				if(findAgentArrayAdaptor == null) {
					findAgentArrayAdaptor = new FindAgentArrayAdaptor(getActivity(), R.id.findAgentListListView, findAgentItems);
					System.out.println("Adapter is null "+findAgentArrayAdaptor.getCount());
					findAgentlistView.setAdapter(findAgentArrayAdaptor);
				} else {
					System.out.println("Adapter is Notify "+findAgentArrayAdaptor.getCount());
				//	if(findAgentArrayAdaptor.getCount() > 10) {
						flag_loading = false;
						findAgentArrayAdaptor.notifyDataSetChanged();
					//}
					
				}
			} else {
					Toast.makeText(getActivity(),((JSONObject) jsonArry.get(0)).getString(Constant.FindAgent.API_MESSAGE),Toast.LENGTH_LONG).show();
					flag_loading = true;
					/*if(!flag_loading)
						((MainFragmentActivity)getActivity()).onBackPressed();*/
			}
			findAgentlistView.onRefreshComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    this.getActivity().getActionBar().setTitle("Agent List");
	}
	
	private void getAgentRecord(final int pageNumber) {
		//execute the find agent web service
	    new WebserviceClient(FindAgentListActivity.this, findAgentService).execute(intent.getString(Constant.FindAgent.SEARCH_STRING),intent.getString(Constant.FindAgent.CMBFIELDS),userSessionManager.getSessionValue(Constant.Login.USER_ID),String.valueOf(pageNumber));
	}
}
