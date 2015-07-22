package com.pvo.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.pvo.json.cache.CreateJsonArrayFileIntoCache;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.touch.imageview.TouchImageView;
import com.pvo.user.service.DistrictListService;
import com.pvo.user.service.JantriListService;
import com.pvo.user.service.MojegamListService;
import com.pvo.user.service.TalukaListService;
import com.pvo.util.Constant;
import com.pvo.util.JSONUtils;
import com.squareup.picasso.Picasso;

public class JantriActivity extends Activity {
	
	private Spinner spinJantriState;
	private Spinner spinJantriDistrict;
	private Spinner spinJantriTaluko;
	private Spinner spinJantriVillage;
	private Spinner spinJantriJantri;
	private Button btnShowJantriMap;
	private DistrictListService districtListService;
	private ArrayAdapter<String> districtArrayAdapter;
	private TalukaListService talukaListService;
	private ArrayAdapter<String> talukaArrayAdapter;
	private MojegamListService mojegamListService;
	private ArrayAdapter<String> mojegamArrayAdapter;
	private JantriListService jantriListService;
	private ArrayAdapter<String> jantriArrayAdapter;
	private String mapUrl;
	private ArrayAdapter<String> stateArrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard_jantri);
		
		spinJantriState = (Spinner) findViewById(R.id.spinJantriState);
		spinJantriDistrict = (Spinner) findViewById(R.id.spinJantriDistrict);
		spinJantriTaluko = (Spinner) findViewById(R.id.spinJantriTaluko);
		spinJantriVillage = (Spinner) findViewById(R.id.spinJantriVillage);
		spinJantriJantri = (Spinner) findViewById(R.id.spinJantriJantri);

		//Show jantri map 
		btnShowJantriMap = (Button) findViewById(R.id.btnShowJantriMap);
		btnShowJantriMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(checkState())
					return;
				else if(checkDistrict())
					return;
				else if(checkTaluka())
					return;
				else if(checkvillage())
					return;
				else if(checkJantri())
					return;
				else
					showMap();
			}
		});
		//call state list web service
		getStateList();
	}
	
	//get the list of state
	private void getStateList() {
		final JSONArray jsonArray = CreateJsonArrayFileIntoCache.readStateListJsonData(getApplicationContext());
		try {
			if(jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.TPSchemesListing.API_STATUS)) {
				spinJantriState.setEnabled(true);
				stateArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text,JSONUtils.getList(jsonArray, Constant.State.STATENAME));
				stateArrayAdapter.insert("Select State",0);
				stateArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
				spinJantriState.setAdapter(stateArrayAdapter);
				spinJantriState.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
						try {
							if(!spinJantriState.getSelectedItem().toString().equals("Select State")) { 
								getDistrictList(jsonArray.getJSONObject(position-1).getString(Constant.State.STATEID));
							} 
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {}
				});
			} else {
				Toast.makeText(getApplicationContext(),((JSONObject) jsonArray.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
				notifyAdapter(stateArrayAdapter, "state");
				notifyAdapter(districtArrayAdapter, "district");
				notifyAdapter(talukaArrayAdapter, "taluka");
				notifyAdapter(mojegamArrayAdapter, "village");
				notifyAdapter(jantriArrayAdapter, "jantri");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	//get the list of district from webservice
	private void getDistrictList(String stateId) {
		districtListService =  new DistrictListService();
		WebserviceClient districtListWebserviceClient = new WebserviceClient(JantriActivity.this, districtListService);
		districtListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.TPSchemesListing.API_STATUS)) {
						spinJantriDistrict.setEnabled(true);
						districtArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text,JSONUtils.getList(jsonArray, Constant.District.DISTRICT_NAME));
						districtArrayAdapter.insert("Select District",0);
						districtArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						spinJantriDistrict.setAdapter(districtArrayAdapter);
						//Set Default Selection
						spinJantriDistrict.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									//Toast.makeText(getApplicationContext(),"District Id: "+jsonArray.getJSONObject(position).getString(Constant.District.DISTRICT_ID), Toast.LENGTH_LONG).show();
									if(!spinJantriDistrict.getSelectedItem().toString().equals("Select District")) {
										getTalukaList(jsonArray.getJSONObject(position-1).getString(Constant.District.DISTRICT_ID));
									} 
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(getApplicationContext(),((JSONObject) jsonArray.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(districtArrayAdapter, "district");
						notifyAdapter(talukaArrayAdapter, "taluka");
						notifyAdapter(mojegamArrayAdapter, "village");
						notifyAdapter(jantriArrayAdapter, "jantri");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		districtListWebserviceClient.execute(stateId,"1");
	}
	
	//get the list of district from webservice
	private void getTalukaList(String districtId) {
		talukaListService =  new TalukaListService();
		WebserviceClient talukaListWebserviceClient = new WebserviceClient(JantriActivity.this, talukaListService);
		talukaListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.TalukaList.API_STATUS)) {
						spinJantriTaluko.setEnabled(true);
						talukaArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text,JSONUtils.getList(jsonArray, Constant.TalukaList.TITLE));
						talukaArrayAdapter.insert("Select Taluka", 0);
						talukaArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						spinJantriTaluko.setAdapter(talukaArrayAdapter);

						// Set Default Selection
						spinJantriTaluko.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									if(!spinJantriTaluko.getSelectedItem().toString().equals("Select Taluka")) {
										getMojegamList(jsonArray.getJSONObject(position-1).getString(Constant.TalukaList.TALUKA_ID));
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(getApplicationContext(),((JSONObject) jsonArray.get(0)).getString(Constant.TalukaList.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(talukaArrayAdapter, "taluka");
						notifyAdapter(mojegamArrayAdapter, "village");
						notifyAdapter(jantriArrayAdapter, "jantri");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		talukaListWebserviceClient.execute(districtId);
	}
	
	//get the list of mojegam 
	private void getMojegamList(String talukaID) {
		mojegamListService = new MojegamListService();
		WebserviceClient mojegamListWebserviceClient = new WebserviceClient(JantriActivity.this, mojegamListService);
		mojegamListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				//System.out.println("DP Json Array===>  "+jsonArray);
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.DpMapList.API_STATUS)) {
						spinJantriVillage.setEnabled(true);
						mojegamArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text,JSONUtils.getListFromFirstPos(jsonArray, Constant.DpMapList.TITLE));
						mojegamArrayAdapter.insert("Select Village", 0);
						mojegamArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						spinJantriVillage.setAdapter(mojegamArrayAdapter);

						// Set Default Selection
						spinJantriVillage.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									if(!spinJantriVillage.getSelectedItem().toString().equals("Select Village")) {
										getJantriList(jsonArray.getJSONObject(position-1).getString(Constant.MojegamList.MOJEGAM_ID));
									} 
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(getApplicationContext(),((JSONObject) jsonArray.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(mojegamArrayAdapter, "village");
						notifyAdapter(jantriArrayAdapter, "jantri");			
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		mojegamListWebserviceClient.execute(talukaID);
	}
	//get the list of jantri
	private void getJantriList(String villageID) {
		jantriListService = new JantriListService();
		WebserviceClient mojegamListWebserviceClient = new WebserviceClient(JantriActivity.this, jantriListService);
		mojegamListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.DpMapList.API_STATUS)) {
						spinJantriJantri.setEnabled(true);
						jantriArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text,JSONUtils.getListFromFirstPos(jsonArray, Constant.DpMapList.TITLE));
						jantriArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						spinJantriJantri.setAdapter(jantriArrayAdapter);
						// Set Default Selection
						spinJantriJantri.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									mapUrl = jsonArray.getJSONObject(1).getString(Constant.Jantri.JANTRI_FILE_PATH);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(getApplicationContext(),((JSONObject) jsonArray.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(jantriArrayAdapter, "jantri");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		mojegamListWebserviceClient.execute(villageID);
	}
	
	//Show downloaded map
	private void showMap() {
		if(mapUrl != null && !mapUrl.equals("")) {
			LayoutInflater inflater = LayoutInflater.from(JantriActivity.this);
			View view = inflater.inflate(R.layout.activity_map_view, null);
			TouchImageView  mapView = (TouchImageView) view.findViewById(R.id.mapImageView);
			Picasso.with(getApplicationContext()).load(mapUrl).into(mapView);
			AlertDialog.Builder builder = new AlertDialog.Builder(JantriActivity.this, R.style.full_screen_dialog);
			builder.setView(view);
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		} else {
			Toast.makeText(getApplicationContext(), "Map not available", Toast.LENGTH_LONG).show();
		}
	}
	
	//Check state
	private boolean checkState() {
		if(spinJantriState.getSelectedItem() != null) {
			if(spinJantriState.getSelectedItem().equals("Select State") && spinJantriState.isShown()) {
				Toast.makeText(getApplicationContext(), "Please select state", Toast.LENGTH_LONG).show();
				return true;
			} else if(spinJantriState.getSelectedItem().equals("No state data found") && spinJantriState.isShown()) {
				Toast.makeText(getApplicationContext(), "No state data found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
		
	//Check district
	private boolean checkDistrict() {
		if(spinJantriDistrict.getSelectedItem() != null) {
			if(spinJantriDistrict.getSelectedItem().equals("Select District") && spinJantriDistrict.isShown()) {
				Toast.makeText(getApplicationContext(), "Please select district", Toast.LENGTH_LONG).show();
				return true;
			} else if(spinJantriDistrict.getSelectedItem().equals("No district data found") && spinJantriDistrict.isShown()) {
				Toast.makeText(getApplicationContext(), "No district data found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	//Check Taluka
	private boolean checkTaluka() {
		if(spinJantriTaluko.getSelectedItem() != null) {
			if(spinJantriTaluko.getSelectedItem().equals("Select Taluka") && spinJantriTaluko.isShown()) {
				Toast.makeText(getApplicationContext(), "Please select taluka", Toast.LENGTH_LONG).show();
				return true;
			} else if(spinJantriTaluko.getSelectedItem().equals("No taluka data found") && spinJantriTaluko.isShown()) {
				Toast.makeText(getApplicationContext(), "No taluka data found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	//Check village
	private boolean checkvillage() {
		if(spinJantriVillage.getSelectedItem() != null) {
			if(spinJantriVillage.getSelectedItem().equals("Select Village") && spinJantriVillage.isShown()) {
				Toast.makeText(getApplicationContext(), "Please select village", Toast.LENGTH_LONG).show();
				return true;
			} else if(spinJantriVillage.getSelectedItem().equals("No village data found") && spinJantriVillage.isShown()) {
				Toast.makeText(getApplicationContext(), "No village data found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	//Check jantri
	private boolean checkJantri() {
		if(spinJantriJantri.getSelectedItem() != null) {
			if(spinJantriJantri.getSelectedItem().equals("No jantri data found") && spinJantriJantri.isShown()) {
				Toast.makeText(getApplicationContext(), "No jantri data found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	//Notify adapter
	private void notifyAdapter(ArrayAdapter<String> adapter, String spinnerString) {
		if(adapter == null) {
			adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_text);
		}
		 adapter.clear();
		if(spinnerString.equals("state")) {
			adapter.insert("No state data found", 0);
			spinJantriState.setAdapter(adapter);
			spinJantriState.setEnabled(false);
		} else if(spinnerString.equals("district")) {
			adapter.insert("No district data found", 0);
			spinJantriDistrict.setAdapter(adapter);
			spinJantriDistrict.setEnabled(false);
		} else if(spinnerString.equals("taluka")) {
			adapter.insert("No taluka data found", 0);
			spinJantriTaluko.setAdapter(adapter);
			spinJantriTaluko.setEnabled(false);
		}  else if(spinnerString.equals("village")) {
			adapter.insert("No village data found", 0);
			spinJantriVillage.setAdapter(adapter);
			spinJantriVillage.setEnabled(false);
		} else if(spinnerString.equals("jantri")) {
			adapter.insert("No jantri data found", 0);
			spinJantriJantri.setAdapter(adapter);
			spinJantriJantri.setEnabled(false);
		} 
		adapter.notifyDataSetChanged();
	}
}
