package com.pvo.custom.adapter;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import z.com.pvo.components.PropListRowOptionDialog;
import z.com.pvo.newActivity.ZPropAddCommercialFragment;
import z.com.pvo.newActivity.ZPropAddRecidentialFragment;
import z.com.pvo.newActivity.ZPropertyDetail;
import z.com.pvo.util.ProjectUtility;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pvo.activity.MainFragmentActivity;
import com.pvo.activity.MyPropertyInqueryListActivity;
import com.pvo.activity.R;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.MyPropertyActiveService;
import com.pvo.user.service.MyPropertyDeleteService;
import com.pvo.user.service.MyPropertyInActiveService;
import com.pvo.user.service.ShortListPropertyDeleteService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.pvo.util.ConvertDateFormat;
import com.squareup.picasso.Picasso;

//http://keepsafe.github.io/2014/11/19/building-a-custom-overflow-menu.html
public class MyPropertyArrayAdaptor extends ArrayAdapter<MyPropertyItem> {
	
	private Boolean isPrint = true;
	private String TAG = "MyPropertyArrayAdaptor";
	
	private Context context;
	private List<MyPropertyItem> myPropertyList;
	
	private UserSessionManager userSessionManager;
	private MyPropertyDeleteService deletePropertyService;
	private MyPropertyActiveService activeMyPropertyService;
	private MyPropertyInActiveService inActiveMyPropertyService;
	private String fromPrefereBroker;
	private boolean favFalg = true;
	
	
	public MyPropertyArrayAdaptor(Context context, int resourceId, List<MyPropertyItem> objects) {
		super(context, resourceId, objects);
		myPropertyList = objects;
		this.context = context;
	}
	
	public MyPropertyArrayAdaptor(Context context, int resourceId, List<MyPropertyItem> objects,String fromPrefereBroker) {
		super(context, resourceId, objects);
		myPropertyList = objects;
		this.context = context;
		this.fromPrefereBroker = fromPrefereBroker;
	}
	
	
	
	@Override
	public int getCount() {
		return myPropertyList.size();
	}

