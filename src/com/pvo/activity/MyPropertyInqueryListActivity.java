package com.pvo.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.custom.adapter.MyPropertyReceiveInquiryArrayAdaptor;
import com.pvo.custom.adapter.MyPropertyReceiveInquiryItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.MyPropertyViewReceiveInquiryService;
import com.pvo.util.Constant;

/**
 * This Is Used to display the List of the My Property Receive Inquiry
 * */
public class MyPropertyInqueryListActivity extends PVOFragment {
   
	private ListView myPropViewRecInqListView;
    private TextView myPropViewRecInqTitleTextView;
    private Button myPropertyInquiryBackBtn;
    private boolean flag_loading;
    private int pageCount = 1;
    private List<MyPropertyReceiveInquiryItem> inquiryItems = new ArrayList<MyPropertyReceiveInquiryItem>();
    private MyPropertyReceiveInquiryArrayAdaptor receiveInquiryArrayAdaptor;
    private MyPropertyViewReceiveInquiryService myPropertyViewReceiveInquiryService;
    private Bundle intent;

	
    /**
     * set layout content view 
     */
	public MyPropertyInqueryListActivity() {
		setContentView(R.layout.activity_myproperty_inquery_list);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//used to handle force close 
		//Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(getActivity()));
		
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		intent = getArguments();
		myPropertyViewReceiveInquiryService = new MyPropertyViewReceiveInquiryService();
		
		myPropViewRecInqListView 		= (ListView) findViewById(R.id.myPropViewRecInqListView);
		myPropViewRecInqTitleTextView	= (TextView)findViewById(R.id.myPropViewRecInqTitleTextView);
		myPropViewRecInqTitleTextView.setText("Inquiry List for ID# "+intent.getString("propertyid"));
		
		getMyPropertyRecords(pageCount);
		myPropViewRecInqListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				
				if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0) {
	                
					if(flag_loading == false) {
	                    
						flag_loading = true;
	                    getMyPropertyRecords(++pageCount);
	                }
	            }
				
			}
		});
		
		//on click of back button redirect the previous screen
		myPropertyInquiryBackBtn = (Button) findViewById(R.id.myPropertyInquiryBackBtn);
		myPropertyInquiryBackBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});
		
	}

	/**
	 * Call web service and get records for given page number
	 * 
	 * @param pageNumber
	 */
	private void getMyPropertyRecords(int pageNumber) {                                                            
		new WebserviceClient(MyPropertyInqueryListActivity.this, myPropertyViewReceiveInquiryService).execute(intent.getString("propertyid"), String.valueOf(pageNumber));
	}
	@Override
	public void processResponse(Object response) {
		
		JSONArray jsonArry = (JSONArray) response;
		
		try {
		
			if (jsonArry != null && !((JSONObject)jsonArry.get(0)).has(Constant.MyPropertyViewReceiveInquiry.API_STATUS)) {
				for (int i = 0; i < jsonArry.length(); i++) {
					JSONObject jsonObj = jsonArry.getJSONObject(i);
					MyPropertyReceiveInquiryItem myPropertyReceiveInquiryItem = new MyPropertyReceiveInquiryItem();
					myPropertyReceiveInquiryItem.setInquiryid(jsonObj.getString(Constant.MyPropertyViewReceiveInquiry.INQUIRY_ID));
					myPropertyReceiveInquiryItem.setName(jsonObj.getString(Constant.MyPropertyViewReceiveInquiry.NAME));
					myPropertyReceiveInquiryItem.setPhone(jsonObj.getString(Constant.MyPropertyViewReceiveInquiry.PHONE));
					myPropertyReceiveInquiryItem.setEmail(jsonObj.getString(Constant.MyPropertyViewReceiveInquiry.EMAIL));
					myPropertyReceiveInquiryItem.setMessage(jsonObj.getString(Constant.MyPropertyViewReceiveInquiry.MESSAGE));
					myPropertyReceiveInquiryItem.setDate(jsonObj.getString(Constant.MyPropertyViewReceiveInquiry.DATE));
					myPropertyReceiveInquiryItem.setBrokername(jsonObj.getString(Constant.MyPropertyViewReceiveInquiry.BROKER_NAME));
					myPropertyReceiveInquiryItem.setCompanyname(jsonObj.getString(Constant.MyPropertyViewReceiveInquiry.COMPANY_NAME));
					inquiryItems.add(myPropertyReceiveInquiryItem);
				}
				
				if(receiveInquiryArrayAdaptor == null) {
				
					receiveInquiryArrayAdaptor = new MyPropertyReceiveInquiryArrayAdaptor(getActivity(), R.id.myPropViewRecInqListView, inquiryItems);
					myPropViewRecInqListView.setAdapter(receiveInquiryArrayAdaptor);
					
				} else {
					
					receiveInquiryArrayAdaptor.notifyDataSetChanged();
					flag_loading = false;
				}
				
			} else {
				
				if(receiveInquiryArrayAdaptor == null && flag_loading == true){
				
					Toast.makeText(getActivity(),((JSONObject)jsonArry.get(0)).getString(Constant.MyPropertyViewReceiveInquiry.API_MESSAGE), Toast.LENGTH_LONG).show();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    //this.getActivity().getActionBar().setTitle("View Received Inquiry");
	    MainFragmentActivity.setTitle("View Received Inquiry");
	}
}
