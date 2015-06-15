package com.sunger.wear.lib.toprand;

import android.annotation.TargetApi;
import android.os.Build;

import com.google.android.gms.wearable.DataApi.DataItemResult;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;

/**
 * Created by sunger on 15/6/13.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public interface TeleportListener {
	void onSendMessageResult(boolean isSuccesd, SendMessageResult result);
	void onDataItemResult(boolean isSuccesd, DataItemResult result);
}