	@Override
	public View getView ( final int position, View view, ViewGroup parent ) {
		
		ProjectUtility.sys(isPrint, TAG, "getView");
		
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    view = inflater.inflate(R.layout.z_prop_list_row, parent, false);
	    
	    userSessionManager = new UserSessionManager(view.getContext().getApplicationContext());
	    final MyPropertyItem propertyItem = getItem(position);
	    
	    
	    
	    /*
	     * Inactive property layout color change 
	     */
	    final LinearLayout ll_propListRow_main = (LinearLayout) view.findViewById(R.id.ll_propListRow_main);
	    if(propertyItem.getStstatus() != null) {
		    if(propertyItem.getStstatus().equals("0")) {
		    	ll_propListRow_main.setBackgroundColor(0);
		    	ll_propListRow_main.setBackgroundResource(R.color.in_active_prop_color);
		    }
	    }
	    
	    //http://stackoverflow.com/questions/27782846/popup-menu-in-custom-listview
	   /*
	    * Open menu dialog
	    */
	    LinearLayout ll_swipe_favCallIcon = (LinearLayout) view.findViewById(R.id.ll_swipe_favCallIcon);
	    final ImageView iv_testing = (ImageView) view.findViewById(R.id.iv_propListRow_swipMenu);
	    
	    /*
	     * Show hide view
	     * Hide menu icon
	     * Visible favorite icon
	     * Hide call button
	     */
	    if(fromPrefereBroker != null && fromPrefereBroker.equals(Constant.ShortlistedPropertyList.SHORT_LISTED)) {
	    	iv_testing.setVisibility(View.GONE);
	    	ll_swipe_favCallIcon.setVisibility(View.VISIBLE);
	    	view.findViewById(R.id.iv_propListRow_call).setVisibility(View.GONE);
	    }
	    
	    iv_testing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View layout = layoutInflater.inflate(R.layout.z_poplist_row_menu, null);
				final PropListRowOptionDialog dialog = new PropListRowOptionDialog(getContext());
				dialog.setLayout(layout);
				//Toast.makeText(getContext(), position+":"+getCount(), 10).show();
				if(position == getCount()-2 || position == getCount() || position == getCount()-1) {
					 dialog.setGravity(PropListRowOptionDialog.GRAVITY_TOP)
					.setBackgroundColor(getContext().getResources().getColor(R.color.btnBlueColor))
	                .setLocationByAttachedView(iv_testing)
	                .setAnimationTranslationShow(PropListRowOptionDialog.DIRECTION_X, 350, 400, 0)
	                .setAnimationTranslationDismiss(PropListRowOptionDialog.DIRECTION_X, 350, 0, 400)
	                .setTouchOutsideDismiss(true)
	                .setMatchParent(false)
	                .setMarginLeftAndRight(24, 2)
	                .setOutsideColor(Color.TRANSPARENT)
	                .show();
				} else {
					dialog.setGravity(PropListRowOptionDialog.GRAVITY_BOTTOM)
					.setBackgroundColor(getContext().getResources().getColor(R.color.btnBlueColor))
	                .setLocationByAttachedView(iv_testing)
	                .setAnimationTranslationShow(PropListRowOptionDialog.DIRECTION_X, 350, 400, 0)
	                .setAnimationTranslationDismiss(PropListRowOptionDialog.DIRECTION_X, 350, 0, 400)
	                .setTouchOutsideDismiss(true)
	                .setMatchParent(false)
	                .setMarginLeftAndRight(24, 2)
	                .setOutsideColor(Color.TRANSPARENT)
	                .show();
				}
			
				 /*
				  * redirect to edit property screen
				  */
				 TextView tv_menu_edit = (TextView) layout.findViewById(R.id.tv_menu_edit);
				 tv_menu_edit.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						new AsyncTask<Void, Void, Void>() {
				  			ProgressDialog dialog;
				  			protected void onPreExecute() {
				  				dialog = new ProgressDialog(context);
				  				dialog.setMessage("Loading...");
				  				dialog.show();
				  			};
				  			
							@Override
							protected Void doInBackground(Void... params) {
								ProjectUtility.sys(isPrint, TAG, "Do in background->"+propertyItem.getPurpose());
								Bundle editMyPropertyBundle = new  Bundle();
								editMyPropertyBundle.putString("From","Edit");
								editMyPropertyBundle.putString(Constant.MyProperty.PROPERTY_ID,String.valueOf(propertyItem.getPropertyId()));
								
								if(propertyItem.getPurpose().equals("Residential")) {
									ProjectUtility.sys(isPrint, TAG, "Edit from Residential");
									ZPropAddRecidentialFragment zPropAddRecidentialFragment = new ZPropAddRecidentialFragment();
									zPropAddRecidentialFragment.setArguments(editMyPropertyBundle);
									((MainFragmentActivity)getContext()).redirectScreen(zPropAddRecidentialFragment);
								} else if(propertyItem.getPurpose().equals("Commercial")) {
									ProjectUtility.sys(isPrint, TAG, "Edit from Commercial");
									ZPropAddCommercialFragment addCommercialFragment = new ZPropAddCommercialFragment();
									addCommercialFragment.setArguments(editMyPropertyBundle);
									((MainFragmentActivity)getContext()).redirectScreen(addCommercialFragment);
								}
								return null;
							}
				  			
							protected void onPostExecute(Void result) {
								dialog.dismiss();
							};
				  		}.execute();
						dialog.dismiss();
					}
				});
				 
				 /*
				  * Delete selected property 
				  */
				 TextView tv_menu_delete = (TextView) layout.findViewById(R.id.tv_menu_delete);
				 tv_menu_delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						deletePropertyService = new MyPropertyDeleteService();
						//get Login User_Id from stored Session
						new AlertDialog.Builder(getContext()).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Delete Property").setMessage("Are you sure you want to delete this property?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				        @Override
				        public void onClick(DialogInterface dialog, int which) {
				        	WebserviceClient deletWebserviceClient=new WebserviceClient(getContext(),deletePropertyService);
				        	deletWebserviceClient.setResponseListner(new ResponseListner() {
								@Override
								public void handleResponse(Object response) {
									JSONObject jsonObject=(JSONObject)response;
									if(jsonObject != null) {
										try {
											if (String.valueOf(jsonObject.getString(Constant.DeleteMyProperty.API_STATUS)).equals("1")) {
												Toast.makeText(getContext(),String.valueOf(jsonObject.get(Constant.DeleteMyProperty.API_MESSAGE)),Toast.LENGTH_LONG).show();
												myPropertyList.remove(position);
												notifyDataSetChanged();
											} else {
												Toast.makeText(getContext(),String.valueOf(jsonObject.getString(Constant.DeleteMyProperty.API_MESSAGE)),Toast.LENGTH_LONG).show();
											}
										} catch (JSONException e) {
											e.printStackTrace();
										}
									}
								}
							});
							deletWebserviceClient.execute(userSessionManager.getSessionValue(Constant.Login.USER_ID),propertyItem.getPropertyId());
						}  
						}).setNegativeButton("No", null).show();
						dialog.dismiss();
					}
				});
				 
				 
				 /*
				  * Active property
				  */
				 TextView tv_menu_active = (TextView) layout.findViewById(R.id.tv_menu_active);
				 tv_menu_active.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						activeMyPropertyService = new MyPropertyActiveService();
						WebserviceClient activePropWebserviceClient=new WebserviceClient(v.getContext(),activeMyPropertyService);
						activePropWebserviceClient.setResponseListner(new ResponseListner() {
							@Override
							public void handleResponse(Object response) {
								JSONObject jsonObject=(JSONObject)response;
								if(jsonObject != null){
									try {
										if (String.valueOf(jsonObject.getString(Constant.ActiveMyProperty.API_STATUS)).equals("1")) {
											Toast.makeText(getContext().getApplicationContext(),String.valueOf(jsonObject.get(Constant.ActiveMyProperty.API_MESSAGE)),Toast.LENGTH_LONG).show();
											ll_propListRow_main.setBackgroundResource(R.color.white);
											propertyItem.setStstatus("1");
											notifyDataSetChanged();
										} else {
											Toast.makeText(getContext().getApplicationContext(),String.valueOf(jsonObject.getString(Constant.ActiveMyProperty.API_MESSAGE)),Toast.LENGTH_LONG).show();
										}
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}
							}
						 });
						activePropWebserviceClient.execute(userSessionManager.getSessionValue(Constant.Login.USER_ID),propertyItem.getPropertyId());
						
						dialog.dismiss();
					}	
				});
				 
				 /*
				  * InActive property
				  */
				 TextView tv_menu_inActive = (TextView) layout.findViewById(R.id.tv_menu_inActive);
				 tv_menu_inActive.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						inActiveMyPropertyService = new MyPropertyInActiveService();
						WebserviceClient activePropWebserviceClient = new WebserviceClient(v.getContext(),inActiveMyPropertyService);
						activePropWebserviceClient.setResponseListner(new ResponseListner() {
							@Override
							public void handleResponse(Object response) {
								JSONObject jsonObject=(JSONObject)response;
								if(jsonObject != null){
									try {
										if (String.valueOf(jsonObject.getString(Constant.ActiveMyProperty.API_STATUS)).equals("1")) {
											Toast.makeText(getContext().getApplicationContext(),String.valueOf(jsonObject.get(Constant.ActiveMyProperty.API_MESSAGE)),Toast.LENGTH_LONG).show();
											ll_propListRow_main.setBackgroundColor(0);
									    	ll_propListRow_main.setBackgroundResource(R.color.in_active_prop_color);
											propertyItem.setStstatus("0");
											notifyDataSetChanged();
										} else {
											Toast.makeText(getContext().getApplicationContext(),String.valueOf(jsonObject.getString(Constant.ActiveMyProperty.API_MESSAGE)),Toast.LENGTH_LONG).show();
										}
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}
							}
						 });
						activePropWebserviceClient.execute(userSessionManager.getSessionValue(Constant.Login.USER_ID),propertyItem.getPropertyId());
						
						dialog.dismiss();
					}	
				});
				 
				 
				 /*
				  * View receive inquery
				  */
				 TextView tv_menu_inquiry = (TextView) layout.findViewById(R.id.tv_menu_inquiry);
				 tv_menu_inquiry.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Bundle viewInquiryBundle = new Bundle();
						viewInquiryBundle.putString("propertyid", propertyItem.getPropertyId());
						MyPropertyInqueryListActivity myPropertyViewReceiveInqueryActivity = new  MyPropertyInqueryListActivity();
						myPropertyViewReceiveInqueryActivity.setArguments(viewInquiryBundle);
						((MainFragmentActivity)getContext()).redirectScreen(myPropertyViewReceiveInqueryActivity);
						//Toast.makeText(getContext(), "View receive inquery", 10).show();
						dialog.dismiss();
					}
				});
				 
				 
			}
		});
	    
	    
	    /*
	     * Favotire icon
	     * On click of favorite icon add property into favorite
	     */
	    final ImageView iv_propListRow_fav = (ImageView) view.findViewById(R.id.iv_propListRow_fav);
    	iv_propListRow_fav.setImageResource(R.drawable.fav_star_act);
	    iv_propListRow_fav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				iv_propListRow_fav.setImageResource(R.drawable.fav_star_dis);
				deleteToShortListProperty(propertyItem,position);
			}
		});
	    
	    /*
	     * Property image 
	     * Set the property image from server
	     */
		ImageView iv_propListRow_propPhoto = (ImageView) view.findViewById(R.id.iv_propListRow_propPhoto);
		Picasso.with(context).load(propertyItem.getImage1link()).placeholder(R.drawable.no_image).into(iv_propListRow_propPhoto);
		
	    /*
	     * On click of layout redirect to property detail page
	     */
	    LinearLayout ll_propListRow_detail = (LinearLayout) view.findViewById(R.id.ll_propListRow_detail);
	    ll_propListRow_detail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle fullDetailBundle = new Bundle();
				fullDetailBundle.putString("propertyid",propertyItem.getPropertyId());
			    ZPropertyDetail myPropertyDetailActivity = new ZPropertyDetail();
				myPropertyDetailActivity.setArguments(fullDetailBundle);
				((MainFragmentActivity)getContext()).redirectScreen(myPropertyDetailActivity);
			}
		});
	    
	    /*
	     * Property type and sale/rent
	     */
	    TextView tv_propListRow_title = (TextView) view.findViewById(R.id.tv_propListRow_title);
	    tv_propListRow_title.setText(propertyItem.getPropertyType()+" for "+propertyItem.getSaleOrRent());
	    
	    /*
	     * Price 
	     */
	    TextView tv_propListRow_price = (TextView) view.findViewById(R.id.tv_propListRow_price);
	    tv_propListRow_price.setText(toIndianRupessFormat(Double.parseDouble(propertyItem.getPrice())));
	   
	    /*
	     * Bed textview
	     */
	    TextView tv_propListRow_bed = (TextView) view.findViewById(R.id.tv_propListRow_bed);
	    if(propertyItem.getPropertyType().equalsIgnoreCase("Plot")) {
	    	tv_propListRow_bed.setText(propertyItem.getPlotArea()+" "+propertyItem.getPlotAreaUnit());
	    } else if(propertyItem.getPropertyType().equalsIgnoreCase("Shop")) {
	    	tv_propListRow_bed.setText(propertyItem.getMinarea()+" "+propertyItem.getAreaUnit()+" | "+propertyItem.getBathroom()+" Bathrooms");
	    } else {
	    	tv_propListRow_bed.setText(propertyItem.getBed()+" BHK "+propertyItem.getPropertyType()+" | "+propertyItem.getMinarea()+" "+propertyItem.getAreaUnit()+" | "+propertyItem.getBathroom()+" Bathrooms");
	    }
	   
	    /*
	     * Company name
	     */
	   TextView tv_propListRow_cmp = (TextView) view.findViewById(R.id.tv_propListRow_cmp);
	   tv_propListRow_cmp.setText(propertyItem.getCompanyName());
	    
	   /*
	    * Date
	    * Property posted date  
	    */
	   TextView tv_propListRow_date = (TextView) view.findViewById(R.id.tv_propListRow_date);
	    try {
			tv_propListRow_date.setText(ConvertDateFormat.convertDateFormat(propertyItem.getAddDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
	    
	    /*
	     * Location (Area) 
	     */
	    TextView tv_propListRow_location = (TextView) view.findViewById(R.id.tv_propListRow_location);
	    tv_propListRow_location.setText(propertyItem.getAreaname());
	    
	    return view;
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
	
	/*
	 * Delete property to short listed property
	 */
	private void deleteToShortListProperty(MyPropertyItem allNotificationItem,final int position) {
		ProjectUtility.sys(isPrint, TAG, "Property Id delete--> "+allNotificationItem.getPropertyId());
		ShortListPropertyDeleteService shortListPropertyDeleteService = new ShortListPropertyDeleteService();
		WebserviceClient deletWebserviceClient = new WebserviceClient(getContext(),shortListPropertyDeleteService );
    	deletWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				JSONObject jsonObject=(JSONObject)response;
				if(jsonObject != null) {
					try {
						if (String.valueOf(jsonObject.getString(Constant.DeleteMyProperty.API_STATUS)).equals("1")) {
							Toast.makeText(getContext(),String.valueOf(jsonObject.get(Constant.DeleteMyProperty.API_MESSAGE)),Toast.LENGTH_LONG).show();
							myPropertyList.remove(position);
							notifyDataSetChanged();
						} else {
							Toast.makeText(getContext(),String.valueOf(jsonObject.getString(Constant.DeleteMyProperty.API_MESSAGE)),Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		deletWebserviceClient.execute(allNotificationItem.getPropertyId(), userSessionManager.getSessionValue(Constant.Login.USER_ID));
		
	}
}
