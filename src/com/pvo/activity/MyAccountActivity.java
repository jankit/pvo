package com.pvo.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.MediaColumns;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
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
import com.pvo.json.cache.CreateJsonArrayFileIntoCache;
import com.pvo.prototype.PVOFragment;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.CityListService;
import com.pvo.user.service.DistrictListService;
import com.pvo.user.service.MyAccountService;
import com.pvo.user.service.ViewProfileService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.pvo.util.JSONUtils;
import com.squareup.picasso.Picasso;

public class MyAccountActivity extends PVOFragment {

	// http://android-er.blogspot.in/2012/03/example-of-using-popupwindow.html
	// http://www.androidhub4you.com/2012/07/how-to-create-popup-window-in-android.html
	// http://stackoverflow.com/questions/16605582/how-to-make-a-input-search-for-list-view-in-android
	// http://stackoverflow.com/questions/6996837/android-multi-line-linear-layout
	// http://stackoverflow.com/questions/2961777/android-linearlayout-horizontal-with-wrapping-children

	private PopupWindow pwindo;
	private Button done;
	private Button cancel;
	private EditText search;
	private ListView searchListView;
	private LinearLayout selectedItemLayout;
	private ArrayAdapter<String> adapter;
	private EditText propertyDeal;
	private EditText areaDeal;
	private EditText languageKnown;
	private Button photobutton;
	private Button logobutton;
	private ImageView myAccountLogoImageView;
	private ImageView myAccountPhotoImageView;
	private TextView tvPhotoRemove;
	private TextView logoPathTextView;
	private TextView photoTextView;
	private TextView logoTextView;
	private ScrollView scroll;
	private Button saveButton;
	private Button cancelButton;
	private Button editButton;
	private EditText firstname;
	private EditText lastname;
	private EditText phonemobile;
	private EditText postcode;
	private EditText companyname;
	private EditText website;
	private EditText address;
	private EditText phoneOffice;
	private EditText email;
	private Spinner myAccountStateListSpinner;
	private Spinner district;
	private Spinner city;
	private EditText facebookLink;
	private EditText twitterLink;
	private EditText linkedinLink;
	private EditText businessPageLink;
	private EditText affiatedWith;
	private Spinner businessScience;
	private TextView popUpWindowHeaderText;
	private EditText smsBalanceEditText;
	private EditText smsCreditEditText;
	private EditText lattitudeEditText;
	private EditText longitudeEditText;
	private TextView firstnameTextView;
	private TextView lastnameTextView;
	private TextView phonemobile1TextView;
	private TextView postcodeTextView;
	private Button myAcViewProfileButton;

	//Error Message Text View
	private TextView stateErrorMsg;
	private TextView cityErrorMsg;
	private TextView districtErrorMsg;
	private String stateName;
	private String cityName;
	private String districtName;
	private MyAccountService myAccountService;
	private JSONArray stateResponse;
	private CityListService cityListService;
	private JSONArray cityResponse;
	private DistrictListService districtListService;
	private JSONArray districtResponse;
	private ViewProfileService viewProfileService;
	private UserSessionManager userSessionManager;
	private String photoUrl;
	private String logoUrl;
	private int photoButtonClick;
	private int logoButtonClick;
	private Map<String,Integer> areaMap = new HashMap<String, Integer>();
	private ArrayList<String> notSelectedItemList = new ArrayList<String>();
	private ArrayList<String> selectedItemList = new ArrayList<String>();
	private ArrayList<String> masterList = new ArrayList<String>();
	boolean bool = false;
	protected ArrayAdapter<String> listAdaptor;
	//Google Map to display the map and set the property Latitude and Longitude
	private GoogleMap googleMap;
	private Marker googleMarker;
	private ArrayAdapter<String> cityAdapter;
	private ArrayAdapter<String> districtAdapter;
	private final String DISTRICT = "district";
	private final String CITY = "city";
	private File myDir;
	private HashMap<String, String> imageNameMap;
	private Boolean photoFlag = true;
	private Boolean logoFlag = true;
	
	

