package com.pvo.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.custom.adapter.GroupListArrayAdaptor;
import com.pvo.custom.adapter.GroupListItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.GroupAddService;
import com.pvo.user.service.GroupListService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

//This Is Used to display the List of the Nominee in this we link the Add nominee   
public class GroupListActivity extends PVOFragment {
    
	private LinearLayout groupAddLayout;
    private ListView groupListView;
    private GroupListService groupListService;
    private UserSessionManager userSessionManager;
    private TextView groupNameTV;
    private List<GroupListItem> groupListItems = new ArrayList<GroupListItem>();
    private GroupListArrayAdaptor groupListArrayAdaptor;
    private GroupAddService addGroupService;

	//Set Layout Content View
	public GroupListActivity() {
		setContentView(R.layout.activity_group_list);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		groupListService 			= new GroupListService();
		userSessionManager 			= new UserSessionManager(getActivity().getApplicationContext());
		groupListView 				= (ListView) findViewById(R.id.groupListView);
		groupAddLayout 				= (LinearLayout) findViewById(R.id.groupAddLayout);
		
		//Redirect to add new nominee screen 
		groupAddLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addGroupDialogBox();
			}
		});
		new WebserviceClient(GroupListActivity.this, groupListService).execute(userSessionManager.getSessionValue(Constant.Login.USER_ID));
	}
	
	@Override
	public void processResponse(Object response) {
		JSONArray jsonArry = (JSONArray) response;
		try {
			if (jsonArry != null && !((JSONObject)jsonArry.get(0)).has(Constant.NomineeList.API_STATUS)) {
				for (int i = 0; i < jsonArry.length(); i++) {
					JSONObject jsonObj = jsonArry.getJSONObject(i);
					GroupListItem groupListItem = new GroupListItem();
					groupListItem.setPgid(jsonObj.getString(Constant.GroupList.PGID));
					groupListItem.setGroupname(jsonObj.getString(Constant.GroupList.GROUP_NAME));
					groupListItem.setOwnerbrokerid(jsonObj.getString(Constant.GroupList.OWNER_BROKER_ID));
					groupListItem.setMembercount(jsonObj.getString(Constant.GroupList.MEMBER_COUNT));
					groupListItems.add(groupListItem);
				}
				
				if(groupListArrayAdaptor == null) {
					groupListArrayAdaptor = new GroupListArrayAdaptor(getActivity(), R.id.groupListView, groupListItems);
					groupListView.setAdapter(groupListArrayAdaptor);
				} else {
					groupListArrayAdaptor.notifyDataSetChanged();
				}
			} else {
				Toast.makeText(getActivity(),((JSONObject)jsonArry.get(0)).getString(Constant.NomineeList.API_MESSAGE), Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    this.getActivity().getActionBar().setTitle("Groups");
	}

	
	//dialog for add new landmark call AddnewLandmark Web service
	private void addGroupDialogBox() {
		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.activity_landmark_popup);
		dialog.setTitle("Add Group");
		final EditText groupNameEditBox = (EditText) dialog.findViewById(R.id.landmarkEditBox);
		groupNameEditBox.setHint("Enter Group Name");
		groupNameTV 					= (TextView) dialog.findViewById(R.id.landmarkTv);
		groupNameTV.setText("Group Name");

		Button save = (Button) dialog.findViewById(R.id.landmarkSendButton);
		save.setText("SAVE");
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (groupNameEditBox.length() == 0) {
					groupNameEditBox.setError("Enter Group Name");
				} else {
					addGroupService = new GroupAddService();
					WebserviceClient addGroupWebClientService = new WebserviceClient(getActivity(), addGroupService);
					addGroupWebClientService.setResponseListner(new ResponseListner() {
						@Override
						public void handleResponse(Object response) {
							JSONObject jsonObject = (JSONObject) response;
							if (jsonObject != null) {
								try {
									Toast.makeText(getActivity(),jsonObject.getString(Constant.AddNewLandmark.API_MESSAGE),Toast.LENGTH_LONG).show();
									GroupListActivity groupListActivity = new GroupListActivity();
									((MainFragmentActivity)getActivity()).redirectScreen(groupListActivity);
									dialog.cancel();
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}
					});
					addGroupWebClientService.execute(userSessionManager.getSessionValue(Constant.Login.USER_ID),groupNameEditBox.getText().toString());
				}
			}
		});

		//on click close the dialog box
		Button cancel = (Button) dialog.findViewById(R.id.landmarkCancelButton);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		dialog.show();
	}
}
