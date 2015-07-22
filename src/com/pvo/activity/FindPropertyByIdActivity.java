/**
 *  This class is used for new Registration of user if not Register
 *  in this user can enter the mobile number and Verification code   
 *  registration successfully auto generate password will be sent via sms on mobile number.
 * */

package com.pvo.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pvo.prototype.PVOFragment;

//Find the Property By Property Id enter the Property Id
public class FindPropertyByIdActivity extends PVOFragment {

	private EditText findPropByIdEditBox;
	private TextView propertyTv;
	private Button search;

	//set layout content view
	public FindPropertyByIdActivity() {
		setContentView(R.layout.activity_findproperty_byid);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		// This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		findPropByIdEditBox = (EditText) findViewById(R.id.findPropByIdEditBox);
		propertyTv = (TextView) findViewById(R.id.propertyTv);
		propertyTv.setText(Html.fromHtml("Enter Property Id" + "<sup><font size=5 color=red>*</font></sup>"));
		
		search = (Button) findViewById(R.id.findPropByIdSaveBotton);
		search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (findPropByIdEditBox.length() == 0) 
					findPropByIdEditBox.setError("Enter Property Id");
				
				if (findPropByIdEditBox.length() > 0) {
					Bundle findPropByIdBundle = new Bundle();
					findPropByIdBundle.putString("propertyid",findPropByIdEditBox.getText().toString());
					MyPropertyDetailActivity myPropertyDetailActivity = new MyPropertyDetailActivity();
					myPropertyDetailActivity.setArguments(findPropByIdBundle);
					((MainFragmentActivity)getActivity()).redirectScreen(myPropertyDetailActivity);
				}
				findPropByIdEditBox.setText("");
			}
		});
	}
	@Override
	public void processResponse(Object response) {}
}