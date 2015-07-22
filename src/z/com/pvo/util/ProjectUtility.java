package z.com.pvo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import z.com.pvo.newActivity.ZBedBathFloorViewCommon;
import z.com.pvo.newActivity.ZPropertyTypeViewCommon;
import z.com.pvo.newAdapter.ZAminitiesCommertialAdaptor;
import z.com.pvo.newAdapter.ZAminitiesItem;
import z.com.pvo.newAdapter.ZAminitiesResidentialAdaptor;
import z.com.pvo.newAdapter.ZAraeItem;
import z.com.pvo.newAdapter.ZAreaListAdaptor;
import z.com.pvo.newAdapter.ZCommercialNomineeListAdaptor;
import z.com.pvo.newAdapter.ZGroupItem;
import z.com.pvo.newAdapter.ZGroupListAdaptor;
import z.com.pvo.newAdapter.ZNomineeCallListAdaptor;
import z.com.pvo.newAdapter.ZNomineeItem;
import z.com.pvo.newAdapter.ZNomineeListCommercialAdaptor;
import z.com.pvo.newAdapter.ZNomineeListResidentialAdaptor;
import z.com.pvo.newAdapter.ZPreeferedItem;
import z.com.pvo.newAdapter.ZPreeferedListAdaptor;
import z.com.pvo.newAdapter.ZTersmListAdaptor;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.activity.R;
import com.pvo.json.cache.CreateJsonArrayFileIntoCache;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.GroupListService;
import com.pvo.user.service.NomineesListService;
import com.pvo.user.service.PreferredBrokersService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.pvo.util.JSONUtils;

public class ProjectUtility {
	
	public static DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_hhmmss_aa");
	private static ZPropertyTypeViewCommon propertyTypeViewCommon;
	private static ZBedBathFloorViewCommon bedBathFloorViewCommon;
	private static String mPropertyType = "Apartment";
	
	/*
	 * UserSession manager
	 */
	private static UserSessionManager userSessionManager;
	
	/*
	 * Nominee related object
	 */
    private static ZNomineeListResidentialAdaptor resiNomineeListAdapter;
    private static ZCommercialNomineeListAdaptor commercialnomineeListAdapter;
    private static ZNomineeCallListAdaptor nomineeCallListAdaptor;
	
	/*
	 * Amenities related object
	 */
	//private static ArrayList<ZAminitiesItem> aminitiesItems;
    private static ZAminitiesResidentialAdaptor aminitiesResidetialAdaptor;
    private static ZAminitiesCommertialAdaptor aminitiesCommercialAdaptor;
    
    /*
     * Group list related object
     */
    private static GroupListService groupListService;
    private static ArrayList<ZGroupItem> groupItems;
    private static ZGroupListAdaptor groupListAdapter;
    
    /*
     * Area list related object
     */
    private static ArrayList<ZAraeItem> areaItems;
    private static ZAreaListAdaptor areaListAdapter;
    
    /*
     * Preefered broker list related object
     */
    //private static PreferredBrokersService preferredBrokersService; 
    //private static ArrayList<ZPreeferedItem> preeferedItems;
    private static ZPreeferedListAdaptor preeferedListAdapter;
    
    /*
     * TP Scheme related
     */
    private static String tpScheme;
    public static ArrayAdapter<String> tpSchemeAdapter;
	
    
    private static Boolean isPrint = true;
	private static String TAG = "ProjectUtility";
    
	/*
	 * Add property type view 
	 * Add number of property type view into the horizontal scroll view
	 */
	public static View addPropertyType(final Context context,final LinearLayout ll_residential_propType,int[] iconId,String[] title,String from,Boolean isEdit,int position) {
		sys(isPrint,TAG, "addPropertyType");
		/*
		 * Get the id array from ids.xml resource file
		 * According to residential or commercial
		 */
		TypedArray ar;
		if(from.equals(Constant.Residential.RESIDENTIAL))
			ar = context.getResources().obtainTypedArray(R.array.propTypeResidential);
		else 
			ar = context.getResources().obtainTypedArray(R.array.propTypeCommercial);
		
		int[] resIds = new int[ar.length()];
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT,1.0f);
		
