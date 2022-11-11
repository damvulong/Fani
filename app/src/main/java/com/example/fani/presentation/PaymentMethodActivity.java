/*
 * *
 *  * Created by damvulong on 4/29/22, 2:09 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 4/29/22, 2:09 AM
 *
 */

package com.example.fani.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.fani.R;
import com.example.fani.databinding.ActivityDetailedBinding;
import com.example.fani.databinding.ActivityPaymentMethodBinding;
import com.example.fani.presentation.fragment.CartFragment;
import com.example.fani.utils.LogUtil;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentMethodActivity extends AppCompatActivity implements PaymentResultListener {

    private ActivityPaymentMethodBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

           /* setup view biding
         Document: https://developer.android.com/topic/libraries/view-binding*/
        binding = ActivityPaymentMethodBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        //Toolbar
        setSupportActionBar(binding.tbMethod);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Pay by RazorPay
        binding.btnPayRazorpay.setOnClickListener(view1 -> {
            Checkout checkout = new Checkout();

            checkout.setKeyID("rzp_test_L4tt6SFP4UJPVt");
            checkout.setImage(R.drawable.amongus);

            JSONObject object = new JSONObject();

            try {
                //Set Company Name
                object.put("name", "Fani Furniture App");
                //Ref no
                object.put("description", "Reference No. #123");
                //Currency
                object.put("currency", "USD");
                object.put("amount", CartFragment.amount * 100);
                //Put mobile number
                object.put("prefill.contact", "+84795664880");
                //Put email
                object.put("prefill.email", "kingdamlong1708@gmail.com");
                //Open Razorpay
                checkout.open(PaymentMethodActivity.this, object);
            } catch (JSONException e) {
                LogUtil.e("Error payment" + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
//        //Initialize alert dialog
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        //Set title
//        builder.setTitle("Payment ID");
//        //Set message
//        builder.setMessage(s);
//        //Show alert dialog
//        builder.show();
        Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Cancel", Toast.LENGTH_SHORT).show();
    }
}