package com.pvo.activity;

import java.text.NumberFormat;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.components.NumberToWord;
import com.pvo.components.SpinnerItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.RequirementDetailService;
import com.pvo.user.service.ViewProfileService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.pvo.util.ConvertDateFormat;
import com.squareup.picasso.Picasso;


//This is used for display the full information of the my property on click of first text view of my property List
public class MyRequirementDetailActivity extends PVOFragment {
  
	private TextView requirementID;
    private TextView preferredLocations;
    private TextView dateAdded;
    private TextView dateUpdate;
	private TextView bed;
	private TextView furnishOption; 
	private TextView purpose;
	private TextView floor;
	private TextView buildingType;
	private TextView area;
	private TextView priceBudget;
	private TextView myReqDetSubHeaderTv;
	private Button backButton;
	private Button myReqDetSendSmsBtn;
	
	//text view for display Label
	private TextView requirementIDTxt;
	private TextView preferredLocationsTxt;
	private TextView dateAddedTxt;
	private TextView dateUpdateTxt;
	private TextView bedTxt;
	private TextView furnishOptionTxt; 
	private TextView purposeTxt;
	private TextView floorTxt;
	private TextView buildingTypeTxt;
	private TextView areaTxt;
	private TextView priceBudgetTxt;
	private ImageView myReqDetPropPhotoImg;
	
	//Broker information
	//private Button btnShowBrokerDetail;
	private TextView myReqDetBrokerIdTv;
	private TextView myReqDetBrokerIdValTv;
	private TextView myReqDetBrokerNameTv;
	private TextView myReqDetNameValTv;
	private TextView myReqDetBrokerCompanyTv;
	private TextView myReqDetCompanyValTv;
	private TextView myReqDetBrokerAddressTv;
	private TextView myReqDetAddressValTv;
	private TextView myReqDetPhoneNoTv;
	private TextView myReqDetPhoneNoValTv;
	private TextView myReqDetEmailTv;
	private TextView myReqDetEmailValTv;
	private String brokerId;
	
	//Get Stored User Id From User SessionManager  
	private UserSessionManager userSessionManager;
	private RequirementDetailService requirementDetailService;
	private Bundle requirementIdIntent;
	
