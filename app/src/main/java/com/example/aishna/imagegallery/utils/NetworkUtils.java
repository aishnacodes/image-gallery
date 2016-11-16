package com.example.aishna.imagegallery.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Aishna on 10/02/16.
 */

public class NetworkUtils {

    public static boolean isInternetAvailable(Context context) {
        NetworkInfo info = ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        return info != null && info.isConnected();
    }
}

