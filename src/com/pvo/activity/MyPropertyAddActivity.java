package com.pvo.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pvo.components.DatePickerFragment;
import com.pvo.components.SpinnerItem;
import com.pvo.json.cache.CreateJsonArrayFileIntoCache;
import com.pvo.prototype.PVOFragment;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.AreaListOfCityService;
import com.pvo.user.service.BrokerAllService;
import com.pvo.user.service.CityListService;
import com.pvo.user.service.FindAgentService;
import com.pvo.user.service.GroupListService;
import com.pvo.user.service.GroupMemberListService;
import com.pvo.user.service.LandmarkAddService;
import com.pvo.user.service.MyPropertyAddService;
import com.pvo.user.service.NomineesListService;
import com.pvo.user.service.PreferredBrokersService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.pvo.util.JSONUtils;
import com.squareup.picasso.Picasso;

//Add new Property To Particular Broker Layout File:activity_add_my_property.xml
public class MyPropertyAddActivity extends PVOFragment {

	//Title Constant for Different Layout
	protected static final CharSequence PUBLIC_INFORMATION_HEADING = "Public Information";
	protected static final CharSequence NOMINEE_INFORMATION_HEADING = "Nominee";
	protected static final CharSequence PRICE_INFORMATION_HEADING = "Price / Budget Related";
	protected static final CharSequence EXTRA_INFORMATION_HEADING = "Extra Information";
	protected static final CharSequence BROKER_INFORMATION_HEADING = "Select Broker";
	protected static final CharSequence FACILITY_INFORMATION_HEADING = "Facility";
	protected static final CharSequence OPTION_INFORMATION_HEADING = "Option";
	protected static final CharSequence NOTES_INFORMATION_HEADING = "Notes";
	//Different Layout
	private RelativeLayout iconBarLayout;
	private RelativeLayout mainContainer;
	private LinearLayout addPropPublicInfoLayout;
	private LinearLayout addPropNomineeLayout;
	private LinearLayout addPropFaltLayout;
	private LinearLayout addPropRentLayout;
	private LinearLayout addPropExtraInfoLayout;
	private LinearLayout addPropSelectBrokLayout;
	private LinearLayout addPropFacilityLayout;
	private LinearLayout addPropPriceSaleLayout;
	//Public Infromation Controller
	private Spinner addPorpPublishingOptionSpinner;
	private Spinner addPropPropTypeSpinner;
	private TextView addPropPropType;
	private Spinner addPropOptionSpinner;
	private TextView addPropOption;
	private EditText addPropAddressEditBox;
	private TextView addPropAddress;
	private EditText addPropPostCodeEditBox;
	private Spinner addPropStateSpinner;
	private TextView addPropState;
	private Spinner addPropCitySpinner;
	private TextView addPropCity;
	private Spinner addPropLocationSpinner;
	private TextView addPropLocation;
	//************ Landmark *************
	//private Spinner addPropLandmark1Spinner;
	//private TextView addPropLandmark1;
	//private EditText addProplandmarkOtherEditbox;
	//private Spinner addPropLandmark2Spinner;
	//private TextView addPropLandmark2;
	//private EditText addPropLandmark2Editbox;
	//private JSONObject landmark1Object;
	//private JSONObject landmark2Object;
	
	private EditText addPropLongitudeEditBox;
	private EditText addPropLatitudeEditBox;
	private Spinner addPropPropOccupSpinner;
	private EditText addPropOccupNameEditBox;
	private TextView addPropOccupName;
	private EditText addPropOccupDetailEditBox;
	private EditText addPropReleseDateEditBox;

	//Nominee Information Controller
	private String nomineeFlag = "";
	private ListView nomineeListView;
	private ArrayAdapter<String> nomineeArrayAdapter;
	private List<String> nomineeNames = new ArrayList<String>();
	private Map<String, String> nomineeMap;
	private String bestProvider;

	//Property (Flat) Related 
	private LinearLayout addPropBedLayout;
	private LinearLayout addPropTPSchemeLayout;
	private LinearLayout addPropFloorLayout;
	private LinearLayout addPropFurnishLayout;
	private LinearLayout addPropAreaLayout;
	private LinearLayout addPropPlotAreaLayout;
	private LinearLayout addPropConstructionLayout;
	private LinearLayout addPropBuildingTypeLayout;
	private LinearLayout addPropSellingPriceLayout;
	private LinearLayout addPropTotalPriceLyout;
	private LinearLayout addpropDastawage;
	private LinearLayout addpropFlatImageLayout;
	private Spinner addPropFlatBedsSpinner;
	private TextView addPropFlatBeds;
	private Spinner addPropFlatFurnishOptionSpinner;
	private EditText addPropFlatFurnishDetailEditBox;
	private Spinner addPropFlatFloorSpinner;
	private Spinner addPropFlatBuildingTypeSpinner;
	private EditText addPropFlatAreaEditBox;
	private Button addPropFlatDefaultImgButton;
	private Button addPropFlatImage2Button;
	private Button addPropFlatImage3Button;
	private Button addPropFlatImage4Button;
	private Button addPropFlatImage5Button;
	private ImageView addPropImage1ImageView;
	private ImageView addPropImage2ImageView;
	private ImageView addPropImage3ImageView;
	private ImageView addPropImage4ImageView;
	private ImageView addPropImage5ImageView;
	private ImageView addPropExtraInfoImageView;
	private EditText addPropFlatPlotAreaEditBox;
	private TextView addPropFlatConstructionArea;
	private EditText addPropFlatConstructionAreaEditBox;
	private Spinner addPropTPSchemeSpinner;
	private Spinner addPropNASpinner;
	private EditText addPropPriceTransferFeeseSaleEditBox;
	private EditText addPropPriceParkingChargesSaleEditBox;
	private EditText addPropPriceAECAudaLegalSaleEditBox;
	private TextView addPropFlatAreaHintTv;
	private TextView addPropFlatPlotAreaHintTv;
	private TextView addPropFlatConstructionAreaHintTv;

	//TP Scheme Web Srvice 
	private String tpSchemeId;

	//CheckBox For Zone Layout 
	private Spinner addPropFlatZoneSpinner;

	//Price /Budget Related 
	private EditText addPropPriceMaintenanceEditBox;
	private EditText addPropPriceRentEditBox;
	private EditText addPropPriceRentDepositeEditBox;
	private TextView addPropPriceRent;
	private EditText addPropFlatSellPriceEditBox;
	private EditText addPropFlatTotalPriceEditBox;
	private TextView addPropFlatTatalPrice;
	private EditText addPropFlatDastawageEditBox;
	private LinearLayout addPropPriceRentLayout;
	private LinearLayout addPropPriceRentDepositeLayout;
	private LinearLayout addPropPriceRentMaintenanceLayout;

	//Extra Information Controller 
	private Spinner addPropExtraInfoPurposeSpinner;
	private Spinner addPropExtraInfoWhomToLetSpinner;
	private EditText addPropExtraInfoWhomeToletOther;
	private Spinner addPropExtraInfoBuiltYearSpinner;
	private EditText addPropExtraInfoCommentEditBox;
	private EditText addPropExtraInfoHint;
	private Spinner addPropExtraInfoFramHouseRoadTouchSpinner;
	private LinearLayout addPropExtraInfoPurposeLayout;
	private LinearLayout addPropExtraInfoWhomeToLayout;
	private LinearLayout addPropExtraInfoBuilrYearLayout;
	private LinearLayout addPropExtraInfoCommentLayout;
	private LinearLayout addPropExtraInfoTermLayout;
	private LinearLayout addPropExtraInfoHintKeyowrdLayout;
	private LinearLayout addPropExtraInfoKeyowrdLayout;
	private LinearLayout addPropImageButtonLayout;
	private Button addPropFlatFarmImageButton;

	//Select Broker
	private PreferredBrokersService preferredBrokersService;
	private GroupListService groupListService;
	private ListView brokerListview;
	private ArrayAdapter<String> brokerArrayAdapter;
	private List<String> brokerNames = new ArrayList<String>();
	private Map<String, Integer> brokerMap;
	private String selectedBrokerId = "";
	private Map<String, String> groupMemberMap;
	private List<String> groupMemberNames = new ArrayList<String>();
	private String checked = "";
	private String groupMemberChecked = "";
	private String groupMemberBrokerId = "";

	//Facility
	private CheckBox facilityCheck;
	private ArrayList<Integer> facilityIdArray;
	private String facilityFlag = "";

	//Term Checkbox
	private CheckBox addPropTermsNaviSharatCheck;
	private CheckBox addPropTermsJuniSharatCheck;
	private CheckBox addPropTermsKhetiCheck;
	private CheckBox addPropTermsPRASHACheck;
	private CheckBox addPropTermsDisputeCheck;
	private CheckBox addPropTermsShreeSarkarCheck;

	//Button Save,Next,Previous,cancel This button is used to move the next layout or previous
	private Button addPropSaveButton;
	private Button addPropNextButton;
	private Button addPropPreviousButton;
	private Button addPropCancleButton;

	//Google Map to display the map and set the property Latitude and Longitude
	private GoogleMap googleMap;
	private Marker googleMarker;
	private Marker marker;
	private JSONArray stateResponse;
	private String latitude;
	private String longitude;
	//private Fragment mapFragment;

	//City List Web Service 
	private CityListService cityListService;
	private JSONArray cityResponse;
	private ArrayAdapter<String> cityArrayAdapter;

	//Landmark 
	//private JSONArray landmark1Response;
	private ArrayAdapter<String> landmarkAdapter;

	//Nominee List Web Service 
	private NomineesListService nomineesListService;
	private String nomineeId;

	//AddProperty service
	private MyPropertyAddService addPropertyService;

	//Area List Of City Web Service
	private AreaListOfCityService areaListOfCityService;
	private JSONArray areaOfCityResponse;
	private ArrayAdapter<String> locationAdapter;

	//Plot And Bunglow Type
	private String plotType;
	private String bunglowType;

	//Get Stored User Id From User SessionManager 
	private UserSessionManager userSessionManager;

	//this is used set the tab Index of the Current Layout and Image Icon
	private int activeTabIndex = 0;

	//This is used for Image Selection 
	/*private String defaultImageValue;
	private String image2Value;
	private String image3Value;
	private String image4Value;
	private String image5Value;
	private String extraInfoImageValue;*/
	private int image1_button_click;
	private int image2_button_click;
	private int image3_button_click;
	private int image4_button_click;
	private int image5_button_click;
	private int extraInfoImageButtonClick;
	private TextView addPropRemoveImage1Tv;
	private TextView addPropRemoveImage2Tv;
	private TextView addPropRemoveImage3Tv;
	private TextView addPropRemoveImage4Tv;
	private TextView addPropRemoveImage5Tv;
	private TextView addPropRemoveExtraImgTv;
	//private Button addPropAddLandmarkImgButton;
	private JSONObject stateObject;
	private JSONObject cityObject;
	private JSONObject areaOfCityObject;

	//set flag for pagination
	private boolean flag_loading;
	private int pageCount = 1;
	private String brokerListOf = "";

	//Add new landmark service
	private LandmarkAddService addNewLandmarkService;
	//private Set<Integer> selectedBrokerIds = new HashSet<Integer>();
	
	//private File imageFile;
	private File myDir;
	//private ArrayList<HashMap<String, String>> imageNameMap;
	private HashMap<String, String> imageNameMap;
	private final String PROPERTY_IMAGE = "/pvo_property_images";

	//set layout content view
	public MyPropertyAddActivity() {
		setContentView(R.layout.activity_myproperty_add);
	}

