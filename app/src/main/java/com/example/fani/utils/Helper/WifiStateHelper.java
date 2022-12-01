
package com.example.fani.utils.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;

import androidx.lifecycle.MutableLiveData;

/**
 * Listener wifi change
 */
public class WifiStateHelper {
    /**
     * Wifi state
     */
    public static Boolean isConnected = false;
    public static MutableLiveData<Boolean> wifiStateObs = new MutableLiveData<>();

    public void registerWifiChange(Context context) {
        ConnectivityManager cm
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder = new NetworkRequest.Builder();

        /**Add callback for register wifi change*/
        cm.registerNetworkCallback(
                builder.build(),
                new ConnectivityManager.NetworkCallback() {
                    @Override
                    public void onAvailable(Network network) {
                        //Actions to take with wifi available.
                        isConnected = true;
                        wifiStateObs.postValue(true);
                    }

                    @Override
                    public void onLost(Network network) {
                        //Actions to take with lost Wifi.
                        isConnected = false;
                        wifiStateObs.postValue(false);
                    }
                }
        );
    }
}
