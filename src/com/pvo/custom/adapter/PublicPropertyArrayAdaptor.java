package com.pvo.custom.adapter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import z.com.pvo.newActivity.ZPropertyDetail;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.activity.MainFragmentActivity;
import com.pvo.activity.R;
import com.pvo.util.ConvertDateFormat;
import com.squareup.picasso.Picasso;

public class PublicPropertyArrayAdaptor extends ArrayAdapter<PublicPropertyItem> {
	
	private Context context;
	private boolean favFalg = true;
	
	public PublicPropertyArrayAdaptor(Context context, int resourceId, List<PublicPropertyItem> objects) {
		super(context, resourceId, objects);
		this.context = context;
	}

	@Override
	public View getView ( int position, View view, ViewGroup parent ) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    view = inflater.inflate(R.layout.z_prop_list_row, parent, false);
	    final PublicPropertyItem publicPropertyItem = getItem(position);
	    
	    
	    LinearLayout ll_swipe_favCallIcon = (LinearLayout) view.findViewById(R.id.ll_swipe_favCallIcon);
	    ll_swipe_favCallIcon.setVisibility(View.VISIBLE);
	    
	    LinearLayout ll_swip_icon = (LinearLayout)view.findViewById(R.id.ll_swip_icon);
	    ll_swip_icon.setVisibility(View.GONE);
	    
	    /*
	     * Property image 
	     * Set the property image from server
	     */
		ImageView iv_propListRow_propPhoto = (ImageView) view.findViewById(R.id.iv_propListRow_propPhoto);
		if(publicPropertyItem.getLogoencoded() != null)
			Picasso.with(context).load(publicPropertyItem.getLogoencoded()).placeholder(R.drawable.no_image).into(iv_propListRow_propPhoto);
		else 
			Picasso.with(context).load(publicPropertyItem.getPhotoencoded()).placeholder(R.drawable.no_image).into(iv_propListRow_propPhoto);
		
