/*
 * *
 *  * Created by damvulong on 4/20/22, 7:24 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 4/20/22, 7:24 AM
 *
 */

package com.example.fani.ui;

import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fani.R;
import com.maxpilotto.creditcardview.CreditCardView;
import com.maxpilotto.creditcardview.animations.RotationAnimation;
import com.maxpilotto.creditcardview.models.Brand;
import com.maxpilotto.creditcardview.models.CardArea;
import com.maxpilotto.creditcardview.models.CardInput;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView subTotal;
    TextView discount;
    TextView shipping;
    CreditCardView card;
    CardArea area;

    int amount = 0;

    Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initUI();

        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        amount = getIntent().getIntExtra("amount", 0);

        btnPay.setOnClickListener(view -> {
            Intent intent = new Intent(PaymentActivity.this, PaymentMethodActivity.class);
            startActivity(intent);
        });

        creditCardView();

        subTotal.setText(amount + "$");
    }

    private void creditCardView() {
        card.setFlipOnClick(true);
        card.setFlipOnEditAnimation(new RotationAnimation());
        card.setStyle(Brand.VISA, com.maxpilotto.creditcardview.R.style.DefaultVisa);
        // TODO set text view
       // card.pairInput(CardInput.HOLDER,textView);

       // card.pairInput();

    }

    private void initUI() {
        toolbar = findViewById(R.id.payment_toolbar);
        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.tv_discount);
        shipping = findViewById(R.id.tv_shipping);

        btnPay = findViewById(R.id.btn_pay);
        card = findViewById(R.id.ccv_payment);

    }


}