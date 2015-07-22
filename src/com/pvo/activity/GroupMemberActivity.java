package com.pvo.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.pvo.custom.adapter.GroupMemberArrayAdaptor;
import com.pvo.custom.adapter.GroupMemberItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.GroupMemberListService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

public class GroupMemberActivity extends PVOFragment {
	
	private GroupMemberListService groupMemberListService;
	private ListView groupMemberListview;
	private Button btn_grpMem_back;
	private Button btn_grpMem_addMember;
	private GroupMemberArrayAdaptor memberArrayAdaptor;
	private UserSessionManager userSessionManager;
	private List<GroupMemberItem> groupMemberItems = new ArrayList<GroupMemberItem>();
	private Bundle bundle;
	
	// Set the layout Content View 
	public GroupMemberActivity() {
		setContentView(R.layout.activity_groupmember_list_popup_window);
	}
	
	@Override
	public void init(Bundle savedInstanceState) {
		//super.init(savedInstanceState);
		userSessionManager = new UserSessionManager(getActivity());
		bundle = getArguments();
		groupMemberListService = new GroupMemberListService();
		groupMemberListview = (ListView) findViewById(R.id.groupMemberListview);

		//On back press goto previouse screen
		btn_grpMem_back = (Button) findViewById(R.id.btn_grpMem_back);
		btn_grpMem_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});
		//Redirect to Prefere broker screen
		btn_grpMem_addMember = (Button) findViewById(R.id.btn_grpMem_addMember);
		btn_grpMem_addMember.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PreferreBrokerListActivity preferreBrokerListActivity  = new PreferreBrokerListActivity();
				((MainFragmentActivity)getActivity()).redirectScreenWithoutStack(preferreBrokerListActivity);
			}
		});
		
		
		new WebserviceClient(GroupMemberActivity.this, groupMemberListService).execute(userSessionManager.getSessionValue(Constant.Login.USER_ID),bundle.getString(Constant.GroupList.PGID));
		
	}

	@Override
	public void processResponse(Object response) {
		JSONArray jsonArry = (JSONArray)response;
		
		try {
			if (jsonArry != null && !((JSONObject)jsonArry.get(0)).has(Constant.PreferredBrokers.API_STATUS)) {
				for (int i = 0; i < jsonArry.length(); i++) {
					JSONObject jsonObj = jsonArry.getJSONObject(i);
					GroupMemberItem groupMemberItem = new GroupMemberItem();
					groupMemberItem.setBrokerid(jsonObj.getString(Constant.BrokerListInGroup.BROKER_ID));
					groupMemberItem.setCompanyname(jsonObj.getString(Constant.BrokerListInGroup.COMPANY_NAME));
					groupMemberItem.setFirstname(jsonObj.getString(Constant.BrokerListInGroup.FIRST_NAME));
					groupMemberItem.setLastname(jsonObj.getString(Constant.BrokerListInGroup.LAST_NAME));
					groupMemberItem.setLogolink(jsonObj.getString(Constant.BrokerListInGroup.LOGO_LINK));
					groupMemberItem.setPhotolink(jsonObj.getString(Constant.BrokerListInGroup.PHOTO_LINK));
					groupMemberItems.add(groupMemberItem);
				}
				if(memberArrayAdaptor == null) {
					memberArrayAdaptor = new GroupMemberArrayAdaptor(getActivity(), R.id.publicPropListView, groupMemberItems);
					groupMemberListview.setAdapter(memberArrayAdaptor);
				} else {
					memberArrayAdaptor.notifyDataSetChanged();
				}
			} else {
				Toast.makeText(getActivity(), ((JSONObject)jsonArry.get(0)).getString(Constant.PreferredBrokers.API_MESSAGE), Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/** on fragment resume set the title of fragment **/
	@SuppressLint("NewApi")
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    this.getActivity().getActionBar().setTitle(bundle.getString(Constant.GroupList.GROUP_NAME)+" Group Members");
	}
	/** END **/
}
