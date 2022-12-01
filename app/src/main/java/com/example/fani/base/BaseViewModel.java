
package com.example.fani.base;

import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {

    protected CompositeDisposable disposable  = new CompositeDisposable();

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
