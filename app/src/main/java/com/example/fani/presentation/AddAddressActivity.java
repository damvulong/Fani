/*
 * *
 *  * Created by damvulong on 4/20/22, 6:12 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 4/20/22, 6:12 AM
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    EditText name;
    EditText address;
    EditText city;
    EditText postalCode;
    EditText phoneNumber;

    Button addAddress;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initUI();

        addAddress.setOnClickListener(view -> {

            String userName = name.getText().toString();
            String userAddress = address.getText().toString();
            String userCity = city.getText().toString();
            String userPostalCode = postalCode.getText().toString();
            String userPhoneNumber = phoneNumber.getText().toString();

            String final_address = "";

            if (!userName.isEmpty()) {
                final_address += userName;
            }

            if (!userAddress.isEmpty()) {
                final_address += userAddress;
            }

            if (!userCity.isEmpty()) {
                final_address += userCity;
            }

            if (!userPostalCode.isEmpty()) {
                final_address += userPostalCode;
            }

            if (!userPhoneNumber.isEmpty()) {
                final_address += userPhoneNumber;
            }

            if (!userName.isEmpty() && !userAddress.isEmpty() && !userCity.isEmpty() && !userPostalCode.isEmpty() && !userPhoneNumber.isEmpty()) {

                Map<String, String> map = new HashMap<>();
                map.put("userAddress", final_address);

                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AddAddressActivity.this, "Address Added", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(AddAddressActivity.this, "Kindly Fill All Field", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void initUI() {
        name = findViewById(R.id.ad_name);
        address = findViewById(R.id.address);
        city = findViewById(R.id.ad_city);
        postalCode = findViewById(R.id.ad_postal_code);
        phoneNumber = findViewById(R.id.ad_phone_number);

        addAddress = findViewById(R.id.btn_ad_address);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }
}