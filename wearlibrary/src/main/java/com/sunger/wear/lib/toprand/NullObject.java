package com.sunger.wear.lib.toprand;

import android.os.Message;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by sunger on 15/6/14.
 */
public class NullObject extends AsyncTask {
    public NullObject(GoogleApiClient googleApiClient, TeleportListener listener) {
        super(googleApiClient, listener);
    }

    @Override
    protected void handleFailureMessage() {
        getListener().onDataItemResult(false, null);
        getListener().onSendMessageResult(false, null);
    }

    @Override
    protected void handleSuccessMessage(Message msg) {

    }

    @Override
    public void run() {
        sendFailureMessage();
    }
}
