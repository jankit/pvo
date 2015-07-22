/**
 *  This class is used for new Registration of user if not Register
 *  in this user can enter the mobile number and Verification code   
 *  registration successfully auto generate password will be sent via sms on mobile number.
 * */

package com.pvo.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.json.cache.CreateJsonArrayFileIntoCache;
import com.pvo.prototype.PVOAction;
import com.pvo.prototype.PVOActivity;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.CityListService;
import com.pvo.user.service.DistrictListService;
import com.pvo.user.service.RegisterService;
import com.pvo.util.Constant;
import com.pvo.util.JSONUtils;

public class RegistrationActivity extends PVOActivity {
	
	private EditText mobileNoEditText;
	private EditText verificationEditText;
	private EditText firstNameEditBox_register;
	private Button register;
	private Button login;
	private RegisterService registerService;
	private SharedPreferences prefs;
	//State 
	private Spinner stateSpinner;
	private JSONArray stateResponse;
	private JSONObject stateObject;
	private TextView tvCity;
	private TextView tvState;
	private TextView tvDistrict;
	
	//City List Web Service
	private CityListService cityListService;
	private JSONArray cityResponse;
	private ArrayAdapter<String> cityArrayAdapter;
	private Spinner citySpinner;
	private JSONObject cityObject;
	
	//District List Web Service
	private DistrictListService districtListService;
	private JSONArray districtResponse;
	private ArrayAdapter<String> districtArrayAdapter;
	private JSONObject districtObject;
	private Spinner districtSpinner;
	
	private final String DISTRICT = "district";
	private final String CITY = "city";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		prefs 	= this.getSharedPreferences(SplashScreenActivity.PVOREGID, Context.MODE_PRIVATE);
		final String registerGCMId 				= prefs.getString(SplashScreenActivity.PROPERTY_REG_ID,"");
		
		//This Line is used to hide the keyboard
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		registerService = new RegisterService();
		cityListService = new CityListService();
		districtListService = new DistrictListService();
		
		tvState = (TextView) findViewById(R.id.tvState);
		tvState.setText(Html.fromHtml("State" + "<sup><font size=5 color=red>*</font></sup>"));
		tvDistrict = (TextView) findViewById(R.id.tvDistrict);
		tvDistrict.setText(Html.fromHtml("District" + "<sup><font size=5 color=red>*</font></sup>"));
		tvCity = (TextView) findViewById(R.id.tvCity);
		tvCity.setText(Html.fromHtml("City" + "<sup><font size=5 color=red>*</font></sup>"));
		
		mobileNoEditText = (EditText)findViewById(R.id.mobileNumberEditBox_register);
		verificationEditText=(EditText)findViewById(R.id.VerificatonCodeEditBox_register);
		firstNameEditBox_register = (EditText) findViewById(R.id.firstNameEditBox_register);
		
		stateSpinner = (Spinner) findViewById(R.id.registerStateSpinner);
		districtSpinner = (Spinner) findViewById(R.id.registerDistrictSpinner);
		citySpinner = (Spinner) findViewById(R.id.registerCitySpinner);
		
