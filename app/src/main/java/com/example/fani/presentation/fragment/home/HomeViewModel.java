/*
 * *
 *  * Created by damvulong on 11/9/22, 2:35 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/9/22, 2:33 PM
 *
 */

package com.example.fani.presentation.fragment.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fani.base.Subscribe;
import com.example.fani.data.model.CategoryModel;
import com.example.fani.data.model.NewProductsModel;
import com.example.fani.data.model.PopularProductsModel;
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
    private CompositeDisposable newProductDisposable = new CompositeDisposable();
    private CompositeDisposable popularProductDisposable = new CompositeDisposable();
    MutableLiveData<List<CategoryModel>> categoryListMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<NewProductsModel>> newProductListMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<PopularProductsModel>> popularProductListMutableLiveDate = new MutableLiveData<>();

    @Inject
    public HomeViewModel(AppRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void getNewProduct(){
        firebaseRepository.getNewProductObs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscribe<List<NewProductsModel>>(){
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.e("New Product onSubscribe");
                        newProductDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<NewProductsModel> newProductsModels) {
                        LogUtil.e("New Product onNext"+ newProductsModels.toString());
                        LogUtil.e("New Product Observe onNext thread" + Thread.currentThread().getName());
                        // Check thread
                        newProductListMutableLiveData.postValue(newProductsModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("New Product onError"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        LogUtil.e("New Product onComplete");
                        LogUtil.e("New Product observe onComplete thread" + Thread.currentThread().getName());
                    }
                });
    }

    public void getPopularProduct(){
        firebaseRepository.getPopularProductObs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscribe<List<PopularProductsModel>>(){
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.e("Popular Product onSubscribe");
                        newProductDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<PopularProductsModel> popularProductsModels) {
                        LogUtil.e("Popular Product onNext"+ popularProductsModels.toString());
                        LogUtil.e("Popular Product Observe onNext thread" + Thread.currentThread().getName());
                        // Check thread
                        popularProductListMutableLiveDate.postValue(popularProductsModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("Popular Product onError"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        LogUtil.e("Popular Product onComplete");
                        LogUtil.e("Popular Product observe onComplete thread" + Thread.currentThread().getName());
                    }
                });
    }


    public void  getCategories() {
        firebaseRepository.getCategoryObs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscribe<List<CategoryModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.e("Category onSubscribe");
                        categoryDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<CategoryModel> categoryModelList) {
                        LogUtil.e("Category onNext"+ categoryModelList.toString());
                        LogUtil.e("Category Observe onNext thread" + Thread.currentThread().getName());
                        // Check thread
                        categoryListMutableLiveData.postValue(categoryModelList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("Category onError"+e.getMessage());
                        LogUtil.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        LogUtil.e("Category onComplete");
                        LogUtil.e("Category Observe onComplete thread" + Thread.currentThread().getName());
                    }
                });
    }

    public MutableLiveData<List<CategoryModel>> getCategoryLiveData() {
        return categoryListMutableLiveData;
    }

    public MutableLiveData<List<NewProductsModel>> getNewProductLiveData() {
        return newProductListMutableLiveData;
    }

    public MutableLiveData<List<PopularProductsModel>> getPopularProductLiveData() {
        return popularProductListMutableLiveDate;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        categoryDisposable.clear();
        newProductDisposable.clear();
        popularProductDisposable.clear();
    }
}
