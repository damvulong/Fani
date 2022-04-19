/*
 * *
 *  * Created by damvulong on 4/18/22, 10:32 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 4/12/22, 12:55 AM
 *
 */

package com.example.fani.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fani.R;


public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}