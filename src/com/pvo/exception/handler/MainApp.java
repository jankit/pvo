package com.pvo.exception.handler;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

import android.app.Application;
//https://github.com/TheMedo/acra_example/blob/master/acra/src/main/java/com/example/acra/MainApp.java
@ReportsCrashes(
		formKey = "",
		formUri = "http://complainregister.raichuraenergy.com/post_mail.php/",
		reportType = HttpSender.Type.FORM,
	    httpMethod = HttpSender.Method.POST,
        customReportContent = {
                ReportField.APP_VERSION_CODE,
                ReportField.APP_VERSION_NAME,
                ReportField.ANDROID_VERSION,
                ReportField.PACKAGE_NAME,
                ReportField.REPORT_ID,
                ReportField.BUILD,
                ReportField.USER_COMMENT,
                ReportField.STACK_TRACE}
		)

	public class MainApp extends Application {
	    @Override
	    public void onCreate() {
	        super.onCreate();
	        
	        
	    // The following line triggers the initialization of ACRA
	    ACRA.init(this);
	     
	    //http://stackoverflow.com/questions/8722826/when-do-i-need-to-call-this-method-runtime-getruntime-addshutdownhook
	    /*Runtime.getRuntime().addShutdownHook(new Thread() {
	        public void run() {
		        if (!BuildConfig.DEBUG) {
		            new ANRWatchDog(10000).start();
		        }
	        }
	      });*/
	        
	        
	        
	       /* 
	        if (!BuildConfig.DEBUG) {
	            new ANRWatchDog(10000).start();
	        }*/
	    }
	}
