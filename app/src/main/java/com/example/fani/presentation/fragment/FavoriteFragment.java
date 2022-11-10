/*
 * *
 *  * Created by thaituan on 11/9/22, 2:35 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/9/22, 2:33 PM
 *
 */

package com.example.fani.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fani.R;
import com.example.fani.data.model.MyFavoriteModel;
import com.example.fani.presentation.adapter.MyFavoriteAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {

    RecyclerView recyclerView;
    List<MyFavoriteModel> myFavoriteModelList;
    MyFavoriteAdapter myFavoriteAdapter;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        recyclerView = root.findViewById(R.id.rcv_fav);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        myFavoriteModelList = new ArrayList<>();
        myFavoriteAdapter = new MyFavoriteAdapter(getActivity(), myFavoriteModelList);
        recyclerView.setAdapter(myFavoriteAdapter);

        firestore.collection("AddToFav").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc:task.getResult().getDocuments()){

                        String documentId = doc.getId();

                        MyFavoriteModel myFavoriteModel = doc.toObject(MyFavoriteModel.class);

                        myFavoriteModel.setDocumentId(documentId);

                        myFavoriteModelList.add(myFavoriteModel);
                        myFavoriteAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        return root;
    }
}