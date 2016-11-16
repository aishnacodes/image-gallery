package com.example.aishna.imagegallery.broadcasts;

/**
 * Created by Aishna on 11/02/16.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.aishna.imagegallery.utils.NetworkUtils;

public class NetworkChangeReceiver extends BroadcastReceiver {

    protected boolean mIsConnected = false;

    public boolean isConnected() {
        return mIsConnected;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {

        if (NetworkUtils.isInternetAvailable(context)) {
            mIsConnected = true;
        } else {
            mIsConnected = false;
        }
    }
}