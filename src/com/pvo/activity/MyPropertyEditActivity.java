package com.pvo.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.MediaColumns;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
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
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
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
import com.pvo.user.service.FindByPropertyIdService;
import com.pvo.user.service.GroupListService;
import com.pvo.user.service.GroupMemberListService;
import com.pvo.user.service.LandmarkService;
import com.pvo.user.service.MyPropertyEditService;
import com.pvo.user.service.NomineesListService;
import com.pvo.user.service.PreferredBrokersService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.pvo.util.JSONUtils;
import com.squareup.picasso.Picasso;

//Edit Property 
public class MyPropertyEditActivity extends PVOFragment {
	
	protected static final CharSequence PUBLIC_INFORMATION_HEADING = "Public Information";
	protected static final CharSequence NOMINEE_INFORMATION_HEADING = "Nominee";
	protected static final CharSequence FLAT_INFORMATION_HEADING = "Property (Flat) Related";
	protected static final CharSequence PRICE_INFORMATION_HEADING = "Price / Budget Related";
	protected static final CharSequence EXTRA_INFORMATION_HEADING = "Extra Information";
	protected static final CharSequence BROKER_INFORMATION_HEADING = "Select Broker";
	protected static final CharSequence FACILITY_INFORMATION_HEADING = "Facility";
	protected static final CharSequence OPTION_INFORMATION_HEADING = "Option";
	protected static final CharSequence NOTES_INFORMATION_HEADING = "Notes";
	
	private JSONObject propertyDetailJsonObject;
	//Different Layout
	private RelativeLayout editPropIconBarLayout;
	private RelativeLayout editPropMainContainer;
	private LinearLayout editPropPublicInfoLayout;
	private LinearLayout editPropNomineeLayout;
	private LinearLayout editPropFaltLayout;
	private LinearLayout editPropPriceLayout;
	private LinearLayout editPropExtraInfoLayout;
	private LinearLayout editPropSelectBrokLayout;
	private LinearLayout editPropFacilityLayout;
	private LinearLayout editPropPriceSaleLayout;

	//Public Infromation Controller
	private Spinner editPropPublishOptionSpinner;
	private Spinner editPropPropTypeSpinner;
	private Spinner editPropOptionSpinner;
	private EditText editPropAddressEditBox;
	private TextView editPropAddress;
	private EditText editPropPostCodeEditBox;
	private Spinner editPropStateSpinner;
	private TextView editPropState;
	private Spinner editPropCitySpinner;
	private TextView editPropCity;
	private Spinner editPropLocationSpinner;
	private TextView editPropLocation;
	private EditText editPropLongitudeEditBox;
	private EditText editPropLatitudeEditBox;
	private Spinner editPropPropOccupSpinner;
	private TextView editPropOccupName;
	private EditText editPropOccupNameEditBox;
	private EditText editPropOccupDetailEditBox;
	private EditText editPropReleseDateEditBox;
	
	//text view for display error message
	private TextView cityErrorMsg;
	
	//Nominee Information Controller
	private String nomineeListOf = "";
	private ListView editPropNomineeListView;
	private ArrayAdapter<String> editNomineeArrayAdapter;
	private List<String> editNomineeNames = new ArrayList<String>();
	private Map<String,String> editPropNomineeMap;
	private String editPropNomineeId;
	
	//Property (Flat) Related
	private LinearLayout editPropBedLayout;
	private LinearLayout editPropTPSchemeLayout;
	private LinearLayout editPropFloorLayout;
	private LinearLayout editPropFurnishLayout;
	private LinearLayout editPropAreaLayout;
	private LinearLayout editPropPlotAreaLayout;
	private LinearLayout editPropConstructionLayout;
	private LinearLayout editPropBuildingTypeLayout;
	private LinearLayout editPropSellingPriceLayout;
	private LinearLayout editPropTotalPriceLyout;
	private LinearLayout editPropDastawage;
	private LinearLayout editPropFlatImageLayout;
	private Spinner editPropFlatBedsSpinner;
	private TextView editPropFlatBeds;
	private Spinner editPropFlatFurnishOptionSpinner;
	private EditText editPropFlatFurnishDetailEditBox;
	private Spinner editPropFlatFloorSpinner;
	private Spinner editPropFlatBuildingTypeSpinner;
	private EditText editPropFlatAreaEditBox;
	private Button editPropFlatDefaultImgButton;
	private Button editPropFlatImage2Button;
	private Button editPropFlatImage3Button;
	private Button editPropFlatImage4Button;
	private Button editPropFlatImage5Button;
	private ImageView editPropImage1ImageView;
	private ImageView editPropImage2ImageView;
	private ImageView editPropImage3ImageView;
	private ImageView editPropImage4ImageView;
	private ImageView editPropImage5ImageView;
	private EditText editPropFlatPlotAreaEditBox;
	private TextView editPropFlatConstructionArea;
	private EditText editPropFlatConstructionAreaEditBox;
	private Spinner editPropTPSchemeSpinner;
	private Spinner editPropNASpinner;
	private EditText editPropPriceTransferFeeseSaleEditBox;
	private EditText editPropPriceParkingChargesSaleEditBox;
	private EditText editPropPriceAECAudaLegalSaleEditBox;
	private TextView editPropFlatAreaHintTv;
	private TextView editPropFlatPlotAreaHintTv;
	private TextView editPropFlatConstructionAreaHintTv;

	//TP Scheme Web Srvice
	private String tpSchemeId;
	
	//CheckBox For Zone Layout	
	private Spinner editPropFlatZoneSpinner;
	private ArrayAdapter< String> zoneAdapter;

	//Price /Budget Related 
	private EditText editPropPriceMaintenanceEditBox;
	private EditText editPropRentEditBox;
	private EditText editPropPriceRentDepositeEditBox;
	private TextView editPropPriceRent;
	private EditText editPropFlatSellPriceEditBox;
	private EditText editPropFlatTotalPriceEditBox;
	private TextView editPropFlatTatalPrice;
	private EditText editPropFlatDastawageEditBox;
	private LinearLayout editPropPriceRentLayout;
	private LinearLayout editPropPriceRentMaintenanceLayout;
	private LinearLayout editPropRentDepositeLayout;
	
	//Extra Information Controller
	private Spinner editPropExtraInfoPurposeSpinner;
	private Spinner editPropExtraInfoWhomToLetSpinner;
	private EditText editPropExtraInfoWhomeToletOther;
	private Spinner editPropExtraInfoBuiltYearSpinner;
	private EditText editPropExtraInfoCommentEditBox;
	private EditText editPropExtraInfoHint2;
	private Button editPropFlatFarmImageButton;
	private LinearLayout editPropExtraInfoPurposeLayout; 
	private LinearLayout editPropExtraInfoWhomeToLayout;
	private LinearLayout editPropExtraInfoBuilrYearLayout;
	private LinearLayout editPropExtraInfoTermLayout;
	private LinearLayout editPropExtraInfoHintKeyowrdLayout;
	private LinearLayout editPropImageButtonLayout;
	private LinearLayout editPropExtraInfoKeyowrdLayout;
	private LinearLayout editPropExtraInfoCommentLayout;
	
	//Select Broker
	private PreferredBrokersService preferredBrokersService;
	private GroupListService groupListService;
	
	private ListView brokerListview;
	private ArrayAdapter<String> brokerArrayAdapter;
	private List<String> brokerNames = new ArrayList<String>();
	private String selectedBrokerId="";
	private String areaWiseBrokerId = "";
	private Map<String,String> groupMemberMap;
	private List<String> groupMemberNames = new ArrayList<String>();
	private String groupMemberChecked = "";
	private String groupMemberBrokerId = "";
	private Map<String,Integer> brokerMap;
	private String checked = "";
	private boolean flag_loading;
	private int pageCount = 1;
	private String brokerListOf = "";
	
	//Facility
	private CheckBox facilityCheck;
	private ArrayList<Integer> facilityIdArray;
	private String facilityFlag = "";
	
	//Term Checkbox 
	private CheckBox editPropTermsNaviSharatCheck;
	private CheckBox editPropTermsJuniSharatCheck;
	private CheckBox editPropTermsKhetiCheck;
	private CheckBox editPropTermsPRASHACheck;
	private CheckBox editPropTermsDisputeCheck;
	private CheckBox editPropTermsShreeSarkarCheck;
	private CheckBox editPropTermsDoNotKnowCheck;
	private Spinner editPropExtraInfoFramHouseRoadTouchSpinner;
	
	//Button Save,Next,Previous,cancel This button is used to move the next layout or previous
	private Button editPropSaveButton;
	private Button editPropNextButton;
	private Button editPropPreviousButton;
	private Button editPropCancleButton;

	//Google Map to display the map and set the property Latitude and Longitude
	private GoogleMap googleMap;
	private Marker googleMarker;

	//State List Web Service
	private JSONArray stateResponse;
	
	//City List Web Service
	private CityListService cityListService;
	private JSONArray cityResponse;
	private ArrayAdapter<String> cityAdapter; 
	
	//Landmark **/
	private LandmarkService landmarkService;
	private JSONArray landmark1Response;
	private ArrayAdapter<String> landmarkAdapter;
	
	//Nominee List Web Service
	private NomineesListService nomineesListService;
	
	//AddProperty service
	private MyPropertyEditService editPropertyService;
	
	//Area List Of City Web Service	
	private AreaListOfCityService areaListOfCityService;
	private JSONArray areaOfCityResponse;
	private ArrayAdapter<String> locationAdapter;
	
	//Plot And Bunglow Type
	private String plotType;
	private String bunglowType;
	
	///Get Stored User Id From User SessionManager 
	private UserSessionManager userSessionManager;

	//this is used set the tab Index of the Current Layout and Image Icon
	private int activeTabIndex = 0;
	
	//This is used for Image Selection 
	//private String editPropDefaultImageValue;
	//private String editPropImage2Value;
	//private String editPropImage3Value;
	//private String editPropImage4Value;
	//private String editPropImage5Value;
	private int image1ButtonClick;
	private int image2ButtonClick;
	private int image3ButtonClick;
	private int image4ButtonClick;
	private int image5ButtonClick;
	private TextView editPropFlatDefaultImgPath;
	private TextView editPropFlatImage2Path;
	private TextView editPropFlatImage3Path;
	private TextView editPropFlatImage4Path;
	private TextView editPropFlatImage5Path;
	
	private ArrayAdapter< String> publishingOptionAdapter;
	private ArrayAdapter< String> naStatusAdapter;
	private ArrayList<String> onRoadList ;
	private ArrayList<String> bedList;
	private ArrayAdapter< String> propertyOccupacyAdapter;
	private ArrayList<String> furnishOptionList;
	private ArrayList<String> floorList;
	private ArrayList<String> buildingTypeList;
	private ArrayAdapter< String> purposeAdapter;
	private ArrayList<String> years;
	private Bundle propertyIdIntent;
	private ArrayList<String> propertyTypeOptionList;
	private JSONArray facilityID;
	private JSONArray nomineeID;
	
	//private File imageFile;
	private File myDir;
	private HashMap<String, String> imageNameMap;
	private final String PROPERTY_IMAGE = "/pvo_property_images";
	private Boolean image1Flag = true;
	private Boolean image2Flag = true;
	private Boolean image3Flag = true;
	private Boolean image4Flag = true;
	private Boolean image5Flag = true;
	private String image1Url;
	private String image2Url;
	private String image3Url;
	private String image4Url;
	private String image5Url;
	
	
	//set layout content view 
	public MyPropertyEditActivity() {
		setContentView(R.layout.activity_myproperty_edit);
	}

