package com.sunger.wear.lib.toprand;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class TeleportClient implements NodeApi.NodeListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "TeleportClient";
    private GoogleApiClient mGoogleApiClient;
    private TeleportListener teleportListener;
    private ExecutorService threadPool;

    public TeleportClient(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Wearable.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        threadPool = getDefaultThreadPool();
    }

    public boolean isConnected() {
        return mGoogleApiClient.isConnected();
    }

    public void connect() {
        mGoogleApiClient.connect();
    }

    public void disconnect() {
        Log.d(TAG, "disconnect");
        Wearable.NodeApi.removeListener(mGoogleApiClient, this);
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected");
        Wearable.NodeApi.addListener(mGoogleApiClient, this);
    }

    @Override
    public void onPeerConnected(Node node) {

    }

    @Override
    public void onPeerDisconnected(Node node) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    public void syncString(String key, String item,TeleportListener listener) {
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/" + key);
        putDataMapRequest.getDataMap().putString(key, item);
        syncDataItem(putDataMapRequest);
    }

    public void syncInt(String key, int item,TeleportListener listener) {
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/" + key);
        putDataMapRequest.getDataMap().putInt(key, item);
        syncDataItem(putDataMapRequest);
    }

    public void syncLong(String key, long item,TeleportListener listener) {
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/" + key);
        putDataMapRequest.getDataMap().putLong(key, item);
        syncDataItem(putDataMapRequest);
    }

    public void syncBoolean(String key, boolean item,TeleportListener listener) {
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/" + key);
        putDataMapRequest.getDataMap().putBoolean(key, item);
        syncDataItem(putDataMapRequest);
    }

    public void syncByteArray(String key, byte[] item,TeleportListener listener) {
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/" + key);
        putDataMapRequest.getDataMap().putByteArray(key, item);
        syncDataItem(putDataMapRequest);
    }

    public void syncByte(String key, byte item,TeleportListener listener) {
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/" + key);
        putDataMapRequest.getDataMap().putByte(key, item);
        syncDataItem(putDataMapRequest);
    }

    public void syncAsset(String key, Asset item,TeleportListener listener) {
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/" + key);
        putDataMapRequest.getDataMap().putAsset(key, item);
        syncDataItem(putDataMapRequest);
    }

    public void syncAll(DataMap item,TeleportListener listener) {
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/dataMap");
        putDataMapRequest.getDataMap().putAll(item);
        syncDataItem(putDataMapRequest);
    }

    public void syncDataItem(PutDataMapRequest putDataMapRequest) {
        PutDataRequest request = putDataMapRequest.asPutDataRequest();
        Log.d(TAG, "Generating DataItem: " + request);
        sendMessage(request);
    }

    private byte[] objectToByteArray(Object obj) {
        byte[] bytes = null;
        if (obj instanceof byte[]) {
            bytes = (byte[]) obj;
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(obj);
                oos.flush();
                bytes = bos.toByteArray();
                oos.close();
                bos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex.getMessage());
            }
        }
        return bytes;
    }

    public void sendMessage(String path) {
        sendMessage(path, null);
    }

    public void sendMessage(String path, Object anyObject) {
        SendMessageRequest request = new SendMessageRequest();
        request.setPath(path);
        request.setPayload(objectToByteArray(anyObject));
        sendMessage(request);
    }

    public void sendMessage(SendMessageRequest request, TeleportListener listener) {
        sendMessage(request, listener);
    }

    private void sendMessage(Object param) {
        AsyncTask task = TaskFactory.create(mGoogleApiClient, param, getTeleportListener());
        excuteTask(task);
    }

    private void excuteTask(AsyncTask task) {
        threadPool.submit(task);
    }


    protected ExecutorService getDefaultThreadPool() {
        return Executors.newCachedThreadPool();
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }
    public TeleportListener getTeleportListener() {
        return teleportListener;
    }

    public void setTeleportListener(TeleportListener teleportListener) {
        this.teleportListener = teleportListener;
    }
}