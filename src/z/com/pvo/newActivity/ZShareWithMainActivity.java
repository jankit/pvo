package z.com.pvo.newActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import z.com.pvo.newAdapter.ZAminitiesCommertialAdaptor;
import z.com.pvo.newAdapter.ZAminitiesResidentialAdaptor;
import z.com.pvo.newAdapter.ZAreaListAdaptor;
import z.com.pvo.newAdapter.ZCommercialNomineeListAdaptor;
import z.com.pvo.newAdapter.ZGroupListAdaptor;
import z.com.pvo.newAdapter.ZNomineeListResidentialAdaptor;
import z.com.pvo.newAdapter.ZPreeferedListAdaptor;
import z.com.pvo.newAdapter.ZTersmListAdaptor;
import z.com.pvo.util.ProjectUtility;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.activity.MainFragmentActivity;
import com.pvo.activity.R;
import com.pvo.components.SpinnerItem;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.MyPropertyAddService;
import com.pvo.user.service.MyPropertyEditService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

public class ZShareWithMainActivity extends FragmentActivity implements TabHost.OnTabChangeListener,
ViewPager.OnPageChangeListener, AnimationListener, OnClickListener  {
	
	
	private Boolean isPrint = true;
	private String TAG = "ShareWithMainActivity";
	
	private UserSessionManager userSessionManager;
	private ViewPager viewpager;
	private TabHost tabHost;
	private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, ZShareWithMainActivity.TabInfo>();
	private HorizontalScrollView tabScrollView;
	private Button btn_shareWith_shareWithAndSave;
	private Intent intent;
	private MyPropertyAddService addPropertyService;
	private MyPropertyEditService myPropertyEditService;
	private ImageView iv_actionBar_menu;
	private static TextView tv_actionbar_title;
	
	private File myDir;
	
	/*
	 * Maintains extrinsic info of a tab's construct
	 */
	private class TabInfo {
		private String tag;
		private Class<?> clss;
		private Fragment fragment;

		TabInfo(String tag, Class<?> clazz) {
			this.tag = tag;
			this.clss = clazz;
		}
	}

	/*
	 * A simple factory that returns dummy views to the Tabhost
	 */
	class TabFactory implements TabContentFactory {
		private final Context mContext;

		public TabFactory(Context context) {
			mContext = context;
		}

		// @see
		// android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.z_share_with_activity);
		
		intent = getIntent();
		myDir = new File(Environment.getExternalStorageDirectory().toString()+"/PVO/");
		
		/*
		 * Initialize User session
		 */
		//ProjectUtility.setUsersession(getApplicationContext());
		userSessionManager = new UserSessionManager(getApplicationContext());
				
		ProjectUtility.sys(isPrint,TAG,"STR_OPTIONS Sale/Rent: "+intent.getStringExtra(Constant.AddProperty.STR_OPTIONS));
		ProjectUtility.sys(isPrint,TAG,"Residential/Commercial: "+intent.getStringExtra(Constant.AddProperty.PURPOSE));
		ProjectUtility.sys(isPrint,TAG,"PropertyType: "+intent.getStringExtra(Constant.AddProperty.PROPERTY_TYPE));
		ProjectUtility.sys(isPrint,TAG,"Addredd: "+intent.getStringExtra(Constant.AddProperty.ADDRESS));
		ProjectUtility.sys(isPrint,TAG,"StateId: "+intent.getStringExtra(Constant.AddProperty.STATE_ID));
		ProjectUtility.sys(isPrint,TAG,"CityId: "+intent.getStringExtra(Constant.AddProperty.CITY_ID));
		ProjectUtility.sys(isPrint,TAG,"LactionId: "+intent.getStringExtra(Constant.AddProperty.LOCATION));
		ProjectUtility.sys(isPrint,TAG,"Latitude: "+intent.getStringExtra(Constant.AddProperty.LATITUDE));
		ProjectUtility.sys(isPrint,TAG,"Lomgitude: "+intent.getStringExtra(Constant.AddProperty.LONGITUDE));
		ProjectUtility.sys(isPrint,TAG,"Bed: "+intent.getStringExtra(Constant.AddProperty.BED));
		ProjectUtility.sys(isPrint,TAG,"Bath Room: "+intent.getStringExtra(Constant.AddProperty.BATH_ROOM));
		ProjectUtility.sys(isPrint,TAG,"Floor: "+intent.getStringExtra(Constant.AddProperty.FLOOR));
		ProjectUtility.sys(isPrint,TAG,"Furnish: "+intent.getStringExtra(Constant.AddProperty.FURNISH_STATUS));
		ProjectUtility.sys(isPrint,TAG,"Area: "+intent.getStringExtra(Constant.AddProperty.AREA_SQ_FIT));
		ProjectUtility.sys(isPrint,TAG,"Area unit: "+intent.getStringExtra(Constant.AddProperty.AREA_YARD));
		ProjectUtility.sys(isPrint,TAG,"ConstructionArea: "+intent.getStringExtra(Constant.AddProperty.CONSTRUCTION_AREA));
		ProjectUtility.sys(isPrint,TAG,"ConstUnit: "+intent.getStringExtra(Constant.AddProperty.AREA_UNIT));
		ProjectUtility.sys(isPrint,TAG,"BuildingType: "+intent.getStringExtra(Constant.AddProperty.RISE));
		ProjectUtility.sys(isPrint,TAG,"Price: "+intent.getStringExtra(Constant.AddProperty.PRICE).replaceAll(",", ""));
		ProjectUtility.sys(isPrint,TAG,"Dastawage: "+intent.getStringExtra(Constant.AddProperty.DASTAWAGE));
		ProjectUtility.sys(isPrint,TAG,"TransferFees: "+intent.getStringExtra(Constant.AddProperty.TRANSFER_FEES));
		ProjectUtility.sys(isPrint,TAG,"AECAuda: "+intent.getStringExtra(Constant.AddProperty.AEC_AUDA));
		ProjectUtility.sys(isPrint,TAG,"Parking: "+intent.getStringExtra(Constant.AddProperty.PARKING));
		ProjectUtility.sys(isPrint,TAG,"YearBuildUp: "+intent.getStringExtra(Constant.AddProperty.YEAR_BUILD_UP));
		ProjectUtility.sys(isPrint,TAG,"OnRoad: "+intent.getStringExtra(Constant.AddProperty.ON_ROAD));
		ProjectUtility.sys(isPrint,TAG,"Comment: "+intent.getStringExtra(Constant.AddProperty.COMMENTS));
		ProjectUtility.sys(isPrint,TAG,"TPScheme: "+intent.getStringExtra(Constant.AddProperty.CMBTP_SCHEME));
		ProjectUtility.sys(isPrint,TAG,"Zone: "+intent.getStringExtra(Constant.AddProperty.CHK_ZONE));
		ProjectUtility.sys(isPrint,TAG,"NA: "+intent.getStringExtra(Constant.AddProperty.NA_STATUS));
		ProjectUtility.sys(isPrint,TAG,"ImagePath: "+intent.getStringExtra(Constant.AddProperty.IMAGES));
		
		ProjectUtility.sys(isPrint,TAG,"NAVISHARAT: "+intent.getBooleanExtra(Constant.AddProperty.NAVISHARAT,false));
		ProjectUtility.sys(isPrint,TAG,"SHREE_SARKAR: "+intent.getBooleanExtra(Constant.AddProperty.SHREE_SARKAR,false));
		ProjectUtility.sys(isPrint,TAG,"KHETI: "+intent.getBooleanExtra(Constant.AddProperty.KHETI,false));
		ProjectUtility.sys(isPrint,TAG,"PRASSAP: "+intent.getBooleanExtra(Constant.AddProperty.PRASSAP,false));
		ProjectUtility.sys(isPrint,TAG,"DIS_PUTE: "+intent.getBooleanExtra(Constant.AddProperty.DIS_PUTE,false));
		ProjectUtility.sys(isPrint,TAG,"SHREE_SARKAR: "+intent.getBooleanExtra(Constant.AddProperty.SHREE_SARKAR,false));
		ProjectUtility.sys(isPrint,TAG,"DoNotKnow: "+intent.getBooleanExtra("DoNotKnow",false));
		ProjectUtility.sys(isPrint,TAG,"Recidential Nominee list id"+StringUtils.defaultString(StringUtils.join(ZNomineeListResidentialAdaptor.getNomineeId(), ",")));
		ProjectUtility.sys(isPrint,TAG,"Commertial Nominee list id"+StringUtils.defaultString(StringUtils.join(ZCommercialNomineeListAdaptor.getCommercialoNomineeId(), ",")));
		ProjectUtility.sys(isPrint,TAG,"Facility id"+StringUtils.defaultString(StringUtils.join(ZAminitiesResidentialAdaptor.getAmenitiesId(), ",")));
				
		/*getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setCustomView(R.layout.actionbar_custom);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(true);*/
		
		getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setCustomView(R.layout.actionbar_custom);
        getActionBar().setDisplayShowCustomEnabled(true);
		
		 tv_actionbar_title = (TextView) getActionBar().getCustomView().findViewById(R.id.tv_actionbar_title);
		 tv_actionbar_title.setText("SHARE WITH");
	     iv_actionBar_menu = (ImageView) getActionBar().getCustomView().findViewById(R.id.iv_actionBar_menu);
	     iv_actionBar_menu.setImageResource(R.drawable.pre);
	     iv_actionBar_menu.setOnClickListener(this);
		
		viewpager = (ViewPager) findViewById(R.id.firstMenuItemTabViewpager);
		viewpager.setOffscreenPageLimit(2);
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabScrollView = (HorizontalScrollView) findViewById(R.id.tabScrollView);
		btn_shareWith_shareWithAndSave = (Button) findViewById(R.id.btn_shareWith_shareWithAndSave);
		btn_shareWith_shareWithAndSave.setOnClickListener(this);
		/*
		 * initialize tabHost 
		 */
		initialiseTabHost();

		/*
		 * set current to handle null pointer
		 */
		setCurrentTab();

		viewpager.setAdapter(new SahreWithAdapter(getSupportFragmentManager()));
		// On view page change
		viewpager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// Scroll tab view on page chenge
				// http://stackoverflow.com/questions/7875939/tabhost-in-scrollview-scroll-to-tab-when-selected
				View tabView = tabHost.getCurrentTabView();
				int scrollPos = tabView.getLeft() - (tabScrollView.getWidth() - tabView.getWidth());
				tabScrollView.smoothScrollTo(scrollPos * tabView.getWidth(), tabView.getWidth());
				tabHost.setCurrentTab(position);
			}
		});
		
	}


	/*
	 * Initialize the Tab Host
	 */
	private void initialiseTabHost() {
		tabHost.setup();
		TabInfo tabInfo = null;
		// 1) Attribute
		ZShareWithMainActivity.AddTab(ZShareWithMainActivity.this,this.tabHost,this.tabHost.newTabSpec("GROUP").setIndicator(createTabView(getApplicationContext(), "GROUP")),
		(tabInfo = new TabInfo("GROUP", ZShareWithGroupListTab.class)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		
		ZShareWithMainActivity.AddTab(ZShareWithMainActivity.this,this.tabHost,this.tabHost.newTabSpec("AREA").setIndicator(createTabView(getApplicationContext(), "AREA")),
		(tabInfo = new TabInfo("AREA", ZShareWithAreaListTab.class)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		tabHost.setOnTabChangedListener(this);
		
		ZShareWithMainActivity.AddTab(ZShareWithMainActivity.this,this.tabHost,this.tabHost.newTabSpec("PREEFERED").setIndicator(createTabView(getApplicationContext(), "PREEFERED")),
		(tabInfo = new TabInfo("PREEFERED", ZShareWithPreeferedListTab.class)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		tabHost.setOnTabChangedListener(this);

	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		overridePendingTransition(R.anim.new_left_to_right_back,R.anim.new_right_to_left_back);
	}

	@Override
	public void onAnimationStart(Animation animation) {}


	@Override
	public void onAnimationEnd(Animation animation) {}


	@Override
	public void onAnimationRepeat(Animation animation) {}


	@Override
	public void onPageScrollStateChanged(int arg0) {}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {}


	@Override
	public void onPageSelected(int arg0) {}


	@Override
	public void onTabChanged(String tabId) {
		int activeTab = this.tabHost.getCurrentTab();
		this.viewpager.setCurrentItem(activeTab);
	}
	
	
	/*
	 * Return tab view and set tab text
	 * Set tab custom view and text color
	 */
	private static View createTabView(Context context, String tabText) {
		View view = LayoutInflater.from(context).inflate(R.layout.z_custom_tab_common, null, false);
		TextView tv = (TextView) view.findViewById(R.id.tabTitleText);
		tv.setText(tabText);
		//tabIndicator = (ImageView) view.findViewById(R.id.iv_tabIndicator);
		return view;
	}

	/*
	 * Add Tab content to the Tabhost 
	 */
	private static void AddTab(ZShareWithMainActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
		tabSpec.setContent(activity.new TabFactory(activity));
		tabHost.addTab(tabSpec);
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				overridePendingTransition(R.anim.new_left_to_right_back,R.anim.new_right_to_left_back);
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	
	/*
	 * set the page according to category and change tab
	 */
	private class SahreWithAdapter extends FragmentStatePagerAdapter {

		public SahreWithAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public Fragment getItem(int pos) {
			/*
			 * set all page into viewer page of site data home menu
			 */
				switch (pos) {
				case 0:
					return ZShareWithGroupListTab.newInstance();
				case 1:
					return ZShareWithAreaListTab.newInstance();
				case 2:
					return ZShareWithPreeferedListTab.newInstance();
				default:
					return ZShareWithGroupListTab.newInstance();
				}
		}

		@Override
		public int getCount() {
			return tabHost.getTabWidget().getTabCount();
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_shareWith_shareWithAndSave:
				saveProperty();
				//ProjectUtility.sys(isPrint,TAG,"Group id: "+StringUtils.join(ZGroupListAdaptor.getGroupId(), ","));
				break;
			case R.id.iv_actionBar_menu:
				finish();
				overridePendingTransition(R.anim.new_left_to_right_back,R.anim.new_right_to_left_back);
				break;
			default:
				break;
		}
	}


	// Set current tab when back after image editing
	private void setCurrentTab() {
		// set current page
		tabHost.setCurrentTab(0);
		viewpager.setCurrentItem(0);
		//tabIndicator.setVisibility(View.VISIBLE);
		
		View tabView = tabHost.getCurrentTabView();
		int scrollPos = tabView.getLeft() - (tabScrollView.getWidth() - tabView.getWidth()) / 2;
		tabScrollView.smoothScrollTo(scrollPos, 0);
	}
	
	private void saveProperty() {
		try {
			String propertyId = null;
			//ProjectUtility.sys(isPrint,TAG,"Nominee list id"+StringUtils.defaultString(StringUtils.join(ZNomineeListAdaptor.getNomineeId(), ",")));
			//ProjectUtility.sys(isPrint,TAG,"Facility id"+StringUtils.defaultString(StringUtils.join(ZAminitiesListAdaptor.getAmenitiesId(), ",")));
			ProjectUtility.sys(isPrint,TAG,"Group id"+StringUtils.defaultString(String.valueOf(ZGroupListAdaptor.getGroupId())));
			//ProjectUtility.sys(isPrint,TAG,"Area id"+StringUtils.defaultString(String.valueOf(ZAreaListAdaptor.getAreaId())));
			//ProjectUtility.sys(isPrint,TAG,"Prefeer id"+StringUtils.defaultString(String.valueOf(ZPreeferedListAdaptor.getPreeferedId())));
			//ProjectUtility.sys(isPrint,TAG,"Na status -->"+StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.NA_STATUS)));
			//ProjectUtility.sys(isPrint,TAG,"Om rorad-->"+String.valueOf(SpinnerItem.getOnRoadList().get(intent.getStringExtra(Constant.AddProperty.ON_ROAD))));
			//ProjectUtility.sys(isPrint,TAG,"on road-->"+StringUtils.defaultString(String.valueOf(SpinnerItem.getOnRoadList().get(intent.getStringExtra(Constant.AddProperty.ON_ROAD)))));
			
			
			
			storeScropImage();
			WebserviceClient webserviceClientAdd;
			
			String nomineeId = "";
			if(intent.getStringExtra(Constant.AddProperty.PURPOSE).equals("Residential")) {
				nomineeId = StringUtils.defaultString(StringUtils.join(ZNomineeListResidentialAdaptor.getNomineeId(), ","));
			} else if(intent.getStringExtra(Constant.AddProperty.PURPOSE).equals("Commercial")) {
				nomineeId = StringUtils.defaultString(StringUtils.join(ZCommercialNomineeListAdaptor.getCommercialoNomineeId(), ","));
			}
			/*
			 * Call web service according to add or edit proeprty
			 */
			if(intent.getStringExtra("From") != null) {
				propertyId = intent.getStringExtra(Constant.EditProperty.ID);
				myPropertyEditService = new MyPropertyEditService();
				webserviceClientAdd = new WebserviceClient(ZShareWithMainActivity.this,myPropertyEditService);
				
			} else {
				addPropertyService = new MyPropertyAddService();
				webserviceClientAdd = new WebserviceClient(ZShareWithMainActivity.this,addPropertyService);
			}
			
			
			
			webserviceClientAdd.setResponseListner(new ResponseListner() {
				@Override
				public void handleResponse(Object res) {
					final JSONObject response = (JSONObject) res;
					try {
						if (response != null) {
							if (String.valueOf(response.getString(Constant.AddProperty.API_STATUS)).equals("1")) {
								AlertDialog alertDialog = new AlertDialog.Builder(ZShareWithMainActivity.this).create();
								alertDialog.setCancelable(false);
								alertDialog.setTitle(response.getString(Constant.AddProperty.API_MESSAGE));
								alertDialog.setMessage("1) This Facilities for one time message sending only.\n 2) After 15 Days of posting a property it will automatically be inactived from your account and it will not be searchable to others and it will shown in your &apos;Inactive&apos; folders where you can re-activate it for another 15 days.");
								
								alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										/*
										 * Clear all id array list
										 */
										ProjectUtility.clearAllIdArrayList();
										
										/*
										 * Remove all photo if response is property is successfully add/edit
										 */
										ProjectUtility.removeAllResComPropPhoto();
										
										
										/*
										 * Redirect to main fragment and set my property listing fragment
										 */
										
										try {
											Intent intent = new Intent(getApplicationContext(), MainFragmentActivity.class);
											intent.putExtra("From", "AddProperty");
											intent.putExtra("PropertyId", response.getString("id"));
											startActivity(intent);
										} catch (JSONException e) {
											e.printStackTrace();
										}
									}
								});
								alertDialog.show();
							} else {
								Toast.makeText(ZShareWithMainActivity.this,String.valueOf(response.getString(Constant.AddProperty.API_MESSAGE)),Toast.LENGTH_LONG).show();
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
	
			/*
			 * This is for terms value get 
			 * Create the string array as per constant array size 
			 * And fill to zero by default 
			 * if check position is in tearms array then set value to "1"
			 */
			Iterator<String> termValuesIterator = ZTersmListAdaptor.getSelectedTerms().iterator();
			String[] termValues = new String[Constant.TREMS.length];
			Arrays.fill(termValues, "0");
			while(termValuesIterator.hasNext()) {
				termValues[Integer.parseInt(termValuesIterator.next())] = "1";
			}
			
			/*
			 * Facility
			 * Pass facility id according to residential or commercial property type
			 */
			String facilityId = null;
			if(intent.getStringExtra(Constant.AddProperty.PURPOSE).equals(Constant.Residential.RESIDENTIAL)) {
				facilityId = StringUtils.defaultString(StringUtils.join(ZAminitiesResidentialAdaptor.getAmenitiesId(), ","));
			} else if(intent.getStringExtra(Constant.AddProperty.PURPOSE).equals(Constant.Commercial.COMMERCIAL)) {
				facilityId = StringUtils.defaultString(StringUtils.join(ZAminitiesCommertialAdaptor.getAmenitiesId(), ","));
			}
			
			
			webserviceClientAdd.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					userSessionManager.getSessionValue(Constant.Login.USER_ID),// 1-userID
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.PROPERTY_TYPE)), // 2-Property Type
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.ADDRESS)),//3-Address
					"", // 4-PostCode
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.LOCATION)),// 5-cmbarea1-Location
					"1",// 6-countryid
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.STATE_ID)),// 7-stateId
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.CITY_ID)),// 8-CityId
					"",// 9-Distric ID
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.STR_OPTIONS)),// 10-option rent/sale
					"0",//landmark1Object.getString(Constant.Landmark.LANDMARK_ID),// 11-landmark1
					"0",//landmark2Object.getString(Constant.Landmark.LANDMARK_ID),// 12-landmark2
					"",//addProplandmarkOtherEditbox.getText().toString(),// 13-landmark1other
					"",//addPropLandmark2Editbox.getText().toString(),// 14-landmark2other
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.LONGITUDE)),// 15-Longitude
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.LATITUDE)),// 16-Latitude
					"0",//17 PropertyOccupacy
					"",// 18-occupName
					"",// 19-occupDetail
					"",// 20-RelaseDate
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.PRICE).replaceAll(",", "")),// 21-price
					"",// 22-totalprice
					"",// 23-rent
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.DASTAWAGE).replaceAll(",", "")),// 24-dastawage
					"",// 25-rentDeposition
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.AREA_UNIT)),// 26-areaunit
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.PURPOSE)),// 27-purpose ("Commercial", "Residential");
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.AREA_SQ_FIT)),// 28-MinArea
					"",// 29-Max Area
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.YEAR_BUILD_UP)),// 30-yearbuiltup
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.COMMENTS)),// 31-Comment
					"",// 32-hint
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.BED)),// 33-bed
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.FURNISH_STATUS)),// 34-FurnidhOption
					//String.valueOf(SpinnerItem.getAddPropFurnishOptionList().get(intent.getStringExtra(Constant.AddProperty.FURNISH_STATUS))),// 34-FurnidhOption
					"",// 35-furnishDetail
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.FLOOR)),//36-floor
					//StringUtils.defaultString(String.valueOf(SpinnerItem.getAddPropFloorList().get(intent.getStringExtra(Constant.AddProperty.FLOOR)))),// 36-floor
					StringUtils.defaultString(String.valueOf(SpinnerItem.getAddPropBuildingTypeList().get(intent.getStringExtra(Constant.AddProperty.RISE)))),// 37-BuildingType
					"",// 38-whometolet
					"",// 39-whometoletOther
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.PARKING).replaceAll(",", "")),// 40parking
					"7",// 41-frontheight
					"8",// 42-attachcommon
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.CONSTRUCTION_AREA)),// 43-constructionArea
					"",// 44-bunglowtype
					"",// 45-min plot area
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.PLOT_AREA)),// 46-plotarea
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.PLOT_AREA_UNIT)),// 47-plotareaunit
					"",// 48-plottype
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.NA_STATUS)),// 49-nastatus
					termValues[2],// 50-kheti
					termValues[0],// 51-navisharat
					termValues[1],// 52-junisharat
					termValues[3],// 53-prassap
					termValues[4],// 54-dispute
					"",// 55-titleclear
					termValues[5],// 56-shreesarkar
					StringUtils.defaultString(String.valueOf(SpinnerItem.getOnRoadList().get(intent.getStringExtra(Constant.AddProperty.ON_ROAD)))),// 57-onroad
					"1",// 58-PublishingOption  Area-1,Preferred only-2,Group-3,All Broker-4
					"",// 59-maintenance
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.TRANSFER_FEES).replaceAll(",", "")),// 60-transferfees
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.AEC_AUDA).replaceAll(",", "")),// 61-aecauda
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.CMBTP_SCHEME)),// 62-cmbtpscheme
					StringUtils.defaultString(intent.getStringExtra(Constant.AddProperty.CHK_ZONE)),// 63-chkzone
					"1",//64
					"1",//65
					nomineeId,// 66-chk-NomineeId
					facilityId,// 67-chkfacility
					StringUtils.defaultString(StringUtils.join(ZPreeferedListAdaptor.getSelectedPreeferedId(), ",")),//68-chkbroker
					getImagePath(0),//Image1
					getImagePath(1),//Image2
					getImagePath(2),//Image3
					getImagePath(3),//Image4
					getImagePath(4),//Image5
					StringUtils.defaultString(StringUtils.join(ZGroupListAdaptor.getGroupId(), ",")),//chkgroup
					StringUtils.defaultString(StringUtils.join(ZAreaListAdaptor.getAreaId(), ",")),//chkarea
					StringUtils.defaultString(propertyId)
					);
				//Toast.makeText(getApplicationContext(), "Please Fill Mandatory Field", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * get the image path from  residence photo directory
	 */
	private String getImagePath(int position) {
		
		if(intent.getStringExtra(Constant.AddProperty.PURPOSE).equals(Constant.Residential.RESIDENTIAL)) {
			if(position < ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.RESIDENTIAL_PHOTO_PATH).size())
				return ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.RESIDENTIAL_PHOTO_PATH).get(position);
		} else if(intent.getStringExtra(Constant.AddProperty.PURPOSE).equals(Constant.Commercial.COMMERCIAL)) {
			if(position < ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.COMMERCIAL_PHOTO_PATH).size())
				return ProjectUtility.RetriveCapturedImagePath(Constant.ZAddProperty.COMMERCIAL_PHOTO_PATH).get(position);
		}
		return new File(Environment.getExternalStorageDirectory()+ "/PVO/NoImage.jpeg").getAbsolutePath();
	}
	
	/*
	 * Store noPhoto image into sdcard
	 * if property photo not available then pass no photo image by default
	 */
	private void storeScropImage() {
		if(!myDir.exists())
			myDir.mkdir();
		
		File file = new File (myDir,"NoImage.jpeg");
        try {
           FileOutputStream out = new FileOutputStream(file);
           BitmapFactory.decodeResource(getResources(), R.drawable.no_image).compress(Bitmap.CompressFormat.JPEG, 90, out);
           out.flush();
           out.close();
        } catch (Exception e) {
           e.printStackTrace();
        }
	}
	
	
	
}
