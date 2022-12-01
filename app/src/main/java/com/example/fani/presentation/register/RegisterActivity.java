/*
 * *
 *  * Created by damvulong on 4/18/22, 10:30 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/9/22, 2:18 PM
 *
 */

package com.example.fani.presentation.register;

import static com.example.fani.R.string.Error_email_empty;
import static com.example.fani.R.string.Error_password_empty;
import static com.example.fani.R.string.Error_password_not_match;
import static com.example.fani.R.string.Error_phone_number_empty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fani.R;
import com.example.fani.databinding.ActivityRegisterBinding;
import com.example.fani.presentation.login.LoginActivity;
import com.example.fani.utils.Constants;
import com.example.fani.utils.LogUtil;

import java.util.Locale;


public class RegisterActivity extends AppCompatActivity {

    // init a view binding
    ActivityRegisterBinding binding;

    //init View Model
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /** setup view biding
         Document: https://developer.android.com/topic/libraries/view-binding*/
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        loadLocale();

        //setting viewModel
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                LogUtil.e("Register success");
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
        binding.tvHaveAccount.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
        binding.btnRegister.setOnClickListener(view -> {
            checkRegister();
        });
    }

    public void checkRegister() {
        String userName = binding.etName.getText().toString();
        String userEmail = binding.etEmail.getText().toString();
        String userPhoneNumber = binding.etPhoneNumber.getText().toString();
        String userPassword = binding.etPassword.getText().toString();
        String userConfirmPassword = binding.etConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, R.string.Error_username_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, Error_email_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPhoneNumber)) {
            Toast.makeText(this, Error_phone_number_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, Error_password_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userConfirmPassword)) {
            Toast.makeText(this, R.string.Error_confirm_password_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.length() < Constants.MAXIMUM_PASSWORD) {
            Toast.makeText(this, R.string.Error_password_short, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!userPassword.equals(userConfirmPassword)) {
            Toast.makeText(this, Error_password_not_match, Toast.LENGTH_SHORT).show();
            return;
        }

        registerViewModel.register(userEmail, userPassword);
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}
