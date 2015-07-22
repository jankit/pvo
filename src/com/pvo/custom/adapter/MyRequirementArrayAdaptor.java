package com.pvo.custom.adapter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import z.com.pvo.util.ProjectUtility;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.pvo.activity.EmailSmsSendActivity;
import com.pvo.activity.MainFragmentActivity;
import com.pvo.activity.MyRequirementDetailActivity;
import com.pvo.activity.MyRequirementEditActivity;
import com.pvo.activity.MyRequirementInqueryListActivity;
import com.pvo.activity.R;
import com.pvo.components.NumberToWord;
import com.pvo.components.SpinnerItem;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.MyRequirementActiveService;
import com.pvo.user.service.MyRequirementDeleteService;
import com.pvo.user.service.MyRequirementInActiveService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.pvo.util.ConvertDateFormat;
import com.squareup.picasso.Picasso;

public class MyRequirementArrayAdaptor extends ArrayAdapter<MyRequirementItem> {
	
	private Boolean isPrint = true;
	private String TAG = "MyRequirementArrayAdaptor";
	
	private Context context;
	//Get Stored User Id From User SessionManager 
	private UserSessionManager userSessionManager;
	//Delete Property Service
	private MyRequirementDeleteService deleteMyRequirementService;
	private MyRequirementActiveService activeMyRequirementService;
	private MyRequirementInActiveService inActiveMyRequirementService;
	private List<MyRequirementItem> myRequirementList;
	
	public MyRequirementArrayAdaptor(Context context, int resourceId, List<MyRequirementItem> objects) {
		super(context, resourceId, objects);
		myRequirementList = objects;
		this.context = context;
	}

