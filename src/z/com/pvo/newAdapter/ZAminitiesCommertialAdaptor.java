package z.com.pvo.newAdapter;

import java.util.ArrayList;
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
import com.squareup.picasso.Picasso;

public class ZAminitiesCommertialAdaptor extends ArrayAdapter<ZAminitiesItem> {

	private Context context;
	protected static Boolean isPrint = true;
	protected static String TAG = "ZAminitiesCommertialAdaptor";
	public static boolean checkAll = false;
	public static Set<String> selectedCommercialAmenities = new HashSet();
	public static List<String> selectedCommercialAmenitiesUrl = new ArrayList<String>();
	public static List<String> selectedCommercialAmenitiesTitle = new ArrayList<String>();
	
	public ZAminitiesCommertialAdaptor(Context context, int resourceId, List<ZAminitiesItem> objects) {
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
		

		if (checkAll) {
			aminitiesCheckboxCommon.setChecked(true);
			selectedCommercialAmenities.add(amenitiesItem.getAmenitiesId());
			ProjectUtility.sys(isPrint, TAG,"selectedNominee All--> "+selectedCommercialAmenities);
			
			/*
			 * This is for display selected amenities into list
			 */
			if(!selectedCommercialAmenitiesUrl.contains(amenitiesItem.getImagePath()))
				selectedCommercialAmenitiesUrl.add(amenitiesItem.getImagePath());
			if(!selectedCommercialAmenitiesTitle.contains(amenitiesItem.getIconName()))
				selectedCommercialAmenitiesTitle.add(amenitiesItem.getIconName());
	
		}
		
		
		/*
	     * Set checked nominee as it is when open again
	     */
	    if(selectedCommercialAmenities.size() > 0) {
	    	if(selectedCommercialAmenities.contains(amenitiesItem.getAmenitiesId())) {
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
					selectedCommercialAmenities.add(amenitiesItem.getAmenitiesId());
					
					/*
					 * This is for display selected amenities into list
					 */
					if(!selectedCommercialAmenitiesUrl.contains(amenitiesItem.getImagePath()))
						selectedCommercialAmenitiesUrl.add(amenitiesItem.getImagePath());
					if(!selectedCommercialAmenitiesTitle.contains(amenitiesItem.getIconName()))
						selectedCommercialAmenitiesTitle.add(amenitiesItem.getIconName());
					
				} else {
					Toast.makeText(getContext(), "Un check", Toast.LENGTH_SHORT).show();
					selectedCommercialAmenities.remove(amenitiesItem.getAmenitiesId());
					ZShareWithGroupListTab.checkAllGroup = false;
					
					/*
					 * This is for display selected amenities into list
					 */
					selectedCommercialAmenitiesUrl.remove(amenitiesItem.getImagePath());
					selectedCommercialAmenitiesTitle.remove(amenitiesItem.getIconName());
					
					/*selectedAmenitiesUrl.remove(amenitiesItem.getImagePath());
					selectedAmenitiesUrl.remove(amenitiesItem.getIconName());*/
				}
				ProjectUtility.sys(isPrint, TAG,"selectedNominee--> "+selectedCommercialAmenities);
			}
		});

		return view;
	}
	
	/*
	 * Amenities ID 
	 */
	public static void setSelectdAmenities(JSONArray jsonArray) throws JSONException {
		selectedCommercialAmenities.clear();
		for (int i = 0; i < jsonArray.length(); i++) {
			ProjectUtility.sys(isPrint, TAG,i+jsonArray.get(i).toString());
			selectedCommercialAmenities.add(jsonArray.get(i).toString());
		}
		ProjectUtility.sys(isPrint, TAG, "selectedAmenities-->"+selectedCommercialAmenities);
	}
	
	public static Set<String> getAmenitiesId() {
		return selectedCommercialAmenities;
	}
	
	
	/*
	 * Amenities URL
	 */
	public static void setAmenitiesUrl(JSONArray jsonArray) throws JSONException {
		selectedCommercialAmenitiesUrl.clear();
		for (int i = 0; i < jsonArray.length(); i++) {
			ProjectUtility.sys(isPrint, TAG,i+jsonArray.get(i).toString());
			selectedCommercialAmenitiesUrl.add(jsonArray.get(i).toString());
		}
		ProjectUtility.sys(isPrint, TAG, "selectedAmenities-->"+selectedCommercialAmenitiesUrl);
	}
	
	public static List<String> getAmenitiesUrl() {
		return selectedCommercialAmenitiesUrl; 
	}
	
	/*
	 * Amenities URL
	 */
	public static void setAmenitiesTitle(JSONArray jsonArray) throws JSONException {
		selectedCommercialAmenitiesTitle.clear();
		for (int i = 0; i < jsonArray.length(); i++) {
			ProjectUtility.sys(isPrint, TAG,i+jsonArray.get(i).toString());
			selectedCommercialAmenitiesTitle.add(jsonArray.get(i).toString());
		}
		ProjectUtility.sys(isPrint, TAG, "selectedAmenities-->"+selectedCommercialAmenitiesTitle);
	}
	
	public static List<String> getAmenitiesTitle() {
		return selectedCommercialAmenitiesTitle; 
	}

	public static void clearSelectedAmenities() {
		if(selectedCommercialAmenities != null) {
			checkAll = false;
			selectedCommercialAmenities.clear();
		}
		
		/*
		 * Clear all selected amenities and url
		 */
		if(selectedCommercialAmenitiesUrl != null)
			selectedCommercialAmenitiesUrl.clear();

		/*
		 * Clear all selected amenities and url
		 */
		if(selectedCommercialAmenitiesTitle!= null)
			selectedCommercialAmenitiesTitle.clear();
	}
}
