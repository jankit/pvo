/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pvo.activity;

import java.util.List;
import java.util.Stack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import z.com.pvo.components.BadgeView;
import z.com.pvo.newActivity.ZMyPropertyListActivity;
import z.com.pvo.newActivity.ZNotificationMainFragment;
import z.com.pvo.newActivity.ZPropertyDetail;
import z.com.pvo.newActivity.ZShortListMainFragment;
import android.R.bool;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.pvo.custom.adapter.NavListAdapter;
import com.pvo.prototype.PVOFragment;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.GsmIdDeleteService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;

public class MainFragmentActivity extends FragmentActivity {
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private Intent intent;
	private ImageView imgView;
	//set Nominee flag 
	public static String nomineeFlag = "";
	public static JSONArray nomineeResponse;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private PVOFragment pvoFragment;
	private int selectedPosition = 0;
	private Stack<Fragment> fragmentStack;
	private FragmentManager fragmentManager;
	private GsmIdDeleteService deleteGsmIdService;
	
	//Set title and Icon in menu
	private String[] title;
	private int[] icon;
	private NavListAdapter adapter;
	
	private UserSessionManager userSessionManager;
	
	
	private ImageView iv_actionBar_menu;
	public static TextView tv_actionbar_title;
	
	
    
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_fragment);
		
		intent = getIntent();
		//getActionBar().setTitle("Testing");
		userSessionManager = new UserSessionManager(MainFragmentActivity.this);
		
		// Generate title
        title = new String[] { 
        		"Home",
        		"Favorite",
        		"Search Property",
        		"Search Requirement",
                "Find Agent",
                "Find By Property Id",
                "My Account",
                "Nominee",
                "Change Password",
                "My Property",
                "My Requirement",
                "Preferred Broker",
                "Public Property",
                "Public Requirement",
                "Dashboard",
                "Logout"                
        		};
 
        // Generate icon
        icon = new int[] { 
        		R.drawable.notification,
        		R.drawable.fav_ic,
        		R.drawable.search_ic,
        		R.drawable.srh_req_ic,
        		R.drawable.find_ag_ic,
        		R.drawable.pro_id_ic,
        		R.drawable.ac_info,
        		R.drawable.nome_ic,
        		R.drawable.pass_ic,
        		R.drawable.propert_ic,
        		R.drawable.req_ic,
        		R.drawable.pref_brk_ic,
        		R.drawable.public_pro,
        		R.drawable.public_requ,
        		R.drawable.dashboard,
        		R.drawable.logout
                };
        
        
        //Change the background of action bar
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setCustomView(R.layout.actionbar_custom);
        getActionBar().setDisplayShowCustomEnabled(true);
        
        tv_actionbar_title = (TextView) getActionBar().getCustomView().findViewById(R.id.tv_actionbar_title);
        iv_actionBar_menu = (ImageView) getActionBar().getCustomView().findViewById(R.id.iv_actionBar_menu);
        
        iv_actionBar_menu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (mDrawerLayout.isDrawerOpen(mDrawerList))
					mDrawerLayout.closeDrawer(mDrawerList);
				else 
					mDrawerLayout.openDrawer(mDrawerList);
				
			}
		});
        
        
        adapter = new NavListAdapter(this, title, icon);
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(adapter);
		mDrawerList.smoothScrollToPosition(0);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		
		//ActionBarDrawerToggle ties together the the proper interactions between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.drawable.nav_toggle,R.string.drawer_open,R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				//getSupportActionBar().setTitle(mTitle);
				supportInvalidateOptionsMenu(); //creates call to onPrepareOptionsMenu()
			}
			public void onDrawerOpened(View drawerView) {
				//getSupportActionBar().setTitle(mDrawerTitle);
				mDrawerList.focusSearch(ListView.FOCUS_UP);//set the list view scroll always up
				supportInvalidateOptionsMenu(); //creates call to onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		//Check for first time login or not if first time then set My Account Info Screen
		if (savedInstanceState == null) {
			if(intent.getStringExtra(Constant.Login.LOGIN_FIREST) != null) {
				if(intent.getStringExtra(Constant.Login.LOGIN_FIREST).equals("True") )
					selectItem(4);
				else 
					selectItem(0);
			} else if(intent.getStringExtra("From") != null){
				if(intent.getStringExtra("From").equals("AddProperty"))
					selectItem(16);
			} else {
				selectItem(0);
			}
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		MenuItem menuItem = (MenuItem) menu.findItem(R.id.action_websearch);*/
		return super.onCreateOptionsMenu(menu);
	}

	// Called whenever we call invalidateOptionsMenu()
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				if (mDrawerLayout.isDrawerOpen(mDrawerList))
					mDrawerLayout.closeDrawer(mDrawerList);
				else 
					mDrawerLayout.openDrawer(mDrawerList);
				return true;
			case R.id.action_websearch:
				Bundle adsLisingBundle = new Bundle();
				adsLisingBundle.putString("Type", "Search");
				AdsListingActivity adsListingActivity = new AdsListingActivity();
				adsListingActivity.setArguments(adsLisingBundle);
				redirectScreen(adsListingActivity);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	//The click listner for ListView in the navigation drawer
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			selectItem(position);
		}
	}
	
	//ste selected item into fragment
	private void selectItem(int position) {
		selectedPosition = position;
		// update the main content by replacing fragments
		pvoFragment = null;
		fragmentStack = new Stack<Fragment>();
		 switch (position) {
    		 //Notification
    		 case 0:
    			 //pvoFragment = new  AllNotificationActivity();
    			 
    			 pvoFragment = new  ZNotificationMainFragment();
    			 
    			 /*Bundle myPropBundel = new Bundle();
    			 myPropBundel.putString("Type", "Search");
    			 pvoFragment = new  ZMyPropertyListActivity();
    			 pvoFragment.setArguments(myPropBundel);*/
    			 
    			 break;
    			//Notification
    		 case 1:
    			 //pvoFragment = new  AllNotificationActivity();
    			 
    			 pvoFragment = new  ZShortListMainFragment();
    			 
    			 /*Bundle myPropBundel = new Bundle();
    			 myPropBundel.putString("Type", "Search");
    			 pvoFragment = new  ZMyPropertyListActivity();
    			 pvoFragment.setArguments(myPropBundel);*/
    			 
    			 break;
    		 //Search Property
    		 case 2:
    			pvoFragment = new  SearchPropertyMainActivity();
    			 break;
    		 //Search Requirement	 
    		 case 3:
    			 pvoFragment = new  SearchRequirementMainActivity();
    			  break;
    		//Find Agent
    		 case 4:
    			 pvoFragment = new  FindAgentActivity();
    			 break;
    		 //Find Property by Id
    		 case 5:
    			 pvoFragment = new  FindPropertyByIdActivity();
    			 break;
    		 // My Account Info
    		 case 6:
    			 pvoFragment = new  MyAccountActivity();
    			 break;
    		 //Nominee
    		 case 7:
    			pvoFragment = new  NomineeListActivity();
    			 break;
    	     //Change Password
    		 case 8:
    			 pvoFragment = new  ChangePasswordActivity();
    			 break;
    		 //My Property
    		 case 9:
    			 Bundle myPropertyBundel = new Bundle();
    			 myPropertyBundel.putString("Type", "Search");
    			 pvoFragment = new  ZMyPropertyListActivity();
    			 pvoFragment.setArguments(myPropertyBundel);
    			 break;
    	     //My Requirement
    		 case 10:
    			 Bundle myRequirementBundel = new Bundle();
    			 myRequirementBundel.putString("Type", "Search");
    			 pvoFragment = new  MyRequirementListActivity();
    			 pvoFragment.setArguments(myRequirementBundel);
    			 break;
    		 //prefered Broker
    		 case 11:
    			 pvoFragment = new  PreferreBrokerListActivity();
    			 break;
    		 //Public Property
    		 case 12:
    			 Bundle publicPropertyBundel = new Bundle();
    			 publicPropertyBundel.putString("Type", "Search");
    			 pvoFragment = new  PublicPropertyListActivity();
    			 pvoFragment.setArguments(publicPropertyBundel);
    			 break;
    		 //Public Requirement
    		 case 13:
    			 Bundle publicRequirementBundel = new Bundle();
    			 publicRequirementBundel.putString("Type", "Search");
    			 pvoFragment = new  PublicRequirementListActivity();
    			 pvoFragment.setArguments(publicRequirementBundel);
    			 break;
    		 //Utilities
    		 case 14:
    			startActivity(new Intent(getApplicationContext(), DashboardNewActivity.class));
    			break;
    		 //Send GCM id
    		 /*case 14:
    			 SharedPreferences prefs 	= this.getSharedPreferences(SplashScreenActivity.PVOREGID, Context.MODE_PRIVATE);
    			// prefs.getString(SplashScreenActivity.PROPERTY_REG_ID,"");
    			 Intent i = new Intent(Intent.ACTION_SEND);
					i.setType("message/rfc822");
					i.putExtra(Intent.EXTRA_EMAIL, new String[] { "hirenk@websoptimization.com","nikunj@websoptimization.com","niravj@websoptimization.com" });
					i.putExtra(Intent.EXTRA_SUBJECT, "Device Id and GCM Id");
					i.putExtra(Intent.EXTRA_TEXT,"GCM Id: "+prefs.getString(SplashScreenActivity.PROPERTY_REG_ID,""));
					startActivity(Intent.createChooser(i,"Choose an Email client :"));
    			 break;*/
    		 
    			 //Logout
    		 case 15:
    			//final  = new UserSessionManager(getApplicationContext());
		        // Clearing all user data from Shared Preferences
		    	deleteGsmIdService = new GsmIdDeleteService();
		        WebserviceClient deleteGCMIdWebserviceClient = new WebserviceClient(MainFragmentActivity.this,deleteGsmIdService);
		        deleteGCMIdWebserviceClient.setResponseListner(new ResponseListner() {
					@Override
					public void handleResponse(Object response) {
						JSONObject jsonObject = (JSONObject) response;
						if(jsonObject != null){
							try {
								if (String.valueOf(jsonObject.get(Constant.DeleteRegisterGSM.API_STATUS)).equals("1")) {
									Toast.makeText(getApplicationContext(),String.valueOf(jsonObject.get(Constant.DeleteRegisterGSM.API_MESSAGE)), Toast.LENGTH_LONG).show();
									userSessionManager.logoutUser();
								} else {
									Toast.makeText(getApplicationContext(),String.valueOf(jsonObject.get(Constant.DeleteRegisterGSM.API_MESSAGE)), Toast.LENGTH_LONG).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} 
					}
				});
		        deleteGCMIdWebserviceClient.execute(userSessionManager.getSessionValue(Constant.Login.PHONE_NUMBER));
    			break;
    			
    			//Property detail
    		 case 16:
    			 Bundle propDetailBundel = new Bundle();
    			 propDetailBundel.putString("propertyid", intent.getStringExtra("PropertyId"));
    			 pvoFragment = new  ZPropertyDetail();
    			 pvoFragment.setArguments(propDetailBundel);
    			 break;
		}
		 //redirect the screen
		 if (pvoFragment != null) {
	        fragmentManager = getSupportFragmentManager();
	        redirectScreen(pvoFragment);
	        // update selected item and title, then close the drawer
	        mDrawerList.setItemChecked(position, true);
	        mDrawerList.setSelection(position);
	        if(position != 16)
	        	setTitle(title[position]);
	        else {
	        	setTitle("Property Detail");
	        }
	        mDrawerLayout.closeDrawer(mDrawerList);
		 }
	}
	
	
	//Set Title on menu item selected 
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		//tv_actionbar_title.setText(mTitle);
		setTitle(mTitle);
		getActionBar().setTitle(mTitle);
	}

	//When using the ActionBarDrawerToggle, you must call it during onPostCreate() and onConfigurationChanged()...
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	
	//Redirect the Fragment onBackPress or Menu item selected
	public void redirectScreen(Fragment newFragment) {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		SupportMapFragment f = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
		if (f != null) 
			fragmentTransaction.remove(f);
		
		Fragment visibleFragment = getVisibleFragment();
		if(visibleFragment != null)
			fragmentTransaction.hide(visibleFragment);
		
		fragmentTransaction.add(R.id.content_frame, newFragment);
		if(fragmentStack != null) {
			fragmentStack.push(newFragment);
		} else {
			fragmentStack = new Stack<Fragment>();
			fragmentStack.push(newFragment);
		}
		
		fragmentTransaction.commit();
	}

	//Redirect to screen with create new stack
	public void redirectScreenWithoutStack(Fragment newFragment) {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		SupportMapFragment f = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
		if (f != null) 
			fragmentTransaction.remove(f);
		
		fragmentStack = new Stack<Fragment>();
		Fragment visibleFragment = getVisibleFragment();
		if(visibleFragment != null)
			fragmentTransaction.hide(visibleFragment);
		
		fragmentTransaction.add(R.id.content_frame, newFragment);
		fragmentStack.push(newFragment);
		fragmentTransaction.commit();
		
	}
	
	
	//Redirect to
	public void redirectToNotificaiton() {
		selectItem(0);
	}
	
	//get the Visible Fragment
	public Fragment getVisibleFragment() {
	    FragmentManager fragmentManager = getSupportFragmentManager();
	    List<Fragment> fragments = fragmentManager.getFragments();
	    if(fragments != null) {
		    for(Fragment fragment : fragments){
		        if(fragment != null && fragment.isVisible()) {
		            return fragment;
		        }
		    }
	    }
	    return null;
	}
	
	// onbackPress pop the fragment stack
	@Override
	public void onBackPressed() {
		if (fragmentStack.size() > 1) {
			System.out.println("--> On back press if condition <---");
			FragmentTransaction ft = fragmentManager.beginTransaction();
			if(ft != null) {
				fragmentStack.lastElement().onPause();
				ft.remove(fragmentStack.pop());
				Fragment lastFragment = fragmentStack.lastElement();
				lastFragment.onResume();
				ft.show(lastFragment);
				ft.commit();
			}
		} else {
			System.out.println("--> On back press else condition <---");
			Fragment visible = getVisibleFragment();
			if(visible instanceof ZNotificationMainFragment) {
				clearStoreData();
				Intent intent = new Intent(Intent.ACTION_MAIN);
			    intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			} else {
				redirectToNotificaiton();
			}
		}
	}
	
	//clear stored data when user close application
	public static void clearStoreData(){
		nomineeFlag = "";
		nomineeResponse = new JSONArray();
	}
	
	
	/*
	 * Titil
	 * Set actionbar title
	 */
	public static void setTitle(String title) {
		if(ZMyPropertyListActivity.badge4 != null) {
			System.out.println("---> setTitle <--");
			ZMyPropertyListActivity.badge4.hide();
		}
		tv_actionbar_title.setText(title.toUpperCase());
	}
}