	@Override
	public View getView ( final int position, View view, ViewGroup parent ) {
		
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    view = inflater.inflate(R.layout.activity_myrequirement_list_row, parent, false);
	    userSessionManager = new UserSessionManager(view.getContext().getApplicationContext());
	    final MyRequirementItem requirementItem = getItem(position);
	    
	    ProjectUtility.sys(isPrint, TAG, "Position--> "+position);
		ProjectUtility.sys(isPrint, TAG, "Reruirement ID--> "+requirementItem.getRequirementid());
		
	    
		//change background color according to property status like active or inactive
	    final RelativeLayout myReqCustListlayout = (RelativeLayout) view.findViewById(R.id.myReqCustListlayout);
	    if(requirementItem.getStstatus().equals("0")){
	    	myReqCustListlayout.setBackgroundColor(0);
	    	myReqCustListlayout.setBackgroundResource(R.drawable.listview_inactive_item_customshape);
	    }
	    //END
		
		//set Image from encoded String return from response 
	    ImageView myReqImageView = (ImageView) view.findViewById(R.id.myReqImageView);
	    if(requirementItem.getLogoencoded() != null) 
	    	Picasso.with(context).load(requirementItem.getLogoencoded()).placeholder(R.drawable.no_image).into(myReqImageView);
	    else 
			Picasso.with(context).load(requirementItem.getPhotoencoded()).placeholder(R.drawable.no_image).into(myReqImageView);
		
	    //Requirement id
		TextView myReqRequirementIDTv = (TextView) view.findViewById(R.id.myReqRequirementIDTv);
		myReqRequirementIDTv.setText("PVOR "+requirementItem.getRequirementid());
	   
		//Price or rent 
		TextView myReqPriceRentTv = (TextView) view.findViewById(R.id.myReqPriceRentTv);
		if(requirementItem.getPurpose().equals("Sale")) {
			myReqPriceRentTv.setText(Html.fromHtml(toIndianRupessFormat(Double.parseDouble(requirementItem.getMinprice()))+"("+NumberToWord.numberToWord(requirementItem.getMinprice())+")"+" To "+toIndianRupessFormat(Double.parseDouble(requirementItem.getMaxprice()))+"("+NumberToWord.numberToWord(requirementItem.getMaxprice())+")"));
			myReqPriceRentTv.setMinLines(myReqPriceRentTv.getLineCount());
		} else {
			myReqPriceRentTv.setText(Html.fromHtml(toIndianRupessFormat(Double.parseDouble(requirementItem.getMinrent()))+"("+NumberToWord.numberToWord(requirementItem.getMinrent())+")"+" To "+toIndianRupessFormat(Double.parseDouble(requirementItem.getMaxrent()))+"("+NumberToWord.numberToWord(requirementItem.getMaxrent())+")"));
		    myReqPriceRentTv.setMinLines(myReqPriceRentTv.getLineCount());
		}
		
		//Location TextView
		TextView myReqCustLocationTv = (TextView) view.findViewById(R.id.myReqCustLocationTv);
		/*if(!requirementItem.getLocation1name().equals("") && requirementItem.getLocation2name().equals("")&& requirementItem.getLocation3name().equals("")){
			myReqCustLocationTv.setText(Html.fromHtml("<b>"+requirementItem.getLocation1name()));
			
		} else if(!requirementItem.getLocation1name().equals("") && !requirementItem.getLocation2name().equals("") && requirementItem.getLocation3name().equals("")) {
			myReqCustLocationTv.setText(Html.fromHtml("<b>"+requirementItem.getLocation1name()+","+requirementItem.getLocation2name()));
			//myReqCustLocationTv.setMinLines(myReqCustLocationTv.getLineCount());
		} else if(!requirementItem.getLocation1name().equals("") && !requirementItem.getLocation2name().equals("") && !requirementItem.getLocation3name().equals("")) {
			myReqCustLocationTv.setText(Html.fromHtml("<b>"+requirementItem.getLocation1name()+","+requirementItem.getLocation2name()+","+requirementItem.getLocation3name()));
			//myReqCustLocationTv.setMinLines(myReqCustLocationTv.getLineCount());
		}*/
		myReqCustLocationTv.setText(Html.fromHtml("<b>"+requirementItem.getAlllocationsname()));
		myReqCustLocationTv.setMinLines(myReqCustLocationTv.getLineCount());
		
	 	//Property Type For Sale Or Rent Text View
	 	TextView myReqCustPropertyTypeForSaleprRentTv = (TextView) view.findViewById(R.id.myReqCustPropertyTypeForSaleprRentTv);
	 	myReqCustPropertyTypeForSaleprRentTv.setText(requirementItem.getPropertytype()+ " For "+requirementItem.getPurpose());
	 	myReqCustPropertyTypeForSaleprRentTv.setMinLines(myReqCustPropertyTypeForSaleprRentTv.getLineCount());
	 	
	 	//Area Sq.Foot(Area measurement in SQ Foot in all property types. Only plot will have Sq Yard)
	    TextView myReqCustSqFootTv = (TextView) view.findViewById(R.id.myReqCustSqFootTv);
	    if(requirementItem.getPropertytype().equalsIgnoreCase("Shop")) {
	    	myReqCustSqFootTv.setText("Area: "+requirementItem.getMinsqfoot()+" To "+requirementItem.getMaxsqfoot()+" Sq.Yard\n"
	    			+"Plot Area: "+requirementItem.getMinplotarea()+" To "+requirementItem.getMaxplotarea()+" Sq.Yard\n"
	    			+"Const. Area: "+requirementItem.getMinconstructionarea()+" To "+requirementItem.getMaxconstructionarea()+" Sq.Yard");
	    } else if(requirementItem.getPropertytype().equalsIgnoreCase("Bunglow") || requirementItem.getPropertytype().equalsIgnoreCase("Plot")) {
	    	myReqCustSqFootTv.setText("Plot Area: "+requirementItem.getMinplotarea()+" To "+requirementItem.getMaxplotarea()+" Sq.Yard\n"
	    			+"Const. Area: "+requirementItem.getMinconstructionarea()+" To "+requirementItem.getMaxconstructionarea()+" Sq.Yard");
	    } else { 
	    	myReqCustSqFootTv.setText(requirementItem.getMinsqfoot()+" To "+requirementItem.getMaxsqfoot()+" Sq. Foot");
	    }
	    
	    myReqCustSqFootTv.setMinLines(myReqCustSqFootTv.getLineCount());
	   
	    //Min/Max Bed Text View
	    TextView myReqCustBedMinMaxTv = (TextView) view.findViewById(R.id.myReqCustBedMinMaxTv);
	    if(requirementItem.getPropertytype().equals("Plot") || requirementItem.getPropertytype().equalsIgnoreCase("Shop")) {
	    	myReqCustBedMinMaxTv.setVisibility(View.GONE);
	    } else {
	    	myReqCustBedMinMaxTv.setVisibility(View.VISIBLE);
	    	myReqCustBedMinMaxTv.setText(String.valueOf((SpinnerItem.getBedListKey(requirementItem.getMinbed())))+ " To "+(SpinnerItem.getBedListKey(requirementItem.getMaxbed()))+" Bed");
	    	myReqCustBedMinMaxTv.setMinLines(myReqCustBedMinMaxTv.getLineCount());
	    }
	    
	    //Posted By Broker Name
	    TextView myReqCustPostedeByTv = (TextView) view.findViewById(R.id.myReqCustPostedeByTv);
	    myReqCustPostedeByTv.setText("Posted By: "+userSessionManager.getSessionValue(Constant.Login.FIRST_NAME)+" "+userSessionManager.getSessionValue(Constant.Login.LAST_NAME));
	    myReqCustPostedeByTv.setMinLines(myReqCustPostedeByTv.getLineCount());
	    
	    //Posted on Date
	    TextView myReqCustPostedOnDateTv = (TextView) view.findViewById(R.id.myReqCustPostedOnDateTextView);
	    try {
	    	myReqCustPostedOnDateTv.setText((ConvertDateFormat.convertDateFormat(requirementItem.getDtadded())));
	    	myReqCustPostedOnDateTv.setMinLines(myReqCustPostedOnDateTv.getLineCount());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	    
	    //View receive inquiry counter
	 	TextView myReqViewInquiryCounter = (TextView) view.findViewById(R.id.myReqViewInquiryCounter);
	 	myReqViewInquiryCounter.setText("["+StringUtils.defaultIfBlank(requirementItem.getInquirycount(),"0")+"]");

	    //On click of layout open the my requirement detail page
	    myReqCustListlayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle myReqDetBundle = new Bundle();
				myReqDetBundle.putString("requirementid",requirementItem.getRequirementid());
				MyRequirementDetailActivity myRequirementDetailActivity = new MyRequirementDetailActivity();
				myRequirementDetailActivity.setArguments(myReqDetBundle);
				((MainFragmentActivity)getContext()).redirectScreen(myRequirementDetailActivity);
			}
		});
	    
		//Edit Button Click Listener for Edit Particular My Requirement in this Pass the Requirement Id to the Edit Requirement Activity
		LinearLayout myReqEditLayout = (LinearLayout) view.findViewById(R.id.myReqEditLayout);
		myReqEditLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle editMyReqBundle = new Bundle();
				editMyReqBundle.putString(Constant.MyRequirement.REQUIREMENT_ID,requirementItem.getRequirementid() );
				/*editMyReqBundle.putString(Constant.MyRequirement.PROPERTY_TYPE,requirementItem.getPropertytype());
				editMyReqBundle.putString(Constant.MyRequirement.PROPERTY_SUB_TYPE,requirementItem.getPropertysubtype());
				editMyReqBundle.putString(Constant.MyRequirement.ST_PURPOSE,requirementItem.getStpurpose());
				editMyReqBundle.putString(Constant.MyRequirement.PURPOSE,requirementItem.getPurpose());
				editMyReqBundle.putString(Constant.MyRequirement.LOCATION1_NAME,requirementItem.getLocation1name());
				editMyReqBundle.putString(Constant.MyRequirement.LOCATION2_NAME,requirementItem.getLocation2name());
				editMyReqBundle.putString(Constant.MyRequirement.LOCATION3_NAME,requirementItem.getLocation3name());
				editMyReqBundle.putString(Constant.MyRequirement.STATE_ID,requirementItem.getStateid());
				editMyReqBundle.putString(Constant.MyRequirement.CITY_ID,requirementItem.getCityid());
				editMyReqBundle.putString(Constant.MyRequirement.DISTRICT_ID,requirementItem.getDistrictid());
				editMyReqBundle.putString(Constant.MyRequirement.MAX_BED,requirementItem.getMaxbed());
				editMyReqBundle.putString(Constant.MyRequirement.MIN_BED,requirementItem.getMinbed());
				editMyReqBundle.putString(Constant.MyRequirement.MIN_FLOOR,requirementItem.getMinfloor());
				editMyReqBundle.putString(Constant.MyRequirement.MAX_FLOOR,requirementItem.getMaxfloor());
				editMyReqBundle.putString(Constant.MyRequirement.RISE,requirementItem.getRise());
				editMyReqBundle.putString(Constant.MyRequirement.MIN_SQFOOT,requirementItem.getMinsqfoot());
				editMyReqBundle.putString(Constant.MyRequirement.MAX_SQFOOT,requirementItem.getMaxsqfoot());
				editMyReqBundle.putString(Constant.MyRequirement.MIN_CONSTRUCTION_AREA,requirementItem.getMinconstructionarea());
				editMyReqBundle.putString(Constant.MyRequirement.MAX_CONSTRUCTION_AREA,requirementItem.getMaxconstructionarea());
				editMyReqBundle.putString(Constant.MyRequirement.MAX_PLOT_AREA,requirementItem.getMaxplotarea());
				editMyReqBundle.putString(Constant.MyRequirement.MIN_PLOT_AREA,requirementItem.getMinplotarea());
				editMyReqBundle.putString(Constant.MyRequirement.MIN_RENT,requirementItem.getMinrent());
				editMyReqBundle.putString(Constant.MyRequirement.MAX_RENT,requirementItem.getMaxrent());
				editMyReqBundle.putString(Constant.MyRequirement.MAX_PRICE,requirementItem.getMaxprice());
				editMyReqBundle.putString(Constant.MyRequirement.MIN_PRICE,requirementItem.getMinprice());
				editMyReqBundle.putString(Constant.MyRequirement.HINT,requirementItem.getHint());
				editMyReqBundle.putString(Constant.MyRequirement.KEYWORD,requirementItem.getKeyword());
				editMyReqBundle.putString(Constant.MyRequirement.TP,requirementItem.getTp());
				editMyReqBundle.putString(Constant.MyRequirement.ZONE,requirementItem.getZone());*/
				MyRequirementEditActivity editMyRequirementActivity = new MyRequirementEditActivity();
				editMyRequirementActivity.setArguments(editMyReqBundle);
				((MainFragmentActivity)getContext()).redirectScreen(editMyRequirementActivity);
			}
		});
		//END
		
		//Delete button is used to delete the particular Requirement,in this pass the Requirement Id to the delete web service of selected Requirement From List
		LinearLayout deleteLayout = (LinearLayout) view.findViewById(R.id.deleteLayout);
		deleteLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				deleteMyRequirementService=new MyRequirementDeleteService();
				new AlertDialog.Builder(getContext()).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Delete requirement").setMessage("Are you sure you want to delete this requirement?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		        
				@Override
		        public void onClick(DialogInterface dialog, int which) {
		        	WebserviceClient deletReqWebserviceClient=new WebserviceClient(v.getContext(),deleteMyRequirementService);
		        	deletReqWebserviceClient.setResponseListner(new ResponseListner() {
		        		@Override
						public void handleResponse(Object response) {
		        			JSONObject jsonObject=(JSONObject)response;
		        			if(jsonObject != null) {
								try {
									if (String.valueOf(jsonObject.getString(Constant.DeleteMyProperty.API_STATUS)).equals("1")) {
										Toast.makeText(v.getContext().getApplicationContext(),String.valueOf(jsonObject.get(Constant.DeleteMyProperty.API_MESSAGE)),Toast.LENGTH_LONG).show();
										myRequirementList.remove(position);
										notifyDataSetChanged();
									} else {
										Toast.makeText(v.getContext().getApplicationContext(),String.valueOf(jsonObject.getString(Constant.DeleteMyProperty.API_MESSAGE)),Toast.LENGTH_LONG).show();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}
					});
					deletReqWebserviceClient.execute(requirementItem.getRequirementid(),userSessionManager.getSessionValue(Constant.Login.USER_ID),requirementItem.getRequirementid());
		        }
		    }).setNegativeButton("No", null).show();	
			}
		});
		
		//Active button is used to Active the particular Requirement,* in this pass the Requirement Id to the ActiveRequirement web service of selected Requirement From List
		LinearLayout activeLayout = (LinearLayout) view.findViewById(R.id.activeLayout);
		activeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				activeMyRequirementService = new MyRequirementActiveService();
				WebserviceClient activeReqWebserviceClient=new WebserviceClient( v.getContext(),activeMyRequirementService);
				activeReqWebserviceClient.setResponseListner(new ResponseListner() {
					@Override
					public void handleResponse(Object response) {
						JSONObject jsonObject=(JSONObject)response;
						if(jsonObject != null) {
							try {
								if (String.valueOf(jsonObject.getString(Constant.ActiveMyRequirement.API_STATUS)).equals("1")) {
									Toast.makeText(v.getContext().getApplicationContext(),String.valueOf(jsonObject.get(Constant.ActiveMyRequirement.API_MESSAGE)),Toast.LENGTH_LONG).show();
									requirementItem.setStstatus("1");
									myReqCustListlayout.setBackgroundResource(R.drawable.listview_item_customshape);
								} else {
									Toast.makeText(v.getContext().getApplicationContext(),String.valueOf(jsonObject.getString(Constant.ActiveMyRequirement.API_MESSAGE)),Toast.LENGTH_LONG).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				});
				activeReqWebserviceClient.execute(userSessionManager.getSessionValue(Constant.Login.USER_ID),requirementItem.getRequirementid());
			}
		});
		
		//Inactive button is used to De-Active the particular Requirement,in this pass the Requirement Id to the InActiveRequirement web service of selected requirement From List
		LinearLayout inActiveLayout = (LinearLayout) view.findViewById(R.id.inActiveLayout);
		inActiveLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				inActiveMyRequirementService=new MyRequirementInActiveService();
				WebserviceClient activeReqWebserviceClient=new WebserviceClient(v.getContext(),inActiveMyRequirementService);
				activeReqWebserviceClient.setResponseListner(new ResponseListner() {
					@Override
					public void handleResponse(Object response) {
						JSONObject jsonObject=(JSONObject)response;
						if(jsonObject != null) {
							try {
								if (String.valueOf(jsonObject.getString(Constant.InActiveMyRequirement.API_STATUS)).equals("1")) {
									Toast.makeText(v.getContext().getApplicationContext(),String.valueOf(jsonObject.get(Constant.InActiveMyRequirement.API_MESSAGE)),Toast.LENGTH_LONG).show();
									requirementItem.setStstatus("0");
									myReqCustListlayout.setBackgroundResource(R.drawable.listview_inactive_item_customshape);
								} else {
									Toast.makeText(v.getContext().getApplicationContext(),String.valueOf(jsonObject.getString(Constant.InActiveMyRequirement.API_MESSAGE)),Toast.LENGTH_LONG).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				});
				activeReqWebserviceClient.execute(userSessionManager.getSessionValue(Constant.Login.USER_ID),requirementItem.getRequirementid());
			}
		});
		
		//Redirect to Email Sms Screen
		LinearLayout myReqEmailLayout = (LinearLayout) view.findViewById(R.id.myReqEmailLayout);
		myReqEmailLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle emailSmsBundle = new Bundle();
				emailSmsBundle.putString("propertyid", requirementItem.getRequirementid());
				emailSmsBundle.putString("EmailSms", "myRequirement");
				EmailSmsSendActivity emailSmsSendActivity = new EmailSmsSendActivity();
				emailSmsSendActivity.setArguments(emailSmsBundle);
				((MainFragmentActivity)getContext()).redirectScreen(emailSmsSendActivity);
			}
		});
		//END
		
		//My Requirement Vie receive inquiry
		LinearLayout myReqViewInquiryLayout = (LinearLayout)view.findViewById(R.id.myReqViewInquiryLayout);
		myReqViewInquiryLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle viewInquiryBundle = new Bundle();
				viewInquiryBundle.putString(Constant.MyRequirement.REQUIREMENT_ID, requirementItem.getRequirementid());
				MyRequirementInqueryListActivity requirementViewReceiveInqueryActivity = new MyRequirementInqueryListActivity();
				requirementViewReceiveInqueryActivity.setArguments(viewInquiryBundle);
				((MainFragmentActivity)getContext()).redirectScreen(requirementViewReceiveInqueryActivity);
			}
		});
		return view;
	}
	
	//This method is used to convert the price into indian rupess  format
	private String toIndianRupessFormat(double doubleValue) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
		if(nf.format(doubleValue).length() > 0 && nf.format(doubleValue).contains(".")) {
			return nf.format(doubleValue).substring(0,nf.format(doubleValue).length() - 3);
		} else {
			return "0";
		}
	}
}
