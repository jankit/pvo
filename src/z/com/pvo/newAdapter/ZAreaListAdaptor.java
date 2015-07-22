package z.com.pvo.newAdapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;

import z.com.pvo.newActivity.ZShareWithAreaListTab;
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

public class ZAreaListAdaptor extends ArrayAdapter<ZAraeItem> {
	
	private static Boolean isPrint = true;
	private Context context;
	private String TAG = "ZAreaListAdaptor";
	//public static ArrayList<String> selectedAreaIdArray = new ArrayList<String>();;
	public static Set<String> selectedArea = new HashSet();
	
	public ZAreaListAdaptor(Context context, int resourceId, List<ZAraeItem> objects) {
		super(context, resourceId, objects);
		ProjectUtility.sys(isPrint, TAG,"Construction");
		this.context = context;
	}

	@Override
	  public View getView ( final int position, View view, ViewGroup parent ) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    view = inflater.inflate(R.layout.z_share_with_list_row, parent, false);
	    final ZAraeItem areaItem = getItem(position);
	    
	    CheckBox chk_shareWith_checkBox = (CheckBox) view.findViewById(R.id.chk_shareWith_checkBox);
	    chk_shareWith_checkBox.setText(areaItem.getAreaName());
	    
	    if(ZShareWithAreaListTab.checkAllFlag) {
	    	chk_shareWith_checkBox.setChecked(true);
	    	selectedArea.add(areaItem.getAreaId());
	    	ProjectUtility.sys(isPrint, TAG,"selectedAreaIdArray All--> "+selectedArea);
	    }
	    
	    /*
	     * Set default checked as it is when open again
	     */
	    if(selectedArea.size() > 0) {
	    	if(selectedArea.contains(areaItem.getAreaId())) {
	    		chk_shareWith_checkBox.setChecked(true);
	    	}
	    }
	    
	    /*
	     * group check box check change event
	     */
	    chk_shareWith_checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					selectedArea.add(areaItem.getAreaId());
				} else {
					selectedArea.remove(areaItem.getAreaId());
					ZShareWithAreaListTab.checkAllFlag = false;
				}
				ProjectUtility.sys(isPrint, "ZAreaListAdaptor","selectedAreaIdArray"+selectedArea );
			}
		});
		return view;
	}
	
	
	public static void setSelectedArea(JSONArray jsonArray) throws JSONException {
		selectedArea.clear();
		for (int i = 0; i < jsonArray.length(); i++) {
			ProjectUtility.sys(isPrint, "ZPropAddRecidentialFragment",i+jsonArray.get(i).toString());
			selectedArea.add(jsonArray.get(i).toString());
		}
		ProjectUtility.sys(isPrint, "ZGroupListAdaptor", "selectedGroup-->"+selectedArea);
		
	}
	public static Set<String> getAreaId() {
		return selectedArea;
	} 
	
	public static void clearSelectedArea() {
		if(selectedArea != null) {
			ZShareWithAreaListTab.checkAllFlag = false;
			selectedArea.clear();
		}
	}
}

