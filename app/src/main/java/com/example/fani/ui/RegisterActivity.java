/*
 * *
 *  * Created by damvulong on 4/18/22, 10:27 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 4/12/22, 7:03 PM
 *
 */

package com.example.fani.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fani.R;
import com.example.fani.databinding.ActivityMainBinding;
import com.example.fani.utils.LogUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText Name;
    EditText Email;
    EditText PhoneNumber;
    EditText Password;
    EditText ConfirmPassword;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();

        LogUtil.d(auth.getCurrentUser().toString());
//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
//            finish();
//        }
    }

    public void onRegister(View view) {
        String userName = Name.getText().toString();
        String userEmail = Email.getText().toString();
        String userPhoneNumber = PhoneNumber.getText().toString();
        String userPassword = Password.getText().toString();
        String userConfirmPassword = ConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Please Enter Username!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Please Enter Your Email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPhoneNumber)) {
            Toast.makeText(this, "Please Enter Your Phone Number!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Please Enter Password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userConfirmPassword)) {
            Toast.makeText(this, "Please Confirm Password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.length() < 6) {
            Toast.makeText(this, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword != userConfirmPassword) {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Successfully Register!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration Failed!" + task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    public void onLogin(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    private void initUI() {
        Name = findViewById(R.id.et_name);
        Email = findViewById(R.id.et_email);
        PhoneNumber = findViewById(R.id.et_phone_number);
        Password = findViewById(R.id.et_password);
        ConfirmPassword = findViewById(R.id.et_confirm_password);

        auth = FirebaseAuth.getInstance();

    }


}
