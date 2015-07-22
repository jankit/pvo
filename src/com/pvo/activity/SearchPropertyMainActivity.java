package com.pvo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
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

//This is used for search the Property by Specific criteria
public class SearchPropertyMainActivity extends PVOFragment {
	
	private Spinner searchPropPropTypeSpinner;
	private ArrayList<String> propertyTypeOptionList;
	private ArrayAdapter< String> prpoertyTypeOptionAdapter;
	private Spinner searchPropForSpinner;
	private EditText arearEditBox;
	private EditText searchPropKeywordEditBox;
	private TextView searchPropSelectAreaTV;
	
	//Min/Max Rent Layout 
	private RelativeLayout searchPropRentLayout;
	private Spinner searchPropRentMinSpinner;
	private Spinner searchPropRentMaxSpinner;
	private TextView searchPropRentTv;
	
	//Min/Max Total Price Layout
	private RelativeLayout searchPropTotalPriceLayout;
	private Spinner searchPropTotalPriceMinSpinner;
	private Spinner searchPropTotalPriceMaxSpinner;
	private TextView maxPriceErrorMsg;
	private TextView minPriceErrorMsg;
	private TextView searchPropTotalPriceTv;
	
	//Purpose Layout 
	private RelativeLayout searchPropPurposeLayout;
	private Spinner searchPropPurposeSpinner;
	
	//Area Layout
	private RelativeLayout searchPropAreaLayout;
	private EditText searchPropAreaMinEditBox;
	private EditText searchPropAreaMaxEditBox;
	
	//Plot Area Layout
	private RelativeLayout searchPropPlotAreaLayout;
	private EditText searchPropPlotAreaMinEditBox;
	private EditText searchPropPlotAreaMaxEditBox;
	
	//Construction Area
	private RelativeLayout searchPropComBunglowConstLayout;
	private EditText searchPropConstructionAreaMinEditBox;
	private EditText searchPropConstructionAreaMaxEditBox;
	
	//Min/Max Bed Layout
	private RelativeLayout searchPropBedLayout;
	private Spinner searchPropBedMinSpinner;
	private Spinner searchPropBedMaxSpinner;
	
	//Min/Max Floor
	private RelativeLayout searchPropFloorLayout;
	private Spinner searchPropFloorMinSpinner;
	private Spinner searchPropFloorMaxSpinner;
	
	//Building Type Preference 
	private RelativeLayout searchPropBuildingTypeLayout;
	private Spinner searchPropBuildingTypeSpinner;
	
	//Furnish Option Layout
	private RelativeLayout searchPropFurnishOptionLayout;
	private Spinner searchPropFurnishOptionSpinner;
	
	//T.P.Scheme Layout
	private RelativeLayout searchPropTPSchemeLayout;
	private Spinner searchPropTPSchemeSpinner;
	private String tpScheme1Id;
	
	//Zone Layout
	private LinearLayout searchPropZoneLayout;
	private CheckBox zoneCheckBox;
	private ArrayList<String> zoneArray;
	
	//N.A Layout
	private RelativeLayout searchPropNALayout;
	private Spinner searchPropNASpinner;
	
	//Terms Layout
	private RelativeLayout searchPropTermsLayout;
	private CheckBox searchPropTermsNaviSharatCheck;
	private CheckBox searchPropTermsJuniSharatCheck;
	private CheckBox searchPropTermsKhetiCheck;
	private CheckBox searchPropTermsPRASHACheck;
	private CheckBox searchPropTermsDisputeCheck;
	private CheckBox searchPropTermsShreeSarkarCheck;
	
	//Button for search Property ,Back Button,Calcel Button
	private Button searchPropMoreOptionButton;
	private Button searchPropBackButton;
	private Button SearchPropShowResultButton;
	private RelativeLayout searchPropFlatLayout;
	
	//Area List Of Particular List
	private Map<String,Integer> areaMap = new HashMap<String, Integer>();
	
	//Error message Text Rent
	private TextView minRentErrorMsg;
	private TextView maxRentErrorMsg;
	
	//set the layout content view
	public SearchPropertyMainActivity() {
		setContentView(R.layout.activity_searchproperty_main);
	}
	
	@Override
	public void init(Bundle savedInstanceState) {

		searchPropFlatLayout = (RelativeLayout)findViewById(R.id.searchPropFlatLayout);
		searchPropKeywordEditBox = (EditText) findViewById(R.id.searchPropKeywordEditBox);
		
		
		//Search Property For Spinner Sale/rent	
		searchPropForSpinner=(Spinner)findViewById(R.id.searchPropForSpinner);
		ArrayList<String> propertyForSaleRentOptionList=new ArrayList<String>();
		propertyForSaleRentOptionList.addAll(SpinnerItem.getSaleRentList().keySet());
		ArrayAdapter< String> prpoertySaleRentOptionAdapter= new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,propertyForSaleRentOptionList);
		prpoertySaleRentOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
		prpoertySaleRentOptionAdapter.remove("Select");
		searchPropForSpinner.setAdapter(prpoertySaleRentOptionAdapter);
		
