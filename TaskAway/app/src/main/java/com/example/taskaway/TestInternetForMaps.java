package com.example.taskaway;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * This class will check for an available internet connection in order to load in the maps
 * Source: https://stackoverflow.com/questions/9570237/android-check-internet-connection
 * Source Author: Pratik Butani
 * Accessed on: April 5, 2018
 *
 * @author - Diane Boytang
 *
 */

public class TestInternetForMaps {

    /**
     * Method to text if the device is connected to internet (either cellular data or wifi)
     * @param context - Application context
     * @return - boolean; true if connected, false if not connected
     */


    public static boolean checkConnection(Context context){
        final ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null){
            return true;
        }
        else{
            return false;
        }

    }

}
