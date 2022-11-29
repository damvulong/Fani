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
import com.google.firebase.auth.FirebaseAuth;
import com.example.fani.data.model.NewProductsModel;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class AppRepository {

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private CollectionReference categoryRef = mFirestore.collection("Category");
    private CollectionReference newProductRef = mFirestore.collection("NewProducts");

    private CollectionReference cartRef = mFirestore.collection("AddToCart").document(auth.getCurrentUser().getUid()).collection("User");

    @Inject
    public AppRepository() {

    }

    /** Category observable*/
    public Observable<List<CategoryModel>> getCategoryObs(){
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

    /** New Product observable*/
    public  Observable<List<NewProductsModel>> getNewProductObs(){
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


    public Observable<List<MyCartModel>> getCartObs() {
        return Observable.create(emitter -> {
            cartRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    emitter.onNext(task.getResult().toObjects(MyCartModel.class));
                    emitter.onComplete();
                } else {
                    emitter.onError(task.getException());
                }
            });
        });
    }

}
