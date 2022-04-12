package com.example.fani.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fani.R;
import com.example.fani.databinding.ActivityMainBinding;

public class RegisterActivity extends AppCompatActivity {

    EditText Name;
    EditText Email;
    EditText PhoneNumber;
    EditText Password;
    EditText ConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
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
            Toast.makeText(this, "Please Enter Your Phonenumber!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Password too short, enter minium 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
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

    }


}
