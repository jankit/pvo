package z.com.pvo.newActivity;

import java.util.ArrayList;

import z.com.pvo.newAdapter.ZTermsItem;
import z.com.pvo.newAdapter.ZTersmListAdaptor;
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
import android.widget.RelativeLayout;

import com.pvo.activity.R;
import com.pvo.util.Constant;

//http://www.thaicreate.com/mobile/android-gridview-checkbox.html
public class ZTremsListDialog extends Dialog implements android.view.View.OnClickListener {
    
    private GridView grd_trems_list;
    private Context context;
    public static CheckBox chk_tremsList_checkAll;
    private String from;
    private ArrayList<ZTermsItem> treamItems = new ArrayList<ZTermsItem>();
    private ZTersmListAdaptor zTreamListAdaptor;
    private RelativeLayout rl_nomineeList_chkAll;
    
    public ZTremsListDialog(Context context,String from) {
    	super(context);
    	this.context = context;
    	this.from = from;
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.z_nominee_list_dialog);
        setCancelable(false);
        
        rl_nomineeList_chkAll = (RelativeLayout) findViewById(R.id.rl_nomineeList_chkAll);
        rl_nomineeList_chkAll.setVisibility(View.VISIBLE);
        // gridView1
        grd_trems_list = (GridView)findViewById(R.id.grd_nominee_list); 
        for (int i = 0; i < Constant.TREMS.length; i++) {
        	ZTermsItem item = new ZTermsItem();
        	item.setPosition(String.valueOf(i));
        	item.setName(Constant.TREMS[i]);
        	treamItems.add(item);
		}
        zTreamListAdaptor = new ZTersmListAdaptor(getContext(), R.id.grd_nominee_list,treamItems);
        grd_trems_list.setAdapter(zTreamListAdaptor);
        
		// Get Item Checked
		Button btnGetItem = (Button) findViewById(R.id.btnGetItem);
		btnGetItem.setOnClickListener(this);
		
		
		/*
		 * Check all check box on check and uncheck on uncheck
		 * And notify adapter
		 */
		chk_tremsList_checkAll = (CheckBox)  findViewById(R.id.chk_nomineeList_checkAll);
		if(ZTersmListAdaptor.checkAll == true)
			chk_tremsList_checkAll.setChecked(true);
		else
			chk_tremsList_checkAll.setChecked(false);
		
		
		chk_tremsList_checkAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(from.equals(Constant.Residential.FROM_RESIDENTIAL)) {
					ZTersmListAdaptor.selectedTerms.clear();
					if(isChecked){
						ZTersmListAdaptor.checkAll = true;
					} else {
						ZTersmListAdaptor.checkAll = false;
					}
					zTreamListAdaptor.notifyDataSetChanged();
				} else if(from.equals(Constant.Commercial.FROM_COMMERCIAL)) {
				}
			}
		});
      
    }
    
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnGetItem:
				dismiss();
				break;
			default:
				break;
		}
	}
}
