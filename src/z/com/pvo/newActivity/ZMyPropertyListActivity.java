package z.com.pvo.newActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import z.com.pvo.components.BadgeView;
import z.com.pvo.loading.button.ActionProcessButton;
import z.com.pvo.loading.button.ProgressGenerator;
import z.com.pvo.util.ProjectUtility;
import android.annotation.SuppressLint;
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

import com.pvo.activity.MainFragmentActivity;
import com.pvo.activity.R;
import com.pvo.custom.adapter.MyPropertyArrayAdaptor;
import com.pvo.custom.adapter.MyPropertyItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.MyPropertyService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

public class ZMyPropertyListActivity extends PVOFragment implements ProgressGenerator.OnCompleteListener {
   
	protected Boolean isPrint = true;
	protected String TAG = "MyPropertyListActivity";
	
	private LinearLayout myPropProgresLayout;
	private ListView lv_myPropList_listview;
	private SwipeRefreshLayout swp_myPropList_swipLayout;
    //private TextView addProperty;
    //private LinearLayout myPropRefineSearchLayout;
    private MyPropertyService myPropertyService;
    private Bundle filterMyPropBundel;
    private boolean flag_loading;
    private int pageCount = 1;
    private List<MyPropertyItem> propertyItems = new ArrayList<MyPropertyItem>();
    private MyPropertyArrayAdaptor myPropertyArrayAdaptor;
    //private LinearLayout myPropAddBtnMainLayout;
    private ActionProcessButton btn_myPropList_addProp;
    private Boolean refresh = false;
	private UserSessionManager userSessionManager;
	
	private ProgressGenerator progressGenerator;
	
	public static BadgeView badge4;
	private JSONArray jsonArry;
	
	//public static boolean flagBadge = true;
	
