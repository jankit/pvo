package com.pvo.custom.adapter;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.activity.R;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.GroupBrokerDeleteService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.squareup.picasso.Picasso;

public class GroupMemberArrayAdaptor extends ArrayAdapter<GroupMemberItem> {
	
	private GroupBrokerDeleteService deleteBrokerFromGroupService;
	private Activity context;
	private List<GroupMemberItem> groupMemberList;
	private UserSessionManager userSessionManager;
	
	public GroupMemberArrayAdaptor(Activity context, int resourceId, List<GroupMemberItem> objects) {
		super(context, resourceId, objects);
		groupMemberList = objects;
		this.context = context;
	}

	@Override
	public View getView ( final int position, View convertView, ViewGroup parent ) {
		
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    convertView = inflater.inflate(R.layout.activity_groupmember_list_row, parent, false);
	    
	    final GroupMemberItem groupMemberItem = getItem(position);
	    
	    //Member photo view
	    ImageView ivMemeberPhoto = (ImageView) convertView.findViewById(R.id.ivMemeberPhoto);
	    Picasso.with(getContext().getApplicationContext()).load(groupMemberItem.getPhotolink()).placeholder(R.drawable.no_image).into(ivMemeberPhoto);
	    
	    //Group member name
	    TextView custGroupNameTv = (TextView) convertView.findViewById(R.id.groupMemberName);
	    custGroupNameTv.setText(groupMemberItem.getFirstname());
	    custGroupNameTv.setMinLines(custGroupNameTv.getLineCount());

	    //Group member company name
	    TextView groupMemberCompanyName = (TextView) convertView.findViewById(R.id.groupMemberCompanyName);
	    groupMemberCompanyName.setText(groupMemberItem.getCompanyname());
	    groupMemberCompanyName.setMinLines(groupMemberCompanyName.getLineCount());
		
	    //Delete button is used to delete the particular member From group,
	    Button deleteIV = (Button) convertView.findViewById(R.id.deleteIV);
	    deleteIV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				userSessionManager = new UserSessionManager(context);
				deleteBrokerFromGroupService = new GroupBrokerDeleteService();
				new AlertDialog.Builder(getContext()).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Delete Member").setMessage("Are you sure you want to delete this member?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						WebserviceClient deleteMemberWebserviceClient = new WebserviceClient(v.getContext(),deleteBrokerFromGroupService);
						deleteMemberWebserviceClient.setResponseListner(new ResponseListner() {
							@Override
							public void handleResponse(Object response) {
								JSONObject jsonObject = (JSONObject) response;
								if (jsonObject != null) {
									try {
										if (String.valueOf(jsonObject.getString(Constant.DeleteNominee.API_STATUS)).equals("1")) {
											Toast.makeText(v.getContext().getApplicationContext(),String.valueOf(jsonObject.get(Constant.DeleteNominee.API_MESSAGE)),Toast.LENGTH_LONG).show();
											groupMemberList.remove(position);
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
						deleteMemberWebserviceClient.execute(userSessionManager.getSessionValue(Constant.Login.USER_ID),String.valueOf(groupMemberItem.getBrokerid()));
					}
				}).setNegativeButton("No", null).show();
			}
		});
		return convertView;
	}
}
