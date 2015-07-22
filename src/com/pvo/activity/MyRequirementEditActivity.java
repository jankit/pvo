package com.pvo.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

import com.google.android.gms.maps.GoogleMap;
import com.pvo.components.CheckBoxItem;
import com.pvo.components.SpinnerItem;
import com.pvo.json.cache.CreateJsonArrayFileIntoCache;
import com.pvo.prototype.PVOFragment;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.CityListService;
import com.pvo.user.service.DistrictListService;
import com.pvo.user.service.MyRequirementEditService;
import com.pvo.user.service.RequirementDetailService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.pvo.util.JSONUtils;

//Add new Requirement To Particular Broker
//Layout File: activity_edit_my_requirement.xml

public class MyRequirementEditActivity extends PVOFragment {
	
		protected static final CharSequence PUBLIC_INFORMAITON_HEADING = "Public Information";
		protected static final CharSequence NOMINEE_INFORMAITON_HEADING = "Nominee";
		protected static final CharSequence Flat_INFORMAITON_HEADING = "Property (Flat) Related";
		protected static final CharSequence PRICE_INFORMAITON_HEADING = "Price / Budget Related";
		protected static final CharSequence EXTRA_INFORMAITON_HEADING = "Extra Information";
		protected static final CharSequence BROKER_INFORMAITON_HEADING = "Select Broker";
		protected static final CharSequence FACILITY_INFORMAITON_HEADING = "Facility";
		protected static final CharSequence OPTION_INFORMAITON_HEADING = "Option";
		protected static final CharSequence NOTES_INFORMAITON_HEADING = "Notes";
	
		//Public Information Controller
		private Spinner editReqPropTypeSpinner;
		private Spinner editReqOptionSpinner;
		private Spinner editReqStateSpinner;
		private Spinner editReqCitySpinner;
		private Spinner editReqDistrictSpinner;
		private EditText editReqLocationEditBox;
		private ArrayAdapter<String> districtAdapter;
		private ArrayAdapter<String> cityAdapter;
			
		//Bed Layout
		private RelativeLayout editReqBedLayout;
		private Spinner editReqFlatMinBeds; 
		private Spinner editReqFlatMaxBeds;
		
		//TP Scheme Layout
		private RelativeLayout editReqTPSchemeLayout;
		private Spinner editReqTPSchemeSpinner; 
		private String tpSchemeId="";
		
		//Zone Layout
		private LinearLayout editReqZoneLayout; 
		private CheckBox zoneCheckBox;
		private ArrayList<String> zoneArray;
		
		//Furnish Option
		private RelativeLayout editReqFurnishOptionLayout; 
		private Spinner editReqFlatFurnishOptionSpinner; 
		private ArrayAdapter< String> furnishOptionAdapter;
		
		//Purpose Layout
		private RelativeLayout editReqPurposeLayout; 
		private Spinner editReqFlatPurposeSpinner; 
		
		//Floor Layout
		private  RelativeLayout editReqFloorLayout;
		private Spinner editReqFlatMinFloorSpinner;
		private Spinner  editReqFlatMaxFloorSpinner;
		
		//Bulilding Type
		private RelativeLayout editReqBuildingType;
		private Spinner editReqFlatBuildingTypeSpinner;
		
		//Area Layout
		private RelativeLayout editReqAreaLayout;
		private EditText editReqFlatMinAreaEditBox;
		private EditText editReqFlatMaxAreaEditBox;
		
		//Plot Area
		private RelativeLayout editReqPlotAreaLayout;
		private EditText editReqFlatMinPlotAreaEditBox;
		private EditText editReqFlatMaxPlotAreaEditBox;
		
		//Construction area
		private RelativeLayout editReqConstructionAreaLayout;
		private EditText editReqFlatMinConstructionAreaEditBox;
		private EditText editReqFlatMaxConstructionAreaEditBox;
		
		//Rent Budget Layout
		private RelativeLayout editMyReqRentLayout;
		private Spinner editReqMinRentBudget;
		private Spinner editReqMaxRentBudget;
		
		//Price Budge Related Layout
		private RelativeLayout editMyReqPriceLayout;
		private Spinner editReqMinPriceBudget;
		private Spinner editReqMaxPriceBudget;
		
		//Extra Information Controller 
		private EditText editReqHintEditBox;
		private EditText editReqKeywordEditBox;
		
		
		//Button Save,Next,Previous,cancel This button is used to move the next layout or previous
		private Button editReqSaveButton;
		private Button editReqNextButton;
		private Button editReqPreviousButton;
		private Button editReqCancelButton;

		//Different Layout 
		private RelativeLayout editReqIconLayout2;
		private RelativeLayout editReqMainContainer;
		private RelativeLayout editReqPublicInfoLayout;
		private RelativeLayout editReqFaltLayout;
		private RelativeLayout editReqPriceLayout;
		private RelativeLayout editReqExtraInfoLayout;

		//Google Map to display the map and set the property Latitude and Longitude
		@SuppressWarnings("unused")
		private GoogleMap googleMap;
	
		//User Service to get the response of the Particular web service
		private JSONArray stateResponse;
		private CityListService cityListService;
		private DistrictListService districtListService;
		private MyRequirementEditService editMyRequirementService;
		private Map<String,Integer> areaMap = new HashMap<String, Integer>();
	
		//Get Stored User Id From User SessionManager 
		private UserSessionManager userSessionManager;
	
		//this is used for Store JsonArray
		private JSONArray cityResponse;

		//this is used set the tab Index of the Current Layout and Image Icon
		private int activeTabIndex = 0;
		private JSONArray districtResponse;
	
		//Bundle to get data
		private Bundle editReqBundle;
		private RequirementDetailService requirementDetailService;
		private JSONObject reqirementDetailObject;
		private ArrayAdapter< String> prpoertyTypeOptionAdapter;
		private ArrayAdapter< String> editMyReqSaleRentAdapter;
		private ArrayAdapter< String> bedAdapter;
		private ArrayAdapter< String> editMyReqPurposeAdapter;
		private ArrayAdapter< String> floorAdapter;
		private ArrayAdapter< String> buildingTypeAdapter;
		private ArrayAdapter< String> rentListAdapter;
		private ArrayAdapter< String> priceListAdapter;
		
		private boolean areaFlag = false;
		
		//set layout content view
		public MyRequirementEditActivity() {
			setContentView(R.layout.activity_myrequirement_edit);
		}

	@SuppressWarnings("unchecked")
	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		editReqBundle = getArguments();
		
		//Different Layout of Add property
		editReqPublicInfoLayout 	= (RelativeLayout) findViewById(R.id.editReqPublicInfoLayout);
		editReqFaltLayout 			= (RelativeLayout) findViewById(R.id.editReqFaltLayout);
		editReqPriceLayout 			= (RelativeLayout) findViewById(R.id.editReqPriceLayout);
		editReqExtraInfoLayout 		= (RelativeLayout) findViewById(R.id.editReqExtraInfoLayout);
		editReqMainContainer 		= (RelativeLayout) findViewById(R.id.editReqMainContainer);
		
		//State, City, District Spinner
		editReqStateSpinner 		= (Spinner) findViewById(R.id.editReqStateSpinner);
		editReqCitySpinner 			= (Spinner) findViewById(R.id.editReqCitySpinner);
		editReqDistrictSpinner 		= (Spinner) findViewById(R.id.editReqDistrictSpinner);
		
