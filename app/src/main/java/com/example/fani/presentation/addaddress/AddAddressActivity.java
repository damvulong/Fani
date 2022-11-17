/*
 * *
 *  * Created by damvulong on 11/12/22, 8:56 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/10/22, 3:00 PM
 *
 */

package com.example.fani.presentation.addaddress;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fani.R;
import com.example.fani.databinding.ActivityAddAddressBinding;
import com.example.fani.utils.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    private ActivityAddAddressBinding binding;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Setup view biding
         Document: https://developer.android.com/topic/libraries/view-binding*/
        binding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        handleEvent();

        initUI();
    }

    private void handleEvent() {
        binding.btnAddAddress.setOnClickListener(view -> {
            checkAdd();
        });
    }

    private void checkAdd() {
        String userName = binding.etAddName.getText().toString();
        String userAddress = binding.etAddAddress.getText().toString();
        String userCity = binding.etAddCity.getText().toString();
        String userPostalCode = binding.etAddPostalCode.getText().toString();
        String userPhoneNumber = binding.etAddPhoneNumber.getText().toString();

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
                        Utilities.showToast(AddAddressActivity.this, "Address Added");
                    }
                }
            });
        } else {
            Utilities.showToast(AddAddressActivity.this, "Kindly Fill All Field");
        }
    }

    private void initUI() {
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }
}