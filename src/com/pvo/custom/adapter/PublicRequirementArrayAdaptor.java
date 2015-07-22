package com.pvo.custom.adapter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.activity.EmailSmsSendActivity;
import com.pvo.activity.MainFragmentActivity;
import com.pvo.activity.MyRequirementDetailActivity;
import com.pvo.activity.R;
import com.pvo.components.NumberToWord;
import com.pvo.util.ConvertDateFormat;
import com.squareup.picasso.Picasso;

public class PublicRequirementArrayAdaptor extends ArrayAdapter<PublicRequirementItem> {
	
	private Context context;
	
	public PublicRequirementArrayAdaptor(Context context, int resourceId, List<PublicRequirementItem> objects) {
		super(context, resourceId, objects);
		this.context = context;
	}

	@Override
	public View getView ( int position, View view, ViewGroup parent ) {
		
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    view = inflater.inflate(R.layout.activity_publicrequirement_list_row, parent, false);
	    final PublicRequirementItem publicRequirementItem = getItem(position);
	    final String phonString;  
	    
	    ImageView publicReqImageView = (ImageView) view.findViewById(R.id.publicReqImageView);
	    if(publicRequirementItem.getLogoencoded() != null) 
			Picasso.with(context).load(publicRequirementItem.getLogoencoded()).placeholder(R.drawable.no_image).into(publicReqImageView);
		else 
			Picasso.with(context).load(publicRequirementItem.getPhotoencoded()).placeholder(R.drawable.no_image).into(publicReqImageView);
	    
	    //Requirement id
	    TextView publicReqCustRequirementIdTv = (TextView) view.findViewById(R.id.publicReqCustRequirementIdTv);
	    publicReqCustRequirementIdTv.setText("PVOR "+publicRequirementItem.getRequirementid());
	    
	    //Price or rent 
	 	TextView publicReqCustPreiceRentTv = (TextView) view.findViewById(R.id.publicReqCustPreiceRentTv);
	 	if(publicRequirementItem.getPurpose().equals("Sale")) {
	 		publicReqCustPreiceRentTv.setText(Html.fromHtml(
	 				toTwoDeciamlFormat(Double.parseDouble(publicRequirementItem.getMinprice()))
	 				+"("+NumberToWord.numberToWord(publicRequirementItem.getMinprice())+")"+" To "
	 				+toTwoDeciamlFormat(Double.parseDouble(publicRequirementItem.getMaxprice())))
	 				+"("+NumberToWord.numberToWord(publicRequirementItem.getMaxprice())+")");
	 		//publicReqCustPreiceRentTv.setMinLines(publicReqCustPreiceRentTv.getLineCount());
	 	} else {
	 		publicReqCustPreiceRentTv.setText(toTwoDeciamlFormat(Double.parseDouble(publicRequirementItem.getMinrent()))+"("+NumberToWord.numberToWord(publicRequirementItem.getMinrent())+") To "+
	 				toTwoDeciamlFormat(Double.parseDouble(publicRequirementItem.getMaxrent()))+"("+NumberToWord.numberToWord(publicRequirementItem.getMaxrent())+")");
	 		//publicReqCustPreiceRentTv.setMinLines(publicReqCustPreiceRentTv.getLineCount());
	 		
	 	}
	 	publicReqCustPreiceRentTv.setMinLines(publicReqCustPreiceRentTv.getLineCount());
	 	
	 	//Location TextView
	 	TextView publicReqCustLocationTv = (TextView) view.findViewById(R.id.publicReqCustLocationTv);
	 	/*if(!publicRequirementItem.getLocation1name().equals("") && publicRequirementItem.getLocation2name().equals("") && publicRequirementItem.getLocation3name().equals("")) {
	 		publicReqCustLocationTv.setText(Html.fromHtml("<b>"+publicRequirementItem.getLocation1name()));
	 		publicReqCustLocationTv.setMinLines(publicReqCustLocationTv.getLineCount());
	 	} else if(!publicRequirementItem.getLocation1name().equals("") && !publicRequirementItem.getLocation2name().equals("") && publicRequirementItem.getLocation3name().equals("")) {
	 		publicReqCustLocationTv.setText(Html.fromHtml("<b>"+publicRequirementItem.getLocation1name()+","+publicRequirementItem.getLocation2name()));
	 		publicReqCustLocationTv.setMinLines(publicReqCustLocationTv.getLineCount());
	 	} else if(!publicRequirementItem.getLocation1name().equals("") && !publicRequirementItem.getLocation2name().equals("") && !publicRequirementItem.getLocation3name().equals("")) {
	 		publicReqCustLocationTv.setText(Html.fromHtml("<b>"+publicRequirementItem.getLocation1name()+","+
	 				publicRequirementItem.getLocation2name()+","+
	 				publicRequirementItem.getLocation3name()));
	 		publicReqCustLocationTv.setMinLines(publicReqCustLocationTv.getLineCount());
	 	}*/
	 	publicReqCustLocationTv.setText(Html.fromHtml("<b>"+publicRequirementItem.getAlllocationsname()));
	 	publicReqCustLocationTv.setMinLines(publicReqCustLocationTv.getLineCount());
	 	//Property Type sale/rent
	 	TextView publicReqCustPropertyTypeSaleRentTv = (TextView) view.findViewById(R.id.publicReqCustPropertyTypeSaleRentTv);
	 	publicReqCustPropertyTypeSaleRentTv.setText(publicRequirementItem.getPropertytype()+" For "+publicRequirementItem.getPurpose());
	 	publicReqCustPropertyTypeSaleRentTv.setMinLines(publicReqCustPropertyTypeSaleRentTv.getLineCount());
	 	
	    //Area Sq.Foot(Area measurement in SQ Foot in all property types. Only plot will have Sq Yard)
	    TextView publicReqCustAreaSqFootTv = (TextView) view.findViewById(R.id.publicReqCustAreaSqFootTv);
	    
	    if(publicRequirementItem.getPropertytype().equalsIgnoreCase("Shop")) {
	    	publicReqCustAreaSqFootTv.setText("Area: "+publicRequirementItem.getMinsqfoot()+" To "+publicRequirementItem.getMaxsqfoot()+" Sq.Yard\n"
	    			+"Plot Area: "+publicRequirementItem.getMinplotarea()+" To "+publicRequirementItem.getMaxplotarea()+" Sq.Yard\n"
	    			+"Const. Area: "+publicRequirementItem.getMinconstrarea()+" To "+publicRequirementItem.getMaxconstrarea()+" Sq.Yard");
	    } else if(publicRequirementItem.getPropertytype().equalsIgnoreCase("Bunglow") || publicRequirementItem.getPropertytype().equalsIgnoreCase("Plot")) {
	    	publicReqCustAreaSqFootTv.setText("Plot Area: "+publicRequirementItem.getMinplotarea()+" To "+publicRequirementItem.getMaxplotarea()+" Sq.Yard\n"
	    			+"Const. Area: "+publicRequirementItem.getMinconstrarea()+" To "+publicRequirementItem.getMaxconstrarea()+" Sq.Yard");
	    } else { 
	    	publicReqCustAreaSqFootTv.setText(publicRequirementItem.getMinsqfoot()+" To "+publicRequirementItem.getMaxsqfoot()+" Sq. Foot");
	    }
	    
	    
	   /* 
	    if(publicRequirementItem.getPropertytype().equalsIgnoreCase("Plot")) {
	    	publicReqCustAreaSqFootTv.setText(publicRequirementItem.getMinplotarea()+" To "+ publicRequirementItem.getMaxplotarea()+" Sq.Yard");
	    } else {
	    	publicReqCustAreaSqFootTv.setText(publicRequirementItem.getMinsqfoot()+" To "+ publicRequirementItem.getMaxsqfoot()+" Sq.Foot");
	    }*/
	    publicReqCustAreaSqFootTv.setMinLines(publicReqCustAreaSqFootTv.getLineCount());
	    
	 	//Bed Text View
	    TextView publicReqCustBedTv = (TextView) view.findViewById(R.id.publicReqCustBedTv);
	    if(publicRequirementItem.getPropertytype().equals("Plot") || publicRequirementItem.getPropertytype().equalsIgnoreCase("Shop")) {
	    	publicReqCustBedTv.setVisibility(View.GONE);
	    } else {
		   publicReqCustBedTv.setVisibility(View.VISIBLE);
		   publicReqCustBedTv.setText(publicRequirementItem.getMinbed()+" To "+publicRequirementItem.getMaxbed()+" Bed");
		   publicReqCustBedTv.setMinLines(publicReqCustBedTv.getLineCount());
	    }
	   	    
	    //Posted by Broker name
	 	TextView publicReqCustPosedByBrokerNameTv = (TextView) view.findViewById(R.id.publicReqCustPosedByBrokerNameTv);
	 	publicReqCustPosedByBrokerNameTv.setText("Posted By: "+publicRequirementItem.getFirstname()+" "+publicRequirementItem.getLastname());
	 	publicReqCustPosedByBrokerNameTv.setMinLines(publicReqCustPosedByBrokerNameTv.getLineCount());
	
	 	//Phone Number
	 	TextView publicReqCustPhoneNumberTv = (TextView) view.findViewById(R.id.publicReqCustPhoneNumberTv);
	 	
	 	if(publicRequirementItem.getPhonem().contains(",")){
		   	//String[] split = publicRequirementItem.getPhonem().split(",");
		   	//publicReqCustPhoneNumberTv.setText("Phone No.: "+split[0]);
	 		phonString = publicRequirementItem.getPhonem().substring(0,publicRequirementItem.getPhonem().indexOf(","));
		} else {
			phonString = publicRequirementItem.getPhonem();
		}
	 	publicReqCustPhoneNumberTv.setText(Html.fromHtml("<b>"+"Phone(M): "+"</b>"+phonString));
	 	publicReqCustPhoneNumberTv.setMinLines(publicReqCustPosedByBrokerNameTv.getLineCount());
	 	
	 	//Posted on Date 
	 	TextView publicReqCustPostedDateTv = (TextView) view.findViewById(R.id.publicReqCustPostedDateTv);
	 	try {
	 		publicReqCustPostedDateTv.setText(ConvertDateFormat.convertDateFormat(publicRequirementItem.getDtadded()));
	 		publicReqCustPostedDateTv.setMinLines(publicReqCustPostedDateTv.getLineCount());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	    
	 	//Call Button 
	    ImageView publicReqCustCallButtonTv = (ImageView) view.findViewById(R.id.publicReqCustCallButtonTv);
	    publicReqCustCallButtonTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(phonString != null &&  phonString.length() > 0) {
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setData(Uri.parse("tel:"+phonString));
	                v.getContext().getApplicationContext().startActivity(intent);
				} else {
					Toast.makeText(getContext(), "Number not available", Toast.LENGTH_LONG).show();
				}
			}
		});
	    
	 	//on click of Layout Display the Full Detail of public Requirement
	 	RelativeLayout publicReqCustListlayout = (RelativeLayout) view.findViewById(R.id.publicReqCustListlayout);
	 	publicReqCustListlayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle myReqDetBundle = new Bundle();
				myReqDetBundle.putString("requirementid", publicRequirementItem.getRequirementid());
				myReqDetBundle.putString("page", publicRequirementItem.getPage());
				MyRequirementDetailActivity myRequirementDetailActivity = new  MyRequirementDetailActivity();
				myRequirementDetailActivity.setArguments(myReqDetBundle);
				((MainFragmentActivity)getContext()).redirectScreen(myRequirementDetailActivity);
			}
		});
	 	
	 	//Redirect to Email Sms Screen 
		ImageView publicReqCustSmsButton = (ImageView) view.findViewById(R.id.publicReqCustSmsButtonTv);
		publicReqCustSmsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle emailSmsBundle = new Bundle();
				emailSmsBundle.putString("propertyid", publicRequirementItem.getRequirementid());
				emailSmsBundle.putString("EmailSms", "publicRequirement");
				EmailSmsSendActivity emailSmsSendActivity = new EmailSmsSendActivity();
				emailSmsSendActivity.setArguments(emailSmsBundle);
				((MainFragmentActivity)getContext()).redirectScreen(emailSmsSendActivity);
			}
		});
		return view;
	}
	
	
	
	//This method is used to convert the price into indian rupess  format
	private String toTwoDeciamlFormat(double doubleValue) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
		if(nf.format(doubleValue).length() > 0 && nf.format(doubleValue).contains(".")) {
			return nf.format(doubleValue).substring(0,nf.format(doubleValue).length() - 3);
		} else {
			return "0";
		}
	}
	
	//Convert number decimal format
	/*private String toTwoDeciamlFormat(double doubleValue) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
		return nf.format(doubleValue);
	}*/
}
