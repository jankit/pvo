package com.pvo.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.ViewProfileService;
import com.pvo.util.Constant;
import com.squareup.picasso.Picasso;

 //This Activity is Display the Detail of the Particular Agent from the Agent List.This Activity Run on click of First Text view Of the Agent List
public class FindAgentDetailActivity extends PVOFragment {
	
    private TextView findAgentDetBrokerIdTv;
    private TextView findAgentDetBrokerIdVal;
    
    private TextView findAgentDetBrokerNameTv;
    private TextView findAgentDetBrokerNameVal;
    
    private TextView findAgentDetCompanyNameTv;
    private TextView findAgentDetCompanyNameVal;
    
    private TextView findAgentDetAddressTv;
    private TextView findAgentDetAddressVal;

    private TextView findAgentDetPhoneNumberTv;
    private TextView findAgentDetPhoneNumberVal;
    
    private TextView findAgentDetEmailTv; 
    private TextView findAgentDetEmailVal;
    
    private TextView findAgentDetWebSiteTv;
    private TextView findAgentDetWebSiteVal;
    
    private TextView findAgentPropTypeDealsinTv;
    private TextView findAgentPropTypeValTv;
    
    private TextView findAgentDetDateOfJoinTv;
    private TextView findAgentDetDateOfJoinVal;
    
    private TextView findAgentDetPostCodeTv;
    private TextView findAgentDetPostCodeVal;
    
    private TextView findAgentDetLanguageTv;
    private TextView findAgentDetLanguageVal;
    
    private TextView findAgentDetTotalPropertyTv;
    private TextView findAgentDetTotalPropertyValue;

    private TextView findAgentDetAreaDealsInTv;
    private TextView findAgentDetAreaDealsInVal;
    
    private TextView facebookTv;
    private TextView facebookVal;
    
    private TextView linkedInTv;
    private TextView linkedInVal;
    
    private TextView affiliatedWithTv;
    private TextView affiliatedWithVal;
    
    private TextView businessPageTv;
    private TextView businessPageVal;
    
    private TextView smsCreditTv;
    private TextView smsCreditVal;
    
    private TextView smsBalanceTv;
    private TextView smsBalanceVal;
    
    private Button back;
    private Bundle bundle;
    
    private ImageView findAgentDeLogoImg;
    private ImageView findAgentDePhotoImg;
    
