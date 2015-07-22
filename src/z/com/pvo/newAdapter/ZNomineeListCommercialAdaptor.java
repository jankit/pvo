package z.com.pvo.newAdapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;

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

public class ZNomineeListCommercialAdaptor extends ArrayAdapter<ZNomineeItem> {
	
	
	private static Boolean isPrint = true;
	private static String TAG = "NomineeListCommercialAdaptor";
	
	private Context context;
	//public static ArrayList<String> nomineeIdArray = new ArrayList<String>();
	public static boolean checkAll = false;
	public static Set<String> selectedCommercialNominee = new HashSet();
	
	
	public ZNomineeListCommercialAdaptor(Context context, int resourceId, List<ZNomineeItem> objects) {
		super(context, resourceId, objects);
		ProjectUtility.sys(isPrint, TAG,"Construction");
		this.context = context;
	}

	@Override
	  public View getView (int position, View view, ViewGroup parent ) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    view = inflater.inflate(R.layout.z_nominee_list_row, parent, false);
	    final ZNomineeItem nomineeItem = getItem(position);
	    
	    CheckBox chk_nominee = (CheckBox) view.findViewById(R.id.checkBox);
	    chk_nominee.setText(nomineeItem.getNomineeName());

	    if(checkAll) {
	    	chk_nominee.setChecked(true);
	    	selectedCommercialNominee.add(nomineeItem.getNomineeID());
	    	ProjectUtility.sys(isPrint, TAG,"selectedCommercialNominee--> "+selectedCommercialNominee);
	    	
	    	/*if(!nomineeIdArray.contains(nomineeItem.getNomineeID())) 
	    		nomineeIdArray.add(nomineeItem.getNomineeID());*/
	    }
	    
	    /*
	     * Set checked nominee as it is when open again
	     */
	    if(selectedCommercialNominee.size() > 0) {
	    	if(selectedCommercialNominee.contains(nomineeItem.getNomineeID())) {
	    		chk_nominee.setChecked(true);
	    	}
	    	
	    	/*for (int i = 0; i < nomineeIdArray.size(); i++) {
				if(nomineeIdArray.get(i).equals(nomineeItem.getNomineeID())) {
					chk_nominee.setChecked(true);
				}
			}*/
	    }
	    
	    /*
	     * Nominee check box check change event
	     */
	    chk_nominee.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				/*System.out.println("----> Set on check change listener <---");
				if (isChecked) {
					if(!nomineeIdArray.contains(nomineeItem.getNomineeID()))
					      nomineeIdArray.add(nomineeItem.getNomineeID());
				} else {
					nomineeIdArray.remove(nomineeItem.getNomineeID());
					checkAll = false;
					//ZNomineeListDialog.chk_nomineeList_checkAll.setChecked(false);
				}*/
				
				if (isChecked) {
					selectedCommercialNominee.add(nomineeItem.getNomineeID());
				} else {
					selectedCommercialNominee.remove(nomineeItem.getNomineeID());
					checkAll = false;
				}
				ProjectUtility.sys(isPrint, TAG,"selectedCommercialNominee--> "+selectedCommercialNominee);
			}
		});
	    	
		return view;
	}
	
	
	private static void setSelectedCommercialNominee(JSONArray jsonArray) throws JSONException {
		selectedCommercialNominee.clear();
		for (int i = 0; i < jsonArray.length(); i++) {
			ProjectUtility.sys(isPrint, TAG,i+jsonArray.get(i).toString());
			selectedCommercialNominee.add(jsonArray.get(i).toString());
		}
		ProjectUtility.sys(isPrint, TAG, "selectedCommercialNominee-->"+selectedCommercialNominee);
	}

	/*
	 * get all check nominee id array list 
	 */
	public static Set<String> getNomineeId() {
		return selectedCommercialNominee;
	} 	
	
	public static void clearSelectedCommercialNominee() {
		if(selectedCommercialNominee != null) {
			checkAll = false;
			selectedCommercialNominee.clear();
		}
	}
}
