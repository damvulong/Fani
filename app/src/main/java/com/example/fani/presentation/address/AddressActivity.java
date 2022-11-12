/*
 * *
 *  * Created by damvulong on 11/12/22, 8:55 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/12/22, 7:28 PM
 *
 */

package com.example.fani.presentation.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fani.R;
import com.example.fani.data.model.AddressModel;
import com.example.fani.databinding.ActivityAddressBinding;
import com.example.fani.presentation.addaddress.AddAddressActivity;
import com.example.fani.presentation.adapter.AddressAdapter;
import com.example.fani.presentation.payment.PaymentActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {

    private ActivityAddressBinding binding;

    private List<AddressModel> addressModelList;
    private AddressAdapter addressAdapter;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    String mAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Setup view biding
         Document: https://developer.android.com/topic/libraries/view-binding*/
        binding = ActivityAddressBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initUI();

        //get Data from detailed activity
        //Object obj = getIntent().getSerializableExtra("item");

        binding.rvAddress.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addressModelList = new ArrayList<>();
        addressAdapter = new AddressAdapter(getApplicationContext(), addressModelList, this);
        binding.rvAddress.setAdapter(addressAdapter);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc:task.getResult().getDocuments()){
                        AddressModel addressModel = doc.toObject(AddressModel.class);
                        addressModelList.add(addressModel);
                        addressAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        handleEvent();


    }

    private void handleEvent() {
        //Event add address
        binding.btnAddAddress.setOnClickListener(view -> {
            startActivity(new Intent(AddressActivity.this, AddAddressActivity.class));
        });
        //Event Payment
        binding.btnPayment.setOnClickListener(view -> {

            /*double amount = 0.0;

            if (obj instanceof NewProductsModel) {
                NewProductsModel newProductsModel = (NewProductsModel) obj;
                amount = newProductsModel.getPrice();
            } if (obj instanceof PopularProductsModel) {
                PopularProductsModel popularProductsModel = (PopularProductsModel) obj;
                amount = popularProductsModel.getPrice();
            } if (obj instanceof ShowAllModel) {
                ShowAllModel showAllModel = (ShowAllModel) obj;
                amount = showAllModel.getPrice();
            }*/

            //int amount = getIntent().getIntExtra("totalAmount", 0);

            int amount = 0;

            amount = getIntent().getIntExtra("totalAmount",0);

            Intent intent = new Intent(AddressActivity.this, PaymentActivity.class);
            //intent.putExtra("amount", amount);
            intent.putExtra("amount", amount);

            //intent.putExtra("amount", amount);
            startActivity(intent);

        });
    }
    private void initUI() {
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void setAddress(String address) {
        mAddress = address;
    }
}