package com.pvo.activity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.pvo.custom.adapter.AdsListingArrayAdaptor;
import com.pvo.prototype.PVOFragment;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.AdsDetailService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.squareup.picasso.Picasso;


// This is used for display the full information of the my property on click of first text view of my property List
public class AdsDetailActivity extends PVOFragment {

	private TextView adsDetNumberOfImage;
	private TextView adsDetProjectidTv;
	private TextView adsDetProjectidVal;
	private TextView adsDetBuilderNameTv;
	private TextView adsDetBuilderNameVal;
	private TextView adsDetNumberTv; 
	private TextView adsDetNumberVal;
	private TextView adsDetPriceTv;
	private TextView adsDetPriceVal;
	private TextView adsDetLatitudeTv;
	private TextView adsDetLatitudeVal;
	private TextView adsDetLongitudeTv;
	private TextView adsDetLongitudeVal;
	private TextView adsDetDetailVal;
	private Button adsDetBackBtn;
	private Button adsDetDownloadPriceBtn;
	private Button adsDetDownloadFloorPlanBtn;
	//Get Stored User Id From User SessionManager 
	private UserSessionManager userSessionManager;
	private AdsDetailService adsDetailService;
	private Bundle adsIntent;
	private GoogleMap googleMap;
	private Gallery adsDetGallery;
	private ImageView adsDetailMap;
	int width;
	int height;
	
	//Set layout content view
	public AdsDetailActivity() {
		setContentView(R.layout.activity_ads_detail);
	}
	

