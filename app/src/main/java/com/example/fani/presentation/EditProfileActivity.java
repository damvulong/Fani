/*
 * *
 *  * Created by damvulong on 11/27/22, 12:06 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/27/22, 12:06 AM
 *
 */

package com.example.fani.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.fani.databinding.ActivityEditProfileBinding;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}