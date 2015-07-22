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

import com.pvo.activity.R;
import com.pvo.util.ConvertDateFormat;

public class MyRequirementReceiveInquiryArrayAdaptor extends ArrayAdapter<MyRequirementReceiveInquiryItem> {

	public MyRequirementReceiveInquiryArrayAdaptor(Context context, int resourceId, List<MyRequirementReceiveInquiryItem> objects) {
		super(context, resourceId, objects);
	}

	@Override
	public View getView ( int position, View view, ViewGroup parent ) {
		
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    view = inflater.inflate(R.layout.activity_myrequirement_inquiry_list_row, parent, false);
	    final MyRequirementReceiveInquiryItem myRequirementReceiveInquiryItem = getItem(position);
	    
	    TextView myPropViewInquIDTv = (TextView) view.findViewById(R.id.myReqViewInquTv1);
	    TextView myPropViewInquIDValTv = (TextView) view.findViewById(R.id.myReqViewInquTv2);
	    myPropViewInquIDValTv.setText(myRequirementReceiveInquiryItem.getInquiryid());
	    myPropViewInquIDTv.setMinLines(myPropViewInquIDValTv.getLineCount());
	    myPropViewInquIDValTv.setMinLines(myPropViewInquIDValTv.getLineCount());

	    TextView myPropViewInquNameTv = (TextView) view.findViewById(R.id.myReqViewInquTv3);
	    TextView myPropViewInquNameValTv = (TextView) view.findViewById(R.id.myReqViewInquTv4);
	    myPropViewInquNameValTv.setText(myRequirementReceiveInquiryItem.getName());
	    myPropViewInquNameTv.setMinLines(myPropViewInquNameValTv.getLineCount());
	    myPropViewInquNameValTv.setMinLines(myPropViewInquNameValTv.getLineCount());
	    
	    TextView myPropViewInquPhoneNumberTv = (TextView) view.findViewById(R.id.myReqViewInquTv5);
	    TextView myPropViewInquPhoneNumberValTv = (TextView) view.findViewById(R.id.myReqViewInquTv6);
	    myPropViewInquPhoneNumberValTv.setText(myRequirementReceiveInquiryItem.getPhone());
	    myPropViewInquPhoneNumberTv.setMinLines(myPropViewInquPhoneNumberValTv.getLineCount());
	    myPropViewInquPhoneNumberValTv.setMinLines(myPropViewInquPhoneNumberValTv.getLineCount());
	    
	    TextView myPropViewInquEmailTv = (TextView) view.findViewById(R.id.myReqViewInquTv7);
	    TextView myPropViewInquEmailValTv = (TextView) view.findViewById(R.id.myReqViewInquTv8);
	    myPropViewInquEmailValTv.setText(myRequirementReceiveInquiryItem.getEmail()+"\n");
	    myPropViewInquEmailTv.setMinimumHeight(myPropViewInquEmailValTv.getLineCount());
	    myPropViewInquEmailValTv.setMinLines(myPropViewInquEmailValTv.getLineCount());
	    
	    TextView myPropViewInquMessageTv = (TextView) view.findViewById(R.id.myReqViewInquTv9);
	    TextView myPropViewInquMessageValTv = (TextView) view.findViewById(R.id.myReqViewInquTv10);
	    myPropViewInquMessageValTv.setText(myRequirementReceiveInquiryItem.getMessage());
	    myPropViewInquMessageTv.setMinLines(myPropViewInquMessageValTv.getLineCount());
	    myPropViewInquMessageValTv.setMinLines(myPropViewInquMessageValTv.getLineCount());
	    
	    TextView myPropViewInquDateTv = (TextView) view.findViewById(R.id.myReqViewInquTv11);
	    TextView myPropViewInquDateValTv = (TextView) view.findViewById(R.id.myReqViewInquTv12);
	    
	    try {
	    	myPropViewInquDateValTv.setText(ConvertDateFormat.convertDateFormat(myRequirementReceiveInquiryItem.getDate()));
			myPropViewInquDateTv.setMinLines(myPropViewInquDateValTv.getLineCount());
			myPropViewInquDateValTv.setMinLines(myPropViewInquDateValTv.getLineCount());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    
	    ImageView myReqViewInqCustCallButtonTv = (ImageView) view.findViewById(R.id.myReqViewInqCustCallButtonTv);
	    myReqViewInqCustCallButtonTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setData(Uri.parse("tel:"+ myRequirementReceiveInquiryItem.getPhone()));
                v.getContext().getApplicationContext().startActivity(intent);
			}
		});
		return view;
	}
}
