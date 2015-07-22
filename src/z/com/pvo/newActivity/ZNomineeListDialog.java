package z.com.pvo.newActivity;

import android.widget.RelativeLayout;
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
import android.widget.ProgressBar;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;

import com.pvo.activity.R;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

//http://www.thaicreate.com/mobile/android-gridview-checkbox.html
public class ZNomineeListDialog extends Dialog implements android.view.View.OnClickListener {
    
    private Boolean isPrint = true;
    private String TAG = "NomineeListDialog";
    
	private GridView grd_nominee_list;
    private Context context;
    public static CheckBox chk_nomineeList_checkAll;
    private String from;
    
    private ProgressBar progressbar;
    private RelativeLayout rl_nomineeList_chkAll;
    
    private UserSessionManager userSessionManager;
    
    public ZNomineeListDialog(Context context,String from) {
    	super(context);
    	this.context = context;
    	this.from = from;
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ProjectUtility.sys(isPrint, TAG, "On Create");
        
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.z_nominee_list_dialog);
        setCancelable(false);
        
        userSessionManager = new UserSessionManager(getContext());
        
        rl_nomineeList_chkAll = (RelativeLayout) findViewById(R.id.rl_nomineeList_chkAll);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        // gridView1
        grd_nominee_list = (GridView)findViewById(R.id.grd_nominee_list); 
        ProjectUtility.getNomineeListOfBroker(context, grd_nominee_list,from,progressbar,rl_nomineeList_chkAll,userSessionManager.getSessionValue(Constant.Login.USER_ID));
        
        
		// Get Item Checked
		Button btnGetItem = (Button) findViewById(R.id.btnGetItem);
		btnGetItem.setOnClickListener(this);
		
		/*
		 * Check all check box on check and uncheck on uncheck
		 * And notify adapter
		 */
		chk_nomineeList_checkAll = (CheckBox)  findViewById(R.id.chk_nomineeList_checkAll);
		if(from.equals(Constant.Residential.FROM_RESIDENTIAL)) {
			if(ZNomineeListResidentialAdaptor.checkAll == true)
				chk_nomineeList_checkAll.setChecked(true);
			else
				chk_nomineeList_checkAll.setChecked(false);
		} else if(from.equals(Constant.Commercial.FROM_COMMERCIAL)) {
			if(ZCommercialNomineeListAdaptor.checkAll == true)
				chk_nomineeList_checkAll.setChecked(true);
			else
				chk_nomineeList_checkAll.setChecked(false);
		}
		
		
		
		chk_nomineeList_checkAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(from.equals(Constant.Residential.FROM_RESIDENTIAL)) {
					ZNomineeListResidentialAdaptor.clearSelectedResidentialNominee();
					if(isChecked){
						ZNomineeListResidentialAdaptor.checkAll = true;
					} else {
						ZNomineeListResidentialAdaptor.checkAll = false;
					}
					ProjectUtility.resiNomineeAdapterNotify();
				} else if(from.equals(Constant.Commercial.FROM_COMMERCIAL)) {
					ZCommercialNomineeListAdaptor.clearSelectedCommercialNominee();
					if(isChecked){
						ZCommercialNomineeListAdaptor.checkAll = true;
					} else {
						ZCommercialNomineeListAdaptor.checkAll = false;
					}
					ProjectUtility.commNomineeAdapterNotify();
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
