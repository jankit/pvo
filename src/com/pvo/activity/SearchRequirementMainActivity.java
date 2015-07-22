package com.pvo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.components.CheckBoxItem;
import com.pvo.components.SpinnerItem;
import com.pvo.json.cache.CreateJsonArrayFileIntoCache;
import com.pvo.prototype.PVOFragment;
import com.pvo.util.Constant;
import com.pvo.util.JSONUtils;

//This is used for search the agent by Specific criteria
public class SearchRequirementMainActivity extends PVOFragment{
	
	private Spinner searchReqPropTypeSpinner;
	private Spinner searchReqForSpinner;
	private EditText arearEditBox;
	private EditText searchReqKeywordEditBox;
	private TextView searchReqSelectAreaTV;
	
	//Min/Max Rent 
	private RelativeLayout searchReqRentLayout;
	private Spinner searchReqRentMinSpinner;
	private Spinner searchReqRentMaxSpinner;
	private TextView searchReqMaxRentErrorMsg;
	private TextView searchReqMinRentErrorMsg;
	private TextView searchReqRentTv;
	
	//Min/Max Price Layout
	private RelativeLayout searchReqTotalPriceLayout;
	private Spinner searchReqTotalPriceMinSpinner;
	private Spinner searchReqTotalPriceMaxSpinner;
	private TextView searchReqMaxPriceErrorMsg;
	private TextView searchReqMinPriceErrorMsg;
	private TextView searchReqTotalPriceTv;
	
	//Purpose Layout
	private RelativeLayout searchReqPurposeLayout;
	private Spinner searchReqPurposeSpinner;

	//Area Layout
	private RelativeLayout searchReqAreaLayout;
	private TextView searchReqAreaTv;
	private EditText searchReqAreaMinEditBox;
	
	//Plot Area Layout
	private RelativeLayout searchReqPlotAreaLayout;
	private EditText searchReqPlotAreaMinEditBox;
	private TextView searchReqPlotAreaTv;
	
	//Construction Area
	private RelativeLayout searchReqComBunglowConstLayout;
	private EditText searchReqConstructionAreaMinEditBox;
	private TextView searchReqConstructionAreaTv;
	
	//Bed Layout
	private RelativeLayout searchReqBedLayout;
	private Spinner searchReqBedMinSpinner;
	
	//Floor Layout
	private RelativeLayout searchReqFloorLayout;
	private Spinner searchReqFloorMinSpinner;
	
	//Building Type Preference 
	private RelativeLayout searchReqBuildingTypeLayout;
	private Spinner searchReqBuildingTypeSpinner;
	
	//Furnish Option Layout 
	private RelativeLayout searchReqFurnishOptionLayout;
	private Spinner searchReqFurnishOptionSpinner;
	
	//T.P.Scheme Layout
	private RelativeLayout searchReqTPSchemeLayout;
	private Spinner searchReqTPSchemeSpinner1;
	private Spinner searchReqTPSchemeSpinner2;
	private Spinner searchReqTPSchemeSpinner3;
	private String tpScheme1Id;
	private String tpScheme2Id;
	private String tpScheme3Id;
	
	//Zone Layout
	private LinearLayout searchReqZoneLayout;
	private CheckBox zoneCheckBox;
	private ArrayList<String> zoneArray;
	
	//N.A Layout
	private RelativeLayout searchReqNALayout;
	private Spinner searchReqNASpinner;
	
	//Various Button for Search,Proceed,cancel,back
	private Button searchReqMoreOptionButton;
	private Button searchReqBackButton;
	private Button SearchReqShowResultButton;
	private RelativeLayout searchReqFlatLayout;
	private LinearLayout searchReqButtonLayout;
	private Map<String,Integer> areaMap = new HashMap<String, Integer>();
	
	//Set the layout content view
	public SearchRequirementMainActivity() {
		setContentView(R.layout.activity_searchrequirement_main);
	}
	
	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		searchReqFlatLayout = (RelativeLayout)findViewById(R.id.searchReqFlatLayout);
		searchReqButtonLayout = (LinearLayout) findViewById(R.id.searchReqButtonLayout);
		
