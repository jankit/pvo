package z.com.pvo.newActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import z.com.pvo.cache.ImageLoader;
import z.com.pvo.newAdapter.ZNomineeCallListAdaptor;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.pvo.activity.MainFragmentActivity;
import com.pvo.activity.R;
import com.pvo.components.SpinnerItem;
import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.FindByPropertyIdService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.pvo.util.ConvertDateFormat;

public class ZPropertyDetail extends PVOFragment implements LocationListener, OnClickListener {
	
    private UserSessionManager userSessionManager;
    
	private Gallery gal_propDet_propPhoto;
    
    /*
     * All image view 
     */
    private ImageView iv_propDet_pre, iv_propDet_next, /*iv_propDet_map*/ iv_propDet_calc,
    					iv_propDet_brokerPhoto;
    
    
    /*
     * All text view 
     */
    private TextView tv_propDet_photoCounter, tv_propDet_price, tv_propDet_bed, tv_propDet_bath,
    				 tv_propDet_propType, tv_propDet_address1, tv_propDet_postedDate, tv_propDet_location,
    				 tv_propDet_watch, /*tv_propDet_search,*/ tv_propDet_area, tv_propDet_constArea, tv_propDet_floor,
    				 tv_propDet_furnish, tv_propDet_buildingType, tv_propDet_purpose, tv_propDet_builtyear,
    				 tv_propDet_price2, tv_propDet_maintenance, tv_propDet_transferFee, tv_propDet_aecAuda,
    				 tv_propDet_parkingCharge, tv_propDet_dastavej , tv_propDet_extraComment, tv_propDet_brokerName, tv_propDet_company,
    				 tv_propDet_brk_officeAddress, tv_propDet_price2Plot, tv_propDet_tpScheme, tv_propDet_zone,
    				 tv_propDet_na, tv_propDet_roadTouch, tv_propDet_propId, tv_propDet_mobileNumber, tv_propDet_plotArea,
    				 tv_propDet_totalProp, tv_propDet_totalReq, tv_propDet_rentSale, tv_propDet_price2__flat;   
    
    /*
     * Linear layout
     */
    private LinearLayout ll_propDet_bed, ll_propDet_bath, ll_propDet_constArea, ll_propDet_plotArea,
    					 ll_propDet_floor, ll_propDet_furnishOption, ll_propDet_buildingType, ll_propDet_saleRent,
    					 ll_propDet_purposeComRes, ll_propDet_plot, ll_propDet_map, ll_propDet_area, ll_propDet_buildYear;
    
    /*
     * Relative layout
     */
    private RelativeLayout rl_propDet_forSalRent;
    
    /*
     * All button 
     */
    private Button btn_propDet_forSaleRent, btn_propDet_email, btn_propDet_call, btn_propDet_forSaleRentPlot,
    			   btn_propDet_nearByLocation, btn_propDet_forSaleRent_flat;	
    
    /*
     * Progress bar
     */
    private ProgressBar progres_propDet_map;
    
    private ImageLoader imageLoader;
    private HorizontalScrollView hsv_propDet_facility;
    private LinearLayout ll_propDet_facility;
    private FindByPropertyIdService findByPropertyIdService;
    private Bundle propertyIdIntent;
    
    
    private static final String GOOGLE_API_KEY = "AIzaSyD4ciJ2zFXfp5XPhmMVusaz4DMVxLKCqWs";
    GoogleMap googleMap;
    EditText placeText;
    double latitude = 0;
    double longitude = 0;
    
