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
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;
import com.pvo.custom.adapter.SearchPropertyArrayAdaptor;
import com.pvo.custom.adapter.SearchPropertyItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.SearchPropertyService;
import com.pvo.util.Constant;


// Display the List Of Search Requirement  match the search criteria 
// in this pass the entered search Requirement detail 
public class SearchPropertyListActivity extends PVOFragment {
	
	private TextView tvSearchPropTotalRecord;
	//private ListView searchPropListView;
	private PullToRefreshListView searchPropListView;
	private SearchPropertyService searchPropertyService;
	private LinearLayout searchPropNewSearchLayout;
	private LinearLayout searchPropRefineSearchLayout;
	private Bundle intent;
	private boolean flag_loading;
	private int pageCount = 1;
	private List<SearchPropertyItem> searchPropertyItems = new ArrayList<SearchPropertyItem>();
	private SearchPropertyArrayAdaptor searchPropertyArrayAdaptor;
	private Boolean refresh = false;
	
	//Set the layout content view
	public SearchPropertyListActivity() {
		setContentView(R.layout.activity_searchproperty_list);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		intent=getArguments();
		searchPropertyService 		= new SearchPropertyService();
		searchPropListView 			= (PullToRefreshListView) findViewById(R.id.searchPropListView);
		searchPropNewSearchLayout 	= (LinearLayout) findViewById(R.id.searchPropNewSearchLayout);
		tvSearchPropTotalRecord		= (TextView) findViewById(R.id.tvSearchPropTotalRecord);
		//Redirect to previous fragment with previouse state 
		searchPropRefineSearchLayout = (LinearLayout) findViewById(R.id.searchPropRefineSearchLayout);
		searchPropRefineSearchLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});
		
		// call the search Property list service
		getSearchPropertyRecords(pageCount);
		searchPropListView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if(firstVisibleItem != 0) {	
					if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0) {
						if(flag_loading == false) {
							flag_loading = true;
		                    getSearchPropertyRecords(++pageCount);
		                }
		            }
				}
			}
		});
		
		searchPropListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            	pageCount = 1;
            	refresh = true;
            	getSearchPropertyRecords(pageCount);
            }
        });
		
		
		//Move to SearchPropertyActivity For new property Search
		searchPropNewSearchLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SearchPropertyMainActivity searchPropertyActivity = new SearchPropertyMainActivity();
				((MainFragmentActivity)getActivity()).redirectScreen(searchPropertyActivity);
			}
		});
		//END
	}
	
	//Call web service and get records for given page number
	private void getSearchPropertyRecords(int pageNumber) {
		new WebserviceClient(SearchPropertyListActivity.this, searchPropertyService).execute(
			intent.getString(Constant.SearchProperty.PROPERTY_TYPE),//0)"propertytype"
			intent.getString(Constant.SearchProperty.CMB_OPTION),//1)"cmboptions"
			intent.getString(Constant.SearchProperty.CMB_PROPERTY_TYPE),//2)"cmbpropertytype"
			intent.getString(Constant.SearchProperty.CMB_AREA1),//3)"cmbarea1"
			intent.getString(Constant.SearchProperty.PROPERTY_SUB_TYPE),//4)"propertysubtype"
			intent.getString(Constant.SearchProperty.KEYWORD),//5)"keyword"
			intent.getString(Constant.SearchProperty.MIN_AREA),//6)"minarea"
			intent.getString(Constant.SearchProperty.MAX_AREA),//7)"maxarea"
			intent.getString(Constant.SearchProperty.TXT_MIN_AREA),//8)"minarea"
			intent.getString(Constant.SearchProperty.TXT_MAX_AREA),//9)"maxarea"
			intent.getString(Constant.SearchProperty.TXT_MIN_PRICE),//10)"txtminprice"
			intent.getString(Constant.SearchProperty.TXT_MAX_PRICE),//11)"txtmaxprice"
			intent.getString(Constant.SearchProperty.TXT_MIN_RENT),//12)"txtminrent"
			intent.getString(Constant.SearchProperty.TXT_MAX_RENT),//13)"txtmaxrent"
			intent.getString(Constant.SearchProperty.CMB_MIN_BED),//14)"cmbminbed"
			intent.getString(Constant.SearchProperty.CMB_MAX_BED),//15)"cmbmaxbed"
			intent.getString(Constant.SearchProperty.CMB_MIN_FLOOR),//16)"cmbminfloor"
			intent.getString(Constant.SearchProperty.CMB_MAX_FLOOR),//17)"cmbmaxfloor"
			intent.getString(Constant.SearchProperty.CMB_PURPOSE),//18)"cmbpurpose"
			intent.getString(Constant.SearchProperty.RD_RISE),//19)"rdrise"
			intent.getString(Constant.SearchProperty.MIN_PLOT_AREA),//20)"minplotarea"
			intent.getString(Constant.SearchProperty.MAX_PLOT_AREA),//21)"maxplotarea"
			intent.getString(Constant.SearchProperty.MIN_CONSTRUCTION_AREA),//22)"minconstructionarea"
			intent.getString(Constant.SearchProperty.MAX_CONSTRUCTION_AREA),//23)"maxconstructionarea"
			intent.getString(Constant.SearchProperty.TXT_MIN_PLOT_AREA),//24)"minplotarea"
			intent.getString(Constant.SearchProperty.TXT_MAX_PLOT_AREA),//25)"maxplotarea"
			intent.getString(Constant.SearchProperty.TXT_MIN_CONSTRUCTION_AREA),//26)"minconstructionarea"
			intent.getString(Constant.SearchProperty.TXT_MAX_CONSTRUCTION_AREA),//27)"maxconstructionarea"
			intent.getString(Constant.SearchProperty.FURNISH_STATUS),//28)"furnishoption"
			intent.getString(Constant.SearchProperty.CHK_ZONE),//29)"chkzone"
			intent.getString(Constant.SearchProperty.CMB_TP_SCHEME),//30)"tpScheme1"
			intent.getString(Constant.SearchProperty.NAVI_SHARAT),//31)"naviSharat"
			intent.getString(Constant.SearchProperty.JUNI_SHARAT),//32)"junisharat"
			intent.getString(Constant.SearchProperty.KHETI),//33)"kheti"
			intent.getString(Constant.SearchProperty.PRASSAP),//34)"prasha"
			intent.getString(Constant.SearchProperty.DISPUTE),//35)"dispute"
			intent.getString(Constant.SearchProperty.SHREE_SARKAR),//36)"shreeSarkar"
			intent.getString(Constant.SearchProperty.NA_STATUS),//37)"naStatus"
			String.valueOf(pageNumber)//38)page
			);
	}
	
	//get response from search property service
	@Override
	public void processResponse(Object response) {
		JSONArray jsonArry = (JSONArray) response;
		try {
			if (jsonArry != null && !((JSONObject) jsonArry.get(0)).has("status")) {
				tvSearchPropTotalRecord.setText("Total Record:- "+jsonArry.getJSONObject(0).getString("no_record"));
				if(refresh && pageCount == 1) {
					refresh = false;
					searchPropertyItems.clear();
				}
				for (int i = 1; i < jsonArry.length(); i++) {
					JSONObject jsonObj = jsonArry.getJSONObject(i);
					SearchPropertyItem searchPropertyItem = new SearchPropertyItem();
					searchPropertyItem.setPropertyid(jsonObj.getString(Constant.SearchProperty.PROPERTY_ID));
					searchPropertyItem.setPropertytype(jsonObj.getString(Constant.SearchProperty.PROPERTY_TYPE));
					searchPropertyItem.setStroptions(jsonObj.getString(Constant.SearchProperty.STR_OPTIONS));
					searchPropertyItem.setMinarea(jsonObj.getString(Constant.SearchProperty.MIN_AREA));
					searchPropertyItem.setMaxarea(jsonObj.getString(Constant.SearchProperty.MAX_AREA));
					searchPropertyItem.setMaxplotarea(jsonObj.getString(Constant.SearchProperty.MAX_PLOT_AREA));
					searchPropertyItem.setMinplotarea(jsonObj.getString(Constant.SearchProperty.MIN_PLOT_AREA));
					searchPropertyItem.setAreaunit(jsonObj.getString(Constant.SearchProperty.AREA_UNIT));
					searchPropertyItem.setBed(jsonObj.getInt(Constant.SearchProperty.BED));
					searchPropertyItem.setFloor(jsonObj.getString(Constant.SearchProperty.FLOOR));
					searchPropertyItem.setPurpose(jsonObj.getString(Constant.SearchProperty.PURPOSE));
					searchPropertyItem.setRise(jsonObj.getString(Constant.SearchProperty.RISE));
					searchPropertyItem.setRent(jsonObj.getString(Constant.SearchProperty.RENT));
					searchPropertyItem.setFurnishstatus(jsonObj.getInt(Constant.SearchProperty.FURNISH_STATUS));
					searchPropertyItem.setDtadded(jsonObj.getString(Constant.SearchProperty.DT_ADDED));
					searchPropertyItem.setAddress(jsonObj.getString(Constant.SearchProperty.ADDRESS));
					searchPropertyItem.setDtupdated(jsonObj.getString(Constant.SearchProperty.DT_UPDATE));
					searchPropertyItem.setPlotareaunit(jsonObj.getString(Constant.SearchProperty.PLOT_AREA_UNIT));
					searchPropertyItem.setLogoencoded(jsonObj.getString(Constant.SearchProperty.LOGO_ENCODED));
					searchPropertyItem.setPhotoencoded(jsonObj.getString(Constant.SearchProperty.PHOTO_ENCODED));
					searchPropertyItem.setTotalprice(jsonObj.getString(Constant.SearchProperty.TOTAL_PRICE));
					searchPropertyItem.setAreaname(jsonObj.getString(Constant.SearchProperty.AREA_NAME));
					searchPropertyItem.setLandmark1name(jsonObj.getString(Constant.SearchProperty.LANDMARK1_NAME));
					searchPropertyItem.setLandmark2name(jsonObj.getString(Constant.SearchProperty.LANDMARK2_NAME));
					searchPropertyItem.setFirstname(jsonObj.getString(Constant.SearchProperty.FIRSTNAME));
					searchPropertyItem.setLastname(jsonObj.getString(Constant.SearchProperty.LASTNAME));
					searchPropertyItem.setPhonem(jsonObj.getString(Constant.SearchProperty.PHONEM));
					searchPropertyItems.add(searchPropertyItem);
				}
				if(searchPropertyArrayAdaptor == null) {
					searchPropertyArrayAdaptor = new SearchPropertyArrayAdaptor(getActivity(), R.id.searchPropListView, searchPropertyItems);
					searchPropListView.setAdapter(searchPropertyArrayAdaptor);
				} else {
					searchPropertyArrayAdaptor.notifyDataSetChanged();
					flag_loading = false;
				}
			} else {
				Toast.makeText(getActivity(),((JSONObject) jsonArry.get(0)).getString(Constant.SearchProperty.API_MESSAGE),Toast.LENGTH_LONG).show();
				if(searchPropertyArrayAdaptor != null) {
					searchPropertyArrayAdaptor.notifyDataSetChanged();
					flag_loading = true;
				}
				
			}
			searchPropListView.onRefreshComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    this.getActivity().getActionBar().setTitle("Property Result");
	}
}
