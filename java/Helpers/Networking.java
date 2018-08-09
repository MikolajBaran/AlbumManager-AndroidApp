package com.example.a4id1.manageralbumow.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by 4id1 on 2017-12-07.
 */
public class Networking {
    private Context context;

    public Networking(Context context) {
        this.context = context;
    }

    public boolean checkConnection(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        else{
            return true;
        }
    }
}
