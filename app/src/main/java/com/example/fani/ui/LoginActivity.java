package com.example.fani.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fani.R;
import com.example.fani.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_login);

    }

    public void onLogin(View view) {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    public void onRegister(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}