		/*
	     * Favotire icon
	     * On click of favorite icon add property into favorite
	     */
	    final ImageView iv_propListRow_fav = (ImageView) view.findViewById(R.id.iv_propListRow_fav);
	    iv_propListRow_fav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(favFalg) {
					favFalg = false;
					iv_propListRow_fav.setImageResource(R.drawable.fav_star_act);
				} else {
					favFalg = true;
					iv_propListRow_fav.setImageResource(R.drawable.fav_star_dis);
				}
			}
		});
	    
	    /*
	     * Call button click 
	     */
	    final String phonString;  
	  //Phone Number
	 	if(publicPropertyItem.getPhonem().contains(",")){
		   	phonString = publicPropertyItem.getPhonem().substring(0,publicPropertyItem.getPhonem().indexOf(","));
		   } else {
			   phonString = publicPropertyItem.getPhonem();
		}
	 	
	    
	    ImageView iv_propListRow_call = (ImageView) view.findViewById(R.id.iv_propListRow_call);
	    iv_propListRow_call.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(phonString != null &&  phonString.length() > 0) {
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setData(Uri.parse("tel:"+ phonString));
	                v.getContext().getApplicationContext().startActivity(intent);
				} else {
					Toast.makeText(getContext(), "Number not available", Toast.LENGTH_LONG).show();
				}	
			}
		});
	    
	    
	    /*
	     * On click of layout redirect to property detail page
	     */
	    LinearLayout ll_propListRow_detail = (LinearLayout) view.findViewById(R.id.ll_propListRow_detail);
	    ll_propListRow_detail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle fullDetailBundle = new Bundle();
				fullDetailBundle.putString("propertyid",publicPropertyItem.getPropertyid());
			    ZPropertyDetail myPropertyDetailActivity = new ZPropertyDetail();
				myPropertyDetailActivity.setArguments(fullDetailBundle);
				((MainFragmentActivity)getContext()).redirectScreen(myPropertyDetailActivity);
			}
		});
	    
	    
	    
	    /*
	     * Property type and sale/rent
	     */
	    TextView tv_propListRow_title = (TextView) view.findViewById(R.id.tv_propListRow_title);
	    tv_propListRow_title.setText(publicPropertyItem.getPropertytype()+" for "+publicPropertyItem.getStroptions()+"/"+publicPropertyItem.getPropertyid());
	    
	    /*
	     * Price 
	     */
	    TextView tv_propListRow_price = (TextView) view.findViewById(R.id.tv_propListRow_price);
	    tv_propListRow_price.setText(toIndianRupessFormat(Double.parseDouble(publicPropertyItem.getPrice())));
	   
	    
	    //String str_bed,str_area,str_bath;
	    /*
	     * Bed textview
	     */
	    TextView tv_propListRow_bed = (TextView) view.findViewById(R.id.tv_propListRow_bed);
	    /*if(publicPropertyItem.getPropertytype().equalsIgnoreCase("Plot")) {
	    	tv_propListRow_bed.setVisibility(View.GONE);
	    } else {
	    	if(publicPropertyItem.getBed().length() > 0 && !publicPropertyItem.getBed().equals("0")) {
	    		//str_bed = propertyItem.getBed();
	    		tv_propListRow_bed.setVisibility(View.VISIBLE);
	    		tv_propListRow_bed.setText(publicPropertyItem.getBed()+" BHK "+publicPropertyItem.getPropertytype()+" | ");
	    	} else {
	    		tv_propListRow_bed.setVisibility(View.GONE);
	    	}
	    }*/
	    
	    if(publicPropertyItem.getPropertytype().equalsIgnoreCase("Plot")) {
	    	//tv_propListRow_bed.setVisibility(View.GONE);
	    	tv_propListRow_bed.setText(publicPropertyItem.getMinarea()+" "+publicPropertyItem.getAreaunit());
	    } else if(publicPropertyItem.getPropertytype().equalsIgnoreCase("Shop")) {
	    	//if(propertyItem.getBed().length() > 0 && !propertyItem.getBed().equals("0")) {
	    		//tv_propListRow_bed.setVisibility(View.VISIBLE);
	    	tv_propListRow_bed.setText(publicPropertyItem.getMinarea()+" "+publicPropertyItem.getAreaunit()+" | "+publicPropertyItem.getBathRoom()+" Bathrooms");
	    	/*} else {
	    		tv_propListRow_bed.setVisibility(View.GONE);
	    	}*/
	    } else {
	    	tv_propListRow_bed.setText(publicPropertyItem.getBed()+" BHK "+publicPropertyItem.getPropertytype()+" | "+publicPropertyItem.getMinarea()+" "+publicPropertyItem.getAreaunit()+" | "+publicPropertyItem.getBathRoom()+" Bathrooms");
	    }
	    
	    /*
	     * Area text view
	     */
	    /*TextView tv_propListRow_area = (TextView) view.findViewById(R.id.tv_propListRow_area);
	    if (publicPropertyItem.getMinarea().length()>0 &&!publicPropertyItem.getMinarea().equals("0.00") ) {
	    	//str_area = propertyItem.getMinarea();
	    	tv_propListRow_area.setText(publicPropertyItem.getMinarea()+" "+publicPropertyItem.getAreaunit()+" | ");
		} else {
			tv_propListRow_area.setVisibility(View.GONE);
		}*/
	    
	    /*
	     * Bath room 
	     */
	    /*TextView tv_propListRow_bathroom = (TextView) view.findViewById(R.id.tv_propListRow_bathroom);
	    if(publicPropertyItem.getPropertytype().equalsIgnoreCase("Plot")) {
	    	tv_propListRow_bathroom.setVisibility(View.GONE);
	    } else {
	    	if(publicPropertyItem.getBathRoom().length() > 0 && !publicPropertyItem.getBathRoom().equals("0")) {
	    		//str_bath = propertyItem.getBathroom();
	    		tv_propListRow_bathroom.setVisibility(View.VISIBLE);
	    		tv_propListRow_bathroom.setText(publicPropertyItem.getBathRoom()+" Bathrooms");
	    	}
	    }*/
	    
	   
	    /*
	     * Bed/area/bath
	     */
	    /*TextView tv_propListRow_areaBedBath = (TextView) view.findViewById(R.id.tv_propListRow_areaBedBath);
	    if(propertyItem.getPropertyType().equals("Plot") || propertyItem.getPropertyType().equalsIgnoreCase("Shop")) {
	    	tv_propListRow_areaBedBath.setText("");
	    } else {
	    	tv_propListRow_areaBedBath.setText(propertyItem.getMinArea()+" | 2 Bathrooms");
	   }*/
	   
	    /*
	     * Company name
	     */
	   TextView tv_propListRow_cmp = (TextView) view.findViewById(R.id.tv_propListRow_cmp);
	   tv_propListRow_cmp.setText(publicPropertyItem.getCompanyname());
	   
	    
	   /*
	    * Date
	    * Property posted date  
	    */
	   TextView tv_propListRow_date = (TextView) view.findViewById(R.id.tv_propListRow_date);
	    try {
			tv_propListRow_date.setText(ConvertDateFormat.convertDateFormat(publicPropertyItem.getAddDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    
	    /*
	     * Location (Area) 
	     */
	    TextView tv_propListRow_location = (TextView) view.findViewById(R.id.tv_propListRow_location);
	    tv_propListRow_location.setText(publicPropertyItem.getAreaname());
	    
	    
	    
	    /*
	     
	    final String phonString;  
	    //set Image from encoded String return from response 
	    ImageView publicProp2ImageView = (ImageView) view.findViewById(R.id.publicPropImageView);
	    if(publicPropertyItem.getLogoencoded() != null) 
			Picasso.with(context).load(publicPropertyItem.getLogoencoded()).placeholder(R.drawable.no_image).into(publicProp2ImageView);
		else 
			Picasso.with(context).load(publicPropertyItem.getPhotoencoded()).placeholder(R.drawable.no_image).into(publicProp2ImageView);
	    
	    //property id 
	    TextView publicPropCustPropertyIdTv = (TextView) view.findViewById(R.id.publicPropCustPropertyIdTv);
	    publicPropCustPropertyIdTv.setText("PVO "+publicPropertyItem.getPropertyid());
	    
	    //Price or rent 
	 	TextView publicPropCustPreiceRentTv = (TextView) view.findViewById(R.id.publicPropCustPreiceRentTv);
	 	if(publicPropertyItem.getStroptions().equals("Sale")) {
	 		publicPropCustPreiceRentTv.setText(Html.fromHtml(toIndianRupessFormat(Double.parseDouble(publicPropertyItem.getPrice())))+"("+NumberToWord.numberToWord(publicPropertyItem.getPrice())+")");
	 		publicPropCustPreiceRentTv.setMinLines(publicPropCustPreiceRentTv.getLineCount());
	 	} else {
	 		publicPropCustPreiceRentTv.setText(Html.fromHtml(toIndianRupessFormat(Double.parseDouble(publicPropertyItem.getRent())))+"("+NumberToWord.numberToWord(publicPropertyItem.getRent())+")");
	 		publicPropCustPreiceRentTv.setMinLines(publicPropCustPreiceRentTv.getLineCount());
	 	}
	   
	 	//Location 
	 	TextView publicPropCustLocationTv = (TextView) view.findViewById(R.id.publicPropCustLocationTv);
	 	//publicPropCustLocationTv.setText(Html.fromHtml("<b>"+publicPropertyItem.getAddress()));
	
	    if(!publicPropertyItem.getAreaname().equals("") && publicPropertyItem.getLandmark1name().equals("") && publicPropertyItem.getLandmark2name().equals("")) 
	    	publicPropCustLocationTv.setText(Html.fromHtml("<b>"+publicPropertyItem.getAddress()+","+publicPropertyItem.getAreaname()));
	    else if(!publicPropertyItem.getAreaname().equals("")&& !publicPropertyItem.getLandmark1name().equals("")&& publicPropertyItem.getLandmark2name().equals(""))
	    	publicPropCustLocationTv.setText(Html.fromHtml("<b>"+publicPropertyItem.getAreaname()+","+publicPropertyItem.getLandmark1name()));
	    else if(!publicPropertyItem.getAreaname().equals("") && !publicPropertyItem.getLandmark1name().equals("") && !publicPropertyItem.getLandmark2name().equals(""))
	    	publicPropCustLocationTv.setText(Html.fromHtml("<b>"+publicPropertyItem.getAreaname()+","+publicPropertyItem.getLandmark1name()+","+publicPropertyItem.getLandmark2name()));

	 	publicPropCustLocationTv.setMinLines(publicPropCustLocationTv.getLineCount());
	    
	 	//Property Type sale/rent
	 	TextView publicPropCustPropertyTypeSaleRentTv = (TextView) view.findViewById(R.id.publicPropCustPropertyTypeSaleRentTv);
	 	publicPropCustPropertyTypeSaleRentTv.setText(publicPropertyItem.getPropertytype()+" For "+publicPropertyItem.getStroptions());
	 	publicPropCustPropertyTypeSaleRentTv.setMinLines(publicPropCustPropertyTypeSaleRentTv.getLineCount());
	 	
	    //Area Sq.Foot(Area measurement in SQ Foot in all property types. Only plot will have Sq Yard)
	    TextView publicPropCustAreaSqFootTv = (TextView) view.findViewById(R.id.publicPropCustAreaSqFootTv);
	    if(publicPropertyItem.getPropertytype().equalsIgnoreCase("Flat")) {
	    	publicPropCustAreaSqFootTv.setText(publicPropertyItem.getMinarea()+" "+publicPropertyItem.getAreaunit());
	    } else if(publicPropertyItem.getPropertytype().equalsIgnoreCase("Bunglow")) {
	    	publicPropCustAreaSqFootTv.setText("Plot Area:"+publicPropertyItem.getPlotarea()+" "+publicPropertyItem.getPlotareaunit()+"\nConst. Area:"+publicPropertyItem.getConstructionarea()+" "+publicPropertyItem.getAreaunit());
	    } else if(publicPropertyItem.getPropertytype().equalsIgnoreCase("Shop") && !publicPropertyItem.getPropertysubtype().equalsIgnoreCase("Godown")) {
	    	publicPropCustAreaSqFootTv.setText("Area:"+publicPropertyItem.getMinarea()+" "+publicPropertyItem.getAreaunit()+"\nPlot Area:"+publicPropertyItem.getPlotarea()+" "+publicPropertyItem.getPlotareaunit()+"\nConst. Area:"+publicPropertyItem.getConstructionarea()+" "+publicPropertyItem.getAreaunit());
	    } else if(publicPropertyItem.getPropertytype().equalsIgnoreCase("Plot")) {
	    	publicPropCustAreaSqFootTv.setText("Plot Area:"+publicPropertyItem.getPlotarea()+" "+publicPropertyItem.getPlotareaunit()+"\nConst. Area:"+publicPropertyItem.getConstructionarea()+" "+publicPropertyItem.getAreaunit());
	    } else if(publicPropertyItem.getPropertysubtype().equalsIgnoreCase("Godown")) {
	    	publicPropCustAreaSqFootTv.setText("Area:"+publicPropertyItem.getMinarea()+" "+publicPropertyItem.getAreaunit());
	    }
	    publicPropCustAreaSqFootTv.setMinLines(publicPropCustAreaSqFootTv.getLineCount());
	 	
	    //Bed Text View
	    TextView publicPropCustBedTv = (TextView) view.findViewById(R.id.publicPropCustBedTv);
	    if(publicPropertyItem.getPropertytype().equals("Plot") ||publicPropertyItem.getPropertytype().equalsIgnoreCase("Shop")) {
	    	publicPropCustBedTv.setVisibility(View.GONE);
	   } else {
		   publicPropCustBedTv.setVisibility(View.VISIBLE);
		   publicPropCustBedTv.setText(publicPropertyItem.getBed()+" Bed");
		   publicPropCustBedTv.setMinLines(publicPropCustBedTv.getLineCount());
	   }
	   	    
	    //Posted by Broker name
	 	TextView publicPropCustPosedByBrokerNameTv = (TextView) view.findViewById(R.id.publicPropCustPosedByBrokerNameTv);
	 	publicPropCustPosedByBrokerNameTv.setText("Posted By: "+publicPropertyItem.getFirstName()+" "+publicPropertyItem.getLastName());
	 	publicPropCustPosedByBrokerNameTv.setMinLines(publicPropCustPosedByBrokerNameTv.getLineCount());
	 	
	 	//Phone Number
	 	TextView publicPropCustPhoneNoTv = (TextView) view.findViewById(R.id.publicPropCustPhoneNumberTv);
	 	if(publicPropertyItem.getPhonem().contains(",")){
		   	phonString = publicPropertyItem.getPhonem().substring(0,publicPropertyItem.getPhonem().indexOf(","));
		   } else {
			   phonString = publicPropertyItem.getPhonem();
		}
	 	publicPropCustPhoneNoTv.setText(Html.fromHtml("<b>"+"Phone(M): "+"</b>"+phonString));
	 	publicPropCustPhoneNoTv.setMinLines(publicPropCustPosedByBrokerNameTv.getLineCount());
	 	
	 	
	 	//Posted on Date 
	 	TextView publicPropCustPostedDateTv = (TextView) view.findViewById(R.id.publicPropCustPostedDateTv);
	 	try {
	 		publicPropCustPostedDateTv.setText(ConvertDateFormat.convertDateFormat(publicPropertyItem.getAddDate()));
	 		publicPropCustPostedDateTv.setMinLines(publicPropCustPostedDateTv.getLineCount());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	 	
	 	//Nominee name and mobile number
	 	TextView publicPropCustNomineeNameTv = (TextView) view.findViewById(R.id.publicPropCustNomineeNameTv);
	 	TextView publicPropCustNomineePhoneTv = (TextView) view.findViewById(R.id.publicPropCustNomineePhoneTv);
	 	
	 	if(!publicPropertyItem.getNominee().equalsIgnoreCase("NA")) {
	 		publicPropCustNomineeNameTv.setVisibility(View.VISIBLE);
	 		publicPropCustNomineePhoneTv.setVisibility(View.VISIBLE);
	 		//Name
	 		publicPropCustNomineeNameTv.setText("Nominee Name: "+publicPropertyItem.getNominee_name());
	 		publicPropCustNomineeNameTv.setMinLines(publicPropCustNomineeNameTv.getLineCount());
	 		//number
	 		publicPropCustNomineePhoneTv.setText("Nominee Number: "+publicPropertyItem.getNominee_mobile_no());
	 		publicPropCustNomineePhoneTv.setMinLines(publicPropCustNomineePhoneTv.getLineCount());
	 	}

	 	
	 	 //Call Button
	    ImageView publicPropCustCallButtonTv = (ImageView) view.findViewById(R.id.publicPropCustCallButtonTv);
	    publicPropCustCallButtonTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(phonString != null &&  phonString.length() > 0) {
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setData(Uri.parse("tel:"+ phonString));
	                v.getContext().getApplicationContext().startActivity(intent);
				} else {
					Toast.makeText(getContext(), "Number not available", Toast.LENGTH_LONG).show();
				}
			}
		});
	    
	 	//on click of first text view of the public property list 
	 	RelativeLayout publicPropCustListlayout = (RelativeLayout) view.findViewById(R.id.publicPropCustListlayout);
	 	publicPropCustListlayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle myPropDetBundle = new Bundle();
				myPropDetBundle.putString("propertyid", publicPropertyItem.getPropertyid());
				myPropDetBundle.putString("page", publicPropertyItem.getPage());
				MyPropertyDetailActivity myPropertyDetailActivity = new MyPropertyDetailActivity();
				myPropertyDetailActivity.setArguments(myPropDetBundle);
				((MainFragmentActivity)getContext()).redirectScreen(myPropertyDetailActivity);
			}
		});
	    
	    //Onclick Of Message Icon
	 	ImageView publicPropCustSmsButton = (ImageView) view.findViewById(R.id.publicPropCustSmsButtonTv);
	 	publicPropCustSmsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle publicPropEmailSms = new Bundle();
				publicPropEmailSms.putString("propertyid", publicPropertyItem.getPropertyid());
				publicPropEmailSms.putString("EmailSms", "publicProperty");
				EmailSmsSendActivity emailSmsSendActivity = new EmailSmsSendActivity();
				emailSmsSendActivity.setArguments(publicPropEmailSms);
				((MainFragmentActivity)getContext()).redirectScreen(emailSmsSendActivity);
			}
		});
	 	
	 	*/
	    
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