	@Override
	public void init(Bundle savedInstanceState) {
		//This Line is used to hide the keyboard
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		//set google map height and weight
		WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		
		//get Login User_Id from stored Session
		userSessionManager 		= new UserSessionManager(getActivity().getApplicationContext());
		adsDetailService 		= new AdsDetailService();
		adsIntent 				= getArguments();
		adsDetNumberOfImage 	= (TextView) findViewById(R.id.adsDetNumberOfImage);
		adsDetProjectidTv 		= (TextView) findViewById(R.id.adsDetProjectidTv);
		adsDetProjectidVal 		= (TextView) findViewById(R.id.adsDetProjectidVal); 
		adsDetBuilderNameTv 	= (TextView) findViewById(R.id.adsDetBuilderNameTv);
		adsDetBuilderNameVal 	= (TextView) findViewById(R.id.adsDetBuilderNameVal);
		adsDetNumberTv 			= (TextView) findViewById(R.id.adsDetNumberTv); 
		adsDetNumberVal 		= (TextView) findViewById(R.id.adsDetNumberVal);
		adsDetPriceTv			= (TextView) findViewById(R.id.adsDetPriceTv);
		adsDetPriceVal 			= (TextView) findViewById(R.id.adsDetPriceVal);
		adsDetLatitudeTv 		= (TextView) findViewById(R.id.adsDetLatitudeTv);
		adsDetLatitudeVal 		= (TextView) findViewById(R.id.adsDetLatitudeVal);
		adsDetLongitudeTv 		= (TextView) findViewById(R.id.adsDetLongitudeTv);
		adsDetLongitudeVal 		= (TextView) findViewById(R.id.adsDetLongitudeVal);
		adsDetDetailVal 		= (TextView) findViewById(R.id.adsDetDetailVal);
		adsDetGallery 			= (Gallery) findViewById(R.id.adsDetGallery);
		adsDetailMap 			= (ImageView) findViewById(R.id.adsDetailMap);
		adsDetDownloadPriceBtn	= (Button) findViewById(R.id.adsDetDownloadPriceBtn);
		adsDetDownloadFloorPlanBtn	= (Button) findViewById(R.id.adsDetDownloadFloorPlanBtn);
		
		//on click download the price list pdf
		adsDetDownloadPriceBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(URLUtil.isValidUrl(adsIntent.getString(Constant.AdsListing.PRICE_PDF))) {
					try {
						URL url = new URL(adsIntent.getString(Constant.AdsListing.PRICE_PDF));
						HttpURLConnection connection = (HttpURLConnection)url.openConnection();
						connection.setRequestMethod("GET");
						connection.connect();
						int code = connection.getResponseCode();
						if(code == 200) {
							Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(adsIntent.getString(Constant.AdsListing.PRICE_PDF)));
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);
						} else {
							Toast.makeText(getActivity().getApplicationContext(), "No brochure available", Toast.LENGTH_LONG).show();
						} 
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (ProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(v.getContext(), "No brochure available", Toast.LENGTH_LONG).show();
				}
			}
		});

		//on click of download the floor plan pdf
		adsDetDownloadFloorPlanBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(URLUtil.isValidUrl(adsIntent.getString(Constant.AdsListing.FLOOR_PLAN))) {
					try {
						URL url = new URL(adsIntent.getString(Constant.AdsListing.FLOOR_PLAN));
						HttpURLConnection connection = (HttpURLConnection)url.openConnection();
						connection.setRequestMethod("GET");
						connection.connect();
						int code = connection.getResponseCode();
						if(code == 200) {
							Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(adsIntent.getString(Constant.AdsListing.FLOOR_PLAN)));
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);
						} else {
							Toast.makeText(getActivity().getApplicationContext(), "No brochure available", Toast.LENGTH_LONG).show();
						} 
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (ProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(v.getContext(), "No brochure available", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		//onBack Button press Move to 
		adsDetBackBtn = (Button)findViewById(R.id.adsDetBackBtn);
		adsDetBackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainFragmentActivity)getActivity()).onBackPressed();
			}		
		});
		//Ads detail web service 
		new WebserviceClient(AdsDetailActivity.this, adsDetailService).execute(adsIntent.getString(Constant.AdsDetail.PROJECT_ID),userSessionManager.getSessionValue(Constant.Login.USER_ID));
		
		/*// Google Map
		try {
			setUpMapIfNeeded();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	@Override
	public void processResponse(Object response) {
		JSONArray jsonArry = (JSONArray)response;
		try {
			if (response != null && !((JSONObject)jsonArry.get(0)).has(Constant.MyProperty.API_STATUS)) {
				JSONObject object=(JSONObject) jsonArry.getJSONObject(0);
        		JSONArray imaJsonArray = object.getJSONArray(Constant.AdsDetail.IMAGES);
        		
        		//get Image object from response 
        		final ArrayList<String> imageList = new ArrayList<String>();
        		for(int i=0 ; i<imaJsonArray.length(); i++) {
        			imageList.add(imaJsonArray.getString(i));
        		}
        		
        		//set decode image string and set into image view
        		adsDetGallery.setAdapter(new ImageAdapter(getActivity(),imageList));
        		adsDetGallery.setOnItemSelectedListener(new OnItemSelectedListener() {
	        		@Override
	        		public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long arg3) {
	        			adsDetNumberOfImage.setText(position+1+"/"+imageList.size());
	        		}
	        		@Override
	        		public void onNothingSelected(AdapterView<?> arg0) {}	
        		});
        		
        		//Display Project Id
        		adsDetProjectidVal.setText(object.getString(Constant.AdsListing.PROJECT_ID));
        		adsDetProjectidTv.setMinLines(adsDetProjectidVal.getLineCount());
        		adsDetProjectidVal.setMinLines(adsDetProjectidVal.getLineCount());

        		//Display Builder Name
        		adsDetBuilderNameVal.setText(object.getString(Constant.AdsListing.BUILDER_NAME));
        		adsDetBuilderNameTv.setMinLines(adsDetBuilderNameVal.getLineCount());
        		adsDetBuilderNameVal.setMinLines(adsDetBuilderNameVal.getLineCount());
        		
        		//Display Address
        		if(!object.getString(Constant.AdsListing.CONTACT_NO1).equals(null) && object.getString(Constant.AdsListing.CONTACT_NO2).equals(null) && object.getString(Constant.AdsListing.CONTACT_NO3).equals(null))  
        			adsDetNumberVal.setText(object.getString(Constant.AdsListing.CONTACT_NO1));
        		else if(!object.getString(Constant.AdsListing.CONTACT_NO1).equals(null) && !object.getString(Constant.AdsListing.CONTACT_NO2).equals(null) &&object.getString(Constant.AdsListing.CONTACT_NO3).equals(null)) 
        			adsDetNumberVal.setText(object.getString(Constant.AdsListing.CONTACT_NO1)+ "\n"+object.getString(Constant.AdsListing.CONTACT_NO2));
        		else if(!object.getString(Constant.AdsListing.CONTACT_NO1).equals(null) && !object.getString(Constant.AdsListing.CONTACT_NO2).equals(null) && !object.getString(Constant.AdsListing.CONTACT_NO3).equals(null)) 
        			adsDetNumberVal.setText(object.getString(Constant.AdsListing.CONTACT_NO1)+ "\n"+object.getString(Constant.AdsListing.CONTACT_NO2)+"\n"+object.getString(Constant.AdsListing.CONTACT_NO3));
        
        		adsDetNumberTv.setMinLines(adsDetNumberVal.getLineCount());
        		adsDetNumberVal.setMinLines(adsDetNumberVal.getLineCount());
        		
        		//Display Price
        		adsDetPriceVal.setText(toIndianRupessFormat(Double.parseDouble(object.getString(Constant.AdsListing.START_PRICE)))+ " TO "+toIndianRupessFormat(Double.parseDouble(object.getString(Constant.AdsListing.END_PRICE))));
        		adsDetPriceTv.setMinLines(adsDetPriceVal.getLineCount());
        		adsDetPriceVal.setMinLines(adsDetPriceVal.getLineCount());
        		
        		//Display Latitude
        		adsDetLatitudeVal.setText(object.getString(Constant.AdsListing.LAT));
        		adsDetLatitudeTv.setMinLines(adsDetLatitudeVal.getLineCount());
        		adsDetLatitudeVal.setMinLines(adsDetLatitudeVal.getLineCount());
        			
        		//Display Longitude
        		adsDetLongitudeVal.setText(object.getString(Constant.AdsListing.LONGI));
        		adsDetLongitudeTv.setMinLines(adsDetLongitudeVal.getLineCount());
        		adsDetLongitudeVal.setMinLines(adsDetLongitudeVal.getLineCount());
        		
        		//Display Deatil
        		adsDetDetailVal.setText(object.getString(Constant.AdsListing.DETAIL));
        		//adsDetDetailTv.setMinLines(adsDetDetailVal.getLineCount());
        		adsDetDetailVal.setMinLines(adsDetDetailVal.getLineCount());
        		
        		Picasso.with(getActivity()).load("https://maps.googleapis.com/maps/api/staticmap?zoom=9&size="+width/2+"x300&maptype=roadmap&markers=color:blue|label:|"+ object.getString(Constant.AdsListing.LAT) + "," + object.getString(Constant.AdsListing.LONGI) +"&scale=2").into(adsDetailMap);
			} else {
				Toast.makeText(getActivity(),((JSONObject)jsonArry.get(0)).getString(Constant.MyProperty.API_MESSAGE), Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//This is used for Google Map
	/*private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (googleMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			googleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.adsDetMap)).getMap();
			// Check if we were successful in obtaining the map.
			if (googleMap != null) {
				googleMap.setMyLocationEnabled(true);
				googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
					@Override
					public void onMyLocationChange(Location arg0) {
						googleMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(),arg0.getLongitude())).title("It's Me!"));
						CameraUpdate center=CameraUpdateFactory.newLatLng(new LatLng(arg0.getLatitude(),arg0.getLongitude()));
						CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
						googleMap.moveCamera(center);
						googleMap.animateCamera(zoom);
					}
				});
			}
		}
	}*/
	
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    if(adsIntent.getString(AdsListingArrayAdaptor.PROJECT_DETAIL).equals("Project"))
	    	this.getActivity().getActionBar().setTitle("Project Detail");
	}
	
	//Image Adapter class to set the image in gallery view
	 public class ImageAdapter extends BaseAdapter {
		 private Context context;
		 private ArrayList<String> imageUrls;
		 public ImageAdapter(Context c,ArrayList<String> imageUrls) {
            context = c;
            this.imageUrls = imageUrls;
            TypedArray a = getActivity().obtainStyledAttributes(R.styleable.Gallery1);
            a.recycle();
         }

        //returns the number of images
        public int getCount() { return imageUrls.size(); }
 
        // ---returns the ID of an item---
        public Object getItem(int position) { return position; }

        public long getItemId(int position) { return position; }

        //returns an ImageView view
		public View getView(int position, View convertView, ViewGroup parent) {
           try {
        	   ImageView imageView = new ImageView(context);
        	   imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        	   imageView.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
               Picasso.with(context).load(imageUrls.get(position)).placeholder(R.drawable.no_image).into(imageView);
               return imageView;
            } catch(Exception e) {
            	e.printStackTrace();
            }
            return null;
        }
    }
	
 	//This method is used to convert the price into indian rupess  format
	private String toIndianRupessFormat(double doubleValue) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
		return nf.format(doubleValue);
	}
		
	//clear google map fragment
	@Override
	public void onDestroy() {
		 super.onDestroy();
		/* SupportMapFragment f = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.adsDetMap);
		 if (f != null) 
			 getFragmentManager().beginTransaction().remove(f).commit();*/
	}
}
	

