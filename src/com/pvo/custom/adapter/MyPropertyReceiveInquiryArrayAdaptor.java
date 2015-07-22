package com.pvo.custom.adapter;

import java.text.ParseException;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.activity.R;
import com.pvo.util.ConvertDateFormat;

public class MyPropertyReceiveInquiryArrayAdaptor extends ArrayAdapter<MyPropertyReceiveInquiryItem> {

	public MyPropertyReceiveInquiryArrayAdaptor(Context context, int resourceId, List<MyPropertyReceiveInquiryItem> objects) {
		super(context, resourceId, objects);
	}

	  @Override
	  public View getView ( int position, View view, ViewGroup parent ) {
		
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    view = inflater.inflate(R.layout.activity_myproperty_inquiry_list_row, parent, false);
	    final MyPropertyReceiveInquiryItem myPropertyReceiveInquiryItem = getItem(position);
	    
	    //Inquiry By 
	    TextView myPropViewInquIDTv = (TextView) view.findViewById(R.id.myPropViewRecInqInquiryByTv);
	    TextView myPropViewInquIDValTv = (TextView) view.findViewById(R.id.myPropViewRecInqInquiryByVal);
	    myPropViewInquIDValTv.setText(myPropertyReceiveInquiryItem.getBrokername());
	    myPropViewInquIDTv.setMinLines(myPropViewInquIDValTv.getLineCount());
	    myPropViewInquIDValTv.setMinLines(myPropViewInquIDValTv.getLineCount());

	    //Name Text view
	    TextView myPropViewInquNameTv = (TextView) view.findViewById(R.id.myPropViewRecInqNameTv);
	    TextView myPropViewInquNameValTv = (TextView) view.findViewById(R.id.myPropViewRecInqNameVal);
	    myPropViewInquNameValTv.setText(myPropertyReceiveInquiryItem.getCompanyname());
	    myPropViewInquNameTv.setMinLines(myPropViewInquNameValTv.getLineCount());
	    myPropViewInquNameValTv.setMinLines(myPropViewInquNameValTv.getLineCount());
	    
	    //Phone number
	    TextView myPropViewInquPhoneNumberTv = (TextView) view.findViewById(R.id.myPropViewRecInqPhoneTv);
	    TextView myPropViewInquPhoneNumberValTv = (TextView) view.findViewById(R.id.myPropViewRecInqPhoneVal);
	    myPropViewInquPhoneNumberValTv.setText(myPropertyReceiveInquiryItem.getPhone());
	    myPropViewInquPhoneNumberTv.setMinLines(myPropViewInquPhoneNumberValTv.getLineCount());
	    myPropViewInquPhoneNumberValTv.setMinLines(myPropViewInquPhoneNumberValTv.getLineCount());
	    
	    //Email Text view
	    TextView myPropViewInquEmailTv = (TextView) view.findViewById(R.id.myPropViewRecInqEmailTv);
	    TextView myPropViewInquEmailValTv = (TextView) view.findViewById(R.id.myPropViewRecInqEmailVal);
	    myPropViewInquEmailValTv.setText(myPropertyReceiveInquiryItem.getEmail());
	    myPropViewInquEmailTv.setEllipsize(myPropViewInquEmailValTv.getEllipsize());
	    myPropViewInquEmailValTv.setEllipsize(myPropViewInquEmailValTv.getEllipsize());
	   
	    //Message Text view 
	    //TextView myPropViewInquMessageTv = (TextView) view.findViewById(R.id.myPropViewRecInqMessageTv);
	    
	    TextView myPropViewInquMessageValTv = (TextView) view.findViewById(R.id.myPropViewRecInqMessageVal);
	    myPropViewInquMessageValTv.setText(myPropertyReceiveInquiryItem.getMessage());
	    
	    //Date Text view 
	    TextView myPropViewInquDateTv = (TextView) view.findViewById(R.id.myPropViewRecInqDateTv);
	    TextView myPropViewInquDateValTv = (TextView) view.findViewById(R.id.myPropViewRecInqDateVal);
	    try {
			myPropViewInquDateValTv.setText(ConvertDateFormat.convertDateFormat(myPropertyReceiveInquiryItem.getDate()));
			myPropViewInquDateTv.setMinLines(myPropViewInquDateValTv.getLineCount());
			myPropViewInquDateValTv.setMinLines(myPropViewInquDateValTv.getLineCount());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    
	    //Call button Image view 
	    ImageView myPropViewInqCustCallButtonTv = (ImageView) view.findViewById(R.id.myPropViewInqCustCallButtonTv);
	    myPropViewInqCustCallButtonTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(myPropertyReceiveInquiryItem.getPhone().length() > 0 && !myPropertyReceiveInquiryItem.getPhone().equals(null)) {
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setData(Uri.parse("tel:"+ myPropertyReceiveInquiryItem.getPhone()));
					//Starting the Dialer activity
	                v.getContext().getApplicationContext().startActivity(intent);
				} else {
					Toast.makeText(getContext(), "Number is not available", Toast.LENGTH_LONG).show();
				}
			}
		});
	    
		return view;
	}
}
