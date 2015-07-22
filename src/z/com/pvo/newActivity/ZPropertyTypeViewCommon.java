package z.com.pvo.newActivity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvo.activity.R;

/*
 * This class  set the view of property type 
 * This class is common for all project where used property type option
 */
public class ZPropertyTypeViewCommon extends LinearLayout {

	private ImageView iv_propTypeRow_icon;
	private TextView tv_propTypRow_name;
	private int iocnId;
	private String title;
	private LinearLayout ll_PropTypeMailLayout;

	/*
	 * This constructor is used to set the property type icon and title
	 * Icon and title set into image view and textview corresponding
	 */
	public ZPropertyTypeViewCommon(Context context, int icon,String title) {
		super(context);
		iocnId = icon;
		this.title = title;
		init(context);
	}

	/*
	 * Inflate view and set icon and text 
	 */
	private void init(Context context) {
		View.inflate(context, R.layout.z_prop_type_view, this);
		//setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
		//ll_PropTypeMailLayout = (LinearLayout) findViewById(R.id.ll_PropTypeMailLayout);
		iv_propTypeRow_icon = (ImageView) findViewById(R.id.iv_propTypeRow_icon);
		iv_propTypeRow_icon.setBackgroundResource(iocnId);
		tv_propTypRow_name = (TextView) findViewById(R.id.tv_propTypRow_name);
		tv_propTypRow_name.setText(title);
		
		/*tv_propTypRow_name.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), tv_propTypRow_name.getText(), Toast.LENGTH_SHORT).show();
			}
		});*/
	}
}
