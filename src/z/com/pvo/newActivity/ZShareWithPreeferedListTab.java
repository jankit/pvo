package z.com.pvo.newActivity;

import z.com.pvo.newAdapter.ZPreeferedListAdaptor;
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
public class ZShareWithPreeferedListTab extends Fragment implements OnClickListener {
	
	private ListView lv_shareWith_preefered;
	private CheckBox chk_shareWith_allPreefered;
    public static boolean checkAllBroker; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.z_share_with_list, container, false);
		chk_shareWith_allPreefered = (CheckBox) v.findViewById(R.id.chk_shareWith_checkBoxAll);
		lv_shareWith_preefered = (ListView) v.findViewById(R.id.lv_shareWith_listview);
		ProjectUtility.getPreeferedBrokerList(getActivity(), lv_shareWith_preefered);
		
		chk_shareWith_allPreefered.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				ZPreeferedListAdaptor.clearSelectedPreferedBroker();
				if(isChecked)
					checkAllBroker = true;
				else
					checkAllBroker = false;
				ProjectUtility.notifyPreeferedListAdapter();;
			}
		});
		return v;
	}
	
	//instantiate All fragment 
	public static ZShareWithPreeferedListTab newInstance() {
		ZShareWithPreeferedListTab f = new ZShareWithPreeferedListTab();
		Bundle b = new Bundle();
		f.setArguments(b);
		return f;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}
