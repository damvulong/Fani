/*
 * *
 *  * Created by damvulong on 11/11/22, 5:20 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/11/22, 5:20 PM
 *
 */

package com.example.fani.data.repositories;

import com.example.fani.data.model.CategoryModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class AppRepository {

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private CollectionReference categoryRef = mFirestore.collection("Category");

    @Inject
    public AppRepository() {

    }

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

    public interface OnFirebaseStoreTaskComplete {
        void categoryDataAdded(List<CategoryModel> categoryModelList);

        void onError(Exception e);
    }

}
