package com.appstoremarketresearch.android_customalertdialogs.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.AbstractSet;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * AppEventNotifier
 */
public class AppEventNotifier
    extends android.content.BroadcastReceiver
    implements AssetFileNameReceiver {

    private static AbstractSet<AssetFileNameReceiver> mAssetFileNameReceivers =
            new CopyOnWriteArraySet<>();

    private static final String LOG_TAG = AppEventNotifier.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        Bundle extras = intent.getExtras();

        switch (action) {
            case "ASSET_FILE_SELECTED":
                String filename = extras.getString("filename");
                receiveAssetFileName(filename);
                break;

            default:
                Log.w(LOG_TAG, "Unexpected event type: " + action);
                break;
        }
    }

    @Override
    public void receiveAssetFileName(String filename) {
        for (AssetFileNameReceiver listener : mAssetFileNameReceivers) {
            listener.receiveAssetFileName(filename);
        }
    }

    /**
     * subscribe
     */
    public static void subscribe(AssetFileNameReceiver listener) {
        if (listener != null) {
            mAssetFileNameReceivers.add(listener);
        }
    }

    /**
     * unsubscribe
     */
    public static void unsubscribe(AssetFileNameReceiver listener) {
        if (listener != null) {
            mAssetFileNameReceivers.remove(listener);
        }
    }
}
