package com.pvo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.pvo.components.SpinnerItem;
import com.pvo.json.cache.CreateJsonArrayFileIntoCache;
import com.pvo.prototype.PVOFragment;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

//This is used for search the agent by Specific criteria
public class FilterPropertyActivity extends PVOFragment{
	
	private Spinner filterPropPropetyTypeSpinner;
	private Spinner searchPropForSpinner;
	private EditText filterPropLocationEditTextView;
	private EditText filterPropKeywordEditTextView;
	private Button filterPropSearchBotton;
	private Button filterPropCancelBotton;
	private String title;
	
	//Login User Session
	private UserSessionManager userSessionManager;
	
	//Area List Of Particular List 
	private Map<String,Integer> areaMap = new HashMap<String, Integer>();
	
	//set layout Content View 
	public FilterPropertyActivity() {
		setContentView(R.layout.activity_filter_property);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		userSessionManager = new UserSessionManager(getActivity());
		
		//Keyword Edit text 
		filterPropKeywordEditTextView = (EditText) findViewById(R.id.filterPropKeywordEditTextView);
		
		title = this.getActivity().getActionBar().getTitle().toString();

		//Fill the Poperty type Spinner using has map 
		filterPropPropetyTypeSpinner=(Spinner) findViewById(R.id.filterPropPropetyTypeSpinner);
		
		final ArrayList<String> filterPropertyTypeList = new ArrayList<String>();
		filterPropertyTypeList.addAll((SpinnerItem.getPropertyTypeOptionList()).keySet());
		
		ArrayAdapter< String> filterPropertyTypeAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,filterPropertyTypeList){
			@Override      
			public boolean isEnabled(int position) {
				if (position == 0) 
					return false;
				if (position == 2) 
					return false;
				if (position == 13) 
					return false;
				if (position == 22) 
					return false;
				return true;
			}

           @Override                
           public boolean areAllItemsEnabled() {
              return false;
           }

           @Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				View v = convertView;
				if (v == null) {
					Context mContext = this.getContext();
					LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					v = vi.inflate(R.layout.spinner_text, null);
				}

				TextView tv = (TextView) v.findViewById(R.id.spinnerTextView);
				tv.setText(filterPropertyTypeList.get(position));

				switch (position) {
					case 0:
						tv.setTextColor(Color.GRAY);
						tv.setTypeface(null, Typeface.ITALIC);
						break;
					case 2:
						tv.setTextColor(Color.GRAY);
						tv.setTypeface(null, Typeface.ITALIC);
						break;
					case 13:
						tv.setTextColor(Color.GRAY);
						tv.setTypeface(null, Typeface.ITALIC);
						break;
					case 22:
						tv.setTextColor(Color.GRAY);
						tv.setTypeface(null, Typeface.ITALIC);
						break;
					default:
						tv.setTextColor(Color.BLACK);
						tv.setTypeface(null, Typeface.NORMAL);
						break;
				}
				return v;
			}
		};
		
		filterPropertyTypeAdapter.setDropDownViewResource(R.layout.spinner_text);
		filterPropPropetyTypeSpinner.setAdapter(filterPropertyTypeAdapter);
		filterPropPropetyTypeSpinner.setSelection(1);
		
		//fill the Property Purpose Spinner Using Hash map
		searchPropForSpinner = (Spinner) findViewById(R.id.searchPropForSpinner);
		ArrayList<String> filterSaleRentList=new ArrayList<String>();
		filterSaleRentList.addAll((SpinnerItem.getSaleRentList()).keySet());
		ArrayAdapter< String> filterSaleRentAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,filterSaleRentList);
		filterPropertyTypeAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchPropForSpinner.setAdapter(filterSaleRentAdapter);
		
		//on click of location Edit box open the Location Popup window
		filterPropLocationEditTextView=(EditText)findViewById(R.id.filterPropLocationEditTextView);
		String areaNames = userSessionManager.getSessionValue(Constant.Login.AREA_DEALS_IN_TEXT);
		filterPropLocationEditTextView.setText(areaNames.replace(",", " - "));
		filterPropLocationEditTextView.setFocusable(false);
		filterPropLocationEditTextView.setInputType(InputType.TYPE_NULL);
		
		filterPropLocationEditTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				filterPropLocationEditTextView.setError(null);
				getAreaList();
			}
		});
		
		//click event of Find Agent Button
		filterPropSearchBotton=(Button)findViewById(R.id.filterPropSearchBotton);
		filterPropSearchBotton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Comma Seprated String of Selected area Code
				String selectedAreaString = "";
				if(areaMap != null ){
					String area = filterPropLocationEditTextView.getText().toString();
					String[] selectedArea = area.split(" - ");
					
					for (int i = 0; i < selectedArea.length; i++) {
						selectedAreaString += areaMap.get(selectedArea[i]);
						if (i != selectedArea.length - 1) {
							selectedAreaString += ",";
						}
					}
				} else {
					selectedAreaString  = userSessionManager.getSessionValue(Constant.Login.AREA_DEALS_IN).toString();
				}
					
				Bundle filterPropBundle = new  Bundle();
				filterPropBundle.putString("Type","Filter");
				filterPropBundle.putString(Constant.FilterList.PROPERTY_TYPE, String.valueOf(SpinnerItem.getPropertyTypeOptionList().get(filterPropPropetyTypeSpinner.getSelectedItem().toString())));
				filterPropBundle.putString(Constant.FilterList.PURPOSE, searchPropForSpinner.getSelectedItem().toString());
				filterPropBundle.putString(Constant.FilterList.LOCATION,selectedAreaString );
				filterPropBundle.putString(Constant.FilterList.TXT_KEYWORD,filterPropKeywordEditTextView.getText().toString());
				
				if(title.equals("My Property")) {
					MyPropertyListActivity myPropertyActivity = new MyPropertyListActivity();
					myPropertyActivity.setArguments(filterPropBundle);
					((MainFragmentActivity)getActivity()).redirectScreen(myPropertyActivity);
				} else if(title.equals("My Requirement")) {
					MyRequirementListActivity myRequirementActivity = new MyRequirementListActivity();
					myRequirementActivity.setArguments(filterPropBundle);
					((MainFragmentActivity)getActivity()).redirectScreen(myRequirementActivity);
				} else if(title.equals("Public Property")) {
					PublicPropertyListActivity publicPropertyActivity = new PublicPropertyListActivity();
					publicPropertyActivity.setArguments(filterPropBundle);
					((MainFragmentActivity)getActivity()).redirectScreen(publicPropertyActivity);
				} else if(title.equals("Public Requirement")) {
					PublicRequirementListActivity publicRequirementActivity = new PublicRequirementListActivity();
					publicRequirementActivity.setArguments(filterPropBundle);
					((MainFragmentActivity)getActivity()).redirectScreen(publicRequirementActivity);
				}
			}
		});
		
		//on click of cancel button redirect to previouse fragment
		filterPropCancelBotton = (Button) findViewById(R.id.filterPropCancelBotton);
		filterPropCancelBotton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
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
			openAreaListOfCityPopupWindow("areaMap", filterPropLocationEditTextView, areaMap);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		this.getActivity().getActionBar().setTitle("Refine Search");
	}
}
	