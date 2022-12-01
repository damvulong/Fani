/*
 * *
 *  * Created by damvulong on 4/18/22, 10:30 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/9/22, 2:18 PM
 *
 */

package com.example.fani.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fani.base.BaseMVVMActivity;
import com.example.fani.databinding.ActivityLoginBinding;
import com.example.fani.databinding.ActivityMainBinding;
import com.example.fani.presentation.forgotpassword.ForgotPasswordActivity;
import com.example.fani.presentation.main.MainActivity;
import com.example.fani.presentation.main.MainViewModel;
import com.example.fani.presentation.register.RegisterActivity;
import com.example.fani.utils.Constants;
import com.example.fani.utils.LogUtil;
import com.example.fani.utils.Utilities;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends BaseMVVMActivity<ActivityLoginBinding,LoginViewModel> {

    @Override
    protected ActivityLoginBinding getLayoutBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<LoginViewModel> getViewModelClass() {
        return LoginViewModel.class;
    }

    @Override
    protected void initialize() {
        getViewModel().getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                LogUtil.e("Login success");
            }
        });

        handleEvent();
    }

    private void handleEvent() {
        /**Check validation email and password to pass view model*/
        getViewBinding().btnLogin.setOnClickListener(view -> {
            checkValidationEmailAndPassword();
        });
        /**Move to Register page*/
        getViewBinding().txtRegister.setOnClickListener(view -> onRegister());

        /**Move to Forgot Password page*/
        getViewBinding().tvForgotPassword.setOnClickListener(view -> onForgotPassword());
    }

    public void checkValidationEmailAndPassword() {
        // TODO fake data to check
        String userEmailFake = "admin@gmail.com";
        String passwordEmailFake = "123456";

        String userEmail = getViewBinding().etEmail.getText().toString();
        String userPassword = getViewBinding().etPassword.getText().toString();

        //TODO fake data to check
        userEmail = userEmailFake;
        userPassword = passwordEmailFake;

        if (TextUtils.isEmpty(userEmail)) {
            Utilities.showToast(this, "Please Enter Your Email!");
            return;
        }

        if (TextUtils.isEmpty(userPassword)) {
            Utilities.showToast(this, "Please Enter Password!");
            return;
        }

        if (userPassword.length() < Constants.MAXIMUM_PASSWORD) {
            Utilities.showToast(this, "Password too short, enter minimum 6 characters!");
            return;
        }

        getViewModel().login(userEmail, userPassword);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));

    }

    public void onForgotPassword() {
        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
    }

    public void onRegister() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}