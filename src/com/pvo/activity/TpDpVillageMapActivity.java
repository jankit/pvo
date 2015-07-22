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
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.pvo.json.cache.CreateJsonArrayFileIntoCache;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.touch.imageview.TouchImageView;
import com.pvo.user.service.CityListService;
import com.pvo.user.service.DistrictListService;
import com.pvo.user.service.DpMapListService;
import com.pvo.user.service.GdcrListService;
import com.pvo.user.service.MojegamListService;
import com.pvo.user.service.TPSchemesListService;
import com.pvo.user.service.TalukaListService;
import com.pvo.user.service.VillageListService;
import com.pvo.util.Constant;
import com.pvo.util.JSONUtils;
import com.squareup.picasso.Picasso;

public class TpDpVillageMapActivity extends Activity {
	
	private Button btnShowMap;
	// check box
	private CheckBox chkTp;
	private CheckBox chkDp;
	private CheckBox chkVillageMaps;
	private CheckBox chkGdcr;
	private String clickCheckBox;
	private LinearLayout mainSecondLayout;

	// City
	private LinearLayout llCity;
	private Spinner spinCity;
	// TP
	private LinearLayout llTp;
	private Spinner spinTp;
	// DP
	private LinearLayout llDp;
	private Spinner spinDp;
	//State
	private LinearLayout llState;
	private Spinner spinState;
	private ArrayAdapter<String> stateAdapter;
	// District
	private LinearLayout llDistrict;
	private Spinner spinDistrict;
	// Taluka
	private LinearLayout llTaluka;
	private Spinner spinTaluka;
	// Village
	private LinearLayout llMojegam;
	private Spinner spinMojegam;
	// Village
	private LinearLayout llVillage;
	private Spinner spinVillage;
	// GDCR
	private LinearLayout llGdcr;
	private Spinner spinGdcr;

	private CityListService cityListService;
	private JSONArray cityResponse;
	private ArrayAdapter<String> cityArrayAdapter;
	
	private TPSchemesListService tpSchemesListService;
	private ArrayAdapter<String> tpMapArrayAdapter;
	
	private DistrictListService districtListService;
	private ArrayAdapter<String> districtArrayAdapter;
	private String mapUrl;
	
	private TalukaListService talukaListService;
	private ArrayAdapter<String> talukaArrayAdapter;
	
	private DpMapListService dpMapListService;
	private ArrayAdapter<String> dpArrayAdapter;
	
	private GdcrListService gdcrListService;
	private ArrayAdapter<String> gdcrArrayAdapter;
	
	private MojegamListService mojegamListService;
	private ArrayAdapter<String> mojegamArrayAdapter;
	
