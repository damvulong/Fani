/*
 * *
 *  * Created by damvulong on 4/18/22, 10:28 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 4/12/22, 5:10 PM
 *
 */

package com.example.fani.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.fani.R;
import com.example.fani.adapter.OnbroadingAdapter;

import me.relex.circleindicator.CircleIndicator;

public class OnbroadingActivity extends AppCompatActivity {

    private Button btnGetStart;
    private ViewPager mViewPager;
    private CircleIndicator circleIndicator;


    private OnbroadingAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onbroading);

        initUI();

        //Get Started
        btnGetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }

        });

        viewPagerAdapter = new OnbroadingAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapter);
        circleIndicator.setViewPager(mViewPager);

    }

    private void initUI() {
        btnGetStart = findViewById(R.id.btn_get_started);
        mViewPager = findViewById(R.id.onbroading_viewPager);
        circleIndicator = findViewById(R.id.circleIndicator);
    }


}
