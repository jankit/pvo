package com.pvo.service;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pvo.components.CustomeProcessDialog;
import com.pvo.prototype.PVOAction;
import com.pvo.prototype.PVOActivity;
import com.pvo.prototype.PVOFragment;
import com.pvo.prototype.PVOService;
import com.pvo.prototype.ResponseListner;

public class WebserviceClient extends AsyncTask<String, Void, Object> {
	private PVOAction pvoAction;
	private PVOService pvoService;
	private CustomeProcessDialog dialog;
	private Context currentContext;
	private long startTime;
	private long endTime;
	private PVOActivity pvoActivity;
	private ResponseListner responseListner;
	private String errorMsg;
	private LinearLayout progressLayout;
	
	/** this Construction is used for fragment **/
	public WebserviceClient(PVOAction activity, PVOService service) {
		this.pvoAction = activity;
		this.pvoService = service;
		
		if(activity instanceof PVOFragment)
			currentContext = ((PVOFragment)activity).getActivity();
		else if(activity  instanceof PVOActivity)
			currentContext = ((PVOActivity)activity);
		
		this.dialog = new CustomeProcessDialog(currentContext);
		//Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(currentContext));
	}
	/** END **/
	
	/** this construction is used in Array Adapter class **/
	public WebserviceClient(Context context, PVOService service) {
		this.pvoService = service;
		this.dialog = new CustomeProcessDialog(context);
		this.currentContext = context;
		//Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(context));
	}
	/** END **/
	
	/** this Construction is used for List View  **/
	public WebserviceClient(PVOAction activity, PVOService service,LinearLayout progressLayout) {
		this.pvoAction = activity;
		this.pvoService = service;
		this.progressLayout = progressLayout;
		
		if(activity instanceof PVOFragment)
			currentContext = ((PVOFragment)activity).getActivity();
		else if(activity  instanceof PVOActivity)
			currentContext = ((PVOActivity)activity);
		//this.dialog = new CustomeProcessDialog(currentContext);
		//Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(currentContext));
		
	}
	/** END **/
	
	/** doInBackground Method **/
	@Override
	protected Object doInBackground(String... params) {
		startTime = System.currentTimeMillis();
		try {
			return pvoService.executeService(params);
		} catch (Exception e) {
			e.printStackTrace();
			
			if(e instanceof JSONException) {
				/** Toast "Not valid response received from web service" **/
				errorMsg = "Not valid response received from "+pvoService.getServiceName();
			} else {
				/** Toast "There is some problem with web service" **/
				errorMsg = "There is some problem with "+pvoService.getServiceName();
			}
		} finally {
			endTime = System.currentTimeMillis();
		}
		return null;
	}
	/** END **/
	
	/** display toast for web service execution time **/
	private void toastTimeDuration(String task) {
		long timeTaken = endTime - startTime;
		String timeStr = String.format("%d min, %d sec", 
			    TimeUnit.MILLISECONDS.toMinutes(timeTaken),
			    TimeUnit.MILLISECONDS.toSeconds(timeTaken) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeTaken))
			);
		
		if(pvoAction instanceof PVOFragment){
			Toast.makeText(currentContext, task+" time for "+pvoService.getServiceName()+" is "+timeStr, 10000).show();
		}
	}
	/** END **/
	
	/** On PostExecute method after loading data hide the dialog **/
	@Override
	protected void onPostExecute(Object response) {
		//toastTimeDuration("execution");
		startTime = System.currentTimeMillis();
		if(StringUtils.defaultString(errorMsg).length() > 0) {
			/** if Dialog is not null **/
			if(dialog != null){
				dialog.hide();
			}
			/** END **/
			
			/** progress Layout is not null **/
			if(progressLayout != null){
				progressLayout.setVisibility(View.GONE);
			}
			/** END **/
			
			//Set dismiss message to dialog
			Toast tost = Toast.makeText(currentContext, errorMsg, Toast.LENGTH_LONG);
			tost.show();
		} else {
			if(this.responseListner != null)
				this.responseListner.handleResponse(response);
			
			else
				this.pvoAction.processResponse(response);
			
			/** Dialog is not null **/
			if(dialog != null){
				dialog.hide();
			}
			/** END **/
			
			/** Progress Layout is not null **/
			if(progressLayout != null){
				progressLayout.setVisibility(View.GONE);
			}
			/** END **/
			
		}
		endTime = System.currentTimeMillis();
		//toastTimeDuration("process");
	}
	/** END **/
	
	/** this method call before loading data and show dialog **/
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		/** if Dialog is not null **/
		if(dialog != null){
			dialog.show();
		}
		/** END **/
		
		/** if progress is not null **/
		if(progressLayout != null){
			progressLayout.setVisibility(View.VISIBLE);
		}
		/** END **/
		
	}
	/** END **/

	public ResponseListner getResponseListner() {
		return responseListner;
	}

	public void setResponseListner(ResponseListner responseListner) {
		this.responseListner = responseListner;
	}
}
