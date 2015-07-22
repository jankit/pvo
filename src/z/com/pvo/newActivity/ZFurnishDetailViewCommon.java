package z.com.pvo.newActivity;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvo.activity.R;

public class ZFurnishDetailViewCommon  extends LinearLayout {
	
	private TextView tv_furnishDet_fully;
	private TextView tv_furnishDet_semi;
	private TextView tv_furnishDet_un;
	private String fullyTitle;
	private String semiTitle;
	private String unTitle;

	public ZFurnishDetailViewCommon(Context context,String txtFully,String txtSemi,String txtUn) {
		super(context);
		fullyTitle = txtFully;
		semiTitle = txtSemi;
		unTitle = txtUn;
		init(context);
	}
	
	/*
	 * Inflate view and set icon and text 
	 */
	private void init(Context context) {
		View.inflate(context, R.layout.z_furnish_detail_view, this);
		//setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
		tv_furnishDet_fully = (TextView) findViewById(R.id.tv_furnishDet_fully);
		tv_furnishDet_semi = (TextView) findViewById(R.id.tv_furnishDet_semi);
		tv_furnishDet_un = (TextView) findViewById(R.id.tv_furnishDet_un);
		tv_furnishDet_fully.setText(fullyTitle);
		tv_furnishDet_semi.setText(semiTitle);
		tv_furnishDet_un.setText(unTitle);
	}
}
