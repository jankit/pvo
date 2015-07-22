package com.pvo.custom.adapter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.pvo.activity.MyPropertyDetailActivity;
import com.pvo.activity.R;
import com.pvo.components.NumberToWord;
import com.pvo.util.ConvertDateFormat;
import com.squareup.picasso.Picasso;

public class SearchPropertyArrayAdaptor extends ArrayAdapter<SearchPropertyItem> {

	private Context context;
	
	public SearchPropertyArrayAdaptor(Context context, int resourceId, List<SearchPropertyItem> objects) {
		super(context, resourceId, objects);
		this.context = context;
	}

	@Override
	public View getView ( int position, View view, ViewGroup parent ) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    view = inflater.inflate(R.layout.activity_searchproperty_list_row, parent, false);
	    final SearchPropertyItem searchPropertyItem = getItem(position);
	    
	   //set Image from encoded String return from response 
	   ImageView searchPropCustListImageView = (ImageView) view.findViewById(R.id.searchPropCustListImageView);
	   if(searchPropertyItem.getLogoencoded() != null) 
		   Picasso.with(context).load(searchPropertyItem.getLogoencoded()).placeholder(R.drawable.no_image).into(searchPropCustListImageView);
		else 
			Picasso.with(context).load(searchPropertyItem.getPhotoencoded()).placeholder(R.drawable.no_image).into(searchPropCustListImageView);
		    
