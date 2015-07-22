package z.com.pvo.newActivity;

import z.com.pvo.newAdapter.ZGroupListAdaptor;
import z.com.pvo.util.ProjectUtility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

import com.pvo.activity.R;

//http://blog.andolasoft.com/2013/06/how-to-show-captured-images-dynamically-in-gridview-layout.html
public class ZShareWithGroupListTab extends Fragment implements OnClickListener {
	
	/*private ZGroupListAdaptor groupListAdapter;
	private ArrayList<ZGroupItem> groupItems = new ArrayList<ZGroupItem>();*/
	private static ListView lv_shareWith_listview;
	private CheckBox chk_shareWith_checkBoxAll;
    public static boolean checkAllGroup; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.z_share_with_list, container, false);
		lv_shareWith_listview = (ListView) v.findViewById(R.id.lv_shareWith_listview);
		chk_shareWith_checkBoxAll = (CheckBox) v.findViewById(R.id.chk_shareWith_checkBoxAll);
		ProjectUtility.getGroupList(getActivity(), lv_shareWith_listview);
	
		
		chk_shareWith_checkBoxAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				ZGroupListAdaptor.clearSelectedGroup();
				if(isChecked)
					checkAllGroup = true;
				else
					checkAllGroup = false;
				ProjectUtility.notifyGroupListAdapter();
			}
		});
		
		return v;
	}
	
	//instantiate All fragment 
	public static ZShareWithGroupListTab newInstance() {
		ZShareWithGroupListTab f = new ZShareWithGroupListTab();
		Bundle b = new Bundle();
		f.setArguments(b);
		return f;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			
		default:
			break;
		}
	}
	
}
