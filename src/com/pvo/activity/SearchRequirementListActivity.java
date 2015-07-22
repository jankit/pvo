package com.pvo.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;
import com.pvo.custom.adapter.SearchRequirementArrayAdaptor;
import com.pvo.custom.adapter.SearchRequirementItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.SearchRequirementService;
import com.pvo.util.Constant;


//Display the List Of Search Requirement  match the search criteria 
//in this pass the entered search Requirement detail 
public class SearchRequirementListActivity extends PVOFragment {
	
	private TextView tvSearchReqTotalRecord;
    private PullToRefreshListView searchReqListListView;
    private SearchRequirementService searchRequirementService;
    private LinearLayout searchReqNewSearchLayout;
    private LinearLayout searchReqRefineSearchlayout;
    private Bundle searchReqListIntent;
    private boolean flag_loading;
    private int pageCount = 1;
    private List<SearchRequirementItem> searchRequirementItems = new ArrayList<SearchRequirementItem>();
    private SearchRequirementArrayAdaptor searchRequirementArrayAdaptor;
    private boolean refresh = false;
	
	//set the layout content view
	public SearchRequirementListActivity() {
		setContentView(R.layout.activity_searchrequirement_list);
	}
	//END 

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
		searchReqListIntent			= getArguments();
		searchRequirementService 	= new SearchRequirementService();
		searchReqListListView 		= (PullToRefreshListView) findViewById(R.id.searchReqListListView);
		searchReqRefineSearchlayout = (LinearLayout) findViewById(R.id.searchReqRefineSearchlayout);
		tvSearchReqTotalRecord		= (TextView) findViewById(R.id.tvSearchReqTotalRecord);
		
