package z.com.pvo.newAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;

import z.com.pvo.components.RoundedTransformation;
import z.com.pvo.newActivity.ZAminitiesCheckboxCommon;
import z.com.pvo.newActivity.ZShareWithGroupListTab;
import z.com.pvo.util.ProjectUtility;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.pvo.activity.R;
import com.pvo.util.Constant;
import com.squareup.picasso.Picasso;

public class ZAminitiesResidentialAdaptor extends ArrayAdapter<ZAminitiesItem> {

	private Context context;
	protected static Boolean isPrint = true;
	protected static String TAG = "ZAminitiesListAdaptor";
	//public static ArrayList<String> selectedAmenitiesIdArray = new ArrayList<String>();
	public static boolean checkAll = false;
	
	public static Set<String> selectedAmenities = new HashSet();

	public static List<String> selectedAmenitiesUrl = new ArrayList<String>();
	public static List<String> selectedAmenitiesTitle = new ArrayList<String>();
	
	/*public static Set<String> selectedAmenitiesUrlSet = new HashSet();
	public static Set<String> selectedAmenitiesTitleSet = new HashSet();*/
	
	public ZAminitiesResidentialAdaptor(Context context, int resourceId, List<ZAminitiesItem> objects) {
		super(context, resourceId, objects);
		this.context = context;
	}
	

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		view = inflater.inflate(R.layout.z_aminities_list_row, parent, false);
		final ZAminitiesItem amenitiesItem = getItem(position);

		
		int valueInPixels = (int) getContext().getResources().getDimension(R.dimen.amenitiesDiaIconWidth);
		int margin = (int) getContext().getResources().getDimension(R.dimen.amenitiesDiaRoundMargin);
				
				
		ImageView v = (ImageView) view.findViewById(R.id.iv_aminitiesRow_icon);
		ZAminitiesCheckboxCommon aminitiesCheckboxCommon = (ZAminitiesCheckboxCommon) view.findViewById(R.id.chk_addProp_aminities);
		Picasso.with(context).load(amenitiesItem.getImagePath()).centerCrop().resize(valueInPixels,valueInPixels).transform(new RoundedTransformation(50,margin )).into(v);
		//aminitiesCheckboxCommon.setButtonDrawable(facilityIcon.getDrawable());
		

		if (checkAll) {
			aminitiesCheckboxCommon.setChecked(true);
			selectedAmenities.add(amenitiesItem.getAmenitiesId());
			ProjectUtility.sys(isPrint, TAG,"selectedNominee All--> "+selectedAmenities);
			
			/*
			 * This is for display selected amenities into list
			 */
			if(!selectedAmenitiesUrl.contains(amenitiesItem.getImagePath()))
				selectedAmenitiesUrl.add(amenitiesItem.getImagePath());
			if(!selectedAmenitiesTitle.contains(amenitiesItem.getIconName()))
				selectedAmenitiesTitle.add(amenitiesItem.getIconName());
			
			/*selectedAmenitiesUrlSet.add(amenitiesItem.getImagePath());
			selectedAmenitiesTitleSet.add(amenitiesItem.getIconName());*/
			
			
		}
		
		
		/*
	     * Set checked nominee as it is when open again
	     */
	    if(selectedAmenities.size() > 0) {
	    	if(selectedAmenities.contains(amenitiesItem.getAmenitiesId())) {
	    		aminitiesCheckboxCommon.setChecked(true);
	    	}
	    }
	    
		 /*
	     * aemenities check box check change event
	     */
		aminitiesCheckboxCommon.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					selectedAmenities.add(amenitiesItem.getAmenitiesId());
					
					/*
					 * This is for display selected amenities into list
					 */
					if(!selectedAmenitiesUrl.contains(amenitiesItem.getImagePath()))
						selectedAmenitiesUrl.add(amenitiesItem.getImagePath());
					if(!selectedAmenitiesTitle.contains(amenitiesItem.getIconName()))
						selectedAmenitiesTitle.add(amenitiesItem.getIconName());
					
