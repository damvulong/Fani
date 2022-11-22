/*
 * *
 *  * Created by thaituan on 11/22/22, 11:22 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/22/22, 11:22 AM
 *
 */

package com.example.fshiperapplication;

import static com.mapbox.maps.Style.MAPBOX_STREETS;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fshiperapplication.Utils.LogUtil;
import com.example.fshiperapplication.Utils.PermissionUtils;
import com.example.fshiperapplication.databinding.ActivityMainBinding;
import com.mapbox.navigation.core.MapboxNavigation;
import com.tbruyelle.rxpermissions3.RxPermissions;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RxPermissions rxPermissions;
    private CompositeDisposable compositeDisposable;
    private MapboxNavigation mapboxNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Setup view biding
         Document: https://developer.android.com/topic/libraries/view-binding*/
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        rxPermissions = new RxPermissions(this);
        compositeDisposable = new CompositeDisposable();

        binding.mapView.getMapboxMap().loadStyleUri(MAPBOX_STREETS);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (requestAppPermissions()) {
            LogUtil.d("Request permission ok");
        }
    }

    private boolean requestAppPermissions() {
        /**Request location permission*/
        if (!PermissionUtils.isLocationPermission(rxPermissions)) {
            requestAppPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
            return false;
        }
        return true;
    }

    /**
     * Request app permissions
     *
     * @param permissions
     */
    private void requestAppPermissions(String[] permissions) {
        requestPermissions(permissions, 0);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        binding.mapView.onStop();
    }
}