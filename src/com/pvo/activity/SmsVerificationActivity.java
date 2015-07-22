package com.pvo.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pvo.prototype.PVOAction;
import com.pvo.prototype.PVOActivity;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.RegisterGsmIDService;
import com.pvo.user.service.SmsVerificatonCodeService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;


/**
 * This is used for verify the sms code 
 **/
public class SmsVerificationActivity extends PVOActivity {
  
	private EditText smsCode;
    private Button verifyBtton;
    private Button btnSmsVeriLoginAgain;
    private SmsVerificatonCodeService smsVerificatonCodeService;
    private RegisterGsmIDService registerGsmService;
    private UserSessionManager userSessionManager;
    private Intent loginDataIntent;
    private String registerGCMId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms_verification);
		
		registerGsmService 			= new RegisterGsmIDService();
		SharedPreferences prefs 	= this.getSharedPreferences(SplashScreenActivity.PVOREGID, Context.MODE_PRIVATE);
		registerGCMId 				= prefs.getString(SplashScreenActivity.PROPERTY_REG_ID,"");
		
		//This Line is used to hide the keyboard
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		smsVerificatonCodeService	= new SmsVerificatonCodeService();
		userSessionManager			= new UserSessionManager(getApplicationContext());
		loginDataIntent 			= getIntent();
		
		smsCode						= (EditText)findViewById(R.id.SmsVerificationEditBox);
		smsCode.setText(loginDataIntent.getStringExtra(Constant.Login.SMS_CODE));
	
		/** call verify web service **/
		verifyBtton=(Button)findViewById(R.id.smsVrificationBotton);
		verifyBtton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(smsCode.length() == 0)
					smsCode.setError("Enter Verification Code");
				
				if(smsCode.length() > 0) {
					verifyBtton.setEnabled(false);
					new WebserviceClient((PVOAction)SmsVerificationActivity.this,smsVerificatonCodeService).execute(loginDataIntent.getStringExtra(Constant.Login.USER_ID),smsCode.getText().toString());
				}
			}
		});
		/** END **/
		
		btnSmsVeriLoginAgain = (Button) findViewById(R.id.btnSmsVeriLoginAgain);
		btnSmsVeriLoginAgain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getApplicationContext(), LoginActivity.class));
			}
		});
	}

	@Override
	public void processResponse(Object res) {
		JSONObject response = (JSONObject) res;
		if(response != null && response.length() > 0) {
			try {
				if (String.valueOf(response.get(Constant.Login.API_STATUS)).equals("1")) {
						Toast.makeText(getApplicationContext(),String.valueOf(response.get(Constant.Login.API_MESSAGE)), Toast.LENGTH_LONG).show();
						userSessionManager.userLoginSession(
								String.valueOf(loginDataIntent.getStringExtra(Constant.Login.USER_ID)),
								String.valueOf(loginDataIntent.getStringExtra(Constant.Login.LOGIN_FIREST)),
								String.valueOf(loginDataIntent.getStringExtra(Constant.Login.FIRST_NAME)),
								String.valueOf(loginDataIntent.getStringExtra(Constant.Login.LAST_NAME)),
								String.valueOf(loginDataIntent.getStringExtra(Constant.Login.COMPANY_NAME)),
								String.valueOf(loginDataIntent.getStringExtra(Constant.Login.PHONE_NUMBER)),
								String.valueOf(loginDataIntent.getStringExtra(Constant.Login.EMAIL)),
								String.valueOf(loginDataIntent.getStringExtra(Constant.Login.STATE_ID)),
								String.valueOf(loginDataIntent.getStringExtra(Constant.Login.CITY_ID)),
								String.valueOf(loginDataIntent.getStringExtra(Constant.Login.DISTRICT_ID)),
								String.valueOf(loginDataIntent.getStringExtra(Constant.Login.AREA_DEALS_IN_TEXT)),
								String.valueOf(loginDataIntent.getStringExtra(Constant.Login.AREA_DEALS_IN)),
								String.valueOf(loginDataIntent.getStringExtra(Constant.Login.COUNTRY_NAME)),
								String.valueOf(loginDataIntent.getStringExtra(Constant.Login.STATE_NAME)),
								String.valueOf(loginDataIntent.getStringExtra(Constant.Login.CITY_NAME)),
								String.valueOf(loginDataIntent.getStringExtra(Constant.Login.DISTRICT_NAME))
								);
						     storeRegisterGCMId();
						     smsCode.setText("");
				} else {
					Toast.makeText(getApplicationContext(),String.valueOf(response.get(Constant.Login.API_MESSAGE)), Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} 
	}

	/** OnBack Press close the Application **/
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
	    intent.addCategory(Intent.CATEGORY_HOME);
		//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
		
	@Override
	protected void onDestroy() {
		//super.onDestroy();
		System.exit(0);
	}
	/** END **/
	
	/** Call web service to store GCM Id after verification **/
	public void storeRegisterGCMId(){
		Toast.makeText(getApplicationContext(), "registerGCMId==> "+registerGCMId , Toast.LENGTH_LONG).show();
		WebserviceClient registerGCMIdWebserviceClient = new WebserviceClient((PVOAction)SmsVerificationActivity.this, registerGsmService);
		registerGCMIdWebserviceClient.setResponseListner(new ResponseListner() {
				@Override
				public void handleResponse(Object response) {
					JSONObject jsonObject = (JSONObject) response;
					if(jsonObject != null) {
						try {
							if (String.valueOf(jsonObject.get(Constant.RegisterGSM.API_STATUS)).equals("1")) {
								Toast.makeText(getApplicationContext(),String.valueOf(jsonObject.get(Constant.RegisterGSM.API_MESSAGE)), Toast.LENGTH_LONG).show();
								Intent mainFrgmentIntent = new Intent(getApplicationContext(), MainFragmentActivity.class);
								if(loginDataIntent.getStringExtra("From").equals("Login")) {
									mainFrgmentIntent.putExtra(Constant.Login.LOGIN_FIREST,loginDataIntent.getStringExtra(Constant.Login.LOGIN_FIREST));
								} else if(loginDataIntent.getStringExtra("From").equals("Registraion")) {
									mainFrgmentIntent.putExtra(Constant.Login.LOGIN_FIREST,"True");
								}
								startActivity(mainFrgmentIntent);
							} else {
								Toast.makeText(getApplicationContext(),String.valueOf(jsonObject.get(Constant.RegisterGSM.API_MESSAGE)), Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} 
				}
			});
			registerGCMIdWebserviceClient.execute(registerGCMId,loginDataIntent.getStringExtra(Constant.Login.MOBILE_NO));
	}
	/** END **/
}