	private VillageListService villageListService;
	private ArrayAdapter<String> villageArrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(TpDpVillageMapActivity.this));
		setContentView(R.layout.activity_dashboard_tpdp_maps);

		btnShowMap = (Button) findViewById(R.id.btnShowMap);
		
		chkTp = (CheckBox) findViewById(R.id.chkTp);
		chkTp.setChecked(true);
		
		chkDp = (CheckBox) findViewById(R.id.chkDp);
		chkVillageMaps = (CheckBox) findViewById(R.id.chkVillageMaps);
		chkGdcr = (CheckBox) findViewById(R.id.chkGdcr);

		llCity = (LinearLayout) findViewById(R.id.llCity);
		llTp = (LinearLayout) findViewById(R.id.llTp);
		llDp = (LinearLayout) findViewById(R.id.llDp);
		llState = (LinearLayout) findViewById(R.id.llState);
		llDistrict = (LinearLayout) findViewById(R.id.llDistrict);
		llTaluka = (LinearLayout) findViewById(R.id.llTaluka);
		llVillage = (LinearLayout) findViewById(R.id.llVillage);
		llGdcr = (LinearLayout) findViewById(R.id.llGdcr);
		llMojegam = (LinearLayout) findViewById(R.id.llMojegam);

		spinCity = (Spinner) findViewById(R.id.spinCity);
		spinTp = (Spinner) findViewById(R.id.spinTp);
		spinDp = (Spinner) findViewById(R.id.spinDp);
		spinState = (Spinner) findViewById(R.id.spinState);
		spinDistrict = (Spinner) findViewById(R.id.spinDistrict);
		spinTaluka = (Spinner) findViewById(R.id.spinTaluka);
		spinVillage = (Spinner) findViewById(R.id.spinVillage);
		spinGdcr = (Spinner) findViewById(R.id.spinGdcr);
		spinMojegam = (Spinner) findViewById(R.id.spinMojegam);

		mainSecondLayout = (LinearLayout) findViewById(R.id.mainSecondLayout);
		
		//call state list service
		getStateList();
		//call city list service
		getCityList();
		//call gdcr list service
		getGDCRList();

		//TP checkbox
		chkTp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mapUrl = "";
				hideAllChildLayout();
				unCheckCheckBox();
				llCity.setVisibility(View.VISIBLE);
				llTp.setVisibility(View.VISIBLE);
				chkTp.setChecked(true);
			}
		});
		//DP checkbox
		chkDp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mapUrl = "";
				clickCheckBox = "DP";
				getTalukaList("");
				hideAllChildLayout();
				unCheckCheckBox();
				llState.setVisibility(View.VISIBLE);
				llDistrict.setVisibility(View.VISIBLE);
				llTaluka.setVisibility(View.VISIBLE);
				llDp.setVisibility(View.VISIBLE);
				chkDp.setChecked(true);
			}
		});
		
		//Village maps checkbox
		chkVillageMaps.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mapUrl = "";
				clickCheckBox = "VILLAGE";
				hideAllChildLayout();
				unCheckCheckBox();
				llState.setVisibility(View.VISIBLE);
				llDistrict.setVisibility(View.VISIBLE);
				llTaluka.setVisibility(View.VISIBLE);
				llMojegam.setVisibility(View.VISIBLE);
				llVillage.setVisibility(View.VISIBLE);
				chkVillageMaps.setChecked(true);
			}
		});
		
		//GDCR checkbox
		chkGdcr.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mapUrl = "";
				hideAllChildLayout();
				unCheckCheckBox();
				llGdcr.setVisibility(View.VISIBLE);
				chkGdcr.setChecked(true);
			}
		});
		
		//Download map and show
		btnShowMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(checkCity())
					return;
				else if(checkTp())
					return;
				else if(checkState())
					return;
				else if(checkDistrict())
					return;
				else if(checkTaluka())
					return;
				else if(checkMojegam())
					return;
				else if(checkvillage())
					return;
				else if(checkDp())
					return;
				else
					showMap();
			}
		});
	}

	
	/**
	 * get the list of state 
	 */
	private void getStateList() {
		final JSONArray jsonArray = CreateJsonArrayFileIntoCache.readStateListJsonData(getApplicationContext());
		//System.out.println("state lsit===> "+jsonArray);
		try {
			if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.City.API_STATUS)) {
					stateAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text, JSONUtils.getList(jsonArray, Constant.State.STATENAME));
					stateAdapter.setDropDownViewResource(R.layout.spinner_text);
					// stateAdapter.
					spinState.setAdapter(stateAdapter);
					spinState.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
							try {
								getDistrictList(jsonArray.getJSONObject(position).getString(Constant.State.STATEID));
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {}
					});
			} else {
				Toast.makeText(getApplicationContext(),((JSONObject) jsonArray.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
				notifyAdapter(stateAdapter, "state");
				notifyAdapter(districtArrayAdapter, "district");
				notifyAdapter(talukaArrayAdapter, "taluka");
				notifyAdapter(mojegamArrayAdapter, "mojegam");
				notifyAdapter(villageArrayAdapter, "village");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get the list of city from webservice
	 */
	private void getCityList() {
		cityListService = new CityListService();
		WebserviceClient cityListWebserviceClient = new WebserviceClient(TpDpVillageMapActivity.this, cityListService);
		cityListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.City.API_STATUS)) {
						spinCity.setEnabled(true);
						cityArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text,JSONUtils.getList(jsonArray, Constant.City.CITY_NAME));
						cityArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						spinCity.setAdapter(cityArrayAdapter);

						// Set Default Selection
						spinCity.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									//String cityID = jsonArray.getJSONObject(position).getString(Constant.City.CITY_ID);
									getTPList(jsonArray.getJSONObject(position).getString(Constant.City.CITY_ID));
									//Toast.makeText(getApplicationContext(),"City Id: "+jsonArray.getJSONObject(position).getString(Constant.City.CITY_ID), Toast.LENGTH_LONG).show();
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(getApplicationContext(),((JSONObject) jsonArray.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(cityArrayAdapter, "city");
						notifyAdapter(tpMapArrayAdapter, "tp");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		cityListWebserviceClient.execute();
	}

	/**
	 * get the list of TP from webservice
	 */
	private void getTPList(String cityId) {
		tpSchemesListService = new TPSchemesListService();
		WebserviceClient tpListWebserviceClient = new WebserviceClient(TpDpVillageMapActivity.this, tpSchemesListService);
		tpListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.TPSchemesListing.API_STATUS)) {
						spinTp.setEnabled(true);
						tpMapArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text,JSONUtils.getList(jsonArray, Constant.TPSchemesListing.TITLE));
						tpMapArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						spinTp.setAdapter(tpMapArrayAdapter);

						// Set Default Selection
						spinTp.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									getTpMapBaseOnTpId(jsonArray.getJSONObject(position).getString(Constant.TPSchemesListing.TP_ID));
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(getApplicationContext(),((JSONObject) jsonArray.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(tpMapArrayAdapter, "tp");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		tpListWebserviceClient.execute(cityId);

	}

	/**
	* get the map of particular tp
	*/
	private void getTpMapBaseOnTpId(String tpId) {
		mapUrl = "";
		tpSchemesListService = new TPSchemesListService();
		WebserviceClient tpMapWebserviceClient = new WebserviceClient(TpDpVillageMapActivity.this, tpSchemesListService);
		tpMapWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null) {
						mapUrl = jsonArray.getJSONObject(0).getString(Constant.TPSchemesListing.MAP);
					} else {
						Toast.makeText(getApplicationContext(),((JSONObject) jsonArray.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		tpMapWebserviceClient.execute("map",tpId);
	}
	
	/**
	 * get the list of district from webservice
	 */
	private void getDistrictList(String stateId) {
		districtListService =  new DistrictListService();
		WebserviceClient districtListWebserviceClient = new WebserviceClient(TpDpVillageMapActivity.this, districtListService);
		districtListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.TPSchemesListing.API_STATUS)) {
						spinDistrict.setEnabled(true);
						districtArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text,JSONUtils.getList(jsonArray, Constant.District.DISTRICT_NAME));
						districtArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						spinDistrict.setAdapter(districtArrayAdapter);
						
						//Set Default Selection
						spinDistrict.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									//String tpID = jsonArray.getJSONObject(position).getString(Constant.TPSchemesListing.TP_ID);
									//Toast.makeText(getApplicationContext(),"District Id: "+jsonArray.getJSONObject(position).getString(Constant.District.DISTRICT_ID), Toast.LENGTH_LONG).show();
									getTalukaList(jsonArray.getJSONObject(position).getString(Constant.District.DISTRICT_ID));
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
						notifyAdapter(mojegamArrayAdapter, "mojegam");
						notifyAdapter(villageArrayAdapter, "village");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		districtListWebserviceClient.execute(stateId,"1");
	}
	
	/**
	 * Show downloaded map
	 */
	private void showMap() {
		if(mapUrl != null && !mapUrl.equals("")) {
			LayoutInflater inflater = LayoutInflater.from(TpDpVillageMapActivity.this);
			View view = inflater.inflate(R.layout.activity_map_view, null);
			TouchImageView mapView = (TouchImageView) view.findViewById(R.id.mapImageView);
			Picasso.with(getApplicationContext()).load(mapUrl).into(mapView);
			AlertDialog.Builder builder = new AlertDialog.Builder(TpDpVillageMapActivity.this, R.style.full_screen_dialog);
			builder.setView(view);
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		} else {
			Toast.makeText(getApplicationContext(), "Map not available", Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * get the list of DP from webservice
	 */
	private void getDPList(String talukaID) {
		dpMapListService = new DpMapListService();
		WebserviceClient dpListWebserviceClient = new WebserviceClient(TpDpVillageMapActivity.this, dpMapListService);
		dpListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				System.out.println("DP Json Array===>  "+jsonArray);
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.DpMapList.API_STATUS)) {
						spinDp.setEnabled(true);
						dpArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text,JSONUtils.getListFromFirstPos(jsonArray, Constant.DpMapList.TITLE));
						dpArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						spinDp.setAdapter(dpArrayAdapter);

						// Set Default Selection
						spinDp.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									//Toast.makeText(getApplicationContext(),"Dp File Path: "+jsonArray.getJSONObject(position+1).getString(Constant.DpMapList.DP_FILE_PATH), Toast.LENGTH_LONG).show();
									mapUrl = jsonArray.getJSONObject(position+1).getString(Constant.DpMapList.DP_FILE_PATH);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(getApplicationContext(),((JSONObject) jsonArray.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(dpArrayAdapter, "dp");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		dpListWebserviceClient.execute(talukaID);
	}
	
	/**
	 * get the list of district from webservice
	 */
	private void getTalukaList(String districtId) {
		talukaListService =  new TalukaListService();
		WebserviceClient talukaListWebserviceClient = new WebserviceClient(TpDpVillageMapActivity.this, talukaListService);
		talukaListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.TalukaList.API_STATUS)) {
						spinTaluka.setEnabled(true);
						talukaArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text,JSONUtils.getList(jsonArray, Constant.TalukaList.TITLE));
						talukaArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						spinTaluka.setAdapter(talukaArrayAdapter);

						spinTaluka.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									if(clickCheckBox.equals("DP"))  
										getDPList(jsonArray.getJSONObject(position).getString(Constant.TalukaList.TALUKA_ID));
									else 
										getMojegamList(jsonArray.getJSONObject(position).getString(Constant.TalukaList.TALUKA_ID));
									
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
						notifyAdapter(mojegamArrayAdapter, "mojegam");
						notifyAdapter(villageArrayAdapter, "village");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		if(districtId.length() > 0 && !districtId.equals(""))
			talukaListWebserviceClient.execute(districtId);
		else
			talukaListWebserviceClient.execute();
	}
	
	/**
	 * get the list of mojegam 
	 */
	private void getMojegamList(String talukaID) {
		mojegamListService = new MojegamListService();
		WebserviceClient mojegamListWebserviceClient = new WebserviceClient(TpDpVillageMapActivity.this, mojegamListService);
		mojegamListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.DpMapList.API_STATUS)) {
						spinMojegam.setEnabled(true);
						mojegamArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text,JSONUtils.getListFromFirstPos(jsonArray, Constant.DpMapList.TITLE));
						mojegamArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						spinMojegam.setAdapter(mojegamArrayAdapter);

						// Set Default Selection
						spinMojegam.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									//Toast.makeText(getApplicationContext(),"Mojegam Id: "+jsonArray.getJSONObject(position+1).getString(Constant.MojegamList.MOJEGAM_ID), Toast.LENGTH_LONG).show();
									getVillageList(jsonArray.getJSONObject(position+1).getString(Constant.MojegamList.MOJEGAM_ID));
									//mapUrl = jsonArray.getJSONObject(position+1).getString(Constant.MojegamList.MOJEGAM_ID);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(getApplicationContext(),((JSONObject) jsonArray.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(mojegamArrayAdapter, "mojegam");
						notifyAdapter(villageArrayAdapter, "village");				
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		mojegamListWebserviceClient.execute(talukaID);
	}
	
	/**
	 * get the list of Village from webservice
	 */
	private void getVillageList(String mojegamId) {
		villageListService = new VillageListService();
		WebserviceClient villageListWebserviceClient = new WebserviceClient(TpDpVillageMapActivity.this, villageListService);
		villageListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				System.out.println("Village response===>  "+jsonArray);
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.GdcrList.API_STATUS)) {
						spinVillage.setEnabled(true);
						villageArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text,JSONUtils.getListFromFirstPos(jsonArray, Constant.VillageList.TITLE));
						villageArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						spinVillage.setAdapter(villageArrayAdapter);

						// Set Default Selection
						spinVillage.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									//Toast.makeText(getApplicationContext(),"Village File Path: "+jsonArray.getJSONObject(position+1).getString(Constant.VillageList.TITLE), Toast.LENGTH_LONG).show();
									mapUrl = jsonArray.getJSONObject(position+1).getString(Constant.VillageList.VILLAGE_FILE_PATH);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(getApplicationContext(),((JSONObject) jsonArray.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(villageArrayAdapter, "village");	
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		villageListWebserviceClient.execute(mojegamId);
	}

	
	/**
	 * get the list of GDCR from webservice
	 */
	private void getGDCRList() {
		gdcrListService = new GdcrListService();
		WebserviceClient gdcrListWebserviceClient = new WebserviceClient(TpDpVillageMapActivity.this, gdcrListService);
		gdcrListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				System.out.println("GDCR Response===>  "+jsonArray);
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.GdcrList.API_STATUS)) {
						gdcrArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text,JSONUtils.getListFromFirstPos(jsonArray, Constant.GdcrList.GDCR_TITLE));
						gdcrArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						spinGdcr.setAdapter(gdcrArrayAdapter);

						// Set Default Selection
						spinGdcr.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									//Toast.makeText(getApplicationContext(),"Gdcr File Path: "+jsonArray.getJSONObject(position+1).getString(Constant.GdcrList.GDCR_PATH), Toast.LENGTH_LONG).show();
									mapUrl = jsonArray.getJSONObject(position+1).getString(Constant.GdcrList.GDCR_PATH);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(getApplicationContext(),((JSONObject) jsonArray.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		gdcrListWebserviceClient.execute();
	}

	/**
	 * unCheck all checkbox
	 */
	private void unCheckCheckBox() {
		chkTp.setChecked(false);
		chkDp.setChecked(false);
		chkGdcr.setChecked(false);
		chkVillageMaps.setChecked(false);
	}

	/**
	 * Hide all child layout
	 */
	private void hideAllChildLayout() {
		int childCount = mainSecondLayout.getChildCount();
		for (int i = 0; i < childCount; i++) {
			mainSecondLayout.getChildAt(i).setVisibility(View.GONE);
		}
	}
	
	
	//Check state
	private boolean checkState() {
		if(spinState.getSelectedItem() != null) {
			if(spinState.getSelectedItem().equals("No state data found") && spinState.isShown()) {
				Toast.makeText(getApplicationContext(), "No state data found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	
	//Check district
	private boolean checkDistrict() {
		if(spinDistrict.getSelectedItem() != null) {
			if(spinDistrict.getSelectedItem().equals("No district data found") && spinDistrict.isShown()) {
				Toast.makeText(getApplicationContext(), "No district data found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
		
	//Check city
	private boolean checkCity() {
		if(spinCity.getSelectedItem() != null) {
			if(spinCity.getSelectedItem().equals("No city data found") && spinCity.isShown()) {
				Toast.makeText(getApplicationContext(), "No city data found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	
	//Check Mojegam
	private boolean checkMojegam() {
		if(spinMojegam.getSelectedItem() != null) {
			if(spinMojegam.getSelectedItem().equals("No mojegam data found") && spinMojegam.isShown()) {
				Toast.makeText(getApplicationContext(), "No mojegam data found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	
	//Check Taluka
	private boolean checkTaluka() {
		if(spinTaluka.getSelectedItem() != null) {
			if(spinTaluka.getSelectedItem().equals("No taluka data found") && spinTaluka.isShown()) {
				Toast.makeText(getApplicationContext(), "No taluka data found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	
	//Check village
	private boolean checkvillage() {
		if(spinVillage.getSelectedItem() != null) {
			if(spinVillage.getSelectedItem().equals("No village data found") && spinVillage.isShown()) {
				Toast.makeText(getApplicationContext(), "No village data found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	
	//Check dp
	private boolean checkDp() {
		if(spinDp.getSelectedItem() != null) {
			if(spinDp.getSelectedItem().equals("No dp data found") && spinDp.isShown()) {
				Toast.makeText(getApplicationContext(), "No dp data found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	//Check tp
	private boolean checkTp() {
		if(spinTp.getSelectedItem() != null) {
			if(spinTp.getSelectedItem().equals("No TP data found") && spinTp.isShown()) {
				Toast.makeText(getApplicationContext(), "No TP data found", Toast.LENGTH_LONG).show();
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
		 if(spinnerString.equals("city")) {
			adapter.insert("No city data found", 0);
			spinCity.setAdapter(adapter);
			spinCity.setEnabled(false);
		} else if(spinnerString.equals("tp")) {
			adapter.insert("No TP data found", 0);
			spinTp.setAdapter(adapter);
			spinTp.setEnabled(false);
		} else if(spinnerString.equals("state")) {
			adapter.insert("No state data found", 0);
			spinState.setAdapter(adapter);
			spinState.setEnabled(false);
		} else if(spinnerString.equals("district")) {
			adapter.insert("No district data found", 0);
			spinDistrict.setAdapter(adapter);
			spinDistrict.setEnabled(false);
		} else if(spinnerString.equals("taluka")) {
			adapter.insert("No taluka data found", 0);
			spinTaluka.setAdapter(adapter);
			spinTaluka.setEnabled(false);
		} else if(spinnerString.equals("mojegam")) {
			adapter.insert("No mojegam data found", 0);
			spinMojegam.setAdapter(adapter);
			spinMojegam.setEnabled(false);
		} else if(spinnerString.equals("village")) {
			adapter.insert("No village data found", 0);
			spinVillage.setAdapter(adapter);
			spinVillage.setEnabled(false);
		} else if(spinnerString.equals("dp")) {
			adapter.insert("No dp data found", 0);
			spinDp.setAdapter(adapter);
			spinDp.setEnabled(false);
		} 
		adapter.notifyDataSetChanged();
	}
}
