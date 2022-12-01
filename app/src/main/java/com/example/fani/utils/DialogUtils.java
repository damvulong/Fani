
package com.example.fani.utils;

import android.app.Activity;

import androidx.lifecycle.Lifecycle;

import com.example.fani.R;

import org.imaginativeworld.oopsnointernet.dialogs.signal.DialogPropertiesSignal;
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal;

public class DialogUtils {

    public static void displayDialogInternet(Activity activity, Lifecycle lifecycle) {

        // No Internet Dialog
        NoInternetDialogSignal.Builder builder = new NoInternetDialogSignal.Builder(activity, lifecycle);
        DialogPropertiesSignal properties = builder.getDialogProperties();

        /**No internet dialog setting*/
        properties.setCancelable(false);
        properties.setNoInternetConnectionTitle(activity.getString(R.string.title_no_internet));
        properties.setNoInternetConnectionMessage(activity.getString(R.string.msg_wifi));
        properties.setShowInternetOnButtons(true);
        properties.setPleaseTurnOnText(activity.getString(R.string.title_button));
        properties.setWifiOnButtonText(activity.getString(R.string.btn_wifi));
        properties.setMobileDataOnButtonText(activity.getString(R.string.btn_mobile_data));

        /**Airplane mode*/
        properties.setOnAirplaneModeTitle(activity.getString(R.string.title_no_internet));
        properties.setOnAirplaneModeMessage(activity.getString(R.string.msg_airplane));
        properties.setPleaseTurnOffText(activity.getString(R.string.title_button));
        properties.setAirplaneModeOffButtonText(activity.getString(R.string.btn_airplane_mode));
        properties.setShowAirplaneModeOffButtons(true);

        builder.build();
    }
}
