package com.pvo.activity;

import java.text.ParseException;

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
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.pvo.util.ConvertDateFormat;
import com.squareup.picasso.Picasso;


/**
 * This is used for display the detail of the current Login user full detail
 * */
public class ViewProfileActivity extends PVOFragment {
	private ImageView viewprofileImageView;
	private TextView brokerId;
	private TextView name;
	private TextView cmpName;
	private TextView address;
	private TextView phoneNo;
	private TextView website;
	private TextView email;
	private TextView doj;
	private TextView postCode;
	private TextView scince;
	private TextView brokerIdTv;
	private TextView nameTv;
	private TextView cmpNameTv;
	private TextView addressTv;
	private TextView phoneNoTv;
	private TextView webSiteTv;
	private TextView emailTv;
	private TextView dateOfJoinTv;
	private TextView postcodeTv;
	private TextView scinceTv;
	private TextView phoneOfficeTv;
	private TextView phoneOfficeVal;
	private TextView totalProperty;
	private TextView totalPropertyValue;
	private TextView smsCredit;
	private TextView smsCreditValue;
	private TextView smsBalance;
	private TextView smsBalanceValue;
	private Button back;
	private ViewProfileService viewProfileService;
	private UserSessionManager userSessionManager;
	
	/** Set Layout Content View **/
	public ViewProfileActivity() {
		setContentView(R.layout.activity_view_profile);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//used to handle force close 
		//Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(getActivity()));
		
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		viewProfileService		= new ViewProfileService();
		userSessionManager		= new UserSessionManager(getActivity().getApplicationContext());
		brokerId				= (TextView)findViewById(R.id.brokerIdVal);
		name					= (TextView)findViewById(R.id.nameVal);
		cmpName					= (TextView)findViewById(R.id.cmpVal);
		address					= (TextView)findViewById(R.id.addressVal);
		phoneNo					= (TextView)findViewById(R.id.phoneVal);
		website					= (TextView)findViewById(R.id.websitVal);
		email					= (TextView)findViewById(R.id.emailVal);
		doj						= (TextView)findViewById(R.id.dojVal);
		postCode				= (TextView)findViewById(R.id.postCodeVal);
		scince					= (TextView)findViewById(R.id.scinceVal);
		brokerIdTv				= (TextView)findViewById(R.id.brokerIdTv);
		nameTv					= (TextView)findViewById(R.id.nameTv);
		cmpNameTv				= (TextView)findViewById(R.id.cmpNameTv);
		addressTv				= (TextView)findViewById(R.id.addressTv);
		phoneNoTv				= (TextView)findViewById(R.id.PhoneNoTv);
		webSiteTv				= (TextView)findViewById(R.id.webSitTv);
		emailTv					= (TextView)findViewById(R.id.emailTv);
		dateOfJoinTv			= (TextView)findViewById(R.id.dojTv);
		postcodeTv				= (TextView)findViewById(R.id.postcodeTv);
		scinceTv				= (TextView)findViewById(R.id.scince);
		phoneOfficeTv			= (TextView)findViewById(R.id.PhoneOTv);
		phoneOfficeVal			= (TextView)findViewById(R.id.phoneOVal);
		totalProperty 			= (TextView) findViewById(R.id.totalProperty);
		totalPropertyValue 		= (TextView) findViewById(R.id.totalPropertyVal);
		smsBalance 				= (TextView) findViewById(R.id.smsBalance);
		smsBalanceValue 		= (TextView) findViewById(R.id.smsBalanceVal);
		smsCredit 				= (TextView) findViewById(R.id.smsCredit);
		smsCreditValue 			= (TextView) findViewById(R.id.smsCreditVal);		
		
		
		/** on back button press goto the previous fragment **/
		back=(Button)findViewById(R.id.viewProfileBackBtn);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});
		/** END **/
		
		/** call View profile web service **/
		new WebserviceClient(ViewProfileActivity.this, viewProfileService).execute(userSessionManager.getSessionValue(Constant.Login.USER_ID));
	}
	
	@Override
	public void processResponse(Object res) {
		JSONArray jsonArray=(JSONArray)res;
		if (jsonArray != null) {
			try {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject response = jsonArray.getJSONObject(i);
					/** Set Image from response **/
					viewprofileImageView = (ImageView)findViewById(R.id.viewProfileImageView);
					if(response.getString(Constant.ViewProfile.LOGO_ENCODED).equals(null)) {
						Picasso.with(getActivity().getApplicationContext()).load(response.getString(Constant.ViewProfile.LOGO_ENCODED)).placeholder(R.drawable.no_image).into(viewprofileImageView);
					} else {
						Picasso.with(getActivity().getApplicationContext()).load(response.getString(Constant.ViewProfile.PHOTO_ENCODED)).placeholder(R.drawable.no_image).into(viewprofileImageView);
					}
					/** END **/

					brokerId.setText(response.getString(Constant.ViewProfile.BROKER_ID));
					brokerIdTv.setMinLines(brokerId.getLineCount());
					brokerId.setMinLines(brokerId.getLineCount());
					
					name.setText(response.getString(Constant.ViewProfile.FIRST_NAME)+ " "+ response.getString(Constant.ViewProfile.LAST_NAME));
					nameTv.setMinLines(name.getLineCount());
					name.setMinLines(name.getLineCount());
				
					cmpName.setText(response.getString(Constant.ViewProfile.COMPANY_NAME));
					cmpNameTv.setMinLines(cmpName.getLineCount());
					
					address.setText(response.getString(Constant.ViewProfile.ADDRESS));
					addressTv.setMinLines(address.getLineCount());
					address.setMinLines(address.getLineCount());
					
					phoneNo.setText(response.getString(Constant.ViewProfile.PHONE_M));
					phoneNoTv.setMinLines(phoneNo.getLineCount());
					address.setMinLines(address.getLineCount());
					
					phoneOfficeVal.setText(response.getString(Constant.ViewProfile.PHONE_O));
					phoneOfficeTv.setMinLines(phoneOfficeVal.getLineCount());
					phoneOfficeVal.setMinLines(phoneOfficeVal.getLineCount());
					
					website.setText(response.getString(Constant.ViewProfile.WEBSITE));
					webSiteTv.setMinLines(website.getLineCount());
					website.setMinLines(website.getLineCount());
					
					email.setText(response.getString(Constant.ViewProfile.EMAIL));
					emailTv.setMinLines(email.getLineCount());
					
					try {
						doj.setText(ConvertDateFormat.convertDateFormat(response.getString(Constant.ViewProfile.DT_JOIN)));
						dateOfJoinTv.setMinLines(doj.getLineCount());
						doj.setMinLines(doj.getLineCount());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					postCode.setText(response.getString(Constant.ViewProfile.POST_CODE));
					postcodeTv.setMinLines(postCode.getLineCount());
					postCode.setMinLines(postCode.getLineCount());
					
					scince.setText(response.getString(Constant.ViewProfile.SCINCE));
					scinceTv.setMinLines(scince.getLineCount());
					scince.setMinLines(scince.getLineCount());
					
					totalPropertyValue.setText(response.getString(Constant.ViewProfile.TOTAL_PROPERTY));
					totalProperty.setMinLines(totalPropertyValue.getLineCount());
					
					smsBalanceValue.setText(response.getString(Constant.ViewProfile.SMS_BALANCE));
					smsBalance.setMinLines(smsBalanceValue.getLineCount());

					smsCreditValue.setText(response.getString(Constant.ViewProfile.SMS_CREDIT));
					smsCredit.setMinLines(smsCreditValue.getLineCount());
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}			
		} 
	}
}
