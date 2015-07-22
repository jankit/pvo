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
import z.com.pvo.newAdapter.ZAminitiesResidentialAdaptor;
import z.com.pvo.newAdapter.ZAreaListAdaptor;
import z.com.pvo.newAdapter.ZGroupListAdaptor;
import z.com.pvo.newAdapter.ZNomineeListResidentialAdaptor;
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
public class ZPropAddRecidentialFragment extends PVOFragment implements OnClickListener, OnItemSelectedListener, OnTouchListener {

	/*
	 * Picture crop 
	 */
	private final int PIC_CROP = 3;
	
	/*
	 * print log into console
	 */
	private Boolean isPrint = true;
	protected String TAG = "ZPropAddRecidentialFragment";
	
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
	private MySwitch togl_addResProp_saleRent,togl_addResProp_buildingType;
	
	/*
	 * Linear layout
	 */
	private LinearLayout ll_addResProp_propType, ll_addResProp_furnishDet, ll_addResProp_BedRoom,
						 ll_addResProp_bathRoom, ll_addResProp_floor, ll_addResPropFurnishDetail,
						 ll_addResProp_buildingType, ll_addResProp_tpscheme, ll_addResProp_zone,
						 ll_addResProp_na , ll_addResProp_priceOprion, ll_addResProp_buildYear, 
						 ll_addResProp_constArea, ll_addResProp_roadTouch, ll_addResProp_plotArea, 
						 ll_addResProp_area, ll_addResProp_container;
	
	private static LinearLayout ll_addResProp_facility;
	
	/*
	 * Relative layout
	 */
	private RelativeLayout rl_addResProp_photoContainer;
	
	/*
	 * All Edit text 
	 */
	private EditText edt_addResProp_address, edt_addResProp_area, edt_addResProp_Price, 
	                 edt_addResProp_comment, edt_addResProp_constArea, edt_addResProp_dastavej,
	                 edt_addResProp_transFees, edt_addResProp_audaLegal, edt_addResProp_parCharge,
	                 edt_addResProp_plotArea;
	
	/*
	 * All Spinner
	 */
	private Spinner spn_addResProp_location, spn_addResProp_city, spn_addResProp_district, 
					spn_addResProp_state, spn_addResProp_areaUnit, spn_addResProp_tpscheme, 
					spn_addResProp_zone, spn_addResProp_na, spn_addResProp_buildyear, 
					spn_addResProp_constAreaUnit, spn_addResProp_roadTouch, spn_addResProp_plotAreaUnit;
	
	/*
	 * Image View 
	 */
	private ImageView iv_addResProp_photo, iv_bed_minus, iv_bed_plus,iv_bath_minus, 
					 iv_bath_plus,iv_floor_minus, iv_floor_plus, iv_addResProp_topPhotoView;
	
	/*
	 * Textview
	 */
	private TextView tv_addResProp_counter, tv_bedRoom, tv_bathRoom, 
	                 tv_floor, tv_furnish_fully, tv_furnish_semi, 
	                 tv_furnish_un /*tv_photoDialog_counter*/;
	
	/*
	 * Button 
	 */
	private Button btn_addResProp_nominee, btn_addResProp_amenities, btn_addResProp_shreWith,
				   btn_addResProp_camera,btn_addResProp_gallery, btn_addResProp_priceOption, 
				   btn_addResProp_trems;
	
	
	/*
	 * View Bed,Bathroom,floor
	 */
	private View bedView, bathView, floorView;
	
	/*
	 * String variable
	 */
	private String str_furnish = "3", str_propType = "Flat";
	
	/*
	 * Counter for bed, bath, floor
	 */
	private int bedCounter = 1, bathCounter = 0, floorCounter = 0;
	
	/*
	 * Google map related
	 */
	private GoogleMap googleMap;
	private Marker googleMarker, marker;
	private String latitude,longitude;
	private MapView map_view;
	private GPSTracker gpsTracker;
	private String[] latLong;
	
	/*
	 * Photo dialog (Property photo related)
	 */
	private GridView grd_addResProp_photo;
	public static ZAddPropPhotoAdapter adpt_addResProp_photo;
	public static  List<String> list_Of_photo_Path;
	
	/*
	 * String array for area unit
	 */
	 private String[] areaUnit;
	 
	 /*
	  * Trems check box
	  */
	 private CheckBox chk_trems_naviSharat, chk_trems_juniSharat, chk_trems_kheti, 
	 				  chk_trems_prasha, chk_trems_dispute, chk_trems_shreeSarkar; 
	 
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
	 

	private static Activity activity;
	 
	
	 public ZPropAddRecidentialFragment() {
		setContentView(R.layout.z_prop_add_residential_fragment);
	 }
	
	
	@Override
	public void init(Bundle savedInstanceState) {
		
		activity = getActivity();
		
		/*
		 * Initialize GPS tracker
		 */
		gpsTracker = new GPSTracker(getActivity());
		
		/*
		 * Hide keyboard when focus change 
		 */
		InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);	
		
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
		nomineeListDialog = new ZNomineeListDialog(getActivity(),Constant.Residential.FROM_RESIDENTIAL);
		nomineeListDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		
		/*
		 * Initialize Amenities dialog
		 */
		amenitiesListDialog = new ZAmenitiesListDialog(getActivity(),Constant.Residential.FROM_RESIDENTIAL);
		amenitiesListDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		
		/*
		 * Initialize tearmsDailog
		 */
		termsListDialog = new ZTremsListDialog(getActivity(),Constant.Residential.FROM_RESIDENTIAL);
		termsListDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		
		if(propIntent.getString("From") != null) {
			MainFragmentActivity.setTitle("Edit Property");
		}
		
		/*
		 * Initialize all view and find by id 
		 */
		setupView();
		map_view.onCreate(savedInstanceState);
		
