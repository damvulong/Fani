/*
 * *
 *  * Created by damvulong on 11/12/22, 7:28 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/10/22, 3:00 PM
 *
 */

package com.example.fani.presentation.payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fani.databinding.ActivityPaymentBinding;
import com.example.fani.presentation.PaymentMethodActivity;
import com.example.fani.presentation.fragment.CartFragment;
import com.maxpilotto.creditcardview.animations.RotationAnimation;
import com.maxpilotto.creditcardview.models.Brand;

public class PaymentActivity extends AppCompatActivity {

    private ActivityPaymentBinding binding;

    //int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** setup view biding
         Document: https://developer.android.com/topic/libraries/view-binding*/
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Toolbar
        setSupportActionBar(binding.tbPayment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handleEvent();

        creditCardView();

        binding.tvSubTotalMoney.setText(String.valueOf(CartFragment.amount) + "$");

        binding.tvTotalMoney.setText(String.valueOf(CartFragment.amount) + "$");
    }

    private void handleEvent() {
        //Event pay
        binding.btnPay.setOnClickListener(view -> {
            startActivity(new Intent(PaymentActivity.this, PaymentMethodActivity.class));
        });
    }

    private void creditCardView() {
        binding.ccvPayment.setFlipOnClick(true);
        binding.ccvPayment.setFlipOnEditAnimation(new RotationAnimation());
        binding.ccvPayment.setStyle(Brand.VISA, com.maxpilotto.creditcardview.R.style.DefaultVisa);
        // TODO set text view
       // card.pairInput(CardInput.HOLDER,textView);

       // card.pairInput();
    }
}