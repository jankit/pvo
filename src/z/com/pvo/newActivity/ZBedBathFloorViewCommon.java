package z.com.pvo.newActivity;

import com.pvo.activity.R;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ZBedBathFloorViewCommon extends LinearLayout {
	
	private ImageView iv_bedBathFolorView_icon;
	/*private ImageView iv_bedBathFolorView_minusIcon;
	private ImageView iv_bedBathFolorView_plusIcon;
	private TextView tv_bedBathFolorView_number;
	private int counter = 1;*/

	/*
	 * This constructor is used to initialize the view,icon,and text
	 */
	public ZBedBathFloorViewCommon(Context context,int icon){
		super(context);
		init(context,icon);
	}
	
	/*
	 * Inflate view and set icon and text 
	 */
	private void init(Context context,int icon) {
		
		View.inflate(context, R.layout.z_bed_bath_floor_view_common, this);
		iv_bedBathFolorView_icon = (ImageView) findViewById(R.id.iv_bedBathFolorView_icon);
		iv_bedBathFolorView_icon.setBackgroundResource(icon);
		/*iv_bedBathFolorView_minusIcon = (ImageView) findViewById(R.id.iv_bedBathFolorView_minusIcon);
		iv_bedBathFolorView_plusIcon = (ImageView) findViewById(R.id.iv_bedBathFolorView_plusIcon);
		tv_bedBathFolorView_number = (TextView) findViewById(R.id.tv_bedBathFolorView_number);*/
		//tv_bedBathFolorView_number.setText(""+counter);
		//tv_bedBathFolorView_number.setText(numberOF);
		
		/*
		 * Minus icon click event 
		 */
		/*iv_bedBathFolorView_minusIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(counter > 1)
					tv_bedBathFolorView_number.setText(""+counter--);
				else 
					tv_bedBathFolorView_number.setText("1");
			}
		});*/
		
		/*
		 * Plus icon click event
		 */
		/*iv_bedBathFolorView_plusIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(counter <= 20)
					tv_bedBathFolorView_number.setText(""+counter++);
				
			}
		});*/
	}
	
}
