package com.example.productapp.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Global {


    public static boolean isOnline(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static void showsnackbar(Activity activity, String message){
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void showtoast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
