/*
 * *
 *  * Created by damvulong on 11/29/22, 3:16 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/26/22, 10:48 PM
 *
 */

package com.example.fani.presentation.fragment.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.fani.R;
import com.example.fani.presentation.ChangePasswordActivity;


public class ProfileFragment extends Fragment {

    Button btnChangePassword;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        btnChangePassword = root.findViewById(R.id.btn_change_password);

        btnChangePassword.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        });
        return root;
    }
}