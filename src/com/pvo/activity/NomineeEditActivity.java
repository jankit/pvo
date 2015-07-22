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
import com.pvo.user.service.NomineeEditService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

/**
 * Edit the Selected Nominee Detail 
 * */
public class NomineeEditActivity extends PVOFragment{
	
    private EditText nameOfNominee;
    private EditText mobileNoOfNominee;
    private EditText emailIdOfNominee;
    private Button saveNomineeDetailButton;
    private Button editNomineeCancelBotton;
    private EditText editNomineePasswordEditBox;
	private Bundle intent;
	private NomineeEditService editNomineeService;
	private UserSessionManager userSessionManager;
	private Pattern pattern;
	private Matcher matcher;
	
	private TextView editNomineeNameTV;
	private TextView editNomineeEmailTV;
	private TextView editNomineeMobileTV;
	private TextView editNomineePasswordTV;
	
	
	//Set layout content view 
	public NomineeEditActivity() {
		setContentView(R.layout.activity_nominee_edit);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		editNomineeService			= new NomineeEditService();
		userSessionManager			= new UserSessionManager(getActivity().getApplicationContext());
		
		nameOfNominee				= (EditText) findViewById(R.id.editNomineeNameEditBox);
		mobileNoOfNominee			= (EditText) findViewById(R.id.editNomineeMobileEditBox);
		emailIdOfNominee			= (EditText) findViewById(R.id.editNomineeEmailEditBox);
		editNomineePasswordEditBox	= (EditText) findViewById(R.id.editNomineePasswordEditBox);
		
		editNomineeNameTV = (TextView) findViewById(R.id.editNomineeNameTV);
		editNomineeEmailTV = (TextView) findViewById(R.id.editNomineeEmailTV);
		editNomineeMobileTV = (TextView) findViewById(R.id.editNomineeMobileTV);
		editNomineePasswordTV = (TextView) findViewById(R.id.editNomineePasswordTV);
		
		editNomineeNameTV.setText(Html.fromHtml("Name"+"<sup><font size=5 color=red>*</font></sup>"));
		editNomineeEmailTV.setText(Html.fromHtml("Email"+"<sup><font size=5 color=red>*</font></sup>"));
		editNomineeMobileTV.setText(Html.fromHtml("Mobile No"+"<sup><font size=5 color=red>*</font></sup>"));
		editNomineePasswordTV.setText(Html.fromHtml("Password"+"<sup><font size=5 color=red>*</font></sup>"));
		
		intent=getArguments();
		nameOfNominee.setText(intent.getString("title"));
		mobileNoOfNominee.setText(intent.getString(Constant.NomineeList.MOBILE_NO));
		emailIdOfNominee.setText(intent.getString(Constant.NomineeList.EMAIL_ID));
		editNomineePasswordEditBox.setText(intent.getString(Constant.NomineeList.PWD));
		
		/** Save the edit nominee information and redirect to nominee list screen **/
		saveNomineeDetailButton=(Button)findViewById(R.id.editNomineeSaveBotton);
		saveNomineeDetailButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(nameOfNominee.length()==0){
					nameOfNominee.setError("Enter Name");
				}
				if(mobileNoOfNominee.length()==0){
					mobileNoOfNominee.setError("Enter Mobile No");
				}
				if(emailIdOfNominee.length()==0){
					emailIdOfNominee.setError("Enter Email");
				}
				if(editNomineePasswordEditBox.length()==0){
					editNomineePasswordEditBox.setError("Enter Password");
				}
				if(nameOfNominee.length() > 0 && mobileNoOfNominee.length() > 0 && emailIdOfNominee.length() > 0 && editNomineePasswordEditBox.length() > 0 && isEmailValid(emailIdOfNominee.getText().toString())){
					saveNomineeDetailButton.setEnabled(false);
					new WebserviceClient(NomineeEditActivity.this, editNomineeService).execute(
							userSessionManager.getSessionValue(Constant.Login.USER_ID).toString(),
							intent.getString(Constant.NomineeList.NOMINEE_ID).toString(),
							nameOfNominee.getText().toString(),
							mobileNoOfNominee.getText().toString(),
							emailIdOfNominee.getText().toString(),
							editNomineePasswordEditBox.getText().toString());
				}
				
			}
		});
		/** END **/
		
		/** redirect to previous screen on click of cancel button **/
		editNomineeCancelBotton = (Button) findViewById(R.id.editNomineeCancelBotton);
		editNomineeCancelBotton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});
		/** END **/
	}

	@Override
	public void processResponse(Object res) {
		JSONObject response = (JSONObject) res;
		saveNomineeDetailButton.setEnabled(true);
		if (response != null && response.length() > 0) {
			try {
				if (String.valueOf(response.get(Constant.EditNominee.API_STATUS)).equals("1") )  {//&& response.has(Constant.Login.USER_ID)
					Toast.makeText(getActivity(),String.valueOf(response.get(Constant.EditNominee.API_MESSAGE)), Toast.LENGTH_LONG).show();
					NomineeListActivity nomineesActivity = new NomineeListActivity();
					((MainFragmentActivity)getActivity()).redirectScreenWithoutStack(nomineesActivity);
				} else{
						Toast.makeText(getActivity(),String.valueOf(response.get(Constant.EditNominee.API_MESSAGE)), Toast.LENGTH_LONG).show();
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
	    this.getActivity().getActionBar().setTitle("Edit Nominee");
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
