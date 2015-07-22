package com.pvo.custom.adapter;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pvo.activity.AdsDetailActivity;
import com.pvo.activity.DashboardProjectActivity;
import com.pvo.activity.MainFragmentActivity;
import com.pvo.activity.R;
import com.pvo.components.NumberToWord;
import com.pvo.util.Constant;
import com.squareup.picasso.Picasso;

public class AdsListingArrayAdaptor extends ArrayAdapter<AdsListingItem> {
	
	public final static String PROJECT_DETAIL = "projectdeatil";
	private String projectDeatail;
	private Context context;
	
	public AdsListingArrayAdaptor(Context context, int resourceId, List<AdsListingItem> objects,String profectDeatail) {
		super(context, resourceId, objects);
		this.context = context;
		this.projectDeatail = profectDeatail;
	}

	@Override
	  public View getView ( int position, View view, ViewGroup parent ) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		// create a new view of my layout and inflate it in the row
	    view = inflater.inflate(R.layout.activity_ads_list_row, parent, false);
	    final AdsListingItem adsListingItem = getItem(position);

	    // Property Logo
	    ImageView propertylogo = (ImageView) view.findViewById(R.id.adsListingCustImageView);
	    Picasso.with(context).load(adsListingItem.getPropertylogo()).placeholder(R.drawable.no_image).into(propertylogo);
	    
	    //Ads Title
	    TextView adsListingCustTitle = (TextView) view.findViewById(R.id.adsListingCustTitle);
	    adsListingCustTitle.setText(adsListingItem.getTitle());
	    adsListingCustTitle.setMinLines(adsListingCustTitle.getLineCount());
	    
	    //Price 
	    TextView adsPriceTv = (TextView) view.findViewById(R.id.adsListingCustPrice);
	    adsPriceTv.setText(toIndianRupessFormat(Double.parseDouble(adsListingItem.getStartprice()))+"("+NumberToWord.numberToWord(adsListingItem.getStartprice())+")"+" To "+toIndianRupessFormat(Double.parseDouble(adsListingItem.getEndprice()))+"("+NumberToWord.numberToWord(adsListingItem.getEndprice())+")");
	    adsPriceTv.setMinLines(adsPriceTv.getLineCount());
	    
	    //Address 
	    TextView adsAddressTv = (TextView) view.findViewById(R.id.adsListingCustAddress);
	    adsAddressTv.setText(adsListingItem.getAddress());
	    adsAddressTv.setMinLines(adsAddressTv.getLineCount());
	    
	    //Property type
	    TextView adsListingCustProprtyType = (TextView) view.findViewById(R.id.adsListingCustProprtyType);
	    adsListingCustProprtyType.setText(adsListingItem.getPropertytype());
	    adsListingCustProprtyType.setMinLines(adsListingCustProprtyType.getLineCount());
	    
	    //Builder name
	    TextView adsListingCustBuilderName = (TextView) view.findViewById(R.id.adsListingCustBuilderName);
	    adsListingCustBuilderName.setText(adsListingItem.getBuildername());
	    adsListingCustBuilderName.setMinLines(adsListingCustBuilderName.getLineCount());

	    //Contact number
	    TextView adsListingCustContactNUmber = (TextView) view.findViewById(R.id.adsListingCustContactNUmber);
	    adsListingCustContactNUmber.setText(adsListingItem.getContactnumber());
	    adsListingCustContactNUmber.setMinLines(adsListingCustContactNUmber.getLineCount());
	    
	   //Email
	   TextView adsListingCustEmail = (TextView) view.findViewById(R.id.adsListingCustEmail);
	   adsListingCustEmail.setText(adsListingItem.getEmail());
	   adsListingCustEmail.setMinLines(adsListingCustEmail.getLineCount());
	   
	 //Contact person
	   TextView adsListingCustcontactperson = (TextView) view.findViewById(R.id.adsListingCustcontactperson);
	   if(adsListingItem.getContactperson().length() > 0 && adsListingItem.getContactperson() != null)
		   adsListingCustcontactperson.setText(adsListingItem.getContactperson());
	   else 
		   adsListingCustcontactperson.setText("Not mention");
	   adsListingCustcontactperson.setMinLines(adsListingCustcontactperson.getLineCount());
	    
	    
	    //Detail 
	  /*  TextView adsDetailTv = (TextView) view.findViewById(R.id.adsListingCustDetail);
	    adsDetailTv.setText(adsListingItem.getDetail());
	    adsDetailTv.setMinLines(adsDetailTv.getLineCount());
	   */
	    //Display the View page of particular ads
	    RelativeLayout adsListingCustLayout = (RelativeLayout) view.findViewById(R.id.adsListingCustLayout);
	    adsListingCustLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle adsBundle = new Bundle();
				System.out.println("Ads id -----> "+adsListingItem.getProjectid());
				adsBundle.putString(Constant.AdsDetail.PROJECT_ID,adsListingItem.getProjectid());
				adsBundle.putString(Constant.AdsListing.TITLE,adsListingItem.getTitle());
				adsBundle.putString(Constant.AdsListing.PRICE_PDF,adsListingItem.getPricepdf());
				adsBundle.putString(Constant.AdsListing.FLOOR_PLAN,adsListingItem.getFloorplan());
				
				AdsDetailActivity adsDetailActivity = new AdsDetailActivity();
				adsDetailActivity.setArguments(adsBundle);
				
				if(projectDeatail == null) {
					adsBundle.putString(PROJECT_DETAIL,"Project");
					((MainFragmentActivity)getContext()).redirectScreen(adsDetailActivity);
				} else if(projectDeatail.equals("DashboardProjectDetail")){
					adsBundle.putString(PROJECT_DETAIL,"Dashboard");
					//((MainFragmentActivity)getContext()).redirectScreen(adsDetailActivity);
					((DashboardProjectActivity)getContext()).getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, adsDetailActivity).commit();
				}
			}
		});
		return view;
	}
	
	//This method is used to convert the price into indian rupess  format
	private String toIndianRupessFormat(double doubleValue) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
		return nf.format(doubleValue);
	}
}
