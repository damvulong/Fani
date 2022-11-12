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

public class AppRepository {

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    OnFirebaseStoreTaskComplete onFirebaseStoreTaskComplete;
    private CollectionReference categoryRef = mFirestore.collection("Category");


    public AppRepository(OnFirebaseStoreTaskComplete onFirebaseStoreTaskComplete) {
        this.onFirebaseStoreTaskComplete = onFirebaseStoreTaskComplete;
    }

    public void getCategoryData(){
        categoryRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                onFirebaseStoreTaskComplete.categoryDataAdded(task.getResult().toObjects(CategoryModel.class));
            }else{
                onFirebaseStoreTaskComplete.onError(task.getException());
            }
        });
    }

    public interface OnFirebaseStoreTaskComplete {
        void categoryDataAdded(List<CategoryModel> categoryModelList);

        void onError(Exception e);
    }

}
