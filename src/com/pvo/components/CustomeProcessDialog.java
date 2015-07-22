package com.pvo.components;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;

import com.pvo.activity.R;

/**
 * This is used to show the custom process dialog and used in 
 * 		--> WebserviceClient
 * 
 * 
 * 
 * **/
@SuppressLint({ "ValidFragment", "InlinedApi" })
public class CustomeProcessDialog extends DialogFragment{
	
	
	private ProgressDialog progressDialog;
	
	@SuppressLint("ValidFragment")
	public CustomeProcessDialog(Context context) {
		progressDialog = new ProgressDialog(context);
		
		progressDialog = ProgressDialog.show(context, null, null, true, true);
		progressDialog.setContentView(R.layout.progress_layout);
		//progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		//progressDialog.setMessage("Please wait...");
		//progressDialog.setIndeterminate(false);
		//progressDialog.setInverseBackgroundForced(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
		progressDialog.getWindow();
		progressDialog.setCancelable(false);
		
	}

	public void show() {
		progressDialog.show();
	}

	public void hide() {
		progressDialog.dismiss();
		
	}
}
