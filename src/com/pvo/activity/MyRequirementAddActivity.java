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
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.CityListService;
import com.pvo.user.service.DistrictListService;
import com.pvo.user.service.MyRequirementAddService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.pvo.util.JSONUtils;

//Add new Requirement To Particular Broker
//Layout File: activity_add_my_requirement.xml
public class MyRequirementAddActivity extends PVOFragment {
	
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
	private Spinner addReqPropTypeSpinner;
	private TextView addReqPropType;
	private Spinner addReqOptionSpinner;
	private TextView addReqOption;
	private Spinner addReqStateSpinner;
	private Spinner addReqCitySpinner;
	private Spinner addReqDistrictSpinner;
	private EditText addReqLocationEditBox;
	private TextView addReqLocationTv;
	private ArrayAdapter<String> districtAdapter;
	private ArrayAdapter<String> cityAdapter;
			
	//Bed Layout
	private RelativeLayout addReqBedLayout;
	private Spinner addReqFlatMinBeds; 
	private Spinner addReqFlatMaxBeds;
	private TextView addReqFlatBeds;
		
	//TP Scheme Layout
	private RelativeLayout addReqTPSchemeLayout;
	private Spinner addReqTPSchemeSpinner; 
	private String tpSchemeId;
		
	//Zone Layout
	private LinearLayout addReqZoneLayout; 
	private CheckBox zoneCheckBox;
	private ArrayList<String> zoneArray;
		
	//Furnish Option
	private RelativeLayout addReqFurnishOptionLayout; 
	private Spinner addReqFlatFurnishOptionSpinner; 

	//Purpose Layout
	private RelativeLayout addReqPurposeLayout; 
	private Spinner addReqFlatPurposeSpinner; 
		
	//Floor Layout
	private  RelativeLayout addReqFloorLayout;
	private Spinner addReqFlatMinFloorSpinner;
	private Spinner  addReqFlatMaxFloorSpinner;
		
	//Building Type
	private RelativeLayout addReqBuildingType;
	private Spinner addReqFlatBuildingTypeSpinner;
		
	//Area Layout
	private RelativeLayout addReqAreaLayout;
	private EditText addReqFlatMinAreaEditBox;
	private EditText addReqFlatMaxAreaEditBox;
	private TextView addReqFlatArea;
		
	//Plot Area
	private RelativeLayout addReqPlotAreaLayout;
	private EditText addReqFlatMinPlotAreaEditBox;
	private EditText addReqFlatMaxPlotAreaEditBox;
		
	//Construction area
	private RelativeLayout addReqConstructionAreaLayout;
	private EditText addReqFlatMinConstructionAreaEditBox;
	private EditText addReqFlatMaxConstructionAreaEditBox;
		
	//Rent Budge Related Layout Layout	
	private RelativeLayout addMyReqRentLayout;
	private Spinner addReqMinRentBudget;
	private Spinner addReqMaxRentBudget;
	private TextView addReqRentBudget;

	//Price Budge Related Layout Layout	
	private RelativeLayout addMyReqPriceLayout;
	private Spinner addReqMinPriceBudget;
	private Spinner addReqMaxPriceBudget;
	private TextView addReqPriceBudget;
	
	//Extra Information Controller
	private EditText addReqHintEditBox;
	private EditText addReqKeywordEditBox;
	
	//Button Save,Next,Previous,cancel This button is used to move the next layout or previous
	private Button addReqSaveButton;
	private Button addReqNextButton;
	private Button addReqPreviousButton;
	private Button addReqCancelButton;

	//Different Layout
	private RelativeLayout addReqIconLayout2;
	private RelativeLayout addReqMainContainer;
	private RelativeLayout addReqPublicInfoLayout;
	private RelativeLayout addReqFaltLayout;
	private RelativeLayout addReqPriceLayout;
	private RelativeLayout addReqExtraInfoLayout;
	
	//User Service to get the response of the Particular web service 
	private JSONArray stateResponse;
	private CityListService cityListService;
	private DistrictListService districtListService;
	private MyRequirementAddService addMyRequirementService;
	private Map<String,Integer> areaMap = new HashMap<String, Integer>();
	
	//Get Stored User Id From User SessionManager 
	private UserSessionManager userSessionManager;
	
	//this is used for Store JsonArray
	private JSONArray cityResponse;

	//this is used set the tab Index of the Current Layout and Image Icon
	private int activeTabIndex = 0;
	private JSONArray districtResponse;
	private TextView addReqFloorMinErrorMsg;
	private TextView addReqBedErrorMsg;
	private TextView addReqRentErrorMsg;
	
	//set the layout Content View 
	public MyRequirementAddActivity() {
		setContentView(R.layout.activity_myrequirement_add);	
	}

	//init Method
	@SuppressWarnings("unchecked")
	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		//Service for get Particular API Response 
		cityListService 		= new CityListService();
		districtListService		= new DistrictListService();
		addMyRequirementService = new MyRequirementAddService();
		
		//get Login User_Id from stored Session
		userSessionManager 		= new UserSessionManager(getActivity().getApplicationContext());
		
		//Different Layout of Add property
		addReqPublicInfoLayout 	= (RelativeLayout) findViewById(R.id.addReqPublicInfoLayout);
		addReqFaltLayout 		= (RelativeLayout) findViewById(R.id.addReqFaltLayout);
		addReqPriceLayout 		= (RelativeLayout) findViewById(R.id.addReqPriceLayout);
		addReqExtraInfoLayout 	= (RelativeLayout) findViewById(R.id.addReqExtraInfoLayout);
		addReqMainContainer 	= (RelativeLayout) findViewById(R.id.addReqMainContainer);
		addReqStateSpinner 		= (Spinner) findViewById(R.id.addMyReqStateSpinner);
		addReqCitySpinner 		= (Spinner) findViewById(R.id.addReqCitySpinner);
		addReqDistrictSpinner 	= (Spinner) findViewById(R.id.addReqDistrictSpinner);
		
