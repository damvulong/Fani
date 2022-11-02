/*
 * *
 *  * Created by damvulong on 4/18/22, 10:31 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 4/10/22, 1:51 AM
 *
 */

package com.example.fani.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fani.R;
import com.example.fani.fragment.CartFragment;
import com.example.fani.fragment.HomeFragment;
import com.example.fani.fragment.ProfileFragment;
import com.example.fani.fragment.FavoriteFragment;
import com.example.fani.utils.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.zoho.salesiqembed.ZohoSalesIQ;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private BottomNavigationView bottomNavigationView;

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_FAVORITE = 1;
    private static final int FRAGMENT_CART = 2;
    private static final int FRAGMENT_PROFILE = 3;

    private int mCurrentFragment = FRAGMENT_HOME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        //replaceFragment(new HomeFragment());
        ZohoSalesIQ.init(getApplication(), Constants.APP_KEY, Constants.ACCESS_KEY);
        ZohoSalesIQ.showLauncher(true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        bottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);

        bottomNavigationView.setOnItemSelectedListener(item -> {

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
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            openHomeFragment();
            bottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
        } else if (id == R.id.nav_favorite) {
            openFavoriteFragment();
            bottomNavigationView.getMenu().findItem(R.id.bottom_favorite).setChecked(true);
        } else if (id == R.id.nav_cart) {
            openCartFragment();
            bottomNavigationView.getMenu().findItem(R.id.bottom_cart).setChecked(true);
        } else if (id == R.id.nav_profile) {
            openProfileFragment();
            bottomNavigationView.getMenu().findItem(R.id.bottom_profile).setChecked(true);
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logout Successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_aboutUS) {
            Intent intent = new Intent(this, AboutUsActivity.class);
            startActivity(intent);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openHomeFragment() {
        if (mCurrentFragment != FRAGMENT_HOME) {
            replaceFragment(new HomeFragment());
            mCurrentFragment = FRAGMENT_HOME;
        }
    }

    private void openFavoriteFragment() {
        if (mCurrentFragment != FRAGMENT_FAVORITE) {
            replaceFragment(new FavoriteFragment());
            mCurrentFragment = FRAGMENT_FAVORITE;
        }
    }

    private void openCartFragment() {
        if (mCurrentFragment != FRAGMENT_CART) {
            replaceFragment(new CartFragment());
            mCurrentFragment = FRAGMENT_CART;
        }
    }

    private void openProfileFragment() {
        if (mCurrentFragment != FRAGMENT_PROFILE) {
            replaceFragment(new ProfileFragment());
            mCurrentFragment = FRAGMENT_PROFILE;
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void initUI() {
        mDrawerLayout = findViewById(R.id.drawerLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }
}