package z.com.pvo.newActivity;

import z.com.pvo.components.RoundedTransformation;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pvo.activity.R;
import com.squareup.picasso.Picasso;

public class ZPropDetFacilityView extends LinearLayout {
	
	private String url;
	private  String name;
	
	public ZPropDetFacilityView(Context context,String iconURL,String name) {
		super(context);
		url = iconURL;
		this.name = name;
		init(context);
	}
	
	/*
	 * Inflate view and set icon and text 
	 */
	private void init(Context context) {
		View.inflate(context, R.layout.z_prop_det_aemenities_view_common, this);
		ImageView iv_propDetAmenities_icon = (ImageView) findViewById(R.id.iv_propDetAmenities_icon);
		TextView tv_propDetAmenities_name = (TextView) findViewById(R.id.tv_propDetAmenities_name);
		tv_propDetAmenities_name.setText(name);
		
		System.out.println("name-->"+name);
		System.out.println("URL-->"+url);
		
		int valueInPixels = (int) getContext().getResources().getDimension(R.dimen.amenitiesDiaIconWidth);
		int margin = (int) getContext().getResources().getDimension(R.dimen.amenitiesDiaRoundMargin);
		
		/*ZAminitiesCheckboxCommon aminitiesCheckboxCommon = (ZAminitiesCheckboxCommon) findViewById(R.id.chk_addProp_aminities);*/
		Picasso.with(context).load(url).centerCrop().resize(valueInPixels,valueInPixels).transform(new RoundedTransformation(50,margin )).into(iv_propDetAmenities_icon);
	}
}