		//Register Button Click event
		register=(Button)findViewById(R.id.registerBotton);
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mobileNoEditText.length() == 0) 
					mobileNoEditText.setError("Enter Mobile Number");
				
				if (verificationEditText.length() == 0) 
					verificationEditText.setError("Enter Verification Code");
				
				if(firstNameEditBox_register.getText().toString().length() == 0)
					firstNameEditBox_register.setError("Enter First Name");
				
				if (!checkState()  && !checkDistrict() && !checkCity() && mobileNoEditText.length() > 0 && verificationEditText.length() > 0 && firstNameEditBox_register.getText().toString().length() > 0 ) {
					try {
						stateObject = stateResponse.getJSONObject(stateSpinner.getSelectedItemPosition()-1);
						cityObject = cityResponse.getJSONObject(citySpinner.getSelectedItemPosition()-1);
						districtObject = districtResponse.getJSONObject(districtSpinner.getSelectedItemPosition()-1);
					
						new WebserviceClient((PVOAction)RegistrationActivity.this, registerService).execute(
							mobileNoEditText.getText().toString(),//Mobile 
							verificationEditText.getText().toString(),//Verification
							registerGCMId,//GCM id
							firstNameEditBox_register.getText().toString(),//name
							"1",//Country id
							stateObject.getString(Constant.State.STATEID),//State id
							cityObject.getString(Constant.City.CITY_ID),//City id
							districtObject.getString(Constant.District.DISTRICT_ID)//District id
							);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}		
		});

		/**
		 * Login Button click
		 * */
		login=(Button)findViewById(R.id.loginBtnRegister);
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),LoginActivity.class));
				mobileNoEditText.setText("");
				verificationEditText.setText("");
			}
		});
		
		getStateList();
	}
	
	@Override
	public void processResponse(Object res) {
		register.setEnabled(true);
		JSONObject response = (JSONObject) res;
		if (response != null) {
			try {
				if (String.valueOf(response.get(Constant.Registration.API_STATUS)).equals("1")) {
					Toast.makeText(getApplicationContext(),String.valueOf(response.get(Constant.Registration.API_MESSAGE)),Toast.LENGTH_LONG).show();
					Intent mainFrgmentIntent = new Intent(getApplicationContext(), SmsVerificationActivity.class);
					mainFrgmentIntent.putExtra("From","Registraion");
					startActivity(mainFrgmentIntent);
					//startActivity(new Intent(getApplicationContext(),SmsVerificationActivity.class));
				} else {
					Toast.makeText(getApplicationContext(),String.valueOf(response.get(Constant.Registration.API_MESSAGE)),Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} 
	}

	@Override
	public void onBackPressed() {
		finish();
	}
	
	/**
	 * get the list of state Web Service to get State list and add into the
	 * state spinner
	 * 
	 * @throws JSONException
	 * **/
	public void getStateList() {
		//final JSONArray jsonArray = CreateJsonArrayFileIntoCache.readStateListJsonData(getApplicationContext());
		stateResponse = CreateJsonArrayFileIntoCache.readStateListJsonData(getApplicationContext());;
		if (stateResponse != null) {
			
			try {
				ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text, JSONUtils.getList(stateResponse, Constant.State.STATENAME));
				stateAdapter.insert("Select State", 0);
				stateAdapter.setDropDownViewResource(R.layout.spinner_text);
				// stateAdapter.
				stateSpinner.setAdapter(stateAdapter);
				stateSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
						try {
							if(!stateSpinner.getSelectedItem().toString().equals("Select State")) {
								getDistrictList(stateResponse.getJSONObject(position-1).getString(Constant.State.STATEID));
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {}
				});
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
	}
	/** Web Service to get the District name List and add into the City Spinner **/
	public void getDistrictList(String stateId) {
		WebserviceClient districtListWebserviceClient = new WebserviceClient((PVOAction)RegistrationActivity.this, districtListService);
		districtListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				districtResponse = (JSONArray) response;
				try {
					if (districtResponse != null && !((JSONObject) districtResponse.get(0)).has(Constant.City.API_STATUS)) {
						districtSpinner.setEnabled(true);
						districtArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text, JSONUtils.getList(districtResponse, Constant.District.DISTRICT_NAME));
						districtArrayAdapter.insert("Select District", 0);
						districtArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						districtSpinner.setAdapter(districtArrayAdapter);

						districtSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									if(!districtSpinner.getSelectedItem().toString().equals("Select District")) {
										getCityList(districtResponse.getJSONObject(position-1).getString(Constant.District.DISTRICT_ID),districtResponse.getJSONObject(position-1).getString(Constant.State.STATEID));
									} else {
										//notifyCityAdapter();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(getApplicationContext(),((JSONObject) districtResponse.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(districtArrayAdapter, DISTRICT);
						notifyAdapter(cityArrayAdapter, CITY);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		districtListWebserviceClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,stateId,"1");
	}
	/** END **/
	/** Web Service to get the City name List and add into the City Spinner **/
	public void getCityList(String district,String stateId) {
		WebserviceClient cityListWebserviceClient = new WebserviceClient((PVOAction)RegistrationActivity.this, cityListService);
		cityListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				cityResponse = (JSONArray) response;
				try {
					if (cityResponse != null && !((JSONObject) cityResponse.get(0)).has(Constant.City.API_STATUS)) {
						citySpinner.setEnabled(true);
						cityArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text, JSONUtils.getList(cityResponse, Constant.City.CITY_NAME));
						cityArrayAdapter.insert("Select City", 0);
						cityArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						citySpinner.setAdapter(cityArrayAdapter);
						
						citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(getApplicationContext(),((JSONObject) cityResponse.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(cityArrayAdapter, CITY);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		cityListWebserviceClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,district,stateId);
	}
	/** END **/
	
	//Notify City Array adapter	
	/*private void notifyCityAdapter() {
		if(cityArrayAdapter != null){
			cityArrayAdapter.clear();
			cityArrayAdapter.notifyDataSetChanged();
		}
	}*/
	//Notify District array adapter
	/*private void notifyDistrictAdapter() {
		if(districtArrayAdapter != null){
			districtArrayAdapter.clear();
			districtArrayAdapter.notifyDataSetChanged();
		}
	}*/
	
	//Check state spinner
	private boolean checkState() {
		if(stateSpinner.getSelectedItem() != null) {
			if(stateSpinner.getSelectedItem().equals("Select State")) {
				Toast.makeText(getApplicationContext(), "Please select state", Toast.LENGTH_LONG).show();
				return true;
			} else if(stateSpinner.getSelectedItem().equals("No state found")) {
				Toast.makeText(getApplicationContext(), "No state found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	
	//Check district spinner
	private boolean checkDistrict() {
		if(districtSpinner.getSelectedItem() != null) {
			if(districtSpinner.getSelectedItem().equals("Select District")) {
				Toast.makeText(getApplicationContext(), "Please select district", Toast.LENGTH_LONG).show();
				return true;
			} else if(districtSpinner.getSelectedItem().equals("No district found")) {
				Toast.makeText(getApplicationContext(), "No district found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	
	//Check district spinner
	private boolean checkCity() {
		if(citySpinner.getSelectedItem() != null) {
			if(citySpinner.getSelectedItem().equals("Select City")) {
				Toast.makeText(getApplicationContext(), "Please select city", Toast.LENGTH_LONG).show();
				return true;
			} else if(citySpinner.getSelectedItem().equals("No city found")) {
				Toast.makeText(getApplicationContext(), "No city found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	
	//Validate state spinner
		/*private boolean checkDistrictSpinner() {
			if(districtSpinner != null) {
				if(!districtSpinner.getSelectedItem().toString().equals("Select District")) 
					return true;
				 else 
					Toast.makeText(getApplicationContext(), "Please select District", Toast.LENGTH_LONG).show();
			}
			return false;
		}*/
		
	//Validate state spinner
	/*private boolean checkStateSpinner() {
		if(stateSpinner.getSelectedItem() != null) {
			if(!stateSpinner.getSelectedItem().toString().equals("Select State")) 
				return true;
			else 
				Toast.makeText(getApplicationContext(), "Please select state", Toast.LENGTH_LONG).show();
		}
		return false;
	}*/
	
	//Validate state spinner
	/*private boolean checkCitySpinner() {
		if(citySpinner != null) {
			if(!citySpinner.getSelectedItem().toString().equals("Select City")) 
				return true;
			else 
				Toast.makeText(getApplicationContext(), "Please select City", Toast.LENGTH_LONG).show();
		}
		return false;
	}*/
	
	
	
	//Notify adapter if data not found
	private void notifyAdapter(ArrayAdapter<String> adapter,String spinnerString) {
		if(adapter != null) {
			adapter.clear();
			if(spinnerString.equals(DISTRICT)) {
				adapter.insert("No district found",0);
				districtSpinner.setEnabled(false);
			} else if(spinnerString.equals(CITY)) {
				adapter.insert("No city found",0);
				citySpinner.setEnabled(false);
			}
			adapter.notifyDataSetChanged();
		}
	}
}