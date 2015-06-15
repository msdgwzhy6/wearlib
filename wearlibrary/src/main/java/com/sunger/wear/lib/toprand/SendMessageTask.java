package com.sunger.wear.lib.toprand;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Message;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;
import com.google.android.gms.wearable.Wearable;

import java.util.Collection;

/**
 * Created by sunger on 15/6/13.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class SendMessageTask extends AsyncTask {
    private SendMessageRequest request;

    public SendMessageTask(GoogleApiClient googleApiClient, TeleportListener listener) {
        super(googleApiClient, listener);
    }

    @Override
    protected void handleFailureMessage() {
        getListener().onSendMessageResult(false,null);
    }

    @Override
    protected void handleSuccessMessage(Message msg) {
        SendMessageResult sendMessageResult = (SendMessageResult) msg.obj;
        getListener().onSendMessageResult(sendMessageResult.getStatus().isSuccess(),
                sendMessageResult);
    }

    @Override
    public void run() {
        if (!getGoogleApiClient().isConnected()) {
            sendFailureMessage();
        }
        else
        {
            Collection<String> ids = getConnectedNodesResult();
            for (String id : ids) {
                request.setDeviceNodeId(id);
                sendWearMessage(getGoogleApiClient());
            }
        }

    }

    private void sendWearMessage(GoogleApiClient client) {
        Wearable.MessageApi.sendMessage(client, request.getDeviceNodeId(), request.getPath(), request.getPayload())
                .setResultCallback(
                        new ResultCallback<MessageApi.SendMessageResult>() {

                            @Override
                            public void onResult(
                                    SendMessageResult sendMessageResult) {
                                sendSuccessMessage(sendMessageResult);
                            }
                        });
    }



    public SendMessageRequest getRequest() {
        return request;
    }

    public void setRequest(SendMessageRequest request) {
        this.request = request;
    }

}