		//set visible layout according to sale or rent 
		searchPropForSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View v,int arg2, long arg3) {
				if(searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropRentLayout.setVisibility(View.VISIBLE);
					searchPropTotalPriceLayout.setVisibility(View.GONE);
				}else{
					searchPropTotalPriceLayout.setVisibility(View.VISIBLE);
					searchPropRentLayout.setVisibility(View.GONE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		//Area Layout
		searchPropAreaLayout = (RelativeLayout)findViewById(R.id.searchPropAreaLayout);
		searchPropAreaMinEditBox = (EditText) findViewById(R.id.searchPropAreaMinEditBox);
		searchPropAreaMaxEditBox = (EditText) findViewById(R.id.searchPropAreaMaxEditBox);
		searchPropSelectAreaTV = (TextView) findViewById(R.id.searchPropTextView4);
		searchPropSelectAreaTV.setText(Html.fromHtml("Select Area " + "<sup><font size=5 color=red>*</font></sup>"));
		
		//Plot Area Layout
		searchPropPlotAreaLayout = (RelativeLayout) findViewById(R.id.searchPropPlotAreaLayout);
		searchPropPlotAreaMinEditBox = (EditText) findViewById(R.id.searchPropPlotAreaMinEditBox);
		searchPropPlotAreaMaxEditBox = (EditText) findViewById(R.id.searchPropPlotAreaMaxEditBox);
		
		//Purpose
		searchPropPurposeLayout = (RelativeLayout) findViewById(R.id.searchPropPurposeLayout);
		
		//fill the spinner using hash map commercial / residential
		searchPropPurposeSpinner = (Spinner) findViewById(R.id.searchPropPurposeSpinner);
		ArrayList<String> searchPropPurposeList=new ArrayList<String>();
		searchPropPurposeList.addAll(SpinnerItem.getSerchPropPurposeTypeList().keySet());
		ArrayAdapter< String> searchPorpPurposeAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,searchPropPurposeList);
		searchPorpPurposeAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchPropPurposeSpinner.setAdapter(searchPorpPurposeAdapter);
		
		//Construction area
		searchPropComBunglowConstLayout = (RelativeLayout) findViewById(R.id.searchPropComBunglowConstLayout);
		searchPropConstructionAreaMinEditBox = (EditText) findViewById(R.id.searchPropConstructionAreaMinEditBox);
		searchPropConstructionAreaMaxEditBox = (EditText) findViewById(R.id.searchPropConstructionAreaMaxEditBox);
		
		//T.P.Scheme Layout
		searchPropTPSchemeLayout = (RelativeLayout)findViewById(R.id.searchPropTPSchemeLayout);
		searchPropTPSchemeSpinner = (Spinner) findViewById(R.id.searchPropTPSchemeSpinner);
		
		//Zone Layout
		searchPropZoneLayout = (LinearLayout) findViewById(R.id.searchPropZoneLayout);
		zoneArray = new ArrayList<String>();
		for(int i=0;i<CheckBoxItem.zoneFarmHouseCheckItem.length;i++){
			zoneCheckBox = new CheckBox(getActivity());
			zoneCheckBox.setId(i);
			zoneCheckBox.setTextColor(Color.BLACK);
			zoneCheckBox.setText(CheckBoxItem.zoneFarmHouseCheckItem[i]);
			zoneCheckBox.setButtonDrawable(R.xml.custom_checkbox);
			searchPropZoneLayout.addView(zoneCheckBox);
			zoneCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked){
						zoneArray.add(buttonView.getText().toString());
					} else {
						zoneArray.remove(buttonView.getText().toString());
					}
				}
			});
		}
		//Fill the Min/Max Bed Spinner 
		searchPropBedLayout = (RelativeLayout)findViewById(R.id.searchPropBedLayout);
		searchPropBedMinSpinner = (Spinner) findViewById(R.id.searchPropBedMinSpinner);
		searchPropBedMaxSpinner = (Spinner) findViewById(R.id.searchPropBedMaxSpinner);
		ArrayList<String> bedList=new ArrayList<String>();
		bedList.addAll(SpinnerItem.getBedList().keySet());
		ArrayAdapter< String> bedAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,bedList);
		bedAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchPropBedMinSpinner.setAdapter(bedAdapter);
		searchPropBedMaxSpinner.setAdapter(bedAdapter);
		
		//N.A Layout fill the spinner using the Hash map
		searchPropNALayout = (RelativeLayout)findViewById(R.id.searchPropNALayout);
		searchPropNASpinner = (Spinner) findViewById(R.id.searchPropNASpinner);
		ArrayList<String> naStatusList=new ArrayList<String>();
		naStatusList.addAll(SpinnerItem.getNaStatusList().keySet());
		ArrayAdapter< String> naStatusAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,naStatusList);
		naStatusAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchPropNASpinner.setAdapter(naStatusAdapter);
		
		//Terms Layout
		searchPropTermsLayout = (RelativeLayout) findViewById(R.id.searchPropTermsLayout);
		searchPropTermsNaviSharatCheck = (CheckBox) findViewById(R.id.searchPropTermsNaviSharatCheck);
		searchPropTermsJuniSharatCheck = (CheckBox) findViewById(R.id.searchPropTermsJuniSharatCheck);
		searchPropTermsKhetiCheck = (CheckBox) findViewById(R.id.searchPropTermsKhetiCheck);
		searchPropTermsPRASHACheck = (CheckBox) findViewById(R.id.searchPropTermsPRASHACheck);
		searchPropTermsDisputeCheck = (CheckBox) findViewById(R.id.searchPropTermsDisputeCheck);
		searchPropTermsShreeSarkarCheck = (CheckBox) findViewById(R.id.searchPropTermsShreeSarkarCheck);
		
		//Fill the Min/max Floor spinner Using hashmap 
		searchPropFloorLayout = (RelativeLayout)findViewById(R.id.searchPropFloorLayout);
		searchPropFloorMinSpinner = (Spinner) findViewById(R.id.searchPropFloorMinSpinner);
		searchPropFloorMaxSpinner = (Spinner) findViewById(R.id.searchPropFloorMaxSpinner);
		ArrayList<String> minFloorList=new ArrayList<String>();
		minFloorList.addAll(SpinnerItem.getFloorList().keySet());
		ArrayAdapter< String> minFloorAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,minFloorList);
		minFloorAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchPropFloorMinSpinner.setAdapter(minFloorAdapter);
		searchPropFloorMaxSpinner.setAdapter(minFloorAdapter);
		
		//Min/Max Price Layout  Fill The Spinner Using Hash Map
		searchPropTotalPriceLayout = (RelativeLayout) findViewById(R.id.searchPropTotalPriceLayout);
		searchPropTotalPriceMinSpinner = (Spinner) findViewById(R.id.searchPropTotalPriceMinSpinner); 
		searchPropTotalPriceMaxSpinner = (Spinner) findViewById(R.id.searchPropTotalPriceMaxSpinner);
		maxPriceErrorMsg = (TextView) findViewById(R.id.searchPropMaxPriceErrorMsg);
		minPriceErrorMsg = (TextView) findViewById(R.id.searchPropMinPriceErrorMsg);
		searchPropTotalPriceTv = (TextView) findViewById(R.id.searchPropTotalPriceTv);
		searchPropTotalPriceTv.setText(Html.fromHtml("Total price"+"<sup><font size=5 color=red>*</font></sup>"));
		
		ArrayList<String> minTotalPriceList=new ArrayList<String>();
		minTotalPriceList.addAll(SpinnerItem.getTotalPriceList().keySet());
		ArrayAdapter< String> minTotalPriceAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,minTotalPriceList);
		minTotalPriceAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchPropTotalPriceMinSpinner.setAdapter(minTotalPriceAdapter);
		searchPropTotalPriceMaxSpinner.setAdapter(minTotalPriceAdapter);
		//hide the error message
		searchPropTotalPriceMinSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				minPriceErrorMsg.setVisibility(View.GONE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
		});
		
		//hide error message
		searchPropTotalPriceMaxSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				maxPriceErrorMsg.setVisibility(View.GONE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {}
		});
		
		//Min/Max Rent Layout Fill The Spinner Using Hash Map
		searchPropRentLayout = (RelativeLayout) findViewById(R.id.searchPropRentLayout);
		searchPropRentMinSpinner = (Spinner) findViewById(R.id.searchPropRentMinSpinner);
		searchPropRentMaxSpinner = (Spinner) findViewById(R.id.searchPropRentMaxSpinner);
		searchPropRentTv = (TextView) findViewById(R.id.searchPropRentTv);
		searchPropRentTv.setText(Html.fromHtml("Rent"+"<sup><font size=5 color=red>*</font></sup>"));
		ArrayList<String> minRentList=new ArrayList<String>();
		minRentList.addAll(SpinnerItem.getRentList().keySet());
		
		ArrayAdapter< String> minRentAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,minRentList);
		minRentAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchPropRentMinSpinner.setAdapter(minRentAdapter);
		searchPropRentMaxSpinner.setAdapter(minRentAdapter);
		
		//error msg textview for Rent
		minRentErrorMsg = (TextView) findViewById(R.id.minRentErrorMsg);
		maxRentErrorMsg = (TextView) findViewById(R.id.maxRentErrorMsg);
		searchPropRentMinSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				minRentErrorMsg.setVisibility(View.GONE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});	
	
		searchPropRentMaxSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				maxRentErrorMsg.setVisibility(View.GONE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		//Fill The Building Type spinner Using HashMap
		searchPropBuildingTypeLayout = (RelativeLayout)findViewById(R.id.searchPropBuildingTypeLayout);
		searchPropBuildingTypeSpinner = (Spinner) findViewById(R.id.searchPropBuildingTypeSpinner);
		ArrayList<String> buildingTypeList=new ArrayList<String>();
		buildingTypeList.addAll(SpinnerItem.getSerchPropBuildingTypeList().keySet());
		ArrayAdapter< String> buildingTypeAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,buildingTypeList);
		buildingTypeAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchPropBuildingTypeSpinner.setAdapter(buildingTypeAdapter);
			
		//Fill the Furnish Option spinner Using hashmap
		searchPropFurnishOptionLayout = (RelativeLayout) findViewById(R.id.searchPropFurnishOptionLayout);
		searchPropFurnishOptionSpinner=(Spinner)findViewById(R.id.searchPropFurnishOptionSpinner);
		ArrayList<String> furnishOptionList=new ArrayList<String>();
		furnishOptionList.addAll(SpinnerItem.getSerchPropFurnishStatusTypeList().keySet());
		ArrayAdapter< String> furnishOptionAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,furnishOptionList);
		furnishOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchPropFurnishOptionSpinner.setAdapter(furnishOptionAdapter);
		
		//Select multiple area on click of Edit box open Popup window for select multiple Area 
		arearEditBox=(EditText)findViewById(R.id.searchPropAreaEditBox);
		arearEditBox.setFocusable(false);
		arearEditBox.setInputType(InputType.TYPE_NULL);
		arearEditBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//counter = 0;
				//System.out.println("counter = selectedItemList.size()"+selectedItemList.size());
				arearEditBox.setError(null);
				getAreaList();
			}
		});

		//Fill the Search Property Type Spinner Using hash Map
		searchPropPropTypeSpinner = (Spinner)findViewById(R.id.searchPropSpinner);
		propertyTypeOptionList = new ArrayList<String>();
		propertyTypeOptionList.addAll(SpinnerItem.getPropertyTypeOptionList().keySet());
		
		//Spinner adapter set property type group like flat,shop,bunglow,plot
		prpoertyTypeOptionAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,propertyTypeOptionList){
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

            //set the group label text and color
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
		searchPropPropTypeSpinner.setAdapter(prpoertyTypeOptionAdapter);
		searchPropPropTypeSpinner.setSelection(1);
		//Reset all data when property type is change
		searchPropPropTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if(position != 0)
					resetData();
				
					showHidelayout();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		//Proceed Button Proceed to search Specific criteria Property
		searchPropMoreOptionButton = (Button)findViewById(R.id.searchPropProceedBotton);
		searchPropMoreOptionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showHidelayout();
				/*searchPropAreaLayout.setVisibility(View.GONE);//area
				searchPropBedLayout.setVisibility(View.GONE); //Min/Max Bed
				searchPropFloorLayout.setVisibility(View.GONE);//Min/Max Floor
				searchPropBuildingTypeLayout.setVisibility(View.GONE);//Bulding Type
				searchPropPurposeLayout.setVisibility(View.GONE);//Purpose 
				searchPropFurnishOptionLayout.setVisibility(View.GONE);//furnish 
				searchPropComBunglowConstLayout.setVisibility(View.GONE);//Construction Area
				searchPropTPSchemeLayout.setVisibility(View.GONE);//TP SCheme
				searchPropZoneLayout.setVisibility(View.GONE);//Zone
				searchPropNALayout.setVisibility(View.GONE);//N.A.
				searchPropTermsLayout.setVisibility(View.GONE);//Trems
				searchPropPlotAreaLayout.setVisibility(View.GONE);//Plot area*/
			
				//Web Service for TPScheme This web service is used to set the TPScheme Spinner
				//For Property type Bunglow and sub Property type Farm House(Bunglow) for Sale/Rent and PLOT
				/*if((SpinnerItem.getPropertyTypeOptionList().get(searchPropPropTypeSpinner.getSelectedItem().toString()).equals("Bunglow") && searchPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)"))
						|| SpinnerItem.getPropertyTypeOptionList().get(searchPropPropTypeSpinner.getSelectedItem().toString()).equals("Plot") ||searchPropPropTypeSpinner.getSelectedItem().toString().equals("All Plots")){
						getTpSchem();
				}*/
			
				//reset the Property type adapter
				/*ArrayList<String> propertyTypeOptionList = new ArrayList<String>();
				propertyTypeOptionList.add((String) searchPropPropTypeSpinner.getSelectedItem());
				ArrayAdapter< String> prpoertyTypeOptionAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,propertyTypeOptionList);
				prpoertyTypeOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
				searchPropPropTypeSpinner.setAdapter(prpoertyTypeOptionAdapter);*/
				//END
				
				//Reset the Sale/Rent Spinner
				/*ArrayList<String> propertyForOptionList = new ArrayList<String>();
				propertyForOptionList.add((String) searchPropForSpinner.getSelectedItem());
				ArrayAdapter< String> prpoertyForOptionAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,propertyForOptionList);
				prpoertyForOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
				searchPropForSpinner.setAdapter(prpoertyForOptionAdapter);*/
				//END
				
				searchPropMoreOptionButton.setVisibility(View.GONE);
				searchPropFlatLayout.setVisibility(View.VISIBLE);
			
				
				/*//Flat For Sale Set Layout To Visible
				if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Flat") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropAreaLayout.setVisibility(View.VISIBLE);//area
					searchPropBedLayout.setVisibility(View.VISIBLE); //Min/Max Bed
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose 
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish 
				} //Flat For Rent Set Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Flat") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropAreaLayout.setVisibility(View.VISIBLE);
					searchPropRentLayout.setVisibility(View.VISIBLE);//Rent
					searchPropBedLayout.setVisibility(View.VISIBLE);
					searchPropFloorLayout.setVisibility(View.VISIBLE);
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);
					searchPropPurposeLayout.setVisibility(View.VISIBLE);
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);
				} //Shop For Sale Set Layout To Visible 
				else if((searchPropPropTypeSpinner.getSelectedItem().toString().equals("Shop") || (searchPropPropTypeSpinner.getSelectedItem().toString().equals("All Shop"))) && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropAreaLayout.setVisibility(View.VISIBLE);//area
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Shop For Rent Set Layout To Visible
				else if((searchPropPropTypeSpinner.getSelectedItem().toString().equals("Shop") || (searchPropPropTypeSpinner.getSelectedItem().toString().equals("All Shop"))) && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropAreaLayout.setVisibility(View.VISIBLE);//area
					searchPropRentLayout.setVisibility(View.VISIBLE);//Rent
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Show Room For Sale Set Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Show Rooms") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropAreaLayout.setVisibility(View.VISIBLE);//area
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Show Room For Rent Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Show Rooms") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropAreaLayout.setVisibility(View.VISIBLE);//area
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Commercial bunglow For Sale Set Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Commercial Bunglow") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Commercial bunglow For Rent Set Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Commercial Bunglow") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Commercial Flat For Sale Set Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Commercial Flat") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropAreaLayout.setVisibility(View.VISIBLE);// area
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Commercial Flat For Rent Set Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Commercial Flat") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropAreaLayout.setVisibility(View.VISIBLE);// area
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Office For Sale Set Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Office") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropAreaLayout.setVisibility(View.VISIBLE);// area
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Office For Rent Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Office") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropAreaLayout.setVisibility(View.VISIBLE);// area
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Corporate House For Sale Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Corporate House") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Corporate House For Rent Set Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Corporate House") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Godown For Sale Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Godown") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Godown For Rent Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Godown") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Shades For Sale Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Shades") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Shades For Rent Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Shades") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Factory For Sale Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Factory") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Factory For Rent Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Factory") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
					searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Bunglow For Sale Set Layout To Visible 
				else if((searchPropPropTypeSpinner.getSelectedItem().toString().equals("Bunglow") || searchPropPropTypeSpinner.getSelectedItem().toString().equals("All Bunglow")) && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Bunglow For Rent Set Layout To Visible 
				else if((searchPropPropTypeSpinner.getSelectedItem().toString().equals("Bunglow") || searchPropPropTypeSpinner.getSelectedItem().toString().equals("All Bunglow")) && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Twin For Sale Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Twin") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Twin For Rent Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Twin") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Row House For Sale Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Row House") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} // Row House For Rent Set Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Row House") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Tenament For Sale Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Tenament") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Tenament For Rent Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Tenament") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Individual For Sale Set Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Individual") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Individual For Rent Set Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Individual") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Independent Bunglow For Sale Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Independent Bunglows") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Independent Bunglow For Rent Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Independent Bunglows") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
					searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
				} //Farm House For Sale Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Farm House For Rent Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Plot For Sale Set Layout To Visible 
				else if((searchPropPropTypeSpinner.getSelectedItem().toString().equals("Plot") || searchPropPropTypeSpinner.getSelectedItem().toString().equals("All Plots")) && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Plot For Rent Set Layout To Visible 
				else if((searchPropPropTypeSpinner.getSelectedItem().toString().equals("Plot") || searchPropPropTypeSpinner.getSelectedItem().toString().equals("All Plots")) && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Society Plot For Sale Set Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Society Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Society Plot For Rent Set Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Society Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Society Sub For Sale Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Society Sub Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Society Sub For Rent Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Society Sub Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //F.P Plot For Sale Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("F.P.Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //F.P Plot For Rent Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("F.P.Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Land of T.P For Sale Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Land Of T.P") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Land of T.P For Rent Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Land Of T.P") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Land of Agriculture For Sale Set Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Land of Agriculture") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Land of Agriculture For Rent Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Land of Agriculture") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Land of Industrial For Sale Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Land of Industrial") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Land of Industrial For Rent Set Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Land of Industrial") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Development Society For Sale Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Development Society") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Development Society For Rent Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Development Society") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Farm House For Sale Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Plot)") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construal area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Farm House For Rent Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Plot)") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construal area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Individual Plot For Sale Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Individual Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Individual Plot For Rent Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Individual Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Gam Tal Land For Sale Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Gam Tal Land") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //Gam Tal Land For Rent Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Gam Tal Land") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //GIDC Plot for Sale Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("GIDC Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //GIDC Plot for Rent Layout To Visible
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("GIDC Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} //SEZ for Sale Layout To Visible 
				else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("SEZ Land") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				} else {//SEZ for Rent Layout To Visible
					searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
					searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
					searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
					searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
					searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
					searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
				}*/
			}
		});
		
		//Search Result Button click Event 
		SearchPropShowResultButton = (Button) findViewById(R.id.SearchPropShowResultButton);
		SearchPropShowResultButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!checkLocation() && !checkMinMaxPriceRent() && !checkMinLessThenMaxArea()){
					//Comma Seprated String of Selected area Code
					String selectedAreaString = "";
					String area = arearEditBox.getText().toString();
					String[] selectedArea=area.split(" - ");
					for (int i = 0; i < selectedArea.length; i++) { 
						selectedAreaString += areaMap.get(selectedArea[i]);
						if(i != selectedArea.length-1){
							selectedAreaString += ",";
						}
					}
				
					Bundle searchPropertyBundle = new Bundle();
					searchPropertyBundle.putString("Type","Search");
					searchPropertyBundle.putString(Constant.SearchProperty.PROPERTY_TYPE,String.valueOf(SpinnerItem.getPropertyTypeOptionList().get(searchPropPropTypeSpinner.getSelectedItem().toString())) );//"propertytype"
					searchPropertyBundle.putString(Constant.SearchProperty.CMB_OPTION, searchPropForSpinner.getSelectedItem().toString());//"cmboptions"
					searchPropertyBundle.putString(Constant.SearchProperty.CMB_PROPERTY_TYPE,searchPropPropTypeSpinner.getSelectedItem().toString() );//"cmbpropertytype"
					searchPropertyBundle.putString(Constant.SearchProperty.CMB_AREA1,selectedAreaString );//"cmbarea1"
					searchPropertyBundle.putString(Constant.SearchProperty.PROPERTY_SUB_TYPE, searchPropPropTypeSpinner.getSelectedItem().toString());//"propertysubtype"
					searchPropertyBundle.putString(Constant.SearchProperty.KEYWORD,searchPropKeywordEditBox.getText().toString() );//"keyword"
					searchPropertyBundle.putString(Constant.SearchProperty.MIN_AREA,searchPropAreaMinEditBox.getText().toString() );//"minarea"
					searchPropertyBundle.putString(Constant.SearchProperty.MAX_AREA, searchPropAreaMaxEditBox.getText().toString());//"maxarea"
					searchPropertyBundle.putString(Constant.SearchProperty.TXT_MIN_AREA,searchPropAreaMinEditBox.getText().toString() );//"minarea"
					searchPropertyBundle.putString(Constant.SearchProperty.TXT_MAX_AREA, searchPropAreaMaxEditBox.getText().toString());//"maxarea"
					searchPropertyBundle.putString(Constant.SearchProperty.TXT_MIN_PRICE,StringUtils.defaultIfBlank(String.valueOf(SpinnerItem.getTotalPriceList().get(searchPropTotalPriceMinSpinner.getSelectedItem().toString())),"") );//"txtminprice"
					searchPropertyBundle.putString(Constant.SearchProperty.TXT_MAX_PRICE,StringUtils.defaultIfBlank(String.valueOf(SpinnerItem.getTotalPriceList().get(searchPropTotalPriceMaxSpinner.getSelectedItem().toString())),"") );//"txtmaxprice"
					searchPropertyBundle.putString(Constant.SearchProperty.TXT_MIN_RENT,StringUtils.defaultIfBlank(String.valueOf(SpinnerItem.getRentList().get(searchPropRentMinSpinner.getSelectedItem().toString())),""));//"txtminrent"
					searchPropertyBundle.putString(Constant.SearchProperty.TXT_MAX_RENT,StringUtils.defaultIfBlank(String.valueOf(SpinnerItem.getRentList().get(searchPropRentMaxSpinner.getSelectedItem().toString())),""));//"txtmaxrent"
					searchPropertyBundle.putString(Constant.SearchProperty.CMB_MIN_BED,String.valueOf(SpinnerItem.getBedList().get(searchPropBedMinSpinner.getSelectedItem().toString()))  );//"cmbminbed"
					searchPropertyBundle.putString(Constant.SearchProperty.CMB_MAX_BED,String.valueOf(SpinnerItem.getBedList().get(searchPropBedMaxSpinner.getSelectedItem().toString())) );//"cmbmaxbed"
					searchPropertyBundle.putString(Constant.SearchProperty.CMB_MIN_FLOOR,StringUtils.defaultIfBlank(String.valueOf(SpinnerItem.getFloorList().get(searchPropFloorMinSpinner.getSelectedItem().toString())),""));//"cmbminfloor"
					searchPropertyBundle.putString(Constant.SearchProperty.CMB_MAX_FLOOR,StringUtils.defaultIfBlank(String.valueOf(SpinnerItem.getFloorList().get(searchPropFloorMaxSpinner.getSelectedItem().toString())),""));//"cmbmaxfloor"
					searchPropertyBundle.putString(Constant.SearchProperty.CMB_PURPOSE,StringUtils.defaultIfBlank(String.valueOf(SpinnerItem.getSerchPropPurposeTypeList().get(searchPropPurposeSpinner.getSelectedItem().toString())),""));//"cmbpurpose"
					searchPropertyBundle.putString(Constant.SearchProperty.RD_RISE,String.valueOf(SpinnerItem.getSerchPropBuildingTypeList().get(searchPropBuildingTypeSpinner.getSelectedItem().toString())) );//"rdrise"
					searchPropertyBundle.putString(Constant.SearchProperty.MIN_PLOT_AREA,searchPropPlotAreaMinEditBox.getText().toString() );//"minplotarea"
					searchPropertyBundle.putString(Constant.SearchProperty.MAX_PLOT_AREA,searchPropPlotAreaMaxEditBox.getText().toString() );//"maxplotarea"
					searchPropertyBundle.putString(Constant.SearchProperty.MIN_CONSTRUCTION_AREA,searchPropConstructionAreaMinEditBox.getText().toString());//"minconstructionarea"
					searchPropertyBundle.putString(Constant.SearchProperty.MAX_CONSTRUCTION_AREA,searchPropConstructionAreaMaxEditBox.getText().toString() );//"maxconstructionarea"
					searchPropertyBundle.putString(Constant.SearchProperty.TXT_MIN_PLOT_AREA,searchPropPlotAreaMinEditBox.getText().toString() );//"minplotarea"
					searchPropertyBundle.putString(Constant.SearchProperty.TXT_MAX_PLOT_AREA,searchPropPlotAreaMaxEditBox.getText().toString() );//"maxplotarea"
					searchPropertyBundle.putString(Constant.SearchProperty.TXT_MIN_CONSTRUCTION_AREA,searchPropConstructionAreaMinEditBox.getText().toString());//"minconstructionarea"
					searchPropertyBundle.putString(Constant.SearchProperty.TXT_MAX_CONSTRUCTION_AREA,searchPropConstructionAreaMaxEditBox.getText().toString() );//"maxconstructionarea"
					searchPropertyBundle.putString(Constant.SearchProperty.FURNISH_STATUS,String.valueOf(SpinnerItem.getSerchPropFurnishStatusTypeList().get(searchPropFurnishOptionSpinner.getSelectedItem().toString())));//"furnishoption"
					searchPropertyBundle.putString(Constant.SearchProperty.CHK_ZONE,StringUtils.join(zoneArray,","));//"chkzone"
					searchPropertyBundle.putString(Constant.SearchProperty.CMB_TP_SCHEME,StringUtils.defaultString(tpScheme1Id,""));//"tpScheme1"
					searchPropertyBundle.putString(Constant.SearchProperty.NAVI_SHARAT,String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(searchPropTermsNaviSharatCheck.isChecked()))));//"naviSharat"
					searchPropertyBundle.putString(Constant.SearchProperty.JUNI_SHARAT,String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(searchPropTermsJuniSharatCheck.isChecked()))));//"junisharat"
					searchPropertyBundle.putString(Constant.SearchProperty.KHETI,String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(searchPropTermsKhetiCheck.isChecked()))));//"kheti"
					searchPropertyBundle.putString(Constant.SearchProperty.PRASSAP,String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(searchPropTermsPRASHACheck.isChecked()))));//"prasha"
					searchPropertyBundle.putString(Constant.SearchProperty.DISPUTE,String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(searchPropTermsDisputeCheck.isChecked()))));//"dispute"
					searchPropertyBundle.putString(Constant.SearchProperty.SHREE_SARKAR,String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(searchPropTermsShreeSarkarCheck.isChecked()))));//"shreeSarkar"
					searchPropertyBundle.putString(Constant.SearchProperty.NA_STATUS,String.valueOf(SpinnerItem.getNaStatusList().get(searchPropNASpinner.getSelectedItem().toString())));//"naStatus"
					SearchPropertyListActivity searchPropertyListActivity = new SearchPropertyListActivity();
					searchPropertyListActivity.setArguments(searchPropertyBundle);
					((MainFragmentActivity)getActivity()).redirectScreen(searchPropertyListActivity);
				}
			}
		});
		
		//Back Button Click Event
		searchPropBackButton = (Button) findViewById(R.id.searchPropBackButton);
		searchPropBackButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});
		//END 
	}

	@Override
	public void processResponse(Object response) {}

	@Override
	public void onResume() {
		super.onResume();
		this.getActivity().getActionBar().setTitle("Search Property");
		
	}
	
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
	
	// get the list of TP scheme
	// Web Service for TPScheme This web service is used to set the TPScheme Spinner 
	public void getTpSchem(){
		final JSONArray jsonArray = CreateJsonArrayFileIntoCache.readTPSchemeListJsonData(getActivity().getApplicationContext());
		try {
			if(jsonArray != null) {
				ArrayAdapter<String> tpSchemeAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,JSONUtils.getList(jsonArray, Constant.TPSchemesListing.TITLE));
				tpSchemeAdapter.insert("Any", 0);
				tpSchemeAdapter.setDropDownViewResource(R.layout.spinner_text);
				searchPropTPSchemeSpinner.setAdapter(tpSchemeAdapter);
				searchPropTPSchemeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
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
			}
		} catch(JSONException e) {
			e.printStackTrace();
		}
	}
	//END
	
	// check the location is null or not
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
	
	// check rent is null or not and max is greater then min
	private boolean checkMinRent() {
			if(searchPropRentMinSpinner.getSelectedItem().toString().equals("Select") ) {
				minRentErrorMsg.requestFocus();
				minRentErrorMsg.setVisibility(View.VISIBLE);
				return true;
			} 
		return false;
	}
	
	//check max rent null or not
	private boolean checkMaxRent() {
		if(searchPropRentMaxSpinner.getSelectedItem().toString().equals("Select")) {
			maxRentErrorMsg.requestFocus();
			maxRentErrorMsg.setVisibility(View.VISIBLE);
			return true;
		}
		return false;
	}
	
	// check the mim/max price is null or not
	private boolean checkMaxPrice() {
		if(searchPropTotalPriceMaxSpinner.getSelectedItem().toString().equals("Select")) {
			maxPriceErrorMsg.requestFocus();
			maxPriceErrorMsg.setVisibility(View.VISIBLE);
			return true;
		} 
		return false;
	}
	
	// check min prica is null or not
	private boolean checkMinPrice() {
		if(searchPropTotalPriceMinSpinner.getSelectedItem().toString().equals("Select")) {
			minPriceErrorMsg.requestFocus();
			minPriceErrorMsg.setVisibility(View.VISIBLE);
			return true;
		}
		return false;
	}
	
	// check the max is greater then min price/rent  
	private boolean checkMinMaxPriceRent() {
		if(searchPropForSpinner.getSelectedItem().toString().equals("Sale")) {
			if(checkMinPrice())
				return true;
			else if(checkMaxPrice())
				return true;
			else {
				if(!searchPropTotalPriceMinSpinner.getSelectedItem().toString().equals("5 Crore and Above") && !searchPropTotalPriceMaxSpinner.getSelectedItem().toString().equals("5 Crore and Above")) {
					if(Double.parseDouble(String.valueOf(SpinnerItem.getTotalPriceList().get(searchPropTotalPriceMaxSpinner.getSelectedItem().toString()))) <= Double.parseDouble(String.valueOf(SpinnerItem.getTotalPriceList().get(searchPropTotalPriceMinSpinner.getSelectedItem().toString())))) {
						Toast.makeText(getActivity(), "Please Select Proper Minimum and Maximum Price", Toast.LENGTH_LONG).show();
						return true;
					}
				}
			}
		} else if(searchPropForSpinner.getSelectedItem().toString().equals("Rent")) {
			if(checkMinRent())
				return true;
			else if(checkMaxRent())
				return true;
			else {
				if(!searchPropRentMinSpinner.getSelectedItem().toString().equals("5,00,000 and Above") && !searchPropRentMaxSpinner.getSelectedItem().toString().equals("5,00,000 and Above")) {
					if(Double.parseDouble(String.valueOf(SpinnerItem.getRentList().get(searchPropRentMaxSpinner.getSelectedItem().toString()))) <= Double.parseDouble(String.valueOf(SpinnerItem.getRentList().get(searchPropRentMinSpinner.getSelectedItem().toString())))) {
						Toast.makeText(getActivity(), "Please Select Proper Minimum and Maximum Rent", Toast.LENGTH_LONG).show();
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	//check min area is less then max area
	private boolean checkMinLessThenMaxArea() {
		if(searchPropAreaMinEditBox.isShown() && searchPropAreaMaxEditBox.isShown()) {
			if(searchPropAreaMinEditBox.length() > 0 && searchPropAreaMaxEditBox.length() > 0) { 
				if((Integer.parseInt(searchPropAreaMaxEditBox.getText().toString())) <=  (Integer.parseInt(searchPropAreaMinEditBox.getText().toString()))){
					Toast.makeText(getActivity(), "Please enter proper min and max area", Toast.LENGTH_LONG).show();
					return true;
				}
			} else if(searchPropAreaMinEditBox.length() > 0 || searchPropAreaMaxEditBox.length() > 0){
				Toast.makeText(getActivity(), "Please Enter both area", Toast.LENGTH_LONG).show();
				return true;
			} 
		}
		return false;
	}	
	//Reset data when property type change
	private void resetData() {
		searchPropForSpinner.setSelection(0);
		arearEditBox.setText("");
		searchPropKeywordEditBox.setText("");
		searchPropRentMinSpinner.setSelection(0);
		searchPropRentMaxSpinner.setSelection(0);
		searchPropTotalPriceMinSpinner.setSelection(0);
		searchPropTotalPriceMaxSpinner.setSelection(0);
		searchPropPurposeSpinner.setSelection(0);
		searchPropAreaMinEditBox.setSelection(0);
		searchPropAreaMaxEditBox.setSelection(0);
		searchPropPlotAreaMinEditBox.setText("");
		searchPropPlotAreaMaxEditBox.setText("");
		searchPropConstructionAreaMinEditBox.setText("");
		searchPropConstructionAreaMaxEditBox.setText("");
		searchPropBedMinSpinner.setSelection(0);
		searchPropBedMaxSpinner.setSelection(0);
		searchPropFloorMinSpinner.setSelection(0);
		searchPropFloorMaxSpinner.setSelection(0);
		searchPropBuildingTypeSpinner.setSelection(0);
		searchPropFurnishOptionSpinner.setSelection(0);
		searchPropTPSchemeSpinner.setSelection(0);
		zoneCheckBox.setChecked(false);;
		searchPropNASpinner.setSelection(0);
		searchPropTermsNaviSharatCheck.setChecked(false);
		searchPropTermsJuniSharatCheck.setChecked(false);
		searchPropTermsKhetiCheck.setChecked(false);
		searchPropTermsPRASHACheck.setChecked(false);
		searchPropTermsDisputeCheck.setChecked(false);
		searchPropTermsShreeSarkarCheck.setChecked(false);
		
		//Uncheck zone check box if prpoerty type change other then plot
		if(!SpinnerItem.getPropertyTypeOptionList().get(searchPropPropTypeSpinner.getSelectedItem().toString()).equals("Plot") || !searchPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Plot)")) {
			if(zoneArray != null) {
				for(int i=0; i<searchPropZoneLayout.getChildCount(); i++) {
					View child = searchPropZoneLayout.getChildAt(i);
					if(child instanceof CheckBox) {
						CheckBox cb = (CheckBox) child;
						cb.setChecked(false);
					}
				}
			}
		}
	}
	
	private void showHidelayout() {
		searchPropAreaLayout.setVisibility(View.GONE);//area
		searchPropBedLayout.setVisibility(View.GONE); //Min/Max Bed
		searchPropFloorLayout.setVisibility(View.GONE);//Min/Max Floor
		searchPropBuildingTypeLayout.setVisibility(View.GONE);//Bulding Type
		searchPropPurposeLayout.setVisibility(View.GONE);//Purpose 
		searchPropFurnishOptionLayout.setVisibility(View.GONE);//furnish 
		searchPropComBunglowConstLayout.setVisibility(View.GONE);//Construction Area
		searchPropTPSchemeLayout.setVisibility(View.GONE);//TP SCheme
		searchPropZoneLayout.setVisibility(View.GONE);//Zone
		searchPropNALayout.setVisibility(View.GONE);//N.A.
		searchPropTermsLayout.setVisibility(View.GONE);//Trems
		searchPropPlotAreaLayout.setVisibility(View.GONE);//Plot area
	
		//Web Service for TPScheme This web service is used to set the TPScheme Spinner
		//For Property type Bunglow and sub Property type Farm House(Bunglow) for Sale/Rent and PLOT
		if((SpinnerItem.getPropertyTypeOptionList().get(searchPropPropTypeSpinner.getSelectedItem().toString()).equals("Bunglow") && searchPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)"))
				|| SpinnerItem.getPropertyTypeOptionList().get(searchPropPropTypeSpinner.getSelectedItem().toString()).equals("Plot") ||searchPropPropTypeSpinner.getSelectedItem().toString().equals("All Plots")){
				getTpSchem();
		}
	
		//reset the Property type adapter
		/*ArrayList<String> propertyTypeOptionList = new ArrayList<String>();
		propertyTypeOptionList.add((String) searchPropPropTypeSpinner.getSelectedItem());
		ArrayAdapter< String> prpoertyTypeOptionAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,propertyTypeOptionList);
		prpoertyTypeOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchPropPropTypeSpinner.setAdapter(prpoertyTypeOptionAdapter);*/
		//END
		
		//Reset the Sale/Rent Spinner
		/*ArrayList<String> propertyForOptionList = new ArrayList<String>();
		propertyForOptionList.add((String) searchPropForSpinner.getSelectedItem());
		ArrayAdapter< String> prpoertyForOptionAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,propertyForOptionList);
		prpoertyForOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
		searchPropForSpinner.setAdapter(prpoertyForOptionAdapter);*/
		//END
		
		//searchPropMoreOptionButton.setVisibility(View.GONE);
		//searchPropFlatLayout.setVisibility(View.VISIBLE);
		
		//Flat For Sale Set Layout To Visible
		if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Flat") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropAreaLayout.setVisibility(View.VISIBLE);//area
			searchPropBedLayout.setVisibility(View.VISIBLE); //Min/Max Bed
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose 
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish 
		} //Flat For Rent Set Layout To Visible
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Flat") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropAreaLayout.setVisibility(View.VISIBLE);
			searchPropRentLayout.setVisibility(View.VISIBLE);//Rent
			searchPropBedLayout.setVisibility(View.VISIBLE);
			searchPropFloorLayout.setVisibility(View.VISIBLE);
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);
			searchPropPurposeLayout.setVisibility(View.VISIBLE);
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);
		} //Shop For Sale Set Layout To Visible 
		else if((searchPropPropTypeSpinner.getSelectedItem().toString().equals("Shop") || (searchPropPropTypeSpinner.getSelectedItem().toString().equals("All Shop"))) && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropAreaLayout.setVisibility(View.VISIBLE);//area
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Shop For Rent Set Layout To Visible
		else if((searchPropPropTypeSpinner.getSelectedItem().toString().equals("Shop") || (searchPropPropTypeSpinner.getSelectedItem().toString().equals("All Shop"))) && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropAreaLayout.setVisibility(View.VISIBLE);//area
			searchPropRentLayout.setVisibility(View.VISIBLE);//Rent
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Show Room For Sale Set Layout To Visible
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Show Rooms") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropAreaLayout.setVisibility(View.VISIBLE);//area
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Show Room For Rent Set Layout To Visible 
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Show Rooms") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropAreaLayout.setVisibility(View.VISIBLE);//area
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Commercial bunglow For Sale Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Commercial Bunglow") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Commercial bunglow For Rent Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Commercial Bunglow") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Commercial Flat For Sale Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Commercial Flat") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropAreaLayout.setVisibility(View.VISIBLE);// area
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Commercial Flat For Rent Set Layout To Visible
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Commercial Flat") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropAreaLayout.setVisibility(View.VISIBLE);// area
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Office For Sale Set Layout To Visible
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Office") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropAreaLayout.setVisibility(View.VISIBLE);// area
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Office For Rent Set Layout To Visible 
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Office") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropAreaLayout.setVisibility(View.VISIBLE);// area
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Corporate House For Sale Set Layout To Visible 
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Corporate House") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Corporate House For Rent Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Corporate House") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Godown For Sale Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Godown") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Godown For Rent Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Godown") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Shades For Sale Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Shades") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Shades For Rent Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Shades") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Factory For Sale Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Factory") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Factory For Rent Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Factory") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropFloorLayout.setVisibility(View.VISIBLE);//Min/Max Floor
			searchPropBuildingTypeLayout.setVisibility(View.VISIBLE);//Bulding Type
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Bunglow For Sale Set Layout To Visible **/
		else if((searchPropPropTypeSpinner.getSelectedItem().toString().equals("Bunglow") || searchPropPropTypeSpinner.getSelectedItem().toString().equals("All Bunglow")) && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Bunglow For Rent Set Layout To Visible **/
		else if((searchPropPropTypeSpinner.getSelectedItem().toString().equals("Bunglow") || searchPropPropTypeSpinner.getSelectedItem().toString().equals("All Bunglow")) && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Twin For Sale Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Twin") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Twin For Rent Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Twin") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Row House For Sale Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Row House") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} // Row House For Rent Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Row House") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Tenament For Sale Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Tenament") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Tenament For Rent Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Tenament") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Individual For Sale Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Individual") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Individual For Rent Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Individual") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Independent Bunglow For Sale Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Independent Bunglows") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Independent Bunglow For Rent Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Independent Bunglows") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropBedLayout.setVisibility(View.VISIBLE);//Min/Max Bed
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
			searchPropFurnishOptionLayout.setVisibility(View.VISIBLE);//furnish
		} //Farm House For Sale Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Farm House For Rent Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construction Area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Plot For Sale Set Layout To Visible **/
		else if((searchPropPropTypeSpinner.getSelectedItem().toString().equals("Plot") || searchPropPropTypeSpinner.getSelectedItem().toString().equals("All Plots")) && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Plot For Rent Set Layout To Visible **/
		else if((searchPropPropTypeSpinner.getSelectedItem().toString().equals("Plot") || searchPropPropTypeSpinner.getSelectedItem().toString().equals("All Plots")) && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Society Plot For Sale Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Society Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Society Plot For Rent Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Society Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Society Sub For Sale Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Society Sub Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Society Sub For Rent Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Society Sub Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //F.P Plot For Sale Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("F.P.Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //F.P Plot For Rent Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("F.P.Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Land of T.P For Sale Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Land Of T.P") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Land of T.P For Rent Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Land Of T.P") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Land of Agriculture For Sale Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Land of Agriculture") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Land of Agriculture For Rent Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Land of Agriculture") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Land of Industrial For Sale Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Land of Industrial") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Land of Industrial For Rent Set Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Land of Industrial") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Development Society For Sale Layout To Visible **/
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Development Society") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Development Society For Rent Layout To Visible
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Development Society") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Farm House For Sale Layout To Visible
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Plot)") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construal area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Farm House For Rent Layout To Visible
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Plot)") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropComBunglowConstLayout.setVisibility(View.VISIBLE);//Construal area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Individual Plot For Sale Layout To Visible
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Individual Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Individual Plot For Rent Layout To Visible 
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Individual Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Gam Tal Land For Sale Layout To Visible
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Gam Tal Land") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //Gam Tal Land For Rent Layout To Visible
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("Gam Tal Land") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //GIDC Plot for Sale Layout To Visible
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("GIDC Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //GIDC Plot for Rent Layout To Visible
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("GIDC Plot") && searchPropForSpinner.getSelectedItem().toString().equals("Rent")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} //SEZ for Sale Layout To Visible 
		else if(searchPropPropTypeSpinner.getSelectedItem().toString().equals("SEZ Land") && searchPropForSpinner.getSelectedItem().toString().equals("Sale")){
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		} else {//SEZ for Rent Layout To Visible
			searchPropTPSchemeLayout.setVisibility(View.VISIBLE);//TP SCheme
			searchPropZoneLayout.setVisibility(View.VISIBLE);//Zone
			searchPropNALayout.setVisibility(View.VISIBLE);//N.A.
			searchPropTermsLayout.setVisibility(View.VISIBLE);//Trems
			searchPropPlotAreaLayout.setVisibility(View.VISIBLE);//Plot area
			searchPropPurposeLayout.setVisibility(View.VISIBLE);//Purpose
		}
	}
	
	/*private void addZoneCheckBox() {
		zoneArray = new ArrayList<String>();
		for(int i=0;i<CheckBoxItem.zoneFarmHouseCheckItem.length;i++){
			zoneCheckBox = new CheckBox(getActivity());
			zoneCheckBox.setId(i);
			zoneCheckBox.setTextColor(Color.BLACK);
			zoneCheckBox.setText(CheckBoxItem.zoneFarmHouseCheckItem[i]);
			zoneCheckBox.setButtonDrawable(R.xml.custom_checkbox);
			searchPropZoneLayout.addView(zoneCheckBox);
			zoneCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked){
						zoneArray.add(buttonView.getText().toString());
					} else {
						zoneArray.remove(buttonView.getText().toString());
					}
				}
			});
		}
	}*/
}