    private ViewProfileService viewProfileService;
    private Button btnAgentDetProperty;
    private Button btnAgentDetRequirement;
    private String firstname;
    
	
	//set layout content view 
	public FindAgentDetailActivity() {
		setContentView(R.layout.activity_findagent_detail);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		bundle = getArguments();
		viewProfileService = new ViewProfileService();
		
		//Photo & Logo image view
		findAgentDeLogoImg = (ImageView) findViewById(R.id.findAgentDeLogoImg);
	    findAgentDePhotoImg = (ImageView) findViewById(R.id.findAgentDetPhotoImg);
	   
		//Broker Id
		findAgentDetBrokerIdTv = (TextView) findViewById(R.id.findAgenttv1);
		findAgentDetBrokerIdVal = (TextView) findViewById(R.id.findAgenttv11);
		
		//name
		findAgentDetBrokerNameTv = (TextView) findViewById(R.id.findAgenttv2);
		findAgentDetBrokerNameVal = (TextView) findViewById(R.id.findAgenttv12);
		
		//Company name
		findAgentDetCompanyNameTv = (TextView) findViewById(R.id.findAgenttv3);
		findAgentDetCompanyNameVal = (TextView) findViewById(R.id.findAgenttv13);
		
		//Address
		findAgentDetAddressTv = (TextView) findViewById(R.id.findAgenttv4);
		findAgentDetAddressVal = (TextView) findViewById(R.id.findAgenttv14);
		
		//Phone number
		findAgentDetPhoneNumberTv = (TextView) findViewById(R.id.findAgenttv5);
		findAgentDetPhoneNumberVal = (TextView) findViewById(R.id.findAgenttv15);
		
		//Email
		findAgentDetEmailTv = (TextView) findViewById(R.id.findAgenttv6);
		findAgentDetEmailVal = (TextView) findViewById(R.id.findAgenttv16);
		
		//Website
		findAgentDetWebSiteTv = (TextView) findViewById(R.id.findAgenttv7);
		findAgentDetWebSiteVal = (TextView) findViewById(R.id.findAgenttv17);
		
		//Property type deals in
		findAgentPropTypeDealsinTv = (TextView) findViewById(R.id.findAgentPropTypeDealsinTv);
		findAgentPropTypeValTv = (TextView) findViewById(R.id.findAgentPropTypeValTv);
		
		//date of Join
		findAgentDetDateOfJoinTv = (TextView) findViewById(R.id.findAgenttv8);
		findAgentDetDateOfJoinVal = (TextView) findViewById(R.id.findAgenttv18);
		
		//Post code
		findAgentDetPostCodeTv = (TextView) findViewById(R.id.findAgenttv9);
		findAgentDetPostCodeVal = (TextView) findViewById(R.id.findAgenttv19);
		
		//Language Known
		findAgentDetLanguageTv = (TextView) findViewById(R.id.findAgenttv10);
		findAgentDetLanguageVal = (TextView) findViewById(R.id.findAgenttv20);
		
		//Total Property
		findAgentDetTotalPropertyTv = (TextView) findViewById(R.id.totalProperty);
		findAgentDetTotalPropertyValue = (TextView) findViewById(R.id.totalPropertyVal);
		
		//area Deals in
		findAgentDetAreaDealsInTv = (TextView) findViewById(R.id.areaDealsIn);
		findAgentDetAreaDealsInVal = (TextView) findViewById(R.id.areaDealsInVal);
		
		//Facebook
		facebookTv = (TextView) findViewById(R.id.facebookTv);
		facebookVal = (TextView) findViewById(R.id.facebookVal);

		//Linked in
		linkedInTv = (TextView) findViewById(R.id.linkedInTv);
		linkedInVal = (TextView) findViewById(R.id.linkedInVal);
		    
		//affiliate with 
		affiliatedWithTv = (TextView) findViewById(R.id.affiliatedWithTv);
		affiliatedWithVal = (TextView) findViewById(R.id.affiliatedWithVal);
		    
		//Business page
		businessPageTv = (TextView) findViewById(R.id.businessPageTv);
		businessPageVal = (TextView) findViewById(R.id.businessPageVal);
		
		//SMS Credit
		smsCreditTv = (TextView) findViewById(R.id.smsCreditTv);
		smsCreditVal = (TextView) findViewById(R.id.smsCreditVal);
		
		//SMS Balance
		smsBalanceTv = (TextView) findViewById(R.id.smsBalanceTv);
	    smsBalanceVal = (TextView) findViewById(R.id.smsBalanceVal);
		
     	//Back Button to go for Agent List
		back=(Button)findViewById(R.id.findAgentDetailBackBtn);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});
		
		//See property of agent
		btnAgentDetProperty = (Button) findViewById(R.id.btnAgentDetProperty);
		btnAgentDetProperty.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Bundle prefereBrokerBundle = new  Bundle();
				prefereBrokerBundle.putString(Constant.PreferredBrokers.BROKER_ID,findAgentDetBrokerIdVal.getText().toString());
				prefereBrokerBundle.putString("Type","PrefereBroker");
				prefereBrokerBundle.putString(Constant.PreferredBrokers.FIRST_NAME,firstname);
				MyPropertyListActivity myPropertyActivity = new MyPropertyListActivity();
				myPropertyActivity.setArguments(prefereBrokerBundle);
				((MainFragmentActivity)getActivity()).redirectScreen(myPropertyActivity);
			}
		});
		
		//See requirement of agent
		btnAgentDetRequirement = (Button) findViewById(R.id.btnAgentDetRequirement);
		btnAgentDetRequirement.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Bundle prefereBrokerBundle = new  Bundle();
				prefereBrokerBundle.putString(Constant.FindAgent.BROKER_ID,findAgentDetBrokerIdVal.getText().toString());
				prefereBrokerBundle.putString("Type","Requirement");
				prefereBrokerBundle.putString(Constant.FindAgent.FIRST_NAME,firstname);
				MyRequirementListActivity myRequirementListActivity = new MyRequirementListActivity();
				myRequirementListActivity.setArguments(prefereBrokerBundle);
				((MainFragmentActivity)getActivity()).redirectScreen(myRequirementListActivity);
			}
		});
		//Call view profile web service 
		new WebserviceClient(FindAgentDetailActivity.this, viewProfileService).execute(bundle.getString(Constant.FindAgent.BROKER_ID));
	}
	
	@Override
	public void processResponse(Object response) {
		JSONArray jsonArray = (JSONArray) response;
		try {
			if(jsonArray != null) {
				for(int i = 0; i < jsonArray.length() ; i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					
					if(!jsonObject.getString(Constant.ViewProfile.LOGO).equals("null") && !jsonObject.getString(Constant.ViewProfile.LOGO).equals(""))
						Picasso.with(getActivity().getApplicationContext()).load(jsonObject.getString(Constant.ViewProfile.LOGO)).placeholder(R.drawable.no_image).into(findAgentDeLogoImg);
				    
					if(!jsonObject.getString(Constant.ViewProfile.PHOTO).equals("null") && !jsonObject.getString(Constant.ViewProfile.PHOTO).equals(""))
						Picasso.with(getActivity().getApplicationContext()).load(jsonObject.getString(Constant.ViewProfile.PHOTO)).placeholder(R.drawable.no_image).into(findAgentDePhotoImg);
					
					//Broker Id
					findAgentDetBrokerIdVal.setText(jsonObject.getString(Constant.ViewProfile.BROKER_ID));
					findAgentDetBrokerIdTv.setMinLines(findAgentDetBrokerIdVal.getLineCount());
					findAgentDetBrokerIdVal.setMinLines(findAgentDetBrokerIdVal.getLineCount());
					
					//name
					firstname = jsonObject.getString(Constant.ViewProfile.FIRST_NAME);
					findAgentDetBrokerNameVal.setText(jsonObject.getString(Constant.ViewProfile.FIRST_NAME)+" "+jsonObject.getString(Constant.ViewProfile.LAST_NAME));
					findAgentDetBrokerNameTv.setMinLines(findAgentDetBrokerNameVal.getLineCount());
					findAgentDetBrokerNameVal.setMinLines(findAgentDetBrokerNameVal.getLineCount());
					
					//Company name
					findAgentDetCompanyNameVal.setText(jsonObject.getString(Constant.ViewProfile.COMPANY_NAME));
					findAgentDetCompanyNameTv.setMinLines(findAgentDetCompanyNameVal.getLineCount());
					findAgentDetCompanyNameVal.setMinLines(findAgentDetCompanyNameVal.getLineCount());
					
					//Address
					findAgentDetAddressVal.setText(jsonObject.getString(Constant.ViewProfile.ADDRESS));
					findAgentDetAddressTv.setMinLines(findAgentDetAddressVal.getLineCount());
					findAgentDetAddressVal.setMinLines(findAgentDetAddressVal.getLineCount());
					
					//Phone number
					findAgentDetPhoneNumberVal.setText("(M)"+jsonObject.getString(Constant.ViewProfile.PHONE_M)+"\n(O)"+jsonObject.getString(Constant.ViewProfile.PHONE_O));
					findAgentDetPhoneNumberTv.setMinLines(findAgentDetPhoneNumberVal.getLineCount());
					findAgentDetPhoneNumberVal.setMinLines(findAgentDetPhoneNumberVal.getLineCount());
					
					//Email
					if(jsonObject.getString(Constant.ViewProfile.EMAIL).equals(""))
						findAgentDetEmailVal.setText(jsonObject.getString(Constant.ViewProfile.EMAIL));
					else
						findAgentDetEmailVal.setText(jsonObject.getString(Constant.ViewProfile.EMAIL));
					
					findAgentDetEmailTv.setMinHeight(findAgentDetEmailVal.getLineCount());
					findAgentDetEmailVal.setMinHeight(findAgentDetEmailVal.getLineCount());
					
					//Website
					if(jsonObject.getString(Constant.ViewProfile.WEBSITE).equals(""))
						findAgentDetWebSiteVal.setText("Not mention");
					else
						findAgentDetWebSiteVal.setText(jsonObject.getString(Constant.ViewProfile.WEBSITE));
					
					findAgentDetWebSiteTv.setMinLines(findAgentDetWebSiteVal.getLineCount());
					findAgentDetWebSiteVal.setMinLines(findAgentDetWebSiteVal.getLineCount());
					
					//Property type dealse in
					if(jsonObject.getString(Constant.ViewProfile.PROPERTY_TYPE_DEALIN).equals(""))
						findAgentPropTypeValTv.setText("Not mention");
					else
						findAgentPropTypeValTv.setText(jsonObject.getString(Constant.ViewProfile.PROPERTY_TYPE_DEALIN));
					
					findAgentPropTypeDealsinTv.setMinLines(findAgentPropTypeValTv.getLineCount());
					findAgentPropTypeValTv.setMinLines(findAgentPropTypeValTv.getLineCount());
					
					//date of Join
					if(jsonObject.getString(Constant.ViewProfile.DT_JOIN).equals("") && jsonObject.getString(Constant.ViewProfile.DT_JOIN).equalsIgnoreCase("n.a."))
						findAgentDetDateOfJoinVal.setText(jsonObject.getString(Constant.ViewProfile.DT_JOIN));
					else
						findAgentDetDateOfJoinVal.setText(jsonObject.getString(Constant.ViewProfile.DT_JOIN));
					
					findAgentDetDateOfJoinTv.setMinLines(findAgentDetDateOfJoinVal.getLineCount());
					findAgentDetDateOfJoinVal.setMinLines(findAgentDetDateOfJoinVal.getLineCount());
	
					//Post code
					if(jsonObject.getString(Constant.ViewProfile.POST_CODE).equals(""))
						findAgentDetPostCodeVal.setText("Not mention");
					else
						findAgentDetPostCodeVal.setText(jsonObject.getString(Constant.ViewProfile.POST_CODE));
					
					findAgentDetPostCodeTv.setMinLines(findAgentDetPostCodeVal.getLineCount());
					findAgentDetPostCodeVal.setMinLines(findAgentDetPostCodeVal.getLineCount());
	
					//Language Known
					if(jsonObject.getString(Constant.ViewProfile.LANGUAGE_KNOWN).equals(""))
						findAgentDetLanguageVal.setText("Not mention");
					else
						findAgentDetLanguageVal.setText(jsonObject.getString(Constant.ViewProfile.LANGUAGE_KNOWN));
					
					findAgentDetLanguageTv.setMinLines(findAgentDetLanguageVal.getLineCount());
					findAgentDetLanguageVal.setMinLines(findAgentDetLanguageVal.getLineCount());
					
					//Total Property
					if(jsonObject.getString(Constant.ViewProfile.TOTAL_PROPERTY).equals(""))
						findAgentDetTotalPropertyValue.setText("Not mention");
					else
						findAgentDetTotalPropertyValue.setText(jsonObject.getString(Constant.ViewProfile.TOTAL_PROPERTY));
					
					findAgentDetTotalPropertyTv.setMinLines(findAgentDetTotalPropertyValue.getLineCount());
					findAgentDetTotalPropertyValue.setMinLines(findAgentDetTotalPropertyValue.getLineCount());
					
					//area Deals in
					if(jsonObject.getString(Constant.ViewProfile.AREA_DEALS_IN_TEXT).equals(""))
						findAgentDetAreaDealsInVal.setText("Not mention");
					else
						findAgentDetAreaDealsInVal.setText(jsonObject.getString(Constant.ViewProfile.AREA_DEALS_IN_TEXT));
					
					findAgentDetAreaDealsInTv.setMinLines(findAgentDetAreaDealsInVal.getLineCount());
					findAgentDetAreaDealsInVal.setMinLines(findAgentDetAreaDealsInVal.getLineCount());
					
					
					//Facebook
					if(jsonObject.getString(Constant.ViewProfile.FACEBOOK).equals(""))
						facebookVal.setText("Not mention");
					else
						facebookVal.setText(jsonObject.getString(Constant.ViewProfile.FACEBOOK));
					
					facebookTv.setMinLines(facebookVal.getLineCount());
					facebookVal.setMinLines(facebookVal.getLineCount());

					//Linked in
					if(jsonObject.getString(Constant.ViewProfile.LINKEDIN).equals(""))
						linkedInVal.setText("Not mention");
					else
						linkedInVal.setText(jsonObject.getString(Constant.ViewProfile.LINKEDIN));

					linkedInTv.setMinLines(linkedInVal.getLineCount());
					linkedInVal.setMinLines(linkedInVal.getLineCount());
					    
					//affiliate with 
					if(jsonObject.getString(Constant.ViewProfile.AFFILIATED_WITH).equals(""))
						affiliatedWithVal.setText("Not mention");
					else
						affiliatedWithVal.setText(jsonObject.getString(Constant.ViewProfile.AFFILIATED_WITH));
					
					affiliatedWithTv.setMinLines(affiliatedWithVal.getLineCount());
					affiliatedWithVal.setMinLines(affiliatedWithVal.getLineCount());
					    
					//Business page
					if(jsonObject.getString(Constant.ViewProfile.BUSINESS_PAGE).equals(""))
						businessPageVal.setText("Not mention");
					else
						businessPageVal.setText(jsonObject.getString(Constant.ViewProfile.BUSINESS_PAGE));
					
					businessPageTv.setMinLines(businessPageVal.getLineCount());
					businessPageVal.setMinLines(businessPageVal.getLineCount());
					
					//SMS Credit
					if(jsonObject.getString(Constant.ViewProfile.SMS_CREDIT).equals(""))
						smsCreditVal.setText("Not mention");
					else
						smsCreditVal.setText(jsonObject.getString(Constant.ViewProfile.SMS_CREDIT));
					
					smsCreditTv.setMinLines(smsCreditVal.getLineCount());
					smsCreditVal.setMinLines(smsCreditVal.getLineCount());
					
					//SMS Balance
				    if(jsonObject.getString(Constant.ViewProfile.SMS_BALANCE).equals(""))
				    	smsBalanceVal.setText("Not mention");
					else
						smsBalanceVal.setText(jsonObject.getString(Constant.ViewProfile.SMS_BALANCE));
				    
				    smsBalanceTv.setMinLines(smsBalanceVal.getLineCount());
				    smsBalanceVal.setMinLines(smsBalanceVal.getLineCount());
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    this.getActivity().getActionBar().setTitle("Agent Detail");
	}
}
