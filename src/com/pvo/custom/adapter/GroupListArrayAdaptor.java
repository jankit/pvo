package com.pvo.custom.adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.activity.GroupMemberActivity;
import com.pvo.activity.MainFragmentActivity;
import com.pvo.activity.R;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.GroupDeleteService;
import com.pvo.user.service.GroupEditService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

public class GroupListArrayAdaptor extends ArrayAdapter<GroupListItem> {
	
	private GroupDeleteService deleteGroupService;
	private GroupEditService editGroupService;
	private List<GroupListItem> groupListItems;
	private Activity context;
	private UserSessionManager userSessionManager;
	//private List<GroupMemberItem> grpMemberItems = new ArrayList<GroupMemberItem>();
	
	public GroupListArrayAdaptor(Activity context, int resourceId, List<GroupListItem> objects) {
		super(context, resourceId, objects);
		groupListItems = objects;
		this.context = context;
		
	}

  @Override
  public View getView ( final int position, View convertView, ViewGroup parent ) {
	
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    convertView = inflater.inflate(R.layout.activity_group_list_row, parent, false);
	    final GroupListItem groupListItem = getItem(position);
	    
	    //Group name text view
	    TextView custGroupNameTv = (TextView) convertView.findViewById(R.id.custGroupNameTv);
	    custGroupNameTv.setText(groupListItem.getGroupname());
	    custGroupNameTv.setMinLines(custGroupNameTv.getLineCount());
	
	    //group member counter
	 	TextView tvMemberCount = (TextView) convertView.findViewById(R.id.tvMemberCount);
	 	tvMemberCount.setText("["+groupListItem.getMembercount()+"]");
	    
	    //Edit Button Click Listener for Edit Particular Nominee Detail in this Pass the NomineeId,Name,MobileNo to the EditNomineeActivity
		LinearLayout custGroupEditLayout = (LinearLayout) convertView.findViewById(R.id.custGroupEditLayout);
		custGroupEditLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				userSessionManager = new UserSessionManager(context);
				editGroupService = new GroupEditService();
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.activity_landmark_popup);
				dialog.setTitle("Edit Group");
				
				TextView groupNameTV 				= (TextView) dialog.findViewById(R.id.landmarkTv);
				groupNameTV.setText("Group Name");
	
				final EditText groupNameEditBox = (EditText) dialog.findViewById(R.id.landmarkEditBox);
				groupNameEditBox.setHint("Enter Group Name");
				groupNameEditBox.setText(groupListItem.getGroupname());
				
