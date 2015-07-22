package z.com.pvo.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.pvo.activity.R;
import com.pvo.json.cache.CreateJsonArrayFileIntoCache;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.AreaListOfCityService;
import com.pvo.user.service.CityListService;
import com.pvo.util.Constant;
import com.pvo.util.JSONUtils;

/*
 * City list:- http://www.propertyviaonline.com/ws/city.php?token=@6sm@9re
 * Location list:- http://www.propertyviaonline.com/ws/area.php?token=@6sm@9re&city_id=3 
 * State list:- propertyviaonline.com/ws/state.php?token=@6sm@9re
 * District:- http://www.propertyviaonline.com/ws/district.php?token=@6sm@9re&state_id=1&country_id=1 
 */
public class StateCityLocationUtils {
	
	
	private static Boolean isPrint = true;
	private static String  TAG = "StateCityLocationUtils";
	
	/*
	 * State relate object
	 */
	private static JSONArray stateJsonArray;
	public static ArrayAdapter<String> stateArrayAdapter;
	
	/*
	 * Location related object
	 */
	//private static AreaListOfCityService locationListOfCityService;
	private static JSONArray locationJsonArray;
	public static ArrayAdapter<String> locationArrayAdapter;
	
	
	/*
	 * City related object
	 */
	private static CityListService cityListService;
	private static JSONArray cityJsonArray;
	public static ArrayAdapter<String> cityArrayAdapter;
	
	
	
	/*
	 * Get the list of state(All) 
	 */
	public static JSONArray getStateListAll(Context context,Spinner spnState,String from,String defaultValue) {
		stateJsonArray = CreateJsonArrayFileIntoCache.readStateListJsonData(context);
		ProjectUtility.sys(isPrint, TAG, "getStateListAll");
		if (stateJsonArray != null) {
			//ArrayAdapter<String> stateAdapter;
			try {
				stateArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text, JSONUtils.getList(stateJsonArray, Constant.State.STATENAME));
				stateArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
				// stateAdapter.
				spnState.setAdapter(stateArrayAdapter);
				
				/*
				 * set default value if call from edit 
				 */
				if(from.equals("Edit")) {
					spnState.setSelection(stateArrayAdapter.getPosition(defaultValue));
				}
				
				
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return stateJsonArray;
	}
	
	/*
	 * Get the list of city by passing state id
	 */
	public static JSONArray getCityByStateId(final Context contex,final Spinner spnCity,String stateId, final String from, final String defaultVal) {
		if(cityListService == null)
			cityListService = new CityListService();
		
		WebserviceClient cityListWebserviceClient = new WebserviceClient(contex, cityListService);
		cityListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				cityJsonArray = (JSONArray) response;;
				try {
					if (cityJsonArray != null && !((JSONObject) cityJsonArray.get(0)).has(Constant.City.API_STATUS)) {
						//spnCity.setEnabled(true);
						cityArrayAdapter = new ArrayAdapter<String>(contex, R.layout.spinner_text, JSONUtils.getList(cityJsonArray, Constant.City.CITY_NAME));
						cityArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						spnCity.setAdapter(cityArrayAdapter);
						
						/*
						 * From edit 
						 * If call from edit then set default value
						 */
						if(from.equals("Edit")) {
							spnCity.setSelection(cityArrayAdapter.getPosition(defaultVal));
						}
						
					} else {
						Toast.makeText(contex,((JSONObject) cityJsonArray.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(cityArrayAdapter);
						notifyAdapter(locationArrayAdapter);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		cityListWebserviceClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,stateId);
		return cityJsonArray;
	}
	
	
	/*
	 * Get the list of location(area) by passing city id 
	 */
	public static JSONArray getLocationByCityId(final Context context,final Spinner spnLocation,String cityId, final String from, final String defaultVal) {
		AreaListOfCityService locationListOfCityService = new AreaListOfCityService();
		WebserviceClient locationListWebserviceClient = new WebserviceClient(context,locationListOfCityService);
		locationListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				locationJsonArray = (JSONArray) response;
				try {
					if (locationJsonArray != null && !((JSONObject) locationJsonArray.get(0)).has(Constant.Area.API_STATUS)) {
						locationArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text, JSONUtils.getList(locationJsonArray, Constant.Area.AREA_NAME));
						locationArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						locationArrayAdapter.insert("Select", 0);
						spnLocation.setAdapter(locationArrayAdapter);
						
						/*
						 * From Edit
						 * If call form edit then set default value
						 */
						if(from.equals("Edit")) {
							ProjectUtility.sys(isPrint,TAG,"Location item position--> "+defaultVal+"-"+locationArrayAdapter.getPosition(defaultVal));
							spnLocation.setSelection(locationArrayAdapter.getPosition(defaultVal));
						}
					} else {
						Toast.makeText(context,((JSONObject) locationJsonArray.get(0)).getString(Constant.Area.API_MESSAGE), Toast.LENGTH_LONG).show();
						if(locationArrayAdapter != null)
							locationArrayAdapter.clear();
						notifyAdapter(locationArrayAdapter);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		locationListWebserviceClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,cityId);
		return locationJsonArray;
	}
	
	
	/*
	 * Notify array adapter 
	 */
	public static void notifyAdapter(ArrayAdapter<String> arrayAdapter) {
		if(arrayAdapter != null) {
			arrayAdapter.notifyDataSetChanged();
		}
	}

	/*
	 * Return the id of selected item on spinner
	 */
	public static String getIdOfItem(int index,String from) {
		JSONObject jsonObject = null;
		try {
			if(from.equals(Constant.StateCityLoation.FROM_LOCATION)) {
				if(index != 0) {
					jsonObject = locationJsonArray.getJSONObject(index-1);
					ProjectUtility.sys(isPrint,TAG,"Location json object--> "+jsonObject);
					ProjectUtility.sys(isPrint,TAG,"Location id--> "+jsonObject.getString(Constant.Area.AREA_ID));
					return jsonObject.getString(Constant.Area.AREA_ID);
				} else {
					return "";
				}
			} else if(from.equals(Constant.StateCityLoation.FROM_CITY)) {
				jsonObject = cityJsonArray.getJSONObject(index);
				return jsonObject.getString(Constant.City.CITY_ID);
			} else if(from.equals(Constant.StateCityLoation.FROM_STATE)) {
				jsonObject = stateJsonArray.getJSONObject(index);
				return jsonObject.getString(Constant.State.STATEID);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
