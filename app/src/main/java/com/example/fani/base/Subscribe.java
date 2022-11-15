package com.example.fani.base;/*
 * *
 *  * Created by damvulong on 11/14/22, 4:34 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/14/22, 4:34 PM
 *
 */

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


/**
 * Standard Subscribe
 */
public abstract class Subscribe<T> implements Observer<T> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {

    }

    @Override
    public void onError(@NonNull Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
