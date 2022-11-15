/*
 * *
 *  * Created by damvulong on 11/9/22, 2:35 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/9/22, 2:33 PM
 *
 */

package com.example.fani.presentation.fragment.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fani.data.model.CategoryModel;
import com.example.fani.data.repositories.AppRepository;
import com.example.fani.utils.LogUtil;

import java.util.List;

public class HomeViewModel extends ViewModel implements AppRepository.OnFirebaseStoreTaskComplete {
    MutableLiveData<List<CategoryModel>> categoryListMutableLiveData = new MutableLiveData<>();

    private AppRepository firebaseRepository = new AppRepository(this);

    public LiveData<List<CategoryModel>> getQuizListModelData() {
        return categoryListMutableLiveData;
    }

    public HomeViewModel() {
        firebaseRepository.getCategoryData();
    }

    public MutableLiveData<List<CategoryModel>> getCategoryLiveData() {
        return categoryListMutableLiveData;
    }


    @Override
    public void categoryDataAdded(List<CategoryModel> categoryModelList) {
        categoryListMutableLiveData.setValue(categoryModelList);
    }

    @Override
    public void onError(Exception e) {
        LogUtil.e(e.getMessage());
    }
}
