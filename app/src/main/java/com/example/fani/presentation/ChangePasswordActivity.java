/*
 * *
 *  * Created by damvulong on 5/9/22, 10:41 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 5/9/22, 10:41 AM
 *
 */

package com.example.fani.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fani.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    Button btnChangeNewPass;
    EditText etNewPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        btnChangeNewPass = findViewById(R.id.btn_change_new_password);
        etNewPass = findViewById(R.id.et_new_password);

        btnChangeNewPass.setOnClickListener(view -> onClickChangePassword());
    }

    private void onClickChangePassword() {
        String newPass = etNewPass.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updatePassword(newPass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ChangePasswordActivity.this, "Your Password Updated", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}