package z.com.pvo.newActivity;

import z.com.pvo.newAdapter.ZAreaListAdaptor;
import z.com.pvo.util.ProjectUtility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

import com.pvo.activity.R;

//http://blog.andolasoft.com/2013/06/how-to-show-captured-images-dynamically-in-gridview-layout.html
public class ZShareWithAreaListTab extends Fragment implements OnClickListener {
	
	/*private ZAreaListAdaptor areaListAdaptor;
	private ArrayList<ZAraeItem> areaItems = new ArrayList<ZAraeItem>();*/
	private ListView lv_shareWith_area;
	private CheckBox chk_shareWith_allarea;
	public static boolean checkAllFlag; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.z_share_with_list, container, false);
		chk_shareWith_allarea = (CheckBox) v.findViewById(R.id.chk_shareWith_checkBoxAll);
		lv_shareWith_area = (ListView) v.findViewById(R.id.lv_shareWith_listview);
		ProjectUtility.getAreaList(getActivity(), lv_shareWith_area);
		
		
		chk_shareWith_allarea.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				ZAreaListAdaptor.clearSelectedArea();
				if(isChecked)
					checkAllFlag = true;
				else
					checkAllFlag = false;
				ProjectUtility.notifyAreaListAdapter();
			}
		});
		
		return v;
	}
	
	//instantiate All fragment 
	public static ZShareWithAreaListTab newInstance() {
		ZShareWithAreaListTab f = new ZShareWithAreaListTab();
		Bundle b = new Bundle();
		f.setArguments(b);
		return f;
	}

	@Override
	public void onClick(View v) {
		
	}
	
}
