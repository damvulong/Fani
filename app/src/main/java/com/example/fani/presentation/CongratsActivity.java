/*
 * *
 *  * Created by thaituan on 11/30/22, 2:58 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/30/22, 2:58 PM
 *
 */

package com.example.fani.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fani.databinding.ActivityCongratsBinding;
import com.example.fani.presentation.main.MainActivity;
import com.example.fani.utils.Utilities;

public class CongratsActivity extends AppCompatActivity {

    private ActivityCongratsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCongratsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.ivBtnTrackOrder.setOnClickListener(view1 -> {
            //TODO track order
            Utilities.showToast(this, "Sorry we will update soon!");
        });

        binding.ivBtnBackHome.setOnClickListener(view12 ->
                startActivity(new Intent(CongratsActivity.this, MainActivity.class)));
    }
}