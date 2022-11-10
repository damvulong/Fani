/*
 * *
 *  * Created by damvulong on 4/29/22, 2:09 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 4/29/22, 2:09 AM
 *
 */

package com.example.fani.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.fani.R;
import com.example.fani.presentation.fragment.CartFragment;
import com.example.fani.utils.LogUtil;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentMethodActivity extends AppCompatActivity implements PaymentResultListener {

    Toolbar toolbar;
    Button btnPaypal;
    Button btnRazorpay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        initUI();

        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Pay by RazorPay
        btnRazorpay.setOnClickListener(view -> {
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

    private void initUI() {
        toolbar = findViewById(R.id.method_toolbar);
        btnPaypal = findViewById(R.id.pay_paypal);
        btnRazorpay = findViewById(R.id.pay_razorpay);
    }
}