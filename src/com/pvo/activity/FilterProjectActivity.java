package com.pvo.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.pvo.components.SpinnerItem;
import com.pvo.json.cache.CreateJsonArrayFileIntoCache;
import com.pvo.prototype.PVOFragment;
import com.pvo.util.Constant;

//This is used for search the agent by Specific criteria
public class FilterProjectActivity extends PVOFragment{
	
	private EditText filterProjectBuilderNameEt;
	private EditText filterProjectLocationEditTextView;
	private EditText filterProjectKeywordEditTextView;
	private Spinner filterProjectPriceRangeSpinner;
	private Button filterProjectSearchBotton;
	private Button filterProjectCancelBotton;
	private Spinner filterProjectBuilderIDSpinner;
	private Map<String,Integer> areaMap = new HashMap<String, Integer>();
	
	//set layout Content View 
	public FilterProjectActivity() {
		setContentView(R.layout.activity_filter_project);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		filterProjectBuilderNameEt 			= (EditText) findViewById(R.id.filterProjectBuilderNameEt);
		filterProjectLocationEditTextView   = (EditText) findViewById(R.id.filterProjectLocationEditTextView);
		filterProjectKeywordEditTextView 	= (EditText) findViewById(R.id.filterPropKeywordEditTextView);
		filterProjectPriceRangeSpinner 		= (Spinner) findViewById(R.id.filterProjectPriceRangeSpinner);
		filterProjectSearchBotton 			= (Button) findViewById(R.id.filterProjectSearchBotton);
		filterProjectCancelBotton 			= (Button) findViewById(R.id.filterProjectCancelBotton);
		
		//open area list popup window
		filterProjectLocationEditTextView.setFocusable(false);
		filterProjectLocationEditTextView.setInputType(InputType.TYPE_NULL);
		filterProjectLocationEditTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				filterProjectLocationEditTextView.setError(null);
				getAreaList();
			}
		});
		
		//Fill the builder id spinner 
		filterProjectBuilderIDSpinner = (Spinner) findViewById(R.id.filterProjectBuilderIDSpinner);
		
		//Remove the duplicate value form list 
		HashSet<String> hashSet = new HashSet<String>();
		hashSet.addAll(AdsListingActivity.builderIdList);
		AdsListingActivity.builderIdList.clear();
		AdsListingActivity.builderIdList.addAll(hashSet);
		
		//sort the list in ascending order
		Collections.sort(AdsListingActivity.builderIdList);
		ArrayAdapter<String> builderIdAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,AdsListingActivity.builderIdList);
		builderIdAdapter.setDropDownViewResource(R.layout.spinner_text);
		filterProjectBuilderIDSpinner.setAdapter(builderIdAdapter);
		
		//Fill The Budgetspinner Using HashMap
		filterProjectPriceRangeSpinner = (Spinner) findViewById(R.id.filterProjectPriceRangeSpinner);
		ArrayList<String> budgetList=new ArrayList<String>();
		budgetList.addAll(SpinnerItem.getProjectBudgetList().keySet());
		ArrayAdapter< String> projectBudgetAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,budgetList);
		projectBudgetAdapter.setDropDownViewResource(R.layout.spinner_text);
		filterProjectPriceRangeSpinner.setAdapter(projectBudgetAdapter);
		
		//call filter project listing web service 
		filterProjectSearchBotton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//check the length of the area (Location) Edit box
				String selectedAreaString = "";
				if(filterProjectLocationEditTextView.getText().length() == 0) {
					filterProjectLocationEditTextView.requestFocus();
					filterProjectLocationEditTextView.setError("Please select at least one location.");
				} else {
					//Comma Seprated String of Selected area Code
					String area = filterProjectLocationEditTextView.getText().toString();
					String[] selectedArea=area.split(" - ");
					for (int i = 0; i < selectedArea.length; i++) { 
						selectedAreaString += areaMap.get(selectedArea[i]);
						if(i != selectedArea.length-1)
							selectedAreaString += ",";
					}
				}
				
				if(filterProjectLocationEditTextView.getText().length() > 0) {
					Bundle filterProjectBundle = new  Bundle();
					filterProjectBundle.putString("Type","Filter");
					filterProjectBundle.putString(Constant.AdsListing.BUILDER_ID,filterProjectBuilderIDSpinner.getSelectedItem().toString());//BUILDER_ID
					filterProjectBundle.putString(Constant.AdsListing.BUILDER_NAME,filterProjectBuilderNameEt.getText().toString());//BUILDER_NAME
					filterProjectBundle.putString(Constant.AdsListing.AREA_NAME,selectedAreaString);//CMB_AREA1
					filterProjectBundle.putString("KEYWORD",filterProjectKeywordEditTextView.getText().toString());//TXT_KEYWORD
					filterProjectBundle.putString("PRICERANGE",filterProjectPriceRangeSpinner.getSelectedItem().toString());//PRICE_RANGE
					AdsListingActivity adsListingActivity = new AdsListingActivity();
					adsListingActivity.setArguments(filterProjectBundle);
					((MainFragmentActivity)getActivity()).redirectScreen(adsListingActivity);
				}
			}
		});
		
		//close the search project dilog
		filterProjectCancelBotton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});
	}

	@Override
	public void processResponse(Object response) {}
	
	//get the list of area(Location) from the web service 
	public void getAreaList(){
		JSONArray jsonArray = CreateJsonArrayFileIntoCache.readAreaListJsonData(getActivity().getApplicationContext());
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				areaMap.put(jsonObject.getString(Constant.Area.AREA_NAME).trim(),jsonObject.getInt(Constant.Area.AREA_ID));
			}
			openAreaListOfCityPopupWindow("areaMap", filterProjectLocationEditTextView, areaMap);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		this.getActivity().getActionBar().setTitle("Project Listing");
	}
}