	//Oncreate Method	
	@Override
	public void init(Bundle savedInstanceState) {
		
		myDir = new File(Environment.getExternalStorageDirectory() + PROPERTY_IMAGE);
        //myDir.mkdirs();
		deleteScropImageDirectory();
		imageNameMap = new HashMap<String, String>();
		storeScropImage(BitmapFactory.decodeResource(getResources(), R.drawable.no_image),"NoImage.jpeg");
		
        //This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		propertyIdIntent = getArguments();
		
		//get Login User_Id from stored Session
		userSessionManager = new UserSessionManager(getActivity());
		
		//Error Message textview
		cityErrorMsg 				 = (TextView) findViewById(R.id.cityErrorMsg);
		//Different Layout of Add property
		editPropNomineeLayout		 = (LinearLayout) findViewById(R.id.editPropNomineeLayout);
		editPropExtraInfoLayout  	 = (LinearLayout) findViewById(R.id.editPropExtraInfoLayout);
		editPropSelectBrokLayout 	 = (LinearLayout) findViewById(R.id.editPropSelectBrokLayout);
		editPropFacilityLayout 	 	 = (LinearLayout) findViewById(R.id.editPropFacilityLayout);
		editPropPriceSaleLayout  	 = (LinearLayout) findViewById(R.id.editPropPriceSaleLayout);
		editPropMainContainer 	 	 = (RelativeLayout) findViewById(R.id.editPropMainContainer);
		editPropFlatDefaultImgPath 	 = (TextView) findViewById(R.id.editPropFlatDefaultImgPath);
		editPropFlatImage2Path 		 = (TextView) findViewById(R.id.editPropFlatImage2Path);
		editPropFlatImage3Path   	 = (TextView) findViewById(R.id.editPropFlatImage3Path);
		editPropFlatImage4Path 		 = (TextView) findViewById(R.id.editPropFlatImage4Path);
		editPropFlatImage5Path       = (TextView) findViewById(R.id.editPropFlatImage5Path);
		editPropImage1ImageView 	 = (ImageView) findViewById(R.id.editPropImage1ImageView);
		editPropImage2ImageView 	 = (ImageView) findViewById(R.id.editPropImage2ImageView);
		editPropImage3ImageView 	 = (ImageView) findViewById(R.id.editPropImage3ImageView);
		editPropImage4ImageView      = (ImageView) findViewById(R.id.editPropImage4ImageView);
		editPropImage5ImageView 	 = (ImageView) findViewById(R.id.editPropImage5ImageView);
		editPropNomineeListView 	 = (ListView) findViewById(R.id.editPropNomineeListView);
		
		//call the getStateList method fist time otherwise set adapter as previous
		editPropStateSpinner	= (Spinner) findViewById(R.id.editPropStateSpinner);
		editPropState 			= (TextView) findViewById(R.id.editPropState);
		editPropState.setText(Html.fromHtml("State"+"<sup><font size=5 color=red>*</font></sup>"));
		
		//************ Public Information Controller ID *******
		editPropPublicInfoLayout 		= (LinearLayout) findViewById(R.id.editPropPublicInfoLayout);
		editPropPublishOptionSpinner 	= (Spinner) findViewById(R.id.editPropPublishOptionSpinner);
		editPropPropTypeSpinner = (Spinner)findViewById(R.id.editPropPropTypeSpinner);
		editPropOptionSpinner = (Spinner) findViewById(R.id.editPropOptionSpinner);
		editPropAddress 		= (TextView) findViewById(R.id.editPropAddress);
		editPropAddressEditBox 	= (EditText) findViewById(R.id.editPropAddressEditBox);
		editPropAddress.setText(Html.fromHtml("Address"+"<sup><font size=5 color=red>*</font></sup>"));
		editPropPostCodeEditBox 	 = (EditText) findViewById(R.id.editPropPostCodeEditBox);
		editPropCity 				 = (TextView) findViewById(R.id.editPropCity);
		editPropCity.setText(Html.fromHtml("City"+"<sup><font size=5 color=red>*</font></sup>"));
		editPropCitySpinner 		 = (Spinner) findViewById(R.id.editPropCitySpinner);
		editPropLocationSpinner 	= (Spinner) findViewById(R.id.editPropLocationSpinner);
		editPropLocation 			= (TextView) findViewById(R.id.editPropLocation);
		editPropLocation.setText(Html.fromHtml("Location"+"<sup><font size=5 color=red>*</font></sup>"));
		editPropLongitudeEditBox 	= (EditText) findViewById(R.id.editPropLongitudeEditBox);
		editPropLatitudeEditBox		= (EditText) findViewById(R.id.editPropLatitudeEditBox);
		editPropOccupName 			= (TextView) findViewById(R.id.editPropOccupName);
		editPropOccupNameEditBox 	= (EditText) findViewById(R.id.editPropOccupNameEditBox);
		editPropOccupDetailEditBox 	= (EditText) findViewById(R.id.editPropOccupDetailEditBox);
		editPropReleseDateEditBox	 = (EditText) findViewById(R.id.editPropReleseDateEditBox);
		editPropPropOccupSpinner 	= (Spinner) findViewById(R.id.editPropPropOccupSpinner);
		
		//*********Property (Flat) Related Information Controller ID  Sale/Rent *******
		editPropFaltLayout 			= (LinearLayout) findViewById(R.id.editPropFaltLayout);
		editPropBedLayout 			= (LinearLayout) findViewById(R.id.editPropBedLayout);
		editPropTPSchemeLayout 		= (LinearLayout) findViewById(R.id.editPropTPSchemeLayout);
		editPropFloorLayout 		= (LinearLayout) findViewById(R.id.editPropFloorLayout);
		editPropFurnishLayout 		= (LinearLayout) findViewById(R.id.editPropFurnishLayout);
		editPropAreaLayout 			= (LinearLayout) findViewById(R.id.editPropAreaLayout);
		editPropPlotAreaLayout 		= (LinearLayout) findViewById(R.id.editPropPlotAreaLayout);
		editPropConstructionLayout	= (LinearLayout) findViewById(R.id.editPropConstructionLayout);
		editPropBuildingTypeLayout 	= (LinearLayout) findViewById(R.id.editPropBuildingTypeLayout);
		editPropSellingPriceLayout 	= (LinearLayout) findViewById(R.id.editPropSellingPriceLayout);
		editPropTotalPriceLyout 	= (LinearLayout) findViewById(R.id.editPropTotalPriceLyout);
		editPropDastawage 			= (LinearLayout) findViewById(R.id.editPropDastawage);
		editPropFlatImageLayout 	= (LinearLayout) findViewById(R.id.editPropFlatImageLayout);
		editPropFlatAreaHintTv 		= (TextView) findViewById(R.id.editPropFlatAreaHintTv);
		editPropFlatPlotAreaHintTv 	= (TextView) findViewById(R.id.editPropFlatPlotAreaHintTv);
		editPropFlatConstructionAreaHintTv = (TextView) findViewById(R.id.editPropFlatConstructionAreaHintTv);
		editPropPriceTransferFeeseSaleEditBox = (EditText) findViewById(R.id.editPropPriceTransferFeeseSaleEditBox);
		editPropPriceAECAudaLegalSaleEditBox = (EditText) findViewById(R.id.editPropPriceAECAudaLegalSaleEditBox);
		editPropFlatFurnishDetailEditBox 	= (EditText)findViewById(R.id.editPropFlatFurnishDetailEditBox);
		editPropFlatAreaEditBox				= (EditText)findViewById(R.id.editPropFlatAreaEditBox);
		editPropFlatPlotAreaEditBox 		= (EditText) findViewById(R.id.editPropFlatPlotAreaEditBox);
		editPropFlatConstructionArea 		= (TextView) findViewById(R.id.editPropFlatConstructionArea);
		editPropFlatConstructionAreaEditBox = (EditText) findViewById(R.id.editPropFlatConstructionAreaEditBox);
		editPropFlatSellPriceEditBox 		= (EditText) findViewById(R.id.editPropFlatSellPriceEditBox);
		editPropFlatTotalPriceEditBox 		= (EditText) findViewById(R.id.editPropFlatTatalPriceEditBox);
		editPropFlatTatalPrice 				= (TextView) findViewById(R.id.editPropFlatTatalPrice);
		editPropFlatDastawageEditBox		= (EditText) findViewById(R.id.editPropFlatDastawageEditBox);
		editPropFlatDefaultImgButton		= (Button)findViewById(R.id.editPropFlatDefaultImgButton);
		editPropFlatImage2Button			= (Button)findViewById(R.id.editPropFlatImage2Button);
		editPropFlatImage3Button			= (Button)findViewById(R.id.editPropFlatImage3Button);
		editPropFlatImage4Button			= (Button)findViewById(R.id.editPropFlatImage4Button);
		editPropFlatImage5Button			= (Button)findViewById(R.id.editPropFlatImage5Button);
		editPropTPSchemeSpinner 			= (Spinner)findViewById(R.id.editPropTPSchemeSpinner);
		
		//======>> Term <<=======
		editPropTermsNaviSharatCheck 		= (CheckBox) findViewById(R.id.editPropTermsNaviSharatCheck);
		editPropTermsJuniSharatCheck 		= (CheckBox) findViewById(R.id.editPropTermsJuniSharatCheck);
		editPropTermsKhetiCheck 			= (CheckBox) findViewById(R.id.editPropTermsKhetiCheck);
		editPropTermsPRASHACheck 			= (CheckBox) findViewById(R.id.editPropTermsPRASHACheck);
		editPropTermsDisputeCheck 			= (CheckBox) findViewById(R.id.editPropTermsDisputeCheck);
		editPropTermsShreeSarkarCheck 		= (CheckBox) findViewById(R.id.editPropTermsShreeSarkarCheck);
		editPropTermsDoNotKnowCheck 		= (CheckBox) findViewById(R.id.editPropTermsDoNotKnowCheck);
		
		editPropNASpinner 					= (Spinner)findViewById(R.id.editPropNASpinner);
		editPropFlatZoneSpinner 			= (Spinner) findViewById(R.id.editPropFlatZoneSpinner);
		editPropFlatBeds = (TextView) findViewById(R.id.editPropFlatBeds);
		editPropFlatBeds.setText(Html.fromHtml("Beds"+"<sup><font size=5 color=red>*</font></sup>"));
		editPropFlatBedsSpinner = (Spinner)findViewById(R.id.editPropFlatBedsSpinner);
		editPropFlatFurnishOptionSpinner = (Spinner)findViewById(R.id.editPropFlatFurnishOptionSpinner);
		editPropFlatFloorSpinner = (Spinner)findViewById(R.id.editPropFlatFloorSpinner);
		editPropFlatBuildingTypeSpinner = (Spinner)findViewById(R.id.editPropFlatBuildingTypeSpinner);
		
		//******* Price/Budget Related Information Controller ID ******** 
		editPropPriceLayout 				= (LinearLayout) findViewById(R.id.editPropPriceLayout);
		editPropPriceRentLayout 			= (LinearLayout) findViewById(R.id.editPropPriceRentLayout);
		editPropPriceRentMaintenanceLayout 	= (LinearLayout) findViewById(R.id.editPropPriceRentMaintenanceLayout);
		editPropRentDepositeLayout 			= (LinearLayout) findViewById(R.id.editPropRentDepositeLayout);
		editPropPriceMaintenanceEditBox		= (EditText)findViewById(R.id.editPropPriceMaintenanceEditBox);
		editPropPriceRent 					= (TextView) findViewById(R.id.editPropPriceRent);
		editPropRentEditBox			= (EditText)findViewById(R.id.editPropPriceRentEditBox);
		editPropPriceRentDepositeEditBox	= (EditText)findViewById(R.id.editPropPriceRentDepositeEditBox);
		editPropPriceParkingChargesSaleEditBox = (EditText) findViewById(R.id.editPropPriceParkingChargesSaleEditBox);
		
		//Extra Information Controller ID
		editPropExtraInfoPurposeLayout 		= (LinearLayout) findViewById(R.id.editPropExtraInfoPurposeLayout);
		editPropExtraInfoWhomeToLayout 		= (LinearLayout) findViewById(R.id.editPropExtraInfoWhomeToLayout);
		editPropExtraInfoBuilrYearLayout 	= (LinearLayout) findViewById(R.id.editPropExtraInfoBuilrYearLayout);
		editPropExtraInfoHintKeyowrdLayout 	= (LinearLayout) findViewById(R.id.editPropExtraInfoHintKeyowrdLayout);
		editPropExtraInfoTermLayout 		= (LinearLayout) findViewById(R.id.editPropExtraInfoTermLayout);
		editPropImageButtonLayout 			= (LinearLayout) findViewById(R.id.editPropImageButtonLayout);
		editPropExtraInfoCommentLayout 		= (LinearLayout) findViewById(R.id.editPropExtraInfoCommentLayout);
		editPropExtraInfoKeyowrdLayout 		= (LinearLayout) findViewById(R.id.editPropExtraInfoKeyowrdLayout);
		
		//Select Broker
		brokerListview = (ListView) findViewById(R.id.editPropBrokerListView);
		editPropExtraInfoPurposeSpinner = (Spinner)findViewById(R.id.editPropExtraInfoPurposeSpinner);
		editPropExtraInfoWhomToLetSpinner	= (Spinner)findViewById(R.id.editPropExtraInfoWhomToLetSpinner);
		editPropExtraInfoWhomeToletOther	= (EditText)findViewById(R.id.editPropExtraInfoWhomeToletOther);
		editPropExtraInfoCommentEditBox = (EditText)findViewById(R.id.editPropExtraInfoCommentEditBox);
		editPropExtraInfoHint2 = (EditText) findViewById(R.id.editPropExtraInfoHintEditBox2);
		editPropExtraInfoBuiltYearSpinner = (Spinner)findViewById(R.id.editPropExtraInfoBuiltYearSpinner);
		
		//Button Save,Next,Previous,Cancel this is used to move next layout or Previous Layout
	    editPropSaveButton			= (Button)findViewById(R.id.editPropSaveButton);
	    editPropNextButton			= (Button)findViewById(R.id.editPropNextButton);
	    editPropPreviousButton		= (Button)findViewById(R.id.editPropPreviousButton);
	    editPropCancleButton		= (Button)findViewById(R.id.editPropCancelButton);
	    editPropFlatFarmImageButton = (Button) findViewById(R.id.editPropFlatFarmImageButton);
	    
	    editPropIconBarLayout = (RelativeLayout) findViewById(R.id.editPropIconLayout2);
	    editPropExtraInfoFramHouseRoadTouchSpinner = (Spinner) findViewById(R.id.editPropExtraInfoFramHouseRoadTouchSpinner);
		
		//get the detail of property which you want to update 
		getPropertyDetailById();
		
		//Service for get Particular API Response 
	    editPropertyService		= new MyPropertyEditService();		
	    cityListService 		= new CityListService();
	    nomineesListService 	= new NomineesListService();
	    preferredBrokersService	= new PreferredBrokersService();
	    groupListService		= new GroupListService();
	    areaListOfCityService	= new AreaListOfCityService();
	    landmarkService 		= new LandmarkService();
	    brokerMap				= new HashMap<String, Integer>();
	    groupMemberMap			= new HashMap<String, String>();
	    facilityIdArray			= new ArrayList<Integer>();
	    editPropNomineeMap		= new HashMap<String, String>();

		//==> Public Information Controller ID <<==
		//Fill The Publish Option spinner Using HashMap 1)Public only 2)preferred only 3)Both
		try {
			ArrayList<String> publishingOptionList = new ArrayList<String>();
			publishingOptionList.addAll(SpinnerItem.getPublishinOptionList().keySet());
			publishingOptionAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,publishingOptionList);
			publishingOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
			editPropPublishOptionSpinner.setAdapter(publishingOptionAdapter);
			//End
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Fill the Property Occupacy spinner Using HashMap
		ArrayList<String> propertyOccupacyList = new ArrayList<String>();
		propertyOccupacyList.addAll(SpinnerItem.getPropertyOccupacyList().keySet());
		propertyOccupacyAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,propertyOccupacyList);
		propertyOccupacyAdapter.setDropDownViewResource(R.layout.spinner_text);
		editPropPropOccupSpinner.setAdapter(propertyOccupacyAdapter);
		editPropPropOccupSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(editPropPropOccupSpinner.getSelectedItem().toString().equalsIgnoreCase("Yes")){
					editPropOccupName.setVisibility(View.VISIBLE);
					editPropOccupNameEditBox.setVisibility(View.VISIBLE);
				} else {
					editPropOccupName.setVisibility(View.GONE);
					editPropOccupNameEditBox.setVisibility(View.GONE);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		//END
		
		//Fill the On Road Touch Spinner Using Hash Map 
		onRoadList = new ArrayList<String>();
		onRoadList.addAll(SpinnerItem.getOnRoadList().keySet());
		ArrayAdapter< String> onRoadAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,onRoadList);
		onRoadAdapter.setDropDownViewResource(R.layout.spinner_text);
		editPropExtraInfoFramHouseRoadTouchSpinner.setAdapter(onRoadAdapter);
		//END

