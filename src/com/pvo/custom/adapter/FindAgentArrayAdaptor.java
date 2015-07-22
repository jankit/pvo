package com.pvo.custom.adapter;

import java.text.ParseException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.activity.FindAgentDetailActivity;
import com.pvo.activity.MainFragmentActivity;
import com.pvo.activity.MyPropertyListActivity;
import com.pvo.activity.MyRequirementListActivity;
import com.pvo.activity.R;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.FindAgentService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.pvo.util.ConvertDateFormat;
import com.squareup.picasso.Picasso;

public class FindAgentArrayAdaptor extends ArrayAdapter<FindAgentItem> {
	
	private FindAgentService findAgentService;
	private Context context;
	private UserSessionManager userSessionManager;
	
	public FindAgentArrayAdaptor(Context context, int resourceId, List<FindAgentItem> objects) {
		super(context, resourceId, objects);
		this.context = context;
	}

	@Override
	  public View getView ( int position, View view, ViewGroup parent ) {
		
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		// create a new view of my layout and inflate it in the row
	    view = inflater.inflate(R.layout.activity_findagent_list_row, parent, false);
	    final FindAgentItem findAgentItem = getItem(position);
	    final String phonString;
	 
	    //Set image from URL 
	    ImageView findAgentImageView = (ImageView) view.findViewById(R.id.findAgentImageView);
	    if(findAgentItem.getLogolink() != null) 
	    	Picasso.with(context).load(findAgentItem.getLogolink()).placeholder(R.drawable.no_image).into(findAgentImageView);
		else 
			Picasso.with(context).load(findAgentItem.getPhotolink()).placeholder(R.drawable.no_image).into(findAgentImageView);
	    
	    
	    //Id,First,Last Name,Company name
	    TextView  findAgentFirstLastNameTextView = (TextView) view.findViewById(R.id.findAgentComplexTextView);
	    findAgentFirstLastNameTextView.setText("ID# "+findAgentItem.getBrokerid()+" "+findAgentItem.getFirstname()+" "+findAgentItem.getLastname()+" | "+findAgentItem.getCompanyname());
	    findAgentFirstLastNameTextView.setMinLines(findAgentFirstLastNameTextView.getLineCount());
	    
	    //Property Deals in
	    TextView findAgentDealerInTextView = (TextView) view.findViewById(R.id.findAgentDealerInTextView);
	    findAgentDealerInTextView.setText(Html.fromHtml("<b>"+"Deals in: "+"</b>"+findAgentItem.getPropertytypedealin()));
	    findAgentDealerInTextView.setMinLines(findAgentDealerInTextView.getLineCount());
	    
	    //Address
	    TextView prefBrokerNumberOfficeTextView = (TextView) view.findViewById(R.id.findAgentAddressTextView);
	    prefBrokerNumberOfficeTextView.setText(Html.fromHtml("<b>"+"Address : "+"</b>"+findAgentItem.getAddress()));
	    findAgentDealerInTextView.setMinLines(findAgentDealerInTextView.getLineCount());
	    
	    //Phone number
	    TextView findAgentMobileNumberTextView = (TextView) view.findViewById(R.id.findAgentSizeTextView);
	    if(findAgentItem.getPhonem().contains(",")) {
	    	phonString = findAgentItem.getPhonem().substring(0,findAgentItem.getPhonem().indexOf(","));
	    	//findAgentMobileNumberTextView.setText(Html.fromHtml("<b>"+"Phone(M): "+"</b>"+findAgentItem.getPhonem().substring(0,findAgentItem.getPhonem().indexOf(","))));
	    } else {
	    	phonString = findAgentItem.getPhonem();
	    	//findAgentMobileNumberTextView.setText(Html.fromHtml("<b>"+"Phone(M): "+"</b>"+findAgentItem.getPhonem()));
	    }
	    findAgentMobileNumberTextView.setText(Html.fromHtml("<b>"+"Phone(M): "+"</b>"+phonString));
	    findAgentMobileNumberTextView.setMinLines(findAgentMobileNumberTextView.getLineCount());
	    
	    //Date of Join
	    try {
	    	TextView findAgentDateOfJoinTextView = (TextView) view.findViewById(R.id.findAgentDateOfJoinTextView);
	    	findAgentDateOfJoinTextView.setText(Html.fromHtml("<b>"+"Date of Join: "+"</b>"+ConvertDateFormat.convertDateFormat(findAgentItem.getDtjoin())));
	    	findAgentDateOfJoinTextView.setMinLines(findAgentDateOfJoinTextView.getLineCount());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    
	    //Total property coutn of agent
	    TextView agentResultPropCounter = (TextView) view.findViewById(R.id.agentResultPropCounter);
	    agentResultPropCounter.setText("["+findAgentItem.getTotalproperty()+"]");
	    
	  //Total Requirement coutn of agent
	    TextView agentResultReqCounter = (TextView) view.findViewById(R.id.agentResultReqCounter);
	    agentResultReqCounter.setText("["+findAgentItem.getTotalrequirement()+"]");
	    
	    //on click of open agent detail page
	    RelativeLayout findAgentCustListlayout = (RelativeLayout) view.findViewById(R.id.findAgentCustListlayout);
	    findAgentCustListlayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle findAgnetBundel = new Bundle();
				findAgnetBundel.putString(Constant.FindAgent.BROKER_ID, findAgentItem.getBrokerid());
				FindAgentDetailActivity findAgentDetailActivity = new FindAgentDetailActivity();
				findAgentDetailActivity.setArguments(findAgnetBundel);
				((MainFragmentActivity)getContext()).redirectScreen(findAgentDetailActivity);
			}
		});
	    
	    //Add to prefere button
	    LinearLayout findAgentAddToPrefereLayout = (LinearLayout) view.findViewById(R.id.findAgentAddToPrefereLayout);
	    findAgentAddToPrefereLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				findAgentService = new FindAgentService();
				userSessionManager = new UserSessionManager(context);
				WebserviceClient findAgentWebserviceClient = new WebserviceClient(context, findAgentService);
				findAgentWebserviceClient.setResponseListner(new ResponseListner() {
					@Override
					public void handleResponse(Object response) {
						JSONArray jsonArray = (JSONArray) response;
						try {
							if(jsonArray != null) {
								JSONObject jsonObject = jsonArray.getJSONObject(0);
								Toast.makeText(context, jsonObject.getString(Constant.FindAgent.API_MESSAGE), Toast.LENGTH_LONG).show();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				findAgentWebserviceClient.execute("Prefere",userSessionManager.getSessionValue(Constant.Login.USER_ID),findAgentItem.getBrokerid());
			}
		});
	    
	    //See broker property, Redirect to property list
	    LinearLayout findAgentCustSeeProp = (LinearLayout) view.findViewById(R.id.findAgentCustSeeProp);
	    findAgentCustSeeProp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!findAgentItem.getTotalproperty().equals("0")) {
					Bundle prefereBrokerBundle = new  Bundle();
					prefereBrokerBundle.putString(Constant.PreferredBrokers.BROKER_ID,findAgentItem.getBrokerid());
					prefereBrokerBundle.putString("Type","PrefereBroker");
					prefereBrokerBundle.putString(Constant.PreferredBrokers.FIRST_NAME,findAgentItem.getFirstname());
					MyPropertyListActivity myPropertyActivity = new MyPropertyListActivity();
					myPropertyActivity.setArguments(prefereBrokerBundle);
					((MainFragmentActivity)getContext()).redirectScreen(myPropertyActivity);
				} else {
					Toast.makeText(getContext(), "No property found", Toast.LENGTH_LONG).show();
				}
			}
		});
	    
	    //See broker property, Redirect to property list
	    LinearLayout findAgentCustSeeReq = (LinearLayout) view.findViewById(R.id.findAgentCustSeeReq);
	    findAgentCustSeeReq.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!findAgentItem.getTotalrequirement().equals("0")) {
					Bundle prefereBrokerBundle = new  Bundle();
					prefereBrokerBundle.putString(Constant.PreferredBrokers.BROKER_ID,findAgentItem.getBrokerid());
					prefereBrokerBundle.putString("Type","Requirement");
					prefereBrokerBundle.putString(Constant.PreferredBrokers.FIRST_NAME,findAgentItem.getFirstname());
					MyRequirementListActivity myRequirementListActivity = new MyRequirementListActivity();
					myRequirementListActivity.setArguments(prefereBrokerBundle);
					((MainFragmentActivity)getContext()).redirectScreen(myRequirementListActivity);
				} else {
					Toast.makeText(getContext(), "No requirement found", Toast.LENGTH_LONG).show();
				}
			}
		});
	    
	    //on click of call button to make a call
	    LinearLayout findAgentCallLayout = (LinearLayout) view.findViewById(R.id.findAgentCallLayout);
	    findAgentCallLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(phonString.length() > 0) {
					Intent intent = new Intent(Intent.ACTION_CALL);
			    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    	intent.setData(Uri.parse("tel:"+phonString));
			    	v.getContext().getApplicationContext().startActivity(intent);
				} else {
					Toast.makeText(getContext(), "Number not available", Toast.LENGTH_LONG).show();
				}
			}
		});
		return view;
	}
}
