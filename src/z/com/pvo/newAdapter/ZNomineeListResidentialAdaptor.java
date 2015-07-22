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
import android.widget.TextView;

import com.pvo.activity.R;

public class ZNomineeListResidentialAdaptor extends ArrayAdapter<ZNomineeItem> {
	
	
	private static Boolean isPrint = true;
	private static String TAG = "ZNomineeListResidentialAdaptor";   
	
	private Context context;
	//public static ArrayList<String> selectedNomineeIdArray = new ArrayList<String>();
	//public static ArrayList<String> nomineeCommercialIdArray = new ArrayList<String>();
	public static boolean checkAll = false;
	
	public static Set<String> selectedNominee = new HashSet();
	
	
	public ZNomineeListResidentialAdaptor(Context context, int resourceId, List<ZNomineeItem> objects) {
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
	    
	    TextView tv_nomineeRow_number = (TextView) view.findViewById(R.id.tv_nomineeRow_number);
	    tv_nomineeRow_number.setVisibility(View.VISIBLE);
	    tv_nomineeRow_number.setText(nomineeItem.getNumber());
	    
	    if(checkAll) {
	    	chk_nominee.setChecked(true);
	    	selectedNominee.add(nomineeItem.getNomineeID());
	    	ProjectUtility.sys(isPrint, TAG,"selectedNominee All--> "+selectedNominee);
	    	/*if(!selectedNomineeIdArray.contains(nomineeItem.getNomineeID())) 
	    		selectedNomineeIdArray.add(nomineeItem.getNomineeID());*/
	    }
	    
	    
	    /*
	     * Set checked nominee as it is when open again
	     */
	    if(selectedNominee.size() > 0) {
	    	if(selectedNominee.contains(nomineeItem.getNomineeID())) {
	    		chk_nominee.setChecked(true);
	    	}
	    	
	    	/*for (int i = 0; i < selectedNomineeIdArray.size(); i++) {
				if(selectedNomineeIdArray.get(i).equals(nomineeItem.getNomineeID())) {
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
				if (isChecked) {
					selectedNominee.add(nomineeItem.getNomineeID());
				} else {
					selectedNominee.remove(nomineeItem.getNomineeID());
					checkAll = false;
				}
				ProjectUtility.sys(isPrint, TAG,"selectedNominee--> "+selectedNominee);
			}
		});
	    	
		return view;
	}
	
    /*
     * Set default selected nominee when edit
     */
	public static void setSelectedNominee(JSONArray jsonArray) throws JSONException {
		selectedNominee.clear();
		for (int i = 0; i < jsonArray.length(); i++) {
			ProjectUtility.sys(isPrint, TAG,i+jsonArray.get(i).toString());
			selectedNominee.add(jsonArray.get(i).toString());
		}
		ProjectUtility.sys(isPrint, TAG, "selectedNominee-->"+selectedNominee);
	}
	
	/*
	 * get all check nominee id array list 
	 */
	public static Set<String> getNomineeId() {
		return selectedNominee;
		
	}
	
	/*
	 * Clear all the selected nominee
	 */
	public static void clearSelectedResidentialNominee() {
		if(selectedNominee != null) {
			ProjectUtility.sys(isPrint, TAG, "clearSelectedResidentialNominee");
			checkAll = false;
			selectedNominee.clear();
		}
	}
}
