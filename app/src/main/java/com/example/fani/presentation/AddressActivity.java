/*
 * *
 *  * Created by damvulong on 4/20/22, 5:52 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 4/20/22, 5:52 AM
 *
 */

package com.example.fani.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fani.R;
import com.example.fani.presentation.adapter.AddressAdapter;
import com.example.fani.data.model.AddressModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {

    Button addAddress;
    Button payment;

    RecyclerView recyclerView;
    private List<AddressModel> addressModelList;
    private AddressAdapter addressAdapter;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    String mAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initUI();

        //get Data from detailed activity
        //Object obj = getIntent().getSerializableExtra("item");

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addressModelList = new ArrayList<>();
        addressAdapter = new AddressAdapter(getApplicationContext(), addressModelList, this);
        recyclerView.setAdapter(addressAdapter);

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

        //Event Add Address
        addAddress.setOnClickListener(view -> startActivity(new Intent(AddressActivity.this, AddAddressActivity.class)));

        //Event Payment
        payment.setOnClickListener(view -> {

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

        addAddress = findViewById(R.id.btn_add_address);
        payment = findViewById(R.id.btn_payment);

        recyclerView = findViewById(R.id.rcv_address);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void setAddress(String address) {
        mAddress = address;
    }
}