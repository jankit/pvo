package z.com.pvo.newActivity;

import z.com.pvo.components.GooglePlacesReadTask;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pvo.activity.R;

public class ZNearByLocation extends FragmentActivity implements LocationListener, OnClickListener {
    
    private static final String GOOGLE_API_KEY = "AIzaSyD4ciJ2zFXfp5XPhmMVusaz4DMVxLKCqWs";
    private GoogleMap googleMap;
    private double latitude = 0, longitude = 0;
    private int PROXIMITY_RADIUS = 5000;
    
    private String[] mPlaceType=null;
	private String[] mPlaceTypeName=null;
    private TextView tv_actionbar_title;
    private Intent intent;
    private ImageView iv_actionBar_menu;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.near_by_location);
		intent = getIntent();
	
	    getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setCustomView(R.layout.actionbar_custom);
        getActionBar().setDisplayShowCustomEnabled(true);
		
		 tv_actionbar_title = (TextView) getActionBar().getCustomView().findViewById(R.id.tv_actionbar_title);
		 tv_actionbar_title.setText("NEAR BY LOCATION");
	     iv_actionBar_menu = (ImageView) getActionBar().getCustomView().findViewById(R.id.iv_actionBar_menu);
	     iv_actionBar_menu.setImageResource(R.drawable.pre);
	     iv_actionBar_menu.setOnClickListener(this);
	     
	    // Array of place types
		mPlaceType = getResources().getStringArray(R.array.place_type);
		mPlaceTypeName = getResources().getStringArray(R.array.place_type_name);

		googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap)).getMap();
        googleMap.setMyLocationEnabled(false);
        
        System.out.println("latitude--> "+intent.getDoubleExtra("latitude", 0));
        System.out.println("longitude--> "+intent.getDoubleExtra("longitude", 0));
        
        LatLng latLng = new LatLng(intent.getDoubleExtra("latitude", 0), intent.getDoubleExtra("longitude", 0));
        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(19));
        
		// Creating an array adapter with an array of Place types
		final Spinner  spr_place_type = (Spinner) findViewById(R.id.spr_place_type);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(ZNearByLocation.this, android.R.layout.simple_spinner_dropdown_item, mPlaceTypeName);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spr_place_type.setAdapter(adapter);
		spr_place_type.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if(position != 0) {
					int selectedPosition = spr_place_type.getSelectedItemPosition();
					String type = mPlaceType[selectedPosition];
	                StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
	                googlePlacesUrl.append("location=" + intent.getDoubleExtra("latitude", 0) + "," + intent.getDoubleExtra("longitude", 0));
	                googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
	                googlePlacesUrl.append("&types=" + type);
	                googlePlacesUrl.append("&sensor=true");
	                googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);
	                
	                System.out.println("Near By location URL--> "+googlePlacesUrl);
	                GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
	                Object[] toPass = new Object[2];
	                toPass[0] = googleMap;
	                toPass[1] = googlePlacesUrl.toString();
	                googlePlacesReadTask.execute(toPass);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
        

        Button btnFind = (Button) findViewById(R.id.btn_find);
        btnFind.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	int selectedPosition = spr_place_type.getSelectedItemPosition();
				String type = mPlaceType[selectedPosition];
                StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                googlePlacesUrl.append("location=" + latitude + "," + longitude);
                googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
                googlePlacesUrl.append("&types=" + type);
                googlePlacesUrl.append("&sensor=true");
                googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);
                
                GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
                Object[] toPass = new Object[2];
                toPass[0] = googleMap;
                toPass[1] = googlePlacesUrl.toString();
                googlePlacesReadTask.execute(toPass);
            }
        });
        
        iv_actionBar_menu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				removeGoogleMapFragment();
				finish();
			}
		});

	}

	
	//Remove google map fragment when click on done or cancle button
	private void removeGoogleMapFragment() {
		SupportMapFragment f = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
		 if (f != null) 
			 getSupportFragmentManager().beginTransaction().remove(f).commit();
	}
		
	 private boolean isGooglePlayServicesAvailable() {
	        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
	        if (ConnectionResult.SUCCESS == status) {
	            return true;
	        } else {
	            GooglePlayServicesUtil.getErrorDialog(status, ZNearByLocation.this, 0).show();
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
				break;

			default:
				break;
			}
	}
 }

