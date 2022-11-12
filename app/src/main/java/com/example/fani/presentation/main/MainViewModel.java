/*
 * *
 *  * Created by damvulong on 4/18/22, 10:30 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/9/22, 2:18 PM
 *
 */

package com.example.fani.presentation.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fani.data.repositories.AuthAppRepository;

public class MainViewModel extends AndroidViewModel {

    //init
    private AuthAppRepository authAppRepository;
    private MutableLiveData<Boolean> loggedOutLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        authAppRepository = new AuthAppRepository(application);
        loggedOutLiveData = authAppRepository.getLoggedOutLiveData();
    }

    public void logOut() {
        authAppRepository.logOut();
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }


}
