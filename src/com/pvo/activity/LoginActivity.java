package com.pvo.activity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.prototype.PVOAction;
import com.pvo.prototype.PVOActivity;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.LoginService;
import com.pvo.util.Constant;

//this is used for Login,in this Broker Enter the mobile number and Password 
public class LoginActivity extends PVOActivity {

	private Button loginBtn;
    private EditText mobileText;
    private EditText passwordText;
    private TextView registerTextview;
    private TextView passwordTextview;
    private LoginService loginService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//This Line is used to hide the keyboard
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		mobileText = (EditText)findViewById(R.id.mobileNumberEditBox);
		passwordText = (EditText)findViewById(R.id.passwordEditBox);
		mobileText.setText("9420088175");//Temporary set 
		loginService = new LoginService();
		
		//Login Button click event
		loginBtn=(Button)findViewById(R.id.loginButton);
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mobileText.length() == 0)
					mobileText.setError("Enter Mobile Number");
				if (passwordText.length() == 0) 
					passwordText.setError("Enter Password");
				
				if (mobileText.length() > 0 && passwordText.length() > 0) {
					loginBtn.setEnabled(false);
					new WebserviceClient((PVOAction)LoginActivity.this,loginService).execute(mobileText.getText().toString(), passwordText.getText().toString());
				}
			}
		});
		
		//Register Button click event
		registerTextview=(TextView)findViewById(R.id.registerText_login);
		registerTextview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
			}
		});
		
		//Forgot Password click Event
		passwordTextview=(TextView)findViewById(R.id.passwordText_login);
		passwordTextview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
			}
		});
	}

	@Override
	public void processResponse(Object res) {
		JSONObject response = (JSONObject) res;
		loginBtn.setEnabled(true);
		if (response != null && response.length() > 0) {
			try {
				if (String.valueOf(response.get(Constant.Login.API_STATUS)).equals("1") && response.has(Constant.Login.USER_ID))  {
					Toast.makeText(getApplicationContext(),String.valueOf(response.get(Constant.Login.API_MESSAGE)), Toast.LENGTH_LONG).show();
					
					Intent intent=new Intent(getApplicationContext(),SmsVerificationActivity.class);
					intent.putExtra("From","Login");
					intent.putExtra(Constant.Login.SMS_CODE,response.getString(Constant.Login.SMS_CODE));
					intent.putExtra(Constant.Login.USER_ID,response.getString(Constant.Login.USER_ID));
					intent.putExtra(Constant.Login.LOGIN_FIREST,response.getString(Constant.Login.LOGIN_FIREST));
					intent.putExtra(Constant.Login.FIRST_NAME,response.getString(Constant.Login.FIRST_NAME));
					intent.putExtra(Constant.Login.LAST_NAME,response.getString(Constant.Login.LAST_NAME));
					intent.putExtra(Constant.Login.COMPANY_NAME,response.getString(Constant.Login.COMPANY_NAME));
					intent.putExtra(Constant.Login.PHONE_NUMBER,response.getString(Constant.Login.PHONE_NUMBER));
					intent.putExtra(Constant.Login.EMAIL,response.getString(Constant.Login.EMAIL));
					intent.putExtra(Constant.Login.STATE_ID,response.getString(Constant.Login.STATE_ID));
					intent.putExtra(Constant.Login.CITY_ID,response.getString(Constant.Login.CITY_ID));
					intent.putExtra(Constant.Login.DISTRICT_ID,response.getString(Constant.Login.DISTRICT_ID));
					intent.putExtra(Constant.Login.MOBILE_NO,response.getString(Constant.Login.PHONE_NUMBER));
					intent.putExtra(Constant.Login.AREA_DEALS_IN_TEXT,response.getString(Constant.Login.AREA_DEALS_IN_TEXT));
					intent.putExtra(Constant.Login.AREA_DEALS_IN,response.getString(Constant.Login.AREA_DEALS_IN));
					intent.putExtra(Constant.Login.COUNTRY_NAME,response.getString(Constant.Login.COUNTRY_NAME));
					intent.putExtra(Constant.Login.STATE_NAME,response.getString(Constant.Login.STATE_NAME));
					intent.putExtra(Constant.Login.CITY_NAME,response.getString(Constant.Login.CITY_NAME));
					intent.putExtra(Constant.Login.DISTRICT_NAME,response.getString(Constant.Login.DISTRICT_NAME));
					startActivity(intent);
					mobileText.setText("");
					passwordText.setText("");
				} else{
					loginBtn.setEnabled(true);	
					Toast.makeText(getApplicationContext(),String.valueOf(response.get(Constant.Login.API_MESSAGE)), Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				loginBtn.setEnabled(true);
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onBackPressed() {
		//mobileText.setText("");
		//passwordText.setText("");
		//finish();
		startActivity(new Intent(getApplicationContext(), DashboardNewActivity.class));
	}
}
