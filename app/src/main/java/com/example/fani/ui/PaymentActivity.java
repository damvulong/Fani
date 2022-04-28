/*
 * *
 *  * Created by damvulong on 4/20/22, 7:24 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 4/20/22, 7:24 AM
 *
 */

package com.example.fani.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fani.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView subTotal;
    TextView discount;
    TextView shipping;

    Button btnPay;

    String sAmount = "100";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initUI();

        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //amount = getIntent().getDoubleExtra("amount", 0.0);
        //subTotal.setText(amount+"$");


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentActivity.this, PaymentMethodActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initUI() {
        toolbar = findViewById(R.id.payment_toolbar);
        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.tv_discount);
        shipping = findViewById(R.id.tv_shipping);

        btnPay = findViewById(R.id.btn_pay);

    }


}