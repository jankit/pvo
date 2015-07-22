package com.pvo.activity;

import java.io.IOException;
import java.util.Calendar;

import z.com.pvo.util.ProjectUtility;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.pvo.gcm.notification.Config;
import com.pvo.json.cache.UpdateJsonReceiver;
import com.pvo.prototype.PVOActivity;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

public class SplashScreenActivity extends PVOActivity {
	
	private UserSessionManager userSessionManager;
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	public static final String PVOREGID = "PVOREGID";
	private GoogleCloudMessaging gcm;
	private String gcmId;
	private static final String APP_VERSION = "appVersion";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		//userSessionManager = ProjectUtility.setUsersession(getApplicationContext());
		userSessionManager 	= new UserSessionManager(getApplicationContext());
		//This code is used for check Internet Connection 
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		//get Registered GCM id
		if (TextUtils.isEmpty(gcmId)) {
			gcmId = registerGCM();
			//Log.d("Splash Activity", "GCM RegId: " + gcmId);
		} 
				
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if(haveNetworkConnection()){
					//Call broad cast receiver to create json file into cache memory
					startReceiverToStoreJsonFileIntoCache();
					
					if(userSessionManager.isUserLoggedIn()) {
						Intent intent = new Intent(SplashScreenActivity.this, MainFragmentActivity.class);
						intent.putExtra(Constant.Login.LOGIN_FIREST, "False");
						startActivity(intent);
					} else {
						startActivity(new Intent(getApplicationContext(), DashboardNewActivity.class));
					}
				} else {
					Toast.makeText(SplashScreenActivity.this, "You don't have internet connection.", Toast.LENGTH_LONG).show();	
						finish();
				}
			}
		}, 5000); // wait for 1 seconds
		
		
	}
	
	//get register GCM id
	public String registerGCM() {
		//System.out.println("**** Register GCM id **********");
		gcm = GoogleCloudMessaging.getInstance(this);
		gcmId = getRegistrationId(SplashScreenActivity.this);
		if (TextUtils.isEmpty(gcmId)) {
			registerInBackground();
			Log.d("RegisterActivity","registerGCM - successfully registered with GCM server - regId: "+ gcmId);
		} 
		return gcmId;
	}
	
	
	
	//register GCM id to GCM server
	private void registerInBackground() {
		System.out.println("**** Register in background **********");
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(SplashScreenActivity.this);
					}
					//pass the GOOGLE_PROJECT_ID (Project Number)
					gcmId = gcm.register(Config.GOOGLE_PROJECT_ID);
					Log.d("RegisterActivity", "registerInBackground - regId: "+ gcmId);
					msg = "Device registered, registration ID=" + gcmId;
					storeRegistrationId(getApplicationContext(), gcmId);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					Log.d("RegisterActivity", "Error: " + msg);
				}
				Log.d("RegisterActivity", "AsyncTask completed: " + msg);
				return msg;
			}
			@Override
			protected void onPostExecute(String msg) {}
		}.execute();
	}
	
	
	//get  the application version
	private static int getAppVersion(Context context) {
		//System.out.println("**** getAppVersion **********");
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			Log.d("RegisterActivity","I never expected this! Going down, going down!" + e);
			throw new RuntimeException(e);
		}
	}
	
	//store registration id to SharedPreferences
	private void storeRegistrationId(Context context, String regId) {
		//System.out.println("******** Store Registration id *****");
		
		final SharedPreferences prefs = getSharedPreferences(PVOREGID, Context.MODE_PRIVATE);
		int appVersion = getAppVersion(context);
		Log.i("PropertyViaOnline", "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(APP_VERSION, appVersion);
		editor.commit();
	}
	
	//get registration id from from sharedPreference
	private String getRegistrationId(Context context) {
		//System.out.println("******** getRegistrationId *****");
		final SharedPreferences prefs = getSharedPreferences(PVOREGID, Context.MODE_PRIVATE);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		//System.out.println("registrationId "+registrationId);
		if (registrationId.isEmpty()) {
			//Log.i(PVOREGID, "Registration not found.");
			return "";
		}
		int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(PVOREGID, "App version changed.");
			return "";
		}
		//System.out.println("get registration id registrationId: "+registrationId);
		return registrationId;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void processResponse(Object response) {}

	//You need to do the Play Services APK check here too
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	//Check for Internet connection
	private boolean haveNetworkConnection() {
		boolean haveConnectedWifi 	= false;
		boolean haveConnectedMobile = false;
		ConnectivityManager cm 	= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo 	= cm.getAllNetworkInfo();
		
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}
	
	//This method is used to create json file into cache memory when application start
	private void startReceiverToStoreJsonFileIntoCache() {
		//set alarm hour,minute,second,AM_PM as 00:00:00 AM to call broadcast Receiver
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 00); 
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.AM_PM, Calendar.AM); 
        
        //call the Broadcast receiver UpdateJsonReceiver.java
        Intent i = new Intent(this, UpdateJsonReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000*60, pi);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pi);
	}
	
	//This method is temporary Remove 
	private void tempStoreUserData() {
		userSessionManager.userLoginSession(
				String.valueOf("2"),
				String.valueOf("false"),
				String.valueOf("Hiren"),
				String.valueOf("Patel"),
				String.valueOf("websoptimization"),
				String.valueOf("9428088175"),
				String.valueOf("hirenk@websoptimization.com"),
				String.valueOf("0"),
				String.valueOf("0"),
				String.valueOf("0"),
				String.valueOf("Jodhpur,Ambawadi,Ambali"),
				String.valueOf("1,158,229"),
				String.valueOf("India"),
				String.valueOf("Gujarat"),
				String.valueOf("Ahmedabad"),
				String.valueOf("Ahmedabad"));
	}
}   
    
    
    