		editReqOptionSpinner = (Spinner) findViewById(R.id.editReqOptionSpinner);
		editReqLocationEditBox = (EditText) findViewById(R.id.editReqLocationEditBox);
		
		//Bed Layout 
		editReqBedLayout		= (RelativeLayout) findViewById(R.id.editReqBedLayout);
		editReqFlatMinBeds 		= (Spinner) findViewById(R.id.editReqFlatMinBeds); 
		editReqFlatMaxBeds 		= (Spinner) findViewById(R.id.editReqFlatMaxBeds);
		editReqPropTypeSpinner = (Spinner) findViewById(R.id.editReqPropTypeSpinner);
		//TP Scheme Layout 
		editReqTPSchemeLayout = (RelativeLayout) findViewById(R.id.editReqTPSchemeLayout);
		editReqTPSchemeSpinner = (Spinner) findViewById(R.id.editReqTPSchemeSpinner);
		editReqZoneLayout = (LinearLayout) findViewById(R.id.editReqZoneLayout);
		
		//Furnish Option
		editReqFurnishOptionLayout = (RelativeLayout) findViewById(R.id.editReqFurnishOptionLayout); 
		editReqFlatFurnishOptionSpinner = (Spinner) findViewById(R.id.editReqFlatFurnishOptionSpinner);
		editReqPurposeLayout = (RelativeLayout) findViewById(R.id.editReqPurposeLayout); 
		editReqFlatPurposeSpinner = (Spinner) findViewById(R.id.editReqFlatPurposeSpinner);
		editReqFloorLayout = (RelativeLayout) findViewById(R.id.editReqFloorLayout);
		editReqFlatMinFloorSpinner = (Spinner) findViewById(R.id.editReqFlatMinFloorSpinner);
		editReqFlatMaxFloorSpinner = (Spinner) findViewById(R.id.editReqFlatMaxFloorSpinner);
				
		//Bulilding Type 
		editReqBuildingType = (RelativeLayout) findViewById(R.id.editReqBuildingType);
		editReqFlatBuildingTypeSpinner = (Spinner) findViewById(R.id.editReqFlatBuildingTypeSpinner);
		//Area Layout
		editReqAreaLayout = (RelativeLayout) findViewById(R.id.editReqAreaLayout);
		editReqFlatMinAreaEditBox = (EditText) findViewById(R.id.editReqFlatMinAreaEditBox);
		editReqFlatMaxAreaEditBox = (EditText) findViewById(R.id.editReqFlatMaxAreaEditBox);
		
		//Plot Area 
		editReqPlotAreaLayout = (RelativeLayout) findViewById(R.id.editReqPlotAreaLayout);
		editReqFlatMinPlotAreaEditBox = (EditText) findViewById(R.id.editReqFlatMinPlotAreaEditBox);
		editReqFlatMaxPlotAreaEditBox = (EditText) findViewById(R.id.editReqFlatMaxPlotAreaEditBox);
		
		//Construction area
		editReqConstructionAreaLayout = (RelativeLayout) findViewById(R.id.editReqConstructionAreaLayout);
		editReqFlatMinConstructionAreaEditBox = (EditText) findViewById(R.id.editReqFlatMinConstructionAreaEditBox);
		editReqFlatMaxConstructionAreaEditBox = (EditText) findViewById(R.id.editReqFlatMaxConstructionAreaEditBox);
		
		editMyReqRentLayout = (RelativeLayout) findViewById(R.id.editMyReqRentLayout);
		editMyReqPriceLayout = (RelativeLayout) findViewById(R.id.editMyReqPriceLayout);
		editReqMinRentBudget=(Spinner)findViewById(R.id.editReqMinRentBudget);
		editReqMaxRentBudget=(Spinner)findViewById(R.id.editReqMaxRentBudget);
		editReqMinPriceBudget = (Spinner) findViewById(R.id.editReqMinPriceBudget);
		editReqMaxPriceBudget = (Spinner) findViewById(R.id.editReqMaxPriceBudget);
		
		editReqHintEditBox=(EditText)findViewById(R.id.editReqHintEditBox);
		editReqKeywordEditBox=(EditText)findViewById(R.id.editReqKeywordEditBox);
		editReqNextButton=(Button)findViewById(R.id.editReqNextButton);
		editReqPreviousButton=(Button)findViewById(R.id.editReqPreviousButton);
		editReqCancelButton=(Button)findViewById(R.id.editReqCancelButton);
		editReqSaveButton=(Button)findViewById(R.id.editReqSaveButton);
		editReqIconLayout2 = (RelativeLayout) findViewById(R.id.editReqIconLayout2);
		
		//Service for get Particular API Response 
		cityListService 		 = new CityListService();
		districtListService		 = new DistrictListService();
		editMyRequirementService = new MyRequirementEditService();
		//get Login User_Id from stored Session
		userSessionManager 		 = new UserSessionManager(getActivity());
		
		getAreaList(false);

