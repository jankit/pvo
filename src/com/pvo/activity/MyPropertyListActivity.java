package com.pvo.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import z.com.pvo.newActivity.ZPropAddMainFragment;
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
import com.pvo.custom.adapter.AdsListingItem;
import com.pvo.custom.adapter.MyPropertyArrayAdaptor;
import com.pvo.custom.adapter.MyPropertyItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.AdsListingService;
import com.pvo.user.service.MyPropertyService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

public class MyPropertyListActivity extends PVOFragment {
   
	private Boolean isPrint = true;
	private String TAG = "MyPropertyListActivity";
	
	private LinearLayout myPropProgresLayout;
	private PullToRefreshListView myProplistView;
    private TextView addProperty;
    private LinearLayout myPropRefineSearchLayout;
    private int adCounter = 0;
    private MyPropertyService myPropertyService;
    private UserSessionManager userSessionManager;
    private Bundle filterMyPropBundel;
    private AdsListingService adsListingService; 
    private boolean flag_loading;
    private int pageCount = 1;
    private List<MyPropertyItem> propertyItems = new ArrayList<MyPropertyItem>();
    private List<AdsListingItem> adsListItems = new ArrayList<AdsListingItem>();
    private MyPropertyArrayAdaptor myPropertyArrayAdaptor;
    private final int AD_AFTER_PROPERTY = 5;
    private TextView tvTotalRecord;
    private LinearLayout myPropAddBtnMainLayout;
    private Boolean refresh = false;
	
