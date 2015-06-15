package com.sunger.wear.lib.toprand;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by sunger on 15/6/13.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public abstract class TeleportService extends WearableListenerService {

    private static final String TAG = "TeleportService";

    public abstract void handleMessageReceived(MessageEvent messageEvent);

    public abstract void handleDataChanged(DataEventBuffer dataEvents);

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        Log.d(TAG, "Received Message");
        handleMessageReceived(messageEvent);
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d(TAG, "onDataChanged");
        handleDataChanged(dataEvents);
    }
}