		//********* Public Information Controller ID ***********/
		//1) Property Type===> Fill the  Property Type Spinner Using hash Map
		final ArrayList<String> propertyTypeOptionList=new ArrayList<String>();
		propertyTypeOptionList.addAll(SpinnerItem.getPropertyTypeOptionList().keySet());
		prpoertyTypeOptionAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,propertyTypeOptionList) {
			@Override      
			public boolean isEnabled(int position) {
				if (position == 1) 
					return false;
				if (position == 3) 
					return false;
				if (position == 13) 
					return false;
				if (position == 21) 
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
					case 1:
						tv.setTextColor(Color.GRAY);
						tv.setTypeface(null, Typeface.ITALIC);
						break;
					case 3:
						tv.setTextColor(Color.GRAY);
						tv.setTypeface(null, Typeface.ITALIC);
						break;
					case 13:
						tv.setTextColor(Color.GRAY);
						tv.setTypeface(null, Typeface.ITALIC);
						break;
					case 21:
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
		prpoertyTypeOptionAdapter.remove("All Shop");
		prpoertyTypeOptionAdapter.remove("All Bunglow");
		prpoertyTypeOptionAdapter.remove("All Plots");
		editReqPropTypeSpinner.setAdapter(prpoertyTypeOptionAdapter);
		//End
		
		//fill the Option Sale /rent  spinner using hash map
		ArrayList<String> editMyReqSaleRentList = new ArrayList<String>();
		editMyReqSaleRentList.addAll(SpinnerItem.getSaleRentList().keySet());
		editMyReqSaleRentAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,editMyReqSaleRentList);
		editMyReqSaleRentAdapter.setDropDownViewResource(R.layout.spinner_text);
		editReqOptionSpinner.setAdapter(editMyReqSaleRentAdapter);
		//END

		//Open area list popup window
		editReqLocationEditBox.setFocusable(false);
		editReqLocationEditBox.setInputType(InputType.TYPE_NULL);
		editReqLocationEditBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getAreaList(true);
			}
		});
		
		//********* Property (Flat) Related Information Controller ID ***********// 
		
		//Fill the Min/Max Bed Spinner Using HashMap 
		ArrayList<String> bedList = new ArrayList<String>();
		bedList.addAll(SpinnerItem.getAddPropBedList().keySet());
		bedAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,bedList);
		bedAdapter.setDropDownViewResource(R.layout.spinner_text);
		//set default selected MinBed spinner
		editReqFlatMinBeds.setAdapter(bedAdapter);
		//set default selected MaxBed spinner
		editReqFlatMaxBeds.setAdapter(bedAdapter);
		//END BED
			
		//Zone Layout
		/*try {
			zoneArray = new ArrayList<String>();
			for(int i=0;i<CheckBoxItem.zoneCheckItem.length;i++){
				zoneCheckBox = new CheckBox(getActivity());
				zoneCheckBox.setId(i);
				zoneCheckBox.setTextColor(Color.BLACK);
				zoneCheckBox.setText(CheckBoxItem.zoneCheckItem[i]);
				String[] zoneValue;
				zoneValue = reqirementDetailObject.getString(Constant.MyRequirement.ZONE).split(",");
				editReqZoneLayout.addView(zoneCheckBox);
				for(int j=0; j<zoneValue.length;j++){
					if(zoneCheckBox.getText().equals(zoneValue[j])){
						zoneCheckBox.setChecked(true);
						zoneArray.add(zoneCheckBox.getText().toString());
					}
				}
				zoneCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked)
							zoneArray.add(buttonView.getText().toString());
						else
							zoneArray.remove(buttonView.getText().toString());
					}
				});
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}*/
			
		//Fill the Furnish Option spinner Using hashmap
		ArrayList<String> furnishOptionList=new ArrayList<String>();
		furnishOptionList.addAll(SpinnerItem.getFurnishOptionList().keySet());
		furnishOptionAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,furnishOptionList);
		furnishOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
		editReqFlatFurnishOptionSpinner.setAdapter(furnishOptionAdapter);
		//End
			
		//Purpose Layout Any="",Residential='Residential',Commercial='Commercial'
		ArrayList<String> editMyReqPurposeList=new ArrayList<String>();
		editMyReqPurposeList.addAll(SpinnerItem.getAddPropPurposeList().keySet());
		editMyReqPurposeAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,editMyReqPurposeList);
		editMyReqPurposeAdapter.setDropDownViewResource(R.layout.spinner_text);
		editReqFlatPurposeSpinner.setAdapter(editMyReqPurposeAdapter);
		//END 
		
		//Floor Layout Minimum Floor='',Will tell you later='-2'
		//Fill The Floor spinner Using HashMap
		ArrayList<String> floorTypeList=new ArrayList<String>();
		floorTypeList.addAll(SpinnerItem.getAddPropFloorList().keySet());
		floorAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,floorTypeList);
		floorAdapter.setDropDownViewResource(R.layout.spinner_text);
		editReqFlatMinFloorSpinner.setAdapter(floorAdapter);
		
		//set Default selected
		editReqFlatMaxFloorSpinner.setAdapter(floorAdapter);
		
		//End
		
		//Fill The BuildingType spinner Using HashMap
		ArrayList<String> buildingTypeList=new ArrayList<String>();
		buildingTypeList.addAll(SpinnerItem.getBuildingTypeList().keySet());
		buildingTypeAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,buildingTypeList);
		buildingTypeAdapter.setDropDownViewResource(R.layout.spinner_text);
		editReqFlatBuildingTypeSpinner.setAdapter(buildingTypeAdapter);
		//End
		//==============>> Rent Related Information Controller ID <<======= 
		//Fill the Min/Max Rent Spinner Using Hash Map
		ArrayList<String> rentList=new ArrayList<String>();
		rentList.addAll(SpinnerItem.getRentList().keySet());
		rentListAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,rentList);

		rentListAdapter.setDropDownViewResource(R.layout.spinner_text);
		//set default value
		editReqMinRentBudget.setAdapter(rentListAdapter);
		//set default value
		editReqMaxRentBudget.setAdapter(rentListAdapter);
		
		//END
			
		//Fill the Min/Max Price Spinner Using Hash Map
		ArrayList<String> priceList=new ArrayList<String>();
		priceList.addAll(SpinnerItem.getTotalPriceList().keySet());
		priceListAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,priceList);
		priceListAdapter.setDropDownViewResource(R.layout.spinner_text);
		//set default Value
		editReqMinPriceBudget.setAdapter(priceListAdapter);
		
		//set default
		editReqMaxPriceBudget.setAdapter(priceListAdapter);
		//END
		
		//*********** Extra Information Controler ID *********** 
		//Button Save,Next,Previous,Cancel This is used to move next layout or Previous Layout goto previous fragment
		editReqCancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});
		
		//Save Requirement
		editReqSaveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String area = editReqLocationEditBox.getText().toString();
				String selectedAreaString = "";
				if(area.length() > 0) {
					String[] selectedArea = area.split(" - ");
					for (int i = 0; i < selectedArea.length; i++) { // Comma Seprated String of Selected area Code
						selectedAreaString += areaMap.get(selectedArea[i].trim());
						if(i != selectedArea.length){
							selectedAreaString += ",";
						}
					}
				}
				
				System.out.println("on save All location ===> "+selectedAreaString);
				System.out.println("StringUtils.defaultString(selectedAreaString"+StringUtils.defaultString(selectedAreaString,""));
				try {
					JSONObject stateObject 		= stateResponse.getJSONObject(editReqStateSpinner.getSelectedItemPosition());
					JSONObject cityObject 		= cityResponse.getJSONObject(editReqCitySpinner.getSelectedItemPosition());
					JSONObject districtObject 	= districtResponse.getJSONObject(editReqDistrictSpinner.getSelectedItemPosition());
					
				    WebserviceClient editMyRequirement = new WebserviceClient(MyRequirementEditActivity.this, editMyRequirementService);
					editMyRequirement.setResponseListner(new ResponseListner() {
						@Override
						public void handleResponse(Object res) {
							final JSONObject response=(JSONObject) res;
							try{
								if(response != null){
									if(String.valueOf(response.getString(Constant.AddProperty.API_STATUS)).equals("1")){
										Toast.makeText(getActivity(), String.valueOf(response.getString(Constant.AddProperty.API_MESSAGE)), 100).show();
										Bundle bundle = new Bundle();
										bundle.putString("Type", "");
										MyRequirementListActivity myRequirementActivity = new MyRequirementListActivity();
										myRequirementActivity .setArguments(bundle);
										((MainFragmentActivity)getActivity()).redirectScreenWithoutStack(myRequirementActivity);
									}else{
										Toast.makeText(getActivity(), String.valueOf(response.getString(Constant.AddProperty.API_MESSAGE)), 100).show();
									}
								}
							}catch(JSONException e){
								e.printStackTrace();
							}
						}
					});
					editMyRequirement.execute(
							userSessionManager.getSessionValue(Constant.Login.USER_ID),//user ID
							reqirementDetailObject.getString(Constant.MyRequirement.REQUIREMENT_ID),//id-If action=edit then id=requirementrecordid
							String.valueOf(SpinnerItem.getPropertyTypeOptionList().get(editReqPropTypeSpinner.getSelectedItem().toString())),//cmbproptype
							editReqPropTypeSpinner.getSelectedItem().toString(),//propertysubtype
							"1",//countryid
							String.valueOf(stateObject.getString(Constant.State.STATEID)),//stateId
							String.valueOf(cityObject.getString(Constant.City.CITY_ID)),//City Id
							String.valueOf(districtObject.getString(Constant.District.DISTRICT_ID)),//Distric ID
							StringUtils.defaultString(selectedAreaString,""),//location
							//String.valueOf(selectedAreaString),//location
							editReqHintEditBox.getText().toString(),//hint
							editReqKeywordEditBox.getText().toString(),//keyword
							editReqOptionSpinner.getSelectedItem().toString(),//stroptions
							(editReqMinPriceBudget.getSelectedItem() == null) ? "" : String.valueOf(SpinnerItem.getTotalPriceList().get(StringUtils.defaultString(editReqMinPriceBudget.getSelectedItem().toString()))),
							//String.valueOf(SpinnerItem.getTotalPriceList().get(StringUtils.defaultString(editReqMinPriceBudget.getSelectedItem().toString()))),//txtminprice
							(editReqMaxPriceBudget.getSelectedItem() == null) ? "" : String.valueOf(SpinnerItem.getTotalPriceList().get(editReqMaxPriceBudget.getSelectedItem().toString())),//txtmaxprice	
							//String.valueOf(SpinnerItem.getTotalPriceList().get(editReqMaxPriceBudget.getSelectedItem().toString())),//txtmaxprice
						    (editReqMinRentBudget.getSelectedItem() == null) ? "" : String.valueOf(SpinnerItem.getRentList().get(editReqMinRentBudget.getSelectedItem().toString())),//txtminrent
							//String.valueOf(SpinnerItem.getRentList().get(editReqMinRentBudget.getSelectedItem().toString())),//txtminrent
						    (editReqMaxRentBudget.getSelectedItem() == null) ? "" : String.valueOf(SpinnerItem.getRentList().get(editReqMaxRentBudget.getSelectedItem().toString())),//txtmaxrent	
							//String.valueOf(SpinnerItem.getRentList().get(editReqMaxRentBudget.getSelectedItem().toString())),//txtmaxrent
							editReqFlatMinBeds.getSelectedItem().toString(),//minbed
							editReqFlatMaxBeds.getSelectedItem().toString(),//maxbed
							String.valueOf(SpinnerItem.getAddPropFloorList().get(editReqFlatMinFloorSpinner.getSelectedItem().toString())),//minfloor
							String.valueOf(SpinnerItem.getAddPropFloorList().get(editReqFlatMaxFloorSpinner.getSelectedItem().toString())),//maxfloor
							String.valueOf(SpinnerItem.getFurnishOptionList().get(editReqFlatFurnishOptionSpinner.getSelectedItem().toString())),//editReqFlatFurnishOptionSpinner.getSelectedItem().toString(),//furnishstatus
							String.valueOf(SpinnerItem.getBuildingTypeList().get(editReqFlatBuildingTypeSpinner.getSelectedItem().toString())),//rise
							//editReqFlatBuildingTypeSpinner.getSelectedItem().toString(),//rise
							editReqFlatMinAreaEditBox.getText().toString(),//minsqfoot
							editReqFlatMaxAreaEditBox.getText().toString(),//maxsqfoot
							editReqFlatMinPlotAreaEditBox.getText().toString(),//minplotarea
							editReqFlatMaxPlotAreaEditBox.getText().toString(),//maxplotarea
							editReqFlatMinConstructionAreaEditBox.getText().toString(),//minconstrarea
							editReqFlatMaxConstructionAreaEditBox.getText().toString(),//maxconstrarea
							StringUtils.defaultString(String.valueOf(tpSchemeId),""),//cmbtpscheme
							editReqFlatPurposeSpinner.getSelectedItem().toString(),//stpurpose
							StringUtils.join(zoneArray,",")//chkzone
							);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		});
		
	//********* ANKUR *********
	//next Button to move the next Layout
	editReqNextButton.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			if(checkPropertyType()) {//1) Check proerty type
				//System.out.println("==> 1)");
				activeTabIndex = ((ViewGroup)editReqMainContainer).indexOfChild(editReqPublicInfoLayout);
                editReqIconLayout2.getChildAt(activeTabIndex).performClick();
			} else if(checkPropertyOption()) {//2) check property option
				//System.out.println("==> 2)");
				activeTabIndex = ((ViewGroup)editReqMainContainer).indexOfChild(editReqPublicInfoLayout);
                editReqIconLayout2.getChildAt(activeTabIndex).performClick();
			} else if(checkDistrict()) {//3) check District
				activeTabIndex = ((ViewGroup)editReqMainContainer).indexOfChild(editReqPublicInfoLayout);
                editReqIconLayout2.getChildAt(activeTabIndex).performClick();
			} else if(checkCity()) {//4) check city
				//System.out.println("==> 2)");
				activeTabIndex = ((ViewGroup)editReqMainContainer).indexOfChild(editReqPublicInfoLayout);
                editReqIconLayout2.getChildAt(activeTabIndex).performClick();
			} else if(areaEditBoxValidation()) {//3) Check location
				//System.out.println("==> 3)");
				activeTabIndex = ((ViewGroup)editReqMainContainer).indexOfChild(editReqPublicInfoLayout);
                editReqIconLayout2.getChildAt(activeTabIndex).performClick();
			} else if(checkMinLessThenMaxBed()) {//4) check Min/max bed
				//System.out.println("==> 4)");
				activeTabIndex = ((ViewGroup)editReqMainContainer).indexOfChild(editReqFaltLayout);
                editReqIconLayout2.getChildAt(activeTabIndex).performClick();
			}  else if(checkMinLessThenMaxArea()){//5) checkMinLessThanMaxArea()
				//System.out.println("==> 5)");
				activeTabIndex = ((ViewGroup)editReqMainContainer).indexOfChild(editReqFaltLayout);
                editReqIconLayout2.getChildAt(activeTabIndex).performClick();
			}else if(checkMinMaxRent()) {//6) check min/max rent
				//System.out.println("==> 6)");
				activeTabIndex = ((ViewGroup)editReqMainContainer).indexOfChild(editReqPriceLayout);
                editReqIconLayout2.getChildAt(activeTabIndex).performClick();
			} else if(checkMinMaxPrice()) {//7)check min/max price
				//System.out.println("==> 7)");
				activeTabIndex = ((ViewGroup)editReqMainContainer).indexOfChild(editReqPriceLayout);
                editReqIconLayout2.getChildAt(activeTabIndex).performClick();
			} else {
				//System.out.println("==> Else)");
				++activeTabIndex;
				setBackgroundImageView(editReqIconLayout2.getChildAt(activeTabIndex));
				editReqIconLayout2.getChildAt(activeTabIndex).performClick();
			}
			
			/*++activeTabIndex;
			setBackgroundImageView(editReqIconLayout2.getChildAt(activeTabIndex));
			editReqIconLayout2.getChildAt(activeTabIndex).performClick();*/
		}
	});
	//END 
	
	//previous Button to move Previous Layout 
	editReqPreviousButton.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			--activeTabIndex;
			setBackgroundImageView(editReqIconLayout2.getChildAt(activeTabIndex));
			editReqIconLayout2.getChildAt(activeTabIndex).performClick();
		}
	});
	//END
		
	//Bind event to all image icons of left side icon bar
	int totalChild = editReqIconLayout2.getChildCount();
	for(int i = 0; i < totalChild; i++) {
		View imageView = editReqIconLayout2.getChildAt(i);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editReqPreviousButton.setVisibility(View.VISIBLE);
				editReqNextButton.setVisibility(View.VISIBLE);
				editReqSaveButton.setVisibility(View.GONE);
				setBackgroundImageView(v);
				TextView header = (TextView) findViewById(R.id.editReqHeaderTv2);
				//Set Header according to icon 
				switch (v.getId()) {
					case R.id.editReqPublicInfoImg:
						header.setText(PUBLIC_INFORMAITON_HEADING);
						//Hide previous button for first screen
						editReqPreviousButton.setVisibility(View.GONE);
						setVisibility(editReqPublicInfoLayout);
						activeTabIndex = ((ViewGroup)editReqMainContainer).indexOfChild(editReqPublicInfoLayout);
						break;
					case R.id.editReqflatImg:
						//Web Service for TPScheme This web service is used to set the TPScheme Spinner **/
						getTpSchemList();
						hideLayoutChild(editReqFaltLayout);
						//header.setText(Flat_INFORMAITON_HEADING);
						header.setText("Requirement ("+editReqPropTypeSpinner.getSelectedItem().toString()+") Related");
						setVisibility(editReqFaltLayout);
						activeTabIndex = ((ViewGroup)editReqMainContainer).indexOfChild(editReqFaltLayout);
						
						if(SpinnerItem.getPropertyTypeOptionList().get(editReqPropTypeSpinner.getSelectedItem().toString()).equals("Flat")){
							editReqBedLayout.setVisibility(View.VISIBLE);
							editReqFurnishOptionLayout.setVisibility(View.VISIBLE);
							editReqPurposeLayout.setVisibility(View.VISIBLE);
							editReqFloorLayout.setVisibility(View.VISIBLE);
							editReqBuildingType.setVisibility(View.VISIBLE);
							editReqAreaLayout.setVisibility(View.VISIBLE);
						} else if(SpinnerItem.getPropertyTypeOptionList().get(editReqPropTypeSpinner.getSelectedItem().toString()).equals("Shop")){
							editReqFloorLayout.setVisibility(View.VISIBLE);
							editReqFurnishOptionLayout.setVisibility(View.VISIBLE);
							editReqAreaLayout.setVisibility(View.VISIBLE);
							editReqPlotAreaLayout.setVisibility(View.VISIBLE);
							editReqConstructionAreaLayout.setVisibility(View.VISIBLE);
							editReqBuildingType.setVisibility(View.VISIBLE);
						} else if(SpinnerItem.getPropertyTypeOptionList().get(editReqPropTypeSpinner.getSelectedItem().toString()).equals("Bunglow")&& !editReqPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)")) {
							editReqBedLayout.setVisibility(View.VISIBLE);
							editReqFurnishOptionLayout.setVisibility(View.VISIBLE);
							editReqPurposeLayout.setVisibility(View.VISIBLE);
							editReqPlotAreaLayout.setVisibility(View.VISIBLE);
							editReqConstructionAreaLayout.setVisibility(View.VISIBLE);
						} else if(editReqPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)")){
							editReqPurposeLayout.setVisibility(View.VISIBLE);
							editReqTPSchemeLayout.setVisibility(View.VISIBLE);
							editReqZoneLayout.setVisibility(View.VISIBLE);
							editReqPlotAreaLayout.setVisibility(View.VISIBLE);
							editReqConstructionAreaLayout.setVisibility(View.VISIBLE);
						} else {
							editReqPurposeLayout.setVisibility(View.VISIBLE);
							editReqTPSchemeLayout.setVisibility(View.VISIBLE);
							editReqZoneLayout.setVisibility(View.VISIBLE);
							editReqPlotAreaLayout.setVisibility(View.VISIBLE);
							editReqConstructionAreaLayout.setVisibility(View.VISIBLE);
						}
						break;
						
					case R.id.editReqPriceImg:
						hideLayoutChild(editReqPriceLayout);
						header.setText(PRICE_INFORMAITON_HEADING);
						setVisibility(editReqPriceLayout);
						activeTabIndex = ((ViewGroup)editReqMainContainer).indexOfChild(editReqPriceLayout);
						if(editReqOptionSpinner.getSelectedItem().toString().equals("Rent"))
							editMyReqRentLayout.setVisibility(View.VISIBLE);
						else 
							editMyReqPriceLayout.setVisibility(View.VISIBLE);
						break;
							
					case R.id.editReqExtraInfoImg:
						header.setText(EXTRA_INFORMAITON_HEADING);
						editReqNextButton.setVisibility(View.GONE);
						editReqSaveButton.setVisibility(View.VISIBLE);
						setVisibility(editReqExtraInfoLayout);
						activeTabIndex = ((ViewGroup)editReqMainContainer).indexOfChild(editReqExtraInfoLayout);
						break;
					default:
						break;
				  }
				}
			});
		}
		//set default value 
		setDefaultValue();
	}		

	//Get Process Response From Web Service
	@Override
	public void processResponse(Object response) {}
	
	//Hide all the layout and set visible true for given layout
	private void setVisibility(View visibalLinerLayout) {
		this.hideAllLayout();
		visibalLinerLayout.setVisibility(View.VISIBLE);
	}
	//END
	
	//Hide all the layout and remove background color
	private void hideAllLayout() {
		int totalChild = editReqMainContainer.getChildCount();
		//Minus one to escape buttons layout
		for(int i = 0; i < totalChild; i++) {
			editReqMainContainer.getChildAt(i).setVisibility(View.GONE);
		}	
	}
	//END
	
	//Remove backgroud color of all image view/icons Set white backgroud color for given view
	private void setBackgroundImageView(View v) {
		reSetAllImageView();
		v.setBackgroundColor(Color.WHITE);
	}
	//END
	
	//Remove background of all image view/icons 
	private void reSetAllImageView() {
		int totalChild = editReqIconLayout2.getChildCount();
		for(int i = 0; i < totalChild; i++) {
			editReqIconLayout2.getChildAt(i).setBackgroundColor(0);
		}		
	}
	//END
	
	//Hide Particular View Of Layout	
	private void hideLayoutChild(View hideLinerLayout){
		int childView =  ((ViewGroup) hideLinerLayout).getChildCount();
		for (int i = 0; i < childView; i++) {
			((ViewGroup) hideLinerLayout).getChildAt(i).setVisibility(View.GONE);
		}
	}
	  
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    this.getActivity().getActionBar().setTitle("Edit Requirement");
	}
	
	//get the list of state 
	public void getStateList(){
		stateResponse = CreateJsonArrayFileIntoCache.readStateListJsonData(getActivity().getApplicationContext());
		if(stateResponse != null){
			ArrayAdapter<String> stateAdapter;
			try {
				stateAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text,JSONUtils.getList(stateResponse,Constant.State.STATENAME));
				stateAdapter.setDropDownViewResource(R.layout.spinner_text);
				//stateAdapter.
				editReqStateSpinner.setAdapter(stateAdapter);
				//Set Default Selection
				//editReqStateSpinner.setSelection(stateAdapter.getPosition(userSessionManager.getSessionValue(Constant.Login.STATE_NAME)));
				
				// get the index of state and set default selected into spinner 
				for (int i = 0; i < stateResponse.length(); i++) {
					JSONObject jsonObj = stateResponse.getJSONObject(i);
					if (jsonObj.getString(Constant.State.STATEID).equals(reqirementDetailObject.getString(Constant.MyRequirement.STATE_ID))) {
						editReqStateSpinner.setSelection(stateAdapter.getPosition(jsonObj.getString(Constant.State.STATENAME)));
					}
				}
				editReqStateSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0,View arg1, int position, long arg3) {
						try {
								getDistrictList(stateResponse.getJSONObject(position).getString(Constant.State.STATEID), stateResponse.getJSONObject(position).getString(Constant.State.COUNTRY_ID));
								//getCityList(stateResponse.getJSONObject(position).getString(Constant.State.STATEID));
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						@Override
						public void onNothingSelected(AdapterView<?> arg0) {}
					});
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
	}
		
	//Web Service to get the City name List and add into the City Spinner
	public void getCityList(String stateId,String districtId){
		WebserviceClient  cityListWebserviceClient=new WebserviceClient(MyRequirementEditActivity.this, cityListService);
		cityListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				cityResponse =(JSONArray)response;
				try{
					if (cityResponse != null && !((JSONObject) cityResponse.get(0)).has(Constant.City.API_STATUS)) {
						editReqCitySpinner.setEnabled(true);
						cityAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,JSONUtils.getList(cityResponse,Constant.City.CITY_NAME));
						cityAdapter.setDropDownViewResource(R.layout.spinner_text);
						editReqCitySpinner.setAdapter(cityAdapter);
						//get the index of city and set default selected into city spinner
						for (int i = 0; i < cityResponse.length(); i++) {
							JSONObject jsonObj = cityResponse.getJSONObject(i);
							if (jsonObj.getString(Constant.City.CITY_ID).equals(reqirementDetailObject.getString(Constant.MyRequirement.CITY_ID))) {
								editReqCitySpinner.setSelection(cityAdapter.getPosition(jsonObj.getString(Constant.City.CITY_NAME)));
							}
						}
						//on city spinner item selected call district method
						/*editReqCitySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0,View arg1, int position, long arg3) {
								try{
									getDistrictList(cityResponse.getJSONObject(position).getString(Constant.State.STATEID),cityResponse.getJSONObject(position).getString(Constant.City.COUNTRY_ID));
								}catch(JSONException e){
									e.printStackTrace();
								}
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});*/
					} else {
						Toast.makeText(getActivity(),((JSONObject) cityResponse.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(cityAdapter, "city");
					}
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		});
		cityListWebserviceClient.execute(stateId,districtId);		
	}
	
	//Web Service to get the District name List and add into the District Spinner
	public void getDistrictList(String stateId,String countryId){
		WebserviceClient  districtListWebserviceClient=new WebserviceClient(MyRequirementEditActivity.this, districtListService);
		districtListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				districtResponse =(JSONArray)response;
				try{
					if (districtResponse != null && !((JSONObject) districtResponse.get(0)).has(Constant.District.API_STATUS)) {
						editReqDistrictSpinner.setEnabled(true);
						districtAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,JSONUtils.getList(districtResponse,Constant.District.DISTRICT_NAME));
						districtAdapter.setDropDownViewResource(R.layout.spinner_text);
						editReqDistrictSpinner.setAdapter(districtAdapter);
						//get the index of city and set default selected into city spinner
						for (int i = 0; i < districtResponse.length(); i++) {
							JSONObject jsonObj = districtResponse.getJSONObject(i);
							
							if (jsonObj.getString(Constant.District.DISTRICT_ID).equals(reqirementDetailObject.getString(Constant.MyRequirement.DISTRICT_ID))) {
								editReqDistrictSpinner.setSelection(districtAdapter.getPosition(jsonObj.getString(Constant.District.DISTRICT_NAME)));
							}
						}
						editReqDistrictSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									getCityList(districtResponse.getJSONObject(position).getString(Constant.District.DISTRICT_ID),districtResponse.getJSONObject(position).getString(Constant.District.STATEID));
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(getActivity(),((JSONObject) districtResponse.get(0)).getString(Constant.District.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(districtAdapter, "district");
						notifyAdapter(cityAdapter, "city");
					}
				} catch(JSONException e) {
					e.printStackTrace();
				}
			}
		});
		districtListWebserviceClient.execute(stateId,countryId);	
	}
		
	//get the list of area(Location)
	public void getAreaList(boolean flage){
		JSONArray jsonArray = CreateJsonArrayFileIntoCache.readAreaListJsonData(getActivity().getApplicationContext());
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				areaMap.put(jsonObject.getString(Constant.Area.AREA_NAME).trim(),jsonObject.getInt(Constant.Area.AREA_ID));
			}
			if(flage) 
				openAreaListOfCityPopupWindow("areaMap", editReqLocationEditBox, areaMap);
			

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
		
	//get the list of tp scheme and fill the tp schem spinner
	public void getTpSchemList(){
		final JSONArray jsonArray = CreateJsonArrayFileIntoCache.readTPSchemeListJsonData(getActivity().getApplicationContext());
		try {
			if(jsonArray != null) {
				ArrayAdapter<String> tpSchemeAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,JSONUtils.getList(jsonArray, Constant.TPSchemesListing.TITLE));
				tpSchemeAdapter.setDropDownViewResource(R.layout.spinner_text);
				editReqTPSchemeSpinner.setAdapter(tpSchemeAdapter);
				//get the index of city and set default selected into city spinner
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					
					if (jsonObj.getString(Constant.TPSchemesListing.TP_ID).equals(reqirementDetailObject.getString(Constant.MyRequirement.TP))) 
						editReqTPSchemeSpinner.setSelection(tpSchemeAdapter.getPosition(jsonObj.getString(Constant.TPSchemesListing.TITLE)));
				}
				editReqTPSchemeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View view,int position, long arg3) {
						try {
							tpSchemeId = jsonArray.getJSONObject(position).getString(Constant.TPSchemesListing.TP_ID);
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
	
	//This method is used to set default value into 
	private void setDefaultValue() {
		requirementDetailService = new RequirementDetailService();
		WebserviceClient requirementWebserviceClient = new WebserviceClient(getActivity(), requirementDetailService);
		requirementWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				JSONArray jsonArray = (JSONArray)response;
				try {
					if (response != null && !((JSONObject)jsonArray.get(0)).has(Constant.MyRequirement.API_STATUS)) {
						reqirementDetailObject = (JSONObject) jsonArray.getJSONObject(0);
						
						if(reqirementDetailObject.getString(Constant.MyRequirement.PROPERTY_TYPE).equals("Flat")) 
							editReqPropTypeSpinner.setSelection(prpoertyTypeOptionAdapter.getPosition("Flat"));
						else
							editReqPropTypeSpinner.setSelection(prpoertyTypeOptionAdapter.getPosition(reqirementDetailObject.getString(Constant.MyRequirement.PROPERTY_SUB_TYPE)));
						
						editReqOptionSpinner.setSelection(editMyReqSaleRentAdapter.getPosition(reqirementDetailObject.getString(Constant.MyRequirement.PURPOSE)));
						
						//Location edit box set prefilled location
						StringBuffer locationStr = new StringBuffer();
						List<String> items = Arrays.asList(reqirementDetailObject.getString(Constant.MyRequirementDetail.ALL_LOCATIONNAME).split("\\s*,\\s*"));
						for (int i = 0; i < items.size(); i++) {
							locationStr.append(items.get(i).trim());
							if(i != items.size() -1)
								locationStr.append(" - ");
						}
						
						//selectedAreaString = reqirementDetailObject.getString(Constant.MyRequirementDetail.ALL_LOCATIONS);
						/*if(reqirementDetailObject.getString(Constant.MyRequirementDetail.ALL_LOCATIONS).length() > 0 && reqirementDetailObject.getString(Constant.MyRequirementDetail.ALL_LOCATIONNAME).length() > 0 ){
							String[] areaId = reqirementDetailObject.getString(Constant.MyRequirementDetail.ALL_LOCATIONS).toString().split(",");
							String[] areaName = reqirementDetailObject.getString(Constant.MyRequirementDetail.ALL_LOCATIONNAME).split(",");
							
							for(int i=0; i<areaId.length;i++) {
								areaMap.put(areaName[i],Integer.parseInt(areaId[i]));
							}
						}*/
								
						
						editReqLocationEditBox.setText(locationStr);
						editReqFlatMinBeds.setSelection(bedAdapter.getPosition(reqirementDetailObject.getString(Constant.MyRequirement.MIN_BED)));
						editReqFlatMaxBeds.setSelection(bedAdapter.getPosition(reqirementDetailObject.getString(Constant.MyRequirement.MAX_BED)));
						
						editReqFlatPurposeSpinner.setSelection(editMyReqPurposeAdapter.getPosition(SpinnerItem.getAddPropPurposeListKey(reqirementDetailObject.getString(Constant.MyRequirement.ST_PURPOSE))));
						editReqFlatBuildingTypeSpinner.setSelection(buildingTypeAdapter.getPosition(SpinnerItem.getBuildingTypeListKey(reqirementDetailObject.getString(Constant.MyRequirement.RISE))));
						
						editReqFlatMinFloorSpinner.setSelection(floorAdapter.getPosition(SpinnerItem.getAddPropFloorListKey(reqirementDetailObject.getString(Constant.MyRequirement.MIN_FLOOR))));
						editReqFlatMaxFloorSpinner.setSelection(floorAdapter.getPosition(SpinnerItem.getAddPropFloorListKey(reqirementDetailObject.getString(Constant.MyRequirement.MAX_FLOOR))));
						
						editReqFlatMinAreaEditBox.setText(reqirementDetailObject.getString(Constant.MyRequirement.MIN_SQFOOT).substring(0,reqirementDetailObject.getString(Constant.MyRequirement.MIN_SQFOOT).length() - 3));
						editReqFlatMaxAreaEditBox.setText(reqirementDetailObject.getString(Constant.MyRequirement.MAX_SQFOOT).substring(0,reqirementDetailObject.getString(Constant.MyRequirement.MAX_SQFOOT).length() - 3));
						
						editReqFlatMinPlotAreaEditBox.setText(reqirementDetailObject.getString(Constant.MyRequirement.MIN_PLOT_AREA).substring(0,reqirementDetailObject.getString(Constant.MyRequirement.MIN_PLOT_AREA).length() - 3));
						editReqFlatMaxPlotAreaEditBox.setText(reqirementDetailObject.getString(Constant.MyRequirement.MAX_PLOT_AREA).substring(0,reqirementDetailObject.getString(Constant.MyRequirement.MAX_PLOT_AREA).length() - 3));
						
						editReqFlatMinConstructionAreaEditBox.setText(reqirementDetailObject.getString(Constant.MyRequirement.MIN_CONSTRUCTION_AREA).substring(0,reqirementDetailObject.getString(Constant.MyRequirement.MIN_CONSTRUCTION_AREA).length() - 3));
						editReqFlatMaxConstructionAreaEditBox.setText(reqirementDetailObject.getString(Constant.MyRequirement.MAX_CONSTRUCTION_AREA).substring(0,reqirementDetailObject.getString(Constant.MyRequirement.MAX_CONSTRUCTION_AREA).length() - 3));
						
						editReqMinRentBudget.setSelection(rentListAdapter.getPosition(SpinnerItem.getRentListKey(reqirementDetailObject.getString(Constant.MyRequirement.MIN_RENT))));
						editReqMaxRentBudget.setSelection(rentListAdapter.getPosition(SpinnerItem.getRentListKey(reqirementDetailObject.getString(Constant.MyRequirement.MAX_RENT))));
						
						editReqMinPriceBudget.setSelection(priceListAdapter.getPosition(SpinnerItem.getTotalPriceListKey(reqirementDetailObject.getString(Constant.MyRequirement.MIN_PRICE))));
						editReqMaxPriceBudget.setSelection(priceListAdapter.getPosition(SpinnerItem.getTotalPriceListKey(reqirementDetailObject.getString(Constant.MyRequirement.MAX_PRICE))));
						
						editReqHintEditBox.setText(reqirementDetailObject.getString(Constant.MyRequirement.HINT));
						editReqKeywordEditBox.setText(reqirementDetailObject.getString(Constant.MyRequirement.KEYWORD));
						
						editReqFlatFurnishOptionSpinner.setSelection(furnishOptionAdapter.getPosition(SpinnerItem.getFurnishOptionListKey(Integer.parseInt(reqirementDetailObject.getString(Constant.MyRequirement.FURNISH)))));
						
						zoneArray = new ArrayList<String>();
						for(int i=0;i<CheckBoxItem.zoneCheckItem.length;i++){
							zoneCheckBox = new CheckBox(getActivity());
							zoneCheckBox.setId(i);
							zoneCheckBox.setTextColor(Color.BLACK);
							zoneCheckBox.setButtonDrawable(R.xml.custom_checkbox);
							zoneCheckBox.setText(CheckBoxItem.zoneCheckItem[i]);
							String[] zoneValue;
							zoneValue = reqirementDetailObject.getString(Constant.MyRequirement.ZONE).split(",");
							editReqZoneLayout.addView(zoneCheckBox);
							for(int j=0; j<zoneValue.length;j++){
								if(zoneCheckBox.getText().equals(zoneValue[j])){
									zoneCheckBox.setChecked(true);
									zoneArray.add(zoneCheckBox.getText().toString());
								}
							}
							zoneCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
								@Override
								public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
									if(isChecked)
										zoneArray.add(buttonView.getText().toString());
									else
										zoneArray.remove(buttonView.getText().toString());
								}
							});
						}
						//get the list of state
						getStateList();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}	
		});
		requirementWebserviceClient.execute(userSessionManager.getSessionValue(Constant.Login.USER_ID),editReqBundle.getString(Constant.MyRequirement.REQUIREMENT_ID));
	}
	
	//Check property type 
	private boolean checkPropertyType() {
		if(editReqPropTypeSpinner.getSelectedItem().equals(" Select ") && editReqPropTypeSpinner.isShown()) {
			Toast.makeText(getActivity(), "Please select property type", 100).show();
			return true;
		}
		return false;
	}
	//Check property option
	private boolean checkPropertyOption() {
		if(editReqOptionSpinner.getSelectedItem().equals("Select") && editReqOptionSpinner.isShown()) {
			Toast.makeText(getActivity(), "Please select option", 100).show();
			return true;
		}
		return false;
	}
	
	//check the validation of location editbox
	private boolean areaEditBoxValidation(){
		if(editReqLocationEditBox.isShown()) {
			if(editReqLocationEditBox.getText().length() == 0 ) {
				editReqLocationEditBox.setError("Select Location");
				return true;
			}
		}
		return false;
	}
		
	//Chcek min bed validation
	private boolean checkMinBedValidation() {
		if(editReqFlatMinBeds.getSelectedItem().toString().equals("0")) {
			Toast.makeText(getActivity(), "Please select min bed", 100).show();
			return true;
		}
		return false;
	}
		
	//Chcek min bed validation
	private boolean checkMaxBedValidation() {
		if(editReqFlatMaxBeds.getSelectedItem().toString().equals("0")) {
			Toast.makeText(getActivity(), "Please select max bed", 100).show();
			return true;
		}
		return false;
	}
	
	//Check min bed is less then max bed
	private boolean checkMinLessThenMaxBed() {
		if(editReqFlatMinBeds.isShown() && editReqFlatMaxBeds.isShown()) {
			if(!checkMinBedValidation() && !checkMaxBedValidation()) {
				if(Integer.parseInt(editReqFlatMinBeds.getSelectedItem().toString()) >= Integer.parseInt(editReqFlatMaxBeds.getSelectedItem().toString())) {
					Toast.makeText(getActivity(), "Please select proepr min and max bed", 100).show();
					return true;
				} 
			} else {
				return true;
			} 
		}
		return false;
	}
	//check the validation of maximum area 
		private boolean maxAreaValidation() {
			if(editReqFlatMaxAreaEditBox.getText().length() == 0 ) {
				editReqFlatMaxAreaEditBox.setError("Enter Maximum Area");
				return true;
			}
			return false;
		}
		
		// check the validation of minimum area 
		private boolean minAreaValidation() {
			if(editReqFlatMinAreaEditBox.getText().length() == 0 ){
				editReqFlatMinAreaEditBox.setError("Enter Minimum Area");
				return true;
			}
			return false;
		}
		
		//check min area is less then max area
		private boolean checkMinLessThenMaxArea() {
			if(editReqFlatMinAreaEditBox.isShown() && editReqFlatMaxAreaEditBox.isShown()) {
				if(!minAreaValidation() && !maxAreaValidation() ) { 
					if((Integer.parseInt(editReqFlatMaxAreaEditBox.getText().toString())) <=  (Integer.parseInt(editReqFlatMinAreaEditBox.getText().toString()))){
						Toast.makeText(getActivity(), "Please select proper min and max area", Toast.LENGTH_LONG).show();
						return true;
					}
				} else {
					return true;
				}
			}
			return false;
		}
		
		//check min rent
		private boolean chekcMinRentValidation() {
			if(editReqMinRentBudget.getSelectedItem().toString().equals("Select")) {
				Toast.makeText(getActivity(), "Please select min rent", 100).show();
				return true;
			}
			return false;
		}
		
		//check min rent
		private boolean chekcMaxRentValidation() {
			if(editReqMaxRentBudget.getSelectedItem().toString().equals("Select")) {
				Toast.makeText(getActivity(), "Please select max rent", 100).show();
				return true;
			}
			return false;
		}
		
		//Check min/max rent
		private boolean checkMinMaxRent() {
			if(editReqOptionSpinner.getSelectedItem().toString().equals("Rent")) {
				if(editReqMinRentBudget.isShown() && editReqMaxRentBudget.isShown()) {
					if(!chekcMinRentValidation() && !chekcMaxRentValidation()) {
						if(!editReqMinRentBudget.getSelectedItem().toString().equals("5,00,000 and Above") && !editReqMaxRentBudget.getSelectedItem().toString().equals("5,00,000 and Above")) {
							String minRentString = String.valueOf(SpinnerItem.getRentList().get(editReqMinRentBudget.getSelectedItem().toString()));
							String maxRentString = String.valueOf(SpinnerItem.getRentList().get(editReqMaxRentBudget.getSelectedItem().toString()));
							if(Integer.parseInt(maxRentString.substring(0,maxRentString.length()-3)) <= Integer.parseInt(minRentString.substring(0,minRentString.length()-3))) {
								Toast.makeText(getActivity(), "Please Select Proper Minimum and Maximum Rent", 100).show();
								return true;	
							}
						} else {
							return false;
						}
					} else {
						return true;
					}
				}
			}
			return false;
		}
		
		//check min price validation
		private boolean checkMinPriceValidation() {
			if(editReqMinPriceBudget.getSelectedItem().toString().equals(" Select ")) {
				Toast.makeText(getActivity(), "Please select min price", 100).show();
				return true;
			}
			return false;
		}
		
		//check max price validation
		private boolean checkMaxPriceValidation() {
			if(editReqMaxPriceBudget.getSelectedItem().toString().equals(" Select ")) {
				Toast.makeText(getActivity(), "Please select min price", Toast.LENGTH_LONG).show();
				return true;
			}
			return false;
		}
		
	//Check min/max price
	private boolean checkMinMaxPrice() {
		if(editReqOptionSpinner.getSelectedItem().toString().equals("Sale")) {
			if(editReqMinPriceBudget.isShown() && editReqMaxPriceBudget.isShown()) {
				if(!checkMinPriceValidation() && !checkMaxPriceValidation()) {
					if(!editReqMinPriceBudget.getSelectedItem().toString().equals("5 Crore and Above") && !editReqMaxPriceBudget.getSelectedItem().toString().equals("5 Crore and Above")) {
						String minpriceString = String.valueOf(SpinnerItem.getTotalPriceList().get(editReqMinPriceBudget.getSelectedItem().toString()));
						String maxPriceString = String.valueOf(SpinnerItem.getTotalPriceList().get(editReqMaxPriceBudget.getSelectedItem().toString()));
						if(Integer.parseInt(maxPriceString.substring(0,maxPriceString.length()-3)) <= Integer.parseInt(minpriceString.substring(0,minpriceString.length()-3))) {
							Toast.makeText(getActivity(), "Please Select Proper Minimum and Maximum Price", Toast.LENGTH_LONG).show();
							return true;
						}
					} else {
						return false;
					}
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	//Check City
	private boolean checkCity() {
		if(editReqCitySpinner.getSelectedItem() != null) {
			if(editReqCitySpinner.getSelectedItem().equals("No city found") && editReqCitySpinner.isShown()) {
				Toast.makeText(getActivity(), "No city found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
		
	//Check location
	private boolean checkDistrict() {
		if(editReqDistrictSpinner.getSelectedItem() != null) {
			if(editReqDistrictSpinner.getSelectedItem().equals("No district found") && editReqDistrictSpinner.isShown()) {
				Toast.makeText(getActivity(), "No district found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
		
	//Notify adapter
	private void notifyAdapter(ArrayAdapter<String> adapter, String spinnerString) {
		if(adapter == null) {
			adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text);
		}
		
		if(adapter != null) {
			adapter.clear();
			if(spinnerString.equals("city")) {
				adapter.insert("No city found", 0);
				editReqCitySpinner.setAdapter(adapter);
				editReqCitySpinner.setEnabled(false);
			} else if(spinnerString.equals("district")) {
				adapter.insert("No district found", 0);
				editReqDistrictSpinner.setAdapter(adapter);
				editReqDistrictSpinner.setEnabled(false);
			}
			adapter.notifyDataSetChanged();
		}
	}
}