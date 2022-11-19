/*
 * *
 *  * Created by damvulong on 4/18/22, 10:30 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/9/22, 2:18 PM
 *
 */

package com.example.fani.presentation.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fani.databinding.ActivityLoginBinding;
import com.example.fani.databinding.ActivityMainBinding;
import com.example.fani.presentation.main.MainActivity;
import com.example.fani.presentation.register.RegisterActivity;
import com.example.fani.utils.LogUtil;
import com.example.fani.utils.Utilities;

import java.util.Locale;

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

        loadLocale();

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                LogUtil.e("Login success");
            }
        });

        handleEvent();
    }

    private void setLocale(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("app_lang", language);
        editor.apply();

    }

    private void loadLocale() {
        SharedPreferences preferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = preferences.getString("app_lang", "");
        setLocale(language);
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
            Utilities.showToast(this, "Please Enter Your Email!");
            return;
        }

        if (TextUtils.isEmpty(userPassword)) {
            Utilities.showToast(this, "Please Enter Password!");
            return;
        }

        if (userPassword.length() < 6) {
            Utilities.showToast(this, "Password too short, enter minimum 6 characters!");
            return;
        }

        loginViewModel.login(userEmail, userPassword);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));

    }

    public void onRegister() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}