package com.pvo.custom.adapter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;
import z.com.pvo.newActivity.ZPropertyDetail;
import z.com.pvo.util.ProjectUtility;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
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
import com.pvo.activity.MyRequirementDetailActivity;
import com.pvo.activity.R;
import com.pvo.prototype.ResponseListner;
import com.pvo.service.WebserviceClient;
import com.pvo.user.service.ReadNotificationService;
import com.pvo.user.service.ShortListPropertyAddService;
import com.pvo.user.service.ShortListPropertyDeleteService;
import com.pvo.user.session.UserSessionManager;
import com.pvo.util.Constant;
import com.pvo.util.ConvertDateFormat;
import com.squareup.picasso.Picasso;

public class AllNotificationArrayAdaptor extends ArrayAdapter<AllNotificationItem> {
	
	private boolean isPrint = true;
	private String TAG = "AllNotificationArrayAdaptor";
	
	private Context context;
	private boolean favFalg = true;
	private UserSessionManager userSessionManager;
	private ReadNotificationService readNotificationService;
	
	public AllNotificationArrayAdaptor(Context context, int resourceId, List<AllNotificationItem> objects) {
		super(context, resourceId, objects);
		this.context = context;
		userSessionManager = new UserSessionManager(context);
	}

	
	@SuppressLint("ResourceAsColor")
	@Override
	  public View getView ( int position, View view, ViewGroup parent ) {
		
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	    view = inflater.inflate(R.layout.z_prop_list_row, parent, false);
	    final AllNotificationItem allNotificationItem = getItem(position);
	    
	    
	    LinearLayout ll_swipe_favCallIcon = (LinearLayout) view.findViewById(R.id.ll_swipe_favCallIcon);
	    ll_swipe_favCallIcon.setVisibility(View.VISIBLE);
	    
	    LinearLayout ll_swip_icon = (LinearLayout)view.findViewById(R.id.ll_swip_icon);
	    ll_swip_icon.setVisibility(View.GONE);
	    
	    ImageView iv_propListRow_calander = (ImageView) view.findViewById(R.id.iv_propListRow_calander);
	    iv_propListRow_calander.setVisibility(View.GONE);
	    
	    
	    /*
	     * Layout for change color 
	     */
	    LinearLayout ll_propListRow_main = (LinearLayout) view.findViewById(R.id.ll_propListRow_main);
	    if(allNotificationItem.getStstatus() != null) {
		    if(allNotificationItem.getStstatus().equals("unread")) {
		    	ll_propListRow_main.setBackgroundColor(0);
		    	ll_propListRow_main.setBackgroundResource(R.color.unreadNotificationColor);
		    }
	    }
	    
	    //Set Image from response
	    ImageView iv_propListRow_propPhoto = (ImageView) view.findViewById(R.id.iv_propListRow_propPhoto);
	    
	    if(allNotificationItem.getLogolink() != null && allNotificationItem.getLogolink().length() > 0)
  			Picasso.with(context).load(allNotificationItem.getLogolink()).placeholder(R.drawable.no_image).into(iv_propListRow_propPhoto);
  		else if(allNotificationItem.getPhotolink() != null && allNotificationItem.getPhotolink().length() > 0) 
  			Picasso.with(context).load(allNotificationItem.getPhotolink()).placeholder(R.drawable.no_image).into(iv_propListRow_propPhoto);
	    
  		/*
	     * Favorite icon
	     * On click of favorite icon add property into favorite
	     */
	    final ImageView iv_propListRow_fav = (ImageView) view.findViewById(R.id.iv_propListRow_fav);
	    if(allNotificationItem.getShortlisted() != null) {
		    if(allNotificationItem.getShortlisted().equals("1")) {
		    	iv_propListRow_fav.setImageResource(R.drawable.fav_star_act);
		    } else {
		    	iv_propListRow_fav.setImageResource(R.drawable.fav_star_dis);
		    }
		    
		    iv_propListRow_fav.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					//Toast.makeText(context, "Property Id: "+allNotificationItem.getPropertyid(), Toast.LENGTH_SHORT).show();
					if(allNotificationItem.getShortlisted().equals("0")) {
						iv_propListRow_fav.setImageResource(R.drawable.fav_star_act);
						allNotificationItem.setShortlisted("1");
						addToShortListProperty(allNotificationItem);
						
					} else {
						iv_propListRow_fav.setImageResource(R.drawable.fav_star_dis);
						allNotificationItem.setShortlisted("0");
						deleteToShortListProperty(allNotificationItem);
					}
				}
			});
	    }
	    
	    
	    /*
	     * On click of layout redirect to property detail page
	     */
	    LinearLayout ll_propListRow_detail = (LinearLayout) view.findViewById(R.id.ll_propListRow_detail);
	    ll_propListRow_detail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				//if(allNotificationItem.getStstatus() != null && allNotificationItem.getStstatus().equals("read")) {
					readNotificationService = new ReadNotificationService();
					WebserviceClient readNotificationWebserviceClient = new WebserviceClient(getContext(), readNotificationService);
					readNotificationWebserviceClient.setResponseListner(new ResponseListner() {
						@Override
						public void handleResponse(Object response) {
							JSONObject jsonObject = (JSONObject) response;
							try {
								if(jsonObject.getString(Constant.ReadNotifaction.API_STATUS).equals("0")) {
									Bundle allNotifictionBundle = new Bundle();
									if(allNotificationItem.getVtype().equalsIgnoreCase("sale req") || allNotificationItem.getVtype().equalsIgnoreCase("rent req")){
										if(allNotificationItem.getPropertyid() != null && allNotificationItem.getPropertyid().length() > 0) {
											allNotifictionBundle.putString(Constant.AllNotification.REQUIREMETN_ID,allNotificationItem.getPropertyid());
											MyRequirementDetailActivity myRequirementDetail = new MyRequirementDetailActivity();
											myRequirementDetail.setArguments(allNotifictionBundle);
											((MainFragmentActivity)getContext()).redirectScreen(myRequirementDetail);
										}
	
									} else if(allNotificationItem.getVtype().equalsIgnoreCase("sale prop") || allNotificationItem.getVtype().equalsIgnoreCase("rent prop")) {
										System.out.println("Property id--> "+allNotificationItem.getPropertyid());
										if(allNotificationItem.getPropertyid() != null && allNotificationItem.getPropertyid().length() > 0) {
											Bundle fullDetailBundle = new Bundle();
											fullDetailBundle.putString("propertyid",allNotificationItem.getPropertyid());
										    ZPropertyDetail myPropertyDetailActivity = new ZPropertyDetail();
											myPropertyDetailActivity.setArguments(fullDetailBundle);
											((MainFragmentActivity)getContext()).redirectScreen(myPropertyDetailActivity);
										}
									}
									allNotificationItem.setStstatus("read");
									notifyDataSetChanged();
								} else {
									Toast.makeText(getContext(), jsonObject.getString(Constant.ReadNotifaction.API_MESSAGE), Toast.LENGTH_LONG).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
					readNotificationWebserviceClient.execute(allNotificationItem.getNotificationid());
				/*} else {
					Bundle fullDetailBundle = new Bundle();
					fullDetailBundle.putString("propertyid",allNotificationItem.getPropertyid());
				    ZPropertyDetail myPropertyDetailActivity = new ZPropertyDetail();
					myPropertyDetailActivity.setArguments(fullDetailBundle);
					((MainFragmentActivity)getContext()).redirectScreen(myPropertyDetailActivity);
				}*/
			}
		});
	    
	    
	    /*
	     * Property type and sale/rent
	     */
	    TextView tv_propListRow_title = (TextView) view.findViewById(R.id.tv_propListRow_title);
	    //tv_propListRow_title.setText(allNotificationItem.getPropertytype()+" for "+allNotificationItem.getPurpose()+"/"+allNotificationItem.getPropertyid());
	    if(allNotificationItem.getVtype().equalsIgnoreCase("sale req")) 
	    	tv_propListRow_title.setText(allNotificationItem.getPropertyid()+"-"+allNotificationItem.getPropertytype()+" for Sale");
		else if(allNotificationItem.getVtype().equalsIgnoreCase("rent req")) 
			tv_propListRow_title.setText(allNotificationItem.getPropertyid()+"-"+allNotificationItem.getPropertytype()+" for Rent");
		else if(allNotificationItem.getVtype().equalsIgnoreCase("rent prop")) 
			tv_propListRow_title.setText(allNotificationItem.getPropertyid()+"-"+allNotificationItem.getPropertytype()+" for Rent");
		else if(allNotificationItem.getVtype().equalsIgnoreCase("sale prop")) 
			tv_propListRow_title.setText(allNotificationItem.getPropertyid()+"-"+allNotificationItem.getPropertytype()+" for Sale");
	    
	    
	    
	    /*
	     * Price 
	     */
	    TextView tv_propListRow_price = (TextView) view.findViewById(R.id.tv_propListRow_price);
	    if(allNotificationItem.getPrice().length() > 0) 
	    	tv_propListRow_price.setText(toIndianRupessFormat(Double.parseDouble(allNotificationItem.getPrice())));
	   
	    /*
	     * Bed textview
	     */
	    TextView tv_propListRow_bed = (TextView) view.findViewById(R.id.tv_propListRow_bed);
	    
	    /*if(allNotificationItem.getPropertytype().equalsIgnoreCase("Plot")) {
	    	tv_propListRow_bed.setVisibility(View.GONE);
	    } else {
	    	if(allNotificationItem.getBed().length() > 0 && !allNotificationItem.getBed().equals("0")) {
	    		//str_bed = propertyItem.getBed();
	    		tv_propListRow_bed.setVisibility(View.VISIBLE);
	    		tv_propListRow_bed.setText(allNotificationItem.getBed()+" BHK "+allNotificationItem.getPropertytype()+" | ");
	    	} else {
	    		tv_propListRow_bed.setVisibility(View.GONE);
	    	}
	    }*/
	    
	    
	    if(allNotificationItem.getPropertytype().equalsIgnoreCase("Plot")) {
	    	tv_propListRow_bed.setText(allNotificationItem.getPlotarea()+" "+allNotificationItem.getPlotareaunit());
	    } else if(allNotificationItem.getPropertytype().equalsIgnoreCase("Shop")) {
	    	tv_propListRow_bed.setText(allNotificationItem.getMinarea()+" "+allNotificationItem.getAreaunit()+" | "+allNotificationItem.getBathroom()+" Bathrooms");
	    } else {
	    	tv_propListRow_bed.setText(allNotificationItem.getBed()+" BHK "+allNotificationItem.getPropertytype()+" | "+allNotificationItem.getMinarea()+" "+allNotificationItem.getAreaunit()+" | "+allNotificationItem.getBathroom()+" Bathrooms");
	    }
	    
	   
	    /*
	     * Company name
	     */
	   TextView tv_propListRow_cmp = (TextView) view.findViewById(R.id.tv_propListRow_cmp);
	   tv_propListRow_cmp.setText(allNotificationItem.getCompanyname());
	   
	   
	    
	   /*
	    * Date
	    * Property posted date  
	    */
	   TextView tv_propListRow_date = (TextView) view.findViewById(R.id.tv_propListRow_date);
	    try {
			tv_propListRow_date.setText(ConvertDateFormat.convertDateFormat(allNotificationItem.getDtadded()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    
	    /*
	     * Location (Area) 
	     */
	    TextView tv_propListRow_location = (TextView) view.findViewById(R.id.tv_propListRow_location);
	    tv_propListRow_location.setText(allNotificationItem.getAddress());
	    
	    /*
	     * first name and last name of property posted by
	     */
	    TextView tv_propListRow_postedby = (TextView) view.findViewById(R.id.tv_propListRow_postedby);
	    tv_propListRow_postedby.setVisibility(View.VISIBLE);
	    tv_propListRow_postedby.setText(allNotificationItem.getFirstname()+" "+allNotificationItem.getLastname());

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
	 * Add property to short listed property
	 */
	private void addToShortListProperty(AllNotificationItem allNotificationItem) {
		ProjectUtility.sys(isPrint, TAG, "Property Id add--> "+allNotificationItem.getPropertyid());
		ShortListPropertyAddService shortListPropertyAddService = new ShortListPropertyAddService();
		
		WebserviceClient addWebserviceClient=new WebserviceClient(getContext(),shortListPropertyAddService );
    	addWebserviceClient.setResponseListner(new ResponseListner() {
			@Override
			public void handleResponse(Object response) {
				JSONObject jsonObject=(JSONObject)response;
				if(jsonObject != null) {
					try {
						if (String.valueOf(jsonObject.getString(Constant.DeleteMyProperty.API_STATUS)).equals("1")) {
							Toast.makeText(getContext(),String.valueOf(jsonObject.get(Constant.DeleteMyProperty.API_MESSAGE)),Toast.LENGTH_LONG).show();
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
		addWebserviceClient.execute(allNotificationItem.getPropertyid(), userSessionManager.getSessionValue(Constant.Login.USER_ID));
		
	}
	
	/*
	 * Delete property to short listed property
	 */
	private void deleteToShortListProperty(AllNotificationItem allNotificationItem) {
		ProjectUtility.sys(isPrint, TAG, "Property Id delete--> "+allNotificationItem.getPropertyid());
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
		deletWebserviceClient.execute(allNotificationItem.getPropertyid(), userSessionManager.getSessionValue(Constant.Login.USER_ID));
		
	}
}
