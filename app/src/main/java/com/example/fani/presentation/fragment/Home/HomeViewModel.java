/*
 * *
 *  * Created by thaituan on 11/9/22, 2:35 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/9/22, 2:33 PM
 *
 */

package com.example.fani.presentation.fragment.Home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fani.data.model.CategoryModel;

import java.util.List;

public class HomeViewModel extends ViewModel {

    MutableLiveData<List<CategoryModel>> categoryObs = new MutableLiveData<>();


   public void getCategory(){

   }



}
