package com.pvo.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import z.com.pvo.loading.button.ActionProcessButton;
import z.com.pvo.loading.button.ProgressGenerator;
import z.com.pvo.newActivity.ZPropAddMainFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.pvo.custom.adapter.AllNotificationArrayAdaptor;
import com.pvo.custom.adapter.AllNotificationItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.NotificationAllService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;


//This is used to display the list of Public property
public class NotificationActivity extends PVOFragment implements ProgressGenerator.OnCompleteListener {
   
	//private PullToRefreshListView allNotificationListView;
	private ListView lv_notification_listview;
	private SwipeRefreshLayout swp_notification_swipLayout;
	
    private NotificationAllService allNotificationService;
    //private Button btn_allNotificationList_addProp;
    //private LinearLayout allNotificationAddNewPropLayout;
	private List<AllNotificationItem> allNotificationItems = new ArrayList<AllNotificationItem>();
	private AllNotificationArrayAdaptor allNotificationArrayAdaptor;
	private UserSessionManager userSessionManager;
	private boolean flag_loading;
	private int pageCount = 1;
	private LinearLayout allNotificationProgresLayout;
	private JSONArray jsonArry;
	private boolean refresh = false;
	
	private ActionProcessButton  btn_addProp;
	private ProgressGenerator progressGenerator;
	
	
	//Set the layout content View
	public NotificationActivity() {
		setContentView(R.layout.activity_notification_list);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		allNotificationService		 = new NotificationAllService();
		userSessionManager			 = new UserSessionManager(getActivity().getApplicationContext());
		//allNotificationListView 	 = (PullToRefreshListView) findViewById(R.id.allNotificationListView);
		lv_notification_listview = (ListView) findViewById(R.id.lv_notification_listview);
		swp_notification_swipLayout = (SwipeRefreshLayout) findViewById(R.id.swp_notification_swipLayout);
		allNotificationProgresLayout = (LinearLayout) findViewById(R.id.allNotifiactionProgresLayout);
		
	    btn_addProp = (ActionProcessButton) findViewById(R.id.btnSignIn);
	    btn_addProp.setMode(ActionProcessButton.Mode.ENDLESS);
	    btn_addProp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	progressGenerator = new ProgressGenerator(NotificationActivity.this);
                progressGenerator.start(btn_addProp);
                btn_addProp.setEnabled(false);
                
            	new Timer().schedule(new TimerTask(){
                    public void run() { 
                     getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                        	Bundle addPropBundle = new Bundle();
            				addPropBundle.putString("From","Add");
            				ZPropAddMainFragment zAddPropTabViewActivity = new ZPropAddMainFragment();
            				zAddPropTabViewActivity.setArguments(addPropBundle);
            				((MainFragmentActivity)getActivity()).redirectScreen(zAddPropTabViewActivity);
                      }
                    });
                   }
                }, 3000);
            }
        });
	    
	    
		
		//Redirect to Add new property Screen
		/*btn_allNotificationList_addProp = (Button) findViewById(R.id.btn_allNotificationList_addProp);
		btn_allNotificationList_addProp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle addPropBundle = new Bundle();
				addPropBundle.putString("From","Add");
				ZPropAddMainFragment zAddPropTabViewActivity = new ZPropAddMainFragment();
				zAddPropTabViewActivity.setArguments(addPropBundle);
				((MainFragmentActivity)getActivity()).redirectScreen(zAddPropTabViewActivity);
			}
		});*/
		
		//call the public property record
		getAllNotification(pageCount);
		
		lv_notification_listview.setOnScrollListener(new OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if(firstVisibleItem != 0) {	
					if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0) {
		                if(flag_loading == false) {
		                    flag_loading = true;
		                    getAllNotification(++pageCount);
		                }
		            }
				}
			}
		});
		
		
		/*
		 * on swipe down layout refresh the list view
		 */
		swp_notification_swipLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				pageCount = 1;
            	refresh = true;
            	getAllNotification(pageCount);
			}
		});
		
		
	 }

	//Call web service and get records for given page number
	private void getAllNotification(int pageNumber) {
		new WebserviceClient(NotificationActivity.this, allNotificationService,allNotificationProgresLayout).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userSessionManager.getSessionValue(Constant.Login.USER_ID),String.valueOf(pageNumber));
	}
	
	//MyProperty List web service Response
	@Override
	public void processResponse(Object response) {
		jsonArry = (JSONArray)response;
		try {
			if (response != null && !((JSONObject)jsonArry.get(0)).has(Constant.MyProperty.API_STATUS)) {
				
				getActivity().getActionBar().setTitle(Html.fromHtml("Home "+"<html><body><sup><small><font size = 1	color=#ffffff>["+jsonArry.getJSONObject(0).getString(Constant.AllNotification.UNREAD)+"/"+jsonArry.getJSONObject(0).getString(Constant.AllNotification.TOTAL)+"]</small></sup></font></body><html>"));
				if(refresh && pageCount == 1) {
					refresh = false;
					allNotificationItems.clear();
				}
				for (int i = 1; i < jsonArry.length(); i++) {
					
					JSONObject jsonObj = jsonArry.getJSONObject(i);
					AllNotificationItem allNotificationItem = new AllNotificationItem();
					
					allNotificationItem.setLogolink(jsonObj.getString(Constant.AllNotification.LOGO_LINK));
					allNotificationItem.setPhotolink(jsonObj.getString(Constant.AllNotification.PHOTO_LINK));
					
					//Set the property id for Property
					if(jsonObj.getString(Constant.AllNotification.V_TYPE).equals("rent prop") || jsonObj.getString(Constant.AllNotification.V_TYPE).equals("sale prop"))
					   allNotificationItem.setPropertyid(jsonObj.getString(Constant.AllNotification.PROPERTY_ID));
					
					//Set the Requirement id for Requirement
					if(jsonObj.getString(Constant.AllNotification.V_TYPE).equals("rent req") || jsonObj.getString(Constant.AllNotification.V_TYPE).equals("sale req"))
						allNotificationItem.setPropertyid(jsonObj.getString(Constant.AllNotification.REQUIREMETN_ID));
					
					allNotificationItem.setVtype(jsonObj.getString(Constant.AllNotification.V_TYPE));
					allNotificationItem.setPropertytype(jsonObj.getString(Constant.AllNotification.PROPERTY_TYPE));
					allNotificationItem.setPrice(jsonObj.getString(Constant.AllNotification.PRICE));
					allNotificationItem.setPlotarea(jsonObj.getString(Constant.AllNotification.PLOT_AREA));
					allNotificationItem.setPlotareaunit(jsonObj.getString(Constant.AllNotification.PLOT_AREA_UNIT));
					allNotificationItem.setMinarea(jsonObj.getString("minarea"));
					allNotificationItem.setAreaunit(jsonObj.getString(Constant.AllNotification.AREA_UNIT));
					allNotificationItem.setBathroom("4");
					allNotificationItem.setBed(jsonObj.getString(Constant.AllNotification.BED));
					allNotificationItem.setCompanyname(jsonObj.getString(Constant.AllNotification.COMPANY_NAME));
					allNotificationItem.setDtadded(jsonObj.getString(Constant.AllNotification.DT_ADDED));
					allNotificationItem.setAddress(jsonObj.getString(Constant.AllNotification.ADDRESS));
					allNotificationItem.setFirstName(jsonObj.getString(Constant.AllNotification.FIRST_NAME));
					allNotificationItem.setLastName(jsonObj.getString(Constant.AllNotification.LAST_NAME));
					
					
					/*allNotificationItem.setTotalprice(StringUtils.defaultIfEmpty(jsonObj.getString(Constant.AllNotification.TOTAL_PRICE),"0.00"));
					allNotificationItem.setRent(StringUtils.defaultIfEmpty(jsonObj.getString(Constant.AllNotification.RENT),"0.00"));
					allNotificationItem.setAreaname(jsonObj.getString(Constant.AllNotification.AREA_NAME));
					allNotificationItem.setLandmark1name(jsonObj.getString(Constant.AllNotification.LOCATION1_NAME));
					allNotificationItem.setLandmark2name(jsonObj.getString(Constant.AllNotification.LOCATION2_NAME));
					allNotificationItem.setStroptions(jsonObj.getString(Constant.AllNotification.PUROPSE));
					allNotificationItem.setMaxarea(jsonObj.getString(Constant.AllNotification.MAX_SQFOOT));
					allNotificationItem.setMinbed(jsonObj.getString(Constant.AllNotification.MIN_BED));
					allNotificationItem.setMaxbed(jsonObj.getString(Constant.AllNotification.MAX_BED));
					allNotificationItem.setStstatus(jsonObj.getString(Constant.AllNotification.ST_STATUS));//1 for unread and 0 for read
					allNotificationItem.setNotificationid(jsonObj.getString(Constant.ReadNotifaction.NOTIFICATION_ID));
					allNotificationItem.setMinplotarea(jsonObj.getString(Constant.AllNotification.MIN_PLOT_AREA));
					allNotificationItem.setMaxplotarea(jsonObj.getString(Constant.AllNotification.MAX_PLOT_AREA));
					allNotificationItem.setMinrent(jsonObj.getString(Constant.AllNotification.MIN_RENT));
					allNotificationItem.setMaxrent(jsonObj.getString(Constant.AllNotification.MAX_RENT));
					allNotificationItem.setMaxprice(jsonObj.getString(Constant.AllNotification.MAX_PRICE));
					allNotificationItem.setMinprice(jsonObj.getString(Constant.AllNotification.MIN_PRICE));
					allNotificationItem.setPhonem(jsonObj.getString(Constant.AllNotification.PHONE_M));*/
					
					allNotificationItems.add(allNotificationItem);
				}
				
				if(allNotificationArrayAdaptor == null) {
					allNotificationArrayAdaptor = new AllNotificationArrayAdaptor(getActivity(), R.id.lv_notification_listview, allNotificationItems);
					lv_notification_listview.setAdapter(allNotificationArrayAdaptor);
				} else {
					flag_loading = false;
					allNotificationArrayAdaptor.notifyDataSetChanged();
				}
				
			} else {
				Toast.makeText(getActivity(),((JSONObject)jsonArry.get(0)).getString(Constant.AddProperty.API_MESSAGE), Toast.LENGTH_LONG).show();
				flag_loading = true;
			}
			
			//hide the oncomplete 
			swp_notification_swipLayout.setRefreshing(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Set the title of the current fragment
	@Override
	public void onResume() {
		super.onResume();
		try {
			if(jsonArry != null && !((JSONObject)jsonArry.get(0)).has(Constant.AllNotification.API_STATUS)) {
				getActivity().getActionBar().setTitle(Html.fromHtml("Home "+"<html><body><sup><small><font size = 1	color=#ffffff>["+
				jsonArry.getJSONObject(0).getString(Constant.AllNotification.UNREAD)+"/"+jsonArry.getJSONObject(0).getString(Constant.AllNotification.TOTAL)+"]</small></sup></font></body><html>"));
			} else {
				if(getActivity() != null)
					getActivity().getActionBar().setTitle("Home");
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Initialize the fragment
	 */
	public static NotificationActivity newInstance() {
		NotificationActivity f = new NotificationActivity();
		Bundle b = new Bundle();
		f.setArguments(b);
		return f;
	}

	@Override
	public void onComplete() {
		btn_addProp.setEnabled(true);
		/*Bundle addPropBundle = new Bundle();
		addPropBundle.putString("From","Add");
		ZPropAddMainFragment zAddPropTabViewActivity = new ZPropAddMainFragment();
		zAddPropTabViewActivity.setArguments(addPropBundle);
		((MainFragmentActivity)getActivity()).redirectScreen(zAddPropTabViewActivity);*/
	}
}
