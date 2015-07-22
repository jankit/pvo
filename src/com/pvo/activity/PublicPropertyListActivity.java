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
import com.pvo.custom.adapter.PublicPropertyArrayAdaptor;
import com.pvo.custom.adapter.PublicPropertyItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.PublicPropertyService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;


//This is used to display the list of Public property
public class PublicPropertyListActivity extends PVOFragment {
	
	private TextView tvPublicPropTotalRecord;
    private LinearLayout publicPropProgresLayout;
    private PullToRefreshListView publicProplistView;
    private PublicPropertyService publicPropertyService;
    private Bundle filterPbulicPropBundel;
    private LinearLayout publicPropRefineSearchLayout;
	private boolean flag_loading;
	private int pageCount = 1;
	private List<PublicPropertyItem> publicPropertyItems = new ArrayList<PublicPropertyItem>();
	private PublicPropertyArrayAdaptor publicPropertyArrayAdaptor;
	private UserSessionManager userSessionManager;
	private Boolean refresh = false;
	
	//set the layout content view 
	public PublicPropertyListActivity() {
		setContentView(R.layout.activity_publicproperty_list);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		publicPropertyService 	= new PublicPropertyService();
		userSessionManager 		= new UserSessionManager(getActivity());
		tvPublicPropTotalRecord = (TextView) findViewById(R.id.tvPublicPropTotalRecord);
		filterPbulicPropBundel 	= getArguments();
		publicProplistView 		= (PullToRefreshListView) findViewById(R.id.publicPropListView);
		publicPropProgresLayout = (LinearLayout) findViewById(R.id.publicPropProgresLayout);
		
		/** redirect to the Refine search property screen **/ 
		publicPropRefineSearchLayout = (LinearLayout) findViewById(R.id.publicPropRefineSearchLayout);
		publicPropRefineSearchLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FilterPropertyActivity filterPropertyActivity = new FilterPropertyActivity();
				((MainFragmentActivity)getActivity()).redirectScreen(filterPropertyActivity);
			}
		});
		/** END **/
		
		/** call the public property record **/
		getPublicPropertyRecords(pageCount);
		publicProplistView.setOnScrollListener(new OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if(firstVisibleItem != 0) {	
					if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0) {
						if(flag_loading == false) {
							flag_loading = true;
		                    getPublicPropertyRecords(++pageCount);
		                }
		            }
				}
			}
		});
		/** END **/
		
		publicProplistView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            	pageCount = 1;
            	refresh = true;
            	getPublicPropertyRecords(pageCount);
            }
        });
	}
	
	/**
	 * Call web service and get records for given page number
	 * 
	 * @param pageNumber
	 */
	private void getPublicPropertyRecords(int pageNumber) {
		
		if(filterPbulicPropBundel.getString("Type").equals("Filter")){
			new WebserviceClient(PublicPropertyListActivity.this, publicPropertyService, publicPropProgresLayout).execute(
			        userSessionManager.getSessionValue(Constant.Login.USER_ID),
			        filterPbulicPropBundel.getString(Constant.FilterList.PROPERTY_TYPE),
					filterPbulicPropBundel.getString(Constant.FilterList.PURPOSE),
					filterPbulicPropBundel.getString(Constant.FilterList.LOCATION),
					filterPbulicPropBundel.getString(Constant.FilterList.TXT_KEYWORD),
					String.valueOf(pageNumber));
		} else if(filterPbulicPropBundel.getString("Type").equals("Search")) {
			new WebserviceClient(PublicPropertyListActivity.this, publicPropertyService, publicPropProgresLayout).execute(String.valueOf(pageNumber));
		}
	}
	/** END **/

	@Override
	public void processResponse(Object response) {
		JSONArray jsonArry=(JSONArray)response;
		try {
			if (response != null && !((JSONObject)jsonArry.get(0)).has(Constant.MyProperty.API_STATUS)) {
				tvPublicPropTotalRecord.setText("Total Record:- "+jsonArry.getJSONObject(0).getString("no_record"));
				if(refresh && pageCount == 1) {
					refresh = false;
					publicPropertyItems.clear();
				}
				for (int i = 1; i < jsonArry.length(); i++) {
					JSONObject jsonObj = jsonArry.getJSONObject(i);
					PublicPropertyItem propertyItem = new PublicPropertyItem();
					propertyItem.setPropertyid(jsonObj.getString(Constant.PublicProperty.PROPERTY_ID));
					propertyItem.setPropertytype(jsonObj.getString(Constant.PublicProperty.PROPERTY_TYPE));
					propertyItem.setPropertysubtype(jsonObj.getString(Constant.PublicProperty.PROPERTY_SUBTYPE));
					propertyItem.setStroptions(jsonObj.getString(Constant.PublicProperty.STR_OPTION));
					propertyItem.setBed(jsonObj.getString(Constant.PublicProperty.BED));
					propertyItem.setAddress(jsonObj.getString(Constant.PublicProperty.ADDRESS));
					propertyItem.setArea1(jsonObj.getString(Constant.PublicProperty.AREA1));
					propertyItem.setFirstName(jsonObj.getString(Constant.PublicProperty.FIRST_NAME));
					propertyItem.setLastName(jsonObj.getString(Constant.PublicProperty.LAST_NAME));
					propertyItem.setAddDate(jsonObj.getString(Constant.PublicProperty.DT_ADDED));
					propertyItem.setUpdateDate(jsonObj.getString(Constant.PublicProperty.DT_UPDATE));
					propertyItem.setMinarea(jsonObj.getString(Constant.PublicProperty.MIN_AREA));
					propertyItem.setMaxarea(jsonObj.getString(Constant.PublicProperty.MAX_AREA));
					propertyItem.setPrice(jsonObj.getString(Constant.PublicProperty.TOTAL_PRICE));
					propertyItem.setRent(jsonObj.getString(Constant.PublicProperty.RENT));
					propertyItem.setAreaunit(jsonObj.getString(Constant.PublicProperty.AREA_UNIT));
					propertyItem.setLandmark1(jsonObj.getString(Constant.PublicProperty.LANDMARK1));
					propertyItem.setLandmark2(jsonObj.getString(Constant.PublicProperty.LANDMARK2));
					propertyItem.setDetailcount(jsonObj.getInt(Constant.PublicProperty.DETAIL_COUNT));
					propertyItem.setPlotarea(jsonObj.getString(Constant.PublicProperty.PLOT_AREA));
					propertyItem.setPlotareaunit(jsonObj.getString(Constant.PublicProperty.PLOT_AREA_UNIT));
					propertyItem.setConstructionarea(jsonObj.getString(Constant.PublicProperty.CONSTRUCTION_AREA));
					propertyItem.setAreaname(jsonObj.getString(Constant.PublicProperty.AREA_NAME));
					propertyItem.setLandmark1name(jsonObj.getString(Constant.PublicProperty.LANDMARK1_NAME));
					propertyItem.setLandmark2name(jsonObj.getString(Constant.PublicProperty.LANDMARK2_NAME));
					propertyItem.setLogoencoded(jsonObj.getString(Constant.PublicProperty.LOGO_ENCODED));
					propertyItem.setPhotoencoded(jsonObj.getString(Constant.PublicProperty.PHOTO_ENCODED));
					propertyItem.setPhonem(jsonObj.getString(Constant.PublicProperty.PHONE_M));
					propertyItem.setMinplotarea(jsonObj.getString(Constant.PublicProperty.PHONE_M));
					propertyItem.setPhonem(jsonObj.getString(Constant.PublicProperty.PHONE_M));
					propertyItem.setPage(String.valueOf(pageCount));
					propertyItem.setNominee(jsonObj.getString(Constant.PublicProperty.NOMINEE));
					propertyItem.setBathRoom("4");
					propertyItem.setCompanyname("companyname");
					
					//set Nominee name and number
					if(!jsonObj.getString(Constant.PublicProperty.NOMINEE).equalsIgnoreCase("NA"))	{
						propertyItem.setNominee_name(jsonObj.getString(Constant.PublicProperty.NOMINEE_NAME));
						propertyItem.setNominee_mobile_no(jsonObj.getString(Constant.PublicProperty.NOMINEE_MOBILE_NO));
					}
					publicPropertyItems.add(propertyItem);
				}
				if(publicPropertyArrayAdaptor == null) {
					publicPropertyArrayAdaptor = new PublicPropertyArrayAdaptor(getActivity(), R.id.publicPropListView, publicPropertyItems);
					publicProplistView.setAdapter(publicPropertyArrayAdaptor);
				} else {
					publicPropertyArrayAdaptor.notifyDataSetChanged();
					flag_loading = false;
				}
			} else {
				if(publicPropertyArrayAdaptor == null) {
					Toast.makeText(getActivity(),((JSONObject)jsonArry.get(0)).getString(Constant.AddProperty.API_MESSAGE), Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getActivity(),((JSONObject)jsonArry.get(0)).getString(Constant.AddProperty.API_MESSAGE), Toast.LENGTH_LONG).show();
					publicPropertyArrayAdaptor.notifyDataSetChanged();
					flag_loading = true;
				}
			}
			publicProplistView.onRefreshComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Set the title on resume
	@Override
	public void onResume() {
		super.onResume();
		this.getActivity().getActionBar().setTitle("Public Property");
	}
}
