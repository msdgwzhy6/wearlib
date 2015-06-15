package com.sunger.wear.lib.toprand;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Message;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi.DataItemResult;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by sunger on 15/6/13.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class PutDataTask extends AsyncTask {

    private PutDataRequest putDataRequest;

    PutDataTask(GoogleApiClient googleApiClient, TeleportListener listener) {
        super(googleApiClient, listener);
    }

    @Override
    protected void handleFailureMessage() {
        getListener().onDataItemResult(false,
                null);
    }

    @Override
    protected void handleSuccessMessage(Message msg) {
        DataItemResult dataItemResult = (DataItemResult) msg.obj;
        getListener().onDataItemResult(dataItemResult.getStatus().isSuccess(),
                dataItemResult);
    }

    @Override
    public void run() {
        if (!getGoogleApiClient().isConnected()) {
            sendFailureMessage();
        }else {
            sendDataMessage(getGoogleApiClient(), putDataRequest);
        }
    }

    private void sendDataMessage(GoogleApiClient client, PutDataRequest request) {
        Wearable.DataApi.putDataItem(client, request).setResultCallback(
                new ResultCallback<DataItemResult>() {
                    @Override
                    public void onResult(DataItemResult dataItemResult) {
                        sendSuccessMessage(dataItemResult);
                    }
                });
    }


    public void setPutDataRequest(PutDataRequest putDataRequest) {
        this.putDataRequest = putDataRequest;
    }

}
