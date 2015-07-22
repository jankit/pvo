package z.com.pvo.newAdapter;

import java.util.List;

import z.com.pvo.util.ProjectUtility;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pvo.activity.R;

public class ZNomineeCallListAdaptor extends ArrayAdapter<ZNomineeItem> {
	
	private static Boolean isPrint = true;
	private static String TAG = "NomineeListCommercialAdaptor";
	private Context context;
	
	public ZNomineeCallListAdaptor(Context context, int resourceId, List<ZNomineeItem> objects) {
		super(context, resourceId, objects);
		ProjectUtility.sys(isPrint, TAG,"Construction");
		this.context = context;
	}

	@Override
	  public View getView (int position, View view, ViewGroup parent ) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    view = inflater.inflate(R.layout.z_nominee_call_list_row, parent, false);
	    final ZNomineeItem nomineeItem = getItem(position);
	    
	    /*
	     * Nominee name 
	     */
	    TextView tv_nomineeCall_name = (TextView) view.findViewById(R.id.tv_nomineeCall_name);
	    tv_nomineeCall_name.setText(nomineeItem.getNomineeName());
	    tv_nomineeCall_name.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(nomineeItem.getNumber().length() > 0) {
					Intent callIntent = new Intent(Intent.ACTION_DIAL);
			        callIntent.setData(Uri.parse("tel:"+Uri.encode(nomineeItem.getNumber())));
			        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			        context.startActivity(callIntent); 
				}
			}
		});
	    
	    /*
	     * Call button 
	     */
	    ImageView iv_nomineeCall_call = (ImageView) view.findViewById(R.id.iv_nomineeCall_call);
	    iv_nomineeCall_call.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(nomineeItem.getNumber().length() > 0) {
					Intent callIntent = new Intent(Intent.ACTION_DIAL);
			        callIntent.setData(Uri.parse("tel:"+Uri.encode(nomineeItem.getNumber())));
			        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			        context.startActivity(callIntent); 
				}
			}
		});
		return view;
	}
}