		/*
		 * Add property type view and set id of each view from id array
		 * Also set icon from icon id array 
		 * on click of property type view set the property type 
		 */
		if(isEdit) {
			for (int i = 0; i < title.length; i++) {
				propertyTypeViewCommon = new ZPropertyTypeViewCommon(context,iconId[i], title[i]);
				propertyTypeViewCommon.setId(resIds[i]);
				propertyTypeViewCommon.setLayoutParams(params);
				changeBGColorOfPropTypeView(i, propertyTypeViewCommon, true, ll_residential_propType);
				ll_residential_propType.addView(propertyTypeViewCommon);
			}
		} else {
			propertyTypeViewCommon = new ZPropertyTypeViewCommon(context,iconId[position], title[position]);
			propertyTypeViewCommon.setId(resIds[position]);
			propertyTypeViewCommon.setLayoutParams(params);
			changeBGColorOfPropTypeView(position, propertyTypeViewCommon, true, ll_residential_propType);
			ll_residential_propType.addView(propertyTypeViewCommon);
		}
		
		return propertyTypeViewCommon;
	}
	
	
	/*
	 * getProperty type
	 */
	public static String getPropertyType() {
		sys(isPrint,TAG, "getPropertyType");
		return mPropertyType;
	}
	
	/*
	 * setProperty type
	 */
	public static void setPropertyType(String propertyType) {
		sys(isPrint,TAG, "setPropertyType");
		mPropertyType = propertyType;
	}
	
	/*
	 * change and set background of property type view according to selection
	 */
	@SuppressLint("ResourceAsColor")
	public static void changeBGColorOfPropTypeView(int position,final ZPropertyTypeViewCommon propertyTypeLayoutCommon,boolean flag,LinearLayout ll_MainLayout) {
		sys(isPrint,TAG, "changeBGColorOfPropTypeView");
		
		LinearLayout ll_main = (LinearLayout)propertyTypeLayoutCommon.getChildAt(0);
		LinearLayout ll_second = (LinearLayout) ll_main.getChildAt(0);
		TextView tv_title = (TextView)ll_main.getChildAt(1);
		
		if(flag) {
			if(position == 0 && flag) {
				ll_main.setBackgroundResource(R.drawable.z_prop_type_view_main_enable);
				ll_second.setBackgroundResource(R.drawable.z_prop_type_view_second_enable);
				tv_title.setTextColor(Color.WHITE);
			} else {
				ll_main.setBackgroundResource(R.drawable.z_prop_type_view_main_disable);
				ll_second.setBackgroundResource(R.drawable.z_prop_type_view_second_disable);
				tv_title.setTextColor(R.color.grayDark);
			}
		} else {
			for (int i = 0; i < ll_MainLayout.getChildCount(); i++) {
				LinearLayout ll_prop_type = (LinearLayout) ll_MainLayout.getChildAt(i);
				LinearLayout ll_mainChangeBg = (LinearLayout)ll_prop_type.getChildAt(0);
				LinearLayout ll_secondChangeBg = (LinearLayout) ll_mainChangeBg.getChildAt(0);
				TextView tv_titleChangeBg = (TextView)ll_mainChangeBg.getChildAt(1);
				
				ll_mainChangeBg.setBackgroundResource(R.drawable.z_prop_type_view_main_disable);
				ll_secondChangeBg.setBackgroundResource(R.drawable.z_prop_type_view_second_disable);
				tv_titleChangeBg.setTextColor(R.color.grayDark);
				
				if(position == ll_MainLayout.indexOfChild(ll_prop_type)) {
					ll_mainChangeBg.setBackgroundResource(R.drawable.z_prop_type_view_main_enable);
					ll_secondChangeBg.setBackgroundResource(R.drawable.z_prop_type_view_second_enable);
					tv_titleChangeBg.setTextColor(Color.WHITE);
				} 
			}
		}
	}
	
	
	/*
	 * Open gallery to select photo
	 */
	public static void openGallery(Activity activity) {
		sys(isPrint,TAG, "openGallery");
		Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		activity.startActivityForResult(intent, Constant.ZAddProperty.GALLERY_PHOTO);
	}
	
	/*
	 * Open camera to take photo
	 */
	public static void openCamera(Activity activity) {
		sys(isPrint,TAG, "openCamera");
		Intent captureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
		activity.startActivityForResult(captureIntent, Constant.ZAddProperty.CAMERA_CAPTURE);
	}
	
	/*
	 * Store selected photo as per give path  
	 */
	public static void storePhoto(Bitmap bitmap,String dirPath,String imgNumber) {
		
		String imgcurTime = dateFormat.format(new Date());
		File imageDirectory = new File(dirPath);
		if(!imageDirectory.exists())
			imageDirectory.mkdirs();
		String _path = dirPath + imgNumber+imgcurTime + ".jpeg";
		sys(isPrint,TAG, "storePhoto path--> "+_path);
		try {
			FileOutputStream out = new FileOutputStream(_path);
			if(bitmap != null)
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.close();
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * get the list of capture photo path of property 
	 */
	public static List<String> RetriveCapturedImagePath(String path) {
		//sys(isPrint,TAG, "RetriveCapturedImagePath");
		List<String> tFileList = new ArrayList<String>();
		File f = new File(path);
		if (f.exists()) {
			File[] files = f.listFiles();
			Arrays.sort(files);

			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isDirectory())
					continue;
				tFileList.add(file.getPath());
			}
		}
		return tFileList;
	}
	
	
	/*
	 * Add bed, Bathroom, Floor
	 */
	public static View addBedBathFloorView(Context context,int icon,LinearLayout layout ) {
		sys(isPrint,TAG, "addBedBathFloorView");
		bedBathFloorViewCommon = new ZBedBathFloorViewCommon(context,icon);
		layout.addView(bedBathFloorViewCommon);
		return bedBathFloorViewCommon;
	}
	
	
	/*
	 * get the list of nominee of broker
	 */
	public static JSONArray getNomineeListOfBroker(final Context context,final GridView grdNominee,final String from,final ProgressBar progressBar,final android.widget.RelativeLayout layout,String brokerID) {
		sys(isPrint,TAG, "getNomineeListOfBroker");
		userSessionManager = new UserSessionManager(context);
		progressBar.setVisibility(View.VISIBLE);
		NomineesListService nomineesListService = new NomineesListService();
		final ArrayList<ZNomineeItem> nomineeItems = new ArrayList<ZNomineeItem>();
		
		WebserviceClient webServiceClient = new WebserviceClient(context, nomineesListService);
		webServiceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.NomineeList.API_STATUS)) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							ZNomineeItem item = new ZNomineeItem();
				    		item.setNomineeName(jsonObject.getString(Constant.NomineeList.TITLE));
				    		item.setNomineeID(jsonObject.getString(Constant.NomineeList.NOMINEE_ID));
				    		item.setNumber(jsonObject.getString(Constant.NomineeList.MOBILE_NO));
				    		nomineeItems.add(item);
						}
						
						/*
						 * Create separate adapter object for nominee list 
						 * Residential or commercial
						 * This is for avoid checked nominee is not checked by default in each other 
						 */
						if(from.equals(Constant.Residential.FROM_RESIDENTIAL)) {
							if (resiNomineeListAdapter == null) {
								 resiNomineeListAdapter = new ZNomineeListResidentialAdaptor(context, R.id.grd_nominee_list,nomineeItems);
							}	
							grdNominee.setAdapter(resiNomineeListAdapter);
							layout.setVisibility(View.VISIBLE);
						} else if(from.equals(Constant.Commercial.FROM_COMMERCIAL)) {
							if (commercialnomineeListAdapter == null) {
								commercialnomineeListAdapter = new ZCommercialNomineeListAdaptor(context, R.id.grd_nominee_list,nomineeItems);
							}	
							grdNominee.setAdapter(commercialnomineeListAdapter);
							layout.setVisibility(View.VISIBLE);
						} else if(from.equals(Constant.PropertyDetail.FROM_PROPERTY_DETAIL)) {
							if (nomineeCallListAdaptor == null) {
								nomineeCallListAdaptor = new ZNomineeCallListAdaptor(context, R.id.grd_nomineeCall_list,nomineeItems);
							}	
							grdNominee.setAdapter(nomineeCallListAdaptor);
							layout.setVisibility(View.GONE);
						}
					} else {
						Toast.makeText(context,((JSONObject) jsonArray.get(0)).getString(Constant.NomineeList.API_MESSAGE),Toast.LENGTH_LONG).show();
					}
					/*
					 * hide progress dialog when response return successfully
					 */
					progressBar.setVisibility(View.GONE);
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		});
		webServiceClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,brokerID);
		return null;
	}
	
	/*
	 * Residential Notify nominee list adapter  
	 */
	public static void resiNomineeAdapterNotify() {
		sys(isPrint,TAG, "resiNomineeAdapterNotify");
		if(resiNomineeListAdapter != null)
			resiNomineeListAdapter.notifyDataSetChanged();
	}
	
	/*
	 * Commertial notify nominee list adapter 
	 */
	public static void commNomineeAdapterNotify() {
		sys(isPrint,TAG, "commNomineeAdapterNotify");
		if(commercialnomineeListAdapter != null)
			commercialnomineeListAdapter.notifyDataSetChanged();
	}
	
	
	//Facility Service Pass Value of Checked Facility CheckBok
	public static void getFacilityList(Context context,final GridView grdAmenities,final String from) {
		sys(isPrint,TAG, "getFacilityList");
		ArrayList<ZAminitiesItem> aminitiesItems = new ArrayList<ZAminitiesItem>();
		JSONArray jsonArray = CreateJsonArrayFileIntoCache.readFacilityListJsonData(context);
		//System.out.println("jsonArray-->"+jsonArray);
		try {
			if(jsonArray != null) {
				if (!((JSONObject) jsonArray.get(0)).has(Constant.NomineeList.API_STATUS)) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						ZAminitiesItem aminitiesItem = new ZAminitiesItem();
						aminitiesItem.setAmenitiesId(jsonObject.getString(Constant.Facility.FACILITY_ID));
						aminitiesItem.setFacilityIconPath(jsonObject.getString(Constant.Facility.IMAGE_PATH));
						aminitiesItem.setFacilityID(jsonObject.getInt(Constant.Facility.FACILITY_ID));
						aminitiesItem.setTitle(jsonObject.getString(Constant.Facility.TITLE));
						System.out.println("Title--> "+jsonObject.getString(Constant.Facility.TITLE));
						aminitiesItems.add(aminitiesItem);
					}
					
					if(from.equals(Constant.Residential.FROM_RESIDENTIAL)) {
						if (aminitiesResidetialAdaptor == null) {
							aminitiesResidetialAdaptor = new ZAminitiesResidentialAdaptor(context, R.id.grd_aminities_gridview,aminitiesItems);
							//grdAmenities.setAdapter(aminitiesResidetialAdaptor);
						}
						grdAmenities.setAdapter(aminitiesResidetialAdaptor);
					} else if(from.equals(Constant.Commercial.FROM_COMMERCIAL)) {
						if (aminitiesCommercialAdaptor == null) {
							aminitiesCommercialAdaptor = new ZAminitiesCommertialAdaptor(context, R.id.grd_aminities_gridview,aminitiesItems);
							//grdAmenities.setAdapter(aminitiesCommercialAdaptor);
						}
						grdAmenities.setAdapter(aminitiesCommercialAdaptor);
					}
					
				} else {
					Toast.makeText(context,((JSONObject) jsonArray.get(0)).getString(Constant.NomineeList.API_MESSAGE),Toast.LENGTH_LONG).show();
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Notify amenities list adapter 
	 */
	public static void notifyResidentialAmenitiesAdapter() {
		sys(isPrint,TAG, "notifyAmenitiesAdapter");
		if(aminitiesResidetialAdaptor != null)
			aminitiesResidetialAdaptor.notifyDataSetChanged();
	}
	
	/*
	 * Notify amenities list adapter 
	 */
	public static void notifyCommertialAmenitiesAdapter() {
		sys(isPrint,TAG, "notifyAmenitiesAdapter");
		if(aminitiesCommercialAdaptor != null)
			aminitiesCommercialAdaptor.notifyDataSetChanged();
	}
	
	
	/*
	 * Group list
	 * Get the list of group and set group list view
	 */
	public static void getGroupList(final Context context,final ListView lstGroup) {
		sys(isPrint,TAG, "getGroupList");
		userSessionManager = new UserSessionManager(context);
		groupListService = new GroupListService();
		groupItems = new ArrayList<ZGroupItem>();
		WebserviceClient groupListwebservice = new WebserviceClient(context, groupListService);
		groupListwebservice.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object res) {
				final JSONArray jsonArray = (JSONArray) res;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.PreferredBrokers.API_STATUS)) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							ZGroupItem groupItem = new ZGroupItem();
							groupItem.setGroupId(jsonObject.getString(Constant.GroupList.PGID));
							groupItem.setGroupName(jsonObject.getString(Constant.GroupList.GROUP_NAME));
							groupItems.add(groupItem);
						}
						if (groupListAdapter == null) {
							groupListAdapter = new ZGroupListAdaptor(context, R.layout.custom_list_item,groupItems);
							//lstGroup.setAdapter(groupListAdapter);
						}
						lstGroup.setAdapter(groupListAdapter);
						/*} else {
							lstGroup.setAdapter(groupListAdapter);
							groupListAdapter.notifyDataSetChanged();
							//flag_loading = false;
						}*/
						
					} else {
						Toast.makeText(context,((JSONObject) jsonArray.get(0)).getString(Constant.PreferredBrokers.API_MESSAGE),Toast.LENGTH_LONG).show();
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		});
		groupListwebservice.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userSessionManager.getSessionValue(Constant.Login.USER_ID));
	}
	
	/*
	 * Notify group list adapter 
	 */
	public static void notifyGroupListAdapter() {
		sys(isPrint,TAG, "notifyGroupListAdapter");
		if(groupListAdapter != null)
			groupListAdapter.notifyDataSetChanged();
	}
	
	/*
	 * Group list
	 * Get the list of group and set group list view
	 */
	public static void getAreaList(final Context context,final ListView lstArea) {
		sys(isPrint,TAG, "getAreaList");
		areaItems = new ArrayList<ZAraeItem>();
		final JSONArray jsonArray = CreateJsonArrayFileIntoCache.readAreaListJsonData(context);
		try {
			if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.PreferredBrokers.API_STATUS)){
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					ZAraeItem areaItem = new ZAraeItem();
					areaItem.setAreaId(jsonObject.getString(Constant.Area.AREA_ID));
					areaItem.setAreaName(jsonObject.getString(Constant.Area.AREA_NAME));
					areaItems.add(areaItem);
					/*brokerMap.put(jsonObject.getString(Constant.Area.AREA_NAME),jsonObject.getInt(Constant.Area.AREA_ID));
					brokerNames.add(jsonObject.getString(Constant.Area.AREA_NAME));*/
				}
				if (areaListAdapter == null) {
					areaListAdapter = new ZAreaListAdaptor(context, R.layout.custom_list_item,areaItems);
				}	//lstArea.setAdapter(areaListAdapter);
				lstArea.setAdapter(areaListAdapter);
			} else {
				Toast.makeText(context,((JSONObject) jsonArray.get(0)).getString(Constant.PreferredBrokers.API_MESSAGE),Toast.LENGTH_LONG).show();
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}
	
	/*
	 * Notify area list adapter 
	 */
	public static void notifyAreaListAdapter() {
		sys(isPrint,TAG, "notifyAreaListAdapter");
		if(areaListAdapter != null)
			areaListAdapter.notifyDataSetChanged();
	}
	
	/*
	 * Preefered Broker list
	 * Get the list of preefered broker and set into list 
	 */
	public static void getPreeferedBrokerList(final Context context,final ListView lstPreefered) {
		sys(isPrint,TAG, "getPreeferedBrokerList");
		userSessionManager = new UserSessionManager(context);
		PreferredBrokersService preferredBrokersService = new PreferredBrokersService();
		final ArrayList<ZPreeferedItem> preeferedItems = new ArrayList<ZPreeferedItem>();
		WebserviceClient preferredBrokerwebservice = new WebserviceClient(context,preferredBrokersService);
		preferredBrokerwebservice.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object res) {
				final JSONArray jsonArray = (JSONArray) res;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.PreferredBrokers.API_STATUS)) {
						for (int i = 1; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							ZPreeferedItem preeferedItem = new ZPreeferedItem();
							preeferedItem.setPreeferedName(jsonObject.getString(Constant.PreferredBrokers.FIRST_NAME) + " "+ jsonObject.getString(Constant.PreferredBrokers.LAST_NAME));
							preeferedItem.setPreeferedID(jsonObject.getString(Constant.PreferredBrokers.BROKER_ID));
							preeferedItems.add(preeferedItem);
						}
						if (preeferedListAdapter == null) {
							preeferedListAdapter = new ZPreeferedListAdaptor(context, R.layout.custom_list_item,preeferedItems);
							//lstPreefered.setAdapter(preeferedListAdapter);
						}
						/*} else {
							lstPreefered.setAdapter(preeferedListAdapter);
							preeferedListAdapter.notifyDataSetChanged();
						}*/
							lstPreefered.setAdapter(preeferedListAdapter);
					} else {
						Toast.makeText(context,((JSONObject) jsonArray.get(0)).getString(Constant.PreferredBrokers.API_MESSAGE),Toast.LENGTH_LONG).show();
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		});
		preferredBrokerwebservice.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userSessionManager.getSessionValue(Constant.Login.USER_ID));
	}
	
	/*
	 * Notify preefered list adapter 
	 */
	public static void notifyPreeferedListAdapter() {
		if(preeferedListAdapter != null)
			preeferedListAdapter.notifyDataSetChanged();
	}
	
	/*
	 * Remove all contain photo from property photo directory
	 */
	public static void removeAllResComPropPhoto() {
		ProjectUtility.sys(isPrint,TAG, "Remove All photo");
		File residentialDir = new File(Constant.ZAddProperty.RESIDENTIAL_PHOTO_PATH);
		if (residentialDir.isDirectory()) {
			String[] children = residentialDir.list();
			for (int i = 0; i < children.length; i++) {
				new File(residentialDir, children[i]).delete();
			}
		}
		
		File dir = new File(Constant.ZAddProperty.COMMERCIAL_PHOTO_PATH);
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				new File(dir, children[i]).delete();
			}
		}
	}
	
	/*
	 * Remove all contain photo from property photo directory
	 */
	/*public static void removeAllCommercialPropPhoto() {
		ProjectUtility.sys(isPrint,TAG, "Remove All photo");
		File residentialDir = new File(Constant.ZAddProperty.COMMERCIAL_PHOTO_PATH);
		if (residentialDir.isDirectory()) {
			String[] children = residentialDir.list();
			for (int i = 0; i < children.length; i++) {
				new File(residentialDir, children[i]).delete();
			}
		}
		
		File dir = new File(Constant.ZAddProperty.COMMERCIAL_PHOTO_PATH);
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				new File(dir, children[i]).delete();
			}
		}
	}*/
	
	/*
	 * TPScheme
	 * Get the list of tp scheme from web service
	 */
	public static void getTPScheme(final Context context,Spinner spnTpScheme) {
		ProjectUtility.sys(isPrint,TAG, "getTPScheme");
		final JSONArray jsonArray = CreateJsonArrayFileIntoCache.readTPSchemeListJsonData(context);
		try {
			if (jsonArray != null) {
				tpSchemeAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text,JSONUtils.getList(jsonArray, Constant.TPSchemesListing.TITLE));
				tpSchemeAdapter.setDropDownViewResource(R.layout.spinner_text);
				spnTpScheme.setAdapter(tpSchemeAdapter);
				spnTpScheme.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View view, int position, long arg3) {
						try {
							//Toast.makeText(context, "TP SCHEME", Toast.LENGTH_SHORT).show();
							setTPScheme(jsonArray.getJSONObject(position).getString(Constant.TPSchemesListing.TP_ID));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {}
				});
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * set tpScheme id
	 */
	private static void setTPScheme(String tpId) {
		ProjectUtility.sys(isPrint,TAG, "setTPScheme");
		tpScheme = tpId;
	}
	
	public static String getTPSchemeId() {
		ProjectUtility.sys(isPrint,TAG, "getTPSchemeId");
		return tpScheme; 
	}
	
	public static void sys(Boolean isPrint,String from,String msg) {
		if(isPrint) 
		  System.out.println("[(-->"+from+"<--)] ::"+msg);
		
	}
	
	/*
	 * Clear all array list 
	 * This method is call when property add successfully
	 */
	public static void clearAllIdArrayList() {
		ProjectUtility.sys(isPrint,TAG, "Clear all array list");
	
		ZGroupListAdaptor.clearSelectedGroup();
		
		ZPreeferedListAdaptor.clearSelectedPreferedBroker();
		
		ZAreaListAdaptor.clearSelectedArea();
		
		ZNomineeListResidentialAdaptor.clearSelectedResidentialNominee();
		
		ZNomineeListCommercialAdaptor.clearSelectedCommercialNominee();
		
		ZAminitiesResidentialAdaptor.clearSelectedAmenities();
		
		ZTersmListAdaptor.clearSelectedTerms();
	}
	
	/*
	 * Show toast with validation
	 */
	public static void showToast(Context context,View edt, String msg) {
		
		Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
		edt.startAnimation(shake);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.toast_layout,null);
		TextView textView = (TextView)  layout.findViewById(R.id.tv_toast_text);
		textView.setText(msg);
		Toast mFixedToast = new Toast(context);
	    mFixedToast.setDuration(10);
	    mFixedToast.setView(layout);
	    mFixedToast.setGravity(Gravity.CENTER|Gravity.FILL_HORIZONTAL, 0, 0);
	    mFixedToast.show();
	}
}