		//on click of refine search goto the previous fragment whit fill data as you entered
		searchReqRefineSearchlayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((FragmentActivity)getActivity()).onBackPressed();
			}
		});
		//END
		
		//Move to SearchRequirementActivity
		searchReqNewSearchLayout = (LinearLayout) findViewById(R.id.searchReqNewSearchLayout);
		searchReqNewSearchLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SearchRequirementMainActivity searchRequirementActivity = new SearchRequirementMainActivity();
				((MainFragmentActivity)getActivity()).redirectScreen(searchRequirementActivity);
			}
		});
		//END
		
		//call property list methods to get property
		getMyPropertyRecords(pageCount);
		
		//on listview scroll change page number increase and call the method to get property of next page
		searchReqListListView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0) {
	                if(flag_loading == false) {
	                    flag_loading = true;
	                    getMyPropertyRecords(++pageCount);
	                }
	            }
			}
		});
		
		searchReqListListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            	pageCount = 1;
            	refresh = true;
            	getMyPropertyRecords(pageCount);
            }
        });
	}
	
	//Call web service and get records for given page number
	private void getMyPropertyRecords(int pageNumber) {
		new WebserviceClient(SearchRequirementListActivity.this, searchRequirementService).execute(
		searchReqListIntent.getString(Constant.SearchRequirement.PROPERTY_TYPE),//0)propertytype
		searchReqListIntent.getString(Constant.SearchRequirement.CMB_OPTION),//1)cmboptions
		searchReqListIntent.getString(Constant.SearchRequirement.CMB_PROPERTY_TYPE),//2)cmbpropertytype
		searchReqListIntent.getString(Constant.SearchRequirement.CMB_AREA1),//3)cmbarea1
		searchReqListIntent.getString(Constant.SearchRequirement.BED),//4)bed
		searchReqListIntent.getString(Constant.SearchRequirement.FURNISH_STATUS),//5)furnishstatus
		searchReqListIntent.getString(Constant.SearchRequirement.FLOOR),//6)floor
		searchReqListIntent.getString(Constant.SearchRequirement.RISE),//7)rise
		searchReqListIntent.getString(Constant.SearchRequirement.ST_PURPOSE),//8)stpurpose
		searchReqListIntent.getString(Constant.SearchRequirement.AREA_SQFOOT),//9)areasqfoot
		searchReqListIntent.getString(Constant.SearchRequirement.TXT_MIN_RENT),//10)txtminrent
		searchReqListIntent.getString(Constant.SearchRequirement.TXT_MAX_RENT),//11)txtmaxrent
		searchReqListIntent.getString(Constant.SearchRequirement.TXT_MIN_PRICE),//12)propertytype
		searchReqListIntent.getString(Constant.SearchRequirement.TXT_MAX_PRICE),//13)propertytype
		searchReqListIntent.getString(Constant.SearchRequirement.KEYWORD),//14)propertytype
		searchReqListIntent.getString(Constant.SearchRequirement.PLOT_AREA),//15)plotarea
		searchReqListIntent.getString(Constant.SearchRequirement.AREA_UNIT_BUNGLOW),//16)&areaunit=Sq. Yard
		searchReqListIntent.getString(Constant.SearchRequirement.CONSTR_AREA),//17)constrarea
		searchReqListIntent.getString(Constant.SearchRequirement.AREA_UNIT),//18)areaunit Sq. Foot
		searchReqListIntent.getString(Constant.SearchRequirement.CMB_TP_SCHEME),//19)cmbtpscheme
		searchReqListIntent.getString(Constant.SearchRequirement.CMB_TP_SCHEME2),//20)cmbtpscheme2
		searchReqListIntent.getString(Constant.SearchRequirement.CMB_TP_SCHEME3),//21)cmbtpscheme3
		searchReqListIntent.getString(Constant.SearchRequirement.PLOT_AREA_UNIT),//22)propertytype
		searchReqListIntent.getString(Constant.SearchRequirement.CONSTR_AREA_UNIT),//23)constructionAreaUnit
		searchReqListIntent.getString(Constant.SearchRequirement.CHK_ZONE),//24)chkXone
		searchReqListIntent.getString(Constant.SearchRequirement.PROPERTY_SUBTYPE),//25)propertySubType
		String.valueOf(StringUtils.defaultIfBlank(String.valueOf(pageNumber),"1"))//26)page
		);
	}

	@Override
	public void processResponse(Object response) {
		JSONArray jsonArry = (JSONArray) response;
		try {
			if (jsonArry != null && !((JSONObject) jsonArry.get(0)).has(Constant.SearchRequirement.API_STATUS)) {
				tvSearchReqTotalRecord.setText("Total Record:- "+jsonArry.getJSONObject(0).getString("no_record"));
				//Skipped 0 index to avoid "no of records" data
				if(refresh && pageCount == 1) {
					refresh = false;
					searchRequirementItems.clear();
				}
				
				for (int i = 1; i < jsonArry.length(); i++) {
					JSONObject jsonObj = jsonArry.getJSONObject(i);
					SearchRequirementItem searchRequirementItem = new SearchRequirementItem();
						searchRequirementItem.setFirstname(jsonObj.getString(Constant.SearchRequirement.FIRST_NAME));
						searchRequirementItem.setLastname(jsonObj.getString(Constant.SearchRequirement.LAST_NAME));
						searchRequirementItem.setCompanyname(jsonObj.getString(Constant.SearchRequirement.COMPANY_NAME));
						searchRequirementItem.setRequirementid(jsonObj.getString(Constant.SearchRequirement.REQUIREMENT_ID));
						searchRequirementItem.setPropertytype(jsonObj.getString(Constant.SearchRequirement.PROPERTY_TYPE));
						searchRequirementItem.setPropertysubtype(jsonObj.getString(Constant.SearchRequirement.PROPERTY_SUBTYPE));
						searchRequirementItem.setPurpose(jsonObj.getString(Constant.SearchRequirement.PURPOSE));
						searchRequirementItem.setFurnish(jsonObj.getInt(Constant.SearchRequirement.FURNISH));
						searchRequirementItem.setMinfloor(jsonObj.getString(Constant.SearchRequirement.MIN_FLOOR));
						searchRequirementItem.setMaxfloor(jsonObj.getString(Constant.SearchRequirement.MAX_FLOOR));
						searchRequirementItem.setMinsqfoot(jsonObj.getInt(Constant.SearchRequirement.MIN_SQFOOT));
						searchRequirementItem.setMaxsqfoot(jsonObj.getInt(Constant.SearchRequirement.MAX_SQFOOT));
						searchRequirementItem.setMinbed(jsonObj.getString(Constant.SearchRequirement.MIN_BED));
						searchRequirementItem.setMaxbed(jsonObj.getString(Constant.SearchRequirement.MAX_BED));
						searchRequirementItem.setMinplotarea(jsonObj.getString(Constant.SearchRequirement.MIN_PLOT_AREA));
						searchRequirementItem.setMaxplotarea(jsonObj.getString(Constant.SearchRequirement.MAX_PLOT_AREA));
						searchRequirementItem.setMaxconstrarea(jsonObj.getString(Constant.SearchRequirement.MAX_CONST_AREA));
						searchRequirementItem.setMinconstrarea(jsonObj.getString(Constant.SearchRequirement.MIN_CONST_AREA));
						searchRequirementItem.setStpurpose(jsonObj.getString(Constant.SearchRequirement.ST_PURPOSE));
						searchRequirementItem.setMinprice(jsonObj.getString(Constant.SearchRequirement.MIN_PRICE));
						searchRequirementItem.setMaxprice(jsonObj.getString(Constant.SearchRequirement.MAX_PRICE));
						searchRequirementItem.setMinrent(jsonObj.getString(Constant.SearchRequirement.MIN_RENT));
						searchRequirementItem.setMaxrent(jsonObj.getString(Constant.SearchRequirement.MAX_RENT));
						searchRequirementItem.setLocation1(jsonObj.getString(Constant.SearchRequirement.LOCATION1));
						searchRequirementItem.setLocation2(jsonObj.getString(Constant.SearchRequirement.LOCATION2));
						searchRequirementItem.setLocation3(jsonObj.getString(Constant.SearchRequirement.LOCATION3));
						searchRequirementItem.setDtadded(jsonObj.getString(Constant.SearchRequirement.DT_ADDED));
						searchRequirementItem.setDtupdated(jsonObj.getString(Constant.SearchRequirement.DT_UPDATED));
						searchRequirementItem.setStstatus(jsonObj.getString(Constant.SearchRequirement.ST_STATUS));
						searchRequirementItem.setLogoencoded(jsonObj.getString(Constant.SearchRequirement.LOGO_ENCODED));
						searchRequirementItem.setPhotoencoded(jsonObj.getString(Constant.SearchRequirement.PHOTO_ENCODE));
						searchRequirementItem.setLocation1name(jsonObj.getString(Constant.SearchRequirement.LOCATION1_NAME));
						searchRequirementItem.setLocation2name(jsonObj.getString(Constant.SearchRequirement.LOCATION2_NAME));
						searchRequirementItem.setLocation3name(jsonObj.getString(Constant.SearchRequirement.LOCATION3_NAME));
						searchRequirementItem.setAlllocationsname(jsonObj.getString(Constant.SearchRequirement.ALL_LOCATION_NAME));
					
						if(jsonObj.getString(Constant.SearchRequirement.PHONE_M).length() > 10) 
						    searchRequirementItem.setPhonem(jsonObj.getString(Constant.SearchRequirement.PHONE_M).substring(0, 10));
						else 
						    searchRequirementItem.setPhonem(jsonObj.getString(Constant.SearchRequirement.PHONE_M));
					
						searchRequirementItems.add(searchRequirementItem);
					}
					if(searchRequirementArrayAdaptor == null) {
						searchRequirementArrayAdaptor = new SearchRequirementArrayAdaptor(getActivity(), R.id.searchReqListListView, searchRequirementItems);
						searchReqListListView.setAdapter(searchRequirementArrayAdaptor);
					} else {
						searchRequirementArrayAdaptor.notifyDataSetChanged();
						flag_loading = false;
					}
				} else {
					if(searchRequirementArrayAdaptor == null) {
						Toast.makeText(getActivity(),((JSONObject) jsonArry.get(0)).getString(Constant.SearchRequirement.API_MESSAGE),Toast.LENGTH_LONG).show();
					} else {
						searchRequirementArrayAdaptor.notifyDataSetChanged();
						flag_loading = true;
					}
			  }
			searchReqListListView.onRefreshComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    this.getActivity().getActionBar().setTitle("Requirement Result");
	}
}