		//Spinner For Sale/Rent
		searchReqForSpinner=(Spinner)findViewById(R.id.searchReqForSpinner);
		ArrayList<String> searchReqSaleRentList=new ArrayList<String>();
		searchReqSaleRentList.addAll(SpinnerItem.getSaleRentList().keySet());
		ArrayAdapter< String> searchReqSaleRentAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,searchReqSaleRentList);
		searchReqSaleRentAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchReqSaleRentAdapter.remove("Select");
		searchReqForSpinner.setAdapter(searchReqSaleRentAdapter);
		//set visible layout according to sale or rent 
		searchReqForSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View v,int arg2, long arg3) {
				if(searchReqForSpinner.getSelectedItem().toString().equals("Rent")) {
					searchReqRentLayout.setVisibility(View.VISIBLE); 
					searchReqTotalPriceLayout.setVisibility(View.GONE);
				} else {
					searchReqTotalPriceLayout.setVisibility(View.VISIBLE);
					searchReqRentLayout.setVisibility(View.GONE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		//Keyword Edit Box	
		searchReqKeywordEditBox = (EditText) findViewById(R.id.searchReqKeywordEditBox);
		
		//Area Layout
		searchReqAreaLayout 		= (RelativeLayout)findViewById(R.id.searchReqAreaLayout);
		searchReqAreaTv 			= (TextView) findViewById(R.id.searchReqAreaTv);
		searchReqAreaMinEditBox 	= (EditText) findViewById(R.id.searchReqAreaMinEditBox);
		searchReqSelectAreaTV 		= (TextView) findViewById(R.id.searchReqTextView4);
		searchReqSelectAreaTV.setText(Html.fromHtml("Select Area " + "<sup><font size=5 color=red>*</font></sup>"));
		
		//Plot Area Layout
		searchReqPlotAreaLayout 	= (RelativeLayout) findViewById(R.id.searchReqPlotAreaLayout);
		searchReqPlotAreaMinEditBox = (EditText) findViewById(R.id.searchReqPlotAreaMinEditBox);
		searchReqPlotAreaTv 		= (TextView) findViewById(R.id.searchReqPlotAreaTv);
		
		//Purpose Layout
		searchReqPurposeLayout 		= (RelativeLayout) findViewById(R.id.searchReqPurposeLayout);
		
		//fill the spinner using hash map 1)commercial 2)residential
		searchReqPurposeSpinner 	= (Spinner) findViewById(R.id.searchReqPurposeSpinner);
		ArrayList<String> searchReqPurposeList = new ArrayList<String>();
		searchReqPurposeList.addAll(SpinnerItem.getPurposeList().keySet());
		ArrayAdapter< String> searchReqPurposeAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,searchReqPurposeList);
		searchReqPurposeAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchReqPurposeSpinner.setAdapter(searchReqPurposeAdapter);
		//END
		
		//Construction area 
		searchReqComBunglowConstLayout 		= (RelativeLayout) findViewById(R.id.searchReqComBunglowConstLayout);
		searchReqConstructionAreaMinEditBox = (EditText) findViewById(R.id.searchReqConstructionAreaMinEditBox);
		searchReqConstructionAreaTv 		= (TextView) findViewById(R.id.searchReqConstructionAreaTv);
		
		//T.P.Scheme Layout
		searchReqTPSchemeLayout 			= (RelativeLayout)findViewById(R.id.searchReqTPSchemeLayout);
		searchReqTPSchemeSpinner1 			= (Spinner) findViewById(R.id.searchReqTPSchemeSpinner1);
		searchReqTPSchemeSpinner2 			= (Spinner) findViewById(R.id.searchReqTPSchemeSpinner2);
		searchReqTPSchemeSpinner3 			= (Spinner) findViewById(R.id.searchReqTPSchemeSpinner3);
		
		//Create the Zone Check Box and get the Text and store into the Array Layout
		searchReqZoneLayout 				= (LinearLayout) findViewById(R.id.searchReqZoneLayout);
		zoneArray 							= new ArrayList<String>();
		for(int i = 0; i < CheckBoxItem.zoneCheckItem.length; i++){
			zoneCheckBox = new CheckBox(getActivity());
			zoneCheckBox.setId(i);
			zoneCheckBox.setTextColor(Color.BLACK);
			zoneCheckBox.setText(CheckBoxItem.zoneCheckItem[i]);
			zoneCheckBox.setButtonDrawable(R.xml.custom_checkbox);
			searchReqZoneLayout.addView(zoneCheckBox);
			//get the checked zone Id and add into the Zone Array
			zoneCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked)
							zoneArray.add(buttonView.getText().toString());
						else
							zoneArray.remove(buttonView.getText().toString());
				}
			});
			//END
		}
		
		//Bed Layout Fill the bed Spinner using hash map
		searchReqBedLayout 			= (RelativeLayout)findViewById(R.id.searchReqBedLayout);
		searchReqBedMinSpinner 		= (Spinner) findViewById(R.id.searchReqBedMinSpinner);
		ArrayList<String> bedList 	= new ArrayList<String>();
		bedList.addAll(SpinnerItem.getBedList().keySet());
		ArrayAdapter< String> bedAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,bedList);
		bedAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchReqBedMinSpinner.setAdapter(bedAdapter);
		//END BED
		
		//Fill the N.A Spinner Using Hash Map.---> N.A Layout 
		searchReqNALayout 				= (RelativeLayout)findViewById(R.id.searchReqNALayout);
		searchReqNASpinner 				= (Spinner) findViewById(R.id.searchReqNASpinner);
		ArrayList<String> naStatusList 	= new ArrayList<String>();
		naStatusList.addAll(SpinnerItem.getNaStatusList().keySet());
		ArrayAdapter< String> naStatusAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,naStatusList);
		naStatusAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchReqNASpinner.setAdapter(naStatusAdapter);
		//END 
		
		//Fill the Floor spinner Using hashmap 
		searchReqFloorLayout = (RelativeLayout)findViewById(R.id.searchReqFloorLayout);
		searchReqFloorMinSpinner = (Spinner) findViewById(R.id.searchReqFloorMinSpinner);
		ArrayList<String> minFloorList = new ArrayList<String>();
		minFloorList.addAll(SpinnerItem.getFloorList().keySet());
		ArrayAdapter< String> minFloorAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,minFloorList);
		minFloorAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchReqFloorMinSpinner.setAdapter(minFloorAdapter);
		//END FLOOR 
		
		//Fill the Total Price spinner Using hashmap
		searchReqTotalPriceLayout = (RelativeLayout) findViewById(R.id.searchReqTotalPriceLayout);
		searchReqTotalPriceMinSpinner = (Spinner) findViewById(R.id.searchReqTotalPriceMinSpinner); 
		searchReqTotalPriceMaxSpinner = (Spinner) findViewById(R.id.searchReqTotalPriceMaxSpinner);
		searchReqTotalPriceTv = (TextView) findViewById(R.id.searchReqTotalPriceTv);
		searchReqTotalPriceTv.setText(Html.fromHtml("Budget"+"<sup><font size=5 color=red>*</font></sup>"));
		
		ArrayList<String> minTotalPriceList = new ArrayList<String>();
		minTotalPriceList.addAll(SpinnerItem.getTotalPriceList().keySet());
		ArrayAdapter< String> minTotalPriceAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,minTotalPriceList);
		minTotalPriceAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchReqTotalPriceMinSpinner.setAdapter(minTotalPriceAdapter);
		searchReqTotalPriceMaxSpinner.setAdapter(minTotalPriceAdapter);
		
		//error msg textview for price
		searchReqMaxPriceErrorMsg = (TextView) findViewById(R.id.searchReqMaxPriceErrorMsg);
		searchReqMinPriceErrorMsg = (TextView) findViewById(R.id.searchReqMinPriceErrorMsg);
		searchReqTotalPriceMinSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				searchReqMinPriceErrorMsg.setVisibility(View.GONE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});	
	
		searchReqTotalPriceMaxSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				searchReqMaxPriceErrorMsg.setVisibility(View.GONE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		//END Price
		
		//Fill the Rent spinner Using hashmap
		searchReqRentLayout = (RelativeLayout) findViewById(R.id.searchReqRentLayout);
		searchReqRentMinSpinner = (Spinner) findViewById(R.id.searchReqRentMinSpinner);
		searchReqRentMaxSpinner = (Spinner) findViewById(R.id.searchReqRentMaxSpinner);
		
		searchReqRentTv = (TextView) findViewById(R.id.searchReqRentTv);
		searchReqRentTv.setText(Html.fromHtml("Rent"+"<sup><font size=5 color=red>*</font></sup>"));
		
		ArrayList<String> minRentList = new ArrayList<String>();
		minRentList.addAll(SpinnerItem.getRentList().keySet());
		ArrayAdapter< String> minRentAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,minRentList);
		minRentAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchReqRentMinSpinner.setAdapter(minRentAdapter);
		searchReqRentMaxSpinner.setAdapter(minRentAdapter);
		
		//error msg textview for Rent
		searchReqMaxRentErrorMsg = (TextView) findViewById(R.id.searchReqMaxRentErrorMsg);
		searchReqMinRentErrorMsg = (TextView) findViewById(R.id.searchReqMinRentErrorMsg);
		searchReqRentMinSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				searchReqMinRentErrorMsg.setVisibility(View.GONE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});	
	
		searchReqRentMaxSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				searchReqMaxRentErrorMsg.setVisibility(View.GONE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		//END Rent
		
		//Fill The Building Type spinner Using HashMap
		searchReqBuildingTypeLayout = (RelativeLayout)findViewById(R.id.searchReqBuildingTypeLayout);
		searchReqBuildingTypeSpinner = (Spinner) findViewById(R.id.searchReqBuildingTypeSpinner);
		ArrayList<String> buildingTypeList = new ArrayList<String>();
		buildingTypeList.addAll(SpinnerItem.getBuildingTypeList().keySet());
		ArrayAdapter< String> buildingTypeAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,buildingTypeList);
		
		buildingTypeAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchReqBuildingTypeSpinner.setAdapter(buildingTypeAdapter);
		//End 

		//Fill the Furnish Option spinner Using hashmap
		searchReqFurnishOptionLayout = (RelativeLayout) findViewById(R.id.searchReqFurnishOptionLayout);
		searchReqFurnishOptionSpinner = (Spinner)findViewById(R.id.searchReqFurnishOptionSpinner);
		ArrayList<String> furnishOptionList = new ArrayList<String>();
		furnishOptionList.addAll(SpinnerItem.getFurnishOptionList().keySet());
		ArrayAdapter< String> furnishOptionAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,furnishOptionList);
		furnishOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchReqFurnishOptionSpinner.setAdapter(furnishOptionAdapter);
		//End 
		//On Click of Multiple Area selection Edit Box open Popup Window
		arearEditBox = (EditText)findViewById(R.id.searchReqAreaEditBox);
		arearEditBox.setFocusable(false);
		arearEditBox.setInputType(InputType.TYPE_NULL);
		arearEditBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				arearEditBox.setError(null);
				getAreaList();
			}
		});
		//END
		
		//Fill the Search Property Type Spinner Using hash Map
		searchReqPropTypeSpinner = (Spinner)findViewById(R.id.searchReqSpinner);
		final ArrayList<String> propertyTypeOptionList = new ArrayList<String>();
		propertyTypeOptionList.addAll(SpinnerItem.getPropertyTypeOptionList().keySet());
		ArrayAdapter< String> prpoertyTypeOptionAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,propertyTypeOptionList){
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
				tv.setText(propertyTypeOptionList.get(position));

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
		prpoertyTypeOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
		prpoertyTypeOptionAdapter.remove(" Select ");
		searchReqPropTypeSpinner.setAdapter(prpoertyTypeOptionAdapter);
		searchReqPropTypeSpinner.setSelection(1);//set default selected
		
		//Reset all data when property type change
		searchReqPropTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if(position != 0)
					resetData();
				
				showHidelayout();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
		});
		
		//Proceed Button Proceed to search Specific criteria Property
		searchReqMoreOptionButton = (Button)findViewById(R.id.searchReqProceedButton);
		searchReqMoreOptionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showHidelayout();
				searchReqMoreOptionButton.setVisibility(View.GONE);
				searchReqFlatLayout.setVisibility(View.VISIBLE);
			}
		});
		
		//	Search Result Button click Event
		//	on click of Show result Button pass the value to the SearchRequirementListActivity.java
		SearchReqShowResultButton = (Button) findViewById(R.id.SearchReqShowResultButton);
		SearchReqShowResultButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!checkLocation() && !checkMinMaxPriceRent()) {
					String selectedAreaString = "";
					String area=arearEditBox.getText().toString();
					String[] selectedArea=area.split(" - ");
					
					for (int i = 0; i < selectedArea.length; i++) { // Comma Seprated String of Selected area Code
						selectedAreaString += areaMap.get(selectedArea[i]);
						if(i != selectedArea.length-1){
							selectedAreaString += ",";
						}
					}
					
					Bundle searchRequirementBundle = new Bundle();
    				searchRequirementBundle.putString("Type","Search");
    				//Flat - sale
    				searchRequirementBundle.putString(Constant.SearchRequirement.CMB_PROPERTY_TYPE, searchReqPropTypeSpinner.getSelectedItem().toString());
    				searchRequirementBundle.putString(Constant.SearchRequirement.CMB_OPTION, searchReqForSpinner.getSelectedItem().toString());
    				searchRequirementBundle.putString(Constant.SearchRequirement.CMB_AREA1, selectedAreaString);
    				searchRequirementBundle.putString(Constant.SearchRequirement.BED, SpinnerItem.getBedList().get(searchReqBedMinSpinner.getSelectedItem()).toString());
    				searchRequirementBundle.putString(Constant.SearchRequirement.FURNISH_STATUS, String.valueOf(SpinnerItem.getFurnishOptionList().get(searchReqFurnishOptionSpinner.getSelectedItem().toString())));
    				searchRequirementBundle.putString(Constant.SearchRequirement.FLOOR,String.valueOf(SpinnerItem.getFloorList().get(searchReqFloorMinSpinner.getSelectedItem().toString())));
    				searchRequirementBundle.putString(Constant.SearchRequirement.RISE,String.valueOf(SpinnerItem.getBuildingTypeList().get(searchReqBuildingTypeSpinner.getSelectedItem().toString())));
    				searchRequirementBundle.putString(Constant.SearchRequirement.ST_PURPOSE,searchReqPurposeSpinner.getSelectedItem().toString());
    				searchRequirementBundle.putString(Constant.SearchRequirement.AREA_SQFOOT,searchReqAreaMinEditBox.getText().toString());
    				searchRequirementBundle.putString(Constant.SearchRequirement.TXT_MIN_PRICE,String.valueOf(SpinnerItem.getTotalPriceList().get(searchReqTotalPriceMinSpinner.getSelectedItem().toString())));
    				searchRequirementBundle.putString(Constant.SearchRequirement.TXT_MAX_PRICE,String.valueOf(SpinnerItem.getTotalPriceList().get(searchReqTotalPriceMaxSpinner.getSelectedItem().toString())));
    				searchRequirementBundle.putString(Constant.SearchRequirement.KEYWORD,searchReqKeywordEditBox.getText().toString());
    				//Flat - Rent
    				searchRequirementBundle.putString(Constant.SearchRequirement.TXT_MIN_RENT,String.valueOf(SpinnerItem.getRentList().get(searchReqRentMinSpinner.getSelectedItem().toString())));
    				searchRequirementBundle.putString(Constant.SearchRequirement.TXT_MAX_RENT,String.valueOf(SpinnerItem.getRentList().get(searchReqRentMaxSpinner.getSelectedItem().toString())));
    				//Bunglow - rent
    				searchRequirementBundle.putString(Constant.SearchRequirement.PROPERTY_TYPE, String.valueOf(SpinnerItem.getPropertyTypeOptionList().get(searchReqPropTypeSpinner.getSelectedItem().toString())));
    				searchRequirementBundle.putString(Constant.SearchRequirement.PROPERTY_SUBTYPE,searchReqPropTypeSpinner.getSelectedItem().toString());
    				searchRequirementBundle.putString(Constant.SearchRequirement.PLOT_AREA,searchReqPlotAreaMinEditBox.getText().toString());
    				searchRequirementBundle.putString(Constant.SearchRequirement.AREA_UNIT_BUNGLOW,"Sq.Yard");
    				searchRequirementBundle.putString(Constant.SearchRequirement.CONSTR_AREA,searchReqConstructionAreaMinEditBox.getText().toString());
    				//Bunglow-Sale
    				//Plot-Rent
    				searchRequirementBundle.putString(Constant.SearchRequirement.CMB_TP_SCHEME,StringUtils.defaultIfBlank(tpScheme1Id,""));
    				searchRequirementBundle.putString(Constant.SearchRequirement.CMB_TP_SCHEME2,StringUtils.defaultIfBlank(tpScheme2Id,""));
    				searchRequirementBundle.putString(Constant.SearchRequirement.CMB_TP_SCHEME3,StringUtils.defaultIfBlank(tpScheme3Id,""));
    				searchRequirementBundle.putString(Constant.SearchRequirement.CHK_ZONE,StringUtils.join(zoneArray,","));
    				//Plot -sale
    				//Shop-rent
    				searchRequirementBundle.putString(Constant.SearchRequirement.AREA_UNIT,"Sq.Foot");
    				searchRequirementBundle.putString(Constant.SearchRequirement.PLOT_AREA_UNIT,"Sq.Foot");
    				searchRequirementBundle.putString(Constant.SearchRequirement.CONSTR_AREA_UNIT,"Sq.Foot");

					//redirect to Search Requirement List Activity 
					SearchRequirementListActivity searchRequirementListActivity = new SearchRequirementListActivity();
					searchRequirementListActivity.setArguments(searchRequirementBundle);
					((MainFragmentActivity)getActivity()).redirectScreen(searchRequirementListActivity);
				}
			}
			
		});
		
		//Back Button Click Event
		searchReqBackButton = (Button) findViewById(R.id.searchReqBackButton);
		searchReqBackButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((FragmentActivity)getActivity()).onBackPressed();
			}
		});
		// END
		
	}

	//Get the Process Response of the Search Requirement Web Service
	@Override
	public void processResponse(Object response) {
		JSONArray jsonArray = (JSONArray) response;
		try {
			if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.SearchRequirement.API_STATUS)) {
				Toast.makeText(getActivity(),((JSONObject) jsonArray.get(0)).getString(Constant.SearchRequirement.API_MESSAGE), Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getActivity(),((JSONObject) jsonArray.get(0)).getString(Constant.SearchRequirement.API_MESSAGE), Toast.LENGTH_LONG).show();
			}
		} catch(JSONException e) {
			e.printStackTrace();
		}
	}
	//END
	
	//Set the title according to fragment onResume
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    this.getActivity().getActionBar().setTitle("Search Requirement");
	}
	//END
	
	//get the list of area(Location)
	public void getAreaList(){
		JSONArray jsonArray = CreateJsonArrayFileIntoCache.readAreaListJsonData(getActivity().getApplicationContext());
		try {
			if(jsonArray != null && !((JSONObject)jsonArray.get(0)).has(Constant.Area.API_STATUS)) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					areaMap.put(jsonObject.getString(Constant.Area.AREA_NAME).trim(),jsonObject.getInt(Constant.Area.AREA_ID));
				}
				openAreaListOfCityPopupWindow("areaMap", arearEditBox, areaMap);
			} else {
				Toast.makeText(getActivity(), "No area found", Toast.LENGTH_LONG).show();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	//END
	
	//get the list of TP scheme Web Service for TPScheme This web service is used to set the TPScheme Spinner 
	public void getTpSchem(){
		final JSONArray jsonArray = CreateJsonArrayFileIntoCache.readTPSchemeListJsonData(getActivity().getApplicationContext());
		try {
			if(jsonArray != null) {
				ArrayAdapter<String> tpSchemeAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,JSONUtils.getList(jsonArray, Constant.TPSchemesListing.TITLE));
				tpSchemeAdapter.setDropDownViewResource(R.layout.spinner_text);
				searchReqTPSchemeSpinner1.setAdapter(tpSchemeAdapter);
				searchReqTPSchemeSpinner2.setAdapter(tpSchemeAdapter);
				searchReqTPSchemeSpinner3.setAdapter(tpSchemeAdapter);
				
				searchReqTPSchemeSpinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View view,int position, long arg3) {
						try {
							tpScheme1Id = jsonArray.getJSONObject(position).getString(Constant.TPSchemesListing.TP_ID);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {}
				});
				
				searchReqTPSchemeSpinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View view,int position, long arg3) {
						try {
							tpScheme2Id = jsonArray.getJSONObject(position).getString(Constant.TPSchemesListing.TP_ID);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {}
				});
				
				searchReqTPSchemeSpinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View view,int position, long arg3) {
						try {
							tpScheme3Id = jsonArray.getJSONObject(position).getString(Constant.TPSchemesListing.TP_ID);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {}
				});
			}
		} catch(JSONException e) {
			e.printStackTrace();
		}
	}
	//END 
	
	
	//check the location is null or not
	private boolean checkLocation() {
		if(arearEditBox != null) {
			if(arearEditBox.getText().length() == 0) {
				arearEditBox.requestFocus();
				arearEditBox.setError("Please select at least one location.");
				return true;
			}
		}
		return false;
	}
	
	//check rent is null or not and max is greater then min
	private boolean checkMinRent() {
			if(searchReqRentMinSpinner.getSelectedItem().toString().equals("Select") ) {
				searchReqMinRentErrorMsg.requestFocus();
				searchReqMinRentErrorMsg.setVisibility(View.VISIBLE);
				return true;
			} 
		return false;
	}
	
	//check max rent null or not
	private boolean checkMaxRent() {
		if(searchReqRentMaxSpinner.getSelectedItem().toString().equals("Select")) {
			searchReqMaxRentErrorMsg.requestFocus();
			searchReqMaxRentErrorMsg.setVisibility(View.VISIBLE);
			return true;
		}
		return false;
	}
	
	//check the mim/max price is null or not
	private boolean checkMaxPrice() {
		if(searchReqTotalPriceMaxSpinner.getSelectedItem().toString().equals("Select")) {
			searchReqMaxPriceErrorMsg.requestFocus();
			searchReqMaxPriceErrorMsg.setVisibility(View.VISIBLE);
			return true;
		} 
		return false;
	}
	
	//check min prica is null or not
	private boolean checkMinPrice() {
		if(searchReqTotalPriceMinSpinner.getSelectedItem().toString().equals("Select")) {
			searchReqMinPriceErrorMsg.requestFocus();
			searchReqMinPriceErrorMsg.setVisibility(View.VISIBLE);
			return true;
		}
		return false;
	}
	
	//check the max is greater then min price/rent  
	private boolean checkMinMaxPriceRent() {
		if(searchReqForSpinner.getSelectedItem().toString().equals("Sale")) {
				if(checkMinPrice())
					return true;
				else if(checkMaxPrice())
					return true;
				else {
					if(!searchReqTotalPriceMinSpinner.getSelectedItem().toString().equals("5 Crore and Above") && !searchReqTotalPriceMaxSpinner.getSelectedItem().toString().equals("5 Crore and Above")) {
						if(Double.parseDouble(String.valueOf(SpinnerItem.getTotalPriceList().get(searchReqTotalPriceMaxSpinner.getSelectedItem().toString()))) <= Double.parseDouble(String.valueOf(SpinnerItem.getTotalPriceList().get(searchReqTotalPriceMinSpinner.getSelectedItem().toString())))) {
							Toast.makeText(getActivity(), "Please Select Proper Minimum and Maximum Price", Toast.LENGTH_LONG).show();
							return true;
						}
					}
				}
		} else if(searchReqForSpinner.getSelectedItem().toString().equals("Rent")) {
			if(checkMinRent())
				return true;
			else if(checkMaxRent())
				return true;
			else {
				if(Double.parseDouble(String.valueOf(SpinnerItem.getRentList().get(searchReqRentMaxSpinner.getSelectedItem().toString()))) <= Double.parseDouble(String.valueOf(SpinnerItem.getRentList().get(searchReqRentMinSpinner.getSelectedItem().toString())))) {
					Toast.makeText(getActivity(), "Please Select Proper Minimum and Maximum Rent", Toast.LENGTH_LONG).show();
					return true;
				}
			}
		}
		return false;
	}
	
	//Reset all field when property type change
	private void resetData() {
		
		//searchReqPropTypeSpinner.setSelection(1);
		searchReqForSpinner.setSelection(0);;
		arearEditBox.setText("");
		searchReqKeywordEditBox.setText("");
		searchReqRentMinSpinner.setSelection(0);
		searchReqRentMaxSpinner.setSelection(0);
		searchReqTotalPriceMinSpinner.setSelection(0);
		searchReqTotalPriceMaxSpinner.setSelection(0);
		searchReqPurposeSpinner.setSelection(0);
		searchReqAreaMinEditBox.setText("");
		searchReqPlotAreaMinEditBox.setText("");
		searchReqConstructionAreaMinEditBox.setText("");
		searchReqBedMinSpinner.setSelection(0);
		searchReqFloorMinSpinner.setSelection(0);
		searchReqBuildingTypeSpinner.setSelection(0);
		searchReqFurnishOptionSpinner.setSelection(0);
		searchReqTPSchemeSpinner1.setSelection(0);
		searchReqTPSchemeSpinner2.setSelection(0);
		searchReqTPSchemeSpinner3.setSelection(0);
		zoneCheckBox.setChecked(false);
		searchReqNASpinner.setSelection(0);
	}
	
	//Show hide field according to property type 
	private void showHidelayout() {
		
		searchReqAreaLayout.setVisibility(View.GONE);//area
		searchReqBedLayout.setVisibility(View.GONE); //Min/Max Bed
		searchReqFloorLayout.setVisibility(View.GONE);//Min/Max Floor
		searchReqBuildingTypeLayout.setVisibility(View.GONE);//Bulding Type
		searchReqPurposeLayout.setVisibility(View.GONE);//Purpose 
		searchReqFurnishOptionLayout.setVisibility(View.GONE);//furnish 
		searchReqComBunglowConstLayout.setVisibility(View.GONE);//Construction Area
		searchReqTPSchemeLayout.setVisibility(View.GONE);//TP SCheme
		searchReqZoneLayout.setVisibility(View.GONE);//Zone
		searchReqNALayout.setVisibility(View.GONE);//N.A.
		searchReqPlotAreaLayout.setVisibility(View.GONE);//Plot area
		//searchReqMoreOptionButton.setVisibility(View.GONE);
		//searchReqFlatLayout.setVisibility(View.VISIBLE);
		searchReqButtonLayout.setVisibility(View.VISIBLE);
		
		// Web Service for TPScheme This web service is used to set the TPScheme Spinner
		// For Property type Bunglow and sub Property type Farm House(Bunglow) for Sale/Rent and Plot
		System.out.println("Property type==> "+SpinnerItem.getPropertyTypeOptionList().get(searchReqPropTypeSpinner.getSelectedItem().toString()).equals("all plots"));
		if(SpinnerItem.getPropertyTypeOptionList().get(searchReqPropTypeSpinner.getSelectedItem().toString()).equals("Bunglow") && searchReqPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)")
			|| SpinnerItem.getPropertyTypeOptionList().get(searchReqPropTypeSpinner.getSelectedItem().toString()).equals("Plot") || SpinnerItem.getPropertyTypeOptionList().get(searchReqPropTypeSpinner.getSelectedItem().toString()).equals("all plots") ){
			getTpSchem();
		}
		
		//Flat For Sale Set Layout To Visible 
		if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Flat") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqAreaLayout.setVisibility(View.VISIBLE);//area
			searchReqBedLayout.setVisibility(View.VISIBLE); //Min/Max Bed
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose 
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish 
			searchReqAreaTv.setText("Area (Sq. Yards.)");//Area text view Text
		} //Flat For Rent Set Layout To Visible 
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Flat") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqAreaLayout.setVisibility(View.VISIBLE);
			searchReqBedLayout.setVisibility(View.VISIBLE);
			searchReqFloorLayout.setVisibility(View.VISIBLE);
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);
			searchReqPurposeLayout.setVisibility(View.VISIBLE);
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);
			searchReqAreaTv.setText("Area (Sq.Yards.)");//Area text view Text
		} //Shop For Sale Set Layout To Visible 
		else if((searchReqPropTypeSpinner.getSelectedItem().toString().equals("Shop") || searchReqPropTypeSpinner.getSelectedItem().toString().equals("All Shop")) && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqAreaLayout.setVisibility(View.VISIBLE);//area
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
		} //Shop For Rent Set Layout To Visible **/
		else if((searchReqPropTypeSpinner.getSelectedItem().toString().equals("Shop") || searchReqPropTypeSpinner.getSelectedItem().toString().equals("All Shop")) && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqAreaLayout.setVisibility(View.VISIBLE);//area
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
		} //Show Room For Sale Set Layout To Visible
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Show Rooms") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqAreaLayout.setVisibility(View.VISIBLE);//area
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
		} //Show Room For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Show Rooms") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqAreaLayout.setVisibility(View.VISIBLE);//area
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
		} //Commercial bunglow For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Commercial Bunglow") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqAreaLayout.setVisibility(View.VISIBLE);// area
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
		} //Commercial bunglow For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Commercial Bunglow") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqAreaLayout.setVisibility(View.VISIBLE);// area
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
		} //Commercial Flat For Sale Set Layout To Visible 
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Commercial Flat") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqAreaLayout.setVisibility(View.VISIBLE);// area
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
			
		} //Commercial Flat For Rent Set Layout To Visible
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Commercial Flat") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqAreaLayout.setVisibility(View.VISIBLE);// area
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
		} //Office For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Office") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqAreaLayout.setVisibility(View.VISIBLE);// area
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
		} //Office For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Office") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqAreaLayout.setVisibility(View.VISIBLE);// area
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
		} //Corporate House For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Corporate House") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqAreaLayout.setVisibility(View.VISIBLE);// area
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
		} //Corporate House For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Corporate House") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqAreaLayout.setVisibility(View.VISIBLE);// area
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
		} //Godown For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Godown") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqAreaLayout.setVisibility(View.VISIBLE);// area
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
		} //Godown For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Godown") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqAreaLayout.setVisibility(View.VISIBLE);// area
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
		} //Shades For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Shades") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqAreaLayout.setVisibility(View.VISIBLE);// area
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
		} //Shades For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Shades") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
		} //Factory For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Factory") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchReqAreaLayout.setVisibility(View.VISIBLE);// area
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
		} //Factory For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Factory") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchReqBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot Area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqAreaLayout.setVisibility(View.VISIBLE);// area
			searchReqAreaTv.setText("Area (Sq.Foot)");//Area text view Text
			searchReqPlotAreaTv.setText("Plot Area (Sq.Foot)\n(if applicable)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Foot)\n(if applicable)");//Construction Area Text view Text
		} //Bunglow For Sale Set Layout To Visible **/
		else if((searchReqPropTypeSpinner.getSelectedItem().toString().equals("Bunglow") ||searchReqPropTypeSpinner.getSelectedItem().toString().equals("All Bunglow"))&& searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish Option
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Yard)");//Construction Area Text view Text
		} //Bunglow For Rent Set Layout To Visible **/
		else if((searchReqPropTypeSpinner.getSelectedItem().toString().equals("Bunglow") ||searchReqPropTypeSpinner.getSelectedItem().toString().equals("All Bunglow")) && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish Option
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Yard)");//Construction Area Text view Text
		} //Twin For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Twin") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Yard)");//Construction Area Text view Text
			
		} //Twin For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Twin") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Yard)");//Construction Area Text view Text
	
		} //Row House For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Row House") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Yard)");//Construction Area Text view Text
	
		} //Row House For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Row House") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Yard)");//Construction Area Text view Text
		} //Tenament For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Tenament") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Yard)");//Construction Area Text view Text
		} //Tenament For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Tenament") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Yard)");//Construction Area Text view Text
		} //Individual For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Individual") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Yard)");//Construction Area Text view Text
		} //Individual For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Individual") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Yard)");//Construction Area Text view Text
		} //Independent Bunglow For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Independent Bunglows") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Yard)");//Construction Area Text view Text
		} //Independent Bunglow For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Independent Bunglows") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area (Sq.Yard)");//Construction Area Text view Text
		} //Farm House For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Farm House") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqNALayout.setVisibility(View.VISIBLE);//N.A.
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Farm House For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Farm House") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqNALayout.setVisibility(View.VISIBLE);//N.A.
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
	
		} //Plot For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Plot") && searchReqForSpinner.getSelectedItem().toString().equals("sale")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text

		} //Plot For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Plot") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text

		} //Society Plot For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Society Plot") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text

		} //Society Plot For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Society Plot") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Society Sub For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Society Sub Plot") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Society Sub For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Society Sub Plot") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //F.P Plot For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("F.P.Plot") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //F.P Plot For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("F.P.Plot") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Land of T.P For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Land Of T.P") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Land of T.P For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Land Of T.P") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Land of Agriculture For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Land of Agriculture") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Land of Agriculture For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Land of Agriculture") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Land of Industrial For Sale Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Land of Industrial") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Land of Industrial For Rent Set Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Land of Industrial") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Development Society For Sale Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Development Society") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Development Society For Rent Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Development Society") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Farm House For Sale Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Farm House") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construal area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Farm House For Rent Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Farm House") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Individual Plot For Sale Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Individual Plot") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Individual Plot For Rent Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Individual Plot") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Gam Tal Land For Sale Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Gam Tal Land") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Gam Tal Land For Rent Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("Gam Tal Land") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //GIDC Plot for Sale Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("GIDC Plot") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //GIDC Plot for Rent Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("GIDC Plot") && searchReqForSpinner.getSelectedItem().toString().equals("Rent")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //SEZ for Sale Layout To Visible **/
		else if(searchReqPropTypeSpinner.getSelectedItem().toString().equals("SEZ Land") && searchReqForSpinner.getSelectedItem().toString().equals("Sale")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //Plot For sale **/
		else if(searchReqPropTypeSpinner.getSelectedItem().equals("Plot") && searchReqForSpinner.getSelectedItem().equals("Sale")){
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		} //SEZ for Rent Layout To Visible **/
		else{
			searchReqTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchReqZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchReqPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchReqComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction area
			searchReqPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchReqPlotAreaTv.setText("Plot Area (Sq.Yard)");//Plot Area Text View Text
			searchReqConstructionAreaTv.setText("Construction Area(Sq.Yard)\n(For Farm house only)");//Construction Area Text view Text
		}
		
		
	}
}
