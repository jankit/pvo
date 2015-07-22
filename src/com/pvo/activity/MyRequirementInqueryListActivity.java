package com.pvo.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.custom.adapter.MyRequirementReceiveInquiryArrayAdaptor;
import com.pvo.custom.adapter.MyRequirementReceiveInquiryItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.MyRequirementViewReceiveInquiryService;
import com.pvo.util.Constant;

/**
 * This Is Used to display the List of the My Property Receive Inquiry
 * */
public class MyRequirementInqueryListActivity extends PVOFragment {
    private ListView myReqViewRecInqListView;
    private TextView myReqViewRecInqTitleTextView;
    private MyRequirementViewReceiveInquiryService myRequirementViewReceiveInquiryService;
    private MyRequirementReceiveInquiryArrayAdaptor myRequirementReceiveInquiryArrayAdaptor;
    private List<MyRequirementReceiveInquiryItem> inquiryItems = new ArrayList<MyRequirementReceiveInquiryItem>();
    private Bundle intent;
    private boolean flag_loading;
    private int pageCount = 1;
	
	/**
	 * set layout content view
	 */
    public MyRequirementInqueryListActivity() {
		setContentView(R.layout.activity_myrequirement_inquery_list);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//used to handle force close 
		//Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(getActivity()));
				
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		myRequirementViewReceiveInquiryService 	= new MyRequirementViewReceiveInquiryService();
		intent	= getArguments();
		
		myReqViewRecInqListView 				= (ListView) findViewById(R.id.myReqViewRecInqListView);
		myReqViewRecInqTitleTextView			= (TextView)findViewById(R.id.myReqViewRecInqTitleTextView);
		myReqViewRecInqTitleTextView.setText("Inquiry List for ID# "+intent.getString(Constant.MyRequirement.REQUIREMENT_ID));
		
		getMyPropertyRecords(pageCount);
		myReqViewRecInqListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				
				if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
	            {
	                if(flag_loading == false)
	                {
	                    flag_loading = true;
	                    getMyPropertyRecords(++pageCount);
	                }
	            }
				
			}
		});
	}
	
	/**
	 * Call web service and get records for given page number
	 * 
	 * @param pageNumber
	 */
	private void getMyPropertyRecords(int pageNumber) {                                                            
		
		new WebserviceClient(MyRequirementInqueryListActivity.this, myRequirementViewReceiveInquiryService).execute(
				intent.getString(Constant.MyRequirement.REQUIREMENT_ID), //Requirement ID
				String.valueOf(pageNumber), //Page number 
				"2" //Type 2 Means Requirement
				);
	}

	@Override
	public void processResponse(Object response) {
		
		JSONArray jsonArry = (JSONArray) response;
		
		try {
		
			if (jsonArry != null && !((JSONObject)jsonArry.get(0)).has(Constant.MyRequirementViewReceiveInquiry.API_STATUS)) {
				
				for (int i = 0; i < jsonArry.length(); i++) {
					
					JSONObject jsonObj = jsonArry.getJSONObject(i);
					
					MyRequirementReceiveInquiryItem myRequirementReceiveInquiryItem = new MyRequirementReceiveInquiryItem();
					myRequirementReceiveInquiryItem.setInquiryid(jsonObj.getString(Constant.MyPropertyViewReceiveInquiry.INQUIRY_ID));
					myRequirementReceiveInquiryItem.setName(jsonObj.getString(Constant.MyPropertyViewReceiveInquiry.NAME));
					myRequirementReceiveInquiryItem.setPhone(jsonObj.getString(Constant.MyPropertyViewReceiveInquiry.PHONE));
					myRequirementReceiveInquiryItem.setEmail(jsonObj.getString(Constant.MyPropertyViewReceiveInquiry.EMAIL));
					myRequirementReceiveInquiryItem.setMessage(jsonObj.getString(Constant.MyPropertyViewReceiveInquiry.MESSAGE));
					myRequirementReceiveInquiryItem.setDate(jsonObj.getString(Constant.MyPropertyViewReceiveInquiry.DATE));
					
					inquiryItems.add(myRequirementReceiveInquiryItem);
				}
				
				if(myRequirementReceiveInquiryArrayAdaptor == null) {
				
					myRequirementReceiveInquiryArrayAdaptor = new MyRequirementReceiveInquiryArrayAdaptor(getActivity(), R.id.myReqViewRecInqListView, inquiryItems);
					myReqViewRecInqListView.setAdapter(myRequirementReceiveInquiryArrayAdaptor);
					
				} else {
					
					myRequirementReceiveInquiryArrayAdaptor.notifyDataSetChanged();
					flag_loading = false;
				}
				
			} else {
				
				Toast.makeText(getActivity(),((JSONObject)jsonArry.get(0)).getString(Constant.MyRequirementViewReceiveInquiry.API_MESSAGE), Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Set title on resume **/
	@Override
	public void onResume() {
		super.onResume();
		
		getActivity().setTitle("Receive Inquiry");
	}
	/** END **/
	
}
