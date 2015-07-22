package z.com.pvo.newActivity;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import z.com.pvo.components.MySwitch;
import z.com.pvo.newAdapter.ZAddPropPhotoAdapter;
import z.com.pvo.newAdapter.ZAminitiesCommertialAdaptor;
import z.com.pvo.newAdapter.ZAminitiesResidentialAdaptor;
import z.com.pvo.newAdapter.ZAreaListAdaptor;
import z.com.pvo.newAdapter.ZCommercialNomineeListAdaptor;
import z.com.pvo.newAdapter.ZGroupListAdaptor;
import z.com.pvo.newAdapter.ZPreeferedListAdaptor;
import z.com.pvo.newAdapter.ZTersmListAdaptor;
import z.com.pvo.util.GPSTracker;
import z.com.pvo.util.ProjectUtility;
import z.com.pvo.util.StateCityLocationUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pvo.activity.MainFragmentActivity;
import com.pvo.activity.R;
import com.pvo.components.SpinnerItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.FindByPropertyIdService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.squareup.picasso.Picasso;

//http://blog.andolasoft.com/2013/06/how-to-show-captured-images-dynamically-in-gridview-layout.html
public class ZPropAddCommercialFragment extends PVOFragment implements OnClickListener, OnItemSelectedListener , OnTouchListener {
	
	/*
	 * print log into console
	 */
	private Boolean isPrint = true;
	protected String TAG = "PropAddCommercialFragment";
	
	/*
	 * Activity
	 * This for static reference
	 */
	 private static Activity activity;
	
	/*
	 * User session 
	 */
	 private UserSessionManager userSessionManager;
	 
	/*
	 * Bundle
	 */
	 private Bundle propIntent;
	 
	/*
	 * Toggle button 
	 */
	private MySwitch togl_addComProp_saleRent,togl_addComProp_buildingType;
	
	/*
	 * Linear layout
	 */
	private LinearLayout ll_addComProp_propType, ll_addComProp_furnishDet, ll_addComProp_bathRoom, 
						 ll_addComProp_floor, ll_addComPropFurnishDetail, ll_addComProp_buildingType,
						 ll_addComProp_tpscheme, ll_addComProp_zone, ll_addComProp_na,
						 ll_addComProp_constArea, ll_addComProp_priceOprion, ll_addComProp_buildYear,
						 ll_addComProp_roadTouch, ll_addComProp_container;
	
	private static LinearLayout ll_addComProp_facility;
	
	/*
	 * Relative layout
	 */
	private RelativeLayout rl_addComProp_photoContainer;
	
	
	/*
	 * All Edit text 
	 */
	private EditText edt_addComProp_address, edt_addComProp_area, edt_addComProp_Price,
					 edt_addComProp_constArea, edt_addComProp_dastavej, edt_addComProp_transFees, 
					 edt_addComProp_audaLegal, edt_addComProp_parCharge, edt_addComProp_comment;
	
	/*
	 * All Spinner
	 */
	private Spinner spn_addComProp_location, spn_addComProp_city, spn_addComProp_district,
					spn_addComProp_state,spn_addComProp_areaUnit, spn_addComProp_tpscheme, 
					spn_addComProp_zone, spn_addComProp_na, spn_addComProp_buildyear,
					spn_addComProp_roadTouch, spn_addComProp_constAreaUnit;
	
	/*
	 * Image View 
	 */
	private ImageView iv_addResProp_photo, iv_bed_minus, iv_addComProp_bed_plus, 
	                  iv_addComProp_bath_minus, iv_addComProp_bath_plus, iv_addComProp_floor_minus,
	                  iv_addComProp_floor_plus, iv_addComProp_topPhotoView;
	
	/*
	 * Textview
	 */
	private TextView tv_addComProp_counter, tv_addComProp_bathRoom, tv_addComProp_floor, 
	                 tv_addComProp_furnish_fully, tv_addComProp_furnish_semi, tv_addComProp_furnish_un 
	                 /*tv_addComProp_photoDialog_counter*/;
	
	/*
	 * Button 
	 */
	private Button btn_addComProp_nominee, btn_addComProp_amenities, btn_addComProp_shreWith,
				   btn_addComProp_camera,btn_addComProp_gallery, btn_addComProp_priceOption,
				   btn_addComProp_trems;
	
	
	/*
	 * View Bed,Bathroom,floor
	 */
	private View view_addComProp_bed, view_addComProp_bath, view_addComProp_floor, 
				 view_addComProp_propType, view_addComProp_mapView;
	
	/*
	 * String variable
	 * 3=Fully
	 */ 
	private String str_addComProp_furnish = "3", str_addComProp_propType = "Shop", latitude, longitude;
	
	/*
	 * Counter for bed, bath, floor
	 */
	private int count_addComProp_bed = 1, count_addComProp_bath = 0, count_addComProp_floor = 0, PIC_CROP = 3;;
	
	/*
	 * Google map related
	 */
	private GoogleMap googleMap_addComProp_gmap;
	private Marker googleMarker_addComProp, marker_addComProp_marker;
	private String bestProvider_addComProp;
	private MapView mapview_addComProp;
	private GPSTracker gpsTracker;
	private String[] latLong;

	/*
	 * Photo dialog (Property photo related)
	 */
	private GridView grd_addComProp_photo;
	public static ZAddPropPhotoAdapter adpt_addComProp_photo;
	public static  List<String> list_Of_photo_Path_addComProp;
	
	/*
	 * String array for area unit
	 */
	 private String[] areaUnit;
	
	 /*
	  * Trems check box
	  */
	 private CheckBox chk_tremsCom_naviSharat, chk_tremsCom_juniSharat, chk_tremsCom_kheti, 
	 				  chk_tremsCom_prasha, chk_tremsCom_dispute, chk_tremsCom_shreeSarkar; 
	 
	 
	 /*
	  * JsonObject 
	  */
	 private JSONObject propertyDetailJsonObject;
	 
	
	/*
	  * ArrayAdapter<String>
	  * Spinner array adapter 
	  */
	 private ArrayAdapter<String> naStatusAdapter ,zoneAdapter, builtYearAdapter, onRoadAdapter;
	 
	 /*
	  * Terams list dialog object
	  */
	 private ZTremsListDialog termsListDialog;
	 
	 /*
	  * Nominee list dialog
	  */
	 private ZNomineeListDialog nomineeListDialog;
	 
	 /*
	  * Amenities List dialog
	  */
	 private ZAmenitiesListDialog amenitiesListDialog;
	
	
	public ZPropAddCommercialFragment() {
		setContentView(R.layout.z_prop_add_commercial_fragment);
	}
	
	
	@Override
	public void init(Bundle savedInstanceState) {
		
		ProjectUtility.sys(isPrint, TAG, "PropAddCommercialFragment");
		
		/*
		 * Inititalize activity
		 */
		activity = getActivity();
		
		/*
		 * Initialize GPS tracker
		 */
		gpsTracker = new GPSTracker(getActivity());
		
		
		/*
		 * Initialize usersession object
		 */
		userSessionManager = new UserSessionManager(getActivity());
		
		/*
		 * Get the intent value
		 */
		propIntent = getArguments();
		
		/*
		 * Initialize nominee dialog
		 */
		nomineeListDialog = new ZNomineeListDialog(getActivity(),Constant.Commercial.FROM_COMMERCIAL);
		nomineeListDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		
		
		/*
		 * Initialize Amenities dialog
		 */
		amenitiesListDialog = new ZAmenitiesListDialog(getActivity(),Constant.Commercial.FROM_COMMERCIAL);
		amenitiesListDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		
		/*
		 * Initialize tearmsDailog
		 */
		termsListDialog = new ZTremsListDialog(getActivity(),Constant.Commercial.FROM_COMMERCIAL);
		termsListDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		
		if(propIntent.getString("From") != null) {
			MainFragmentActivity.setTitle("Edit Property");
		}
		
		/*
		 * Initialize all view and find by id 
		 */
		setupView();
		mapview_addComProp.onCreate(savedInstanceState);
		
		/*
		 * State response handler
		 * This method return the json array
		 * Set the state spinner
		 */
		if(propIntent.getString("From") == null)
			StateCityLocationUtils.getStateListAll(getActivity(), spn_addComProp_state,"","");
		
		/*
		 * Area unit adapter 
		 * Fill 
		 */
		areaUnit = Constant.ZAddProperty.areaUnit;
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,areaUnit); //selected item will look like a spinner set from XML
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn_addComProp_areaUnit.setAdapter(spinnerArrayAdapter);
		spn_addComProp_constAreaUnit.setAdapter(spinnerArrayAdapter);
		
