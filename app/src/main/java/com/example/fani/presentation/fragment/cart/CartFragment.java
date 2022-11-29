/*
 * *
 *  * Created by damvulong on 11/29/22, 3:16 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/27/22, 12:51 AM
 *
 */

package com.example.fani.presentation.fragment.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fani.R;
import com.example.fani.data.model.MyCartModel;
import com.example.fani.presentation.adapter.MyCartAdapter;
import com.example.fani.presentation.address.AddressActivity;
import com.example.fani.presentation.fragment.home.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CartFragment extends Fragment {

    TextView total;
    Button btnBuyNow;

    RecyclerView recyclerView;
    List<MyCartModel> myCartModelList;
    MyCartAdapter myCartAdapter;

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    //Init ViewModel
    private CartViewModel cartViewModel;

    public static double amount = 0.0;


    public CartFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        //define viewModel
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        total = root.findViewById(R.id.tv_total);

        recyclerView = root.findViewById(R.id.rcv_cart);

        myCartAdapter = new MyCartAdapter(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        myCartModelList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        //myCartAdapter = new MyCartAdapter(getActivity(), myCartModelList);
        recyclerView.setAdapter(myCartAdapter);

        cartViewModel.getCart();
        cartViewModel.getCartLiveData().observe(getViewLifecycleOwner(), cartModelList -> {
            myCartAdapter.updateItemsCartListModel(cartModelList);
            recyclerView.setVisibility(View.VISIBLE);
            myCartAdapter.notifyDataSetChanged();
        });
        calculateTotalAmount(myCartModelList);



        /*firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
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
        });*/

        btnBuyNow = root.findViewById(R.id.buy_now);
        btnBuyNow.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), AddressActivity.class);
            startActivity(intent);
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