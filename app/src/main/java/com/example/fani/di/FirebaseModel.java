/*
 * *
 *  * Created by damvulong on 11/15/22, 10:37 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/15/22, 10:37 AM
 *
 */

package com.example.fani.di;

import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class FirebaseModel {

    @Provides
    @Singleton
    public FirebaseFirestore provideFireStoreInstance(){
        return FirebaseFirestore.getInstance();
    }
}
