package com.pvo.custom.adapter;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.activity.FindAgentDetailActivity;
import com.pvo.activity.GroupMemberActivity;
import com.pvo.activity.MainFragmentActivity;
import com.pvo.activity.MyPropertyListActivity;
import com.pvo.activity.R;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.GroupListService;
import com.pvo.user.service.GroupMemberAddService;
import com.pvo.user.service.PrefereBrokerDeleteService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.pvo.util.JSONUtils;
import com.squareup.picasso.Picasso;

public class PreferreBrokerArrayAdaptor extends ArrayAdapter<PreferreBrokerItem> {
	
	private Context context;
	private GroupListService groupListService;
	private ArrayAdapter groupListAdapter;
	private HashMap<String, String> groupNameMap;
	private GroupMemberAddService addGroupMemberService;
	//private PreferreBrokerItem preferreBrokerItem;
	
	//Get Stored User Id From User SessionManager 
	private UserSessionManager userSessionManager;
	private PrefereBrokerDeleteService deletePrefereBrokerService;
	private List<PreferreBrokerItem> brokerList;
	
	public PreferreBrokerArrayAdaptor(Context context, int resourceId, List<PreferreBrokerItem> objects) {
		super(context, resourceId, objects);
		brokerList = objects;
		this.context = context;
	}

