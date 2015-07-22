package com.pvo.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
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
import com.pvo.user.service.NomineeAddService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

//Add Nominee to Particular Broker 
public class NomineeAddActivity extends PVOFragment {
	
	private EditText nameOfNominee;
	private EditText mobileNoOfNominee;
	private EditText emailIdOfNominee;
	private TextView addNomineeNameTV;
	private TextView addNomineeEmailTV;
	private TextView addNomineeMobileTV;
	private Button saveNomineeDetailButton;
	private Button addNomineeCancelButton;
	private TextView addNomineePasswordTV;
	private EditText addNomineePasswordEditBox;
	private NomineeAddService addNomineeService;
	private UserSessionManager userSessionManager;
	private Pattern pattern;
	Matcher matcher;
	

	//Set Layout Content View 
	public NomineeAddActivity() {
		setContentView(R.layout.activity_nominee_add);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				
		addNomineeService		= new NomineeAddService();
		userSessionManager		= new UserSessionManager(getActivity().getApplicationContext());
		nameOfNominee			= (EditText) findViewById(R.id.addNomineeNameEditBox);
		mobileNoOfNominee		= (EditText) findViewById(R.id.addNomineeMobileEditBox);
		emailIdOfNominee		= (EditText) findViewById(R.id.addNomineeEmailEditBox);
		addNomineePasswordEditBox = (EditText) findViewById(R.id.addNomineePasswordEditBox);
		addNomineeNameTV 		= (TextView) findViewById(R.id.addNomineeNameTV);
		addNomineeEmailTV 		= (TextView) findViewById(R.id.addNomineeEmailTV);
		addNomineeMobileTV 		= (TextView) findViewById(R.id.addNomineeMobileTV);
		addNomineePasswordTV	= (TextView) findViewById(R.id.addNomineePasswordTV);
		addNomineeNameTV.setText(Html.fromHtml("Name"+"<sup><font size=5 color=red>*</font></sup>"));
		addNomineeEmailTV.setText(Html.fromHtml("Email"+"<sup><font size=5 color=red>*</font></sup>"));
		addNomineeMobileTV.setText(Html.fromHtml("Mobile No."+"<sup><font size=5 color=red>*</font></sup>"));
		addNomineePasswordTV.setText(Html.fromHtml("Password."+"<sup><font size=5 color=red>*</font></sup>"));
		//mobileNoOfNominee.setText(userSessionManager.getSessionValue(Constant.Login.PHONE_NUMBER));
		
		//on click of cancel button goto previous button
		addNomineeCancelButton = (Button) findViewById(R.id.addNomineeCancelButton);
		addNomineeCancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});
		//END
		
		// Save Button To save the Nominee Detail To the current Login Broker 
		saveNomineeDetailButton = (Button)findViewById(R.id.addNomineeSaveBotton);
		saveNomineeDetailButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(nameOfNominee.length()==0)
					nameOfNominee.setError("Enter Name");
				if(mobileNoOfNominee.length()==0)
					mobileNoOfNominee.setError("Enter Mobile No.");
				if(emailIdOfNominee.length()==0)
					emailIdOfNominee.setError("Enter Email");
				if(addNomineePasswordEditBox.length()==0)
					addNomineePasswordEditBox.setError("Enter Password");
				
				if(nameOfNominee.length() > 0 && mobileNoOfNominee.length() > 0 && emailIdOfNominee.length() > 0 && addNomineePasswordEditBox.length() > 0 && isEmailValid(emailIdOfNominee.getText().toString())){
				    saveNomineeDetailButton.setEnabled(false);
				    new WebserviceClient(NomineeAddActivity.this, addNomineeService).execute(
			    		userSessionManager.getSessionValue(Constant.Login.USER_ID),//User Id
			    		nameOfNominee.getText().toString(),//Name Of Nominee
			    		mobileNoOfNominee.getText().toString(),//Mobile Number
			    		emailIdOfNominee.getText().toString(),//Email Address
			    		addNomineePasswordEditBox.getText().toString()//Password
			    		);
				}
			}
		});
	}

	//Get Response Of Add Nominee Web Service
	@Override
	public void processResponse(Object res) {
		JSONObject response = (JSONObject) res;
		saveNomineeDetailButton.setEnabled(true);
		if(response != null && response.length() > 0) {
			try {
				if(response != null && String.valueOf(response.get(Constant.AddNominee.API_STATUS)).equals("1"))  {
					Toast.makeText(getActivity(),String.valueOf(response.get(Constant.AddNominee.API_MESSAGE)), Toast.LENGTH_LONG).show();
					NomineeListActivity nomineesActivity = new NomineeListActivity();
					((MainFragmentActivity)getActivity()).redirectScreenWithoutStack(nomineesActivity);
				} else {
					Toast.makeText(getActivity(),String.valueOf(response.get(Constant.AddNominee.API_MESSAGE)), Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    this.getActivity().getActionBar().setTitle("Add Nominee");
	}
	
	//set email validation
	public boolean isEmailValid(String email) {
         String regExpn =
             "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                 +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                   +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                   +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                   +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                   +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

     CharSequence inputStr = email;
     pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
     matcher = pattern.matcher(inputStr);

     if(matcher.matches()) {
        return true;
     } else {
    	 emailIdOfNominee.setError("Invalid email address");
    	 return false;
     }
    }
}
