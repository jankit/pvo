package z.com.pvo.newActivity;

import org.apache.commons.lang3.StringUtils;

import z.com.pvo.newAdapter.ZAminitiesCommertialAdaptor;
import z.com.pvo.newAdapter.ZAminitiesResidentialAdaptor;
import z.com.pvo.newAdapter.ZCommercialNomineeListAdaptor;
import z.com.pvo.newAdapter.ZNomineeListResidentialAdaptor;
import z.com.pvo.util.ProjectUtility;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;

import com.pvo.activity.R;
import com.pvo.util.Constant;

//http://www.thaicreate.com/mobile/android-gridview-checkbox.html
public class ZAmenitiesListDialog extends Dialog implements android.view.View.OnClickListener {
    
    private GridView grd_aminities_gridview;
    private Context context;
    private CheckBox chk_amenitites_checkAll;
    private Button btn_aminities_ok;
    private String from;
    
    public ZAmenitiesListDialog(Context context,String from) {
    	super(context);
    	this.context = context;
    	this.from = from;
    	
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.z_amenities_list_dialog);
       
        setCancelable(false);
        btn_aminities_ok = (Button) findViewById(R.id.btn_aminities_ok);
        
        // gridView1
        grd_aminities_gridview = (GridView)findViewById(R.id.grd_aminities_gridview); 
        ProjectUtility.getFacilityList(context, grd_aminities_gridview,from);
        
        /*
         * Select all amenities on check this check button and uncheck on uncheck button
         */
        chk_amenitites_checkAll = (CheckBox) findViewById(R.id.chk_amenitites_checkAll);
        if(from.equals(Constant.Residential.FROM_RESIDENTIAL)) {
			if(ZNomineeListResidentialAdaptor.checkAll == true)
				chk_amenitites_checkAll.setChecked(true);
			else
				chk_amenitites_checkAll.setChecked(false);
		} else if(from.equals(Constant.Commercial.FROM_COMMERCIAL)) {
			if(ZCommercialNomineeListAdaptor.checkAll == true)
				chk_amenitites_checkAll.setChecked(true);
			else
				chk_amenitites_checkAll.setChecked(false);
		}
        
        
        
        
        chk_amenitites_checkAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(from.equals(Constant.Residential.FROM_RESIDENTIAL)) {
					ZAminitiesResidentialAdaptor.clearSelectedAmenities();
					if(isChecked)
						ZAminitiesResidentialAdaptor.checkAll = true;
					else
						ZAminitiesResidentialAdaptor.checkAll = false;
					ProjectUtility.notifyResidentialAmenitiesAdapter();
				} else if(from.equals(Constant.Commercial.FROM_COMMERCIAL)) {
					ZAminitiesCommertialAdaptor.clearSelectedAmenities();
					if(isChecked)
						ZAminitiesCommertialAdaptor.checkAll = true;
					else
						ZAminitiesCommertialAdaptor.checkAll = false;
					ProjectUtility.notifyCommertialAmenitiesAdapter();
				}
			}
		});
        
        btn_aminities_ok.setOnClickListener(this);
    }

    
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_aminities_ok:
				System.out.println("Amenitites : "+StringUtils.join(ZAminitiesResidentialAdaptor.getAmenitiesId(), ","));
				if(from.equals(Constant.Residential.FROM_RESIDENTIAL)) {
					ZPropAddRecidentialFragment.showSelectedFacility();
				} else if(from.equals(Constant.Commercial.FROM_COMMERCIAL)) {
					ZPropAddCommercialFragment.showSelectedFacility();
				}
					
				dismiss();
				break;
			default:
				break;
		}
	}
}
