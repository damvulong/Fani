/*
 * *
 *  * Created by damvulong on 4/18/22, 10:30 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/9/22, 2:18 PM
 *
 */

package com.example.fani.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fani.BuildConfig;
import com.example.fani.R;
import com.example.fani.data.State.FragmentState;
import com.example.fani.databinding.ActivityMainBinding;
import com.example.fani.presentation.aboutUs.AboutUsActivity;
import com.example.fani.presentation.fragment.CartFragment;
import com.example.fani.presentation.fragment.FavoriteFragment;
import com.example.fani.presentation.fragment.home.HomeFragment;
import com.example.fani.presentation.fragment.ProfileFragment;
import com.example.fani.presentation.login.LoginActivity;
import com.example.fani.utils.Utilities;
import com.google.android.material.navigation.NavigationView;
import com.zoho.commons.LauncherModes;
import com.zoho.commons.LauncherProperties;
import com.zoho.salesiqembed.ZohoSalesIQ;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    //init View Model
    private MainViewModel mainViewModel;

    private int mCurrentFragment = FragmentState.home.value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Setup view biding
         Document: https://developer.android.com/topic/libraries/view-binding*/
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /**Setting main view model*/
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getLoggedOutLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoggedOut) {
                if (isLoggedOut) {
                    Utilities.showToast(getApplicationContext(), getString(R.string.title_logout_successfully));
                }
            }
        });
        // #10 TODO https://salesiq.zoho.com/long1234/settings/brands/712564000000002056/installation/android
        ZohoSalesIQ.init(getApplication(), BuildConfig.APP_KEY_ZOHO, BuildConfig.ACCESS_KEY_ZOHO);
        //to set can moving
        ZohoSalesIQ.setLauncherProperties(new LauncherProperties(LauncherModes.FLOATING));
        ZohoSalesIQ.showLauncher(true);

        //replaceFragment(new HomeFragment());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        binding.bottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.bottom_home) {
                openHomeFragment();
                navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
            } else if (id == R.id.bottom_favorite) {
                openFavoriteFragment();
                navigationView.getMenu().findItem(R.id.nav_favorite).setChecked(true);
            } else if (id == R.id.bottom_cart) {
                openCartFragment();
                navigationView.getMenu().findItem(R.id.nav_cart).setChecked(true);
            } else if (id == R.id.bottom_profile) {
                openProfileFragment();
                navigationView.getMenu().findItem(R.id.nav_profile).setChecked(true);
            }
            return true;
        });

    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            openHomeFragment();
            binding.bottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
        } else if (id == R.id.nav_favorite) {
            openFavoriteFragment();
            binding.bottomNavigationView.getMenu().findItem(R.id.bottom_favorite).setChecked(true);
        } else if (id == R.id.nav_cart) {
            openCartFragment();
            binding.bottomNavigationView.getMenu().findItem(R.id.bottom_cart).setChecked(true);
        } else if (id == R.id.nav_profile) {
            openProfileFragment();
            binding.bottomNavigationView.getMenu().findItem(R.id.bottom_profile).setChecked(true);
        } else if (id == R.id.nav_logout) {
            /**Handle logout in firebase*/
            Intent intent = new Intent(this, LoginActivity.class);
            /**This is handle create more activity without call back
             * Follow this document: https://stackoverflow.com/questions/20235697/go-back-to-2nd-previous-activity
             * */
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            mainViewModel.logOut();

        } else if (id == R.id.nav_aboutUS) {
            Intent intent = new Intent(this, AboutUsActivity.class);
            startActivity(intent);
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openHomeFragment() {
        if (mCurrentFragment != FragmentState.home.value) {
            replaceFragment(new HomeFragment());
            mCurrentFragment = FragmentState.home.value;
        }
    }

    private void openFavoriteFragment() {
        if (mCurrentFragment != FragmentState.favorite.value) {
            replaceFragment(new FavoriteFragment());
            mCurrentFragment = FragmentState.favorite.value;
        }
    }

    private void openCartFragment() {
        if (mCurrentFragment != FragmentState.cart.value) {
            replaceFragment(new CartFragment());
            mCurrentFragment = FragmentState.cart.value;
        }
    }

    private void openProfileFragment() {
        if (mCurrentFragment != FragmentState.profile.value) {
            replaceFragment(new ProfileFragment());
            mCurrentFragment = FragmentState.profile.value;
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrameLayout, fragment);
        fragmentTransaction.commit();
    }
}