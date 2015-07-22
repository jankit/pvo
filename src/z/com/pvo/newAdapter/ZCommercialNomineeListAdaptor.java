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
import android.widget.TextView;

import com.pvo.activity.R;

public class ZCommercialNomineeListAdaptor extends ArrayAdapter<ZNomineeItem> {
	
	
	protected static final Boolean isPrint = true;
	protected static String TAG = "ZCommercialNomineeListAdaptor";
	private Context context;
	//public static ArrayList<String> nomineeIdArray = new ArrayList<String>();
	//public static ArrayList<String> nomineeCommercialIdArray = new ArrayList<String>();
	public static boolean checkAll = false;
	
	public static Set<String> selectedNomineeCommercial = new HashSet();
	
	public ZCommercialNomineeListAdaptor(Context context, int resourceId, List<ZNomineeItem> objects) {
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
	    	selectedNomineeCommercial.add(nomineeItem.getNomineeID());
	    	ProjectUtility.sys(isPrint, TAG,"selectedNomineeCommercial--> "+selectedNomineeCommercial);
	    	
	    	/*if(!nomineeIdArray.contains(nomineeItem.getNomineeID())) 
	    		nomineeIdArray.add(nomineeItem.getNomineeID());*/
	    }
	    
	    /*
	     * Set checked nominee as it is when open again
	     */
	    if(selectedNomineeCommercial.size() > 0) {
	    	if(selectedNomineeCommercial.contains(nomineeItem.getNomineeID())) {
	    		chk_nominee.setChecked(true);
	    	}
	    }
	    
	    /*
	     * Nominee check box check change event
	     */
	    chk_nominee.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				if (isChecked) {
					selectedNomineeCommercial.add(nomineeItem.getNomineeID());
				} else {
					selectedNomineeCommercial.remove(nomineeItem.getNomineeID());
					ZShareWithGroupListTab.checkAllGroup = false;
				}
				ProjectUtility.sys(isPrint, TAG,"selectedNomineeCommercial--> "+selectedNomineeCommercial);
			}
		});
	    	
		return view;
	}
	
	
	public static void setSelectedCommercialoNominee(JSONArray jsonArray) throws JSONException {
		selectedNomineeCommercial.clear();
		for (int i = 0; i < jsonArray.length(); i++) {
			ProjectUtility.sys(isPrint, TAG,i+jsonArray.get(i).toString());
			selectedNomineeCommercial.add(jsonArray.get(i).toString());
		}
		ProjectUtility.sys(isPrint, TAG, "selectedNomineeCommercial-->"+selectedNomineeCommercial);
	}
	
	/*
	 * get all check nominee id array list 
	 */
	public static Set<String> getCommercialoNomineeId() {
		return selectedNomineeCommercial;
		
	} 	
	
	public static void clearSelectedCommercialNominee() {
		if(selectedNomineeCommercial != null) {
			checkAll = false;
			selectedNomineeCommercial.clear();
		}
	}
}
