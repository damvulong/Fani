
package com.example.fani.presentation.appApplication;

import android.app.Application;

import com.example.fani.utils.Helper.WifiStateHelper;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new WifiStateHelper().registerWifiChange(this);
    }
}