		//N.A Layout fill the spinner using the Hash map 
		ArrayList<String> naStatusList=new ArrayList<String>();
		naStatusList.addAll(SpinnerItem.getAddPropNaStatusList().keySet());
		naStatusAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,naStatusList);
		naStatusAdapter.setDropDownViewResource(R.layout.spinner_text);
		editPropNASpinner.setAdapter(naStatusAdapter);
			
		//Zone Layout  Add check box into Layout 
		ArrayList<String> zoneList=new ArrayList<String>();
		zoneList.addAll(SpinnerItem.getAddPropZoneList().keySet());
		zoneAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,zoneList);
		zoneAdapter.setDropDownViewResource(R.layout.spinner_text);
		editPropFlatZoneSpinner.setAdapter(zoneAdapter);
		//END 

		//Fill the Bed Spinner using Hash Map
		bedList = new ArrayList<String>();
		bedList.addAll(SpinnerItem.getAddPropBedList().keySet());
		ArrayAdapter< String> bedAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,bedList);
		bedAdapter.setDropDownViewResource(R.layout.spinner_text);
		editPropFlatBedsSpinner.setAdapter(bedAdapter);
		//END
		
		//Fill the Furnish Option spinner Using Hash map
		furnishOptionList = new ArrayList<String>();
		furnishOptionList.addAll(SpinnerItem.getAddPropFurnishOptionList().keySet());
		ArrayAdapter< String> furnishOptionAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,furnishOptionList);
		furnishOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
		editPropFlatFurnishOptionSpinner.setAdapter(furnishOptionAdapter);
		//End
		
		//Fill the Floor spinner Using Hash map 
		floorList = new ArrayList<String>();
		floorList.addAll(SpinnerItem.getAddPropFloorList().keySet());
		ArrayAdapter< String> floorAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,floorList);
		floorAdapter.setDropDownViewResource(R.layout.spinner_text);
		editPropFlatFloorSpinner.setAdapter(floorAdapter);
		//END
		
		//Fill The Building Type spinner Using Hash Map 1)Low rise 2)High Rise 
		buildingTypeList = new ArrayList<String>();
		buildingTypeList.addAll(SpinnerItem.getAddPropBuildingTypeList().keySet());
		ArrayAdapter< String> buildingTypeAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,buildingTypeList);
		buildingTypeAdapter.setDropDownViewResource(R.layout.spinner_text);
		editPropFlatBuildingTypeSpinner.setAdapter(buildingTypeAdapter);
		//End

		//Set the Text View Text According to the Spinner item Selected
		editPropOptionSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(editPropOptionSpinner.getSelectedItem().equals("Sale"))
					editPropFlatTatalPrice.setText(Html.fromHtml("Total Price"+"<sup><font size=5 color=red>*</font></sup>"));
				else
					editPropPriceRent.setText(Html.fromHtml("Rent"+"<sup><font size=5 color=red>*</font></sup>"));
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		//END
	
		// Fill the Purpose Spinner 1)Any 2)Commercial 3)residential
		ArrayList<String> purposeList = new ArrayList<String>();
		purposeList.addAll(SpinnerItem.getAddPropPurposeList().keySet());
		purposeAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,purposeList);
		purposeAdapter.setDropDownViewResource(R.layout.spinner_text);
		editPropExtraInfoPurposeSpinner.setAdapter(purposeAdapter);
		// END
			
		//set the Whome To let Spinner According to Property Type from Hash map this spinner is different for flat and Shop
		editPropPropTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
					try {
						if((editPropPropTypeSpinner.getSelectedItem().toString().equals("Flat") 
								&& editPropOptionSpinner.getSelectedItem().toString().equals("Rent")
								&& !propertyDetailJsonObject.getString(Constant.AddProperty.PROPERTY_TYPE).equals("Plot")
								&& !propertyDetailJsonObject.getString(Constant.AddProperty.PROPERTY_TYPE).equals("Bunglow"))) {						
							ArrayList<String> whomToLetFlatList = new ArrayList<String>();
							whomToLetFlatList.addAll(SpinnerItem.getAddPropWhomToLetFlatList().keySet());
							ArrayAdapter< String> whomToLetFlatAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,whomToLetFlatList);
							whomToLetFlatAdapter.setDropDownViewResource(R.layout.spinner_text);
							editPropExtraInfoWhomToLetSpinner.setAdapter(whomToLetFlatAdapter);
							//set default value 
							editPropExtraInfoWhomToLetSpinner.setSelection(whomToLetFlatAdapter.getPosition(propertyDetailJsonObject.getString(Constant.MyProperty.WHOM_TO_LET)));
							//set the default value
							editPropExtraInfoWhomeToletOther.setText(propertyDetailJsonObject.getString(Constant.MyProperty.WHOM_TO_LET_OTHER));
							
						}else if(propertyDetailJsonObject.getString(Constant.AddProperty.PROPERTY_TYPE).equals("Shop")
								&& !propertyDetailJsonObject.getString(Constant.AddProperty.PROPERTY_TYPE).equals("Plot")
								&& !propertyDetailJsonObject.getString(Constant.AddProperty.PROPERTY_TYPE).equals("Bunglow")){
							ArrayList<String> whomToLetShopList = new ArrayList<String>();
							whomToLetShopList.addAll(SpinnerItem.getAddPropWhomToLetShopList().keySet());
							ArrayAdapter< String> whomToLetShopAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,whomToLetShopList);
							whomToLetShopAdapter.setDropDownViewResource(R.layout.spinner_text);
							editPropExtraInfoWhomToLetSpinner.setAdapter(whomToLetShopAdapter);
							//set the default selected value
							editPropExtraInfoWhomToLetSpinner.setSelection(whomToLetShopAdapter.getPosition(propertyDetailJsonObject.getString(Constant.MyProperty.WHOM_TO_LET)));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {}
		});

		//Show Other edit box if selected item is "Other"
		editPropExtraInfoWhomToLetSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(editPropExtraInfoWhomToLetSpinner.getSelectedItem().equals("Other"))
					editPropExtraInfoWhomeToletOther.setVisibility(View.VISIBLE);
				else
					editPropExtraInfoWhomeToletOther.setVisibility(View.GONE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		//Fill the Built Year Spinner
		years = new ArrayList<String>();
	    int thisYear = Calendar.getInstance().get(Calendar.YEAR);

	    for (int i = thisYear-64; i <= thisYear; i++){
	        years.add(Integer.toString(i));
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,years);
	    adapter.setDropDownViewResource(R.layout.spinner_text);
	    editPropExtraInfoBuiltYearSpinner.setAdapter(adapter);
	    //END

	    //Remove Current Fragment and set previous fragment
	    editPropCancleButton.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		((MainFragmentActivity)getActivity()).onBackPressed();
			}
	    });
		    
		//this is used for Image selection(Browse)
	    image1ButtonClick=0;
	    image2ButtonClick=0;
	    image3ButtonClick=0;
	    image4ButtonClick=0;
	    image5ButtonClick=0;
		
		//first(Default) Image Selection button
	    editPropFlatDefaultImgButton.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		image1ButtonClick = 1;
	    		doCrop();
	    	}
	    });
	    //END
		
		// second Image Selection button 
		editPropFlatImage2Button.setOnClickListener(new OnClickListener() {
		 @Override
		 public void onClick(View v) {
			image2ButtonClick = 1;
			doCrop();
		 }	
		});
		// END 
		
		 //third Image Selection button
	    editPropFlatImage3Button.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		image3ButtonClick = 1;
	    		doCrop();
	    	}
	    });
	    //END
		
	    //fourth Image Selection button 
	    editPropFlatImage4Button.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		image4ButtonClick = 1;
	    		doCrop();
	    	}
	    });
	    // END
		
	    // 5th Image Selection button
	    editPropFlatImage5Button.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		image5ButtonClick = 1;
	    		doCrop();
	    	}
	    });
	    // END
	    
	    //Extra information Browse button 
	    editPropFlatFarmImageButton.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		image5ButtonClick = 1;
	    		doCrop();
	    	}
	    });
	    //END
	
	    //Save Button Click Event Save the Add Property Detail
		editPropSaveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {	
					if(!checkAddress() && !checkPrice() && !checkRent()) {
						if(editPropPublishOptionSpinner.getSelectedItem().toString().equals("Preferred only")) {
							selectedBrokerId = getCommaSepretedBrokerId();
						} else if(editPropPublishOptionSpinner.getSelectedItem().toString().equals("Group")){
							selectedBrokerId = groupMemberBrokerId;
						}
						
						//Set same image if user not edit  
						if(image1Flag)
							setdefaultImage(image1Url,Constant.AddProperty.IMAGE_1);
						//Set same image if user not edit  
						if(image2Flag)
							setdefaultImage(image2Url,Constant.AddProperty.IMAGE_2);
						//Set same image if user not edit  
						if(image3Flag)
							setdefaultImage(image3Url,Constant.AddProperty.IMAGE_3);
						//Set same image if user not edit  
						if(image4Flag)
							setdefaultImage(image4Url,Constant.AddProperty.IMAGE_4);
						//Set same image if user not edit  
						if(image5Flag)
							setdefaultImage(image5Url,Constant.AddProperty.IMAGE_5);
						
						//get checked nominee id
						int editPropNomineeCntChoice = editPropNomineeListView.getCount();
						String edirPropNomineeChecked = "";
						SparseBooleanArray editPropNomineeSparseBooleanArray = editPropNomineeListView.getCheckedItemPositions();
						for(int i = 0; i < editPropNomineeCntChoice; i++) {
						     if(editPropNomineeSparseBooleanArray.get(i) == true) {
						    	 edirPropNomineeChecked += editPropNomineeListView.getItemAtPosition(i).toString();
						    	 editPropNomineeId = editPropNomineeMap.get(edirPropNomineeChecked);
						     }
						 }
						// END 
						JSONObject stateObject 			= stateResponse.getJSONObject(editPropStateSpinner.getSelectedItemPosition());
						JSONObject cityObject			= cityResponse.getJSONObject(editPropCitySpinner.getSelectedItemPosition());
						JSONObject areaOfCityObject		= areaOfCityResponse.getJSONObject(editPropLocationSpinner.getSelectedItemPosition());	
						
						if(SpinnerItem.getPropertyTypeOptionList().get(editPropPropTypeSpinner.getSelectedItem().toString()).equals("Bunglow"))
							bunglowType = editPropPropTypeSpinner.getSelectedItem().toString();
						if(SpinnerItem.getPropertyTypeOptionList().get(editPropPropTypeSpinner.getSelectedItem().toString()).equals("Plot"))
							plotType = editPropPropTypeSpinner.getSelectedItem().toString();
						
							//call Service to save Property
							WebserviceClient editPropWebserviceClient = new WebserviceClient(MyPropertyEditActivity.this, editPropertyService);
							editPropWebserviceClient.setResponseListner(new ResponseListner() {
								@Override
								public void handleResponse(Object res) {
									final JSONObject response=(JSONObject) res;
									try{
										if(response != null){
											if(String.valueOf(response.getString(Constant.AddProperty.API_STATUS)).equals("1")){
												AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
												alertDialog.setTitle(response.getString(Constant.AddProperty.API_MESSAGE));
												alertDialog.setMessage("1) This Facilities for one time message sending only.\n 2) After 15 Days of posting a property it will automatically be inactived from your account and it will not be searchable to others and it will shown in your &apos;Inactive&apos; folders where you can re-activate it for another 15 days.");
												alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface dialog, int which) {
														deleteScropImageDirectory();
														image1Flag = true;
														image2Flag = true;
														image3Flag = true;
														image4Flag = true;
														image5Flag = true;
														Bundle editPropBundle = new Bundle();
														editPropBundle.putString("Type", "Search");
														MyPropertyListActivity myPropertyActivity = new MyPropertyListActivity();
														myPropertyActivity.setArguments(editPropBundle);
														((MainFragmentActivity)getActivity()).redirectScreenWithoutStack(myPropertyActivity);
														//getActivity().getFragmentManager().popBackStack();
													}
												});
												alertDialog.show();
											}else{
												Toast.makeText(getActivity(), String.valueOf(response.getString(Constant.AddProperty.API_MESSAGE)), 100).show();
											}
										}
									}catch(JSONException e){
										e.printStackTrace();
									}
								}
							});
							
							editPropWebserviceClient.execute(
									userSessionManager.getSessionValue(Constant.Login.USER_ID),//1-user_id
									propertyDetailJsonObject.getString(Constant.MyProperty.PROPERTY_ID),//2-id
									editPropPropTypeSpinner.getSelectedItem().toString(), //3-propertytype
									editPropAddressEditBox.getText().toString(), //4-address
									editPropPostCodeEditBox.getText().toString(), //5-ostcode
									StringUtils.defaultString(areaOfCityObject.getString(Constant.Area.AREA_ID)),//6-cmbarea1-Location 
									"1",//7-countryid
									StringUtils.defaultString(stateObject.getString(Constant.State.STATEID)),//8-stateid
									StringUtils.defaultString(cityObject.getString(Constant.City.CITY_ID)),//9-cityid
									"",//districtObject.getString(Constant.District.DISTRICT_ID),//10-districtid
									editPropOptionSpinner.getSelectedItem().toString(),//11-stroptions- rent/sale
									"0",//StringUtils.defaultString(landmark1Object.getString(Constant.Landmark.LANDMARK_ID)),//12-landmark1
									"0",//StringUtils.defaultString(landmark2Object.getString(Constant.Landmark.LANDMARK_ID)),//13-landmark2
									"",//editProplandmarkOtherEditbox.getText().toString(),//14-landmark1other
									"",//editPropLandmark2Editbox.getText().toString(),//15-landmark2other
									editPropLongitudeEditBox.getText().toString(),//16-longval
									editPropLatitudeEditBox.getText().toString(),//17-latval
									//18-occupancy
									String.valueOf(SpinnerItem.getPropertyOccupacyList().get(editPropPropOccupSpinner.getSelectedItem().toString())),//Property Occupacy
									editPropOccupNameEditBox.getText().toString(),//19-occupacyName
									editPropOccupDetailEditBox.getText().toString(),//20-occupacyDetail
									editPropReleseDateEditBox.getText().toString(),//21-occupacyDate
									editPropFlatSellPriceEditBox.getText().toString(),//22-price
									editPropFlatTotalPriceEditBox.getText().toString(),//23-totalprice
									editPropRentEditBox.getText().toString(),//24-rent
									editPropFlatDastawageEditBox.getText().toString(),//25-dastawage
									editPropPriceRentDepositeEditBox.getText().toString(),//26-rentdeposit
									"SqFoot",//27-areaunit
									editPropExtraInfoPurposeSpinner.getSelectedItem().toString(),//28-cmbpurpose
									editPropFlatAreaEditBox.getText().toString(),//29-minarea
									"",//30-maxarea
									editPropExtraInfoBuiltYearSpinner.getSelectedItem().toString(),//31-yearbuiltup
									editPropExtraInfoCommentEditBox.getText().toString(),//32-comments
									editPropExtraInfoHint2.getText().toString(),//33-hint
									String.valueOf(SpinnerItem.getAddPropBedList().get(editPropFlatBedsSpinner.getSelectedItem().toString())),//34-bed
									String.valueOf(SpinnerItem.getAddPropFurnishOptionList().get(editPropFlatFurnishOptionSpinner.getSelectedItem().toString())),//35-furnishstatus
									editPropFlatFurnishDetailEditBox.getText().toString(),//36-furnishcomment
									String.valueOf(SpinnerItem.getAddPropFloorList().get(editPropFlatFloorSpinner.getSelectedItem().toString())),//37-floor
									String.valueOf(SpinnerItem.getAddPropBuildingTypeList().get(editPropFlatBuildingTypeSpinner.getSelectedItem().toString())),//38-rise
									(editPropExtraInfoWhomToLetSpinner.getSelectedItem() == null) ? "" : editPropExtraInfoWhomToLetSpinner.getSelectedItem().toString(),
									//StringUtils.defaultString(editPropExtraInfoWhomToLetSpinner.getSelectedItem().toString())	,//39-whomtolet
									editPropExtraInfoWhomeToletOther.getText().toString(),//40-whomtoletother
									editPropPriceParkingChargesSaleEditBox.getText().toString(),//41-parking
									"7",//42-frontheight
									"8",//43-attachcommon
									editPropFlatConstructionAreaEditBox.getText().toString(),//44-constructionarea
									StringUtils.defaultIfBlank(bunglowType,""),//45-bunglowtype
									editPropFlatPlotAreaEditBox.getText().toString(),//46-minplotarea
									"",//47-plotarea
									"sqYard",//48-plotareaunit
									StringUtils.defaultIfEmpty(plotType,""),//49-plottype
									String.valueOf(SpinnerItem.getNaStatusList().get(editPropNASpinner.getSelectedItem().toString())),//50-nastatus
									String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(editPropTermsKhetiCheck.isChecked()))),//51-kheti
									String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(editPropTermsNaviSharatCheck.isChecked()))),//52-navisharat
									String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(editPropTermsJuniSharatCheck.isChecked()))),//53-junisharat
									String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(editPropTermsPRASHACheck.isChecked()))),//54-prassap
									String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(editPropTermsDisputeCheck.isChecked()))),//55-dispute
									"",//56-titleclear
									String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(editPropTermsShreeSarkarCheck.isChecked()))),//57-shreesarkar
									String.valueOf(SpinnerItem.getOnRoadList().get(editPropExtraInfoFramHouseRoadTouchSpinner.getSelectedItem().toString())),//58-onroad
									//59-prefopt
									String.valueOf(SpinnerItem.getPublishinOptionList().get(editPropPublishOptionSpinner.getSelectedItem().toString())),//Publishing Option
									editPropPriceMaintenanceEditBox.getText().toString(),//60-maintenance
									editPropPriceTransferFeeseSaleEditBox.getText().toString(),//61-transferfees
									editPropPriceAECAudaLegalSaleEditBox.getText().toString(),//62-aecauda
									StringUtils.defaultIfEmpty(tpSchemeId,""),//63-cmbtpscheme
									editPropFlatZoneSpinner.getSelectedItem().toString(),//64-chkzone
									//String.valueOf(BooleanUtils.toInteger(BooleanUtils.toBoolean(editPropMessageChekBox.isChecked()))),//65-chkmessage
									"1",//65-chkmessage
									"1",//66-chkmail
									//StringUtils.join(nomineeIdArray,","),//67-chk- NomineeId
									StringUtils.join(editPropNomineeId),//67-chk- NomineeId
									StringUtils.defaultString(StringUtils.join(facilityIdArray,","),""),//68-chkfacility
									StringUtils.defaultIfEmpty(selectedBrokerId,""),//StringUtils.join(brokerIdArray,",")//69-chkbroker
									getCropImagePath(Constant.AddProperty.IMAGE_1),//70
									getCropImagePath(Constant.AddProperty.IMAGE_2),//71
									getCropImagePath(Constant.AddProperty.IMAGE_3),//72
									getCropImagePath(Constant.AddProperty.IMAGE_4),//73
									getCropImagePath(Constant.AddProperty.IMAGE_5)//74
									
									
									/*StringUtils.defaultString(editPropDefaultImageValue,""),//69-defaultImage
									StringUtils.defaultString(editPropImage2Value,""), //70-Image1
									StringUtils.defaultString(editPropImage3Value,""), //71-Image2
									StringUtils.defaultString(editPropImage4Value,""), //72-Image3
									StringUtils.defaultString(editPropImage5Value,"") //73-Image4*/
									);
							System.out.println("facilityIdArray==> "+facilityIdArray);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
			
	});

		
	//********* ANKUR *********
	//next Button to move the next Layout 
	/*editPropNextButton.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) { 
		    //Check address edit box is blank
		    if(checkAddress()){ //editPropAddressEditBox.getText().length() == 0 && editPropAddressEditBox.isShown()) {
                activeTabIndex = ((ViewGroup)editPropMainContainer).indexOfChild(editPropPublicInfoLayout);
                editPropIconBarLayout.getChildAt(activeTabIndex).performClick();
            } //Check price.Rent edit box is blank or not
		    else if(checkRent()){//editPropPriceRentEditBox.getText().length() == 0 && editPropPriceRentEditBox.isShown()) {
                activeTabIndex = ((ViewGroup)editPropMainContainer).indexOfChild(editPropPriceLayout);
                editPropIconBarLayout.getChildAt(activeTabIndex).performClick();
            } else if (checkPrice()) {
            	activeTabIndex = ((ViewGroup)editPropMainContainer).indexOfChild(editPropPriceSaleLayout);
                editPropIconBarLayout.getChildAt(activeTabIndex).performClick();
            } //goto next frame 
		    else {
                ++activeTabIndex;
                setBackgroundImageView(editPropIconBarLayout.getChildAt(activeTabIndex));
                editPropIconBarLayout.getChildAt(activeTabIndex).performClick();
            }
		}
	});*/
	
	
	//next Button to move the next Layout
	editPropNextButton.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(checkPropertyType()){//Check property type is not equal to select
				activeTabIndex = ((ViewGroup) editPropMainContainer).indexOfChild(editPropPublicInfoLayout);
				editPropIconBarLayout.getChildAt(activeTabIndex).performClick();
			} else if(checkPropertyOption()) {//Check property option is selected 
				activeTabIndex = ((ViewGroup) editPropMainContainer).indexOfChild(editPropPublicInfoLayout);
				editPropIconBarLayout.getChildAt(activeTabIndex).performClick();
			} else if(checkLocation()) { //Check property type is not equal to "Select"
				activeTabIndex = ((ViewGroup) editPropMainContainer).indexOfChild(editPropPublicInfoLayout);
				editPropIconBarLayout.getChildAt(activeTabIndex).performClick();
			} else if (checkAddress()) {// check Address edit box is blank or not
				activeTabIndex = ((ViewGroup) editPropMainContainer).indexOfChild(editPropPublicInfoLayout);
				editPropIconBarLayout.getChildAt(activeTabIndex).performClick();
			} else if(checkBed()) {
				activeTabIndex = ((ViewGroup) editPropMainContainer).indexOfChild(editPropFaltLayout);
				editPropIconBarLayout.getChildAt(activeTabIndex).performClick();
			} else if (checkRent()) { // check price and rent is blank or not
				activeTabIndex = ((ViewGroup) editPropMainContainer).indexOfChild(editPropPriceLayout);
				editPropIconBarLayout.getChildAt(activeTabIndex).performClick();
			} else if (checkPrice()) { // check price is selected or not blank
				activeTabIndex = ((ViewGroup) editPropMainContainer).indexOfChild(editPropPriceSaleLayout);
				editPropIconBarLayout.getChildAt(activeTabIndex).performClick();
			} else { //goto next screen
				++activeTabIndex;
				setBackgroundImageView(editPropIconBarLayout.getChildAt(activeTabIndex));
				editPropIconBarLayout.getChildAt(activeTabIndex).performClick();
			}
		}
	});
	//END 
		
	//previous Button to move Previous Layout
	editPropPreviousButton.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			--activeTabIndex;
			setBackgroundImageView(editPropIconBarLayout.getChildAt(activeTabIndex));
			editPropIconBarLayout.getChildAt(activeTabIndex).performClick();
		}
	});
	// END
		
	//Bind event to all image icons of left side icon bar
	int totalChild = editPropIconBarLayout.getChildCount();
	for(int i = 0; i < totalChild; i++) {
		View imageView = editPropIconBarLayout.getChildAt(i);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editPropPreviousButton.setVisibility(View.VISIBLE);
				editPropNextButton.setVisibility(View.VISIBLE);
				editPropSaveButton.setVisibility(View.GONE);
				setBackgroundImageView(v);
				TextView header = (TextView) findViewById(R.id.editPropHeaderTv2);
				
			//Set Header according to icon 
			switch (v.getId()) {
				//(1)
			    case R.id.editPropPublicInfoImg:
					header.setText(PUBLIC_INFORMATION_HEADING);
					//Hide previous button for first screen
					editPropPreviousButton.setVisibility(View.GONE);
					setVisibility(editPropPublicInfoLayout);
					activeTabIndex = ((ViewGroup)editPropMainContainer).indexOfChild(editPropPublicInfoLayout);
					break;
				//(2)
			    case R.id.editPropNomineeImg:
			        if(editPropAddressEditBox.getText().length() == 0 && editPropAddressEditBox.isShown()) {
			            editPropNextButton.performClick();
			        } else {
				        header.setText(NOMINEE_INFORMATION_HEADING);
						setVisibility(editPropNomineeLayout);
						activeTabIndex = ((ViewGroup)editPropMainContainer).indexOfChild(editPropNomineeLayout);
						//call the method of nominee list 
                        if(!nomineeListOf.equalsIgnoreCase("Nominee")){
                            nomineeListOf = "Nominee";
                            getNomineeList();
                        }
			        }
					break;
				//(3)
				case R.id.editPropFlatImg:
					if(SpinnerItem.getPropertyTypeOptionList().get(editPropPropTypeSpinner.getSelectedItem().toString()).equals("Plot") || editPropPropTypeSpinner.getSelectedItem().equals("Farm House(Bunglow)"))
						getTpSchemList();

					hideLayoutChild(editPropFaltLayout);//hide all the child layout form particular Layout
					header.setText("Property (" + editPropPropTypeSpinner.getSelectedItem().toString()  + ") Related");
					setVisibility(editPropFaltLayout);
					activeTabIndex = ((ViewGroup)editPropMainContainer).indexOfChild(editPropFaltLayout);
					
					//Flat For Rent
					if(editPropPropTypeSpinner.getSelectedItem().toString().equals("Flat")  && editPropOptionSpinner.getSelectedItem().toString().equals("Rent")){
						editPropBedLayout.setVisibility(View.VISIBLE);
						editPropFurnishLayout.setVisibility(View.VISIBLE);
						editPropFloorLayout.setVisibility(View.VISIBLE);
						editPropBuildingTypeLayout.setVisibility(View.VISIBLE);
						editPropAreaLayout.setVisibility(View.VISIBLE);
						editPropFlatImageLayout.setVisibility(View.VISIBLE);
						editPropFlatAreaHintTv.setText("Sq.Foot");
					} //Flat For Sale
					else if(editPropPropTypeSpinner.getSelectedItem().toString().equals("Flat")  && editPropOptionSpinner.getSelectedItem().toString().equals("Sale")){
						editPropBedLayout.setVisibility(View.VISIBLE);
						editPropFurnishLayout.setVisibility(View.VISIBLE);
						editPropFloorLayout.setVisibility(View.VISIBLE);
						editPropBuildingTypeLayout.setVisibility(View.VISIBLE);
						editPropAreaLayout.setVisibility(View.VISIBLE);
						editPropFlatImageLayout.setVisibility(View.VISIBLE);
						editPropSellingPriceLayout.setVisibility(View.VISIBLE);
						editPropTotalPriceLyout.setVisibility(View.VISIBLE);
						editPropDastawage.setVisibility(View.VISIBLE);
						editPropFlatAreaHintTv.setText("Sq.Foot");
					} //Shop For Rent 
					else if (SpinnerItem.getPropertyTypeOptionList().get(editPropPropTypeSpinner.getSelectedItem().toString()).equals("Shop")  
							&& editPropOptionSpinner.getSelectedItem().toString().equals("Rent")  
							&& !editPropPropTypeSpinner.getSelectedItem().toString().equals("Commercial Bunglow") 
							&& !editPropPropTypeSpinner.getSelectedItem().toString().equals("Corporate House")
							&& !editPropPropTypeSpinner.getSelectedItem().toString().equals("Godown")
							&& !editPropPropTypeSpinner.getSelectedItem().toString().equals("Shades")
							&& !editPropPropTypeSpinner.getSelectedItem().toString().equals("Factory")){
						editPropFloorLayout.setVisibility(View.VISIBLE);
						editPropFurnishLayout.setVisibility(View.VISIBLE);
						editPropAreaLayout.setVisibility(View.VISIBLE);
						editPropBuildingTypeLayout.setVisibility(View.VISIBLE);
						editPropFlatImageLayout.setVisibility(View.VISIBLE);
						editPropFlatAreaHintTv.setText("Sq.Foot");
					} //Shop For Sale
					else if(SpinnerItem.getPropertyTypeOptionList().get(editPropPropTypeSpinner.getSelectedItem().toString()).equals("Shop")
							&& editPropOptionSpinner.getSelectedItem().toString().equals("Sale")
							&& !editPropPropTypeSpinner.getSelectedItem().toString().equals("Commercial Bunglow") 
							&& !editPropPropTypeSpinner.getSelectedItem().toString().equals("Corporate House")
							&& !editPropPropTypeSpinner.getSelectedItem().toString().equals("Godown")
							&& !editPropPropTypeSpinner.getSelectedItem().toString().equals("Shades")
							&& !editPropPropTypeSpinner.getSelectedItem().toString().equals("Factory")){
						editPropFloorLayout.setVisibility(View.VISIBLE);
						editPropFurnishLayout.setVisibility(View.VISIBLE);
						editPropAreaLayout.setVisibility(View.VISIBLE);
						editPropBuildingTypeLayout.setVisibility(View.VISIBLE);
						editPropSellingPriceLayout.setVisibility(View.VISIBLE);
						editPropTotalPriceLyout.setVisibility(View.VISIBLE);
						editPropFlatImageLayout.setVisibility(View.VISIBLE);
						editPropFlatAreaHintTv.setText("Sq.Foot");
					} //Shop for Sale
					else if(SpinnerItem.getPropertyTypeOptionList().get(editPropPropTypeSpinner.getSelectedItem().toString()).equals("Shop") 
							&&  editPropOptionSpinner.getSelectedItem().toString().equals("Sale")){
						editPropFloorLayout.setVisibility(View.VISIBLE);
						editPropFurnishLayout.setVisibility(View.VISIBLE);
						editPropAreaLayout.setVisibility(View.VISIBLE);
						editPropPlotAreaLayout.setVisibility(View.VISIBLE);
						editPropConstructionLayout.setVisibility(View.VISIBLE);
						editPropBuildingTypeLayout.setVisibility(View.VISIBLE);
						editPropSellingPriceLayout.setVisibility(View.VISIBLE);
						editPropTotalPriceLyout.setVisibility(View.VISIBLE);
						editPropFlatImageLayout.setVisibility(View.VISIBLE);
						editPropFlatAreaHintTv.setText("Sq.Foot");
						editPropFlatPlotAreaHintTv.setText("Sq.Foot");
						editPropFlatConstructionAreaHintTv.setText("Sq.Foot");
					} //Shop for Rent 
					else if(SpinnerItem.getPropertyTypeOptionList().get(editPropPropTypeSpinner.getSelectedItem().toString()).equals("Shop") 
							&& editPropOptionSpinner.getSelectedItem().toString().equals("Rent")){
						editPropFloorLayout.setVisibility(View.VISIBLE);
						editPropFurnishLayout.setVisibility(View.VISIBLE);
						editPropAreaLayout.setVisibility(View.VISIBLE);
						editPropPlotAreaLayout.setVisibility(View.VISIBLE);
						editPropConstructionLayout.setVisibility(View.VISIBLE);
						editPropBuildingTypeLayout.setVisibility(View.VISIBLE);
						editPropFlatImageLayout.setVisibility(View.VISIBLE);
						editPropFlatAreaHintTv.setHint("Sq.Foot");
						editPropFlatPlotAreaHintTv.setHint("Sq.Foot");
						editPropFlatConstructionAreaHintTv.setHint("Sq.Foot");
					} //farm House(Bunglow) rent 
					else if(editPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow")
							&& editPropOptionSpinner.getSelectedItem().toString().equals("Rent")){
						editPropTPSchemeLayout.setVisibility(View.VISIBLE);
						editPropAreaLayout.setVisibility(View.VISIBLE);
						editPropConstructionLayout.setVisibility(View.VISIBLE);
						editPropFlatConstructionArea.setText("Construction Area(for farm hourse only)");
						editPropFlatImageLayout.setVisibility(View.VISIBLE);
						editPropFlatAreaHintTv.setText("Sq.Foot");
						editPropFlatConstructionAreaHintTv.setText("Sq.Foot");
					} //new add farm House(Bunglow)  Sale 
					else if(editPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)")
							&& editPropOptionSpinner.getSelectedItem().toString().equals("Sale")){
						editPropTPSchemeLayout.setVisibility(View.VISIBLE);
						editPropAreaLayout.setVisibility(View.VISIBLE);
						editPropConstructionLayout.setVisibility(View.VISIBLE);
						editPropFlatConstructionArea.setText("Construction Area(for farm hourse only)");
						editPropTotalPriceLyout.setVisibility(View.VISIBLE);
						editPropFlatImageLayout.setVisibility(View.VISIBLE);
						editPropFlatAreaHintTv.setText("Sq.Foot");
						editPropFlatConstructionAreaHintTv.setText("Sq.Foot");
					} //Bunglow for sale And Rent **/
					else if(SpinnerItem.getPropertyTypeOptionList().get(editPropPropTypeSpinner.getSelectedItem().toString()).equals("Bunglow") 
							&& !editPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)")){
						editPropBedLayout.setVisibility(View.VISIBLE);
						editPropFurnishLayout.setVisibility(View.VISIBLE);
						editPropPlotAreaLayout.setVisibility(View.VISIBLE);
						editPropConstructionLayout.setVisibility(View.VISIBLE);
						
						if(editPropOptionSpinner.getSelectedItem().toString().equals("Sale"))
							editPropTotalPriceLyout.setVisibility(View.VISIBLE);
						else 
							editPropTotalPriceLyout.setVisibility(View.GONE);
						
						editPropFlatImageLayout.setVisibility(View.VISIBLE);
						editPropFlatPlotAreaHintTv.setText("Sq.Foot");
						editPropFlatConstructionAreaHintTv.setText("Sq.Foot");
					} //Plot For Rent 
					else if(SpinnerItem.getPropertyTypeOptionList().get(editPropPropTypeSpinner.getSelectedItem().toString()).equals("Plot") 
							&& editPropOptionSpinner.getSelectedItem().toString().equals("Rent")){
						editPropTPSchemeLayout.setVisibility(View.VISIBLE);
						editPropAreaLayout.setVisibility(View.VISIBLE);
						editPropConstructionLayout.setVisibility(View.VISIBLE);
						editPropFlatConstructionArea.setText("Construction Area(for farm hourse only)");
						editPropFlatImageLayout.setVisibility(View.VISIBLE);
						editPropFlatAreaHintTv.setText("Sq.Yard");
						editPropFlatConstructionAreaHintTv.setText("Sq.Yard");
					} //Plot For Sale 
					else{
						editPropTPSchemeLayout.setVisibility(View.VISIBLE);
						editPropAreaLayout.setVisibility(View.VISIBLE);
						editPropConstructionLayout.setVisibility(View.VISIBLE);
						editPropFlatConstructionArea.setText("Construction Area(for farm hourse only)");
						editPropTotalPriceLyout.setVisibility(View.VISIBLE);
						editPropFlatImageLayout.setVisibility(View.VISIBLE);
						editPropFlatAreaHintTv.setText("Sq.Yard");
						editPropFlatConstructionAreaHintTv.setText("Sq.Yard");
					}
					break;
				
				//(4)Price Budge Related
				case R.id.editPropPriceImg:
					hideLayoutChild(editPropPriceLayout);
					header.setText(PRICE_INFORMATION_HEADING);
					setVisibility(editPropPriceLayout);
					activeTabIndex = ((ViewGroup)editPropMainContainer).indexOfChild(editPropPriceLayout);
					//Rent 
					if (editPropOptionSpinner.getSelectedItem().toString().equals("Rent") 
							&& !editPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)") 
							&& !SpinnerItem.getPropertyTypeOptionList().get(editPropPropTypeSpinner.getSelectedItem().toString()).equals("Plot")) {
						editPropPriceRentMaintenanceLayout.setVisibility(View.VISIBLE);
						editPropPriceRentLayout.setVisibility(View.VISIBLE);
						editPropRentDepositeLayout.setVisibility(View.VISIBLE);
					} //Sale 
					else if(editPropOptionSpinner.getSelectedItem().toString().equals("Sale")) {
						editPropPriceRentMaintenanceLayout.setVisibility(View.VISIBLE);
						editPropPriceSaleLayout.setVisibility(View.VISIBLE);
					} //Farm house(Bunglow) Rent 
					else if(editPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)")
							&& editPropOptionSpinner.getSelectedItem().toString().equals("Rent")){
						editPropPriceRentMaintenanceLayout.setVisibility(View.VISIBLE);
						editPropPriceRentLayout.setVisibility(View.VISIBLE);
					} //Plot And Rent 
					else if(SpinnerItem.getPropertyTypeOptionList().get(editPropPropTypeSpinner.getSelectedItem().toString()).equals("Plot")
							&& editPropOptionSpinner.getSelectedItem().toString().equals("Rent")){
						editPropPriceRentMaintenanceLayout.setVisibility(View.VISIBLE);
						editPropPriceRentLayout.setVisibility(View.VISIBLE);
					}
					break;
				//(5)
				case R.id.editPropExtraInfoImg:
				    if(editPropRentEditBox.getText().length() == 0 && editPropRentEditBox.isShown()) {
				        editPropNextButton.performClick();
				    } else { 
					    hideLayoutChild(editPropExtraInfoLayout);
						header.setText(EXTRA_INFORMATION_HEADING);
						setVisibility(editPropExtraInfoLayout);
						activeTabIndex = ((ViewGroup)editPropMainContainer).indexOfChild(editPropExtraInfoLayout);
						//Flat For Rent Layout
						if(editPropPropTypeSpinner.getSelectedItem().toString().equals("Flat") && editPropOptionSpinner.getSelectedItem().toString().equals("Rent")){
							editPropExtraInfoPurposeLayout.setVisibility(View.VISIBLE);
							editPropExtraInfoWhomeToLayout.setVisibility(View.VISIBLE);
							editPropExtraInfoBuilrYearLayout.setVisibility(View.VISIBLE);
							editPropExtraInfoHintKeyowrdLayout.setVisibility(View.VISIBLE);
							editPropExtraInfoCommentLayout.setVisibility(View.VISIBLE);
						} //Flat For Sale 
						else if(editPropPropTypeSpinner.getSelectedItem().toString().equals("Flat") && editPropOptionSpinner.getSelectedItem().toString().equals("Sale")){
							editPropExtraInfoPurposeLayout.setVisibility(View.VISIBLE);
							editPropExtraInfoBuilrYearLayout.setVisibility(View.VISIBLE);
							editPropExtraInfoHintKeyowrdLayout.setVisibility(View.VISIBLE);
							editPropExtraInfoCommentLayout.setVisibility(View.VISIBLE);
						} //Shop For Rent And Sale 
						else if(SpinnerItem.getPropertyTypeOptionList().get(editPropPropTypeSpinner.getSelectedItem().toString()).equals("Shop")){
							editPropExtraInfoWhomeToLayout.setVisibility(View.VISIBLE);
							editPropExtraInfoBuilrYearLayout.setVisibility(View.VISIBLE);
							editPropExtraInfoHintKeyowrdLayout.setVisibility(View.VISIBLE);
							editPropExtraInfoCommentLayout.setVisibility(View.VISIBLE);
						} // Bunglow For Sale and Rent 
						else if(SpinnerItem.getPropertyTypeOptionList().get(editPropPropTypeSpinner.getSelectedItem().toString()).equals("Bunglow") && !editPropPropTypeSpinner.getSelectedItem().toString().equals("Farm House(Bunglow)")){
							editPropExtraInfoHintKeyowrdLayout.setVisibility(View.VISIBLE);	
							editPropExtraInfoKeyowrdLayout.setVisibility(View.VISIBLE);
						} //Plot For Sale And Rent 
						else if(SpinnerItem.getPropertyTypeOptionList().get(editPropPropTypeSpinner.getSelectedItem().toString()).equals("Plot")){
							editPropExtraInfoPurposeLayout.setVisibility(View.VISIBLE);
							editPropExtraInfoTermLayout.setVisibility(View.VISIBLE);
							editPropExtraInfoHintKeyowrdLayout.setVisibility(View.VISIBLE);
							editPropExtraInfoCommentLayout.setVisibility(View.VISIBLE);
							editPropImageButtonLayout.setVisibility(View.VISIBLE);
						} //Farm House For Rent And sale
						else{
							editPropExtraInfoPurposeLayout.setVisibility(View.VISIBLE);
							editPropExtraInfoTermLayout.setVisibility(View.VISIBLE);
							editPropExtraInfoHintKeyowrdLayout.setVisibility(View.VISIBLE);
							editPropExtraInfoCommentLayout.setVisibility(View.VISIBLE);
							editPropImageButtonLayout.setVisibility(View.VISIBLE);
						}
				    }
					break;
					
				case R.id.editPropBrokerImg:
					header.setText(BROKER_INFORMATION_HEADING);
					setVisibility(editPropSelectBrokLayout);
					activeTabIndex = ((ViewGroup)editPropMainContainer).indexOfChild(editPropSelectBrokLayout);
					
					//call the getBrokerList method according to spinner item selected 
					editPropPublishOptionSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
							brokerNames.clear();
							brokerMap.clear();
							pageCount = 1;
							brokerListview.clearChoices();
							
							if(editPropPublishOptionSpinner.getSelectedItem().equals("Area") && !brokerListOf.equalsIgnoreCase("Area")) {
								brokerListOf = "Area";
								getAreaList();;
							} else if(editPropPublishOptionSpinner.getSelectedItem().equals("Preferred only") && !brokerListOf.equalsIgnoreCase("Preferred")) {
								brokerListOf = "Preferred";
								getPreferredBothBroker(pageCount);
							} else if(editPropPublishOptionSpinner.getSelectedItem().equals("Group") && !brokerListOf.equalsIgnoreCase("Group")){
								brokerListOf = "Group";
								getGroupList();
							} else if(editPropPublishOptionSpinner.getSelectedItem().equals("All Broker") && !brokerListOf.equalsIgnoreCase("All Broker")) {
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
					brokerListview.setOnScrollListener(new OnScrollListener(){
						@Override
						public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
							if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
				                if(flag_loading == false) {
				                    flag_loading = true;
				                    if(brokerListOf.equals("Preferred only"))
				                    		getPreferredBothBroker(++pageCount);
				                }
				            }
						}
						@Override
						public void onScrollStateChanged( AbsListView view, int scrollState) {}								
					});
					//END
					break;
					
				case R.id.editPropFacilityImg:
					//get the slected broker id list
					if(editPropPublishOptionSpinner.getSelectedItem().toString().equals("Area")) {
						getAreaWiseBroketList(getCommaSepretedBrokerId());
					}
					
					if(!facilityFlag.equalsIgnoreCase("Facility")){
						facilityFlag = "Facility";
						getFacilityList();
					}
					editPropSaveButton.setVisibility(View.VISIBLE);
					editPropNextButton.setVisibility(View.GONE);
					
					header.setText(FACILITY_INFORMATION_HEADING);
					setVisibility(editPropFacilityLayout);
					activeTabIndex = ((ViewGroup)editPropMainContainer).indexOfChild(editPropFacilityLayout);
					break;
				default:
					break;
			}
		}
     });
	}
		
	//Calender For Relese Date Edit Box onclick of Edit Box Display Calender
	editPropReleseDateEditBox.setFocusable(false);
	editPropReleseDateEditBox.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(editPropReleseDateEditBox.requestFocus()) {
			   getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			}
			new DatePickerFragment((EditText) v).show(getActivity().getSupportFragmentManager(), "datePicker");
		}
	});
	//END
}
	//process Response For State Web Service
	@Override
	public void processResponse(Object response) {}

	//Hide all the layout and set visible true for given layout
	private void setVisibility(View visibalLinerLayout) {
		this.hideAllLayout();
		visibalLinerLayout.setVisibility(View.VISIBLE);
	}
	//END
	
	//Hide all the layout and remove background color
	private void hideAllLayout() {
		int totalChild = editPropMainContainer.getChildCount();
		for(int i = 0; i < totalChild; i++) {
			editPropMainContainer.getChildAt(i).setVisibility(View.GONE);
		}
	}
	//END
		
	//Hide Particular View Of Layout	
	private void hideLayoutChild(View hideLinerLayout){
	  int childView =  ((ViewGroup) hideLinerLayout).getChildCount();
	  for (int i = 0; i < childView; i++) {
		  ((ViewGroup) hideLinerLayout).getChildAt(i).setVisibility(View.GONE);
	  }
	}
	//END
	
	//Remove background color of all image view/icons Set white background color for given view
	private void setBackgroundImageView(View v) {
		reSetAllImageView();
		v.setBackgroundColor(Color.WHITE);
	}
	//END 
	
	//Remove background of all image view/icons 
	private void reSetAllImageView() {
		int totalChild = editPropIconBarLayout.getChildCount();
		for(int i = 0; i < totalChild; i++) {
			editPropIconBarLayout.getChildAt(i).setBackgroundColor(0);
		}		
	}
	//END 

	
	
	//Get RealPath from URI this is used in Browse Image from gallery
	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaColumns.DATA };
		@SuppressWarnings("deprecation")
		android.database.Cursor cursor = getActivity().managedQuery(contentUri, proj, null,null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	
	//This is used for set the Location of the google map
	/*private void setUpMapIfNeeded(Double lat,Double lng) {
		if (googleMap == null) {
			
			googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.editPropMap)).getMap();
			if (googleMap != null) {
				drawMarker(lat,lng);
				googleMap.setOnMapClickListener(new OnMapClickListener() {
					@Override
					public void onMapClick(LatLng latlng) {
						drawMarker(latlng);
					}
				});
			}
		}
	}*/
	//END
	
	//This is used for set the Location of the google map
		private void setUpMapIfNeeded(Double lat,Double lng) {
			if (googleMap == null) {
				googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.googleMap)).getMap();
				if (googleMap != null) {
					googleMap.setMyLocationEnabled(true);
					
					googleMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)));
					CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(lat,lng));
					googleMap.moveCamera(center);
					CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
					googleMap.animateCamera(zoom);
					
					googleMap.setOnMapClickListener(new OnMapClickListener() {
						@Override
						public void onMapClick(LatLng latlng) {
							drawMarker(latlng);
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
		editPropLatitudeEditBox.setText(String.valueOf(latlng.latitude));
		editPropLongitudeEditBox.setText(String.valueOf(latlng.longitude));
	}
	//END
	
	//get the list of state 
	public void getStateList(){
		stateResponse = CreateJsonArrayFileIntoCache.readStateListJsonData(getActivity().getApplicationContext());
		try {
			if(stateResponse != null && !((JSONObject) stateResponse.get(0)).has(Constant.City.API_STATUS)){
				ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text,JSONUtils.getList(stateResponse,Constant.State.STATENAME));
				stateAdapter.setDropDownViewResource(R.layout.spinner_text);
				
				//stateAdapter.
				editPropStateSpinner.setAdapter(stateAdapter);
				
				//This is used for default item selected 
				int selectedPosition = 0;
				for(int i=0; i < stateResponse.length(); i++) {
					JSONObject jsonObj = stateResponse.getJSONObject(i);
					if(jsonObj.getInt(Constant.State.STATEID) == Integer.parseInt(propertyDetailJsonObject.getString(Constant.State.STATEID))) {
						selectedPosition = i;
					}
				}
				//Set Default Selection 
				editPropStateSpinner.setSelection(selectedPosition);
				editPropStateSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0,View arg1, int position, long arg3) {
						try {
							getCityList(stateResponse.getJSONObject(position).getString(Constant.State.STATEID));
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
		
	//Web Service to get the City name List and add into the City Spinner
	public void getCityList(String stateId){
		WebserviceClient  cityListWebserviceClient = new WebserviceClient(MyPropertyEditActivity.this, cityListService);
		cityListWebserviceClient.setResponseListner(new ResponseListner() {
		@Override
		public void handleResponse(Object response) {
				cityResponse = (JSONArray)response;
				try{
					if (cityResponse != null && !((JSONObject) cityResponse.get(0)).has(Constant.City.API_STATUS)) {
						editPropCitySpinner.setEnabled(true);
						cityAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,JSONUtils.getList(cityResponse,Constant.City.CITY_NAME));
						cityAdapter.setDropDownViewResource(R.layout.spinner_text);
						editPropCitySpinner.setAdapter(cityAdapter);

						//This is used for default item selected 
						int selectedPosition = 0;
						for(int i=0; i < cityResponse.length(); i++) {
							JSONObject jsonObj = cityResponse.getJSONObject(i);
							if(jsonObj.getInt(Constant.City.CITY_ID) == Integer.parseInt(propertyDetailJsonObject.getString(Constant.AddProperty.CITY_ID))) 
								selectedPosition = i;
						}
						//Default item selected
						editPropCitySpinner.setSelection(selectedPosition);
						editPropCitySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0,View arg1, int position, long arg3) {
								try{
									cityErrorMsg.setVisibility(View.GONE);
									getLocationList(cityResponse.getJSONObject(position).getString(Constant.City.CITY_ID));
								}catch(Exception e){
									e.printStackTrace();
								}
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(getActivity(),((JSONObject) cityResponse.get(0)).getString(Constant.City.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(cityAdapter,"city");
						notifyAdapter(locationAdapter,"location");
					}
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		});
		cityListWebserviceClient.execute(stateId);		
	}
	//This method is used to get the List of area Form the Area list Web Service
	public void getLocationList(String cityId){
		WebserviceClient  locationListWebserviceClient=new WebserviceClient(MyPropertyEditActivity.this, areaListOfCityService);
		locationListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				//final JSONArray jsonArray = (JSONArray)response;
				areaOfCityResponse = (JSONArray)response;
				try{
					if (areaOfCityResponse != null && !((JSONObject) areaOfCityResponse.get(0)).has(Constant.Area.API_STATUS)) {
						editPropLocationSpinner.setEnabled(true);
						locationAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,JSONUtils.getList(areaOfCityResponse,Constant.Area.AREA_NAME));
						locationAdapter.setDropDownViewResource(R.layout.spinner_text);
						editPropLocationSpinner.setAdapter(locationAdapter);
						//set default selected 
						editPropLocationSpinner.setSelection(locationAdapter.getPosition(propertyDetailJsonObject.getString(Constant.MyProperty.AREA_NAME)));
						
						//on location spinner item selected event get the list of landmark
						editPropLocationSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0,View arg1, int position, long arg3) {}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});		
					} else {
						Toast.makeText(getActivity(),((JSONObject) areaOfCityResponse.get(0)).getString(Constant.Area.API_MESSAGE), Toast.LENGTH_LONG).show();
						notifyAdapter(locationAdapter,"location");
					}
					
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		});
		locationListWebserviceClient.execute(cityId);	
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    this.getActivity().getActionBar().setTitle("Edit Property");
	}
	
	//get the List of area
	public void getAreaList(){
		final JSONArray jsonArray = CreateJsonArrayFileIntoCache.readAreaListJsonData(getActivity().getApplicationContext());
		try {		
			if (jsonArray != null){// && !((JSONObject) jsonArray.get(0)).has(Constant.PreferredBrokers.API_STATUS)) {
				for (int i = 0; i <jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					brokerMap.put(jsonObject.getString(Constant.Area.AREA_NAME),jsonObject.getInt(Constant.Area.AREA_ID));
					brokerNames.add(jsonObject.getString(Constant.Area.AREA_NAME));
				}
				
				if(brokerArrayAdapter == null ) {
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
				
				//set default checked list item 
				String[] smsToBroker = (propertyDetailJsonObject.getString(Constant.AddProperty.SMS_TO_BROKER)).split(",");
				Map<String, String> brokerInvartMap = new HashMap<String, String>(); 
				brokerInvartMap = MapUtils.invertMap(brokerMap);

				for(int j = 0; j<smsToBroker.length; j++){
						brokerListview.setItemChecked(brokerNames.indexOf(brokerInvartMap.get(smsToBroker[j])), true);
				}
			} 
			
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	//get Preferred Or Both Broker List PreferredBrokerService
	//Add the broker list into the select broker layout screen
	//in this also store the checked broker Id into the brokerIdArray 
	public void getPreferredBothBroker(int pageNumber){
		WebserviceClient preferredBrokerwebservice = new WebserviceClient(MyPropertyEditActivity.this, preferredBrokersService);
		preferredBrokerwebservice.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object res) {
				final JSONArray jsonArray = (JSONArray) res;
				try {		
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.PreferredBrokers.API_STATUS)) {
						for (int i = 1; i <jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							brokerMap.put(jsonObject.getString(Constant.PreferredBrokers.FIRST_NAME)+" "+jsonObject.getString(Constant.PreferredBrokers.LAST_NAME),jsonObject.getInt(Constant.PreferredBrokers.BROKER_ID));
							brokerNames.add(jsonObject.getString(Constant.PreferredBrokers.FIRST_NAME)+" "+jsonObject.getString(Constant.PreferredBrokers.LAST_NAME));
						}
						if(brokerArrayAdapter == null ) {
							brokerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_list_item, brokerNames);
							brokerListview.setAdapter(brokerArrayAdapter);
						} else {
							brokerArrayAdapter.notifyDataSetChanged();
							flag_loading = false;
						}
						brokerListview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
						setListViewHeightBasedOnChildren(brokerListview);
						//set default checked list item
						String[] smsToBroker = (propertyDetailJsonObject.getString(Constant.AddProperty.SMS_TO_BROKER)).split(",");
						Map<String, String> brokerInvartMap = new HashMap<String, String>(); 
						brokerInvartMap = MapUtils.invertMap(brokerMap);
						for(int j=0; j<smsToBroker.length; j++){
							brokerListview.setItemChecked(brokerNames.indexOf(brokerInvartMap.get(smsToBroker[j])), true);
						}
						//override list item click event
						brokerListview.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {}
						});
					} else {
						Toast.makeText(getActivity(),((JSONObject) jsonArray.get(0)).getString(Constant.PreferredBrokers.API_MESSAGE), Toast.LENGTH_LONG).show();
					}				
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		//call the web service according to Broker type and pass the parameter to web service
		preferredBrokerwebservice.execute(userSessionManager.getSessionValue(Constant.Login.USER_ID));
		
	}
	//END
	//call group list web service to get group name and set into list view
	public void getGroupList(){
		WebserviceClient groupListwebservice = new WebserviceClient(MyPropertyEditActivity.this, groupListService);
		groupListwebservice.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object res) {
				final JSONArray jsonArray = (JSONArray) res;
				try {		
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.PreferredBrokers.API_STATUS)) {
						for (int i = 0 ; i <jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							brokerMap.put(jsonObject.getString(Constant.GroupList.GROUP_NAME),jsonObject.getInt(Constant.GroupList.PGID));
							brokerNames.add(jsonObject.getString(Constant.GroupList.GROUP_NAME));
						}
						if(brokerArrayAdapter == null ) {
							brokerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_list_item, brokerNames);
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
						Toast.makeText(getActivity(),((JSONObject) jsonArray.get(0)).getString(Constant.PreferredBrokers.API_MESSAGE), Toast.LENGTH_LONG).show();
					}				
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		});
		groupListwebservice.execute(userSessionManager.getSessionValue(Constant.Login.USER_ID));
	}
	
	//This method is used to get the login user nominee list, create the check box and add into the layout
	//Call Service To get Nominee List add nominee List to the Nominee Layout,also store the checked nominee Id into the nomineeIdArray
	public void getNomineeList(){
		WebserviceClient webServiceClient = new WebserviceClient(MyPropertyEditActivity.this, nomineesListService);
		webServiceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				MainFragmentActivity.nomineeResponse = (JSONArray) response;
				try {		
					if (MainFragmentActivity.nomineeResponse != null && !((JSONObject) MainFragmentActivity.nomineeResponse.get(0)).has(Constant.NomineeList.API_STATUS)) {
						for(int i=0; i < MainFragmentActivity.nomineeResponse.length();i++){
							JSONObject jsonObject = MainFragmentActivity.nomineeResponse.getJSONObject(i);
							editPropNomineeMap.put(jsonObject.getString(Constant.NomineeList.TITLE),jsonObject.getString(Constant.NomineeList.NOMINEE_ID));
							editNomineeNames.add(jsonObject.getString(Constant.NomineeList.TITLE));
						}
						if(editNomineeArrayAdapter == null ){
							editNomineeArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_list_item, editNomineeNames);
							editPropNomineeListView.setAdapter(editNomineeArrayAdapter);
						}
						editPropNomineeListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
						setListViewHeightBasedOnChildren(editPropNomineeListView);
						
						//set default selected nominee
						for (int j = 0; j < nomineeID.length(); j++) {
							if(editNomineeNames.indexOf(nomineeID.get(j).toString().trim()) != -1) {
								editPropNomineeListView.setItemChecked(editNomineeNames.indexOf(nomineeID.get(j).toString().trim()), true);
							}
						}
					} else {
						Toast.makeText(getActivity(),((JSONObject) MainFragmentActivity.nomineeResponse.get(0)).getString(Constant.NomineeList.API_MESSAGE), Toast.LENGTH_LONG).show();
					}				
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		});
		webServiceClient.execute(userSessionManager.getSessionValue(Constant.Login.USER_ID));
	}
	
	//get the list of tp scheme and fill the tp schem spinner
	public void getTpSchemList(){
		final JSONArray jsonArray = CreateJsonArrayFileIntoCache.readTPSchemeListJsonData(getActivity().getApplicationContext());
		try {
			if(jsonArray != null) {
				ArrayAdapter<String> tpSchemeAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,JSONUtils.getList(jsonArray, Constant.TPSchemesListing.TITLE));
				tpSchemeAdapter.setDropDownViewResource(R.layout.spinner_text);
				editPropTPSchemeSpinner.setAdapter(tpSchemeAdapter);
				editPropTPSchemeSpinner.setSelection(tpSchemeAdapter.getPosition(propertyDetailJsonObject.getJSONObject("tpdetail").getString("tpname")));
				editPropTPSchemeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View view,int position, long arg3) {
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
		} catch(JSONException e) {
			e.printStackTrace();
		}
	}
	
	//get the list of Facility 
	public void getFacilityList(){
		JSONArray jsonArray = CreateJsonArrayFileIntoCache.readFacilityListJsonData(getActivity().getApplicationContext());
		facilityIdArray = new ArrayList<Integer>();
		try {
			if (jsonArray != null) {
				for(int i=0;i<jsonArray.length();i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					//Dynamically create Facility CheckBox 
					LinearLayout ll = new LinearLayout(getActivity().getApplicationContext());
			        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30,30);
	                layoutParams.setMargins(5, 0 , 5, 0);
	                layoutParams.gravity = Gravity.CENTER;
			        
					ImageView facilityIcon = new ImageView(getActivity().getApplicationContext());
					Picasso.with(getActivity()).load(jsonObject.getString(Constant.Facility.IMAGE_PATH)).into(facilityIcon);
					facilityIcon.setLayoutParams(layoutParams);
					
					facilityCheck = new CheckBox(getActivity());								
					facilityCheck.setId(jsonObject.getInt(Constant.Facility.FACILITY_ID));
					facilityCheck.setTextColor(Color.BLACK);
					facilityCheck.setText(jsonObject.getString(Constant.Facility.TITLE));
					facilityCheck.setButtonDrawable(R.xml.custom_checkbox);

					ll.addView(facilityIcon);
					ll.addView(facilityCheck);
					editPropFacilityLayout.addView(ll);
					
					//check default selected facility
					if(facilityID != null) {
						for (int j = 0; j < facilityID.length(); j++) {
							if(facilityCheck.getId() == facilityID.getInt(j)) {
								facilityCheck.setChecked(true);
								facilityIdArray.add(facilityCheck.getId());
							}
						}
					}
					
					//store checked checkbox Facility Id to the FacilityIdArray
					facilityCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							if(isChecked)
								facilityIdArray.add((Integer)buttonView.getId());//store facility ID to facilityIdArray
							else
								facilityIdArray.remove((Integer)buttonView.getId());
						}
					});
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	//Method for Setting the Height of the ListView dynamically.
	//Hack to fix the issue of not showing all the items of the ListView
	//when placed inside a ScrollView 
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
	
	
	/*//open dialog for add new landmark
	private void addNewLandmarkDialogBox(){
		final Dialog dialog = new Dialog(getActivity());
	    dialog.setContentView(R.layout.activity_landmark_popup);
	    dialog.setTitle("Add Landmark");
	    final EditText editText = (EditText)dialog.findViewById(R.id.landmarkEditBox);
	   
	    Button send = (Button)dialog.findViewById(R.id.landmarkSendButton);
	    send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Thank you! Your landmark request is pending for admin approval. We will contact you once it is approved.", Toast.LENGTH_LONG).show();
			}
		});
	    
	    *//**
	     * on click close the dialog box
	     *//*
	    Button cancel = (Button)dialog.findViewById(R.id.landmarkCancelButton);
	    cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
	    
	    
	    dialog.show();
	}*/
	
	// crop the selected image from gallery
	private void doCrop() {
		Intent intent = new Intent();
		// call android default gallery
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		
		// code for crop image
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 0);
		intent.putExtra("aspectY", 0);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 150);
		try {
			intent.putExtra("return-data", true);
			startActivityForResult(Intent.createChooser(intent,"Complete action using"), 1);
		} catch (ActivityNotFoundException e) {
			//display an error message
	        String errorMessage = "Whoops - your device doesn't support the crop action!";
	        Toast.makeText(getActivity().getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
		}
	}
	
	private void storeScropImage(Bitmap photo,String imageName) {
		//String root = Environment.getExternalStorageDirectory().toString();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		java.util.Date now = new java.util.Date();
		String fileName = formatter.format(now) + ".jpeg";
		imageNameMap.put(imageName,fileName);
        //imageNameMap.add(hashMap);
        
        
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
	
	// Image Selection(Browse) for Logo And Photo 
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (resultCode == getActivity().RESULT_OK) {
				if (requestCode == 1) {
					Bundle extras2 = data.getExtras();
					if (extras2 != null) {
						Bitmap photo = extras2.getParcelable("data");
						
						/*ByteArrayOutputStream baos = new ByteArrayOutputStream();
						photo.compress(Bitmap.CompressFormat.JPEG, 70, baos);
					    byte[] byteForImage = baos.toByteArray();
						String encodedImage = Base64.encodeToString(byteForImage,Base64.DEFAULT);*/
						
						if (image1ButtonClick == 1) {
							storeScropImage(photo,Constant.AddProperty.IMAGE_1);
							editPropFlatDefaultImgPath.setVisibility(View.VISIBLE);
							editPropImage1ImageView.setImageBitmap(photo);
							image1Flag = false;
						}
						if (image2ButtonClick == 1) {
							storeScropImage(photo,Constant.AddProperty.IMAGE_2);
							editPropFlatImage2Path.setVisibility(View.VISIBLE);
							editPropImage2ImageView.setImageBitmap(photo);
							image2Flag = false;
						}
						if (image3ButtonClick == 1) {
							storeScropImage(photo,Constant.AddProperty.IMAGE_3);
							editPropFlatImage3Path.setVisibility(View.VISIBLE);
							editPropImage3ImageView.setImageBitmap(photo);
							image3Flag = false;
						}
						if (image4ButtonClick == 1) {
							storeScropImage(photo,Constant.AddProperty.IMAGE_4);
							editPropFlatImage4Path.setVisibility(View.VISIBLE);
							editPropImage4ImageView.setImageBitmap(photo);
							image4Flag = false;
						}
						if (image5ButtonClick == 1) {
							storeScropImage(photo,Constant.AddProperty.IMAGE_5);
							editPropFlatImage5Path.setVisibility(View.VISIBLE);
							editPropImage5ImageView.setImageBitmap(photo);
							image5Flag = false;
						}
						
						image1ButtonClick = 0;
						image2ButtonClick = 0;
						image3ButtonClick = 0;
						image4ButtonClick = 0;
						image5ButtonClick = 0;
					}
				}
			}
		}
	
	//Check property type
	private boolean checkPropertyType() {
		if(editPropPropTypeSpinner.getSelectedItem().equals(" Select ") && editPropPropTypeSpinner.isShown()) {
			Toast.makeText(getActivity(), "Please seelct Property Type", Toast.LENGTH_LONG).show();
			return true;
		}
		return false;
	}
	//Check Property option
	private boolean checkPropertyOption() {
		if(editPropOptionSpinner.getSelectedItem().equals("Select")){
			Toast.makeText(getActivity(), "Please select option", Toast.LENGTH_LONG).show();
			return true;
		}
		return false;
	}
		
	//Check address is null or not
	private boolean checkAddress() {
		if(editPropAddressEditBox != null) {
			if(editPropAddressEditBox.getText().length() == 0 && editPropAddressEditBox.isShown()) {
				editPropAddressEditBox.setError("Please Fill Address");
				return true;
			}
		}
		return false;
	}
	//Check location
	private boolean checkLocation() {
		if(editPropLocationSpinner.getSelectedItem() != null) {
			if(editPropLocationSpinner.getSelectedItem().equals("Select")  && editPropLocationSpinner.isShown()) {
				Toast.makeText(getActivity(), "Please select Loaction", Toast.LENGTH_LONG).show();
				return true;
			} else if(editPropLocationSpinner.getSelectedItem().equals("No location found")) {
				Toast.makeText(getActivity(), "No loaction found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	
	//Check Bes is null or not
	private boolean checkBed() {
		if (editPropFlatBedsSpinner != null) {
			if (editPropFlatBedsSpinner.getSelectedItem().equals("0") && editPropFlatBedsSpinner.isShown()) {
				Toast.makeText(getActivity(), "Please select bed", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
		
	//check the Rent box is null or not
	private boolean checkRent() {
		if(editPropOptionSpinner.getSelectedItem() != null) {
			if(editPropOptionSpinner.getSelectedItem().equals("Rent")) {
				if(editPropRentEditBox != null) {
					if(editPropRentEditBox.getText().length() == 0 && editPropRentEditBox.isShown()) {
						editPropRentEditBox.setError("Please Fill Rent");
						return true;
					}
				}
			}
		}
	    return false;
	}
	
	// check price if null or not
	private boolean checkPrice() {
		if(editPropOptionSpinner.getSelectedItem() != null) {
			if(editPropOptionSpinner.getSelectedItem().equals("Sale")) {
				if(editPropFlatTotalPriceEditBox != null) {
					if(editPropFlatTotalPriceEditBox.getText().length() == 0) {
						editPropFlatTotalPriceEditBox.setError("Please Fill Price");
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//this method call when user select area and get the list of broker of particular area 
	private String getAreaWiseBroketList(String areaId) {
		FindAgentService findAgentService = new FindAgentService();
		WebserviceClient findAgentWebserviceClient = new WebserviceClient(MyPropertyEditActivity.this, findAgentService);
		findAgentWebserviceClient.setResponseListner(new ResponseListner() {
			@SuppressLint("NewApi")
			@Override
			public void handleResponse(Object response) {
				JSONArray jsonArray = (JSONArray) response;
				try {
					if(jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.FindAgent.API_STATUS)) {
						//jsonArray.remove(0);
						JSONUtils.getList(jsonArray,Constant.FindAgent.BROKER_ID);
						selectedBrokerId = StringUtils.join(JSONUtils.getList(jsonArray,Constant.FindAgent.BROKER_ID), ",");
					} 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		findAgentWebserviceClient.execute("AddNewProperty",areaId,userSessionManager.getSessionValue(Constant.Login.USER_ID));
		
		return areaWiseBrokerId;
	}
	
	//get the list of comma sepreted Broker
	private String getCommaSepretedBrokerId() {
		checked = "";
		String brokerID = "";
		int cntChoice = brokerListview.getCount();
		SparseBooleanArray sparseBooleanArray = brokerListview.getCheckedItemPositions();
		for(int i = 0; i < cntChoice; i++) {
			if(sparseBooleanArray.get(i) == true) {
		    	 checked += brokerListview.getItemAtPosition(i).toString();
		    	 if(i != sparseBooleanArray.size())
		    		 checked+=",";
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
	
	//get group member list 
    public void getGroupMemberList(String groupID) {
    	groupMemberMap.clear();
    	groupMemberNames.clear();
    	GroupMemberListService groupMemberListService = new GroupMemberListService();
		WebserviceClient groupMemberListWebserviceClient = new WebserviceClient(MyPropertyEditActivity.this, groupMemberListService);
		groupMemberListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				JSONArray jsonArray = (JSONArray) response;
				try {
					if(jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.GroupList.API_STATUS)) {
						for (int i = 0 ; i < jsonArray.length(); i++) {
							JSONObject jsonObject=jsonArray.getJSONObject(i);
							groupMemberMap.put(jsonObject.getString(Constant.GroupList.FIRST_NAME)+" "+jsonObject.getString(Constant.GroupList.LAST_NAME),jsonObject.getString(Constant.GroupList.BROKER_ID));
							groupMemberNames.add(jsonObject.getString(Constant.GroupList.FIRST_NAME)+" "+jsonObject.getString(Constant.GroupList.LAST_NAME));
						}
							
						LayoutInflater li = LayoutInflater.from(getActivity());
				        View promptsView = li.inflate(R.layout.activity_groupmember_list_popup_window, null);
				        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
				        alertDialogBuilder.setView(promptsView);

				        final ListView groupMemberListView = (ListView) promptsView.findViewById(R.id.groupMemberListview);
				        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_list_item,groupMemberNames);
				        groupMemberListView.setAdapter(adapter);
				        groupMemberListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
				        
				        //set default checked list item 
						String[] smsToBroker = (groupMemberChecked).split(",");
						Map<String, String> brokerInvartMap = new HashMap<String, String>(); 
						brokerInvartMap = MapUtils.invertMap(groupMemberMap);
						
						for(int j=0; j<smsToBroker.length; j++){
							groupMemberListView.setItemChecked(groupMemberNames.indexOf(smsToBroker[j]), true);
						}
					        
				        // set dialog message
				        alertDialogBuilder.setCancelable(true).setTitle("Select Broker").setPositiveButton("Save",new DialogInterface.OnClickListener() {
		                        public void onClick(DialogInterface dialog,int id) {
		                        		groupMemberBrokerId = "";
		                        		groupMemberChecked  = "";
		                        		
		                        		int cntChoice = groupMemberListView.getCount();
		                        		SparseBooleanArray sparseBooleanArray = groupMemberListView.getCheckedItemPositions();
		                        		
		                        		for(int i = 0; i < cntChoice; i++) {
		                        			if(sparseBooleanArray.get(i) == true) {
		                        		    	 groupMemberChecked += groupMemberListView.getItemAtPosition(i).toString();
		                        		    	 if(i != sparseBooleanArray.size())
		                        		    		 groupMemberChecked+=",";
		                        		     }
		                        		 }
		                        		
		                        		String[] selectedBrokerName = (groupMemberChecked).split(",");
		                        		for (int i = 0; i < selectedBrokerName.length; i++) {
		                        			groupMemberBrokerId += groupMemberMap.get(selectedBrokerName[i]);
		                        	
		                        			if (i != selectedBrokerName.length - 1) 
		                        				groupMemberBrokerId += ",";
		                        		}
		                        }
		                    }).setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
				                  public void onClick(DialogInterface dialog,int id) {} });
				        // create alert dialog
				        AlertDialog alertDialog = alertDialogBuilder.create();
				        // show it
				        alertDialog.show();
					} else {
						Toast.makeText(getActivity(),((JSONObject) jsonArray.get(0)).getString(Constant.GroupList.API_MESSAGE), Toast.LENGTH_LONG).show();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		groupMemberListWebserviceClient.execute(userSessionManager.getSessionValue(Constant.Login.USER_ID),groupID);
    }
	
    //Rest the map fragment for reuse
	@Override
	public void onDestroyView() {
	    super.onDestroyView();
	    try {
	        SupportMapFragment fragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.editPropMap);
	        if (fragment != null)
	            getFragmentManager().beginTransaction().remove(fragment).commit();

	    } catch (IllegalStateException e) {
	    	e.printStackTrace();
	    }
	}
	
	//get the List of all broker
	public void getAllBrokerList(int page) {
		BrokerAllService allBrokerService = new BrokerAllService();
		WebserviceClient allBrokerWebserviceClient = new WebserviceClient(MyPropertyEditActivity.this,allBrokerService);
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
	
	//Notify adapter
		private void notifyAdapter(ArrayAdapter<String> adapter, String spinnerString) {
			if(adapter != null) {
				adapter.clear();
				if(spinnerString.equals("city")) {
					adapter.insert("No city found", 0);
					editPropCitySpinner.setEnabled(false);
				} else if(spinnerString.equals("location")) {
					adapter.insert("No location found", 0);
					editPropLocationSpinner.setEnabled(false);
				}
				adapter.notifyDataSetChanged();
			}
		}
	
	// get the detail of property by ID
	public void getPropertyDetailById() {
		FindByPropertyIdService findByPropertyIdService = new FindByPropertyIdService();
		WebserviceClient propertyDetailWebserviceClient = new WebserviceClient(MyPropertyEditActivity.this,findByPropertyIdService);
		propertyDetailWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null && !((JSONObject) jsonArray.get(0)).has(Constant.PreferredBrokers.API_STATUS)){
						propertyDetailJsonObject = jsonArray.getJSONObject(0);
						
						editPropPublishOptionSpinner.setSelection(publishingOptionAdapter.getPosition(SpinnerItem.getPublishingOptionListKey(propertyDetailJsonObject.getInt("prefopt"))));
						editPropAddressEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.ADDRESS));
						editPropPostCodeEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.POST_CODE));
						editPropLongitudeEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.LONGITUDE));
						editPropLatitudeEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.LATITUDE));
						editPropOccupNameEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.OCCUPANCY_NAME));
						editPropOccupDetailEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.OCCUPANCY_DETAIL));
						editPropReleseDateEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.OCCUPANCY_DATE));
						editPropPriceTransferFeeseSaleEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.TRANSFER_FEES));
						editPropPriceAECAudaLegalSaleEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.AEC_AUDA));
						editPropFlatFurnishDetailEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.FURNISH_COMMENT));
						editPropFlatAreaEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.MIN_AREA));
						editPropFlatPlotAreaEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.PLOT_AREA));
						editPropFlatConstructionAreaEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.CONSTRUCTION_AREA));
						editPropFlatSellPriceEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.PRICE));
						editPropFlatTotalPriceEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.TOTAL_PRICE));
						editPropFlatDastawageEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.DASTAWAGE));
						
						//if Property type Plot than set default checked value
						if(propertyDetailJsonObject.getString(Constant.AddProperty.PROPERTY_TYPE).equals("Plot")){
							editPropTermsNaviSharatCheck.setChecked(BooleanUtils.toBoolean(Integer.parseInt(StringUtils.defaultString(propertyDetailJsonObject.getString(Constant.AddProperty.NAVISHARAT)))));
							editPropTermsJuniSharatCheck.setChecked(BooleanUtils.toBoolean(Integer.parseInt(propertyDetailJsonObject.getString(Constant.AddProperty.JUNISHARAT))));
							editPropTermsKhetiCheck.setChecked(BooleanUtils.toBoolean(Integer.parseInt(propertyDetailJsonObject.getString(Constant.AddProperty.KHETI))));
							editPropTermsPRASHACheck.setChecked(BooleanUtils.toBoolean(Integer.parseInt(propertyDetailJsonObject.getString(Constant.AddProperty.PRASSAP))));
							editPropTermsDisputeCheck.setChecked(BooleanUtils.toBoolean(Integer.parseInt(propertyDetailJsonObject.getString(Constant.AddProperty.DIS_PUTE))));
							editPropTermsShreeSarkarCheck.setChecked(BooleanUtils.toBoolean(Integer.parseInt(propertyDetailJsonObject.getString(Constant.AddProperty.SHREE_SARKAR))));
							//Check idex of item is -1
							if(onRoadList.contains(propertyDetailJsonObject.getString(Constant.AddProperty.ON_ROAD)))
								editPropExtraInfoFramHouseRoadTouchSpinner.setSelection(onRoadList.indexOf(SpinnerItem.getOnRoadListKey(propertyDetailJsonObject.getString(Constant.AddProperty.ON_ROAD))));
							
							editPropNASpinner.setSelection(naStatusAdapter.getPosition(SpinnerItem.getAddPropNaStatusListKey(propertyDetailJsonObject.getString(Constant.AddProperty.NA_STATUS))));
							editPropFlatZoneSpinner.setSelection(zoneAdapter.getPosition(propertyDetailJsonObject.getString("zone")));
						}
						//END	
						
						//Fill the  Property Type Spinner Using hash Map 1)Flat 2)Shop 3)Bunglow 4)Plot
						propertyTypeOptionList = new ArrayList<String>();
						if(propertyDetailJsonObject.getString(Constant.AddProperty.PROPERTY_TYPE).equals("Flat")) 
							propertyTypeOptionList.add(propertyDetailJsonObject.getString(Constant.AddProperty.PROPERTY_TYPE));
						 else if(propertyDetailJsonObject.getString(Constant.AddProperty.PROPERTY_TYPE).equals("Shop")) 
							propertyTypeOptionList.add(propertyDetailJsonObject.getString(Constant.MyProperty.SHOP_TYPE));
						 else if(propertyDetailJsonObject.getString(Constant.AddProperty.PROPERTY_TYPE).equals("Bunglow")) 
							propertyTypeOptionList.add(propertyDetailJsonObject.getString(Constant.MyProperty.BUNGLOW_TYPE));
						 else if(propertyDetailJsonObject.getString(Constant.AddProperty.PROPERTY_TYPE).equals("Plot")) 
							propertyTypeOptionList.add(propertyDetailJsonObject.getString(Constant.MyProperty.PLOT_TYPE));
						
						ArrayAdapter< String> prpoertyTypeOptionAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,propertyTypeOptionList);
						prpoertyTypeOptionAdapter.setDropDownViewResource(R.layout.spinner_text);
						editPropPropTypeSpinner.setAdapter(prpoertyTypeOptionAdapter);
						
						//Fill the Sale/Rent Spinner using Intent 	
						ArrayList<String> reanOrSaleList = new ArrayList<String>();
						reanOrSaleList.add(propertyDetailJsonObject.getString(Constant.AddProperty.STR_OPTIONS));
						ArrayAdapter< String> saleOrRentAdapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,reanOrSaleList);
						saleOrRentAdapter.setDropDownViewResource(R.layout.spinner_text);
						editPropOptionSpinner.setAdapter(saleOrRentAdapter);
						//END
						
						editPropPropOccupSpinner.setSelection(propertyOccupacyAdapter.getPosition(SpinnerItem.getPropertyOccupacyListKey(propertyDetailJsonObject.getString(Constant.AddProperty.OCCUPACY))));
						//Check Index of item is -1
						if(bedList.contains(propertyDetailJsonObject.getString(Constant.AddProperty.BED)))
							editPropFlatBedsSpinner.setSelection(bedList.indexOf(SpinnerItem.getBedListKey(propertyDetailJsonObject.getString(Constant.AddProperty.BED))));
						
						editPropFlatFurnishOptionSpinner.setSelection(furnishOptionList.indexOf(SpinnerItem.getFurnishOptionListKey((Integer.parseInt(propertyDetailJsonObject.getString(Constant.AddProperty.FURNISH_STATUS))))));
						//Check item is available in list or not
						if(floorList.contains(propertyDetailJsonObject.getString(Constant.AddProperty.FLOOR)))
							editPropFlatFloorSpinner.setSelection(floorList.indexOf(SpinnerItem.getFloorListKey(propertyDetailJsonObject.getString(Constant.AddProperty.FLOOR))));
						
						editPropFlatBuildingTypeSpinner.setSelection(buildingTypeList.indexOf(SpinnerItem.getBuildingTypeListKey(propertyDetailJsonObject.getString(Constant.AddProperty.RISE))));
						editPropPriceMaintenanceEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.MAINTENANCE));
						editPropRentEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.RENT));
						editPropPriceRentDepositeEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.RENT_DEPOSIT));
						editPropPriceParkingChargesSaleEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.PARKING));
						editPropExtraInfoPurposeSpinner.setSelection(purposeAdapter.getPosition(SpinnerItem.getAddPropPurposeListKey(propertyDetailJsonObject.getString(Constant.AddProperty.PURPOSE))));
						editPropExtraInfoCommentEditBox.setText(propertyDetailJsonObject.getString(Constant.AddProperty.COMMENTS));
						editPropExtraInfoHint2.setText(propertyDetailJsonObject.getString(Constant.AddProperty.HINT));
						
						//Check item is available in list or not, if available than set in Buildup year spinner 
					    if(years.contains(propertyDetailJsonObject.getString(Constant.AddProperty.YEAR_BUILD_UP)))
					    	editPropExtraInfoBuiltYearSpinner.setSelection(years.indexOf(propertyDetailJsonObject.getString(Constant.AddProperty.YEAR_BUILD_UP)));
						
						//Set the Google map    
						try {
							setUpMapIfNeeded(propertyDetailJsonObject.getDouble("latval"),propertyDetailJsonObject.getDouble("longval"));
						} catch (Exception e) {
							e.printStackTrace();
						}
						//store the facility id 
						//if(!propertyDetailJsonObject.getJSONArray("facilityid").("no_facilityids"))
							facilityID = propertyDetailJsonObject.getJSONArray("facilityid");
						
						//store the nominee id 
						nomineeID = propertyDetailJsonObject.getJSONArray("nominee");
						
						Picasso.with(getActivity()).load(propertyDetailJsonObject.getString("image1link")).into(editPropImage1ImageView);
						Picasso.with(getActivity()).load(propertyDetailJsonObject.getString("image2link")).into(editPropImage2ImageView);
						Picasso.with(getActivity()).load(propertyDetailJsonObject.getString("image3link")).into(editPropImage3ImageView);
						Picasso.with(getActivity()).load(propertyDetailJsonObject.getString("image4link")).into(editPropImage4ImageView);
						Picasso.with(getActivity()).load(propertyDetailJsonObject.getString("image5link")).into(editPropImage5ImageView);
						
						image1Url = propertyDetailJsonObject.getString("image1link");
						image2Url = propertyDetailJsonObject.getString("image2link");
						image3Url = propertyDetailJsonObject.getString("image3link");
						image4Url = propertyDetailJsonObject.getString("image4link");
						image5Url = propertyDetailJsonObject.getString("image5link");
						
						getStateList();
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		});
		propertyDetailWebserviceClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,propertyIdIntent.getString(Constant.MyProperty.PROPERTY_ID),userSessionManager.getSessionValue(Constant.Login.USER_ID));
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
	
	//Store default image if user not select
	private void setdefaultImage(String aUrl,String name) {
		try {
			URL url = new URL(aUrl);
			storeScropImage(BitmapFactory.decodeStream(url.openConnection().getInputStream()),name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}