/*
 * *
 *  * Created by damvulong on 4/29/22, 2:09 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 4/29/22, 2:09 AM
 *
 */

package com.example.fani.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fani.BuildConfig;
import com.example.fani.R;
import com.example.fani.databinding.ActivityPaymentMethodBinding;
import com.example.fani.presentation.fragment.CartFragment;
import com.example.fani.utils.Constants;
import com.example.fani.utils.LogUtil;
import com.example.fani.utils.Utilities;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import vn.momo.momo_partner.AppMoMoLib;
import vn.momo.momo_partner.MoMoParameterNameMap;

public class PaymentMethodActivity extends AppCompatActivity implements PaymentResultListener {

    private ActivityPaymentMethodBinding binding;

    private String username = "Fani Furniture App";
    private String clientId = "billid_89733120112";
    private String partnerCode = "FANI";

    public static PayPalConfiguration configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(BuildConfig.CLIENT_ID);

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

            checkout.setKeyID(BuildConfig.APP_KEY_RAZORPAY);
            checkout.setImage(R.drawable.amongus);

            JSONObject object = new JSONObject();

            try {
                //Set Company Name
                object.put("name", username);
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


        // Pay by momo
        // TODO https://business.momo.vn/ need to merchantName from momo
        // Currently due to system maintenance
        binding.btnPayMomo.setOnClickListener(view12 -> {
            requestMapping();
        });

        // Pay by Paypal
        // https://github.com/paypal/PayPal-Android-SDK
        binding.btnPayPayPal.setOnClickListener(view13 -> {
            getPayment();
        });

    }

    private void getPayment() {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(CartFragment.amount)), "USD", username, PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, Constants.PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.PAYPAL_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (confirm != null) {
                    try {
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        JSONObject payObj = new JSONObject(paymentDetails);
                        LogUtil.e("" + payObj);
                        Utilities.showToast(getApplicationContext(), "PaymentConfirmation info received from PayPal");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        LogUtil.e("Something went wrong");
                    }
                }
            } else if (requestCode == Activity.RESULT_CANCELED) {
                LogUtil.e("Something went wrong");
            } else {
                LogUtil.e("Something went wrong else");
            }
        }
    }

    private void requestMapping() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.MAP);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.LINK);

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put(MoMoParameterNameMap.CLIENT_ID, clientId);
        eventValue.put(MoMoParameterNameMap.USER_NAME, username);
        eventValue.put(MoMoParameterNameMap.PARTNER_CODE, partnerCode);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("key", "value");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        eventValue.put(MoMoParameterNameMap.EXTRA, jsonObject);
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
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

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}