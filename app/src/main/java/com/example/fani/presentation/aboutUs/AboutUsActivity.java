/*
 * *
 *  * Created by damvulong on 11/12/22, 7:22 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 11/11/22, 4:50 PM
 *
 */

package com.example.fani.presentation.aboutUs;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fani.R;
import com.example.fani.utils.Constants;
import com.example.fani.utils.Utilities;


import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class AboutUsActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Element adsElement = new Element();
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription("This is an application for final year project")
                .addEmail("longdvgcd17238@fpt.edu.vn", getString(R.string.email))
                .addGitHub(Constants.LINK_GIT, getString(R.string.gitHub))
                .addItem(Utilities.createCopyright(AboutUsActivity.this))
                .create();
        setContentView(aboutPage);
    }


}
