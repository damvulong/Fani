package com.example.fshiperapplication.Utils;

import android.Manifest;

import com.tbruyelle.rxpermissions3.RxPermissions;

public class PermissionUtils {

    /**
     * Check Location permissions
     */
    public static Boolean isLocationPermission(RxPermissions rxPermissions){
        return rxPermissions.isGranted(Manifest.permission.ACCESS_FINE_LOCATION)
                && rxPermissions.isGranted(Manifest.permission.ACCESS_COARSE_LOCATION);
    }
}