	   //Call Button 
	   ImageView searchPropCallButton = (ImageView) view.findViewById(R.id.searchPropCallButton);
	   searchPropCallButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(searchPropertyItem.getPhonem() != null &&  searchPropertyItem.getPhonem().length() > 0) {
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setData(Uri.parse("tel:"+searchPropertyItem.getPhonem()));
		            v.getContext().getApplicationContext().startActivity(intent);
				} else {
					Toast.makeText(getContext(), "Number not available", Toast.LENGTH_LONG).show();
				}
			}
	   });
	    
	   //Property id 
	   TextView searchPropCustPropertyIdTv = (TextView) view.findViewById(R.id.searchPropCustPropertyIdTv);
	   searchPropCustPropertyIdTv.setText("PVO "+searchPropertyItem.getPropertyid());
	   
	   //Price / Rent / purpose(Sale/Rent)
	    TextView searchPropCustPriceRentTv = (TextView) view.findViewById(R.id.searchPropCustPriceRentTv);
	    if(searchPropertyItem.getStroptions().equals("Sale")) 
	    	searchPropCustPriceRentTv.setText(toIndianRupessFormat(Double.parseDouble(searchPropertyItem.getTotalprice()))+"("+NumberToWord.numberToWord(searchPropertyItem.getTotalprice())+")");
	    else 
	    	searchPropCustPriceRentTv.setText(toIndianRupessFormat(Double.parseDouble(searchPropertyItem.getRent()))+"("+NumberToWord.numberToWord(searchPropertyItem.getRent())+")");
	    
	    searchPropCustPriceRentTv.setMinLines(searchPropCustPriceRentTv.getLineCount());
	    
	    //Location 
	    TextView searchPropCustLocationTv = (TextView) view.findViewById(R.id.searchPropCustLocationTv);
	    if(!searchPropertyItem.getAreaname().equals("") && searchPropertyItem.getLandmark1name().equals("") && searchPropertyItem.getLandmark2name().equals("")) 
	    	searchPropCustLocationTv.setText(searchPropertyItem.getAreaname());
        else if(!searchPropertyItem.getAreaname().equals("") && !searchPropertyItem.getLandmark1name().equals("") && searchPropertyItem.getLandmark2name().equals("")) 
        	searchPropCustLocationTv.setText(searchPropertyItem.getAreaname()+","+searchPropertyItem.getLandmark1name());
        else if(!searchPropertyItem.getAreaname().equals("")&& !searchPropertyItem.getLandmark1name().equals("")&& !searchPropertyItem.getLandmark2name().equals("")) 
        	searchPropCustLocationTv.setText(searchPropertyItem.getAreaname()+","+searchPropertyItem.getLandmark1name()+","+searchPropertyItem.getLandmark2name());
	    searchPropCustLocationTv.setMinLines(searchPropCustLocationTv.getLineCount());
	    
	    //Property type For sale Rent
	    TextView searchPropCustPropertyTypeRentSaleTv = (TextView) view.findViewById(R.id.searchPropCustPropertyTypeRentSaleTv);
	    searchPropCustPropertyTypeRentSaleTv.setText(searchPropertyItem.getPropertytype()+" For "+searchPropertyItem.getStroptions());
	    searchPropCustPropertyTypeRentSaleTv.setMinLines(searchPropCustPropertyTypeRentSaleTv.getLineCount());
	    
	    //Area Sq.Foot(Area measurement in SQ Foot in all property types. Only plot will have Sq Yard)
	    TextView searchPropCustAreaSqFootTv = (TextView) view.findViewById(R.id.searchPropCustAreaSqFootTv);
	    if(searchPropertyItem.getPropertytype().equalsIgnoreCase("Plot"))
	    	searchPropCustAreaSqFootTv.setText(searchPropertyItem.getMinarea()+" Sq.Yard");//+" To "+searchPropertyItem.getMaxarea()+" Sq.Yard");
	    else 
	    	searchPropCustAreaSqFootTv.setText(searchPropertyItem.getMinarea()+" Sq.Foot");//+" To "+searchPropertyItem.getMaxarea()+" Sq.Foot");
	    
	    searchPropCustAreaSqFootTv.setMinLines(searchPropCustAreaSqFootTv.getLineCount());
	    
	    //Bed Text view
	    TextView searchPropCustBedTv = (TextView) view.findViewById(R.id.searchPropCustBedTv);
	    searchPropCustBedTv.setText(searchPropertyItem.getBed()+" Bed");
	    searchPropCustBedTv.setMinLines(searchPropCustBedTv.getLineCount());
	    
	    //Posted By Broker name
	    TextView searchPropCustPostedByBrokerNaemTv = (TextView) view.findViewById(R.id.searchPropCustPostedByBrokerNaemTv);
	    searchPropCustPostedByBrokerNaemTv.setText("Posted By: "+searchPropertyItem.getFirstname()+" "+searchPropertyItem.getLastname());
	    searchPropCustPostedByBrokerNaemTv.setMinLines(searchPropCustPostedByBrokerNaemTv.getLineCount());
	    
	    //Phone No.
	    TextView searchPropCustPhoneNumberTv = (TextView) view.findViewById(R.id.searchPropCustPhoneNumberTv);
	    if(searchPropertyItem.getPhonem().contains("-")){
	    	String[] split = searchPropertyItem.getPhonem().split("-");
		    searchPropCustPhoneNumberTv.setText("Phone No.: "+split[0]);
	    } else {
	    	searchPropCustPhoneNumberTv.setText("Phone No.: "+searchPropertyItem.getPhonem());
	    }
	    searchPropCustPhoneNumberTv.setMinLines(searchPropCustPostedByBrokerNaemTv.getLineCount());
        
	    //Posted Date
	    TextView searchPropCustPostedDateTv = (TextView) view.findViewById(R.id.searchPropCustPostedDateTv);
	    try {
	    	searchPropCustPostedDateTv.setText(ConvertDateFormat.convertDateFormat(searchPropertyItem.getDtadded()));
		    searchPropCustPostedDateTv.setMinLines(searchPropCustPostedDateTv.getLineCount());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    
	    //Message Button Click Listener for Send Email/SMS to Particular Search Property 
		//in this Pass the Property Id to the EmailSmsSend Activity
		ImageView searchPropCustSmsButton = (ImageView) view.findViewById(R.id.searchPropCustSmsButtonTv);
		searchPropCustSmsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle searchPropEmailSmsBundle = new Bundle();
				searchPropEmailSmsBundle.putString("propertyid", searchPropertyItem.getPropertyid());
				searchPropEmailSmsBundle.putString("EmailSms", "searchProperty");
				EmailSmsSendActivity emailSmsSendActivity = new EmailSmsSendActivity();
				emailSmsSendActivity.setArguments(searchPropEmailSmsBundle);
				((MainFragmentActivity)getContext()).redirectScreen(emailSmsSendActivity);
			}
		});
			
		//On click Of Layout Display the Selected Search Property full Detail
		RelativeLayout searchPropCustListlayout = (RelativeLayout) view.findViewById(R.id.searchPropCustListlayout);
		searchPropCustListlayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle searchPropDetBundle = new Bundle();
				searchPropDetBundle.putString("propertyid",searchPropertyItem.getPropertyid());
				MyPropertyDetailActivity myPropertyDetailActivity = new MyPropertyDetailActivity();
				myPropertyDetailActivity.setArguments(searchPropDetBundle);
				((MainFragmentActivity)getContext()).redirectScreen(myPropertyDetailActivity);
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
