package com.walhalla.ytlib;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkStateReceiver {
    //        extends BroadcastReceiver {
//    public static NetworkStateReceiverListener networkStateReceiverListener;
//    private static DexApplication application;
//
//    public interface NetworkStateReceiverListener {
//        void onNetworkConnectionChanged(boolean z);
//    }
//
//    public void onReceive(Context context, Intent intent) {
//        NetworkInfo activeNetwork = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
//        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
//        if (networkStateReceiverListener != null) {
//            networkStateReceiverListener.onNetworkConnectionChanged(isConnected);
//        }
//    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager)
                context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE));

        return (connectivityManager) != null
                && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
