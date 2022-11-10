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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fani.databinding.ActivityLoginBinding;
import com.example.fani.presentation.main.MainActivity;
import com.example.fani.presentation.register.RegisterActivity;
import com.example.fani.utils.LogUtil;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    //init View Model
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** setup view biding
         Document: https://developer.android.com/topic/libraries/view-binding*/
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                LogUtil.e("Login success");
            }
        });

        handleEvent();
    }

    private void handleEvent() {
        /**Check validation email and password to pass view model*/
        binding.btnLogin.setOnClickListener(view -> {
            checkValidationEmailAndPassword();
        });
        /**Move to Register page*/
        binding.txtRegister.setOnClickListener(view -> onRegister());
    }

    public void checkValidationEmailAndPassword() {
        // TODO fake data to check
        String userEmailFake = "admin@gmail.com";
        String passwordEmailFake = "123456";

        String userEmail = binding.etEmail.getText().toString();
        String userPassword = binding.etPassword.getText().toString();

        //TODO fake data to check
        userEmail = userEmailFake;
        userPassword = passwordEmailFake;

        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Please Enter Your Email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Please Enter Password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.length() < 6) {
            Toast.makeText(this, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        loginViewModel.login(userEmail, userPassword);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));

    }

    public void onRegister() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}