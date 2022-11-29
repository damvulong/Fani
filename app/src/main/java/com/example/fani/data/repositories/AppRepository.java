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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class AppRepository {

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private CollectionReference categoryRef = mFirestore.collection("Category");

    private CollectionReference cartRef = mFirestore.collection("AddToCart").document(auth.getCurrentUser().getUid()).collection("User");

    @Inject
    public AppRepository() {

    }

    // #35 This function move to RxAndroid
    /*    public void getCategoryData(){
        categoryRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                onFirebaseStoreTaskComplete.categoryDataAdded(task.getResult().toObjects(CategoryModel.class));
            }else{
                onFirebaseStoreTaskComplete.onError(task.getException());
            }
        });
    }*/

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

    // #35 This function move to RxAndroid
    /*    public interface OnFirebaseStoreTaskComplete {
        void categoryDataAdded(List<CategoryModel> categoryModelList);

        void onError(Exception e);
    }*/

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
