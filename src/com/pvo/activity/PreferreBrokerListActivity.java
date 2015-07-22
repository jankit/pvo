package com.pvo.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;
import com.pvo.custom.adapter.PreferreBrokerArrayAdaptor;
import com.pvo.custom.adapter.PreferreBrokerItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.PreferredBrokersService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;


// This is used to Display the list of the Preferre Broker of Particular Broker in this pass the Current Broker Id
public class PreferreBrokerListActivity extends PVOFragment{
    
	private PullToRefreshListView prefBrokerListView;
    private PreferreBrokerArrayAdaptor preferreBrokerArrayAdaptor;
    private List<PreferreBrokerItem> publicPropertyItems = new ArrayList<PreferreBrokerItem>();
    private PreferredBrokersService preferreBrokerService;
    private UserSessionManager userSessionManager;
    private LinearLayout prefBrokerGroupListLayout;
    private LinearLayout prefBrokerAddPrefBrokerLayout;
    private LinearLayout prefBrokerRefinePrefListLayout;
	
    private boolean flag_loading;
    private Boolean refresh = false;
	
	// Set the layout Content View 
	public PreferreBrokerListActivity() {
		setContentView(R.layout.activity_preferrebroker_list);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
		preferreBrokerService		= new PreferredBrokersService();
		userSessionManager			= new UserSessionManager(getActivity().getApplicationContext());
		prefBrokerListView 			= (PullToRefreshListView) findViewById(R.id.prefBrokerListView);
		
		//on click of add prefere button move to Find agent screen 
		prefBrokerAddPrefBrokerLayout 	= (LinearLayout) findViewById(R.id.prefBrokerAddPrefBrokerLayout);
		prefBrokerAddPrefBrokerLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FindAgentActivity findAgentActivity = new FindAgentActivity();
				((MainFragmentActivity)getActivity()).redirectScreen(findAgentActivity);
			}
		});
		
		//on click of group button move to group listing screen 
		prefBrokerGroupListLayout 	= (LinearLayout) findViewById(R.id.prefBrokerGroupListLayout);
		prefBrokerGroupListLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GroupListActivity groupListActivity = new GroupListActivity();
				((MainFragmentActivity)getActivity()).redirectScreen(groupListActivity);
			}
		});
		
		//on click of Refine search button move to Find agent screen
		prefBrokerRefinePrefListLayout 	= (LinearLayout) findViewById(R.id.prefBrokerRefinePrefListLayout);
		prefBrokerRefinePrefListLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FindAgentActivity findAgentActivity = new FindAgentActivity();
				((MainFragmentActivity)getActivity()).redirectScreen(findAgentActivity);
			}
		});
		
		/*prefBrokerListView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if(firstVisibleItem != 0) {	
					if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0) {
						if(flag_loading == false) {
							flag_loading = true;
							new WebserviceClient(PreferreBrokerListActivity.this,preferreBrokerService).execute(userSessionManager.getSessionValue(Constant.Login.USER_ID));
		                }
		            }
				}
			}
		});*/
		
		prefBrokerListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            	refresh = true;
            	new WebserviceClient(PreferreBrokerListActivity.this,preferreBrokerService).execute(userSessionManager.getSessionValue(Constant.Login.USER_ID));
            }
        });
		//call Preferred broker web service 
		new WebserviceClient(PreferreBrokerListActivity.this,preferreBrokerService).execute(userSessionManager.getSessionValue(Constant.Login.USER_ID));
	}

	@Override
	public void processResponse(Object response) {
		JSONArray jsonArry=(JSONArray)response;
		try {
			if (response != null && !((JSONObject)jsonArry.get(0)).has(Constant.PreferredBrokers.API_STATUS)) {
				TextView prefeBrokerTotalNoRecodTv = (TextView) findViewById(R.id.prefeBrokerTotalNoRecodTv);
				prefeBrokerTotalNoRecodTv.setText("Total Record:- "+jsonArry.getJSONObject(0).getString(Constant.PreferredBrokers.NO_RECORD));
				if(refresh ) {
					refresh = false;
					publicPropertyItems.clear();
				}
				for (int i = 1; i < jsonArry.length(); i++) {
					JSONObject jsonObj = jsonArry.getJSONObject(i);
					PreferreBrokerItem preferreBrokerItem = new PreferreBrokerItem();
					preferreBrokerItem.setBrokerid(jsonObj.getString(Constant.PreferredBrokers.BROKER_ID));
					preferreBrokerItem.setFirstname(jsonObj.getString(Constant.PreferredBrokers.FIRST_NAME));
					preferreBrokerItem.setLastname(jsonObj.getString(Constant.PreferredBrokers.LAST_NAME));
					preferreBrokerItem.setCompanyname(jsonObj.getString(Constant.PreferredBrokers.COMPANY_NAME));
					preferreBrokerItem.setAddress(jsonObj.getString(Constant.PreferredBrokers.ADDRESS));
					preferreBrokerItem.setPhonem(jsonObj.getString(Constant.PreferredBrokers.PHONEM));
					preferreBrokerItem.setPhoneo(jsonObj.getString(Constant.PreferredBrokers.PHONEO));
					preferreBrokerItem.setWebsite(jsonObj.getString(Constant.PreferredBrokers.WEB_SITE));
					preferreBrokerItem.setEmail(jsonObj.getString(Constant.PreferredBrokers.EMAIL));
					preferreBrokerItem.setStstatus(jsonObj.getString(Constant.PreferredBrokers.ST_STATUS));
					preferreBrokerItem.setDtjoin(jsonObj.getString(Constant.PreferredBrokers.DT_JOIN));
					preferreBrokerItem.setLogolink(jsonObj.getString(Constant.PreferredBrokers.LOGO_LINK));
					preferreBrokerItem.setPhotolink(jsonObj.getString(Constant.PreferredBrokers.PHOTO_LINK));
					preferreBrokerItem.setPostcode(jsonObj.getString(Constant.PreferredBrokers.POSTCODE));
					preferreBrokerItem.setFacebook(jsonObj.getString(Constant.PreferredBrokers.FACEBOOK));
					preferreBrokerItem.setTwitter(jsonObj.getString(Constant.PreferredBrokers.TWITTER));
					preferreBrokerItem.setLinkedin(jsonObj.getString(Constant.PreferredBrokers.LINKEDIN));
					preferreBrokerItem.setBusinessScince(jsonObj.getString(Constant.PreferredBrokers.BUSINESS_SCINCE));
					preferreBrokerItem.setAffiliatedWith(jsonObj.getString(Constant.PreferredBrokers.AFFILIATE_WITH));
					preferreBrokerItem.setLanguageKnown(jsonObj.getString(Constant.PreferredBrokers.LANGUAGE_KNOWN));
					preferreBrokerItem.setBusinessPage(jsonObj.getString(Constant.PreferredBrokers.BUSINESS_PAGE));
					preferreBrokerItem.setPrefid(jsonObj.getString(Constant.PreferredBrokers.PREF_ID));
					publicPropertyItems.add(preferreBrokerItem);
				}
				
				if(preferreBrokerArrayAdaptor == null) {
					preferreBrokerArrayAdaptor = new PreferreBrokerArrayAdaptor(getActivity(), R.id.publicPropListView, publicPropertyItems);
					prefBrokerListView.setAdapter(preferreBrokerArrayAdaptor);
				} else {
					preferreBrokerArrayAdaptor.notifyDataSetChanged();
				}
				prefBrokerListView.onRefreshComplete();
			} else {
				
				Toast.makeText(getActivity(), ((JSONObject)jsonArry.get(0)).getString(Constant.PreferredBrokers.API_MESSAGE), Toast.LENGTH_LONG).show();
				/** if no preferred broker found than redirect to find agent screen **/
				FindAgentActivity findAgentActivity = new FindAgentActivity();
				((MainFragmentActivity)getActivity()).redirectScreen(findAgentActivity);
				/** END **/
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	/** on fragment resume set the title of fragment **/
	@SuppressLint("NewApi")
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    this.getActivity().getActionBar().setTitle("Preferred Broker");
	}
	/** END **/
}