		/*
		 * TPScheme adapter
		 */
		ProjectUtility.getTPScheme(getActivity(), spn_addComProp_tpscheme);
		
		/*
		 * Zone
		 * Set zone spinner
		 */
		ArrayList<String> zoneList = new ArrayList<String>();
		zoneList.addAll(SpinnerItem.getAddPropZoneList().keySet());
		zoneAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, zoneList);
		zoneAdapter.setDropDownViewResource(R.layout.spinner_text);
		spn_addComProp_zone.setAdapter(zoneAdapter);
		//End
		
		
		/*
		 *N.A Layout fill the spinner using the Hash map 
		 */
		ArrayList<String> naStatusList = new ArrayList<String>();
		naStatusList.addAll(SpinnerItem.getAddPropNaStatusList().keySet());
		naStatusAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text,naStatusList);
		naStatusAdapter.setDropDownViewResource(R.layout.spinner_text);
		spn_addComProp_na.setAdapter(naStatusAdapter);
		//End
		
		/*
		 * Build year
		 * Fill the Built Year Spinner 
		 */
		ArrayList<String> years = new ArrayList<String>();
		years.add("Do not know");
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = thisYear - 64; i <= thisYear; i++) {
			years.add(Integer.toString(i));
		}
		builtYearAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, years);
		builtYearAdapter.setDropDownViewResource(R.layout.spinner_text);
		spn_addComProp_buildyear.setAdapter(builtYearAdapter);
		//End
		
		/*
		 * Road touch spinner 
		 * Fill road touch adapter suing hash map
		 */
		ArrayList<String> onRoadList = new ArrayList<String>();
		onRoadList.addAll(SpinnerItem.getOnRoadList().keySet());
		onRoadAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, onRoadList);
		onRoadAdapter.setDropDownViewResource(R.layout.spinner_text);
		spn_addComProp_roadTouch.setAdapter(onRoadAdapter);
		//End
		
		
		
		/*
		 * Set visible filed by default of apartment
		 */
		showHideFieldByPropType(str_addComProp_propType);
		
		/*
		 * Initialize google map 
		 */
		if(propIntent.getString("From") == null)
			setUpMapIfNeeded(true);
		
		/*
		 * Register spinner on item selected listener
		 */
		spn_addComProp_state.setOnItemSelectedListener(this);
		spn_addComProp_city.setOnItemSelectedListener(this);
		spn_addComProp_location.setOnItemSelectedListener(this);
		
		/*
		 * Register click lisitener for all button
		 */
		iv_addResProp_photo.setOnClickListener(this);
		btn_addComProp_nominee.setOnClickListener(this);
		btn_addComProp_amenities.setOnClickListener(this);
		btn_addComProp_shreWith.setOnClickListener(this);
		tv_addComProp_furnish_fully.setOnClickListener(this);
		tv_addComProp_furnish_semi.setOnClickListener(this);
		tv_addComProp_furnish_un.setOnClickListener(this);
		togl_addComProp_buildingType.setOnClickListener(this);
		togl_addComProp_saleRent.setOnClickListener(this);
		rl_addComProp_photoContainer.setOnClickListener(this);
		btn_addComProp_priceOption.setOnClickListener(this);
		btn_addComProp_trems.setOnClickListener(this);
		
		/*
		 * Set default value
		 * This is call when from edit property 
		 */
		if(propIntent.getString("From") != null) {
			if(propIntent.getString("From").equals("Edit")) {
				getPropertyDetailById();
			}
		}
		
	}
	
	/*
	 * Initialize all view 
	 * find by id
	 */
	private void setupView() {
		
		togl_addComProp_saleRent = (MySwitch) findViewById(R.id.togl_addComProp_saleRent);
		togl_addComProp_buildingType = (MySwitch) findViewById(R.id.togl_addComProp_buildingType);
		ll_addComProp_propType = (LinearLayout) findViewById(R.id.ll_addComProp_propType);
		ll_addComProp_furnishDet = (LinearLayout) findViewById(R.id.ll_addComProp_furnishDet);
		ll_addComProp_bathRoom = (LinearLayout) findViewById(R.id.ll_addComProp_bathRoom);
		ll_addComProp_floor = (LinearLayout) findViewById(R.id.ll_addComProp_floor);
		iv_addResProp_photo = (ImageView) findViewById(R.id.iv_addComProp_photo);
		rl_addComProp_photoContainer = (RelativeLayout) findViewById(R.id.rl_addComProp_photoContainer);
		tv_addComProp_counter = (TextView) findViewById(R.id.tv_addComProp_counter);
		iv_addComProp_topPhotoView = (ImageView) findViewById(R.id.iv_addComProp_topPhotoView);
		edt_addComProp_address = (EditText) findViewById(R.id.edt_addComProp_address);
		edt_addComProp_area = (EditText) findViewById(R.id.edt_addComProp_area);
		spn_addComProp_areaUnit = (Spinner) findViewById(R.id.spn_addComProp_areaUnit);
		spn_addComProp_tpscheme = (Spinner) findViewById(R.id.spn_addComProp_tpscheme);
		spn_addComProp_zone = (Spinner) findViewById(R.id.spn_addComProp_zone);
		spn_addComProp_na = (Spinner) findViewById(R.id.spn_addComProp_na);
		spn_addComProp_buildyear = (Spinner) findViewById(R.id.spn_addComProp_buildyear);
		spn_addComProp_roadTouch = (Spinner) findViewById(R.id.spn_addComProp_roadTouch);
		spn_addComProp_constAreaUnit = (Spinner) findViewById(R.id.spn_addComProp_constAreaUnit);
		edt_addComProp_Price = (EditText) findViewById(R.id.edt_addComProp_Price);
		edt_addComProp_constArea = (EditText) findViewById(R.id.edt_addComProp_constArea);
		edt_addComProp_dastavej = (EditText) findViewById(R.id.edt_addComProp_dastavej);
		edt_addComProp_transFees = (EditText) findViewById(R.id.edt_addComProp_transFees);
		edt_addComProp_audaLegal = (EditText) findViewById(R.id.edt_addComProp_audaLegal);
		edt_addComProp_parCharge = (EditText) findViewById(R.id.edt_addComProp_parCharge);
		edt_addComProp_comment = (EditText) findViewById(R.id.edt_addComProp_comment);
		ll_addComProp_buildingType = (LinearLayout) findViewById(R.id.ll_addComProp_buildingType);
		ll_addComProp_tpscheme = (LinearLayout) findViewById(R.id.ll_addComProp_tpscheme);
		ll_addComProp_na = (LinearLayout) findViewById(R.id.ll_addComProp_na);
		ll_addComProp_zone = (LinearLayout) findViewById(R.id.ll_addComProp_zone);
		ll_addComProp_constArea = (LinearLayout) findViewById(R.id.ll_addComProp_constArea);
		ll_addComProp_priceOprion = (LinearLayout) findViewById(R.id.ll_addComProp_priceOprion);
		btn_addComProp_priceOption = (Button) findViewById(R.id.btn_addComProp_priceOption);
		btn_addComProp_trems = (Button) findViewById(R.id.btn_addComProp_trems);
		ll_addComProp_buildYear = (LinearLayout) findViewById(R.id.ll_addComProp_buildYear);
		ll_addComProp_roadTouch = (LinearLayout) findViewById(R.id.ll_addComProp_roadTouch);
		ll_addComProp_container = (LinearLayout) findViewById(R.id.ll_addComProp_container);
		ll_addComProp_facility = (LinearLayout) findViewById(R.id.ll_addComProp_facility);
		
		mapview_addComProp = (MapView) findViewById(R.id.map_addComProp);
		
		/*
		 * This touch event is used for hide keyboard when touch outside
		 */
		ll_addComProp_container.setOnTouchListener(this);
		
		/*
		 * Check price is not start with zero if start with zero then show alert 
		 * Dastavej
		 * Transfer fees
		 * auda legal
		 * parking charge
		 */
		priceNotStartWithZero(edt_addComProp_Price,"Enter Proper price");
		priceNotStartWithZero(edt_addComProp_dastavej,"Enter Proper Dastavej price");
		priceNotStartWithZero(edt_addComProp_transFees,"Enter Proper Transfer Fees");
		priceNotStartWithZero(edt_addComProp_audaLegal,"Enter Proper Auda Legal price");
		priceNotStartWithZero(edt_addComProp_parCharge, "Enter Proper Parking charge");
		
		/*
		 * Set photo and total number of photo
		 */
		setPhotoAndNumberOfPhoto();
		
		spn_addComProp_state = (Spinner) findViewById(R.id.spn_addComProp_state);
		spn_addComProp_location = (Spinner) findViewById(R.id.spn_addComProp_location);
		spn_addComProp_city = (Spinner) findViewById(R.id.spn_addComProp_city);
		
		/*
		 * Add property type view into layout dynamically
		 * On click of view change backgrond color
		 */
		if(propIntent.getString("From") == null) {
			ProjectUtility.addPropertyType(getActivity(), ll_addComProp_propType,Constant.PropertyType.commercialIconId,Constant.PropertyType.commercialTitle,"Commercial",true,0);
			for (int i = 0; i < ll_addComProp_propType.getChildCount(); i++) {
				final ZPropertyTypeViewCommon layout = (ZPropertyTypeViewCommon)ll_addComProp_propType.getChildAt(i);
				final TextView tv_propType = (TextView) layout.findViewById(R.id.tv_propTypRow_name);
				layout.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ProjectUtility.changeBGColorOfPropTypeView(ll_addComProp_propType.indexOfChild(v), layout,false, ll_addComProp_propType);
						str_addComProp_propType = z.com.pvo.util.SpinnerItem.getCommercialPropTypeVal().get(tv_propType.getText().toString()).toString();
						showHideFieldByPropType(tv_propType.getText().toString());
						//Toast.makeText(getActivity(), tv_propType.getText().toString()+"-"+str_addComProp_propType, Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
		
		/*
		 * Bath room 
		 */
		view_addComProp_bath = ProjectUtility.addBedBathFloorView(getActivity(), R.drawable.bathroom,ll_addComProp_bathRoom);
		iv_addComProp_bath_minus = (ImageView) view_addComProp_bath.findViewById(R.id.iv_bedBathFolorView_minusIcon);
		iv_addComProp_bath_plus = (ImageView) view_addComProp_bath.findViewById(R.id.iv_bedBathFolorView_plusIcon);
		tv_addComProp_bathRoom= (TextView) view_addComProp_bath.findViewById(R.id.tv_bedBathFolorView_number);
		tv_addComProp_bathRoom.setText("0");
		//minus bath
		iv_addComProp_bath_minus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(count_addComProp_bath > 0) {
					tv_addComProp_bathRoom.setText(""+ --count_addComProp_bath);
				} else {
					tv_addComProp_bathRoom.setText("0");
				}
			}
		});
		
		//Plus bath
		iv_addComProp_bath_plus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(count_addComProp_bath < 12) {
					tv_addComProp_bathRoom.setText(""+ ++count_addComProp_bath);
				}
			}
		});
		
		/*
		 * Floor view
		 */
		view_addComProp_floor = ProjectUtility.addBedBathFloorView(getActivity(), R.drawable.floor,ll_addComProp_floor);
		iv_addComProp_floor_minus = (ImageView) view_addComProp_floor.findViewById(R.id.iv_bedBathFolorView_minusIcon);
		iv_addComProp_floor_plus = (ImageView) view_addComProp_floor.findViewById(R.id.iv_bedBathFolorView_plusIcon);
		tv_addComProp_floor = (TextView) view_addComProp_floor.findViewById(R.id.tv_bedBathFolorView_number);
		tv_addComProp_floor.setText("0");

		//minus floor
		iv_addComProp_floor_minus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(count_addComProp_floor > 0) {
					tv_addComProp_floor.setText(""+ --count_addComProp_floor);
				} else {
					tv_addComProp_floor.setText("0");
				}
			}
		});
		
		//Plus floor
		iv_addComProp_floor_plus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(count_addComProp_floor < 40) {
					tv_addComProp_floor.setText(""+ ++count_addComProp_floor);
				}
			}
		});
		
		/*
		 * Add Furnish detail view dynamically
		 * With three option like fully furnish, Semi furnish, unfurnish
		 */
		ll_addComProp_furnishDet.addView(new ZFurnishDetailViewCommon(getActivity(), "Fully", "Semi", "Un"));
		ll_addComPropFurnishDetail = (LinearLayout) ll_addComProp_furnishDet.findViewById(R.id.ll_furnishDet_container); 
		tv_addComProp_furnish_fully = (TextView) ll_addComPropFurnishDetail.findViewById(R.id.tv_furnishDet_fully);
		tv_addComProp_furnish_semi = (TextView) ll_addComPropFurnishDetail.findViewById(R.id.tv_furnishDet_semi);
		tv_addComProp_furnish_un = (TextView) ll_addComPropFurnishDetail.findViewById(R.id.tv_furnishDet_un);
		ll_addComPropFurnishDetail.setBackgroundResource(R.drawable.select1);
		
		/*
		 * Button nominee for open nominee dialog
		 * Button amenities for open amenities dialog 
		 * Button share with is for share property
		 */
		btn_addComProp_nominee = (Button) findViewById(R.id.btn_addComProp_nominee);
		btn_addComProp_amenities = (Button) findViewById(R.id.btn_addComProp_amenities);
		btn_addComProp_shreWith = (Button) findViewById(R.id.btn_addComProp_shreWith);
		
	}
	
	
	/*
	 * Initialize the fragment
	 */
	public static ZPropAddCommercialFragment newInstance() {
		ZPropAddCommercialFragment f = new ZPropAddCommercialFragment();
		Bundle b = new Bundle();
		f.setArguments(b);
		return f;
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityResult(int, int, android.content.Intent)
	 * Return the result of capture photo from camera or gallery
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == getActivity().RESULT_OK) {
			if (requestCode == Constant.ZAddProperty.CAMERA_CAPTURE) {
				
				File file = new File(Environment.getExternalStorageDirectory()+File.separator + "PVO/img.jpg");
				doCrop(Uri.fromFile(file));
				
				//ProjectUtility.storePhoto((Bitmap)data.getParcelableExtra("data"), Constant.ZAddProperty.COMMERCIAL_PHOTO_PATH,"");
			} else if (requestCode == Constant.ZAddProperty.GALLERY_PHOTO) {
				Uri selectedImage = data.getData();
				String[] filePath = { MediaStore.Images.Media.DATA };
				Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePath[0]);
				String picturePath = c.getString(columnIndex);
				c.close();
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 3;
				
				doCrop(selectedImage);
				//ProjectUtility.storePhoto((BitmapFactory.decodeFile(picturePath, options)), Constant.ZAddProperty.COMMERCIAL_PHOTO_PATH,"");
			} else if (requestCode == PIC_CROP) {
				ProjectUtility.storePhoto((Bitmap)data.getParcelableExtra("data"), Constant.ZAddProperty.COMMERCIAL_PHOTO_PATH,"");
			}
			
			/*
			 * Get the all photo from directory
			 * and set photo adapter
			 */
			list_Of_photo_Path_addComProp = ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.COMMERCIAL_PHOTO_PATH);
			adpt_addComProp_photo = new ZAddPropPhotoAdapter(getActivity(), list_Of_photo_Path_addComProp);
			grd_addComProp_photo.setAdapter(adpt_addComProp_photo);
			
		}
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 * Handle click event
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			//Open photo dialog on camera icon
			case R.id.iv_addComProp_photo:
				openPhotoDialog();
				break;
			case R.id.rl_addComProp_photoContainer:
				openPhotoDialog();
				break;
			//Open camera on camera button click from photo dialog
			case R.id.btn_addResProp_camera:
				if(ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.COMMERCIAL_PHOTO_PATH).size() < 4) {
					/* create an instance of intent
					 * pass action android.media.action.IMAGE_CAPTURE 
					 * as argument to launch camera
					 */
					Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
					File file = new File(Environment.getExternalStorageDirectory()+File.separator + "PVO/img.jpg");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
					startActivityForResult(intent, Constant.ZAddProperty.CAMERA_CAPTURE);
				} else {
					Toast.makeText(getActivity(), "You can add four photo only", Toast.LENGTH_SHORT).show();
				}
				break;
			//Open gallery on gallery button click from photo dialog	
			case R.id.btn_addResProp_gallery:
				if(ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.COMMERCIAL_PHOTO_PATH).size() < 4) {
					Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, Constant.ZAddProperty.GALLERY_PHOTO);
				} else {
					Toast.makeText(getActivity(), "You can add four photo only", Toast.LENGTH_SHORT).show();
				}
				
				break;
			//Open nominee list dialog on nominee button click  	
			case R.id.btn_addComProp_nominee:
				nomineeListDialog.show();
				break;
			//Open aemenities dialog on amenities button click  
			case R.id.btn_addComProp_amenities:
				amenitiesListDialog.show();
				break;
			//Redirect to share with activity on click of share with button 
			case R.id.btn_addComProp_shreWith:
				redirectToShareWithActivity();
				break;
			//furnish detail fully 3
			case R.id.tv_furnishDet_fully:
				ll_addComPropFurnishDetail.setBackgroundResource(R.drawable.select1);
				str_addComProp_furnish = "3";
				break;
			//furnish detail semi	
			case R.id.tv_furnishDet_semi:
				ll_addComPropFurnishDetail.setBackgroundResource(R.drawable.select2);
				str_addComProp_furnish = "2";
				//Toast.makeText(getActivity(),tv_addComProp_furnish_semi.getText(), Toast.LENGTH_SHORT).show();
				break;
			//furnish detail unfurnish
			case R.id.tv_furnishDet_un:
				ll_addComPropFurnishDetail.setBackgroundResource(R.drawable.select3);
				str_addComProp_furnish = "0";
				break;
			//Building type toggle button
			case R.id.togl_addComProp_buildingType:
				break;
			//Sale/Rent toggle button	
			case R.id.togl_addComProp_saleRent:
				/*
				 * Visible more price option button if sale is selected
				 * else hide more option button 
				 */
				showhidePriceMoreOptionIcon();
				//Toast.makeText(getActivity(),togl_addComProp_saleRent.getCurrentText(), Toast.LENGTH_SHORT).show();
				break;
		    //More price option show/hide on buton click 	
			case R.id.btn_addComProp_priceOption:
				/*
				 * Show hide price more option 
				 */
				showHidePriceMoreOptionLayout();
				break;
			//Open treams dialog	
			case R.id.btn_addComProp_trems:
				//openTreamDialog();
				termsListDialog.show();
				break;
			default:
				break;
		}
	}
	
	@Override
	public void onItemSelected(AdapterView<?> adptView, View view, int position, long i) {
		try{
			switch (adptView.getId()) {
				//State
				case R.id.spn_addComProp_state:
					 /*
					  * City response handler
					  * This method return the city json array and  
					  * set the city spinner
					  */
					if(propIntent.getString("From") == null) {
					  StateCityLocationUtils.getCityByStateId(getActivity(), spn_addComProp_city,StateCityLocationUtils.getIdOfItem(position,Constant.StateCityLoation.FROM_STATE),"","");
					} else if(propIntent.getString("From").equals("Edit")){
						StateCityLocationUtils.getCityByStateId(getActivity(), spn_addComProp_city,StateCityLocationUtils.getIdOfItem(position,Constant.StateCityLoation.FROM_STATE),propIntent.getString("From"),propertyDetailJsonObject.getString(Constant.EditProperty.CITY_NAME));
					}
					 break;
				//City
				case R.id.spn_addComProp_city:
					/*
					 * Location response handler
					 * This method return the location json array,
					 * Set the location spinner 
					 */
					if(propIntent.getString("From") == null) {
						StateCityLocationUtils.getLocationByCityId(getActivity(), spn_addComProp_location, StateCityLocationUtils.getIdOfItem(position,Constant.StateCityLoation.FROM_CITY),"","");
					} else if(propIntent.getString("From").equals("Edit")){
						StateCityLocationUtils.getLocationByCityId(getActivity(), spn_addComProp_location, StateCityLocationUtils.getIdOfItem(position,Constant.StateCityLoation.FROM_CITY),propIntent.getString("From"),propertyDetailJsonObject.getString(Constant.EditProperty.AREA_NAME));
					}
					break;
				//Location
				case R.id.spn_addComProp_location:
					break;
				
				default:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {}
	
	/*
	 * Open photo dialog to take from camera or gallery
	 */
	private void openPhotoDialog() {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.z_prop_photo_dialog_list, null);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
		alertDialog.setView(view);
		alertDialog.setCancelable(false);
		
		final AlertDialog dialog = alertDialog.create();
		dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		dialog.show();
		//tv_addComProp_photoDialog_counter = (TextView) view.findViewById(R.id.tv_photoDialog_counter);
		btn_addComProp_camera = (Button) view.findViewById(R.id.btn_addResProp_camera);
		btn_addComProp_camera.setOnClickListener(this);
		btn_addComProp_gallery = (Button) view.findViewById(R.id.btn_addResProp_gallery);
		btn_addComProp_gallery.setOnClickListener(this);
		grd_addComProp_photo = (GridView) view.findViewById(R.id.grd_addResProp_photo);	
		
		
		/*
		 * Save and close photo dialog
		 */
		Button btn_photoDilog_ok = (Button) view.findViewById(R.id.btn_photoDilog_ok);
		btn_photoDilog_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//tv_addResProp_counter.setText(""+ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.RESIDENTIAL_PHOTO_PATH).size());
				setPhotoAndNumberOfPhoto();
				dialog.dismiss();
			}
		});
		
		/*
		 * Get all the photo from directory and set adapter
		 */
		list_Of_photo_Path_addComProp = ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.COMMERCIAL_PHOTO_PATH);
		adpt_addComProp_photo = new ZAddPropPhotoAdapter(getActivity(), list_Of_photo_Path_addComProp);
		grd_addComProp_photo.setAdapter(adpt_addComProp_photo);
	}

	/*
	 * (non-Javadoc)
	 * @see com.pvo.prototype.PVOAction#processResponse(java.lang.Object)
	 * Handle response of webservice
	 */
	@Override
	public void processResponse(Object response) {}
	
	//This is used for set the Location of the google map
	private void setUpMapIfNeeded(boolean flag) {
		if (googleMap_addComProp_gmap == null) {
			//googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			googleMap_addComProp_gmap = mapview_addComProp.getMap();
			googleMap_addComProp_gmap.getUiSettings().setMyLocationButtonEnabled(true);
			
			if (googleMap_addComProp_gmap != null) {
				googleMap_addComProp_gmap.setMyLocationEnabled(true);
				
				MapsInitializer.initialize(this.getActivity()); 
				
				latLong = getCurrentLatLong().split("@");
				
				/*LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
				Criteria criteria = new Criteria();
				if(locationManager != null) {
					bestProvider_addComProp = locationManager.getBestProvider(criteria, true);
					Location location = locationManager.getLastKnownLocation(bestProvider_addComProp);
					if(location == null) {
						location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					} 
					if(location == null) {
						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					}
					
					if(location != null) {*/
				   if(latLong.length > 0) {
							if(googleMarker_addComProp != null) 
								googleMarker_addComProp = googleMap_addComProp_gmap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latLong[0]),Double.parseDouble(latLong[1]))));
						
							if(flag) {
								latitude = latLong[0];// String.valueOf(location.getLatitude());
								longitude = latLong[1];//String.valueOf(location.getLongitude());
							} else {
								try {
									ProjectUtility.sys(isPrint, TAG, "Google map from edit");
									latitude = propertyDetailJsonObject.getString(Constant.AddProperty.LATITUDE);
									longitude = propertyDetailJsonObject.getString(Constant.AddProperty.LONGITUDE);
									
									ProjectUtility.sys(isPrint, TAG, "latitude-> "+latitude);
									ProjectUtility.sys(isPrint, TAG, "longitude-> "+longitude);
									
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}	/*latitude_addComProp = String.valueOf(location.getLatitude());
							longitude_addComProp = String.valueOf(location.getLongitude());*/
							
						//if(location != null) {
							CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(latLong[0]),Double.parseDouble(latLong[1])));
							googleMap_addComProp_gmap.moveCamera(center);
							CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
							googleMap_addComProp_gmap.animateCamera(zoom);
						//}
					//}
				//}
							
				googleMap_addComProp_gmap.setOnMapClickListener(new OnMapClickListener() {
					@Override
					public void onMapClick(LatLng latlng) {
						//drawMarker(latlng);
						openGoogleMapDialog();
					}
				});
			}
		}
	}
	
	//Open google map dialog
	private void openGoogleMapDialog() {
		
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.googlemap_activity, null);
		//final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(HomeActivity.this);
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();	
		
		
		final GoogleMap googleMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.fgm_addProp_googleMap)).getMap();
		if (googleMap != null) {
			//googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			googleMap.setMyLocationEnabled(true);
			
			LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			String provider = locationManager.getBestProvider(criteria, true);
			Location location = locationManager.getLastKnownLocation(provider);
			
			if(location == null) {
				location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			}
			if(location == null) {
				location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}
		
			if(location != null) {
				
				if(marker_addComProp_marker != null)
					marker_addComProp_marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())));
				
				CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude)));
				googleMap.moveCamera(center);
				CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
				googleMap.animateCamera(zoom);
			}
			
			googleMap.setOnMapClickListener(new OnMapClickListener() {
				@Override
				public void onMapClick(LatLng latlng) {
					if (marker_addComProp_marker != null) {
						marker_addComProp_marker.setPosition(latlng);
					} else {
						marker_addComProp_marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(latlng.latitude,latlng.longitude)));
					}
					CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latlng.latitude, latlng.longitude));
					googleMap.moveCamera(center);
					CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
					googleMap.animateCamera(zoom);
				}
			});
			
			googleMap.setOnCameraChangeListener(new OnCameraChangeListener() {
				public void onCameraChange(CameraPosition position) {
					LatLng latLng = position.target;
					latitude = String.valueOf(latLng.latitude);
					longitude = String.valueOf(latLng.longitude);
					float maxZoom = 19.9f;
				    if (position.zoom > maxZoom)
				        googleMap.moveCamera(CameraUpdateFactory.zoomTo(maxZoom));
				}
			});
		
		
		Button btn_addProp_googleMap_done = (Button) view.findViewById(R.id.btn_addProp_googleMap_done);
		btn_addProp_googleMap_done.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				/*addPropLatitudeEditBox.setText(latitude);
				addPropLongitudeEditBox.setText(longitude);*/
				drawMarker(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)));
				alertDialog.cancel();
				removeGoogleMapFragment();
			}
		});
		
		Button btn_addProp_googleMap_cancel = (Button) view.findViewById(R.id.btn_addProp_googleMap_cancel);
		btn_addProp_googleMap_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				alertDialog.cancel();
				removeGoogleMapFragment();
			}
		});
		}
	}
	
	//Draw marker on Map 
	private void drawMarker(LatLng latlng) {
		if (googleMarker_addComProp != null) {
			googleMarker_addComProp.setPosition(latlng);
		} else {
			googleMarker_addComProp = googleMap_addComProp_gmap.addMarker(new MarkerOptions().position(new LatLng(latlng.latitude,latlng.longitude)));
		}
		CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latlng.latitude, latlng.longitude));
		googleMap_addComProp_gmap.moveCamera(center);
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
		googleMap_addComProp_gmap.animateCamera(zoom);
	}
	//END
	
	//Remove google map fragment when click on done or cancle button
	private void removeGoogleMapFragment() {
		SupportMapFragment f = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fgm_addProp_googleMap);
		 if (f != null) 
			 getFragmentManager().beginTransaction().remove(f).commit();
	}
	
	/*
	 * Set last capture photo t image view and display total photo 
	 */
	private void setPhotoAndNumberOfPhoto() {
		int total = ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.COMMERCIAL_PHOTO_PATH).size();
		if(total > 0) {
			tv_addComProp_counter.setText(""+total);
			iv_addComProp_topPhotoView.setBackgroundResource(Color.TRANSPARENT);
			Uri uri = Uri.fromFile(new File(ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.COMMERCIAL_PHOTO_PATH).get(total-1)));
			Picasso.with(getActivity()).load(uri).into(iv_addComProp_topPhotoView);
		} else {
			iv_addComProp_topPhotoView.setImageBitmap(null);
			tv_addComProp_counter.setText("0");
			iv_addComProp_topPhotoView.setBackgroundResource(R.drawable.no_image);
		}
	}
	
	/*
	 * Check validation and redirect to share activity
	 */
	private void redirectToShareWithActivity() {
		if(checkEditboxValidation(edt_addComProp_address,"Enter Address") 
		   && checkSpinnerValidation(spn_addComProp_location, "Please select location")
		   && checkEditboxValidation(edt_addComProp_area,"Enter Area") 
		   && checkEditboxValidation(edt_addComProp_Price,"Enter Price")) {
			
			
			Intent intent = new Intent(getActivity(), ZShareWithMainActivity.class);
			
			/*
			 * Pass this value is from edit
			 */
			if(propIntent.getString("From") != null) {
				if(propIntent.getString("From").equals("Edit")) {
					intent.putExtra("From","Edit");
				    intent.putExtra(Constant.EditProperty.ID, propIntent.getString(Constant.MyProperty.PROPERTY_ID));
				}
			}
			
			intent.putExtra(Constant.AddProperty.STR_OPTIONS, getValue(togl_addComProp_saleRent));
			intent.putExtra(Constant.AddProperty.PURPOSE,Constant.Commercial.COMMERCIAL);
			intent.putExtra(Constant.AddProperty.PROPERTY_TYPE, str_addComProp_propType);
			intent.putExtra(Constant.AddProperty.ADDRESS, getValue(edt_addComProp_address));
			intent.putExtra(Constant.AddProperty.STATE_ID,StateCityLocationUtils.getIdOfItem(spn_addComProp_state.getSelectedItemPosition(),Constant.StateCityLoation.FROM_STATE));
			intent.putExtra(Constant.AddProperty.CITY_ID,StateCityLocationUtils.getIdOfItem(spn_addComProp_city.getSelectedItemPosition(),Constant.StateCityLoation.FROM_CITY));
			intent.putExtra(Constant.AddProperty.LOCATION,StateCityLocationUtils.getIdOfItem(spn_addComProp_location.getSelectedItemPosition(),Constant.StateCityLoation.FROM_LOCATION));
			intent.putExtra(Constant.AddProperty.LATITUDE,latitude);
			intent.putExtra(Constant.AddProperty.LONGITUDE,longitude);
			//intent.putExtra(Constant.AddProperty.BED,getValue(tv_bedRoom));
			intent.putExtra(Constant.AddProperty.BATH_ROOM,getValue(tv_addComProp_bathRoom));
			intent.putExtra(Constant.AddProperty.FLOOR,getValue(tv_addComProp_floor));
			intent.putExtra(Constant.AddProperty.FURNISH_STATUS,str_addComProp_furnish);
			intent.putExtra(Constant.AddProperty.AREA_SQ_FIT,getValue(edt_addComProp_area));
			intent.putExtra(Constant.AddProperty.AREA_YARD,getValue(spn_addComProp_areaUnit));
			intent.putExtra(Constant.AddProperty.CONSTRUCTION_AREA,getValue(edt_addComProp_constArea));
			intent.putExtra(Constant.AddProperty.AREA_UNIT,getValue(spn_addComProp_constAreaUnit));
			intent.putExtra(Constant.AddProperty.RISE,getValue(togl_addComProp_buildingType));
			intent.putExtra(Constant.AddProperty.PRICE,getValue(edt_addComProp_Price));
			intent.putExtra(Constant.AddProperty.DASTAWAGE,getValue(edt_addComProp_dastavej));
			intent.putExtra(Constant.AddProperty.TRANSFER_FEES,getValue(edt_addComProp_transFees));
			intent.putExtra(Constant.AddProperty.AEC_AUDA,getValue(edt_addComProp_audaLegal));
			intent.putExtra(Constant.AddProperty.PARKING,getValue(edt_addComProp_parCharge));
			intent.putExtra(Constant.AddProperty.YEAR_BUILD_UP,getValue(spn_addComProp_buildyear));
			intent.putExtra(Constant.AddProperty.ON_ROAD,getValue(spn_addComProp_roadTouch));
			intent.putExtra(Constant.AddProperty.COMMENTS,getValue(edt_addComProp_comment));
			intent.putExtra(Constant.AddProperty.CMBTP_SCHEME,ProjectUtility.getTPSchemeId());
			intent.putExtra(Constant.AddProperty.CHK_ZONE,getValue(spn_addComProp_zone));
			intent.putExtra(Constant.AddProperty.NA_STATUS,String.valueOf(SpinnerItem.getAddPropNaStatusList().get(getValue(spn_addComProp_na))));
			intent.putExtra(Constant.AddProperty.IMAGES, StringUtils.join(ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.COMMERCIAL_PHOTO_PATH),","));
			
			intent.putExtra(Constant.AddProperty.NAVISHARAT, getTremValue(chk_tremsCom_naviSharat));
			intent.putExtra(Constant.AddProperty.SHREE_SARKAR, getTremValue(chk_tremsCom_juniSharat));
			intent.putExtra(Constant.AddProperty.KHETI, getTremValue(chk_tremsCom_kheti));
			intent.putExtra(Constant.AddProperty.PRASSAP, getTremValue(chk_tremsCom_prasha));
			intent.putExtra(Constant.AddProperty.DIS_PUTE, getTremValue(chk_tremsCom_dispute));
			intent.putExtra(Constant.AddProperty.SHREE_SARKAR, getTremValue(chk_tremsCom_shreeSarkar));
			
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.new_right_to_left_layout,R.anim.new_left_to_right_layout);
			
		}
	}
	/*
	 * Set edit box validation 
	 */
	private boolean checkEditboxValidation(EditText editText,String errorMsg) {
		if(editText != null) {
			if(editText.getText().length() == 0 || editText.getText().toString().replaceAll(" ", "").length() == 0){
				editText.requestFocus();
				editText.performClick();
				editText.setError(errorMsg);
				ProjectUtility.showToast(getActivity(), editText, errorMsg);
				return false;
			} 
		}
		return true;
	}
	
	/*
	 * Check spinner validation 
	 * Location spinner
	 */
	private boolean checkSpinnerValidation(Spinner spinner,String errorMsg) {
		if(spinner != null) {
			if(spinner.getSelectedItem() != null) {
				if(spinner.getSelectedItem().toString().equals("Select")) {
					spinner.setFocusableInTouchMode(true);
					spinner.requestFocus();
					spinner.setFocusableInTouchMode(false);
					spinner.clearFocus();
					ProjectUtility.showToast(getActivity(), spinner, errorMsg);
					return false;
				}
			} else {
				ProjectUtility.showToast(getActivity(), spinner, "Area not found");
				//Toast.makeText(getActivity(), "Area not found", Toast.LENGTH_SHORT).show();
				return false;
			}
			
		}
		
		return true;
	}
	
	/*
	 * 
	 * This method is for testing
	 */
	private void toast() {
		Toast.makeText(getActivity(), "This is for Testing", 10).show();
	}

	@Override
	public void onResume() {
		mapview_addComProp.onResume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapview_addComProp.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapview_addComProp.onLowMemory();
	}
	
	
	/*
	 * Show/Hide field as per property type selection
	 */
	private void showHideFieldByPropType(String propType) {
		/*
		 * Hide all field which is not common
		 */
		hideFieldBeforeShow();
		
		/*
		 * Show price more icon is sale and not equal to plot 
		 */
		showhidePriceMoreOptionIcon();
		
		//Appartment(Flat)
		if(propType.equals("Shop") || propType.equals("Showroom") || propType.equals("Office/Space")){
			System.out.println("--> Shop <---");
			ll_addComProp_bathRoom.setVisibility(View.VISIBLE);
			ll_addComProp_floor.setVisibility(View.VISIBLE);
			ll_addComProp_furnishDet.setVisibility(View.VISIBLE);
			ll_addComProp_buildingType.setVisibility(View.VISIBLE);
			btn_addComProp_amenities.setVisibility(View.VISIBLE);
			ll_addComProp_buildYear.setVisibility(View.VISIBLE);
			
		} // Factory
		else if(propType.equals("Factory")) {
			ll_addComProp_bathRoom.setVisibility(View.VISIBLE);
			ll_addComProp_floor.setVisibility(View.VISIBLE);
			ll_addComProp_furnishDet.setVisibility(View.VISIBLE);
			btn_addComProp_amenities.setVisibility(View.VISIBLE);
			ll_addComProp_buildYear.setVisibility(View.VISIBLE);
		}// Warehouse
		else if(propType.equals("Warehouse")) {
			ll_addComProp_bathRoom.setVisibility(View.VISIBLE);
			ll_addComProp_floor.setVisibility(View.VISIBLE);
			ll_addComProp_furnishDet.setVisibility(View.VISIBLE);
			ll_addComProp_buildYear.setVisibility(View.VISIBLE);
			
		}// Agri./Farm Land
		/*else if(propType.equals("Agri./Farm Land")) {
			ll_addComProp_tpscheme.setVisibility(View.VISIBLE);
			ll_addComProp_zone.setVisibility(View.VISIBLE);
			ll_addComProp_na.setVisibility(View.VISIBLE);
			btn_addComProp_trems.setVisibility(View.VISIBLE);
			ll_addComProp_constArea.setVisibility(View.VISIBLE);
			ll_addComProp_roadTouch.setVisibility(View.VISIBLE);
		}*/// Industrial Land
		else if(propType.equals("Industrial Land")) {
			ll_addComProp_tpscheme.setVisibility(View.VISIBLE);
			ll_addComProp_zone.setVisibility(View.VISIBLE);
			ll_addComProp_na.setVisibility(View.VISIBLE);
			ll_addComProp_constArea.setVisibility(View.VISIBLE);
			ll_addComProp_roadTouch.setVisibility(View.VISIBLE);
			btn_addComProp_trems.setVisibility(View.VISIBLE);
		}
	}
	
	/*
	 * Hide field before show
	 */
	private void hideFieldBeforeShow() {
		ll_addComProp_bathRoom.setVisibility(View.GONE);
		ll_addComProp_floor.setVisibility(View.GONE);
		ll_addComProp_furnishDet.setVisibility(View.GONE);
		ll_addComProp_buildingType.setVisibility(View.GONE);
		btn_addComProp_amenities.setVisibility(View.GONE);
		btn_addComProp_trems.setVisibility(View.GONE);
		ll_addComProp_tpscheme.setVisibility(View.GONE);
		ll_addComProp_zone.setVisibility(View.GONE);
		ll_addComProp_na.setVisibility(View.GONE);
		ll_addComProp_constArea.setVisibility(View.GONE);
		ll_addComProp_buildYear.setVisibility(View.GONE);
		ll_addComProp_roadTouch.setVisibility(View.GONE);
	}
	
	/*
	 * Show price 
	 */
	private void showHidePriceMoreOptionLayout() {
		if(ll_addComProp_priceOprion.isShown()) {
			ll_addComProp_priceOprion.setVisibility(View.GONE);
			btn_addComProp_priceOption.setBackgroundResource(R.drawable.drop_down_2);
		} else {
			ll_addComProp_priceOprion.setVisibility(View.VISIBLE);
			btn_addComProp_priceOption.setBackgroundResource(R.drawable.drop_up);
		}
	}
	
	/*
	 * Price option bitton 
	 * Show hide show more option if sale and not equal to ploat
	 */
	private void showhidePriceMoreOptionIcon() {
		if(togl_addComProp_saleRent.getCurrentText().equals("Sale") && !str_addComProp_propType.equals("Agri./Farm Land") && !str_addComProp_propType.equals("Industrial Land")) {
			btn_addComProp_priceOption.setVisibility(View.VISIBLE);
		} else {
			btn_addComProp_priceOption.setVisibility(View.GONE);
			ll_addComProp_priceOprion.setVisibility(View.GONE);
		}
	}
	
	/*
	 * Get the value of visible item
	 */
	private String getValue(View view) {
		if(view instanceof Spinner) {
			if(view.isShown()) {
				return ((Spinner) view).getSelectedItem().toString();
			} 
		} else if(view instanceof EditText) {
			if(view.isShown()) {
				return ((EditText) view).getText().toString();
			}
		} else if(view instanceof MySwitch) {
			if(view.isShown()) {
				return ((MySwitch) view).getCurrentText().toString();
			}
		} else if(view instanceof TextView) {
			if(view.isShown()) {
				return ((TextView) view).getText().toString();
			}
		} 
		return "";
	}
	
	/*
	 * Trems check box
	 * get the status of check box if it is checked or not
	 */
	private boolean getTremValue(CheckBox checkBox) {
		if(checkBox != null) {
			return checkBox.isChecked(); 
		}
		return false;
	}
	
	/*
	 * Property detail
	 * get the detail of property when edit  
	 */
	public void getPropertyDetailById() {
		FindByPropertyIdService findByPropertyIdService = new FindByPropertyIdService();
		WebserviceClient propertyDetailWebserviceClient = new WebserviceClient(getActivity(),findByPropertyIdService);
		propertyDetailWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.PreferredBrokers.API_STATUS)){
						propertyDetailJsonObject = jsonArray.getJSONObject(0);
						
						//1) Sale/Rent toggle
						setDefaultToggleValue(togl_addComProp_saleRent,propertyDetailJsonObject.getString(Constant.AddProperty.STR_OPTIONS));
						/*
						 * If property is for sale then show more price option
						 */
						if(propertyDetailJsonObject.getString(Constant.AddProperty.STR_OPTIONS).equals("Sale")) {
							showhidePriceMoreOptionIcon();
							btn_addComProp_priceOption.performClick();
						}
						
						//2) Property type
						ProjectUtility.addPropertyType(getActivity(), ll_addComProp_propType,Constant.PropertyType.commercialIconId,Constant.PropertyType.commercialTitle,Constant.Commercial.COMMERCIAL,false,setDefaultPropertyType("Shop"));
						//str_propType = propertyDetailJsonObject.getString("");
						
						//3) Address
						setDefaultEditBoxValue(edt_addComProp_address, propertyDetailJsonObject.getString(Constant.AddProperty.ADDRESS));
						
						//4) state(Spinner)
						StateCityLocationUtils.getStateListAll(getActivity(), spn_addComProp_state,propIntent.getString("From"),propertyDetailJsonObject.getString(Constant.EditProperty.STATE_NAME));
						
						//5) City(Spinner)
						
						//6) location(Spinner)
						
						//7) latlong on map(Google map)
						setUpMapIfNeeded(false);
						drawMarker(new LatLng(Double.parseDouble(propertyDetailJsonObject.getString(Constant.EditProperty.LATITUDE)), Double.parseDouble(propertyDetailJsonObject.getString(Constant.EditProperty.LONGITUDE))));
						
						//8) Bedroom (Text view)
						/*if(tv_bedRoom.isShown()) {
							bedCounter = Integer.parseInt(propertyDetailJsonObject.getString(Constant.EditProperty.BED));
							setDefaultTextBoxValue(tv_bedRoom, propertyDetailJsonObject.getString(Constant.EditProperty.BED));
						}*/
						
						//9) Bathroom (Text view)
						if(tv_addComProp_bathRoom.isShown()) {
							count_addComProp_bath = Integer.parseInt(propertyDetailJsonObject.getString(Constant.EditProperty.BED));
							setDefaultTextBoxValue(tv_addComProp_bathRoom, propertyDetailJsonObject.getString(Constant.EditProperty.BED));
						}
						
						//10) Floor (Text view)
						if(tv_addComProp_floor.isShown()) {
							count_addComProp_floor = Integer.parseInt(propertyDetailJsonObject.getString(Constant.EditProperty.FLOOR)); 
							setDefaultTextBoxValue(tv_addComProp_floor, propertyDetailJsonObject.getString(Constant.EditProperty.FLOOR));
						}
						
						//11) TP.Scheme (Spinner)
						if(spn_addComProp_tpscheme.isShown()) {
							setDefaultSpinnerValue(spn_addComProp_tpscheme,ProjectUtility.tpSchemeAdapter ,propertyDetailJsonObject.getString(Constant.EditProperty.CITY_NAME));
						}
						
						//12) Zone (Spinner)
						if(spn_addComProp_zone.isShown()) {
							setDefaultSpinnerValue(spn_addComProp_zone,zoneAdapter,propertyDetailJsonObject.getString(Constant.EditProperty.CITY_NAME));
						}
						
						//13) N.A status((Spinner))
						if(spn_addComProp_na.isShown())
							setDefaultSpinnerValue(spn_addComProp_na,naStatusAdapter,SpinnerItem.getAddPropNaStatusListKey(propertyDetailJsonObject.getString(Constant.AddProperty.NA_STATUS)));
						
						//14) Furnish option(Custome view)
						setDefaultSelFurnishStatus(propertyDetailJsonObject.getString(Constant.AddProperty.FURNISH_STATUS));
						
						//15) Area(Editbox) and area unit
						setDefaultEditBoxValue(edt_addComProp_area, propertyDetailJsonObject.getString(Constant.AddProperty.MIN_AREA));
						
						//16) Construction area
						setDefaultEditBoxValue(edt_addComProp_constArea, propertyDetailJsonObject.getString(Constant.AddProperty.CONSTRUCTION_AREA));
						
						//17) Building type ("Low Rise", "1"),("High Rise", "0")
						setDefaultToggleValue(togl_addComProp_buildingType,SpinnerItem.getBuildingTypeListKey(propertyDetailJsonObject.getString(Constant.AddProperty.RISE)));
						
						//18) Price
						ProjectUtility.sys(isPrint, TAG, "Price--> "+propertyDetailJsonObject.getString(Constant.AddProperty.PRICE));
						setDefaultEditBoxValue(edt_addComProp_Price, propertyDetailJsonObject.getString(Constant.AddProperty.PRICE));
						
						//19) Dastavej
						setDefaultEditBoxValue(edt_addComProp_dastavej,propertyDetailJsonObject.getString(Constant.AddProperty.DASTAWAGE));
						
						//20) Transfer fee
						setDefaultEditBoxValue(edt_addComProp_transFees, propertyDetailJsonObject.getString(Constant.AddProperty.TRANSFER_FEES));
						
						//21) AEC/Auda Legal
						setDefaultEditBoxValue(edt_addComProp_audaLegal, propertyDetailJsonObject.getString(Constant.AddProperty.AEC_AUDA));
						
						//22) Parking Charge
						setDefaultEditBoxValue(edt_addComProp_parCharge, propertyDetailJsonObject.getString(Constant.AddProperty.PARKING));
						
						//23) Build Year
						setDefaultSpinnerValue(spn_addComProp_buildyear,builtYearAdapter,propertyDetailJsonObject.getString(Constant.AddProperty.YEAR_BUILD_UP));
						
						//24) Road touch
						if(spn_addComProp_roadTouch.isShown())
							setDefaultSpinnerValue(spn_addComProp_roadTouch,onRoadAdapter,SpinnerItem.getOnRoadListKey(propertyDetailJsonObject.getString(Constant.AddProperty.ON_ROAD)));
						
						//25) Comment
						setDefaultEditBoxValue(edt_addComProp_comment, propertyDetailJsonObject.getString(Constant.AddProperty.COMMENTS));
						
						//26) Photo layout set one photo here
						try {
							
							/*
							 * Remove all photo befo1re add new photo 
							 */
							ProjectUtility.removeAllResComPropPhoto();
							
							//Image-1
							if(!checkImageLink(propertyDetailJsonObject.getString(Constant.EditProperty.IMAGE1_LINK)).equals("NoImage.jpeg")) {
								ProjectUtility.sys(isPrint, TAG, "IMAGE1_LINK:"+propertyDetailJsonObject.getString(Constant.EditProperty.IMAGE1_LINK));
								ProjectUtility.storePhoto(getBitmapFromURL(propertyDetailJsonObject.getString(Constant.EditProperty.IMAGE1_LINK)), Constant.ZAddProperty.RESIDENTIAL_PHOTO_PATH,"A");
							}
							
							//Image-2
							if(!checkImageLink(propertyDetailJsonObject.getString(Constant.EditProperty.IMAGE2_LINK)).equals("NoImage.jpeg")) {
								ProjectUtility.sys(isPrint, TAG, "IMAGE2_LINK:"+propertyDetailJsonObject.getString(Constant.EditProperty.IMAGE2_LINK));
								ProjectUtility.storePhoto(getBitmapFromURL(propertyDetailJsonObject.getString(Constant.EditProperty.IMAGE2_LINK)), Constant.ZAddProperty.RESIDENTIAL_PHOTO_PATH,"B");
							}
							
							//Image-3
							if(!checkImageLink(propertyDetailJsonObject.getString(Constant.EditProperty.IMAGE3_LINK)).equals("NoImage.jpeg")) {
								ProjectUtility.sys(isPrint, TAG, "IMAGE3_LINK:"+propertyDetailJsonObject.getString(Constant.EditProperty.IMAGE3_LINK));
								ProjectUtility.storePhoto(getBitmapFromURL(propertyDetailJsonObject.getString(Constant.EditProperty.IMAGE3_LINK)), Constant.ZAddProperty.RESIDENTIAL_PHOTO_PATH,"C");
							}
							
							//Image-4
							if(!checkImageLink(propertyDetailJsonObject.getString(Constant.EditProperty.IMAGE4_LINK)).equals("NoImage.jpeg")) {
								ProjectUtility.sys(isPrint, TAG, "IMAGE4_LINK:"+propertyDetailJsonObject.getString(Constant.EditProperty.IMAGE4_LINK));
								ProjectUtility.storePhoto(getBitmapFromURL(propertyDetailJsonObject.getString(Constant.EditProperty.IMAGE4_LINK)), Constant.ZAddProperty.RESIDENTIAL_PHOTO_PATH,"D");
							}
							
							setPhotoAndNumberOfPhoto();
							
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						//27) Set nominee
						ZCommercialNomineeListAdaptor.setSelectedCommercialoNominee(propertyDetailJsonObject.getJSONArray("nominee"));
						
						//28) Aemenities ID
						ZAminitiesCommertialAdaptor.setSelectdAmenities(propertyDetailJsonObject.getJSONArray("facilityid"));
						/*
						 * Set amenities URL and amenities title 
						 * This is for show selected amenitis 
						 */
						ZAminitiesCommertialAdaptor.setAmenitiesUrl(propertyDetailJsonObject.getJSONArray("facilities"));
						showSelectedFacility();
						
						//29) Trems (Set only when property type is plot)
						if(propertyDetailJsonObject.getString(Constant.EditProperty.PROPERTY_TYPE).equals("Plot")) {
							ZTersmListAdaptor.setTerms(propertyDetailJsonObject);
						}
						
						//30) Area
						ZAreaListAdaptor.setSelectedArea(propertyDetailJsonObject.getJSONArray("area"));
						
						//31) group
						ZGroupListAdaptor.setSelecttedGroup(propertyDetailJsonObject.getJSONArray("groups"));
						
						//32) prefered broker
						ZPreeferedListAdaptor.setSelecttedPreferredBroker(Arrays.asList(propertyDetailJsonObject.getString("smstobrokers").split("\\s*,\\s*")));
						
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		});
		propertyDetailWebserviceClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,propIntent.getString(Constant.MyProperty.PROPERTY_ID),userSessionManager.getSessionValue(Constant.Login.USER_ID));
	}
	
	/*
	 * Return position of pass value from json array (This is only for edit property)
	 * and set this item into spinner 
	 */
	private int setDefaultSpinnerValue(Spinner spinner, ArrayAdapter<String> arrayAdapter, String value) {
		if(arrayAdapter != null) {
			ProjectUtility.sys(isPrint, TAG, "setDefaultSpinnerValue"+value);
			if(spinner.isShown()) {
				spinner.setSelection(arrayAdapter.getPosition(value));
			}
		}
		return 0;
	}
	
	/*
	 * Furnish option 
	 * This is used only for edit property 
	 *	1) str_furnish = "3";//Fully
	 *  2) str_furnish = "2";//Semi
	 *  3) str_furnish = "0";//Un
	 */
	private void setDefaultSelFurnishStatus(String status){
		
		if(status.equals("3")) {
			ll_addComPropFurnishDetail.setBackgroundResource(R.drawable.select1);
			str_addComProp_furnish = "3";
		} else if(status.equals("2")) {
			ll_addComPropFurnishDetail.setBackgroundResource(R.drawable.select2);
			str_addComProp_furnish = "2";
		} else if(status.equals("0")) {
			ll_addComPropFurnishDetail.setBackgroundResource(R.drawable.select3);
			str_addComProp_furnish = "0";
		}
	}
	
	/*
	 * Edit box value 
	 * Set preset value of edit box when edit property
	 * This is only for edit property
	 */
	private void setDefaultEditBoxValue(EditText editText, String strValue) {
		if(editText.isShown()) {
			editText.setText(strValue);
		}
	}
	
	/*
	 * Textview value 
	 * Set  preset value of textview when edit property
	 * This only for edit property
	 */
	private void setDefaultTextBoxValue(TextView textView, String strValue) {
		if(textView.isShown()) {
			textView.setText(strValue);
		}
	}
	
	
	/*
	 * Toggle button value
	 * Set toggle button value
	 */
	private void setDefaultToggleValue(MySwitch mySwitch, String strValue) {
		if(strValue.equals("Rent")) {
			mySwitch.setChecked(false);
		} else if(strValue.equals("Sale")) {
			mySwitch.setChecked(true);
		} else if(strValue.equals("Low Rise")) {
			mySwitch.setChecked(false);
		} else if(strValue.equals("High Rise")) {
			mySwitch.setChecked(true);
		}
	}
	
	/*
	 * Property type 
	 * Property type set default value
	 * This only for edit property
	 */
	private int setDefaultPropertyType(String proeprtyType) {
		for(int i= 0;i <Constant.PropertyType.residentialTitle.length;i++) {
			if(Constant.PropertyType.residentialTitle[i].equals(proeprtyType)) {
				return i;
			}
		}
		return 0;
	}
	
	/*
	 * Store photo into directory 
	 * This is call from edit only  
	 */
	private Bitmap getBitmapFromURL(String mUrl) throws IOException {
		if(mUrl != null) {
			URL url = new URL(mUrl);
			return BitmapFactory.decodeStream(url.openConnection().getInputStream());
		}
		return null;
	}
	
	/*
	 * Check the image link is it proper or not
	 * if image link is noImage then ignore this image link
	 * This is used only when edit property  
	 */
	private String checkImageLink(String imgLink) {
		String[] separated = imgLink.split("_");
		ProjectUtility.sys(isPrint, TAG, "Image path"+separated[1]);
		return separated[1];
	}
	
	
	/*
	 * Crop image take by camera or gallery
	 */
	private void doCrop(Uri picUri) {
		try {
			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			//indicate image type and Uri
			cropIntent.setDataAndType(picUri, "image/*");
			//set crop properties
			cropIntent.putExtra("crop", "true");
			//indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			//indicate output X and Y
			cropIntent.putExtra("outputX", 256);
			cropIntent.putExtra("outputY", 256);
			//retrieve data on return
			cropIntent.putExtra("return-data", true);
			//start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, PIC_CROP);
			
		} catch(ActivityNotFoundException anfe){
		    Toast.makeText(getActivity(), "Whoops - your device doesn't support the crop action!", Toast.LENGTH_SHORT).show();;
		    
		}
	}
	
	/*
	 * Check price is not start with zero
	 */
	private void priceNotStartWithZero(final EditText editText,final String msg) {
		editText.addTextChangedListener(new TextWatcher() {
		    public void afterTextChanged(Editable s){}
		    
		    public void beforeTextChanged(CharSequence s, int start, int count, int after){
		    	//ProjectUtility.sys(isPrint, TAG, "beforeTextChanged--> "+s);
		    }
		    
		    public void onTextChanged(CharSequence s, int start, int before, int count){
		    	//ProjectUtility.sys(isPrint, TAG, "onTextChanged--> "+s);
		        String x = s.toString();
		        if(x.startsWith("0")) {
		        	ProjectUtility.showToast(getActivity(), editText, msg);
		        	editText.setText("");
		        	
		        } else {
		        	editText.removeTextChangedListener(this);
		            if(s.toString().length() > 0) {
		            	editText.setText(toIndianRupessFormat(Double.parseDouble(s.toString().replaceAll("[,. ]",""))));
		            	editText.setSelection(editText.getText().length());
		            }
		            editText.addTextChangedListener(this);
		        }
		    }
		}); 
	}
	
	//This method is used to convert the price into indian rupess  format
	private String toIndianRupessFormat(double doubleValue) {
		DecimalFormat decimalFormat = new DecimalFormat("##,##,##0");
		return decimalFormat.format(doubleValue);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(!(v instanceof EditText)) {
			 ProjectUtility.sys(isPrint, TAG, "Ontouch event");
			 InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			 imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
			 return true;
		}
		return false;
	}
	
	/*
	 * Get current location latitude and longitude from GPS
	 */
	public String getCurrentLatLong() {
		return gpsTracker.getLatitude() + "@" + gpsTracker.getLongitude();
	}
	
	/*
	 * Show selected facility 
	 * This method is used to show the facility icon and facility title 
	 */
	public static void showSelectedFacility() {
		if(ll_addComProp_facility != null)
			ll_addComProp_facility.removeAllViews();
		
		for (int i = 0; i < ZAminitiesCommertialAdaptor.getAmenitiesUrl().size(); i++) {
			ZPropDetFacilityView detFacilityView = new ZPropDetFacilityView(activity,ZAminitiesCommertialAdaptor.getAmenitiesUrl().get(i),"");//ZAminitiesCommertialAdaptor.getAmenitiesTitle().get(i));
			ll_addComProp_facility.addView(detFacilityView);
		}
	}
}
