package com.sunger.wear.lib.toprand;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataRequest;

/**
 * Created by sunger on 15/6/14.
 */
public class TaskFactory {
    public static AsyncTask create(GoogleApiClient client, Object param, TeleportListener listener) {
        if (param instanceof PutDataRequest) {
            return newPutDataTask(client, param, listener);
        } else if (param instanceof SendMessageRequest) {
            return newSendMessageTask(client, param, listener);
        }
        return new NullObject(client, listener);
    }

    private static PutDataTask newPutDataTask(GoogleApiClient client, Object param, TeleportListener listener) {
        PutDataTask task = new PutDataTask(client, listener);
        task.setPutDataRequest((PutDataRequest) param);
        return task;
    }

    private static SendMessageTask newSendMessageTask(GoogleApiClient client, Object param, TeleportListener listener) {
        SendMessageTask task = new SendMessageTask(client, listener);
        task.setRequest((SendMessageRequest) param);
        return task;
    }
}
