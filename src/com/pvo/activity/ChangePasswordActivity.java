package com.pvo.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.ChangePasswordService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

//ChangePasswordActivity is Used To change current Password
public class ChangePasswordActivity extends PVOFragment {
	
    private Button savePwdButton;
    private EditText currentPwdBox;
    private EditText newPwdBox;
    private EditText confrimPwdBox;
    private TextView changePWDOldpwdTV;
    private TextView changePWDNewpwdTV;
    private TextView changePWDConfrimpwdTV;
    private Button changePasswordCancelButton;
	private ChangePasswordService changePasswordService;
	private UserSessionManager userSessionManager;

	//set layout Content View
	public ChangePasswordActivity() {
		setContentView(R.layout.activity_change_password);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		changePasswordService = new ChangePasswordService();
		userSessionManager = new UserSessionManager(getActivity().getApplicationContext());
		currentPwdBox 			= (EditText) findViewById(R.id.changePWDOldpwdEditBox);
		newPwdBox 				= (EditText) findViewById(R.id.changePWDNewpwdEditBox);
		confrimPwdBox 			= (EditText) findViewById(R.id.changePWDConfrimpwdEditBox);
		changePWDOldpwdTV 		= (TextView) findViewById(R.id.changePWDOldpwdTV);
		changePWDNewpwdTV 		= (TextView) findViewById(R.id.changePWDNewpwdTV);
		changePWDConfrimpwdTV 	= (TextView) findViewById(R.id.changePWDConfrimpwdTV);
		changePWDOldpwdTV.setText(Html.fromHtml("Current Password"+"<sup><font size=5 color=red>*</font></sup>"));
		changePWDNewpwdTV.setText(Html.fromHtml("New Password"+"<sup><font size=5 color=red>*</font></sup>"));
		changePWDConfrimpwdTV.setText(Html.fromHtml("Confrim Password"+"<sup><font size=5 color=red>*</font></sup>"));

		//on click of cancel button goto the previous fragment
		changePasswordCancelButton = (Button) findViewById(R.id.changePasswordCancelButton);
		changePasswordCancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});
		
		//on click of save call the change password web service and change password 
		savePwdButton = (Button) findViewById(R.id.changePwdSaveBotton);
		savePwdButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String newpwd = newPwdBox.getText().toString();
				String confrinPwd = confrimPwdBox.getText().toString();

				if (currentPwdBox.length() == 0) 
					currentPwdBox.setError("Enter Current Password");
				if (newPwdBox.length() == 0) 
					newPwdBox.setError("Enter New Password");
				if (confrimPwdBox.length() == 0) 
					confrimPwdBox.setError("Enter Confrim Password");
				if (!newpwd.equals(confrinPwd)) 
					confrimPwdBox.setError("Password Does not match !");

				if (currentPwdBox.length() > 0 && newPwdBox.length() > 0 && confrimPwdBox.length() > 0 && newpwd.equals(confrinPwd)) {
					savePwdButton.setEnabled(false);
					new WebserviceClient(ChangePasswordActivity.this,changePasswordService).execute(userSessionManager.getSessionValue(Constant.Login.USER_ID),currentPwdBox.getText().toString(),newPwdBox.getText().toString());
				}
			}
	   });
	}

	@Override
	public void processResponse(Object res) {
		JSONObject response = (JSONObject) res;
		savePwdButton.setEnabled(true);
		if (response != null) {
			try {
				if (String.valueOf(response.get(Constant.Registration.API_STATUS)).equals("1")) {
					Toast.makeText(getActivity(),String.valueOf(response.get(Constant.Registration.API_MESSAGE)),Toast.LENGTH_LONG).show();
					((MainFragmentActivity)getActivity()).onBackPressed();
				} else {
					Toast.makeText(getActivity(),String.valueOf(response.get(Constant.Registration.API_MESSAGE)),Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} 
	}
}
