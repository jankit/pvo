package com.pvo.json.cache;

import org.acra.ReportField;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

import android.content.Intent;
import android.net.ParseException;

/**
 * This class is call everyday 00:00:00 in background call doReminderWork()
 * method check update is available or not
 * 
 * @author HirenK
 * 
 * @Ref 
 *  http://stackoverflow.com/questions/6001245/set-an-alarm-from-my-application
 */

@ReportsCrashes(
		//mailTo = "hirenk@websoptimization.com",
		//forceCloseDialogAfterToast = true,
        //formUri = "https://medo.cloudant.com/acra-example/_design/acra-storage/_update/report",
        //reportType = HttpSender.Type.JSON,
        //httpMethod = HttpSender.Method.POST,
        //formUriBasicAuthLogin = "tubtakedstinumenterences",
        //formUriBasicAuthPassword = "igqMFFMatvtMXVCKgy7u6a5W",
        //formKey = "", // This is required for backward compatibility but not used
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
                ReportField.STACK_TRACE
        }
        //mode = ReportingInteractionMode.DIALOG,
        //resDialogText = R.string.crash_dialog_text,
        //resDialogIcon = android.R.drawable.ic_dialog_info, //optional. default is a warning sign
        //resDialogTitle = R.string.crash_dialog_title, // optional. default is your application name
        //resDialogCommentPrompt = R.string.crash_dialog_comment_prompt, // optional. when defined, adds a user text field input with this text resource as a label
        //resDialogOkToast = R.string.crash_dialog_ok_toast // optional. displays a Toast message when the user accepts to send a report.
)
public class UpdateJsonService extends WakeIntentService {

	public UpdateJsonService() {
		super("AlarmService");
	}

	@Override
	void doReminderWork(Intent intent) {
		try {
			//Create and write state list file
			CreateJsonArrayFileIntoCache.createAndWriteStateListFile(getApplicationContext());
			
			//Create and write area list file
			CreateJsonArrayFileIntoCache.createAndWriteAreaListFile(getApplicationContext());
			
			//Create and write facility list file
			CreateJsonArrayFileIntoCache.createAndWriteFacilityListFile(getApplicationContext());
			
			//Create and write TPScheme list file
			CreateJsonArrayFileIntoCache.createAndWriteTPSchemeListFile(getApplicationContext());
			
			// set notification
			/*NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			Notification note = new Notification(com.pvo.activity.R.drawable.logo_sm,new Date().getHours()+":"+new Date().getMinutes()+ "Update Completed", System.currentTimeMillis());
			note.setLatestEventInfo(this, "Update Completed", "update successfully!", null);
			note.defaults |= Notification.DEFAULT_ALL;
			note.flags |= Notification.FLAG_AUTO_CANCEL;
			manager.notify(1346579, note);*/
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