	@Override
	public View getView ( final int position, View view, final ViewGroup parent ) {
		
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    view = inflater.inflate(R.layout.activity_preferrebroker_list_row, parent, false);
	    userSessionManager = new UserSessionManager(view.getContext().getApplicationContext());
	    final PreferreBrokerItem preferreBrokerItem = getItem(position);
	   
	    //Set image from URL 
	    ImageView prefBrokerImageView = (ImageView) view.findViewById(R.id.prefBrokerImageView);
	    if(preferreBrokerItem.getLogolink() != null) 
	    	Picasso.with(context).load(preferreBrokerItem.getLogolink()).placeholder(R.drawable.no_image).into(prefBrokerImageView);
		else 
			Picasso.with(context).load(preferreBrokerItem.getPhotolink()).placeholder(R.drawable.no_image).into(prefBrokerImageView);
	    
	    //Broker id,first name,last name,company name 
	    TextView  prefBrokerFiratLastNameTextView = (TextView) view.findViewById(R.id.prefBrokerFiratLastNameTextView);
	    prefBrokerFiratLastNameTextView.setText("ID# "+preferreBrokerItem.getBrokerid()+" "+preferreBrokerItem.getFirstname()+" "+preferreBrokerItem.getLastname()+" | "+preferreBrokerItem.getCompanyname());
	    prefBrokerFiratLastNameTextView.setMinLines(prefBrokerFiratLastNameTextView.getLineCount());
	    
	    //display Mobie number
	    TextView prefBrokerPhoneNumberTextView = (TextView) view.findViewById(R.id.prefBrokerDealerTextView);
	    prefBrokerPhoneNumberTextView.setText(Html.fromHtml("<b>"+"(M): "+"</b>"+preferreBrokerItem.getPhonem()));
	    prefBrokerPhoneNumberTextView.setMinLines(prefBrokerPhoneNumberTextView.getLineCount());
	    
	    //Display Address
	    TextView prefBrokerAddressTextView = (TextView) view.findViewById(R.id.prefBrokerMobileTextView);
	    prefBrokerAddressTextView.setText(Html.fromHtml("<b>"+preferreBrokerItem.getAddress()+"</b>"));
	    prefBrokerAddressTextView.setMinLines(prefBrokerAddressTextView.getLineCount());
	    
	    //Display email
	    TextView prefBrokerEmailTextView = (TextView) view.findViewById(R.id.prefBrokerPostCodeTextView);
	    prefBrokerEmailTextView.setText(Html.fromHtml(preferreBrokerItem.getEmail()));
	    prefBrokerEmailTextView.setMinLines(prefBrokerEmailTextView.getLineCount());
	   
	    //on click of open agent detail page
	    RelativeLayout prefBrokerCustListlayout = (RelativeLayout) view.findViewById(R.id.prefBrokerCustListlayout);
	    prefBrokerCustListlayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle findAgnetBundel = new Bundle();
				findAgnetBundel.putString(Constant.FindAgent.BROKER_ID, preferreBrokerItem.getBrokerid());
				FindAgentDetailActivity findAgentDetailActivity = new FindAgentDetailActivity();
				findAgentDetailActivity.setArguments(findAgnetBundel);
				((MainFragmentActivity)getContext()).redirectScreen(findAgentDetailActivity);
			}
		});
	    
	    //Redirect to property list (Display broker property list)
	    LinearLayout prefereBrokCustPropListLayout = (LinearLayout) view.findViewById(R.id.prefereBrokCustPropListLayout);
	    prefereBrokCustPropListLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Bundle prefereBrokerBundle = new  Bundle();
				prefereBrokerBundle.putString(Constant.PreferredBrokers.BROKER_ID,preferreBrokerItem.getBrokerid());
				prefereBrokerBundle.putString("Type","PrefereBroker");
				prefereBrokerBundle.putString(Constant.PreferredBrokers.FIRST_NAME,preferreBrokerItem.getFirstname());
				MyPropertyListActivity myPropertyActivity = new MyPropertyListActivity();
				myPropertyActivity.setArguments(prefereBrokerBundle);
				((MainFragmentActivity)getContext()).redirectScreen(myPropertyActivity);
			}
		});
	    
	    //add broker to group
	    LinearLayout prefereBrokCustAddToGroupLayout = (LinearLayout) view.findViewById(R.id.prefBrokCustAddtoGroupLayout);
	    prefereBrokCustAddToGroupLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				getGroupList(preferreBrokerItem.getBrokerid());
			}
		});
	    
	    //On click Of Delete Icon Delete Prefer Broker
	    LinearLayout prefereBrokCustDeleteLayout = (LinearLayout) view.findViewById(R.id.prefBrokerCustDeleteLayout);
	    prefereBrokCustDeleteLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				deletePrefereBrokerService = new PrefereBrokerDeleteService();
				new AlertDialog.Builder(getContext()).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Delete Broker").setMessage("Are you sure you want to delete this broker?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						WebserviceClient deletePreferBrokerWebserviceClient = new WebserviceClient(v.getContext(),deletePrefereBrokerService);
						deletePreferBrokerWebserviceClient.setResponseListner(new ResponseListner() {
							@Override
							public void handleResponse(Object response) {
								JSONObject jsonObject=(JSONObject)response;
								if(jsonObject != null) {
									try {
										if (String.valueOf(jsonObject.getString(Constant.InActiveMyProperty.API_STATUS)).equals("1")) {
											Toast.makeText(v.getContext().getApplicationContext(),String.valueOf(jsonObject.get(Constant.DeletePrefereBroker.API_MESSAGE)),Toast.LENGTH_LONG).show();
											brokerList.remove(position);
											notifyDataSetChanged();
										} else {
											Toast.makeText(v.getContext().getApplicationContext(),String.valueOf(jsonObject.getString(Constant.DeletePrefereBroker.API_MESSAGE)),Toast.LENGTH_LONG).show();
										}
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}
							}
						});
						deletePreferBrokerWebserviceClient.execute(userSessionManager.getSessionValue(Constant.Login.USER_ID),preferreBrokerItem.getBrokerid());
					}
				}).setNegativeButton("No", null).show();
			}
		});
	    
	    //Click on call button to make a call 
	    LinearLayout prefereBrokCustCallLayout = (LinearLayout) view.findViewById(R.id.prefBrokerCustCallLayout);
	    prefereBrokCustCallLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(preferreBrokerItem.getPhonem() != null &&  preferreBrokerItem.getPhonem().length() > 0) {
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setData(Uri.parse("tel:"+preferreBrokerItem.getPhonem()));
					v.getContext().getApplicationContext().startActivity(intent);
				} else {
					Toast.makeText(getContext(), "Number not available", Toast.LENGTH_LONG).show();
				}
			}
		});
	    
		return view;
	}
    
	//call group list web service to get group name and set into list view
	public void getGroupList(final String brokerID){
		groupListService =  new GroupListService();
		groupNameMap = new HashMap<String, String>();
		WebserviceClient groupListwebservice = new WebserviceClient(context, groupListService);
		groupListwebservice.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object res) {
				final JSONArray jsonArray = (JSONArray) res;
				try {		
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.PreferredBrokers.API_STATUS)) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							groupNameMap.put(jsonObject.getString(Constant.GroupList.GROUP_NAME), jsonObject.getString(Constant.GroupList.PGID));
						}
						groupListAdapter = new ArrayAdapter<String>(getContext().getApplicationContext(),R.layout.spinner_text,JSONUtils.getList(jsonArray, Constant.GroupList.GROUP_NAME));
						groupListAdapter.setDropDownViewResource(R.layout.spinner_text);
						selectGroup(brokerID);
					} else {
						Toast.makeText(getContext().getApplicationContext(),((JSONObject) jsonArray.get(0)).getString(Constant.PreferredBrokers.API_MESSAGE), Toast.LENGTH_LONG).show();
					}				
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		});
		groupListwebservice.execute(userSessionManager.getSessionValue(Constant.Login.USER_ID));
	}
	
	//open dialog to select the group to add member into selected group
	private void selectGroup(final String brokerId) {
		
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.activity_group_dilog);
		dialog.setTitle("Select Group");
		
		//open dialog at the top of screen 
		Window window = dialog.getWindow();
		WindowManager.LayoutParams wlp = window.getAttributes();
		wlp.gravity = Gravity.TOP;
		wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		window.setAttributes(wlp);

		final Spinner groupListSpinner = (Spinner) dialog.findViewById(R.id.groupListSpinner);
		groupListSpinner.setAdapter(groupListAdapter);

		//on click of send button send the new landmark
		Button save = (Button) dialog.findViewById(R.id.groupSaveButton);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					//call web service to add member into group 
					addGroupMemberService = new GroupMemberAddService();
					WebserviceClient addGroupMemberWebserviceClient = new WebserviceClient(getContext(), addGroupMemberService);
					addGroupMemberWebserviceClient.setResponseListner(new ResponseListner() {
						@Override
						public void handleResponse(Object response) {
							JSONObject jsonObject = (JSONObject) response;
							if(jsonObject != null) {
								try {
									Toast.makeText(getContext(), String.valueOf(jsonObject.getString(Constant.AddGroupMember.API_MESSAGE)), Toast.LENGTH_LONG).show();
									Bundle bundle = new Bundle();
									bundle.putString(Constant.GroupList.PGID,groupNameMap.get(groupListSpinner.getSelectedItem().toString()));
									bundle.putString(Constant.GroupList.GROUP_NAME, groupListSpinner.getSelectedItem().toString());
									
									GroupMemberActivity groupMemberActivity  = new GroupMemberActivity();
									groupMemberActivity.setArguments(bundle);
									((MainFragmentActivity)getContext()).redirectScreen(groupMemberActivity);
									dialog.cancel();
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}
						
					});
					addGroupMemberWebserviceClient.execute(
							userSessionManager.getSessionValue(Constant.Login.USER_ID),
							groupNameMap.get(groupListSpinner.getSelectedItem().toString()),
							brokerId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		//on click close the dialog box
		Button cancel = (Button) dialog.findViewById(R.id.groupCancelButton);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		dialog.show();
	}
}

