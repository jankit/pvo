package com.pvo.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.pvo.activity.R;
import com.pvo.json.cache.CreateJsonArrayFileIntoCache;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.AreaListOfCityService;
import com.pvo.user.service.CityListService;
import com.pvo.user.service.LandmarkService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.pvo.util.JSONUtils;

public class CommonUtils {
	
	private static UserSessionManager userSessionManager;
	private static CityListService cityListService;
	private static ArrayAdapter<String> cityArrayAdapter;
	private static AreaListOfCityService areaListOfCityService;
	private static ArrayAdapter<String> locationAdapter;
	private static ArrayAdapter<String> landmarkAdapter;
	
	public static JSONArray stateResponse;
	public static JSONArray cityResponse;
	public static JSONArray areaOfCityResponse;
	public static JSONArray landmark1Response;
	
	private static Context context;
	private static Spinner stateSpinner;
	private static Spinner citySpinner;
	private static Spinner locationSpinner;
	private static Spinner landmark1Spinner;
	private static Spinner landmark2Spinner;
	
	public CommonUtils(Context context,Spinner stateSpinner, Spinner citySpinner,Spinner locationSpinner,Spinner landmark1Spinner,Spinner landmark2Spinner) {
		this.context = context;
		this.stateSpinner = stateSpinner;
		this.citySpinner = citySpinner;
		this.locationSpinner = locationSpinner;
		this.landmark1Spinner = landmark1Spinner;
		this.landmark2Spinner = landmark2Spinner;
	}
	
	/**
	 * get the list of state Web Service to get State list and add into the
	 * state spinner
	 * @throws JSONException
	 * **/
	public static void getStateList() {
		userSessionManager = new UserSessionManager(context);
		final JSONArray jsonArray = CreateJsonArrayFileIntoCache.readStateListJsonData(context);
		stateResponse = jsonArray;
		if (jsonArray != null) {
			ArrayAdapter<String> stateAdapter;
			try {
				stateAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text, JSONUtils.getList(jsonArray, Constant.State.STATENAME));
				stateAdapter.setDropDownViewResource(R.layout.spinner_text);
				// stateAdapter.
				stateSpinner.setAdapter(stateAdapter);
				// Set Default Selection
				stateSpinner.setSelection(stateAdapter.getPosition(userSessionManager.getSessionValue(Constant.Login.STATE_NAME)));
				stateSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
						try {
							//String stateId = jsonArray.getJSONObject(position).getString(Constant.State.STATEID);
							getCityList(jsonArray.getJSONObject(position).getString(Constant.State.STATEID));
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
	//***************************************************************//
	
	
	/** Web Service to get the City name List and add into the City Spinner **/
	public static void getCityList(String stateId) {
		cityListService = new CityListService();
		WebserviceClient cityListWebserviceClient = new WebserviceClient(context, cityListService);
		cityListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				cityResponse = jsonArray;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.City.API_STATUS)) {
						cityArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text, JSONUtils.getList(jsonArray, Constant.City.CITY_NAME));
						cityArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						citySpinner.setAdapter(cityArrayAdapter);

						// Set Default Selection
						citySpinner.setSelection(cityArrayAdapter.getPosition(userSessionManager.getSessionValue(Constant.Login.CITY_NAME)));
						citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									getLocationList(jsonArray.getJSONObject(position).getString(Constant.City.CITY_ID));
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(context,((JSONObject) jsonArray.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyCityAdapter();
						notifyLocationAdapter();
						/*cityArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text);
						citySpinner.setAdapter(cityArrayAdapter);*/
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		cityListWebserviceClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,stateId);
	}
	//*************************************************************************//
	
	/**
	 * This method is used to get the List of area Form the Area list Web
	 * Service
	 **/
	public static void getLocationList(String cityId) {
		areaListOfCityService = new AreaListOfCityService();
		WebserviceClient locationListWebserviceClient = new WebserviceClient(context,areaListOfCityService);
		locationListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				areaOfCityResponse = jsonArray;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.Area.API_STATUS)) {
						//for (int i = 0; i < jsonArray.length(); i++) {
							locationAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text, JSONUtils.getList(jsonArray, Constant.Area.AREA_NAME));
							locationAdapter.setDropDownViewResource(R.layout.spinner_text);
							locationSpinner.setAdapter(locationAdapter);
						//}
						locationSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									getLandmarkList(jsonArray.getJSONObject(position).getString(Constant.Area.AREA_ID));
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(context,((JSONObject) jsonArray.get(0)).getString(Constant.Area.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyLocationAdapter();
						/*locationAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text);
						locationSpinner.setAdapter(locationAdapter);*/
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		locationListWebserviceClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,cityId);
	}
	//*************************************************************//
	
	/**
	 * This method is used to get the List of LandMark of particular Area from
	 * Web Service
	 **/
	public static void getLandmarkList(String areaId) {
		LandmarkService landmarkService = new LandmarkService();
		WebserviceClient landmarkListWebserviceClient = new WebserviceClient(context,landmarkService);
		landmarkListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				JSONArray jsonArray = (JSONArray) response;
				landmark1Response = jsonArray;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.AddProperty.API_STATUS)) {
						//for (int i = 0; i < jsonArray.length(); i++) {
							landmarkAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text, JSONUtils.getList(jsonArray, Constant.Landmark.LANDMARK_NAME));
							landmarkAdapter.setDropDownViewResource(R.layout.spinner_text);
							landmark1Spinner.setAdapter(landmarkAdapter);
							landmark2Spinner.setAdapter(landmarkAdapter);

							/*landmarkSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
								@Override
								public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
									if (landmarkSpinner.getSelectedItem().equals("other")) {
										addProplandmarkOtherEditbox.setVisibility(View.VISIBLE);
									}
								}
								@Override
								public void onNothingSelected(AdapterView<?> arg0) {}
							});

							addPropLandmark2Spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
								@Override
								public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
									if (addPropLandmark2Spinner.getSelectedItem().equals("other"))
										addPropLandmark2Editbox.setVisibility(View.VISIBLE);
								}
								@Override
								public void onNothingSelected(AdapterView<?> arg0) {}
							});
						}*/

					} else {
						Toast.makeText(context,((JSONObject) jsonArray.get(0)).getString(Constant.AddProperty.API_MESSAGE),Toast.LENGTH_LONG).show();
						notifyLandmark();
						/*landmarkAdapter = new ArrayAdapter<String>(Context, R.layout.spinner_text);
						addPropLandmark1Spinner.setAdapter(landmarkAdapter);
						addPropLandmark2Spinner.setAdapter(landmarkAdapter);*/
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		landmarkListWebserviceClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
	/** END **/
	
	//Notify City adapter
	private static void notifyCityAdapter() {
		if(cityArrayAdapter != null) {
			cityArrayAdapter.clear();
			cityArrayAdapter.notifyDataSetChanged();
			
		}
	}
	
	//Notify Location adapter
	private static void notifyLocationAdapter() {
		if(locationAdapter != null) {
			locationAdapter.clear();
			locationAdapter.notifyDataSetChanged();
		}
	}
	
	//Notify Landmark adapter
	private static void notifyLandmark() {
		if(landmarkAdapter != null) {
			landmarkAdapter.clear();
			landmarkAdapter.notifyDataSetChanged();
		}
	}
	
}
