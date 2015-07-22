package com.pvo.custom.adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.activity.MainFragmentActivity;
import com.pvo.activity.NomineeEditActivity;
import com.pvo.activity.R;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.NomineeDeleteService;
import com.pvo.util.Constant;

public class NomineeArrayAdaptor extends ArrayAdapter<NomineeItem> {
	
	private NomineeDeleteService deleteNomineeService;
	private List<NomineeItem> nomineeList;
	
	public NomineeArrayAdaptor(Context context, int resourceId, List<NomineeItem> objects) {
		super(context, resourceId, objects);
		nomineeList = objects;
	}

	@Override
	public View getView ( final int position, View convertView, ViewGroup parent ) {
		
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    convertView = inflater.inflate(R.layout.activity_nominee_list_row, parent, false);
	    final NomineeItem nomineeItem = getItem(position);
	    
	    TextView nomineesCustomeTitleTextView = (TextView) convertView.findViewById(R.id.nomineesCustomeTitleTextView);
	    nomineesCustomeTitleTextView.setText(nomineeItem.getTitle());
	    nomineesCustomeTitleTextView.setMinLines(nomineesCustomeTitleTextView.getLineCount());

	    TextView nomineesCustomeMobileTextView = (TextView) convertView.findViewById(R.id.nomineesCustomeMobileTextView);
	    nomineesCustomeMobileTextView.setText(nomineeItem.getMobile_no());
	    nomineesCustomeMobileTextView.setMinLines(nomineesCustomeMobileTextView.getLineCount());
	    
	    //Edit Button Click Listener for Edit Particular Nominee Detail 
		//in this Pass the NomineeId,Name,MobileNo to the EditNomineeActivity
		LinearLayout nomineeEditLayout = (LinearLayout) convertView.findViewById(R.id.nomineeCustEditLayout);
		nomineeEditLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 Bundle editNomineeBundle = new Bundle();
				 editNomineeBundle.putString(Constant.NomineeList.NOMINEE_ID, nomineeItem.getNomineeid());
				 editNomineeBundle.putString(Constant.NomineeList.TITLE, nomineeItem.getTitle());
				 editNomineeBundle.putString(Constant.NomineeList.MOBILE_NO, nomineeItem.getMobile_no());
				 editNomineeBundle.putString(Constant.NomineeList.EMAIL_ID, nomineeItem.getEmail_id());
				 editNomineeBundle.putString(Constant.NomineeList.PWD, nomineeItem.getPwd());
				
				 NomineeEditActivity editNomineeActivity = new NomineeEditActivity();
				 editNomineeActivity.setArguments(editNomineeBundle);
				 ((MainFragmentActivity)getContext()).redirectScreen(editNomineeActivity);
			}
		});
		
		// Delete button is used to delete the particular Nominee,
		// in this pass the NomineeId to the delete web service of selected Nominee From List
		LinearLayout nomineeDeleteLayout = (LinearLayout) convertView.findViewById(R.id.nomineeDeleteLayout);
		nomineeDeleteLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				deleteNomineeService = new NomineeDeleteService();
				new AlertDialog.Builder(getContext()).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Delete Nominee").setMessage("Are you sure you want to delete this nominee?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						WebserviceClient deletWebserviceClient = new WebserviceClient(v.getContext(),deleteNomineeService);
						deletWebserviceClient.setResponseListner(new ResponseListner() {
							@Override
							public void handleResponse(Object response) {
								JSONObject jsonObject = (JSONObject) response;
								if (jsonObject != null) {
									try {
										if (String.valueOf(jsonObject.getString(Constant.DeleteNominee.API_STATUS)).equals("1")) {
											Toast.makeText(v.getContext().getApplicationContext(),String.valueOf(jsonObject.get(Constant.DeleteNominee.API_MESSAGE)),Toast.LENGTH_LONG).show();
											nomineeList.remove(position);
											notifyDataSetChanged();
										} else {
											Toast.makeText(v.getContext().getApplicationContext(),String.valueOf(jsonObject.getString(Constant.DeleteNominee.API_MESSAGE)),Toast.LENGTH_LONG).show();
										}
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}
							}
						});
						deletWebserviceClient.execute(nomineeItem.getNomineeid());
					}
				}).setNegativeButton("No", null).show();
			}
		});
		return convertView;
	}
}
