package z.com.pvo.newActivity;

import z.com.pvo.util.ProjectUtility;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.pvo.activity.R;

public class ZTakePhotoDialog extends Dialog implements android.view.View.OnClickListener{

	private  Button btn_addProp_camera;
	private  Button btn_addProp_gallery;
	private GridView grd_prjGallery;
	private Activity activity;
	//private ImageView iv_photoDilog_close;
	private Button btn_photoDilog_ok;
	
	public ZTakePhotoDialog(Context context) {
		super(context);
		this.activity = (Activity) context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.z_prop_photo_dialog_list);
		setCancelable(false);
		btn_addProp_camera = (Button) findViewById(R.id.btn_addResProp_camera);
		btn_addProp_gallery = (Button) findViewById(R.id.btn_addResProp_gallery);
		grd_prjGallery = (GridView) findViewById(R.id.grd_addResProp_photo);
		//iv_photoDilog_close = (ImageView) findViewById(R.id.iv_photoDilog_close);
		btn_photoDilog_ok = (Button) findViewById(R.id.btn_photoDilog_ok);
		
		btn_photoDilog_ok.setOnClickListener(this);
		//iv_photoDilog_close.setOnClickListener(this);
		
		btn_addProp_camera.setOnClickListener(this);
		btn_addProp_gallery.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_addResProp_camera:
				ProjectUtility.openCamera(activity);
				break;
			case R.id.btn_addResProp_gallery:
				ProjectUtility.openGallery(activity);
				break;
			/*case R.id.iv_photoDilog_close:
				Toast.makeText(activity,"Close click", Toast.LENGTH_SHORT).show();
				dismiss();*/
			case R.id.btn_photoDilog_ok:
				Toast.makeText(activity,"Close Ok", Toast.LENGTH_SHORT).show();
				dismiss();
			default:
				break;
		}
	}
	
}
