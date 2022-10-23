/*
 * *
 *  * Created by damvulong on 4/18/22, 10:32 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 4/12/22, 12:55 AM
 *
 */

package com.example.fani.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fani.R;
import com.example.fani.adapter.MyCartAdapter;
import com.example.fani.model.MyCartModel;
import com.example.fani.ui.AddressActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    TextView total;
    Button btnBuyNow;

    RecyclerView recyclerView;
    List<MyCartModel> myCartModelList;
    MyCartAdapter myCartAdapter;

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    public static double amount = 0.0;


    public CartFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        total = root.findViewById(R.id.tv_total);



        recyclerView = root.findViewById(R.id.rcv_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        myCartModelList = new ArrayList<>();
        myCartAdapter = new MyCartAdapter(getActivity(), myCartModelList);
        recyclerView.setAdapter(myCartAdapter);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc:task.getResult().getDocuments()){

                        String documentId = doc.getId();

                        MyCartModel myCartModel = doc.toObject(MyCartModel.class);

                        myCartModel.setDocumentId(documentId);

                        myCartModelList.add(myCartModel);
                        myCartAdapter.notifyDataSetChanged();
                    }
                }
                calculateTotalAmount(myCartModelList);
            }
        });

        btnBuyNow = root.findViewById(R.id.buy_now);
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddressActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    private void calculateTotalAmount(List<MyCartModel> myCartModelList) {
        double totalAmount = 0.0;
        for (MyCartModel myCartModel : myCartModelList) {
            totalAmount += myCartModel.getTotalPrice();
        }

        total.setText("Total Amount:   " + totalAmount+ "$");
        amount = totalAmount;
    }


}