    /*
     * Nominee list dailog
     */
    private ZNomineeCallListDialog zNomineeCallListDialog;
    
    
    public ZPropertyDetail() {
		setContentView(R.layout.z_prop_detail_page);
	}
	
	
	@Override
	public void init(Bundle savedInstanceState) {

		MainFragmentActivity.setTitle("Property Detail");
		imageLoader=new ImageLoader(getActivity());
		userSessionManager = new UserSessionManager(getActivity());
		 
		 findByPropertyIdService		= new FindByPropertyIdService();
		 propertyIdIntent 			= getArguments();
		 
		
		 iv_propDet_pre = (ImageView) findViewById(R.id.iv_propDet_pre);
		 iv_propDet_next = (ImageView) findViewById(R.id.iv_propDet_next);
		 //iv_propDet_map = (ImageView) findViewById(R.id.iv_propDet_map); 
		 iv_propDet_calc = (ImageView) findViewById(R.id.iv_propDet_calc);
		 iv_propDet_brokerPhoto = (ImageView) findViewById(R.id.iv_propDet_brokerPhoto);
		 
		tv_propDet_photoCounter = (TextView) findViewById(R.id.tv_propDet_photoCounter);
		tv_propDet_price = (TextView) findViewById(R.id.tv_propDet_price);
		tv_propDet_bed = (TextView) findViewById(R.id.tv_propDet_bed);
		tv_propDet_bath = (TextView) findViewById(R.id.tv_propDet_bath);
		tv_propDet_propType = (TextView) findViewById(R.id. tv_propDet_propType);
		tv_propDet_address1 = (TextView) findViewById(R.id.tv_propDet_address1);
		tv_propDet_postedDate = (TextView) findViewById(R.id.tv_propDet_postedDate);
		tv_propDet_location = (TextView) findViewById(R.id.tv_propDet_location);
		tv_propDet_watch = (TextView) findViewById(R.id.tv_propDet_watch);
		//tv_propDet_search = (TextView) findViewById(R.id.tv_propDet_search);
		tv_propDet_area = (TextView) findViewById(R.id.tv_propDet_area);
		tv_propDet_constArea = (TextView) findViewById(R.id.tv_propDet_constArea);
		tv_propDet_floor = (TextView) findViewById(R.id.tv_propDet_floor);
		tv_propDet_furnish = (TextView) findViewById(R.id.tv_propDet_furnish);
		tv_propDet_buildingType = (TextView) findViewById(R.id.tv_propDet_buildingType);
		tv_propDet_purpose = (TextView) findViewById(R.id.tv_propDet_purpose);
		tv_propDet_builtyear = (TextView) findViewById(R.id.tv_propDet_builtyear);
		tv_propDet_price2 = (TextView) findViewById(R.id.tv_propDet_price2);
		tv_propDet_maintenance = (TextView) findViewById(R.id.tv_propDet_maintenance);
		tv_propDet_transferFee = (TextView) findViewById(R.id.tv_propDet_transferFee);
		tv_propDet_aecAuda = (TextView) findViewById(R.id.tv_propDet_aecAuda);
		tv_propDet_parkingCharge = (TextView) findViewById(R.id.tv_propDet_parkingCharge);
		tv_propDet_dastavej = (TextView) findViewById(R.id.tv_propDet_dastavej);
		tv_propDet_extraComment = (TextView) findViewById(R.id.tv_propDet_extraComment);
		tv_propDet_brokerName = (TextView) findViewById(R.id.tv_propDet_brokerName);
		tv_propDet_company = (TextView) findViewById(R.id.tv_propDet_company);
		tv_propDet_brk_officeAddress = (TextView) findViewById(R.id.tv_propDet_brk_officeAddress);
		tv_propDet_price2Plot = (TextView) findViewById(R.id.tv_propDet_price2Plot);
		tv_propDet_tpScheme = (TextView) findViewById(R.id.tv_propDet_tpScheme);
		tv_propDet_zone = (TextView) findViewById(R.id.tv_propDet_zone);
		tv_propDet_na = (TextView) findViewById(R.id.tv_propDet_na);
		tv_propDet_roadTouch = (TextView) findViewById(R.id.tv_propDet_roadTouch);
		tv_propDet_propId = (TextView) findViewById(R.id.tv_propDet_propId);
		tv_propDet_mobileNumber = (TextView) findViewById(R.id.tv_propDet_mobileNumber);
		tv_propDet_plotArea = (TextView) findViewById(R.id.tv_propDet_plotArea);
		tv_propDet_totalProp = (TextView) findViewById(R.id.tv_propDet_totalProp);
		tv_propDet_totalReq = (TextView) findViewById(R.id.tv_propDet_totalReq);
		tv_propDet_rentSale = (TextView) findViewById(R.id.tv_propDet_rentSale);
		tv_propDet_price2__flat = (TextView) findViewById(R.id.tv_propDet_price2__flat);
		
		btn_propDet_forSaleRent = (Button) findViewById(R.id.btn_propDet_forSaleRent);
		btn_propDet_forSaleRentPlot = (Button) findViewById(R.id.btn_propDet_forSaleRentPlot);
		btn_propDet_email = (Button) findViewById(R.id.btn_propDet_email);
		btn_propDet_call = (Button) findViewById(R.id.btn_propDet_call);
		btn_propDet_nearByLocation = (Button) findViewById(R.id.btn_propDet_nearByLocation);
		btn_propDet_forSaleRent_flat = (Button) findViewById(R.id.btn_propDet_forSaleRent_flat);
		
		ll_propDet_bed = (LinearLayout) findViewById(R.id.ll_propDet_bed);
		ll_propDet_bath = (LinearLayout) findViewById(R.id.ll_propDet_bath);
		ll_propDet_constArea = (LinearLayout) findViewById(R.id.ll_propDet_constArea);
		ll_propDet_plotArea = (LinearLayout) findViewById(R.id.ll_propDet_plotArea);
		ll_propDet_floor = (LinearLayout) findViewById(R.id.ll_propDet_floor);
		ll_propDet_furnishOption = (LinearLayout) findViewById(R.id.ll_propDet_furnishOption);
		ll_propDet_buildingType = (LinearLayout) findViewById(R.id.ll_propDet_buildingType);
		ll_propDet_saleRent = (LinearLayout) findViewById(R.id.ll_propDet_saleRent);
		ll_propDet_purposeComRes = (LinearLayout) findViewById(R.id.ll_propDet_purposeComRes);
		ll_propDet_plot = (LinearLayout) findViewById(R.id.ll_propDet_plot);
		ll_propDet_map = (LinearLayout) findViewById(R.id.ll_propDet_map);
		ll_propDet_area = (LinearLayout) findViewById(R.id.ll_propDet_area);
		ll_propDet_buildYear = (LinearLayout) findViewById(R.id.ll_propDet_buildYear);
		
		rl_propDet_forSalRent = (RelativeLayout) findViewById(R.id.rl_propDet_forSalRent);
		
		progres_propDet_map = (ProgressBar) findViewById(R.id.progres_propDet_map);
		
		gal_propDet_propPhoto = (Gallery) findViewById(R.id.gal_propDet_propPhoto);
        ll_propDet_facility = (LinearLayout) findViewById(R.id.ll_propDet_facility);
        
        
        new WebserviceClient(ZPropertyDetail.this, findByPropertyIdService).execute(propertyIdIntent.getString("propertyid"),userSessionManager.getSessionValue(Constant.Login.USER_ID));
        
        /*
         * Register button click event
         */
        btn_propDet_call.setOnClickListener(this);
        btn_propDet_nearByLocation.setOnClickListener(this);
	}
	