    /*
     * set Layout Content view 
     */
	public ZMyPropertyListActivity() {
		setContentView(R.layout.z_prop_myprop_list);
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void init(Bundle savedInstanceState) {
		
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		filterMyPropBundel 		= getArguments();
		userSessionManager = new UserSessionManager(getActivity());
		myPropertyService 		= new MyPropertyService();
		
		badge4 = new BadgeView(getActivity(), MainFragmentActivity.tv_actionbar_title);
		
		/*
         * show cointer budger this is only for 
         */
        /*int tvSize = getResources().getDimensionPixelSize(R.dimen.commonSpinnerTextSize);
        badge4 = new BadgeView(getActivity(), MainFragmentActivity.tv_actionbar_title);
        badge4.setBadgePosition(BadgeView.POSITION_TOP_LEFT);
		badge4.setTextSize(tvSize);
		badge4.setBadgeMargin(MainFragmentActivity.tv_actionbar_title.getWidth(), 0);
		badge4.setBadgeBackgroundColor(R.color.green);*/
		
		   
		lv_myPropList_listview = (ListView) findViewById(R.id.lv_myPropList_listview);
		swp_myPropList_swipLayout = (SwipeRefreshLayout) findViewById(R.id.swp_myPropList_swipLayout);
		//addProperty 			= (TextView) findViewById(R.id.myPropAddTextView);
		myPropProgresLayout 	= (LinearLayout) findViewById(R.id.myPropProgresLayout);

		/*
		 * redirect to add new property screen
		 */
		btn_myPropList_addProp = (ActionProcessButton) findViewById(R.id.btn_myPropList_addProp);
		/*btn_myPropList_addProp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle addPropBundle = new Bundle();
				addPropBundle.putString("From","Add");
				ZPropAddMainFragment zAddPropTabViewActivity = new ZPropAddMainFragment();
				zAddPropTabViewActivity.setArguments(addPropBundle);
				((MainFragmentActivity)getActivity()).redirectScreen(zAddPropTabViewActivity);
			}
		});*/
		
		
		btn_myPropList_addProp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	progressGenerator = new ProgressGenerator(ZMyPropertyListActivity.this);
                progressGenerator.start(btn_myPropList_addProp);
                btn_myPropList_addProp.setEnabled(false);
                
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
		
		//myPropAddBtnMainLayout	= (LinearLayout) findViewById(R.id.myPropAddBtnMainLayout);
		
		//Hide add property button when list show from prefere boker 
		/*if(filterMyPropBundel.getString("Type").equals("PrefereBroker")) {
			myPropAddBtnMainLayout.setVisibility(View.GONE);
		}*/
		
		//Redirect to Filter property screen
		/*myPropRefineSearchLayout = (LinearLayout) findViewById(R.id.myPropRefineSearchLayout);
		myPropRefineSearchLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FilterPropertyActivity filterPropertyActivity = new FilterPropertyActivity();
				((MainFragmentActivity)getActivity()).redirectScreen(filterPropertyActivity);
			}
		});*/

		//call AdsList() to get Ads list from webservice 
		//getAdsList();
		getMyPropertyRecords(pageCount);
	
		//on List view scroll change increase the page counter
		lv_myPropList_listview.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				
				if(firstVisibleItem != 0) {	
					if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0) {
			            if(flag_loading == false) {
			                flag_loading = true;
			                //ProjectUtility.sys(isPrint, TAG, "setOnScrollListener-->"+firstVisibleItem);
			                getMyPropertyRecords(++pageCount);
			            } 
			        }
				}
			}
		});
		
		/*
		 * refresh on swipe to down layout
		 */
		swp_myPropList_swipLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				pageCount = 1;
            	refresh = true;
            	getMyPropertyRecords(pageCount);
			}
		});
	}
	
	
	//Call web service and get records for given page number
	private void getMyPropertyRecords(final int pageNumber) {
		ProjectUtility.sys(isPrint, TAG, "getMyPropertyRecords--> "+pageNumber);
		if(filterMyPropBundel.getString("Type").equals("Filter")) {
			new WebserviceClient(ZMyPropertyListActivity.this, myPropertyService, myPropProgresLayout).execute(
					userSessionManager.getSessionValue(Constant.Login.USER_ID),
					filterMyPropBundel.getString(Constant.FilterList.PROPERTY_TYPE),
					filterMyPropBundel.getString(Constant.FilterList.PURPOSE),
					filterMyPropBundel.getString(Constant.FilterList.LOCATION),
					filterMyPropBundel.getString(Constant.FilterList.TXT_KEYWORD),
					String.valueOf(pageNumber));
			
		} else if(filterMyPropBundel.getString("Type").equals("Search")) { //Call from my property list
			
			new WebserviceClient(ZMyPropertyListActivity.this, myPropertyService, myPropProgresLayout).execute(userSessionManager.getSessionValue(Constant.Login.USER_ID), String.valueOf(pageNumber));
			
		} else if(filterMyPropBundel.getString("Type").equals("PrefereBroker")) { //Call from prefereBroker
			
			new WebserviceClient(ZMyPropertyListActivity.this, myPropertyService, myPropProgresLayout).execute(filterMyPropBundel.getString(Constant.PreferredBrokers.BROKER_ID), String.valueOf(pageNumber));
		} 
	 }
	
	@SuppressLint("ResourceAsColor")
	@Override
	public void processResponse(Object response) {
		jsonArry = (JSONArray)response;
		
		try {
			if (jsonArry != null && !((JSONObject)jsonArry.get(0)).has(Constant.MyProperty.API_STATUS)){
				if(refresh && pageCount == 1) {
				   refresh = false;
				   propertyItems.clear();
				}
				
				/*
				 * Show total property counter
				 */
				MainFragmentActivity.tv_actionbar_title.setText(Html.fromHtml("My Property "+"<html><body><sup><small><font size = 1	color=#ffffff>["+jsonArry.getJSONObject(0).getString(Constant.MyProperty.NO_RECORD)+"]</small></sup></font></body><html>"));
				
				
				
				for (int i = 1; i < jsonArry.length(); i++) {
					JSONObject jsonObj = jsonArry.getJSONObject(i);
					//ProjectUtility.sys(isPrint, TAG, "JSONObject--> "+jsonObj);
					//ProjectUtility.sys(isPrint, TAG, "Property ID--> "+jsonObj.getString(Constant.MyProperty.PROPERTY_ID));
					
					MyPropertyItem propItem = new MyPropertyItem();
					/*propItem.setLogoencoded(jsonObj.getString(Constant.MyProperty.LOGO_ENCODED));
					propItem.setPhotoencoded(jsonObj.getString(Constant.MyProperty.PHOTO_ENCODED));*/
					propItem.setImage1link(jsonObj.getString(Constant.MyProperty.IMAGE1_LINK));
					propItem.setPropertyId(jsonObj.getString(Constant.MyProperty.PROPERTY_ID));
					propItem.setPropertyType(jsonObj.getString(Constant.AddProperty.PROPERTY_TYPE));
					propItem.setSaleOrRent(jsonObj.getString(Constant.AddProperty.STR_OPTIONS));
					propItem.setPrice(jsonObj.getString(Constant.AddProperty.PRICE));
					propItem.setBed(jsonObj.getString(Constant.AddProperty.BED));
					propItem.setMinArea(jsonObj.getString(Constant.AddProperty.MIN_AREA));
					propItem.setPlotArea(jsonObj.getString(Constant.AddProperty.PLOT_AREA));
					propItem.setPlotAreaUnit(jsonObj.getString(Constant.AddProperty.PLOT_AREA_UNIT));
					propItem.setAreaUnit(jsonObj.getString(Constant.AddProperty.AREA_UNIT));
					propItem.setBathroom("3");
					propItem.setAddDate(jsonObj.getString(Constant.AddProperty.DATE_ADDED));
					propItem.setAreaname(jsonObj.getString(Constant.MyProperty.AREA_NAME));
					propItem.setPurpose(jsonObj.getString(Constant.MyProperty.PURPOSE));
					propItem.setCompanyName(jsonObj.getString(Constant.MyProperty.COMPANY_NAME));
					propItem.setStstatus(jsonObj.getString("ststatus"));

					propertyItems.add(propItem);
					//ProjectUtility.sys(isPrint, TAG, "propertyItems-> "+propertyItems.size());
					
				}
				
				//Check Adapter is null than create and fill otherwise only notify change 
				if(myPropertyArrayAdaptor == null) {
					//ProjectUtility.sys(isPrint, TAG, "myPropertyArrayAdaptor is null");
					
					if(filterMyPropBundel.getString("Type").equals("PrefereBroker")) {
						myPropertyArrayAdaptor = new MyPropertyArrayAdaptor(getActivity(), R.id.myPropListView, propertyItems,filterMyPropBundel.getString("Type"));
						//ProjectUtility.sys(isPrint, TAG, "From PrefereBroker--> "+myPropertyArrayAdaptor.getCount());
					} else {
						myPropertyArrayAdaptor = new MyPropertyArrayAdaptor(getActivity(), R.id.myPropListView, propertyItems);
						//ProjectUtility.sys(isPrint, TAG, "From My property listing--> "+myPropertyArrayAdaptor.getCount());
					}
					lv_myPropList_listview.setAdapter(myPropertyArrayAdaptor);
					
				} else {
					myPropertyArrayAdaptor.notifyDataSetChanged();
					flag_loading = false;
					//ProjectUtility.sys(isPrint, TAG, "Is not null---> "+myPropertyArrayAdaptor.getCount());
				}
				
			} else {
				 flag_loading = true;
				 Toast.makeText(getActivity(),((JSONObject)jsonArry.get(0)).getString(Constant.AddProperty.API_MESSAGE), Toast.LENGTH_LONG).show();
			}
			/*
			 * After load hide the refresh layout
			 */
			swp_myPropList_swipLayout.setRefreshing(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 * on resume set title
	 */
	@Override
	public void onResume() {
	   super.onResume();
	   if(filterMyPropBundel.getString("Type").equals("PrefereBroker")) {
		   MainFragmentActivity.setTitle("Properties of "+filterMyPropBundel.getString(Constant.PreferredBrokers.FIRST_NAME));
	   } else {
		   try {
			   if(jsonArry != null && !((JSONObject)jsonArry.get(0)).has(Constant.AllNotification.API_STATUS)) {
				   MainFragmentActivity.tv_actionbar_title.setText(Html.fromHtml("My Property "+"<html><body><sup><small><font size = 1	color=#ffffff>["+jsonArry.getJSONObject(0).getString(Constant.MyProperty.NO_RECORD)+"]</small></sup></font></body><html>"));
			   }
			} catch (JSONException e) {
				e.printStackTrace();
			}
	   }
	}

	@Override
	public void onComplete() {
		btn_myPropList_addProp.setEnabled(true);
	}
	
	/*@SuppressLint("ResourceAsColor")
	private void showCounter(boolean flag) {
	    try {
	    	if(flag) {
	    		System.out.println("---> showCounter <--");
	    		flagBadge = false;
		    	MainFragmentActivity.tv_actionbar_title.setText("My Property");
		    	int tvSize = getResources().getDimensionPixelSize(R.dimen.commonSpinnerTextSize);
		        
		        badge4.setBadgePosition(BadgeView.POSITION_TOP_LEFT);
				badge4.setTextSize(tvSize);
				badge4.setBadgeMargin(MainFragmentActivity.tv_actionbar_title.getWidth(), 0);
				badge4.setBadgeBackgroundColor(R.color.green);
		    	badge4.setText(jsonArry.getJSONObject(0).getString(Constant.MyProperty.NO_RECORD));
				TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
		        anim.setInterpolator(new BounceInterpolator());
		        anim.setDuration(1000);
		        badge4.toggle(anim, null);
	    	}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    
	}*/
}
