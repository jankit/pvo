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

import com.pvo.activity.EmailSmsSendActivity;
import com.pvo.activity.MainFragmentActivity;
import com.pvo.activity.MyRequirementDetailActivity;
import com.pvo.activity.R;
import com.pvo.components.NumberToWord;
import com.pvo.util.ConvertDateFormat;
import com.squareup.picasso.Picasso;

public class SearchRequirementArrayAdaptor extends ArrayAdapter<SearchRequirementItem> {

	private Context context;
	
	public SearchRequirementArrayAdaptor(Context context, int resourceId, List<SearchRequirementItem> objects) {
		super(context, resourceId, objects);
		this.context = context;
	}
	
	@Override
	public View getView ( int position, View view, ViewGroup parent ) {
	
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    view = inflater.inflate(R.layout.activity_searchrequirement_list_row, parent, false);
	    final SearchRequirementItem searchRequirementItem = getItem(position);
	    
	    //set Image from encoded String return from response
	    ImageView searchReqCustListImageView = (ImageView) view.findViewById(R.id.searchReqCustListImageView);
	    if(searchRequirementItem.getLogoencoded() != null) {
	    	Picasso.with(context).load(searchRequirementItem.getLogoencoded()).placeholder(R.drawable.no_image).into(searchReqCustListImageView);
		} else {
			Picasso.with(context).load(searchRequirementItem.getLogoencoded()).placeholder(R.drawable.no_image).into(searchReqCustListImageView);
		}
		    
	    
	    // Call Button 
	    ImageView searchReqCallButton = (ImageView) view.findViewById(R.id.searchReqCallButton);
	    searchReqCallButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setData(Uri.parse("tel:"+searchRequirementItem.getPhonem()));
                v.getContext().getApplicationContext().startActivity(intent);
			}
		});
	 
	    //Requirement id
	    TextView searchReqCusRequirementIdTv = (TextView) view.findViewById(R.id.searchReqCusRequirementIdTv);
	    searchReqCusRequirementIdTv.setText("PVOR "+searchRequirementItem.getRequirementid());
	    
	    //Price/Rent Textview 
	    TextView searchReqCustPriceRentTv = (TextView) view.findViewById(R.id.searchReqCustPriceRentTv);
	    if(searchRequirementItem.getPurpose().equals("Sale")) {
	    	searchReqCustPriceRentTv.setText(Html.fromHtml(toIndianRupessFormat(Double.parseDouble(searchRequirementItem.getMinprice()))+"("+NumberToWord.numberToWord(searchRequirementItem.getMinprice())+") To "+toIndianRupessFormat(Double.parseDouble(searchRequirementItem.getMaxprice())))+"("+NumberToWord.numberToWord(searchRequirementItem.getMaxprice())+")");
	    } else {
	    	searchReqCustPriceRentTv.setText(Html.fromHtml(toIndianRupessFormat(Double.parseDouble(searchRequirementItem.getMinrent()))+"("+NumberToWord.numberToWord(searchRequirementItem.getMinrent())+") To "+toIndianRupessFormat(Double.parseDouble(searchRequirementItem.getMaxrent())))+"("+NumberToWord.numberToWord(searchRequirementItem.getMaxrent())+")");
	    }
	    searchReqCustPriceRentTv.setMinLines(searchReqCustPriceRentTv.getLineCount());
	    
	    //Location 
	    TextView searchReqCustLocationTv = (TextView) view.findViewById(R.id.searchReqCustLocationTv);
	    searchReqCustLocationTv.setText(searchRequirementItem.getAlllocationsname());
	    /*if(!searchRequirementItem.getLocation1name().equals("")&& searchRequirementItem.getLocation2name().equals("") && searchRequirementItem.getLocation3name().equals("")) {
	    	 searchReqCustLocationTv.setText(searchRequirementItem.getLocation1name());
	    } else if(!searchRequirementItem.getLocation1name().equals("")&& !searchRequirementItem.getLocation2name().equals("")&& searchRequirementItem.getLocation3name().equals("")) {
	    	searchReqCustLocationTv.setText(searchRequirementItem.getLocation1name()+","+searchRequirementItem.getLocation2name());
	    } else if(!searchRequirementItem.getLocation1name().equals("")&& !searchRequirementItem.getLocation2name().equals("")&& !searchRequirementItem.getLocation3name().equals("")) {
	    	searchReqCustLocationTv.setText(searchRequirementItem.getLocation1name()+","+searchRequirementItem.getLocation2name()+","+searchRequirementItem.getLocation3name());
	    }*/
	    searchReqCustLocationTv.setMinLines(searchReqCustLocationTv.getLineCount());

	    //Property Type for Sale/Rent
	    TextView searchReqCustPropertyTypeSaleRentTv = (TextView) view.findViewById(R.id.searchReqCustPropertyTypeSaleRentTv);
	    
	    searchReqCustPropertyTypeSaleRentTv.setText(searchRequirementItem.getPropertytype()+" For "+searchRequirementItem.getPurpose());
	    searchReqCustPropertyTypeSaleRentTv.setMinLines(searchReqCustPropertyTypeSaleRentTv.getLineCount());
	
	    //Area Sq.Foot
	    TextView searchReqCustAreaSqFootTv = (TextView) view.findViewById(R.id.searchReqCustAreaSqFootTv);
	    if(searchRequirementItem.getPropertytype().equalsIgnoreCase("Shop")) {
	    	searchReqCustAreaSqFootTv.setText("Area: "+searchRequirementItem.getMinsqfoot()+" To "+searchRequirementItem.getMaxsqfoot()+" Sq.Yard\n"
	    			+"Plot Area: "+searchRequirementItem.getMinplotarea()+" To "+searchRequirementItem.getMaxplotarea()+" Sq.Yard\n"
	    			+"Const. Area: "+searchRequirementItem.getMinconstrarea()+" To "+searchRequirementItem.getMaxconstrarea()+" Sq.Yard");
	    } else if(searchRequirementItem.getPropertytype().equalsIgnoreCase("Bunglow") || searchRequirementItem.getPropertytype().equalsIgnoreCase("Plot")) {
	    	searchReqCustAreaSqFootTv.setText("Plot Area: "+searchRequirementItem.getMinplotarea()+" To "+searchRequirementItem.getMaxplotarea()+" Sq.Yard\n"
	    			+"Const. Area: "+searchRequirementItem.getMinconstrarea()+" To "+searchRequirementItem.getMaxconstrarea()+" Sq.Yard");
	    } else { 
	    	searchReqCustAreaSqFootTv.setText(searchRequirementItem.getMinsqfoot()+" To "+searchRequirementItem.getMaxsqfoot()+" Sq. Foot");
	    }
	    //searchReqCustAreaSqFootTv.setText(searchRequirementItem.getMinsqfoot()+" To "+searchRequirementItem.getMaxsqfoot()+" Sq.foot");
	    searchReqCustAreaSqFootTv.setMinLines(searchReqCustAreaSqFootTv.getLineCount());
	  
	    //Bed
	    TextView searchReqCustBedTv = (TextView) view.findViewById(R.id.searchReqCustBedTv);
	    if(searchRequirementItem.getPropertytype().equals("Shop") || searchRequirementItem.getPropertytype().equals("Plot") ) {
	    	searchReqCustBedTv.setVisibility(View.GONE);
	    } else {
	    	searchReqCustBedTv.setVisibility(View.VISIBLE);
	    	searchReqCustBedTv.setText(searchRequirementItem.getMinbed()+" To "+searchRequirementItem.getMaxbed()+" Bed");
		    searchReqCustBedTv.setMinLines(searchReqCustBedTv.getLineCount());	
	    }
	    
	    
	    //Posted By Broker Name
	    TextView searchReqCustPostedByBrokerNameTv = (TextView) view.findViewById(R.id.searchReqCustPostedByBrokerNameTv);
	    searchReqCustPostedByBrokerNameTv.setText("Posted By: "+searchRequirementItem.getFirstname()+" "+searchRequirementItem.getLastname());
	    searchReqCustPostedByBrokerNameTv.setMinLines(searchReqCustPostedByBrokerNameTv.getLineCount());
	    
	    //Phone number
	    TextView searchReqCustPhoneNumberTv = (TextView) view.findViewById(R.id.searchReqCustPhoneNumberTv);
	    if(searchRequirementItem.getPhonem().contains("-")){
	    	String[] split = searchRequirementItem.getPhonem().split("-");
		    searchReqCustPhoneNumberTv.setText("Phone No.: "+split[0]);
	    } else {
	    	searchReqCustPhoneNumberTv.setText("Phone No.: "+searchRequirementItem.getPhonem());
	    }
	    searchReqCustPhoneNumberTv.setMinLines(searchReqCustPhoneNumberTv.getLineCount());
	    
	    //Posted Date
	    TextView searchReqCustPostedDateTv = (TextView) view.findViewById(R.id.searchReqCustPostedDateTv);
	    try {
	    	searchReqCustPostedDateTv.setText(ConvertDateFormat.convertDateFormat(searchRequirementItem.getDtadded()));
			searchReqCustPostedDateTv.setMinLines(searchReqCustPostedDateTv.getLineCount());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	  
		//On click Of Layout Display the Selected Search Requirement full Detail
	    RelativeLayout searchReqCustListlayout = (RelativeLayout) view.findViewById(R.id.searchReqCustListlayout);
	    searchReqCustListlayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle myReqDetBundle = new Bundle();
				myReqDetBundle.putString("requirementid",searchRequirementItem.getRequirementid());
				MyRequirementDetailActivity myRequirementDetailActivity = new MyRequirementDetailActivity();
				myRequirementDetailActivity.setArguments(myReqDetBundle);
				((MainFragmentActivity)getContext()).redirectScreen(myRequirementDetailActivity);
			
			}
		});

	    //open EmailSms Activity onClick Of Message Button
	    ImageView searchReqCustSmsButton = (ImageView) view.findViewById(R.id.searchReqCustSmsButtonTv);
	    searchReqCustSmsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle searchReqEmailSmsBundle = new Bundle();
				searchReqEmailSmsBundle.putString("EmailSms", "searchRequirement");
				EmailSmsSendActivity emailSmsSendActivity = new EmailSmsSendActivity();
				emailSmsSendActivity.setArguments(searchReqEmailSmsBundle);
				((MainFragmentActivity)getContext()).redirectScreen(emailSmsSendActivity);
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
