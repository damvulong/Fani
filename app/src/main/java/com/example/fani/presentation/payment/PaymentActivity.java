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
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.fani.R;
import com.example.fani.databinding.ActivityPaymentBinding;
import com.example.fani.presentation.PaymentMethodActivity;
import com.maxpilotto.creditcardview.CreditCardView;
import com.maxpilotto.creditcardview.animations.RotationAnimation;
import com.maxpilotto.creditcardview.models.Brand;
import com.maxpilotto.creditcardview.models.CardArea;

public class PaymentActivity extends AppCompatActivity {

    private ActivityPaymentBinding binding;

    int amount = 0;

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

        amount = getIntent().getIntExtra("amount", 0);

        handleEvent();

        creditCardView();

        binding.tvSubTotalMoney.setText(amount + "$");
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