package com.pvo.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.EmailSmsService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

//Send Email/SMS to Particular Broker 
public class EmailSmsSendActivity extends PVOFragment {
	
	private EditText nameEditBox;
	private EditText numberEditBox;
	private EditText emailIdEditBox;
	private EditText messageEditBox;
	private Button SendButton;
	private Button myPropSmsEmailBackBotton;
	private UserSessionManager userSessionManager;
	private Bundle intent;
	private EmailSmsService emailSmsService;
	
	//set Layout content View
	public EmailSmsSendActivity() {
		setContentView(R.layout.activity_email_sms);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		intent = getArguments();
		emailSmsService = new EmailSmsService();
		userSessionManager=new UserSessionManager(getActivity());
		
		nameEditBox=(EditText)findViewById(R.id.myPropSmsEmailNameEditBox);
		nameEditBox.setText(userSessionManager.getSessionValue(Constant.Login.FIRST_NAME)+" "+userSessionManager.getSessionValue(Constant.Login.LAST_NAME));
		
		numberEditBox=(EditText)findViewById(R.id.myPropSmsEmailMobileEditBox);
		numberEditBox.setText(userSessionManager.getSessionValue(Constant.Login.PHONE_NUMBER));
		
		emailIdEditBox=(EditText)findViewById(R.id.myPropSmsEmailEmailEditBox);
		emailIdEditBox.setText(userSessionManager.getSessionValue(Constant.Login.EMAIL));

		messageEditBox=(EditText)findViewById(R.id.myPropSmsEmailMessageEditBox);
		
		///On click back button goto the previous fragment
		myPropSmsEmailBackBotton = (Button) findViewById(R.id.myPropSmsEmailBackBotton);
		myPropSmsEmailBackBotton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});
		
		// Send Button To Send the Email/Sms to Broker
		SendButton=(Button)findViewById(R.id.myPropSmsEmailSendBotton);
		SendButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(nameEditBox.getText().toString().isEmpty())
					nameEditBox.setError("Please Enter Name");
				if(numberEditBox.getText().toString().isEmpty())
					numberEditBox.setError("Please Enter Number");
				if(emailIdEditBox.getText().toString().isEmpty())
					emailIdEditBox.setError("Please Enter Email");
				
				if(!nameEditBox.getText().toString().isEmpty()&& !numberEditBox.getText().toString().isEmpty() && !emailIdEditBox.getText().toString().isEmpty()) {
					new WebserviceClient(EmailSmsSendActivity.this, emailSmsService).execute(
							intent.getString("propertyid"),
							userSessionManager.getSessionValue(Constant.Login.USER_ID),
							nameEditBox.getText().toString(),
							messageEditBox.getText().toString(),
							numberEditBox.getText().toString(),
							emailIdEditBox.getText().toString());
				}
			}
		});
	}

	//Get Response Of Send Email/Sms Web Service 
	@Override
	public void processResponse(Object res) {
		JSONObject jsonObject = (JSONObject) res;
		if (jsonObject != null && jsonObject.length() > 0) {
			try {
				if (String.valueOf(jsonObject.get(Constant.EmailSms.API_STATUS)).equals("1") )  {
					Toast.makeText(getActivity(),String.valueOf(jsonObject.get(Constant.EmailSms.API_MESSAGE)), Toast.LENGTH_LONG).show();
					((MainFragmentActivity)getActivity()).onBackPressed();
				} else{
					Toast.makeText(getActivity(),String.valueOf(jsonObject.get(Constant.EmailSms.API_MESSAGE)), Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onResume() {
	    super.onResume();
	    this.getActivity().getActionBar().setTitle("Send Message");
	}
}
