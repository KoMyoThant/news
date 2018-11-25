package com.ps.news.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

/**
 * Created by pyaesone on 11/25/18
 */
public class NetworkUtils {

    private static NetworkUtils objInstance;

    private NetworkUtils() {

    }

    public static NetworkUtils getInstance() {
        if (objInstance == null) {
            objInstance = new NetworkUtils();
        }

        return objInstance;
    }


    public static boolean checkConnection(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
//            Toast.makeText(context, "Your device is connected to internet.", Toast.LENGTH_SHORT).show();
            return true;
        } else {
//            Toast.makeText(context, "Your device is no longer connected to internet.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
