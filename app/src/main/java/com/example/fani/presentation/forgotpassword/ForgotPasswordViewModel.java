/*
 * *
 *  * Created by damvulong on 11/15/22, 5:59 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/15/22, 5:59 PM
 *
 */

package com.example.fani.presentation.forgotpassword;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fani.data.repositories.AuthAppRepository;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPasswordViewModel extends AndroidViewModel {
    //init
    private AuthAppRepository authAppRepository;
    private MutableLiveData<FirebaseUser> userLiveData;

    public ForgotPasswordViewModel(@NonNull Application application) {
        super(application);
        authAppRepository = new AuthAppRepository(application);
        userLiveData = authAppRepository.getUserLiveData();
    }

    public void forgotPassword(String email) {
        authAppRepository.forgotPassword(email);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }
}
