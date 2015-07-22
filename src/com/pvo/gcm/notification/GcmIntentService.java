package com.pvo.gcm.notification;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.pvo.activity.MainFragmentActivity;
import com.pvo.activity.R;
import com.pvo.util.Constant;
import com.pvo.util.Constant.AllNotification;

//http://www.androidbegin.com/tutorial/android-google-cloud-messaging-gcm-tutorial/
public class GcmIntentService extends IntentService {

	public static final int NOTIFICATION_ID = 1;
	private static final String TAG = null;
	private String message;

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		message = intent.getStringExtra("message");
		//Toast.makeText(getApplicationContext(), "Messag "+message, Toast.LENGTH_LONG).show();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) { // has effect of unparcelling Bundle
			/*
			 * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				sendNotification(intent.getExtras().getString("message"));
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				sendNotification(intent.getExtras().getString("message"));
				// If it's a regular GCM message, do some work.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
				// This loop represents the service doing some work.
				for (int i = 0; i < 5; i++) {
					Log.i(TAG,"Working... " + (i + 1) + "/5 @ "+ SystemClock.elapsedRealtime());
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
				}
			}
				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
				// Post notification of received message.
				sendNotification(intent.getExtras().getString("message"));
				Log.i(TAG, "Received: " + extras.toString());
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	@SuppressLint("NewApi")
	private void sendNotification(String msg) {
		// Create the notification with a notification builder
		/*Intent intent = new Intent(getApplicationContext(), MainFragmentActivity.class);
		intent.putExtra(Constant.Login.LOGIN_FIREST, "false");
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(getApplicationContext(), MainFragmentActivity.class), 0);
		
		Notification notification = new Notification.Builder(this)
		.setSmallIcon(R.drawable.logo_sm)
		.setWhen(System.currentTimeMillis())
		.setContentTitle("PropertyViaOnline")
		.setContentText(message).setContentIntent(contentIntent)
		.getNotification();
		// Remove the notification on click
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		 
		notification.defaults |= Notification.DEFAULT_SOUND;
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		manager.notify(R.string.app_name, notification);
		
		{
		// Wake Android Device when notification received
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		final PowerManager.WakeLock mWakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK| PowerManager.ACQUIRE_CAUSES_WAKEUP, "GCM_PUSH");
		mWakelock.acquire();
		 
		// Timer before putting Android Device to sleep mode.
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				mWakelock.release();
			}
		};
		timer.schedule(task, 50);
		}
	}
}
