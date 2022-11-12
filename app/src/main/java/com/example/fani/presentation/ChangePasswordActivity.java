/*
 * *
 *  * Created by damvulong on 5/9/22, 10:41 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 5/9/22, 10:41 AM
 *
 */

package com.example.fani.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fani.R;
import com.example.fani.databinding.ActivityChangePasswordBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private ActivityChangePasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Setup view biding
         Document: https://developer.android.com/topic/libraries/view-binding*/
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        handleEvent();
    }

    private void handleEvent() {
        binding.btnChangeNewPassword.setOnClickListener(view -> {
            onClickChangePassword();
        });
    }

    private void onClickChangePassword() {
        String newPass = binding.etNewPassword.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updatePassword(newPass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ChangePasswordActivity.this, "Your Password Updated", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}