				//on click of send button send the new landmark
				Button saveGroupName = (Button) dialog.findViewById(R.id.landmarkSendButton);
				saveGroupName.setText("SAVE");
				saveGroupName.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (groupNameEditBox.length() == 0) {
							groupNameEditBox.setError("Enter Group Name");
						} else {
							WebserviceClient editGroupWebserviceClient = new WebserviceClient(getContext(), editGroupService);
							editGroupWebserviceClient.setResponseListner(new ResponseListner() {
								@Override
								public void handleResponse(Object response) {
									JSONObject jsonObject = (JSONObject)response;
									if(jsonObject != null) {
										try {
											Toast.makeText(getContext(), jsonObject.getString(Constant.GroupList.API_MESSAGE),Toast.LENGTH_LONG).show();
											groupListItem.setGroupname(groupNameEditBox.getText().toString());
											notifyDataSetChanged();
											dialog.cancel();
											
										} catch (JSONException e) {
											e.printStackTrace();
										}
									} 
								}
							});
							editGroupWebserviceClient.execute(
									userSessionManager.getSessionValue(Constant.Login.USER_ID),
									groupListItem.getPgid(),
									groupNameEditBox.getText().toString()
									);
						}
					}
				});
				
				// on click close the dialog box
				Button cancel = (Button) dialog.findViewById(R.id.landmarkCancelButton);
				cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.cancel();
					}
				});
				dialog.show();
			}
		});
			
		// Delete button is used to delete the particular Nominee,in this pass the NomineeId to the delete web service of selected Nominee From List
		LinearLayout custGroupDeleteLayout = (LinearLayout) convertView.findViewById(R.id.custGroupDeleteLayout);
		custGroupDeleteLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				userSessionManager = new UserSessionManager(context);
				deleteGroupService = new GroupDeleteService();
				new AlertDialog.Builder(getContext()).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Delete Group").setMessage("Are you sure you want to delete this group?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						WebserviceClient deletWebserviceClient = new WebserviceClient(v.getContext(),deleteGroupService);
						deletWebserviceClient.setResponseListner(new ResponseListner() {
							@Override
							public void handleResponse(Object response) {
								JSONObject jsonObject = (JSONObject) response;
								if (jsonObject != null) {
									try {
										if (String.valueOf(jsonObject.getString(Constant.DeleteNominee.API_STATUS)).equals("1")) {
											Toast.makeText(v.getContext().getApplicationContext(),String.valueOf(jsonObject.get(Constant.DeleteNominee.API_MESSAGE)),Toast.LENGTH_LONG).show();
											groupListItems.remove(position);
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
						deletWebserviceClient.execute(userSessionManager.getSessionValue(Constant.Login.USER_ID),groupListItem.getPgid());
					}
				}).setNegativeButton("No", null).show();
			}
		});
			
		//Redirect to broker list activity
		LinearLayout custGroupViewMemberLayout = (LinearLayout) convertView.findViewById(R.id.custGroupViewMemberLayout);
		custGroupViewMemberLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Bundle bundle = new Bundle();
				bundle.putString(Constant.GroupList.PGID, groupListItem.getPgid());
				bundle.putString(Constant.GroupList.GROUP_NAME, groupListItem.getGroupname());
				GroupMemberActivity groupMemberActivity  = new GroupMemberActivity();
				groupMemberActivity.setArguments(bundle);
				((MainFragmentActivity)getContext()).redirectScreen(groupMemberActivity);
				
				//getGroupMemberList(groupListItem.getPgid());
			}
		});
		
		return convertView;
	}
	
	//Return the list of group member list
   /* public void getGroupMemberList(String groupID) {
    	userSessionManager = new UserSessionManager(context);
    	GroupMemberListService groupMemberListService = new GroupMemberListService();
		WebserviceClient groupMemberListWebserviceClient = new WebserviceClient(context, groupMemberListService);
		groupMemberListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				JSONArray jsonArray = (JSONArray) response;
				try {
					if(jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.BrokerListInGroup.API_STATUS)) {
						LayoutInflater li = LayoutInflater.from(context);
				        View promptsView = li.inflate(R.layout.activity_groupmember_list_popup_window, null);
				        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				        alertDialogBuilder.setView(promptsView);
				        final ListView groupMemberListView = (ListView) promptsView.findViewById(R.id.groupMemberListview);
				        List<HashMap<String, String>> memberList = new ArrayList<HashMap<String,String>>();
				        for (int i = 0; i < jsonArray.length(); i++) {
				        	JSONObject jsonObject = jsonArray.getJSONObject(i);
				        	GroupMemberItem groupMemberItem = new GroupMemberItem();
				        	groupMemberItem.setBrokerid(jsonObject.getString(Constant.BrokerListInGroup.BROKER_ID));
				        	groupMemberItem.setFirstname(jsonObject.getString(Constant.BrokerListInGroup.FIRST_NAME)+" "+jsonObject.getString(Constant.BrokerListInGroup.LAST_NAME));
				        	groupMemberItem.setCompanyname(jsonObject.getString(Constant.BrokerListInGroup.COMPANY_NAME));
				        	groupMemberItem.setLogolink(jsonObject.getString(Constant.BrokerListInGroup.LOGO_LINK));
				        	groupMemberItem.setPhotolink(jsonObject.getString(Constant.BrokerListInGroup.PHOTO_LINK));
				        	grpMemberItems.add(groupMemberItem);
				        	
						}
				        GroupMemberArrayAdaptor memberArrayAdaptor = new GroupMemberArrayAdaptor(context, R.id.groupMemberListview, grpMemberItems);//JSONUtils.getList(jsonArray, "name"));
				        groupMemberListView.setAdapter(memberArrayAdaptor);
				        
				        // set dialog message
				        alertDialogBuilder.setCancelable(true).setTitle("Group Member List").setPositiveButton("Cancel",new DialogInterface.OnClickListener() {
				        	public void onClick(DialogInterface dialog,int id) {}
				        });
				        // create alert dialog
				        AlertDialog alertDialog = alertDialogBuilder.create();
				        // show it
				        alertDialog.show();
					} else {
						Toast.makeText(context,((JSONObject) jsonArray.get(0)).getString(Constant.BrokerListInGroup.API_MESSAGE), Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		groupMemberListWebserviceClient.execute(userSessionManager.getSessionValue(Constant.Login.USER_ID),groupID);
    }*/
}
