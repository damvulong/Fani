/*
 * *
 *  * Created by damvulong on 11/9/22, 2:35 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/9/22, 2:33 PM
 *
 */

package com.example.fani.presentation.fragment.Home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fani.base.Subscribe;
import com.example.fani.data.model.CategoryModel;
import com.example.fani.data.repositories.AppRepository;
import com.example.fani.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class HomeViewModel  extends ViewModel {


    private AppRepository firebaseRepository;
    private CompositeDisposable categoryDisposable = new CompositeDisposable();
    MutableLiveData<List<CategoryModel>> categoryListMutableLiveData = new MutableLiveData<>();


    @Inject
    public HomeViewModel(AppRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void  getCategories() {
        firebaseRepository.getCategoryObs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscribe<List<CategoryModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        categoryDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<CategoryModel> categoryModelList) {
                        categoryListMutableLiveData.postValue(categoryModelList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
    }

    public MutableLiveData<List<CategoryModel>> getCategoryLiveData() {
        return categoryListMutableLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        categoryDisposable.clear();
    }
}
