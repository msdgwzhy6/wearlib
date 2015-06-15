package com.sunger.wear.lib.toprand;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by sunger on 15/6/13.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public abstract class AsyncTask implements Runnable {
    private static final int MESSAGE_SUCCESS = 0;
    private static final int MESSAGE_FAILUE = 1;

    public TeleportListener listener;
    private GoogleApiClient googleApiClient;


    AsyncTask(GoogleApiClient googleApiClient, TeleportListener listener) {
        this.googleApiClient = Utils.notNull(googleApiClient, "GoogleApiClient");
        this.listener = listener;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            AsyncTask.this.handleMessage(msg);
        }
    };

    private void handleMessage(Message msg) {
        if (msg.what == MESSAGE_SUCCESS) {
            handleSuccessMessage(msg);
        } else if (msg.what == MESSAGE_FAILUE) {
            handleFailureMessage();
        }
    }

    protected abstract void handleFailureMessage();

    protected abstract void handleSuccessMessage(Message msg);


    public Collection<String> getConnectedNodesResult() {
        HashSet<String> results = new HashSet<String>();
        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi
                .getConnectedNodes(getGoogleApiClient()).await();
        if (nodes == null) {
            return Collections.emptyList();
        }
        for (Node node : nodes.getNodes()) {
            results.add(node.getId());
        }
        return results;
    }


    private Message obtainMessage(int type, Object msg) {
        Message message = new Message();
        message.obj = msg;
        return message;
    }

    protected void sendSuccessMessage(Object msg) {
        sendMessage(obtainMessage(MESSAGE_SUCCESS, msg));
    }

    protected void sendFailureMessage() {
        sendMessage(obtainMessage(MESSAGE_FAILUE, null));
    }

    private void sendMessage(Message msg) {
        if (getListener() != null)
            handler.sendMessage(msg);
    }

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }


    public TeleportListener getListener() {
        return listener;
    }


}
