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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText Email;
    EditText Password;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();

    }

    public void onLogin(View view) {

        String userEmail = Email.getText().toString();
        String userPassword = Password.getText().toString();

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

        auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        } else {
                            Toast.makeText(LoginActivity.this, "Error: "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void onRegister(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }


    private void initUI() {
        Email = findViewById(R.id.et_email);
        Password = findViewById(R.id.et_password);

        auth = FirebaseAuth.getInstance();

    }
}