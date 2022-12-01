/*
 * *
 *  * Created by damvulong on 11/11/22, 5:20 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/11/22, 5:20 PM
 *
 */

package com.example.fani.data.repositories;

import com.example.fani.data.model.CategoryModel;
import com.example.fani.data.model.MyCartModel;
import com.example.fani.data.model.NewProductsModel;
import com.example.fani.data.model.PopularProductsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class AppRepository {

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private CollectionReference categoryRef = mFirestore.collection("Category");
    private CollectionReference newProductRef = mFirestore.collection("NewProducts");
    private CollectionReference popularProductRef = mFirestore.collection("AllProducts");

    private CollectionReference cartRef = mFirestore
            .collection("AddToCart")
            .document(auth.getCurrentUser().getUid())
            .collection("User");

    @Inject
    public AppRepository() {

    }

    /**
     * Category observable
     */
    public Observable<List<CategoryModel>> getCategoryObs() {
        return Observable.create(emitter -> {
            categoryRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onNext(task.getResult().toObjects(CategoryModel.class));
                    emitter.onComplete();
                } else {
                    emitter.onError(task.getException());
                }
            });
        });
    }

    /**
     * New Product observable
     */
    public Observable<List<NewProductsModel>> getNewProductObs() {
        return Observable.create(emitter -> {
            newProductRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onNext(task.getResult().toObjects(NewProductsModel.class));
                    emitter.onComplete();
                } else {
                    emitter.onError(task.getException());
                }
            });
        });
    }

    /**
     * Popular Product observable
     */
    public Observable<List<PopularProductsModel>> getPopularProductObs() {
        return Observable.create(emitter -> {
            popularProductRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onNext(task.getResult().toObjects(PopularProductsModel.class));
                    emitter.onComplete();
                } else {
                    emitter.onError(task.getException());
                }
            });
        });
    }

    /**
     * Cart observable
     */
    public Observable<List<MyCartModel>> getCartObs() {
        return Observable.create(emitter -> {
            cartRef.get().addOnCompleteListener(task -> {
                if (auth.getCurrentUser() == null) {
                    return;
                }
                if (task.isSuccessful()) {
                    List<MyCartModel> list = new ArrayList<>();
                    for (DocumentSnapshot doc : task.getResult()) {
                        String documentId = doc.getId();
                        MyCartModel myCartModel = doc.toObject(MyCartModel.class);
                        myCartModel.setDocumentId(documentId);
                        list.add(myCartModel);
                    }
                    emitter.onNext(list);
                    emitter.onComplete();
                } else {
                    emitter.onError(task.getException());
                }
            });
        });
    }

}
