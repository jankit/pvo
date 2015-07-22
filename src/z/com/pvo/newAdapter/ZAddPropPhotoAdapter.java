package z.com.pvo.newAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import z.com.pvo.newDailog.Effectstype;
import z.com.pvo.newDailog.NiftyDialogBuilder;
import z.com.pvo.util.ProjectUtility;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.pvo.activity.R;
import com.squareup.picasso.Picasso;

public class ZAddPropPhotoAdapter extends BaseAdapter {
	
	private Boolean isPrint = true;
	private String TAG = "AddPropPhotoAdapter";
	
	private Context context;
	private List<String> imgPic;
	public static boolean flag = true;
	public static boolean gridClientChooseMode = true;
	public static List<String> deletePic;
	//private static String from;
	
	public ZAddPropPhotoAdapter(Context c, List<String> imagePathList) {
		ProjectUtility.sys(isPrint, TAG, "Construction");
		
		context = c;
		imgPic  = imagePathList;
		deletePic = new ArrayList<String>();
	}

	public int getCount() {
		if (imgPic != null) {
			return imgPic.size();
		} else {
			return 0;
		}
	}
	
	// ---returns the ID of an item---
	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	// ---returns an ImageView view---
	@SuppressLint("NewApi")
	public View getView(final int position, View view, final ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    view = inflater.inflate(R.layout.z_prop_photo_dialog_row, parent, false);
	   
		final ZSquareImageView imageView = (ZSquareImageView) view.findViewById(R.id.iv_refRackRow_photo);
		imageView.setScaleType(ScaleType.FIT_XY);
		Uri uri = Uri.fromFile(new File(imgPic.get(position)));
		//imageView.setImageURI(uri);
		Picasso.with(context).load(uri).resize(100, 100).centerCrop().into(imageView);
		
		/*
		 * On click of remove iocn remove rack photo and notify adapter
		 */
		ImageView iv_refRackRow_photo_remove = (ImageView) view.findViewById(R.id.iv_refRackRow_photo_remove);
		iv_refRackRow_photo_remove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				deletePropPhoto(position);
			}
		});
		
	return view;
  }
	
	/*
	 * Delete photo from referigeration rack photo directory
	 * And notify adapter
	 * 
	 */
	private void deletePropPhoto(final int position) {
		
		View view = LayoutInflater.from(context).inflate(R.layout.z_prop_photo_delete_dialog,null);
		TextView edt_searchitem_item = (TextView) view.findViewById(R.id.edt_searchitem_item);
		edt_searchitem_item.setText("Are you sure want to delete this photo?");
		
  		final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(context);
		dialogBuilder.isCancelableOnTouchOutside(true).withDuration(700).withEffect(Effectstype.Shake).setCustomView(view,context).show();
		 
		Button btn_closePrj_yes = (Button) view.findViewById(R.id.btn_closePrj_yes);
		btn_closePrj_yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialogBuilder.cancel();
				File file = new File(imgPic.get(position));
				file.delete();
				imgPic.remove(position);
				notifyDataSetChanged();
			}
		});
		
		Button btn_close_prj_no = (Button) view.findViewById(R.id.btn_close_prj_no);
		btn_close_prj_no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialogBuilder.cancel();
			}
		});
	}
	
	public void notifyAdapter() {
		notifyDataSetChanged();
	}
	
}
