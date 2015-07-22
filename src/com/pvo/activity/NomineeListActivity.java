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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.pvo.custom.adapter.NomineeArrayAdaptor;
import com.pvo.custom.adapter.NomineeItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.NomineesListService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

/**
 * This Is Used to display the List of the Nominee
 * in this we link the Add nominee   
 * */
public class NomineeListActivity extends PVOFragment {
    
	//private LinearLayout nomineeAddLayout;
	private Button btnAddNominee;
	private Button btnCancelNominee;
    private ListView nomiListView;
    private NomineesListService nomineesListService;
    private UserSessionManager userSessionManager;
    private List<NomineeItem> nomineeItems = new ArrayList<NomineeItem>();
    private NomineeArrayAdaptor nomineeArrayAdaptor;

	/** Set Layout Content View **/
	public NomineeListActivity() {
		setContentView(R.layout.activity_nominee_list);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		nomineesListService 		= new NomineesListService();
		userSessionManager 			= new UserSessionManager(getActivity().getApplicationContext());
		nomiListView 				= (ListView) findViewById(R.id.nomineesListView);
		btnAddNominee 				= (Button) findViewById(R.id.btnAddNominee);
		
		/** Redirect to add new nominee screen **/
		btnAddNominee.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				NomineeAddActivity addNomineeActivity = new NomineeAddActivity();
				((MainFragmentActivity)getActivity()).redirectScreen(addNomineeActivity);
			}
		});
		/** END **/
		
		//Redirect to home screen
		btnCancelNominee = (Button) findViewById(R.id.btnCancelNominee);
		btnCancelNominee.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});
		
		new WebserviceClient(NomineeListActivity.this, nomineesListService).execute(userSessionManager.getSessionValue(Constant.Login.USER_ID));
	}
	
	@Override
	public void processResponse(Object response) {
		JSONArray jsonArry = (JSONArray) response;
		try {
			if (jsonArry != null && !((JSONObject)jsonArry.get(0)).has(Constant.NomineeList.API_STATUS)) {
				for (int i = 0; i < jsonArry.length(); i++) {
					JSONObject jsonObj = jsonArry.getJSONObject(i);
					NomineeItem nomineeItem = new NomineeItem();
					nomineeItem.setTitle(jsonObj.getString(Constant.NomineeList.TITLE));
					nomineeItem.setMobile_no(jsonObj.getString(Constant.NomineeList.MOBILE_NO));
					nomineeItem.setNomineeid(jsonObj.getString(Constant.NomineeList.NOMINEE_ID));
					nomineeItem.setEmail_id(jsonObj.getString(Constant.NomineeList.EMAIL_ID));
					nomineeItem.setPwd(jsonObj.getString(Constant.NomineeList.PWD));
					
					nomineeItems.add(nomineeItem);
				}
				if(nomineeArrayAdaptor == null) {
					nomineeArrayAdaptor = new NomineeArrayAdaptor(getActivity(), R.id.nomineesListView, nomineeItems);
					nomiListView.setAdapter(nomineeArrayAdaptor);
				} else {
					nomineeArrayAdaptor.notifyDataSetChanged();
				}
			} else {
				Toast.makeText(getActivity(),((JSONObject)jsonArry.get(0)).getString(Constant.NomineeList.API_MESSAGE), Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@SuppressLint("NewApi")
	@Override
	public void onResume() {
	    super.onResume();
	    this.getActivity().getActionBar().setTitle("Nominee");
	}
	
}
