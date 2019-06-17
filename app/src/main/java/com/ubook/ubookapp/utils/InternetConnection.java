package com.ubook.ubookapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnection {
    private static InternetConnection instance;

    private InternetConnection() {

    }

    public static InternetConnection getInstance() {
        if (instance == null) {
            instance = new InternetConnection();
        }
        return instance;
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
