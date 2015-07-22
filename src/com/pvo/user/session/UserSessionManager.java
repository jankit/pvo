package com.pvo.user.session;

//http://androidexample.com/Android_Session_Management_Using_SharedPreferences_-_Android_Example/index.php?view=article_discription&aid=127&aaid=147

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.pvo.activity.DashboardNewActivity;
import com.pvo.activity.LoginActivity;
import com.pvo.util.Constant;

public class UserSessionManager {
	
	private SharedPreferences pref;
	private Editor editor;
	private Context context;
	//private GsmIdDeleteService deleteGsmIdService;
	
	int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREFER_NAME = "PropertyViaSharedPreferences";
    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    @SuppressLint("CommitPrefEdits")
	public UserSessionManager(Context context){
    	this.context = context;
    	this.pref = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
    	this.editor = pref.edit();
    }
    
    public void userLoginSession(String... params){
    	editor.putBoolean(IS_USER_LOGIN, true);
    	editor.putString(Constant.Login.USER_ID, params[0]);
    	editor.putString(Constant.Login.LOGIN_FIREST, params[1]);
    	editor.putString(Constant.Login.FIRST_NAME, params[2]);
    	editor.putString(Constant.Login.LAST_NAME, params[3]);
    	editor.putString(Constant.Login.COMPANY_NAME, params[4]);
    	editor.putString(Constant.Login.PHONE_NUMBER, params[5]);
    	editor.putString(Constant.Login.EMAIL, params[6]);
    	editor.putString(Constant.Login.STATE_ID, params[7]);
    	editor.putString(Constant.Login.CITY_ID, params[8]);
    	editor.putString(Constant.Login.DISTRICT_ID, params[9]);
    	editor.putString(Constant.Login.AREA_DEALS_IN_TEXT, params[10]);
    	editor.putString(Constant.Login.AREA_DEALS_IN, params[11]);
    	editor.putString(Constant.Login.COUNTRY_NAME, params[12]);
    	editor.putString(Constant.Login.STATE_NAME, params[13]);
    	editor.putString(Constant.Login.CITY_NAME, params[14]);
    	editor.putString(Constant.Login.DISTRICT_NAME, params[15]);
    	editor.commit();
    }

    public void setAddPropCurrentTab(String currentTab) {
    	editor.putString(Constant.ZAddProperty.CURRENT_TAB, currentTab);
    	editor.commit();
    }
    
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }
        return false;
    }
    
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails() {
         
        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();
         
        user.put(Constant.Login.USER_ID, pref.getString(Constant.Login.USER_ID, null));
        user.put(Constant.Login.LOGIN_FIREST, pref.getString(Constant.Login.LOGIN_FIREST, null));
        user.put(Constant.Login.FIRST_NAME, pref.getString(Constant.Login.FIRST_NAME, null));
        user.put(Constant.Login.LAST_NAME, pref.getString(Constant.Login.LAST_NAME, null));
        user.put(Constant.Login.COMPANY_NAME, pref.getString(Constant.Login.COMPANY_NAME, null));
        user.put(Constant.Login.PHONE_NUMBER, pref.getString(Constant.Login.PHONE_NUMBER, null));
        user.put(Constant.Login.EMAIL, pref.getString(Constant.Login.EMAIL, null));
        user.put(Constant.Login.STATE_ID, pref.getString(Constant.Login.STATE_ID, null));
        user.put(Constant.Login.CITY_ID, pref.getString(Constant.Login.CITY_ID, null));
        user.put(Constant.Login.DISTRICT_ID, pref.getString(Constant.Login.DISTRICT_ID, null));
        user.put(Constant.Login.AREA_DEALS_IN_TEXT, pref.getString(Constant.Login.AREA_DEALS_IN_TEXT, null));
        user.put(Constant.Login.AREA_DEALS_IN, pref.getString(Constant.Login.AREA_DEALS_IN, null));
        user.put(Constant.Login.COUNTRY_NAME, pref.getString(Constant.Login.COUNTRY_NAME, null));
        user.put(Constant.Login.STATE_NAME, pref.getString(Constant.Login.STATE_NAME, null));
        user.put(Constant.Login.CITY_NAME, pref.getString(Constant.Login.CITY_NAME, null));
        user.put(Constant.Login.DISTRICT_NAME, pref.getString(Constant.Login.DISTRICT_NAME, null));
        
        return user;
    }
    
    public String getSessionValue(String key) {
    	return pref.getString(key, null);
    }
    
    public void logoutUser(){
    	System.out.println("===> Logout user <===");
    	editor.clear();
        editor.commit();
        // After logout redirect user to Login Activity
    	Intent i = new Intent(context, DashboardNewActivity.class);
    	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
    
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}