					/*selectedAmenitiesUrlSet.add(amenitiesItem.getImagePath());
					selectedAmenitiesTitleSet.add(amenitiesItem.getIconName());*/
					
					
				} else {
					Toast.makeText(getContext(), "Un check", Toast.LENGTH_SHORT).show();
					selectedAmenities.remove(amenitiesItem.getAmenitiesId());
					ZShareWithGroupListTab.checkAllGroup = false;
					
					/*
					 * This is for display selected amenities into list
					 */
					selectedAmenitiesUrl.remove(amenitiesItem.getImagePath());
					selectedAmenitiesTitle.remove(amenitiesItem.getIconName());
					
					/*selectedAmenitiesUrlSet.remove(amenitiesItem.getImagePath());
					selectedAmenitiesTitleSet.remove(amenitiesItem.getIconName());*/
					
					
				}
				//ProjectUtility.sys(isPrint, TAG,"selectedNominee--> "+selectedAmenities);
			}
		});

		return view;
	}
	
	
	/*
	 * Amenities ID
	 * Set amenities id When call from edit
	 */
	public static void setSelectdAmenities(JSONArray jsonArray) throws JSONException {
		selectedAmenities.clear();
		for (int i = 0; i < jsonArray.length(); i++) {
			ProjectUtility.sys(isPrint, TAG,i+jsonArray.get(i).toString());
			selectedAmenities.add(jsonArray.get(i).toString());
		}
		ProjectUtility.sys(isPrint, TAG, "selectedAmenities-->"+selectedAmenities);
	}
	
	/*
	 * Set amenities id for edit purpose
	 */
	public static Set<String> getAmenitiesId() {
		return selectedAmenities;
	}
	
	
	/*
	 * Amenities URL
	 * Set amenities id When call from edit
	 */
	public static void setAmenitiesUrl(JSONArray jsonArray) throws JSONException {
		selectedAmenitiesUrl.clear();
		for (int i = 0; i < jsonArray.length(); i++) {
			ProjectUtility.sys(isPrint, TAG,i+jsonArray.get(i).toString());
			selectedAmenitiesUrl.add(jsonArray.get(i).toString());
		}
		ProjectUtility.sys(isPrint, TAG, "Amenities URL-->"+selectedAmenitiesUrl);
	}
	
	/*
	 * get the slected amenities url
	 */
	public static List<String> getAmenitiesUrl() {
		return selectedAmenitiesUrl; 
	}
	
	/*
	 * Amenities TITLE
	 * Set amenities id When call from edit
	 */
	public static void setAmenitiesTitle(JSONArray jsonArray) throws JSONException {
		selectedAmenitiesTitle.clear();
		for (int i = 0; i < jsonArray.length(); i++) {
			ProjectUtility.sys(isPrint, TAG,i+jsonArray.get(i).toString());
			selectedAmenitiesTitle.add(jsonArray.get(i).toString());
		}
		ProjectUtility.sys(isPrint, TAG, "Amenities Title-->"+selectedAmenitiesTitle);
	}
	
	/*
	 * get amenities title
	 */
	public static List<String> getAmenitiesTitle() {
		return selectedAmenitiesTitle; 
	}

	/*
	 * get the slected amenities url
	 */
	/*public static Set<String> getAmenitiesSetUrl() {
		return selectedAmenitiesUrlSet; 
	}*/
	
	/*
	 * get amenities title
	 */
	/*public static Set<String> getAmenitiesSetTitle() {
		return selectedAmenitiesTitleSet; 
	}*/
	
	
	public static void clearSelectedAmenities() {
		if(selectedAmenities != null) {
			checkAll = false;
			selectedAmenities.clear();
		}
		
		/*
		 * Clear all selected amenities and url
		 */
		if(selectedAmenitiesUrl != null)
			selectedAmenitiesUrl.clear();
		/*
		 * Clear all selected amenities and url
		 */
		if(selectedAmenitiesTitle!= null)
			selectedAmenitiesTitle.clear();

		/*
		 * Clear all selected amenities and url
		 */
		/*if(selectedAmenitiesUrlSet != null)
			selectedAmenitiesUrlSet.clear();*/
		/*
		 * Clear all selected amenities and url
		 */
		/*if(selectedAmenitiesTitleSet != null)
			selectedAmenitiesTitleSet.clear();*/
		
	}
}