		//=====>> Public Information Controller ID <<=====
			
		//1) Property Type ==> Fill the  Property Type Spinner Using hash Map
		addReqPropType = (TextView) findViewById(R.id.addReqPropType);
		addReqPropType.setText(Html.fromHtml("Property Type" + "<sup><font size=5 color=red>*</font></sup>"));
		addReqPropTypeSpinner = (Spinner) findViewById(R.id.addReqPropTypeSpinner);
		final ArrayList<String> propertyTypeOptionList=new ArrayList<String>();
		propertyTypeOptionList.addAll(SpinnerItem.getPropertyTypeOptionList().keySet());
		//Set spinner item read only which have below number
		ArrayAdapter< String> prpoertyTypeOptionAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,propertyTypeOptionList){
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
		addReqPropTypeSpinner.setAdapter(prpoertyTypeOptionAdapter);
		//addReqPropTypeSpinner.setSelection(1);
		//End
		 
		 // 2) Option ==> fill sale/Rent Spinner using Hash map 1)Rent 2)Sale
		addReqOption = (TextView) findViewById(R.id.addReqOption);
		addReqOption.setText(Html.fromHtml("Options" + "<sup><font size=5 color=red>*</font></sup>"));
		addReqOptionSpinner = (Spinner) findViewById(R.id.addReqOptionSpinner);
		ArrayList<String> addMyReqSaleRentOptionList=new ArrayList<String>();
		addMyReqSaleRentOptionList.addAll(SpinnerItem.getSaleRentList().keySet());
		ArrayAdapter< String> addMyReqSaleRentOptionAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,addMyReqSaleRentOptionList);
		addMyReqSaleRentOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
		addReqOptionSpinner.setAdapter(addMyReqSaleRentOptionAdapter);
		//END
		
		// 3) State==> onLoad call the getstateList method if flag not "State" call the getStateList method fist time otherwise set adapter as previous
		getStateList();
			
		//6) Location ==> onclick of location edit box open the location popup window and select the multiple location
		addReqLocationTv = (TextView) findViewById(R.id.addReqLocationTv);
		addReqLocationTv.setText(Html.fromHtml("Location" + "<sup><font size=5 color=red>*</font></sup>"));
		addReqLocationEditBox = (EditText) findViewById(R.id.addReqLocationEditBox);
		addReqLocationEditBox.setFocusable(false);
		addReqLocationEditBox.setInputType(InputType.TYPE_NULL);
		addReqLocationEditBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addReqLocationEditBox.setError(null);
				getAreaList();
			}
		});

		// ==> Property (Flat) Related Information Controller ID <<==
		//Bed Layout
		addReqBedLayout 	= (RelativeLayout) findViewById(R.id.addReqBedLayout);
		addReqFlatMinBeds 	= (Spinner) findViewById(R.id.addReqFlatMinBeds); 
		addReqFlatMaxBeds 	= (Spinner) findViewById(R.id.addReqFlatMaxBeds);
		addReqFlatBeds		= (TextView) findViewById(R.id.addReqFlatBeds);
		addReqFlatBeds.setText(Html.fromHtml("Beds" + "<sup><font size=5 color=red>*</font></sup>"));
		
		//Fill the Min/Max Bed Spinner Using HashMap 
		addReqBedErrorMsg = (TextView) findViewById( R.id.addReqBedErrorMsg);
		ArrayList<String> bedList=new ArrayList<String>();
		bedList.addAll(SpinnerItem.getAddPropBedList().keySet());
		ArrayAdapter< String> bedAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,bedList);
		bedAdapter.setDropDownViewResource(R.layout.spinner_text);
		addReqFlatMinBeds.setAdapter(bedAdapter);
		addReqFlatMaxBeds.setAdapter(bedAdapter);
		//Hide error message textview,show when min/max bed is not select proper	
		addReqFlatMinBeds.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				addReqBedErrorMsg.setVisibility(View.GONE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		addReqFlatMaxBeds.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				addReqBedErrorMsg.setVisibility(View.GONE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		//END BED	
		
		//TP Scheme Layout
		addReqTPSchemeLayout 	= (RelativeLayout) findViewById(R.id.addReqTPSchemeLayout);
		addReqTPSchemeSpinner 	= (Spinner) findViewById(R.id.addReqTPSchemeSpinner);

		//Zone Layout
		addReqZoneLayout = (LinearLayout) findViewById(R.id.addReqZoneLayout); 
		zoneArray = new ArrayList<String>();
		for(int i=0;i<CheckBoxItem.zoneCheckItem.length;i++){
			zoneCheckBox = new CheckBox(getActivity());
			zoneCheckBox.setId(i);
			zoneCheckBox.setTextColor(Color.BLACK);
			zoneCheckBox.setButtonDrawable(R.xml.custom_checkbox);
			zoneCheckBox.setText(CheckBoxItem.zoneCheckItem[i]);
			zoneCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
					if (isChecked) {
						zoneArray.add(buttonView.getText().toString());
					} else {
						zoneArray.remove(buttonView.getText().toString());
					}
				}
			});
			addReqZoneLayout.addView(zoneCheckBox);
		}
		//END
			
		//Furnish Option
		addReqFurnishOptionLayout 		 = (RelativeLayout) findViewById(R.id.addReqFurnishOptionLayout); 
		addReqFlatFurnishOptionSpinner	 = (Spinner) findViewById(R.id.addReqFlatFurnishOptionSpinner);
	
		//Fill the Furnish Option spinner Using hashmap 1)Any 2)Unfurnished 3)Semifurnished 4)Fully Furnished
		ArrayList<String> furnishOptionList = new ArrayList<String>();
		furnishOptionList.addAll(SpinnerItem.getFurnishOptionList().keySet());
		ArrayAdapter<String> furnishOptionAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,furnishOptionList);
		furnishOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
		addReqFlatFurnishOptionSpinner.setAdapter(furnishOptionAdapter);
		//End 
	
		//Property Purpose Spinner 1)Any 2)Residential 3)Commercial
		addReqPurposeLayout 		= (RelativeLayout) findViewById(R.id.addReqPurposeLayout); 
		addReqFlatPurposeSpinner 	= (Spinner) findViewById(R.id.addReqFlatPurposeSpinner); 
		ArrayList<String> purposeTypeList = new ArrayList<String>();
		purposeTypeList.addAll(SpinnerItem.getAddPropPurposeList().keySet());
		ArrayAdapter< String> purposeAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,purposeTypeList);
		purposeAdapter.setDropDownViewResource(R.layout.spinner_text);
		addReqFlatPurposeSpinner.setAdapter(purposeAdapter);
		//END	
			
		//Floor Layout
		addReqFloorLayout 			= (RelativeLayout) findViewById(R.id.addReqFloorLayout);
		addReqFlatMinFloorSpinner 	= (Spinner) findViewById(R.id.addReqFlatMinFloorSpinner);
		addReqFlatMaxFloorSpinner 	= (Spinner) findViewById(R.id.addReqFlatMaxFloorSpinner);

		//Fill The Floor spinner Using HashMap 
		addReqFloorMinErrorMsg = (TextView) findViewById(R.id.addReqFloorMinErrorMsg);
		ArrayList<String> floorTypeList=new ArrayList<String>();
		floorTypeList.addAll(SpinnerItem.getAddReqFloorList().keySet());
		ArrayAdapter< String> floorAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,floorTypeList);
		floorAdapter.setDropDownViewResource(R.layout.spinner_text);
		addReqFlatMinFloorSpinner.setAdapter(floorAdapter);
		addReqFlatMaxFloorSpinner.setAdapter(floorAdapter);
		addReqFlatMinFloorSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				addReqFloorMinErrorMsg.setVisibility(View.GONE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		addReqFlatMaxFloorSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				addReqFloorMinErrorMsg.setVisibility(View.GONE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		//End

		//Bulilding Type Spinner 1) Any 2) Low Rise 3) High Rise
		addReqBuildingType			  = (RelativeLayout) findViewById(R.id.addReqBuildingType);
		addReqFlatBuildingTypeSpinner = (Spinner) findViewById(R.id.addReqFlatBuildingTypeSpinner);
		
		//Fill The BuildingType spinner Using HashMap
		addReqFlatBuildingTypeSpinner = (Spinner)findViewById(R.id.addReqFlatBuildingTypeSpinner);
		ArrayList<String> buildingTypeList=new ArrayList<String>();
		buildingTypeList.addAll(SpinnerItem.getBuildingTypeList().keySet());
		ArrayAdapter< String> buildingTypeAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,buildingTypeList);
		buildingTypeAdapter.setDropDownViewResource(R.layout.spinner_text);
		addReqFlatBuildingTypeSpinner.setAdapter(buildingTypeAdapter);
		//End 
				
		//Area Layout
		addReqAreaLayout 		 		= (RelativeLayout) findViewById(R.id.addReqAreaLayout);
		addReqFlatMinAreaEditBox 		= (EditText) findViewById(R.id.addReqFlatMinAreaEditBox);
		addReqFlatMaxAreaEditBox 		= (EditText) findViewById(R.id.addReqFlatMaxAreaEditBox);
		addReqFlatArea					= (TextView) findViewById(R.id.addReqFlatArea);
		addReqFlatArea.setText(Html.fromHtml("Area" + "<sup><font size=5 color=red>*</font></sup>"));
		
		//Plot Area
		addReqPlotAreaLayout 			= (RelativeLayout) findViewById(R.id.addReqPlotAreaLayout);
		addReqFlatMinPlotAreaEditBox 	= (EditText) findViewById(R.id.addReqFlatMinPlotAreaEditBox);
		addReqFlatMaxPlotAreaEditBox 	= (EditText) findViewById(R.id.addReqFlatMaxPlotAreaEditBox);
			
		//Construction area
		addReqConstructionAreaLayout 	= (RelativeLayout) findViewById(R.id.addReqConstructionAreaLayout);
		addReqFlatMinConstructionAreaEditBox = (EditText) findViewById(R.id.addReqFlatMinConstructionAreaEditBox);
		addReqFlatMaxConstructionAreaEditBox = (EditText) findViewById(R.id.addReqFlatMaxConstructionAreaEditBox); 
		
		//Rent Related Information Controller ID Fill the Min/Max Sinner Using Hash Map
		addMyReqRentLayout 				= (RelativeLayout) findViewById(R.id.addMyReqRentLayout);
		addMyReqPriceLayout 			= (RelativeLayout) findViewById(R.id.addMyReqPriceLayout);
			
		//Fill the Price Budge Spinner Using Hash Map
		addReqMinPriceBudget 			= (Spinner) findViewById(R.id.addReqMinPriceBudget);
		addReqMaxPriceBudget 			= (Spinner) findViewById(R.id.addReqMaxPriceBudget);
		addReqPriceBudget				= (TextView) findViewById(R.id.addReqPriceBudget);
		addReqPriceBudget.setText(Html.fromHtml("Price Budget" + "<sup><font size=5 color=red>*</font></sup>"));
		
		ArrayList<String> totalPriceList=new ArrayList<String>();
		totalPriceList.addAll(SpinnerItem.getTotalPriceList().keySet());
		ArrayAdapter< String> totalPriceAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,totalPriceList);
		totalPriceAdapter.setDropDownViewResource(R.layout.spinner_text);
		addReqMinPriceBudget.setAdapter(totalPriceAdapter);
		addReqMaxPriceBudget.setAdapter(totalPriceAdapter);
		//END
				
		//Fill the Rent Budge Spinner Using Hash Map
		addReqRentErrorMsg 		= (TextView) findViewById(R.id.addReqRentErrorMsg);
		addReqMinRentBudget		= (Spinner)findViewById(R.id.addReqMinRentBudget);
		addReqMaxRentBudget		= (Spinner)findViewById(R.id.addReqMaxRentBudget);
		addReqRentBudget		= (TextView) findViewById(R.id.addReqRentBudget);
		addReqRentBudget.setText(Html.fromHtml("Rent" + "<sup><font size=5 color=red>*</font></sup>"));
		
		ArrayList<String> minRentList = new ArrayList<String>();
		minRentList.addAll(SpinnerItem.getRentList().keySet());
		ArrayAdapter< String> minRentAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,minRentList);
		minRentAdapter.setDropDownViewResource(R.layout.spinner_text);
		addReqMinRentBudget.setAdapter(minRentAdapter);
		addReqMaxRentBudget.setAdapter(minRentAdapter);
		addReqMinRentBudget.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				addReqRentErrorMsg.setVisibility(View.GONE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		addReqMaxRentBudget.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				addReqRentErrorMsg.setVisibility(View.GONE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		//END
			
		//========>>> Extra Information Controller ID <<<==============
		addReqHintEditBox		= (EditText)findViewById(R.id.addReqHintEditBox);
		addReqKeywordEditBox	= (EditText)findViewById(R.id.addReqKeywordEditBox);
	
		//Button Save,Next,Previous,Cancel this is used to move next layout or Previous Layout
		addReqNextButton		= (Button)findViewById(R.id.addReqNextButton);
		addReqPreviousButton	= (Button)findViewById(R.id.addReqPreviousButton);
		addReqCancelButton		= (Button)findViewById(R.id.addReqCancelButton);
		
		//cancel the current fragment and goto previous fragment
		addReqCancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});
		
		//onclick of save button Save the Requirement 
		addReqSaveButton		= (Button)findViewById(R.id.addReqSaveButton);
		addReqSaveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String selectedAreaString = "";
				if(!areaEditBoxValidation()) {
					String area = addReqLocationEditBox.getText().toString();
					String[] selectedArea=area.split(" - ");
					for (int i = 0; i < selectedArea.length; i++) { // Comma Seprated String of Selected area Code
						selectedAreaString += areaMap.get(selectedArea[i]);
						if(i != selectedArea.length-1)
							selectedAreaString += ",";
					}
				}
				
				if(!checkPropertyType() && !checkPropertyOption() && !checkDistrict() && !checkCity() && !checkMinLessThenMaxBed() && !checkMinLessThenMaxArea() && !checkMinLessThenMaxArea() && (!checkMinMaxRent() || !checkMinMaxPrice()) )  
					try {
						WebserviceClient addMyRequirement = new WebserviceClient(MyRequirementAddActivity.this, addMyRequirementService);
    					addMyRequirement.setResponseListner(new ResponseListner() {
							@Override
							public void handleResponse(Object res) {
								final JSONObject response=(JSONObject) res;
								try {
									if(response != null) {
										if(String.valueOf(response.getString(Constant.AddProperty.API_STATUS)).equals("1")){
											Toast.makeText(getActivity(), String.valueOf(response.getString(Constant.AddProperty.API_MESSAGE)), 100).show();
											Bundle addMyReqBumdle = new Bundle();
											addMyReqBumdle.putString("Type", "Search");
											MyRequirementListActivity myRequirementActivity = new MyRequirementListActivity();
											myRequirementActivity.setArguments(addMyReqBumdle);
											((MainFragmentActivity)getActivity()).redirectScreenWithoutStack(myRequirementActivity);
										} else {
											Toast.makeText(getActivity(), String.valueOf(response.getString(Constant.AddProperty.API_MESSAGE)), 100).show();
										}
									}
								} catch(JSONException e) {
									e.printStackTrace();
								}
							}
						});
    					
						addMyRequirement.execute(
							userSessionManager.getSessionValue(Constant.Login.USER_ID),//user ID
							"0",//If action=add then id=0
							String.valueOf(SpinnerItem.getPropertyTypeOptionList().get(addReqPropTypeSpinner.getSelectedItem().toString())),//cmbproptype
							addReqPropTypeSpinner.getSelectedItem().toString(),//propertysubtype
							"1",//countryid it's for India
							stateResponse.getJSONObject(addReqStateSpinner.getSelectedItemPosition()).getString(Constant.State.STATEID),//String.valueOf(stateObject.getString(Constant.State.STATEID)),//stateId
							cityResponse.getJSONObject(addReqCitySpinner.getSelectedItemPosition()).getString(Constant.City.CITY_ID),//String.valueOf(cityObject.getString(Constant.City.CITY_ID)),//City Id
							districtResponse.getJSONObject(addReqDistrictSpinner.getSelectedItemPosition()).getString(Constant.District.DISTRICT_ID),//String.valueOf(districtObject.getString(Constant.District.DISTRICT_ID)),//Distric ID
							String.valueOf(selectedAreaString),//location
							addReqHintEditBox.getText().toString(),//hint
							addReqKeywordEditBox.getText().toString(),//keyword
							addReqOptionSpinner.getSelectedItem().toString(),//stroptions
							String.valueOf(SpinnerItem.getTotalPriceList().get(addReqMinPriceBudget.getSelectedItem().toString())),//txtminprice
							String.valueOf(SpinnerItem.getTotalPriceList().get(addReqMaxPriceBudget.getSelectedItem().toString())),//txtmaxprice
							String.valueOf(SpinnerItem.getRentList().get(addReqMinRentBudget.getSelectedItem().toString())),//txtminrent
							String.valueOf(SpinnerItem.getRentList().get(addReqMaxRentBudget.getSelectedItem().toString())),//txtmaxrent
							addReqFlatMinBeds.getSelectedItem().toString(),//minbed
							addReqFlatMaxBeds.getSelectedItem().toString(),//maxbed
							String.valueOf(SpinnerItem.getAddReqFloorList().get(addReqFlatMinFloorSpinner.getSelectedItem().toString())),//minfloor
							String.valueOf(SpinnerItem.getAddReqFloorList().get(addReqFlatMaxFloorSpinner.getSelectedItem().toString())),//maxfloor
							String.valueOf(SpinnerItem.getFurnishOptionList().get(addReqFlatFurnishOptionSpinner.getSelectedItem().toString())),//furnishstatus
							String.valueOf(SpinnerItem.getBuildingTypeList().get(addReqFlatBuildingTypeSpinner.getSelectedItem().toString())),//rise
							addReqFlatMinAreaEditBox.getText().toString(),//minsqfoot
							addReqFlatMaxAreaEditBox.getText().toString(),//maxsqfoot
							addReqFlatMinPlotAreaEditBox.getText().toString(),//minplotarea
							addReqFlatMaxPlotAreaEditBox.getText().toString(),//maxplotarea
							addReqFlatMinConstructionAreaEditBox.getText().toString(),//minconstrarea
							addReqFlatMaxConstructionAreaEditBox.getText().toString(),//maxconstrarea
							String.valueOf(StringUtils.defaultString(tpSchemeId)),//cmbtpscheme
							addReqFlatPurposeSpinner.getSelectedItem().toString(),//stpurpose
							StringUtils.join(zoneArray,",")//chkzone
						);
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
			}
		});
		
		//********* ANKUR ********
		//next Button to move the next Layout 
		addReqNextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//System.out.println("1) checkPropertyType()"+checkPropertyType());
				//System.out.println("2) checkPropertyOption()"+checkPropertyOption());
				//System.out.println("3) areaEditBoxValidation()"+areaEditBoxValidation());
				//System.out.println("4) checkMinLessThenMaxBed()"+checkMinLessThenMaxBed());
				//System.out.println("5) checkMinLessThenMaxArea()"+checkMinLessThenMaxArea());
				//System.out.println("6) checkMinMaxRent()"+checkMinMaxRent());
				//System.out.println("7) checkMinMaxPrice()"+checkMinMaxPrice());
				
				if(checkPropertyType()) {//1) Check proerty type
					//System.out.println("==> 1)");
					activeTabIndex = ((ViewGroup)addReqMainContainer).indexOfChild(addReqPublicInfoLayout);
                    addReqIconLayout2.getChildAt(activeTabIndex).performClick();
				} else if(checkPropertyOption()) {//2) check property option
					//System.out.println("==> 2)");
					activeTabIndex = ((ViewGroup)addReqMainContainer).indexOfChild(addReqPublicInfoLayout);
                    addReqIconLayout2.getChildAt(activeTabIndex).performClick();
				} else if(checkDistrict()) {//3) check District
					activeTabIndex = ((ViewGroup)addReqMainContainer).indexOfChild(addReqPublicInfoLayout);
                    addReqIconLayout2.getChildAt(activeTabIndex).performClick();
				} else if(checkCity()) {//4) check city
					//System.out.println("==> 2)");
					activeTabIndex = ((ViewGroup)addReqMainContainer).indexOfChild(addReqPublicInfoLayout);
                    addReqIconLayout2.getChildAt(activeTabIndex).performClick();
				} else if(areaEditBoxValidation()) {//3) Check location
					//System.out.println("==> 3)");
					activeTabIndex = ((ViewGroup)addReqMainContainer).indexOfChild(addReqPublicInfoLayout);
                    addReqIconLayout2.getChildAt(activeTabIndex).performClick();
				} else if(checkMinLessThenMaxBed()) {//4) check Min/max bed
					//System.out.println("==> 4)");
					activeTabIndex = ((ViewGroup)addReqMainContainer).indexOfChild(addReqFaltLayout);
                    addReqIconLayout2.getChildAt(activeTabIndex).performClick();
				}  else if(checkMinLessThenMaxArea()){//5) checkMinLessThanMaxArea()
					//System.out.println("==> 5)");
					activeTabIndex = ((ViewGroup)addReqMainContainer).indexOfChild(addReqFaltLayout);
                    addReqIconLayout2.getChildAt(activeTabIndex).performClick();
				}else if(checkMinMaxRent()) {//6) check min/max rent
					//System.out.println("==> 6)");
					activeTabIndex = ((ViewGroup)addReqMainContainer).indexOfChild(addReqPriceLayout);
                    addReqIconLayout2.getChildAt(activeTabIndex).performClick();
				} else if(checkMinMaxPrice()) {//7)check min/max price
					//System.out.println("==> 7)");
					activeTabIndex = ((ViewGroup)addReqMainContainer).indexOfChild(addReqPriceLayout);
                    addReqIconLayout2.getChildAt(activeTabIndex).performClick();
				} else {
					//System.out.println("==> Else)");
					++activeTabIndex;
					setBackgroundImageView(addReqIconLayout2.getChildAt(activeTabIndex));
					addReqIconLayout2.getChildAt(activeTabIndex).performClick();
				}
			}
		});
		
		//previous Button to move Previous Layout
		addReqPreviousButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				--activeTabIndex;
				setBackgroundImageView(addReqIconLayout2.getChildAt(activeTabIndex));
				addReqIconLayout2.getChildAt(activeTabIndex).performClick();
			}
		});
		
		//Bind event to all image icons of left side icon bar
		addReqIconLayout2 = (RelativeLayout) findViewById(R.id.addReqIconLayout2);
		int totalChild = addReqIconLayout2.getChildCount();
		
		for(int i = 0; i < totalChild; i++) {
			View imageView = addReqIconLayout2.getChildAt(i);
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					addReqPreviousButton.setVisibility(View.VISIBLE);
					addReqNextButton.setVisibility(View.VISIBLE);
					addReqSaveButton.setVisibility(View.GONE);
					setBackgroundImageView(v);
					TextView header = (TextView) findViewById(R.id.addReqHeaderTv2);
					//Set Header according to icon
					switch (v.getId()) {
						case R.id.addReqPublicInfoImg://1)
							header.setText(PUBLIC_INFORMAITON_HEADING);
							//Hide previous button for first screen
							addReqPreviousButton.setVisibility(View.GONE);
							setVisibility(addReqPublicInfoLayout);
							activeTabIndex = ((ViewGroup)addReqMainContainer).indexOfChild(addReqPublicInfoLayout);
							break;
						case R.id.addReqflatImg://2) 
							if(checkPropertyType() || checkPropertyOption() ||areaEditBoxValidation()) {
								addReqNextButton.performClick();
							} else {
								getTpSchem();
								hideLayoutChild(addReqFaltLayout);
								header.setText("Requirement ("+addReqPropTypeSpinner.getSelectedItem().toString()+") Related");
								setVisibility(addReqFaltLayout);
								activeTabIndex = ((ViewGroup)addReqMainContainer).indexOfChild(addReqFaltLayout);
								if(SpinnerItem.getPropertyTypeOptionList().get(addReqPropTypeSpinner.getSelectedItem().toString()).equals("Flat")){
									addReqBedLayout.setVisibility(View.VISIBLE);
									addReqFurnishOptionLayout.setVisibility(View.VISIBLE);
									addReqPurposeLayout.setVisibility(View.VISIBLE);
									addReqFloorLayout.setVisibility(View.VISIBLE);
									addReqBuildingType.setVisibility(View.VISIBLE);
									addReqAreaLayout.setVisibility(View.VISIBLE);
								} else if(SpinnerItem.getPropertyTypeOptionList().get(addReqPropTypeSpinner.getSelectedItem().toString()).equals("Shop")){
									addReqFloorLayout.setVisibility(View.VISIBLE);
									addReqFurnishOptionLayout.setVisibility(View.VISIBLE);
									addReqAreaLayout.setVisibility(View.VISIBLE);
									addReqPlotAreaLayout.setVisibility(View.VISIBLE);
									addReqConstructionAreaLayout.setVisibility(View.VISIBLE);
									addReqBuildingType.setVisibility(View.VISIBLE);
								} else if(SpinnerItem.getPropertyTypeOptionList().get(addReqPropTypeSpinner.getSelectedItem().toString()).equals("Bunglow")&& !addReqPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)")){
									addReqBedLayout.setVisibility(View.VISIBLE);
									addReqFurnishOptionLayout.setVisibility(View.VISIBLE);
									addReqPurposeLayout.setVisibility(View.VISIBLE);
									addReqPlotAreaLayout.setVisibility(View.VISIBLE);
									addReqConstructionAreaLayout.setVisibility(View.VISIBLE);
								} else if(addReqPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)")){
									addReqPurposeLayout.setVisibility(View.VISIBLE);
									addReqTPSchemeLayout.setVisibility(View.VISIBLE);
									addReqZoneLayout.setVisibility(View.VISIBLE);
									addReqPlotAreaLayout.setVisibility(View.VISIBLE);
									addReqConstructionAreaLayout.setVisibility(View.VISIBLE);
								} else {
									addReqPurposeLayout.setVisibility(View.VISIBLE);
									addReqTPSchemeLayout.setVisibility(View.VISIBLE);
									addReqZoneLayout.setVisibility(View.VISIBLE);
									addReqPlotAreaLayout.setVisibility(View.VISIBLE);
									addReqConstructionAreaLayout.setVisibility(View.VISIBLE);
								}
							}
								break;
							
						case R.id.addReqPriceImg://3) 
							if(checkMinLessThenMaxBed() || checkMinLessThenMaxArea()) {
								addReqNextButton.performClick();
							} else {
								hideLayoutChild(addReqPriceLayout);
								header.setText(PRICE_INFORMAITON_HEADING);
								setVisibility(addReqPriceLayout);
								activeTabIndex = ((ViewGroup)addReqMainContainer).indexOfChild(addReqPriceLayout);
								if(addReqOptionSpinner.getSelectedItem().toString().equals("Rent")) {
									addMyReqRentLayout.setVisibility(View.VISIBLE);
								} else {
									addMyReqPriceLayout.setVisibility(View.VISIBLE);
								}	
							}
							break;
							
						case R.id.addReqExtraInfoImg://4)
							if(checkMinMaxRent() || checkMinMaxPrice()) {
								addReqNextButton.performClick();
							} else { 
								header.setText(EXTRA_INFORMAITON_HEADING);
								addReqNextButton.setVisibility(View.GONE);
								addReqSaveButton.setVisibility(View.VISIBLE);
								setVisibility(addReqExtraInfoLayout);
								activeTabIndex = ((ViewGroup)addReqMainContainer).indexOfChild(addReqExtraInfoLayout);
							}
							break;
						default:
							break;
					}
				}
			});
		}
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
		int totalChild = addReqMainContainer.getChildCount();
		
		for(int i = 0; i < totalChild; i++) {
			addReqMainContainer.getChildAt(i).setVisibility(View.GONE);
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
		int totalChild = addReqIconLayout2.getChildCount();
		for(int i = 0; i < totalChild; i++) {
			addReqIconLayout2.getChildAt(i).setBackgroundColor(0);
		}		
	}
	//END
		
	//get the list of state 
	public void getStateList(){
		stateResponse = CreateJsonArrayFileIntoCache.readStateListJsonData(getActivity().getApplicationContext());
		if(stateResponse != null){
			try {
				ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text,JSONUtils.getList(stateResponse,Constant.State.STATENAME));
				stateAdapter.setDropDownViewResource(R.layout.spinner_text);
				//stateAdapter.
				addReqStateSpinner.setAdapter(stateAdapter);
				//Set Default Selection 
				if(stateAdapter.getPosition(userSessionManager.getSessionValue(Constant.Login.STATE_NAME)) != -1)
					addReqStateSpinner.setSelection(stateAdapter.getPosition(userSessionManager.getSessionValue(Constant.Login.STATE_NAME)));
				
				addReqStateSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0,View arg1, int position, long arg3) {
						try {
							getDistrictList(stateResponse.getJSONObject(position).getString(Constant.State.STATEID), stateResponse.getJSONObject(position).getString(Constant.State.COUNTRY_ID));
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
		WebserviceClient  cityListWebserviceClient=new WebserviceClient(MyRequirementAddActivity.this, cityListService);
		cityListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				cityResponse = (JSONArray)response;
				try{
					if (cityResponse != null && !((JSONObject) cityResponse.get(0)).has(Constant.City.API_STATUS)) {
						addReqCitySpinner.setEnabled(true);
						cityAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,JSONUtils.getList(cityResponse, Constant.City.CITY_NAME));
						cityAdapter.setDropDownViewResource(R.layout.spinner_text);
						addReqCitySpinner.setAdapter(cityAdapter);
						//set default spinner item selected from login user City Name
						if(cityAdapter.getPosition(userSessionManager.getSessionValue(Constant.Login.CITY_NAME)) != -1)
							addReqCitySpinner.setSelection(cityAdapter.getPosition(userSessionManager.getSessionValue(Constant.Login.CITY_NAME)));
						/*addReqCitySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0,View arg1, int position, long arg3) {
								try{
									//call the getDistrictList Method to getDistrict List and set into spinner 
									//getDistrictList(cityResponse.getJSONObject(position).getString(Constant.State.STATEID),cityResponse.getJSONObject(position).getString(Constant.City.COUNTRY_ID));
								} catch(JSONException e) {
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
					
				} catch(JSONException e){
					e.printStackTrace();
				}
			}
		});
		cityListWebserviceClient.execute(districtId,stateId);		
	}
	
	//Web Service to get the District name List and add into the District Spinner
	public void getDistrictList(String stateId,String countryId){
		WebserviceClient  districtListWebserviceClient=new WebserviceClient(MyRequirementAddActivity.this, districtListService);
		districtListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				districtResponse = (JSONArray)response;
				try{
					if (districtResponse != null && !((JSONObject) districtResponse.get(0)).has(Constant.District.API_STATUS)) {
						addReqDistrictSpinner.setEnabled(true);
						districtAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,JSONUtils.getList(districtResponse, Constant.District.DISTRICT_NAME));
						districtAdapter.setDropDownViewResource(R.layout.spinner_text);
						addReqDistrictSpinner.setAdapter(districtAdapter);
						//set default spinner item selected from login user District Name
						if(districtAdapter.getPosition(userSessionManager.getSessionValue(Constant.Login.DISTRICT_NAME)) != -1)
							addReqDistrictSpinner.setSelection(districtAdapter.getPosition(userSessionManager.getSessionValue(Constant.Login.DISTRICT_NAME)));
						addReqDistrictSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									getCityList(districtResponse.getJSONObject(position).getString(Constant.District.STATEID),districtResponse.getJSONObject(position).getString(Constant.District.DISTRICT_ID));
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(getActivity(),((JSONObject) districtResponse.get(0)).getString(Constant.District.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(districtAdapter,"district");
						notifyAdapter(cityAdapter,"city");
					}
				} catch(JSONException e) {
					e.printStackTrace();
				}
			}
		});
		districtListWebserviceClient.execute(stateId,countryId);	
	}
	
	//get the list of area(Location) from the web service 
	public void getAreaList(){
		JSONArray jsonArray = CreateJsonArrayFileIntoCache.readAreaListJsonData(getActivity().getApplicationContext());
		try {
			if(jsonArray != null && !((JSONObject)jsonArray.get(0)).has(Constant.Area.API_STATUS)) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					areaMap.put(jsonObject.getString(Constant.Area.AREA_NAME).trim(),jsonObject.getInt(Constant.Area.AREA_ID));
				}
				openAreaListOfCityPopupWindow("areaMap", addReqLocationEditBox, areaMap);
			} else {
				Toast.makeText(getActivity(), "No area found!", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	//get the list of TP scheme 
	public void getTpSchem(){
		final JSONArray jsonArray = CreateJsonArrayFileIntoCache.readTPSchemeListJsonData(getActivity().getApplicationContext());
		try {
			if(jsonArray != null) {
				ArrayAdapter<String> tpSchemeAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,JSONUtils.getList(jsonArray, Constant.TPSchemesListing.TITLE));
				tpSchemeAdapter.setDropDownViewResource(R.layout.spinner_text);
				
				addReqTPSchemeSpinner.setAdapter(tpSchemeAdapter);
				addReqTPSchemeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
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
	
	//Hide Particular View Of Layout	
	private void hideLayoutChild(View hideLinerLayout){
	  int childView =  ((ViewGroup) hideLinerLayout).getChildCount();
	  for (int i = 0; i < childView; i++) {
		  ((ViewGroup) hideLinerLayout).getChildAt(i).setVisibility(View.GONE);
	  }
	}
	//END
	  
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    this.getActivity().getActionBar().setTitle("Add Requirement");
	}
	
	//Check property type 
	private boolean checkPropertyType() {
		if(addReqPropTypeSpinner.getSelectedItem().equals(" Select ") && addReqPropTypeSpinner.isShown()) {
			Toast.makeText(getActivity(), "Please select property type", 100).show();
			return true;
		}
		return false;
	}
		
	//Check property option
	private boolean checkPropertyOption() {
		if(addReqOptionSpinner.getSelectedItem().equals("Select") && addReqOptionSpinner.isShown()) {
			Toast.makeText(getActivity(), "Please select option", 100).show();
			return true;
		}
		return false;
	}
	
	//check the validation of location editbox
	private boolean areaEditBoxValidation(){
		if(addReqLocationEditBox.isShown()) {
			if(addReqLocationEditBox.getText().length() == 0 ) {
				addReqLocationEditBox.setError("Select Location");
				return true;
			}
		}
		return false;
	}
	
	//Chcek min bed validation
	private boolean checkMinBedValidation() {
		if(addReqFlatMinBeds.getSelectedItem().toString().equals("0")) {
			Toast.makeText(getActivity(), "Please select min bed", 100).show();
			return true;
		}
		return false;
	}
	
	//Chcek min bed validation
	private boolean checkMaxBedValidation() {
		if(addReqFlatMaxBeds.getSelectedItem().toString().equals("0")) {
			Toast.makeText(getActivity(), "Please select max bed", 100).show();
			return true;
		}
		return false;
	}
		
		
	//Check min bed is less then max bed
	private boolean checkMinLessThenMaxBed() {
		if(addReqFlatMinBeds.isShown() && addReqFlatMaxBeds.isShown()) {
			if(!checkMinBedValidation() && !checkMaxBedValidation()) {
				if(Integer.parseInt(addReqFlatMinBeds.getSelectedItem().toString()) >= Integer.parseInt(addReqFlatMaxBeds.getSelectedItem().toString())) {
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
		if(addReqFlatMaxAreaEditBox.getText().length() == 0 ) {
			addReqFlatMaxAreaEditBox.setError("Enter Maximum Area");
			return true;
		}
		return false;
	}
	
	// check the validation of minimum area 
	private boolean minAreaValidation() {
		if(addReqFlatMinAreaEditBox.getText().length() == 0 ){
			addReqFlatMinAreaEditBox.setError("Enter Minimum Area");
			return true;
		}
		return false;
	}
	
	//check min area is less then max area
	private boolean checkMinLessThenMaxArea() {
		if(addReqFlatMinAreaEditBox.isShown() && addReqFlatMaxAreaEditBox.isShown()) {
			if(!minAreaValidation() && !maxAreaValidation() ) { 
				if((Integer.parseInt(addReqFlatMaxAreaEditBox.getText().toString())) <=  (Integer.parseInt(addReqFlatMinAreaEditBox.getText().toString()))){
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
		if(addReqMinRentBudget.getSelectedItem().toString().equals("Select")) {
			Toast.makeText(getActivity(), "Please select min rent", 100).show();
			return true;
		}
		return false;
	}
	
	//check min rent
	private boolean chekcMaxRentValidation() {
		if(addReqMaxRentBudget.getSelectedItem().toString().equals("Select")) {
			Toast.makeText(getActivity(), "Please select max rent", 100).show();
			return true;
		}
		return false;
	}
	
	//Check min/max rent
	private boolean checkMinMaxRent() {
		if(addReqOptionSpinner.getSelectedItem().toString().equals("Rent")) {
			if(addReqMinRentBudget.isShown() && addReqMaxRentBudget.isShown()) {
				if(!chekcMinRentValidation() && !chekcMaxRentValidation()) {
					if(!addReqMinRentBudget.getSelectedItem().toString().equals("5,00,000 and Above") && !addReqMaxRentBudget.getSelectedItem().toString().equals("5,00,000 and Above")) {
						String minRentString = String.valueOf(SpinnerItem.getRentList().get(addReqMinRentBudget.getSelectedItem().toString()));
						String maxRentString = String.valueOf(SpinnerItem.getRentList().get(addReqMaxRentBudget.getSelectedItem().toString()));
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
		if(addReqMinPriceBudget.getSelectedItem().toString().equals(" Select ")) {
			Toast.makeText(getActivity(), "Please select min price", 100).show();
			return true;
		}
		return false;
	}
	
	//check max price validation
	private boolean checkMaxPriceValidation() {
		if(addReqMaxPriceBudget.getSelectedItem().toString().equals(" Select ")) {
			Toast.makeText(getActivity(), "Please select min price", Toast.LENGTH_LONG).show();
			return true;
		}
		return false;
	}
	
	//Check min/max price
	private boolean checkMinMaxPrice() {
		if(addReqOptionSpinner.getSelectedItem().toString().equals("Sale")) {
			if(addReqMinPriceBudget.isShown() && addReqMaxPriceBudget.isShown()) {
				if(!checkMinPriceValidation() && !checkMaxPriceValidation()) {
					if(!addReqMinPriceBudget.getSelectedItem().toString().equals("5 Crore and Above") && !addReqMaxPriceBudget.getSelectedItem().toString().equals("5 Crore and Above")) {
						String minpriceString = String.valueOf(SpinnerItem.getTotalPriceList().get(addReqMinPriceBudget.getSelectedItem().toString()));
						String maxPriceString = String.valueOf(SpinnerItem.getTotalPriceList().get(addReqMaxPriceBudget.getSelectedItem().toString()));
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
	
	//Check location
	private boolean checkCity() {
		if(addReqCitySpinner.getSelectedItem() != null) {
			if(addReqCitySpinner.getSelectedItem().equals("No city found") && addReqCitySpinner.isShown()) {
				Toast.makeText(getActivity(), "No city found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	
	//Check location
	private boolean checkDistrict() {
		if(addReqDistrictSpinner.getSelectedItem() != null) {
			if(addReqDistrictSpinner.getSelectedItem().equals("No district found") && addReqDistrictSpinner.isShown()) {
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
				addReqCitySpinner.setAdapter(adapter);
				addReqCitySpinner.setEnabled(false);
			} else if(spinnerString.equals("district")) {
				adapter.insert("No district found", 0);
				addReqDistrictSpinner.setAdapter(adapter);
				addReqDistrictSpinner.setEnabled(false);
			}
			adapter.notifyDataSetChanged();
		} 
	}
}