		/*
		 * State response handler
		 * This method return the json array
		 * Set the state spinner
		 */
		if(propIntent.getString("From") == null)
			 StateCityLocationUtils.getStateListAll(getActivity(), spn_addResProp_state,"","");
		
		
		/*
		 * Area Unit adapter 
		 * Construction area unit
		 * Plot area unit
		 * Fill the area unit sipnner by  area Unit string array
		 */
		areaUnit = Constant.ZAddProperty.areaUnit;
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,areaUnit); //selected item will look like a spinner set from XML
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn_addResProp_areaUnit.setAdapter(spinnerArrayAdapter);
		spn_addResProp_constAreaUnit.setAdapter(spinnerArrayAdapter);
		spn_addResProp_plotAreaUnit.setAdapter(spinnerArrayAdapter);
		
		/*
		 * TPScheme adapter
		 */
		ProjectUtility.getTPScheme(getActivity(), spn_addResProp_tpscheme);
		
		/*
		 * Zone
		 * Set zone spinner
		 */
		ArrayList<String> zoneList = new ArrayList<String>();
		zoneList.addAll(SpinnerItem.getAddPropZoneList().keySet());
		zoneAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, zoneList);
		zoneAdapter.setDropDownViewResource(R.layout.spinner_text);
		spn_addResProp_zone.setAdapter(zoneAdapter);
		//End
		
		
		/*
		 *N.A Layout fill the spinner using the Hash map 
		 */
		ArrayList<String> naStatusList = new ArrayList<String>();
		naStatusList.addAll(SpinnerItem.getAddPropNaStatusList().keySet());
		naStatusAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text,naStatusList);
		naStatusAdapter.setDropDownViewResource(R.layout.spinner_text);
		spn_addResProp_na.setAdapter(naStatusAdapter);
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
		spn_addResProp_buildyear.setAdapter(builtYearAdapter);
		//End
		
		
		/*
		 * Road touch spinner 
		 * Fill road touch adapter suing hash map
		 */
		ArrayList<String> onRoadList = new ArrayList<String>();
		onRoadList.addAll(SpinnerItem.getOnRoadList().keySet());
		onRoadAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, onRoadList);
		onRoadAdapter.setDropDownViewResource(R.layout.spinner_text);
		spn_addResProp_roadTouch.setAdapter(onRoadAdapter);
		//End
				
		/*
		 * Set visible filed by default of apartment
		 */
		showHideFieldByPropType("Apartment");
		
		/*
		 * Initialize google map 
		 */
		if(propIntent.getString("From") == null)
			setUpMapIfNeeded(true);
		
		
		/*
		 * Register spinner on item selected listener
		 */
		spn_addResProp_state.setOnItemSelectedListener(this);
		spn_addResProp_location.setOnItemSelectedListener(this);
		spn_addResProp_city.setOnItemSelectedListener(this);
		
		
		/*
		 * Register click lisitener for all button
		 */
		iv_addResProp_photo.setOnClickListener(this);
		btn_addResProp_nominee.setOnClickListener(this);
		btn_addResProp_amenities.setOnClickListener(this);
		btn_addResProp_shreWith.setOnClickListener(this);
		tv_furnish_fully.setOnClickListener(this);
		tv_furnish_semi.setOnClickListener(this);
		tv_furnish_un.setOnClickListener(this);
		togl_addResProp_buildingType.setOnClickListener(this);
		togl_addResProp_saleRent.setOnClickListener(this);
		rl_addResProp_photoContainer.setOnClickListener(this);
		btn_addResProp_priceOption.setOnClickListener(this);
		btn_addResProp_trems.setOnClickListener(this);
		
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
		
		togl_addResProp_saleRent = (MySwitch) findViewById(R.id.togl_addResProp_saleRent);
		togl_addResProp_buildingType = (MySwitch) findViewById(R.id.togl_addResProp_buildingType);
		ll_addResProp_propType = (LinearLayout) findViewById(R.id.ll_addResProp_propType);
		ll_addResProp_furnishDet = (LinearLayout) findViewById(R.id.ll_addResProp_furnishDet);
		ll_addResProp_BedRoom = (LinearLayout) findViewById(R.id.ll_addResProp_BedRoom);
		ll_addResProp_bathRoom = (LinearLayout) findViewById(R.id.ll_addResProp_bathRoom);
		ll_addResProp_floor = (LinearLayout) findViewById(R.id.ll_addResProp_floor);
		iv_addResProp_photo = (ImageView) findViewById(R.id.iv_addResProp_photo);
		rl_addResProp_photoContainer = (RelativeLayout) findViewById(R.id.rl_addResProp_photoContainer);
		tv_addResProp_counter = (TextView) findViewById(R.id.tv_addResProp_counter);
		iv_addResProp_topPhotoView = (ImageView) findViewById(R.id.iv_addResProp_topPhotoView);
		edt_addResProp_address = (EditText) findViewById(R.id.edt_addResProp_address);
		edt_addResProp_area = (EditText) findViewById(R.id.edt_addResProp_area);
		spn_addResProp_areaUnit = (Spinner) findViewById(R.id.spn_addResProp_areaUnit);
		spn_addResProp_buildyear = (Spinner) findViewById(R.id.spn_addResProp_buildyear);
		spn_addResProp_constAreaUnit = (Spinner) findViewById(R.id.spn_addResProp_constAreaUnit);
		spn_addResProp_plotAreaUnit = (Spinner) findViewById(R.id.spn_addResProp_plotAreaUnit);
		spn_addResProp_roadTouch = (Spinner) findViewById(R.id.spn_addResProp_roadTouch);
		spn_addResProp_tpscheme = (Spinner) findViewById(R.id.spn_addResProp_tpscheme);
		spn_addResProp_zone = (Spinner) findViewById(R.id.spn_addResProp_zone);
		spn_addResProp_na = (Spinner) findViewById(R.id.spn_addResProp_na);
		edt_addResProp_Price = (EditText) findViewById(R.id.edt_addResProp_Price);
		edt_addResProp_comment = (EditText) findViewById(R.id.edt_addResProp_comment);
		edt_addResProp_constArea = (EditText) findViewById(R.id.edt_addResProp_constArea);
		edt_addResProp_dastavej = (EditText) findViewById(R.id.edt_addResProp_dastavej);
		edt_addResProp_transFees = (EditText) findViewById(R.id.edt_addResProp_transFees);
		edt_addResProp_audaLegal = (EditText) findViewById(R.id.edt_addResProp_audaLegal);
		edt_addResProp_parCharge = (EditText) findViewById(R.id.edt_addResProp_parCharge);
		edt_addResProp_plotArea = (EditText) findViewById(R.id.edt_addResProp_plotArea);
		
		ll_addResProp_buildingType = (LinearLayout) findViewById(R.id.ll_addResProp_buildingType);
		ll_addResProp_tpscheme = (LinearLayout) findViewById(R.id.ll_addResProp_tpscheme);
		ll_addResProp_na = (LinearLayout) findViewById(R.id.ll_addResProp_na);
		ll_addResProp_constArea = (LinearLayout) findViewById(R.id.ll_addResProp_constArea);
		ll_addResProp_roadTouch = (LinearLayout) findViewById(R.id.ll_addResProp_roadTouch);
		ll_addResProp_plotArea = (LinearLayout) findViewById(R.id.ll_addResProp_plotArea);
		ll_addResProp_area = (LinearLayout) findViewById(R.id.ll_addResProp_area);
		ll_addResProp_container = (LinearLayout) findViewById(R.id.ll_addResProp_container);
		ll_addResProp_facility = (LinearLayout) findViewById(R.id.ll_addResProp_facility);
		ll_addResProp_priceOprion = (LinearLayout) findViewById(R.id.ll_addResProp_priceOprion);
		ll_addResProp_buildYear = (LinearLayout) findViewById(R.id.ll_addResProp_buildYear);
		ll_addResProp_zone = (LinearLayout) findViewById(R.id.ll_addResProp_zone);
		btn_addResProp_priceOption = (Button) findViewById(R.id.btn_addResProp_priceOption);
		btn_addResProp_trems = (Button) findViewById(R.id.btn_addResProp_trems);
		
		
		map_view = (MapView) findViewById(R.id.map_view);
	
		/*
		 * Check price is not start with zero if start with zero then show alert 
		 * Dastavej
		 * Transfer fees
		 * auda legal
		 * parking charge
		 */
		priceNotStartWithZero(edt_addResProp_Price,"Enter Proper price");
		priceNotStartWithZero(edt_addResProp_dastavej,"Enter Proper Dastavej price");
		priceNotStartWithZero(edt_addResProp_transFees,"Enter Proper Transfer Fees");
		priceNotStartWithZero(edt_addResProp_audaLegal,"Enter Proper Auda Legal price");
		priceNotStartWithZero(edt_addResProp_parCharge, "Enter Proper Parking charge");
		
		/*
		 * Set photo and total number of photo
		 */
		setPhotoAndNumberOfPhoto();
		
		spn_addResProp_state = (Spinner) findViewById(R.id.spn_addResProp_state);
		spn_addResProp_location = (Spinner) findViewById(R.id.spn_addResProp_location);
		spn_addResProp_city = (Spinner) findViewById(R.id.spn_addResProp_city);
		
		/*
		 * This is for hide keyboard when touch outside of screen 
		 */
		ll_addResProp_container.setOnTouchListener(this);
		
		/*
		 * Add property type view into layout dynamically
		 * On click of view change backgrond color
		 */
		if(propIntent.getString("From") == null) {
			ProjectUtility.sys(isPrint, TAG, "Add Proeprty type form add");
			ProjectUtility.addPropertyType(getActivity(), ll_addResProp_propType,Constant.PropertyType.residentialIconId,Constant.PropertyType.residentialTitle,"Residential",true,0);
			
			for (int i = 0; i < ll_addResProp_propType.getChildCount(); i++) {
				final ZPropertyTypeViewCommon layout = (ZPropertyTypeViewCommon)ll_addResProp_propType.getChildAt(i);
				final TextView tv_propType = (TextView) layout.findViewById(R.id.tv_propTypRow_name);
				layout.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ProjectUtility.changeBGColorOfPropTypeView(ll_addResProp_propType.indexOfChild(v), layout,false, ll_addResProp_propType);
						str_propType = z.com.pvo.util.SpinnerItem.getResidentialPropTypeVal().get(tv_propType.getText().toString()).toString();
						showHideFieldByPropType(tv_propType.getText().toString());
						//Toast.makeText(getActivity(), tv_propType.getText().toString()+"/Visible->"+z.com.pvo.util.SpinnerItem.getResidentialPropTypeVal().get(tv_propType.getText().toString()), Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
		
		
		/*
		 * Add bed, bath and floor view into layout
		 */
		bedView = ProjectUtility.addBedBathFloorView(getActivity(), R.drawable.bed,ll_addResProp_BedRoom);
		iv_bed_minus = (ImageView) bedView.findViewById(R.id.iv_bedBathFolorView_minusIcon);
		iv_bed_plus = (ImageView) bedView.findViewById(R.id.iv_bedBathFolorView_plusIcon);
		tv_bedRoom = (TextView) bedView.findViewById(R.id.tv_bedBathFolorView_number);
		tv_bedRoom.setText("1");
		//Minus bed
		iv_bed_minus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ProjectUtility.sys(isPrint, TAG, "bedCounter"+bedCounter);
				
				if(bedCounter > 1) {
					tv_bedRoom.setText(""+ --bedCounter);
				} else {
					tv_bedRoom.setText("1");
				}
			}
		});
		
		//Plus bed
		iv_bed_plus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(bedCounter < 12) {
					tv_bedRoom.setText(""+ ++bedCounter);
				}
					
			}
		});
		
		/*
		 * Bath room 
		 */
		bathView = ProjectUtility.addBedBathFloorView(getActivity(), R.drawable.bathroom,ll_addResProp_bathRoom);
		iv_bath_minus = (ImageView) bathView.findViewById(R.id.iv_bedBathFolorView_minusIcon);
		iv_bath_plus = (ImageView) bathView.findViewById(R.id.iv_bedBathFolorView_plusIcon);
		tv_bathRoom= (TextView) bathView.findViewById(R.id.tv_bedBathFolorView_number);
		tv_bathRoom.setText("0");
		//minus bath
		iv_bath_minus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(bathCounter > 0) {
					tv_bathRoom.setText(""+--bathCounter);
				} else {
					tv_bathRoom.setText("0");
				}
			}
		});
		
		//Plus bed
		iv_bath_plus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(bathCounter < 12) {
					tv_bathRoom.setText(""+ ++bathCounter);
				}
			}
		});
		
		floorView = ProjectUtility.addBedBathFloorView(getActivity(), R.drawable.floor,ll_addResProp_floor);
		iv_floor_minus = (ImageView) floorView.findViewById(R.id.iv_bedBathFolorView_minusIcon);
		iv_floor_plus = (ImageView) floorView.findViewById(R.id.iv_bedBathFolorView_plusIcon);
		tv_floor = (TextView) floorView.findViewById(R.id.tv_bedBathFolorView_number);
		tv_floor.setText("0");

		//minus floor
		iv_floor_minus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(floorCounter > 0) {
					tv_floor.setText(""+ --floorCounter);
				} else {
					tv_floor.setText("0");
				}
			}
		});
		
		//Plus floor
		iv_floor_plus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(floorCounter < 40) {
					tv_floor.setText(""+ ++floorCounter);
				}
			}
		});
		
		/*
		 * Add Furnish detail view dynamically
		 * With three option like fully furnish, Semi furnish, unfurnish
		 */
		ll_addResProp_furnishDet.addView(new ZFurnishDetailViewCommon(getActivity(), "Fully", "Semi", "Un"));
		ll_addResPropFurnishDetail = (LinearLayout) ll_addResProp_furnishDet.findViewById(R.id.ll_furnishDet_container); 
		tv_furnish_fully = (TextView) ll_addResPropFurnishDetail.findViewById(R.id.tv_furnishDet_fully);
		tv_furnish_semi = (TextView) ll_addResPropFurnishDetail.findViewById(R.id.tv_furnishDet_semi);
		tv_furnish_un = (TextView) ll_addResPropFurnishDetail.findViewById(R.id.tv_furnishDet_un);
		ll_addResPropFurnishDetail.setBackgroundResource(R.drawable.select1);
		
		/*
		 * Button nominee for open nominee dialog
		 * Button amenities for open amenities dialog 
		 * Button share with is for share property
		 */
		btn_addResProp_nominee = (Button) findViewById(R.id.btn_addResProp_nominee);
		btn_addResProp_amenities = (Button) findViewById(R.id.btn_addResProp_amenities);
		btn_addResProp_shreWith = (Button) findViewById(R.id.btn_addResProp_shreWith);
		
	}
	
	
	/*
	 * Initialize the fragment
	 */
	public static ZPropAddRecidentialFragment newInstance() {
		ZPropAddRecidentialFragment f = new ZPropAddRecidentialFragment();
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
		ProjectUtility.sys(isPrint, TAG,"---> Add Residential prop Activity on result <---");
		if (resultCode == getActivity().RESULT_OK) {
			if (requestCode == Constant.ZAddProperty.CAMERA_CAPTURE) {
				File file = new File(Environment.getExternalStorageDirectory()+File.separator + "PVO/img.jpg");
				if(file != null)
					doCrop(Uri.fromFile(file));
				
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
				doCrop(data.getData());
			} else if(requestCode == PIC_CROP) {
				ProjectUtility.storePhoto((Bitmap)data.getParcelableExtra("data"), Constant.ZAddProperty.RESIDENTIAL_PHOTO_PATH,"");
			}
			
			/*
			 * Get the all photo from directory
			 * and set photo adapter
			 */
			list_Of_photo_Path = ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.RESIDENTIAL_PHOTO_PATH);
			adpt_addResProp_photo = new ZAddPropPhotoAdapter(getActivity(), list_Of_photo_Path);
			grd_addResProp_photo.setAdapter(adpt_addResProp_photo);
			
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
			case R.id.iv_addResProp_photo:
				openPhotoDialog();
				break;
			case R.id.rl_addResProp_photoContainer:
				openPhotoDialog();
				break;
			//Open camera on camera button click from photo dialog
			case R.id.btn_addResProp_camera:
				if(ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.RESIDENTIAL_PHOTO_PATH).size() < 4) {
				    try{
				    	
				    	/* create an instance of intent
						 * pass action android.media.action.IMAGE_CAPTURE 
						 * as argument to launch camera
						 */
						Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
						/*create instance of File with name img.jpg*/
						//File file = new File(Environment.getExternalStorageDirectory()+File.separator + "PVO/img.jpg");
						File file = new File(Environment.getExternalStorageDirectory()+File.separator + "PVO");
						if(!file.exists())
							file.mkdir();
						
						File imgFile = new File(file, "img.jpg");
						
						/*put uri as extra in intent object*/
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
						/*start activity for result pass intent as argument and request code */
						startActivityForResult(intent, Constant.ZAddProperty.CAMERA_CAPTURE);
				    	
				    } catch(ActivityNotFoundException anfe){
				        Toast.makeText(getActivity(),"Whoops - your device doesn't support capturing images!", Toast.LENGTH_SHORT).show();
				    }
				} else {
					Toast.makeText(getActivity(), "You can add four photo only", Toast.LENGTH_SHORT).show();
				}
				break;
			//Open gallery on gallery button click from photo dialog	
			case R.id.btn_addResProp_gallery:
				if(ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.RESIDENTIAL_PHOTO_PATH).size() < 4) {
					Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, Constant.ZAddProperty.GALLERY_PHOTO);
				} else {
					Toast.makeText(getActivity(), "You can add four photo only", Toast.LENGTH_SHORT).show();
				}
				
				break;
			//Open nominee list dialog on nominee button click  	
			case R.id.btn_addResProp_nominee:
				nomineeListDialog.show();
				
				break;
			//Open aemenities dialog on amenities button click  
			case R.id.btn_addResProp_amenities:
				amenitiesListDialog.show();
				break;
			//Redirect to share with activity on click of share with button 
			case R.id.btn_addResProp_shreWith:
				redirectToShareWithActivity();
				break;
			//furnish detail fully
			case R.id.tv_furnishDet_fully:
				ll_addResPropFurnishDetail.setBackgroundResource(R.drawable.select1);
				str_furnish = "3";//Fully
				//Toast.makeText(getActivity(),tv_furnish_fully.getText(), Toast.LENGTH_SHORT).show();
				break;
			//furnish detail semi	
			case R.id.tv_furnishDet_semi:
				ll_addResPropFurnishDetail.setBackgroundResource(R.drawable.select2);
				str_furnish = "2";//Semi
				//Toast.makeText(getActivity(),tv_furnish_semi.getText(), Toast.LENGTH_SHORT).show();
				break;
			//furnish detail unfurnish
			case R.id.tv_furnishDet_un:
				ll_addResPropFurnishDetail.setBackgroundResource(R.drawable.select3);
				str_furnish = "0";//Un
				//Toast.makeText(getActivity(),tv_furnish_un.getText(), Toast.LENGTH_SHORT).show();
				break;
			//Building type toggle button
			case R.id.togl_addResProp_buildingType:
				//Toast.makeText(getActivity(),togl_addResProp_buildingType.getCurrentText(), Toast.LENGTH_SHORT).show();
				break;
			//Sale/Rent toggle button	
			case R.id.togl_addResProp_saleRent:
				//Toast.makeText(getActivity(),""+togl_addResProp_saleRent.isChecked(), 10).show();
				/*
				 * Visible more price option button if sale is selected
				 * else hide more option butotn 
				 */
				showhidePriceMoreOptionIcon();
				
				//Toast.makeText(getActivity(),togl_addResProp_saleRent.getCurrentText(), Toast.LENGTH_SHORT).show();
				break;
			//More price option show/hide on buton click 	
			case R.id.btn_addResProp_priceOption:
				/*
				 * Show hide price more option 
				 */
				showHidePriceMoreOptionLayout();
				break;
			//Open treams dialog	
			case R.id.btn_addResProp_trems:
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
				case R.id.spn_addResProp_state:
					 /*
					  * City response handler
					  * This method return the city json array and  
					  * set the city spinner
					  * 
					  * If call from edit then set default value 
					  */
					if(propIntent.getString("From") == null ) {
						StateCityLocationUtils.getCityByStateId(getActivity(), spn_addResProp_city,StateCityLocationUtils.getIdOfItem(position,Constant.StateCityLoation.FROM_STATE),"","");
					} else if(propIntent.getString("From").equals("Edit")) {
						StateCityLocationUtils.getCityByStateId(getActivity(), spn_addResProp_city,StateCityLocationUtils.getIdOfItem(position,Constant.StateCityLoation.FROM_STATE),propIntent.getString("From"),propertyDetailJsonObject.getString(Constant.EditProperty.CITY_NAME));
					}
					 break;
				//City
				case R.id.spn_addResProp_city:
					/*
					 * Location response handler
					 * This method return the location json array,
					 * Set the location spinner 
					 * 
					 * If call from edit then set default value
					 */
					if(propIntent.getString("From") == null) {  
						StateCityLocationUtils.getLocationByCityId(getActivity(), spn_addResProp_location, StateCityLocationUtils.getIdOfItem(position,Constant.StateCityLoation.FROM_CITY),"","");
					} else if(propIntent.getString("From").equals("Edit")) {
						StateCityLocationUtils.getLocationByCityId(getActivity(), spn_addResProp_location, StateCityLocationUtils.getIdOfItem(position,Constant.StateCityLoation.FROM_CITY),propIntent.getString("From"),propertyDetailJsonObject.getString(Constant.EditProperty.AREA_NAME));
					}
					break;
				//Location
				case R.id.spn_addResProp_location:
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
		
		btn_addResProp_camera = (Button) view.findViewById(R.id.btn_addResProp_camera);
		btn_addResProp_camera.setOnClickListener(this);
		btn_addResProp_gallery = (Button) view.findViewById(R.id.btn_addResProp_gallery);
		btn_addResProp_gallery.setOnClickListener(this);
		grd_addResProp_photo = (GridView) view.findViewById(R.id.grd_addResProp_photo);	
		
		/*
		 * Save and close photo dialog
		 */
		Button btn_photoDilog_ok = (Button) view.findViewById(R.id.btn_photoDilog_ok);
		btn_photoDilog_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setPhotoAndNumberOfPhoto();
				dialog.dismiss();
			}
		});
		
		/*
		 * Get all the photo from directory and set adapter
		 */
		list_Of_photo_Path = ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.RESIDENTIAL_PHOTO_PATH);
		adpt_addResProp_photo = new ZAddPropPhotoAdapter(getActivity(), list_Of_photo_Path);
		grd_addResProp_photo.setAdapter(adpt_addResProp_photo);
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
		if (googleMap == null) {
			googleMap = map_view.getMap();
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);
			
			if (googleMap != null) {
				googleMap.setMyLocationEnabled(true);
				
				MapsInitializer.initialize(this.getActivity()); 
				latLong = getCurrentLatLong().split("@");
					
				  if(latLong.length > 0) {
						  if(googleMarker != null)
								googleMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latLong[0]),Double.parseDouble(latLong[1]))));
							
								ProjectUtility.sys(isPrint, TAG, "Google Map-->"+googleMap);
									if(flag) {
										latitude = latLong[0];//String.valueOf(location.getLatitude());
										longitude = latLong[1];//String.valueOf(location.getLongitude());
									} else {
										try {
											ProjectUtility.sys(isPrint, TAG, "Google map from edit");
											latitude = propertyDetailJsonObject.getString(Constant.AddProperty.LATITUDE);
											longitude = propertyDetailJsonObject.getString(Constant.AddProperty.LONGITUDE);
											
										} catch (JSONException e) {
											e.printStackTrace();
										}
									}
								
								ProjectUtility.sys(isPrint, TAG, "latitude-> "+latitude);
								ProjectUtility.sys(isPrint, TAG, "longitude-> "+longitude);
								
								CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(latLong[0]),Double.parseDouble(latLong[1])));
								googleMap.moveCamera(center);
								CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
								googleMap.animateCamera(zoom);
				  }
				googleMap.setOnMapClickListener(new OnMapClickListener() {
					@Override
					public void onMapClick(LatLng latlng) {
						openGoogleMapDialog();
					}
				});
			}
		}
	}
		
	//Open google map dialog
	private void openGoogleMapDialog() {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.googlemap_activity, null);
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);
		final AlertDialog alertDialog = builder.create();
		alertDialog.setCancelable(false);
		alertDialog.show();	
		
		
		final GoogleMap googleMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.fgm_addProp_googleMap)).getMap();
		if (googleMap != null) {
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
				if(marker != null)
					marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())));
				
				CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude()));
				googleMap.moveCamera(center);
				CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
				googleMap.animateCamera(zoom);
			}
			
			googleMap.setOnMapClickListener(new OnMapClickListener() {
				@Override
				public void onMapClick(LatLng latlng) {
					if (marker != null) {
						marker.setPosition(latlng);
					} else {
						marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(latlng.latitude,latlng.longitude)));
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
		if (googleMarker != null) {
			googleMarker.setPosition(latlng);
		} else {
			googleMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(latlng.latitude,latlng.longitude)));
		}
		CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latlng.latitude, latlng.longitude));
		googleMap.moveCamera(center);
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
		googleMap.animateCamera(zoom);
	}
	//END
	
	//Remove google map fragment when click on done or cancle button
	private void removeGoogleMapFragment() {
		SupportMapFragment f = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fgm_addProp_googleMap);
		 if (f != null) 
			 getFragmentManager().beginTransaction().remove(f).commit();
	}
	
	/*
	 * Set last capture photo to image view and display total photo 
	 */
	private void setPhotoAndNumberOfPhoto() {
		int total = ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.RESIDENTIAL_PHOTO_PATH).size();
		if(total > 0) {
			tv_addResProp_counter.setText(""+total);
			iv_addResProp_topPhotoView.setBackgroundResource(Color.TRANSPARENT);
			Uri uri = Uri.fromFile(new File(ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.RESIDENTIAL_PHOTO_PATH).get(total-1)));
			Picasso.with(getActivity()).load(uri).into(iv_addResProp_topPhotoView);
		} else {
			iv_addResProp_topPhotoView.setImageBitmap(null);
			tv_addResProp_counter.setText("0");
			iv_addResProp_topPhotoView.setBackgroundResource(R.drawable.no_image);
		}
	}
	
	/*
	 * Check validation and redirect to share activity
	 */
	private void redirectToShareWithActivity() {
		
		if(checkEditboxValidation(edt_addResProp_address,"Enter Address") 
		   && checkSpinnerValidation(spn_addResProp_location, "Please select location")
		   && checkEditboxValidation(edt_addResProp_area,"Enter Area") 
		   && checkEditboxValidation(edt_addResProp_plotArea,"Enter Plot Area")
		   && checkEditboxValidation(edt_addResProp_Price,"Enter Price")
		   ) {
			
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
			
			intent.putExtra(Constant.AddProperty.STR_OPTIONS, getValue(togl_addResProp_saleRent));
			intent.putExtra(Constant.AddProperty.PURPOSE,Constant.Residential.RESIDENTIAL);
			intent.putExtra(Constant.AddProperty.PROPERTY_TYPE, str_propType);
			intent.putExtra(Constant.AddProperty.ADDRESS, getValue(edt_addResProp_address));
			intent.putExtra(Constant.AddProperty.STATE_ID,StateCityLocationUtils.getIdOfItem(spn_addResProp_state.getSelectedItemPosition(),Constant.StateCityLoation.FROM_STATE));
			intent.putExtra(Constant.AddProperty.CITY_ID,StateCityLocationUtils.getIdOfItem(spn_addResProp_city.getSelectedItemPosition(),Constant.StateCityLoation.FROM_CITY));
			intent.putExtra(Constant.AddProperty.LOCATION,StateCityLocationUtils.getIdOfItem(spn_addResProp_location.getSelectedItemPosition(),Constant.StateCityLoation.FROM_LOCATION));
			intent.putExtra(Constant.AddProperty.LATITUDE,latitude);
			intent.putExtra(Constant.AddProperty.LONGITUDE,longitude);
			intent.putExtra(Constant.AddProperty.BED,getValue(tv_bedRoom));
			intent.putExtra(Constant.AddProperty.BATH_ROOM,getValue(tv_bathRoom));
			intent.putExtra(Constant.AddProperty.FLOOR,getValue(tv_floor));
			intent.putExtra(Constant.AddProperty.FURNISH_STATUS,str_furnish);
			intent.putExtra(Constant.AddProperty.AREA_SQ_FIT,getValue(edt_addResProp_area));
			intent.putExtra(Constant.AddProperty.AREA_YARD,getValue(spn_addResProp_areaUnit));
			intent.putExtra(Constant.AddProperty.CONSTRUCTION_AREA,getValue(edt_addResProp_constArea));
			intent.putExtra(Constant.AddProperty.AREA_UNIT,getValue(spn_addResProp_constAreaUnit));
			intent.putExtra(Constant.AddProperty.PLOT_AREA,getValue(edt_addResProp_plotArea));
			intent.putExtra(Constant.AddProperty.PLOT_AREA_UNIT,getValue(spn_addResProp_plotAreaUnit));
			intent.putExtra(Constant.AddProperty.RISE,getValue(togl_addResProp_buildingType));
			intent.putExtra(Constant.AddProperty.PRICE,getValue(edt_addResProp_Price));
			intent.putExtra(Constant.AddProperty.DASTAWAGE,getValue(edt_addResProp_dastavej));
			intent.putExtra(Constant.AddProperty.TRANSFER_FEES,getValue(edt_addResProp_transFees));
			intent.putExtra(Constant.AddProperty.AEC_AUDA,getValue(edt_addResProp_audaLegal));
			intent.putExtra(Constant.AddProperty.PARKING,getValue(edt_addResProp_parCharge));
			intent.putExtra(Constant.AddProperty.YEAR_BUILD_UP,getValue(spn_addResProp_buildyear));
			intent.putExtra(Constant.AddProperty.ON_ROAD,getValue(spn_addResProp_roadTouch));
			intent.putExtra(Constant.AddProperty.COMMENTS,getValue(edt_addResProp_comment));
			intent.putExtra(Constant.AddProperty.CMBTP_SCHEME,ProjectUtility.getTPSchemeId());
			intent.putExtra(Constant.AddProperty.CHK_ZONE,getValue(spn_addResProp_zone));
			intent.putExtra(Constant.AddProperty.NA_STATUS,String.valueOf(SpinnerItem.getAddPropNaStatusList().get(getValue(spn_addResProp_na))));
			intent.putExtra(Constant.AddProperty.IMAGES, StringUtils.join(ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.RESIDENTIAL_PHOTO_PATH),","));
			
			intent.putExtra(Constant.AddProperty.NAVISHARAT, getTremValue(chk_trems_naviSharat));
			intent.putExtra(Constant.AddProperty.SHREE_SARKAR, getTremValue(chk_trems_juniSharat));
			intent.putExtra(Constant.AddProperty.KHETI, getTremValue(chk_trems_kheti));
			intent.putExtra(Constant.AddProperty.PRASSAP, getTremValue(chk_trems_prasha));
			intent.putExtra(Constant.AddProperty.DIS_PUTE, getTremValue(chk_trems_dispute));
			intent.putExtra(Constant.AddProperty.SHREE_SARKAR, getTremValue(chk_trems_shreeSarkar));
			//intent.putExtra("DoNotKnow", getTremValue(chk_trems_doNotKnow));
			
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.new_right_to_left_layout,R.anim.new_left_to_right_layout);
		}
	}
	/*
	 * Set edit box validation 
	 * Common for all
	 */
	private boolean checkEditboxValidation(EditText editText,String errorMsg) {
		if(editText != null && editText.isShown()) {
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
					//spinner.setFocusable(true);
					//spinner.performClick();
					spinner.setFocusableInTouchMode(true);
					spinner.requestFocus();
					spinner.setFocusableInTouchMode(false);
					spinner.clearFocus();
					ProjectUtility.showToast(getActivity(), spinner, errorMsg);
					return false;
				}
			} else {
				ProjectUtility.showToast(getActivity(), spinner, "Area not found");
				return false;
			}
		}
		
		return true;
	}

	@Override
	public void onResume() {
		map_view.onResume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		map_view.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		map_view.onLowMemory();
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
		if(propType.equals("Apartment")){
			ProjectUtility.sys(isPrint, TAG,"--> Apartment <---");
			ll_addResProp_BedRoom.setVisibility(View.VISIBLE);
			ll_addResProp_bathRoom.setVisibility(View.VISIBLE);
			ll_addResProp_floor.setVisibility(View.VISIBLE);
			ll_addResProp_furnishDet.setVisibility(View.VISIBLE);
			ll_addResProp_furnishDet.setVisibility(View.VISIBLE);
			ll_addResProp_buildingType.setVisibility(View.VISIBLE);
			btn_addResProp_amenities.setVisibility(View.VISIBLE);
			ll_addResProp_buildYear.setVisibility(View.VISIBLE);
			ll_addResProp_area.setVisibility(View.VISIBLE);
		}//House/Vill  
		else if(propType.equals("House/Villa")){
			ProjectUtility.sys(isPrint, TAG,"--> House/Villa <---");
			ll_addResProp_BedRoom.setVisibility(View.VISIBLE);
			ll_addResProp_bathRoom.setVisibility(View.VISIBLE);
			ll_addResProp_furnishDet.setVisibility(View.VISIBLE);
			btn_addResProp_amenities.setVisibility(View.VISIBLE);
			ll_addResProp_constArea.setVisibility(View.VISIBLE);
			ll_addResProp_buildYear.setVisibility(View.VISIBLE);
			ll_addResProp_area.setVisibility(View.VISIBLE);
		}//Plot/land / Agri./Farm Land
		else if(propType.equals("plot/Land") || propType.equals("Agri./Farm Land")) {
			ProjectUtility.sys(isPrint, TAG,"--> plot/Land <---");
			ll_addResProp_tpscheme.setVisibility(View.VISIBLE);
			ll_addResProp_zone.setVisibility(View.VISIBLE);
			ll_addResProp_na.setVisibility(View.VISIBLE);
			ll_addResProp_roadTouch.setVisibility(View.VISIBLE);
			btn_addResProp_trems.setVisibility(View.VISIBLE);
			ll_addResProp_constArea.setVisibility(View.VISIBLE);
			ll_addResProp_plotArea.setVisibility(View.VISIBLE);
		}
	}
	
	
	/*
	 * Hide Field when property type chenge
	 * Hide those field which not common in all porpery 
	 */
	private void hideFieldBeforeShow() {
		ll_addResProp_BedRoom.setVisibility(View.GONE);
		ll_addResProp_bathRoom.setVisibility(View.GONE);
		ll_addResProp_floor.setVisibility(View.GONE);
		ll_addResProp_furnishDet.setVisibility(View.GONE);
		ll_addResProp_buildingType.setVisibility(View.GONE);
		btn_addResProp_amenities.setVisibility(View.GONE);
		ll_addResProp_tpscheme.setVisibility(View.GONE);
		ll_addResProp_zone.setVisibility(View.GONE);
		ll_addResProp_na.setVisibility(View.GONE);
		ll_addResProp_constArea.setVisibility(View.GONE);
		ll_addResProp_roadTouch.setVisibility(View.GONE);
		btn_addResProp_trems.setVisibility(View.GONE);
		ll_addResProp_priceOprion.setVisibility(View.GONE);
		ll_addResProp_buildYear.setVisibility(View.GONE);
		ll_addResProp_plotArea.setVisibility(View.GONE);
		ll_addResProp_area.setVisibility(View.GONE);
	}
	
	/*
	 * Show price 
	 */
	private void showHidePriceMoreOptionLayout() {
		if(ll_addResProp_priceOprion.isShown()) {
			ll_addResProp_priceOprion.setVisibility(View.GONE);
			btn_addResProp_priceOption.setBackgroundResource(R.drawable.drop_down_2);
		} else {
			ll_addResProp_priceOprion.setVisibility(View.VISIBLE);
			btn_addResProp_priceOption.setBackgroundResource(R.drawable.drop_up);
		}
	}
	
	/*
	 * Price option bitton 
	 * Show hide show more option if sale and not equal to ploat
	 */
	private void showhidePriceMoreOptionIcon() {
		if(togl_addResProp_saleRent.getCurrentText().equals("Sale") && !str_propType.equals("plot/Land")) {
			btn_addResProp_priceOption.setVisibility(View.VISIBLE);
		} else {
			btn_addResProp_priceOption.setVisibility(View.GONE);
			ll_addResProp_priceOprion.setVisibility(View.GONE);
		}
	}
	
	
	
	/*
	 * Get the value of visible item
	 */
	private String getValue(View view) {
		if(view instanceof Spinner) {
			if(view.isShown()) {
				ProjectUtility.sys(isPrint, TAG, "ZONE--> "+((Spinner) view).getSelectedItem().toString());
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
						setDefaultToggleValue(togl_addResProp_saleRent,propertyDetailJsonObject.getString(Constant.AddProperty.STR_OPTIONS));
						/*
						 * If property is for sale then show more price option
						 */
						if(propertyDetailJsonObject.getString(Constant.AddProperty.STR_OPTIONS).equals("Sale")) {
							showhidePriceMoreOptionIcon();
							btn_addResProp_priceOption.performClick();
						}
						
						//2) Property type
						ProjectUtility.addPropertyType(getActivity(), ll_addResProp_propType,Constant.PropertyType.residentialIconId,Constant.PropertyType.residentialTitle,Constant.Residential.RESIDENTIAL,false,setDefaultPropertyType("Apartment"));
						
						//3) Address
						setDefaultEditBoxValue(edt_addResProp_address, propertyDetailJsonObject.getString(Constant.AddProperty.ADDRESS));
						
						//4) state(Spinner)
						StateCityLocationUtils.getStateListAll(getActivity(), spn_addResProp_state,propIntent.getString("From"),propertyDetailJsonObject.getString(Constant.EditProperty.STATE_NAME));
						
						//5) City(Spinner)
						
						//6) location(Spinner)
						
						//7) latlong on map(Google map)
						setUpMapIfNeeded(false);
						drawMarker(new LatLng(Double.parseDouble(propertyDetailJsonObject.getString(Constant.EditProperty.LATITUDE)), Double.parseDouble(propertyDetailJsonObject.getString(Constant.EditProperty.LONGITUDE))));
						
						//8) Bedroom (Text view)
						if(tv_bedRoom.isShown()) {
							bedCounter = Integer.parseInt(propertyDetailJsonObject.getString(Constant.EditProperty.BED));
							setDefaultTextBoxValue(tv_bedRoom, propertyDetailJsonObject.getString(Constant.EditProperty.BED));
						}
						
						//9) Bathroom (Text view)
						if(tv_bathRoom.isShown()) {
							bathCounter = Integer.parseInt(propertyDetailJsonObject.getString(Constant.EditProperty.BED));
							setDefaultTextBoxValue(tv_bathRoom, propertyDetailJsonObject.getString(Constant.EditProperty.BED));
						}
						
						//10) Floor (Text view)
						if(tv_floor.isShown()) {
							floorCounter = Integer.parseInt(propertyDetailJsonObject.getString(Constant.EditProperty.FLOOR)); 
							setDefaultTextBoxValue(tv_floor, propertyDetailJsonObject.getString(Constant.EditProperty.FLOOR));
						}
						
						//11) TP.Scheme (Spinner)
						if(spn_addResProp_tpscheme.isShown()) {
							setDefaultSpinnerValue(spn_addResProp_tpscheme,ProjectUtility.tpSchemeAdapter ,propertyDetailJsonObject.getString(Constant.EditProperty.CITY_NAME));
						}
						
						//12) Zone (Spinner)
						if(spn_addResProp_zone.isShown()) {
							setDefaultSpinnerValue(spn_addResProp_zone,zoneAdapter,propertyDetailJsonObject.getString(Constant.EditProperty.CITY_NAME));
						}
						
						//13) N.A status((Spinner))
						if(spn_addResProp_na.isShown())
							setDefaultSpinnerValue(spn_addResProp_na,naStatusAdapter,SpinnerItem.getAddPropNaStatusListKey(propertyDetailJsonObject.getString(Constant.AddProperty.NA_STATUS)));
						
						//14) Furnish option(Custome view)
						setDefaultSelFurnishStatus(propertyDetailJsonObject.getString(Constant.AddProperty.FURNISH_STATUS));
						
						//15) Area(Editbox) and area unit
						setDefaultEditBoxValue(edt_addResProp_area, propertyDetailJsonObject.getString(Constant.AddProperty.MIN_AREA));
						
						//16) Construction area
						setDefaultEditBoxValue(edt_addResProp_constArea, propertyDetailJsonObject.getString(Constant.AddProperty.CONSTRUCTION_AREA));
						
						//17) Building type ("Low Rise", "1"),("High Rise", "0")
						setDefaultToggleValue(togl_addResProp_buildingType,SpinnerItem.getBuildingTypeListKey(propertyDetailJsonObject.getString(Constant.AddProperty.RISE)));
						
						//18) Price
						ProjectUtility.sys(isPrint, TAG, "Price--> "+propertyDetailJsonObject.getString(Constant.AddProperty.PRICE));
						setDefaultEditBoxValue(edt_addResProp_Price, propertyDetailJsonObject.getString(Constant.AddProperty.PRICE));
						
						//19) Dastavej
						setDefaultEditBoxValue(edt_addResProp_dastavej,propertyDetailJsonObject.getString(Constant.AddProperty.DASTAWAGE));
						
						//20) Transfer fee
						setDefaultEditBoxValue(edt_addResProp_transFees, propertyDetailJsonObject.getString(Constant.AddProperty.TRANSFER_FEES));
						
						//21) AEC/Auda Legal
						setDefaultEditBoxValue(edt_addResProp_audaLegal, propertyDetailJsonObject.getString(Constant.AddProperty.AEC_AUDA));
						
						//22) Parking Charge
						setDefaultEditBoxValue(edt_addResProp_parCharge, propertyDetailJsonObject.getString(Constant.AddProperty.PARKING));
						
						//23) Build Year
						setDefaultSpinnerValue(spn_addResProp_buildyear,builtYearAdapter,propertyDetailJsonObject.getString(Constant.AddProperty.YEAR_BUILD_UP));
						
						//24) Road touch
						if(spn_addResProp_roadTouch.isShown())
							setDefaultSpinnerValue(spn_addResProp_roadTouch,onRoadAdapter,SpinnerItem.getOnRoadListKey(propertyDetailJsonObject.getString(Constant.AddProperty.ON_ROAD)));
						
						//25) Comment
						setDefaultEditBoxValue(edt_addResProp_comment, propertyDetailJsonObject.getString(Constant.AddProperty.COMMENTS));
						
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
						ZNomineeListResidentialAdaptor.setSelectedNominee(propertyDetailJsonObject.getJSONArray("nominee"));
						
						//28) Aemenities
						ZAminitiesResidentialAdaptor.setSelectdAmenities(propertyDetailJsonObject.getJSONArray("facilityid"));
						//28) Amenities URL
						ZAminitiesResidentialAdaptor.setAmenitiesUrl(propertyDetailJsonObject.getJSONArray("facilities"));
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
				ll_addResPropFurnishDetail.setBackgroundResource(R.drawable.select1);
				str_furnish = "3";
			} else if(status.equals("2")) {
				ll_addResPropFurnishDetail.setBackgroundResource(R.drawable.select2);
				str_furnish = "2";
			} else if(status.equals("0")) {
				ll_addResPropFurnishDetail.setBackgroundResource(R.drawable.select3);
				str_furnish = "0";
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
			if(imgLink.contains("_")) {
				String[] separated = imgLink.split("_");
				ProjectUtility.sys(isPrint, TAG, "Image path"+separated[1]);
				return separated[1];
			}
			return "";
		}
		
		/*
		 * Crop imag take by camera or gallery
		 */
		private void doCrop(Uri picUri) {
			try {
				if(picUri != null) {
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
				} else {
					Toast.makeText(getActivity(), "Whoops - your device doesn't support the crop action!", Toast.LENGTH_SHORT).show();;
				}
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
			if(ll_addResProp_facility != null)
				ll_addResProp_facility.removeAllViews();
			
			for (int i = 0; i < ZAminitiesResidentialAdaptor.getAmenitiesUrl().size(); i++) {
				ZPropDetFacilityView detFacilityView = new ZPropDetFacilityView(activity,ZAminitiesResidentialAdaptor.getAmenitiesUrl().get(i),"");//ZAminitiesResidentialAdaptor.getAmenitiesTitle().get(i));
				ll_addResProp_facility.addView(detFacilityView);
			}
		}
}
