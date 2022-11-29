/*
 * *
 *  * Created by damvulong on 11/29/22, 3:28 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/29/22, 3:28 PM
 *
 */

package com.example.fani.presentation.fragment.cart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fani.base.Subscribe;
import com.example.fani.data.model.MyCartModel;
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
public class CartViewModel extends ViewModel {

    private AppRepository firebaseRepository;
    private CompositeDisposable cartDisposable = new CompositeDisposable();
    MutableLiveData<List<MyCartModel>> cartListMutableLiveData = new MutableLiveData<>();

    @Inject
    public CartViewModel(AppRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public void  getCart() {
        firebaseRepository.getCartObs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscribe<List<MyCartModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        cartDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<MyCartModel> cartModelList) {
                        cartListMutableLiveData.postValue(cartModelList);
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

    public MutableLiveData<List<MyCartModel>> getCartLiveData() {
        return cartListMutableLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        cartDisposable.clear();
    }

}