	//Set Layout Content View 
	public MyRequirementDetailActivity() {
		setContentView(R.layout.activity_myrequirement_detail_new);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		//get Login User_Id from stored Session
		userSessionManager 			= new UserSessionManager(getActivity().getApplicationContext());
		requirementDetailService	= new RequirementDetailService();
		requirementIdIntent			= getArguments();

		myReqDetSubHeaderTv 		= (TextView) findViewById(R.id.myReqDetSubHeaderTv);
		myReqDetPropPhotoImg 		= (ImageView) findViewById(R.id.myReqDetPropPhotoImg);
		
		requirementIDTxt			= (TextView)findViewById(R.id.myReqDettv1);
		requirementID				= (TextView)findViewById(R.id.myReqDettv11);
		
		preferredLocationsTxt		= (TextView)findViewById(R.id.myReqDettv2);
		preferredLocations			= (TextView)findViewById(R.id.myReqDettv12);
		
		dateAddedTxt				= (TextView)findViewById(R.id.myReqDettv3);
		dateAdded					= (TextView)findViewById(R.id.myReqDettv13);
		
		dateUpdateTxt				= (TextView)findViewById(R.id.myReqDettv4);
		dateUpdate					= (TextView)findViewById(R.id.myReqDettv14);
		
		bedTxt						= (TextView)findViewById(R.id.myReqDettv5);
		bed							= (TextView)findViewById(R.id.myReqDettv15);
		
		furnishOptionTxt			= (TextView)findViewById(R.id.myReqDettv6);
		furnishOption 				= (TextView)findViewById(R.id.myReqDettv16);
		
		purposeTxt					= (TextView)findViewById(R.id.myReqDettv7);
		purpose						= (TextView)findViewById(R.id.myReqDettv17);
		
		floorTxt					= (TextView)findViewById(R.id.myReqDettv8);
		floor						= (TextView)findViewById(R.id.myReqDettv18);
		
		buildingTypeTxt				= (TextView)findViewById(R.id.myReqDettv9);
		buildingType				= (TextView)findViewById(R.id.myReqDettv19);
		
		areaTxt						= (TextView)findViewById(R.id.myReqDettv10);
		area						= (TextView)findViewById(R.id.myReqDettv20);
		
		priceBudgetTxt				= (TextView)findViewById(R.id.myReqDettv21);
		priceBudget					= (TextView)findViewById(R.id.myReqDettv27);
		
		myReqDetBrokerIdTv			= (TextView) findViewById(R.id.myReqDetBrokerIdTv);
		myReqDetBrokerIdValTv		= (TextView) findViewById(R.id.myReqDetIdValTv);
		
		myReqDetBrokerNameTv		= (TextView) findViewById(R.id.myReqDetBrokerNameTv);
		myReqDetNameValTv			= (TextView) findViewById(R.id.myReqDetNameValTv);
		
		myReqDetBrokerCompanyTv		= (TextView) findViewById(R.id.myReqDetBrokerCompanyTv);
		myReqDetCompanyValTv		= (TextView) findViewById(R.id.myReqDetCompanyValTv);
		
		myReqDetBrokerAddressTv		= (TextView) findViewById(R.id.myReqDetBrokerAddressTv);
		myReqDetAddressValTv		= (TextView) findViewById(R.id.myReqDetAddressValTv);
		
		myReqDetPhoneNoTv			= (TextView) findViewById(R.id.myReqDetPhoneNoTv);
		myReqDetPhoneNoValTv		= (TextView) findViewById(R.id.myReqDetPhoneNoValTv);
		
		myReqDetEmailTv				= (TextView) findViewById(R.id.myReqDetEmailTv);
		myReqDetEmailValTv			= (TextView) findViewById(R.id.myReqDetEmailValTv);
		
		//redirect to send email screen 
		myReqDetSendSmsBtn 			= (Button) findViewById(R.id.myReqDetSendSmsBtn);
		myReqDetSendSmsBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("propertyid", requirementIdIntent.getString("requirementid"));
				
				EmailSmsSendActivity emailSmsSendActivity = new EmailSmsSendActivity();
				emailSmsSendActivity.setArguments(bundle);
				((MainFragmentActivity)getActivity()).redirectScreen(emailSmsSendActivity);
			}
		});
		//END
		
		//Back button on click back to the  MyRequirement List or SearchRequirementList 
		backButton = (Button)findViewById(R.id.myReqDetBackBtn);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});
		//END
		
		//web service for display the full detail of the selected Requirement Detail 
		new WebserviceClient(MyRequirementDetailActivity.this, requirementDetailService).execute(userSessionManager.getSessionValue(Constant.Login.USER_ID),requirementIdIntent.getString("requirementid"));
		}
		//END

	@Override
	public void processResponse(Object response) {
		JSONArray jsonArry=(JSONArray)response;
		try {
			if (response != null && !((JSONObject)jsonArry.get(0)).has(Constant.MyRequirement.API_STATUS)) {
				JSONObject object=(JSONObject) jsonArry.getJSONObject(0);
				String status;
				if(object.getString(Constant.AddProperty.REQ_STATUS).equals("1")) {
            			status = "(Active)";
            		} else {
            			status = "(InActive)";
            		}
            		myReqDetSubHeaderTv.setText(object.getString(Constant.MyRequirement.PROPERTY_TYPE)+" - "+object.getString(Constant.MyRequirement.PROPERTY_SUB_TYPE)+" For "+object.getString(Constant.MyRequirement.PURPOSE)+" Purpose "+status);
            		
            		//Set image from url
	        		 if(object.getString(Constant.MyRequirementDetail.LOGO_ENCODED) != null)
	     			     Picasso.with(getActivity().getApplicationContext()).load(object.getString(Constant.MyRequirementDetail.LOGO_ENCODED)).placeholder(R.drawable.no_image).into(myReqDetPropPhotoImg);
	     			 else
	     				Picasso.with(getActivity().getApplicationContext()).load(object.getString(Constant.MyRequirementDetail.PHOTO_ENCODED)).placeholder(R.drawable.no_image).into(myReqDetPropPhotoImg);
            		 
            		brokerId = object.getString(Constant.AddProperty.BROKER_ID);
            		
            		requirementID.setText("PVOR "+object.getString(Constant.MyRequirement.REQUIREMENT_ID));
            		requirementIDTxt.setMinLines(requirementID.getLineCount());
            		requirementID.setMinLines(requirementID.getLineCount());

            		preferredLocations.setText(object.getString(Constant.MyRequirementDetail.ALL_LOCATIONNAME));
            		preferredLocationsTxt.setMinLines(preferredLocations.getLineCount());
            		preferredLocations.setMinLines(preferredLocations.getLineCount());
            		//END
            		
            		dateAdded.setText(ConvertDateFormat.convertDateFormat(object.getString(Constant.MyRequirement.DT_ADDED)));
            		dateAddedTxt.setMinLines(dateAdded.getLineCount());
            		dateAdded.setMinLines(dateAdded.getLineCount());
            		
            		dateUpdate.setText(ConvertDateFormat.convertDateFormat(object.getString(Constant.MyRequirement.DT_UPDATE)));
            		dateUpdateTxt.setMinLines(dateUpdate.getLineCount());
            		dateUpdate.setMinLines(dateUpdate.getLineCount());
            		
            		if(object.getString("propertytype").equals("Shop")) {
            			bed.setVisibility(View.GONE);
            			bedTxt.setVisibility(View.GONE);
            			purpose.setVisibility(View.GONE);
            			purposeTxt.setVisibility(View.GONE);
            		} else if(object.getString("propertytype").equals("Plot")) {
            			bed.setVisibility(View.GONE);
            			bedTxt.setVisibility(View.GONE);
            		} else if(object.getString("propertytype").equals("Farm House(Bunglow)")) {
            			bed.setVisibility(View.GONE);
            			bedTxt.setVisibility(View.GONE);
            		} else {
            			bed.setVisibility(View.VISIBLE);
            			bedTxt.setVisibility(View.VISIBLE);
            			purpose.setVisibility(View.VISIBLE);
            			purposeTxt.setVisibility(View.VISIBLE);
            			bed.setText(object.getString(Constant.MyRequirement.MIN_BED)+"-"+object.getString(Constant.MyRequirement.MAX_BED));
            			bedTxt.setMinLines(bed.getLineCount());
            			bed.setMinLines(bed.getLineCount());
            		}
            			
        			furnishOption.setText(SpinnerItem.getFurnishOptionListKey(object.getInt(Constant.MyRequirement.FURNISH)));
        			furnishOptionTxt.setMinLines(furnishOption.getLineCount());
        			furnishOption.setMinLines(furnishOption.getLineCount());
            	
            		if(object.getString(Constant.MyRequirement.ST_PURPOSE).equals("")) {
            			purpose.setText("Not define");
            			purposeTxt.setMinLines(purpose.getLineCount());
            			purpose.setMinLines(purpose.getLineCount());
            		} else {
            			purpose.setText(object.getString(Constant.MyRequirement.ST_PURPOSE));
            			purposeTxt.setMinLines(purpose.getLineCount());
            			purpose.setMinLines(purpose.getLineCount());
            		} if(object.getString("propertytype").equals("Plot")) {
            			floor.setVisibility(View.GONE);
            			floorTxt.setVisibility(View.GONE);
            		} else if(object.getString("propertytype").equals("Bunglow")) {
            			floor.setVisibility(View.GONE);
            			floorTxt.setVisibility(View.GONE);
            		} else {
            			floor.setVisibility(View.VISIBLE);
            			floorTxt.setVisibility(View.VISIBLE);
            			floor.setText(SpinnerItem.getAddPropFloorListKey(object.getString(Constant.MyRequirement.MIN_FLOOR))+"-"+SpinnerItem.getAddPropFloorListKey(object.getString(Constant.MyRequirement.MAX_FLOOR)));
	            		floorTxt.setMinLines(floor.getLineCount());
	            		floor.setMinLines(floor.getLineCount());
            		}
            		
        			buildingType.setText(SpinnerItem.getBuildingTypeListKey(object.getString(Constant.MyRequirement.RISE)));
        			buildingTypeTxt.setMinLines(buildingType.getLineCount());
        			buildingType.setMinLines(buildingType.getLineCount());
            		
            		area.setText(object.getString(Constant.MyRequirement.MIN_SQFOOT)+" Sq -  "+object.getString(Constant.MyRequirement.MAX_SQFOOT)+"Sq Foot");
            		areaTxt.setMinLines(area.getLineCount());
            		area.setMinLines(area.getLineCount());
            		
            		if(object.getString(Constant.MyRequirement.PURPOSE).equals("Sale")) {
            			priceBudget.setText(toIndianRupessFormat(Double.parseDouble(object.getString(Constant.MyRequirement.MIN_PRICE)))+"("+NumberToWord.numberToWord(object.getString(Constant.MyRequirement.MIN_PRICE))+") TO "+toIndianRupessFormat(Double.parseDouble(object.getString(Constant.MyRequirement.MAX_PRICE)))+"("+NumberToWord.numberToWord(object.getString(Constant.MyRequirement.MAX_PRICE))+")");
	            		priceBudgetTxt.setMinLines(priceBudget.getLineCount());
	            		priceBudget.setMinLines(priceBudget.getLineCount());
            		} else {
            			priceBudget.setText(toIndianRupessFormat(Double.parseDouble(object.getString(Constant.MyRequirement.MIN_RENT)))+"("+NumberToWord.numberToWord(object.getString(Constant.MyRequirement.MIN_RENT))+") TO "+toIndianRupessFormat(Double.parseDouble(object.getString(Constant.MyRequirement.MAX_RENT)))+"("+NumberToWord.numberToWord(object.getString(Constant.MyRequirement.MAX_RENT))+")");
	            		priceBudgetTxt.setMinLines(priceBudget.getLineCount());
	            		priceBudget.setMinLines(priceBudget.getLineCount());
            		}
            		showBrokerDetail();
				} else {
					Toast.makeText(getActivity(), ((JSONObject)jsonArry.get(0)).getString(Constant.MyRequirement.API_MESSAGE), Toast.LENGTH_LONG).show();
					((MainFragmentActivity)getActivity()).onBackPressed();
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressLint("NewApi")
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    this.getActivity().getActionBar().setTitle("Requirement Detail");
	}
	
	//Display the detail of broker 
	private void showBrokerDetail() {
		//view Profile Web service 
		ViewProfileService viewProfileService = new ViewProfileService();
		WebserviceClient viewProfile = new WebserviceClient(MyRequirementDetailActivity.this, viewProfileService);
		viewProfile.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null && !((JSONObject)jsonArray.get(0)).has(Constant.MyRequirement.API_STATUS)) {
						JSONObject jsonObject = jsonArray.getJSONObject(0);
						
						//Broker id
						myReqDetBrokerIdValTv.setText(brokerId);
						myReqDetBrokerIdTv.setMinLines(myReqDetBrokerIdValTv.getLineCount());
						myReqDetBrokerIdValTv.setMinLines(myReqDetBrokerIdValTv.getLineCount());
						
						//Name
						myReqDetNameValTv.setText(jsonObject.getString(Constant.ViewProfile.FIRST_NAME)+" "+jsonObject.getString(Constant.ViewProfile.LAST_NAME));
						myReqDetBrokerNameTv.setMinLines(myReqDetNameValTv.getLineCount());
						myReqDetNameValTv.setMinLines(myReqDetNameValTv.getLineCount());
						
						//Company
						myReqDetCompanyValTv.setText(jsonObject.getString(Constant.ViewProfile.COMPANY_NAME));
						myReqDetBrokerCompanyTv.setMinLines(myReqDetCompanyValTv.getLineCount());
						myReqDetCompanyValTv.setMinLines(myReqDetCompanyValTv.getLineCount());
						
						//Address
						myReqDetAddressValTv.setText(jsonObject.getString(Constant.ViewProfile.ADDRESS));
						myReqDetBrokerAddressTv.setMinLines(myReqDetAddressValTv.getLineCount());
						myReqDetAddressValTv.setMinLines(myReqDetAddressValTv.getLineCount());
						
						//Phone number
						myReqDetPhoneNoValTv.setText(jsonObject.getString(Constant.ViewProfile.PHONE_M));
						myReqDetPhoneNoTv.setMinLines(myReqDetPhoneNoValTv.getLineCount());
						myReqDetPhoneNoValTv.setMinLines(myReqDetPhoneNoValTv.getLineCount());
						
						//Email
						myReqDetEmailValTv.setText(jsonObject.getString(Constant.ViewProfile.EMAIL));
						myReqDetEmailTv.setMinLines(myReqDetEmailValTv.getLineCount());
						myReqDetEmailValTv.setMinLines(myReqDetEmailValTv.getLineCount());
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
