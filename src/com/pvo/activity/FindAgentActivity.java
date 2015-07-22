package com.pvo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.components.SpinnerItem;
import com.pvo.json.cache.CreateJsonArrayFileIntoCache;
import com.pvo.prototype.PVOFragment;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

//This is used for search the agent by Specific criteria
public class FindAgentActivity extends PVOFragment {
	
	private EditText findAgentEditText;
	private Spinner findAgentSpinner;
	private Button findAgentSearchButton;
	private Button findAgentCancelBotton;
	private EditText findAgentAreaEditBox;
	private TextView findAgentAreaTextView;
	private TextView findAgentEnterInfoTextView;
	private Spinner findAgentPropTypeSpinner;
	private TextView findAgentPropTypeTextView;
	//Login User Session
	private UserSessionManager userSessionManager;
	//Area List Of Particular List
	private Map<String,Integer> areaMap = new HashMap<String, Integer>();
	
	//set Layout content View 
	public FindAgentActivity() {
		setContentView(R.layout.activity_findagent_main);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		userSessionManager = new UserSessionManager(getActivity());
		
		findAgentEnterInfoTextView = (TextView) findViewById(R.id.findAgentEnterInfoTextView);
		findAgentEditText = (EditText)findViewById(R.id.findAgentEditBox);
		findAgentEnterInfoTextView.setText(Html.fromHtml("Enter Agent Info."+"<sup><font color=red>*</font></sup>"));
		
		findAgentAreaTextView = (TextView) findViewById(R.id.findAgentAreaTextView);
		findAgentAreaEditBox = (EditText) findViewById(R.id.findAgentAreaEditBox);
		findAgentAreaTextView.setText(Html.fromHtml("Select Area "+"<sup><font size=5 color=red>*</font></sup>"));
		
		findAgentPropTypeSpinner = (Spinner) findViewById(R.id.findAgentPropTypeSpinner);
		findAgentPropTypeTextView = (TextView) findViewById(R.id.findAgentPropTypeTextView);
		findAgentPropTypeTextView.setText(Html.fromHtml("Select Property Type "+"<sup><font size=5 color=red>*</font></sup>"));
		
		//Property type spinner
		final ArrayList<String> propertyTypeOptionList=new ArrayList<String>();
		propertyTypeOptionList.addAll(SpinnerItem.getPropertyTypeOptionList().keySet());
		
		//Spinner adapter set property type group like flat,shop,bunglow,plot
		ArrayAdapter< String> prpoertyTypeOptionAdapter= new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,propertyTypeOptionList){
			@Override      
			public boolean isEnabled(int position) {
				if (position == 1) 
					return false;
				if (position == 3) 
					return false;
				if (position == 14) 
					return false;
				if (position == 23) 
					return false;
				return true;
			}

           @Override                
           public boolean areAllItemsEnabled() {
              return false;
           }

            //set the group label text and color
            @Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				View v = convertView;
				if (v == null) {
					Context mContext = this.getContext();
					LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					v = vi.inflate(R.layout.spinner_text, null);
				}

				TextView tv = (TextView) v.findViewById(R.id.spinnerTextView);
				tv.setText(propertyTypeOptionList.get(position));

				switch (position) {
					case 1:
						tv.setTextColor(Color.GRAY);
						tv.setTypeface(null, Typeface.ITALIC);
						break;
					case 3:
						tv.setTextColor(Color.GRAY);
						tv.setTypeface(null, Typeface.ITALIC);
						break;
					case 14:
						tv.setTextColor(Color.GRAY);
						tv.setTypeface(null, Typeface.ITALIC);
						break;
					case 23:
						tv.setTextColor(Color.GRAY);
						tv.setTypeface(null, Typeface.ITALIC);
						break;
					default:
						tv.setTextColor(Color.BLACK);
						tv.setTypeface(null, Typeface.NORMAL);
						break;
				}
				return v;
			}
		};
		prpoertyTypeOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
		findAgentPropTypeSpinner.setAdapter(prpoertyTypeOptionAdapter);
		//findAgentPropTypeSpinner.setSelection(0);
		
		//Fill the Find Agent Criteria Spinner 
		findAgentSpinner = (Spinner) findViewById(R.id.findAgentSpinner);
		ArrayList<String> findAgentCriteriaList = new ArrayList<String>();
		findAgentCriteriaList.addAll((SpinnerItem.getFindAgentList()).keySet());
		ArrayAdapter< String> findAgentCriteriaAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,findAgentCriteriaList);
		findAgentCriteriaAdapter.setDropDownViewResource(R.layout.spinner_text);
		findAgentSpinner.setAdapter(findAgentCriteriaAdapter);
		//on spinner item selected hide the select location edit box
		findAgentSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(findAgentSpinner.getSelectedItem().equals("Area Deals in")) {
					findAgentEnterInfoTextView.setVisibility(View.GONE);
					findAgentEditText.setVisibility(View.GONE);
					findAgentAreaEditBox.setVisibility(View.VISIBLE);
					findAgentAreaTextView.setVisibility(View.VISIBLE);
					findAgentPropTypeSpinner.setVisibility(View.GONE);
					findAgentPropTypeTextView.setVisibility(View.GONE);
				} else if(findAgentSpinner.getSelectedItem().equals("Property Type Deals in")){
					findAgentEnterInfoTextView.setVisibility(View.GONE);
					findAgentEditText.setVisibility(View.GONE);
					findAgentAreaEditBox.setVisibility(View.GONE);
					findAgentAreaTextView.setVisibility(View.GONE);
					findAgentPropTypeSpinner.setVisibility(View.VISIBLE);
					findAgentPropTypeTextView.setVisibility(View.VISIBLE);
				} else {
					findAgentEnterInfoTextView.setVisibility(View.VISIBLE);
					findAgentEditText.setVisibility(View.VISIBLE);
					findAgentAreaEditBox.setVisibility(View.GONE);
					findAgentAreaTextView.setVisibility(View.GONE);
					findAgentPropTypeSpinner.setVisibility(View.GONE);
					findAgentPropTypeTextView.setVisibility(View.GONE);
				}
				
				//set input type phone number is spinner item is phone number
				if(findAgentSpinner.getSelectedItem().equals("Phone Number")) {
					findAgentEditText.setInputType(InputType.TYPE_CLASS_PHONE);
					int maxLength = 10;
					InputFilter[] fArray = new InputFilter[1];
					fArray[0] = new InputFilter.LengthFilter(maxLength);
					findAgentEditText.setFilters(fArray);
				} else {
					findAgentEditText.setInputType(InputType.TYPE_CLASS_TEXT);
				}
				findAgentEditText.setText("");
				findAgentAreaEditBox.setText("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		//Area Edit box click event
		findAgentAreaEditBox.setFocusable(false);
		findAgentAreaEditBox.setInputType(InputType.TYPE_NULL);
		findAgentAreaEditBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				findAgentAreaEditBox.setError(null);
				getAreaList();
			}
		});
			
		//click event of Find Agent Button
		findAgentSearchButton=(Button)findViewById(R.id.findAgentBotton);
		findAgentSearchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				searchResult();
				/*if (findAgentEditText.length() == 0 && findAgentEditText.isShown())
					findAgentEditText.setError("Enter Search String");
				if(findAgentAreaEditBox.length() == 0 && findAgentAreaEditBox.isShown())
					findAgentAreaEditBox.setError("Please Select Location");
				//(findAgentSpinner.getSelectedItem().equals("Property Type Deals in") && findAgentPropTypeSpinner.isShown())
				if((findAgentEditText.length() > 0 && findAgentEditText.isShown()) || (findAgentAreaEditBox.length() > 0 && findAgentAreaEditBox.isShown()) || !checkPropertyType()) {
					//Comma Seprated String of Selected area Code
					String selectedAreaString = "";
					if(areaMap != null ) {
						String area = findAgentAreaEditBox.getText().toString();
						String[] selectedArea = area.split(" - ");
						
						for (int i = 0; i < selectedArea.length; i++) {
							selectedAreaString += areaMap.get(selectedArea[i]);
							if (i != selectedArea.length - 1) 
								selectedAreaString += ",";
						}
					} else {
						selectedAreaString  = userSessionManager.getSessionValue(Constant.Login.AREA_DEALS_IN);
					}
					
					Bundle findAgentBundle = new  Bundle();
					findAgentBundle.putString("Type","Search");
					findAgentBundle.putString(Constant.FindAgent.CMBFIELDS, String.valueOf(SpinnerItem.getFindAgentList().get(findAgentSpinner.getSelectedItem().toString())));
					
					//pass area id if spinner selected item is 'Area'
					if(findAgentSpinner.getSelectedItem().equals("Area Deals in")) 
						findAgentBundle.putString(Constant.FindAgent.SEARCH_STRING, selectedAreaString);
					else if(findAgentSpinner.getSelectedItem().equals("Property Type Deals in"))
						findAgentBundle.putString(Constant.FindAgent.SEARCH_STRING, String.valueOf(SpinnerItem.getPropertyTypeOptionList().get(findAgentPropTypeSpinner.getSelectedItem().toString())));
					else if(findAgentSpinner.getSelectedItem().equals("Name") || findAgentSpinner.getSelectedItem().equals("Phone Number")) 
						findAgentBundle.putString(Constant.FindAgent.SEARCH_STRING, findAgentEditText.getText().toString());
					
					FindAgentListActivity findAgentListActivity = new FindAgentListActivity();
					findAgentListActivity.setArguments(findAgentBundle);
					((MainFragmentActivity)getActivity()).redirectScreen(findAgentListActivity);
				}*/
			}
		});
		
		//Redirect to previous Fragment
		findAgentCancelBotton = (Button) findViewById(R.id.findAgentCancelBotton);
		findAgentCancelBotton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});
	} 

	@Override
	public void processResponse(Object response) {}
	
	//get the list of area(Location)
	public void getAreaList(){
		JSONArray jsonArray = CreateJsonArrayFileIntoCache.readAreaListJsonData(getActivity().getApplicationContext());
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				areaMap.put(jsonObject.getString(Constant.Area.AREA_NAME).trim(),jsonObject.getInt(Constant.Area.AREA_ID));
			}
			openAreaListOfCityPopupWindow("areaMap", findAgentAreaEditBox, areaMap);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	//set the title on resume
	@Override
	public void onResume() {
		super.onResume();
		this.getActivity().getActionBar().setTitle("Find Agent");
	}
	
	//Check property type
	private boolean checkPropertyType() {
		if(findAgentSpinner.getSelectedItem().equals("Property Type Deals in")) {
			if(findAgentPropTypeSpinner.getSelectedItem().equals(" Select ") && findAgentPropTypeSpinner.isShown()) {
				System.out.println("Agent Property condition");
				Toast.makeText(getActivity(), "Please seelct Property Type", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	
	//Check Naem strrin 
	private boolean checkAgentInfo() {
		if(findAgentSpinner.getSelectedItem().equals("Name")) {
			if(findAgentEditText.isShown() && findAgentEditText.getText().length() == 0) {
				System.out.println("Agent name condition");
				findAgentEditText.setError("Please enter agent Info");
				Toast.makeText(getActivity(), "Please enter agent name", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	 
	//Check Naem strrin 
	private boolean checkAgentNumber() {
		if(findAgentSpinner.getSelectedItem().equals("Phone Number")) {
			if(findAgentEditText.isShown() && findAgentEditText.getText().length() == 0) {
				System.out.println("Agent number condition");
				findAgentEditText.setError("Please enter agent Info");
				Toast.makeText(getActivity(), "Please enter agent number", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	
	//Check Location(area) 
	private boolean checkAgentAreaDealsIn() {
		if(findAgentSpinner.getSelectedItem().equals("Area Deals in")) {
			if(findAgentAreaEditBox.isShown() && findAgentAreaEditBox.getText().length() == 0) {
				System.out.println("Agent area condition");
				findAgentEditText.setError("Please select agent area deals in");
				Toast.makeText(getActivity(), "Please select agent area deals in", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}

	private void searchResult() {
		System.out.println("name===> "+checkAgentInfo());
		System.out.println("Number===> "+checkAgentNumber());
		System.out.println("Area deals in===> "+checkAgentAreaDealsIn());
		System.out.println("Area Property deals in===> "+checkPropertyType());
		//Comma Seprated String of Selected area Code
		String selectedAreaString = "";
		if(areaMap != null ) {
			String area = findAgentAreaEditBox.getText().toString();
			String[] selectedArea = area.split(" - ");
			
			for (int i = 0; i < selectedArea.length; i++) {
				selectedAreaString += areaMap.get(selectedArea[i]);
				if (i != selectedArea.length - 1) 
					selectedAreaString += ",";
			}
		} else {
			selectedAreaString  = userSessionManager.getSessionValue(Constant.Login.AREA_DEALS_IN);
		}
		
		
		Bundle findAgentBundle = new  Bundle();
		findAgentBundle.putString("Type","Search");
		findAgentBundle.putString(Constant.FindAgent.CMBFIELDS, String.valueOf(SpinnerItem.getFindAgentList().get(findAgentSpinner.getSelectedItem().toString())));
		
		//pass area id if spinner selected item is 'Area'
		if(!checkAgentAreaDealsIn() && findAgentAreaEditBox.isShown())  {
			System.out.println("Find agent by Arae deals in");
			findAgentBundle.putString(Constant.FindAgent.SEARCH_STRING, selectedAreaString);
			FindAgentListActivity findAgentListActivity = new FindAgentListActivity();
			findAgentListActivity.setArguments(findAgentBundle);
			((MainFragmentActivity)getActivity()).redirectScreen(findAgentListActivity);
		} else if(!checkPropertyType() && findAgentPropTypeSpinner.isShown()) {
			System.out.println("Find agent by Property typre deals in");
			findAgentBundle.putString(Constant.FindAgent.SEARCH_STRING, String.valueOf(SpinnerItem.getPropertyTypeOptionList().get(findAgentPropTypeSpinner.getSelectedItem().toString())));
			FindAgentListActivity findAgentListActivity = new FindAgentListActivity();
			findAgentListActivity.setArguments(findAgentBundle);
			((MainFragmentActivity)getActivity()).redirectScreen(findAgentListActivity);
		} else if(!checkAgentInfo() && findAgentEditText.isShown() && findAgentEditText.length() > 0 ) { 
			System.out.println("Find agent by name");
			findAgentBundle.putString(Constant.FindAgent.SEARCH_STRING, findAgentEditText.getText().toString());
			FindAgentListActivity findAgentListActivity = new FindAgentListActivity();
			findAgentListActivity.setArguments(findAgentBundle);
			((MainFragmentActivity)getActivity()).redirectScreen(findAgentListActivity);
		} else if(!checkAgentNumber() && findAgentEditText.isShown() && findAgentEditText.length() > 0) {
			System.out.println("Find agent by number");
			findAgentBundle.putString(Constant.FindAgent.SEARCH_STRING, findAgentEditText.getText().toString());
			FindAgentListActivity findAgentListActivity = new FindAgentListActivity();
			findAgentListActivity.setArguments(findAgentBundle);
			((MainFragmentActivity)getActivity()).redirectScreen(findAgentListActivity);
		}
	}
}
