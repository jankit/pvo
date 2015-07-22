package z.com.pvo.newActivity;

import z.com.pvo.util.ProjectUtility;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.activity.R;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

//http://www.thaicreate.com/mobile/android-gridview-checkbox.html
public class ZNomineeCallListDialog extends Dialog implements android.view.View.OnClickListener {
    
    private Boolean isPrint = true;
    private String TAG = "NomineeCallListDialog";
    
	private GridView grd_nomineeCall_list;
    private Context context;
    
    private ProgressBar progressbar;
    private RelativeLayout rl_nomineeCall_chkAll;
    
    private TextView tv_nomineeCall_name;
    private String brokerName, brokerNumber,from, brokerId; 
    private ImageView iv_nomineeCallList_call;
   
    
    public ZNomineeCallListDialog(Context context,String from,String brokerName,String brokerNmuber, String brokerId) {
    	super(context);
    	this.context = context;
    	this.from = from;
    	this.brokerName = brokerName;
    	this.brokerNumber = brokerNmuber;
    	this.brokerId = brokerId;
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ProjectUtility.sys(isPrint, TAG, "On Create");
        
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.z_nominee_call_list_dialog);
        setCancelable(false);
        
        /*
         * Set broker name
         */
        tv_nomineeCall_name = (TextView) findViewById(R.id.tv_nomineeCall_name);
        tv_nomineeCall_name.setText(brokerName);
        
        iv_nomineeCallList_call = (ImageView) findViewById(R.id.iv_nomineeCallList_call);
        iv_nomineeCallList_call.setOnClickListener(this);
        
        rl_nomineeCall_chkAll = (RelativeLayout) findViewById(R.id.rl_nomineeCall_chkAll);
        progressbar = (ProgressBar) findViewById(R.id.prog_nomineeCall_progressbar);
        // gridView1
        grd_nomineeCall_list = (GridView)findViewById(R.id.grd_nomineeCall_list); 
        ProjectUtility.getNomineeListOfBroker(context, grd_nomineeCall_list,from,progressbar,rl_nomineeCall_chkAll,brokerId);
        
        
		// Get Item Checked
		Button btn_nomineeCall_ok = (Button) findViewById(R.id.btn_nomineeCall_ok);
		btn_nomineeCall_ok.setOnClickListener(this);
    }
    
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_nomineeCall_ok:
				dismiss();
				break;
			case R.id.iv_nomineeCallList_call:
				if(brokerNumber.length() > 0) {
					Intent callIntent = new Intent(Intent.ACTION_DIAL);
			        callIntent.setData(Uri.parse("tel:"+Uri.encode(brokerNumber)));
			        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			        context.startActivity(callIntent); 
				} else {
					Toast.makeText(getContext(), "Not valid number "+brokerNumber, Toast.LENGTH_SHORT).show();
				}
			default:
				break;
		}
	}
}