	//init method
	@Override
	public void init(Bundle savedInstanceState) {
		
		myDir = new File(Environment.getExternalStorageDirectory() + PROPERTY_IMAGE);
        //myDir.mkdirs();
		deleteScropImageDirectory();
		imageNameMap = new HashMap<String, String>();
		storeScropImage(BitmapFactory.decodeResource(getResources(), R.drawable.no_image),"NoImage.jpeg");
        
		addPropertyService = new MyPropertyAddService();
		cityListService = new CityListService();
		areaListOfCityService = new AreaListOfCityService();
		nomineesListService = new NomineesListService();
		preferredBrokersService = new PreferredBrokersService();
		groupListService = new GroupListService();
		
		//areaListOfCityService = new AreaListOfCityService();

		//Create new Broker HashMap used to get broker id of checked Broker
		brokerMap = new HashMap<String, Integer>();
		groupMemberMap = new HashMap<String, String>();
		nomineeMap = new HashMap<String, String>();

		// This method Used to set google map
		setUpMapIfNeeded();

		//get Login User_Id from stored Session 
		userSessionManager = new UserSessionManager(getActivity());

		//Different Layout of Add property
		addPropNomineeLayout = (LinearLayout) findViewById(R.id.addPropNomineeLayout);
		nomineeListView = (ListView) findViewById(R.id.nomineeListView);
		addPropExtraInfoLayout = (LinearLayout) findViewById(R.id.addPropExtraInfoLayout);
		addPropSelectBrokLayout = (LinearLayout) findViewById(R.id.addPropSelectBrokLayout);
		addPropFacilityLayout = (LinearLayout) findViewById(R.id.addPropFacilityLayout);
		addPropPriceSaleLayout = (LinearLayout) findViewById(R.id.addPropPriceSaleLayout);
		mainContainer = (RelativeLayout) findViewById(R.id.mainContainer);

		//Property (Flat) Related Information Controller ID Sale/Rent
		addPropFaltLayout = (LinearLayout) findViewById(R.id.addPropFaltLayout);
		addPropBedLayout = (LinearLayout) findViewById(R.id.addPropBedLayout);
		addPropTPSchemeLayout = (LinearLayout) findViewById(R.id.addPropTPSchemeLayout);
		addPropFloorLayout = (LinearLayout) findViewById(R.id.addPropFloorLayout);
		addPropFurnishLayout = (LinearLayout) findViewById(R.id.addPropFurnishLayout);
		addPropAreaLayout = (LinearLayout) findViewById(R.id.addPropAreaLayout);
		addPropPlotAreaLayout = (LinearLayout) findViewById(R.id.addPropPlotAreaLayout);
		addPropConstructionLayout = (LinearLayout) findViewById(R.id.addPropConstructionLayout);
		addPropBuildingTypeLayout = (LinearLayout) findViewById(R.id.addPropBuildingTypeLayout);
		addPropSellingPriceLayout = (LinearLayout) findViewById(R.id.addPropSellingPriceLayout);
		addPropTotalPriceLyout = (LinearLayout) findViewById(R.id.addPropTotalPriceLyout);
		addpropDastawage = (LinearLayout) findViewById(R.id.addpropDastawage);
		addpropFlatImageLayout = (LinearLayout) findViewById(R.id.addpropFlatImageLayout);
		addPropPriceTransferFeeseSaleEditBox = (EditText) findViewById(R.id.addPropPriceTransferFeeseSaleEditBox);
		addPropPriceAECAudaLegalSaleEditBox = (EditText) findViewById(R.id.addPropPriceAECAudaLegalSaleEditBox);
		addPropFlatFurnishDetailEditBox = (EditText) findViewById(R.id.addPropFlatFurnishDetailEditBox);
		addPropFlatAreaEditBox = (EditText) findViewById(R.id.addPropFlatAreaEditBox);
		addPropFlatPlotAreaEditBox = (EditText) findViewById(R.id.addPropFlatPlotAreaEditBox);
		addPropFlatConstructionArea = (TextView) findViewById(R.id.addPropFlatConstructionArea);
		addPropFlatConstructionAreaEditBox = (EditText) findViewById(R.id.addPropFlatConstructionAreaEditBox);
		addPropFlatSellPriceEditBox = (EditText) findViewById(R.id.addPropFlatSellPriceEditBox);
		addPropFlatTotalPriceEditBox = (EditText) findViewById(R.id.addPropFlatTatalPriceEditBox);
		addPropFlatTatalPrice = (TextView) findViewById(R.id.addPropFlatTatalPrice);
		addPropFlatDastawageEditBox = (EditText) findViewById(R.id.addPropFlatDastawageEditBox);
		addPropFlatDefaultImgButton = (Button) findViewById(R.id.addPropFlatDefaultImgButton);
		addPropFlatImage2Button = (Button) findViewById(R.id.addPropFlatImage2Button);
		addPropFlatImage3Button = (Button) findViewById(R.id.addPropFlatImage3Button);
		addPropFlatImage4Button = (Button) findViewById(R.id.addPropFlatImage4Button);
		addPropFlatImage5Button = (Button) findViewById(R.id.addPropFlatImage5Button);
		addPropImage1ImageView = (ImageView) findViewById(R.id.addPropImage1ImageView);
		addPropImage2ImageView = (ImageView) findViewById(R.id.addPropImage2ImageView);
		addPropImage3ImageView = (ImageView) findViewById(R.id.addPropImage3ImageView);
		addPropImage4ImageView = (ImageView) findViewById(R.id.addPropImage4ImageView);
		addPropImage5ImageView = (ImageView) findViewById(R.id.addPropImage5ImageView);
		addPropExtraInfoImageView = (ImageView) findViewById(R.id.addPropExtraInfoImageView);
		addPropRemoveImage1Tv = (TextView) findViewById(R.id.addPropFlatDefaultImgPath);
		addPropRemoveImage2Tv = (TextView) findViewById(R.id.addPropFlatImage2Path);
		addPropRemoveImage3Tv = (TextView) findViewById(R.id.addPropFlatImage3Path);
		addPropRemoveImage4Tv = (TextView) findViewById(R.id.addPropFlatImage4Path);
		addPropRemoveImage5Tv = (TextView) findViewById(R.id.addPropFlatImage5Path);
		addPropRemoveExtraImgTv = (TextView) findViewById(R.id.addPropExtraInfoImgPath);
		addPropTPSchemeSpinner = (Spinner) findViewById(R.id.addPropTPSchemeSpinner);
		addPropFlatAreaHintTv = (TextView) findViewById(R.id.addPropFlatAreaHintTv);
		addPropFlatPlotAreaHintTv = (TextView) findViewById(R.id.addPropFlatPlotAreaHintTv);
		addPropFlatConstructionAreaHintTv = (TextView) findViewById(R.id.addPropFlatConstructionAreaHintTv);
		addPropAddressEditBox = (EditText) findViewById(R.id.addPropAddressEditBox);
		addPropAddress = (TextView) findViewById(R.id.addPropAddress);
		addPropAddress.setText(Html.fromHtml("Address" + "<sup><font size=5 color=red>*</font></sup>"));
		addPropPostCodeEditBox = (EditText) findViewById(R.id.addPropPostCodeEditBox);
		addPropStateSpinner = (Spinner) findViewById(R.id.addPropStateSpinner);
		addPropState = (TextView) findViewById(R.id.addPropState);
		addPropState.setText(Html.fromHtml("State" + "<sup><font size=5 color=red>*</font></sup>"));
		addPropCitySpinner = (Spinner) findViewById(R.id.addPropCitySpinner);
		addPropCity = (TextView) findViewById(R.id.addPropCity);
		addPropCity.setText(Html.fromHtml("City" + "<sup><font size=5 color=red>*</font></sup>"));
		addPropLocationSpinner = (Spinner) findViewById(R.id.addPropLocationSpinner);
		addPropLocation = (TextView) findViewById(R.id.addPropLocation);
		addPropLocation.setText(Html.fromHtml("Location" + "<sup><font size=5 color=red>*</font></sup>"));
		addPropLongitudeEditBox = (EditText) findViewById(R.id.addPropLongitudeEditBox);
		addPropLatitudeEditBox = (EditText) findViewById(R.id.addPropLatitudeEditBox);
		addPropOccupNameEditBox = (EditText) findViewById(R.id.addPropOccupNameEditBox);
		addPropOccupName = (TextView) findViewById(R.id.addPropOccupName);
		addPropOccupDetailEditBox = (EditText) findViewById(R.id.addPropOccupDetailEditBox);
		addPropReleseDateEditBox = (EditText) findViewById(R.id.addPropReleseDateEditBox);
		
		//************* Landmark ************************
		/*addPropLandmark1Spinner = (Spinner) findViewById(R.id.addPropLandmark1Spinner);
		addPropLandmark1 = (TextView) findViewById(R.id.addPropLandmark1);
		addPropLandmark1.setText("Landmark1");
		addPropLandmark2Spinner = (Spinner) findViewById(R.id.addPropLandmark2Spinner);
		addPropLandmark2 = (TextView) findViewById(R.id.addPropLandmark2);
		addPropLandmark2.setText("Landmark2");
		addProplandmarkOtherEditbox = (EditText) findViewById(R.id.addProplandmarkOtherEditbox);
		addPropLandmark2Editbox = (EditText) findViewById(R.id.addPropLandmark2Editbox);*/
		
		//on click of add landmark button open the popup window
		/*addPropAddLandmarkImgButton = (Button) findViewById(R.id.addPropAddLandmarkImgButton);
		addPropAddLandmarkImgButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addNewLandmarkDialogBox();
			}
		});*/
		//END
		
		//Remove default image
		addPropRemoveImage1Tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setDefauleImage(addPropImage1ImageView);
				addPropRemoveImage1Tv.setVisibility(View.GONE);
			}
		});
		//Remove First image
		addPropRemoveImage2Tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setDefauleImage(addPropImage2ImageView);
				addPropRemoveImage2Tv.setVisibility(View.GONE);
			}
		});
		//Remove Second image
		addPropRemoveImage3Tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setDefauleImage(addPropImage3ImageView);
				addPropRemoveImage3Tv.setVisibility(View.GONE);
			}
		});
		//Remove Third image
		addPropRemoveImage4Tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setDefauleImage(addPropImage4ImageView);
				addPropRemoveImage4Tv.setVisibility(View.GONE);
			}
		});
		//Remove fourth image
		addPropRemoveImage5Tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setDefauleImage(addPropImage5ImageView);
				addPropRemoveImage5Tv.setVisibility(View.GONE);
			}
		});
		
		//Remove extra image
		addPropRemoveExtraImgTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setDefauleImage(addPropExtraInfoImageView);
				addPropRemoveExtraImgTv.setVisibility(View.GONE);
			}
		});
		addPropFlatFarmImageButton = (Button) findViewById(R.id.addPropFlatFarmImageButton);
		//Public Information Controller ID
		addPropPublicInfoLayout = (LinearLayout) findViewById(R.id.addPropPublicInfoLayout);
		addPropPropTypeSpinner = (Spinner) findViewById(R.id.addPropPropTypeSpinner);
		addPropPropType = (TextView) findViewById(R.id.addPropPropType);
		addPropPropType.setText(Html.fromHtml("Property Type" + "<sup><font size=5 color=red>*</font></sup>"));
		
		//call the getStateList method fist time otherwise set adapter as previous Loaded
		getStateList();

		//Fill The Publish Option spinner Using HashMap 1) Public Only 2) Preferred Only 3) Both
		addPorpPublishingOptionSpinner = (Spinner) findViewById(R.id.addPorpPublishingOptionSpinner);
		ArrayList<String> publishingOptionList = new ArrayList<String>();
		publishingOptionList.addAll(SpinnerItem.getPublishinOptionList().keySet());
		ArrayAdapter<String> publishingOptionAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text,publishingOptionList);
		publishingOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
		addPorpPublishingOptionSpinner.setAdapter(publishingOptionAdapter);
		//End

		// Fill the Property Type Spinner Using hash Map Flat,Shop,bunglow,Plot set group for flat,shop,bunglow,plot
		addPropPropTypeSpinner = (Spinner) findViewById(R.id.addPropPropTypeSpinner);
		final ArrayList<String> propertyTypeOptionList = new ArrayList<String>();
		propertyTypeOptionList.addAll(SpinnerItem.getPropertyTypeOptionList().keySet());
		//set read only item for below number
		ArrayAdapter<String> prpoertyTypeOptionAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text,propertyTypeOptionList) {
			@Override
			public boolean isEnabled(int position) {
				if (position == 1) 
					return false;
				if (position == 3) 
					return false;
				if (position == 13) 
					return false;
				if (position == 21) 
					return false;
				return true;
			}

			@Override
			public boolean areAllItemsEnabled() {
				return false;
			}

			//set the color of read only item
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
					case 13:
						tv.setTextColor(Color.GRAY);
						tv.setTypeface(null, Typeface.ITALIC);
						break;
					case 21:
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
		prpoertyTypeOptionAdapter.remove("All Shop");
		prpoertyTypeOptionAdapter.remove("All Bunglow");
		prpoertyTypeOptionAdapter.remove("All Plots");
		addPropPropTypeSpinner.setAdapter(prpoertyTypeOptionAdapter);
		//addPropPropTypeSpinner.setSelection(1);//
		//End

		//Set spinner Sale/Rent using has map 1) Sale 2) Rent
		addPropOption = (TextView) findViewById(R.id.addPropOption);
		addPropOption.setText(Html.fromHtml("Options" + "<sup><font size=5 color=red>*</font></sup>"));
		addPropOptionSpinner = (Spinner) findViewById(R.id.addPropOptionSpinner);
		ArrayList<String> addMuPropSaleRentOptionList = new ArrayList<String>();
		addMuPropSaleRentOptionList.addAll(SpinnerItem.getSaleRentList().keySet());
		ArrayAdapter<String> addMyPropSaleRentOptionAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text, addMuPropSaleRentOptionList);
		addMyPropSaleRentOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
		addPropOptionSpinner.setAdapter(addMyPropSaleRentOptionAdapter);
		//END

		//Fill the Property Occupacy spinner Using HashMap 1)Select 2)Yes 3)No
		addPropPropOccupSpinner = (Spinner) findViewById(R.id.addPropPropOccupSpinner);
		ArrayList<String> propertyOccupacyList = new ArrayList<String>();
		propertyOccupacyList.addAll(SpinnerItem.getPropertyOccupacyList().keySet());
		ArrayAdapter<String> propertyOccupacyAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text,propertyOccupacyList);
		propertyOccupacyAdapter.setDropDownViewResource(R.layout.spinner_text);
		addPropPropOccupSpinner.setAdapter(propertyOccupacyAdapter);
		addPropPropOccupSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (addPropPropOccupSpinner.getSelectedItem().equals("Yes")) {
					addPropOccupNameEditBox.setVisibility(View.VISIBLE);
					addPropOccupName.setVisibility(View.VISIBLE);
				} else {
					addPropOccupNameEditBox.setVisibility(View.GONE);
					addPropOccupName.setVisibility(View.GONE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		//End

		//Term Layout Check Box 1)Navi Sharat 2)Juni sharat 3)Kheti 4)PRASHA 5)Dispute 6)Shree Sarkar
		addPropTermsNaviSharatCheck = (CheckBox) findViewById(R.id.addPropTermsNaviSharatCheck);
		addPropTermsJuniSharatCheck = (CheckBox) findViewById(R.id.addPropTermsJuniSharatCheck);
		addPropTermsKhetiCheck = (CheckBox) findViewById(R.id.addPropTermsKhetiCheck);
		addPropTermsPRASHACheck = (CheckBox) findViewById(R.id.addPropTermsPRASHACheck);
		addPropTermsDisputeCheck = (CheckBox) findViewById(R.id.addPropTermsDisputeCheck);
		addPropTermsShreeSarkarCheck = (CheckBox) findViewById(R.id.addPropTermsShreeSarkarCheck);

		//Fill the On Road Touch Spinner Using Hash Map
		addPropExtraInfoFramHouseRoadTouchSpinner = (Spinner) findViewById(R.id.addPropExtraInfoFramHouseRoadTouchSpinner);
		ArrayList<String> onRoadList = new ArrayList<String>();
		onRoadList.addAll(SpinnerItem.getOnRoadList().keySet());
		ArrayAdapter<String> onRoadAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, onRoadList);
		onRoadAdapter.setDropDownViewResource(R.layout.spinner_text);
		addPropExtraInfoFramHouseRoadTouchSpinner.setAdapter(onRoadAdapter);
		//End

		//N.A Layout fill the spinner using the Hash map
		addPropNASpinner = (Spinner) findViewById(R.id.addPropNASpinner);
		ArrayList<String> naStatusList = new ArrayList<String>();
		naStatusList.addAll(SpinnerItem.getAddPropNaStatusList().keySet());
		ArrayAdapter<String> naStatusAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text,naStatusList);
		naStatusAdapter.setDropDownViewResource(R.layout.spinner_text);
		addPropNASpinner.setAdapter(naStatusAdapter);
		//End

		//Zone Layout Add check box into Layout
		addPropFlatZoneSpinner = (Spinner) findViewById(R.id.addPropFlatZoneSpinner);
		ArrayList<String> zoneList = new ArrayList<String>();
		zoneList.addAll(SpinnerItem.getAddPropZoneList().keySet());
		ArrayAdapter<String> zoneAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, zoneList);
		zoneAdapter.setDropDownViewResource(R.layout.spinner_text);
		addPropFlatZoneSpinner.setAdapter(zoneAdapter);
		//End

		//Fill the Bed Spinner using Hash Map
		addPropFlatBeds = (TextView) findViewById(R.id.addPropFlatBeds);
		addPropFlatBeds.setText(Html.fromHtml("Beds" + "<sup><font size=5 color=red>*</font></sup>"));
		addPropFlatBedsSpinner = (Spinner) findViewById(R.id.addPropFlatBedsSpinner);
		ArrayList<String> bedList = new ArrayList<String>();
		bedList.addAll(SpinnerItem.getAddPropBedList().keySet());
		ArrayAdapter<String> bedAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, bedList);
		bedAdapter.setDropDownViewResource(R.layout.spinner_text);
		addPropFlatBedsSpinner.setAdapter(bedAdapter);
		//END BED

		//Fill the Furnish Option spinner Using Hash map
		addPropFlatFurnishOptionSpinner = (Spinner) findViewById(R.id.addPropFlatFurnishOptionSpinner);
		ArrayList<String> furnishOptionList = new ArrayList<String>();
		furnishOptionList.addAll(SpinnerItem.getAddPropFurnishOptionList().keySet());
		ArrayAdapter<String> furnishOptionAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text,furnishOptionList);
		furnishOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
		addPropFlatFurnishOptionSpinner.setAdapter(furnishOptionAdapter);
		//End

		//Fill the Floor spinner Using Hash map
		addPropFlatFloorSpinner = (Spinner) findViewById(R.id.addPropFlatFloorSpinner);
		ArrayList<String> floorList = new ArrayList<String>();
		floorList.addAll(SpinnerItem.getAddPropFloorList().keySet());
		ArrayAdapter<String> floorAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, floorList);
		floorAdapter.setDropDownViewResource(R.layout.spinner_text);
		addPropFlatFloorSpinner.setAdapter(floorAdapter);
		// Set Default selected "Ground Floor"
		addPropFlatFloorSpinner.setSelection(2);
		//END

		//Fill The Building Type spinner Using Hash Map 1)Low Rise 2)High Rise
		addPropFlatBuildingTypeSpinner = (Spinner) findViewById(R.id.addPropFlatBuildingTypeSpinner);
		ArrayList<String> buildingTypeList = new ArrayList<String>();
		buildingTypeList.addAll(SpinnerItem.getAddPropBuildingTypeList().keySet());
		ArrayAdapter<String> buildingTypeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text,buildingTypeList);
		buildingTypeAdapter.setDropDownViewResource(R.layout.spinner_text);
		addPropFlatBuildingTypeSpinner.setAdapter(buildingTypeAdapter);
		//End

		//Price/Budget Related Information Controller ID
		addPropRentLayout = (LinearLayout) findViewById(R.id.addPropPriceLayout);
		addPropPriceMaintenanceEditBox = (EditText) findViewById(R.id.addPropPriceMaintenanceEditBox);
		addPropPriceRentEditBox = (EditText) findViewById(R.id.addPropPriceRentEditBox);
		addPropPriceRentDepositeEditBox = (EditText) findViewById(R.id.addPropPriceRentDepositeEditBox);
		addPropPriceRent = (TextView) findViewById(R.id.addPropPriceRent);
		addPropPriceRentLayout = (LinearLayout) findViewById(R.id.addPropPriceRentLayout);
		addPropPriceRentDepositeLayout = (LinearLayout) findViewById(R.id.addPropPriceRentDepositeLayout);
		addPropPriceRentMaintenanceLayout = (LinearLayout) findViewById(R.id.addPropPriceRentMaintenanceLayout);
		addPropPriceParkingChargesSaleEditBox = (EditText) findViewById(R.id.addPropPriceParkingChargesSaleEditBox);
		
		//Set the Text View Text According to the Spinner item Selected 1)Total Price For Sale 2)Rent For Rent
		addPropOptionSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (addPropOptionSpinner.getSelectedItem().equals("Sale"))
					addPropFlatTatalPrice.setText(Html.fromHtml("Total Price"+ "<sup><font size=5 color=red>*</font></sup>"));
				else
					addPropPriceRent.setText(Html.fromHtml("Rent" + "<sup><font size=5 color=red>*</font></sup>"));
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});

		//Extra Information Controller ID
		addPropExtraInfoPurposeLayout = (LinearLayout) findViewById(R.id.addPropExtraInfoPurposeLayout);
		addPropExtraInfoWhomeToLayout = (LinearLayout) findViewById(R.id.addPropExtraInfoWhomeToLayout);
		addPropExtraInfoBuilrYearLayout = (LinearLayout) findViewById(R.id.addPropExtraInfoBuilrYearLayout);
		addPropExtraInfoCommentLayout = (LinearLayout) findViewById(R.id.addPropExtraInfoCommentLayout);
		addPropExtraInfoHintKeyowrdLayout = (LinearLayout) findViewById(R.id.addPropExtraInfoHintKeyowrdLayout);
		addPropExtraInfoKeyowrdLayout = (LinearLayout) findViewById(R.id.addPropExtraInfoKeyowrdLayout);
		addPropExtraInfoTermLayout = (LinearLayout) findViewById(R.id.addPropExtraInfoTermLayout);
		addPropImageButtonLayout = (LinearLayout) findViewById(R.id.addPropImageButtonLayout);
		addPropExtraInfoCommentEditBox = (EditText) findViewById(R.id.addPropExtraInfoCommentEditBox);
		addPropExtraInfoHint = (EditText) findViewById(R.id.addPropExtraInfoHintEditBox2);
		brokerListview = (ListView) findViewById(R.id.brokerListView);

		//Fill the Purpose Spinner 1)Any 2)Commercial 3)Residential
		addPropExtraInfoPurposeSpinner = (Spinner) findViewById(R.id.addPropExtraInfoPurposeSpinner);
		ArrayList<String> purposeList = new ArrayList<String>();
		purposeList.addAll(SpinnerItem.getAddPropPurposeList().keySet());
		ArrayAdapter<String> purposeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text,purposeList);
		purposeAdapter.setDropDownViewResource(R.layout.spinner_text);
		addPropExtraInfoPurposeSpinner.setAdapter(purposeAdapter);
		//END

		//Whom to let spinner
		addPropExtraInfoWhomToLetSpinner = (Spinner) findViewById(R.id.addPropExtraInfoWhomToLetSpinner);
		addPropExtraInfoWhomeToletOther = (EditText) findViewById(R.id.addPropExtraInfoWhomeToletOther);
		// set Visible other edit box if selected item is "Other"
		addPropExtraInfoWhomToLetSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (addPropExtraInfoWhomToLetSpinner.getSelectedItem().equals("Other"))
					addPropExtraInfoWhomeToletOther.setVisibility(View.VISIBLE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});

		//Fill whom to let spinner according to Property type Flat and Shop 1)Flat->Any, Company Employee only, Family only, Company
		//Employee/Family only", Bachelors only, Other 2)Shop-> Any, Company Only, Privates
		addPropPropTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View arg1, int arg2, long arg3) {
				if (addPropPropTypeSpinner.getSelectedItem().toString().equals("Flat") && addPropOptionSpinner.getSelectedItem().toString().equals("Rent")) {
					ArrayList<String> whomToLetFlatList = new ArrayList<String>();
					whomToLetFlatList.addAll(SpinnerItem.getAddPropWhomToLetFlatList().keySet());
					ArrayAdapter<String> whomToLetFlatAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text, whomToLetFlatList);
					whomToLetFlatAdapter.setDropDownViewResource(R.layout.spinner_text);
					addPropExtraInfoWhomToLetSpinner.setAdapter(whomToLetFlatAdapter);
				} else {
					ArrayList<String> whomToLetShopList = new ArrayList<String>();
					whomToLetShopList.addAll(SpinnerItem.getAddPropWhomToLetShopList().keySet());
					ArrayAdapter<String> whomToLetShopAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text, whomToLetShopList);
					whomToLetShopAdapter.setDropDownViewResource(R.layout.spinner_text);
					addPropExtraInfoWhomToLetSpinner.setAdapter(whomToLetShopAdapter);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		//END

		//Fill the Built Year Spinner
		addPropExtraInfoBuiltYearSpinner = (Spinner) findViewById(R.id.addPropExtraInfoBuiltYearSpinner);
		ArrayList<String> years = new ArrayList<String>();
		years.add("Do not know");
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = thisYear - 64; i <= thisYear; i++) {
			years.add(Integer.toString(i));
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, years);
		adapter.setDropDownViewResource(R.layout.spinner_text);
		addPropExtraInfoBuiltYearSpinner.setAdapter(adapter);
		//End

		
		//Button Save,Next,Previous,Cancel this is used to move next layout or Previous Layout
		addPropSaveButton = (Button) findViewById(R.id.addPropSaveButton);
		addPropNextButton = (Button) findViewById(R.id.addPropNextButton);
		addPropPreviousButton = (Button) findViewById(R.id.addPropPreviousButton);
		addPropCancleButton = (Button) findViewById(R.id.addPropCancelButton);

		//Cancel Button go back to previous Fragment
		addPropCancleButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainFragmentActivity) getActivity()).onBackPressed();
			}
		});
		//END

		//this is used for Image selection(Browse) 
		image1_button_click = 0;
		image2_button_click = 0;
		image3_button_click = 0;
		image4_button_click = 0;
		image5_button_click = 0;
		extraInfoImageButtonClick = 0;

		//first(Default) Image Selection button 
		addPropFlatDefaultImgButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				image1_button_click = 1;
				doCrop();
			}
		});
		//END

		//second Image Selection button
		addPropFlatImage2Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				image2_button_click = 1;
				doCrop();
			}
		});
		//END

		//third Image Selection button 
		addPropFlatImage3Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				image3_button_click = 1;
				doCrop();
			}
		});
		//END

		//fourth Image Selection button
		addPropFlatImage4Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				image4_button_click = 1;
				doCrop();
			}
		});
		//END

		//5th Image Selection button
		addPropFlatImage5Button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				image5_button_click = 1;
				doCrop();
			}
		});
		///END

		//Extra information image button click
		addPropFlatFarmImageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				extraInfoImageButtonClick = 1;
				doCrop();
			}
		});
		//END

		//Save Button Click Event Save the Add Property Detail
		addPropSaveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					//get the checked broker Name from list
					if (!checkAddress() && !checkPrice() && !checkRent()) {
						if (addPorpPublishingOptionSpinner.getSelectedItem().toString().equals("Preferred only"))
							selectedBrokerId = getCommaSepretedBrokerId();
						else if (addPorpPublishingOptionSpinner.getSelectedItem().toString().equals("Group"))
							selectedBrokerId = groupMemberBrokerId;

						//get checked nominee id
						int nomineeCntChoice = nomineeListView.getCount();
						String nomineeChecked = "";
						SparseBooleanArray nomineeSparseBooleanArray = nomineeListView.getCheckedItemPositions();
						for (int i = 0; i < nomineeCntChoice; i++) {
							if (nomineeSparseBooleanArray.get(i) == true) {
								nomineeChecked += nomineeListView.getItemAtPosition(i).toString();
								nomineeId = nomineeMap.get(nomineeChecked);
							}
						}
						//END

						if(addPropStateSpinner.getSelectedItemPosition() != -1)
							stateObject = stateResponse.getJSONObject(addPropStateSpinner.getSelectedItemPosition());
						if(addPropCitySpinner.getSelectedItemPosition() != -1)
							cityObject = cityResponse.getJSONObject(addPropCitySpinner.getSelectedItemPosition());
					
						/*************** Landmark
						 if(addPropLandmark1Spinner.getSelectedItemPosition() != -1)
							landmark1Object = landmark1Response.getJSONObject(addPropLandmark1Spinner.getSelectedItemPosition());
						if(addPropLandmark2Spinner.getSelectedItemPosition() != -1)
							landmark2Object = landmark1Response.getJSONObject(addPropLandmark2Spinner.getSelectedItemPosition());*/
						
						if(addPropLocationSpinner.getSelectedItemPosition() != -1)
							areaOfCityObject = areaOfCityResponse.getJSONObject(addPropLocationSpinner.getSelectedItemPosition()-1);

						//Set sub Property type for Bunglow and Plot type 
						if (SpinnerItem.getPropertyTypeOptionList().get(addPropPropTypeSpinner.getSelectedItem().toString()).equals("Bunglow")) 
							bunglowType = addPropPropTypeSpinner.getSelectedItem().toString();
						
						if (SpinnerItem.getPropertyTypeOptionList().get(addPropPropTypeSpinner.getSelectedItem().toString()).equals("Plot"))
							plotType = addPropPropTypeSpinner.getSelectedItem().toString();
						//END 
						//&& landmark1Object != null && landmark2Object != null
						if(stateObject !=null &&  cityObject != null  && areaOfCityObject != null) {
								WebserviceClient webserviceClientAdd = new WebserviceClient(MyPropertyAddActivity.this,addPropertyService);
								webserviceClientAdd.setResponseListner(new ResponseListner() {
									@Override
									public void handleResponse(Object res) {
										final JSONObject response = (JSONObject) res;
										try {
											if (response != null) {
												if (String.valueOf(response.getString(Constant.AddProperty.API_STATUS)).equals("1")) {
													AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
													alertDialog.setCancelable(false);
													alertDialog.setTitle(response.getString(Constant.AddProperty.API_MESSAGE));
													alertDialog.setMessage("1) This Facilities for one time message sending only.\n 2) After 15 Days of posting a property it will automatically be inactived from your account and it will not be searchable to others and it will shown in your &apos;Inactive&apos; folders where you can re-activate it for another 15 days.");
													
													alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
														public void onClick(DialogInterface dialog, int which) {
															Bundle addMyPropBundle = new Bundle();
															addMyPropBundle.putString("Type", "Search");
															MyPropertyListActivity myPropertyActivity = new MyPropertyListActivity();
															myPropertyActivity.setArguments(addMyPropBundle);
															((MainFragmentActivity) getActivity()).redirectScreenWithoutStack(myPropertyActivity);
														}
													});
													alertDialog.show();
												} else {
													Toast.makeText(getActivity(),String.valueOf(response.getString(Constant.AddProperty.API_MESSAGE)),Toast.LENGTH_LONG).show();
												}
											}
										} catch (JSONException e) {
											e.printStackTrace();
										}
									}
								});
						
								webserviceClientAdd.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
										userSessionManager.getSessionValue(Constant.Login.USER_ID),// 1-userID
										String.valueOf(SpinnerItem.getPropertyTypeOptionList().get((addPropPropTypeSpinner.getSelectedItem().toString()))), // 2-Property Type
										addPropAddressEditBox.getText().toString(), // 3-Address
										addPropPostCodeEditBox.getText().toString(), // 4-PostCode
										areaOfCityObject.getString(Constant.Area.AREA_ID),// 5-cmbarea1-Location
										"1",// 6-countryid
										stateObject.getString(Constant.State.STATEID),// 7-stateId
										cityObject.getString(Constant.City.CITY_ID),// 8-CityId
										"",// 9-Distric ID
										addPropOptionSpinner.getSelectedItem().toString(),// 10-optionrent/sale
										"0",//landmark1Object.getString(Constant.Landmark.LANDMARK_ID),// 11-landmark1
										"0",//landmark2Object.getString(Constant.Landmark.LANDMARK_ID),// 12-landmark2
										"",//addProplandmarkOtherEditbox.getText().toString(),// 13-landmark1other
										"",//addPropLandmark2Editbox.getText().toString(),// 14-landmark2other
										addPropLongitudeEditBox.getText().toString(),// 15-Longitude
										addPropLatitudeEditBox.getText().toString(),// 16-Latitude
										String.valueOf(SpinnerItem.getPropertyOccupacyList().get(addPropPropOccupSpinner.getSelectedItem().toString())),//17 PropertyOccupacy
										addPropOccupNameEditBox.getText().toString(),// 18-occupName
										addPropOccupDetailEditBox.getText().toString(),// 19-occupDetail
										addPropReleseDateEditBox.getText().toString(),// 20-RelaseDate
										addPropFlatSellPriceEditBox.getText().toString(),// 21-price
										addPropFlatTotalPriceEditBox.getText().toString(),// 22-totalprice
										addPropPriceRentEditBox.getText().toString(),// 23-rent
										addPropFlatDastawageEditBox.getText().toString(),// 24-dastawage
										addPropPriceRentDepositeEditBox.getText().toString(),// 25-rentDeposition
										"SqYard",// 26-areaunit
										addPropExtraInfoPurposeSpinner.getSelectedItem().toString(),// 27-purpose
										addPropFlatAreaEditBox.getText().toString(),// 28-MinArea
										"",// 29-Max Area
										addPropExtraInfoBuiltYearSpinner.getSelectedItem().toString(),// 30-yearbuiltup
										addPropExtraInfoCommentEditBox.getText().toString(),// 31-Comment
										addPropExtraInfoHint.getText().toString(),// 32-hint
										String.valueOf(SpinnerItem.getAddPropBedList().get(addPropFlatBedsSpinner.getSelectedItem().toString())),// 33-bed
										String.valueOf(SpinnerItem.getAddPropFurnishOptionList().get(addPropFlatFurnishOptionSpinner.getSelectedItem().toString())),// 34-FurnidhOption
										addPropFlatFurnishDetailEditBox.getText().toString(),// 35-furnishDetail
										String.valueOf(SpinnerItem.getAddPropFloorList().get(addPropFlatFloorSpinner.getSelectedItem().toString())),// 36-floor
										String.valueOf(SpinnerItem.getAddPropBuildingTypeList().get(addPropFlatBuildingTypeSpinner.getSelectedItem().toString())),// 37-BuildingType
										addPropExtraInfoWhomToLetSpinner.getSelectedItem().toString(),// 38-whometolet
										addPropExtraInfoWhomeToletOther.getText().toString(),// 39-whometoletOther
										addPropPriceParkingChargesSaleEditBox.getText().toString(),// 40parking
										"7",// 41-frontheight
										"8",// 42-attachcommon
										addPropFlatConstructionAreaEditBox.getText().toString(),// 43-constructionArea
										StringUtils.defaultString(bunglowType, ""),// 44-bunglowtype
										"",// 45-min plot area
										addPropFlatPlotAreaEditBox.getText().toString(),// 46-plotarea
										"sqYard",// 47-plotareaunit
										StringUtils.defaultString(plotType, ""),// 48-plottype
										String.valueOf(SpinnerItem.getAddPropNaStatusList().get(addPropNASpinner.getSelectedItem().toString())),// 49-nastatus
										String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(addPropTermsKhetiCheck.isChecked()))),// 50-kheti
										String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(addPropTermsNaviSharatCheck.isChecked()))),// 51-navisharat
										String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(addPropTermsJuniSharatCheck.isChecked()))),// 52-junisharat
										String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(addPropTermsPRASHACheck.isChecked()))),// 53-prassap
										String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(addPropTermsDisputeCheck.isChecked()))),// 54-dispute
										"",// 55-titleclear
										String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(addPropTermsShreeSarkarCheck.isChecked()))),// 56-shreesarkar
										String.valueOf(SpinnerItem.getOnRoadList().get(addPropExtraInfoFramHouseRoadTouchSpinner.getSelectedItem().toString())),// 57-onroad
										String.valueOf(SpinnerItem.getPublishinOptionList().get(addPorpPublishingOptionSpinner.getSelectedItem().toString())),// 58-PublishingOption
										addPropPriceMaintenanceEditBox.getText().toString(),// 59-maintenance
										addPropPriceTransferFeeseSaleEditBox.getText().toString(),// 60-transferfees
										addPropPriceAECAudaLegalSaleEditBox.getText().toString(),// 61-aecauda
										StringUtils.defaultString(tpSchemeId),// 62-cmbtpscheme
										addPropFlatZoneSpinner.getSelectedItem().toString(),// 63-chkzone
										"1",//64
										"1",//65
										StringUtils.defaultString(nomineeId),// 66-chk-NomineeId
										StringUtils.defaultString(StringUtils.join(facilityIdArray, ",")),// 67-chkfacility
										StringUtils.defaultString(selectedBrokerId),// StringUtils.defaultString(StringUtils.join(brokerIdArray,","),""),//68-chkbroker
										getCropImagePath(Constant.AddProperty.IMAGE_1),
										getCropImagePath(Constant.AddProperty.IMAGE_2),
										getCropImagePath(Constant.AddProperty.IMAGE_3),
										getCropImagePath(Constant.AddProperty.IMAGE_4),
										getCropImagePath(Constant.AddProperty.IMAGE_5)
										
										/*StringUtils.defaultString(defaultImageValue, ""),// 69-defaultImage
										StringUtils.defaultString(image2Value, ""), // 70-Image1
										StringUtils.defaultString(image3Value, ""), // 71-Image2
										StringUtils.defaultString(image4Value, ""), // 72-Image3
										StringUtils.defaultString(image5Value, "") // 73-Image4*/
								);
						}
					} else {
						Toast.makeText(getActivity(), "Please Fill Mandatory Field", Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		// ********* ANKUR *********
		
		//next Button to move the next Layout
		addPropNextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(checkPropertyType()){//Check property type is not equal to select
					activeTabIndex = ((ViewGroup) mainContainer).indexOfChild(addPropPublicInfoLayout);
					iconBarLayout.getChildAt(activeTabIndex).performClick();
				} else if(checkPropertyOption()) {//Check property option is selected 
					activeTabIndex = ((ViewGroup) mainContainer).indexOfChild(addPropPublicInfoLayout);
					iconBarLayout.getChildAt(activeTabIndex).performClick();
				} else if(checkLocation()) { //Check property type is not equal to "Select"
					activeTabIndex = ((ViewGroup) mainContainer).indexOfChild(addPropPublicInfoLayout);
					iconBarLayout.getChildAt(activeTabIndex).performClick();
				} else if (checkAddress()) {// check Address edit box is blank or not
					activeTabIndex = ((ViewGroup) mainContainer).indexOfChild(addPropPublicInfoLayout);
					iconBarLayout.getChildAt(activeTabIndex).performClick();
				} else if(checkBed()) {
					activeTabIndex = ((ViewGroup) mainContainer).indexOfChild(addPropFaltLayout);
					iconBarLayout.getChildAt(activeTabIndex).performClick();
				} else if (checkRent()) { // check price and rent is blank or not
					activeTabIndex = ((ViewGroup) mainContainer).indexOfChild(addPropRentLayout);
					iconBarLayout.getChildAt(activeTabIndex).performClick();
				} else if (checkPrice()) { // check price is selected or not blank
					activeTabIndex = ((ViewGroup) mainContainer).indexOfChild(addPropFaltLayout);
					iconBarLayout.getChildAt(activeTabIndex).performClick();
				} else { //goto next screen
					++activeTabIndex;
					setBackgroundImageView(iconBarLayout.getChildAt(activeTabIndex));
					iconBarLayout.getChildAt(activeTabIndex).performClick();
				}
			}
		});
		//END 

		//previous Button to move Previous Layout
		addPropPreviousButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				--activeTabIndex;
				setBackgroundImageView(iconBarLayout.getChildAt(activeTabIndex));
				iconBarLayout.getChildAt(activeTabIndex).performClick();
			}
		});
		//END

		//Bind event to all image icons of left side icon bar
		iconBarLayout = (RelativeLayout) findViewById(R.id.iconLayout2);
		int totalChild = iconBarLayout.getChildCount();
		for (int i = 0; i < totalChild; i++) {
			View imageView = iconBarLayout.getChildAt(i);
			imageView.setOnClickListener(new OnClickListener() {
				@SuppressLint("InlinedApi")
				@Override
				public void onClick(View v) {
					addPropPreviousButton.setVisibility(View.VISIBLE);
					addPropNextButton.setVisibility(View.VISIBLE);
					addPropSaveButton.setVisibility(View.GONE);
					setBackgroundImageView(v);
					final TextView header = (TextView) findViewById(R.id.addPropHeaderTv2);

					//Set Header according to icon
					switch (v.getId()) {
					// (1)
					case R.id.publicInfoImg:
						header.setText(PUBLIC_INFORMATION_HEADING);
						addPropPreviousButton.setVisibility(View.GONE);
						setVisibility(addPropPublicInfoLayout);
						activeTabIndex = ((ViewGroup) mainContainer).indexOfChild(addPropPublicInfoLayout);
						break;
					// (2)
					case R.id.nomineeImg:
						if (checkAddress()) {// addPropAddressEditBox.getText().length()== 0 &&addPropAddressEditBox.isShown()){
							addPropNextButton.performClick();
						} else {
							if (!nomineeFlag.equalsIgnoreCase("Nominee")) {//call getNomineeList Method if flag not equal to  "Nomiee"
								nomineeFlag = "Nominee";
								getNomineeList();
							}
							header.setText(NOMINEE_INFORMATION_HEADING);
							setVisibility(addPropNomineeLayout);
							activeTabIndex = ((ViewGroup) mainContainer).indexOfChild(addPropNomineeLayout);
						}
						break;
					// (3)
					case R.id.flatImg:
						if(SpinnerItem.getPropertyTypeOptionList().get(addPropPropTypeSpinner.getSelectedItem().toString()).equals("Plot") || addPropPropTypeSpinner.getSelectedItem().equals("Farm House(Bunglow)"))
							getTpSchem();//call getTpScheme() to get list of TPScheme

						String propertyType = addPropPropTypeSpinner.getSelectedItem().toString();
						hideLayoutChild(addPropFaltLayout);// hide all the child layout form particular Layout
						header.setText("Property (" + propertyType + ") Related");
						setVisibility(addPropFaltLayout);
						activeTabIndex = ((ViewGroup) mainContainer).indexOfChild(addPropFaltLayout);

						//Flat For Rent
						if (addPropPropTypeSpinner.getSelectedItem().toString().equals("Flat") && addPropOptionSpinner.getSelectedItem().toString().equals("Rent")) {
							addPropBedLayout.setVisibility(View.VISIBLE);
							addPropFurnishLayout.setVisibility(View.VISIBLE);
							addPropFloorLayout.setVisibility(View.VISIBLE);
							addPropBuildingTypeLayout.setVisibility(View.VISIBLE);
							addPropAreaLayout.setVisibility(View.VISIBLE);
							addpropFlatImageLayout.setVisibility(View.VISIBLE);
							addPropFlatAreaHintTv.setText("Sq.Foot");
						} //Flat For Sale 
						else if (addPropPropTypeSpinner.getSelectedItem().toString().equals("Flat") && addPropOptionSpinner.getSelectedItem().toString().equals("Sale")) {
							addPropBedLayout.setVisibility(View.VISIBLE);
							addPropFurnishLayout.setVisibility(View.VISIBLE);
							addPropFloorLayout.setVisibility(View.VISIBLE);
							addPropBuildingTypeLayout.setVisibility(View.VISIBLE);
							addPropAreaLayout.setVisibility(View.VISIBLE);
							addpropFlatImageLayout.setVisibility(View.VISIBLE);
							addPropSellingPriceLayout.setVisibility(View.VISIBLE);
							addPropTotalPriceLyout.setVisibility(View.VISIBLE);
							addpropDastawage.setVisibility(View.VISIBLE);
							addPropFlatAreaHintTv.setText("Sq.Foot");
						} //Shop For Rent
						else if (SpinnerItem.getPropertyTypeOptionList().get(addPropPropTypeSpinner.getSelectedItem().toString()).equals("Shop")
								&& addPropOptionSpinner.getSelectedItem().toString().equals("Rent")
								&& !addPropPropTypeSpinner.getSelectedItem().toString().equals("Commercial Bunglow")
								&& !addPropPropTypeSpinner.getSelectedItem().toString().equals("Corporate House")
								&& !addPropPropTypeSpinner.getSelectedItem().toString().equals("Godown")
								&& !addPropPropTypeSpinner.getSelectedItem().toString().equals("Shades")
								&& !addPropPropTypeSpinner.getSelectedItem().toString().equals("Factory")) {
							addPropFloorLayout.setVisibility(View.VISIBLE);
							addPropFurnishLayout.setVisibility(View.VISIBLE);
							addPropAreaLayout.setVisibility(View.VISIBLE);
							addPropBuildingTypeLayout.setVisibility(View.VISIBLE);
							addpropFlatImageLayout.setVisibility(View.VISIBLE);
							addPropFlatAreaHintTv.setText("Sq. Foot");
						} //Shop For Sale
						else if (SpinnerItem.getPropertyTypeOptionList().get(addPropPropTypeSpinner.getSelectedItem().toString()).equals("Shop")
								&& addPropOptionSpinner.getSelectedItem().toString().equals("Sale")
								&& !addPropPropTypeSpinner.getSelectedItem().toString().equals("Commercial Bunglow")
								&& !addPropPropTypeSpinner.getSelectedItem().toString().equals("Corporate House")
								&& !addPropPropTypeSpinner.getSelectedItem().toString().equals("Godown")
								&& !addPropPropTypeSpinner.getSelectedItem().toString().equals("Shades")
								&& !addPropPropTypeSpinner.getSelectedItem().toString().equals("Factory")) {
							addPropFloorLayout.setVisibility(View.VISIBLE);
							addPropFurnishLayout.setVisibility(View.VISIBLE);
							addPropAreaLayout.setVisibility(View.VISIBLE);
							addPropBuildingTypeLayout.setVisibility(View.VISIBLE);
							addPropSellingPriceLayout.setVisibility(View.VISIBLE);
							addPropTotalPriceLyout.setVisibility(View.VISIBLE);
							addpropFlatImageLayout.setVisibility(View.VISIBLE);
							addPropFlatAreaHintTv.setText("Sq. Foot");
						} //Shop for Sale
						else if (SpinnerItem.getPropertyTypeOptionList().get(addPropPropTypeSpinner.getSelectedItem().toString()).equals("Shop")&& addPropOptionSpinner.getSelectedItem().toString().equals("Sale")) {
							addPropFloorLayout.setVisibility(View.VISIBLE);
							addPropFurnishLayout.setVisibility(View.VISIBLE);
							addPropAreaLayout.setVisibility(View.VISIBLE);
							addPropPlotAreaLayout.setVisibility(View.VISIBLE);
							addPropConstructionLayout.setVisibility(View.VISIBLE);
							addPropBuildingTypeLayout.setVisibility(View.VISIBLE);
							addPropSellingPriceLayout.setVisibility(View.VISIBLE);
							addPropTotalPriceLyout.setVisibility(View.VISIBLE);
							addpropFlatImageLayout.setVisibility(View.VISIBLE);
							addPropFlatAreaHintTv.setText("Sq.Foot");
							addPropFlatPlotAreaHintTv.setText("Sq.Foot");
							addPropFlatConstructionAreaHintTv.setText("Sq.Foot");
						} //Shop for Rent
						else if (SpinnerItem.getPropertyTypeOptionList().get(addPropPropTypeSpinner.getSelectedItem().toString()).equals("Shop")&& addPropOptionSpinner.getSelectedItem().toString().equals("Rent")) {
							addPropFloorLayout.setVisibility(View.VISIBLE);
							addPropFurnishLayout.setVisibility(View.VISIBLE);
							addPropAreaLayout.setVisibility(View.VISIBLE);
							addPropPlotAreaLayout.setVisibility(View.VISIBLE);
							addPropConstructionLayout.setVisibility(View.VISIBLE);
							addPropBuildingTypeLayout.setVisibility(View.VISIBLE);
							addpropFlatImageLayout.setVisibility(View.VISIBLE);
							addPropFlatAreaHintTv.setHint("Sq.Foot");
							addPropFlatPlotAreaHintTv.setHint("Sq.Foot");
							addPropFlatConstructionAreaHintTv.setHint("Sq.Foot");
						} //farm House(Bunglow) Rent 
						else if (addPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)")
								&& addPropOptionSpinner.getSelectedItem().toString().equals("Rent")) {
							addPropTPSchemeLayout.setVisibility(View.VISIBLE);
							addPropAreaLayout.setVisibility(View.VISIBLE);
							addPropConstructionLayout.setVisibility(View.VISIBLE);
							addPropFlatConstructionArea.setText("Construction Area(for farm hourse only)");
							addpropFlatImageLayout.setVisibility(View.VISIBLE);
							addPropFlatAreaHintTv.setText("Sq.Foot");
							addPropFlatConstructionAreaHintTv.setText("Sq.Foot");
						} //farm House(Bunglow) Sale
						else if (addPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)")
								&& addPropOptionSpinner.getSelectedItem().toString().equals("Sale")) {
							addPropTPSchemeLayout.setVisibility(View.VISIBLE);
							addPropAreaLayout.setVisibility(View.VISIBLE);
							addPropConstructionLayout.setVisibility(View.VISIBLE);
							addPropFlatConstructionArea.setText("Construction Area(for farm hourse only)");
							addPropTotalPriceLyout.setVisibility(View.VISIBLE);
							addpropFlatImageLayout.setVisibility(View.VISIBLE);
							addPropFlatAreaHintTv.setText("Sq.Foot");
							addPropFlatConstructionAreaHintTv.setText("Sq.Foot");
						} //Bunglow for sale and rent
						else if (SpinnerItem.getPropertyTypeOptionList().get(addPropPropTypeSpinner.getSelectedItem().toString()).equals("Bunglow")
								&& !addPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)")) {
							addPropBedLayout.setVisibility(View.VISIBLE);
							addPropFurnishLayout.setVisibility(View.VISIBLE);
							addPropPlotAreaLayout.setVisibility(View.VISIBLE);
							addPropConstructionLayout.setVisibility(View.VISIBLE);
							//show total price 
							if(addPropOptionSpinner.getSelectedItem().toString().equals("Sale"))
								addPropTotalPriceLyout.setVisibility(View.VISIBLE);
							else
								addPropTotalPriceLyout.setVisibility(View.GONE);
							addpropFlatImageLayout.setVisibility(View.VISIBLE);
							addPropFlatPlotAreaHintTv.setText("Sq.Foot");
							addPropFlatConstructionAreaHintTv.setText("Sq.Foot");
						} //Plot For Rent **/
						else if (SpinnerItem.getPropertyTypeOptionList().get(addPropPropTypeSpinner.getSelectedItem().toString()).equals("Plot")
								&& addPropOptionSpinner.getSelectedItem().toString().equals("Rent")) {
							addPropTPSchemeLayout.setVisibility(View.VISIBLE);
							addPropAreaLayout.setVisibility(View.VISIBLE);
							addPropConstructionLayout.setVisibility(View.VISIBLE);
							addPropFlatConstructionArea.setText("Construction Area(for farm hourse only)");
							if(addPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Plot)"))
								addPropFlatConstructionAreaEditBox.setEnabled(true);
							else 
								addPropFlatConstructionAreaEditBox.setEnabled(false);
							addpropFlatImageLayout.setVisibility(View.VISIBLE);
							addPropFlatAreaHintTv.setText("Sq. Yard");
							addPropFlatConstructionAreaHintTv.setText("Sq. Yard");
						} //Plot For Sale 
						else {
							if(addPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Plot)"))
								addPropFlatConstructionAreaEditBox.setEnabled(true);
							else 
								addPropFlatConstructionAreaEditBox.setEnabled(false);
							addPropTPSchemeLayout.setVisibility(View.VISIBLE);
							addPropAreaLayout.setVisibility(View.VISIBLE);
							addPropConstructionLayout.setVisibility(View.VISIBLE);
							addPropFlatConstructionArea.setText("Construction Area(for farm hourse only)");
							addPropTotalPriceLyout.setVisibility(View.VISIBLE);
							addpropFlatImageLayout.setVisibility(View.VISIBLE);
							addPropFlatAreaHintTv.setText("Sq. Yard");
							addPropFlatConstructionAreaHintTv.setText("Sq. Yard");
						}
						break;

					//======> (4) Price Budge Related <====
					case R.id.priceImg:
						hideLayoutChild(addPropRentLayout);
						header.setText(PRICE_INFORMATION_HEADING);
						setVisibility(addPropRentLayout);
						activeTabIndex = ((ViewGroup) mainContainer).indexOfChild(addPropRentLayout);
						//Rent 
						if (addPropOptionSpinner.getSelectedItem().toString().equals("Rent") && !addPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)") && !SpinnerItem.getPropertyTypeOptionList().get(addPropPropTypeSpinner.getSelectedItem().toString()).equals("Plot")) {
							addPropPriceRentMaintenanceLayout.setVisibility(View.VISIBLE);
							addPropPriceRentLayout.setVisibility(View.VISIBLE);
							addPropPriceRentDepositeLayout.setVisibility(View.VISIBLE);
						} //Sale
						else if (addPropOptionSpinner.getSelectedItem().toString().equals("Sale")) {
							addPropPriceRentMaintenanceLayout.setVisibility(View.VISIBLE);
							addPropPriceSaleLayout.setVisibility(View.VISIBLE);
						} //Farm house(Bunglow) Rent
						else if (addPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)") && addPropOptionSpinner.getSelectedItem().toString().equals("Rent")) {
							addPropPriceRentMaintenanceLayout.setVisibility(View.VISIBLE);
							addPropPriceRentLayout.setVisibility(View.VISIBLE);
						} //Plot And Rent
						else if (SpinnerItem.getPropertyTypeOptionList().get(addPropPropTypeSpinner.getSelectedItem().toString()).equals("Plot") && addPropOptionSpinner.getSelectedItem().toString().equals("Rent")) {
							addPropPriceRentMaintenanceLayout.setVisibility(View.VISIBLE);
							addPropPriceRentLayout.setVisibility(View.VISIBLE);
						}
						break;
					// (5)
					case R.id.extraInfoImg:
						if (addPropPriceRentEditBox.getText().length() == 0 && addPropPriceRentEditBox.isShown()) {
							addPropNextButton.performClick();
						} else {
							hideLayoutChild(addPropExtraInfoLayout);
							header.setText(EXTRA_INFORMATION_HEADING);
							setVisibility(addPropExtraInfoLayout);
							activeTabIndex = ((ViewGroup) mainContainer).indexOfChild(addPropExtraInfoLayout);
							//Flat For Rent Layout
							if (addPropPropTypeSpinner.getSelectedItem().toString().equals("Flat") && addPropOptionSpinner.getSelectedItem().toString().equals("Rent")) {
								addPropExtraInfoPurposeLayout.setVisibility(View.VISIBLE);
								addPropExtraInfoWhomeToLayout.setVisibility(View.VISIBLE);
								addPropExtraInfoBuilrYearLayout.setVisibility(View.VISIBLE);
								addPropExtraInfoHintKeyowrdLayout.setVisibility(View.VISIBLE);
								addPropExtraInfoCommentLayout.setVisibility(View.VISIBLE);
							} //Flat For Sale 
							else if (addPropPropTypeSpinner.getSelectedItem().toString().equals("Flat") && addPropOptionSpinner.getSelectedItem().toString().equals("Sale")) {
								addPropExtraInfoPurposeLayout.setVisibility(View.VISIBLE);
								addPropExtraInfoBuilrYearLayout.setVisibility(View.VISIBLE);
								addPropExtraInfoHintKeyowrdLayout.setVisibility(View.VISIBLE);
								addPropExtraInfoCommentLayout.setVisibility(View.VISIBLE);
							} //Shop For Rent And Sale
							else if (SpinnerItem.getPropertyTypeOptionList().get(addPropPropTypeSpinner.getSelectedItem().toString()).equals("Shop")) {
								addPropExtraInfoWhomeToLayout.setVisibility(View.VISIBLE);
								addPropExtraInfoBuilrYearLayout.setVisibility(View.VISIBLE);
								addPropExtraInfoHintKeyowrdLayout.setVisibility(View.VISIBLE);
								addPropExtraInfoCommentLayout.setVisibility(View.VISIBLE);
							} //Bunglow For Sale and Rent
							else if (SpinnerItem.getPropertyTypeOptionList().get(addPropPropTypeSpinner.getSelectedItem().toString()).equals("Bunglow")&& !addPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)")) {
								addPropExtraInfoHintKeyowrdLayout.setVisibility(View.VISIBLE);
								addPropExtraInfoKeyowrdLayout.setVisibility(View.VISIBLE);
							} //Plot For Sale And Rent 
							else if (SpinnerItem.getPropertyTypeOptionList().get(addPropPropTypeSpinner.getSelectedItem().toString()).equals("Plot")) {
								addPropExtraInfoPurposeLayout.setVisibility(View.VISIBLE);
								addPropExtraInfoTermLayout.setVisibility(View.VISIBLE);
								addPropExtraInfoHintKeyowrdLayout.setVisibility(View.VISIBLE);
								addPropExtraInfoCommentLayout.setVisibility(View.VISIBLE);
								addPropImageButtonLayout.setVisibility(View.VISIBLE);
							} //Farm House(Bunglow) For Rent And sale
							else {
								addPropExtraInfoPurposeLayout.setVisibility(View.VISIBLE);
								addPropExtraInfoTermLayout.setVisibility(View.VISIBLE);
								addPropExtraInfoHintKeyowrdLayout.setVisibility(View.VISIBLE);
								addPropExtraInfoCommentLayout.setVisibility(View.VISIBLE);
								addPropImageButtonLayout.setVisibility(View.VISIBLE);
							}
						}
						break;
					// (6)
					case R.id.brokerImg:
						header.setText(BROKER_INFORMATION_HEADING);
						setVisibility(addPropSelectBrokLayout);
						activeTabIndex = ((ViewGroup) mainContainer).indexOfChild(addPropSelectBrokLayout);

						//call the getBrokerList method according to spinner item selected
						addPorpPublishingOptionSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
								brokerNames.clear();
								brokerMap.clear();
								pageCount = 1;
								brokerListview.clearChoices();

								if (addPorpPublishingOptionSpinner.getSelectedItem().equals("Area")&& !brokerListOf.equalsIgnoreCase("Area")) {
									brokerListOf = "Area";
									getAreaList();
								} else if (addPorpPublishingOptionSpinner.getSelectedItem().equals("Preferred only") && !brokerListOf.equalsIgnoreCase("Preferred")) {
									brokerListOf = "Preferred";
									getPreferredBothBroker(pageCount);
								} else if (addPorpPublishingOptionSpinner.getSelectedItem().equals("Group") && !brokerListOf.equalsIgnoreCase("Group")) {
									brokerListOf = "Group";
									getGroupList();
								} else if(addPorpPublishingOptionSpinner.getSelectedItem().equals("All Broker") && !brokerListOf.equalsIgnoreCase("All Broker")) {
									brokerListOf = "All Broker";
									getAllBrokerList(pageCount);
								}
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});

						//used to Disallow Intercept Touch Event of list view
						brokerListview.setOnTouchListener(new OnTouchListener() {
							// Setting on Touch Listener for handling the touch inside ScrollView
							@Override
							public boolean onTouch(View v, MotionEvent event) {
								// Disallow the touch request for parent scroll on touch of child view
								v.getParent().requestDisallowInterceptTouchEvent(true);
								return false;
							}
						});
						//END

						//on list view scroll change event page count increase
						brokerListview.setOnScrollListener(new OnScrollListener() {
							@Override
							public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
									int totalItemCount) {
								if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
									if (flag_loading == false) {
										flag_loading = true;
										if (brokerListOf.equals("Preferred only"))
											getPreferredBothBroker(++pageCount);
									}
								}
							}
							@Override
							public void onScrollStateChanged(AbsListView view, int scrollState) {}
						});
						//END

						break;
					// (7)
					case R.id.facilityImg:
						// get the slected broker id list
						if (addPorpPublishingOptionSpinner.getSelectedItem().toString().equals("Area")) {
							getAreaWiseBroketList(getCommaSepretedBrokerId());
						}
						if (!facilityFlag.equalsIgnoreCase("Facility")) {
							facilityFlag = "Facility";
							getFacilityList();
						}
						addPropSaveButton.setVisibility(View.VISIBLE);
						addPropNextButton.setVisibility(View.GONE);
						header.setText(FACILITY_INFORMATION_HEADING);
						setVisibility(addPropFacilityLayout);
						activeTabIndex = ((ViewGroup) mainContainer).indexOfChild(addPropFacilityLayout);
						break;
					default:
						break;
					}
				}
			});
		}

		//Calender For Relese Date Edit Box onclick of Edit Box Display Calender on click of relese date box open the date picker dialog
		addPropReleseDateEditBox.setFocusable(false);
		addPropReleseDateEditBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (addPropReleseDateEditBox.requestFocus()) {
					getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				}
				new DatePickerFragment((EditText) v).show(getActivity().getSupportFragmentManager(), "datePicker");
			}
		});
	}

	//process Response For State Web Service 
	@Override
	public void processResponse(Object response) {}

	//Hide all the layout and set visible true for given layout
	private void setVisibility(View visibalLinerLayout) {
		this.hideAllLayout();
		visibalLinerLayout.setVisibility(View.VISIBLE);
	}

	//Hide all the layout and remove background color
	private void hideAllLayout() {
		int totalChild = mainContainer.getChildCount();
		for (int i = 0; i < totalChild; i++) {
			mainContainer.getChildAt(i).setVisibility(View.GONE);
		}
	}

	//Hide Particular View Of Layout
	private void hideLayoutChild(View hideLinerLayout) {
		int childView = ((ViewGroup) hideLinerLayout).getChildCount();
		for (int i = 0; i < childView; i++) {
			((ViewGroup) hideLinerLayout).getChildAt(i).setVisibility(View.GONE);
		}
	}

	//Remove background color of all image view/icons Set white background color for given view
	private void setBackgroundImageView(View v) {
		reSetAllImageView();
		v.setBackgroundColor(Color.WHITE);
		v.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				v.requestFocus();
			}
		});
	}

	//Remove background of all image view/icons
	private void reSetAllImageView() {
		int totalChild = iconBarLayout.getChildCount();
		for (int i = 0; i < totalChild; i++) {
			iconBarLayout.getChildAt(i).setBackgroundColor(0);
		}
	}

	//crop the selected image from gallery
	private void doCrop() {
		Intent intent = new Intent();
		//call android default gallery
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		//code for crop image
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 0);
		intent.putExtra("aspectY", 0);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 150);
		try {
			intent.putExtra("return-data", true);
			startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(getActivity().getApplicationContext(), "Whoops - your device doesn't support the crop action!", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	//Get RealPath from URI this is used in Browse Image from gallery
	/*public String getRealPathFromURI(Uri contentUri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = getActivity().managedQuery(contentUri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}*/
	
	
	private void storeScropImage(Bitmap photo,String imageName) {
		//String root = Environment.getExternalStorageDirectory().toString();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		java.util.Date now = new java.util.Date();
		String fileName = imageName+"_"+formatter.format(now) + ".jpeg";
		imageNameMap.put(imageName,fileName);
        
        
		File file = new File (myDir,imageNameMap.get(imageName) );
        try {
           FileOutputStream out = new FileOutputStream(file);
           photo.compress(Bitmap.CompressFormat.JPEG, 90, out);
           out.flush();
           out.close();
           
        } catch (Exception e) {
           e.printStackTrace();
        }
	}
	
	private String getCropImagePath(String fileName) {
		if(imageNameMap.get(fileName) != null) {
			File tempFile = new File(Environment.getExternalStorageDirectory() + PROPERTY_IMAGE+"/"+imageNameMap.get(fileName));
			if(tempFile.exists())
				return tempFile.getAbsolutePath();
		}else { 
			return new File(Environment.getExternalStorageDirectory() + PROPERTY_IMAGE+"/"+imageNameMap.get("NoImage.jpeg")).getAbsolutePath();
		}
		return fileName;
	}
	
	//Image Selection(Browse) for Logo And Photo
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == getActivity().RESULT_OK) {
			if (requestCode == 1) {
				Bundle extras2 = data.getExtras();
				if (extras2 != null) {
					Bitmap photo = extras2.getParcelable("data");
				
					if (image1_button_click == 1) {
						storeScropImage(photo,Constant.AddProperty.IMAGE_1);
						addPropRemoveImage1Tv.setVisibility(View.VISIBLE);
						addPropImage1ImageView.setImageBitmap(photo);
					} else if (image2_button_click == 1) {
						storeScropImage(photo,Constant.AddProperty.IMAGE_2);
						addPropRemoveImage2Tv.setVisibility(View.VISIBLE);
						addPropImage2ImageView.setImageBitmap(photo);
					} else if (image3_button_click == 1) {
						storeScropImage(photo,Constant.AddProperty.IMAGE_3);
						addPropRemoveImage3Tv.setVisibility(View.VISIBLE);
						addPropImage3ImageView.setImageBitmap(photo);
					} else if (image4_button_click == 1) {
						storeScropImage(photo,Constant.AddProperty.IMAGE_4);
						addPropRemoveImage4Tv.setVisibility(View.VISIBLE);
						addPropImage4ImageView.setImageBitmap(photo);
					} else if (image5_button_click == 1) {
						storeScropImage(photo,Constant.AddProperty.IMAGE_5);
						addPropRemoveImage5Tv.setVisibility(View.VISIBLE);
						addPropImage5ImageView.setImageBitmap(photo);
					} else if (extraInfoImageButtonClick == 1) {
						storeScropImage(photo,Constant.AddProperty.EXTRA_IMG);
						addPropRemoveExtraImgTv.setVisibility(View.VISIBLE);
						addPropExtraInfoImageView.setImageBitmap(photo);
					}
					
					image1_button_click = 0;
					image2_button_click = 0;
					image3_button_click = 0;
					image4_button_click = 0;
					image5_button_click = 0;
					extraInfoImageButtonClick = 0;
				}
			}
		}
	}

	

	//This is used for set the Location of the google map
	private void setUpMapIfNeeded() {
		if (googleMap == null) {
			googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.googleMap)).getMap();
			if (googleMap != null) {
				googleMap.setMyLocationEnabled(true);
				
				LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
				Criteria criteria = new Criteria();
				if(locationManager != null) {
					bestProvider = locationManager.getBestProvider(criteria, true);
					Location location = locationManager.getLastKnownLocation(bestProvider); 
					
					if(location == null) {
						location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					} 
					
					if(googleMarker != null) 
						googleMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())));
				
					if(location != null) {
						CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude()));
						googleMap.moveCamera(center);
						CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
						googleMap.animateCamera(zoom);
					}
				}
				googleMap.setOnMapClickListener(new OnMapClickListener() {
					@Override
					public void onMapClick(LatLng latlng) {
						//drawMarker(latlng);
						openGoogleMapDialog();
					}
				});
			}
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
		addPropLatitudeEditBox.setText(String.valueOf(latlng.latitude));
		addPropLongitudeEditBox.setText(String.valueOf(latlng.longitude));
	}
	//END
	
	//get the list of state Web Service to get State list and add into the state spinner
	public void getStateList() {
		stateResponse = CreateJsonArrayFileIntoCache.readStateListJsonData(getActivity().getApplicationContext());
		if (stateResponse != null) {
			ArrayAdapter<String> stateAdapter;
			try {
				stateAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, JSONUtils.getList(stateResponse, Constant.State.STATENAME));
				stateAdapter.setDropDownViewResource(R.layout.spinner_text);
				// stateAdapter.
				addPropStateSpinner.setAdapter(stateAdapter);
				// Set Default Selection
				addPropStateSpinner.setSelection(stateAdapter.getPosition(userSessionManager.getSessionValue(Constant.Login.STATE_NAME)));
				addPropStateSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
						try {
							getCityList(stateResponse.getJSONObject(position).getString(Constant.State.STATEID));
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
	//END

	//Web Service to get the City name List and add into the City Spinner
	public void getCityList(String stateId) {
		WebserviceClient cityListWebserviceClient = new WebserviceClient(MyPropertyAddActivity.this, cityListService);
		cityListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				cityResponse = (JSONArray) response;;
				try {
					if (cityResponse != null && !((JSONObject) cityResponse.get(0)).has(Constant.City.API_STATUS)) {
						addPropCitySpinner.setEnabled(true);
						cityArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, JSONUtils.getList(cityResponse, Constant.City.CITY_NAME));
						cityArrayAdapter.setDropDownViewResource(R.layout.spinner_text);
						addPropCitySpinner.setAdapter(cityArrayAdapter);
						// Set Default Selection
						addPropCitySpinner.setSelection(cityArrayAdapter.getPosition(userSessionManager.getSessionValue(Constant.Login.CITY_NAME)));
						addPropCitySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
								try {
									getLocationList(cityResponse.getJSONObject(position).getString(Constant.City.CITY_ID));
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(getActivity(),((JSONObject) cityResponse.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(cityArrayAdapter,"city");
						notifyAdapter(locationAdapter,"location");
						//notifyAdapter(landmarkAdapter);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		cityListWebserviceClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,stateId);
	}

	//This method is used to get the List of area Form the Area list Web Service
	public void getLocationList(String cityId) {
		WebserviceClient locationListWebserviceClient = new WebserviceClient(MyPropertyAddActivity.this,areaListOfCityService);
		locationListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				areaOfCityResponse = (JSONArray) response;
				try {
					if (areaOfCityResponse != null && !((JSONObject) areaOfCityResponse.get(0)).has(Constant.Area.API_STATUS)) {
						addPropLocationSpinner.setEnabled(true);
						locationAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, JSONUtils.getList(areaOfCityResponse, Constant.Area.AREA_NAME));
						locationAdapter.setDropDownViewResource(R.layout.spinner_text);
						locationAdapter.insert("Select", 0);
						addPropLocationSpinner.setAdapter(locationAdapter);
					} else {
						Toast.makeText(getActivity(),((JSONObject) areaOfCityResponse.get(0)).getString(Constant.Area.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(locationAdapter,"location");
						//notifyAdapter(landmarkAdapter);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		locationListWebserviceClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,cityId);
	}

	/**
	 * This method is used to get the List of LandMark of particular Area from
	 * Web Service
	 **/
	/*public void getLandmarkList(String areaId) {
		LandmarkService landmarkService = new LandmarkService();
		WebserviceClient landmarkListWebserviceClient = new WebserviceClient(AddMyPropertyActivity.this,landmarkService);
		landmarkListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				//JSONArray jsonArray = (JSONArray) response;
				landmark1Response = (JSONArray) response;
				try {
					if (landmark1Response != null && !((JSONObject) landmark1Response.get(0)).has(Constant.AddProperty.API_STATUS)) {
						//for (int i = 0; i < jsonArray.length(); i++) {
							landmarkAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text, JSONUtils.getList(landmark1Response, Constant.Landmark.LANDMARK_NAME));
							landmarkAdapter.setDropDownViewResource(R.layout.spinner_text);
							addPropLandmark1Spinner.setAdapter(landmarkAdapter);
							addPropLandmark2Spinner.setAdapter(landmarkAdapter);

							addPropLandmark1Spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
								@Override
								public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
									if (addPropLandmark1Spinner.getSelectedItem().equals("other")) {
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
						//}

					} else {
						Toast.makeText(getActivity(),((JSONObject) landmark1Response.get(0)).getString(Constant.AddProperty.API_MESSAGE),Toast.LENGTH_LONG).show();
						notifyAdapter(landmarkAdapter);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		landmarkListWebserviceClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,areaId);
	}*/

	//on fragment resume set the title of fragment
	@Override
	public void onResume() {
		super.onResume();
		// Set title
		this.getActivity().getActionBar().setTitle("Add Property");
	}
	
	//Method for Setting the Height of the ListView dynamically. Hack to fix
	//the issue of not showing all the items of the ListView when placed inside a ScrollView
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null)
			return;

		int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
		int totalHeight = 0;
		View view = null;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			view = listAdapter.getView(i, view, listView);
			if (i == 0)
				view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));

			view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			totalHeight += view.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = 750;//totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	//get the List of area
	public void getAreaList() {
		final JSONArray jsonArray = CreateJsonArrayFileIntoCache.readAreaListJsonData(getActivity().getApplicationContext());
		try {
			if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.PreferredBrokers.API_STATUS)){
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					brokerMap.put(jsonObject.getString(Constant.Area.AREA_NAME),jsonObject.getInt(Constant.Area.AREA_ID));
					brokerNames.add(jsonObject.getString(Constant.Area.AREA_NAME));
				}
				if (brokerArrayAdapter == null) {
					brokerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_list_item, brokerNames);
					brokerListview.setAdapter(brokerArrayAdapter);
				} else {
					brokerArrayAdapter.notifyDataSetChanged();
					flag_loading = false;
				}
				brokerListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
				setListViewHeightBasedOnChildren(brokerListview);
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	//get the List of all broker
	public void getAllBrokerList(int page) {
		BrokerAllService allBrokerService = new BrokerAllService();
		WebserviceClient allBrokerWebserviceClient = new WebserviceClient(MyPropertyAddActivity.this,allBrokerService);
		allBrokerWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.PreferredBrokers.API_STATUS)){
						for (int i = 1; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							brokerMap.put(jsonObject.getString(Constant.AllBrokers.FIRST_NAME),jsonObject.getInt(Constant.AllBrokers.BROKER_ID));
							brokerNames.add(jsonObject.getString(Constant.AllBrokers.FIRST_NAME)+" "+jsonObject.getString(Constant.AllBrokers.LAST_NAME));
						}
						if (brokerArrayAdapter == null) {
							brokerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_list_item, brokerNames);
							brokerListview.setAdapter(brokerArrayAdapter);
						} else {
							brokerArrayAdapter.notifyDataSetChanged();
							flag_loading = false;
						}
						brokerListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
						setListViewHeightBasedOnChildren(brokerListview);
						brokerListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
						brokerListview.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {}
						});
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		});
		allBrokerWebserviceClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,String.valueOf(page));
	}
	
	//get Preferred Or Both Broker List PreferredBrokerService Add the broker
	//list into the select broker layout screen in this also store the checked
	//broker Id into the brokerIdArray
	public void getPreferredBothBroker(int pageNumber) {
		WebserviceClient preferredBrokerwebservice = new WebserviceClient(MyPropertyAddActivity.this,preferredBrokersService);
		preferredBrokerwebservice.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object res) {
				final JSONArray jsonArray = (JSONArray) res;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.PreferredBrokers.API_STATUS)) {
						for (int i = 1; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							brokerMap.put(jsonObject.getString(Constant.PreferredBrokers.FIRST_NAME) + " "+ jsonObject.getString(Constant.PreferredBrokers.LAST_NAME),jsonObject.getInt(Constant.PreferredBrokers.BROKER_ID));
							brokerNames.add(jsonObject.getString(Constant.PreferredBrokers.FIRST_NAME) + " "+ jsonObject.getString(Constant.PreferredBrokers.LAST_NAME));
						}
						if (brokerArrayAdapter == null) {
							brokerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_list_item,brokerNames);
							brokerListview.setAdapter(brokerArrayAdapter);
						} else {
							brokerArrayAdapter.notifyDataSetChanged();
							flag_loading = false;
						}

						brokerListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
						brokerListview.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {}
						});

						brokerListview.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
						setListViewHeightBasedOnChildren(brokerListview);
					} else {
						Toast.makeText(getActivity(),((JSONObject) jsonArray.get(0)).getString(Constant.PreferredBrokers.API_MESSAGE),Toast.LENGTH_LONG).show();
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		});
		preferredBrokerwebservice.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userSessionManager.getSessionValue(Constant.Login.USER_ID));
	}
	 
	//call group list web service to get group name and set into list view
	public void getGroupList() {
		WebserviceClient groupListwebservice = new WebserviceClient(MyPropertyAddActivity.this, groupListService);
		groupListwebservice.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object res) {
				final JSONArray jsonArray = (JSONArray) res;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.PreferredBrokers.API_STATUS)) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							brokerMap.put(jsonObject.getString(Constant.GroupList.GROUP_NAME),jsonObject.getInt(Constant.GroupList.PGID));
							brokerNames.add(jsonObject.getString(Constant.GroupList.GROUP_NAME));
						}
						if (brokerArrayAdapter == null) {
							brokerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_list_item,brokerNames);
							brokerListview.setAdapter(brokerArrayAdapter);
						} else {
							brokerArrayAdapter.notifyDataSetChanged();
							flag_loading = false;
						}
						brokerListview.setChoiceMode(ListView.GONE);
						brokerListview.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
								try {
									getGroupMemberList(String.valueOf(jsonArray.getJSONObject(arg2).get(Constant.GroupList.PGID)));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						setListViewHeightBasedOnChildren(brokerListview);
					} else {
						Toast.makeText(getActivity(),((JSONObject) jsonArray.get(0)).getString(Constant.PreferredBrokers.API_MESSAGE),Toast.LENGTH_LONG).show();
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		});
		groupListwebservice.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userSessionManager.getSessionValue(Constant.Login.USER_ID));
	}

	//get the list of TP scheme Web Service for TPScheme This web service is used to set the TPScheme Spinner
	public void getTpSchem() {
		final JSONArray jsonArray = CreateJsonArrayFileIntoCache.readTPSchemeListJsonData(getActivity().getApplicationContext());
		try {
			if (jsonArray != null) {
				ArrayAdapter<String> tpSchemeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text,JSONUtils.getList(jsonArray, Constant.TPSchemesListing.TITLE));
				tpSchemeAdapter.setDropDownViewResource(R.layout.spinner_text);
				addPropTPSchemeSpinner.setAdapter(tpSchemeAdapter);
				addPropTPSchemeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View view, int position, long arg3) {
						try {
							tpSchemeId = jsonArray.getJSONObject(position).getString(Constant.TPSchemesListing.TP_ID);
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

	//Call Service To get Nominee List add nominee List to the Nominee Layout,also store the checked nominee Id into the nomineeIdArray
	public void getNomineeList() {
		WebserviceClient webServiceClient = new WebserviceClient(MyPropertyAddActivity.this, nomineesListService);
		webServiceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				MainFragmentActivity.nomineeResponse = jsonArray;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.NomineeList.API_STATUS)) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							nomineeMap.put(jsonObject.getString(Constant.NomineeList.TITLE),jsonObject.getString(Constant.NomineeList.NOMINEE_ID));
							nomineeNames.add(jsonObject.getString(Constant.NomineeList.TITLE));
						}
						if (nomineeArrayAdapter == null) {
							nomineeArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_list_item,nomineeNames);
							nomineeListView.setAdapter(nomineeArrayAdapter);
						}
						nomineeListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
						setListViewHeightBasedOnChildren(nomineeListView);
					} else {
						Toast.makeText(getActivity(),((JSONObject) jsonArray.get(0)).getString(Constant.NomineeList.API_MESSAGE),Toast.LENGTH_LONG).show();
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		});
		webServiceClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userSessionManager.getSessionValue(Constant.Login.USER_ID));
	}

	//Facility Service Pass Value of Checked Facility CheckBok
	public void getFacilityList() {
		JSONArray jsonArray = CreateJsonArrayFileIntoCache.readFacilityListJsonData(getActivity().getApplicationContext());
		facilityIdArray = new ArrayList<Integer>();
		try {
			if (jsonArray != null) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					//Dynamically create Facility CheckBox 
					LinearLayout ll = new LinearLayout(getActivity().getApplicationContext());
			        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30, 30);
	                layoutParams.setMargins(5, 0 , 5, 0);
	                layoutParams.gravity = Gravity.CENTER;
					ImageView facilityIcon = new ImageView(getActivity().getApplicationContext());
					Picasso.with(getActivity()).load(jsonObject.getString(Constant.Facility.IMAGE_PATH)).into(facilityIcon);
					facilityIcon.setLayoutParams(layoutParams);
					
					facilityCheck = new CheckBox(getActivity());
					facilityCheck.setId(jsonObject.getInt(Constant.Facility.FACILITY_ID));
					facilityCheck.setTextColor(Color.BLACK);
					facilityCheck.setButtonDrawable(R.xml.custom_checkbox);
					facilityCheck.setText(jsonObject.getString(Constant.Facility.TITLE));
					facilityCheck.setEms(10);
					
					ll.addView(facilityIcon);
					ll.addView(facilityCheck);
					addPropFacilityLayout.addView(ll);
					//store checked checkbox Facility Id to the FacilityIdArray
					facilityCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							if (isChecked)
								facilityIdArray.add((Integer) buttonView.getId());// store facility ID to facilityIdArray
							else
								facilityIdArray.remove((Integer) buttonView.getId());
						}
					});
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	//dialog for add new landmark call AddnewLandmark Web service
	/*private void addNewLandmarkDialogBox() {
		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.activity_landmark_popup);
		dialog.setTitle("Add Landmark");
		final EditText landmarkNameEditBox = (EditText) dialog.findViewById(R.id.landmarkEditBox);
		//on click of send button send the new landmark
		Button send = (Button) dialog.findViewById(R.id.landmarkSendButton);
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if (landmarkNameEditBox.length() == 0) {
						landmarkNameEditBox.setError("Enter Landmark Name");
					} else {
						Boolean stateBoolean = ((JSONObject) stateResponse.get(0)).has(Constant.AddNewLandmark.API_STATUS);
						Boolean cityBoolean = ((JSONObject) cityResponse.get(0)).has(Constant.AddNewLandmark.API_STATUS);
						Boolean areaBoolean = ((JSONObject) areaOfCityResponse.get(0)).has(Constant.AddNewLandmark.API_STATUS);
						if (!stateBoolean && !cityBoolean && !areaBoolean) {
							JSONObject stateObject = stateResponse.getJSONObject(addPropStateSpinner.getSelectedItemPosition());
							JSONObject cityObject = cityResponse.getJSONObject(addPropCitySpinner.getSelectedItemPosition());
							JSONObject areaOfCityObject = areaOfCityResponse.getJSONObject(addPropLocationSpinner.getSelectedItemPosition());

							String nameNumber = userSessionManager.getSessionValue(Constant.Login.FIRST_NAME) + " "
									+ userSessionManager.getSessionValue(Constant.Login.LAST_NAME) + ","
									+ userSessionManager.getSessionValue(Constant.Login.PHONE_NUMBER);

							addNewLandmarkService = new AddNewLandmarkService();
							WebserviceClient addNewLandmarkWebClientService = new WebserviceClient(getActivity(),addNewLandmarkService);
							addNewLandmarkWebClientService.setResponseListner(new ResponseListner() {
								@Override
								public void handleResponse(Object response) {
									JSONArray jsonArray = (JSONArray) response;
									if (jsonArray != null) {
										try {
											JSONObject jsonObject = jsonArray.getJSONObject(0);
											Toast.makeText(getActivity(),jsonObject.getString(Constant.AddNewLandmark.API_MESSAGE),Toast.LENGTH_LONG).show();
											dialog.cancel();
										} catch (JSONException e) {
											e.printStackTrace();
										}
									}
								}
							});
							addNewLandmarkWebClientService.executeOnExecutor(
											AsyncTask.THREAD_POOL_EXECUTOR,
											areaOfCityObject.getString(Constant.Area.AREA_ID),
											areaOfCityObject.getString(Constant.Area.DISTRICT_ID), 
											cityObject.getString(Constant.City.CITY_ID), 
											stateObject.getString(Constant.State.STATEID), 
											landmarkNameEditBox.getText().toString(), nameNumber);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		//on click close the dialog box
		Button cancel = (Button) dialog.findViewById(R.id.landmarkCancelButton);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		dialog.show();
	}*/

	
	//Check address is null or not
	private boolean checkAddress() {
		if (addPropAddressEditBox != null) {
			if (addPropAddressEditBox.getText().length() == 0 && addPropAddressEditBox.isShown()) {
				addPropAddressEditBox.setError("Please Fill Address");
				return true;
			}
		}
		return false;
	}
	
	//Check Bes is null or not
	private boolean checkBed() {
		if (addPropFlatBedsSpinner != null) {
			if (addPropFlatBedsSpinner.getSelectedItem().equals("0") && addPropFlatBedsSpinner.isShown()) {
				Toast.makeText(getActivity(), "Please select bed", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	//check the Rent box is null or not
	private boolean checkRent() {
		if (addPropOptionSpinner.getSelectedItem().equals("Rent")) {
			if (addPropPriceRentEditBox != null) {
				if (addPropPriceRentEditBox.getText().length() == 0 && addPropPriceRentEditBox.isShown()) {
					addPropPriceRentEditBox.setError("Please Fill Rent");
					return true;
				}
			}
		}
		return false;
	}

	//check price if null or not
	private boolean checkPrice() {
		if (addPropOptionSpinner.getSelectedItem().equals("Sale")) {
			if (addPropFlatTotalPriceEditBox != null) {
				if (addPropFlatTotalPriceEditBox.getText().length() == 0 && addPropFlatTotalPriceEditBox.isShown()) {
					addPropFlatTotalPriceEditBox.setError("Please Fill Price");
					return true;
				}
			}
		}
		return false;
	}
	
	//Check location
	private boolean checkLocation() {
		if(addPropLocationSpinner.getSelectedItem() != null) {
			if(addPropLocationSpinner.getSelectedItem().equals("Select") && addPropLocationSpinner.isShown()) {
				Toast.makeText(getActivity(), "Please select Loaction", Toast.LENGTH_LONG).show();
				return true;
			} else if(addPropLocationSpinner.getSelectedItem().equals("No location found")) {
				Toast.makeText(getActivity(), "No loaction found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	//Check property type
	private boolean checkPropertyType() {
		if(addPropPropTypeSpinner.getSelectedItem().equals(" Select ") && addPropPropTypeSpinner.isShown()) {
			Toast.makeText(getActivity(), "Please seelct Property Type", Toast.LENGTH_LONG).show();
			return true;
		}
		return false;
	}
	//Check Property option
	private boolean checkPropertyOption() {
		if(addPropOptionSpinner.getSelectedItem().equals("Select")){
			Toast.makeText(getActivity(), "Please select option", Toast.LENGTH_LONG).show();
			return true;
		}
		return false;
	}

	//this method call when user select area and get the list of broker of particular area
	private void getAreaWiseBroketList(String areaId) {
		FindAgentService findAgentService = new FindAgentService();
		WebserviceClient findAgentWebserviceClient = new WebserviceClient(MyPropertyAddActivity.this, findAgentService);
		findAgentWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.FindAgent.API_STATUS)) {
						//jsonArray.remove(0);
						JSONUtils.getList(jsonArray, Constant.FindAgent.BROKER_ID);
						selectedBrokerId = StringUtils.join(JSONUtils.getList(jsonArray, Constant.FindAgent.BROKER_ID),",");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		findAgentWebserviceClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"AddNewProperty", areaId,userSessionManager.getSessionValue(Constant.Login.USER_ID));
		// return areaWiseBrokerId;
	}

	//get the list of comma separeted Broker
	private String getCommaSepretedBrokerId() {
		checked = "";
		String brokerID = "";
		int cntChoice = brokerListview.getCount();
		SparseBooleanArray sparseBooleanArray = brokerListview.getCheckedItemPositions();

		for (int i = 0; i < cntChoice; i++) {
			if (sparseBooleanArray.get(i) == true) {
				checked += brokerListview.getItemAtPosition(i).toString();
				if (i != sparseBooleanArray.size())
					checked += ",";
			}
		}

		String[] selectedBrokerName = checked.split(",");
		for (int i = 0; i < selectedBrokerName.length; i++) {
			brokerID += brokerMap.get(selectedBrokerName[i]);

			if (i != selectedBrokerName.length - 1)
				brokerID += ",";
		}
		return brokerID;
	}

	//get the list of group member
	public void getGroupMemberList(String groupID) {
		groupMemberMap.clear();
		groupMemberNames.clear();
		GroupMemberListService groupMemberListService = new GroupMemberListService();
		WebserviceClient groupMemberListWebserviceClient = new WebserviceClient(MyPropertyAddActivity.this,groupMemberListService);

		groupMemberListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null && !((JSONObject)jsonArray.get(0)).has(Constant.GroupList.API_STATUS)){
						System.out.println("Group Json ====> "+jsonArray);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							System.out.println("Group Json object===> "+jsonObject);
							groupMemberMap.put(jsonObject.getString(Constant.GroupList.FIRST_NAME)+" "+jsonObject.getString(Constant.GroupList.LAST_NAME),jsonObject.getString(Constant.GroupList.BROKER_ID));
							groupMemberNames.add(jsonObject.getString(Constant.GroupList.FIRST_NAME)+" "+jsonObject.getString(Constant.GroupList.LAST_NAME));
						}
						
						LayoutInflater li = LayoutInflater.from(getActivity());
						View promptsView = li.inflate(R.layout.activity_groupmember_list_popup_window, null);
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
						alertDialogBuilder.setView(promptsView);

						final ListView groupMemberListView = (ListView) promptsView.findViewById(R.id.groupMemberListview);
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_list_item, groupMemberNames);
						groupMemberListView.setAdapter(adapter);
						groupMemberListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

						//set default checked list item
						String[] smsToBroker = (groupMemberChecked).split(",");

						//Map<String, String> brokerInvartMap = new
						//HashMap<String, String>();
						//brokerInvartMap = MapUtils.invertMap(groupMemberMap);

						for (int j = 0; j < smsToBroker.length; j++) {
							groupMemberListView.setItemChecked(groupMemberNames.indexOf(smsToBroker[j]), true);
						}

						// set dialog message
						alertDialogBuilder.setCancelable(true).setTitle("Select Broker").setPositiveButton("Save", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								groupMemberBrokerId = "";
								groupMemberChecked = "";
	
								int cntChoice = groupMemberListView.getCount();
								SparseBooleanArray sparseBooleanArray = groupMemberListView.getCheckedItemPositions();
	
								for (int i = 0; i < cntChoice; i++) {
									if (sparseBooleanArray.get(i) == true) {
										groupMemberChecked += groupMemberListView.getItemAtPosition(i).toString();
										if (i != sparseBooleanArray.size())
											groupMemberChecked += ",";
									}
								}
								String[] selectedBrokerName = (groupMemberChecked).split(",");
								for (int i = 0; i < selectedBrokerName.length; i++) {
									groupMemberBrokerId += groupMemberMap.get(selectedBrokerName[i]);
									if (i != selectedBrokerName.length - 1)
										groupMemberBrokerId += ",";
								}
								System.out.println("group Check broker id====> "+groupMemberBrokerId);
							}
						}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {}
					});
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
						// show it
						alertDialog.show();
					} else {
						Toast.makeText(getActivity(),((JSONObject) jsonArray.get(0)).getString(Constant.GroupList.API_MESSAGE),Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		groupMemberListWebserviceClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userSessionManager.getSessionValue(Constant.Login.USER_ID), groupID);
	}
	
	//clear google map fragment
	@Override
	public void onDestroy() {
		 super.onDestroy();
		 SupportMapFragment f = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.googleMap);
		 if (f != null) 
			 getFragmentManager().beginTransaction().remove(f).commit();
	}
	
	//Notify adapter
	private void notifyAdapter(ArrayAdapter<String> adapter, String spinnerString) {
		if(adapter != null) {
			adapter.clear();
			if(spinnerString.equals("city")) {
				adapter.insert("No city found", 0);
				addPropCitySpinner.setEnabled(false);
			} else if(spinnerString.equals("location")) {
				adapter.insert("No location found", 0);
				addPropLocationSpinner.setEnabled(false);
			}
			adapter.notifyDataSetChanged();
		}
	}
	
	//SEt default image 
	private void setDefauleImage(ImageView imageView) {
		imageView.setImageResource(R.drawable.no_image);
	}
	
	//Delete scrop photo directory
	private void deleteScropImageDirectory() {
		if(myDir != null && myDir.exists()) {
			if (myDir.isDirectory()) {
		        String[] children = myDir.list();
		        for (int i = 0; i < children.length; i++) {
		            new File(myDir, children[i]).delete();
		        }
		    }
		} else {
			myDir.mkdirs();
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
			
			if(marker != null)
				marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())));
		
			if(location != null) {
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
				addPropLatitudeEditBox.setText(latitude);
				addPropLongitudeEditBox.setText(longitude);
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
	
	//Remove google map fragment when click on done or cancle button
	private void removeGoogleMapFragment() {
		SupportMapFragment f = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fgm_addProp_googleMap);
		 if (f != null) 
			 getFragmentManager().beginTransaction().remove(f).commit();
	}
}	