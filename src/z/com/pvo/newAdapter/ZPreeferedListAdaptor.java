package z.com.pvo.newAdapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;

import z.com.pvo.newActivity.ZShareWithPreeferedListTab;
import z.com.pvo.util.ProjectUtility;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.pvo.activity.R;

public class ZPreeferedListAdaptor extends ArrayAdapter<ZPreeferedItem> {
	
	
	protected static Boolean isPrint = true;
	private static String TAG = "PreeferedListAdaptor";
	
	private Context context;
	//public static ArrayList<String> selectedPreeferedIdArray = new ArrayList<String>();;
	public static Set<String> selectedPreferedBroker = new HashSet();
	
	public ZPreeferedListAdaptor(Context context, int resourceId, List<ZPreeferedItem> objects) {
		super(context, resourceId, objects);
		ProjectUtility.sys(isPrint, TAG,"Construction");
		this.context = context;
	}

	@Override
	  public View getView ( final int position, View view, ViewGroup parent ) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    view = inflater.inflate(R.layout.z_share_with_list_row, parent, false);
	    final ZPreeferedItem preeferedItem = getItem(position);
	    
	    CheckBox chkShareWithPreferedBroker = (CheckBox) view.findViewById(R.id.chk_shareWith_checkBox);
	    chkShareWithPreferedBroker.setText(preeferedItem.getPreeferedName());
	    
	    if(ZShareWithPreeferedListTab.checkAllBroker) {
	    	chkShareWithPreferedBroker.setChecked(true);
	    	selectedPreferedBroker.add(preeferedItem.getPreeferedID());
	    	ProjectUtility.sys(isPrint, TAG,"selectedPrefferredIdArray All--> "+selectedPreferedBroker);
	    	/*if(!selectedPreeferedIdArray.contains(preeferedItem.getPreeferedID()))
	    		selectedPreeferedIdArray.add(preeferedItem.getPreeferedID());*/
	    }
	    
	    
	    
	    /*
	     * Set checked broker as it is when open again
	     */
	    if(selectedPreferedBroker.size() > 0) {
	    	if(selectedPreferedBroker.contains(preeferedItem.getPreeferedID())) {
	    		chkShareWithPreferedBroker.setChecked(true);
	    	}
	    	
	    	/*for (int i = 0; i < selectedPreeferedIdArray.size(); i++) {
				if(selectedPreeferedIdArray.get(i).equals(preeferedItem.getPreeferedID())) {
					chk_shareWith_checkBox.setChecked(true);
				}
			}*/
	    }
	    
	    /*
	     * group check box check change event
	     */
	    chkShareWithPreferedBroker.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				ProjectUtility.sys(isPrint, TAG,"Before"+selectedPreferedBroker);
				if (isChecked) {
					/*System.out.println(preeferedItem);
					System.out.println(preeferedItem.getPreeferedID());
					System.out.println(selectedPreferedBroker);
					System.out.println(selectedPreferedBroker.size());*/
					selectedPreferedBroker.add(preeferedItem.getPreeferedID().trim());
					/*System.out.println(selectedPreferedBroker);
					System.out.println(selectedPreferedBroker.size());*/
					ProjectUtility.sys(isPrint, TAG,"preeferedItem.getPreeferedID()"+preeferedItem.getPreeferedID());
				} else {
					selectedPreferedBroker.remove(preeferedItem.getPreeferedID());
					ZShareWithPreeferedListTab.checkAllBroker = false;
				}
				ProjectUtility.sys(isPrint, TAG,"ZPreeferedListAdaptor"+selectedPreferedBroker);
			}
		});
		return view;
	}
	
	public static void setSelecttedPreferredBroker(List<String> brokerList) throws JSONException {
		selectedPreferedBroker.clear();
		for (int i = 0; i < brokerList.size(); i++) {
			ProjectUtility.sys(isPrint, TAG,i+brokerList.get(i).toString());
			selectedPreferedBroker.add(brokerList.get(i).toString());
		}
		ProjectUtility.sys(isPrint, "ZPreeferedListAdaptor", "selectedPreferedBroker-->"+selectedPreferedBroker);
	}
	
	
	public static Set<String> getSelectedPreeferedId() {
		return selectedPreferedBroker;
	} 
	
	public static void clearSelectedPreferedBroker() {
		if(selectedPreferedBroker != null) {
			ZShareWithPreeferedListTab.checkAllBroker = false;
			selectedPreferedBroker.clear();
		}
	}
}