	@Override
	public void processResponse(Object response) {
		JSONArray jsonArry=(JSONArray)response;
		try {
			if (response != null && !((JSONObject)jsonArry.get(0)).has(Constant.MyProperty.API_STATUS)) {
				
				JSONObject jsonObject=(JSONObject) jsonArry.getJSONObject(0);
	    		//get Image object from response
	    		final ArrayList<String> imageList = new ArrayList<String>();
	    		imageList.add(jsonObject.getString(Constant.EditProperty.IMAGE1_LINK));
	    		imageList.add(jsonObject.getString(Constant.EditProperty.IMAGE2_LINK));
	    		imageList.add(jsonObject.getString(Constant.EditProperty.IMAGE3_LINK));
	    		imageList.add(jsonObject.getString(Constant.EditProperty.IMAGE4_LINK));
	    		
	    	
	    		//set decode image string and set into image view
	    		gal_propDet_propPhoto.setAdapter(new AddImgAdp(getActivity(),imageList));
	    		 
	    		//gallery.setAdapter(new ImageAdapter(getActivity(),imageList));
	    		gal_propDet_propPhoto.setOnItemSelectedListener(new OnItemSelectedListener() {
		    		@Override
		    		public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long arg3) {
		    			tv_propDet_photoCounter.setText(position+1+"/"+imageList.size());
		    		}
		    		@Override
		    		public void onNothingSelected(AdapterView<?> arg0) {}	
	    		});
	    		
	    		//Set Next image from list 
	    		iv_propDet_next.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						int position = gal_propDet_propPhoto.getSelectedItemPosition() + 1;
						if (position < 0)
					          return;
						if(position < imageList.size())
							gal_propDet_propPhoto.setSelection(position);
					}
				});
	    		
	    		//Set Previous image from list
	    		iv_propDet_pre.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						int position = gal_propDet_propPhoto.getSelectedItemPosition() - 1;
						if (position < 0)
					          return;
						if(position < imageList.size())
							gal_propDet_propPhoto.setSelection(position);
					}
				});
	    		
	    		
	    		/*
	    		 * Property for sale/rent
	    		 */
	    		tv_propDet_rentSale.setText(jsonObject.getString(Constant.AddProperty.PROPERTY_TYPE)+" For "+jsonObject.getString(Constant.AddProperty.STR_OPTIONS));
	    		
	    		
	    		/*
	    		 * Set map into  layout background
	    		 */
	    		mapBitmap(jsonObject.getString(Constant.AddProperty.LATITUDE),jsonObject.getString(Constant.AddProperty.LONGITUDE));
	    		latitude = Double.parseDouble(jsonObject.getString(Constant.AddProperty.LATITUDE));
	    		longitude = Double.parseDouble(jsonObject.getString(Constant.AddProperty.LONGITUDE));
	    		
	    		/*
	    		 * Property id 
	    		 */
	    		tv_propDet_propId.setText("PVO "+jsonObject.getString(Constant.MyProperty.PROPERTY_ID));
	    		
	    		/*
	    		 * Price  
	    		 */
	    		System.out.println("Price--> "+jsonObject.getString(Constant.AddProperty.PRICE));
	    		tv_propDet_price.setText(toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.PRICE))));
	    		
	    		/*
	    		 * Bed room
	    		 */
	    		//Hide bed if property type shop and plot
	    		if(jsonObject.getString("propertytype").equals("Shop") || jsonObject.getString("propertytype").equals("Plot")) {
	    			ll_propDet_bed.setVisibility(View.GONE);
	    		}  else {
	    			ll_propDet_bed.setVisibility(View.VISIBLE);
	    			tv_propDet_bed.setText(jsonObject.getString(Constant.AddProperty.BED));
	    		}

	    		
	    		/*
	    		 * Bathroom
	    		 */
	    		if(jsonObject.getString("propertytype").equals("Plot")) {
	    			ll_propDet_bath.setVisibility(View.GONE);
	    		}  else {
	    			ll_propDet_bath.setVisibility(View.VISIBLE);
	    			tv_propDet_bath.setText("0");
	    		}
	    		
	    		/*
	    		 * Property type 
	    		 * Flat, Shop, bunglow, plot	
	    		 */
	    		tv_propDet_propType.setText(jsonObject.getString(Constant.AddProperty.PROPERTY_TYPE));
	    		
	    		/*
	    		 * Address
	    		 */
	    		tv_propDet_address1.setText(jsonObject.getString(Constant.AddProperty.ADDRESS));
	    		
	    		/*
	    		 * Posted date
	    		 */
	    		tv_propDet_postedDate.setText(ConvertDateFormat.convertDateFormat(jsonObject.getString(Constant.AddProperty.DATE_UPDTE)));
	    		
	    		/*
	    		 * Location
	    		 */
	    		tv_propDet_location.setText(jsonObject.getString(Constant.AddProperty.AREANAME));
	    		
	    		/*
	    		 * Watch
	    		 */
	    		tv_propDet_watch.setText(jsonObject.getString(Constant.AddProperty.DETAIL_COUNT));
	    		
	    		/*
	    		 * Search
	    		 */
	    		//tv_propDet_search.setText("0");
	    		
	    		/*
	    		 * Area
	    		 */
	    		tv_propDet_area.setText(jsonObject.getString(Constant.AddProperty.MIN_AREA)+" "+jsonObject.getString(Constant.AddProperty.AREA_UNIT));
	    		
	    		/*
	    		 * Construction area
	    		 */
	    		if(jsonObject.getString("propertytype").equals("Plot") ||jsonObject.getString("propertytype").equals("Bunglow")) {
	    			ll_propDet_constArea.setVisibility(View.VISIBLE);
	    			ll_propDet_plotArea.setVisibility(View.VISIBLE);
	    			tv_propDet_plotArea.setText(jsonObject.getString(Constant.AddProperty.PLOT_AREA)+" "+jsonObject.getString(Constant.AddProperty.PLOT_AREA_UNIT));
	    			tv_propDet_constArea.setText(jsonObject.getString(Constant.AddProperty.CONSTRUCTION_AREA)+" "+jsonObject.getString(Constant.AddProperty.AREA_UNIT));
	    		} else {
	    			ll_propDet_constArea.setVisibility(View.GONE);
	    		}
	    		
	    		if(jsonObject.getString("propertytype").equals("Plot")) {
	    			ll_propDet_area.setVisibility(View.GONE);
	    			ll_propDet_plotArea.setVisibility(View.VISIBLE);
	    			tv_propDet_plotArea.setText(jsonObject.getString(Constant.AddProperty.PLOT_AREA)+" "+jsonObject.getString(Constant.AddProperty.PLOT_AREA_UNIT));
	    		} else {
	    			ll_propDet_plotArea.setVisibility(View.GONE);
	    			ll_propDet_area.setVisibility(View.VISIBLE);
	    		}
	    		
	    		/*
	    		 * Floor 
	    		 */
	    		if(jsonObject.getString("propertytype").equals("Plot") || jsonObject.getString("propertytype").equals("Bunglow")) {
	    			ll_propDet_floor.setVisibility(View.GONE);
	    		} else {
	    			ll_propDet_floor.setVisibility(View.VISIBLE);
	    			tv_propDet_floor.setText(SpinnerItem.getAddPropFloorListKey(jsonObject.getString(Constant.AddProperty.FLOOR)));
	    		}
	    		
	    		/*
	    		 * Furnish option
	    		 */
	    		if(jsonObject.getString("propertytype").equals("Plot")) {
	    			ll_propDet_furnishOption.setVisibility(View.GONE);
	    		} else {
	    			ll_propDet_furnishOption.setVisibility(View.VISIBLE);
	    			tv_propDet_furnish.setText(SpinnerItem.getFurnishOptionListKey(jsonObject.getInt(Constant.AddProperty.FURNISH_STATUS)));
	    		}
	    		
	    		/*
	    		 * Building type
	    		 */
	    		if(jsonObject.getString("propertytype").equals("Flat") ||( jsonObject.getString("propertytype").equals("Shop") && jsonObject.getString("propertytype").equals("Factory") )|| (jsonObject.getString("propertytype").equals("Shop") && jsonObject.getString("propertytype").equals("Warehouse"))) {
	    			ll_propDet_buildingType.setVisibility(View.VISIBLE);
	    			tv_propDet_buildingType.setText(SpinnerItem.getBuildingTypeListKey(jsonObject.getString(Constant.AddProperty.RISE)));
	    		} else {
	    			ll_propDet_buildingType.setVisibility(View.GONE);
	    		}
	    		
	    		
	    		/*
	    		 * Purpose Residential/Commertial
	    		 */
	    		tv_propDet_purpose.setText(jsonObject.getString(Constant.AddProperty.PURPOSE));
	    		
	    		/*
	    		 * Built year
	    		 */
	    		 if(jsonObject.getString("propertytype").equals("Plot")) {
	    			 ll_propDet_buildYear.setVisibility(View.GONE);
	    		 } else {
	    			 ll_propDet_buildYear.setVisibility(View.VISIBLE);
	    			 if(jsonObject.getString(Constant.AddProperty.YEAR_BUILD_UP).length() > 0) {
	    				 tv_propDet_builtyear.setText(jsonObject.getString(Constant.AddProperty.YEAR_BUILD_UP));
	    			 } else {
	    				 tv_propDet_builtyear.setText("Detail on call");
	    			 }
	    		 }
	    		 
	    		 
	    		
	    		/*
	    		 * Price second
	    		 * Maintanance , transfer fee, parking charge etc
	    		 */
	    		if(jsonObject.getString(Constant.AddProperty.STR_OPTIONS).equals("Sale") && !jsonObject.getString("propertytype").equals("Plot")) {
	    			
	    			ll_propDet_saleRent.setVisibility(View.VISIBLE);
	    			btn_propDet_forSaleRent.setText("For "+jsonObject.getString(Constant.AddProperty.STR_OPTIONS));
	    			if(!jsonObject.getString(Constant.AddProperty.PRICE).equals("0.00"))
	    				tv_propDet_price2.setText(toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.PRICE))));
	    			else 
	    				tv_propDet_price2.setText("Details on call");
	    			if(!jsonObject.getString(Constant.AddProperty.MAINTENANCE).equals("0.00"))
	    				tv_propDet_maintenance.setText(toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.MAINTENANCE))));
	    			else 
	    				tv_propDet_maintenance.setText("Details on call");
	    			if(!jsonObject.getString(Constant.AddProperty.TRANSFER_FEES).equals("0.00"))
	    				tv_propDet_transferFee.setText(toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.TRANSFER_FEES))));
	    			else
	    				tv_propDet_transferFee.setText("Details on call");
	    			if(!jsonObject.getString(Constant.AddProperty.AEC_AUDA).equals("0.00"))
	    				tv_propDet_aecAuda.setText(toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.AEC_AUDA))));
	    			else 
	    				tv_propDet_aecAuda.setText("Details on call");
	    			if(!jsonObject.getString(Constant.AddProperty.PARKING).equals("0.00"))
	    				tv_propDet_parkingCharge.setText(toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.PARKING))));
	    			else
	    				tv_propDet_parkingCharge.setText("Details on call");
	    			if(!jsonObject.getString(Constant.AddProperty.DASTAWAGE).equals("0.00"))
	    				tv_propDet_dastavej.setText(toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.DASTAWAGE))));
	    			else 
	    				tv_propDet_dastavej.setText("Details on call");
	    		} else {
	    			ll_propDet_saleRent.setVisibility(View.GONE);
	    			if(!jsonObject.getString("propertytype").equals("Plot"))
	    				rl_propDet_forSalRent.setVisibility(View.VISIBLE);
	    			btn_propDet_forSaleRent_flat.setText("For "+jsonObject.getString(Constant.AddProperty.STR_OPTIONS));
	    			tv_propDet_price2__flat.setText(toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.PRICE))));
	    		}
	    		
	    		
	    		/*
	    		 * Plot type only
	    		 */
	    		if(jsonObject.getString("propertytype").equals("Plot")) {
	    			ll_propDet_plot.setVisibility(View.VISIBLE);
	    			btn_propDet_forSaleRentPlot.setText("For "+jsonObject.getString(Constant.AddProperty.STR_OPTIONS));
	    			tv_propDet_price2Plot.setText(toIndianRupessFormat(Double.parseDouble(jsonObject.getString(Constant.AddProperty.PRICE))));
	    			tv_propDet_tpScheme.setText(jsonObject.getString("tpname"));
	    			if(jsonObject.getString(Constant.AddProperty.ZONE).length() > 0)
	    				tv_propDet_zone.setText(SpinnerItem.getAddPropZoneListKey(jsonObject.getString(Constant.AddProperty.ZONE)));
	    			else 
	    				tv_propDet_zone.setText("Detail on call");
	    			
	    			if(!jsonObject.getString("moredetails").equals("no more information")) {
		    			System.out.println("moredetail-->"+jsonObject.getJSONObject("moredetails").getString("nastatus"));
		    			tv_propDet_na.setText(SpinnerItem.getAddPropNaStatusListKey(jsonObject.getJSONObject("moredetails").getString("nastatus")));
		    			System.out.println("On Road touch---> "+jsonObject.getJSONObject("moredetails").getString("onroad"));
		    			if(!jsonObject.getJSONObject("moredetails").equals("0") && jsonObject.getJSONObject("moredetails").getString("onroad").length() > 0) {
		    				tv_propDet_roadTouch.setText(SpinnerItem.getOnRoadListKey(jsonObject.getJSONObject("moredetails").getString("onroad")));
		    			} else {
		    				tv_propDet_roadTouch.setText("Details on call");
		    			}
	    			} else {
	    				tv_propDet_na.setText("Details on call");
	    				tv_propDet_roadTouch.setText("Details on call");
	    			}
	    			
	    		} else {
	    			ll_propDet_plot.setVisibility(View.GONE);
	    		}
	    		
	    		/*
	    		 * Extra comment
	    		 */
	    		tv_propDet_extraComment.setText(jsonObject.getString(Constant.AddProperty.COMMENTS));
	    		
	    		
	    		/*
	    		 * Aemenities
	    		 */
	    		  JSONArray facilityArray = jsonObject.getJSONArray(Constant.AddProperty.FACILITIES);
					if (facilityArray.length() > 0) {
						for (int i = 0; i < facilityArray.length(); i++) {
							ZPropDetFacilityView detFacilityView = new ZPropDetFacilityView(getActivity(), facilityArray.getString(i), "");
				        	ll_propDet_facility.addView(detFacilityView);
						}
					}
					
				/*
				 * Posted details
				 */
	    		tv_propDet_brokerName.setText(jsonObject.getString(Constant.AddProperty.FIRST_NAME)+" "+jsonObject.getString(Constant.AddProperty.LAST_NAME));
	    		tv_propDet_company.setText(jsonObject.getString("companyname"));
	    		tv_propDet_brk_officeAddress.setText(jsonObject.getString("brokerad"));
	    		tv_propDet_mobileNumber.setText(jsonObject.getString("phonem"));
	    		tv_propDet_totalProp.setText("Total Property: "+jsonObject.getString("totalrproperty"));
	    		tv_propDet_totalReq.setText("Total Requirement: "+jsonObject.getString("totalrequirement"));
	    		
	    		/*
				  * Initialize nominee list dialog 
				  */
				zNomineeCallListDialog = new ZNomineeCallListDialog(getActivity(), Constant.PropertyDetail.FROM_PROPERTY_DETAIL,tv_propDet_brokerName.getText().toString(),tv_propDet_mobileNumber.getText().toString(),jsonObject.getString("brokerid"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public class AddImgAdp extends BaseAdapter {
        
		int GalItemBg;
        private Context cont;
        private ArrayList<String> strings;
        
        public AddImgAdp(Context c,ArrayList<String> strings) {
            cont = c;
            this.strings=strings;
        }

        public int getCount() {
            return strings.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imgView = new ImageView(cont);

            imgView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT,Gallery.LayoutParams.FILL_PARENT));
            imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageLoader.DisplayImage(strings.get(position), imgView);
            return imgView;
        }
    }
	//This method is used to convert the price into indian rupess  format
	private String toIndianRupessFormat(double doubleValue) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
		if(nf.format(doubleValue).length() > 0 && nf.format(doubleValue).contains(".")) {
			return nf.format(doubleValue).substring(0,nf.format(doubleValue).length() - 3);
		} else {
			return "0";
		}
	}
	
	private void mapBitmap(final String latitude,final String longitude) {
		new AsyncTask<String, Void, Bitmap>() {
			private volatile boolean running = true;
			
			@Override
			protected Bitmap doInBackground(String... params) {
				// url;
				try {
				 while (running) {
				 	URL url = new URL("http://maps.google.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&zoom="+15+"&size=" + ll_propDet_map.getWidth() + "x" + ll_propDet_map.getHeight() + "&scale=2,&markers=color:yellowlabel:2|"+latitude+","+longitude);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				    connection.setDoInput(true);
				    connection.connect();
				    InputStream input = connection.getInputStream();
				    return BitmapFactory.decodeStream(input);
			      }
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
			
			protected void onPostExecute(Bitmap result) {
				progres_propDet_map.setVisibility(View.GONE);
				ll_propDet_map.setBackground(new BitmapDrawable(result));
			};
			
		 }.execute();
		 
		 ll_propDet_map.setOnClickListener(new OnClickListener() {
 			@Override
 			public void onClick(View v) {
 				System.out.println("latitude:"+latitude+"longitude:"+longitude);
 				Intent intent = new Intent(getActivity(), ZNearByLocation.class);
				intent.putExtra("latitude", Double.parseDouble(latitude));
				intent.putExtra("longitude", Double.parseDouble(longitude));
				startActivity(intent);
 			}
 		});
	}
	
	//Remove google map fragment when click on done or cancle button
	private void removeGoogleMapFragment() {
		SupportMapFragment f = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.googleMap);
		 if (f != null) 
			 getFragmentManager().beginTransaction().remove(f).commit();
	}
		
	 private boolean isGooglePlayServicesAvailable() {
	        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
	        if (ConnectionResult.SUCCESS == status) {
	            return true;
	        } else {
	            GooglePlayServicesUtil.getErrorDialog(status, getActivity(), 0).show();
	            return false;
	        }
	 }


    @Override
    public void onLocationChanged(Location location) {
        //latitude = location.getLatitude();
        //longitude = location.getLongitude();
    	/*System.out.println("latitude--> "+latitude);
    	System.out.println("longitude--> "+longitude);
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));*/
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_propDet_nearByLocation:
				Intent intent = new Intent(getActivity(), ZNearByLocation.class);
				intent.putExtra("latitude", latitude);
				intent.putExtra("longitude", longitude);
				startActivity(intent);
				break;
			case R.id.btn_propDet_call:
				zNomineeCallListDialog.show();
				break;
				
			default:
				break;
			}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		MainFragmentActivity.setTitle("Property Detail");
	};
 }

