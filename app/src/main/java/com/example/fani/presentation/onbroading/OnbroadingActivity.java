/*
 * *
 *  * Created by damvulong on 11/12/22, 7:28 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/11/22, 4:50 PM
 *
 */

package com.example.fani.presentation.onbroading;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.fani.databinding.ActivityOnbroadingBinding;
import com.example.fani.presentation.adapter.OnbroadingAdapter;
import com.example.fani.presentation.login.LoginActivity;

public class OnbroadingActivity extends AppCompatActivity {

    private ActivityOnbroadingBinding binding;

    private OnbroadingAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Setup view biding
         Document: https://developer.android.com/topic/libraries/view-binding*/
        binding = ActivityOnbroadingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        handleEvent();

        viewPagerAdapter = new OnbroadingAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.onbroadingViewPager.setAdapter(viewPagerAdapter);
        binding.circleIndicator.setViewPager(binding.onbroadingViewPager);
    }

    private void handleEvent() {
        //Get Started
        binding.btnGetStarted.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        });
    }
}
