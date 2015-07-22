package z.com.pvo.newActivity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import z.com.pvo.loading.button.ActionProcessButton;
import z.com.pvo.loading.button.ProgressGenerator;
import z.com.pvo.util.ProjectUtility;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.pvo.activity.R;
import com.pvo.custom.adapter.MyPropertyArrayAdaptor;
import com.pvo.custom.adapter.MyPropertyItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.ShortListPropertyService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

public class ZShortListedPropertyListActivity extends PVOFragment implements ProgressGenerator.OnCompleteListener {
   
	protected Boolean isPrint = true;
	protected String TAG = "MyPropertyListActivity";
	
	private LinearLayout myPropProgresLayout;
	private ListView lv_myPropList_listview;
	private SwipeRefreshLayout swp_myPropList_swipLayout;
    private ShortListPropertyService shortListPropertyService;
    private boolean flag_loading;
    private int pageCount = 1;
    private List<MyPropertyItem> propertyItems = new ArrayList<MyPropertyItem>();
    private MyPropertyArrayAdaptor myPropertyArrayAdaptor;
    private ActionProcessButton btn_myPropList_addProp;
    private Boolean refresh = false;
	private UserSessionManager userSessionManager;
	private JSONArray jsonArry;
	
	
    /*
     * set Layout Content view 
     */
	public ZShortListedPropertyListActivity() {
		setContentView(R.layout.z_prop_myprop_list);
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void init(Bundle savedInstanceState) {
		
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		userSessionManager = new UserSessionManager(getActivity());
		shortListPropertyService = new ShortListPropertyService();
		
		lv_myPropList_listview = (ListView) findViewById(R.id.lv_myPropList_listview);
		swp_myPropList_swipLayout = (SwipeRefreshLayout) findViewById(R.id.swp_myPropList_swipLayout);
		myPropProgresLayout 	= (LinearLayout) findViewById(R.id.myPropProgresLayout);

		/*
		 * redirect to add new property screen
		 */
		btn_myPropList_addProp = (ActionProcessButton) findViewById(R.id.btn_myPropList_addProp);
		btn_myPropList_addProp.setVisibility(View.GONE);
		
		//call AdsList() to get Ads list from webservice 
		getShortListPropertyRecords(pageCount);
	
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
			                getShortListPropertyRecords(++pageCount);
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
            	getShortListPropertyRecords(pageCount);
			}
		});
	}
	
	
	//Call web service and get records for given page number
	private void getShortListPropertyRecords(final int pageNumber) {
		ProjectUtility.sys(isPrint, TAG, "getMyPropertyRecords--> "+pageNumber);
		new WebserviceClient(ZShortListedPropertyListActivity.this, shortListPropertyService, myPropProgresLayout).execute(userSessionManager.getSessionValue(Constant.Login.USER_ID), String.valueOf(pageNumber));
		
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
				
				for (int i = 1; i < jsonArry.length(); i++) {
					JSONObject jsonObj = jsonArry.getJSONObject(i);
					
					MyPropertyItem propItem = new MyPropertyItem();
					//propItem.setImage1link(jsonObj.getString(Constant.MyProperty.IMAGE1_LINK));
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
					
				}
				
				//Check Adapter is null than create and fill otherwise only notify change 
				if(myPropertyArrayAdaptor == null) {
					myPropertyArrayAdaptor = new MyPropertyArrayAdaptor(getActivity(), R.id.myPropListView, propertyItems,Constant.ShortlistedPropertyList.SHORT_LISTED);
					lv_myPropList_listview.setAdapter(myPropertyArrayAdaptor);
				} else {
					myPropertyArrayAdaptor.notifyDataSetChanged();
					flag_loading = false;
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
	   //MainFragmentActivity.tv_actionbar_title.setText("Short list Property");
		
	}

	@Override
	public void onComplete() {
		btn_myPropList_addProp.setEnabled(true);
	}
	
	/*
	 * Initialize the fragment
	 */
	public static ZShortListedPropertyListActivity newInstance() {
		ZShortListedPropertyListActivity f = new ZShortListedPropertyListActivity();
		Bundle b = new Bundle();
		f.setArguments(b);
		return f;
	}
}
