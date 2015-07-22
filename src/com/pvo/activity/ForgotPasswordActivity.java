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
import android.widget.Toast;

import com.pvo.prototype.PVOAction;
import com.pvo.prototype.PVOActivity;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.ForgotPasswordService;
import com.pvo.util.Constant;


//Forgot Password is used to send new password into enter mobile number
//enter mobile number pass into the webservice

public class ForgotPasswordActivity  extends PVOActivity{

	private Button forgotPwdBtn;
	private EditText mobileNoEditText;
	private ForgotPasswordService forgotPasswordservice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
		//This Line is used to hide the keyboard
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		forgotPasswordservice = new ForgotPasswordService();
		mobileNoEditText = (EditText) findViewById(R.id.mobileNumberEditBoxPwd);
		
		forgotPwdBtn = (Button) findViewById(R.id.pwdBotton);
		forgotPwdBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (mobileNoEditText.length() == 0) 
					mobileNoEditText.setError("Enter Mobile Number");
				
				if (mobileNoEditText.length() > 0) {
					forgotPwdBtn.setEnabled(false);
					new WebserviceClient((PVOAction)ForgotPasswordActivity.this,forgotPasswordservice).execute(mobileNoEditText.getText().toString());
				}
			}
		});
	}

	@Override
	public void processResponse(Object res) {
		JSONObject response = (JSONObject) res;
		forgotPwdBtn.setEnabled(true);
		if (response != null && response.length() > 0) {
			try {
				if (String.valueOf(response.get(Constant.ForgotPassword.API_STATUS)).equals("1")) {
					Toast.makeText(getApplicationContext(),String.valueOf(response.get(Constant.Login.API_MESSAGE)),Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),LoginActivity.class));
					mobileNoEditText.setText("");
				} else {
					Toast.makeText(getApplicationContext(),String.valueOf(response.get(Constant.ForgotPassword.API_MESSAGE)),Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} 
	}

	@Override
	public void onBackPressed() {
		finish();
		/*Intent intent = new Intent(Intent.ACTION_MAIN);
	    intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);*/
	}
}