	//set Layout Content view 
	public MyPropertyListActivity() {
		setContentView(R.layout.activity_myproperty_list);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		filterMyPropBundel 		= getArguments();
		myPropertyService 		= new MyPropertyService();
		userSessionManager 		= new UserSessionManager(getActivity().getApplicationContext());
		myProplistView 			= (PullToRefreshListView) findViewById(R.id.myPropListView);
		addProperty 			= (TextView) findViewById(R.id.myPropAddTextView);
		myPropProgresLayout 	= (LinearLayout) findViewById(R.id.myPropProgresLayout);
		tvTotalRecord			= (TextView) findViewById(R.id.tvTotalRecord);
		myPropAddBtnMainLayout	= (LinearLayout) findViewById(R.id.myPropAddBtnMainLayout);
		
		
		//Hide add property button when list show from prefere boker 
		if(filterMyPropBundel.getString("Type").equals("PrefereBroker")) {
			myPropAddBtnMainLayout.setVisibility(View.GONE);
			
		}
		
		//Redirect to Filter property screen
		myPropRefineSearchLayout = (LinearLayout) findViewById(R.id.myPropRefineSearchLayout);
		myPropRefineSearchLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FilterPropertyActivity filterPropertyActivity = new FilterPropertyActivity();
				((MainFragmentActivity)getActivity()).redirectScreen(filterPropertyActivity);
			}
		});

		//redirect to add new property screen
		addProperty.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*MyPropertyAddActivity addMyPropertyActivity = new MyPropertyAddActivity();
				((MainFragmentActivity)getActivity()).redirectScreen(addMyPropertyActivity);*/
				
				ProjectUtility.sys(isPrint, TAG, "Add Property");
				Bundle addPropBundle = new Bundle();
				addPropBundle.putString("From","Add");
				ZPropAddMainFragment zAddPropTabViewActivity = new ZPropAddMainFragment();
				zAddPropTabViewActivity.setArguments(addPropBundle);
				((MainFragmentActivity)getActivity()).redirectScreen(zAddPropTabViewActivity);
				
			}
		});
		
		//call AdsList() to get Ads list from webservice 
		getAdsList();
		//getMyPropertyRecords(pageCount);
	
		//on List view scroll change increase the page counter
		myProplistView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if(firstVisibleItem != 0) {	
					if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0) {
			            if(flag_loading == false) {
			                flag_loading = true;
			                getMyPropertyRecords(++pageCount);
			            } 
			        }
				}
			}
		});
		myProplistView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            	pageCount = 1;
            	adCounter = 0;
            	refresh = true;
            	getMyPropertyRecords(pageCount);
            }
        });
		}

		//get ads listing
		public void getAdsList() {
			 adsListingService = new AdsListingService();
			 WebserviceClient adsListingWebserviceClient = new WebserviceClient(MyPropertyListActivity.this, adsListingService,myPropProgresLayout);
			 adsListingWebserviceClient.setResponseListner(new ResponseListner() {
				@Override
				public void handleResponse(Object response) {
					 JSONArray jsonArray=(JSONArray)response;
					 try {
						 if (jsonArray != null) {
							 for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject jsonObject = jsonArray.getJSONObject(i);
								AdsListingItem adsListingItem = new AdsListingItem();
								adsListingItem.setPropertylogo(jsonObject.getString(Constant.AdsListing.PROPERTY_LOGO));
								adsListingItem.setVideo(jsonObject.getString(Constant.AdsListing.VIDEO));
								adsListingItem.setProjectid(jsonObject.getString(Constant.AdsListing.PROJECT_ID));
	    						adsListItems.add(adsListingItem);
							}
							//get Property list
							getMyPropertyRecords(pageCount);						
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			adsListingWebserviceClient.execute();
		}
	
	//Call web service and get records for given page number
	private void getMyPropertyRecords(final int pageNumber) {
		if(filterMyPropBundel.getString("Type").equals("Filter")) {
			new WebserviceClient(MyPropertyListActivity.this, myPropertyService, myPropProgresLayout).execute(
					userSessionManager.getSessionValue(Constant.Login.USER_ID),
					filterMyPropBundel.getString(Constant.FilterList.PROPERTY_TYPE),
					filterMyPropBundel.getString(Constant.FilterList.PURPOSE),
					filterMyPropBundel.getString(Constant.FilterList.LOCATION),
					filterMyPropBundel.getString(Constant.FilterList.TXT_KEYWORD),
					String.valueOf(pageNumber));
		} else if(filterMyPropBundel.getString("Type").equals("Search")) {
			new WebserviceClient(MyPropertyListActivity.this, myPropertyService, myPropProgresLayout).execute(userSessionManager.getSessionValue(Constant.Login.USER_ID), String.valueOf(pageNumber));
		} else if(filterMyPropBundel.getString("Type").equals("PrefereBroker")) {
			new WebserviceClient(MyPropertyListActivity.this, myPropertyService, myPropProgresLayout).execute(filterMyPropBundel.getString(Constant.PreferredBrokers.BROKER_ID), String.valueOf(pageNumber));
		} 
	 }
	
	@Override
	public void processResponse(Object response) {
		JSONArray jsonArry=(JSONArray)response;
		try {
			if (jsonArry != null && !((JSONObject)jsonArry.get(0)).has(Constant.MyProperty.API_STATUS)){
				tvTotalRecord.setText("Total Record:- "+jsonArry.getJSONObject(0).getString("no_record"));	
				if(refresh && pageCount == 1) {
					refresh = false;
					propertyItems.clear();
				}
				for (int i = 1; i < jsonArry.length(); i++) {
					JSONObject jsonObj = jsonArry.getJSONObject(i);
					MyPropertyItem propItem = new MyPropertyItem();
					propItem.setStstatus(jsonObj.getString(Constant.AddProperty.ST_STATUS));
					propItem.setLogoencoded(jsonObj.getString(Constant.MyProperty.LOGO_ENCODED));
					propItem.setPhotoencoded(jsonObj.getString(Constant.MyProperty.PHOTO_ENCODED));
					propItem.setPrice(jsonObj.getString(Constant.AddProperty.TOTAL_PRICE));
					propItem.setRent(jsonObj.getString(Constant.AddProperty.RENT));
					propItem.setAreaname(jsonObj.getString(Constant.MyProperty.AREA_NAME));
					propItem.setPropertyType(jsonObj.getString(Constant.AddProperty.PROPERTY_TYPE));
					propItem.setSaleOrRent(jsonObj.getString(Constant.AddProperty.STR_OPTIONS));
					propItem.setMinArea(jsonObj.getString(Constant.AddProperty.MIN_AREA));
					propItem.setMaxarea(jsonObj.getString(Constant.AddProperty.MAX_AREA));
					propItem.setAreaUnit(jsonObj.getString(Constant.AddProperty.AREA_UNIT));
					propItem.setBed(jsonObj.getString(Constant.AddProperty.BED));
					propItem.setFirstName(jsonObj.getString(Constant.AddProperty.FIRST_NAME));
					propItem.setLastName(jsonObj.getString(Constant.AddProperty.LAST_NAME));
					propItem.setAddDate(jsonObj.getString(Constant.AddProperty.DATE_ADDED));
					propItem.setPropertyId(jsonObj.getString(Constant.MyProperty.PROPERTY_ID));
					propItem.setStateid(jsonObj.getString(Constant.State.STATEID));
					propItem.setCityid(jsonObj.getString(Constant.City.CITY_ID));
					propItem.setDistrictid(jsonObj.getString(Constant.District.DISTRICT_ID));
					propItem.setPostcode(jsonObj.getString(Constant.MyProperty.POSTCODE));
					propItem.setArea1(jsonObj.getString(Constant.MyProperty.AREA1));
					propItem.setLandmark1(jsonObj.getString(Constant.MyProperty.LANDMARK1));
					propItem.setLandmark2(jsonObj.getString(Constant.MyProperty.LANDMARK2));
					propItem.setFloor(jsonObj.getString(Constant.AddProperty.FLOOR));
					propItem.setRise(jsonObj.getString(Constant.AddProperty.RISE));
					propItem.setFurnishstatus(jsonObj.getString(Constant.AddProperty.FURNISH_STATUS));
					propItem.setFurnishcomment(jsonObj.getString(Constant.AddProperty.FURNISH_COMMENT));
					propItem.setPrice(jsonObj.getString(Constant.AddProperty.PRICE));
					propItem.setRentdeposit(jsonObj.getString(Constant.AddProperty.RENT_DEPOSIT));
					propItem.setMaintenance(jsonObj.getString(Constant.AddProperty.MAINTENANCE));
					propItem.setTransferfees(jsonObj.getString(Constant.AddProperty.TRANSFER_FEES));
					propItem.setAecauda(jsonObj.getString(Constant.AddProperty.AEC_AUDA));
					propItem.setParking(jsonObj.getString(Constant.AddProperty.PARKING));
					propItem.setDastawage(jsonObj.getString(Constant.AddProperty.DASTAWAGE));
					propItem.setPlotArea(jsonObj.getString(Constant.AddProperty.PLOT_AREA));
					propItem.setMinplotarea(jsonObj.getString(Constant.AddProperty.MIN_PLOT_AREA));
					propItem.setMaxplotarea(jsonObj.getString(Constant.AddProperty.MAX_PLOT_AREA));
					propItem.setYearbuiltup(jsonObj.getString(Constant.AddProperty.YEAR_BUILD_UP));
					propItem.setComments(jsonObj.getString(Constant.AddProperty.COMMENTS));
					propItem.setPrefopt(jsonObj.getString(Constant.AddProperty.PREF_OPT));
					propItem.setHint(jsonObj.getString(Constant.AddProperty.HINT));
					propItem.setPurpose(jsonObj.getString(Constant.AddProperty.PURPOSE));
					propItem.setSmsstatus(jsonObj.getString(Constant.AddProperty.SMS_STATUS));
					propItem.setEmailstatus(jsonObj.getString(Constant.AddProperty.EMAIL_STATUS));
					propItem.setZone(jsonObj.getString(Constant.AddProperty.ZONE));
					propItem.setCnt(jsonObj.getString(Constant.AddProperty.CNT));
					propItem.setTpid(jsonObj.getString(Constant.TPSchemesListing.TP_ID));
					propItem.setSmstobrokers(jsonObj.getString(Constant.AddProperty.SMS_TO_BROKER));
					propItem.setLongval(jsonObj.getString(Constant.AddProperty.LONGITUDE));
					propItem.setLatval(jsonObj.getString(Constant.AddProperty.LATITUDE));
					propItem.setOccupacy(jsonObj.getString(Constant.AddProperty.OCCUPACY));
					propItem.setOccupacyName(jsonObj.getString(Constant.AddProperty.OCCUPANCY_NAME));
					propItem.setOccupacyDetail(jsonObj.getString(Constant.AddProperty.OCCUPANCY_DETAIL));
					propItem.setOccupacyDate(jsonObj.getString(Constant.AddProperty.OCCUPANCY_DATE));
					propItem.setPlotArea(jsonObj.getString(Constant.AddProperty.PLOT_AREA));
					propItem.setPlotAreaUnit(jsonObj.getString(Constant.AddProperty.PLOT_AREA_UNIT));
					propItem.setConstructionArea(jsonObj.getString(Constant.AddProperty.CONSTRUCTION_AREA));
					propItem.setAddress(jsonObj.getString(Constant.AddProperty.ADDRESS));
					propItem.setUpdateDate(jsonObj.getString(Constant.AddProperty.DATE_UPDTE));
					propItem.setDetailcount(jsonObj.getString(Constant.AddProperty.DETAIL_COUNT));
					propItem.setImages(jsonObj.getString(Constant.AddProperty.IMAGES));
					propItem.setLandmark1name(jsonObj.getString(Constant.MyProperty.LANDMARK1_NAME));
					propItem.setLandmark2name(jsonObj.getString(Constant.MyProperty.LANDMARK2_NAME));
					propItem.setInquirycount(jsonObj.getString(Constant.MyProperty.INQUIRYCOUNT));
					
					//get the More detail Object 
					if(!jsonObj.getString(Constant.MyProperty.MORE_DETAILS).equals("null") ) {
						JSONObject moreDetailJsonObject = jsonObj.getJSONObject(Constant.MyProperty.MORE_DETAILS);
						//check "Whom to let" parameter is available or not  
						if(moreDetailJsonObject.has(Constant.MyProperty.WHOM_TO_LET)) {
							propItem.setWhomtolet(moreDetailJsonObject.getString(Constant.MyProperty.WHOM_TO_LET));
							propItem.setWhomtoletother(moreDetailJsonObject.getString(Constant.MyProperty.WHOM_TO_LET_OTHER));
						}
						//Set the plot detail if property type is plot
						if(moreDetailJsonObject.has(Constant.AddProperty.PLOT_TYPE)) {
							propItem.setNastatus(moreDetailJsonObject.getString(Constant.AddProperty.NA_STATUS));
							propItem.setPlottype(moreDetailJsonObject.getString(Constant.AddProperty.PLOT_TYPE));
							propItem.setNavisharat(moreDetailJsonObject.getString(Constant.AddProperty.NAVISHARAT));
							propItem.setJunisharat(moreDetailJsonObject.getString(Constant.AddProperty.JUNISHARAT));
							propItem.setPrassap(moreDetailJsonObject.getString(Constant.AddProperty.PRASSAP));
							propItem.setDispute(moreDetailJsonObject.getString(Constant.AddProperty.DIS_PUTE));
							propItem.setTitleclear(moreDetailJsonObject.getString(Constant.AddProperty.TITLE_CLEAR));
							propItem.setShreesarkar(moreDetailJsonObject.getString(Constant.AddProperty.SHREE_SARKAR));
							propItem.setOnroad(moreDetailJsonObject.getString(Constant.AddProperty.ON_ROAD));
							propItem.setKheti(moreDetailJsonObject.getString(Constant.AddProperty.KHETI));
						}
						//Set Bunglow sub type
						if(moreDetailJsonObject.has(Constant.MyProperty.BUNGLOW_TYPE)) {
							propItem.setBunglowtype(moreDetailJsonObject.getString(Constant.MyProperty.BUNGLOW_TYPE));
						}
						//set shop type for shop sub type where sub type is not Shop 
						if(moreDetailJsonObject.has(Constant.MyProperty.SHOP_TYPE) && !jsonObj.getString(Constant.AddProperty.PROPERTY_TYPE).equalsIgnoreCase("Shop")) {
							propItem.setShoptype(moreDetailJsonObject.getString(Constant.MyProperty.SHOP_TYPE));
						} else if(jsonObj.getString(Constant.AddProperty.PROPERTY_TYPE).equalsIgnoreCase("Shop")) {
							propItem.setShoptype(jsonObj.getString(Constant.MyProperty.PROPERTY_TYPE));
						}
					}
					
					//Add advertise item to property
					if(i % AD_AFTER_PROPERTY == 0 && adsListItems.size() > adCounter) {
						propItem.setAdsListingItem(adsListItems.get(adCounter));
						adCounter++;
					}
					propertyItems.add(propItem);
				}
				
				//Check Adapter is null than create and fill otherwise only notify change 
				if(myPropertyArrayAdaptor == null) {
					if(filterMyPropBundel.getString("Type").equals("PrefereBroker")) {
						myPropertyArrayAdaptor = new MyPropertyArrayAdaptor(getActivity(), R.id.myPropListView, propertyItems,filterMyPropBundel.getString("Type"));
					} else {
						myPropertyArrayAdaptor = new MyPropertyArrayAdaptor(getActivity(), R.id.myPropListView, propertyItems);
					}
					myProplistView.setAdapter(myPropertyArrayAdaptor);
				} else {
					flag_loading = false;
					myPropertyArrayAdaptor.notifyDataSetChanged();
				}
			     //this method used to display the inactive property first and then all actives
				 Collections.sort(propertyItems, new Comparator<MyPropertyItem>(){
			    	public int compare(MyPropertyItem emp1, MyPropertyItem emp2) {
			    	  return emp1.getStstatus().compareToIgnoreCase(emp2.getStstatus());
			    	}
				 });
			} else {
				 flag_loading = true;
				 Toast.makeText(getActivity(),((JSONObject)jsonArry.get(0)).getString(Constant.AddProperty.API_MESSAGE), Toast.LENGTH_LONG).show();
			}
			myProplistView.onRefreshComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//set title on resume
	@Override
	public void onResume() {
	   super.onResume();
	   if(filterMyPropBundel.getString("Type").equals("PrefereBroker"))
		   //this.getActivity().getActionBar().setTitle("Properties of "+filterMyPropBundel.getString(Constant.PreferredBrokers.FIRST_NAME));
	   		MainFragmentActivity.setTitle("Properties of "+filterMyPropBundel.getString(Constant.PreferredBrokers.FIRST_NAME));
	   else
		   MainFragmentActivity.setTitle("My Property");
		   //this.getActivity().getActionBar().setTitle("My Property");
	}
}
