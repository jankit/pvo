package z.com.pvo.newAdapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;

import z.com.pvo.newActivity.ZShareWithGroupListTab;
import z.com.pvo.util.ProjectUtility;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.pvo.activity.R;

public class ZGroupListAdaptor extends ArrayAdapter<ZGroupItem> {
	
	private static Boolean isPrint = true;
	private static String TAG = "GroupListAdaptor";
	private Context context;
	
	//public static ArrayList<String> selectedGroupIdArray = new ArrayList<String>();
	public static Set<String> selectedGroup = new HashSet();
	
	
	public ZGroupListAdaptor(Context context, int resourceId, List<ZGroupItem> objects) {
		super(context, resourceId, objects);
		ProjectUtility.sys(isPrint, TAG, "Construction");
		this.context = context;
	}

	@Override
	  public View getView ( final int position, View view, ViewGroup parent ) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    view = inflater.inflate(R.layout.z_share_with_list_row, parent, false);
	    final ZGroupItem groupItem = getItem(position);
	    
	    CheckBox chkShareWithGroup = (CheckBox) view.findViewById(R.id.chk_shareWith_checkBox);
	    chkShareWithGroup.setText(groupItem.getGroupName());
	    
	    if(ZShareWithGroupListTab.checkAllGroup) {
	    	chkShareWithGroup.setChecked(true);
	    	selectedGroup.add(groupItem.getGroupId());
	    	ProjectUtility.sys(isPrint, "ZGroupListAdaptor","selectedGroupIdArray All--> "+selectedGroup);
	    }
	    
	    
	    
	    /*
	     * Set default checked as it is when open again
	     */
	    if(selectedGroup.size() > 0) {
	    	if(selectedGroup.contains(groupItem.getGroupId())) {
	    		chkShareWithGroup.setChecked(true);
	    	}
	    	/*for (int i = 0; i < selectedGroupIdArray.size(); i++) {
				if(selectedGroupIdArray.get(i).equals(groupItem.getGroupId())) {
					chk_shareWith_checkBox.setChecked(true);
				}
			}*/
	    }
	    
	    
	    /*
	     * group check box check change event
	     */
	    chkShareWithGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				/*if (isChecked) {
					if(!selectedGroupIdArray.contains(groupItem.getGroupId()))
						selectedGroupIdArray.add(groupItem.getGroupId());
				} else {
					selectedGroupIdArray.remove(groupItem.getGroupId());
					ZShareWithGroupListTab.checkAllFlag = false;
				}*/
				
				if (isChecked) {
					selectedGroup.add(groupItem.getGroupId());
				} else {
					selectedGroup.remove(groupItem.getGroupId());
					ZShareWithGroupListTab.checkAllGroup = false;
				}
				ProjectUtility.sys(isPrint, "ZGroupListAdaptor","selectedGroupIdArray--> "+selectedGroup);
			}
		});
	    
		return view;
	}
	
	public static void setSelecttedGroup(JSONArray jsonArray) throws JSONException {
		selectedGroup.clear();
		for (int i = 0; i < jsonArray.length(); i++) {
			ProjectUtility.sys(isPrint, "ZPropAddRecidentialFragment",i+jsonArray.get(i).toString());
			selectedGroup.add(jsonArray.get(i).toString());
		}
		ProjectUtility.sys(isPrint, "ZGroupListAdaptor", "selectedGroup-->"+selectedGroup);
	}
	
	public static Set<String> getGroupId() {
		return selectedGroup;
	}
	
	
	public static void clearSelectedGroup() {
		if(selectedGroup != null) {
			ZShareWithGroupListTab.checkAllGroup = false;
			selectedGroup.clear();
		}
	}
}

