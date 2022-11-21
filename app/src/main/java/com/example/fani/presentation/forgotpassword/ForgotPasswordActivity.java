/*
 * *
 *  * Created by damvulong on 11/15/22, 5:56 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/15/22, 12:22 AM
 *
 */

package com.example.fani.presentation.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fani.databinding.ActivityForgotPasswordBinding;
import com.example.fani.presentation.login.LoginActivity;
import com.example.fani.utils.LogUtil;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;

    //init View Model
    private ForgotPasswordViewModel forgotPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** setup view biding
         Document: https://developer.android.com/topic/libraries/view-binding*/
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        forgotPasswordViewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        forgotPasswordViewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                LogUtil.e("Recover success");
            }
        });

        handleEvent();
    }

    private void handleEvent() {
        binding.btnRecover.setOnClickListener(view -> {
            checkValidationEmail();
        });
    }

    public void checkValidationEmail() {

        String userEmailRecover = binding.etEmailRecover.getText().toString();

        if (TextUtils.isEmpty(userEmailRecover)) {
            binding.etEmailRecover.setError("Required");
        } else {
            forgotPasswordViewModel.forgotPassword(userEmailRecover);
            startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
        }
    }
}