	//Set layout content view
	public MyAccountActivity() {
		setContentView(R.layout.activity_my_account);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		// This Line is used to hide the keyboard
		onDestroy();
		
		myDir = new File(Environment.getExternalStorageDirectory() + "/pvo_scrop_images");
		imageNameMap = new HashMap<String, String>();
		deleteScropImageDirectory();
		storeCropImage(BitmapFactory.decodeResource(getResources(), R.drawable.no_image),"NoImage");
		
		//getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		firstnameTextView 		= (TextView) findViewById(R.id.firstnameTextView);
		firstnameTextView.setText(Html.fromHtml("First Name" + "<sup><font size=5 color=red>*</font></sup>"));
		lastnameTextView 		= (TextView) findViewById(R.id.lastnameTextView);
		lastnameTextView.setText(Html.fromHtml("Last Name" + "<sup><font size=5 color=red>*</font></sup>"));
		phonemobile1TextView 	= (TextView) findViewById(R.id.phonemobile1TextView);
		phonemobile1TextView.setText(Html.fromHtml("Phone (M)" + "<sup><font size=5 color=red>*</font></sup>"));
		postcodeTextView 		= (TextView) findViewById(R.id.postcodeTextView);
		postcodeTextView.setText(Html.fromHtml("Post Code" + "<sup><font size=5 color=red>*</font></sup>"));
		propertyDeal 			= (EditText) findViewById(R.id.propertyDealEditText);
		areaDeal 				= (EditText) findViewById(R.id.areaDealEditText);
		photobutton 			= (Button) findViewById(R.id.photoButton);
		logobutton				= (Button) findViewById(R.id.logoButton);
		photoTextView 			= (TextView) findViewById(R.id.photoTextView);
		logoTextView 			= (TextView) findViewById(R.id.logoTextView);
		tvPhotoRemove	 		= (TextView) findViewById(R.id.tvPhotoRemove);
		logoPathTextView 		= (TextView) findViewById(R.id.logoPathTextView);
		myAccountLogoImageView 	= (ImageView) findViewById(R.id.myAccountLogoImageView);
		myAccountPhotoImageView = (ImageView) findViewById(R.id.myAccountPhotoImageView);
		scroll 					= (ScrollView) findViewById(R.id.selectedList);
		saveButton 				= (Button) findViewById(R.id.saveButton);
		firstname 				= (EditText) findViewById(R.id.firstnameEditText);
		firstname.clearFocus();
		lastname				= (EditText) findViewById(R.id.lastnameEditText);
		phonemobile 			= (EditText) findViewById(R.id.phonemobile1EditText);
		postcode 				= (EditText) findViewById(R.id.postcodeEditText);
		companyname 			= (EditText) findViewById(R.id.companynameEditText);
		website 				= (EditText) findViewById(R.id.websiteEditText);
		address 				= (EditText) findViewById(R.id.addressEditText);
		phoneOffice 			= (EditText) findViewById(R.id.phoneoffice1EditText);
		email 					= (EditText) findViewById(R.id.emailEditText);
		myAccountStateListSpinner = (Spinner) findViewById(R.id.myAcstateSpinner);
		district 				= (Spinner) findViewById(R.id.myAcDistinctSpinner);
		city 					= (Spinner) findViewById(R.id.myAcCitySpinner);
		postcode 				= (EditText) findViewById(R.id.postcodeEditText);
		facebookLink 			= (EditText) findViewById(R.id.facebooklinkEditText);
		twitterLink 			= (EditText) findViewById(R.id.twitterlinkEditText);
		linkedinLink 			= (EditText) findViewById(R.id.linkedlinkEditText);
		businessPageLink 		= (EditText) findViewById(R.id.businesslinkEditText);
		affiatedWith 			= (EditText) findViewById(R.id.afiliatedwithEditText);
		businessScience 		= (Spinner) findViewById(R.id.BusinessScienceSpinner);
		languageKnown 			= (EditText) findViewById(R.id.languageEditText);
		smsBalanceEditText 		= (EditText) findViewById(R.id.smsBalanceEditText);
		smsCreditEditText 		= (EditText) findViewById(R.id.smsCreditEditText);
		lattitudeEditText 		= (EditText) findViewById(R.id.lettitudeEditText);
		longitudeEditText 		= (EditText) findViewById(R.id.longitudeEditText);
		
		photoButtonClick 		= 0;
		logoButtonClick 		= 0;
		myAccountService 		= new MyAccountService();
		cityListService 		= new CityListService();
		districtListService 	= new DistrictListService();
		//Error message Text View Id
		stateErrorMsg 			= (TextView) findViewById(R.id.stateErrorMsg);
		cityErrorMsg 			= (TextView) findViewById(R.id.cityErrorMsg);
		districtErrorMsg 		= (TextView) findViewById(R.id.districtErrorMsg);
		userSessionManager 		= new UserSessionManager(getActivity().getApplicationContext());
		
	    //OnClick of remove button remove the selected image from image view
	    tvPhotoRemove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				myAccountPhotoImageView.setImageResource(R.drawable.no_image);
			}
		});
	    
	    //OnClick of remove button remove the selected image from image view
	    logoPathTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myAccountLogoImageView.setImageResource(R.drawable.no_image);
			}
		});
	    
		//call get area list method
		getAreaList();

		//view Profile Web service 
		viewProfileService = new ViewProfileService();
		WebserviceClient viewProfile = new WebserviceClient(MyAccountActivity.this, viewProfileService);
		viewProfile.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				JSONArray jsonArray = (JSONArray) response;
				try {
					if (jsonArray != null) {
							JSONObject jsonObject = jsonArray.getJSONObject(0);
							firstname.setText(jsonObject.getString(Constant.ViewProfile.FIRST_NAME));
							lastname.setText(jsonObject.getString(Constant.ViewProfile.LAST_NAME));
							companyname.setText(jsonObject.getString(Constant.ViewProfile.COMPANY_NAME));
							website.setText(jsonObject.getString(Constant.ViewProfile.WEBSITE));
							address.setText(jsonObject.getString(Constant.ViewProfile.ADDRESS));
							phoneOffice.setText(jsonObject.getString(Constant.ViewProfile.PHONE_O));
							phonemobile.setText(jsonObject.getString(Constant.ViewProfile.PHONE_M));
							email.setText(jsonObject.getString(Constant.ViewProfile.EMAIL));
							areaDeal.setText(jsonObject.getString(Constant.ViewProfile.AREA_DEALS_IN_TEXT).replace(","," - "));
							propertyDeal.setText(jsonObject.getString(Constant.ViewProfile.PROPERTY_TYPE_DEALIN).replace(","," - "));
							postcode.setText(jsonObject.getString(Constant.ViewProfile.POST_CODE));
							facebookLink.setText(jsonObject.getString(Constant.ViewProfile.FACEBOOK));
							twitterLink.setText(jsonObject.getString(Constant.ViewProfile.TWITTER));
							linkedinLink.setText(jsonObject.getString(Constant.ViewProfile.LINKEDIN));
							businessPageLink.setText(jsonObject.getString(Constant.ViewProfile.BUSINESS_PAGE));
							affiatedWith.setText(jsonObject.getString(Constant.ViewProfile.AFFILIATED_WITH));
							languageKnown.setText(jsonObject.getString(Constant.ViewProfile.LANGUAGE_KNOWN));
							smsBalanceEditText.setText(jsonObject.getString(Constant.ViewProfile.SMS_BALANCE));
							smsCreditEditText.setText(jsonObject.getString(Constant.ViewProfile.SMS_CREDIT));
							stateName = jsonObject.getString(Constant.ViewProfile.STATENAME);
							cityName = jsonObject.getString(Constant.ViewProfile.CITYNAME);
							districtName = jsonObject.getString(Constant.ViewProfile.DISTRICTNAME);
							lattitudeEditText.setText(jsonObject.getString(Constant.ViewProfile.LATITUDE));
						    longitudeEditText.setText(jsonObject.getString(Constant.ViewProfile.LONGITUDE));
						    //Photo and logo image view	
						    Picasso.with(getActivity()).load(jsonObject.getString(Constant.ViewProfile.LOGO)).into(myAccountLogoImageView);
						    Picasso.with(getActivity()).load(jsonObject.getString(Constant.ViewProfile.PHOTO)).into(myAccountPhotoImageView);
						    logoUrl = jsonObject.getString(Constant.ViewProfile.LOGO);
						    photoUrl = jsonObject.getString(Constant.ViewProfile.PHOTO);
						    //get state list
						    getStateList();
						    setBusinessScinceSpinner(jsonObject.getString(Constant.ViewProfile.SCINCE));
						    
						    //Google Map
						    setUpMapIfNeeded(Double.parseDouble(jsonObject.getString(Constant.ViewProfile.LATITUDE)),Double.parseDouble(jsonObject.getString(Constant.ViewProfile.LONGITUDE)));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		viewProfile.execute(userSessionManager.getSessionValue(Constant.Login.USER_ID));
		//END
		
		//Open Language popup window
		languageKnown.setInputType(InputType.TYPE_NULL);
		languageKnown.setFocusable(false);
		languageKnown.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openPopupWindow("language");
				popUpWindowHeaderText.setText("Language");
			}
		});
		//END
	
		//Open Property Deals Popup Window
		propertyDeal.setInputType(InputType.TYPE_NULL);
		propertyDeal.setFocusable(false);
		propertyDeal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openPopupWindow("propertyDeal");
				popUpWindowHeaderText.setText("Property Deals");
			}
		});
		//END
	    
		//Open Area Deals Popup Window 
		areaDeal.setInputType(InputType.TYPE_NULL);
		areaDeal.setFocusable(false);
		areaDeal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openPopupWindow("areaMap");
			}
		});
		//END
	
		//onClick of Photo button open the
		photobutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				photoButtonClick = 1;
				doCrop();
			}
		});
	
		//onClick of logo button open the
		logobutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				logoButtonClick = 1;
				doCrop();
			}
		});
		//END
	
		//set Edit box to editable mode
		editButton = (Button) findViewById(R.id.editButton);
		editButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setEditableEditBox();
				saveButton.setVisibility(View.VISIBLE);
				editButton.setVisibility(View.GONE);
			}
		});
		//END
		
		
		//on click of save button Update the My account information
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			    try {
    			    saveButton.setEnabled(false);
    			    if (firstname.length() == 0) 
    					firstname.setError("Enter First Name");
    				if (lastname.length() == 0) 
    					lastname.setError("Enter Last Name");
    				if (phonemobile.length() == 0) 
    					phonemobile.setError("Enter Phone Number");
    				if (postcode.length() == 0) 
    					postcode.setError("Enter PostCode");
    				
    				if (myAccountStateListSpinner.getSelectedItem() == null) {
    					stateErrorMsg.setVisibility(View.VISIBLE);
    					saveButton.setEnabled(true);
    				}
    				
    				if (city.getSelectedItem() == null) {
    					cityErrorMsg.setVisibility(View.VISIBLE);
    					saveButton.setEnabled(true);
    				}
        				
    				if (district.getSelectedItem() == null) {
    					districtErrorMsg.setVisibility(View.VISIBLE);
    					saveButton.setEnabled(true);
    				}
    				
					if (firstname.length() > 0 && lastname.length() > 0 && phonemobile.length() > 0 && postcode.length() > 0 && !checkState() && !checkCity() && !checkDistrict()){//myAccountStateListSpinner.getSelectedItem() != null && city.getSelectedItem() != null && district.getSelectedItem() != null) {
						String areaList = areaDeal.getText().toString();
						String[] selectedArea = areaList.split(" - ");
						String selectedAreaString = "";
						for (int i = 0; i < selectedArea.length; i++) { // Comma Seprated String of Selected area Code
							selectedAreaString += areaMap.get(selectedArea[i]);
							if (i != selectedArea.length - 1) {
								selectedAreaString += ",";
							}
						}
						
						//This object are used to get the id of state, city, district **/
	                    JSONObject stateObject 		= stateResponse.getJSONObject(myAccountStateListSpinner.getSelectedItemPosition());
	                    JSONObject cityObject 		= cityResponse.getJSONObject(city.getSelectedItemPosition());
	                    JSONObject districtObject 	= districtResponse.getJSONObject(district.getSelectedItemPosition());
	                    //END 
	                    
	                    //Set default photo as per return response
	                    if(photoFlag) {
	                    	setdefaultImage(photoUrl,"Photo");
	                    }
	                    //Set default logo as per return response
	                    if(logoFlag) {
	                    	setdefaultImage(logoUrl,"Logo");
	                    }
	                    
						new WebserviceClient(MyAccountActivity.this,myAccountService).execute(
						        userSessionManager.getSessionValue(Constant.Login.USER_ID),
								firstname.getText().toString(),
								lastname.getText().toString(),
								companyname.getText().toString(),
								website.getText().toString(),
								address.getText().toString(),
								phoneOffice.getText().toString(),
								phonemobile.getText().toString(),
								email.getText().toString(), 
								getCropImagePath("Photo"),
								getCropImagePath("Logo"),
								stateObject.getString(Constant.State.STATEID),//state.getSelectedItem().toString(),
								districtObject.getString(Constant.District.DISTRICT_ID),//district.getSelectedItem().toString(),
								cityObject.getString(Constant.City.CITY_ID),//city.getSelectedItem().toString(),
								selectedAreaString,// area.getText().toString(),
								propertyDeal.getText().toString(), 
								postcode.getText().toString(), 
								facebookLink.getText().toString(),
								twitterLink.getText().toString(),
								linkedinLink.getText().toString(),
								businessPageLink.getText().toString(),
								affiatedWith.getText().toString(),
								businessScience.getSelectedItem().toString(),
								languageKnown.getText().toString().replace(" - ", ","),
						        lattitudeEditText.getText().toString(),
						        longitudeEditText.getText().toString());
					}
			    } catch (JSONException e) {
	                e.printStackTrace();
	            }
			}
		});
	
		//Go back to previous fragment
		cancelButton = (Button) findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}
		});
		
		//Redirect to agent detail screen
		myAcViewProfileButton = (Button) findViewById(R.id.myAcViewProfileButton);
		myAcViewProfileButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Bundle findAgnetBundel = new Bundle();
				findAgnetBundel.putString(Constant.FindAgent.BROKER_ID, userSessionManager.getSessionValue(Constant.Login.USER_ID));
				FindAgentDetailActivity findAgentDetailActivity = new FindAgentDetailActivity();
				findAgentDetailActivity.setArguments(findAgnetBundel);
				((MainFragmentActivity)getActivity()).redirectScreen(findAgentDetailActivity);
			}
		});
	}

	@Override
	public void processResponse(Object res) {
		JSONObject response = (JSONObject) res;
		if (response != null) {
			try {
				if (String.valueOf(response.getString(Constant.MyAccount.API_STATUS)).equals("1")) {
					Toast.makeText(getActivity(),String.valueOf(response.getString(Constant.MyAccount.API_MESSAGE)),Toast.LENGTH_LONG).show();
					deleteScropImageDirectory();
					saveButton.setVisibility(View.GONE);
					editButton.setVisibility(View.VISIBLE);
					//make editbox read only
					setReadOnlyEditBox();
					photoFlag = true;
					logoFlag = true;
				} else {
					Toast.makeText(getActivity(),String.valueOf(response.getString(Constant.MyAccount.API_MESSAGE)),Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		saveButton.setEnabled(true);
	}

	//this activity for choose the image from gallery
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("===> Start Activity <===");
		if (resultCode == getActivity().RESULT_OK) {
			if (requestCode == 1) {
				Bundle extras2 = data.getExtras();
				firstname.setEnabled(true);
			     
				
				if (extras2 != null) {
					 Bitmap photo = extras2.getParcelable("data");
					 
					 ByteArrayOutputStream stream = new ByteArrayOutputStream();   
					 photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);   
				     byte[] imageInByte = stream.toByteArray(); 
				     long lengthbmp = imageInByte.length; 
				     long kb = (lengthbmp/1024);
				     long mb = (kb/1024);
				     
					//storeCropImage(BitmapFactory.decodeResource(getResources(), R.drawable.no_image),"NoImage");
					if (photoButtonClick == 1) {
						//photoValue = encodedImage;
						if(mb <= 4) {
							storeCropImage(photo,"Photo");
							tvPhotoRemove.setVisibility(View.VISIBLE);
							myAccountPhotoImageView.setImageBitmap(photo);
							photoFlag = false;
						} else {
							Toast.makeText(getActivity(), "Please select image less then 4MB in size", Toast.LENGTH_SHORT).show();
						}
					} else if (logoButtonClick == 1) {
						//logoValue = encodedImage;
						if(mb <= 4) {
							storeCropImage(photo,"Logo");
							logoPathTextView.setVisibility(View.VISIBLE);
							myAccountLogoImageView.setImageBitmap(photo);
							logoFlag = false;
						} else {
							Toast.makeText(getActivity(), "Please select image less then 4MB in size", Toast.LENGTH_SHORT).show();
						}
					}
					photoButtonClick = 0;
					logoButtonClick = 0;
				}
			}
		}
	}
	//END

	//crop the selected image from gallery
	private void doCrop() {
		System.out.println("===> Do crop <====");
		Intent intent = new Intent();
		// call android default gallery
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
			startActivityForResult(Intent.createChooser(intent,"Complete action using"), 1);
		} catch (ActivityNotFoundException e) {
			//display an error message
	        String errorMessage = "Whoops - your device doesn't support the crop action!";
	        Toast.makeText(getActivity().getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
		}
	}
		
	//Store Crop Image
	private void storeCropImage(Bitmap photo,String imageName) {
		//String root = Environment.getExternalStorageDirectory().toString();
        //myDir = new File(Environment.getExternalStorageDirectory() + "/pvo_scrop_images");
        //myDir.mkdirs();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		java.util.Date now = new java.util.Date();
		String fileName = imageName+"_"+formatter.format(now) + ".jpeg";
		imageNameMap.put(imageName,fileName);
		
        File file = new File (myDir, fileName);
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
			File tempFile = new File(Environment.getExternalStorageDirectory() + "/pvo_scrop_images/"+imageNameMap.get(fileName));
			if(tempFile.exists())
				return tempFile.getAbsolutePath();
		}else { 
			return new File(Environment.getExternalStorageDirectory() + "/pvo_scrop_images/"+imageNameMap.get("NoImage")).getAbsolutePath();
		}
		return fileName;
	}
	
	//Get RealPath from URI
	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaColumns.DATA };// { MediaStore.Images.Media.DATA
		android.database.Cursor cursor = getActivity().managedQuery(contentUri,proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	//END
	
	//Open Pop Up Window
	private void openPopupWindow(final String purpose) {
		try {
			// We need to get the instance of the LayoutInflater
			LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.activity_area_list_popup_window,(ViewGroup) findViewById(R.id.popupWindow));
			pwindo = new PopupWindow(layout, LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT, true);
			pwindo.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
			pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
			popUpWindowHeaderText = (TextView) layout.findViewById(R.id.popUpWindowHeaderText);
			search = (EditText) layout.findViewById(R.id.search);
			scroll = (ScrollView) layout.findViewById(R.id.selectedList);
			searchListView = (ListView) layout.findViewById(R.id.searchList);
			selectedItemLayout = (LinearLayout) layout.findViewById(R.id.selectitem);
			selectedItemLayout.setOrientation(LinearLayout.VERTICAL);
			selectedItemList.clear();
			notSelectedItemList.clear();
			masterList.clear();
			selectedItemLayout.removeAllViews();
			if (purpose.equalsIgnoreCase("language")) {
				notSelectedItemList = new ArrayList<String>(Arrays.asList(Constant.LANGUAGE));
				masterList = new ArrayList<String>(Arrays.asList(Constant.LANGUAGE));
			} else if (purpose.equalsIgnoreCase("propertyDeal")) {
				notSelectedItemList = new ArrayList<String>(Arrays.asList(Constant.PROPERTY_DEALS));
				masterList = new ArrayList<String>(Arrays.asList(Constant.PROPERTY_DEALS));
			} else if (purpose.equalsIgnoreCase("areaMap")) {
				notSelectedItemList = new ArrayList<String>(areaMap.keySet());
				masterList = new ArrayList<String>(areaMap.keySet());
			}
			
			Collections.sort(notSelectedItemList);
			Collections.sort(masterList);
			String purposeText = "";
			
			if (purpose.equalsIgnoreCase("language"))
				purposeText = languageKnown.getText().toString();
			else if (purpose.equalsIgnoreCase("propertyDeal"))
				purposeText = propertyDeal.getText().toString();
			else if (purpose.equalsIgnoreCase("areaMap"))
				purposeText = areaDeal.getText().toString();

			if (purposeText != null && purposeText.length() > 0) {
				ArrayList<TextView> getAllTextViewbyLanguageText = new ArrayList<TextView>();
				selectedItemList = new ArrayList<String>(Arrays.asList(purposeText.split(" - ")));
				final ArrayList<TextView> getLanguageTextView = getAllLanguageTextViewbyLanguageText(purposeText,getAllTextViewbyLanguageText,selectedItemList);
				notSelectedItemList.removeAll(selectedItemList);
				
				for (int i = 0; i < getLanguageTextView.size(); i++) {
					addTextViewINLinearLayout(selectedItemLayout,getLanguageTextView.get(i), getActivity());
				}
			}
			
			adapter = new ArrayAdapter<String>(getActivity(), R.layout.demo,R.id.listTV, notSelectedItemList);
			adapter.setNotifyOnChange(true);
			searchListView.setBackgroundColor(Color.WHITE);
			searchListView.setAdapter(adapter);
			done = (Button) layout.findViewById(R.id.doneButton);
			done.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ArrayList<TextView> allTextView = new ArrayList<TextView>();
					pwindo.dismiss();
					allTextView = getAllTextViewbyParentLinearLayout(selectedItemLayout, allTextView);
					String getAllTextViewString = getAlltextViewString(allTextView);

					if (purpose.equalsIgnoreCase("language"))
						languageKnown.setText(getAllTextViewString,TextView.BufferType.EDITABLE);
					else if (purpose.equalsIgnoreCase("propertyDeal"))
						propertyDeal.setText(getAllTextViewString,TextView.BufferType.EDITABLE);
					else if (purpose.equalsIgnoreCase("areaMap"))
						areaDeal.setText(getAllTextViewString,TextView.BufferType.EDITABLE);
				}
			});
			
			//Close the Popup window
			cancel = (Button) layout.findViewById(R.id.cancelPopupButton);
			cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					pwindo.dismiss();
				}
			});
			//END

			//Search text view listener
			search.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start,int before, int count) {}
				@Override
				public void beforeTextChanged(CharSequence s, int start,int count, int after) {}

				@Override
				public void afterTextChanged(Editable s) {
					if (search.getText().length() > 0) {
						@SuppressWarnings("unchecked")
						List<String> searchItemList = (List<String>) CollectionUtils.select(masterList, new Predicate() {
							@Override
							public boolean evaluate(Object item) {
								String text = search.getText().toString().toLowerCase();
								return !selectedItemList.contains(item)&& String.valueOf(item).toLowerCase().startsWith(text);
							}
						});

						Collections.sort(searchItemList);
						notSelectedItemList.clear();
						notSelectedItemList.addAll(searchItemList);
						adapter.notifyDataSetChanged();
					} else {
						notSelectedItemList.clear();firstname.setEnabled(true);
						
						notSelectedItemList.addAll(masterList);
						notSelectedItemList.removeAll(selectedItemList);
						Collections.sort(notSelectedItemList);
						adapter.notifyDataSetChanged();
					}
				}
			});
			
			searchListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapterView, View v,int position, long id) {
					String item = (String) searchListView.getItemAtPosition(position);
					final TextView tv = new TextView(getActivity());
					tv.setText(item);
					tv.setId((int) id);
					LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
					llp.setMargins(12, 8, 0, 0); // llp.setMargins(left,top,right, bottom);
					tv.setLayoutParams(llp);
					tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.imageclose, 0, 0, 0);
					tv.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							clickOnTextView(tv);
						}
					});

					addTextViewINLinearLayout(selectedItemLayout, tv,getActivity());
					selectedItemList.add(notSelectedItemList.get(position));
					notSelectedItemList.remove(position);
					adapter.notifyDataSetChanged();
					
					// scroll view down at bottom wheen add item
					scroll.post(new Runnable() {
						@Override
						public void run() {
							scroll.fullScroll(View.FOCUS_DOWN);
						}
					});
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//onclick of selected item remove from selected item and add into notSelected item
	public void clickOnTextView(TextView tv) {
		LinearLayout parentll = (LinearLayout) tv.getParent();
		parentll.removeView(tv);
		notSelectedItemList.add((String) tv.getText());
		selectedItemList.remove(tv.getText());
		Collections.sort(notSelectedItemList);
		adapter.notifyDataSetChanged();
	}
	//End

	//GEt All TExtView by parent LinearLayout
	private ArrayList<TextView> getAllTextViewbyParentLinearLayout(LinearLayout selectedItem, ArrayList<TextView> alltextView) {
		if (selectedItem.getChildCount() > 0) {
			for (int i = 0; i < selectedItem.getChildCount(); i++) {
				if (selectedItem.getChildAt(i) instanceof LinearLayout) {
					LinearLayout ll = (LinearLayout) selectedItem.getChildAt(i);
					for (int j = 0; j < ll.getChildCount(); j++) {
						if (ll.getChildAt(j) instanceof TextView) {
							alltextView.add((TextView) ll.getChildAt(j));
						}
					}
					ll.removeAllViews();
				}
			}
		}
		return alltextView;
	}
	//END

	private String getAlltextViewString(ArrayList<TextView> allTextView) {
		String textViewText = "";
		for (int i = 0; i < allTextView.size(); i++) {
			if (i == (allTextView.size() - 1)) {
				textViewText += allTextView.get(i).getText();
			} else {
				textViewText += allTextView.get(i).getText() + " - ";
			}
		}
		return textViewText;
	}

	//Add TextView IN LinearLayout
	@SuppressWarnings("deprecation")
	private void addTextViewINLinearLayout(LinearLayout parentll, TextView tv,Context mContext) {
		
		int widthSoFar = 0;
		int maxWidth = 0;
		tv.measure(0, 0);
		tv.setTextSize(20);
		tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_location_tv_border));
		tv.setTextColor(Color.BLACK);
		tv.setPadding(5, 5, 5, 5);
		LinearLayout currentLL;

		if (parentll.getChildAt(parentll.getChildCount() - 1) != null) {
			parentll.getChildAt(parentll.getChildCount() - 1).measure(0, 0);
			widthSoFar = (parentll.getChildAt(parentll.getChildCount() - 1).getMeasuredWidth()) + tv.getMeasuredWidth();
		}
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		maxWidth = display.getWidth() - 100;

		if (parentll.getChildCount() == 0 || widthSoFar >= maxWidth) {
			// Create a liner layout and add
			currentLL = new LinearLayout(mContext);
			currentLL.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			currentLL.setOrientation(LinearLayout.HORIZONTAL);//VERTICAL
			parentll.addView(currentLL);
		}
		currentLL = (LinearLayout) parentll.getChildAt(parentll.getChildCount() - 1);
		currentLL.addView(tv);
	}
	//END

	//Get Language Text View by LanguageString
	private ArrayList<TextView> getAllLanguageTextViewbyLanguageText(String languageText,ArrayList<TextView> getAllTextViewbyLanguageText,List<String> languageStringList) {
		for (int i = 0; i < languageStringList.size(); i++) {
			final TextView tv = new TextView(getActivity());
			tv.setText(languageStringList.get(i));
			tv.setTextSize(20);
			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			llp.setMargins(12, 8, 0, 0); // llp.setMargins(left, top,right,bottom);
			tv.setLayoutParams(llp);
			tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.imageclose,0, 0, 0);
			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					clickOnTextView(tv);
				}
			});
			getAllTextViewbyLanguageText.add(tv);
		}
		return getAllTextViewbyLanguageText;
	}
	//END
	
    //get the list of state 
    public void getStateList(){
    	final JSONArray jsonArray = CreateJsonArrayFileIntoCache.readStateListJsonData(getActivity().getApplicationContext());
		stateResponse = jsonArray;
		if(jsonArray != null){
			ArrayAdapter<String> stateAdapter;
			try {
				stateAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text,JSONUtils.getList(jsonArray,Constant.State.STATENAME));
				stateAdapter.setDropDownViewResource(R.layout.spinner_text);
				//stateAdapter.
				myAccountStateListSpinner.setAdapter(stateAdapter);
				//Set Default Selection 
				myAccountStateListSpinner.setSelection(stateAdapter.getPosition(stateName));
				myAccountStateListSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0,View arg1, int position, long arg3) {
							try {
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
    //END

	//Web Service to get the City name List and add into the City Spinner
	public void getCityList(final String stateId) {
		WebserviceClient cityListWebserviceClient = new WebserviceClient(MyAccountActivity.this, cityListService);
		cityListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				cityResponse = jsonArray;
				try {
					if (jsonArray != null&& !((JSONObject) jsonArray.get(0)).has(Constant.City.API_STATUS)) {
						city.setEnabled(true);
						cityAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text,JSONUtils.getList(jsonArray,Constant.City.CITY_NAME));
						cityAdapter.setDropDownViewResource(R.layout.spinner_text);
						city.setAdapter(cityAdapter);
						city.setSelection(cityAdapter.getPosition(cityName));
						city.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0,View arg1, int position, long arg3) {
								try {
									cityErrorMsg.setVisibility(View.GONE);
									getDistrictList(stateId,jsonArray.getJSONObject(position).getString(Constant.City.COUNTRY_ID));//jsonArray.getJSONObject(position).getString(Constant.State.STATEID),
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});
					} else {
						Toast.makeText(getActivity(),((JSONObject) jsonArray.get(0)).getString(Constant.City.API_MESSAGE),Toast.LENGTH_LONG).show();
						notifyAdapter(cityAdapter,CITY);
						notifyAdapter(districtAdapter,DISTRICT);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		cityListWebserviceClient.execute(stateId);
	}

	//Call the District list Web service to fill the District Spinner this district is according to the City
	public void getDistrictList(String stateId, String countryId) {
		WebserviceClient districtListWebserviceClient = new WebserviceClient(MyAccountActivity.this, districtListService);
		districtListWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				final JSONArray jsonArray = (JSONArray) response;
				districtResponse = jsonArray;
				try {
					if (jsonArray != null&& !((JSONObject) jsonArray.get(0)).has(Constant.District.API_STATUS)) {
						district.setEnabled(true);
						districtAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_text,JSONUtils.getList(jsonArray,Constant.District.DISTRICT_NAME));
						districtAdapter.setDropDownViewResource(R.layout.spinner_text);
						district.setAdapter(districtAdapter);
						district.setSelection(districtAdapter.getPosition(districtName));
						district.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> arg0,View arg1, int position, long arg3) {
								districtErrorMsg.setVisibility(View.GONE);
							}
							@Override
							public void onNothingSelected(AdapterView<?> arg0) {}
						});

					} else {
						Toast.makeText(getActivity(),((JSONObject) jsonArray.get(0)).getString(Constant.District.API_MESSAGE),Toast.LENGTH_LONG).show();
						notifyAdapter(districtAdapter,DISTRICT);
						//notifyAdapter(districtAdapter);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		districtListWebserviceClient.execute(stateId, countryId);

	}
	@Override
	public void onResume() {
		super.onResume();
		this.getActivity().getActionBar().setTitle("My Account");
	}
	
	//get the list of area(Location)
	public void getAreaList() {
		JSONArray jsonArray = CreateJsonArrayFileIntoCache.readAreaListJsonData(getActivity().getApplicationContext());
		try {
			if(jsonArray != null && !((JSONObject)jsonArray.get(0)).has(Constant.Area.API_STATUS)) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					areaMap.put(jsonObject.getString(Constant.Area.AREA_NAME).trim(),jsonObject.getInt(Constant.Area.AREA_ID));
				}
			} else {
				Toast.makeText(getActivity(), "No area found", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
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
				CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
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
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
		googleMap.animateCamera(zoom);
		lattitudeEditText.setText(String.valueOf(latlng.latitude));
	    longitudeEditText.setText(String.valueOf(latlng.longitude));
	}
	//END	
	//set editable editbox
	public void setEditableEditBox(){
		
		propertyDeal.setEnabled(true);
		areaDeal.setEnabled(true);
		languageKnown.setEnabled(true);
		photobutton.setEnabled(true);
		logobutton.setEnabled(true);
		logoTextView.setVisibility(View.VISIBLE);
		photoTextView.setVisibility(View.VISIBLE);
		myAccountLogoImageView.setVisibility(View.VISIBLE);
		myAccountPhotoImageView.setVisibility(View.VISIBLE);
		firstname.setEnabled(true);
		lastname.setEnabled(true);
		//phonemobile.setEnabled(true);
		postcode.setEnabled(true);
		companyname.setEnabled(true);
		website.setEnabled(true);
		address.setEnabled(true);
		phoneOffice.setEnabled(true);
		//email.setEnabled(true);
		myAccountStateListSpinner.setClickable(true);
		district.setClickable(true);
		city.setClickable(true);
		facebookLink.setEnabled(true);
		twitterLink.setEnabled(true);
		linkedinLink.setEnabled(true);
		businessPageLink.setEnabled(true);
		affiatedWith.setEnabled(true);
		businessScience.setClickable(true);
		saveButton.setEnabled(true);
		lattitudeEditText.setEnabled(true);
		longitudeEditText.setEnabled(true);
		
	}
	
	//set read only editbox
	public void setReadOnlyEditBox(){
		propertyDeal.setEnabled(false);
		areaDeal.setEnabled(false);
		languageKnown.setEnabled(false);
		photobutton.setEnabled(false);
		logobutton.setEnabled(false);
		//logoTextView.setVisibility(View.GONE);
		//photoTextView.setVisibility(View.GONE);
		//myAccountLogoImageView.setVisibility(View.GONE);
		//myAccountPhotoImageView.setVisibility(View.GONE);
		firstname.setEnabled(false);
		lastname.setEnabled(false);
		phonemobile.setEnabled(false);
		postcode.setEnabled(false);
		companyname.setEnabled(false);
		website.setEnabled(false);
		address.setEnabled(false);
		phoneOffice.setEnabled(false);
		email.setEnabled(false);
		myAccountStateListSpinner.setClickable(false);
		district.setClickable(false);
		city.setClickable(false);
		facebookLink.setEnabled(false);
		twitterLink.setEnabled(false);
		linkedinLink.setEnabled(false);
		businessPageLink.setEnabled(false);
		affiatedWith.setEnabled(false);
		businessScience.setClickable(false);
		saveButton.setEnabled(false);
		lattitudeEditText.setEnabled(false);
		longitudeEditText.setEnabled(false);
	}
	
	//get the businness
	private void setBusinessScinceSpinner(String businessScince) {
		ArrayList<String> years = new ArrayList<String>();
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		
		for (int i = 1960; i <= thisYear; i++) {
			years.add(Integer.toString(i));
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text, years);
		adapter.setDropDownViewResource(R.layout.spinner_text);
		businessScience.setAdapter(adapter);
		businessScience.setSelection(adapter.getPosition(businessScince));
	}
	@Override
	public void callParentMethod() {
		super.callParentMethod();
	}
	
	//Notify adapter if data not found
	private void notifyAdapter(ArrayAdapter<String> adapter,String spinnerString) {
		if(adapter != null) {
			adapter.clear();
			if(spinnerString.equals(DISTRICT)) {
				adapter.insert("No district found",0);
				district.setEnabled(false);
			} else if(spinnerString.equals(CITY)) {
				adapter.insert("No city found",0);
				city.setEnabled(false);
			}
			adapter.notifyDataSetChanged();
		}
	}
	
	//Check state spinner
	private boolean checkState() {
		if(myAccountStateListSpinner.getSelectedItem() != null) {
			if(myAccountStateListSpinner.getSelectedItem().equals("No state found")) {
				Toast.makeText(getActivity(), "No state found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
		
	//Check district spinner
	private boolean checkDistrict() {
		if(district.getSelectedItem() != null) {
			if(district.getSelectedItem().equals("No district found")) {
				Toast.makeText(getActivity(), "No district found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	
	//Check district spinner
	private boolean checkCity() {
		if(city.getSelectedItem() != null) {
			if(city.getSelectedItem().equals("No city found")) {
				Toast.makeText(getActivity(), "No city found", Toast.LENGTH_LONG).show();
				return true;
			}
		}
		return false;
	}
	
	//Store default image if user not select
	private void setdefaultImage(String aUrl,String name) {
		try {
			URL url = new URL(aUrl);
			storeCropImage(BitmapFactory.decodeStream(url.openConnection().getInputStream()),name);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		if(imageNameMap != null)
			imageNameMap.clear();
	}
}
