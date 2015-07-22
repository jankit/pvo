package z.com.pvo.newActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.pvo.activity.R;

//http://stackoverflow.com/questions/20015463/defining-custom-checkbox-in-android
public class ZAminitiesCheckboxCommon extends CheckBox {

	
	
	public ZAminitiesCheckboxCommon(Context context, AttributeSet attrs) {
		super(context, attrs);
		// setButtonDrawable(new StateListDrawable());
	}

	@Override
	public void setChecked(boolean t) {
		if (t) {
			this.setBackgroundResource(R.drawable.z_amenities_select_main);
		} else {
			this.setBackgroundResource(R.drawable.z_amenities_chk_deselect);
		}
		super.setChecked(t);
	}
}