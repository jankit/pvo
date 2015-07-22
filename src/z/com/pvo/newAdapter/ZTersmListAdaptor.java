package z.com.pvo.newAdapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.BooleanUtils;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.pvo.util.Constant;

public class ZTersmListAdaptor extends ArrayAdapter<ZTermsItem> {
	
	private Boolean isPrint = true;
	private String TAG = "TersmListAdaptor";
	
	private Context context;
	public static Set<String> selectedTerms = new HashSet();
	public static boolean checkAll = false;
	
	public ZTersmListAdaptor(Context context, int resourceId, List<ZTermsItem> objects) {
		super(context, resourceId, objects);
		ProjectUtility.sys(isPrint, TAG, "Construction");
		this.context = context;
	}

	@Override
	  public View getView (int position, View view, ViewGroup parent ) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    view = inflater.inflate(R.layout.z_nominee_list_row, parent, false);
	    final ZTermsItem termsItem = getItem(position);
	    
	    CheckBox chk_terms = (CheckBox) view.findViewById(R.id.checkBox);
	    chk_terms.setText(termsItem.getName());
	    
	    if(checkAll) {
	    	chk_terms.setChecked(true);
	    	selectedTerms.add(termsItem.getPosition());
	    }
	    
	    /*
	     * Set checked nominee as it is when open again
	     */
	    if(selectedTerms.size() > 0) {
	    	if(selectedTerms.contains(termsItem.getPosition())) {
				chk_terms.setChecked(true);
	    	}
	    	
	    	/*for(HashMap<String, String> map: tremIdArray) {
	    		System.out.println("map-->"+map);
	    		
	    		if(map != null) {
		    		if(map.get(treamItem.getName()).equals(treamItem.getPosition())) {
					}
	    		}
	        }*/
	    }
	    
	    /*
	     * Nominee check box check change event
	     */
	    chk_terms.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {				
				if (isChecked) {
					selectedTerms.add(termsItem.getPosition());
				} else {
					selectedTerms.remove(termsItem.getPosition());
					checkAll = false;
				}
			}
		});
	    	
		return view;
	}
	
	
	/*
	 * set static value for terms
	 */
	public static void setTerms(JSONObject propertyDetailJsonObject) throws NumberFormatException, JSONException {
		selectedTerms.clear();
		if(BooleanUtils.toBoolean(Integer.parseInt(propertyDetailJsonObject.getString(Constant.EditProperty.NAVISHARAT))))
			selectedTerms.add("0");
		if(BooleanUtils.toBoolean(Integer.parseInt(propertyDetailJsonObject.getString(Constant.EditProperty.JUNISHARAT))))
			selectedTerms.add("1");
		if(BooleanUtils.toBoolean(Integer.parseInt(propertyDetailJsonObject.getString(Constant.EditProperty.KHETI))))
			selectedTerms.add("2");
		if(BooleanUtils.toBoolean(Integer.parseInt(propertyDetailJsonObject.getString(Constant.EditProperty.PRASSAP))))
			selectedTerms.add("3");
		if(BooleanUtils.toBoolean(Integer.parseInt(propertyDetailJsonObject.getString(Constant.EditProperty.DIS_PUTE))))
			selectedTerms.add("4");
		if(BooleanUtils.toBoolean(Integer.parseInt(propertyDetailJsonObject.getString(Constant.EditProperty.SHREE_SARKAR))))
			selectedTerms.add("5");
	}

	/*
	 * get terms value
	 */
	public static Set<String> getSelectedTerms() {
		return selectedTerms;
	}

	public static void clearSelectedTerms() {
		if(selectedTerms != null) {
			checkAll = false;
			selectedTerms.clear();
		}
	}
}
