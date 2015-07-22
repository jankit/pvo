package com.pvo.activity;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pvo.components.NumberToWord;
import com.pvo.components.SpinnerItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.FindByPropertyIdService;
import com.pvo.user.service.ViewProfileService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.pvo.util.ConvertDateFormat;
import com.squareup.picasso.Picasso;

//This is used for display the full information of the my property on click of first text view of my property List
public class MyPropertyDetailActivity extends PVOFragment {
	
	private TextView propertyIdTxt;
	private TextView addressTxt;
	private TextView postcodeTxt;
	private TextView locationTxt;
	//private TextView landmarkTxt;
	private TextView myPropDetStateTv;
	private TextView myPropDetStateValTv;
	private TextView myPropDetCityTv;
	private TextView myPropDetCityValTv;
	private TextView typeofRiseTxt; 
	private TextView myPropDetBedTv;
	private TextView furnishOptionsTxt;
	//private TextView nATxt;
	private TextView dateUpdatedTxt;
	private TextView floorTxt;
	private TextView BuiltupAreaTxt;
	private TextView yearbuiltupTxt;
	private TextView  additionalChargesTxt;
	private TextView occupacyTxt;
	private TextView discriptionTxt;
	private TextView purposeTxt;
	private TextView propertyStatisticsTxt;
	//Text View for Display Json Array value
	private TextView propertyId;
	private TextView address;
	private TextView postcode;
	private TextView location;
	//private TextView landmark;
	private TextView typeofRise;
	private TextView myPropDetBedValTv;
	private TextView furnishOptions;
	//private TextView nA;
	private TextView dateUpdated;
	private TextView floor;
	private TextView BuiltupArea;
	private TextView yearbuiltup;
	private TextView  additionalCharges;
	private LinearLayout facility;
	private TextView occupacy;
	private TextView discription;
	private TextView purpose;
	private TextView propertyStatistics;
	private TextView myPropDetSubHeaderTv;
	private TextView numberOfImage;
	private Button backButton;
	private Button myPropertyDetailSendSmsBtn;
	private Button imageNextButton;
	private Button imagePreviousButton;
	//Broker information
	private Button btnShowBrokerDetail;
	private TextView myPropDetBrokerIdTv;
	private TextView myPropDetBrokerIdValTv;
	private TextView myPropDetBrokerNameTv;
	private TextView myPropDetNameValTv;
	private TextView myPropDetBrokerCompanyTv;
	private TextView myPropDetCompanyValTv;
	private TextView myPropDetBrokerAddressTv;
	private TextView myPropDetAddressValTv;
	private TextView myPropDetPhoneNoTv;
	private TextView myPropDetPhoneNoValTv;
	private TextView myPropDetEmailTv;
	private TextView myPropDetEmailValTv;
	private String brokerId;
	private RelativeLayout rlBrokerInfo;
	//Get Stored User Id From User SessionManager
	private UserSessionManager userSessionManager;
	private FindByPropertyIdService findByPropertyIdService;
	private Bundle propertyIdIntent;
	private GoogleMap googleMap;
	private Marker googleMarker;
	//private ImageView propDetailMap;
	private Gallery gallery;
	int width;
	int height;
	
	
	//Set layout content view
	public MyPropertyDetailActivity() {
		setContentView(R.layout.activity_myproperty_detail);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		numberOfImage 	= (TextView) findViewById(R.id.numberOfImage);
		
		WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		
		userSessionManager 			= new UserSessionManager(getActivity().getApplicationContext());
		findByPropertyIdService		= new FindByPropertyIdService();
		propertyIdIntent 			= getArguments();
		
		//goto the send email sms screen
		myPropertyDetailSendSmsBtn 	= (Button) findViewById(R.id.myPropertyDetailSendSmsBtn);
		myPropertyDetailSendSmsBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("propertyid",propertyIdIntent.getString("propertyid"));
				EmailSmsSendActivity emailSmsSendActivity = new EmailSmsSendActivity();
				emailSmsSendActivity.setArguments(bundle);
				((MainFragmentActivity)getActivity()).redirectScreen(emailSmsSendActivity);
			}
		});
		//END
		
		gallery 				= (Gallery) findViewById(R.id.gallery);
		myPropDetSubHeaderTv	= (TextView) findViewById(R.id.myPropDetSubHeaderTv);

		propertyIdTxt			= (TextView)findViewById(R.id.myPropDettv1);
		propertyId				= (TextView)findViewById(R.id.myPropDettv11);
		
		myPropDetStateTv 		= (TextView) findViewById(R.id.myPropDetStateTv);
		myPropDetStateValTv     = (TextView) findViewById(R.id.myPropDetStateValTv);
		
		myPropDetCityTv			= (TextView) findViewById(R.id.myPropDetCityTv);
		myPropDetCityValTv  	= (TextView) findViewById(R.id.myPropDetCityValTv);
		
		addressTxt				= (TextView)findViewById(R.id.myPropDettv2);
		address					= (TextView)findViewById(R.id.myPropDettv12);
		
		postcodeTxt				= (TextView)findViewById(R.id.myPropDettv3);
		postcode				= (TextView)findViewById(R.id.myPropDettv13);
		
		locationTxt				= (TextView)findViewById(R.id.myPropDettv4);
		location				= (TextView)findViewById(R.id.myPropDettv14);
		
		/*landmarkTxt				= (TextView)findViewById(R.id.myPropDettv5);
		landmark				= (TextView)findViewById(R.id.myPropDettv15);*/
		
		typeofRiseTxt			= (TextView)findViewById(R.id.myPropDettv6);
		typeofRise				= (TextView)findViewById(R.id.myPropDettv16);
		
		myPropDetBedTv 			= (TextView)findViewById(R.id.myPropDetBedTv);
		myPropDetBedValTv 		= (TextView)findViewById(R.id.myPropDetBedValTv);
		
		furnishOptionsTxt		= (TextView)findViewById(R.id.myPropDettv7);
		furnishOptions			= (TextView)findViewById(R.id.myPropDettv17);
		
		//nATxt					= (TextView)findViewById(R.id.myPropDettv8);
		//nA						= (TextView)findViewById(R.id.myPropDettv18);
		
		dateUpdatedTxt			= (TextView)findViewById(R.id.myPropDettv9);
		dateUpdated				= (TextView)findViewById(R.id.myPropDettv19);
		
		floorTxt				= (TextView)findViewById(R.id.myPropDettv10);
		floor					= (TextView)findViewById(R.id.myPropDettv20);
		
		BuiltupAreaTxt			= (TextView)findViewById(R.id.myPropDettv21);
		BuiltupArea				= (TextView)findViewById(R.id.myPropDettv27);
		
		yearbuiltupTxt			= (TextView)findViewById(R.id.myPropDettv22);
		yearbuiltup				= (TextView)findViewById(R.id.myPropDettv28);
		
		additionalChargesTxt	= (TextView)findViewById(R.id.myPropDettv23);
		additionalCharges		= (TextView)findViewById(R.id.myPropDettv29);
		
		facility				= (LinearLayout)findViewById(R.id.myPropDettv30);
		
		occupacyTxt 			= (TextView) findViewById(R.id.myPropDetOccupacyTv);
		occupacy 				= (TextView) findViewById(R.id.myPropDetOccupacyValTv);
		
		discriptionTxt 			= (TextView) findViewById(R.id.myPropDetDiscriptionTv);
		discription 			= (TextView) findViewById(R.id.myPropDetDiscriptionValTv);
		
		purposeTxt 				= (TextView) findViewById(R.id.myPropDetPurposeTv);
		purpose 				= (TextView) findViewById(R.id.myPropDetPurposeValTv);
		
		propertyStatisticsTxt 	= (TextView) findViewById(R.id.myPropDetPropStatisticsTv);
		propertyStatistics 		= (TextView) findViewById(R.id.myPropDetPropStatisticsValTv);
		
		imageNextButton			= (Button) findViewById(R.id.imageNextButton);
		imagePreviousButton		= (Button) findViewById(R.id.imagePreviousButton);
		
		myPropDetBrokerIdTv		= (TextView) findViewById(R.id.myPropDetBrokerIdTv);
		myPropDetBrokerIdValTv	= (TextView) findViewById(R.id.myPropDetBrokerIdValTv);
		
		myPropDetBrokerNameTv	= (TextView) findViewById(R.id.myPropDetBrokerNameTv);
		myPropDetNameValTv		= (TextView) findViewById(R.id.myPropDetNameValTv);
		
		myPropDetBrokerCompanyTv = (TextView) findViewById(R.id.myPropDetBrokerCompanyTv);
		myPropDetCompanyValTv	= (TextView) findViewById(R.id.myPropDetCompanyValTv);
		
		myPropDetBrokerAddressTv= (TextView) findViewById(R.id.myPropDetBrokerAddressTv);
		myPropDetAddressValTv	= (TextView) findViewById(R.id.myPropDetAddressValTv);
		
		myPropDetPhoneNoTv		= (TextView) findViewById(R.id.myPropDetPhoneNoTv);
		myPropDetPhoneNoValTv	= (TextView) findViewById(R.id.myPropDetPhoneNoValTv);
		
		myPropDetEmailTv		= (TextView) findViewById(R.id.myPropDetEmailTv);	
		myPropDetEmailValTv		= (TextView) findViewById(R.id.myPropDetEmailValTv);
		
		rlBrokerInfo 			= (RelativeLayout) findViewById(R.id.rlBrokerInfo);
		
		//propDetailMap			= (ImageView) findViewById(R.id.propDetailMap);
		
		//onBack Button press Move to previous fragment
		backButton				= (Button)findViewById(R.id.myPropertyDetailBackBtn);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}		
		});
		
		//Show broker
		btnShowBrokerDetail = (Button) findViewById(R.id.btnShowBrokerDetail);
		btnShowBrokerDetail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//showBrokerDetail();
			}
		});
		new WebserviceClient(MyPropertyDetailActivity.this, findByPropertyIdService).execute(propertyIdIntent.getString("propertyid"),userSessionManager.getSessionValue(Constant.Login.USER_ID));
	}

	@Override
	public void processResponse(Object response) {
		JSONArray jsonArry=(JSONArray)response;
		try {
			if (response != null && !((JSONObject)jsonArry.get(0)).has(Constant.MyProperty.API_STATUS)) {
				
				JSONObject jsonObject=(JSONObject) jsonArry.getJSONObject(0);
	    		//get Image object from response
	    		final ArrayList<String> imageList = new ArrayList<String>();
	    		imageList.add(jsonObject.getString("image1link"));
	    		imageList.add(jsonObject.getString("image2link"));
	    		imageList.add(jsonObject.getString("image3link"));
	    		imageList.add(jsonObject.getString("image4link"));
	
	    		//set decode image string and set into image view
	    		gallery.setAdapter(new ImageAdapter(getActivity(),imageList));
	    		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
		    		@Override
		    		public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long arg3) {
		    			numberOfImage.setText(position+1+"/"+imageList.size());
		    		}
		    		@Override
		    		public void onNothingSelected(AdapterView<?> arg0) {}	
	    		});
	    		
	    		//Set Next image from list 
	    		imageNextButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						int position = gallery.getSelectedItemPosition() + 1;
						if (position < 0)
					          return;
						if(position < imageList.size())
				    	  gallery.setSelection(position);
					}
				});
	    		
	    		//Set Previous image from list
	    		imagePreviousButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						int position = gallery.getSelectedItemPosition() - 1;
						if (position < 0)
					          return;
						if(position < imageList.size())
				    	  gallery.setSelection(position);
					}
				});
	    		
	    		//set header of Property Detail page
	    		String propertySubType = "";
	    		if(!jsonObject.getString(Constant.AddProperty.PROPERTY_TYPE).equals("Flat") && !jsonObject.getString(Constant.AddProperty.PROPERTY_TYPE).equals("Plot") && !jsonObject.getString(Constant.AddProperty.PROPERTY_TYPE).equals("Shop"))
	    			propertySubType = jsonObject.getString(Constant.FindByPropertyId.PROPERTY_SUB_TYPE);
	    		
	    		if(jsonObject.getString(Constant.AddProperty.STR_OPTIONS).equalsIgnoreCase("Sale")) 
	    			myPropDetSubHeaderTv.setText(jsonObject.getString(Constant.AddProperty.PROPERTY_TYPE)+" "+ propertySubType + " For "+jsonObject.getString(Constant.AddProperty.STR_OPTIONS)+"-"+toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.TOTAL_PRICE)))+"("+NumberToWord.numberToWord(jsonObject.getString(Constant.AddProperty.TOTAL_PRICE))+")");
	    		else 
	    			myPropDetSubHeaderTv.setText(jsonObject.getString(Constant.AddProperty.PROPERTY_TYPE)+" "+ propertySubType + " For "+jsonObject.getString(Constant.AddProperty.STR_OPTIONS)+"-"+toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.RENT)))+"("+NumberToWord.numberToWord(jsonObject.getString(Constant.AddProperty.RENT))+")");
	    		
	    		brokerId = jsonObject.getString(Constant.AddProperty.BROKER_ID);
	    				
	    		//Display Property Id
	    		propertyId.setText("PVO "+jsonObject.getString(Constant.MyProperty.PROPERTY_ID));
	    		propertyIdTxt.setMinLines(propertyId.getLineCount());
	    		propertyId.setMinLines(propertyId.getLineCount());
	    		
	    		//State
	    		myPropDetStateValTv.setText(jsonObject.getString(Constant.MyProperty.STATE_NAME));
	    		myPropDetStateTv.setMinLines(myPropDetStateValTv.getLineCount());
	    		myPropDetStateValTv.setMinLines(myPropDetStateValTv.getLineCount());
	    		
	    		//City
	    		myPropDetCityValTv.setText(jsonObject.getString(Constant.MyProperty.CITY_NAME));
	    		myPropDetCityTv.setMinLines(myPropDetCityValTv.getLineCount());
	    		myPropDetCityValTv.setMinLines(myPropDetCityValTv.getLineCount());
	
	    		//Display Address
	    		address.setText(jsonObject.getString(Constant.AddProperty.ADDRESS));
	    		addressTxt.setMinLines(address.getLineCount());
	    		address.setMinLines(address.getLineCount());
	    		
	    		//Display Post Code
	    		if(!jsonObject.getString(Constant.AddProperty.POST_CODE).equals("")) 
	    			postcode.setText(jsonObject.getString(Constant.AddProperty.POST_CODE));
	    		else 
	    			postcode.setText("Not mention");
	    		
	    		postcodeTxt.setMinLines(postcode.getLineCount());
	    		postcode.setMinLines(postcode.getLineCount());
	    		
	    		//Display Arae1
	    		if(!jsonObject.getString(Constant.AddProperty.AREANAME).equals("")) 
	    			location.setText(jsonObject.getString(Constant.AddProperty.AREANAME));
	    		else 
	    			location.setText("Will be informed on call");
	
	    		locationTxt.setMinLines(location.getLineCount());
	    		location.setMinLines(location.getLineCount());
	    		
	    		//Display Landmark
	    		/*if(jsonObject.getString(Constant.AddProperty.LANDMARK1NAME).equals("") && jsonObject.getString(Constant.AddProperty.LANDMARK2NAME).equals("")){
	    			landmark.setText("Will be informed on call");
	    		} else if(!jsonObject.getString(Constant.AddProperty.LANDMARK1NAME).equals("") 
	                && jsonObject.getString(Constant.AddProperty.LANDMARK2NAME).equals("") 
	    		        ){
	    		    landmark.setText(jsonObject.getString(Constant.AddProperty.LANDMARK1NAME));
	            }else if(!jsonObject.getString(Constant.AddProperty.LANDMARK1NAME).equals("") 
	                    && !jsonObject.getString(Constant.AddProperty.LANDMARK2NAME).equals("") 
	                    ){
	                landmark.setText(jsonObject.getString(Constant.AddProperty.LANDMARK1NAME)+","+jsonObject.getString(Constant.AddProperty.LANDMARK2NAME));
	            }
	    		landmarkTxt.setMinLines(landmark.getLineCount());
	    		landmark.setMinLines(landmark.getLineCount());*/
	    			
	    		//Display type of rise
	    		if(!jsonObject.getString(Constant.AddProperty.RISE).equals("")) 
	    			typeofRise.setText(SpinnerItem.getBuildingTypeListKey(jsonObject.getString(Constant.AddProperty.RISE)));
	    		 else 
	    			typeofRise.setText("Will be informed on call");
	
	    		typeofRiseTxt.setMinLines(typeofRise.getLineCount());
	    		typeofRise.setMinLines(typeofRise.getLineCount());
	    		
	    		//Hide bed and purpose if property type shop
	    		if(jsonObject.getString("propertytype").equals("Shop")) {
	    			myPropDetBedTv.setVisibility(View.GONE);
	    			myPropDetBedValTv.setVisibility(View.GONE);
	    			purpose.setVisibility(View.GONE);
	    			purposeTxt.setVisibility(View.GONE);
	    			
	    		//hide bed if property type Plot and show purpose
	    		} else if(jsonObject.getString("propertytype").equals("Plot")) {
	    			myPropDetBedTv.setVisibility(View.GONE);
	    			myPropDetBedValTv.setVisibility(View.GONE);
	    			purpose.setVisibility(View.VISIBLE);
	    			purposeTxt.setVisibility(View.VISIBLE);
	    			
	    		//hide bed if Property type Farm House(Bunglow)
	    		} else if(jsonObject.getString("propertytype").equals("Farm House(Bunglow)")) {
	    			myPropDetBedTv.setVisibility(View.GONE);
	    			myPropDetBedValTv.setVisibility(View.GONE);
	    			purpose.setVisibility(View.VISIBLE);
	    			purposeTxt.setVisibility(View.VISIBLE);
	    		//Visible Bed
	    		} else {
	    			myPropDetBedTv.setVisibility(View.VISIBLE);
	    			myPropDetBedValTv.setVisibility(View.VISIBLE);
	    			myPropDetBedValTv.setText(jsonObject.getString(Constant.AddProperty.BED));
	    			myPropDetBedTv.setMinLines(myPropDetBedValTv.getLineCount());
	    			myPropDetBedValTv.setMinLines(myPropDetBedValTv.getLineCount());
	    			purpose.setVisibility(View.VISIBLE);
	    			purposeTxt.setVisibility(View.VISIBLE);
	    		}
	    
	    		//Display furnish option
	    		furnishOptions.setText(SpinnerItem.getFurnishOptionListKey(jsonObject.getInt(Constant.AddProperty.FURNISH_STATUS))+"\nFurnish Comment: "+jsonObject.getString(Constant.AddProperty.FURNISH_COMMENT));
	    		furnishOptionsTxt.setMinLines(furnishOptions.getLineCount());
	    		furnishOptions.setMinLines(furnishOptions.getLineCount());
	    
	    		//Display NA Status
	    		/*nA.setText("Will be informed on call");
	    		nATxt.setMinLines(nA.getLineCount());
	    		nA.setMinLines(nA.getLineCount());*/
	    		
	    		dateUpdated.setText(ConvertDateFormat.convertDateFormat(jsonObject.getString(Constant.AddProperty.DATE_UPDTE)));//object.getString(""));
	    		dateUpdatedTxt.setMinLines(dateUpdated.getLineCount());
	    		dateUpdated.setMinLines(dateUpdated.getLineCount());
	    		
	    		if(jsonObject.getString("propertytype").equals("Plot")) {
	    			floorTxt.setVisibility(View.GONE);
	    			floor.setVisibility(View.GONE);
	    		} else if(jsonObject.getString("propertytype").equals("Bunglow")) {
	    			floorTxt.setVisibility(View.GONE);
	    			floor.setVisibility(View.GONE);
	    		} else {
	    			floorTxt.setVisibility(View.VISIBLE);
	    			floor.setVisibility(View.VISIBLE);
	    			
	    			if(jsonObject.getString(Constant.AddProperty.FLOOR).equals("0"))
	    			    floor.setText("will be informed on call");
	    			else 
	    			    floor.setText(SpinnerItem.getAddPropFloorListKey(jsonObject.getString(Constant.AddProperty.FLOOR)));
	        		
	    			floorTxt.setMinLines(floor.getLineCount());
	        		floor.setMinLines(floor.getLineCount());
	    		}
	    		
	    		//Add condition For Shop Type to Show Different Area in this we require Property Sub type
	    		// Flat,shop,show room,office display only Area
	    		if(jsonObject.getString("propertytype").equals("Flat") ||propertySubType.equals("Shop")||propertySubType.equals("Show Room") ||propertySubType.equals("Office")){
	    			BuiltupArea.setText("Area - "+jsonObject.getString(Constant.AddProperty.MIN_AREA)+" "+jsonObject.getString(Constant.AddProperty.AREA_UNIT));
	    		} else if(jsonObject.getString("propertytype").equals("Bunglow")) {
	    			BuiltupArea.setText("Plot Area - "+jsonObject.getString(Constant.AddProperty.PLOT_AREA)+" "+jsonObject.getString(Constant.AddProperty.PLOT_AREA_UNIT)+"\nConstr. Area - "+jsonObject.getString(Constant.AddProperty.CONSTRUCTION_AREA));
	    		} else{
	    			BuiltupArea.setText("Area - "+jsonObject.getString(Constant.AddProperty.MIN_AREA)+" "+jsonObject.getString(Constant.AddProperty.AREA_UNIT)+"\nPlot Area - "+jsonObject.getString(Constant.AddProperty.PLOT_AREA)+" "+jsonObject.getString(Constant.AddProperty.PLOT_AREA_UNIT)+"\nConstr. Area - "+jsonObject.getString(Constant.AddProperty.CONSTRUCTION_AREA));
	    		}
	    		BuiltupAreaTxt.setMinLines(BuiltupArea.getLineCount());
	    		BuiltupArea.setMinLines(BuiltupArea.getLineCount());
	    		
	    		//yearbuiltup;
	    		if (jsonObject.getString(Constant.AddProperty.YEAR_BUILD_UP).equals("0")) {
	    			yearbuiltup.setText("Do not know");
	    		} else {
	    			yearbuiltup.setText(jsonObject.getString(Constant.AddProperty.YEAR_BUILD_UP));
	    		}
	    		yearbuiltupTxt.setMinLines(yearbuiltup.getLineCount());
	            yearbuiltup.setMinLines(yearbuiltup.getLineCount());
	    		
	    		if(jsonObject.getString("stroptions").equals("Sale")){
	    			additionalCharges.setText(
						    "Price Per Unit - "+toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.PRICE)))
						    +"\nDastawage - "+toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.DASTAWAGE)))
							+"\nTrans. Fee - "+toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.TRANSFER_FEES)))
							+"\nAEC/AUDA - "+toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.AEC_AUDA)))
							+"\nParking - "+toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.PARKING)))
							+"\nMaintenance - "+toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.PARKING)))+"\n"
							);
	    		} else {
	    			String rentDeposite;
	    			if(jsonObject.getString(Constant.AddProperty.RENT_DEPOSIT).equals("0.00"))
	    				rentDeposite= "Tell you later";
	    			else
	    				rentDeposite = toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.RENT_DEPOSIT)));
	    			
	    			additionalCharges.setText("Rent - "+toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.RENT)))+"\nRent Deposite - "+rentDeposite +"\nMaintenance - "+toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.MAINTENANCE))));
	    		}
	    		additionalChargesTxt.setMinLines(additionalCharges.getLineCount());
	            additionalCharges.setMinLines(additionalCharges.getLineCount());
			    
	            JSONArray facilityArray = jsonObject.getJSONArray(Constant.AddProperty.FACILITIES);
	            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(25, 25);
                layoutParams.setMargins(3, 0 , 3, 0);
                layoutParams.gravity = Gravity.CENTER;
				if (!facilityArray.get(0).equals("no_facility")) {
					for (int i = 0; i < facilityArray.length(); i++) {
						ImageView image = new ImageView(getActivity());
						Picasso.with(getActivity()).load(facilityArray.getString(i)).into(image);
						image.setLayoutParams(layoutParams);
						facility.addView(image);
					}
				}
			            		
	    		if(jsonObject.getString(Constant.AddProperty.PURPOSE).equals("")) {
	    			purpose.setText("Not define");
	    		} else {
	    			purpose.setText(jsonObject.getString(Constant.AddProperty.PURPOSE));
	    		}
	    		purposeTxt.setMinLines(purpose.getLineCount());
	            purpose.setMinLines(purpose.getLineCount());
	    		
	    		occupacy.setText("Occupacy Name - "+(jsonObject.getString(Constant.AddProperty.OCCUPANCY_NAME).equals("") ? "Will be informed on call" : jsonObject.getString(Constant.AddProperty.OCCUPANCY_NAME))+"\nOccupacy Details - "+(jsonObject.getString(Constant.AddProperty.OCCUPANCY_DETAIL).equals("") ? "Will be informed on call" : jsonObject.getString(Constant.AddProperty.OCCUPANCY_DETAIL))+"\nRelese Date - "+(jsonObject.getString(Constant.AddProperty.OCCUPANCY_DATE).equals("") ? "Will be informed on call" : jsonObject.getString(Constant.AddProperty.OCCUPANCY_DATE)));
	    		occupacyTxt.setMinLines(occupacy.getLineCount());
	    		occupacy.setMinLines(occupacy.getLineCount());
	    		
	    		discription.setText("comments - "+(jsonObject.getString(Constant.AddProperty.COMMENTS).equals("") ? "Will be informed on call" : jsonObject.getString(Constant.AddProperty.COMMENTS))+"\nHint - "+(jsonObject.getString(Constant.AddProperty.HINT).equals("") ? "Will be informed on call" : jsonObject.getString(Constant.AddProperty.HINT)));
	    		discriptionTxt.setMinLines(discription.getLineCount());
	    		discription.setMinLines(discription.getLineCount());
	    		
	    		propertyStatistics.setText("Total Summary Views - "+jsonObject.getString(Constant.AddProperty.SUMMARY_COUNT)+"\nTotal Detailed Views - "+jsonObject.getString(Constant.AddProperty.DETAIL_COUNT));
	    		propertyStatisticsTxt.setMinLines(propertyStatistics.getLineCount());
	    		propertyStatistics.setMinLines(propertyStatistics.getLineCount());
	    		
	    		//setup google map 
	    		setUpMapIfNeeded(Double.parseDouble(jsonObject.getString(Constant.AddProperty.LATITUDE)),Double.parseDouble(jsonObject.getString(Constant.AddProperty.LONGITUDE)));
	    		//propDetailMap
	    		//Picasso.with(getActivity()).load("https://maps.googleapis.com/maps/api/staticmap?zoom=9&size="+width/2+"x300&maptype=roadmap&markers=color:blue|label:|"+ jsonObject.getString(Constant.AddProperty.LATITUDE) + "," + jsonObject.getString(Constant.AddProperty.LONGITUDE) +"&scale=2").into(propDetailMap);
	    		
	    		
	    		//"http://maps.google.com/maps/api/staticmap?center="+ jsonObject.getString(Constant.AddProperty.LATITUDE) + "," + jsonObject.getString(Constant.AddProperty.LONGITUDE) +"&zoom=15&size=500x300&scale=2&markers=color:green|color:red|label:C|40.718217,-73.998284&sensor=true"
	    		//Hide Email sms button if property posted by same user
	    		if(!jsonObject.getString(Constant.AddProperty.BROKER_ID).equals(userSessionManager.getSessionValue(Constant.Login.USER_ID)))
	    			myPropertyDetailSendSmsBtn.setVisibility(View.VISIBLE);
	    		
				} else {
					Toast.makeText(getActivity(),((JSONObject)jsonArry.get(0)).getString(Constant.MyProperty.API_MESSAGE), Toast.LENGTH_LONG).show();
					((MainFragmentActivity)getActivity()).onBackPressed();
					//FindPropertyByIdActivity findByPropertyIdActivity = new FindPropertyByIdActivity();
					//((MainFragmentActivity)getSherlockActivity()).redirectScreen(findByPropertyIdActivity);
					//getFragmentManager().beginTransaction().replace(R.id.content_frame, findByPropertyIdActivity).commit();
				}
			
			//Show broker id
			showBrokerDetail();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//This is used for Google Map
	/*@SuppressLint("NewApi")
	private void setUpMapIfNeeded(final Double lat, final Double lon) {
		if (googleMap == null) {
			googleMap = ((SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.googleMap)).getMap();
			if (googleMap != null) {
				 googleMap.setMyLocationEnabled(true);
				 googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title("It's Me!"));
				 CameraUpdate center=CameraUpdateFactory.newLatLng(new LatLng(lat,lon));
				 CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
				 googleMap.moveCamera(center);
				 googleMap.animateCamera(zoom);
				 googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
					@Override
					public void onMyLocationChange(Location arg0) {
						 googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title("It's Me!"));
						 CameraUpdate center=CameraUpdateFactory.newLatLng(new LatLng(lat,lon));
						 CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
						 googleMap.moveCamera(center);
						 googleMap.animateCamera(zoom);
					}
				});
			}
		}
	}*/
	
	
	//This is used for set the Location of the google map
	private void setUpMapIfNeeded(Double lat,Double lng) {
		if (googleMap == null) {
			googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.googleMap)).getMap();
			if (googleMap != null) {
			googleMap.setMyLocationEnabled(true);
				
				googleMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)));
				CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(lat,lng));
				googleMap.moveCamera(center);
				CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
				googleMap.animateCamera(zoom);
				
				googleMap.setOnMapClickListener(new OnMapClickListener() {
					@Override
					public void onMapClick(LatLng latlng) {
						drawMarker(latlng);
					}
				});
			}
		}
	}
		
	//Draw marker on Map 
	private void drawMarker(LatLng latlng) {
		if (googleMarker != null) {
			googleMarker.setPosition(latlng);
		} else {
			googleMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(latlng.latitude,latlng.longitude)));
		}
		CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latlng.latitude, latlng.longitude));
		googleMap.moveCamera(center);
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
		googleMap.animateCamera(zoom);
	}
	//END
		
	@Override
	public void onResume() {
	    super.onResume();
	    this.getActivity().getActionBar().setTitle("Property Detail");
	}
	
	//Image Adapter class to set the image in gallery view **/
	 public class ImageAdapter extends BaseAdapter {
	        private Context context;
	        private ArrayList<String> imageUrls;
	        public ImageAdapter(Context c,ArrayList<String> imageUrls) {
	            context = c;
	            this.imageUrls = imageUrls;
	            // ---setting the style---
	             TypedArray a = getActivity().obtainStyledAttributes(R.styleable.Gallery1);
	             a.recycle();
	        }

	        // ---returns the number of images---
	        public int getCount() {
	            return imageUrls.size();
	        }
	        // ---returns the ID of an item---
	        public Object getItem(int position) {
	            return position;
	        }
	        public long getItemId(int position) {
	            return position;
	        }

	        // ---returns an ImageView view---
	        @SuppressLint("InlinedApi")
			@SuppressWarnings("deprecation")
			public View getView(int position, View convertView, ViewGroup parent) {
	           ImageView imageView = new ImageView(context);
	           URL url;
	            try {
	                url = new URL(imageUrls.get(position));
	                //Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
	                //imageView.setImageBitmap(bmp);
	                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	                imageView.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
	                Picasso.with(context).load(imageUrls.get(position)).placeholder(R.drawable.no_image).into(imageView);
	            } catch(Exception e) {
	            	e.printStackTrace();
	            }
	            return imageView;
	        }
	    }
	 
	//This method is used to convert the price into indian rupess  format 
	/*private String toIndianRupessFormat(double doubleValue) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
		return nf.format(doubleValue);
	}*/
		
	// Rest the map fragment for reuse
	@Override
	public void onDestroyView() {
	    super.onDestroyView();
	    try {
	        SupportMapFragment fragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.googleMap);
	        if (fragment != null)
	            getFragmentManager().beginTransaction().remove(fragment).commit();

	    } catch (IllegalStateException e) {
	    	e.printStackTrace();
	    }
	}
	
	//Display the detail of broker 
	private void showBrokerDetail() {
		//view Profile Web service 
		ViewProfileService viewProfileService = new ViewProfileService();
		WebserviceClient viewProfile = new WebserviceClient(MyPropertyDetailActivity.this, viewProfileService);
		viewProfile.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null && !((JSONObject)jsonArray.get(0)).has(Constant.MyProperty.API_STATUS)) {
						
						JSONObject jsonObject = jsonArray.getJSONObject(0);
						//Broker id
						myPropDetBrokerIdValTv.setText(brokerId);
						myPropDetBrokerIdTv.setMinLines(myPropDetBrokerIdValTv.getLineCount());
						myPropDetBrokerIdValTv.setMinLines(myPropDetBrokerIdValTv.getLineCount());
						
						//Name
						myPropDetNameValTv.setText(jsonObject.getString(Constant.ViewProfile.FIRST_NAME)+" "+jsonObject.getString(Constant.ViewProfile.LAST_NAME));
						myPropDetBrokerNameTv.setMinLines(myPropDetNameValTv.getLineCount());
						myPropDetNameValTv.setMinLines(myPropDetNameValTv.getLineCount());
						
						//Company
						myPropDetCompanyValTv.setText(jsonObject.getString(Constant.ViewProfile.COMPANY_NAME));
						myPropDetBrokerCompanyTv.setMinLines(myPropDetCompanyValTv.getLineCount());
						myPropDetCompanyValTv.setMinLines(myPropDetCompanyValTv.getLineCount());
						
						//Address
						myPropDetAddressValTv.setText(jsonObject.getString(Constant.ViewProfile.ADDRESS));
						myPropDetBrokerAddressTv.setMinLines(myPropDetAddressValTv.getLineCount());
						myPropDetAddressValTv.setMinLines(myPropDetAddressValTv.getLineCount());
						
						//Phone number
						myPropDetPhoneNoValTv.setText(jsonObject.getString(Constant.ViewProfile.PHONE_M));
						myPropDetPhoneNoTv.setMinLines(myPropDetPhoneNoValTv.getLineCount());
						myPropDetPhoneNoValTv.setMinLines(myPropDetPhoneNoValTv.getLineCount());
						
						//Email
						myPropDetEmailValTv.setText(jsonObject.getString(Constant.ViewProfile.EMAIL));
						myPropDetEmailTv.setMinLines(myPropDetEmailValTv.getLineCount());
						myPropDetEmailValTv.setMinLines(myPropDetEmailValTv.getLineCount());
						
						//Visible broker info layout
						rlBrokerInfo.setVisibility(View.VISIBLE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		viewProfile.execute(brokerId);
	}
	
	//This method is used to convert the price into indian rupess  format
	private String toIndianRupessFormat(double doubleValue) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
		if(nf.format(doubleValue).length() > 0 && nf.format(doubleValue).contains(".")) {
			return nf.format(doubleValue).substring(0,nf.format(doubleValue).length() - 3);
		} else {
			return "0";
		}
	}
}
	

