package com.pvo.json.cache;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * This is Broadcast Receiver class which starts the background service
 * @author HirenK
 *
 */
public class UpdateJsonReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        WakeIntentService.acquireStaticLock(context);
        Intent i = new Intent(context, UpdateJsonService.class);
        context.startService(i);
        
    }
}
